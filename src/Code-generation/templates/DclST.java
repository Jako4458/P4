
import org.stringtemplate.v4.ST;

public class DclST implements Template {
    private String output;

    public DclST(String varName, Type type, String prefix, boolean setComment){
        ST template;

        if (type == Type._block){
            return;
        }
        else if (type == Type._vector2 || type == Type._vector3) {
            if (type == Type._vector2)
                template = new ST("<#Comment><DclX><DclY>");
            else
                template = new ST("<#Comment><DclX><DclY><DclZ>");

            DclST DclX = new DclST(varName + "_x", Type._num, prefix, false);
            DclST DclY = new DclST(varName + "_y", Type._num, prefix, false);

            template.add("DclX", DclX.getOutput());
            template.add("DclY", DclY.getOutput());

            if (type == Type._vector2)
                template.add("Comment", setComment ? "#Vector2 Dcl\n" : "");
            if (type == Type._vector3) {
                template.add("Comment", setComment ? "#Vector3 Dcl\n" : "");
                DclST DclZ = new DclST(varName + "_z", Type._num, prefix, false);
                template.add("DclZ", DclZ.getOutput());
            }
        }
        else {
            template = new ST(    "<Comment><prefix>scoreboard objectives add <varName> dummy \n" +
                                    "<prefix>scoreboard players set @s <varName> 0\n");

            template.add("Comment", setComment ? "#"+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "

            template.add("prefix", prefix);
            template.add("varName", varName);
        }

        output = template.render();
    }

    public DclST (String varName, Vector3 pos, Type type, String prefix, boolean setComment) {
        if (type != Type._block)
            return;

        ST template = new ST( "#<Comment>" +
                "<prefix>summon armor_stand <posX> <posStandY> <posZ> {Tags:[\"<varName>\", \"variable\"],NoGravity:1}"
        );

        template.add("Comment", setComment ? "#block "+this.getClass().toString().substring(6)+"\n" : ""); //substring to remove "class "
        template.add("prefix", prefix);

        template.add("varName", varName);
        template.add("posX", pos.getX());
        template.add("posStandY", pos.getY()+1);
        template.add("posZ", pos.getZ());

        output = template.render();
    }

    @Override
    public String getOutput() {
        return this.output;
    }
}
