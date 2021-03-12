import static org.junit.jupiter.api.Assertions.*;

import exceptions.SyntaxErrorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;

public class ParseFuncTests {
    private TestHelper helper = new TestHelper();

    @ParameterizedTest
    @ValueSource(strings = {"func_wrong_params1.ms", "func_wrong_params2.ms"})
    void WrongParams(String file) throws IOException {
        helper.setupFromFile("/parser/test/lexer/func/" + file);
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }


    /* NoneFiles */
    @Test
    void NoParam() {
        helper.setupFromString("func ID() do \n endfunc \n");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }

    @Test
    void FunctionKeywordNotFunc() {
        helper.setupFromString("function ID() do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void FuncNoNewlineAfterDo() {
        helper.setupFromString("func ID() do endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void FuncNoNewlineAfterEndfunc() {
        helper.setupFromString("func ID() do \n endfunc");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void NoID() {
        helper.setupFromString("func () do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void OneParam() {
        helper.setupFromString("func ID(param1: num) do \n endfunc \n");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }

    @Test
    void MoreParams() {
        helper.setupFromString("func ID(param1: num, param2: num) do \n endfunc \n");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }

    @Test
    void NoTypeForParam() {
        helper.setupFromString("func ID(param1: ) do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void NoIDForParam() {
        helper.setupFromString("func ID(: num) do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void NoCommaBetweenParams() {
        helper.setupFromString("func ID(param1: num param2: num ) do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void NoDo() {
        helper.setupFromString("func ID() \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void NoEndfunc() {
        helper.setupFromString("func ID() do \n \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void NoParamsAndNoParentheses() {
        helper.setupFromString("func ID do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void ReturnType() {
        helper.setupFromString("func ID() -> num do \n endfunc \n");
        assertDoesNotThrow(() -> helper.minespeakParser.func());
    }

    @Test
    void ReturnNoType() {
        helper.setupFromString("func ID() -> do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }

    @Test
    void ReturnTypeNoArrow() {
        helper.setupFromString("func ID() num do \n endfunc \n");
        assertThrows(SyntaxErrorException.class, () -> helper.minespeakParser.func());
    }
}
