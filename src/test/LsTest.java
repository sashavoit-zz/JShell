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

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import jShell.commands.Ls;
import jShell.errors.*;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;

/**
 * Class to test ls command
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class LsTest {

  // We will use MockShell object for testing
  MockShell shell;
  Ls ls;

  @Before
  public void init() {
    // Initializing the mock shell
    shell = new MockShell();
    ls = new Ls();
  }

  @Test
  public void testLsWithNoArguments() {
    String[] args = {"ls"};
    String output = "";
    try {
      output = ls.run(args);
    } catch (Exception e) {
      fail();
    }
    String expected = "childDir\nchildFile";
    assertEquals(output, expected);
  }

  @Test
  public void testLsInvalidPath() {
    String[] args = {"ls", "/this/is/invalid/path"};
    try {
      ls.run(args);
      fail();
    } catch (InvalidPathException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLsRedirectionBadName() {
    String[] args1 = {"ls", ">", "thisFile.HasBadName"};
    try {
      ls.run(args1);
      fail();
    } catch (BadFileNameException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLsRedirectionToDirectory() {
    String[] args1 = {"ls", ">", MockFileSystem.validDirFullPath};
    try {
      ls.run(args1);
      fail();
    } catch (NodeExistsException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLsRedirectionToBadPath() {
    String[] args1 = {"ls", ">", "this/file/does/not/exist"};
    try {
      ls.run(args1);
      fail();
    } catch (InvalidPathException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLsValidRedirection() {
    String filePath = "childFile";
    String expectedOutput = "childDir\nchildFile";
    String[] args1 = {"ls", ">", filePath};
    try {
      ls.run(args1);
      String expected = "Redirecting output for overwriting:" + expectedOutput
          + "\n to file with name: " + filePath;
      assertEquals(shell.getLastOutput(), expected);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLsRecursive() {
    String[] args1 = {"ls", "-R", "/working"};
    try {
      String expected = "/working:\nchildDir\nchildFile\n\n"
          + "/working/childDir:\n\n" + "/working/childFile";
      String actual = ls.run(args1);
      assertEquals(expected, actual);
    } catch (Exception e) {
      fail();
    }
  }
}
