
import org.stringtemplate.v4.ST;

public class InstanST implements Template {
    private String output;
    
    public InstanST(String varName, String exprName, String prefix, boolean setComment){
        this(varName, exprName, Type._string, prefix, setComment);
    }

    public InstanST(String varName, String exprName, Type type, String prefix, boolean setComment) {
        if (type == Type._vector2 || type == Type._vector3)
            this.output =  createVectorInstant(varName, exprName, type, prefix, setComment).render();
        else if (type != Type._block) {
            ST template = new ST( "<Comment><prefix>scoreboard objectives add <varName> dummy \n" +
                    "<prefix>execute as @s store result score @s <varName> run scoreboard players get @s <exprName> \n");

            template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");

            template.add("varName", varName);
            template.add("exprName", exprName);
            template.add("prefix", prefix);

            output = template.render();
        }
    }

    public InstanST(String varName, BlockValue blockValue, Vector3 pos, String prefix, boolean setComment) {
        ST template = new ST( "<Comment><prefix>setblock <posX> <posY> <posZ> <block>\n" +
                "<prefix>summon armor_stand <posX> <posStandY> <posZ> {Tags:[\"<varName>\", \"variable\"],NoGravity:1}\n"
                );
        template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");
        template.add("prefix", prefix);
        template.add("varName", varName);
        template.add("block", blockValue.getValue().substring(1).toLowerCase());

        template.add("posX", pos.getX());
        template.add("posY", pos.getY());
        template.add("posZ", pos.getZ());

        template.add("posStandY", pos.getY()+1);

        output = template.render();
    }

    public InstanST(String varName, String exprName, Vector3 newBlockPos, Vector3 blockFactor1Pos, String prefix, boolean setComment) {
        ST template = new ST("<Comment><prefix><summon><prefix><assign>");

        ST summonTemplate = new ST("summon armor_stand <x> <y> <z> {Tags:[\"<varName>\", \"variable\"],NoGravity:1}\n");
        template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");

        summonTemplate.add("x", newBlockPos.getX());
        summonTemplate.add("y", newBlockPos.getY()+1);
        summonTemplate.add("z", newBlockPos.getZ());
        summonTemplate.add("varName", varName);

        template.add("summon", summonTemplate.render());

        AssignST assignTemplate = new AssignST(varName, blockFactor1Pos, exprName, prefix, false);
        template.add("assign", assignTemplate.getOutput());
        template.add("prefix", prefix);

        this.output = template.render();
    }

    public InstanST(String varName, String exprName, Vector3 newBlockPos, Vector3 blockFactor1Pos, String prefix, boolean setComment, String funcName) {
        ST template = new ST("<Comment><prefix><summon><prefix><assign>");

        ST summonTemplate = new ST("summon armor_stand <x> <y> <z> {Tags:[\"<varName>\",\"<func>\",\"variable\"],NoGravity:1}\n");
        template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");

        summonTemplate.add("x", newBlockPos.getX());
        summonTemplate.add("y", newBlockPos.getY()+1);
        summonTemplate.add("z", newBlockPos.getZ());
        summonTemplate.add("varName", varName);
        summonTemplate.add("func", funcName);

        template.add("summon", summonTemplate.render());

        AssignST assignTemplate = new AssignST(varName, blockFactor1Pos, exprName, prefix, false);
        template.add("assign", assignTemplate.getOutput());
        template.add("prefix", prefix);

        this.output = template.render();
    }


    private ST createVectorInstant(String varName, String exprName, Type type, String prefix, boolean setComment) {
        ST template = new ST("<Comment><X><Y><Z>");
        
        if (type == Type._vector2)
            template.add("Comment", setComment ? "#Vector2 Instan\n" : "");
        if (type == Type._vector3)
            template.add("Comment", setComment ? "#Vector3 Instan\n" : "");

        InstanST X = new InstanST(varName + "_x", exprName + "_x", Type._num, prefix, false);
        InstanST Y = new InstanST(varName + "_y", exprName + "_y", Type._num, prefix, false);
        InstanST Z = new InstanST(varName + "_z", exprName + "_z", Type._num, prefix, false);
        template.add("X", X.getOutput());
        template.add("Y", Y.getOutput());
        template.add("Z", Z.getOutput());

        return template;
    }

    public InstanST(String varName, int exprVal, String prefix, boolean setComment) {
        ST template = new ST( "<Comment><prefix>scoreboard objectives add <varName> dummy \n" +
                                "<prefix>scoreboard players set @s <varName> <exprVal> \n");

        template.add("Comment", setComment ? "#"+ this.getTemplateName() +"\n" : "");
        template.add("varName", varName);
        template.add("exprVal", exprVal);
        template.add("prefix", prefix);

        output = template.render();
    }

    public InstanST(String varName, Vector3Value vec3, String prefix, boolean setComment) {
        ST template = new ST("<Comment><InstanX><InstanY><InstanZ>");

        template.add("Comment", setComment ? "#Vector3 instan\n" : "");
        InstanST InstanX = new InstanST(varName + "_x", vec3.getValue().getX(), prefix, false);
        InstanST InstanY = new InstanST(varName + "_y", vec3.getValue().getY(), prefix, false);
        InstanST InstanZ = new InstanST(varName + "_z", vec3.getValue().getZ(), prefix, false);

        template.add("InstanX", InstanX.getOutput());
        template.add("InstanY", InstanY.getOutput());
        template.add("InstanZ", InstanZ.getOutput());
        output = template.render();
    }

    private String getTemplateName(){
        return this.getClass().toString().substring(6); //substring to remove "class "
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}