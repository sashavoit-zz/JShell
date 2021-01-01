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

package jShell;

import java.util.ArrayList;
import java.util.Scanner;
import jShell.commands.Cat;
import jShell.commands.Cd;
import jShell.commands.Command;
import jShell.commands.Cp;
import jShell.commands.Curl;
import jShell.commands.Echo;
import jShell.commands.Exit;
import jShell.commands.Find;
import jShell.commands.History;
import jShell.commands.Load;
import jShell.commands.Ls;
import jShell.commands.Man;
import jShell.commands.Mkdir;
import jShell.commands.Mv;
import jShell.commands.Popd;
import jShell.commands.Pushd;
import jShell.commands.Pwd;
import jShell.commands.Rm;
import jShell.commands.Save;
import jShell.commands.Speak;
import jShell.commands.Tree;
import jShell.errors.CommandNotFoundException;
import jShell.fileSystem.FileSystem;
import jShell.fileSystem.Path;
import jShell.fileSystem.Redirection;
import jShell.interfaces.IFileSystem;
import jShell.interfaces.IRedirection;
import jShell.interfaces.IShell;
import jShell.interfaces.IUserData;
import jShell.interfaces.InputModeCommand;

/**
 * Main shell class.
 * 
 * @author Raz
 */
public class Shell extends IShell {

  private static Shell Shell; // The main shell object for all classes to use

  private IUserData userData;
  private ArrayList<Command> commands; // Stores all the commands

  private FileSystem fileSystem;

  private Scanner input; // User input for the Shell
  private boolean running; // Keep track if the Shell is running

  private Redirection redirect; // Single Redirection object to be used by all classes
  private InputModeCommand inputMode;

  // Constansts for text colors
  public static final String TEXTRESET = "\u001B[0m"; // Text Reset
  public static final String TEXTRED = "\u001B[31m"; // RED (only for errors)
  public static final String TEXTGREEN = "\u001B[32m"; // GREEN
  public static final String TEXTBLUE = "\u001B[34m"; // BLUE

  public InputModeCommand getInputMode() {
    return inputMode;
  }

  public void setInputMode(InputModeCommand inputModeCommand) {
    this.inputMode = inputModeCommand;
  }

  /**
   * constructor for Shell
   */
  public Shell() {
    super();
    Shell = (Shell) IShell.getShell();
    setIsRunning(true);
    commands = new ArrayList<Command>();
    input = new Scanner(System.in);
    redirect = new Redirection();
    userData = new UserData(new Path("/"));
    fileSystem = FileSystem.getFileSystem();
    registerCommands();
  }

  /**
   * Returns userData
   * 
   * @return The main UserData object for the shell
   */
  public IUserData getUserData() {

    return userData;
  }

  /**
   * Sets user data in shell
   * 
   * @param ud is the UserData interface
   */
  public void setUserData(IUserData ud) {
    userData = (IUserData) ud;
  }

  /**
   * Registers all commands to the shell.
   */
  private void registerCommands() {
    commands.add(new Exit());
    commands.add(new Mkdir());
    commands.add(new Cd());
    commands.add(new Cat());
    commands.add(new Echo());
    commands.add(new Man());
    commands.add(new Pwd());
    commands.add(new Ls());
    commands.add(new Pushd());
    commands.add(new Popd());
    commands.add(new History());
    commands.add(new Mv());
    commands.add(new Cp());
    commands.add(new Rm());
    commands.add(new Find());
    commands.add(new Save());
    commands.add(new Load());
    commands.add(new Curl(new UrlDataFetcher()));
    commands.add(new Tree());
  }

  /**
   * Returns reference to shell variable
   * 
   * @return The shell object all others classes use
   */
  public static Shell getShell() {

    return Shell;
  }

  /**
   * Reads user input from the shell
   * 
   * @return The input the user typed in the shell
   */
  public String getUserInput() {

    return this.input.nextLine();
  }

  /**
   * Output value to the shell.
   * 
   * @param value The value to be output to the shell.
   */
  @Override
  public void outputToShell(String value) {

    System.out.print(value);
    System.out.flush();
  }

  /**
   * Output a value to the shell with custom color.
   * 
   * @param value The value to be output to the shell.
   * @param color Color of the text being outputted
   */
  @Override
  public void outputToShell(String value, String color) {

    // Check that text has valid color
    if (color != TEXTBLUE && color != TEXTGREEN) {
      // Color is not valid, do regular output
      outputToShell(value);
    } else {
      // Color is valid
      outputToShell(color + value + TEXTRESET);
    }

    System.out.flush();

  }

  /**
   * Outputs error messages to the shell.
   * 
   * @param value The value to be output to the shell.
   */
  @Override
  public void outputToShellErr(String value) {

    System.err.println(TEXTRED + value + TEXTRESET);
    System.out.flush();
  }

  /**
   * Output a value to the shell with newline at the end.
   * 
   * @param value The value to be output to the shell.
   */
  @Override
  public void outputToShellln(String value) {

    System.out.println(value);
    System.out.flush();
  }

  /**
   * Output a value to the shell with newline at the end and custom color.
   * 
   * @param value The value to be output to the shell.
   * @param color Color of the text being outputted
   */
  @Override
  public void outputToShellln(String value, String color) {

    // Check that text has valid color
    if (color != TEXTBLUE && color != TEXTGREEN) {
      // Color is not valid, do regular output
      outputToShellln(value);
    } else {
      // Color is valid
      outputToShellln(color + value + TEXTRESET);
    }

    System.out.flush();
  }

  /**
   * Processes user's input.
   * 
   * @param input The input the user typed to the shell
   */
  public void onInput(String input) {
    try {
      if (inputMode != null) {
        Shell.getShell().getUserData().getCommandHistory().set(
            Shell.getShell().getUserData().getCommandHistory().size() - 1,
            Shell.getShell().getUserData().getCommandHistory().get(
                Shell.getShell().getUserData().getCommandHistory().size() - 1)
                + " " + input);
        inputMode.onInput(input);
        return;
      }
      userData.getCommandHistory().add(input);
      /*
       * Get rid of all white spaces, and split the arguments into a String array
       */
      String args[] = input.trim().split("\\s+");
      /*
       * Check if command exists by comparing the first argument to class name
       */
      for (Command c : this.commands) {
        if (c.getClass().getSimpleName().toLowerCase().equals(args[0])) {
          // run command
          String output = c.run(args);
          if (!output.isEmpty())
            outputToShellln(output);
          return;
        }
      }
      throw new CommandNotFoundException(args[0]);
    } catch (jShell.errors.Error e) {
      outputToShellErr("Error: " + e.getError());
    }
  }

  /**
   * Returns Whether the program is running or not
   * 
   * @return Whether the program is running or not
   */
  public boolean isRunning() {

    return running;
  }

  /**
   * Sets running to isRunning
   * 
   * @param isRunning The boolean value of isRunning
   */
  public void setIsRunning(boolean isRunning) {

    running = isRunning;
  }

  /**
   * Method to get reference to redirection
   * 
   * @return reference to redirection
   */
  @Override
  public IRedirection getRedirection() {
    return redirect;
  }

  /**
   * Method to shut down the shell
   */
  @Override
  public void shutDown() {
    setIsRunning(false);
  }

  /**
   * Return reference to file system singleton
   * 
   * @return reference to file system
   */
  @Override
  public IFileSystem getFileSystem() {
    return fileSystem;
  }

}
