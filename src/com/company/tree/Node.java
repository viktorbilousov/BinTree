package com.company.tree;

public class Node<T> implements BinNode<T>{
    private Node<T> left;
    private Node<T> right;
    private Node<T> root;
    private T object;

    public Node(T object, Node<T> left, Node<T> right) {
        setLeft(left);
        setRight(right);
        this.object = object;
        this.root = null;
    }

    public Node(T object, Node<T> left, Node<T> right, Node<T> root) {
        setLeft(left);
        setRight(right);
        this.root = root;
        this.object = object;
    }

    public Node(T object) {
        this.object = object;
        right = null;
        left = null;
        root = null;
    }

    public Node(BinNode<T> node){
        object = node.getObject();
        root =  (Node<T>) node.getRoot();
        left = (Node<T>) node.getLeft();
        right = (Node<T>) node.getRight();

    }

    //region get/set
    public T getObject() {
        return object;
    }

    public Node<T> getLeft() {
        return left;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setLeft(BinNode<T> left) {

        this.left = (Node<T>) left;
        if(left != null) this.left.setRoot(this);
    }

    public void setObject(T object) {
        this.object = object;
    }

    public void setRight(BinNode<T> right) {
        this.right = (Node<T>) right;
        if(right != null) this.right.setRoot(this);
    }

    public Node<T> getRoot() {
        return root;
    }

    public void setRoot(BinNode<T> root) {
        this.root = (Node<T>) root;
    }
    //endregion

    public void clean(){
        left = null;
        right = null;
        object = null;
        root = null;
    }

    public boolean isHaveLeft(){
        return left != null;
    }

    public boolean isHaveRight(){
        return right != null;
    }

    public boolean isHaveRoot(){
        return root != null;
    }

    public boolean isHaveChild(){return  isHaveLeft() || isHaveRight(); };

    public boolean removeChild(Node<T> child){
        if(child == left) {
            left = null;
            return true;
        }
        else if (child == right){
            right = null;
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        try {
            T _obj = (T) obj;
        }catch (Exception e){
            return false;
        }
        return  obj.equals(object);
    }

    @Override
    public String toString() {
        return object.toString();
    }
}
