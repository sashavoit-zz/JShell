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
import jShell.commands.Popd;
import jShell.errors.ArgsNumberException;
import jShell.errors.EmptyStackException;
import jShell.errors.InvalidPathException;
import jShell.fileSystem.Path;
import jShell.interfaces.IPath;
import jShell.mockClasses.MockShell;

/**
 * Test cases for the Popd command.
 * 
 * @author Raz Ben Haim
 *
 */
public class PopdTest {
  MockShell ms;
  Popd pd;

  @Before
  public void setUp() throws Exception {
    ms = new MockShell();
    pd = new Popd();
  }

  @After
  public void tearDown() throws Exception {

  }

  @Test(expected = ArgsNumberException.class)
  public void testTooManyArgs() throws ArgsNumberException {
    String[] params = {"popd", "a", "b", "c"};
    try {
      pd.run(params);
    } catch (EmptyStackException | InvalidPathException e) {
      fail("Shouldnt throw these exceptions");
      e.printStackTrace();
    }

  }

  @Test
  public void testCompletePath() {
    String[] params = {"popd"};
    IPath p = new Path("/wroking/childDir");
    ms.getUserData().getPathStack().add(p);
    try {
      pd.run(params);
      assertEquals("Setting current path to " + p.toString() + "\n",
          ms.getLastOutput());
      assertTrue(ms.getUserData().getPathStack().isEmpty());
    } catch (EmptyStackException | InvalidPathException
        | ArgsNumberException e) {
      fail("Shouldnt throw these exceptions");
      e.printStackTrace();
    }

  }

  @Test(expected = EmptyStackException.class)
  public void testEmptyStack() throws EmptyStackException {
    String[] params = {"popd"};
    try {
      pd.run(params);
    } catch (ArgsNumberException | InvalidPathException e) {
      fail("Shouldn't throw execptinos");
      e.printStackTrace();
    }

  }


}
