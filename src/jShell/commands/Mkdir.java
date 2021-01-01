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

import java.util.Arrays;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.fileSystem.FileSystemNode;
import jShell.fileSystem.Path;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;

/**
 * Mkdir class handles all operations related to mkdir command, namely, checks if the arguments are
 * valid arguments for mkdir command and creates a directory at specified location in the file
 * system tree.
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class Mkdir extends Command {

  /**
   * Create a new directory at a given path
   * 
   * @param args Arguments for command
   * @throws NodeExistsException if directory or file with given name already exists
   * @throws InvalidPathException if path for new directory is invalid
   */
  @Override
  public String run(String args[]) throws InvalidPathException,
      NodeExistsException, ArgsNumberException, BadFileNameException {

    // Getting rid of the first argument, which is mkdir
    args = Arrays.copyOfRange(args, 1, args.length);

    if (args.length < 1) {
      throw new ArgsNumberException();
    }

    for (String arg : args) {
      // Throws exception if at least one of the arguments is invalid
      checkValidArg(arg);
    }

    for (String arg : args) {
      // Path of the directory to be created
      IPath path;
      path = new Path(arg);
      // Creating a new directory
      IShell.getShell().getFileSystem().createNewDirectory(path);
    }

    // Mkdir has no output
    return "";
  }

  /**
   * Method to check validity of single path argument
   * 
   * @param arg Argument for mkdir command
   * @throws InvalidPathException if path is invalid
   * @throws NodeExistsException if node at path already exists
   * @throws BadFileNameException if name for new directory is invalid
   */
  private static void checkValidArg(String arg)
      throws InvalidPathException, NodeExistsException, BadFileNameException {
    IPath path = new Path(arg);

    // Checking if path contains two or more consecutive slashes
    if (!path.doubleSlashChecker()) {
      throw new InvalidPathException(arg);
    }

    // Checking if directory with given name already exists
    if (path.isValidPath()) {
      throw new NodeExistsException(arg);
    }

    // Getting the parent of the directory being created
    IPath parent = path.getParentPath();
    // Checking if parent exists
    if (!parent.isValidPath()) {
      throw new InvalidPathException(arg);
    }

    // Checking that parent is a directory
    if (!parent.isPathToDirectory()) {
      throw new NodeExistsException(arg);
    }

    // Checking if the name of directory being created is valid name
    String name = path.getBottomName();
    if (!FileSystemNode.nameChecker(name)) {
      throw new BadFileNameException(name);
    }
  }

  /**
   * Return the name of the command
   * 
   * @return The name of the command, i.e. mkdir
   */
  @Override
  public String toString() {
    return "mkdir";
  }
}
