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

package tud.inf.satplanner.preprocessor;

import java.util.Stack;

public class UnitPropogation {
	
//	//false if empty cluase is found;
//	private boolean OK;
//	//stack of literal, appearing in some unit clauses
//	private Stack<Literal> UNITS;
//	//List of input clauses
//	private Vector<Clauses> CLAUSES;
//
//	public static void propogate_true_value(v: variable) {
//		c: clause;
//	
//		for c in caluses_of_neg_head(v) {
//			if OK then shorten_clause_from_head(c)
//		}
//		
//		for c in caluses_of_neg_tail(v) {
//			if OK then shorten_clause_from_tail(c)
//		}
//		
//	}
//	
//	public static void shorten_clause_from_head(c: clause) {
//		i: integer;
//		
//		for i from head_index(c) + 1 to tail_index(c) {
//			
//			L:literal  = ith_literal(c,i);
//			
//			if(truth_value(L) = unknown) {
//				if(tail_index(c) = i) {
//					push_stack(L,UNITS);
//				}
//			} else if(truth_value(L) = true) {
//				return;
//			}
//		}
//		OK = false;
//	}
//	
//	public void unit_progagation(){
//		
//		/*Initialization*/
//		OK = false;
//		UNITS = null;
//		
//		for c:clause in CLAUSES {
//			
//			if(head_index(c) = tail_index(c)) {
//				L:literal = ith_literal(c,head_index(i));
//				push_stack(L,UNITS);
//			}
//			
//		}
//		
//		/*main loop*/
//		while(OK && stack_empty(UNITS) == false) {
//			/*Handle Unit clauses*/
//			L:literal = stack_pop(UNITS);
//			
//			if(truth_value(L) == true) {
//				continue;
//			} else if(truth_value(L) == false) {
//				OK = false;
//			} else if(positive(L)) {
//				truth_value(L) = true;
//				propogate_truth_value(L);
//			} else {
//				truth_value(-L) = false;
//				propogate_false_value(-L);
//			}
//			
//		}
//		
//		if(OK) {
////			Satisfiable;
//		} else {
//			//Unstaisfiable
//		}
//	}
}
