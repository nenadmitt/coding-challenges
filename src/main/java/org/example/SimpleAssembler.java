package org.example;

import java.util.HashMap;
import java.util.Map;

// Codewards : https://www.codewars.com/kata/58e24788e24ddee28e000053
// 5kyu
public class SimpleAssembler {

    class Variable {
        int value;
        String name;
        boolean isConstant;

        public Variable(int value, String name, boolean isConstant) {
            this.value = value;
            this.name = name;
            this.isConstant = isConstant;
        }
    }
    private final Map<String, Integer> register = new HashMap<>();

    public static Map<String, Integer> interpret(String[] program){
        return new SimpleAssembler()._interpret(program);
    }
    public Map<String,Integer> _interpret(String[] program) {

        var i = 0;
        while (i < program.length) {
            var command = program[i];
            var updatedByCommand = false;
            if (command.startsWith("mov")){
                parseMov(command);
            }else if(command.startsWith("inc") || command.startsWith("dec")) {
                parseIncDec(command);
            }else if(command.startsWith("jnz")) {
                var split = command.split(" ");
                var inputA = checkValue(split[1]);
                var inputB = checkValue(split[2]);

                var ittrValue = inputB.isConstant ? inputB.value : register.get(inputB.name);
                var inputAValue = inputA.isConstant ? inputA.value : register.get(inputA.name);

                if (inputAValue != 0) {
                    i = i + ittrValue;
                    updatedByCommand = true;
                }

            }else {
                throw new IllegalArgumentException("Unknown command:" + command);
            }

            if (!updatedByCommand) {
                i++;
            }
        }
        return register;
    }

    private void parseMov(String command) {
        var splited = command.split(" ");
        var variableA = splited[1];
        var variableB = checkValue(splited[2]);

        if (variableB.isConstant) {
            register.put(variableA, variableB.value);
        }else {
            register.put(variableA, register.get(variableB.name));
        }
    }

    private void parseIncDec(String command) {
        var splited = command.split(" ");
        var type = splited[0];
        var variable = splited[1];
        if (type.equals("inc")) {
            register.put(variable, register.get(variable) + 1);
        }else if ( type.equals("dec")) {
            register.put(variable, register.get(variable) - 1);
        }else {
            throw new IllegalArgumentException("Unknown operation for " + type);
        }
    }

    private Variable checkValue(String c) {
        var numericValue = 0;
        var variableName = "";
        var isConstant = false;

        try {
            numericValue = Integer.parseInt(c);
            isConstant = true;
        }catch (NumberFormatException ex) {
            variableName = c;
        }
        return new Variable(numericValue, variableName, isConstant);
    }
}
