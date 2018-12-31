*******************************************************************************
* SAT Sudoku Solver Copyright (C) 2009                                *
* This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.  *
* This is free software, and you are welcome to redistribute it under certain *
******************************************************************************
# SAT Sudoku Solver:

[Windows Commandline]
run_planner.bat
run_planner.bat -f file_path_name
[Example:]
run_planner.bat -f puzzle/3/table3-0.txt

[Unix/Linux]
./run_planner
./run_planner -f file_path_name

[Example:]
./run_planner -f puzzle/3/table3-0.txt

[Supported Encodings:]
a. Efficient Encoding
b. Minimal Encoding
c. Extended Encoding
d. Compact Encoding
e. Unit Propogation

References:

[1] Gihwon Kwon, Himanshu Jain,"Optimized CNF Encoding for Sudoku Puzzles", In 13th International 
Conference on Logic for Programming Artificial Intelligence and Reasoning (LPAR 2006).