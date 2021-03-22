package com.tamimehsan;



public class Parser {
    private String equation;
    private Stack<Integer> numbers;
    private Stack<Character> operators;
    private boolean isValid = false;
    public Parser(String equation){
        this.equation = equation;
        numbers = new Stack<Integer>();
        operators = new Stack<Character>();
    }

    public boolean isValid(){
        return isValid = checkRBS() && checkSyntax();
    }

    private boolean checkRBS(){
        int brackets = 0;
        for( int i=0;i<equation.length();i++ ){
            char c = equation.charAt(i);
            if( c == '(' ){
                brackets++;
            }else if( c == ')' ){
                if( brackets == 0 ) return false;
                brackets--;
            }
        }
        return brackets == 0;
    }
    public void parse(){
        if(!isValid) return;
        int sz = equation.length();
        Stack<Integer> integerStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        int number = 0;
        for(int i=0;i<sz;i++){
            char ch = equation.charAt(i);
            if( ch == '('){
                operatorStack.push(ch);
                continue;
            }
            if( isDigit(ch) ){
                int digit = ch-'0';
                number = number*10+digit;
                continue;
            }
            if(number!=0) {
                integerStack.push(number);
               // System.out.println(number);
            }
            number = 0;

            if( isOperator(ch) ){
                if( ch == '-' && !operatorStack.empty() && operatorStack.top() == '(' ) integerStack.push(0);
                while ( !operatorStack.empty() && operatorStack.top()!='(' && preceeds(operatorStack.top(),ch) ){
                    int a = integerStack.top();
                    integerStack.pop();
                    int b = integerStack.top();
                    integerStack.pop();
                    char c = operatorStack.top();
                    operatorStack.pop();
                    int value = evaluate(b,a,c);
                    integerStack.push(value);
                }
                operatorStack.push(ch);
               // System.out.println(ch);
                continue;
            }
            while(!operatorStack.empty() && operatorStack.top()!='('){
                int a = integerStack.top();
                integerStack.pop();
                int b = integerStack.top();
                integerStack.pop();
                char c = operatorStack.top();
                operatorStack.pop();
                int value = evaluate(b,a,c);
               // System.out.println("debig "+b+" "+c+" "+a+" ="+value);
                integerStack.push(value);
            }
            operatorStack.pop();
        }
        while(!operatorStack.empty()){
            int a = integerStack.top();
            integerStack.pop();
            int b = integerStack.top();
            integerStack.pop();
            char c = operatorStack.top();
            operatorStack.pop();
            int value = evaluate(b,a,c);
            integerStack.push(value);
        }
        System.out.println("Computed value:    "+integerStack.top());
    }
    private boolean checkSyntax(){
        if( equation.length()>0 && isOperator(equation.charAt(0)) ) return false;
        if( equation.length()>0 && !isOperator(equation.charAt(0)) && !isParenthesis(equation.charAt(0)) && !isDigit(equation.charAt(0)) ) return false; // case: #&%
        for( int i=1;i<equation.length();i++ ){
            char c = equation.charAt(i);
            char b = equation.charAt(i-1);
            if( !isOperator(c) && !isParenthesis(c) && !isDigit(c) ) return false; // case: #&%
            if( isOperator(b) && c ==')' ) return false;                            // case: +)
            if( b == '(' && isOperator(c) && c!='-' ) return false;                 // case: (*
            if( isOperator(b) && isOperator(c) ) return false;                      // case: *-
          //  if( isParenthesis(b) && isParenthesis(c) && b!=c ) return false       // case: () )(
        }
        if( isOperator(equation.charAt(equation.length()-1))) return false;         // case: +100-1  or 100+5/
        return true;
    }

    private boolean isDigit(char c){
         return c>47 && c<58;
    }
    private boolean isParenthesis(char c){
        return c == '(' || c == ')';
    }
    private boolean isOperator(char c){
        return c == '+' || c == '-' || c == '*' || c == '/';
    }
    private int evaluate(int a,int b,char c){
        switch (c){
            case '+': return a+b;
            case '-': return a-b;
            case '*': return a*b;
            case '/': return a/b;
            default: return 0;
        }
    }
    private boolean preceeds(char a,char b){
        if( ( a == '*' || a == '/' ) && ( b == '+' || b == '-' ) ) return true;
        return false;
    }
}
