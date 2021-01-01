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

import jShell.errors.ArgsException;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadStringException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;
import jShell.interfaces.IRedirection;
import jShell.interfaces.IShell;

/**
 * The Echo class handles all operations related to the Echo command. It inherits some basic methods
 * from the Command class in order to function properly within the Shell system as a whole.
 * 
 * Echo displays a user-given string in the shell.
 * 
 * Echo's output can be redirected into a file if specified by the user.
 * 
 * @author Kevin Meharchand
 *
 */
public class Echo extends Command {

  /**
   * The overridden run method inherited from the Command class. Acts as a main hub, calling helper
   * functions when necessary, otherwise keeping track of which argument is currently being parsed and
   * handled.
   * 
   * @return The string entered by the user, otherwise nothing if the string was redirected into a
   *         file.
   * @throws BadStringException
   * @throws ArgsNumberException
   * @throws RedirectionOperatorsException
   * @throws RedirectionException
   */
  @Override
  public String run(String[] args) throws BadStringException, ArgsException,
      ArgsNumberException, RedirectionOperatorsException, RedirectionException {
    isValid(args);
    String fullString = ""; // Initializations
    String currentString = "";
    String procedure;
    boolean quotes;
    for (int i = 1; i < args.length; i++) { // Loop each argument
      currentString = args[i]; // Take one part of the string at a time
      if (i == 1) { // Check the start of the string
        fullString = handleFirstString(currentString, args);
        if (fullString == null) { // If anything went wrong, end
          return "";
        } else if (fullString.equals(" ")) {
          return " ";
        }
        quotes = quoteCheck(currentString, args, i); // Check if the string is complete
        procedure = checkProcedure(quotes, i, args, fullString);
        if (procedure.equals("PRINT")) {
          return fullString; // Successfully completed the command, end
        } else if (procedure.equals("FILE")) {
          return "";
        }
      } else { // Check other arguments
        quotes = quoteCheck(currentString, args, i); // Check completion
        currentString = handleOtherStrings(currentString, args, i);
        if (currentString == null) {
          return ""; // String error, end
        } else {
          fullString = fullString + currentString;
          procedure = checkProcedure(quotes, i, args, fullString);
          if (procedure.equals("PRINT")) {
            return fullString; // Successfully completed the command, end
          } else if (procedure.equals("FILE")) {
            return "";
          }
        }
      }
    } // If something went wrong
    throw new BadStringException();
  }

  /*
   * Method used to check the validity of arguments before code execution.
   */
  private void isValid(String[] args)
      throws ArgsNumberException, BadStringException {
    // Checking if correct number of arguments is passed
    if (args.length < 2) {
      throw new ArgsNumberException();
    }
    // Check for correctness of the string
    if (!args[1].startsWith("\"") || !args[1].startsWith("\"")) {
      // String must be surrounded by ""
      throw new BadStringException(args[1]);
    }
    // Check for a bad string
    if (args.length == 2 && args[1].length() < 2) {
      throw new BadStringException(args[1]);
    }
  }

  /*
   * Method used to determine if a string is complete or not
   */
  private boolean quoteCheck(String currentString, String[] args, int i)
      throws RedirectionOperatorsException {
    // Checks for if the user has passed in a completely empty string
    if (currentString.startsWith("\"") && currentString.endsWith("\"")
        && (currentString.substring(1).indexOf("\"") == currentString.length()
            - 2)) {
      if ((i == 1 && args.length == 2) || i > 1) { // Correctly terminated
        // single string or single quotes at the end of a long string
        return true;
      } else if (i == 1 && args.length == 4) {
        if (!(args[2].equals(">") || args[2].equals(">>"))) {
          throw new RedirectionOperatorsException();
        }
        return true;
      } else {
        return false;
      }
    } // Check to see if the string has properly been closed
    if ((currentString.endsWith("\""))
        && (currentString.indexOf("\"") == currentString.length() - 1)) {
      if (i == 1) {
        return false;
      } else {
        return true; // Correctly terminated, no quotes within
      }
    }
    return false;
  }

