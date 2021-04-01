import static org.junit.jupiter.api.Assertions.*;

import Logging.LogType;
import Logging.Logger;
import Logging.VariableAlreadyDeclaredError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class TypecheckingTests {
    private TestHelper helper = new TestHelper();

    /*
    Functions, assignments (compound), instantiations, func calls,
    array access, func signatures, func retval, dowhile, while, for, foreach, if,
    basic operations

     */


    //region Test of types for function return values
    public void FunctionReturnTypeAsDeclaredPrimitives() {

    }

    public void FunctionReturnTypeAsDeclaredArrays() {

    }

    // This is non-exhaustive
    public void FunctionReturnTypeNotAsDeclared() {

    }

    //endregion

    //region Test of types for function

    //region Test helper functions
    private static Stream<Arguments> primitiveTypes() {
        return Stream.of(
                Arguments.arguments(Type.NUM, "num"),
                Arguments.arguments(Type.BOOL, "bool"),
                Arguments.arguments(Type.BLOCK, "block"),
                Arguments.arguments(Type.STRING, "string"),
                Arguments.arguments(Type.VECTOR2, "vector2"),
                Arguments.arguments(Type.VECTOR3, "vector3")
        );
    }

    private static Stream<Arguments> validNumBinaryOperators() {
        return Stream.of(
                Arguments.arguments(MinespeakParser.ADD, "+"),
                Arguments.arguments(MinespeakParser.SUB, "-"),
                Arguments.arguments(MinespeakParser.POW, "Pow"),
                Arguments.arguments(MinespeakParser.TIMES, "*"),
                Arguments.arguments(MinespeakParser.SUB, "-"),
                Arguments.arguments(MinespeakParser.SUB, "-"),
                Arguments.arguments(MinespeakParser.ADD, "+"),
                Arguments.arguments(MinespeakParser.SUB, "-"),
                Arguments.arguments(MinespeakParser.SUB, "-"),
                Arguments.arguments(MinespeakParser.ADD, "+"),
                Arguments.arguments(MinespeakParser.SUB, "-"),
                Arguments.arguments(MinespeakParser.SUB, "-"),
        );
    }

    private static Stream<Arguments> invalidNumBinaryOperators() {

    }

    private static Stream<Arguments> validNumUnaryOperators() {

    }

    private static Stream<Arguments> invalidNumUnaryOperators() {

    }

    private static Stream<Arguments> paramToList() {
        return Stream.of(
                Arguments.arguments(0, "",
                        new ArrayList<String>(),
                        new ArrayList<Integer>()
                ),
                Arguments.arguments(1, "param1: num",
                        Arrays.asList("param1"),
                        Arrays.asList(Type.NUM)
                ),
                Arguments.arguments(2, "param1: num, param2: bool",
                        Arrays.asList("param1", "param2"),
                        Arrays.asList(Type.NUM, Type.BOOL)
                )
        );
    }

    //endregion
}
