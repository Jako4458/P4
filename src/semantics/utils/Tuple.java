public class Tuple<T, G> {

    private T element1;
    private G element2;


    public Tuple(T element1, G element2){
        this.element1 = element1;
        this.element2 = element2;
    }

    public T getElement1() {
        return element1;
    }

    public G getElement2() {
        return element2;
    }
}
