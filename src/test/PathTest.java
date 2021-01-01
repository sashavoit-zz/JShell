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
import org.junit.Before;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;
import jShell.fileSystem.Path;

/**
 * Class to test Path class
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class PathTest {

  // We will use MockShell object for testing
  MockShell shell;

  @Before
  public void init() {
    // Initializing the mock shell
    shell = new MockShell();
  }

  @Test
  public void testToString() {
    String expected;
    String actual;
    Path p;
    // To string of usual path
    p = new Path("/working");
    expected = "/working";
    actual = p.toString();
    assertEquals(expected, actual);
    // To string of path to root
    p = new Path("/");
    expected = "/";
    actual = p.toString();
    assertEquals(expected, actual);
    // To string of empty path
    p = new Path("");
    expected = "";
    actual = p.toString();
    assertEquals(expected, actual);
  }

  @Test
  public void testEquals() {
    Path p1;
    Path p2;
    // Same paths are equal
    p1 = new Path("/working");
    p2 = new Path("/working");
    assertTrue(p1.equals(p2));
    // Pointing to the same location, but different paths!
    p1 = new Path("/working");
    p2 = new Path(".");
    assertFalse(p1.equals(p2));
    // Path is not equal to string
    p1 = new Path("/working");
    String notPath = "/working";
    assertFalse(p1.equals(notPath));
  }

  @Test
  public void testClone() {
    Path p1;
    Path p2;
    // Clone has same contents
    p1 = new Path("/working");
    p2 = p1.clone();
    assertEquals(p1, p2);
    // Clone does not point to the same object
    p1 = new Path("/working");
    p2 = p1.clone();
    assertFalse(p1 == p2);
  }

  @Test
  public void testIsValidFullPath() {
    Path p;
    // Empty path is not valid full path
    p = new Path("");
    assertTrue(p.isValidPath());
    // Full path to root is valid full path
    p = new Path("/");
    assertTrue(p.isValidFullPath());
    // Full path to directory is valid full path
    p = new Path(MockFileSystem.validDirFullPath);
    assertTrue(p.isValidFullPath());
    // Relative path to directory is not valid full path
    p = new Path(MockFileSystem.validDirRelativePath);
    assertFalse(p.isValidFullPath());
    // Full path to file is valid full path
    p = new Path(MockFileSystem.validFileFullPath);
    assertTrue(p.isValidFullPath());
    // Relative path to file is not valid full path
    p = new Path(MockFileSystem.validFileRelativePath);
    assertFalse(p.isValidFullPath());
    // Path to non-existing location is not valid full path
    p = new Path("/this/path/is/invalid");
    assertFalse(p.isValidFullPath());
  }

  @Test
  public void testIsValidRelativePath() {
    Path p;
    // Empty path is valid relative path
    p = new Path("");
    assertTrue(p.isValidRelativePath());
    // Full path to root is not valid relative path
    p = new Path("/");
    assertFalse(p.isValidRelativePath());
    // Full path to directory is not valid relative path
    p = new Path(MockFileSystem.validDirFullPath);
    assertFalse(p.isValidRelativePath());
    // Relative path to directory is valid relative path
    p = new Path(MockFileSystem.validDirRelativePath);
    assertTrue(p.isValidRelativePath());
    // Full path to file is not valid relative path
    p = new Path(MockFileSystem.validFileFullPath);
    assertFalse(p.isValidRelativePath());
    // Relative path to file is valid relative path
    p = new Path(MockFileSystem.validFileRelativePath);
    assertTrue(p.isValidRelativePath());
    // Path to non-existing location is not valid relative path
    p = new Path("this/path/is/invalid");
    assertFalse(p.isValidRelativePath());
  }

  @Test
  public void testIsValidPath() {
    Path p;
    // Empty path is valid path
    p = new Path("");
    assertTrue(p.isValidPath());
    // Full path to root is valid path
    p = new Path("/");
    assertTrue(p.isValidPath());
    // Full path to directory is valid path
    p = new Path(MockFileSystem.validDirFullPath);
    assertTrue(p.isValidPath());
    // Relative path to directory is valid path
    p = new Path(MockFileSystem.validDirRelativePath);
    assertTrue(p.isValidPath());
    // Full path to file is valid path
    p = new Path(MockFileSystem.validFileFullPath);
    assertTrue(p.isValidPath());
    // Relative path to file is valid path
    p = new Path(MockFileSystem.validFileRelativePath);
    assertTrue(p.isValidPath());
    // Path to non-existing location is not valid path
    p = new Path("/this/path/is/invalid");
    assertFalse(p.isValidFullPath());
  }

  @Test
  public void testIsPathToDirectory() {
    Path p;
    // Return false if path is invalid
    p = new Path("/this/path/is/invalid");
    assertFalse(p.isPathToDirectory());
    // Full path to root
    p = new Path("/");
    assertTrue(p.isPathToDirectory());
    // Empty path points to working directory
    p = new Path("");
    assertTrue(p.isPathToDirectory());
    // Full path to directory
    p = new Path(MockFileSystem.validDirFullPath);
    assertTrue(p.isPathToDirectory());
    // Relative path to directory
    p = new Path(MockFileSystem.validDirRelativePath);
    assertTrue(p.isPathToDirectory());
    // Full path to file
    p = new Path(MockFileSystem.validFileFullPath);
    assertFalse(p.isPathToDirectory());
    // Full path to file
    p = new Path(MockFileSystem.validFileRelativePath);
    assertFalse(p.isPathToDirectory());
  }

  @Test
  public void testDoubleSlashChecker() {
    Path p;
    // Proper path
    p = new Path("/working/");
    assertTrue(p.doubleSlashChecker());
    // Path, which is solely two slash characters
    p = new Path("//");
    assertFalse(p.doubleSlashChecker());
    // Path with two slashes at the beginning
    p = new Path("//working");
    assertFalse(p.doubleSlashChecker());
    // Path with two slashes at the end
    p = new Path("/working//");
    assertFalse(p.doubleSlashChecker());
    // Path with multiple consecutive slash characters
    p = new Path("//working//");
    assertFalse(p.doubleSlashChecker());
    p = new Path("///working");
    assertFalse(p.doubleSlashChecker());
  }

  @Test
  public void testGetSequence() {
    Path p;
    // Sequence of empty path is empty
    p = new Path("");
    assertEquals(0, p.getSequence().length);
    // Sequence of path to root is empty
    p = new Path("/");
    assertEquals(0, p.getSequence().length);
    // Sequence of full path
    p = new Path(MockFileSystem.validDirFullPath);
    String[] expected1 = {"working", "childDir"};
    String[] actual1 = p.getSequence();
    assertArrayEquals(expected1, actual1);
    // Sequence of relative path
    p = new Path(MockFileSystem.validDirRelativePath);
    String[] expected2 = {"childDir"};
    String[] actual2 = p.getSequence();
    assertArrayEquals(expected2, actual2);
    // Sequence of path ending with a slash
    p = new Path(MockFileSystem.validDirFullPath + "/");
    String[] expected3 = {"working", "childDir"};
    String[] actual3 = p.getSequence();
    assertArrayEquals(expected3, actual3);
  }

  @Test
  public void testGetParentPath() {
    Path p;
    Path expected;
    Path actual;
    // Parent path of full path
    p = new Path(MockFileSystem.validDirFullPath);
    expected = new Path(MockFileSystem.workingDirFullPath);
    actual = (Path) p.getParentPath();
    assertEquals(expected, actual);
    // Parent path of relative path is full path
    p = new Path(MockFileSystem.validDirRelativePath);
    expected = new Path(MockFileSystem.workingDirFullPath);
    actual = (Path) p.getParentPath();
    assertEquals(expected, actual);
    // Parent path of root path is root path
    p = new Path(MockFileSystem.fullPathRoot);
    expected = new Path(MockFileSystem.fullPathRoot);
    actual = (Path) p.getParentPath();
    assertEquals(expected, actual);
    // Parent path of full path ending with a slash
    p = new Path(MockFileSystem.validDirFullPath + "/");
    expected = new Path(MockFileSystem.workingDirFullPath);
    actual = (Path) p.getParentPath();
    assertEquals(expected, actual);
    // Parent path of path containing .. at the end
    p = new Path(MockFileSystem.validDirFullPath + "/..");
    expected = new Path(MockFileSystem.fullPathRoot);
    actual = (Path) p.getParentPath();
    assertEquals(expected, actual);
    // Parent path of path containing .. at the beginning
    p = new Path("../" + MockFileSystem.validDirFullPath);
    expected = new Path(MockFileSystem.workingDirFullPath);
    actual = (Path) p.getParentPath();
    assertEquals(expected, actual);
    // Parent path of path containing . at the end
    p = new Path(MockFileSystem.validDirFullPath + "/.");
    expected = new Path(MockFileSystem.workingDirFullPath);
    actual = (Path) p.getParentPath();
    assertEquals(expected, actual);
    // Parent path of path containing . at the beginning
    p = new Path("/." + MockFileSystem.validDirFullPath);
    expected = new Path(MockFileSystem.workingDirFullPath);
    actual = (Path) p.getParentPath();
    assertEquals(expected, actual);
  }

  @Test
  public void testGetBottomName() {
    Path p;
    String expected;
    String actual;
    // Bottom name of full path
    p = new Path(MockFileSystem.validDirFullPath);
    expected = "childDir";
    actual = p.getBottomName();
    assertEquals(expected, actual);
    // Bottom name of relative path
    p = new Path(MockFileSystem.validDirRelativePath);
    expected = "childDir";
    actual = p.getBottomName();
    assertEquals(expected, actual);
    // Bottom name of path ending with /
    p = new Path(MockFileSystem.validDirFullPath + "/");
    expected = "childDir";
    actual = p.getBottomName();
    assertEquals(expected, actual);
    // Bottom name of path ending with .
    p = new Path(MockFileSystem.validDirFullPath + "/.");
    expected = "childDir";
    actual = p.getBottomName();
    assertEquals(expected, actual);
    // Bottom name of path ending with ..
    p = new Path(MockFileSystem.validDirFullPath + "/..");
    expected = "working";
    actual = p.getBottomName();
    assertEquals(expected, actual);
  }

  @Test
  public void testAppendNodeName() {
    Path p;
    String name;
    Path expected;
    Path actual;
    // Appending name to empty path
    p = new Path("");
    name = "dirName";
    expected = new Path("dirName");
    actual = (Path) p.appendNodeName(name);
    assertEquals(expected, actual);
    // Appending name to root path
    p = new Path("/");
    name = "dirName";
    expected = new Path("/dirName");
    actual = (Path) p.appendNodeName(name);
    assertEquals(expected, actual);
    // Appending name to full path
    p = new Path(MockFileSystem.validDirFullPath);
    name = "dirName";
    expected = new Path(MockFileSystem.validDirFullPath + "/dirName");
    actual = (Path) p.appendNodeName(name);
    assertEquals(expected, actual);
    // Appending name to relative path (full path is obtained)
    p = new Path(MockFileSystem.validDirRelativePath);
    name = "dirName";
    expected = new Path(MockFileSystem.validDirRelativePath + "/dirName");
    actual = (Path) p.appendNodeName(name);
    assertEquals(expected, actual);
    // Appending name to path ending with /
    p = new Path(MockFileSystem.validDirFullPath + "/");
    name = "dirName";
    expected = new Path(MockFileSystem.validDirFullPath + "/dirName");
    actual = (Path) p.appendNodeName(name);
    assertEquals(expected, actual);
  }

  @Test
  public void testConvertRelativeToFull() {
    Path p;
    Path expected;
    Path actual;
    // Empty path
    p = new Path("");
    expected = new Path(MockFileSystem.workingDirFullPath);
    actual = (Path) p.convertRelativeToFull();
    assertEquals(expected, actual);
    // Regular relative path
    p = new Path(MockFileSystem.validDirRelativePath);
    expected = new Path(MockFileSystem.validDirFullPath);
    actual = (Path) p.convertRelativeToFull();
    assertEquals(expected, actual);
  }

  @Test
  public void testIsSuccessor() {
    Path p1;
    Path p2;
    // Node is successor of itself (full path and full path)
    p1 = new Path(MockFileSystem.workingDirFullPath);
    p2 = new Path(MockFileSystem.workingDirFullPath);
    assertTrue(p1.isSuccessor(p2));
    // Node is successor of itself (relative path and full path)
    p1 = new Path(MockFileSystem.workingDirFullPath);
    p2 = new Path("");
    assertTrue(p1.isSuccessor(p2));
    // Node is successor of itself (full path and relative path)
    p1 = new Path(MockFileSystem.workingDirFullPath);
    p2 = new Path("");
    assertTrue(p1.isSuccessor(p2));
    // Node is successor of itself (relative path and relative path)
    p1 = new Path("");
    p2 = new Path("");
    assertTrue(p1.isSuccessor(p2));
    // Child is successor of parent
    p1 = new Path(MockFileSystem.workingDirFullPath);
    p2 = new Path(MockFileSystem.validDirFullPath);
    assertTrue(p1.isSuccessor(p2));
    // Parent is not a successor of parent
    p1 = new Path(MockFileSystem.validDirFullPath);
    p2 = new Path(MockFileSystem.workingDirFullPath);
    assertFalse(p1.isSuccessor(p2));
    // Anything is successor of root
    p1 = new Path("/");
    p2 = new Path(MockFileSystem.workingDirFullPath);
    assertTrue(p1.isSuccessor(p2));
    p2 = new Path(MockFileSystem.validDirFullPath);
    assertTrue(p1.isSuccessor(p2));
    // Root is successor of nothing
    p2 = new Path("/");
    p1 = new Path(MockFileSystem.workingDirFullPath);
    assertFalse(p1.isSuccessor(p2));
    p1 = new Path(MockFileSystem.validDirFullPath);
    assertFalse(p1.isSuccessor(p2));
    // Root is successor of itself
    p1 = new Path("/");
    p2 = new Path("/");
    assertTrue(p1.isSuccessor(p2));
  }
}
