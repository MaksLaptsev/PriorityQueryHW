package Queue;

public interface CustomPriorityQuery<T> {
    boolean add(T t);
    T poll();
    T peek();
    int size();
    boolean isEmpty();
}
