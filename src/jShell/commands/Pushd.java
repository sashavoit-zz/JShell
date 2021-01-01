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
import jShell.fileSystem.Path;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;

/**
 * A class to implement the PushdTest command (Saves the current working directory by pushing onto
 * directory stack and then changes the new current working directory to DIR).
 * 
 * @author Raz
 */
public class Pushd extends Command {  

  @Override
  public String run(String[] args) throws InvalidPathException,
  ArgsNumberException, DirNotFoundException {
    
    // Checking if given location corresponds to a valid location at current file
    // If it is, add args[1] to stack
    // Else, print the corresponding error message.
    isValid(args);
    if(args.length != 2) {
      throw new ArgsNumberException();
    }
    IPath pushPath = new Path(args[1]);
    if (!pushPath.isValidFullPath()) {
      if (!pushPath.isValidRelativePath()) {
        throw new InvalidPathException(pushPath);
      }
      pushPath = (new Path(args[1])).convertRelativeToFull();
    }
    IShell.getShell().getUserData().getPathStack().add(pushPath);
    IShell.getShell().getUserData().setCurPath(pushPath);
    return "";
  }

  /**
   * Throws DirNotFoundException ArgsNumberException if necessary 
   * @param args
   * @throws DirNotFoundException
   * @throws ArgsNumberException
   */
  public void isValid(String[] args)
      throws DirNotFoundException, ArgsNumberException {
    // Checking that correct number of arguments is specified
    if (args.length != 2) {
      throw new ArgsNumberException();
    }
    IPath path = new Path(args[1]);
    // Checking that specified path exists and is a path to directory
    if (!path.isValidPath() || !path.isPathToDirectory()) {
      throw new DirNotFoundException(path);
    }
  }

  @Override
  public String toString() {
    return "pushd";
  }

}
