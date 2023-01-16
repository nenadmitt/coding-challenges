package org.example;

import java.util.*;

//https://www.codewars.com/kata/58e61f3d8ff24f774400002c
// Difficulty: 2kyu
public class AssemblerInterpreter {

    enum InstructionType {
        COMMAND, MSG, RET, END, CALL, JUMP
    }
    private class Execution {
        String label;
        LinkedList<Instruction> instructions;

        public Execution(String label) {
            this.label = label;
        }
    }

    private class Instruction {
        InstructionType type;
        List<String> parameters;

        public Instruction(InstructionType type, List<String> parameters) {
            this.type = type;
            this.parameters = parameters;
        }
    }

    private class Variable {
        String name;
        Integer value;
        boolean isConstant;

        public Variable(String name, boolean isConstant) {
            this.name = name;
            this.isConstant = isConstant;
        }

        public Variable(Integer value, boolean isConstant) {
            this.value = value;
            this.isConstant = isConstant;
        }
    }
    private final Map<String, Integer> register;
    private final Set<String> expressions;
    private final Set<String> conditionals;
    private final String jmp = "jmp";
    private final String output = "msg";

    private final Map<String, Execution> executionMap;

    public AssemblerInterpreter() {
        register = new HashMap<>();
        expressions = Set.of("mov", "inc", "dec", "add", "sub", "mul", "div");
        conditionals = Set.of("cmp", "jne", "je", "jge", "jg", "jle", "jl", "jmp");
        executionMap = new HashMap<>();
    }
    public static String interpret(final String input) {
        return new AssemblerInterpreter()._interpret(input);
    }

    public String execute() {

        String execOutput = null;

        if (executionMap.isEmpty()) {
            return null;
        }
        Map<String,LinkedList<Instruction>> executionContext = new HashMap<>();

        var mainExecContext = executionMap.get("main").instructions;
        executionContext.put("main", mainExecContext);

        var currentExecContext = mainExecContext;
        var callStack = new Stack<>();
        var currentContextID = "main";

        while (!currentExecContext.isEmpty()) {

            var instr = currentExecContext.remove();
            switch (instr.type) {
                case COMMAND -> {
                    executeCommand(instr);
                }
                case MSG -> {
                    execOutput = parseOutput(instr);
                }
                case CALL -> {
                    var lbl = instr.parameters.get(1);
                    var funcID = UUID.randomUUID().toString();

                    var executionCtx = new LinkedList<>(executionMap.get(lbl).instructions);

                    callStack.push(currentContextID);
                    currentContextID = funcID;
                    executionContext.put(funcID, executionCtx);

                    currentExecContext = executionCtx;
                }
                case JUMP -> {
                    var params = instr.parameters;
                    var cmd = params.get(0);
                    if (cmd.equals(jmp)) {
                        var lbl = params.get(1);
                        currentExecContext = new LinkedList<>(executionMap.get(lbl).instructions);
                    } else {
                        var shouldJump = evaluateConditional(instr);
                        var lbl = instr.parameters.get(4);
                        if (shouldJump) {
                            currentExecContext = new LinkedList<>(executionMap.get(lbl).instructions);
                        }
                    }
                }
                case END -> {
                    return execOutput;
                }
                case RET -> {

                    if (callStack.isEmpty()) {
                        return null;
                    }

                    while(true) {
                        var prevStack = callStack.peek();
                        var exec = executionContext.get(prevStack);
                        if (prevStack.equals("main")) {
                            currentExecContext = mainExecContext;
                            break;
                        }
                        else if (exec.size() > 0) {
                            currentExecContext = mainExecContext;
                            break;
                        }
                        callStack.pop();
                    }
                }
            }
        }

        return null;
    }

    private boolean evaluateConditional(Instruction instr) {
        var params = instr.parameters;
        var valueA = params.get(1);
        var valueB = getVariable(params.get(2));
        var cmd = params.get(3);

        var x = register.get(valueA);
        var y = valueB.isConstant ? valueB.value : register.get(valueB.name);

        return switch (cmd) {
            case "jne" -> !Objects.equals(x, y);
            case "je" -> Objects.equals(x, y);
            case "jge" -> x >= y;
            case "jg" -> x > y;
            case "jle" -> x <= y;
            case "jl" -> x < y;
            default -> false;
        };
    }

    public String _interpret(String input) {

        var parsedLines = parseRawLines(input);
        var currentLabel = "main";

        var ittr = 0;
        while (ittr < parsedLines.size()) {
            var currentLine = parsedLines.get(ittr);
            var command = currentLine.get(0);

            String funcCall = "call";
            String end = "end";
            String ret = "ret";
            if (expressions.contains(command)) {
                var instruction = new Instruction(InstructionType.COMMAND, currentLine);
                addInstruction(currentLabel, instruction);
            }

            else if (conditionals.contains(command)) {
                if (command.equals(jmp)){
                    addInstruction(currentLabel, new Instruction(InstructionType.JUMP, currentLine));
                }else {
                    // conditionals contain two lines
                    // first line evaluates the expression
                    // second line
                    var nextLine = parsedLines.get(ittr + 1);
                    currentLine.addAll(nextLine);
                    addInstruction(currentLabel, new Instruction(InstructionType.JUMP, currentLine));
                    ittr++;
                }
            }

            else if (command.equals(funcCall)) {
                addInstruction(currentLabel, new Instruction(InstructionType.CALL, currentLine));
            }

            else if (command.equals(end)) {
                addInstruction(currentLabel,new Instruction(InstructionType.END, null));
            }

            else if (command.equals(ret)) {
                addInstruction(currentLabel,new Instruction(InstructionType.RET, null));
            }

            else if (command.equals(output)) {
                addInstruction(currentLabel,new Instruction(InstructionType.MSG, currentLine));
            }

            else if(currentLine.size() == 1) {
                if (command.endsWith(":")) {
                    currentLabel = command.substring(0, command.length() - 1);
                }
            }

            else {
                throw new IllegalArgumentException("Unknown operation " + command);
            }
            ittr++;
        }
        return execute();
    }

