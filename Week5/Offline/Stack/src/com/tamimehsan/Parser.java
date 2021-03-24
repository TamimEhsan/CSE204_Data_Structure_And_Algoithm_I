package com.tamimehsan;

public class Parser {
    private String equation;
    private Stack<Integer> numbers;
    private Stack<Character> operators;
    private boolean isValid = false;

    public Parser(String equation) {
        equation = equation.replaceAll("\\s", "");
        equation = "("+equation+")";
        this.equation = equation;
        numbers = new Stack<Integer>();
        operators = new Stack<Character>();
    }

    public boolean mayBeValid() {
        return isValid = checkRBS() && checkSyntax();
    }

    private boolean checkRBS() {
        int brackets = 0;
        for (int i = 0; i < equation.length(); i++) {
            char c = equation.charAt(i);
            if (c == '(') {
                brackets++;
            } else if (c == ')') {
                if (brackets == 0) return false;
                brackets--;
            }
        }
        return brackets == 0;
    }

    public void parse() throws ArithmeticException{
        if (!isValid) return;
        int sz = equation.length();
        Stack<Float> numberStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();
        float number = 0;
        for (int i = 0; i < sz; i++) {
            char ch = equation.charAt(i);
            if (ch == '(') {
                operatorStack.push(ch);
            } else if (isDigit(ch)) {
                number = 0;
                /*
                while (i < sz && isDigit(equation.charAt(i))) {
                    int digit = equation.charAt(i) - '0';
                    number = number * 10 + digit;
                    i++;
                }*/
                boolean decimal = false;
                float mult = 1;
                while (i < sz && isDigit(equation.charAt(i))) {
                    ch = equation.charAt(i);
                    if( ch == '.' && decimal){
                        System.out.println("Not valid");
                        return;
                    } else if( ch == '.' ){
                        decimal = true;
                    } else if(decimal){
                        int digit = ch - '0';
                        mult = mult/10;
                        number +=  digit*mult;
                    }else{
                        int digit = ch - '0';
                        number = number * 10 + digit;
                    }
                    i++;
                }
                i--;
               // System.out.println(number);
                numberStack.push(number);
            } else if (isOperator(ch)) {
                if (ch == '-' && !operatorStack.empty() && equation.charAt(i - 1) == '(') {
                    numberStack.push((float) 0);
                    operatorStack.push('u');
                } else {
                    if (!operatorStack.empty() && operatorStack.top() == 'u') {
                        System.out.println("Not valid");
                        return;
                    }
                    while (!operatorStack.empty() && operatorStack.top() != '(' && preceeds(operatorStack.top(), ch)) {
                        //  System.out.println("preceds");
                        float a = 0, b = 0;
                        if (!numberStack.empty()) {
                            a = numberStack.top();
                            numberStack.pop();
                        }
                        if (!numberStack.empty()) {
                            b = numberStack.top();
                            numberStack.pop();
                        }
                        char c = operatorStack.top();
                        operatorStack.pop();
                        float value = evaluate(b, a, c);
                        numberStack.push(value);
                        // System.out.println("debig "+b+" "+c+" "+a+" ="+value);
                    }
                    operatorStack.push(ch);
                }

                // System.out.println(ch);
            } else {
                while (!operatorStack.empty() && operatorStack.top() != '(') {
                    float a = 0, b = 0;
                    if (!numberStack.empty()) {
                        a = numberStack.top();
                        numberStack.pop();
                    }

                    if (!numberStack.empty()) {
                        b = numberStack.top();
                        numberStack.pop();
                    } 
                    char c = operatorStack.top();
                    operatorStack.pop();
                    float value = evaluate(b, a, c);
                    //  System.out.println("debig "+b+" "+c+" "+a+" ="+value);
                    numberStack.push(value);
                }
                operatorStack.pop();
            }

        }
        while (!operatorStack.empty()) {
            System.out.println("fsdfsdfsd===============================");
            float a = 0, b = 0;
            if (!numberStack.empty()) {
                a = numberStack.top();
                numberStack.pop();
            }
            if (!numberStack.empty()) {
                b = numberStack.top();
                numberStack.pop();
            }
            char c = operatorStack.top();
            operatorStack.pop();
            float value = evaluate(b, a, c);
           // System.out.println("debig "+b+" "+c+" "+a+" ="+value);
            numberStack.push(value);
        }
        if (!numberStack.empty()) number = numberStack.top();
      //  if( number == Float.POSITIVE_INFINITY || number == Float.NEGATIVE_INFINITY )
        if ((int) number == number) System.out.println("Valid expression, Computed value:    " + (int) number);
        else System.out.println("Valid expression, Computed value:    " + number);

    }

    private boolean checkSyntax() {
        if (equation.length() == 0) return false;
        if (isOperator(equation.charAt(0))) return false;
        if (!isOperator(equation.charAt(0)) && !isParenthesis(equation.charAt(0)) && !isDigit(equation.charAt(0)))
            return false; // case: #&%
        for (int i = 1; i < equation.length(); i++) {
            char c = equation.charAt(i);
            char b = equation.charAt(i - 1);
            if (!isOperator(c) && !isParenthesis(c) && !isDigit(c)) return false; // case: #&%
            if (isOperator(b) && c == ')') return false;                            // case: +)
            if (b == '(' && isOperator(c) && c != '-') return false;                 // case: (*
            if (isOperator(b) && isOperator(c)) return false;                      // case: *-
            if (isParenthesis(b) && isParenthesis(c) && b != c) return false;       // case: () )(
        }
        if (isOperator(equation.charAt(equation.length() - 1))) return false;         // case: +100-1  or 100+5/

        return true;
    }

    private boolean isDigit(char c) {
        return ( c > 47 && c < 58 ) || c == '.';
    }

    private boolean isParenthesis(char c) {
        return c == '(' || c == ')';
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private float evaluate(float a, float b, char c) throws ArithmeticException {
        switch (c) {
            case '+':
                return a + b;
            case '-':
            case 'u':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if( b == 0 ) throw new ArithmeticException();
                return a / b;
            default:
                return 0;
        }
    }

    private boolean preceeds(char a, char b) {
        if ( (a == '+' || a == '-') && (b == '*' || b == '/') ) return false;
        return true;
    }
}
