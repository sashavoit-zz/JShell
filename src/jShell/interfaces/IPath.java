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

package jShell.interfaces;

/**
 * Interface for path class and it's mock version
 * 
 * @author Sasha (Oleksandr)
 */

public interface IPath {

  /**
   * Return textual representation of the path
   * 
   * @return String corresponding to path
   */
  public String toString();

  /**
   * Checks if path is equal to arbitrary object p. If p is a path, checks whether they have the same
   * textual representation
   * 
   * @param p Object to be compared to
   * @return True if p is equal to path; false otherwise
   */
  public boolean equals(Object p);

  /**
   * Checks if path is a valid full path, that points to existing location in current file system.
   * 
   * @return True if string is valid full path; false is not full path
   */
  public boolean isValidFullPath();

  /**
   * Checks if path is a valid relative path, that points to existing location in current file system.
   * 
   * @return True if string is valid relative path; false is not relative path
   */
  public boolean isValidRelativePath();

  /**
   * Checks if path is a valid full or relative path
   * 
   * @return True if string is valid relative or full path; false if is not relative or full path
   */
  public boolean isValidPath();

  /**
   * Checks if path points to a directory. If path is not valid, return false.
   * 
   * @return True if path is valid and ends with a directory
   */
  public boolean isPathToDirectory();

  /**
   * Checks if path has valid format, i.e. contains no consecutive slash characters.
   * 
   * @return True, if path does not contain consecutive slash characters; false, otherwise.
   */
  public boolean doubleSlashChecker();

  /**
   * This method splits the path into the sequence of directories/files
   * 
   * @return The array of names of the directories/files in the path
   */
  public String[] getSequence();

  /**
   * This method returns a full path of regular form (i.e. not containing . and ..) to the parent
   * directory of the node specified by path. If path to root is given, return path to root.
   * 
   * @return Full path to parent directory
   */
  public IPath getParentPath();

  /**
   * Return the name of bottom file or directory of the path, if bottom file or directory exists.
   * Otherwise, return empty string.
   * 
   * @return Name of bottom file or directory
   */
  public String getBottomName();

  /**
   * Return the result of appending name of the node to the path
   * 
   * @param path Path
   * @param nodeName Name of the node
   * @return Result of appending nodeName to path
   */
  public IPath appendNodeName(String nodeName);

  /**
   * Converts relative path to full path, assuming input is valid relative path.
   * 
   * @param relative Relative path
   * @return Full path pointing to the same location
   */
  public IPath convertRelativeToFull();

  /**
   * Checks if second path points a successor of location at first path (or points to the same node)
   * 
   * @param p First path
   * @param potentialSuccessor Second path
   * @return true, if first path is a successor of another (or the path itself); false otherwise
   */
  public boolean isSuccessor(IPath potentialSuccessor);
}
