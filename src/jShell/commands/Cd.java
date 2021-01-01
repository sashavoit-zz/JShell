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

import jShell.errors.ArgsNumberException;
import jShell.errors.DirNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.fileSystem.Directory;
import jShell.fileSystem.Path;
import jShell.interfaces.IShell;
import jShell.interfaces.IDirectory;

/**
 * The Cd command is used for changing directories. It will throw an error if the directory doesn't
 * exist or path is invalid or if you try to change to more than one directory at same time
 * 
 * @author Sankalp Sharma
 * 
 */
public class Cd extends Command {

  /**
   * The run method for CD uses the getNodeAtPath method from the FileSystemclass to determine the
   * right course of action when changing directories such as: going up one level, staying at current
   * level, and changing directories with absolute and relative paths.
   * 
   * @param args Arguments for command
   * @return output of Cd.
   * @throws ArgsNumberException
   * @throws DirNotFoundException
   * @throws InvalidPathException
   * 
   */
  @Override
  public String run(String[] args)
      throws DirNotFoundException, ArgsNumberException, InvalidPathException {
    // catch errors associated with invalid arguments
    argChecker(args);

    // assuming all the arguments are correct, go into ParseDir
    IDirectory dirDestination;
    try {
      dirDestination = (IDirectory) IShell.getShell().getFileSystem()
          .getNodeAtPath(new Path(args[1]));
    } catch (ClassCastException e) {
      throw new DirNotFoundException(args[1]);
    }
    /*
     * special case if when the the destination directory path is null, then we just want to manually
     * set it to root path
     */
    if (dirDestination.getPath() == null) {
      IShell.getShell().getUserData().setCurPath(new Path("/"));

      // else we actually the set the current path to the destination directory
    } else {
      IShell.getShell().getUserData().setCurPath(dirDestination.getPath());
    }
    return "";
  }

  private void argChecker(String args[]) throws ArgsNumberException {
    // can't do more than one directory at a time
    if (args.length != 2) {
      throw new ArgsNumberException();
    }
  }

  @Override
  public String toString() {

    return "cd";
  }
}
