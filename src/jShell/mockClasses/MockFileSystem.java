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

package jShell.mockClasses;

import java.util.HashMap;
import jShell.errors.InvalidPathException;
import jShell.interfaces.IDirectory;
import jShell.interfaces.IFile;
import jShell.interfaces.IFileSystem;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;

/**
 * Mock version of the file system class. It provided mock versions of all methods that file system
 * implements. The idea behind this class is the following
 * 
 * - the mock file system contains 5 mock directories and 1 mock file; the structure of mock file
 * system cannot be changed; it's always the following: \ dir childDir childFile anotherDir childDir
 * 
 * - there are public static paths that correspond to all nodes in the mock files system; these are
 * to be used for testing purposes
 * 
 * @author Sasha (Oleksandr)
 */
public class MockFileSystem implements IFileSystem {

  // Strings representing paths to all existing nodes in mock file system
  public static final String fullPathRoot = "/";
  public static final String workingDirFullPath = "/working";
  public static final String workingDirRelativePath = "";
  public static final String validDirFullPath = "/working/childDir";
  public static final String validFileFullPath = "/working/childFile";
  public static final String validDirRelativePath = "childDir";
  public static final String validFileRelativePath = "childFile";
  public static final String validAnotherDirFullPath = "/anotherDir";
  public static final String validAnotherChildDirFullPath =
      "/anotherDir/childDir";

  // References to all existing nodes in mock file system
  public static IDirectory rootDir;
  public static IDirectory workingDir;
  public static IDirectory childDir;
  public static IFile childFile;
  public static IDirectory anotherDir;
  public static IDirectory anotherChildDir;

  // Mapping from path to node that it corresponding to
  private static HashMap<String, IFileSystemNode> paths;

  /**
   * Constructor for mock file system
   */
  public MockFileSystem() {
    // Initializing node objects
    initNodes();
    // Initialing mapping from paths to nodes
    initMapping();
  }

  /**
   * Method to initialize static node objects
   */
  private void initNodes() {
    rootDir = new MockDirectory("");
    workingDir = new MockDirectory("working");
    childDir = new MockDirectory("childDir");
    childFile = new MockFile("childFile");
    anotherDir = new MockDirectory("anotherDir");
    anotherChildDir = new MockDirectory("childDir");
  }

  /**
   * Method to initialize mapping from paths to mock objects
   */
  private void initMapping() {
    paths = new HashMap<String, IFileSystemNode>();
    paths.put(workingDirFullPath, workingDir);
    paths.put(workingDirRelativePath, workingDir);
    paths.put(validDirFullPath, childDir);
    paths.put(validFileFullPath, childFile);
    paths.put(validFileRelativePath, childFile);
    paths.put(validDirRelativePath, childDir);
    paths.put(fullPathRoot, rootDir);
    paths.put(validAnotherDirFullPath, anotherDir);
    paths.put(validAnotherChildDirFullPath, anotherChildDir);
  }

  /**
   * Method to get root directory of the mock file systems
   * 
   * @return reference to root directory
   */
  @Override
  public IDirectory getRootDir() {
    return rootDir;
  }

  /**
   * Method to set root directory of the mock file system
   * 
   * @param newRoot New root directory
   */
  public void setRootDir(IDirectory newRoot) {
    rootDir = newRoot;
  }

  /**
   * Method to get the node at full path
   * 
   * @param path Full path to potential node
   * @return reference to the node at full path; null if not found
   */
  @Override
  public IFileSystemNode getNodeAtFullPath(IPath path) {
    String p = path.toString();
    if (p.length() == 0 || p.charAt(0) != '/') {
      return null;
    }
    if (paths.containsKey(p)) {
      return paths.get(p);
    }
    return null;
  }

  /**
   * Method to get the node at relative path
   * 
   * @param path Relative path to potential node
   * @return reference to the node at relative path; null, if not found
   */
  @Override
  public IFileSystemNode getNodeAtRelativePath(IPath path) {
    String p = path.toString();
    if (p.length() != 0 && p.charAt(0) == '/') {
      return null;
    }
    if (paths.containsKey(p)) {
      return paths.get(p);
    }
    return null;
  }

  /**
   * Method to get the node at path (relative or full)
   * 
   * @param path Path to potential node
   * @return reference to the node at path
   * @throws InvalidPathException if node not found
   */
  @Override
  public IFileSystemNode getNodeAtPath(IPath path) throws InvalidPathException {
    String p = path.toString();
    if (paths.containsKey(p)) {
      return paths.get(p);
    }
    throw new InvalidPathException(p);
  }

