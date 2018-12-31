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

package tud.inf.satplanner.encoder;

import java.util.HashMap;

import org.sat4j.specs.ISolver;

import tud.inf.satplanner.puzzle.Sudoku;
import tud.inf.satplanner.puzzle.SudokuCell;
import tud.inf.satplanner.satsolver.DIMACS;

public abstract class EncodingScheme {

	//Sudoku Puzzle.
	protected Sudoku sudoku_;
	//Sudoku Grid.
	protected int[][] sudoku_Grid;
	
	//Hash mapping for SAT literals.
	protected HashMap<String, Integer> map;

	//Default Key Cell.
	protected SudokuCell key_cell = new SudokuCell(0, 0, 0, true);

	//Output DIMACS Format.
	protected DIMACS dimacs;


	//1. Extended Encoding Scheme;
	//2. Efficient Encoding Scheme;
    //3. Minimal Encoding Scheme;
	//4. Compact Encoding Scheme;  (Extended) + (Subtracted)

	/**
	 * Encoding Sudoku Puzzle 
	 * output: DIAMCS for SAT Solver for I/O.
	 */
	public abstract void encodeDIMACS();

	public abstract DIMACS getDIMACS();

	/**
	 * Encoding Sudoku Puzzle 
	 * output: SAT Solver added clauses & literals during running
	 */
	public abstract void encodeOnFly(ISolver solver) throws Exception;

}