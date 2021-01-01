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

import java.util.Collection;
import jShell.errors.NodeExistsException;

/**
 * Interface for directory class and it's mock version
 * 
 * @author Sasha (Oleksandr)
 */

public interface IDirectory extends IFileSystemNode {

  public boolean tryAddChildren(IFileSystemNode n) throws NodeExistsException;

  /**
   * Remove a child of the parent directory
   * 
   * @param The FileSystemNode to be removed.
   */
  public void removeChild(IFileSystemNode n);

  /**
   * Returns true or false for if a child exists in a directory object
   * 
   * @param Key to search for hashamp
   * @return boolean value indicating whether child is in directory hashmap true for if key is in
   *         hashamp and false otherwise
   **/

  public boolean ifChildExists(String name);

  /**
   * Returns a child object provided it exists, otherwise returns null
   * 
   * @param String: name of the object you want to return
   * @return FileSystemNode either the child directory or file
   **/
  public IFileSystemNode getChild(String name);

  /**
   * Returns all the children of a particular Directory object. Empty Collection if a directory has no
   * children.
   * 
   * @return Collection of FileSystemNode objects, either the child directories or files
   */

  public Collection<IFileSystemNode> getChildren();

  /**
   * Remove node from the list of children and return reference to it. If child with given name does
   * not exist, return null instead
   * 
   * @param name Name of node being deleted
   * @return reference to the node being deleted; null, if deletion fails
   */
  public IFileSystemNode removeChild(String name);


}
