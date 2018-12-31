@SET cd=
@SET promp$=%prompt%
@PROMPT SET cd$Q$P
@CALL>%temp%.\setdir.bat
@
% do not delete this line %
@ECHO off
PROMPT %promp$%
FOR %%c IN (CALL DEL) DO %%c %temp%.\setdir.bat
java -Xms256m -Xmx512m -jar SATSudoku.jar %1 %2