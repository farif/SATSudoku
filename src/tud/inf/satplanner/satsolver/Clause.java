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

import java.util.Vector;


public class Clause {
	
	//Clause for CNF
	private Vector<Literal> clause_;
	
	/**
	 * Default Constructor
	 */
	public Clause() {
		this.clause_ = new Vector<Literal>();
//		this.nr_literals = 0;
	}

	/**
	 * Add Literal in Clause
	 * @param literal_
	 */
	public void addLiteral(Literal literal_) {
		this.clause_.addElement(literal_);
	}
	
	/**
	 * Removing Literal form Clause by Ref.
	 * @param literal_
	 */
	public void removeLiteral(Literal literal_) {
		this.clause_.remove(literal_);
	}
	
	/**
	 * Removing Literal form Clause by index.
	 * @param literal_
	 */
	public void removeLiteral(int index) {
		this.clause_.remove(index);
	}
	
	/**
	 * Contains clause contains the literal
	 * @param literal_
	 * @return
	 */
	public boolean containLiteral(Literal literal_) {
		
		if(this.clause_.contains(literal_)) {
			return true;
		}
		return false;
	}

	public int get_literal_count() {
		return this.clause_.size();
	}
	/**
	 * Defining Printing Format
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		
		for(Literal lit :this.clause_) {
			str.append(lit.toString());
			str.append(" ");
		}
		str.append("0");
		
		return str.toString();
	}
}
