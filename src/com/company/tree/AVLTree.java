package com.company.tree;

import java.util.Comparator;

public class AVLTree<T> extends BinTree<T> {

    private class AVLNode<T> extends Node<T> {

        private int height;

        public AVLNode(Node<T> node, int height) {
            super(node.getObject(), node.getLeft(), node.getRight(), node.getRoot());
            this.height = height;
        }

        public AVLNode(T object, Node left, Node right, Node root, int height) {
            super(object, left, right, root);
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
            if (height < 0) height = 0;
            this.height = height;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public void sort(T[] arr){
        //1) add alle Werte in einen Baum
        //LZ = n* Teta( log(n) ) = Teta( n*log(n) )
        for (int i = 0; i < arr.length; i++) {
            add(arr[i]);
        }
        //2) Inorder-Durlaufen
        //LZ = Teta(n)
        TreeNode<T>[] nodes = getNodeArrWithDepth(TreeTraversal.Inorder);
        int i =0;
        //3) Einschreiben Feld
        //LZ = Teta(n)
        for (TreeNode<T> node : nodes) {
            if(!node.containEmptyObj()){
                arr[i++] = node.getObj();
            }
        }
        // sum LZ = Teta(n*log[n]) + 2*teta(n) = Teta(n *(log[n] + 2) ) = Teta(n*log[n])
     }

    public AVLTree(Comparator<T> comparator) {
        super(comparator);
    }

    private int  getHeight(Node<T> node) {
        return (node == null) ? -1 : ((AVLNode<T>) node).getHeight();
    }
    private void    setHeight(Node<T> node, int height) {
        ((AVLNode) node).setHeight(height);
    }

    //region rotations

    private void rotateLeft(Node<T> node) {
        Node<T> right = node.getRight();
        Node<T> left = node.getLeft();
        T obj = node.getObject();

        node.setRight(right.getRight());
        node.setLeft(right);
        node.setObject(right.getObject());

        setHeight(node, getHeight(right));
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

        setHeight(node, getHeight(left));
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

    //endregion

    @Override
    public boolean  add(T o) {
        if (o == null) return false;

        if (root == null) {
            root = new AVLNode<>(o, 0);
            size = 1;
            return true;
        }
        return add((Node<T>) root, o);
    }
    private boolean add(Node<T> node, T o) {

      //  if (comparator.compare(o, node.getObject()) == 0) return false;
        boolean res = true;

        if (comparator.compare(o, node.getObject()) <= 0) {
            if (!node.isHaveLeft()) {
                node.setLeft(new AVLNode<>(o, 0));
                updateHeight(node);
                size ++;
            } else {
                res = add(node.getLeft(), o);
                checkRotationRight(node);
            }
        } else {
            if (!node.isHaveRight()) {
                node.setRight(new AVLNode<>(o, 0));
                updateHeight(node);
                size++;
            } else {
                res = add(node.getRight(), o);
                checkRotationLeft(node);
            }
        }
        return res;
    }

    private boolean addAll(T ...o){
        boolean res = true;
        for (T t : o) {
            if(!add(t)) res = false;
        }
        return res;
    }

    @Override
    public boolean  remove(T o) {
        if(isEmpty()) return true;

        AVLNode<T> deleted = (AVLNode<T>) getNode(o , root);
        if(deleted == null) return false;

        remove(deleted);

        return true;
    }

    private void remove(Node<T> deletedNode) {

        if(size == 1) {
            clean();
            return ;
        }
        if (!deletedNode.isHaveRight() && !deletedNode.isHaveLeft()) {
            Node<T> n = deletedNode.getRoot();
            deleteLeaf(deletedNode);
            updateBalanceToRoot(n);

        } else if (!deletedNode.isHaveLeft()) {
             Node<T> n = swapAndDeleteNode(deletedNode, deletedNode.getRight());
             updateBalanceToRoot(n);
        } else if (!deletedNode.isHaveRight()) {
            Node n = swapAndDeleteNode(deletedNode, deletedNode.getLeft());
            updateBalanceToRoot(n);

        } else {
            AVLNode<T> minR = (AVLNode<T>) getSmallest(deletedNode.getRight());
            AVLNode<T> newNode = new AVLNode<>(minR.getObject(), deletedNode.getLeft(),
                    deletedNode.getRight(), deletedNode.getRoot(), -1 );
            updateHeight(newNode);

            swapAndDeleteNode(deletedNode, newNode);
            remove(minR);
            size++;
        }
        size --;
        return;
    }

    private void deleteLeaf(Node<T> leaf){
        Node<T> parent = leaf.getRoot();
        parent.removeChild(leaf);
        leaf.clean();
    }
    private Node<T> swapAndDeleteNode(Node<T> deleted, Node<T> newNode){
        Node<T> parent = deleted.getRoot();
        if(parent == null) {
            this.root = newNode;
            this.root.setRoot(null);
            setHeight((Node<T>) root, depth((Node<T>) root));
            balance(newNode);
            return newNode;
        }
        newNode.setRoot(parent);
        if(parent.getRight() == deleted)  parent.setRight(newNode);
        else parent.setLeft(newNode);
        deleted.clean();
        return newNode;
    }

    private void updateBalanceToRoot(Node<T> n){
        if(n == null) return;
        while (n.isHaveRoot()){
            balance(n);
            n = n.getRoot();
        }
        balance(n);
    }

    private void balance(Node<T> node) {
        if(node == null) return;
        fixHeight(node);
        if(Math.abs(getHeight(node.getRight()) - getHeight(node.getLeft())) == 2 ) {
            checkRotationLeft(node);
            checkRotationRight(node);
        }
    }

    private void fixHeight(Node<T> node){
        setHeight(node, depth(node));
    }

    private void updateHeight(Node<T> node) {
        ((AVLNode<T>) node).setHeight(super.depth(node));
    }

    private void initAllHeight() {
        root = recInitAllNode((Node<T>) root);
    }

    private AVLNode<T> recInitAllNode(Node<T> node) {

        AVLNode<T> avlNode = new AVLNode<>(node, depth(node));
        if (avlNode.isHaveLeft()) {
            avlNode.setLeft(recInitAllNode(avlNode.getLeft()));
        }
        if (avlNode.isHaveRight()) {
            avlNode.setRight(recInitAllNode(avlNode.getRight()));
        }
        return avlNode;
    }


    //region wrappers
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
