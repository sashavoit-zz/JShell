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
import jShell.commands.Mkdir;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;
import jShell.fileSystem.Redirection;
import jShell.fileSystem.File;
import jShell.fileSystem.Path;
import jShell.Shell;
import org.junit.Test;

/**
 * Test cases for the Redirection class.
 * 
 * @author Kevin Meharchand
 *
 */
public class RedirectionTest {

  /*
   * This test file does not make use of mock objects. It is assumed that the dependency between
   * Redirection, Shell and other subsequent file system classes is functional.
   */
  Shell s = new Shell();
  Redirection r = new Redirection();

  /*
   * Testing for the basic functionality of the redirection command.
   */
  @Test
  public void testFileHandling()
      throws InvalidPathException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    Path p = new Path("file");
    r.fileHandling(">", "file", "Contents");
    assertNotNull("Check if the file was created",
        s.getFileSystem().getNodeAtPath(p));
    File f = (File) s.getFileSystem().getNodeAtPath(p);
    assertEquals("Make sure the file's contents are correct", "Contents",
        f.getContents());
    Path p2 = new Path("file2");
    r.fileHandling(">>", "file2", "appended");
    assertNotNull("Check file creation with append",
        s.getFileSystem().getNodeAtPath(p2));
    File f2 = (File) s.getFileSystem().getNodeAtPath(p2);
    assertEquals("Make sure the file's contents are correct", "appended",
        f2.getContents());
  }

  /*
   * Testing for a bad redirection operator being passed into fileHandling
   */
  @Test(expected = RedirectionOperatorsException.class)
  public void testFileHandlingBadRedirOp()
      throws InvalidPathException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    Path p = new Path("file");
    r.fileHandling(">>>", "file", "Unimportant");
  }

  /*
   * Testing for a bad file name being passed into fileHandling
   */
  @Test(expected = BadFileNameException.class)
  public void testFileHandlingBadFilename()
      throws InvalidPathException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    Path p = new Path("file.txt");
    r.fileHandling(">", "file.txt", "Unimportant");
  }

  /*
   * Tests to ensure that redirection works when given paths to files as well
   */
  @Test
  public void testFileHandlingWithPath() throws InvalidPathException,
      NodeExistsException, ArgsNumberException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    Mkdir m = new Mkdir();
    String[] params = {"mkdir", "a"};
    m.run(params);
    Path p = new Path("a/file");
    r.fileHandling(">", "a/file", "contents");
    assertNotNull(s.getFileSystem().getNodeAtPath(p));
    File f = (File) s.getFileSystem().getNodeAtPath(p);
    assertEquals("Make sure the file's contents are correct", "contents",
        f.getContents());
  }

  /*
   * Tests for the checkRedirection method, used to ensure redirection argument validity before
   * redirecting anything.
   */
  @Test
  public void testCheckRedirection() {
    assertEquals("Bad operator", false, r.checkRedirection(">>>", "file"));
    assertEquals("Bad filename", false, r.checkRedirection(">", "file.txt"));
    assertEquals("All good", true, r.checkRedirection(">", "file"));
    assertEquals("Good with path", true, r.checkRedirection(">", "a/file"));
    assertEquals("Good with full path", true,
        r.checkRedirection(">", "/a/file"));
  }
}
