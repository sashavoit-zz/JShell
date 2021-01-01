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

import java.util.ArrayList;
import jShell.errors.ArgsException;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NegativeNumberException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;
import jShell.interfaces.IRedirection;
import jShell.interfaces.IShell;

/**
 * The history command prints out the recent commands entered by the user, irrespective of their
 * correctness. It takes in a number as an optional argument. If no number is entered, then all the
 * commands entered by the user are displayed. If 0 is displayed, then the program does nothing. If
 * the number entered is greater than the number of commands entered, then everything is displayed.
 * History also supports redirection into a file (either appending or overwriting)
 * 
 * @author Sankalp Sharma
 * 
 **/
public class History extends Command {

  /**
   * The run method parses the arguments to determine how many user entered commands to print. The
   * History command will throw exceptions if non integer parameters are used or user uses a negative
   * number or tries to redirect into a file incorrectly. It will also throw an exception if incorrect
   * number of commands are used/
   * 
   * 
   * @param args Arguments for the command
   * @return Output of the appropriate number of commands or the output of redirection
   * @throws ArgsNumberException
   * @throws NegativeNumberException
   * @throws ArgsException
   */


  @Override
  public String run(String[] args) throws InvalidPathException,
      ArgsNumberException, NegativeNumberException, ArgsException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {

    // check arguments for validity
    argChecker(args);

    // initialize objects needed for history command
    IRedirection r = IShell.getShell().getRedirection();
    ArrayList<String> commandHistory =
        IShell.getShell().getUserData().getCommandHistory();
    int start = 0;

    // 1st case no optional number arguments. leave start as is
    if (args.length == 1 || (args[1].equals(">") || args[1].equals(">>"))) {
      start = 0;
    } else {
      int numberArg = 0;
      numberArg = Integer.parseInt(args[1]);
      // now check within range and start with the most Recent command
      if (numberArg > 0 && numberArg <= commandHistory.size()) {
        start = commandHistory.size() - numberArg;
      }
      // account case for 0 special case. Do nothing and return back to prompt
      else if (numberArg == 0)
        return "";
    }

    // loop through list, using correct value of start
    String outputStr = "";
    for (int i = start; i < commandHistory.size(); i++)
      outputStr += (i + 1) + "." + commandHistory.get(i) + "\n";

    // print regularly if no redirection
    if (args.length > 1 && args[args.length - 2].startsWith(">"))
      r.fileHandling(args[args.length - 2], args[args.length - 1], outputStr);
    else
      return outputStr;

    return "";
  }


  private void argChecker(String[] args) throws ArgsNumberException,
      NegativeNumberException, ArgsException, RedirectionException {

    int numParams = args.length;

    // check if last command is redirection
    if (args[args.length - 1].startsWith(">"))
      throw new RedirectionException();
    if (args.length > 2) {
      // check for redirection
      if ((args[1].equals(">") || args[1].equals(">>"))
          || (args[2].equals(">") || args[2].equals(">>"))) {
        // remove the count once you see redirection
        numParams = numParams - 2;
      }
    }

    // Checking if the correct number of arguments if passed
    if (numParams == 3 || numParams == 4)
      throw new ArgsNumberException();

    // If second argument is passed, check if it's a number
    else if (numParams > 1) {
      try {
        int numberArg = Integer.parseInt(args[1]);
        if (numberArg < 0) {
          throw new NegativeNumberException(args[1]);
        }
      } catch (NumberFormatException e) {
        throw new ArgsException(args[1]);
      }
    }

  }

  @Override
  public String toString() {

    return "history";

  }
}
