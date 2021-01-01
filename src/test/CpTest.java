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
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import jShell.Shell;
import jShell.commands.Cp;
import jShell.errors.ArgsNumberException;
import jShell.errors.DirNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.SuccessorException;
import jShell.errors.UnknownErrorException;
import jShell.fileSystem.Path;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;

/**
 * Test cases for the Cp command.
 * 
 * @author Raz Ben Haim
 *
 */
public class CpTest {
  MockShell shell;
  Cp cp;


  @Before
  public void setUp() {
    shell = new MockShell();
    cp = new Cp();
  }

  @Test
  public void testToString() {
    assertEquals("cp", cp.toString());
  }

  @Test(expected = ArgsNumberException.class)
  public void testTooManyArgs() throws ArgsNumberException,
      InvalidPathException, DirNotFoundException, NodeExistsException {
    String[] params = {"cp", "a", "b", "c"};
    try {
      cp.run(params);
    } catch (InvalidPathException | NodeExistsException | UnknownErrorException
        | SuccessorException e) {
      fail("Shouldnt throw these exceptions");
      e.printStackTrace();
    }
  }

  @Test
  public void testTwoDirs() {
    String[] params = {"cp", MockFileSystem.validDirRelativePath,
        MockFileSystem.validAnotherDirFullPath};
    try {
      cp.run(params);
      Path test = new Path(MockFileSystem.validDirRelativePath);
      String sometiung = test.getBottomName();
      assertEquals(shell.getLastOutput(),
          "Copying a node at path " + MockFileSystem.validDirRelativePath
              + ". To location " + MockFileSystem.validAnotherDirFullPath
              + "\n");
    } catch (InvalidPathException | ArgsNumberException | NodeExistsException
        | UnknownErrorException | SuccessorException | DirNotFoundException e) {
      fail("Shouldnt throw anything.");
      e.printStackTrace();
    }
  }

  @Test
  public void testTwoFiles() {
    String[] params = {"cp", MockFileSystem.validFileFullPath, "file2"};
    try {
      cp.run(params);
    } catch (InvalidPathException | ArgsNumberException | NodeExistsException
        | UnknownErrorException | SuccessorException | DirNotFoundException e) {
      fail("Shouldnt throw anything.");
      e.printStackTrace();
    }
    assertEquals(shell.getLastOutput(), "Copying a node at path "
        + MockFileSystem.validFileFullPath + ". To location " + "file2" + "\n");
  }

  @Test
  public void testFileAndDir() {
    String[] params = {"cp", MockFileSystem.validFileRelativePath,
        MockFileSystem.validDirRelativePath};
    try {
      cp.run(params);
      Path test = new Path(MockFileSystem.validDirRelativePath);
      String sometiung = test.getBottomName();
      assertEquals(shell.getLastOutput(),
          "Copying a node at path " + MockFileSystem.validFileRelativePath
              + ". To location " + MockFileSystem.validDirRelativePath + "\n");
    } catch (InvalidPathException | ArgsNumberException | NodeExistsException
        | UnknownErrorException | SuccessorException | DirNotFoundException e) {
      fail("Shouldnt throw anything.");
      e.printStackTrace();
    }
  }

  @Test(expected = InvalidPathException.class)
  public void testFileAndDirInvalid() throws InvalidPathException {
    String[] params = {"cp", "a", MockFileSystem.validDirRelativePath};
    try {
      cp.run(params);
      Path test = new Path(MockFileSystem.validDirRelativePath);
      String sometiung = test.getBottomName();
      assertEquals(shell.getLastOutput(),
          "Copying a node at path " + MockFileSystem.validFileRelativePath
              + ". To location " + MockFileSystem.validDirRelativePath + "\n");
    } catch (ArgsNumberException | NodeExistsException | UnknownErrorException
        | SuccessorException | DirNotFoundException e) {
      fail("It should throw InvalidPathException.");
      e.printStackTrace();
    }
  }

  @After
  public void tearDown() throws Exception {}
}
