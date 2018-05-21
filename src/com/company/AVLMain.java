package com.company;

import com.company.tree.AVLTree;
import com.company.tree.TreeNode;
import com.company.tree.TreeTraversal;

public class AVLMain {
    public static void main(String[] args) {
        AVLTree<Integer> tree = new AVLTree<>(Integer::compare);
        AVLTree<Integer> testRotL = new AVLTree<>(Integer::compare);
        AVLTree<Integer> testRotR = new AVLTree<>(Integer::compare);


        AVLTree<Integer> test = new AVLTree<>(Integer::compare);

        TreeNode[] avlExample = new TreeNode[]{

                new TreeNode<Integer>(null, 3),
                new TreeNode<>(7, 2),
                new TreeNode<>(null, 3),

                new TreeNode<>(12, 1),

                new TreeNode<>(null, 3),
                new TreeNode<>(15, 2),
                new TreeNode<>(null, 3),


                new TreeNode<>(18, 0),


                new TreeNode<>(null, 3),
                new TreeNode<>(23, 2),
                new TreeNode<>(null, 3),

                new TreeNode<>(27, 1),

                new TreeNode<>(null, 3),
                new TreeNode<>(34, 2),
                new TreeNode<>(null, 3),

        };


        TreeNode[] exampleRotLeft = new TreeNode[]{

                new TreeNode<>(null, 2),
                new TreeNode<>(10, 1),
                new TreeNode<>(null, 2),


                new TreeNode<>(15, 0),


                new TreeNode<>(null, 3),
                new TreeNode<>(17, 2),
                new TreeNode<>(null, 3),

                new TreeNode<>(20, 1),


                new TreeNode<>(null, 4),
                new TreeNode<>(23, 3),
                new TreeNode<>(null, 4),


                new TreeNode<>(25, 2),

                new TreeNode<>(null, 4),
                new TreeNode<>(30, 3),
                new TreeNode<>(null, 4),
        };



        TreeNode[] exampleRotRight = new TreeNode[]{

                new TreeNode<Integer>(null, 4),
                new TreeNode<>(7, 3),
                new TreeNode<Integer>(null, 4),

                new TreeNode<>(10, 2),

                new TreeNode<>(null, 4),
                new TreeNode<>(12, 3),
                new TreeNode<>(null, 4),

                new TreeNode<>(15, 1),

                new TreeNode<>(null, 3),
                new TreeNode<>(17, 2),
                new TreeNode<>(null, 3),


                new TreeNode<>(20, 0),


                new TreeNode<>(null, 2),
                new TreeNode<>(25, 1),
                new TreeNode<>(null, 2),

        };

        testRotL.fillFromArr(exampleRotLeft, TreeTraversal.Inorder);
        testRotR.fillFromArr(exampleRotRight, TreeTraversal.Inorder);
        test.fillFromArr(avlExample, TreeTraversal.Inorder);




  /*      tree.fillFromArr(avlExample, TreeTraversal.Inorder);
        tree.print();


        test.addAll(Arrays.asList(7,12,15,18,23,27,34));
        test.print();
*/

    }
}
