package com.company;

import com.company.example.SimpleBinTree;
import com.company.tree.*;

import java.util.*;

public class Main {


    /**
     * tree generation and copying
     */

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



    }
}
