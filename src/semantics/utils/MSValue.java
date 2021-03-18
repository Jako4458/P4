package utils;

import exceptions.InvalidReturnTypeException;

public class MSValue implements Comparable<MSValue> {
    public static final MSValue ERROR = new MSValue("error", "error");
    public static final MSValue BOOLEAN = new MSValue(true);
    public static final MSValue NUMBER = new MSValue(1);
    public static final MSValue BLOCK = new MSValue(Block.DIRT);
    public static final MSValue STRING = new MSValue("");
    public static final MSValue VOID = new MSValue(null);

    private Object value;
    private String type = "";

    public MSValue(Object v) {
        value = v;
    }
    MSValue(Object v, String type) {
        value = v;
        this.type = type;
    }


    public Object getValue() {
        return this.value;
    }

    public MSValue generateDefaultBooleanMSValue() {
        return new MSValue(true);
    }



    public static MSValue generateValueFromType(String typeName) throws InvalidReturnTypeException {
        switch (typeName) {
            case "num": return MSValue.NUMBER;
            case "block": return MSValue.BLOCK;
            case "string": return MSValue.STRING;
            case "bool": return MSValue.BOOLEAN;
            default: return MSValue.ERROR;
        }
    }

    public int getValueAsInt(){
        return Integer.parseInt(this.value.toString());
    }



    @Override
    public int compareTo(MSValue o) {
        return 0;
    }
}
