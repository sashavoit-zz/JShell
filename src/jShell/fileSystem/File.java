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

package jShell.fileSystem;

import java.io.Serializable;
import jShell.interfaces.*;

/**
 * A class representing a file object. It contains a name and contents, which can be modified by
 * various other commands.
 * 
 * @author Kevin Meharchand
 */

public class File extends FileSystemNode implements IFile, Serializable {

  private static final long serialVersionUID = 1L;
  /** A string representing this file's contents. */
  private String contents; // Since the only "files" we are handling are .txt

  /**
   * Constructor for a file that sets the name of the file to "name"
   * 
   * @param name The name of the file
   */
  public File(String name) {

    this.name = name;
  }

  /**
   * Returns false, as file cannot have children
   * 
   * @return false
   */
  public boolean ifChildExists(String name) {

    return false;
  }

  /**
   * Populates the contents of this file with whatever text the user desires
   * 
   * @param contents the text to be added to the file
   */
  public void setContents(String contents) {

    this.contents = contents;
  }

  /**
   * Opens and returns the contents of this file
   * 
   * @return the contents of the file
   */
  public String getContents() {

    return contents;
  }

  /**
   * A method used to create a copy of a given file, with the same name and contents.
   */
  @Override
  public File clone() {
    File temp = new File(this.name);
    temp.setContents(contents);
    return temp;
  }
}
