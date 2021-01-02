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

package jShell.interfaces;

import java.util.ArrayList;
import java.util.Stack;
import jShell.errors.InvalidPathException;
import jShell.errors.UnknownErrorException;

/**
 * Interface for UserData class and it's mock version
 * 
 * @author Sasha (Oleksandr)
 */

public interface IUserData {

  /**
   * Method to get history of executed command
   * 
   * @return reference to command history
   */
  public ArrayList<String> getCommandHistory();

  /**
   * Method to get path to current working directory
   * 
   * @return path to current working directory
   */
  public IPath getCurPath();

  /**
   * Method to set path to current working directory to new value
   * 
   * @param curPath Path to new working directory
   * @throws InvalidPathException If path is invalid, or points to a file
   */
  public void setCurPath(IPath curPath) throws InvalidPathException;

  /**
   * Method to get current working directory
   * 
   * @return reference to current working directory
   */
  public IDirectory getCurDir();

  /**
   * Method to get path stack
   * 
   * @return Reference to the path stack
   */
  public Stack<IPath> getPathStack();
}
