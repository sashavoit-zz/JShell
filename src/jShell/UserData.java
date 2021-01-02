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

package jShell;

import jShell.errors.InvalidPathException;
import jShell.errors.UnknownErrorException;
import jShell.fileSystem.Path;
import jShell.interfaces.IDirectory;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;
import jShell.interfaces.IUserData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Stack;

/**
 * A class to get user's command history, current directory and directory stack.
 * 
 * @author Raz
 */
public class UserData implements IUserData, Serializable {

  private static final long serialVersionUID = 1L;

  private ArrayList<String> commandHistory;

  public Stack<IPath> getPathStack() {
    return pathStack;
  }

  private IPath curPath;

  private Stack<IPath> pathStack;

  /**
   * Constructs a new UserData object which includes commandHistory, pathStack and sets curPath.
   * 
   * @param curPath Current path to working directory
   */
  public UserData(IPath curPath) {

    commandHistory = new ArrayList<String>();
    pathStack = new Stack<IPath>();
    this.curPath = curPath;
  }

  /**
   * Returns command history.
   * 
   * @return history of commands
   */
  public ArrayList<String> getCommandHistory() {

    return commandHistory;
  }

  /**
   * Gets the current path.
   * 
   * @return path pointing to current working directory
   */
  public IPath getCurPath() {

    return curPath;
  }

  /**
   * Sets curPath pointing to current working directory.
   * 
   * @param curPath path pointing to new working directory
   */
  public void setCurPath(IPath curPath) throws InvalidPathException {
    if (!curPath.isValidFullPath()) {
      curPath = (Path) curPath.convertRelativeToFull();
    }
    if (!curPath.isValidPath()) {
      throw new InvalidPathException(curPath);
    }
    this.curPath =
        IShell.getShell().getFileSystem().getNodeAtFullPath(curPath).getPath();
  }

  /**
   * Gets the current directory.
   * 
   * @return current directory
   * @throws UnknownErrorException (shouldn't occur)
   */
  public IDirectory getCurDir() {
    try {
      return (IDirectory) IShell.getShell().getFileSystem()
          .getNodeAtPath(curPath);
    } catch (InvalidPathException e) {
      //Shouldn't ever happen
    }
    return null;
  }

}
