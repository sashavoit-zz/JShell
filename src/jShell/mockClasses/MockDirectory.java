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

import java.util.ArrayList;
import java.util.Collection;
import jShell.fileSystem.Path;
import jShell.interfaces.*;

/**
 * Mock directory object.
 * 
 * @author Sasha (Oleksandr)
 */

public class MockDirectory implements IDirectory {

  // Name of mock directory
  String name;

  /**
   * Constructor for mock directory object, when name is not provided
   */
  public MockDirectory() {
    this.name = "someDir";
  }

  /**
   * Constructor for mock directory object, when name is provided
   * 
   * @param name Name for mock directory object
   */
  public MockDirectory(String name) {
    this.name = name;
  }

  /**
   * Return the name of mock directory
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Set the name of mock directory
   * 
   * @param name New name for mock directory object
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Get the path to mock directory. In the mock file system, there are only four directories: root,
   * workingDir, childDir, anotherDir and anotherChildDir. If directory is not one of them, return
   * arbitrary path
   * 
   * @return the path to the mock directory in the mock file system
   */
  @Override
  public IPath getPath() {
    if (this == MockFileSystem.workingDir) {
      return new Path(MockFileSystem.workingDirFullPath);
    }
    if (this == MockFileSystem.childDir) {
      return new Path(MockFileSystem.validDirFullPath);
    }
    if (this == MockFileSystem.anotherDir) {
      return new Path(MockFileSystem.validAnotherDirFullPath);
    }
    if (this == MockFileSystem.anotherChildDir) {
      return new Path(MockFileSystem.validAnotherChildDirFullPath);
    }
    if (this == MockFileSystem.rootDir) {
      return new Path(MockFileSystem.fullPathRoot);
    }
    return new Path("this/is/a/path/to/mockDir");
  }

  /**
   * Method to copy mock directory
   * 
   * @return reference to mock directory with the same name
   */
  @Override
  public IFileSystemNode clone() {
    return new MockDirectory(this.name);
  }

  /**
   * Method to mock adding child to directory. Return true if addition is possible, false otherwise.
   * Don't actually add a child.
   * 
   * @param n Reference to the child being added
   * @return true, if addition is possible; false, otherwise
   */
  @Override
  public boolean tryAddChildren(IFileSystemNode n) {
    if (this == MockFileSystem.rootDir
        && n.getName().equals(MockFileSystem.workingDir.getName())) {
      return false;
    }
    if (this == MockFileSystem.workingDir
        && (n.getName().equals(MockFileSystem.childDir.getName())
            || n.getName().equals(MockFileSystem.childFile.getName()))) {
      return false;
    }
    IShell.getShell().outputToShellln("Adding " + n.getName());
    return true;
  }

  /**
   * Method that mocks removing the child of the directory. Outputs a success message to the Shell, if
   * removal is possible. Outputs failure message otherwise. Doesn't actually remove the child.
   * 
   * @param n Reference to the child being deleted
   */
  @Override
  public void removeChild(IFileSystemNode n) {
    if (ifChildExists(n.getName())) {
      IShell.getShell()
          .outputToShellln("Removing the node with name " + n.getName());
    } else {
      IShell.getShell()
          .outputToShellln("Failure removing a child with name" + n.getName());
    }
  }

  /**
   * Method to check if child with given name exists
   * 
   * @param name Name of the potential
   * @return true, if child with given name exists; false, otherwise.
   */
  @Override
  public boolean ifChildExists(String name) {
    if (this == MockFileSystem.rootDir
        && name.equals(MockFileSystem.workingDir.getName())) {
      return true;
    }
    if (this == MockFileSystem.rootDir
        && name.equals(MockFileSystem.anotherDir.getName())) {
      return true;
    }
    if (this == MockFileSystem.workingDir
        && name.equals(MockFileSystem.childDir.getName())) {
      return true;
    }
    if (this == MockFileSystem.workingDir
        && name.equals(MockFileSystem.childFile.getName())) {
      return true;
    }
    if (this == MockFileSystem.anotherDir
        && name.equals(MockFileSystem.anotherChildDir.getName())) {
      return true;
    }
    return false;
  }

  /**
   * Method to get a reference to the child with given name.
   * 
   * @param name Name of the child node
   * @return reference to child if exists; null otherwise
   */
  @Override
  public IFileSystemNode getChild(String name) {
    if (this == MockFileSystem.rootDir
        && name.equals(MockFileSystem.workingDir.getName())) {
      return MockFileSystem.workingDir;
    }
    if (this == MockFileSystem.rootDir
        && name.equals(MockFileSystem.anotherDir.getName())) {
      return MockFileSystem.anotherDir;
    }
    if (this == MockFileSystem.workingDir
        && name.equals(MockFileSystem.childDir.getName())) {
      return MockFileSystem.childDir;
    }
    if (this == MockFileSystem.workingDir
        && name.equals(MockFileSystem.childFile.getName())) {
      return MockFileSystem.childFile;
    }
    if (this == MockFileSystem.anotherDir
        && name.equals(MockFileSystem.anotherChildDir.getName())) {
      return MockFileSystem.anotherChildDir;
    }
    return null;
  }

  /**
   * Get the collection of children of directory.
   * 
   * @return collection of references to children
   */
  @Override
  public Collection<IFileSystemNode> getChildren() {
    Collection<IFileSystemNode> result = new ArrayList<IFileSystemNode>();;
    if (this == MockFileSystem.rootDir) {
      result.add(MockFileSystem.workingDir);
      result.add(MockFileSystem.anotherDir);
    }
    if (this == MockFileSystem.workingDir) {
      result.add(MockFileSystem.childDir);
      result.add(MockFileSystem.childFile);
    }
    if (this == MockFileSystem.anotherDir) {
      result.add(MockFileSystem.anotherChildDir);
    }
    return result;
  }

  /**
   * Method to mock removing the child of the directory. Outputs a success message to the Shell, if
   * removal is possible. Outputs failure message otherwise. Doesn't actually remove the child.
   * 
   * @param name Name of the child being deleted
   * @return reference to the child being removed (null if operation fails)
   */
  @Override
  public IFileSystemNode removeChild(String name) {
    if (this.getChild(name) != null) {
      IShell.getShell().outputToShellln("Removing a node with name " + name
          + "from node with name " + this.name);
    } else {
      IShell.getShell().outputToShellln("Failure removing a node with name "
          + name + "from node with name " + this.name);
    }
    return this.getChild(name);
  }
}
