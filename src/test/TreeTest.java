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
import jShell.commands.Tree;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.DeleteRootDirException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;
import jShell.errors.WorkingDirException;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;

public class TreeTest {

  /**
   * Setting up mockShell and Tree objects for this
   */

  MockShell s = new MockShell();
  Tree t = new Tree();

  /*
   * test ArsNumberException, user shouldn't enter any arguments after tree, unless doing redirection
   */
  @Test(expected = ArgsNumberException.class)
  public void testTooManyArgs()
      throws InvalidPathException, ArgsNumberException, NodeExistsException,
      BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, RedirectionException {
    String[] params = {"tree", "a"};
    t.run(params);
  }

  // invalid filename test
  @Test(expected = BadFileNameException.class)
  public void testRedirectionWithInvalidFileName() throws InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, ArgsNumberException, RedirectionException {
    String[] params = {"tree", ">", "!"};
    t.run(params);
  }

  /*
   * Valid Tree call, should print out mockfilesystem, compare with tree's output
   */
  @Test
  public void testValidTree() throws InvalidPathException, NodeExistsException,
      BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, ArgsNumberException, RedirectionException {
    String[] params = {"tree"};
    t.run(params);
    assertEquals(s.getShell().getFileSystem().toString(), t.run(params));
  }

  // test ValidRedirection, both append and overwrite
  @Test
  public void testValidRedirection() throws InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, ArgsNumberException, RedirectionException {
    String[] params = {"tree", ">", "childFile"};
    t.run(params);
    String outputMessageOverWrite = "Redirecting output for overwriting:"
        + s.getShell().getFileSystem().toString() + "\n to file with name: "
        + "childFile";
    assertEquals(outputMessageOverWrite, s.getLastOutput());

    // testing append
    String[] params2 = {"tree", ">>", "childFile"};
    t.run(params2);
    String outputMessageAppend = "Redirecting output for appending:"
        + s.getShell().getFileSystem().toString() + "\n to file with name: "
        + "childFile";
    assertEquals(outputMessageAppend, s.getLastOutput());
  }

  // test RedirectionException, those are invalid args for Redirection

  @Test(expected = RedirectionException.class)
  public void testInValidRedirection() throws InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, ArgsNumberException, RedirectionException {
    String[] params = {"tree", "childFile", "a"};
    t.run(params);
  }

  @Test
  public void testToString() {
    assertEquals("tree", t.toString());
  }
}
