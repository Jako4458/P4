import org.junit.jupiter.api.Test;

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

        boolean actual = scope.addVariable(entryToAdd.getName(), entryToAdd);

        assertTrue(actual);
    }

    @Test
    public void AddVariableWithSameNameReturnsFalse(){
        Scope scope = new Scope();
        SymEntry oldEntry = new SimpleEntry("a", Type._num, null, 46);
        scope.addVariable(oldEntry.getName(), oldEntry);

        SymEntry entryToAdd = new SimpleEntry("a", Type._num, null, 46);
        boolean actual = scope.addVariable(entryToAdd.getName(), entryToAdd);

        assertFalse(actual);
    }

    @Test
    public void AddMultipleVariablesToSymbolTable(){
        Scope scope = new Scope();
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("b", Type._num, null, 46);
        boolean actual1 = scope.addVariable(entry1.getName(), entry1);
        boolean actual2 = scope.addVariable(entry2.getName(), entry2);

        assertTrue(actual1);
        assertTrue(actual2);
    }

    @Test
    public void ScopeLookupSingleScope(){
        Scope scope = new Scope();
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        scope.addVariable(entry1.getName(), entry1);

        SymEntry result = scope.lookup("a");

        assertEquals(entry1, result);
    }

    @Test
    public void ScopeLookupMultipleLookup(){
        Scope scope = new Scope();
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("b", Type._num, null, 46);
        scope.addVariable(entry1.getName(), entry1);
        scope.addVariable(entry2.getName(), entry2);

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
        Scope scope = new Scope();
        Scope innerScope = new Scope(scope);
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        scope.addVariable(entry1.getName(), entry1);

        SymEntry result1 = innerScope.lookup("a");

        assertEquals(entry1, result1);
    }

    @Test
    public void ScopeMultipleLookupOuterScope(){
        Scope scope = new Scope();
        Scope innerScope = new Scope(scope);
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("b", Type._num, null, 46);
        scope.addVariable(entry1.getName(), entry1);
        scope.addVariable(entry2.getName(), entry2);

        SymEntry result1 = innerScope.lookup("a");
        SymEntry result2 = innerScope.lookup("b");

        assertEquals(entry1, result1);
        assertEquals(entry2, result2);
    }

    @Test
    public void ScopeLookupCannotLookDownScopeTree(){
        Scope scope = new Scope();
        Scope innerScope = new Scope(scope);
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        innerScope.addVariable(entry1.getName(), entry1);

        SymEntry result1 = scope.lookup("a");

        assertNull(result1);
    }

    @Test
    public void ScopeVariableShadowing(){
        Scope scope = new Scope();
        Scope innerScope = new Scope(scope);
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("a", Type._num, null, 46);
        scope.addVariable(entry1.getName(), entry1);
        innerScope.addVariable(entry2.getName(), entry2);

        SymEntry result = innerScope.lookup("a");

        assertEquals(entry2, result);
        assertNotEquals(entry1, result);
    }

    @Test
    public void ScopeVariableShadowing3Scopes(){
        Scope scope = new Scope();
        Scope innerScope = new Scope(scope);
        Scope innerInnerScope = new Scope(innerScope);
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry3 = new SimpleEntry("a", Type._num, null, 46);
        scope.addVariable(entry1.getName(), entry1);
        innerScope.addVariable(entry2.getName(), entry2);
        innerInnerScope.addVariable(entry3.getName(), entry3);

        SymEntry result = innerInnerScope.lookup("a");

        assertEquals(entry3, result);
        assertNotEquals(entry2, result);
        assertNotEquals(entry1, result);
    }

    @Test
    public void ScopeVariableReassign(){
        Scope scope = new Scope();
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);
        SymEntry entry2 = new SimpleEntry("a", Type._num, null, 46);
        scope.addVariable(entry1.getName(), entry1);

        scope.reAssign("a", entry2);
        SymEntry actual = scope.lookup("a");

        assertEquals(entry2, actual);
        assertNotEquals(entry1, actual);
    }

    @Test
    public void ScopeVariableReassignUnassignedIsNull(){
        Scope scope = new Scope();
        SymEntry entry1 = new SimpleEntry("a", Type._num, null, 46);

        scope.reAssign("a", entry1);

        SymEntry actual = scope.lookup("a");
        assertNull(actual);
    }
}
