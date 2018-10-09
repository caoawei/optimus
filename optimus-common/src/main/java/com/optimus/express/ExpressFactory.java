package com.optimus.express;

import com.optimus.collection.Stack;
import com.optimus.collection.impl.ArrayStack;

public class ExpressFactory {

    public static void main(String[] args) {

        String exp = "5 * (       2 + 3 )";

        String[] items = exp.split("\\s+");

        System.out.println(calc(items));
    }

    public static int calc(String[] items) {

        // 操作符栈
        Stack<Operator> operatorStack = new ArrayStack<>(2);

        // 操作数栈
        Stack<Integer> operandStack = new ArrayStack<>(2);

        for (int k = 0 ; k < items.length;k++) {
            String item = items[k];
            if("(".equals(item)) {
                String[] subs = new String[items.length - k - 1];
                System.arraycopy(items,k+1,subs,0,subs.length);

                // 需要处理索引(递归处理的直接跳过)
                // 建议的思路是模仿ByteBuffer的操作，也就说递归期间仍然操作原数组,并保留一个读索引位置
                // 需要对数组做封装,提供额外的操作
                operandStack.push(calc(subs));
            } else if(")".equals(item)) {
                continue;
            }

            if(!Operator.isOperator(item)) {
                operandStack.push(Integer.valueOf(item));
                continue;
            }

            // 处理是操作符

            // 当前操作符
            Operator currOp = Operator.mappingOperator(item);
            if(currOp == null) {
                throw new RuntimeException("表达式语法不正确>:操作符不合法:"+item);
            }

            // 上一步操作符
            Operator preOp = operatorStack.offer();

            // 当前是第一个操作符:直接入栈
            if(preOp == null) {
                operatorStack.push(currOp);
                continue;
            }

            // 当前操作符优先于上一步的操作符: 则按降序压入栈中
            if(currOp.isPriority(preOp)) {
                operatorStack.push(currOp);
            } else { // 优先级相同或低于上一步优先级

                // 需要计算在此之前表达式的值,并将结果压入操作数栈中
                cacExpress(operatorStack,operandStack);

                // 当前操作符压入栈顶
                operatorStack.push(currOp);
            }
        }

        cacExpress(operatorStack,operandStack);

        return operandStack.pop();
    }

    private static void cacExpress(Stack<Operator> operatorStack,Stack<Integer> operandStack) {
        Operator op = operatorStack.pop();

        while (op != null) {

            int size = op.getOperand();

            Integer[] oprand = new Integer[size];
            for (int i = 0;i < size;i++) {
                oprand[i] = operandStack.pop();
            }

            int val = cal(oprand,op);

            // 中间结果放入操作数栈顶
            operandStack.push(val);

            op = operatorStack.pop();

        }
    }

    private static int cal(Integer[] oprand, Operator op) {
        int rs = 0;
        switch (op) {
            case ADDITION:
                rs = Math.addExact(oprand[1],oprand[0]);
                break;
            case SUBTRACTION:
                rs = Math.subtractExact(oprand[1],oprand[0]);
                break;
            case MULTIPLICATION:
                rs = Math.multiplyExact(oprand[1],oprand[0]);
                break;
            case DIVISION:
                rs = Math.floorDiv(oprand[1],oprand[0]);
                break;
            case POWER:
                rs = Double.valueOf(Math.pow(oprand[1],oprand[0])).intValue();
                break;
        }

        return rs;
    }

}
