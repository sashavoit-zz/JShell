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

import java.util.ArrayList;
import java.util.Stack;
import jShell.fileSystem.Path;
import jShell.interfaces.*;

/**
 * Mock version of UserData class
 * 
 * @author Sasha (Oleksandr)
 */

public class MockUserData implements IUserData {

  /**
   * Method to mock getting user's command history
   * 
   * @return ArrayList with mock history
   */
  @Override
  public ArrayList<String> getCommandHistory() {
    ArrayList<String> commandHistory = new ArrayList<String>();
    commandHistory.add("command");
    commandHistory.add("some other command");
    commandHistory.add("this was valid command");
    commandHistory.add("this was invalid command");
    commandHistory.add("some more commands");
    commandHistory.add("some more commands");
    commandHistory.add("some more commands");
    return commandHistory;
  }

  /**
   * Method to get a path to current working directory
   * 
   * @return path to current working directory
   */
  @Override
  public IPath getCurPath() {
    return new Path(MockFileSystem.workingDirFullPath);
  }

  /**
   * Method to set a path to working directory
   * 
   * @param curPath Path to new working directory
   */
  @Override
  public void setCurPath(IPath curPath) {
    IShell.getShell()
        .outputToShellln("Setting current path to " + curPath.toString());
  }


  /**
   * Method to get working directory
   * 
   * @return reference to current working directory
   */
  @Override
  public IDirectory getCurDir() {
    return MockFileSystem.workingDir;
  }

  public MockUserData() {
    mockStack = new Stack<IPath>();
  }

  /**
   * Method to mock getting path stack from user data
   * 
   * @return mock path stack
   */
  private Stack<IPath> mockStack;

  @Override
  public Stack<IPath> getPathStack() {
    return mockStack;
  }
}
