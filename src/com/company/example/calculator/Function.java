package com.company.example.calculator;

import com.company.example.calculator.exceptions.FunctionException;

public class Function {
    private String key;
    private iFunc function;

    public Function(String key, iFunc function) {
        this.key = key;
        this.function = function;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public double run(double ...a) throws FunctionException {
        double res;
        try {
            res = function.run(a);
        } catch (Exception E) {
            throw new FunctionException("Error function Param");
        }
        return res;
    }
}
