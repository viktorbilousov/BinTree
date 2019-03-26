package com.company.calculator;

import com.company.tree.BinNode;
import com.company.tree.BinTree;
import com.company.tree.Node;

public class CalcTree  extends BinTree<String> {

    private Operator[] operators;

    public CalcTree(Operator[] operators) {
        super(String::compareTo);
        this.operators = operators;
    }

    public boolean add(String[] o){
        if(o.length == 0) return false;
        this.root = insert(o, 0 , o.length-1);
        return true;
    }

    @Override
    public boolean add(String o) {
        String[] input = o.split(" ");
        return add(input);
    }

    public double calc(){
        recCalc((Node<String>) root);
        return Double.parseDouble(root.getObject());
    }

    private void recCalc(Node<String> node){
        if(node == null) return;
        recCalc(node.getRight());
        recCalc(node.getLeft());
        calcNode(node);
    }

    private void calcNode(Node<String> n){
        if(!n.isHaveChild()) return;

        if(! n.getLeft().isHaveChild() && !n.getRight().isHaveChild()){
            Operator oper = getOperator(n.getObject().charAt(0));

            if(oper != null) {
                Double numL = Double.parseDouble(n.getLeft().getObject());
                Double numR = Double.parseDouble(n.getRight().getObject());

                Double res = oper.calc(numL, numR);
                n.setRight(null);
                n.setLeft(null);
                n.setObject(res.toString());
            }
        }
    }

    private BinNode<String> insert(String[] s, int from, int to ){

        if(from  == to) {
            size++;
            return new Node<>(s[to]);
        }

        if(isNeedRemoveCase(s, from, to)){
            from ++;
            to --;
        }
        if(from  == to) {
            size++;
            return new Node<>(s[to]);
        }

        int index = getSplitIndex(s, from , to );

        Node<String> node = new Node<>(s[index]);

        node.setLeft(insert(s, from, index-1));
        node.setRight(insert(s, index+1, to));

        size++;
        return node;

    }

    private int getSplitIndex(String[] s, int from, int to) {

        int minOrderOper = Integer.MAX_VALUE;
        boolean isIgnore = false;
        int index = -1;

        for (int i = from; i < to; i++) {

            if(s[i].equals("(")) isIgnore = true;
            if(s[i].equals(")")) isIgnore = false;
            if(isIgnore) continue;
            if(s[i].length() == 1) {
                Operator operator = getOperator(s[i].charAt(0));
                if (operator != null && operator.getOrder() < minOrderOper) {
                    minOrderOper = operator.getOrder();
                    index = i;
                }
            }
        }

        return index;

    }

    private boolean isNeedRemoveCase(String[] s, int from, int to){
        int cnt = 0;

        for (int i = from; i < to-1; i++) {
            if(s[i].equals("(")) cnt++;
            if(s[i].equals(")")) cnt--;
            if(cnt == 0) return false;
        }

        return true;
    }

    private Operator getOperator(char c){
        for (Operator operator : operators) {
            if(operator.getKey() == c) return operator;
        }
        return null;
    }

    @Override
    public boolean remove(String o) {
        return false;
    }
}