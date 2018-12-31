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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.sat4j.core.VecInt;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IProblem;
import org.sat4j.reader.DimacsReader;
import org.sat4j.reader.Reader;
import org.sat4j.specs.TimeoutException;
import org.sat4j.specs.ContradictionException;
import org.sat4j.reader.ParseFormatException;

import tud.inf.satplanner.io.FileInput;

public class SAT4J implements SATSolver {

	//Solver Interface
	private ISolver solver;
	
	//Problem Interface
	private IProblem problem;
	
	//Model for Problem Puzzle
	private int[] model;
	
	//DIMACS Reader for Solver
	private Reader reader;

    //Logging Information
    private static final Logger logger = Logger.getLogger(FileInput.class.getName());

	/**
	 * Default Constructor
	 * @param solver_
	 */
	public SAT4J(ISolver solver_) {

		this.solver = solver_;
		this.solver.setTimeout(360);

	}
	
	/**
	 * Solve DIMACS specify the Input file;
	 * Specify Problem
	 * call runSolver; 
	 */
	public void solveDIMACS(String dimacsFileName) {

		try {
			DimacsReader reader_ = new DimacsReader(this.solver);
			reader_.disableNumberOfConstraintCheck();
			
			this.reader = reader_;
			
			this.problem = reader.parseInstance(dimacsFileName);
			runSolver();

		} catch (FileNotFoundException file_exp) {
	    	 logger.log(Level.SEVERE, "Exception for DIMACS File Specified for SAT-solver: " + file_exp.getMessage());
		} catch (ParseFormatException format_exp) {
	    	 logger.log(Level.SEVERE, "Exception for DIMACS File Format for SAT-solver: " + format_exp.getMessage());
		} catch (IOException io_exp) {
	    	 logger.log(Level.SEVERE, "I/O ExceptionFile in SAT-solver: " + io_exp.getMessage());
		} catch (ContradictionException cont_exp) {
	    	 logger.log(Level.SEVERE, "Problem Unsatisfiable...!");
			 logger.log(Level.SEVERE, "Contradictor return  by SAT-solver: " + cont_exp.getMessage());
		}

	}

	/*
	 * Solve using SAT Solver integration not by input file
	 * call run solver
	 */
	public void solveOnFly() {
		this.problem = this.solver;
		runSolver();
	}

	/**
	 * Get Satisfied Model for Puzzle/Problem
	 */
	public int[] getModel() {
		return this.model;
	}

	/**
	 * Add Clause to the Solver internal Interface
	 */
	public void addClause(int[] literals) {

		try {

			VecInt clause = new VecInt(literals);
			this.solver.addClause(clause);

		} catch (ContradictionException exp) {
			exp.printStackTrace();
		}
	}

	/**
	 * Calling solver for any case;
	 * For DIMACS input file or
	 * Only Solution
	 */
	private void runSolver() {

		try {
			if (this.problem.isSatisfiable()) {
				
				this.model = problem.model();

			} else {
		    	 logger.log(Level.SEVERE, "Problem Unsatisfiable...!");
			}
		} catch (TimeoutException timeout_exp) {
	    	 logger.log(Level.SEVERE, "SAT Solver Timeout!: " + timeout_exp.getMessage());
		}
	}

}
