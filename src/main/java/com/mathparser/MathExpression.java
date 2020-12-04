package com.mathparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class MathExpression{

	private String expressionString;
	private String[][] tokens;

	public MathExpression(String expression){
		expressionString=expression;
		tokens=tokenize(expressionString);
	}

	public double evaluate(){
		String[][] tokensCopy=deepCopyTokens(tokens);
		for(int i=0; i<tokensCopy[0].length; i++){
			if(tokensCopy[1][i].equals("GROUP_LEFT")){
				int ignoreNum=0;
				for(int j=i+1; j<tokensCopy[0].length; j++){
					if(tokensCopy[1][j].equals("GROUP_LEFT")){
						ignoreNum++;
					}else if(tokensCopy[1][j].equals("GROUP_RIGHT")){
						if(ignoreNum>0){
							ignoreNum--;
						}else{
							double score=evaluate(new String[][]{Arrays.copyOfRange(tokens[0], i+1, j),
									Arrays.copyOfRange(tokens[1], i+1, j)});
							String[] newLexemes=Stream.concat(
									Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i)),
											Arrays.stream(new String[]{String.valueOf(score)})),
									Arrays.stream(Arrays.copyOfRange(tokensCopy[0], j+1, tokensCopy[0].length))
							).toArray(String[]::new);
							String[] newTokens=Stream.concat(
									Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i)),
											Arrays.stream(new String[]{"NUMBER"})),
									Arrays.stream(Arrays.copyOfRange(tokensCopy[1], j+1, tokensCopy[0].length))
							).toArray(String[]::new);
							tokensCopy=new String[][]{newLexemes, newTokens};
						}
					}
				}
			}
		}
		for(int i=0; i<tokensCopy[0].length; i++){
			if(tokensCopy[0][i].equals("^")){
				double score=Math.pow(Double.valueOf(tokensCopy[0][i-1]), Double.valueOf(tokensCopy[0][i+1]));
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}
		}
		for(int i=0; i<tokensCopy[0].length; i++){
			if(tokensCopy[0][i].equals("*")){
				double score=Double.valueOf(tokensCopy[0][i-1])*Double.valueOf(tokensCopy[0][i+1]);
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}else if(tokensCopy[0][i].equals("/")){
				double score=Double.valueOf(tokensCopy[0][i-1])/Double.valueOf(tokensCopy[0][i+1]);
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}
		}
		for(int i=0; i<tokensCopy[0].length; i++){
			if(tokensCopy[0][i].equals("+")){
				double score=Double.valueOf(tokensCopy[0][i-1])+Double.valueOf(tokensCopy[0][i+1]);
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}else if(tokensCopy[0][i].equals("-")){
				double score=Double.valueOf(tokensCopy[0][i-1])-Double.valueOf(tokensCopy[0][i+1]);
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}
		}
		return Double.valueOf(tokensCopy[0][0]);
	}

	public double evaluate(String[][] tokens){
		String[][] tokensCopy=deepCopyTokens(tokens);
		for(int i=0; i<tokensCopy[0].length; i++){
			if(tokensCopy[1][i].equals("GROUP_LEFT")){
				int ignoreNum=0;
				for(int j=i; j<tokensCopy[0].length; j++){
					if(tokensCopy[1][j].equals("GROUP_LEFT")){
						ignoreNum++;
					}else if(tokensCopy[1][j].equals("GROUP_RIGHT")){
						if(ignoreNum>0){
							ignoreNum--;
						}else{
							evaluate(Arrays.copyOfRange(tokens, i+1, j));
						}
					}
				}
			}
		}
		for(int i=0; i<tokensCopy[0].length; i++){
			if(tokensCopy[0][i].equals("^")){
				double score=Math.pow(Double.valueOf(tokensCopy[0][i-1]), Double.valueOf(tokensCopy[0][i+1]));
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}
		}
		for(int i=0; i<tokensCopy[0].length; i++){
			if(tokensCopy[0][i].equals("*")){
				double score=Double.valueOf(tokensCopy[0][i-1])*Double.valueOf(tokensCopy[0][i+1]);
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}else if(tokensCopy[0][i].equals("/")){
				double score=Double.valueOf(tokensCopy[0][i-1])/Double.valueOf(tokensCopy[0][i+1]);
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}
		}
		for(int i=0; i<tokensCopy[0].length; i++){
			if(tokensCopy[0][i].equals("+")){
				double score=Double.valueOf(tokensCopy[0][i-1])+Double.valueOf(tokensCopy[0][i+1]);
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}else if(tokensCopy[0][i].equals("-")){
				double score=Double.valueOf(tokensCopy[0][i-1])-Double.valueOf(tokensCopy[0][i+1]);
				String[] newLexemes=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[0], 0, i-1)),
								Arrays.stream(new String[]{String.valueOf(score)})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[0], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				String[] newTokens=Stream.concat(
						Stream.concat(Arrays.stream(Arrays.copyOfRange(tokensCopy[1], 0, i-1)),
								Arrays.stream(new String[]{"NUMBER"})),
						Arrays.stream(Arrays.copyOfRange(tokensCopy[1], i+2, tokensCopy[0].length))
				).toArray(String[]::new);
				tokensCopy=new String[][]{newLexemes, newTokens};
			}
		}
		return Double.valueOf(tokensCopy[0][0]);
	}

	private String[][] tokenize(String expressionString){
		if(expressionString.length()==0){
			return new String[2][0];
		}
		String[] splitExpression=expressionString.split("");
		ArrayList<String> lexemes=new ArrayList<>();
		ArrayList<String> tokens=new ArrayList<>();
		String currToken=getTokenType(splitExpression[0]);
		StringBuilder prevLetters=new StringBuilder(splitExpression[0]);
		for(int i=1; i<splitExpression.length; i++){
			if(!getTokenType(splitExpression[i]).equals(currToken)){
				lexemes.add(String.valueOf(prevLetters));
				prevLetters=new StringBuilder();
				tokens.add(currToken);
				currToken=getTokenType(splitExpression[i]);
			}
			prevLetters.append(splitExpression[i]);
		}
		lexemes.add(String.valueOf(prevLetters));
		tokens.add(currToken);
		return new String[][]{lexemes.toArray(new String[0]), tokens.toArray(new String[0])};
	}

	public String getTokenType(String character){
		if(Character.isDigit(character.charAt(0))){
			return "NUMBER";
		}else if(character.matches("[+\\-*/^]")){
			return "OPERATOR";
		}else if(character.matches("[(\\[]")){
			return "GROUP_LEFT";
		}else if(character.matches("[)\\]]")){
			return "GROUP_RIGHT";
		}
		throw new IllegalArgumentException(String.format("Illegal character: %s", character));
	}

	private String[][] deepCopyTokens(String[][] tokens){
		String[][] newTokens=new String[tokens.length][tokens[0].length];
		for(int i=0; i<tokens.length; i++){
			for(int j=0; j<tokens[i].length; j++){
				newTokens[i][j]=tokens[i][j];
			}
		}
		return newTokens;
	}

	private String[][] removeTokenSection(String[][] tokens, int start, int end){
		String[] newLexemes=Stream.concat(Arrays.stream(Arrays.copyOfRange(tokens[0], 0, start)),
				Arrays.stream(Arrays.copyOfRange(tokens[0], end, tokens[0].length))).toArray(String[]::new);
		String[] newTokens=Stream.concat(Arrays.stream(Arrays.copyOfRange(tokens[1], 0, start)),
				Arrays.stream(Arrays.copyOfRange(tokens[1], end, tokens[1].length))).toArray(String[]::new);
		return new String[][]{newLexemes, newTokens};
	}

	@Override
	public int hashCode(){
		return expressionString.hashCode();
	}

	@Override
	public boolean equals(Object obj){
		return expressionString.equals(obj);
	}

	@Override
	public String toString(){
		return expressionString;
	}

	public String getExpressionString(){
		return expressionString;
	}

	public String[][] getTokens(){
		return tokens;
	}
}
