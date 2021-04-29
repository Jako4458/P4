import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.List;

public class BuiltinFuncs {
    public static MSFile createSetB(String playerTag, String destIDName, String blockIDName, String relativeIDName) {
        ST start = new ST("summon minecraft:armor_stand ~ ~ ~ {Tags:[\"tpID\"],NoGravity:1} \n" +
                "execute as @e[tag=tpID,limit=1] run function builtin:setbhelper\n");
        ST fileCall = new ST("execute store result entity @s Pos[0] double 1 run scoreboard players get @p[tag=<playerTag>] <X>\n" +
                "execute store result entity @s Pos[1] double 1 run scoreboard players get @p[tag=<playerTag>] <Y>\n" +
                "execute store result entity @s Pos[2] double 1 run scoreboard players get @p[tag=<playerTag>] <Z>\n" +
                "execute at @s as @p[tag=<playerTag>] run forceload add ~-10 ~-10 ~10 ~10\n");
        ST end = new ST("execute if score @p[tag=<playerTag>] <relativeID> matches 1..1 run execute as @e[tag=<blockID>, limit=1] at @s run clone ~ ~-1 ~ ~ ~-1 ~ 0 255 0\n" +
                "execute if score @p[tag=<playerTag>] <relativeID> matches 1..1 run execute as @e[tag=tpID, limit=1] at @s run clone 0 255 0 0 255 0 ~ ~ ~\n" +
                "execute if score @p[tag=<playerTag>] <relativeID> matches 0..0 run execute as @e[tag=<blockID>, limit=1] at @s run clone ~ ~-1 ~ ~ ~-1 ~ 0 255 0\n" +
                "execute if score @p[tag=<playerTag>] <relativeID> matches 0..0 run execute as @e[tag=tpID, limit=1] at @s run clone 0 255 0 0 255 0 ~ ~ ~\n" +
                "kill @e[tag=tpID]\n");

        fileCall.add("playerTag", playerTag);
        fileCall.add("X", destIDName + "_x");
        fileCall.add("Y", destIDName + "_y");
        fileCall.add("Z", destIDName + "_z");
        end.add("playerTag", playerTag);
        end.add("relativeID", relativeIDName);
        end.add("blockID", blockIDName);


        ArrayList<MSFile> content = new ArrayList<>() {{
            add(new FFile("", start.render()));
            add(new CFile("setbhelper",
                    new ArrayList<>(){{
                        add(new FFile("", fileCall.render()));
                    }}));
            add(new FFile("", end.render()));
        }};

        return new CFile("setb", content);
    }

}
