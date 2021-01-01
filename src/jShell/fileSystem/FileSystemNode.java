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

public abstract class FileSystemNode implements IFileSystemNode, Serializable {

  /**
   * Abstract class that Directory and File class use as super class. Includes methods such as setting
   * and getting name. Setting and getting path and a valid name checker.
   * 
   * @author Sankalp Sharma
   * 
   **/

  private static final long serialVersionUID = 1L;

  /**
   * The name of the FileSystemNode object
   */

  protected String name;

  public abstract IFileSystemNode clone();

  /**
   * Returns name of FileSystemNode
   * 
   * @return String the name of node
   */
  public String getName() {

    return name;
  }

  /**
   * Abstract method for checking whether a child exists in the FileSystemNode Actually implemented in
   * the Directory subclass
   * 
   */
  public abstract boolean ifChildExists(String name);

  /**
   * Checks a potential file name for validity before allowing any file(s) to be created.
   * 
   * @param name The potential file name to be checked
   * @return a boolean value representing if the name is valid (true) or not (false)
   */
  public static boolean nameChecker(String name) {
    String spaceChecker[] = name.trim().split("\\s+");
    if (spaceChecker.length > 1) {
      return false;
    }
    // Check for invalid symbols in the name
    String invalidChar[] = {"/", ".", "!", "@", "#", "$", "%", "^", "&", "*",
        "(", ")", "{", "}", "~", "|", "<", ">", "?"};
    String symbolChecker[] = name.split("");
    for (String currentChar : symbolChecker) {
      for (int i = 0; i < invalidChar.length; i++) {
        if (currentChar.equals(invalidChar[i])) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Sets the name of this FileSystemNode object
   * 
   * @param filename the name of the FileSystemNode that is to be created
   */
  public void setName(String name) {

    if (!(nameChecker(name))) {
      return;
    }
    this.name = name;
  }

  /**
   * Returns a path for a respective Directory or file
   * 
   * @return Path object representing the path to a Directory or File
   */
  public IPath getPath() {
    String path = this.name;
    for (Directory dir =
        (Directory) IShell.getShell().getFileSystem().getParent(this); dir
            .getName() != IShell.getShell().getFileSystem().getRootDir()
                .getName(); dir =
                    (Directory) IShell.getShell().getFileSystem()
                        .getParent(dir)) {
      path = dir.getName() + "/" + path;
    }
    path = "/" + path;
    return new Path(path);
  }
}
