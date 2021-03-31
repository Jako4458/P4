import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionSignatureWalkerTests {
    private TestHelper helper = new TestHelper();

    @Test
    void FuncReturnVoid(){
        helper.setupFromString("func test() do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        String actualName = walker.functionSignatures.get("test").getName();
        int actualSize = walker.functionSignatures.size();
        Type actualType = walker.functionSignatures.get("test").getType();
        int actualParamsSize = walker.functionSignatures.get("test").getParams().size();

        String expectedName = "test";
        int expectedSize = 1;
        Type expectedType = Type._void;
        int expectedParamsSize = 0;

        assertEquals(expectedName, actualName);
        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType, actualType);
        assertEquals(expectedParamsSize, actualParamsSize);
    }

    @Test
    void FuncSignaturesAddedToFunctionSignaturesCorrectly(){
        helper.setupFromString("func test() do \n endfunc \n func testtest(i : num) -> num do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);
        int actualSize1 = walker.functionSignatures.size();
        int expectedSize1 = 2;

        String actualName1 = walker.functionSignatures.get("test").getName();
        Type actualType1 = walker.functionSignatures.get("test").getType();
        int actualParamsSize1 = walker.functionSignatures.get("test").getParams().size();

        String expectedName1 = "test";
        Type expectedType1 = Type._void;
        int expectedParamsSize1 = 0;

        String actualName2 = walker.functionSignatures.get("testtest").getName();
        Type actualType2 = walker.functionSignatures.get("testtest").getType();
        int actualParamsSize2 = walker.functionSignatures.get("testtest").getParams().size();

        String expectedName2 = "testtest";
        Type expectedType2 = Type._num;
        int expectedParamsSize2 = 1;

        assertEquals(expectedSize1, actualSize1);

        //test
        assertEquals(expectedName1, actualName1);
        assertEquals(expectedType1, actualType1);
        assertEquals(expectedParamsSize1, actualParamsSize1);

        //testtest
        assertEquals(expectedName2, actualName2);
        assertEquals(expectedType2, actualType2);
        assertEquals(expectedParamsSize2, actualParamsSize2);
    }

    @Test
    void FuncReturnNum(){
        helper.setupFromString("func test() -> num do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        String actualName = walker.functionSignatures.get("test").getName();
        int actualSize = walker.functionSignatures.size();
        Type actualType = walker.functionSignatures.get("test").getType();
        int actualParamsSize = walker.functionSignatures.get("test").getParams().size();

        String expectedName = "test";
        int expectedSize = 1;
        Type expectedType = Type._num;
        int expectedParamsSize = 0;

        assertEquals(expectedName, actualName);
        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType, actualType);
        assertEquals(expectedParamsSize, actualParamsSize);
    }

    @Test
    void FuncReturnBool(){
        helper.setupFromString("func test() -> bool do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        String actualName = walker.functionSignatures.get("test").getName();
        int actualSize = walker.functionSignatures.size();
        Type actualType = walker.functionSignatures.get("test").getType();
        int actualParamsSize = walker.functionSignatures.get("test").getParams().size();

        String expectedName = "test";
        int expectedSize = 1;
        Type expectedType = Type._bool;
        int expectedParamsSize = 0;

        assertEquals(expectedName, actualName);
        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType, actualType);
        assertEquals(expectedParamsSize, actualParamsSize);
    }

    @Test
    void FuncReturnString(){
        helper.setupFromString("func test() -> string do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        String actualName = walker.functionSignatures.get("test").getName();
        int actualSize = walker.functionSignatures.size();
        Type actualType = walker.functionSignatures.get("test").getType();
        int actualParamsSize = walker.functionSignatures.get("test").getParams().size();

        String expectedName = "test";
        int expectedSize = 1;
        Type expectedType = Type._string;
        int expectedParamsSize = 0;

        assertEquals(expectedName, actualName);
        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType, actualType);
        assertEquals(expectedParamsSize, actualParamsSize);
    }

    @Test
    void FuncReturnVector2(){
        helper.setupFromString("func test() -> vector2 do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        String actualName = walker.functionSignatures.get("test").getName();
        int actualSize = walker.functionSignatures.size();
        Type actualType = walker.functionSignatures.get("test").getType();
        int actualParamsSize = walker.functionSignatures.get("test").getParams().size();

        String expectedName = "test";
        int expectedSize = 1;
        Type expectedType = Type._vector2;
        int expectedParamsSize = 0;

        assertEquals(expectedName, actualName);
        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType, actualType);
        assertEquals(expectedParamsSize, actualParamsSize);
    }

    @Test
    void FuncReturnVector3(){
        helper.setupFromString("func test() -> vector3 do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        String actualName = walker.functionSignatures.get("test").getName();
        int actualSize = walker.functionSignatures.size();
        Type actualType = walker.functionSignatures.get("test").getType();
        int actualParamsSize = walker.functionSignatures.get("test").getParams().size();

        String expectedName = "test";
        int expectedSize = 1;
        Type expectedType = Type._vector3;
        int expectedParamsSize = 0;

        assertEquals(expectedName, actualName);
        assertEquals(expectedSize, actualSize);
        assertEquals(expectedType, actualType);
        assertEquals(expectedParamsSize, actualParamsSize);
    }

    @Test
    void FuncOneParam(){
        helper.setupFromString("func test(et_tal : num) -> vector3 do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        Type actualParamOneType = walker.functionSignatures.get("test").getParams().get(0).getType();
        int actualParamsSize = walker.functionSignatures.get("test").getParams().size();

        Type expectedParamOneType = Type._num;
        int expectedParamsSize = 1;

        assertEquals(expectedParamsSize, actualParamsSize);
        assertEquals(expectedParamOneType, actualParamOneType);
    }

    @Test
    void FuncTwoParam(){
        helper.setupFromString("func test(et_tal : num, en_bool : bool) -> vector3 do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        Type actualParamOneType = walker.functionSignatures.get("test").getParams().get(0).getType();
        Type expectedParamOneType = Type._num;

        Type actualParamTwoType = walker.functionSignatures.get("test").getParams().get(1).getType();
        Type expectedParamTwoType = Type._bool;


        int actualParamsSize = walker.functionSignatures.get("test").getParams().size();
        int expectedParamsSize = 2;

        assertEquals(expectedParamsSize, actualParamsSize);
        assertEquals(expectedParamOneType, actualParamOneType);
        assertEquals(expectedParamTwoType, actualParamTwoType);
    }

    @Test
    void AlreadyDeclaredFuncIDDoesNotGetAdded(){
        helper.setupFromString("func test() -> vector3 do \n endfunc \n func test() -> vector3 do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        int actualSize = walker.functionSignatures.size();
        int expectedSize = 1;

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void MCFuncIsAddedToFuncSignatures(){
        helper.setupFromString("@mc\nfunc test() do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        boolean actualIsMCFunction = walker.functionSignatures.get("test").isMCFunction();
        boolean expectedIsMCFunction = true;

        int actualSize = walker.functionSignatures.size();
        int expectedSize = 1;

        assertEquals(expectedSize, actualSize);
        assertEquals(expectedIsMCFunction, actualIsMCFunction);
    }

    @Test
    void NonVoidMCFuncNotAdded(){
        helper.setupFromString("@mc\nfunc test() -> num do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        int actualSize = walker.functionSignatures.size();
        int expectedSize = 0;

        assertEquals(expectedSize, actualSize);
    }

    @Test
    void FuncSignReturnNumArray() {
        helper.setupFromString("func test() -> num[] do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        Type actualArrayType = walker.functionSignatures.get("test").getType();
        Type expectedArrayType = Type._num;

        int actualSize = walker.functionSignatures.size();
        int expectedSize = 1;

        assertEquals(expectedSize, actualSize);
        assertTrue(actualArrayType instanceof ArrayType);
        assertEquals(expectedArrayType, ((ArrayType) actualArrayType).type);
    }

    @Test
    void FuncSignReturnBoolArray(){
        helper.setupFromString("func test() -> bool[] do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        Type actualArrayType = walker.functionSignatures.get("test").getType();
        Type expectedArrayType = Type._bool;

        int actualSize = walker.functionSignatures.size();
        int expectedSize = 1;

        assertEquals(expectedSize, actualSize);
        assertTrue(actualArrayType instanceof ArrayType);
        assertEquals(expectedArrayType, ((ArrayType) actualArrayType).type);
    }

    @Test
    void FuncSignReturnBlockArray(){
        helper.setupFromString("func test() -> block[] do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        Type actualArrayType = walker.functionSignatures.get("test").getType();
        Type expectedArrayType = Type._block;

        int actualSize = walker.functionSignatures.size();
        int expectedSize = 1;

        assertEquals(expectedSize, actualSize);
        assertTrue(actualArrayType instanceof ArrayType);
        assertEquals(expectedArrayType, ((ArrayType) actualArrayType).type);
    }

    @Test
    void FuncSignReturnStringArray(){
        helper.setupFromString("func test() -> string[] do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        Type actualArrayType = walker.functionSignatures.get("test").getType();
        Type expectedArrayType = Type._string;

        int actualSize = walker.functionSignatures.size();
        int expectedSize = 1;

        assertEquals(expectedSize, actualSize);
        assertTrue(actualArrayType instanceof ArrayType);
        assertEquals(expectedArrayType, ((ArrayType) actualArrayType).type);
    }

    @Test
    void FuncSignReturnVector2Array(){
        helper.setupFromString("func test() -> vector2[] do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        Type actualArrayType = walker.functionSignatures.get("test").getType();
        Type expectedArrayType = Type._vector2;

        int actualSize = walker.functionSignatures.size();
        int expectedSize = 1;

        assertEquals(expectedSize, actualSize);
        assertTrue(actualArrayType instanceof ArrayType);
        assertEquals(expectedArrayType, ((ArrayType) actualArrayType).type);
    }

    @Test
    void FuncSignReturnVector3Array(){
        helper.setupFromString("func test() -> vector3[] do \n endfunc \n");
        MinespeakParser.BlocksContext tree = helper.minespeakParser.blocks();
        SignatureWalker walker = new SignatureWalker();
        helper.walkTree(tree, walker);

        Type actualArrayType = walker.functionSignatures.get("test").getType();
        Type expectedArrayType = Type._vector3;

        int actualSize = walker.functionSignatures.size();
        int expectedSize = 1;

        assertEquals(expectedSize, actualSize);
        assertTrue(actualArrayType instanceof ArrayType);
        assertEquals(expectedArrayType, ((ArrayType) actualArrayType).type);
    }
}