  /**
   * Method to create new mock file at given path. Does not actually add file to the mock file system.
   * 
   * @param path Path for new file
   * @return reference to mock file if creation is possible; null otherwise
   */
  @Override
  public IFile createNewFile(IPath path) {
    String p = path.getParentPath().toString();
    if (paths.containsKey(p) && paths.get(p) instanceof IDirectory) {
      return new MockFile("newFile");
    }
    return null;
  }

  /**
   * Method to create new mock directory at given path. Does not actually add directory to the mock
   * file system.
   * 
   * @param path Path for new directory
   * @return reference to mock directory if creation is possible; null otherwise
   */
  @Override
  public IDirectory createNewDirectory(IPath path) {
    String p = path.getParentPath().toString();
    if (paths.containsKey(p) && paths.get(p) instanceof IDirectory) {
      return new MockDirectory("newDir");
    }
    return null;
  }

  /**
   * Method to get the parent of specified node
   * 
   * @param node Specified node
   * @return reference to the parent of the node; null, node is not in mock file system
   */
  @Override
  public IDirectory getParent(IFileSystemNode node) {
    if (node == rootDir) {
      return rootDir;
    }
    if (node == workingDir) {
      return rootDir;
    }
    if (node == childDir || node == childFile) {
      return workingDir;
    }
    if (node == anotherDir) {
      return rootDir;
    }
    if (node == anotherChildDir) {
      return anotherDir;
    }
    return null;
  }

  /**
   * Method to remove a node from mock file system
   * 
   * @param toDelete Path for node being deleted
   * @return reference to the node being deleted
   * @throws InvalidPathException if node at path not found
   */
  @Override
  public IFileSystemNode removeNode(IPath toDelete)
      throws InvalidPathException {
    String p = toDelete.toString();
    if (!paths.containsKey(p)) {
      throw new InvalidPathException();
    }
    if (paths.get(p) == workingDir) {
      IShell.getShell().outputToShellln("Trying to remove working directory");
      return null;
    }

    if (paths.get(p) == childFile) {
      IShell.getShell().outputToShellln("Trying to remove standalone file");
      return null;
    }
    IShell.getShell().outputToShellln("Removed a node at path " + p);
    return paths.get(p);
  }

  /**
   * Method to move a node in mock file system from old location to new location
   * 
   * @param pathToNode Path for node being moved
   * @param newParent New location for node being moved
   * @return reference to the node being moved
   * @throws InvalidPathException if node at path not found
   */
  @Override
  public IFileSystemNode moveNode(IPath pathToNode, IPath newParent)
      throws InvalidPathException {
    String node = pathToNode.toString();
    if (!paths.containsKey(node)) {
      throw new InvalidPathException(pathToNode);
    }
    if (paths.get(pathToNode.toString()) == workingDir) {
      IShell.getShell().outputToShellln("Trying to move working directory");
      return null;
    }
    IShell.getShell().outputToShellln("Sucessfully moved node at "
        + pathToNode.toString() + " to new parent " + newParent.toString());
    return paths.get(pathToNode.toString());
  }

  /**
   * Method to copy a node in mock file system from old location to new location
   * 
   * @param pathToNode Path for node being copied
   * @param newParent New location for node being copied
   * @return reference to the node being copied
   * @throws InvalidPathException if node at path not found
   */
  @Override
  public IFileSystemNode copyNode(IPath pathToNode, IPath newParent)
      throws InvalidPathException {
    String node = pathToNode.toString();
    String parent = newParent.toString();
    if (!paths.containsKey(node)) {
      throw new InvalidPathException(pathToNode);
    }
    IShell.getShell().outputToShellln(
        "Copying a node at path " + node + ". To location " + parent);
    return paths.get(node);
  }

  /**
   * Method to get string representation of mock file system. Always returns (because the structure of
   * mock file system is immulable): / dir childDir childFile anotherDir childDir
   * 
   * @return string representation of mock file system.
   */
  @Override
  public String toString() {
    String toString = "/\n" + "\tworking\n" + "\t\tchildDir\n"
        + "\t\tchildFile\n" + "\tanotherDir\n" + "\t\tchildDir\n";
    return toString;
  }
}
