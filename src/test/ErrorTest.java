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
import jShell.errors.ArgsException;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.BadStringException;
import jShell.errors.CommandNotFoundException;
import jShell.errors.DeleteRootDirException;
import jShell.errors.DirNotFoundException;
import jShell.errors.EmptyStackException;
import jShell.errors.Error;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.LoadNotFirstException;
import jShell.errors.MoveDirException;
import jShell.errors.NegativeNumberException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;
import jShell.errors.RemovingFileException;
import jShell.errors.SuccessorException;
import jShell.errors.UnknownErrorException;
import jShell.errors.WorkingDirException;

/**
 * Test cases for the Error class.
 * 
 * @author Kevin Meharchand
 *
 */
public class ErrorTest {

  Error e;

  /*
   * Testing cases for each individual exception, ensuring that when it is thrown, that the right
   * message is returned.
   */
  @Test
  public void testErrorArgsException() {
    e = new ArgsException("%s");
    assertEquals("Invalid argument(s): %s.", e.getError());
  }

  @Test
  public void testErrorArgsNumberException() {
    e = new ArgsNumberException();
    assertEquals("Incorrect number of arguments.", e.getError());
  }

  @Test
  public void testErrorBadFileNameException() {
    e = new BadFileNameException();
    assertEquals("Invalid name for file or directory.", e.getError());
  }

  @Test
  public void testErrorBadStringException() {
    e = new BadStringException();
    assertEquals(" Please enter a correctly formatted string (Begins and ends "
        + "with double quotes, no double quotes within).", e.getError());
  }

  @Test
  public void testErrorCommandNotFoundException() {
    e = new CommandNotFoundException("%s");
    assertEquals("Command not found: %s", e.getError());
  }

  @Test
  public void testErrorDeleteRootDirException() {
    e = new DeleteRootDirException();
    assertEquals("Cannot delete root directory.", e.getError());
  }

  @Test
  public void testErrorDirNotFoundException() {
    e = new DirNotFoundException("%s");
    assertEquals("Dir not found: %s.", e.getError());
  }

  @Test
  public void testErrorEmptyStackException() {
    e = new EmptyStackException();
    assertEquals("There are no directories in the " + "stack to be popped.",
        e.getError());
  }

  @Test
  public void testErrorFileNotFoundException() {
    e = new FileNotFoundException("%s");
    assertEquals("File not found: %s.", e.getError());
  }

  @Test
  public void testErrorInvalidPathException() {
    e = new InvalidPathException("%s");
    assertEquals("Invalid path: %s.", e.getError());
  }

  @Test
  public void testErrorLoadNotFirstException() {
    e = new LoadNotFirstException();
    assertEquals("Load must be the first command called.", e.getError());
  }

  @Test
  public void testErrorMoveDirException() {
    e = new MoveDirException();
    assertEquals("Cannot move a directory into itself.", e.getError());
  }

  @Test
  public void testErrorNegativeNumberException() {
    e = new NegativeNumberException("%s");
    assertEquals("%s is negative, please Enter a non-negative number",
        e.getError());
  }

  @Test
  public void testErrorNodeExistsException() {
    e = new NodeExistsException("%s");
    assertEquals("File or directory already exists: %s.", e.getError());
  }

  @Test
  public void testErrorRedirectionException() {
    e = new RedirectionException();
    assertEquals("Arguments for redirection are invalid.", e.getError());
  }

  @Test
  public void testErrorRedirectionOperatorsException() {
    e = new RedirectionOperatorsException();
    assertEquals("Argument type is incorrect. Operators for redirection "
        + "must be > or >>", e.getError());
  }

  @Test
  public void testErrorRemovingFileException() {
    e = new RemovingFileException();
    assertEquals("Cannot delete standalone file.", e.getError());
  }

  @Test
  public void testErrorSuccessorException() {
    e = new SuccessorException("%s", "%s");
    assertEquals("Trying to move a node %s to it's sucessor %s", e.getError());
  }

  @Test
  public void testErrorUnknownErrorException() {
    e = new UnknownErrorException();
    assertEquals("Unknown error occurred.", e.getError());
  }

  @Test
  public void testErrorWorkingDirException() {
    e = new WorkingDirException();
    assertEquals("Cannot delete current working directory.", e.getError());
  }
}
