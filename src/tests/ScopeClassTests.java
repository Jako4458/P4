import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ScopeClassTests {

    @Test
    public void ScopeGetParentNull(){
        Scope scope = new Scope();

        Scope actual = scope.getParent();

        assertNull(actual);
    }

    @Test
    public void ScopeGetParent2(){
        Scope scope = new Scope();
        Scope scope2 = new Scope(scope);

        Scope actual = scope2.getParent();

        assertEquals(scope, actual);
    }


    @Test
    public void AddVariableToSymTableReturnsTrue(){
        Scope scope = new Scope();
        SymEntry entryToAdd = new SimpleEntry("a", Type._num, null, 46);

        boolean actual = scope.addVariable("a", entryToAdd);

        assertTrue(actual);
    }

    @Test
    public void AddVariableWithSameNameReturnsFalse(){
        Scope scope = new Scope();
        SymEntry oldEntry = new SimpleEntry("a", Type._num, null, 46);
        scope.addVariable("a", oldEntry);

        SymEntry entryToAdd = new SimpleEntry("a", Type._num, null, 46);
        boolean actual = scope.addVariable("a", entryToAdd);

        assertFalse(actual);
    }

    @Test
    public void AddMultipleVariablesToSymbolTable(){
        Scope scope = new Scope();
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("b", Type._num, null, 46);
        boolean actual1 = scope.addVariable("a", entry1);
        boolean actual2 = scope.addVariable("b", entry2);

        assertTrue(actual1);
        assertTrue(actual2);
    }

    @Test
    public void ScopeLookupSingleScope(){
        HashMap<String, SymEntry> symbolTable = new HashMap<>();
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        symbolTable.put("a", entry1);
        Scope scope = new Scope(null, symbolTable);

        SymEntry result = scope.lookup("a");

        assertEquals(entry1, result);
    }

    @Test
    public void ScopeLookupMultipleLookup(){
        HashMap<String, SymEntry> symbolTable = new HashMap<>();
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("b", Type._num, null, 46);
        symbolTable.put("a", entry1);
        symbolTable.put("b", entry2);
        Scope scope = new Scope(null, symbolTable);

        SymEntry result1 = scope.lookup("a");
        SymEntry result2 = scope.lookup("b");

        assertEquals(entry1, result1);
        assertEquals(entry2, result2);
    }

    @Test
    public void ScopeLookupNull(){
        Scope scope = new Scope();

        SymEntry actual = scope.lookup("a");

        assertNull(actual);
    }

    @Test
    public void ScopeLookupOuterScope(){
        HashMap<String, SymEntry> symbolTable = new HashMap<>();
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        symbolTable.put("a", entry1);
        Scope scope = new Scope(null, symbolTable);
        Scope innerScope = new Scope(scope);

        SymEntry result1 = innerScope.lookup("a");

        assertEquals(entry1, result1);
    }

    @Test
    public void ScopeMultipleLookupOuterScope(){
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("b", Type._num, null, 46);
        HashMap<String, SymEntry> symbolTable = new HashMap<>();
        symbolTable.put("a", entry1);
        symbolTable.put("b", entry2);
        Scope scope = new Scope(null, symbolTable);
        Scope innerScope = new Scope(scope);

        SymEntry result1 = innerScope.lookup("a");
        SymEntry result2 = innerScope.lookup("b");

        assertEquals(entry1, result1);
        assertEquals(entry2, result2);
    }

    @Test
    public void ScopeLookupCannotLookDownScopeTree(){
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        HashMap<String, SymEntry> symbolTable = new HashMap<>();
        symbolTable.put("a", entry1);
        Scope scope = new Scope();
        Scope innerScope = new Scope(scope, symbolTable);

        SymEntry result1 = scope.lookup("a");
        SymEntry result2 = innerScope.lookup("a");

        assertNull(result1);
        assertEquals(entry1, result2);
    }

    @Test
    public void ScopeVariableShadowing(){
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("a", Type._num, null, 46);

        HashMap<String, SymEntry> symbolTable1 = new HashMap<>();
        HashMap<String, SymEntry> symbolTable2 = new HashMap<>();
        symbolTable1.put("a", entry1);
        symbolTable2.put("a", entry2);

        Scope scope = new Scope(null, symbolTable1);
        Scope innerScope = new Scope(scope, symbolTable2);

        SymEntry result = innerScope.lookup("a");

        assertNotEquals(entry1, result);
        assertEquals(entry2, result);
    }

    @Test
    public void ScopeVariableShadowing3Scopes(){
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry3 = new SimpleEntry("a", Type._num, null, 46);

        HashMap<String, SymEntry> symbolTable1 = new HashMap<>();
        HashMap<String, SymEntry> symbolTable2 = new HashMap<>();
        HashMap<String, SymEntry> symbolTable3 = new HashMap<>();
        symbolTable1.put("a", entry1);
        symbolTable2.put("a", entry2);
        symbolTable3.put("a", entry3);

        Scope scope = new Scope(null, symbolTable1);
        Scope innerScope = new Scope(scope, symbolTable2);
        Scope innerInnerScope = new Scope(innerScope, symbolTable3);

        SymEntry result = innerInnerScope.lookup("a");

        assertEquals(entry3, result);
        assertNotEquals(entry2, result);
        assertNotEquals(entry1, result);
    }

    @Test
    public void ScopeVariableReassign(){
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("a", Type._num, null, 46);
        HashMap<String, SymEntry> symbolTable = new HashMap<>();
        symbolTable.put("a", entry1);

        Scope scope = new Scope(null, symbolTable);

        scope.reAssign("a", entry2);
        SymEntry actual = symbolTable.get("a");

        assertEquals(entry2, actual);
        assertNotEquals(entry1, actual);
    }

    @Test
    public void ScopeVariableReassignUnassignedIsNull(){
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        HashMap<String, SymEntry> symbolTable = new HashMap<>();

        Scope scope = new Scope(null, symbolTable);

        scope.reAssign("a", entry1);
        SymEntry actual = symbolTable.get("a");

        assertNull(actual);
    }
}
