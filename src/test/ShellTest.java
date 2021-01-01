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

package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import jShell.Shell;
import jShell.interfaces.IFileSystem;
import jShell.mockClasses.MockUserData;

public class ShellTest {
  private Shell sh;

  @Before
  public void setUp() throws Exception {
    sh = new Shell();
  }

  @Test
  public void testGetShell() {
    Shell sh2 = Shell.getShell();
    assertEquals("Both shells should be the same", sh, sh2);
  }


  @Test
  public void testOutputToShell() {
    sh.outputToShell("hello");
    assertTrue("There should be no errors", sh.isRunning());
  }

  @Test
  public void testOutputToShellErr() {
    sh.outputToShellErr("Error");
    assertTrue("There should be no errors", sh.isRunning());
  }

  @Test
  public void testOutputToShellln() {
    sh.outputToShellln("hello on a newline");
    assertTrue("There should be no errors", sh.isRunning());
  }

  @Test
  public void testOnInputGoodCommand() {
    String args = "exit";
    sh.onInput(args);
    assertTrue("Exit command should stop the shell", !sh.isRunning());
  }

  @Test
  public void testOnInputBadCommand() {
    String args = "abbas";
    sh.onInput(args);
    assertTrue("Shell should keep going if a bad command is entered",
        sh.isRunning());
  }

  @Test
  public void testSetAndGetUserData() {
    MockUserData m = new MockUserData();
    sh.setUserData(m);
    assertNotNull("User data should not be null", sh.getUserData());
  }

  @Test
  public void testIsRunning() {
    assertTrue("Shell should remain running", sh.isRunning());
  }

  @Test
  public void testSetIsRunning() {
    sh.setIsRunning(false);
    assertFalse("Shell should stop running", sh.isRunning());
  }

  @Test
  public void testGetRedirection() {
    assertNotNull("Shell should have an instance of redirection ready",
        sh.getRedirection());
  }

  @Test
  public void testShutDown() {
    sh.shutDown();
    assertFalse("Shell should stop running", sh.isRunning());
  }

  @Test
  public void testGetFileSystem() {
    IFileSystem f = sh.getFileSystem();
    assertNotNull("Shell should have a file system instance ready", f);
  }
}
