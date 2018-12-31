*******************************************************************************
* SAT Sudoku Solver Copyright (C) 2011                                *
* This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.  *
* This is free software, and you are welcome to redistribute it under certain *
* conditions; type `show c' for details.                                      *
******************************************************************************
# Execute:
[ANT]
run_ant.bat -Dargument1=-h 
run_ant.bat -Dargument1=-f -Dargument2=file_path_name

[Example:]
run_ant.bat -Dargument1=-f -Dargument2=puzzle/3/table3-0.txt


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

[Encoding:]
a. Efficient Encoding
b. Minimal Encoding
c. Extended Encoding
d. Compact Encoding
e. Unit Propogation
