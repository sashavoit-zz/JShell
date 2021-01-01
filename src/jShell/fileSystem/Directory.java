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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import jShell.errors.NodeExistsException;
import jShell.interfaces.IDirectory;
import jShell.interfaces.IFileSystem;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IShell;

public class Directory extends FileSystemNode
    implements IDirectory, Serializable {

  private static final long serialVersionUID = 1L;
  /**
   * Directory class that instantiates a Directory object by taking a name or name and path. Stores
   * children in a hash map. Contains methods for adding/getting children objects and checking whether
   * they exist in the Hash map.
   * 
   * @author Sankalp Sharma
   */
  

  /**
   * Constructor for a Directory that sets the name of the Directory to the name specified.
   * 
   * @param name The name of the Directory
   */

  // Hashmap to store the children of the directory
  private Map<String, IFileSystemNode> children =
      new HashMap<String, IFileSystemNode>();

  public Directory(String name) {

    this.name = name;
  }



  /**
   * This method adds children objects to the Directory object. Children can be either directories or
   * files
   * 
   * @param FileSystemNode object to be added
   * 
   * @return boolean value if the child has been added to Hashmap or not
   * @throws NodeExistsException
   **/
  public boolean tryAddChildren(IFileSystemNode n) throws NodeExistsException {
    if (ifChildExists(n.getName())) {
      // directory already exists
      throw new NodeExistsException(n.getName());
    }
    children.put(n.getName(), n);
    return true;
  }

  /**
   * remove a child of the parent directory
   * 
   * @param The FileSystemNode to be removed.
   */
  public void removeChild(IFileSystemNode n) {
    children.remove(n.getName(), n);
    return;
  }

  /**
   * Returns true or false for if a child exists in a directory object
   * 
   * @param Key to search for hashamp
   * @return boolean value indicating whether child is in directory hashmap true for if key is in
   *         hashamp and false otherwise
   **/
  public boolean ifChildExists(String name) {

    return children.containsKey(name);
  }

  /**
   * Returns a child object provided it exists, otherwise returns null
   * 
   * @param String: name of the object you want to return
   * @return FileSystemNode either the child directory or file
   **/
  public IFileSystemNode getChild(String name) {
    if (ifChildExists(name)) {
      return children.get(name);
    } else {
      return null;
    }
  }

  /**
   * Returns all the children of a particular Directory object. Empty Collection if a directory has no
   * children.
   * 
   * @return Collection of FileSystemNode objects, either the child directories or files
   */
  public Collection<IFileSystemNode> getChildren() {

    return children.values();
  }

  /**
   * Return a clone of a Directory and its contents
   * 
   * @param
   * @return Directory object clone of which Directory you want to clone
   * 
   * @throws NodeExistsException
   * 
   */

  @Override
  public Directory clone() {
    Directory temp = new Directory(this.getName());
    this.children.values().forEach(x -> {
      try {
        temp.tryAddChildren(x.clone());
      } catch (NodeExistsException e) {
        e.printStackTrace();
      }
    });
    return temp;
  }

  /**
   * Remove node from the list of children and return reference to it. If child with given name does
   * not exist, return null instead
   * 
   * @param name Name of node being deleted
   * @return reference to the node being deleted; null, if deletion fails
   */
  public IFileSystemNode removeChild(String name) {
    // check if directory exists and then remove it
    if (ifChildExists(name)) {
      IFileSystemNode child = children.get(name);
      children.remove(name);
      return child;
    } else {
      // doesn't exist
      return null;
    }
  }

}

