package ru.job4j.linked;

public final class Node<T> {
    private final Node<T> next;
    private final T value;

    public Node(final Node<T> next, final T value) {
        this.next = next;
        this.value = value;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        throw new IllegalStateException(
                String.format("Can't change immutable object state (next): %s", next));
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        throw new IllegalStateException(
                String.format("Can't change immutable object state (value): %s", value));
    }
}