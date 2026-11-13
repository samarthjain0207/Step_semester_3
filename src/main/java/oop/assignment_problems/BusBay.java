public class BusBay {
    private final int[] slots;
    private final int capacity;
    private int front;
    private int size;

    BusBay(int capacity) {
        this.capacity = capacity;
        this.slots = new int[capacity];
    }

    boolean insertFront(int bus) {
        if (isFull()) return false;
        front = (front - 1 + capacity) % capacity;
        slots[front] = bus;
        size++;
        return true;
    }

    boolean insertLast(int bus) {
        if (isFull()) return false;
        slots[(front + size) % capacity] = bus;
        size++;
        return true;
    }

    boolean deleteFront() {
        if (isEmpty()) return false;
        front = (front + 1) % capacity;
        size--;
        return true;
    }

    boolean deleteLast() {
        if (isEmpty()) return false;
        size--;
        return true;
    }

    int getFront() {
        return isEmpty() ? -1 : slots[front];
    }

    int getRear() {
        return isEmpty() ? -1 : slots[(front + size - 1) % capacity];
    }

    boolean isEmpty() {
        return size == 0;
    }

    boolean isFull() {
        return size == capacity;
    }

    public static void main(String[] args) {
        BusBay b = new BusBay(3);
        System.out.println(b.insertLast(101) + ", " + b.insertLast(102) + ", " + b.insertFront(100) + ", "
                + b.insertFront(99) + ", " + b.getRear() + ", " + b.isFull() + ", " + b.deleteLast() + ", "
                + b.insertFront(98) + ", " + b.getFront());
    }
}
