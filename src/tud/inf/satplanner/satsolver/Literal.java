/**********************************************************************
* SudokuPlanner: A Sudoku SAT Planner for Java Copyright (C) 2008-2009 
*
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the TU-Dresden v1.0
* which accompanies this distribution, and is available at
* http://inf.tu-dresden.de
*
* Based on the original MiniSat specification from:
* 
* SAT4J for solver.
* 
* Encoding described in Paper; 
* Gihwon Kwon, Himanshu Jain,"Optimized CNF Encoding for Sudoku Puzzles"
* In 13th International Conference on Logic for Programming 
* Artificial Intelligence and Reasoning (LPAR 2006).
*******************************************************************************/

package tud.inf.satplanner.satsolver;

public class Literal {
	
	//Alphabet signature
	private Integer symbol;
	
	//Sign -L(false or L(true)
	private boolean signed = false;
	
	/**
	 * Default Literal Constructor.
	 * @param symbol
	 */
	public Literal(Integer symbol) {
		this.symbol =  symbol;
	}
	
	/**
	 * Define singned Literal
	 * @param symbol
	 * @param signed
	 */
	public Literal(Integer symbol, boolean signed){
		this.symbol =  symbol;
		this.signed = signed;
	}

	/*
	 * Getting Symbol
	 */
	public Integer getSymbol() {
		return this.symbol;
	}

	/**
	 * Checking for +ve/-ve Literal
	 * @return
	 */
	public boolean isSigned() {
		return signed;
	}

	/**
	 * Changing Sing of Literal
	 * @param signed
	 */
	public void setSigned(boolean signed) {
		this.signed = signed;
	}

	
	/**
	 * Printing Format
	 */
	public String toString(){
		
		StringBuffer str = new StringBuffer();
		
		if(this.signed) {
			str.append("-" + this.symbol);
		}
		else {
			str.append(this.symbol);
		}
			
		return str.toString();
	}

}
