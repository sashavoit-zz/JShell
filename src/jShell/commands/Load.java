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

import jShell.interfaces.IDirectory;
import jShell.interfaces.IShell;
import jShell.UserData;
import jShell.errors.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Load class handles all operations related to load command, namely, checks if the arguments are
 * valid arguments for load command and load the state of Shell from the specified file in
 * computer's memory
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class Load extends Command {

  /**
   * Run load with given arguments
   * 
   * @param args Arguments for the load
   * @return output of load
   * @throws ArgsNumberException if number of arguments is invalid
   * @throws FileNotFoundException if file is not found at specified location
   * @throws UnknownErrorException if unknown unexpected error occurred (should not ever occur)
   */
  @Override
  public String run(String[] args) throws LoadNotFirstException,
      ArgsNumberException, FileNotFoundException, UnknownErrorException {

    if (IShell.getShell().getUserData().getCommandHistory().size() > 1) {
      throw new LoadNotFirstException();
    }

    if (args.length != 2) {
      throw new ArgsNumberException();
    }

    try {
      // Retrieving data from the file with state of the Shell
      FileInputStream file = new FileInputStream(args[1]);
      ObjectInputStream in = new ObjectInputStream(file);
      IDirectory newRoot = (IDirectory) in.readObject();
      UserData newUD = (UserData) in.readObject();

      // Getting the load command call
      String loadCall = getLoadCall(args);

      // Setting UserData and FileSystem to retrieved UserData and FileSystem
      IShell.getShell().setUserData(newUD);
      IShell.getShell().getFileSystem().setRootDir(newRoot);

      // Adding the call to load command to command history
      IShell.getShell().getUserData().getCommandHistory().add(loadCall);
      in.close();
    } catch (IOException e) {

      // Failure finding the file
      throw new FileNotFoundException(args[1]);
    } catch (ClassNotFoundException e) {

      // This should not happen, as we have IDirectory and UserData defined
      throw new UnknownErrorException();
    }
    // No output produced
    return "";
  }

  /**
   * Method to get the String representing a call of load command from the arguments for load command
   * 
   * @param args Arguments for load command
   * @return string representation of call to load
   */
  private String getLoadCall(String[] args) {
    String loadCall = "";

    for (String arg : args) {
      loadCall += arg + " ";
    }

    loadCall = loadCall.substring(0, Math.max(0, loadCall.length() - 1));
    return loadCall;
  }

  /**
   * Returns the name of load command
   * 
   * @return the name of load command
   */
  @Override
  public String toString() {

    return "load";
  }
}
