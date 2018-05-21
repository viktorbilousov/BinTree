package com.company.tree;

import java.util.Comparator;

public class AVLTree<T> extends BinTree<T> {


    final private class AVLNode<T> extends Node<T> {

        private int height;

        public AVLNode(Node<T> node, int height){
            super(node.getObject(), node.getLeft(), node.getRight(), node.getRoot());
            this.height = height;
        }

        public AVLNode(T object, int height) {
            super(object);
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        @Override
        public String toString() {
            return super.toString() + "="+ height;
        }
    }

    private int getHeight(Node<T> node) {
        return (node == null) ? -1 : ((AVLNode<T>) node).getHeight();
    }

    public AVLTree(Comparator<T> comparator) {
        super(comparator);
    }

    private void updateHeight(Node<T> node) {
        ((AVLNode<T>) node).setHeight(super.depth(node));
    }

    private void initAllHeight(){
        root = recInitAllNode(root);
    }

    private AVLNode<T> recInitAllNode(Node<T> node){

        AVLNode<T> avlNode = new AVLNode<>(node, depth(node));
        if(avlNode.isHaveLeft()){
            avlNode.setLeft(recInitAllNode(avlNode.getLeft()));
        }
        if(avlNode.isHaveRight()){
            avlNode.setRight(recInitAllNode(avlNode.getRight()));
        }
        return avlNode;
    }

    private void rotateLeft(Node<T> node) {
        Node<T> right = node.getRight();
        Node<T> left = node.getLeft();

        T obj = node.getObject();

        node.setRight(right.getRight());
        node.setLeft(right);
        node.setObject(right.getObject());

        ((AVLNode<T>) node).setHeight(getHeight(right));
        right.setRight(right.getLeft());
        right.setLeft(left);
        right.setObject(obj);

        updateHeight(node.getLeft());
        updateHeight(node);

    }

    private void rotateRight(Node<T> node) {
        Node<T> left = node.getLeft();
        Node<T> right = node.getRight();
        T nodeObj = node.getObject();
        node.setLeft(left.getLeft());
        node.setRight(left);
        node.setObject(left.getObject());
        ((AVLNode)node).setHeight(getHeight(left));
        left.setLeft(left.getRight());
        left.setRight(right);
        left.setObject(nodeObj);

        updateHeight(node.getRight());
        updateHeight(node);
    }

    private void doubleRotationLeft(Node<T> node) {
        rotateRight(node.getRight());
        rotateLeft(node);
    }

    private void doubleRotationRight(Node<T> node) {
        rotateLeft(node.getLeft());
        rotateRight(node);
    }

    private void checkRotationRight(Node<T> node) {

        if (node != null && node.getLeft() != null) {

            if (getHeight(node.getLeft()) - getHeight(node.getRight()) == 2) {

                if (getHeight(node.getLeft().getRight()) >
                        getHeight(node.getLeft().getLeft())) {

                    doubleRotationRight(node);
                } else rotateRight(node);
            } else updateHeight(node);
        } else updateHeight(node);

    }

    private void checkRotationLeft(Node<T> node) {


        if (node != null && node.getRight() != null) {

            if (getHeight(node.getRight()) - getHeight(node.getLeft()) == 2) {

                if (getHeight(node.getRight().getLeft()) >
                        getHeight(node.getRight().getRight())) {

                    doubleRotationLeft(node);
                } else rotateLeft(node);
            } else updateHeight(node);
        } else updateHeight(node);

    }

    @Override
    public boolean add(T o) {
        if(o == null) return false;

        if (root == null){
            root = new AVLNode<>(o, 0);
            size = 1;
            return true;
        }

        return add(root,o);
    }

    private boolean add(Node<T> node, T o){

        if(comparator.compare(o, node.getObject()) == 0) return false ;

        if(comparator.compare(o, node.getObject()) < 0){
            if(!node.isHaveLeft()){
               node.setLeft(new AVLNode<>(o, 0));
               updateHeight(node);
            }else{
                add(node.getLeft(), o);
                checkRotationRight(node);
            }
        }else{
            if(!node.isHaveRight()){
                node.setRight(new AVLNode<>(o,0));
                updateHeight(node);
            }else{
                add(node.getRight(), o);
                checkRotationLeft(node);
            }
        }
        size++;
        return true;
    }

    @Override
    public boolean remove(T o) {
        System.out.println("Ups =) ");
        return false;
    }

    @Override
    public boolean fillFromArr(TreeNode<T>[] tree, TreeTraversal traversal) {
        boolean res = super.fillFromArr(tree, traversal);
        initAllHeight();
        return res;
    }

    @Override
    public boolean fillFromInoUndPre(TreeNode<T>[] inorder, TreeNode<T>[] preorder) {
        boolean res = super.fillFromInoUndPre(inorder, preorder);
        initAllHeight();
        return res;
    }

    @Override
    public boolean fillFromInoUndPos(TreeNode<T>[] inorder, TreeNode<T>[] posorder) {
        boolean res = super.fillFromInoUndPos(inorder, posorder);
        initAllHeight();
        return res;
    }
}
