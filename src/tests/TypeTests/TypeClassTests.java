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
    @RepeatedTest(5)
    void CorrectAddExpressions(RepetitionInfo rI) throws IOException {
        for (i = 0; i < primitiveTypesArray().size(); i++) {
            Type.resultTypes.
        }
    }
    */

    @ParameterizedTest(name = "{index} => using type {0} with + operator")
    @MethodSource("validAddExpressions")
    void opKeyValidAddExpressions(int type)  {
        long encodedExpression = Type.opKey(type, MinespeakParser.ADD, type);

        assertTrue(Type.resultTypes.containsKey(encodedExpression));
    }

    @ParameterizedTest(name = "{index} => using type {0} with + operator")
    @MethodSource("invalidAddExpressions")
    void opKeyInvalidAddExpressions(int type) {
        long encodedExpression = Type.opKey(type, MinespeakParser.ADD, type);

        assertFalse(Type.resultTypes.containsKey(encodedExpression));
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

}
