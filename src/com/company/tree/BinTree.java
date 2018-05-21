package com.company.tree;

import com.company.printer.ConsolePrinter;

import java.util.*;

public abstract class BinTree<T> implements iTree<T>{

    protected class Node<T>{
        private Node<T> left;
        private Node<T> right;
        private Node<T> root = null;
        private T object;

        public T getObject() {
            return object;
        }

        public Node<T> getLeft() {
            return left;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
            if(left != null) this.left.setRoot(this);
        }

        public void setObject(T object) {
            this.object = object;
        }

        public void setRight(Node<T> right) {
            this.right = right;
            if(right != null) this.right.setRoot(this);
        }

        public Node<T> getRoot() {
            return root;
        }

        public void setRoot(Node<T> root) {
            this.root = root;
        }

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

    Node<T> root = null;
    protected int size = 0;
    protected final Comparator<T> comparator;

    private ConsolePrinter printer = new ConsolePrinter();
    private final TreeTraversal DEF_TRAV = TreeTraversal.Inorder;


    public BinTree(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public abstract boolean add(T o);
    public abstract boolean remove(T o);

    @Override
    final public boolean addAll(Collection<T> c) {
        if(c.size() == 0) return false;
        boolean res = true;
        for (T t : c) {
            if( !add(t) ) res = false;
        }
        return res;
    }

    @Override
    final public int size() {
        return size;
    }

    @Override
    final public int depth() {
        return culcDepthRec(root, -1);
    }

    final protected int depth(Node<T> node) {
        if(node == null) return -1;
        return culcDepthRec(node, -1);
    }

    private int culcDepthRec(Node root, int depth){
        if(root == null) return depth;
        int dl = culcDepthRec(root.right, depth +1);
        int dr = culcDepthRec(root.left, depth+1);
        return dl > dr ? dl : dr;
    }

    @Override
    final public boolean remove(Collection<T> c) {
        if(c.size() == 0) return false;
        for (T t : c) {
            remove(t);
        }
        return true;
    }

    @Override
    final public boolean isContain(T o) {
        return  getNode(o, root) != null;
    }

    @Override
    final public T binSearch(T o) {
        return getNode(o,root).getObject();
    }

    @Override
    final public void print(){
        print(DEF_TRAV);
    }

    final public void print(TreeTraversal treeTraversal) {
        String line =  toString(treeTraversal);
        if(size == 0) {
            System.out.println(line);
            return;
        }

        final ConsolePrinter.Format rootFormat = ConsolePrinter.Format.URed;
        switch (treeTraversal) {
            case Preorder:
                line = printer.formatText(line, rootFormat, 1, line.indexOf(','));
                break;
            case Postorder:
                line = printer.formatText(line, rootFormat, line.lastIndexOf(',') + 1, line.length() - 1);
                break;
            case Inorder: {
                String rootObjStr = root.getObject().toString();
                int index = line.indexOf(rootObjStr);
                line = printer.formatText(line, rootFormat, index, rootObjStr.length() + index);
                break;
            }
        }

        int cnt = 0;

        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '(') {
                cnt++;
                line = printer.formatText(line, ConsolePrinter.Format.values()[cnt % 8 + 1], i, i + 1);
                i += ConsolePrinter.Format.values()[cnt % 8 + 1].toString().length();
            } else if (line.charAt(i) == ')') {
                line = printer.formatText(line, ConsolePrinter.Format.values()[cnt % 8 + 1], i, i + 1);
                i += ConsolePrinter.Format.values()[cnt % 8 + 1].toString().length();
                cnt--;
            }
        }

        printer.println(line);
    }

    @Override
    public String toString() {
        return toString(DEF_TRAV);
    }

    @Override
    final public String toString(TreeTraversal treeTraversal) {
        if(root == null) return "(null)";
        return getStr(root, null, treeTraversal, 0);
    }

