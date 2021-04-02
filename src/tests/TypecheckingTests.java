import static org.junit.jupiter.api.Assertions.*;

import Logging.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class TypecheckingTests {
    private TestHelper helper = new TestHelper();

    @BeforeEach
    public void reset() {
        Logger.shared.clear();
    }

    /* To-do
    assignments (compound), instantiations, func calls,
    array access, dowhile, while, for, foreach, if,
    basic operations

     */


    //region Test of types for function return values
    @Test
    public void FunctionReturnTypeAsDeclared() {
        helper.setupFromString("func Test() -> num do \n return 1 \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertTrue(Logger.shared.getLogs().isEmpty());
        assertEquals(Type._num, helper.getListener().getFunctions().get("Test").getType());
    }

    @Test
    public void FunctionReturnTypeAsDeclaredArray() {
        helper.setupFromString("func Test() -> num[] do \n var n: num[]\n return n \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertTrue(Logger.shared.getLogs().isEmpty());
        assertEquals(Type._num, ((ArrayType)helper.getListener().getFunctions().get("Test").getType()).type);
    }

    // This is non-exhaustive
    @Test
    public void FunctionReturnTypeNotAsDeclaredArray() {
        helper.setupFromString("func Test() -> num[] do \n var n: num\n return n \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertFalse(Logger.shared.getLogs().isEmpty());
        assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError);
    }

    @Test
    public void FunctionReturnTypeNotAsDeclaredVoid() {
        helper.setupFromString("func Test() do \n var n: num\n return n \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertFalse(Logger.shared.getLogs().isEmpty());
        assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError);
    }

    @Test
    public void FunctionReturnTypeNotAsDeclaredNoReturn() {
        helper.setupFromString("func Test() -> num do \n \n endfunc\n");
        MinespeakParser.FuncContext tree = helper.minespeakParser.func();
        helper.walkTree(tree);

        assertFalse(Logger.shared.getLogs().isEmpty());
        assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError);
    }
    //endregion

    //region Test of types for compound assignments (Integration tests)
    @ParameterizedTest(name = "{index} => {0} with num for non-num types")
    @MethodSource("validCompoundAssignments")
    public void CompoundAssignmentWithNumTypeLeftSide(String opName) {
        for (int i = 0; i < primitiveTypesArray().size(); i++) {
            if (primitiveTypesArray().get(i) != Type.NUM) {
                helper.setupFromString(String.format("var n: num\n n %s %s\n", opName,
                        TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
                MinespeakParser.BodyContext tree = helper.minespeakParser.body();
                helper.walkTree(tree);

                assertFalse(Logger.shared.getLogs().isEmpty());
                assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError
                        || Logger.shared.getLogs().get(0) instanceof InvalidOperatorError);
                this.reset();
            } else {
                helper.setupFromString(String.format("var n: num\n n %s %s\n", opName,
                        TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
                MinespeakParser.BodyContext tree = helper.minespeakParser.body();
                helper.walkTree(tree);

                assertTrue(Logger.shared.getLogs().isEmpty());
            }
        }
    }

    @ParameterizedTest(name = "{index} => {0} with bool for non-array types")
    @MethodSource("validCompoundAssignments")
    public void CompoundAssignmentWithBoolTypeLeftSide(String opName) {
        for (int i = 0; i < primitiveTypesArray().size(); i++) {
            helper.setupFromString(String.format("var n: bool\n n %s %s\n", opName,
                    TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
            MinespeakParser.BodyContext tree = helper.minespeakParser.body();
            helper.walkTree(tree);

            assertFalse(Logger.shared.getLogs().isEmpty());
            assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError
                    || Logger.shared.getLogs().get(0) instanceof InvalidOperatorError);
            this.reset();
        }
    }

    @ParameterizedTest(name = "{index} => {0} with block for non-array types")
    @MethodSource("validCompoundAssignments")
    public void CompoundAssignmentWithBlockTypeLeftSide(String opName) {
        for (int i = 0; i < primitiveTypesArray().size(); i++) {
            helper.setupFromString(String.format("var n: block\n n %s %s\n", opName,
                    TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
            MinespeakParser.BodyContext tree = helper.minespeakParser.body();
            helper.walkTree(tree);

            assertFalse(Logger.shared.getLogs().isEmpty());
            assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError
                    || Logger.shared.getLogs().get(0) instanceof InvalidOperatorError);
            this.reset();
        }
    }

    @ParameterizedTest(name = "{index} => {0} with vector2 for non-array types")
    @MethodSource("validCompoundAssignments")
    public void CompoundAssignmentWithVector2TypeLeftSide(String opName) {
        for (int i = 0; i < primitiveTypesArray().size(); i++) {
            helper.setupFromString(String.format("var n: vector2\n n %s %s\n", opName,
                    TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
            MinespeakParser.BodyContext tree = helper.minespeakParser.body();
            helper.walkTree(tree);

            assertFalse(Logger.shared.getLogs().isEmpty());
            assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError
                    || Logger.shared.getLogs().get(0) instanceof InvalidOperatorError);
            this.reset();
        }
    }

    @ParameterizedTest(name = "{index} => {0} with vector3 for non-array types")
    @MethodSource("validCompoundAssignments")
    public void CompoundAssignmentWithVector3TypeLeftSide(String opName) {
        for (int i = 0; i < primitiveTypesArray().size(); i++) {
            helper.setupFromString(String.format("var n: vector3\n n %s %s\n", opName,
                    TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
            MinespeakParser.BodyContext tree = helper.minespeakParser.body();
            helper.walkTree(tree);

            assertFalse(Logger.shared.getLogs().isEmpty());
            assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError
                    || Logger.shared.getLogs().get(0) instanceof InvalidOperatorError);
            this.reset();
        }
    }

    @ParameterizedTest(name = "{index} => {0} with string for non-array types")
    @MethodSource("validCompoundAssignments")
    public void CompoundAssignmentWithStringTypeLeftSide(String opName) {
        for (int i = 0; i < primitiveTypesArray().size(); i++) {
            if (!opName.equals(TestHelper.getSymbolFromInt(MinespeakParser.ADDASSIGN)) || primitiveTypesArray().get(i) != Type.STRING) {
                helper.setupFromString(String.format("var n: string\n n %s %s\n", opName,
                        TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
                MinespeakParser.BodyContext tree = helper.minespeakParser.body();
                helper.walkTree(tree);

                assertFalse(Logger.shared.getLogs().isEmpty());
                assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError
                        || Logger.shared.getLogs().get(0) instanceof InvalidOperatorError);
                this.reset();
            } else {
                helper.setupFromString(String.format("var n: string\n n %s %s\n", opName,
                        TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
                MinespeakParser.BodyContext tree = helper.minespeakParser.body();
                helper.walkTree(tree);

                assertTrue(Logger.shared.getLogs().isEmpty());
            }
        }
    }
    //endregion

    //region Test of types for non-compound assignments (Integration tests)
    @ParameterizedTest(name = "{index} => {0} assignment with non-array types")
    @MethodSource("primitiveTypes")
    public void NonCompoundAssignment(String typeName) {
        for (int i = 0; i < primitiveTypesArray().size(); i++) {
            if (!TestHelper.getSymbolFromInt(primitiveTypesArray().get(i)).equals(typeName)) {
                helper.setupFromString(String.format("var n: %s\n n = %s\n", typeName,
                        TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
                MinespeakParser.BodyContext tree = helper.minespeakParser.body();
                helper.walkTree(tree);

                assertFalse(Logger.shared.getLogs().isEmpty());
                assertTrue(Logger.shared.getLogs().get(0) instanceof TypeError
                        || Logger.shared.getLogs().get(0) instanceof InvalidOperatorError);
                this.reset();
            } else {
                helper.setupFromString(String.format("var n: %s\n n = %s\n", typeName,
                        TestHelper.getRepresentativeSymbol(primitiveTypesArray().get(i))));
                MinespeakParser.BodyContext tree = helper.minespeakParser.body();
                helper.walkTree(tree);

                assertTrue(Logger.shared.getLogs().isEmpty());
            }
        }
    }
    //endregion

    //region Test helper functions
    private static ArrayList<Integer> primitiveTypesArray() {
        return new ArrayList<>() {{
                add(Type.NUM); add(Type.BOOL); add(Type.BLOCK);
                add(Type.STRING); add(Type.VECTOR2); add(Type.VECTOR3);
            }};
    }

    private static Stream<Arguments> validNumBinaryOperators() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.ADD, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.ADD)),
                Arguments.arguments(MinespeakParser.SUB, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.SUB)),
                Arguments.arguments(MinespeakParser.POW, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.POW)),
                Arguments.arguments(MinespeakParser.TIMES, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.TIMES)),
                Arguments.arguments(MinespeakParser.DIV, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.DIV)),
                Arguments.arguments(MinespeakParser.MOD, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.MOD)),
                Arguments.arguments(MinespeakParser.LESSER, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.LESSER)),
                Arguments.arguments(MinespeakParser.GREATER, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.GREATER)),
                Arguments.arguments(MinespeakParser.LESSEQ, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.LESSEQ)),
                Arguments.arguments(MinespeakParser.GREATEQ, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.GREATEQ)),
                Arguments.arguments(MinespeakParser.EQUAL, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.EQUAL)),
                Arguments.arguments(MinespeakParser.NOTEQUAL, MinespeakParser.VOCABULARY.getLiteralName(MinespeakParser.NOTEQUAL))
        );
    }

    private static Stream<Arguments> validCompoundAssignments() {
        return Stream.of(
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.ADDASSIGN)),
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.SUBASSIGN)),
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.MODASSIGN)),
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.MULTASSIGN)),
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.DIVASSIGN))
        );
    }

    private static Stream<Arguments> primitiveTypes() {
        return Stream.of(
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.NUM)),
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.BOOL)),
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.BLOCK)),
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.STRING)),
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.VECTOR2)),
                Arguments.arguments(TestHelper.getSymbolFromInt(MinespeakParser.VECTOR3))
        );
    }

    //endregion
}
