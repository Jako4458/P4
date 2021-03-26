
public class ScopeFactory {
    public Scope createFuncScope(Scope parent) {
        return new Scope(parent, true);
    }

    public Scope createScope(Scope parent) {
        return new Scope(parent, false);
    }
}