    private String getStr(Node<T> root, String line, TreeTraversal t, int depth) {
        if(line == null) line = "";
        if (root!= null) {

            switch (t) {
                case Inorder: {
                    line += "(";
                    line = getStr(root.left, line, t, depth+1) +",";
                    line += root + ",";
                    line = getStr(root.right, line,t, depth+1);
                    line += ")";
                    break;
                }case Postorder:{
                    line += "(";
                    line = getStr(root.left, line,t, depth+1) + ",";
                    line = getStr(root.right, line,t, depth+1) + ",";
                    line += root;
                    line += ")";
                    break;
                }case Preorder:{
                    line +=  "(";
                    line += root + ",";
                    line = getStr(root.left, line,t , depth+1) + ",";
                    line = getStr(root.right, line,t, depth+1) ;
                    line += ")";
                    break;
                }
            }
        }else{
            line += "n";
        }

        return line;
    }

    @Override
    final public void clean(){
        root = null;
        size = 0;
    }

    public boolean equals(iTree obj) {
        return this.toString().equals(obj.toString());
    }

    public TreeNode<T>[] getNodeArrWithDepth(TreeTraversal treeTraversal) {

        ArrayList<TreeNode> list = new ArrayList<>();
        fillList(list, root, 0, treeTraversal);
        TreeNode[] treeNodes = new TreeNode[list.size()];
        treeNodes = list.toArray(treeNodes);
        return treeNodes;
    }

    //region tree presentation

    public boolean fillFromArr(TreeNode<T>[] tree, TreeTraversal traversal) {
        clean();
        if(tree.length == 0) return false;

        switch (traversal) {
            case Preorder:  root = createRootPre(tree, 0, tree.length - 1,null); break;
            case Postorder: root = createRootPos(tree,  tree.length - 1,null); break;
            case Inorder:   root = createRootIno(tree, 0, null, 0); break;
        }
        size  = tree.length;
        return false;
    }
    public boolean fillFromInoUndPre(TreeNode<T>[] inorder, TreeNode<T>[] preorder){
        clean();
        if(inorder.length == 0 || preorder.length == 0) return false;
        if(inorder.length != preorder.length) return false;
        root = createRootInoPre(inorder, preorder, 0, new int[]{0}, null);

        size = inorder.length;
        return true;
    }
    public boolean fillFromInoUndPos(TreeNode<T>[] inorder, TreeNode<T>[] posorder){
        clean();
        if(inorder.length == 0 || posorder.length == 0) return false;
        if(inorder.length != posorder.length) return false;
        root = createRootInoPos(inorder, posorder, inorder.length-1, new int[]{posorder.length-1}, null);

        size = inorder.length;
        return true;
    }



    private Node<T> createRootInoPre(TreeNode<T>[] ino, TreeNode<T>[] pre, int fromIno,  int[] indexRootPre, Node<T> prevRoot){

        int _indexRootPre = indexRootPre[0];
        if (pre[_indexRootPre].getObj() == null) return null;

        Node<T> curRoot = new Node<>(pre[_indexRootPre].getObj(), null,null, prevRoot);

        int indexRootIno = fromIno;
        while (!pre[_indexRootPre].getObj().equals(ino[indexRootIno].getObj())) indexRootIno ++;

        indexRootPre[0]++;
        curRoot.setLeft(createRootInoPre(ino, pre, fromIno,  indexRootPre , curRoot ));

        indexRootPre[0] ++;
        curRoot.setRight(createRootInoPre(ino, pre, indexRootIno+1, indexRootPre , curRoot));

        return curRoot;
    }
    private Node<T> createRootInoPos(TreeNode<T>[] ino, TreeNode<T>[] pre, int toIno,  int[] indexRootPre, Node<T> prevRoot){

        int _indexRootPre = indexRootPre[0];
        if (pre[_indexRootPre].getObj() == null) return null;

        Node<T> curRoot = new Node<>(pre[_indexRootPre].getObj(), null,null, prevRoot);

        int indexRootIno = toIno;
        while (!pre[_indexRootPre].getObj().equals(ino[indexRootIno].getObj())) indexRootIno --;

        indexRootPre[0]--;
        curRoot.setRight(createRootInoPos(ino, pre, toIno,  indexRootPre , curRoot ));

        indexRootPre[0]--;
        curRoot.setLeft(createRootInoPos(ino, pre, indexRootIno-1, indexRootPre , curRoot));

        return curRoot;
    }

