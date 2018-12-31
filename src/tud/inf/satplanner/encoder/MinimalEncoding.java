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

import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;

import tud.inf.satplanner.puzzle.Sudoku;
import tud.inf.satplanner.puzzle.SudokuCell;
import tud.inf.satplanner.satsolver.Clause;
import tud.inf.satplanner.satsolver.DIMACS;
import tud.inf.satplanner.satsolver.Literal;

public class MinimalEncoding extends EncodingScheme {

	/**
	 * Default Constructor
	 * @param sudoku_
	 */
	public MinimalEncoding(Sudoku sudoku_) {
		this.sudoku_ = sudoku_;
		this.sudoku_Grid = sudoku_.get_Grid();
		this.map = sudoku_.getEncoding_map();
	}

	
	/**
	 * Encoding Sudoku
	 * Formula Minimal Encoding = Cell_def AND Row_uniq AND Col_uniq AND Block_uniq AND Assigned
	 * output: DIMACS
	 */
	public void encodeDIMACS() {

//		System.out.println("Minimal Encoding is used...!");

		Integer maped_value = 0;
		int pre_assign_values = 0;

		int N = sudoku_.get_N();
		int index_ = 0;

		SudokuCell new_cell = new SudokuCell(0, 0, 0, true);

		/**
		 * Encoding HashMap for size N*N*N
		 * e.g <[1,2] = 3 , 1> (String, Integer)
		 */
		for (int row = 0; row < N; row++) {

			for (int col = 0; col < N; col++) {

				for (int val = 0; val < N; val++) {

					maped_value++;

					new_cell.setRow(row + 1);
					new_cell.setColumn(col + 1);
					new_cell.setValue(val + 1);

					this.map.put(new_cell.toString(), maped_value);

				}

				if (this.sudoku_Grid[row][col] != 0) {
					pre_assign_values++;
				}

				index_++;
			}

		}

		// Number of Clauses;
		int nr_clauses = map.size();

		int nr_literals = N * N + N * N * ((N * (N - 1)) / 2) * 3 + pre_assign_values;

		this.dimacs = new DIMACS(nr_literals, nr_clauses);

		/**
		 * Encoding Formulas for Preassigned values for Cells
		 * Assigned = AND for i = 1 to k (r,c,v) where (r,c,v) in V+
		 */
		for (int row = 0; row < N; row++) {
			
			for (int col = 0; col < N; col++) {

				if (this.sudoku_Grid[row][col] != 0) {

					Clause def_clause = new Clause();

					Literal new_liter = getMappedLiteral(row + 1, col + 1,
							this.sudoku_Grid[row][col], false);

					def_clause.addLiteral(new_liter);

					dimacs.addClause(def_clause);

				}
			}
		}


		/**
		 * Encoding Formulas for Definitional & Uniqueness for Cells.
		 * Cell_def   = AND r = 1 to n AND c = 1 to n OR v = 1 to n (r,c,v)
		 */
		int nr_cell = 0;

		for (int row = 0; row < N; row++) {
			 
			for (int col = 0; col < N; col++) {

				Clause def_clause = new Clause();

				for (int val = 0; val < N; val++) {
					Literal new_liter = getMappedLiteral(row + 1, col + 1, val + 1,
							false);
					def_clause.addLiteral(new_liter);
				}

				dimacs.addClause(def_clause);
				nr_cell++;

			}

		}

		/**
		 * Encoding Formulas for Definitional & Uniqueness for Rows.
		 * Row_uniq  = AND r = 1 to n AND v = 1 to n AND c_i = 1 to (n-1) AND c_j = (c_i+1) to n -(r,c,v_i) OR -(r,c,v_j)
		 */
		int nr_rows = 0;

		for (int row = 0; row < N; row++) {
			
			for (int val = 0; val < N; val++) {
				
				for (int j5 = 0; j5 < N - 1; j5++) {

					for (int k5 = j5 + 1; k5 < N; k5++) {

						Clause unique_clause = new Clause();

						Literal signed_liter_1 = getMappedLiteral(row + 1,
								j5 + 1, val + 1, true);

						// signed_liter_1.setSigned(true);
						Literal signed_liter_2 = getMappedLiteral(row + 1,
								k5 + 1, val + 1, true);
						// signed_liter_2.setSigned(true);

						unique_clause.addLiteral(signed_liter_1);
						unique_clause.addLiteral(signed_liter_2);

						dimacs.addClause(unique_clause);
						nr_rows++;
					}
				}
			}
		}

		/**
		 * Encoding Formulas for Definitional & Uniqueness for Columns
		 * Col_uniq  = AND c = 1 to n AND v = 1 to n AND r_i = 1 to (n-1) AND r_j = (r_i+1) to n -(r,c,v_i) OR -(r,c,v_j)
		 */
		int nr_columns = 0;

		for (int col = 0; col < N; col++) {
		
			for (int val = 0; val < N; val++) {
			
				for (int row = 0; row < N - 1; row++) {
					
					for (int row_1 = row + 1; row_1 < N; row_1++) {

						Clause unique_clause = new Clause();

						Literal signed_liter_1 = getMappedLiteral(row + 1,
								col + 1, val + 1, true);

						Literal signed_liter_2 = getMappedLiteral(row_1 + 1,
								col + 1, val + 1, true);

						unique_clause.addLiteral(signed_liter_1);
						unique_clause.addLiteral(signed_liter_2);

						dimacs.addClause(unique_clause);

						nr_columns++;
					}
				}
			}
		}

		/**
		 * Encoding Formulas for Definitional & Uniqueness of Blocks
		 * Block_uniq  = AND roffs = 1 to _|n| AND coffs = 1 to _|n| AND v = 1 to n AND r = 1 to n OR c = r + 1 to n
		 * -(roffs*_|n| + (r mod _|n|),coffs* _|n|+ (r mod _|n|),v) OR 
		 * -(roffs*_|n| + (c mod _|n|),coffs* _|n|+ (c mod _|n|),v) 
		 */
		int nr_blocks = 0;
		
		int subN = this.sudoku_.get_M();

		for (int rb = 0; rb < N; rb += subN) {
			
			for (int cb = 0; cb < N; cb += subN) {
			
				for (int val = 0; val < N; val++) {
				
					for (int db = 0; db < N; db++) {
						
						for (int db1 = db + 1; db1 < N; db1++) {

							Clause unique_clause = new Clause();

							Literal signed_liter_1 = getMappedLiteral(rb + db
									% subN + 1, cb + db / subN + 1, val + 1,
									true);

							Literal signed_liter_2 = getMappedLiteral(rb + db1
									% subN + 1, cb + db1 / subN + 1, val + 1,
									true);

							unique_clause.addLiteral(signed_liter_1);
							unique_clause.addLiteral(signed_liter_2);

							dimacs.addClause(unique_clause);

							nr_blocks++;
						}
					}
				}
			}
		}
		
//		System.out.println("Number of Clauses: "+ nr_clauses);
//		System.out.println("Number of Literals: "+ nr_literals);

	}

	private Literal getMappedLiteral(int i, int j, int k, boolean signed) {

		key_cell.setRow(i);
		key_cell.setColumn(j);
		key_cell.setValue(k);

		String key = key_cell.toString();

		Integer symbol = (Integer) this.map.get(key);
		Literal new_literal = new Literal(symbol, signed);

		return new_literal;

	}

	public DIMACS getDIMACS() {
		return this.dimacs;
	}

	public void encodeOnFly(ISolver solver) throws ContradictionException {

	}
}

