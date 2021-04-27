import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Stream;

public class TypeClassTests {

    private static ArrayList<Integer> primitiveTypesArray() {
        return new ArrayList<>() {{
            add(Type.NUM); add(Type.BOOL); add(Type.BLOCK);
            add(Type.STRING); add(Type.VECTOR2); add(Type.VECTOR3);
        }};
    }

    /*
        Tests for determining if opKey can correctly be used to generate keys for the resultTypes hash map
    */
    @ParameterizedTest(name = "{index} => using type {0} with + operator")
    @MethodSource("validAddExpressions")
    void opKeyValidAddExpressions(int type)  {
        long encodedExpression = Type.opKey(type, MinespeakParser.ADD, type);

        assertTrue(Type.resultTypes.containsKey(encodedExpression));
    }

    @Test
    void opKeyValidBlockExpressionEqual()  {
        long encodedExpression = Type.opKey(Type.BLOCK, MinespeakParser.EQUAL, Type.BLOCK);

        assertTrue(Type.resultTypes.containsKey(encodedExpression));
    }

    @Test
    void opKeyValidBlockExpressionNotEqual()  {
        long encodedExpression = Type.opKey(Type.BLOCK, MinespeakParser.NOTEQUAL, Type.BLOCK);

        assertTrue(Type.resultTypes.containsKey(encodedExpression));
    }

    @ParameterizedTest(name = "{index} => using type {0} with + operator")
    @MethodSource("invalidAddExpressions")
    void opKeyInvalidAddExpressions(int type) {
        long encodedExpression = Type.opKey(type, MinespeakParser.ADD, type);

        assertFalse(Type.resultTypes.containsKey(encodedExpression));
    }

    //<editor-fold desc="inferType tests">
    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("correctArithmetic")
    void inferTypeCorrectArithmetic(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        assertSame(testType, Type._num);
    }
    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("incorrectArithmetic")
    void inferTypeIncorrectArithmetic(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        assertSame(testType, Type._error);
    }

    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("correctRelational")
    void inferTypeCorrectRelational(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        assertSame(testType, Type._bool);
    }
    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("incorrectRelational")
    void inferTypeIncorrectRelational(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        assertSame(testType, Type._error);
    }

    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("correctLogical")
    void inferTypeCorrectLogical(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        assertSame(testType, Type._bool);
    }
    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("incorrectLogical")
    void inferTypeIncorrectLogical(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        assertSame(testType, Type._error);
    }

    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("correctUniversal")
    void inferTypeCorrectUniversal(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        assertSame(testType, Type._bool);
    }
    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("incorrectUniversal")
    void inferTypeIncorrectUniversal(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        assertSame(testType, Type._error);
    }

    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("correctVectorOps")
    void inferTypeCorrectVectorOps(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        if((type1 == MinespeakParser.VECTOR2) || (type2 == MinespeakParser.VECTOR2)) {
            assertSame(testType, Type._vector2);
        }
        else {
            assertSame(testType, Type._vector3);
        }
    }
    @ParameterizedTest(name = "{index} => using type {0} and {2} with operator {1}")
    @MethodSource("incorrectVectorOps")
    void inferTypeIncorrectVectorOps(int type1, int op, int type2) {

        Type testType = Type.inferType(type1, op, type2);

        assertSame(testType, Type._error);
    }

    @Test
    void inferTypeCorrectStringAdd(){
        Type testType = Type.inferType(MinespeakParser.STRING, MinespeakParser.ADD, MinespeakParser.STRING);

        assertSame(testType, Type._string);
    }
    @Test
    void inferTypeIncorrectStringAdd(){
        Type testType = Type.inferType(MinespeakParser.STRING, MinespeakParser.ADD, MinespeakParser.NUM);

        assertSame(testType, Type._error);
    }
    //</editor-fold>

