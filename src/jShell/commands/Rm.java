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
import jShell.errors.DeleteRootDirException;
import jShell.errors.InvalidPathException;
import jShell.errors.RemovingFileException;
import jShell.errors.WorkingDirException;
import jShell.fileSystem.File;
import jShell.fileSystem.Path;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;

/**
 * Rm class checks for valid Arguments for Rm command, such as not deleting root or trying to delete
 * more than one directory. These checks are performed in the argchecker function
 * 
 * @author Sankalp Sharma
 */
public class Rm extends Command {

  /**
   * Rm command removes a directory from a file system and all its contents. It throws an exception
   * when trying to delete current working directory or root or a file that is not part of a
   * directory.
   * 
   * @throws WorkingDirException if trying to delete current working directory
   * @throws InvalidPathException if trying to delete a directory that doesn't exist
   * @throws DeleteRootDirException if trying to delete root Directory
   * @throws ArgsNumberException if trying to remove more than one directory at a time
   * @throws RemovingFileException if trying to remove a file that is not part of a directory
   * 
   */

  public String run(String[] args)
      throws WorkingDirException, InvalidPathException, DeleteRootDirException,
      ArgsNumberException, RemovingFileException {

    // catch errors that can be caught right away
    argChecker(args);

    // parse the argument and get currpath
    String delStr = args[1];
    String currPath = IShell.getShell().getUserData().getCurPath().toString();

    // get the Node object that the user is trying to delete and make sure its not a File
    IFileSystemNode getNode =
        IShell.getShell().getFileSystem().getNodeAtPath(new Path(delStr));
    if (getNode instanceof File) {
      throw new RemovingFileException();
    }

    // get the Abs path of the node to be Delete
    delStr = getNode.getPath().toString();

    /*
     * compare the path of node to be deleted and currPath and give an error if user tries to delete the
     * current working directory
     */
    if (currPath.startsWith(delStr)) {
      throw new WorkingDirException();
    }

    // if everything is valid then pass delStr as a path onto removeDir
    IPath toDel = new Path(delStr);
    IFileSystemNode delNode =
        IShell.getShell().getFileSystem().removeNode(toDel);
    return "";
  }

  private void argChecker(String[] args)
      throws DeleteRootDirException, ArgsNumberException {

    // Checking that correct number of arguments is passed
    if (args.length != 2) {
      throw new ArgsNumberException();
    }

    // trying to delete root
    if (args[1].equals("/")) {
      throw new DeleteRootDirException();
    }
  }

  @Override
  public String toString() {

    return "rm";
  }
}
