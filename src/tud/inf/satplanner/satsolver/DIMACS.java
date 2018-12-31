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

	
public class DIMACS {

	//Number of Literals
	private int nr_literals;
	//Number of Clauses
	private int nr_clauses;
	
	//CNF
	private Vector<Clause> cnf;

	private final String endline = "\r\n";
	
	/**
	 * Default Constructor:
	 * @param nr_literals
	 * @param nr_clauses
	 */
	public DIMACS(int nr_literals, int nr_clauses) {
		
		this.nr_literals = nr_literals;
		this.nr_clauses  = nr_clauses;
		
		this.cnf = new Vector<Clause>(nr_clauses);
	}
	
	/**
	 * Add a Clause
	 * @param clause_
	 */
	public void addClause(Clause clause_){
		this.cnf.addElement(clause_);
//		this.nr_literals += clause_.get_literal_count();		
		
//		nr_clauses++;
	}

	/**
	 * Remove a Clause by reference.
	 * @param clause_
	 */
	public void removeClause(Clause clause_){
		this.cnf.remove(clause_);		

//		boolean remove_flag = this.cnf.remove(clause_);		
//		if(remove_flag) {
//			this.nr_literals -= clause_.get_literal_count();
//		}
//		nr_clauses--;
	}

	/**
	 * Remove a Clause by index.
	 * @param clause_
	 */	
	public void removeClause(int index){
		this.cnf.remove(index);
//		Clause clause_ = this.cnf.remove(index);
//		this.nr_literals -= clause_.get_literal_count();

//		nr_clauses--;
	}

	/**
	 * Checking Whether cluase contained by literal
	 * @param clause_
	 * @return
	 */
	public boolean containCluase(Clause clause_) {
		
		if(this.cnf.contains(clause_)){
			return true;
		}
		return false;
	}

	/**
	 * Setting CNF size
	 * @return
	 */
	public int sizeCNF() {
		return cnf.size();
	}

	public void set_nr_Literals(int nr_literals) {
		this.nr_literals =  nr_literals;
	}

	public int get_nr_Literals() {
		return this.nr_literals;
	}

	public void set_nr_Clauses(int nr_clauses) {
		this.nr_clauses =  nr_clauses;
	}

	public int get_nr_Clauses() {
		return this.nr_clauses;
	}

	/**
	 * Printing CNF in DIMACS Format
	 */
	public String toString() {
	
		StringBuffer str = new StringBuffer();
		
		str.append("p cnf ");
		str.append(this.nr_clauses);
		str.append(" ");
		str.append(this.nr_literals);
		str.append(endline);	
		
		for(Clause clause :this.cnf) {
			str.append(clause.toString());
			str.append(endline);
		}
		
		return str.toString();

	}
}