    private void addInstruction(String label, Instruction instruction) {
        if (!executionMap.containsKey(label)) {
            var execution = new Execution(label);
            execution.instructions = new LinkedList<>();
            executionMap.put(label, execution);
        }
        executionMap.get(label).instructions.add(instruction);
    }

    private String parseOutput(Instruction instr) {
        var params = instr.parameters;
        var result = new StringBuilder();
        for (var i = 1; i < params.size(); i++) {
            var current = params.get(i);
            if (current.startsWith("'") && current.endsWith("'")) {
                result.append(current);
            }else if (current.length() == 1) {
                var value = register.get(current);
                result.append(value);
            }
        }

        return  result.toString().replaceAll("'","");
    }
    private void executeCommand(Instruction instr) {

        var command = instr.parameters.get(0);
        var label = instr.parameters.get(1);
        if (instr.parameters.size() == 3) {
            var variable = getVariable(instr.parameters.get(2));
            switch (command) {
                case "mov" -> muv(label, variable);
                case "div" -> div(label, variable);
                case "mul" -> mul(label, variable);
                case "add" -> add(label, variable);
                case "sub" -> sub(label, variable);
                default -> throw new IllegalArgumentException("Unsuported command");
            }
        }else {
            switch (command) {
                case "inc" -> inc(label);
                case "dec" -> dec(label);
                default -> throw new IllegalArgumentException("Unsuported command");
            }
        }
    }

    private void muv(String label, Variable var) {
        if (var.isConstant) {
            register.put(label, var.value);
        }else {
            var y = register.get(var.name);
            register.put(label, y);
        }
    }

    private void inc(String label) {
        register.put(label, register.get(label) + 1);
    }

    private void dec(String label) {
        register.put(label, register.get(label) - 1);
    }

    private void add(String label, Variable var) {
        var y = var.isConstant ? var.value : register.get(var.name);
        register.put(label, register.get(label) + y);
    }

    private void sub(String label, Variable var) {
        var y = var.isConstant ? var.value : register.get(var.name);
        var x = register.get(label);
        register.put(label, x - y);
    }

    private void mul(String label, Variable var) {
        var y = var.isConstant ? var.value : register.get(var.name);
        var x = register.get( label);
        register.put( label, x * y);
    }

    private void div(String label, Variable var) {
        var y = var.isConstant ? var.value : register.get(var.name);
        var x = register.get(label);
        register.put(label, x / y);
    }

    private Variable getVariable(String a) {
        try {
            var numericValue = Integer.parseInt(a);
            return new Variable(numericValue, true);
        }catch (NumberFormatException ex){
            return new Variable(a, false);
        }
    }

    private List<List<String>> parseRawLines(String input) {

        var rawLines = input.split("\n");
        var processedLines = new LinkedList<List<String>>();
        for (var line : rawLines) {
            var rawElements = line.trim().split(" ");
            var processedElements = new LinkedList<String>();
            for (String rawElement : rawElements) {

                if (line.trim().startsWith("msg")) {
                    var prcs = this.processMsgOutput(line);
                    processedLines.add(prcs);
                    break;
                }

                var current = rawElement.trim();
                if (current.startsWith(";")) {
                    break;
                }
                if (current.startsWith(",")) {
                    current = current.substring(1);
                }

                if (current.endsWith(",")) {
                    current = current.substring(0, current.length() - 1);
                }

                if (!current.isBlank() || !current.isEmpty()) {
                    processedElements.add(current);
                }
            }
            if (!processedElements.isEmpty()) {
                processedLines.add(processedElements);
            }
        }
        return processedLines;
    }

    private List<String> processMsgOutput(String line){
        var cmd = output;

        var rawParams = line.trim().substring(cmd.length());
        if (rawParams.contains(";")) {
            rawParams = rawParams.substring(0, rawParams.indexOf(";"));
        }

        var params = rawParams.trim();

        var isString = false;
        List<String> result1 = new LinkedList<>();
        result1.add(cmd);

        var current = new StringBuilder();

        for (var c: params.toCharArray()) {
            if (c == '\'') {
                current.append('\'');
                if (isString) {
                    result1.add(current.toString());
                    current = new StringBuilder();
                }
                isString = !isString;

            }else if (!isString && c != ',' && c != ' ') {
                result1.add(String.valueOf(c));
            }else if (isString) {
                current.append(c);
            }
        }
        return result1;
    }
}