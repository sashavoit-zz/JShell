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

import jShell.errors.InvalidPathException;
import jShell.interfaces.IDirectory;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;
import java.io.Serializable;
import java.util.Arrays;

/**
 * Path class handles all operations related to full or relative paths, namely checks the validity
 * of the format of the path, checks if path represents a valid location in the file system tree.
 * 
 * @author Sasha (Oleksandr)
 *
 */
public class Path implements IPath, Serializable {

  // Default id for serializing process
  private static final long serialVersionUID = 1L;

  // Textual representation of the path
  String path;

  /**
   * Constructor for path object.
   * 
   * @param path Textual representation of the path
   */
  public Path(String path) {
    this.path = path;
  }

  /**
   * Return textual representation of the path
   * 
   * @return String corresponding to path
   */
  @Override
  public String toString() {
    return path;
  }

  /**
   * Checks if path is equal to arbitrary object p. If p is a path, checks whether they have the same
   * textual representation
   * 
   * @param p Object to be compared to
   * @return True if p is equal to path; false otherwise
   */
  public boolean equals(Object p) {

    // If p is not instance of Path class, return false
    if (!(p instanceof Path)) {
      return false;
    }

    // If p is instance of Path, compare the textual representation
    return this.path.toString().equals(p.toString());
  }

  /**
   * Return the Path object with the same textual representation
   */
  @Override
  public Path clone() {
    return new Path(this.path);
  }

  /**
   * Checks if path is a valid full path, that points to existing location in current file system.
   * 
   * @return True if string is valid full path; false if not valid full path
   */
  public boolean isValidFullPath() {
    // Try to get a node at full path; if null is returned, path must be invalid
    try {
      return (IShell.getShell().getFileSystem()
          .getNodeAtFullPath(this) != null);
    } catch (InvalidPathException e) {
      // Path is invalid, return false
      return false;
    }
  }

  /**
   * Checks if path is a valid relative path, that points to existing location in current file system.
   * 
   * @return True if string is valid relative path; false if not valid relative path
   */
  public boolean isValidRelativePath() {
    // Try to get a node at relative path; if null is returned, path must be
    // invalid
    try {
      return (IShell.getShell().getFileSystem()
          .getNodeAtRelativePath(this) != null);
    } catch (InvalidPathException e) {
      // Path is invalid, return false
      return false;
    }
  }

  /**
   * Checks if path is a valid full or relative path
   * 
   * @return True if string is valid relative or full path; false if is not valid relative or full
   *         path
   */
  public boolean isValidPath() {
    return (this.isValidFullPath() || this.isValidRelativePath());
  }

  /**
   * Checks if path points to a directory. If path is not valid, return false.
   * 
   * @return True if path is valid and points to a directory
   */
  public boolean isPathToDirectory() {
    IFileSystemNode bottom;
    try {
      // Getting reference to the bottom location of the path
      bottom = IShell.getShell().getFileSystem().getNodeAtPath(this);
    } catch (InvalidPathException e) {
      // Path is invalid, return false
      return false;
    }
    return bottom instanceof IDirectory;
  }

  /**
   * Checks if path has valid format, i.e. contains no consecutive slash characters.
   * 
   * @return True, if path does not contain consecutive slash characters; false, otherwise.
   */
  public boolean doubleSlashChecker() {
    // Iterating over the characters in the path
    for (int i = 0; i < this.path.length() - 1; i++) {
      if (this.path.charAt(i) == '/' && this.path.charAt(i + 1) == '/') {
        // If at least two consecutive slashes are found, return false
        return false;
      }
    }
    return true;
  }

  /**
   * This method splits the path into the sequence of directories/files
   * 
   * @return The array of names of nodes from the path
   */
  public String[] getSequence() {
    String[] sequence = {};
    // If path is not empty, split it. If path is empty, return empty sequence
    if (!this.toString().isEmpty()) {
      sequence = this.toString().split("/", -1);
    }
    // If first or/and last location are equal to empty string, omit them
    if (sequence.length != 0 && sequence[0].isEmpty()) {
      // First location is empty; remove first element
      sequence = Arrays.copyOfRange(sequence, 1, sequence.length);
    }
    if (sequence.length != 0 && sequence[sequence.length - 1].isEmpty()) {
      // Last location is empty; remove last element
      sequence = Arrays.copyOfRange(sequence, 0, sequence.length - 1);
    }
    return sequence;
  }

  /**
   * This method returns a full path of regular form (i.e. not containing . and ..) to the parent
   * directory of the node specified by path. If path to root is given, return path to root.
   * 
   * @return Full path to parent directory
   */
  public IPath getParentPath() {
    IPath regularForm = (Path) Path.getRegularForm(this);
    ((Path) regularForm).removeBottomLocation();
    return regularForm;
  }

  /**
   * Return the name of bottom file or directory of the path, if bottom file or directory exists.
   * Otherwise, return empty string.
   * 
   * @return Name of bottom file or directory
   */
  public String getBottomName() {
    // Removing last slash in path
    IPath pathWithoutLastSlash =
        Path.getRegularForm((Path) Path.removeLastSlash(this));

    // Getting index, where bottom node begins
    int begin = pathWithoutLastSlash.toString().lastIndexOf('/');

    return pathWithoutLastSlash.toString().substring(begin + 1);
  }

