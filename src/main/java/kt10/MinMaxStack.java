package kt10;
public class MinMaxStack {
    private final int[] minimum;
    private final int[] maximum;
    private int size;

    public MinMaxStack(int capacity) {
        // Вместимость известна заранее, поэтому PUSH не требует расширения массива.
        minimum = new int[capacity];
        maximum = new int[capacity];
    }

    public void push(int value) {
        minimum[size] = size == 0 ? value : Math.min(minimum[size - 1], value);
        maximum[size] = size == 0 ? value : Math.max(maximum[size - 1], value);
        size++;
    }

    public void pop() {
        size--;
    }

    public int min() {
        return minimum[size - 1];
    }

    public int max() {
        return maximum[size - 1];
    }
}
