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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import tud.inf.satplanner.io.FileInput;
import tud.inf.satplanner.puzzle.Sudoku;
import tud.inf.satplanner.puzzle.SudokuHeader;

public class ParseSudokuPuzzle {

	//Input Reader
	private BufferedReader bufferedreader;
	//Cells Grid
	private int[][] grid;
	//Puzzle Header Information
	private SudokuHeader header;
	
	//Sudoku Puzzle contains info: (Cell Grid, Encoded Mappings)
	private Sudoku sudoku_;
	//Rows in input File;	
	private String DRow;
    
	//Logging Information
    private static final Logger logger = Logger.getLogger(FileInput.class.getName());

	
    
    /**
	 * Default Constructor:
	 * @param fileReader
	 * @param sudoku_
	 */
	public ParseSudokuPuzzle(FileReader fileReader, Sudoku sudoku_){
		
		this.bufferedreader = new BufferedReader(fileReader);
	
		this.sudoku_ = sudoku_;
		this.header = sudoku_.getHeader_info();
	}
	
	
	
	/**
	 * Parsing Sudoku accordingly:
	 * Input Specifications ABNF specified in 
	 * grammar/specification.txt or 
	 * grammar/grammar.pdf
	 */
	public void parse(){
		
		parseHeader1();
		parseHeader2();
		parseHeader3();
		parseHeader4();
		
		//Initializing Puzzle Size;
		this.sudoku_.set_M(this.header.get_puzzle_size());
		//Initializing Grid
		this.grid = sudoku_.get_Grid();
		//Parsing Puzzle Gird
		parsePuzzle();
		
	}
	
	
	
	
	/**
	 * Parsing Header1 and filling in:
	 * Experiments Information
	 */
	private void parseHeader1() {
		
		try{
			
			String header1 = this.bufferedreader.readLine();
			StringTokenizer tokenizer = new StringTokenizer(header1);
			
			if(tokenizer.hasMoreTokens()){
				
				if(this.header.getExperiment().equals(tokenizer.nextToken())) {
				
					if(tokenizer.hasMoreTokens()) {		
						String token_ = tokenizer.nextToken();
						this.header.setExperiment_value(token_);
					} else {
						//Error Format File
					}
				}
			} else {
				//Error Format File
			}
		
		}catch(IOException io_exp){
	    	 logger.log(Level.SEVERE, "Expcetion in Header1: " + io_exp.getMessage());
		}
	}

	/**
	 * Parsing Header2 Information:
	 * Filling in Number of tasks
	 */
	private void parseHeader2(){

		try{
			
			String header3 = this.bufferedreader.readLine();
			StringTokenizer tokenizer = new StringTokenizer(header3);
			int count_ = 0;
			
			while(tokenizer.hasMoreTokens()) {
				
				String token_ = tokenizer.nextToken();
				
				if(count_ == 3) {					
					int value_ = Integer.parseInt(token_);
					this.header.setNumber_of_tasks_value(value_);
				}
				
				count_++;
				
			}//end while
				
		
		}catch(IOException io_exp){
	    	 logger.log(Level.SEVERE, "Exception in parsing Header2: " + io_exp.getMessage());
		}

	}
	
	/**
	 * Parsing Header3 in Puzzle:
	 * Filling task value.
	 */
	private void parseHeader3(){

		try{
			
			String header2 = this.bufferedreader.readLine();
			StringTokenizer tokenizer = new StringTokenizer(header2);

			if(tokenizer.hasMoreTokens()){
				if(this.header.getTask().equals(tokenizer.nextToken())) {
				
					if(tokenizer.hasMoreTokens()) {
						
						String token_ = tokenizer.nextToken();
						int value_ = Integer.parseInt(token_);
						this.header.setTask_value(value_);
					
					} else {
						//Error Format File
					}
				}
			} else {
				//Error Format File
			}
		
		}catch(IOException io_exp){
	    	 logger.log(Level.SEVERE, "Exception in parsing Header3: " + io_exp.getMessage());
		}
		
	}
	
	
	
	/**
	 * Parsing Header4 Puzzle:
	 * filling size of puzzle NxN
	 */
	private void parseHeader4(){

		try{
			
			String header2 = this.bufferedreader.readLine();
			StringTokenizer tokenizer = new StringTokenizer(header2,"' ','x'");
			
			int count_ = 0;
			
			while(tokenizer.hasMoreTokens()) {

				String token_ = tokenizer.nextToken();

				if(count_ == 2) {
					int value_ = Integer.parseInt(token_);
					this.header.setPuzzle_size_value(value_);
				}
				
				count_++;
				
			}//end while
		
		}catch(IOException io_exp){
	    	 logger.log(Level.SEVERE, "Exception in parsing Header4: " + io_exp.getMessage());
		}

	}
	
	/**
	 * Parsing Puzzle Grid:
	 * Filling in Grid of NxN size with values
	 * 
	 */
	private void parsePuzzle(){

		try {
			
			String puzzle = this.bufferedreader.readLine();	
//			SudokuCell cell = null;
			
			for(int row = 0; puzzle != null; row++) {
				
	            int column_ = 0;
	            StringTokenizer tokenizer = new StringTokenizer(puzzle, "' ','+','-','_','|'",true);
			
				while(tokenizer.hasMoreTokens()) {
	
					String token_ = tokenizer.nextToken();
	
					if(token_.equals("+")) {
						this.DRow = puzzle;
						row--;
						//skip
						break;						
					} else if(token_.equals("|")) {
						//skip
						continue;
					} else if(token_.equals(" ")) {
						
						int q = PuzzleFormater.cal_q(this.sudoku_.get_M(),this.sudoku_.get_N());
						int space_counter = 0;
						
						while(space_counter < q && tokenizer.hasMoreTokens()  ) {
							tokenizer.nextToken();
							space_counter++;
						}
						
					} else if(token_.equals("_")) {
						
						int p = PuzzleFormater.cal_p(this.sudoku_.get_M());
						int under_score_counter = 1;

//						cell = new SudokuCell(row, column_, 0 ,true);
			            grid[row ][column_] =  0;

						column_++;

						
						while(under_score_counter < p && tokenizer.hasMoreTokens()  ) {
							tokenizer.nextToken();
							under_score_counter++;
						}
					} else { //set value
							
						 int  value_ = Integer.parseInt(token_);
	
//						 cell = new SudokuCell(row, column_,value_ ,true);
			             grid[row ][column_] =  value_ ;

			             column_++;		
					}
					
					
				}//end while for column;

				puzzle = this.bufferedreader.readLine();
			
			}//end for row;

			
		}catch(IOException io_exp){
	    	 logger.log(Level.SEVERE, "Exception in parsing Puzzle Grid: " + io_exp.getMessage());
		}

	}

	
	
	/**
	 * Number of Rows in Puzzle Parsed
	 * @return
	 */
	public String getDpart() {
		return this.DRow;
	}
}
