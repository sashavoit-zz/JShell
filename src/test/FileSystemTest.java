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
import java.util.Queue;
import java.util.LinkedList;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import jShell.Shell;
import jShell.errors.*;
import jShell.fileSystem.*;
import jShell.interfaces.IFileSystemNode;

/**
 * Class to test FileSystem object
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class FileSystemTest {

  // References to file system and nodes in file system
  FileSystem fs;
  Directory root;
  Directory dir;
  Directory childDir;
  File file;
  Directory anotherDir;
  Directory anotherChildDir;

  @Before
  public void init() {
    // Initialize shell object
    new Shell();

    /*
     * Initializing a sample file system tree, which looks like:
     * 
     * \ dir childDir file anotherDir childDir
     * 
     */

    fs = FileSystem.getFileSystem();
    root = (Directory) fs.getRootDir();
    dir = new Directory("dir");
    childDir = new Directory("childDir");
    file = new File("file");
    anotherDir = new Directory("anotherDir");
    anotherChildDir = new Directory("anotherChildDir");
    try {
      root.tryAddChildren(dir);
      root.tryAddChildren(anotherDir);
      dir.tryAddChildren(childDir);
      dir.tryAddChildren(file);
      anotherDir.tryAddChildren(anotherChildDir);
    } catch (NodeExistsException e) {
      // tryAddChildren shouldn't fail
      System.out.println("Failure itilializing");
    }
  }

  @Test
  public void testGetRootDir() {
    Directory root = (Directory) fs.getRootDir();
    System.out.println(root.getName());
    assertEquals(root.getName(), "");
  }

  @Test
  public void testSetRootDir() {
    Directory newRoot = new Directory("newRoot");
    fs.setRootDir(newRoot);
    assertEquals(newRoot, fs.getRootDir());
  }

  @Test
  public void testGetNodeAtFullPathValidRegularPath() {
    Path p;
    Directory d;
    File f;
    try {
      // Getting root directory
      p = new Path("/");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, fs.getRootDir());
      // Getting directory at first level
      p = new Path("/dir");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, dir);
      // Getting directory at deeper level
      p = new Path("/dir/childDir");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, childDir);
      // Getting file
      p = new Path("/dir/file");
      f = (File) fs.getNodeAtFullPath(p);
      assertEquals(f, file);
      // Path with slash at the end
      p = new Path("/dir/file/");
      f = (File) fs.getNodeAtFullPath(p);
      assertEquals(f, file);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetNodeAtFullPathValidPathWithDot() {
    Path p;
    Directory d;
    try {
      // . at root refer to root directory
      p = new Path("/.");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, fs.getRootDir());
      // Getting directory using . at the end
      p = new Path("/dir/childDir/.");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, childDir);
      // Getting directory using . at the beginning
      p = new Path("/./dir/");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, dir);
      // Getting directory using .. in the middle
      p = new Path("/dir/./childDir");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, childDir);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetNodeAtFullPathValidPathWithTwoDots() {
    Path p;
    Directory d;
    try {
      // .. at root refer to root directory
      p = new Path("/..");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, fs.getRootDir());
      // Getting directory using .. at the end
      p = new Path("/dir/childDir/..");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, dir);
      // Getting directory using .. at the beginning
      p = new Path("/../dir/");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, dir);
      // Getting directory using .. in the middle
      p = new Path("/dir/childDir/../childDir");
      d = (Directory) fs.getNodeAtFullPath(p);
      assertEquals(d, childDir);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetNodeAtFullPathInvalidPath() {
    Path p;
    try {
      // simple invalid path
      p = new Path("/invalid");
      fs.getNodeAtFullPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path of greater length
      p = new Path("/dir/invalid");
      fs.getNodeAtFullPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path involving multiple invalid locations
      p = new Path("/dir/invalid/anotherInvalid");
      fs.getNodeAtFullPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path involving ..
      p = new Path("/dir/invalid/..");
      fs.getNodeAtFullPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path involving .
      p = new Path("/dir/./invalid");
      fs.getNodeAtFullPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path with slash at the end
      p = new Path("/dir/invalid/");
      fs.getNodeAtFullPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
  }

  public void testGetNodeAtRelativePathValidRegularPath() {
    Path p;
    Directory d;
    File f;
    try {
      // Getting root directory
      p = new Path("");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, fs.getRootDir());
      // Getting directory at first level
      p = new Path("dir");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, dir);
      // Getting directory at deeper level
      p = new Path("childDir");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, childDir);
      // Getting file
      p = new Path("file");
      f = (File) fs.getNodeAtRelativePath(p);
      assertEquals(f, file);
      // Path with slash at the end
      p = new Path("file/");
      f = (File) fs.getNodeAtRelativePath(p);
      assertEquals(f, file);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetNodeAtRelativePathValidPathWithDot() {
    Path p;
    Directory d;
    try {
      // . at root refer to root directory
      p = new Path(".");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, fs.getRootDir());
      // Getting directory using . at the end
      p = new Path("dir/childDir/.");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, childDir);
      // Getting directory using . at the beginning
      p = new Path("./dir/");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, dir);
      // Getting directory using .. in the middle
      p = new Path("dir/./childDir");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, childDir);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetNodeAtRelativePathValidPathWithTwoDots() {
    Path p;
    Directory d;
    try {
      // .. at root refer to root directory
      p = new Path("..");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, fs.getRootDir());
      // Getting directory using .. at the end
      p = new Path("dir/childDir/..");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, dir);
      // Getting directory using .. at the beginning
      p = new Path("../dir/");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, dir);
      // Getting directory using .. in the middle
      p = new Path("dir/childDir/../childDir");
      d = (Directory) fs.getNodeAtRelativePath(p);
      assertEquals(d, childDir);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetNodeAtRelativePathInvalidPath() {
    Path p;
    try {
      // simple invalid path
      p = new Path("invalid");
      fs.getNodeAtRelativePath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path of greater length
      p = new Path("dir/invalid");
      fs.getNodeAtRelativePath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path involving multiple invalid locations
      p = new Path("dir/invalid/anotherInvalid");
      fs.getNodeAtRelativePath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path involving ..
      p = new Path("dir/invalid/..");
      fs.getNodeAtRelativePath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path involving .
      p = new Path("dir/./invalid");
      fs.getNodeAtRelativePath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid path with slash at the end
      p = new Path("dir/invalid/");
      fs.getNodeAtRelativePath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
  }

  public void testGetNodeAtPathValidRegularPath() {
    Path p;
    Directory d;
    File f;
    try {
      // Getting root directory
      p = new Path("");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, fs.getRootDir());
      p = new Path("/");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, fs.getRootDir());
      // Getting directory at first level
      p = new Path("dir");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, dir);
      p = new Path("/dir");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, dir);
      // Getting directory at deeper level
      p = new Path("childDir");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, childDir);
      p = new Path("/childDir");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, childDir);
      // Getting file
      p = new Path("file");
      f = (File) fs.getNodeAtPath(p);
      assertEquals(f, file);
      p = new Path("/file");
      f = (File) fs.getNodeAtPath(p);
      assertEquals(f, file);
      // Path with slash at the end
      p = new Path("file/");
      f = (File) fs.getNodeAtPath(p);
      assertEquals(f, file);
      p = new Path("/file/");
      f = (File) fs.getNodeAtPath(p);
      assertEquals(f, file);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetNodeAtPathValidPathWithDot() {
    Path p;
    Directory d;
    try {
      // . at root refer to root directory
      p = new Path(".");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, fs.getRootDir());
      p = new Path("/.");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, fs.getRootDir());
      // Getting directory using . at the end
      p = new Path("dir/childDir/.");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, childDir);
      p = new Path("/dir/childDir/.");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, childDir);
      // Getting directory using . at the beginning
      p = new Path("./dir/");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, dir);
      p = new Path("/./dir/");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, dir);
      // Getting directory using .. in the middle
      p = new Path("dir/./childDir");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, childDir);
      p = new Path("/dir/./childDir");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, childDir);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetNodeAtPathValidPathWithTwoDots() {
    Path p;
    Directory d;
    try {
      // .. at root refer to root directory
      p = new Path("..");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, fs.getRootDir());
      p = new Path("/..");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, fs.getRootDir());
      // Getting directory using .. at the end
      p = new Path("dir/childDir/..");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, dir);
      p = new Path("/dir/childDir/..");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, dir);
      // Getting directory using .. at the beginning
      p = new Path("../dir/");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, dir);
      p = new Path("/../dir/");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, dir);
      // Getting directory using .. in the middle
      p = new Path("dir/childDir/../childDir");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, childDir);
      p = new Path("/dir/childDir/../childDir");
      d = (Directory) fs.getNodeAtPath(p);
      assertEquals(d, childDir);

    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetNodeAtPathInvalidPath() {
    Path p;
    try {
      // simple full relative invalid path
      p = new Path("invalid");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // simple full invalid path
      p = new Path("/invalid");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid full relative path of greater length
      p = new Path("dir/invalid");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid full path of greater length
      p = new Path("/dir/invalid");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid relative path involving multiple invalid locations
      p = new Path("dir/invalid/anotherInvalid");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid full path involving multiple invalid locations
      p = new Path("/dir/invalid/anotherInvalid");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid relative path involving ..
      p = new Path("dir/invalid/..");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid full path involving ..
      p = new Path("/dir/invalid/..");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid relative path involving .
      p = new Path("dir/./invalid");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid full path involving .
      p = new Path("/dir/./invalid");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid relative path with slash at the end
      p = new Path("dir/invalid/");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
    try {
      // invalid full path with slash at the end
      p = new Path("/dir/invalid/");
      fs.getNodeAtPath(p);
      fail();
    } catch (InvalidPathException e) {
      // expected to throw exception
    }
  }

  @Test
  public void testCreateNewFileValidAtFirstLevel() {
    Path p = new Path("/newFile");
    try {
      File newFile = (File) fs.createNewFile(p);
      assertNotNull(newFile);
      assertEquals(newFile.getName(), "newFile");
      assertEquals(root.getChild("newFile"), newFile);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewFileValidAtDeeperLevel() {
    Path p = new Path("/dir/childDir/newFile");
    try {
      File newFile = (File) fs.createNewFile(p);
      assertNotNull(newFile);
      assertEquals(newFile.getName(), "newFile");
      assertEquals(childDir.getChild("newFile"), newFile);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewFileInvalidPath() {
    Path p = new Path("/dir/thisDirDoesNotExist/newFile");
    try {
      fs.createNewFile(p);
    } catch (InvalidPathException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewFileBadName() {
    Path p = new Path("/dir/thisDirDoesNotExist/thisName.isInvalid");
    try {
      fs.createNewFile(p);
    } catch (BadFileNameException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewFileFileAlreadyExists() {
    Path p = new Path("/dir/file");
    try {
      fs.createNewFile(p);
    } catch (NodeExistsException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewFileDirAlreadyExists() {
    Path p = new Path("/dir/childDir");
    try {
      fs.createNewFile(p);
    } catch (NodeExistsException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewDirValidAtFirstLevel() {
    Path p = new Path("/newDir");
    try {
      Directory newDir = (Directory) fs.createNewDirectory(p);
      assertNotNull(newDir);
      assertEquals(newDir.getName(), "newDir");
      assertEquals(root.getChild("newDir"), newDir);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewDirValidAtDeeperLevel() {
    Path p = new Path("/dir/childDir/newDir");
    try {
      Directory newDir = (Directory) fs.createNewDirectory(p);
      assertNotNull(newDir);
      assertEquals(newDir.getName(), "newDir");
      assertEquals(childDir.getChild("newDir"), newDir);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewDirInvalidPath() {
    Path p = new Path("/dir/thisDirDoesNotExist/newDir");
    try {
      fs.createNewDirectory(p);
    } catch (InvalidPathException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewDirBadName() {
    Path p = new Path("/dir/thisDirDoesNotExist/thisName.isInvalid");
    try {
      fs.createNewDirectory(p);
    } catch (BadFileNameException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewDirFileAlreadyExists() {
    Path p = new Path("/dir/file");
    try {
      fs.createNewDirectory(p);
    } catch (NodeExistsException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateNewDirDirAlreadyExists() {
    Path p = new Path("/dir/childDir");
    try {
      fs.createNewDirectory(p);
    } catch (NodeExistsException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetParent() {
    FileSystemNode node;
    FileSystemNode expected;
    FileSystemNode actual;
    // Parent of root should refer to root
    node = root;
    expected = (FileSystemNode) root;
    actual = (FileSystemNode) fs.getParent(node);
    assertEquals(expected, actual);
    // Parent of directory at first level
    node = dir;
    expected = (FileSystemNode) root;
    actual = (FileSystemNode) fs.getParent(node);
    assertEquals(expected, actual);
    // Parent of directory at deeper level
    node = childDir;
    expected = (FileSystemNode) dir;
    actual = (FileSystemNode) fs.getParent(node);
    assertEquals(expected, actual);
    // Parent of file
    node = file;
    expected = (FileSystemNode) dir;
    actual = (FileSystemNode) fs.getParent(node);
    assertEquals(expected, actual);
    // Parent of node outside of file tree
    node = new Directory("thisDirectoryIsOutsideOfFS");
    expected = null;
    actual = (FileSystemNode) fs.getParent(node);
    assertEquals(expected, actual);
  }

  @Test
  public void testToString() {
    String expected = "/\n" + "\tanotherDir\n" + "\t\tanotherChildDir\n"
        + "\tdir\n" + "\t\tfile\n" + "\t\tchildDir\n";
    String actual = fs.toString();
    assertEquals(expected, actual);
  }

  @Test
  public void testRemoveNodeLeaf() {
    Path toDelete = new Path("/dir/childDir");
    try {
      FileSystemNode deleted = (FileSystemNode) fs.removeNode(toDelete);
      assertEquals(childDir, deleted);
      assertFalse(dir.ifChildExists("childDir"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemoveNodeHigherLevel() {
    Path toDelete = new Path("/dir");
    try {
      FileSystemNode deleted = (FileSystemNode) fs.removeNode(toDelete);
      assertEquals(dir, deleted);
      assertFalse(root.ifChildExists("dir"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemoveNodeFile() {
    Path toDelete = new Path("/dir/file");
    try {
      FileSystemNode deleted = (FileSystemNode) fs.removeNode(toDelete);
      assertEquals(file, deleted);
      assertFalse(dir.ifChildExists("file"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRemoveNodeInvalidPath() {
    Path toDelete = new Path("/dir/childDir/invalid");
    try {
      fs.removeNode(toDelete);
      fail();
    } catch (InvalidPathException | WorkingDirException e) {
    }
  }

  @Test
  public void testMoveNodeValidAppend() {
    Path pathToNode = new Path("/anotherDir");
    Path newParent = new Path("/dir");
    try {
      fs.moveNode(pathToNode, newParent);
      // Moved node was deleted from old location
      assertFalse(root.ifChildExists("anotherDir"));
      // Node has been moved to new location
      assertEquals(dir.getChild("anotherDir"), anotherDir);
      // Node still has it's children
      assertTrue(anotherDir.ifChildExists("anotherChildDir"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMoveNodeValidOverwrite() {
    Path pathToNode = new Path("/anotherDir");
    Path newParent = new Path("/dir");
    try {
      // First create another child of dir with name anotherDir
      Directory newChild = new Directory("anotherDir");
      dir.tryAddChildren(newChild);
      fs.moveNode(pathToNode, newParent);
      // Moved node was deleted from old location
      assertFalse(root.ifChildExists("anotherDir"));
      // Node has been moved to new location
      assertEquals(dir.getChild("anotherDir"), anotherDir);
      // Node still has it's children
      assertTrue(anotherDir.ifChildExists("anotherChildDir"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMoveNodeFile() {
    Path pathToNode = new Path("/dir/file");
    Path newParent = new Path("/anotherDir");
    try {
      fs.moveNode(pathToNode, newParent);
      // Moved node was deleted from old location
      assertFalse(dir.ifChildExists("file"));
      // Node has been moved to new location
      assertEquals(anotherDir.getChild("file"), file);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMoveNodeInvalidPathToNode() {
    Path pathToNode = new Path("/dir/invalid");
    Path newParent = new Path("/anotherDir");
    try {
      fs.moveNode(pathToNode, newParent);
      fail();
    } catch (InvalidPathException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMoveNodeInvalidPathToParent() {
    Path pathToNode = new Path("/dir/childDir");
    Path newParent = new Path("/invalid/parent/dir");
    try {
      fs.moveNode(pathToNode, newParent);
      fail();
    } catch (InvalidPathException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMoveNodeTryingToMoveToSuccessor() {
    Path pathToNode = new Path("/dir");
    Path newParent = new Path("/dir/childDir");
    try {
      fs.moveNode(pathToNode, newParent);
      fail();
    } catch (SuccessorException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMoveNodeTryingToMoveDirToFile() {
    Path pathToNode = new Path("/anotherDir");
    Path newParent = new Path("/dir/file");
    try {
      fs.moveNode(pathToNode, newParent);
      fail();
    } catch (DirNotFoundException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMoveNodeOverwritingTheFile() {
    Path pathToNode = new Path("/dir/file");
    try {
      ((File) fs.getNodeAtPath(pathToNode)).setContents("CONTENTS");
    } catch (Exception e) {
      fail();
    }
    Path pathToFile = new Path("/dir/anotherFile");
    File f = null;
    try {
      f = (File) fs.createNewFile(pathToFile);
    } catch (Exception e) {
      fail();
    }
    try {
      fs.moveNode(pathToNode, pathToFile);
      // Moved node was deleted from old location
      assertFalse(dir.ifChildExists("file"));
      // Checking contents of overwritten file
      assertEquals(f.getContents(), "CONTENTS");
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMoveNodeOverwritingTheDirectory() {
    Path pathToNode = new Path("/dir/childDir");
    Path newPath = new Path("/anotherDir/");
    try {
      fs.moveNode(pathToNode, newPath);
      // Moved node was deleted from old location
      assertFalse(dir.ifChildExists("childDir"));
      // Checking overwritten directory is present
      assertTrue(anotherDir.ifChildExists("childDir"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMoveNodeRenamingTheFile() {
    Path pathToNode = new Path("/dir/file");
    try {
      ((File) fs.getNodeAtPath(pathToNode)).setContents("CONTENTS");

      Path pathToFile = new Path("/dir/anotherFile");
      File f = (File) fs.moveNode(pathToNode, pathToFile);
      // Moved node appears at new location
      assertTrue(dir.ifChildExists("anotherFile"));
      // Moved node was deleted from old location
      assertFalse(dir.ifChildExists("file"));
      // Checking contents of overwritten file
      assertEquals(f.getContents(), "CONTENTS");
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyNodeValidAppend() {
    Path pathToNode = new Path("/anotherDir");
    Path newParent = new Path("/dir");
    try {
      fs.copyNode(pathToNode, newParent);
      // Copied node is still present at old location
      assertTrue(root.ifChildExists("anotherDir"));
      // Copy of node exists at new location
      assertTrue(dir.ifChildExists("anotherDir"));
      // Old node and new node are different references
      assertNotEquals(anotherDir, dir.getChild("anotherDir"));
      // Old node still has it's children
      assertTrue(anotherDir.ifChildExists("anotherChildDir"));
      // New node still has it's children
      assertTrue(dir.getChild("anotherDir").ifChildExists("anotherChildDir"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyNodeValidOverwrite() {
    Path pathToNode = new Path("/anotherDir");
    Path newParent = new Path("/dir");
    try {
      // First create another child of dir with name anotherDir
      Directory newChild = new Directory("anotherDir");
      dir.tryAddChildren(newChild);
      fs.copyNode(pathToNode, newParent);
      // Copied node is still present at old location
      assertTrue(root.ifChildExists("anotherDir"));
      // Copy of node exists at new location
      assertTrue(dir.ifChildExists("anotherDir"));
      // Old node and new node are different references
      assertNotEquals(anotherDir, dir.getChild("anotherDir"));
      // Old node still has it's children
      assertTrue(anotherDir.ifChildExists("anotherChildDir"));
      // New node still has it's children
      assertTrue(dir.getChild("anotherDir").ifChildExists("anotherChildDir"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyNodeFile() {
    Path pathToNode = new Path("/dir/file");
    Path newParent = new Path("/anotherDir");
    try {
      fs.copyNode(pathToNode, newParent);
      // Copied node is still present at old location
      assertTrue(dir.ifChildExists("file"));
      // Node has been moved to new location
      assertTrue(anotherDir.ifChildExists("file"));
      // Old file and new file are different references
      assertNotEquals(dir.getChild("file"), anotherDir.getChild("file"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyNodeInvalidPathToNode() {
    Path pathToNode = new Path("/dir/invalid");
    Path newParent = new Path("/anotherDir");
    try {
      fs.copyNode(pathToNode, newParent);
      fail();
    } catch (InvalidPathException e) {

    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyNodeInvalidPathToParent() {
    Path pathToNode = new Path("/dir/childDir");
    Path newParent = new Path("/invalid/parent/dir");
    try {
      fs.copyNode(pathToNode, newParent);
      fail();
    } catch (InvalidPathException e) {

    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testTryingTCopyNodeMovingToSuccessor() {
    Path pathToNode = new Path("/dir");
    Path newParent = new Path("/dir/childDir");
    try {
      fs.copyNode(pathToNode, newParent);
    } catch (SuccessorException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyNodeTryingToMoveDirToFile() {
    Path pathToNode = new Path("/anotherDir");
    Path newParent = new Path("/dir/file");
    try {
      fs.copyNode(pathToNode, newParent);
      fail();
    } catch (DirNotFoundException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyNodeOverwritingTheFile() {
    Path pathToNode = new Path("/dir/file");
    try {
      ((File) fs.getNodeAtPath(pathToNode)).setContents("CONTENTS");
    } catch (Exception e) {
      fail();
    }
    Path pathToFile = new Path("/dir/anotherFile");
    File f = null;
    try {
      f = (File) fs.createNewFile(pathToFile);
    } catch (Exception e) {
      fail();
    }
    try {
      fs.copyNode(pathToNode, pathToFile);
      // Moved node was deleted from old location
      assertTrue(dir.ifChildExists("file"));
      // Checking contents of overwritten file
      assertEquals(f.getContents(), "CONTENTS");
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyNodeOverwritingTheDirectory() {
    Path pathToNode = new Path("/dir/childDir");
    Path newPath = new Path("/anotherDir/");
    try {
      fs.copyNode(pathToNode, newPath);
      // Moved node was deleted from old location
      assertTrue(dir.ifChildExists("childDir"));
      // Checking overwritten directory is present
      assertTrue(anotherDir.ifChildExists("childDir"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCopyNodeRenamingTheFile() {
    Path pathToNode = new Path("/dir/file");
    try {
      ((File) fs.getNodeAtPath(pathToNode)).setContents("CONTENTS");

      Path pathToFile = new Path("/dir/anotherFile");
      File f = (File) fs.copyNode(pathToNode, pathToFile);
      // Moved node appears at new location
      assertTrue(dir.ifChildExists("anotherFile"));
      // Moved node was deleted from old location
      assertTrue(dir.ifChildExists("file"));
      // Checking contents of overwritten file
      assertEquals(f.getContents(), "CONTENTS");
    } catch (Exception e) {
      fail();
    }
  }

  @After
  public void unInit() {
    // Resetting root dir, in case it was changed
    fs.setRootDir(root);
    // Removing all children of root
    Queue<IFileSystemNode> toDelete = new LinkedList<IFileSystemNode>();
    for (IFileSystemNode node : fs.getRootDir().getChildren()) {
      toDelete.add(node);
    }
    for (IFileSystemNode node : toDelete) {
      fs.getRootDir().removeChild(node);
    }
    // Uninitializing global variables
    root = null;
    dir = null;
    childDir = null;
    file = null;
    anotherDir = null;
    anotherChildDir = null;
  }
}