    private Node<T> createRootPre(TreeNode<T>[] arr, int from, int to, Node<T> root) {
        if (arr[from].getObj() == null) return null;

        Node<T> curRoot = new Node<>(arr[from].getObj());
        curRoot.setRoot(root);
        int nextDepth = arr[from].getDepth() + 1;
        from++;

        for (int i = from + 1; i <= to; i++) {
            if (arr[i].getDepth() == nextDepth) {
                curRoot.setLeft(createRootPre(arr, from, i - 1, curRoot));
                curRoot.setRight(createRootPre(arr, i, to, curRoot));
            }
        }
        return curRoot;
    }
    private Node<T> createRootPos(TreeNode<T>[] arr, int to, Node<T> root) {
        if (arr[to].getObj() == null) return null;

        Node<T> curRoot = new Node<>(arr[to].getObj());
        curRoot.setRoot(root);
        int nextDepth = arr[to].getDepth() + 1;
        to--;

        for (int i = to-1; i >= 0; i--) {
            if (arr[i].getDepth() == nextDepth) {
                curRoot.setRight(createRootPos(arr, to, curRoot));
                curRoot.setLeft (createRootPos(arr, i, curRoot));
                break;
            }
        }
        return curRoot;

    }
    private Node<T> createRootIno(TreeNode<T>[] arr, int from, Node<T> root, int rootDepth) {

        int indexRoot = from;
        while (arr[indexRoot].getDepth() != rootDepth) indexRoot++;

        if (arr[indexRoot].getObj() == null) return null;

        Node<T> curRoot = new Node<>(arr[indexRoot].getObj());
        curRoot.setRoot(root);

        curRoot.setLeft(createRootIno(arr, from,  curRoot, rootDepth + 1 ));
        curRoot.setRight(createRootIno(arr, indexRoot+1,  curRoot,  rootDepth + 1));

        return curRoot;

    }

//endregion

    //todo copy
    @Override
    final public BinTree<T> copy() {
        BinTree<T> copy = new SimpleTree<>(comparator);
        if(root == null) return copy;
        copy.root = new Node<>(root.getObject());
        recCopy(this.root, copy.root);
        return copy;
    }
    private void recCopy(Node<T> thisRoot, Node<T> copyRoot){
        if(thisRoot == null) return;

        if(thisRoot.isHaveLeft()){
            copyRoot.setLeft(new Node<>(thisRoot.getLeft().getObject()));
            recCopy(thisRoot.getLeft(), copyRoot.getLeft());
        }
        if(thisRoot.isHaveRight()){
            copyRoot.setRight(new Node<>(thisRoot.getRight().getObject()));
            recCopy(thisRoot.getRight(), copyRoot.right);
        }
    }

    protected final Node<T> getNode(T o, Node<T> root){

        Node<T> pointer = root;
        while (!pointer.object.equals(o)){
            if(comparator.compare(pointer.object,o) > 0){
                if(pointer.isHaveLeft()) {
                    pointer = pointer.getLeft();
                }else {
                    return null;
                }
            }else{
                if(pointer.isHaveRight()){
                    pointer = pointer.getRight();
                }else{
                    return null;
                }
            }
        }
        return pointer;
    }
    protected final Node<T> getSmallest(Node<T> root){
        Node<T> pointer = root;
        while (pointer.isHaveLeft()) pointer = pointer.getLeft();
        return pointer;
    }
    protected final Node<T> getBigest(Node<T> root){
        Node<T> pointer = root;
        while (pointer.isHaveRight()) pointer = pointer.getRight();
        return pointer;
    }

    private void fillList(ArrayList<TreeNode> list, Node<T> root, int depth, TreeTraversal t) {
        if (root != null) {
            switch (t) {
                case Postorder: {
                    fillList(list, root.left, depth + 1, t);
                    fillList(list, root.right, depth + 1, t);
                    list.add( new TreeNode<>(root.object, depth) );
                    break;
                }
                case Preorder: {
                    list.add(new TreeNode<>(root.object, depth));
                    fillList(list, root.left, depth + 1, t);
                    fillList(list, root.right, depth + 1, t);
                    break;
                }
                case Inorder: {
                    fillList(list, root.left, depth + 1, t);
                    list.add(new TreeNode<>(root.object, depth));
                    fillList(list, root.right, depth + 1, t);
                    break;
                }
            }
        }else{
            list.add(new TreeNode<>(null, depth));
        }
    }

}
