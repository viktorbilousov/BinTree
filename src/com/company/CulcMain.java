package com.company;

import com.company.calculator.Calculator;
import com.company.calculator.exceptions.FunctionException;

public class CulcMain {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        try {
            System.out.println(calculator.calc("sin(0) + max(2,max(4,8))+10.67 + pow(2,10)"));
        } catch (FunctionException e) {
            e.printStackTrace();
        }

    }
}
