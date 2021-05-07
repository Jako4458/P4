import org.stringtemplate.v4.ST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BuiltinFuncs {
    public static HashMap<String, FuncEntry> paramMap = new HashMap<>();

    public static MSFile createSetB(String playerTag, String destIDName, String blockIDName, String relativeIDName) {
        ST start = new ST("execute as @p[tag=<playerTag>] run summon minecraft:armor_stand ~ ~ ~ {Tags:[\"tpID\",\"setb\"],NoGravity:1} \n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives add <X>_temp dummy\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives add <Y>_temp dummy\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives add <Z>_temp dummy\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players set @s <X>_temp 0\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players set @s <Y>_temp 0\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players set @s <Z>_temp 0\n" +
                "execute as @p[tag=<playerTag>] if score @s <cond> matches 1 store result score @s <X>_temp run data get entity @s Pos[0]\n" +
                "execute as @p[tag=<playerTag>] if score @s <cond> matches 1 store result score @s <Y>_temp run data get entity @s Pos[1]\n" +
                "execute as @p[tag=<playerTag>] if score @s <cond> matches 1 store result score @s <Z>_temp run data get entity @s Pos[2]\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players operation @s <X>_temp += @s <X>\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players operation @s <Y>_temp += @s <Y>\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players operation @s <Z>_temp += @s <Z>\n" +
                "execute as @e[tag=tpID,limit=1] run function builtin:setbhelper\n");
        ST fileCall = new ST("execute store result entity @s Pos[0] double 1 run scoreboard players get @p[tag=<playerTag>] <X>_temp\n" +
                "execute store result entity @s Pos[1] double 1 run scoreboard players get @p[tag=<playerTag>] <Y>_temp\n" +
                "execute store result entity @s Pos[2] double 1 run scoreboard players get @p[tag=<playerTag>] <Z>_temp\n" +
                "execute at @s as @p[tag=<playerTag>] run forceload add ~-10 ~-10 ~10 ~10\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives remove <X>_temp\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives remove <Y>_temp\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives remove <Z>_temp\n");
        ST end = new ST("execute as @e[tag=<blockID>, limit=1] at @s run clone ~ ~-1 ~ ~ ~-1 ~ 0 255 0\n" +
                "execute as @e[tag=tpID, limit=1] at @s run clone 0 255 0 0 255 0 ~ ~ ~\n" +
                "execute as @p[tag=<playerTag>] run kill @e[tag=setb]\n");

        start.add("X", destIDName + "_x");
        start.add("Y", destIDName + "_y");
        start.add("Z", destIDName + "_z");
        start.add("playerTag", playerTag);
        start.add("cond", relativeIDName);
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

        // String id, Type type, ParserRuleContext ctx, int modifier
        ArrayList<SymEntry> fParams = new ArrayList<SymEntry>() {{
            add(new SimpleEntry(destIDName, Type._vector3, null, MinespeakParser.VAR));
            add(new SimpleEntry(blockIDName, Type._block, null, MinespeakParser.VAR));
            add(new SimpleEntry(relativeIDName, Type._bool, null, MinespeakParser.VAR));
        }};

        BuiltinFuncs.paramMap.put("setB", new FuncEntry(true, "setB", Type._void, fParams, null));

        return new CFile("setb", content);
    }

    public static MSFile createTP(String playerTag, String destIDName, String relativeIDName) {
        ST start = new ST("execute as @p[tag=<playerTag>] run summon minecraft:armor_stand ~ ~ ~ {Tags:[\"tpID\",\"tp\"],NoGravity:1} \n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives add <X>_temp dummy\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives add <Y>_temp dummy\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives add <Z>_temp dummy\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players set @s <X>_temp 0\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players set @s <Y>_temp 0\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players set @s <Z>_temp 0\n" +
                "execute as @p[tag=<playerTag>] if score @s <cond> matches 1 store result score @s <X>_temp run data get entity @s Pos[0]\n" +
                "execute as @p[tag=<playerTag>] if score @s <cond> matches 1 store result score @s <Y>_temp run data get entity @s Pos[1]\n" +
                "execute as @p[tag=<playerTag>] if score @s <cond> matches 1 store result score @s <Z>_temp run data get entity @s Pos[2]\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players operation @s <X>_temp += @s <X>\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players operation @s <Y>_temp += @s <Y>\n" +
                "execute as @p[tag=<playerTag>] run scoreboard players operation @s <Z>_temp += @s <Z>\n" +
                "execute as @e[tag=tpID,limit=1] run function builtin:tphelper\n");
        ST fileCall = new ST("execute store result entity @s Pos[0] double 1 run scoreboard players get @p[tag=<playerTag>] <X>_temp\n" +
                "execute store result entity @s Pos[1] double 1 run scoreboard players get @p[tag=<playerTag>] <Y>_temp\n" +
                "execute store result entity @s Pos[2] double 1 run scoreboard players get @p[tag=<playerTag>] <Z>_temp\n" +
                "execute at @s as @p[tag=<playerTag>] run forceload add ~-10 ~-10 ~10 ~10\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives remove <X>_temp\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives remove <Y>_temp\n" +
                "execute as @p[tag=<playerTag>] run scoreboard objectives remove <Z>_temp\n");
        ST end = new ST("execute as @p[tag=<playerTag>] run tp @s @e[tag=tpID,limit=1]\n" +
                "execute as @p[tag=<playerTag>] run kill @e[tag=tp]\n");

        start.add("playerTag", playerTag);
        start.add("X", destIDName + "_x");
        start.add("Y", destIDName + "_y");
        start.add("Z", destIDName + "_z");
        start.add("cond", relativeIDName);
        fileCall.add("playerTag", playerTag);
        fileCall.add("X", destIDName + "_x");
        fileCall.add("Y", destIDName + "_y");
        fileCall.add("Z", destIDName + "_z");
        end.add("playerTag", playerTag);


        ArrayList<MSFile> content = new ArrayList<>() {{
            add(new FFile("", start.render()));
            add(new CFile("tphelper",
                    new ArrayList<>() {{
                        add(new FFile("", fileCall.render()));
                    }}));
            add(new FFile("", end.render()));
        }};

        ArrayList<SymEntry> fParams = new ArrayList<SymEntry>() {{
            add(new SimpleEntry(destIDName, Type._vector3, null, MinespeakParser.VAR));
            add(new SimpleEntry(relativeIDName, Type._bool, null, MinespeakParser.VAR));
        }};

        BuiltinFuncs.paramMap.put("tp", new FuncEntry(true, "tp", Type._void, fParams, null));

        return new CFile("tp", content);
    }





}
