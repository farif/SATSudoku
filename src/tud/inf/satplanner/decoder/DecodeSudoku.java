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

package tud.inf.satplanner.decoder;

import java.util.HashMap;

import tud.inf.satplanner.puzzle.Sudoku;
import tud.inf.satplanner.puzzle.SudokuCell;

public class DecodeSudoku {

	private int[][] sudoku_Grid;
	
	private HashMap<String, Integer> map;

	private SudokuCell key_cell;

	private int[] model;
	
	private int N;
	
	public DecodeSudoku(Sudoku sudoku_,int [] model){
		this.map = sudoku_.getEncoding_map();
		this.N = sudoku_.get_N();
		this.model = model;
		this.sudoku_Grid = sudoku_.get_Grid();
		
		this.key_cell = new SudokuCell(0,0,0,true);
	
	}
	
	public  void decodeModel() {

	      for(int i = 1; i <= N; i++)
	        {
	            for(int l1 = 1; l1 <= N; l1++)
	            {
		            for(int k = 1; k <= N; k++)
		            {
		            
						if(isMappedLiteral(i, l1, k)) {
	
		            		int symbol = getMappedValue(i,l1,k);
		            		
		            		
		            		for(int m: this.model) {
		            			
		            			if(m == symbol) {
		            			
		            				this.sudoku_Grid[(i-1)][(l1-1)] = k;
		            	
		            			} 
		            		}
						}
		            }

	            }		            			
	         }
	}

//	public void print_model () {
//		
//		for(int m: this.model) {
//			System.out.print(m + ", ");
//		}
//	}
	
	
    private int getMappedValue(int i, int j, int k)
    {
    	key_cell.setRow(i);
    	key_cell.setColumn(j);
    	key_cell.setValue(k);
     
    	String key = key_cell.toString();

    	int symbol = (int)this.map.get(key);

    	return symbol;
    
    }

	private boolean isMappedLiteral(int i, int j, int k) {

		key_cell.setRow(i);
		key_cell.setColumn(j);
		key_cell.setValue(k);

		String key = key_cell.toString();

		Integer symbol = (Integer) this.map.get(key);
		
		if(symbol == null) {
			return false;
		}

		return true;

	}
   


}
