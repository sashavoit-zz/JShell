// **********************************************************
// Assignment2:
// Student1:
// UTORID user_name: benhaimr
// UT Student #: 1006139830
// Author: Raz Ben Haim
//
// Student2:
// UTORID user_name: sharm697
// UT Student #: 1002352465
// Author: Sankalp Sharma
//
// Student3:
// UTORID user_name: voitovyc
// UT Student #: 1005735563
// Author: Oleksandr Voitovych
//
// Student4:
// UTORID user_name: meharch6
// UT Student #: 1003963570
// Author: Kevin Meharchand
//
//
// Honor Code: I pledge that this program represents my own
// program code and that I have coded on my own. I received
// help from no one in designing and debugging my program.
// I have also read the plagiarism section in the course info
// sheet of CSC B07 and understand the consequences.
// *********************************************************

package jShell.commands;

import jShell.errors.*;
import jShell.interfaces.IShell;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Save class handles all operations related to save command, namely, checks if the arguments are
 * valid arguments for save command and saves the current state of the shell to the specified file
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class Save extends Command {

  /**
   * Run save with given arguments
   * 
   * @param args Arguments for the save
   * @return output of save
   * @throws ArgsNumberException if number of arguments is invalid
   * @throws FileNotFoundException if file is not found at specified location
   * 
   */
  @Override
  public String run(String[] args)
      throws ArgsNumberException, FileNotFoundException {

    if (args.length != 2) {
      throw new ArgsNumberException();
    }

    try {
      // Writing the state of the Shell to the file
      FileOutputStream file = new FileOutputStream(args[1]);
      ObjectOutputStream out = new ObjectOutputStream(file);
      out.writeObject(IShell.getShell().getFileSystem().getRootDir());
      out.writeObject(IShell.getShell().getUserData());
      out.close();
    } catch (IOException e) {
      throw new FileNotFoundException(args[1]);
    }

    return "";
  }

  /**
   * Returns the name of the save command
   * 
   * @return save
   */
  @Override
  public String toString() {
    return "save";
  }
}
