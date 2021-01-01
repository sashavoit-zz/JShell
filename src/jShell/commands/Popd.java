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
import jShell.errors.EmptyStackException;
import jShell.errors.InvalidPathException;
import jShell.interfaces.IShell;

/**
 * A class to implement the Popd command (Remove the top entry from the directory stack, and cd into
 * it).
 * 
 * @author Raz
 */
public class Popd extends Command {

  @Override
  public String run(String[] args)
      throws EmptyStackException, ArgsNumberException, InvalidPathException {
    isValid(args);
    // If stack is empty, print a corresponding error message
    if (IShell.getShell().getUserData().getPathStack().empty()) {
      throw new EmptyStackException();
    }
    // Else, pop the last item from the stack and cd into it
    IShell.getShell().getUserData()
        .setCurPath(IShell.getShell().getUserData().getPathStack().pop());;
    return "";
  }

  /**
   * Throws EmptyStackException and ArgsNumberException if necessary
   * 
   * @param args Arguments for command
   * @throws EmptyStackException
   * @throws ArgsNumberException
   */
  public void isValid(String[] args)
      throws EmptyStackException, ArgsNumberException {

    // Checking if correct number of arguments is passed
    if (args.length != 1) {
      throw new ArgsNumberException();
    }
    // Checking that stack is not empty
    if (IShell.getShell().getUserData().getPathStack().empty()) {
      throw new EmptyStackException();
    }
  }

  @Override
  public String toString() {

    return "popd";
  }
}
