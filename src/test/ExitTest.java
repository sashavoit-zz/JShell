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
import jShell.commands.Exit;
import jShell.errors.ArgsNumberException;
import jShell.interfaces.IShell;

/**
 * Test cases for the Exit command.
 * 
 * @author Raz Ben Haim
 *
 */
public class ExitTest {
  private Exit ex;
  private IShell sh;

  @Before
  public void setUp() throws Exception {
    sh = new Shell();
    ex = new Exit();
  }

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testToString() {
    assertEquals("exit", ex.toString());
  }

  /**
   * Testing where all args are correct
   */
  @Test
  public void testExit1() {
    String[] args = {"exit"};
    try {
      ex.run(args);
    } catch (ArgsNumberException e) {
      fail("Not suppossed to throw a error because args are correct");
    }
    assertTrue(!sh.isRunning());
  }

  /**
   * Testing invalid args
   */
  @Test
  public void testExit2() {
    String[] args = {"exit", "1"};
    try {
      ex.run(args);
      fail("Invalid number of args, the command shouldnt run.");
    } catch (ArgsNumberException e) {
      assertTrue(sh.isRunning());
    }
  }

  /**
   * Testing invalid args
   */
  @Test
  public void testExit3() {
    String[] args = {"exit", "abc", "de", "2"};
    try {
      ex.run(args);
      fail("Invalid number of args, the command shouldnt run.");
    } catch (ArgsNumberException e) {
      assertTrue(sh.isRunning());
    }
  }
}