  /*
   * Method used to determine if redirection is valid or not
   */
  private String checkProcedure(boolean quotes, int i, String[] args,
      String fullString) throws ArgsException, RedirectionOperatorsException,
      RedirectionException {
    IRedirection r = IShell.getShell().getRedirection();
    if (i == args.length - 1 && quotes == true) { // No more arguments
      return "PRINT";
    }
    if ((quotes == true) && ((args[i + 1].equalsIgnoreCase(">"))
        || (args[i + 1].equalsIgnoreCase(">>")))) {
      // Check redirection operations
      if (checkOperatorAndFile(i, args) == true) {
        try { // If anything goes wrong, return nothing
          r.fileHandling(args[i + 1], args[i + 2], fullString);
        } catch (Exception nullPoint) {
          IShell.getShell().outputToShell("");
          throw new RedirectionException();
        }
        return "FILE";
      }
    } else if ((quotes == true) && (!((args[i + 1].equalsIgnoreCase(">"))
        || (args[i + 1].equalsIgnoreCase(">>"))))) {
      throw new RedirectionOperatorsException();
    }
    return "NONE";
  }

  /*
   * A set of instructions specifically designed for handling the first word in a multi-word string OR
   * a single-word string.
   */
  private String handleFirstString(String firstString, String[] args)
      throws BadStringException, ArgsException, RedirectionOperatorsException,
      RedirectionException {
    // For the cases of an entirely empty string
    if (args[1].equals("\"") && args[2].equals("\"") && args.length == 5) {
      if (checkProcedure(true, 2, args, " ").equals("FILE")) {
        return null;
      }
    } else if (args[1].equals("\"") && args[2].equals("\"")
        && args.length == 3) {
      return " ";
    }
    if (!(firstString.startsWith("\""))) { // Check for starting quotes
      throw new BadStringException();
      // Check for a properly terminated single string
    } else if (firstString.startsWith("\"") && firstString.endsWith("\"")) {
      try { // Try to return the truncated string
        return (firstString.substring(1, firstString.length() - 1));
      } catch (Exception onlyOneQuotation) {
        if (args.length == 2) { // If the user only entered one double quote
          throw new BadStringException();
        } else {
          return ""; // An empty string to be returned (signifying a space)
        }
      }
    }
    try { // Try to truncate the string
      return (firstString.substring(1));
    } catch (Exception onlyOneCharacter) {
      throw new BadStringException();
    }
  }

  /*
   * A set of instructions for handling subsequent strings inside of a multi- word string
   */
  private String handleOtherStrings(String currentString, String[] args, int i)
      throws BadStringException {
    if (currentString.indexOf("\"") == -1) { // No double quote
      if (i == args.length - 1) { // If this is the last argument, error
        throw new BadStringException();
      } else {
        return (" " + currentString); // Add to result
      }
    } else if ((currentString.endsWith("\""))
        && (currentString.indexOf("\"") == currentString.length() - 1)) {
      return (" " + currentString.substring(0, (currentString.length() - 1)));
    } else {
      // Quotations in the middle of a string
      throw new BadStringException(currentString);
    }
  }

  /*
   * A redirection pre-check to confirm the validity of a potential filename and redirection operator.
   */
  private boolean checkOperatorAndFile(int currentArg, String[] args)
      throws ArgsException {
    if ((currentArg + 1) == (args.length - 1)) {
      // Not enough arguments
      throw new ArgsException(
          "all. " + " Please add a file after your " + "desired operator.");
    } else if ((currentArg + 1) < (args.length - 2)) {
      // Too many arguments
      throw new ArgsException(
          "all. " + "Do not include multiple operators " + "or files/paths.");
    } else {
      return true;
    }
  }


  /**
   * Returns the name of the command.
   */
  @Override
  public String toString() {

    return "echo";
  }
}
