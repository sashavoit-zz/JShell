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

package jShell.interfaces;

import jShell.errors.Error;

/**
 * Abstract class for shell class and it's mock version
 * 
 * @author Sasha (Oleksandr)
 */

public abstract class IShell {

  /**
   * Returns the fileSystem
   * 
   * @return The main FileSystem for the shell
   */
  public abstract IFileSystem getFileSystem();

  /**
   * Single shell object (either Shell or MockShell) to be accessed by all classes
   */
  private static IShell shell;

  /**
   * Method to get reference to single shell object (Shell of MockShell)
   * 
   * @return reference to single shell object
   */
  public static IShell getShell() {

    return shell;
  }

  /**
   * Default constructor
   */
  protected IShell() {
    shell = this;
  }

  /**
   * Returns userData
   * 
   * @return The main UserData object for the shell
   */
  public abstract IUserData getUserData();

  /**
   * Shuts down the shell
   */
  public abstract void shutDown();

  /**
   * Returns the input the user typed in the shell
   * 
   * @return The input the user typed in the shell
   */
  public abstract String getUserInput();

  /**
   * Output a value to the shell on the same line
   * 
   * @param The value to be output to the shell
   */
  public abstract void outputToShell(String value);


  /**
   * Output a value to the shell on the same line with text of custom color
   * 
   * @param The value to be output to the shell
   */
  public abstract void outputToShell(String value, String color);

  /**
   * Output a value to the shell with newline at the end and text of custom color
   * 
   * @param The value to be output to the shell
   */
  public abstract void outputToShellln(String value, String color);

  /**
   * Processes user's input.
   * 
   * @param The input the user typed to the shell
   * @throws Error
   */
  public abstract void onInput(String input);

  /**
   * Returns Whether the program is running or not
   * 
   * @return Whether the program is running or not
   */
  public abstract boolean isRunning();

  /**
   * Sets running to isRunning
   * 
   * @param The boolean value of isRunning
   */
  public abstract void setIsRunning(boolean isRunning);

  /**
   * Get a reference to the redirection object from shell
   * 
   * @return Reference to redirection
   */
  public abstract IRedirection getRedirection();

  /**
   * Method to output string to Shell
   * 
   * @param value String to be outputted
   */
  public abstract void outputToShellln(String value);

  /**
   * Method to output errors to Shell.
   * 
   * @param error Error to be outputted
   */
  public abstract void outputToShellErr(String error);

  /**
   * Method to set user data to new UserData or MockUserData object.
   * 
   * @param data New user data object
   */
  public abstract void setUserData(IUserData data);

}
