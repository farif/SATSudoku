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

package tud.inf.satplanner.io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SolutionWriter {
	
	public static void writeToFile(String file_name,String output)
	  {
	      try
	      {
	            BufferedWriter writer = new BufferedWriter(new FileWriter(file_name));
	            writer.append(output);
	           // writer.write(string); tried this also.
	            writer.flush();
	            writer.close();
	      }
	      catch(IOException io)
	            {
	                io.printStackTrace();
	            }
	  }
}
