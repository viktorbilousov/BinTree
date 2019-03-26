package com.company.tree;

import java.util.Collection;

public interface Tree<T> {

    int size();         // #Nodes
    int depth();        // tree depth
    boolean isEmpty();  // size == 0


    boolean add(T o);
    boolean addAll(Collection<T> c);

    boolean remove(T o);
    boolean removeAll(Collection<T> c);

    /**
     * get tree as String
     * @param treeTraversal - (Preoder, Inorder, Postorder)
     * @return
     */
    String toString(TreeTraversal treeTraversal); // Ausgabe des Baums (Preoder, Inorder, Postorder)

    void print();
    boolean isContain(T o);
    void clean();

}
