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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;
import jShell.commands.Find;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.BadStringException;
import jShell.errors.DirNotFoundException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;
import jShell.errors.UnknownErrorException;
import jShell.fileSystem.Path;
import jShell.interfaces.IDirectory;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;

/**
 * Test cases for the Find command.
 * 
 * @author Raz Ben Haim
 *
 */
public class FindTest {

  private MockShell s;
  private Find f;
  IDirectory root;
  MockFileSystem fs;

  @Before
  public void createDirs() {
    s = new MockShell();
    f = new Find();
    fs = (MockFileSystem) s.getFileSystem();
    root = fs.getRootDir();
  }

  @Test
  public void testToString() {
    assertEquals("find", f.toString());
  }

  @Test(expected = jShell.errors.Error.class)
  public void testTooFewArgs() throws InvalidPathException,
      DirNotFoundException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException, BadStringException {
    String[] params = {"find", "-name", "-type", "d"};
    f.run(params);
    fail("Invalid number of args, the command shouldnt run.");
  }

  @Test
  public void testRun() {
    String[] params = {"find", "-type", "d", "-name", "\"childDir\""};
    try {
      String s = f.run(params);
      assertEquals(s, MockFileSystem.validDirFullPath);
    } catch (InvalidPathException | DirNotFoundException | NodeExistsException
        | BadFileNameException | RedirectionOperatorsException
        | FileNotFoundException | BadStringException e) {
      fail("shouldnt throw exception.");
    }
  }

  @Test
  public void testRedirectionRun() {
    String[] params =
        {"find", "-type", "d", "-name", "\"childDir\"", ">", "file"};
    try {
      assertEquals(f.run(params), "");
    } catch (InvalidPathException | DirNotFoundException | NodeExistsException
        | BadFileNameException | RedirectionOperatorsException
        | FileNotFoundException | BadStringException e) {
      fail("shouldnt throw exception.");
    }
    fs.getNodeAtFullPath(new Path("/working")).ifChildExists("file");
  }

  @Test
  public void testMoltipuleDirRun() {
    String[] params = {"find", "/", "-type", "d", "-name", "\"childDir\""};
    StringBuilder sb = new StringBuilder();
    sb.append(MockFileSystem.validDirFullPath + "\n");
    sb.append(MockFileSystem.validAnotherChildDirFullPath);
    try {
      assertEquals(sb.toString(), f.run(params));
    } catch (InvalidPathException | DirNotFoundException | NodeExistsException
        | BadFileNameException | RedirectionOperatorsException
        | FileNotFoundException | BadStringException e) {
      fail("shouldnt throw exception.");
    }
  }
}
