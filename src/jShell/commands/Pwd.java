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
import jShell.interfaces.IRedirection;
import jShell.interfaces.IShell;

/**
 * The pwd class prints out the current working directory that the user is in. It takes in no
 * additional arguments, other than the command name. It also supports redirection into a
 * file(appending or overwrite)
 * 
 * @author Sankalp Sharma
 **/

public class Pwd extends Command {

  /**
   * The run method executes if correct arguments are entered. It gets the current path that the User
   * is in. It will throw an error for any arguments after pwd or redirection errors such as bad file
   * name or invalid arguments for redirection.
   * 
   * @param args Arguments for the command
   * @return String output of the pwd command (current working directory), otherwise nothing if
   *         redirection happened correctly
   * 
   * @throws InvalidPathException if path to file is invalid
   * @throws BadFileNameException entering an invalid file name when redirecting
   * @throws NodeExistsException
   * @throws FileNotFoundException
   * @throws RedirectionOperatorsException not using the redirection operators correctly
   * @throws ArgsNumberException using too many arguments
   * 
   */
  public String run(String args[]) throws InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, ArgsNumberException, RedirectionException {

    // Throw exception right away
    argChecker(args);
    IRedirection r = IShell.getShell().getRedirection();

    // assuming that the arguments are correct. Just get current path and print it
    String outputStr = IShell.getShell().getUserData().getCurPath().toString();

    // check for redirection
    if (args.length == 3
        && (args[1].equalsIgnoreCase(">") || args[1].equalsIgnoreCase(">>"))) {
      r.fileHandling(args[1], args[2], outputStr);
    }
    // print w/o redirection
    else {
      return outputStr;
    }
    return "";
  }

  private void argChecker(String args[])
      throws ArgsNumberException, RedirectionException {

    // can only pass 1 or 3 args (redirection), anything else is invalid
    if (args.length != 1 && args.length != 3) {

      throw new ArgsNumberException();
      // if doing 3 args, make sure redirection is done correctly
    } else if (args.length == 3 && (!(args[1].equalsIgnoreCase(">")
        || args[1].equalsIgnoreCase(">>")))) {
      throw new RedirectionException();
    }
  }

  @Override
  public String toString() {

    return "pwd";
  }
}
