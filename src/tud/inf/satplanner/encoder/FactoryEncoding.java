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

import tud.inf.satplanner.puzzle.Sudoku;
import tud.inf.satplanner.encoder.ExtendedEncoding;;

public class FactoryEncoding {
	
	/**
	 * Encodings:
	 *  1. Extended Encoding Scheme;
	 *	2. Efficient Encoding Scheme;
     *	3. Minimal Encoding Scheme;
	 *	4. Compact Encoding Scheme;  (Extended) + (Subtracted);
	 * @param type
	 * @param sudoku
	 * @return
	 */
	public static EncodingScheme getEncoding(EncodingTypes type, Sudoku sudoku) {

		EncodingScheme scheme = null;
		
		switch(type) {
			
			case EXTENDED:
				scheme = new ExtendedEncoding(sudoku);
				break;
			
			case MINIMAL:
				scheme = new MinimalEncoding(sudoku);
				break;
				
			case EFFICIENT:
				scheme = new EfficientEncoding(sudoku);
				break;
				
			case COMPACT:
				scheme = new CompactEncoding(sudoku);
				break;
				
			default:
				//Generate Error Since Required Types is not called For;
		}
		
		return scheme;
	}
}
