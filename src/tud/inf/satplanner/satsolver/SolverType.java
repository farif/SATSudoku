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

public enum SolverType {
	
	/**
	 * Solver(s) List:
	 * 1. MINISAT
	 * 2. MINI SAT with Heap
	 * 3. Mini SAT with Heap EZ
	 * 4. SAT4J
	 * 5. ZCZH (not yet supported)
	 * 6. MXC (not yet supported)
	 */
		MINISAT,
		
		MINI_SAT_HEAP,
		
		MINI_SAT_HEAP_EZ,
		
		SAT4J,
	
		ZCZH,
	
		MXC,
		
		Default
}
