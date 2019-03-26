package com.company;

import com.company.tree.*;

import java.util.*;

public class Main {

    public static void main(String[] args) {

        SimpleBinTree<Integer> tree =       new SimpleBinTree<>(Integer::compareTo);
        SimpleBinTree<Integer> newTree =    new SimpleBinTree<>(Integer::compareTo);

        Random r = new Random();

        for(int i =0 ; i< 10 ; i++){
            if(!tree.add(r.nextInt(100))) i--;
        }

        System.out.printf("Tree:\t\t");
        tree.print(TreeTraversal.Inorder);

        TreeNode[] pos = tree.getNodeArrWithDepth(TreeTraversal.Postorder);
        TreeNode[] ino = tree.getNodeArrWithDepth(TreeTraversal.Inorder);

        newTree.fillFromInoUndPos(ino, pos);
        System.out.printf("New Tree:\t");
        newTree.print(TreeTraversal.Inorder);
        System.out.println("isEquals=" + newTree.equals(tree));




/*    SimpleBinTree<Integer> tree = new SimpleBinTree<>(Integer::compareTo);
    Integer[] integers = new Integer[]{15,5,3,12,13,10,6,7,16,20,18,23};
        for (Integer integer : integers) {
            tree.add(integer);
        }
       // tree.print(TreeTraversal.Postorder);
        System.out.println(Arrays.toString(tree.toArray(TreeTraversal.Inorder)));*/


    }
}
