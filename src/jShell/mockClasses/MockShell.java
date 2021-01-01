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

package jShell.mockClasses;

import jShell.interfaces.*;
import java.util.ArrayList;

/**
 * Mock version of Shell class. Mocks all the methods provided by IShell class
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class MockShell extends IShell {

  // The main mock shell object for all classes to use
  private static MockShell Shell;

  private MockUserData userData;

  private MockFileSystem fileSystem;

  private MockRedirection redirect; // Single Redirection object to be used by all classes

  public ArrayList<String> output = new ArrayList<>();

  /**
   * Constructor for MockShell object
   */
  public MockShell() {
    super();
    Shell = (MockShell) IShell.getShell();
    userData = new MockUserData();
    fileSystem = new MockFileSystem();
    redirect = new MockRedirection(Shell);
  }

  /**
   * Method to get the mock file system, associated with current mock shell
   * 
   * @return reference to mock file system
   */
  @Override
  public IFileSystem getFileSystem() {
    return fileSystem;
  }

  /**
   * Method to get the mock user data, associated with current mock shell
   * 
   * @return reference to mock user data
   */
  @Override
  public IUserData getUserData() {
    return userData;
  }

  /**
   * Method to mock shutting down the shell. Instead of actually shutting down the mock shell, record
   * appropriate message to output
   */
  @Override
  public void shutDown() {
    output.add("Shutting down the Shell");
  }

  /**
   * Method to mock the process of reading user input
   */
  @Override
  public String getUserInput() {
    return "String entered by user";
  }

  /**
   * Method to mock the process of outputting string. Instead of printing to console, save it to
   * output
   * 
   * @param value String to be printed
   */
  @Override
  public void outputToShell(String value) {
    output.add(value);
  }

  /**
   * Method to mock the process of outputting string. Instead of printing to console, save it to
   * output
   * 
   * @param value String to be printed
   */
  @Override
  public void outputToShell(String value, String color) {
    output.add(value);
  }

  /**
   * Method to mock the process of outputting error. Instead of printing to console, save it to output
   * 
   * @param value Error to be printed
   */
  @Override
  public void outputToShellErr(String value) {
    output.add(value);
  }

  /**
   * Method to mock the process processing input. Prints the appropriate message to output
   * 
   * @param input User input to be processed
   */
  @Override
  public void onInput(String input) {
    output.add("Processing user's input: " + input);
  }

  /**
   * Checks whether shell is running
   * 
   * @return true if shell is running
   */
  @Override
  public boolean isRunning() {
    return true;
  }

  /**
   * Method to mock the process of changing running state of shell
   * 
   */
  @Override
  public void setIsRunning(boolean isRunning) {}

  /**
   * Method to get reference to the MockRedirection object
   * 
   * @return mock redirection object associated with shell
   */
  @Override
  public IRedirection getRedirection() {
    return redirect;
  }

  /**
   * Method to mock the process of outputting string. Instead of printing to console, save it to
   * output with extra new line character
   * 
   * @param value String to be printed
   */
  @Override
  public void outputToShellln(String value) {
    output.add(value + "\n");
  }

  /**
   * Method to mock the process of outputting string. Instead of printing to console, save it to
   * output with extra new line character
   * 
   * @param value String to be printed
   */
  @Override
  public void outputToShellln(String value, String color) {
    output.add(value + "\n");
  }


  /**
   * Method to get last printed message from output
   * 
   * @return last recorded message in output
   */
  public String getLastOutput() {
    if (output.size() == 0) {
      return "";
    }
    return output.get(output.size() - 1);
  }

  /**
   * Method to set user data object
   * 
   */
  @Override
  public void setUserData(IUserData data) {
    if (data instanceof MockUserData) {
      this.userData = (MockUserData) data;
    }
  }
}
