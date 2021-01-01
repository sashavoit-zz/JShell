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
import java.util.Collection;
import org.junit.Test;
import jShell.errors.NodeExistsException;
import jShell.fileSystem.Directory;
import jShell.fileSystem.File;
import jShell.interfaces.IFileSystemNode;
import jShell.mockClasses.MockShell;

public class DirectoryTest {

  /**
   * Testing for Directory methods, create some working and children directories and files
   * 
   * @author Sankalp Sharma
   */

  Directory workingDir = new Directory("working");
  Directory childDir = new Directory("childDir");
  File childFile = new File("childFile");

  // assert that directory was created properly
  @Test
  public void testCreation() {
    assertNotNull(workingDir);
  }

  /*
   * check that children were added properly by checking if children exist in the parent directory
   */
  @Test
  public void testAddingChildren() throws NodeExistsException {
    try {
      workingDir.tryAddChildren(childDir);
      workingDir.tryAddChildren(childFile);
    } catch (NodeExistsException e) {
      return;
    }
    assertTrue(workingDir.ifChildExists(childDir.getName()));
    assertTrue(workingDir.ifChildExists(childFile.getName()));
  }

  /*
   * Check the if child exists method by asserting true by name of the child
   */

  @Test
  public void testIfChildExists() {
    try {
      workingDir.tryAddChildren(childDir);
      workingDir.tryAddChildren(childFile);
    } catch (NodeExistsException e) {
      return;
    }
    assertTrue(workingDir.ifChildExists(childDir.getName()));
  }

  /*
   * Check the getChild method by comparing it with the actual child object.
   */
  @Test
  public void testGetChild() {
    try {
      workingDir.tryAddChildren(childDir);
      workingDir.tryAddChildren(childFile);
    } catch (NodeExistsException e) {
      return;
    }
    assertEquals(workingDir.getChild(childDir.getName()), childDir);
  }

  /*
   * Remove child returns the node that was just removed, so we check by comparing the two objects
   */

  @Test
  public void testRemoveChild() {
    try {
      workingDir.tryAddChildren(childDir);
      workingDir.tryAddChildren(childFile);
    } catch (NodeExistsException e) {
      return;
    }
    assertEquals(workingDir.removeChild(childDir.getName()), childDir);
  }


  /*
   * Test clone by compare the name of the directory and the name of its children
   */
  @Test
  public void testClone() {
    try {
      workingDir.tryAddChildren(childDir);
      workingDir.tryAddChildren(childFile);
    } catch (NodeExistsException e) {
      return;
    }
    Directory workingDir2 = workingDir.clone();
    assertEquals(workingDir.getName(), workingDir2.getName());
    assertTrue(workingDir2.ifChildExists(childDir.getName()));
    assertTrue(workingDir2.ifChildExists(childFile.getName()));
  }

  /*
   * Test get children by comparing size of collection and that the contains method contains both the
   * childDir and the childFile
   */

  @Test
  public void testGetChildren() throws NodeExistsException {
    workingDir.tryAddChildren(childDir);
    workingDir.tryAddChildren(childFile);
    Collection<IFileSystemNode> dirChildren;
    try {
      dirChildren = workingDir.getChildren();
    } catch (Exception e) {
      return;
    }
    // Check that dirChildren has 2 objects and contains both the specified children
    assertTrue(dirChildren.size() == 2);
    assertTrue(dirChildren.contains(childDir));
    assertTrue(dirChildren.contains(childFile));
  }

  /*
   * Test the void version of removeChild by checking the state of the directory after performing
   * removeChild. Check size of parent directory and whether the child has been successfully removed.
   */
  @Test
  public void testRemoveChildVoid() throws NodeExistsException {
    try {
      workingDir.tryAddChildren(childDir);
      workingDir.tryAddChildren(childFile);
    } catch (NodeExistsException e) {
      return;
    }
    workingDir.removeChild(childDir);
    assertTrue(workingDir.getChildren().size() == 1);
    assertFalse(workingDir.getChildren().contains(childDir));

  }
}
