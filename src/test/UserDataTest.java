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
import org.junit.Before;
import org.junit.Test;
import jShell.UserData;
import jShell.errors.InvalidPathException;
import jShell.fileSystem.Path;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;

/**
 * Test cases for the UserData class.
 * 
 * @author Raz Ben Haim
 *
 */
public class UserDataTest {
  MockShell shell;
  UserData ud;

  @Before
  public void setUp() {
    shell = new MockShell();
    ud = new UserData(new Path("/working"));
  }

  @Test
  public void testSetCurPath() {
    Path p = new Path("/working/childDir");
    try {
      ud.setCurPath(p);
    } catch (InvalidPathException e) {
      fail("shouldnt throw anything");
    }
    assertEquals(p, ud.getCurPath());

  }


  @Test(expected = InvalidPathException.class)
  public void testSetCurPathInvalid() throws InvalidPathException {
    Path p = new Path("/working/not a path");
    ud.setCurPath(p);
    fail("Should throw InvalidPathException");
  }

  @Test
  public void getCurDir() {
    assertEquals(MockFileSystem.workingDir, ud.getCurDir());
  }
}
