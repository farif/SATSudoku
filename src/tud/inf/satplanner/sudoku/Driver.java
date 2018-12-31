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

package tud.inf.satplanner.sudoku;

import java.io.FileReader;

import org.sat4j.minisat.SolverFactory;
import org.sat4j.specs.IProblem;
import org.sat4j.specs.ISolver;

import tud.inf.satplanner.decoder.DecodeSudoku;
import tud.inf.satplanner.encoder.FactoryEncoding;
import tud.inf.satplanner.encoder.EncodingScheme;
import tud.inf.satplanner.encoder.EncodingTypes;
import tud.inf.satplanner.io.FileInput;
import tud.inf.satplanner.parser.ParseSudokuPuzzle;
import tud.inf.satplanner.parser.PuzzleFormater;
import tud.inf.satplanner.puzzle.Sudoku;
import tud.inf.satplanner.puzzle.SudokuHeader;
import tud.inf.satplanner.util.PuzzleTimer;

public class Driver {

	public static void main(String[] args) throws Exception {
		
		try {
			// String file_name_ = "F:/puzzles/3/table3-1.txt";
			String file_name_ = parse_arg_list(args);
	
			PuzzleTimer.start();
	
			FileInput inputFile = new FileInput(file_name_);
	
			if (inputFile.loadFile()) {
	
				// Default Header.
				SudokuHeader defualt_header_ = new SudokuHeader("0x0", 0, 0, 0);
	
				// Loading Puzzle.
				Sudoku sudoku_ = new Sudoku(defualt_header_);
				// File Reader
				FileReader reader_ = inputFile.getFileInputStream();
	
				// Parsing Puzzle.
				ParseSudokuPuzzle parsePuzzle = new ParseSudokuPuzzle(reader_,
						sudoku_);
				// Calling Parse.
				parsePuzzle.parse();
	
				// Encoding of Puzzle in SAT Format.
				EncodingScheme encod_scheme = FactoryEncoding.getEncoding(
						EncodingTypes.COMPACT, sudoku_);
	
				// encod_scheme.encodeDIMACS();
				// SolutionWriter.writeToFile("result.txt",
				// encod_scheme.getDIMACS().toString());
				//
				// SATSolver solver = SATFactory.getSolver(SolverType.MINISAT);
				// solver.solveDIMACS("result.txt");
				ISolver solver = SolverFactory.newMiniSAT();
				encod_scheme.encodeOnFly(solver);
				// SolutionWriter.writeToFile("result.txt",
				// encod_scheme.getDIMACS().toString());
	
				// solver.solveDIMACS("result.txt");
				IProblem problem;
	
				problem = solver;
	
				int[] model = null;
	
				if (problem.isSatisfiable()) {
	
					model = solver.model();
	
					// for(int inst :model) {
					// System.out.print(inst);
					// }
	
				} else {
					System.err.println("Problem Unsatisfiable...!");
				}
	
				// int [] model = solver.getModel();
	
				DecodeSudoku decode = new DecodeSudoku(sudoku_, model);
				// decode.print_model();
	
				decode.decodeModel();
	
				PuzzleFormater.N = sudoku_.get_M();
	
				PuzzleFormater.grid = sudoku_.get_Grid();
				PuzzleFormater.header = sudoku_.getHeader_info();
	
				String solved_grid_ = PuzzleFormater.puzzle();
	
				System.out.println(solved_grid_);
	
				// SolutionWriter.writeToFile("output.txt", solved_grid_);
	
				 System.out.println("Processing Time (in sec.): [" +
						 PuzzleTimer.getTime_in_seconds() + "]");
				// System.out.println("Processing Time: " +
				// PuzzleTimer.getTime_in_minutes()+ " m");
	
			} else {
				System.err.println("Fail to open Input File to read:" + file_name_);
			}
			
		} catch(final org.sat4j.specs.ContradictionException error) {
			System.err.println("Solver returns contradiction...!");
		}  catch(final java.lang.OutOfMemoryError error) {
			System.err.println("Heap out of Bound...!");
		} catch(final java.lang.Exception error) {
			System.err.println("Unkown error...!");
		} 
	}

	private static String parse_arg_list(String arg_list[]) {

		String argument_ = "-h";
		String input_file_ = null;

		// Help, Print calling interface.
		if (arg_list.length > 0 && arg_list[0].equalsIgnoreCase(argument_)) {
			System.out.println("sudoplanner [-f FILE]");
			System.out
					.println("Solves a Sudoku puzzle. Filename suplied, print solution on STDOUT");
			System.out.println("");
			System.out.println("Options:");
			System.out
					.println("-f --input-file <FILE> \t Read a game from FILE and solve it");
			System.exit(0);
		} else if (arg_list.length > 0 && arg_list[0].equals("-f")) {
			if (arg_list.length > 1) {
				input_file_ = arg_list[1];
				return input_file_;
			} else {
				System.out.println("Invalid Argument:");
				System.out
						.println("-f --input-file <FILE> Read a game from FILE and solve it");
				System.exit(-1);
			}
		} else {
			System.out
					.println("Please! provide following arguments to run the program:");
			System.out.println("sudoplanner [-f FILE]");
			System.exit(-1);
		}

		return input_file_;
	}

}