  /**
   * Return the result of appending name of the node to the path
   * 
   * @param path Path
   * @param nodeName Name of the node
   * @return Result of appending nodeName to path
   */
  public IPath appendNodeName(String nodeName) {
    Path pathToBeAppended = new Path(nodeName);
    return Path.concatPaths(this, pathToBeAppended);
  }

  /**
   * Converts relative path to full path, assuming input is valid relative path.
   * 
   * @param relative Relative path
   * @return Full path pointing to the same location
   */
  public IPath convertRelativeToFull() {
    Path workingDirectory = (Path) IShell.getShell().getUserData().getCurPath();

    // Append relative path to the path or current working directory to make
    // it a full path
    return Path.concatPaths(workingDirectory, this);
  }

  /**
   * If path has a leading slash character, remove it. Otherwise, return the copy of the path.
   * 
   * @param path Path
   * @return Path without leading slash
   */
  private static IPath removeLeadingSlash(IPath path) {
    // Checking first character of path, if it's not empty
    if (!path.toString().isEmpty() && path.toString().charAt(0) == '/') {
      // Return path without first slash character
      return new Path(path.toString().substring(1));
    } else {
      // Return the copy of the path
      return ((Path) path).clone();
    }
  }

  /**
   * If path has a slash character at the end, remove it. Otherwise, return the copy of the path.
   * 
   * @param path Path
   * @return Path without last slash
   */
  private static IPath removeLastSlash(IPath path) {
    String str = path.toString();
    // Checking the last character of path, if it's not empty
    if (!str.toString().isEmpty() && str.charAt(str.length() - 1) == '/') {
      // Return path without the last slash character
      return new Path(str.substring(0, str.length() - 1));
    } else {
      // Return the copy of the path
      return ((Path) path).clone();
    }
  }

  /**
   * Returns the result of concatenation of two paths (in the order they are given), assuming both
   * have valid format.
   * 
   * @param p1 First path
   * @param p2 First path
   * @return Path, which is a result of concatenation of p1 and p2
   */
  private static Path concatPaths(Path p1, Path p2) {
    // Special case if first path is empty - return clone of second path
    if (p1.toString().isEmpty()) {
      return p2.clone();
    }

    // Special case if second path is empty - return first path without
    // slash at the end, unless it is the path to the root
    if (p2.toString().isEmpty()) {
      if (p1.equals(new Path("/"))) {
        return p1.clone();
      } else {
        return (Path) Path.removeLastSlash(p1);
      }
    }

    /*
     * To ensure that concatenation does not generate extra slash symbols on the edge of concatenation,
     * remove last slash from first path and leading slash from second path
     */
    String str1 = Path.removeLastSlash(p1).toString();
    String str2 = Path.removeLeadingSlash(p2).toString();
    // Insert a slash symbol between strings corresponding to first path and
    // second path, and return resulting path
    return new Path(str1 + "/" + str2);
  }

  /**
   * Method to get rid of occurrences of . and .. inside path
   * 
   * @param path Path
   * @return Path of regular form pointing to the same location
   */
  private static Path getRegularForm(Path path) {
    IPath fullPath;

    // If path is relative, convert it to full
    if (path.toString().isEmpty() || path.toString().charAt(0) != '/') {
      fullPath = path.convertRelativeToFull();
    } else {
      fullPath = ((Path) path).clone();
    }

    String[] sequenceOfLocations = fullPath.getSequence();
    // Variable to accumulate the path of regular form
    Path regularPath = new Path("/");
    for (String location : sequenceOfLocations) {
      // If encounter "." , skip it
      if (!location.equals(".")) {
        if (location.equals("..")) {
          // Going one level up in the file tree
          regularPath.removeBottomLocation();
        } else {
          // Extending resulting path by new file/directory
          regularPath = (Path) regularPath.appendNodeName(location);
        }
      }
    }
    return regularPath;
  }


  /**
   * Method to remove bottom file or directory from the given path, assuming it is a valid path of
   * regular form
   */
  private void removeBottomLocation() {
    // Special case of full path to root
    if (this.equals(new Path("/"))) {
      return;
    }

    // Index of last occurrence of slash character
    int lastSlash = Path.removeLastSlash(this).toString().lastIndexOf('/');

    /*
     * If path does not contain slash characters, it is either empty or relative path containing a
     * single directory. In both cases, the result will be empty path
     */
    if (lastSlash == -1) {
      this.path = "";
      return;
    }

    /*
     * If path contains only one slash character at the beginning, it is either path to root or full
     * path containing a single directory. In both cases, the result will be path to root
     */
    if (lastSlash == 0) {
      this.path = "/";
      return;
    }

    // In all other cases, remove the last file/directory
    this.path = this.path.substring(0, lastSlash);
    return;
  }

  /**
   * Checks if second path points a successor of location the path object (or points to the same
   * node).
   * 
   * @param potentialSuccessor Second path
   * @return true, if first path is a successor of another (or the path itself); false otherwise
   */
  public boolean isSuccessor(IPath potentialSuccessor) {
    String[] seq1 = Path.getRegularForm(this).getSequence();
    String[] seq2 =
        Path.getRegularForm((Path) potentialSuccessor).getSequence();

    if (seq1.length > seq2.length) {
      // parent cannot have longer path than successor
      return false;
    }

    for (int i = 0; i < Math.min(seq1.length, seq2.length); i++) {
      if (!seq1[i].equals(seq2[i])) {
        // Parent path has to be a substring of successor path
        return false;
      }
    }

    return true;
  }
}
