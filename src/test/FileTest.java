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
import jShell.fileSystem.File;

/**
 * Test cases for the File class.
 * 
 * @author Kevin Meharchand
 *
 */
public class FileTest {

  File f = new File("test");

  /*
   * A test to ensure that a file has no children, as it is not a directory.
   */
  @Test
  public void testIfChildExists() {
    assertEquals("Should always return false", false, f.ifChildExists("Dummy"));
  }

  /*
   * Test case ensuring that the file was initialized properly
   */
  @Test
  public void testFile() {
    assertNotNull("Make sure the file was created properly", f);
  }

  /*
   * A test case combining both Set and Get contents for the file.
   */
  @Test
  public void testSetAndGetContents() {
    f.setContents("contents");
    assertEquals("Should return contents", "contents", f.getContents());
  }

  /*
   * A test case designed to ensure that a cloned file will retain the same details and features as
   * the original file.
   */
  @Test
  public void testClone() {
    f.setContents("contents");
    File f2 = f.clone();
    assertEquals("Same name", f.getName(), f2.getName());
    assertEquals("Same contents", f.getContents(), f2.getContents());
  }
}
