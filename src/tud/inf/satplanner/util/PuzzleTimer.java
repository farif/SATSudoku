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

public class PuzzleTimer {

    //Clock timer.
    private static double start;
    
   /*
    * Get current time
    */ 
    public static void start() {
    	start = System.currentTimeMillis();
    }
    
    /**
     *  Get elapsed time in milliseconds.
     */
    public static double getTime_in_mill_seconds() {
    	double elapsedTimeMillis = System.currentTimeMillis() - start;
    	return elapsedTimeMillis;
    }
    
    /**
     * Get elapsed time in seconds.
     * @return
     */
    public static double getTime_in_seconds() {
        // Get elapsed time in seconds
    	double elapsedTimeSec = getTime_in_mill_seconds() / 1000F;
        return elapsedTimeSec;
    }
    
    
    /**
     * Get elapsed time in minutes 
     * @return 
     */
    public static double getTime_in_minutes() {
    	double elapsedTimeMin = getTime_in_seconds()/(60*1000F);
    	return elapsedTimeMin;
    }    
    
    /**
     * Get elapsed time in hours
     */
    public static double getTime_in_hours() {
    	double elapsedTimeHour = getTime_in_minutes()/(60*60*1000F);
    	return elapsedTimeHour;
    }    
    
    public static void reset(){
    	start = 0;
    }

}
