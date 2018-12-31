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

package tud.inf.satplanner.parser;

import tud.inf.satplanner.puzzle.SudokuHeader;

public class PuzzleFormater {

	private static final String bar = "|";
	private static final String space = " ";

//	private static final String under_score = "_";
	
	private static final String endline = "\n";
	public static int[][] grid;
	public static SudokuHeader header;
	
	private static int grid_col = 0;
	private static int grid_row = 0;

	public static int N;
	
	public static int cal_k(int N) {

		int sqr_1 = N*N + 1;
		
		Double log_v = new Double(sqr_1);
		log_v = Math.log10(log_v);
		log_v = Math.ceil(log_v);
		int value_ = log_v.intValue();
		 
		value_ += 1;
		value_ *= N;
		value_ += 1;
	
		int k = value_;
		
//		this.k = k;
		
		return k;
	
	}

	public static int cal_p(int N) {

		int sqr_1 = N*N + 1;

//		Double log_v = new Double(sqr_1);
		double log10_ = Math.log10(sqr_1);
		double ceil_  = Math.ceil(log10_);
		
//		log_v = Math.log10(log_v);
//		log_v = Math.ceil(log_v);
//		int p_ = log_v.intValue();
		int p_ = (int) ceil_;
		 
		return p_;
//		return k;
	
	}

	public static int cal_q(int N,int x) {

		double sqr_1 = ((N*N) - x) + 1;
		
//		Double log_v = new Double(sqr_1);
		double log10_ = Math.log10(sqr_1);
		double ceil_  = Math.ceil(log10_);
//		
//		log_v = Math.log10(log_v);
//		log_v = Math.ceil(log_v);
		int q_1 = (int)ceil_ ;
		int q_   = q_1 - 1;
		 
		return q_;
//		return k;
	
	}


	public static int cal_q1(int N,int x) {

		double sqr_1 = (N*N);
		
//		Double log_v = new Double(sqr_1);
		double log10_ = Math.log10(sqr_1);
		double ceil_N  = Math.ceil(log10_);

		double log10_x = Math.log10(x) +  1;
		double floor_x  = Math.floor(log10_x);

//		
//		log_v = Math.log10(log_v);
//		log_v = Math.ceil(log_v);
		int q_1 = (int)(ceil_N - floor_x);
		
//		int q_   = q_1;
		 
		return q_1;
//		return k;
	
	}

	
	private static String DPart(){
		
		
		StringBuffer Dpart = new StringBuffer();
		
		Dpart.append("+");

		int k = cal_k(N);
		int counter_ = 0;
		
		while(counter_ < k) {
			Dpart.append("-");
			counter_++;
		}

		return Dpart.toString();
	}
	
	private static String DRow() {

		StringBuffer DRow = new StringBuffer();
		int counter_ = 0;

		while(counter_ < N) {
			DRow.append(DPart());
			counter_++;

		}
		
		DRow.append("+");
		
		return DRow.toString();
	}

	public static String NPart() {

		StringBuffer NPart = new StringBuffer();
		int counter_ = 0;
				
		NPart.append(bar);
		NPart.append(space);
		
		int value = grid[grid_row][grid_col];
		
		while(counter_ < N) {
			
			value = grid[grid_row][grid_col];
			
			if(value != 0) {
				
				int q = PuzzleFormater.cal_q1(N, value);
				
				int space_counter = 0;
			
				while(space_counter < q ) {
					NPart.append(space);
					space_counter++;
				}
				
				NPart.append(value);
				grid_col++;
				
			} else {	

				int q = PuzzleFormater.cal_q(N, value);
				
				int space_counter = 0;
			
				while(space_counter < q ) {
					NPart.append(space);
					space_counter++;
				}
				
				NPart.append(value);
				grid_col++;
				
			}
//
//				int p = PuzzleFormater.cal_p(N);
//				int under_score_counter = 0;
//
//				while(under_score_counter < p ) {
//					NPart.append(under_score);
//
//					under_score_counter++;
//				}
//			}	

			NPart.append(space);
	
			counter_++;
			
			
		}
		
		return NPart.toString();
		
	}
	
	
	public static String NRow() {

		StringBuffer NRow = new StringBuffer();
		int counter_ = 0;
		
		grid_col = 0;
		
		while(counter_ < N) {
			NRow.append(NPart());
			counter_++;
			
		}
		
		NRow.append("|");
		
		return NRow.toString();
	}
	
	public static String puzzle() {
		
		StringBuffer puzzle = new StringBuffer();
		
		int row_ = 0;
		
		grid_row = 0;
		
		puzzle.append(header);
		
		while(row_ < N) {

			puzzle.append(DRow());
			puzzle.append(endline);

			int column_ = 0;

			while(column_ < N) {
				
				puzzle.append(NRow());
			
				puzzle.append(endline);
				column_++;
				
				grid_row++;
			}//end column

			row_++;				

		}//end row			
		
		
		puzzle.append(DRow());

		return puzzle.toString();
	}
	
	
}
