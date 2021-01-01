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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import jShell.commands.Pushd;
import jShell.errors.ArgsNumberException;
import jShell.errors.DirNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.UnknownErrorException;
import jShell.fileSystem.Path;
import jShell.mockClasses.MockShell;

/**
 * Test cases for the Pushd command.
 * 
 * @author Raz Ben Haim
 *
 */
public class PushdTest {
  MockShell ms;
  Pushd pd;

  @Before
  public void setUp() throws Exception {
    ms = new MockShell();
    pd = new Pushd();
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test(expected = ArgsNumberException.class)
  public void testTooManyArgs() throws ArgsNumberException {
    String[] params = {"pushd", "a", "b", "c"};
    try {
      pd.run(params);
    } catch (InvalidPathException | DirNotFoundException e) {
      fail("Shouldnt throw these exceptions");
      e.printStackTrace();
    }
  }

  @Test
  public void testValidPath() {
    String[] params = {"pushd", "/working/childDir"};
    try {
      pd.run(params);
      assertTrue(ms.getUserData().getPathStack()
          .contains(new Path("/working/childDir")));
    } catch (InvalidPathException | DirNotFoundException
        | ArgsNumberException e) {
      fail("Shouldnt throw these exceptions");
      e.printStackTrace();
    }
  }

  @Test(expected = DirNotFoundException.class)
  public void testInvalidPath() throws DirNotFoundException {
    String[] params = {"pushd", "/working/NOT A PATH"};
    try {
      pd.run(params);
    } catch (InvalidPathException | ArgsNumberException e) {
      fail("Shouldnt throw these exceptions");
      e.printStackTrace();
    }
  }

}
