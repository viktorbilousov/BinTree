package com.company.calculator;

import com.company.calculator.exceptions.FunctionException;

import java.util.Arrays;

public class Calculator {

    private CalcTree tree;
    private Operator[] operators;
    private Function[] functions;

    public Calculator() {
        init();
        this.tree = new CalcTree(operators);
    }

    private void init() {
        operators = new Operator[]{
                new Operator('+', 0, (a, b) -> a + b),
                new Operator('-', 0, (a, b) -> a - b),
                new Operator('*', 1, (a, b) -> a * b),
                new Operator('/', 1, (a, b) -> a / b),
                new Operator('%', 1, (a, b) -> a % b),
                new Operator('^', 2, Math::pow),
                new Operator('@', 2, (a, b) -> Math.pow(b, 1 / a)),
        };

        functions = new Function[]{
                new Function("log", a -> Math.log(a[0])),
                new Function("abs", a -> Math.abs(a[0])),

                new Function("max", a -> {
                    double max = a[0];
                    for (int i = 1; i < a.length; i++) {
                        if (a[i] > max) max = a[i];
                    }
                    return max;
                }),

                new Function("min", a -> {
                    double min = a[0];
                    for (int i = 1; i < a.length; i++) {
                        if (a[i] < min) min = a[i];
                    }
                    return min;
                }),
                new Function("pow", a -> Math.pow(a[0], a[1])),
                new Function("sin", a -> Math.sin(a[0]))
        };

    }

    /**
     * calculation
     * @param line - input expression
     * @return result
     * @throws FunctionException - error recognizing or executing function
     */
    public double calc(String line) throws FunctionException {
        String[] input = splitText(line); // split text to numbers, operators, functions, cases
        if (!checkCorrect(input)) throw new IllegalArgumentException();
        System.out.println(line);
        return calcRec(input);
    }

    private double calcRec(String[] s) throws FunctionException {

        for (int i = 0; i < s.length; i++) {

            Function f = getFunction(s[i]); // search functions
            if (f != null) {

                String[] arguments = getArgumentsInCase(s, i+1);
                double[] calcArguments = new double[arguments.length];

                int closeCaseIndex = getCloseCaseIndex (s, i+1);

                if (arguments.length == 0) throw new FunctionException("Empty param in " + f.getKey());

                int argLength = closeCaseIndex - i;

                for (int j = 0; j < arguments.length; j++) {
                    calcArguments[j] = calcRec(splitText(arguments[j])); // if argument is another function ( ln[pow (2,3)] )
                }

                Double res = f.run(calcArguments);

                //replace the function to the value in 's'
                String[] newS = new String[s.length - argLength]; // 2 cases
                System.arraycopy(s, 0, newS, 0, i);
                newS[i] = res.toString();
                if (newS.length - 1 != i) {
                    System.arraycopy(s, i + 1 + argLength, newS, i + 1, s.length - argLength - i - 1);
                }

                s = newS;
            }
        }
        tree.clean();
        tree.add(s);
        return tree.calc();
    }

    private Function getFunction(String s) {
        for (Function function : functions) {
            if (s.equals(function.getKey())) return function;
        }
        return null;
    }

    private boolean checkCorrect(String[] o) {
        int cnt = 0;
        for (int i = 0; i < o.length; i++) {
            if (o[i].equals("(")) cnt++;
            if (o[i].equals(")")) cnt--;
        }
        return cnt == 0;
    }

    private String[] splitText(String s) {

        s = s.replace(" ", "");

        if (s.charAt(0) == '-') s = 0 + s;

        for (Operator operator : operators) {
            s = s.replace("" + operator.getKey()," " + operator.getKey() + " ");
        }
        for (Function function : functions) {
            s = s.replace(function.getKey(), function.getKey() + " ");
        }

        s = s.replace("(", "( ");
        s = s.replace(")", " )");
        s = s.replace(",", " , ");


        StringBuilder line = new StringBuilder(s);
        boolean isLastOper = false;
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == ' ') continue;

            char c = line.charAt(i);

            if (isOper(c)) {
                if (!isLastOper) {
                    isLastOper = true;
                } else {
                    line.deleteCharAt(i - 1);
                    while (line.charAt(i) == ' ' && i < line.length())
                        line.deleteCharAt(i);
                    isLastOper = false;
                }
            } else {
                isLastOper = false;
            }

        }

        return line.toString().split(" ");
    }

    private boolean isOper(char c) {
        for (Operator operator : operators) {
            if (operator.getKey() == c) return true;
        }
        return false;
    }

    private int getCloseCaseIndex(String[] o, int from){
        int cnt = 0;
        for (int i = from; i < o.length; i++) {
            if (o[i].equals("(")) cnt++;
            if (o[i].equals(")")) cnt--;
            if (cnt == 0) {
                return i;
            }
        }
        return  -1;
    }

    private String[] getArgumentsInCase(String[] o, int from) {
        int cnt = 0;
        String line = "";
        for (int i = from; i < o.length; i++) {
            if (o[i].equals("(")) cnt++;
            if (o[i].equals(")")) cnt--;
            if (cnt == 0) {
                break;
            }
            if(o[i].equals(",") && cnt == 1){
                 line += "@@";
            }else if(i != from){
                line += o[i];
            }
        }

        if (line.equals("")) return new String[]{};
        return line.split("@@");
    }

}
