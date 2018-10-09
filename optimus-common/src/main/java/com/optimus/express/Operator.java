package com.optimus.express;

public enum Operator {

    /**
     * 加法
     */
    ADDITION(2,0,"+"),

    /**
     * 减法
     */
    SUBTRACTION(2,0,"-"),

    /**
     * 乘法
     */
    MULTIPLICATION(2,1,"*"),

    /**
     * 除法
     */
    DIVISION(2,1,"/"),

    /**
     * 幂
     */
    POWER(2,2,"^")
    ;

    // 操作数个数
    int operand;

    // 操作符优先级
    int priority;

    // 操作符
    String op;

    Operator(int operand,int priority,String op) {
        this.operand = operand;
        this.priority = priority;
        this.op = op;
    }

    public int getOperand() {
        return operand;
    }

    public int getPriority() {
        return priority;
    }

    public String getOp() {
        return op;
    }

    public boolean isPriority(Operator op) {
        return this.priority > op.getPriority();
    }

    public static boolean isOperator(String op) {
        for (Operator opt : Operator.values()) {
            if(opt.getOp().equals(op)) {
                return true;
            }
        }

        return false;
    }

    public static Operator mappingOperator(String op) {
        for (Operator opt : Operator.values()) {
            if(opt.getOp().equals(op)) {
                return opt;
            }
        }

        return null;
    }
}
