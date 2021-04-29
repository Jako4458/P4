import org.stringtemplate.v4.ST;

public class SetBlockST implements Template {

    private final String output;

    /* destID is the id of the vector saved on the player as destID_x, destID_y, destID_z
    *  blockID is the id of the armorstand which stands on top of the block
    *  relativeID is the id of the scoreboard value of a boolean expression
    * SetBlock should set an armorstand at the desired destination,
    * and then clone the block at blockID to the desired position.
    */
    public SetBlockST(String destID, String blockID, String relativeID, String prefix) {
        ST template = new ST(
                "<prefix>summon minecraft:armor_stand ~ ~ ~ {Tags:[\"<tpID>\"],NoGravity:1}\n" +
                "<prefix>execute as @e[tag=<tpID>,limit=1] run function <folderID>:<fileID>\n" +
                "#FILE BEGIN: <fileID>\n" +
                "execute store result entity @s Pos[0] double 1 run scoreboard players get @p[tag=<active>] <X>\n" +
                "execute store result entity @s Pos[1] double 1 run scoreboard players get @p[tag=<active>] <Y>\n" +
                "execute store result entity @s Pos[2] double 1 run scoreboard players get @p[tag=<active>] <Z>\n" +
                "execute at @s as @p[tag=<active>] run forceload add ~-10 ~-10 ~10 ~10\n" +
                "#FILE END: <fileID>\n" +
                "<prefix>execute if score @p[tag=<active>] <relativeID> matches 1..1 run execute as @e[tag=<blockID>, limit=1] at @s run clone ~ ~-1 ~ ~ ~-1 ~ 0 255 0\n" +
                "<prefix>execute if score @p[tag=<active>] <relativeID> matches 1..1 run execute as @e[tag=<tpID>, limit=1] at @s run clone 0 255 0 0 255 0 ~ ~ ~\n" +
                "<prefix>execute if score @p[tag=<active>] <relativeID> matches 0..0 run execute as @e[tag=<blockID>, limit=1] at @s run clone ~ ~-1 ~ ~ ~-1 ~ 0 255 0\n" +
                "<prefix>execute if score @p[tag=<active>] <relativeID> matches 0..0 run execute as @e[tag=<tpID>, limit=1] at @s run clone 0 255 0 0 255 0 ~ ~ ~\n"
        );


        this.output = template.render();

        /*
        if (relativeID.value == true) {
            set armorstand at destination with tilde notation   : ID = posID
            execute as @e[tag=<blockID>, limit=1] at @s run clone ~ ~-1 ~ ~ ~-1 ~ 0 255 0
            execute as @e[tag=<posID>, limit=1] at @s run clone 0 255 0 0 255 0 ~ ~ ~
        } else {
            set armorstand at destination with absolute coordinates
            execute as @e[tag=<blockID>, limit=1] at @s run clone ~ ~-1 ~ ~ ~-1 ~ 0 255 0
            execute as @e[tag=<posID>, limit=1] at @s run clone 0 255 0 0 255 0 ~ ~ ~
        }

        summon armor_stand
        execute if score @p[tag=player] <relativeID> matches 1..1 run execute as

         */
    }


    @Override
    public String getOutput() {
        return this.output;
    }
}
