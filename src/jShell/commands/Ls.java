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
import jShell.interfaces.IShell;
import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;
import jShell.errors.UnknownErrorException;
import jShell.fileSystem.Path;
import jShell.interfaces.IDirectory;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IPath;
import jShell.interfaces.IRedirection;

/**
 * Ls class handles all operations related to ls command, namely checks if arguments are valid
 * arguments for ls command and outputs the contents of specified location or multiple locations in
 * the file system tree.
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class Ls extends Command {

  /**
   * For every given path, if path points to directory, print the contents of the directory, if path
   * points to the file print the path of the file. This method assumes all arguments are valid
   * 
   * @return the output of the command
   * @throws InvalidPathException if path for directory is invalid
   * @throws BadFileNameException if name of directory is invalid
   * @throws NodeExistsException if such directory already exists
   * @throws FileNotFoundException if file for redirection is invalid
   * @throws RedirectionOperatorsException if format of redirection is invalid
   * @throws UnknownErrorException shouldn't happen
   */
  public String run(String[] args) throws InvalidPathException,
      BadFileNameException, RedirectionOperatorsException, RedirectionException,
      FileNotFoundException, NodeExistsException, UnknownErrorException {

    IRedirection r = IShell.getShell().getRedirection();

    // Getting rid of first argument, which is Ls
    args = Arrays.copyOfRange(args, 1, args.length);

    // Determining if contents should be listed recursively, if redirection is
    // happening and if any path arguments are provided
    boolean listRecursively = false;
    boolean redirect = false;
    String operator = null;
    String fileName = null;
    String output = "";

    if (args.length != 0 && args[0].equals("-R")) {
      // -R is found
      listRecursively = true;
      args = Arrays.copyOfRange(args, 1, args.length);
    }

    if (Arrays.asList(args).contains(">")
        || Arrays.asList(args).contains(">>")) {
      // Redirection is found
      redirect = true;
      if (args.length > 1) {
        // Getting the operator
        operator = args[args.length - 2];
        // Getting the name of the file
        fileName = args[args.length - 1];
        this.checkRedirection(operator, fileName);
        args = Arrays.copyOfRange(args, 0, args.length - 2);
      } else {
        throw new RedirectionException();
      }
    }

    // Now only path arguments are left is args
    if (args.length == 0) {
      // 0 arguments means list contents of working directory
      if (listRecursively) {
        output += this.listContentsRecursively(new Path("."));
      } else {
        IDirectory curDir = IShell.getShell().getUserData().getCurDir();
        for (IFileSystemNode node : curDir.getChildren()) {
          output += node.getName() + "\n";
        }
      }
    } else {
      for (String arg : args) {
        // Listing contents for every argument of type path
        Path path = new Path(arg);
        if (!path.isValidPath()) {
          throw new InvalidPathException(arg);
        }
        if (listRecursively) {
          output += this.listContentsRecursively(path);
        } else {
          output += this.listContents(path);
        }
      }
    }

    // Removing an extra newline character from the end of output
    if (output.length() != 0) {
      output = output.substring(0, output.length() - 1);
    }

    if (redirect) {
      // Redirecting output
      r.fileHandling(operator, fileName, output);
      return "";
    }

    return output;
  }

  /**
   * Return the name of the command
   * 
   * @return The name of the command, i.e. ls
   */
  @Override
  public String toString() {

    return "ls";

  }

  /**
   * Return string of contents of the node at given path
   * 
   * @param p Path to the node
   * @throws InvalidPathException if path is invalid
   */
  private String listContents(IPath p) throws InvalidPathException {
    String result = "";
    // Checking if specified path is a directory or a file
    if (p.isPathToDirectory()) {
      /*
       * Path points to the directory
       */
      // Getting reference to corresponding directory
      IDirectory dir =
          (IDirectory) IShell.getShell().getFileSystem().getNodeAtPath(p);
      // Printing name of the directory
      result += p.toString() + ":\n";
      // Printing contents
      for (IFileSystemNode child : dir.getChildren()) {
        result += child.getName() + "\n";
      }
      result += "\n";
    } else {
      /*
       * Path points to the file
       */
      result += p.toString() + "\n";
    }
    return result;
  }

  /**
   * Return string of contents of the node at given path listed recursively
   * 
   * @param p Path to the node
   * @throws InvalidPathException if path is invalid
   */
  private String listContentsRecursively(IPath p) throws InvalidPathException {
    String result = "";
    if (p.isPathToDirectory()) {
      // p points to directory
      result += this.listContents(p);
      // Iterate through children of p
      IDirectory dir =
          (IDirectory) IShell.getShell().getFileSystem().getNodeAtPath(p);
      for (IFileSystemNode node : dir.getChildren()) {
        result +=
            this.listContentsRecursively(new Path(p + "/" + node.getName()));
      }
    } else {
      // p points to a file
      result += this.listContents(p);
    }
    return result;
  }

  /**
   * Check if redirection is valid, throw appropriate exception if invalid
   * 
   * @param operator Operator for redirection (either > or >>)
   * @param path Path to file for output to be redirected
   * @throws InvalidPathException if path to file is invalid
   * @throws FileNotFoundException if path to file points to directory
   */
  private void checkRedirection(String operator, String path)
      throws InvalidPathException, FileNotFoundException {

    IPath p = new Path(path);
    IPath parent = p.getParentPath();

    if (operator.equals(">")) {
      if (!parent.isValidPath() || !parent.isPathToDirectory()) {
        throw new InvalidPathException(path);
      }
    }

    if (operator.equals(">>")) {
      if (!p.isValidPath() || p.isPathToDirectory()) {
        throw new FileNotFoundException(p);
      }
    }
  }

}
