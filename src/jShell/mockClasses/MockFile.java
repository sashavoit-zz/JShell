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

import jShell.fileSystem.Path;
import jShell.interfaces.*;

public class MockFile implements IFile {

  // Name of the mock directory
  String name;

  /**
   * Constructor for mock file object when name is not specified
   */
  public MockFile() {
    this.name = "someFile";
  }

  /**
   * Constructor for mock file object when name is specified
   * 
   * @param name
   */
  public MockFile(String name) {
    this.name = name;
  }

  /**
   * Method to get the name of mock file
   * 
   * @return the name of the mock file
   */
  @Override
  public String getName() {
    return name;
  }

  /**
   * Method to set the name of the mock file
   * 
   * @param name New name for mock file
   */
  @Override
  public void setName(String name) {
    this.name = name;
  }

  /**
   * Method to get a path to the mock file. There's only file in mock file system - childFile. If file
   * is childFile, return paths to it in MockFileSystem. Otherwise, return arbitrary path
   * 
   * @return path to the mock file object
   */
  @Override
  public IPath getPath() {
    if (this == MockFileSystem.childFile) {
      return new Path(MockFileSystem.validFileFullPath);
    }

    return new Path("this/is/some/path/to/file");
  }

  /**
   * Get the mock file object with the same name
   * 
   * @return reference to the clone mock file
   */
  @Override
  public IFileSystemNode clone() {
    return new MockFile(this.name);
  }

  /**
   * Method to check if child with given name exists
   * 
   * @return false, because files can't have children
   */
  @Override
  public boolean ifChildExists(String name) {
    return false;
  }

  /**
   * Method to mock setting changing contents of file. Does not actually change the contents, but
   * instead outputs appropriate message to the Shell, which signalizes that this method is being
   * called.
   * 
   * @param contents New contents for mock file
   */
  @Override
  public void setContents(String contents) {
    IShell.getShell().outputToShellln(
        "Setting contents of file " + this.name + " to " + contents);
  }

  /**
   * Method to get the contents of the mock file. Contents of the mock file is always CONTENTS
   * 
   * @return string representing contents of the mock file
   */
  @Override
  public String getContents() {
    return "CONTENTS";
  }
}
