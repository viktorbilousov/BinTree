package com.company;

import com.company.tree.*;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) {
        SimpleTree<Integer> tree = new SimpleTree<>(Integer::compareTo);
        ArrayList<Integer> list = new ArrayList<>();
        TreeTraversal treeTraversal = TreeTraversal.Postorder;

        Integer[] integer = new Integer[] {0, 4, 8, 9, 8, 4, 5, 1, -10, 3};

        for (Integer integer1 : integer) {
            tree.add(integer1);
        }

        tree.print(treeTraversal);
        TreeNode<Integer>[] integerTreeNode = tree.getNodeArrWithDepth(treeTraversal);
        System.out.println(Arrays.toString(integerTreeNode));

        for (int j = 0; j < 100; j++) {

            tree.clean();
            Random r = new Random();
            for (int i = 0; i < 100; i++) {
                list.add(r.nextInt(20) - 10);
            }

            tree.addAll(list);

            TreeNode<Integer>[] nodes = tree.getNodeArrWithDepth(treeTraversal);
            BinTree<Integer> copy = new SimpleTree<>(Integer::compareTo);
            copy.fillFromArr(nodes, treeTraversal);

            if(!copy.toString(treeTraversal).equals(tree.toString(treeTraversal))){
                tree.print(treeTraversal);
                System.out.println(Arrays.toString(nodes));
                list.forEach(o -> System.out.printf(o + ", "));
                System.out.println();
                copy.print(treeTraversal);
                break;
            }else System.out.println("ok "+j );

        }
    }
}
