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

package jShell.fileSystem;

import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;
import jShell.interfaces.IPath;
import jShell.interfaces.IRedirection;
import jShell.interfaces.IShell;

/**
 * A class dedicated to handling file operations related to the redirection of command outputs to
 * files.
 * 
 * @author Kevin Meharchand
 *
 */
public class Redirection implements IRedirection {

  IShell shell;

  public Redirection() {
    this.shell = IShell.getShell();
  }

  /**
   * Deals with file-related operations such as appending, overwriting, or even creating new files
   * with the output of a given command.
   * 
   * @param operation : the desired operation to be performed on a file, either appending or
   *        overwriting
   * @param path : the file (or path to file) provided by the user
   * @param contents : the contents to overwrite/append with
   * @throws InvalidPathException
   * @throws NodeExistsException
   * @throws BadFileNameException
   * @throws RedirectionOperatorsException
   * @throws FileNotFoundException
   */
  public void fileHandling(String operation, String path, String contents)
      throws InvalidPathException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    // Locate file based on the next argument
    Path p = new Path(path);
    String nameCheck = p.getBottomName();
    // Check fileName for validity
    if (!FileSystemNode.nameChecker(nameCheck)) {
      throw new BadFileNameException();
    }
    // Check if a directory with that name already exists
    FileSystemNode parent =
        (FileSystemNode) shell.getFileSystem().getNodeAtPath(p.getParentPath());
    if (parent.ifChildExists(nameCheck)) {
      if (!(((Directory) parent).getChild(
          p.getSequence()[p.getSequence().length - 1]) instanceof File)) {
        throw new NodeExistsException(path);
      }
    }
    // Attempt to get the file node at the user's given path
    FileSystemNode temp;
    try {
      temp = (FileSystemNode) shell.getFileSystem().getNodeAtPath(p);
    } catch (InvalidPathException e) {
      temp = (FileSystemNode) shell.getFileSystem().createNewFile(p);
      // Create the file if it doesn't exist
      if (temp == null) {
        throw new FileNotFoundException(p);
      }
    }
    // From this point, file exists
    // See if we are appending or overwriting
    if (operation.equalsIgnoreCase(">")) {
      overwriteFile(contents, (File) temp);
      return;
    } else if (operation.equalsIgnoreCase(">>")) {
      appendFile(contents, (File) temp);
      return;
    } else {
      // Error
      throw new RedirectionOperatorsException();
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
  public boolean checkRedirection(String operator, String pathToFile) {
    String actualFileName;
    /*
     * A string can be considered a relative/full path if it contains a slash. If no slash is in the
     * string, current directory is assumed.
     */
    if (pathToFile.indexOf("/") != -1) {
      IPath temp = new Path(pathToFile);
      actualFileName = temp.getBottomName();
    } else {
      actualFileName = pathToFile;
    }
    if (!((operator.equalsIgnoreCase(">"))
        || operator.equalsIgnoreCase(">>"))) {
      return false;
    }
    if (!FileSystemNode.nameChecker(actualFileName)) {
      return false;
    }
    return true;
  }

  /**
   * Overwrites the contents of a given file, replacing them with the provided user string
   * 
   * @param newContents: the string that will overwrite the contents of file.
   * @param file : the file to be overwritten
   */
  private void overwriteFile(String newContents, File file) {

    file.setContents(newContents);
  }

  /**
   * Appends the user's given string to the contents of a user-given file.
   * 
   * @param addition : the string to be appended to the file's contents
   * @param file : the file to have the string appended on to.
   */
  private void appendFile(String addition, File file) {

    if (file.getContents() == null) {
      file.setContents("");
    }
    file.setContents(file.getContents() + addition);
  }

}
