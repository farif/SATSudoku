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

package tud.inf.satplanner.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FileInput {

   //File Name & Location
   protected String filePath = null ;
   //File
   protected File file = null ;
   //File Input Stream Reader
   protected FileReader fileReader = null ;
   //Logging Information
   private static final Logger logger = Logger.getLogger(FileInput.class.getName());
   /**
    * Default Constructor, Initialize File Name & Location
    * @param filePath
    */
   public FileInput(String filePath) {
      this.filePath = filePath;
   }// end of Constructor, FileParser(String)

   /**
    /*****************************************************************************
    * @return
    * @throws FileNotFoundException
    * @throws IOException
    */
   public boolean loadFile()
       throws FileNotFoundException, IOException {

     boolean loaded = false;

     this.file = new File(this.filePath);

     if (file.isFile() && file.exists()) { 

//    	 logger.log(Level.CONFIG, "Opening Stream to read:" + filePath );
		 
    	 this.fileReader = new FileReader(file);
    	 
    	 loaded =  true;

     } else {
    	 logger.log(Level.SEVERE, "Fail to open Input File to read:" + filePath );
         loaded  = false;
     }

     return loaded;
   } // end parseFile(String)

   /**
    * Getting File Reading Stream
    * @return
    */
   public FileReader getFileInputStream(){
	   return this.fileReader;
   }
   /**
    * Free Resources, Release File Handler
    * @throws IOException
    */
   protected void closeFile() throws IOException {
	   this.fileReader.close();
   } // end closeFile()

}
