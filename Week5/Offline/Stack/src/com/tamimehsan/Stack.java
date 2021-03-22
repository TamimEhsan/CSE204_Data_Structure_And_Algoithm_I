package com.tamimehsan;

public class Stack<T> {
    private Node<T> head;
    private int size;

    public Stack() {
    }
    public T top(){
        if( head == null ) return null;
        return head.getValue();
    }
    public void push(T value){

        if( size == 0 ){
            Node<T> node = new Node<T>(value,null);
            head = node;
        } else{
            Node<T> node = new Node<T>(value,head);
            head = node;
        }
        size++;
    }
    public void pop(){
        if( size != 0 ){
            head = head.getNext();
            size--;
        }
    }
    public int size(){
        return size;
    }
    public boolean empty(){
        return size == 0;
    }
}
