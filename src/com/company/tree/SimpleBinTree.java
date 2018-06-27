package com.company.tree;

import java.util.Comparator;

public class SimpleBinTree<T> extends BinTree<T> {

    public SimpleBinTree(Comparator<T> comparator) {
        super(comparator);
    }

    public boolean add(T o){
        if(root == null){
            root = new Node<T>(o);
            size++;
            return true;
        }

        Node<T> pointer = (Node<T>) root;
        Node<T> newPoint = new Node<T>(o);
        while (true){
            int compare = compareNode(pointer, newPoint);
            if(compare > 0){
                if(!pointer.isHaveLeft())   {
                    newPoint.setRoot(pointer);
                    pointer.setLeft(newPoint);
                    break;
                }
                else pointer = pointer.getLeft();
            }else if(compare < 0){
                if(!pointer.isHaveRight()) {
                    newPoint.setRoot(pointer);
                    pointer.setRight(newPoint);
                    break;
                }
                else {
                    pointer = pointer.getRight();
                }
            }else{
                return false;
            }
        }
        size++;
        return true;
    }

    public boolean remove(T o) {
        return remove(o, this.root);
    }

    private boolean remove(T o, BinNode<T> root) {

        if(size == 0) return false;

        Node<T> deletedNode = (Node<T>) getNode(o, root);
        if (deletedNode == null) return false;

        if(size == 1) {
            clean();
            return true;
        }
        if (!deletedNode.isHaveRight() && !deletedNode.isHaveLeft()) {
            deleteLeaf(deletedNode);
        } else if (!deletedNode.isHaveLeft()) {
            swapAndDeleteNode(deletedNode, deletedNode.getRight());

        } else if (!deletedNode.isHaveRight()) {
            swapAndDeleteNode(deletedNode, deletedNode.getLeft());

        } else {
            Node<T> minR = getSmallest(deletedNode.getRight());
            Node<T> newNode = new Node<T>(minR.getObject(), deletedNode.getLeft(),
                    deletedNode.getRight(), deletedNode.getRoot());
            swapAndDeleteNode(deletedNode, newNode);
            remove(minR.getObject(), newNode.getRight());
            size++;
        }
        size --;
        return true;
    }

    private void deleteLeaf(Node<T> leaf){
        Node<T> parent = leaf.getRoot();
        if(parent.getLeft() == leaf) parent.setLeft(null);
        else parent.setRight(null);
        leaf.clean();
    }

    private void swapAndDeleteNode(Node<T> deleted, Node<T> newNode){
        Node<T> parent = deleted.getRoot();
        if(parent == null) {
            this.root = newNode;
            this.root.setRoot(null);
            return;
        }
        newNode.setRoot(parent);
        if(parent.getRight() == deleted)  parent.setRight(newNode);
        else parent.setLeft(newNode);
        deleted.clean();
    }

    private int compareNode(Node<T> n1, Node<T> n2){
        return comparator.compare(n1.getObject(),n2.getObject());
    }
}
