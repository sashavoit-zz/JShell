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
import org.junit.Before;
import org.junit.Test;
import jShell.Shell;
import jShell.commands.Cd;
import jShell.errors.ArgsNumberException;
import jShell.errors.DirNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.mockClasses.MockShell;
import jShell.mockClasses.MockFileSystem;

/**
 * Various test cases for the Cd command
 */

public class CdTest {

  // set up mockshell and cd method object
  MockShell s = new MockShell();
  Cd c = new Cd();

  // test of the string method
  @Test
  public void testtoString() {
    assertEquals("cd", c.toString());
  }

  /*
   * Testing the invalid ArgsNumberException, thrown when there are too many args,ie trying to cd to
   * too many directories
   */
  @Test(expected = ArgsNumberException.class)
  public void testTooManyArgs()
      throws DirNotFoundException, ArgsNumberException, InvalidPathException {
    String[] params = {"cd", "a", "b"};
    c.run(params);
  }

  /*
   * Testing cd by changing to valid Directories, these are the full path cases. Assert by checking
   * that the mockshell prints the current message
   */
  @Test
  public void testCdtoDirectory() throws InvalidPathException,
      NodeExistsException, DirNotFoundException, ArgsNumberException {
    String[] params = {"cd", MockFileSystem.workingDirFullPath};
    c.run(params);
    assertEquals(s.getLastOutput(), "Setting current path to /working\n");
    String[] params2 = {"cd", MockFileSystem.validAnotherDirFullPath};
    c.run(params2);
    assertEquals(s.getLastOutput(), "Setting current path to /anotherDir\n");
  }

  /*
   * Testing the Dirnotfound exception by CD'ing to a file
   */
  @Test(expected = DirNotFoundException.class)
  public void testCdtoFile() throws InvalidPathException, NodeExistsException,
      DirNotFoundException, ArgsNumberException {
    String[] params = {"cd", MockFileSystem.validFileFullPath};
    c.run(params);
  }

  /*
   * Testing some more CD cases, this time going to childDir depth level. Asserting by checking the
   * right message for the shell.
   */
  @Test
  public void testCdtoChildDir() throws InvalidPathException,
      NodeExistsException, DirNotFoundException, ArgsNumberException {
    String[] params = {"cd", MockFileSystem.validDirFullPath};
    c.run(params);
    assertEquals(s.getLastOutput(),
        "Setting current path to /working/childDir\n");
    String[] params2 = {"cd", MockFileSystem.validAnotherChildDirFullPath};
    c.run(params2);
    assertEquals(s.getLastOutput(),
        "Setting current path to /anotherDir/childDir\n");

  }

  /*
   * Testing CD to a relative path. Asserting by checking the correct message to the shell
   */

  @Test
  public void testCdRelative() throws InvalidPathException, NodeExistsException,
      DirNotFoundException, ArgsNumberException {
    String[] params = {"cd", MockFileSystem.validDirRelativePath};
    c.run(params);
    assertEquals(s.getLastOutput(),
        "Setting current path to /working/childDir\n");
  }

  /*
   * Testing CD to root Dir
   */
  @Test
  public void testRoot() throws InvalidPathException, NodeExistsException,
      DirNotFoundException, ArgsNumberException {
    String[] params = {"cd", MockFileSystem.fullPathRoot};
    c.run(params);
    assertEquals(s.getLastOutput(), "Setting current path to /\n");
  }
}
