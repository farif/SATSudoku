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

public class SudokuCell {
	
	//Row Index.
	private int row;
	//Column Index.
	private int column;
	//Value in Cell.
	private int value;
	
	//Mapping to Boolean (True(=)/False(!=))
	private boolean status;
	
	/**
	 * Default Constructor
	 * @param row
	 * @param column
	 * @param value
	 */
	public SudokuCell(int row,int column,int value) {
		
		this.row = row;
		this.column = column;
		this.value = value;
		
		this.status = false;
	}

	/**
	 * Constructor with Status.
	 * @param row
	 * @param column
	 * @param value
	 * @param status
	 */
	public SudokuCell(int row,int column,int value, boolean status) {
		this.row = row;
		this.column = column;
		this.value = value;
		this.status = status;
	}

	/**
	 * cell Equal cell = true/false
	 */
	public boolean equals(Object obj){
		
		if(obj instanceof SudokuCell) {
			SudokuCell cell = (SudokuCell) obj;
			if( this.row == cell.row && this.column == cell.column && this.value == cell.column) {
				return true;				
			}
		} 
		
		return false;
	}

	/**
	 * Getting Row Index of cell
	 * @return
	 */
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	
	/**
	 * Getting Column Index of cell
	 * @return
	 */
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * Getting Value Index of cell
	 * @return
	 */
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	/**
	 * Checking Status
	 * @return
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * Setting Status (True/False)
	 * @return
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * Printing Format
	 */
	public String toString(){
		
		StringBuffer str = new StringBuffer();
		
		str.append("[");
		str.append(this.row);
		str.append(",");
		str.append(this.column);
		str.append("]");
		
		if(status) {
			str.append("=");
		} else {
			str.append("/=");
		}
		
		str.append(this.value);
		
		return str.toString();
	}
}