    //<editor-fold desc="Test streams">
    private static Stream<Arguments> correctArithmetic() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.ADD, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.SUB, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.TIMES, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.DIV, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.MOD, MinespeakParser.NUM)
        );
    }
    private static Stream<Arguments> incorrectArithmetic() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.ADD, MinespeakParser.STRING),
                Arguments.arguments(MinespeakParser.BLOCK, MinespeakParser.SUB, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.BLOCK, MinespeakParser.TIMES, MinespeakParser.BLOCK),
                Arguments.arguments(MinespeakParser.STRING, MinespeakParser.DIV, MinespeakParser.STRING),
                Arguments.arguments(MinespeakParser.VECTOR2, MinespeakParser.MOD, MinespeakParser.NUM)
        );
    }

    private static Stream<Arguments> correctRelational() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.LESSER, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.GREATER, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.LESSEQ, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.GREATEQ, MinespeakParser.NUM)
        );
    }
    private static Stream<Arguments> incorrectRelational() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.BOOL, MinespeakParser.LESSER, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.GREATER, MinespeakParser.STRING),
                Arguments.arguments(MinespeakParser.STRING, MinespeakParser.LESSEQ, MinespeakParser.STRING),
                Arguments.arguments(MinespeakParser.BLOCK, MinespeakParser.GREATEQ, MinespeakParser.BLOCK)
        );
    }

    private static Stream<Arguments> correctLogical() {
        return Stream.of(
                //Arguments.arguments(MinespeakParser.BOOL, MinespeakParser.NOT, MinespeakParser.BOOL),
                Arguments.arguments(MinespeakParser.BOOL, MinespeakParser.AND, MinespeakParser.BOOL),
                Arguments.arguments(MinespeakParser.BOOL, MinespeakParser.OR, MinespeakParser.BOOL)
        );
    }
    private static Stream<Arguments> incorrectLogical() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.NOT, MinespeakParser.BOOL),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.AND, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.STRING, MinespeakParser.OR, MinespeakParser.STRING)
        );
    }

    private static Stream<Arguments> correctUniversal() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.EQUAL, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.NOTEQUAL, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.BLOCK, MinespeakParser.EQUAL, MinespeakParser.BLOCK),
                Arguments.arguments(MinespeakParser.BLOCK, MinespeakParser.NOTEQUAL, MinespeakParser.BLOCK),
                Arguments.arguments(MinespeakParser.BOOL, MinespeakParser.EQUAL, MinespeakParser.BOOL),
                Arguments.arguments(MinespeakParser.BOOL, MinespeakParser.NOTEQUAL, MinespeakParser.BOOL),
                Arguments.arguments(MinespeakParser.STRING, MinespeakParser.EQUAL, MinespeakParser.STRING),
                Arguments.arguments(MinespeakParser.STRING, MinespeakParser.NOTEQUAL, MinespeakParser.STRING),
                Arguments.arguments(MinespeakParser.VECTOR2, MinespeakParser.EQUAL, MinespeakParser.VECTOR2),
                Arguments.arguments(MinespeakParser.VECTOR2, MinespeakParser.NOTEQUAL, MinespeakParser.VECTOR2),
                Arguments.arguments(MinespeakParser.VECTOR3, MinespeakParser.EQUAL, MinespeakParser.VECTOR3),
                Arguments.arguments(MinespeakParser.VECTOR3, MinespeakParser.NOTEQUAL, MinespeakParser.VECTOR3)
        );
    }
    private static Stream<Arguments> incorrectUniversal() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.EQUAL, MinespeakParser.STRING),
                Arguments.arguments(MinespeakParser.BOOL, MinespeakParser.NOTEQUAL, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.BLOCK, MinespeakParser.EQUAL, MinespeakParser.BOOL),
                Arguments.arguments(MinespeakParser.VECTOR3, MinespeakParser.NOTEQUAL, MinespeakParser.VECTOR2)
        );
    }

    private static Stream<Arguments> correctVectorOps() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.VECTOR2, MinespeakParser.ADD, MinespeakParser.VECTOR2),
                Arguments.arguments(MinespeakParser.VECTOR3, MinespeakParser.ADD, MinespeakParser.VECTOR3),
                Arguments.arguments(MinespeakParser.VECTOR2, MinespeakParser.SUB, MinespeakParser.VECTOR2),
                Arguments.arguments(MinespeakParser.VECTOR3, MinespeakParser.SUB, MinespeakParser.VECTOR3),
                Arguments.arguments(MinespeakParser.VECTOR2, MinespeakParser.TIMES, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.TIMES, MinespeakParser.VECTOR2),
                Arguments.arguments(MinespeakParser.VECTOR3, MinespeakParser.TIMES, MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.NUM, MinespeakParser.TIMES, MinespeakParser.VECTOR3)
        );
    }
    private static Stream<Arguments> incorrectVectorOps() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.VECTOR2, MinespeakParser.ADD, MinespeakParser.VECTOR3),
                Arguments.arguments(MinespeakParser.VECTOR3, MinespeakParser.SUB, MinespeakParser.VECTOR2),
                Arguments.arguments(MinespeakParser.VECTOR2, MinespeakParser.TIMES, MinespeakParser.VECTOR2),
                Arguments.arguments(MinespeakParser.VECTOR3, MinespeakParser.TIMES, MinespeakParser.VECTOR3)
        );
    }

    private static Stream<Arguments> validAddExpressions() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.STRING),
                Arguments.arguments(MinespeakParser.VECTOR2),
                Arguments.arguments(MinespeakParser.VECTOR3)
        );
    }

    private static Stream<Arguments> invalidAddExpressions() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.BOOL),
                Arguments.arguments(MinespeakParser.BLOCK)
        );
    }

    private static Stream<Arguments> primitiveTypes() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.NUM),
                Arguments.arguments(MinespeakParser.BOOL),
                Arguments.arguments(MinespeakParser.BLOCK),
                Arguments.arguments(MinespeakParser.STRING),
                Arguments.arguments(MinespeakParser.VECTOR2),
                Arguments.arguments(MinespeakParser.VECTOR3)
        );
    }
    //</editor-fold>

}
