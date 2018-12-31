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

package tud.inf.satplanner.puzzle;

import java.util.HashMap;

public class Sudoku {
	
	// Hashmap Contains all the encodings;
	// e.g: 123 by 1, -123 by -1.
	private HashMap<String, Integer> encoding_map;
	
	// Actual grid contains the value [r][c] = v;
	// e.g: [1][4] = 3
	private int [][] sudoku_Grid;
	
	// Size of Sudoku n x n
	// e.g: 9x9 - n = square(m)
	private int n;

	// Block size Sudoku m x m
	// e.g: 3x3 - m = sqrt(n)
	private int m;
	
	
	//Header Information;
	//e.g:-
	//experiment: 2x2
	//number of tasks: 10
	//task: 1
	//puzzle size: 2x2
	private SudokuHeader header_info;
	
	private final String endline = "\r\n";

//	//Logging Information
//    private static final Logger logger = Logger.getLogger(FileInput.class.getName());

    
    /**
     * Default Constructor;
     * @param header_
     */
	public Sudoku(SudokuHeader header_){
		
		this.m  = header_.get_puzzle_size();
		this.n =  m * m;
		
		this.encoding_map =  new HashMap<String, Integer>();		
		this.header_info =  header_;
		
	}

	
	/**
	 * Encoding Map;
	 * @return
	 */
	public HashMap<String, Integer> getEncoding_map() {
		return encoding_map;
	}

	
	/**
	 * Setting Puzzle Size: MxM
	 * Setting Grid Size: sqaure(M) => N
	 * @param m
	 */
	public void set_M(int m){
		this.m = m;
		set_N();
		
		this.sudoku_Grid  =  new int[n][n];

	}

	
	/**
	 * Setting size of N
	 * 
	 */
	private void set_N(){
		this.n = m * m;
	}
	

	/**
	 * Getting size of Puzzle
	 * @return
	 */
	public int get_N() {
		return n;
	}

	/**
	 * Getting format size of Puzzle
	 * @return
	 */
	public int get_M() {
		return m;
	}


	/**
	 * Getting Header Info.
	 * @return
	 */
	public SudokuHeader getHeader_info() {
		return header_info;
	}

	/**
	 * Getting Puzzle Grid
	 * @return
	 */
	public int[][] get_Grid() {
		return this.sudoku_Grid;
	}

	/**
	 * Setting Puzzle Header
	 * @param header_info
	 */
	public void setHeader_info(SudokuHeader header_info) {
		this.header_info = header_info;
	}
	
	/**
	 * Puzzle printing;
	 */
	public String toString(){
		
		StringBuffer str = new StringBuffer();
		
		str.append(this.header_info.toString());
		
		for(int row = 0; row < this.n; row++) {
		
			for(int col= 0; col < this.n; col++) {
				str.append("[");
				str.append(row);
				str.append(",");
				str.append(col);
				str.append("]");
				str.append("=");				
				str.append(this.sudoku_Grid[row][col]);							
			}
			
			str.append(endline);
		}
		
		str.append(endline);
		
		return str.toString();
	}
	
}
