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
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import jShell.commands.Mv;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.DirNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.MoveDirException;
import jShell.errors.NodeExistsException;
import jShell.errors.SuccessorException;
import jShell.errors.UnknownErrorException;
import jShell.errors.WorkingDirException;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;

/**
 * Test cases for the Mv command.
 * 
 * @author Raz Ben Haim
 *
 */
public class MvTest {
  MockShell shell;
  Mv mv;


  @Before
  public void setUp() throws Exception {
    shell = new MockShell();
    mv = new Mv();
  }

  @Test
  public void testToString() {
    assertEquals("mv", mv.toString());
  }

  @Test(expected = ArgsNumberException.class)
  public void testTooManyArgs()
      throws ArgsNumberException, InvalidPathException, DirNotFoundException,
      NodeExistsException, MoveDirException, SuccessorException {
    String[] params = {"mv", "a", "b", "c"};
    try {
      mv.run(params);
    } catch (InvalidPathException | NodeExistsException | UnknownErrorException
        | BadFileNameException | WorkingDirException e) {
      fail("Shouldnt throw these exceptions");
      e.printStackTrace();
    }
  }

  @Test
  public void testTwoDirs() throws MoveDirException, SuccessorException {
    String[] params = {"mv", MockFileSystem.validDirRelativePath,
        MockFileSystem.validAnotherDirFullPath};
    try {
      mv.run(params);
      assertEquals(shell.getLastOutput(),
          "Sucessfully moved node at " + MockFileSystem.validDirFullPath
              + " to new parent " + MockFileSystem.validAnotherDirFullPath
              + "\n");
    } catch (InvalidPathException | ArgsNumberException | NodeExistsException
        | UnknownErrorException | BadFileNameException | DirNotFoundException
        | WorkingDirException e) {
      fail("Shouldnt throw anything.");
      e.printStackTrace();
    }
  }

  @Test
  public void testFileAndDir() {
    String[] params = {"mv", MockFileSystem.validFileRelativePath,
        MockFileSystem.validAnotherDirFullPath};
    try {
      mv.run(params);
      assertEquals(shell.getLastOutput(),
          "Sucessfully moved node at " + MockFileSystem.validFileFullPath
              + " to new parent " + MockFileSystem.validAnotherDirFullPath
              + "\n");
    } catch (InvalidPathException | ArgsNumberException | NodeExistsException
        | UnknownErrorException | MoveDirException | SuccessorException
        | BadFileNameException | DirNotFoundException | WorkingDirException e) {
      fail("Shouldnt throw anything.");
      e.printStackTrace();
    }
  }

  @After
  public void tearDown() throws Exception {}

  // fails
  @Test(expected = InvalidPathException.class)
  public void testInvalid() throws InvalidPathException {
    String[] params = {"mv", MockFileSystem.validDirFullPath, "a"};
    try {
      mv.run(params);
      assertEquals(shell.getLastOutput(), "Sucessfully moved node at "
          + MockFileSystem.validDirFullPath + " to new parent " + "a" + "\n");
    } catch (ArgsNumberException | NodeExistsException | UnknownErrorException
        | MoveDirException | SuccessorException | BadFileNameException
        | DirNotFoundException | WorkingDirException e) {
      fail("Shouldnt throw anything.");
      e.printStackTrace();
    }
  }
}
