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
import jShell.mockClasses.MockShell;
import jShell.commands.Man;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.CommandNotFoundException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;

/**
 * Test cases for the Man command.
 * 
 * @author Kevin Meharchand
 *
 */
public class ManTest {

  MockShell s = new MockShell();
  Man m = new Man();

  /*
   * A test for the basic functionality of the Man command.
   */
  @Test
  public void testRunGoodManual()
      throws InvalidPathException, RedirectionException, ArgsNumberException,
      CommandNotFoundException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    final String[] params = {"man", "exit"};
    String expected = "";
    expected = expected + ("~~~~~~~~~~~~~~~~~~~~\n");
    expected = expected + ("The exit Command:\n");
    expected = expected + ("\n");
    expected = expected + ("Usage: exit\n");
    expected = expected + ("\n");
    expected = expected + ("The exit command closes the shell"
        + " entirely. Adding any arguments after the\n");
    expected = expected + ("initial command will result"
        + " in an error. Nothing else to see here!\n");
    expected = expected + ("~~~~~~~~~~~~~~~~~~~~\n");
    assertEquals("Should have the Exit manual", expected + "\n", m.run(params));
  }

  /*
   * Testing that redirection returns no output to the shell
   */
  @Test
  public void testRunRedirection()
      throws InvalidPathException, RedirectionException, ArgsNumberException,
      CommandNotFoundException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    String[] params = {"man", "exit", ">", "file"};
    assertEquals("No command output", "", m.run(params));
  }

  /*
   * Testing that, when given the name of a nonexistent command, that Man will throw the right
   * exception
   */
  @Test(expected = CommandNotFoundException.class)
  public void testRunBadManual()
      throws InvalidPathException, RedirectionException, ArgsNumberException,
      CommandNotFoundException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    String[] params = {"man", "abbas"};
    m.run(params);
  }

  /*
   * Testing that an exception gets thrown when no command's manual is specified
   */
  @Test(expected = ArgsNumberException.class)
  public void testRunBadArgs()
      throws InvalidPathException, RedirectionException, ArgsNumberException,
      CommandNotFoundException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    String[] params = {"man"};
    m.run(params);
  }

  /*
   * Testing that the right exception is thrown when the operator for potential redirection is bad
   */
  @Test(expected = RedirectionException.class)
  public void testRunBadRedirectionOperator()
      throws InvalidPathException, RedirectionException, ArgsNumberException,
      CommandNotFoundException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    String[] params = {"man", "cat", ">>>", "file"};
    m.run(params);
  }

  /*
   * Testing that the right exception is thrown when the file name for potential redirection is bad
   */
  @Test(expected = RedirectionException.class)
  public void testRunBadRedirectionFilename()
      throws InvalidPathException, RedirectionException, ArgsNumberException,
      CommandNotFoundException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    String[] params = {"man", "cat", ">>", "file.txt"};
    m.run(params);
  }

  /*
   * Basic test of the toString method.
   */
  @Test
  public void testToString() {
    assertEquals(m.toString(), "man");
  }
}
