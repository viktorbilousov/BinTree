package com.company.tree;

public interface BinNode<T> {
    T getObject();
    BinNode<T> getLeft();
    BinNode<T> getRight();
    BinNode<T> getRoot();

    default boolean isHaveRight() {return  getRight() != null;}
    default boolean isHaveLeft() {return  getLeft() != null;}

    void setObject(T object);
    void setLeft(BinNode<T> left);
    void setRight(BinNode<T> right);
    void setRoot(BinNode<T> root);

}
