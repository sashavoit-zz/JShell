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
import jShell.commands.Cat;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;
import jShell.mockClasses.MockShell;

/**
 * Test cases for the Cat command.
 * 
 * @author Kevin Meharchand
 *
 */
public class CatTest {

  MockShell s = new MockShell();
  Cat c = new Cat();

  /*
   * Testing the basic functionality of Cat by using the predetermined valid file "childFile".
   */
  @Test
  public void testRun()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat", "childFile"};
    assertEquals("CONTENTS\n\n\n\n", c.run(params));
  }

  /*
   * Testing Cat's ability to read and print the contents of multiple valid files
   */
  @Test
  public void testRunMultipleFiles()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat", "childFile", "childFile"};
    assertEquals("CONTENTS\n\n\n\nCONTENTS\n\n\n\n", c.run(params));
  }

  /*
   * Testing Cat's ability to differentiate between valid and invalid files, throwing an exception if
   * any file in the list is invalid.
   */
  @Test(expected = FileNotFoundException.class)
  public void testRunBadFileAmongGoodOnes()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat", "childFile", "badfile", "childFile"};
    c.run(params);
  }

  /*
   * Test case for if all of the given files are invalid.
   */
  @Test(expected = FileNotFoundException.class)
  public void testRunAllBadFiles()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat", "badfile", "badfile2"};
    assertEquals("", c.run(params));
  }

  /*
   * Testing for basic functionality redirected out to a file. The returned result from the command
   * should be a blank line.
   */
  @Test
  public void testRunRedirection()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat", "childFile", ">", "file2"};
    assertEquals("", c.run(params));
  }

  /*
   * Testing that redirecting a bad file results in no outputs either.
   */
  @Test(expected = FileNotFoundException.class)
  public void testRunRedirectionBadFile()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat", "badfile", ">", "file2"};
    c.run(params);
  }

  /*
   * Testing that multiple good files being redirected works as intended
   */
  @Test
  public void testRunRedirectionMultipleGoodFiles()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat", "childFile", "childFile", ">", "file2"};
    assertEquals("", c.run(params));
  }

  /*
   * Testing that, given multiple good files and a bad file, that cat will print no result.
   */
  @Test(expected = FileNotFoundException.class)
  public void testRunRedirectionBadFileAmongGood()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat", "childFile", "badfile", ">", "file2"};
    c.run(params);
  }

  /*
   * Testing for redirection when given only bad files.
   */
  @Test(expected = FileNotFoundException.class)
  public void testRunRedirectionMultipleBadFiles()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat", "badfile", "badfile", ">", "file2"};
    c.run(params);
  }

  /*
   * Testing that the correct exception is thrown when no files are included in the command call.
   */
  @Test(expected = ArgsNumberException.class)
  public void testRunNoFiles()
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    String[] params = {"cat"};
    c.run(params);
  }

  /*
   * A simple test of the toString method.
   */
  @Test
  public void testToString() {
    // Testing toString's output
    assertEquals("cat", c.toString());
  }
}
