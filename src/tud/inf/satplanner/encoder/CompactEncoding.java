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

import org.sat4j.core.VecInt;
import org.sat4j.specs.ContradictionException;
import org.sat4j.specs.ISolver;
import org.sat4j.specs.IVecInt;

import tud.inf.satplanner.puzzle.Sudoku;
import tud.inf.satplanner.puzzle.SudokuCell;
import tud.inf.satplanner.satsolver.Clause;
import tud.inf.satplanner.satsolver.DIMACS;
import tud.inf.satplanner.satsolver.Literal;

public class CompactEncoding extends EncodingScheme {

	/**
	 * Default Constructor
	 * @param sudoku_
	 */
	public CompactEncoding(Sudoku sudoku_) {
		this.sudoku_ = sudoku_;
		this.sudoku_Grid = sudoku_.get_Grid();
		this.map = sudoku_.getEncoding_map();
	}

	
	/**
	 * Encoding Sudoku
	 * Formula Efficient Encoding = Cell_def AND Cell_uniq  AND Row_Def AND Row_uniq 
	 * AND Col_def AND Col_uniq AND Block_def AND Block_uniq AND Assigned
	 * output: DIMACS
	 */
	public void encodeDIMACS() {

//		System.out.println("DIMACS Compact Encoding is used...!");

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

				if (this.sudoku_Grid[row][col] != 0) {
					pre_assign_values++;
					continue;
				}

				for (int val = 0; val < N; val++) {

                    if(differnt_region(row, col, val + 1))
                    {

						maped_value++;
	
						new_cell.setRow(row + 1);
						new_cell.setColumn(col + 1);
						new_cell.setValue(val + 1);
	
						this.map.put(new_cell.toString(), maped_value);
                    }
				}


				index_++;
			}

		}

		// Number of Clauses;
		int nr_clauses = map.size();

		// Number of Literals;````	``	``	``	
		int nr_literals =  N * (N - 1) * 4 
				+ pre_assign_values;
		
		this.dimacs = new DIMACS(nr_literals, nr_clauses);


		/**
		 * Encoding Formulas for Definitional & Uniqueness for Cells.
		 * Cell_def   = AND r = 1 to n AND c = 1 to n OR v = 1 to n (r,c,v)
		 * Cell_uniq  = AND r = 1 to n AND c = 1 to n AND v_i = 1 to (n-1) AND v_j = (v_i+1) to n -(r,c,v_i) OR -(r,c,v_j)
		 */
		int nr_cell = 0;
		
		
		for (int row = 0; row < N; row++) {
			 
			for (int col = 0; col < N; col++) {

				Clause def_clause = new Clause();
				boolean is_def_cell = false;

				for (int val = 0; val < N; val++) {
					
					if(isMappedLiteral(row + 1, col + 1, val + 1)) {
						Literal new_liter = getMappedLiteral(row + 1, col + 1, val + 1,
								false);
						def_clause.addLiteral(new_liter);
						is_def_cell = true;
					}
				}

				if(is_def_cell) {
					dimacs.addClause(def_clause);
					nr_cell++;
				}

				for (int val = 0; val < N - 1; val++) {
					
					for (int val_2 = val + 1; val_2 < N; val_2++) {

                        if(isMappedLiteral(row + 1, col + 1, val + 1) && isMappedLiteral(row + 1, col + 1, val_2 + 1)) {

							Clause unique_clause = new Clause();
	
							Literal signed_liter_1 = getMappedLiteral(row + 1,
									col + 1, val + 1, true);
	
							Literal signed_liter_2 = getMappedLiteral(row + 1,
									col + 1, val_2 + 1, true);
	
							unique_clause.addLiteral(signed_liter_1);
							unique_clause.addLiteral(signed_liter_2);
	
							dimacs.addClause(unique_clause);
							
							nr_cell++;
                        }
					}

				}

			}

		}

		/**
		 * Encoding Formulas for Definitional & Uniqueness for Rows.
		 * Row_def   = AND r = 1 to n AND v = 1 to n OR c = 1 to n (r,c,v)
		 * Row_uniq  = AND r = 1 to n AND v = 1 to n AND c_i = 1 to (n-1) AND c_j = (c_i+1) to n -(r,c,v_i) OR -(r,c,v_j)
		 */
		int nr_rows = 0;


		for (int row = 0; row < N; row++) {
			
			for (int val = 0; val < N; val++) {
				
				Clause def_clause = new Clause();
				boolean is_def_row = false;

				for (int col = 0; col < N; col++) {

					if(isMappedLiteral(row + 1, col + 1, val + 1)) {
						Literal new_liter = getMappedLiteral(row + 1, col + 1, val + 1,
								false);
						def_clause.addLiteral(new_liter);
						is_def_row = true;
					}
					
				}

				if(is_def_row) {
					dimacs.addClause(def_clause);
					nr_rows++;
				}

				for (int j5 = 0; j5 < N - 1; j5++) {

					for (int k5 = j5 + 1; k5 < N; k5++) {

                        if(isMappedLiteral(row + 1, j5 + 1, val + 1) && isMappedLiteral(row + 1, k5 + 1, val + 1)) {

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
		}

		/**
		 * Encoding Formulas for Definitional & Uniqueness for Columns
		 * Col_def   = AND c = 1 to n AND c = 1 to n OR r = 1 to n (r,c,v)
		 * Col_uniq  = AND c = 1 to n AND v = 1 to n AND r_i = 1 to (n-1) AND r_j = (r_i+1) to n -(r,c,v_i) OR -(r,c,v_j)
		 */
		int nr_columns = 0;
		
		for (int col = 0; col < N; col++) {
		
			for (int val = 0; val < N; val++) {
			
				Clause def_clause = new Clause();
				boolean is_def_col = false;

				for (int row = 0; row < N; row++) {

					if(isMappedLiteral(row + 1, col + 1, val + 1)) {
						Literal new_liter = getMappedLiteral(row + 1, col + 1, val + 1,
								false);
						def_clause.addLiteral(new_liter);
						is_def_col = true;
					}

				}

				if(is_def_col) {
					dimacs.addClause(def_clause);
					nr_columns++;
				}

				for (int row = 0; row < N - 1; row++) {
					
					for (int row_1 = row + 1; row_1 < N; row_1++) {

                        if(isMappedLiteral(row + 1, col + 1, val + 1) && isMappedLiteral(row_1 + 1, col + 1, val + 1)) {

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
		}

		/**
		 * Encoding Formulas for Definitional & Uniqueness of Blocks
		 * Block_def   = AND roffs = 1 to _|n| AND coffs = 1 to _|n| 
		 * AND v = 1 to n OR r = 1 to _|n| OR c = 1 to _|n| (roffs*_|n| + r,coffs* _|n|+ c,v)
		 *
		 * Block_uniq  = AND roffs = 1 to _|n| AND coffs = 1 to _|n| AND v = 1 to n AND r = 1 to n OR c = r + 1 to n
		 * -(roffs*_|n| + (r mod _|n|),coffs* _|n|+ (r mod _|n|),v) OR 
		 * -(roffs*_|n| + (c mod _|n|),coffs* _|n|+ (c mod _|n|),v) 
		 */
		int nr_blocks = 0;
		
		int subN = this.sudoku_.get_M();

		for (int rb = 0; rb < N; rb += subN) {
			
			for (int cb = 0; cb < N; cb += subN) {
			
				for (int val = 0; val < N; val++) {
				
					Clause def_clause = new Clause();
					boolean is_def_blk = false;

					for (int row = 0; row < subN; row++) {
						for (int col = 0; col < subN; col++) {

							if(isMappedLiteral(rb + row + 1, cb + col + 1, val + 1)) {
								Literal new_liter = getMappedLiteral(rb + row + 1,
										cb + col + 1, val + 1, false);
								def_clause.addLiteral(new_liter);
								is_def_blk = true;
							}

						}
					}

					if(is_def_blk) {
						dimacs.addClause(def_clause);
						nr_blocks++;
					}

					for (int db = 0; db < N; db++) {
						
						for (int db1 = db + 1; db1 < N; db1++) {

	                        if(isMappedLiteral(rb + db % subN + 1, cb + db / subN + 1, val + 1) && 
	                        		isMappedLiteral(rb + db1 % subN + 1, cb + db1 / subN + 1 , val + 1)) {

								Clause unique_clause = new Clause();
	
								Literal signed_liter_1 = getMappedLiteral(rb + db % subN + 1, 
													cb + db / subN + 1, val + 1, true);
	
								Literal signed_liter_2 = getMappedLiteral(rb + db1 % subN + 1, 
										cb + db1 / subN + 1, val + 1, true);
	
								unique_clause.addLiteral(signed_liter_1);
								unique_clause.addLiteral(signed_liter_2);
	
								dimacs.addClause(unique_clause);
	
								nr_blocks++;
	                        }
						}
					}
				}
			}
		}
//		System.out.println("Number of Clauses: "+ nr_clauses);
//		System.out.println("Number of Literals: "+ nr_literals);

	}

	private int getMappedLiteral(int i, int j, int k) {

		key_cell.setRow(i);
		key_cell.setColumn(j);
		key_cell.setValue(k);

		String key = key_cell.toString();

		int symbol_ = this.map.get(key);
	
		return symbol_;

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
	
	
    private boolean differnt_region(int row,int col, int value) {
    	
    	if(sameRow(row, value)) {
    		return false;
    	} else if(sameCol(col, value)) {
    		return false;
    	} else if(sameBlock(row, col, value)) {
    		return false;
    	} else {
    		return true;
    	}
    	
    }
    
    private boolean sameBlock(int row,int col, int value){
    	
    	int subN = sudoku_.get_M();

        int br = row >= subN ? (row / subN) * subN : 0;
        int bc = col >= subN ? (col / subN) * subN : 0;

        for(int row_ = br; row_ < br + subN; row_++) {

        	for(int col_ = bc; col_ < bc + subN; col_++) {
            
        		if(this.sudoku_Grid[row_][col_] == value) {
                    return true;
        		}
            }
        }
    	
    	return false;
    }

    private boolean sameCol(int col, int value){
    	
    	int N = sudoku_.get_N();
    	
    	for(int row_ = 0; row_ < N; row_++) {
    		
            if(this.sudoku_Grid[row_][col] == value) {
                return true;
            }
    	}
    	
    	return false;
    }

    private boolean sameRow(int row, int value){
    	
    	int N = sudoku_.get_N();
    	
    	for(int col_ = 0; col_ < N; col_++) {
    		
            if(this.sudoku_Grid[row][col_] == value) {
                return true;
            }
    	}
    	
    	return false;
    }
    
	public DIMACS getDIMACS() {
		return this.dimacs;
	}

	public void encodeOnFly(ISolver solver) throws ContradictionException {
		
//		System.out.println("Onfly Compact encoding is used...!");

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

				if (this.sudoku_Grid[row][col] != 0) {					
					
					pre_assign_values++;					
					continue;
				}

				for (int val = 0; val < N; val++) {

                    if(differnt_region(row, col, val + 1))
                    {

						maped_value++;
	
						new_cell.setRow(row + 1);
						new_cell.setColumn(col + 1);
						new_cell.setValue(val + 1);
	
						this.map.put(new_cell.toString(), maped_value);
                    }
				}
				index_++;
			}

		}

		
		for (int row = 0; row < N; row++) {
			for (int col = 0; col < N; col++) {
				
				if (this.sudoku_Grid[row][col] != 0) {	
					maped_value++;

					new_cell.setRow(row + 1);
					new_cell.setColumn(col + 1);
					new_cell.setValue(this.sudoku_Grid[row][col]);
					
					this.map.put(new_cell.toString(), maped_value);
										
					pre_assign_values++;
					continue;
				}
			}
		}
		
		// Number of Clauses;
//		int nr_clauses = map.size();

		// Number of Literals;
		int nr_literals = (N  + (N* (N * (N - 1)) / 2)) * 4 +
				+ pre_assign_values;
	
		solver.newVar(nr_literals);

		/**
		 * Encoding Formulas for Preassigned values for Cells
		 * Assigned = AND for i = 1 to k (r,c,v) where (r,c,v) in V+
		 */
		IVecInt assign_clause_ = new VecInt();

		for (int row = 0; row < N; row++) {
			
			for (int col = 0; col < N; col++) {

				if (this.sudoku_Grid[row][col] != 0) {

					int new_liter = getMappedLiteral(row + 1, col + 1, this.sudoku_Grid[row][col]);
					assign_clause_.push(new_liter);
					
					solver.addClause(assign_clause_);
					assign_clause_.clear();
				}
			}
		}


		/**
		 * Encoding Formulas for Definitional & Uniqueness for Cells.
		 * Cell_def   = AND r = 1 to n AND c = 1 to n OR v = 1 to n (r,c,v)
		 * Cell_uniq  = AND r = 1 to n AND c = 1 to n AND v_i = 1 to (n-1) AND v_j = (v_i+1) to n -(r,c,v_i) OR -(r,c,v_j)
		 */
		int nr_cell = 0;
		
		IVecInt cell_def_clause = new VecInt();
		IVecInt cell_unique_clause = new VecInt();
		
		for (int row = 0; row < N; row++) {
			 
			for (int col = 0; col < N; col++) {


				boolean is_def_cell = false;

				for (int val = 0; val < N; val++) {
					
					if(isMappedLiteral(row + 1, col + 1, val + 1)) {
						int new_liter = getMappedLiteral(row + 1, col + 1, val + 1);
						cell_def_clause.push(new_liter);
						is_def_cell = true;
					}
				}

				if(is_def_cell) {
					solver.addClause(cell_def_clause);
					
					cell_def_clause.clear();
					nr_cell++;
				}

				for (int val = 0; val < N - 1; val++) {
					
					for (int val_2 = val + 1; val_2 < N; val_2++) {

                        if(isMappedLiteral(row + 1, col + 1, val + 1) && isMappedLiteral(row + 1, col + 1, val_2 + 1)) {

	
							int signed_liter_1 = (-1) * getMappedLiteral(row + 1,
									col + 1, val + 1);
	
							int signed_liter_2 = (-1) * getMappedLiteral(row + 1,
									col + 1, val_2 + 1);
	
							cell_unique_clause.push(signed_liter_1);
							cell_unique_clause.push(signed_liter_2);
	
							solver.addClause(cell_unique_clause);
							cell_unique_clause.clear();
							nr_cell++;
                        }
					}

				}

			}

		}

//		try {
//			int model[] = solver.findModel();
//			solver.reset();
//			
//			IVecInt unit_clause_ = new VecInt();
//
//			for(int m:  model) {
//				if(m < 0){
//					unit_clause_.push(m);
//					solver.addClause(unit_clause_);
//					unit_clause_.clear();
//				}
//			}} catch(TimeoutException exp) {
//				exp.printStackTrace();
//			}

		/**
		 * Encoding Formulas for Definitional & Uniqueness for Rows.
		 * Row_def   = AND r = 1 to n AND v = 1 to n OR c = 1 to n (r,c,v)
		 * Row_uniq  = AND r = 1 to n AND v = 1 to n AND c_i = 1 to (n-1) AND c_j = (c_i+1) to n -(r,c,v_i) OR -(r,c,v_j)
		 */
		int nr_rows = 0;

		IVecInt row_def_clause_ = new VecInt();
		IVecInt row_unique_clause_ = new VecInt();

		for (int row = 0; row < N; row++) {
			
			for (int val = 0; val < N; val++) {

				boolean is_def_row = false;

				for (int col = 0; col < N; col++) {

					if(isMappedLiteral(row + 1, col + 1, val + 1)) {
						int new_liter = getMappedLiteral(row + 1, col + 1, val + 1);
						row_def_clause_.push(new_liter);
						is_def_row = true;
					}
					
				}

				if(is_def_row) {
					solver.addClause(row_def_clause_);
					row_def_clause_.clear();
					nr_rows++;
				}

				for (int j5 = 0; j5 < N - 1; j5++) {

					for (int k5 = j5 + 1; k5 < N; k5++) {

                        if(isMappedLiteral(row + 1, j5 + 1, val + 1) && isMappedLiteral(row + 1, k5 + 1, val + 1)) {

//            				IVecInt unique_clause_ = new VecInt();
	
							int signed_liter_1 = (-1) * getMappedLiteral(row + 1,
									j5 + 1, val + 1);
	
							// signed_liter_1.setSigned(true);
							int signed_liter_2 = (-1) * getMappedLiteral(row + 1,
									k5 + 1, val + 1);
							// signed_liter_2.setSigned(true);
	
							row_unique_clause_.push(signed_liter_1);
							row_unique_clause_.push(signed_liter_2);
	
							solver.addClause(row_unique_clause_);
							row_unique_clause_.clear();
							nr_rows++;
                        }
					}
				}
			}
		}

		/**
		 * Encoding Formulas for Definitional & Uniqueness for Columns
		 * Col_def   = AND c = 1 to n AND c = 1 to n OR r = 1 to n (r,c,v)
		 * Col_uniq  = AND c = 1 to n AND v = 1 to n AND r_i = 1 to (n-1) AND r_j = (r_i+1) to n -(r,c,v_i) OR -(r,c,v_j)
		 */
		int nr_columns = 0;

		IVecInt col_def_clause_ = new VecInt();
		IVecInt col_unique_clause_ = new VecInt();
		
		for (int col = 0; col < N; col++) {
		
			for (int val = 0; val < N; val++) {

				boolean is_def_col = false;

				for (int row = 0; row < N; row++) {

					if(isMappedLiteral(row + 1, col + 1, val + 1)) {
						int new_liter = getMappedLiteral(row + 1, col + 1, val + 1);
						col_def_clause_.push(new_liter);
						is_def_col = true;
					}

				}

				if(is_def_col) {
					solver.addClause(col_def_clause_);
					col_def_clause_.clear();
					nr_columns++;
				}

				for (int row = 0; row < N - 1; row++) {
					
					for (int row_1 = row + 1; row_1 < N; row_1++) {

                        if(isMappedLiteral(row + 1, col + 1, val + 1) && isMappedLiteral(row_1 + 1, col + 1, val + 1)) {
	
							int signed_liter_1 = (-1) * getMappedLiteral(row + 1,
									col + 1, val + 1);
	
							int signed_liter_2 = (-1) * getMappedLiteral(row_1 + 1,
									col + 1, val + 1);
	
							col_unique_clause_.push(signed_liter_1);
							col_unique_clause_.push(signed_liter_2);
	
							solver.addClause(col_unique_clause_);
							col_unique_clause_.clear();
							nr_columns++;
						
						}
					}
				}
			}
		}

		
		/**
		 * Encoding Formulas for Definitional & Uniqueness of Blocks
		 * Block_def   = AND roffs = 1 to _|n| AND coffs = 1 to _|n| 
		 * AND v = 1 to n OR r = 1 to _|n| OR c = 1 to _|n| (roffs*_|n| + r,coffs* _|n|+ c,v)
		 *
		 * Block_uniq  = AND roffs = 1 to _|n| AND coffs = 1 to _|n| AND v = 1 to n AND r = 1 to n OR c = r + 1 to n
		 * -(roffs*_|n| + (r mod _|n|),coffs* _|n|+ (r mod _|n|),v) OR 
		 * -(roffs*_|n| + (c mod _|n|),coffs* _|n|+ (c mod _|n|),v) 
		 */
		int nr_blocks = 0;

		IVecInt blk_def_clause_ = new VecInt();
		IVecInt blk_unique_clause_ = new VecInt();
		
		int subN = this.sudoku_.get_M();

		for (int rb = 0; rb < N; rb += subN) {
			
			for (int cb = 0; cb < N; cb += subN) {
			
				for (int val = 0; val < N; val++) {


					boolean is_def_blk = false;

					for (int row = 0; row < subN; row++) {
						for (int col = 0; col < subN; col++) {

							if(isMappedLiteral(rb + row + 1, cb + col + 1, val + 1)) {
								int new_liter = getMappedLiteral(rb + row + 1,
										cb + col + 1, val + 1);								
								blk_def_clause_.push(new_liter);
								is_def_blk = true;
							}

						}
					}

					if(is_def_blk) {
						solver.addClause(blk_def_clause_);
						blk_def_clause_.clear();
						nr_blocks++;
					}

					for (int db = 0; db < N; db++) {
						
						for (int db1 = db + 1; db1 < N; db1++) {

	                        if(isMappedLiteral(rb + db % subN + 1, cb + db / subN + 1, val + 1) && 
	                        		isMappedLiteral(rb + db1 % subN + 1, cb + db1 / subN + 1 , val + 1)) {
	
								int signed_liter_1 = (-1) * getMappedLiteral(rb + db % subN + 1, 
													cb + db / subN + 1, val + 1);
	
								int signed_liter_2 = (-1) * getMappedLiteral(rb + db1 % subN + 1, 
										cb + db1 / subN + 1, val + 1);
	
								blk_unique_clause_.push(signed_liter_1);
								blk_unique_clause_.push(signed_liter_2);
	
								solver.addClause(blk_unique_clause_);
								blk_unique_clause_.clear();
								nr_blocks++;
	                        }
						}
					}
				}
			}
		}
		
//		System.out.println("Number of Clauses: "+ nr_clauses);
//		System.out.println("Number of Literals: "+ nr_literals);

	}
}
