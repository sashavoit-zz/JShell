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

import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;

/**
 * Interface for redirection class and it's mock version
 * 
 * @author Sasha (Oleksandr)
 */

public interface IRedirection {
  /**
   * Deals with file-related operations such as appending, overwriting, or even creating new files
   * with the output of a given command.
   * 
   * @param operation : the desired operation to be performed on a file, either appending or
   *        overwriting
   * @param fileName : the file (or path to file) provided by the user
   * @param contents : the contents to overwrite/append with
   * @throws InvalidPathException if path to file is invalid
   * @throws NodeExistsException if file already exists
   * @throws BadFileNameException if file being created has bad name
   * @throws RedirectionOperatorsException if operator is invalid
   * @throws FileNotFoundException if file for appending is not found
   */
  public void fileHandling(String operation, String fileName, String contents)
      throws InvalidPathException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException;

  /**
   * The CheckRedirection method analyzes the two given parameters of operator and filename to see if
   * they are valid for redirection, returning a boolean representing this check.
   * 
   * @param operator The operator argument to be checked for validity
   * @param filename The filename to be checked for validity
   * @return A boolean value representing if the given parameters are valid
   */
  public boolean checkRedirection(String operator, String filename);

}
