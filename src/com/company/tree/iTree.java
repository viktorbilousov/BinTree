package com.company.tree;

import java.util.Collection;

public interface iTree<T> {

    int size();
    int depth();

    boolean add(T o);
    boolean addAll(Collection<T> c);

    boolean remove(T o);
    boolean remove(Collection<T> c);

    String toString(TreeTraversal treeTraversal);

    void print();
    boolean isContain(T o);

    T binSearch( T o );

    iTree copy();

    void clean();

}
