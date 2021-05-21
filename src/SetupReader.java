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

    /**
     * Tries to load the settings from a json file.
     * The path for the file is the variable jsonPath.
     * If an error occurs, a default setup is returned instead.
     * @return the setup object
     */
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
            return new Setup();
        }
    }
}
