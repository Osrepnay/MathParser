package com.mathparser;

import java.util.Arrays;
import java.util.Scanner;

public class ExampleCalculator{
	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		String inputString=in.nextLine();
		while(!inputString.isBlank()){
			System.out.println(new MathExpression(inputString).evaluate());
			inputString=in.nextLine();
		}
		in.close();
	}
}
