import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FunctionSignatureWalkerTests {
    private TestHelper helper = new TestHelper();

    @Test
    void FuncSignaturesAddedToFunctionSignaturesCorrectly1(){
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
    void FuncSignaturesAddedToFunctionSignaturesCorrectly2(){
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
}
