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
import jShell.commands.History;
import jShell.errors.ArgsException;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NegativeNumberException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;
import jShell.mockClasses.MockShell;


public class HistoryTest {

  /**
   * Setting up the mockshell and history object testing
   */

  MockShell s = new MockShell();
  History h = new History();

  // Testing ArgsNumberException, can't have 3 cases unless doing Redirection

  @Test(expected = ArgsNumberException.class)
  public void testInvalidNumberArgsException() throws InvalidPathException,
      ArgsNumberException, NegativeNumberException, ArgsException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {
    String[] params = {"history", "a", "b"};
    h.run(params);

    String[] params2 = {"history", "2", "a"};
    h.run(params2);
  }

  // testing the ArgsException ,Cannot have non integer parameters,
  @Test(expected = ArgsException.class)
  public void testInvalidArgsTypeException() throws InvalidPathException,
      ArgsNumberException, NegativeNumberException, ArgsException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {
    String[] params = {"history", "a"};
    h.run(params);
  }

  /*
   * testing the Redirection Exception, below are 2 invalid cases for redirection either wrong
   * operators or not operators not leading to a file.
   */
  @Test(expected = RedirectionException.class)
  public void testInvalidReDirection() throws InvalidPathException,
      ArgsNumberException, NegativeNumberException, ArgsException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {
    String[] params = {"history", "2", ">"};
    h.run(params);
    String[] params2 = {"history", ">>", ">"};
    h.run(params2);
  }

  // Invalid file name exception, ! is not a valid name
  @Test(expected = BadFileNameException.class)
  public void testBadFileNameReDirection() throws InvalidPathException,
      ArgsNumberException, NegativeNumberException, ArgsException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {
    String[] params = {"history", ">", "!"};
    h.run(params);
  }

  // can't use negative numbers in history
  @Test(expected = NegativeNumberException.class)
  public void testNegativeNumber() throws InvalidPathException,
      ArgsNumberException, NegativeNumberException, ArgsException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {
    String[] params = {"history", "-1"};
    h.run(params);
  }

  /*
   * Using the mock History array in mock user data to check the various combinations of history n
   * commands. 1 most recent, 4 most recent and all history in 3 different ways. Check is done by
   * checking the output of the mockshell.
   */
  @Test
  public void testHistoryNMostRecent() throws InvalidPathException,
      ArgsNumberException, NegativeNumberException, ArgsException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {
    String[] params = {"history", "1"};
    assertEquals("7.some more commands\n", h.run(params));
    // last 4
    String[] params2 = {"history", "4"};
    String historyRep =
        "4.this was invalid command\n" + "5.some more commands\n"
            + "6.some more commands\n" + "7.some more commands\n";
    assertEquals(historyRep, h.run(params2));
    // all of history in three different ways
    String[] params3 = {"history"};
    String historyRepAll =
        "1.command\n" + "2.some other command\n" + "3.this was valid command\n"
            + "4.this was invalid command\n" + "5.some more commands\n"
            + "6.some more commands\n" + "7.some more commands\n";
    assertEquals(historyRepAll, h.run(params3));
    String[] params4 = {"history 7"};
    assertEquals(historyRepAll, h.run(params4));
    String[] params5 = {"history 77"};
    assertEquals(historyRepAll, h.run(params5));
  }

  // check valid history redirection and append cases
  @Test
  public void testHistoryValidRedirection() throws InvalidPathException,
      ArgsNumberException, NegativeNumberException, ArgsException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {
    String[] params = {"history", ">", "childFile"};
    String historyRepAll =
        "1.command\n" + "2.some other command\n" + "3.this was valid command\n"
            + "4.this was invalid command\n" + "5.some more commands\n"
            + "6.some more commands\n" + "7.some more commands\n";
    String RedirectionOverwrite = "Redirecting output for overwriting:"
        + historyRepAll + "\n to file with name: " + "childFile";
    h.run(params);
    assertEquals(RedirectionOverwrite, s.getLastOutput());
    // Testing RedirectionAppend
    String[] params2 = {"history", ">>", "childFile"};
    String historyRepAll2 =
        "1.command\n" + "2.some other command\n" + "3.this was valid command\n"
            + "4.this was invalid command\n" + "5.some more commands\n"
            + "6.some more commands\n" + "7.some more commands\n";
    String RedirectionAppend = "Redirecting output for appending:"
        + historyRepAll + "\n to file with name: " + "childFile";
    h.run(params2);
    assertEquals(RedirectionAppend, s.getLastOutput());
  }

  // testing valid redirection with n most history
  @Test
  public void testHistoryValidRedirectionNmost() throws InvalidPathException,
      ArgsNumberException, NegativeNumberException, ArgsException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {
    String[] params = {"history", "4", ">", "childFile"};
    String historyRep =
        "4.this was invalid command\n" + "5.some more commands\n"
            + "6.some more commands\n" + "7.some more commands\n";
    String RedirectionOverwrite = "Redirecting output for overwriting:"
        + historyRep + "\n to file with name: " + "childFile";
    h.run(params);
    assertEquals(RedirectionOverwrite, s.getLastOutput());
    // Redirection Overwriting
    String[] params2 = {"history", "4", ">>", "childFile"};
    String historyRep2 =
        "4.this was invalid command\n" + "5.some more commands\n"
            + "6.some more commands\n" + "7.some more commands\n";
    String RedirectionAppending = "Redirecting output for appending:"
        + historyRep + "\n to file with name: " + "childFile";
    h.run(params2);
    assertEquals(RedirectionAppending, s.getLastOutput());
  }

  // testing the to string method
  @Test
  public void testtoString() {
    assertEquals("history", h.toString());
  }
}
