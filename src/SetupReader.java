import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import netscape.javascript.JSObject;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SetupReader {
    private static final String jsonPath = System.getProperty("user.dir") + "/setup.json";

    public Setup readSetupJSON() {
        try {
            File file = new File(jsonPath);
            if (!file.exists())
                return new Setup();

            FileReader fReader = new FileReader(file);
            Gson gson = new Gson();
            JsonReader jReader = new JsonReader(fReader);
            Setup setup = gson.fromJson(jReader, Setup.class);
            return setup;
        } catch (IOException e) {
            return null;
        }
    }
}
