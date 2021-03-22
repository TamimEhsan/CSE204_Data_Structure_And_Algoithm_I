package com.tamimehsan;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String equation1 = "(9*3-(7*8+((-4)/2)))";
        String equation2 = "(9*3-(7*8+((-4/2)))";
        String equation3 = "(9*3-(7*8+((4/2)))";
        Parser parser = new Parser(equation1);
        if(parser.isValid() ){
            System.out.print("Valid expression, ");
            parser.parse();
        } else {
            System.out.println("Not Valid");
        }
    }
}
