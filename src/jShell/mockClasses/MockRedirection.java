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

package jShell.mockClasses;

import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;
import jShell.fileSystem.FileSystemNode;
import jShell.fileSystem.Path;
import jShell.interfaces.*;

/**
 * Mock version of Redirection class
 * 
 * @author Sasha (Oleksandr)
 */
public class MockRedirection implements IRedirection {

  // Reference to the mock shell object, where the redirection is happening
  MockShell shell;

  /**
   * Constructor of mock redirection object
   * 
   * @param shell Reference to MockShell object in which the redirection is happening
   */
  public MockRedirection(MockShell shell) {

    this.shell = shell;
  }

  /**
   * Method to mock redirection of output. Checks redirection for validity and throws appropriate
   * exceptions if invalid. If valid, outputs appropriate message to mock shell.
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
  @Override
  public void fileHandling(String operation, String path, String contents)
      throws InvalidPathException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    Path p = new Path(path);
    String nameCheck = p.getBottomName();
    // Check fileName for validity
    if (!FileSystemNode.nameChecker(nameCheck)) {
      throw new BadFileNameException();
    }
    if (p.isValidPath() && p.isPathToDirectory()) {
      throw new NodeExistsException(p.toString());
    }
    if (!p.getParentPath().isValidPath()
        || !p.getParentPath().isPathToDirectory()) {
      throw new InvalidPathException(p.toString());
    }
    if (operation.equals(">")) {
      shell.outputToShell("Redirecting output for overwriting:" + contents
          + "\n to file with name: " + path);
    } else if (operation.equals(">>")) {
      shell.outputToShell("Redirecting output for appending:" + contents
          + "\n to file with name: " + path);
    } else {
      shell.outputToShell("operation for redirection was not recognized");
      throw new RedirectionOperatorsException(operation);
    }
  }

  /**
   * The CheckRedirection method analyzes the two given parameters of operator and filename to see if
   * they are valid for redirection, returning a boolean representing this check.
   * 
   * @param operator The operator argument to be checked for validity
   * @param filename The filename to be checked for validity
   * @return A boolean value representing if the given parameters are valid
   */
  @Override
  public boolean checkRedirection(String operator, String filename) {
    if (!((operator.equalsIgnoreCase(">"))
        || operator.equalsIgnoreCase(">>"))) {
      return false;
    }
    if (!FileSystemNode.nameChecker(filename)) {
      return false;
    }
    return true;
  }
}
