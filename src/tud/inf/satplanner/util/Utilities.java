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

package tud.inf.satplanner.util;

import java.io.File;
import java.util.HashMap;

import tud.inf.satplanner.puzzle.SudokuCell;

public class Utilities {

  public Utilities() {
     //Default Construction
  }

  /**
   * Get the extension of a file.
   * @param file
   * @return
   */
  public static String getExtension(File file) {

    String ext = null;
    String s = file.getName();

    int i = s.lastIndexOf('.');

    if (i > 0 &&  i < s.length() - 1) {
      ext = s.substring(i+1).toLowerCase();
    }

    return ext;
  }// end of getExtension(File)

  /**
   * Matching File with Specified Extion
   * @param file
   * @param inputExt
   * @return
   */
    public static boolean accept(File file, String inputExt) {

	    if (file.isDirectory()) {
	    	return false;
	    }

	    String fileExt = Utilities.getExtension(file);
	    if (fileExt != null) {

	    	if (fileExt.equals(inputExt)) {
	    		return true;
	    	} else {
	    		return false;
	    	}
	    }
	    return false;
    }// end of accept(File, String)

    
    public static Object getKeyFromValue(HashMap<SudokuCell, Integer> hm,Object value){
        for(Object o:hm.keySet()){
            if(hm.get(o).equals(value)) {
                return o;
            }
        }
        return null;
    }
}
