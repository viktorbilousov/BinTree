package com.company.calculator;

class Operator {
    private char key;
    private int order;
    private iOper calc;

    public Operator(char key, int order, iOper calc) {
        this.key = key;
        this.order = order;
        this.calc = calc;
    }

    public char getKey() {
        return key;
    }

    public void setKey(char key) {
        this.key = key;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public double calc(double a, double b){
        return calc.calc(a,b);
    }

}