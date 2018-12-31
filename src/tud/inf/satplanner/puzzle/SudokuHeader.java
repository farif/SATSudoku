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

public class SudokuHeader {
	
	//(* A name of the experiment *)
	String experiment = "experiment:";
	String experiment_value;
	
	//(* The total number of tasks in this experiment *)
	String number_of_tasks = "number of tasks:";
	int number_of_tasks_value;

	//(* The number of the current task *)
	String task = "task:";
	int task_value;
	
	//(* The size of the sudoku puzzle *)
	String puzzle_size = "puzzle size:";
	int puzzle_size_value;
	
	private final String endline = "\r\n";
	private final String space = " ";

	/**
	 * Default Constructor:
	 * @param experiment_value
	 * @param number_of_tasks_value
	 * @param task_value
	 * @param puzzle_size_value
	 */
	public SudokuHeader(String experiment_value, int number_of_tasks_value,
			int task_value,int puzzle_size_value) {
		
		this.experiment_value = experiment_value;
		this.number_of_tasks_value = number_of_tasks_value;
		this.task_value = task_value;
		
		this.puzzle_size_value = puzzle_size_value;
	}

	/**
	 * Getting Experiment spec:"experiment"
	 * @return
	 */
	public String getExperiment() {
		return experiment;
	}

	
	/**
	 * Getting Experiment Value e.g 3x3
	 * @return
	 */
	public String getExperiment_value() {
		return experiment_value;
	}


	/**
	 * Setting experiment value
	 * @param experiment_value
	 */
	public void setExperiment_value(String experiment_value) {
		this.experiment_value = experiment_value;
	}
	
	/**
	 * Getting Number of Task value e.g 4
	 * @return
	 */
	public int getNumber_of_tasks_value() {
		return number_of_tasks_value;
	}

	/**
	 * Setting Number of task value
	 * @param number_of_tasks_value
	 */
	public void setNumber_of_tasks_value(int number_of_tasks_value) {
		this.number_of_tasks_value = number_of_tasks_value;
	}

	/**
	 * Getting Task spec: "task"
	 * @return
	 */
	public String getTask() {
		return task;
	}
	
	/**
	 * Setting task value:
	 * @param task_value
	 */
	public void setTask_value(int task_value) {
		this.task_value = task_value;
	}

	/**
	 * Getting task value e.g 2
	 * @return
	 */
	public int getTask_value() {
		return task_value;
	}

//	/**
//	 * Getting Puzzle Size e.g 3x3 -> 3
//	 * @return
//	 */
//	public int getPuzzle_size_value() {
//		return puzzle_size_value;
//	}
	
	/**
	 * Setting Puzzle size
	 * @param puzzle_size_value
	 */
	public void setPuzzle_size_value(int puzzle_size_value) {
		this.puzzle_size_value = puzzle_size_value;
	}
	
	/**
	 * Getting puzzle size
	 * @return
	 */
	public int get_puzzle_size() {
		return this.puzzle_size_value;
	}
	
	public String toString(){
		
		StringBuffer str = new StringBuffer();
		
		str.append(experiment);
		str.append(space);
		str.append(experiment_value);
		str.append(endline);

		str.append(number_of_tasks);
		str.append(space);
		str.append(number_of_tasks_value);
		str.append(endline);

		str.append(task);
		str.append(space);
		str.append(task_value);
		str.append(endline);

		str.append(puzzle_size);
		str.append(space);
		str.append(puzzle_size_value);
		str.append("x");
		str.append(puzzle_size_value);
		str.append(endline);

		return str.toString();
	}
}
