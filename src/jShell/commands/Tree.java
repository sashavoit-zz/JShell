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
import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;
import jShell.interfaces.IFileSystem;
import jShell.interfaces.IRedirection;
import jShell.interfaces.IShell;


/**
 * Tree command prints out the entire FileSystem hierarchy, starting from the root. Each level is
 * indented by a tab character. Tree also supports redirection, a user can redirect the output to a
 * file.
 * 
 * @author Sankalp Sharma
 */

public class Tree extends Command {

  /**
   * The run method of Tree calls the toString method of FileSystem. It checks for redirection and
   * stores the output into a file, otherwise it prints to the console. It throws exception if there
   * additional arguments after the tree command (excluding redirection). It also throws Redirection
   * exceptions such as bad file name or invalid redirection
   * 
   * @param args Arguments for tree
   * @return output of tree command (String representation of FileSystem), otherwise an empty string
   *         if redirection has been performed successfully
   * 
   * @throws InvalidPathException if path to file is invalid
   * @throws ArgsNumberException too many arguments entered after tree
   * @throws NodeExistsException
   * @throws BadFileNameException invalid file name when trying to redirect
   * @throws RedirectionOperatorsException not using the redirection operators properly
   * @throws FileNotFoundException
   * @throws RedirectionException Incorrect order of redirection arguments
   */

  public String run(String[] args)
      throws InvalidPathException, ArgsNumberException, NodeExistsException,
      BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {

    argChecker(args);

    // create redirection, filesystem objects as required
    IRedirection r = IShell.getShell().getRedirection();
    IFileSystem f = IShell.getShell().getFileSystem();

    // call filesystem's recursive method
    String outputStr = f.toString();

    if (args.length == 3
        && (args[1].equalsIgnoreCase(">") || args[1].equalsIgnoreCase(">>"))) {
      r.fileHandling(args[1], args[2], outputStr);
    }
    // just print normally
    else {
      return outputStr;
    }
    return "";
  }

  private void argChecker(String args[])
      throws ArgsNumberException, RedirectionException {
    // can only pass 1 or 3 args (redirection), anything else is invalid
    if (args.length != 1 && args.length != 3) {
      // if doing 3 args, make sure redirection is done correctly
      throw new ArgsNumberException();
    } else if (args.length == 3 && (!(args[1].equalsIgnoreCase(">")
        || args[1].equalsIgnoreCase(">>")))) {
      throw new RedirectionException();
    }
  }

  @Override
  public String toString() {

    return "tree";
  }
}
