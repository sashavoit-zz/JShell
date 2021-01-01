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
import jShell.commands.Echo;
import jShell.errors.ArgsException;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadStringException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;
import jShell.mockClasses.MockShell;

/**
 * Test cases for the Echo command.
 * 
 * @author Kevin Meharchand
 *
 */
public class EchoTest {

  MockShell s = new MockShell();
  Echo e = new Echo();

  /*
   * Test cases for the basic possibilities of the echo command.
   */
  @Test
  public void testRun() throws BadStringException, ArgsException,
      ArgsNumberException, RedirectionOperatorsException, RedirectionException {
    String[] params = {"echo", "\"hello\""};
    assertEquals("Basic string", "hello", e.run(params));
    String[] redirParams = {"echo", "\"hello\"", ">", "childFile"};
    assertEquals("Redirection to file", "", e.run(redirParams));
    String[] params2 = {"echo", "\"hello", "there\""};
    assertEquals("Basic multiword string", "hello there", e.run(params2));
    String[] redirParams2 = {"echo", "\"hello", "there\"", ">>", "childFile"};
    assertEquals("Redirection to file", "", e.run(redirParams2));
    String[] emptyParams = {"echo", "\"", "\""};
    assertEquals("Empty string but quotes on it", " ", e.run(emptyParams));
  }

  /*
   * Test cases where the code is expected to fail, i.e bad strings being given to the command.
   */
  @Test(expected = BadStringException.class)
  public void testRunExceptions() throws BadStringException, ArgsException,
      ArgsNumberException, RedirectionOperatorsException, RedirectionException {
    String[] quotesInString = {"echo", "\"hello", "th\"ere\""};
    assertEquals("Quotes within a string", "", e.run(quotesInString));
    String[] noClosingQuotes = {"echo", "\"hello"};
    assertEquals("No closing quotes", "", e.run(noClosingQuotes));
    String[] noStartingQuotes = {"echo", "hello\""};
    e.run(noStartingQuotes);
    String[] noQuotes = {"echo", "hello"};
    e.run(noQuotes);
  }

  /*
   * A test case to cover the event that a bad redirection operator is passed during the use of echo
   */
  @Test(expected = RedirectionOperatorsException.class)
  public void testRunRedirectionBadOp()
      throws BadStringException, ArgsException, ArgsNumberException,
      RedirectionOperatorsException, RedirectionException {
    String[] badOperator = {"echo", "\"Hello", "there\"", ">>>", "childFile"};
    e.run(badOperator);
  }

  /*
   * A test case to cover the possibility of a bad file name being passed as an argument.
   */
  @Test(expected = RedirectionException.class)
  public void testRunRedirectionBadFile()
      throws BadStringException, ArgsException, ArgsNumberException,
      RedirectionOperatorsException, RedirectionException {
    String[] badOperator = {"echo", "\"Hello", "there\"", ">", "childFile.edu"};
    e.run(badOperator);
  }

  /*
   * A simple test of the toString method.
   */
  @Test
  public void testToString() {
    assertEquals("echo", e.toString());
  }

}
