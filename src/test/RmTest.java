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
import jShell.commands.Rm;
import jShell.errors.ArgsNumberException;
import jShell.errors.DeleteRootDirException;
import jShell.errors.InvalidPathException;
import jShell.errors.RemovingFileException;
import jShell.errors.WorkingDirException;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;

public class RmTest {

  /**
   * Setting up mockShell and Rm object for testing
   */

  MockShell s = new MockShell();
  Rm r = new Rm();

  // Check if DeleteRootDirException works
  @Test(expected = DeleteRootDirException.class)
  public void testRemovingRootDir()
      throws WorkingDirException, InvalidPathException, DeleteRootDirException,
      ArgsNumberException, RemovingFileException {
    String[] params = {"rm", MockFileSystem.fullPathRoot};
    r.run(params);
  }

  /*
   * Check if ArgsNumberException works, user can't enter two directories at the same time
   */
  @Test(expected = ArgsNumberException.class)
  public void testTooManyArgs()
      throws WorkingDirException, InvalidPathException, DeleteRootDirException,
      ArgsNumberException, RemovingFileException {
    String[] params = {"rm", "a", "b"};
    r.run(params);
  }

  // Check if WorkingDirException works, user can't delete working directory
  @Test(expected = WorkingDirException.class)
  public void testDeleteWorkingDirectory()
      throws WorkingDirException, InvalidPathException, DeleteRootDirException,
      ArgsNumberException, RemovingFileException {
    String[] params = {"rm", MockFileSystem.workingDirFullPath};
    r.run(params);
  }

  /*
   * Checking Valid case for Remvoing Directory, by comparing the message from the mock Shell outputs
   */
  @Test
  public void testValidRm() throws WorkingDirException, InvalidPathException,
      DeleteRootDirException, ArgsNumberException, RemovingFileException {
    String[] params = {"rm", MockFileSystem.validDirFullPath};
    r.run(params);
    assertEquals(s.getLastOutput(),
        "Removed a node at path " + MockFileSystem.validDirFullPath + "\n");
  }

  /*
   * Testing Rm from by using relative path as input, once again checking mockshell last output
   */

  @Test
  public void testValidRmRelative()
      throws WorkingDirException, InvalidPathException, DeleteRootDirException,
      ArgsNumberException, RemovingFileException {
    String[] params = {"rm", MockFileSystem.validDirRelativePath};
    r.run(params);
    assertEquals(s.getLastOutput(),
        "Removed a node at path " + MockFileSystem.validDirFullPath + "\n");
  }

  // more tests for rm, this time also at child directory depth level.
  @Test
  public void testValidRmAtAnotherDir()
      throws WorkingDirException, InvalidPathException, DeleteRootDirException,
      ArgsNumberException, RemovingFileException {
    String[] params = {"rm", MockFileSystem.validAnotherDirFullPath};
    r.run(params);
    assertEquals(s.getLastOutput(), "Removed a node at path "
        + MockFileSystem.validAnotherDirFullPath + "\n");
    // testing its child
    String[] params2 = {"rm", MockFileSystem.validAnotherChildDirFullPath};
    r.run(params2);
    assertEquals(s.getLastOutput(), "Removed a node at path "
        + MockFileSystem.validAnotherChildDirFullPath + "\n");
  }

  // Testing FileCase, User should recieve error message from mock shell
  @Test
  public void testRemovingFile()
      throws WorkingDirException, InvalidPathException, DeleteRootDirException,
      ArgsNumberException, RemovingFileException {
    String[] params = {"rm", MockFileSystem.validFileFullPath};
    r.run(params);
    assertEquals(s.getLastOutput(), "Trying to remove standalone file" + "\n");
  }

  // testing toString method
  @Test
  public void testToString() {
    assertEquals("rm", r.toString());
  }
}
