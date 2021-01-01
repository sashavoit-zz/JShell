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

import jShell.errors.BadFileNameException;
import jShell.errors.DirNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.SuccessorException;
import jShell.errors.UnknownErrorException;
import jShell.errors.WorkingDirException;

/**
 * Interface for file system class and it's mock version
 * 
 * @author Sasha (Oleksandr)
 */

public interface IFileSystem {

  /**
   * Get reference to root directory
   * 
   * @return Reference to root directory
   */
  public IDirectory getRootDir();

  public void setRootDir(IDirectory newRoot);

  /**
   * Assuming pass is a valid full path ending with a directory or file, get the reference to the
   * bottom directory or file
   * 
   * @param path Full path
   * @return The reference to the node at full path
   * @throws InvalidPathException
   */
  public IFileSystemNode getNodeAtFullPath(IPath path)
      throws InvalidPathException;

  /**
   * Get the reference to the bottom directory or file, assuming path is a valid relative path ending
   * with a directory or file,
   * 
   * @param path Relative path
   * @return The reference to the node at full path
   * @throws InvalidPathException
   */
  public IFileSystemNode getNodeAtRelativePath(IPath path)
      throws InvalidPathException;

  /**
   * Get the reference to the bottom directory or file, assuming path is valid relative or full path
   * 
   * @param path
   * @return The reference to the node at the bottom of path
   * @throws InvalidPathException
   */
  public IFileSystemNode getNodeAtPath(IPath path) throws InvalidPathException;

  /**
   * Creates and returns a file given by the path (either relative or full path). If parent path does
   * not exist or name is contains invalid characters, return null.
   * 
   * @param path Path pointing at the file to be created
   * @return Reference to the newly created file
   * @throws InvalidPathException
   * @throws NodeExistsException
   * @throws BadFileNameException
   */
  public IFile createNewFile(IPath path)
      throws InvalidPathException, NodeExistsException, BadFileNameException;

  /**
   * Creates and returns a directory given by the path (either relative or full path). If parent path
   * does not exist or name is contains invalid characters, return null.
   * 
   * @param path Path pointing to the directory to be created.
   * @return reference to newly created directory
   * @throws InvalidPathException
   * @throws NodeExistsException
   */
  public IDirectory createNewDirectory(IPath path)
      throws InvalidPathException, NodeExistsException, BadFileNameException;

  /**
   * This method returns a parent of specified node, assuming that node is part of current file tree.
   * If node is root directory, return root directory.
   * 
   * @param dir Directory in file system
   * @return Parent of dir
   */
  public IDirectory getParent(IFileSystemNode node);

  @Override
  /**
   * Method that returns contents of the file system tree represented as a string.
   * 
   * @return string representation of file system tree
   */
  public String toString();


  /**
   * Remove the node at specified path from the file tree and return the reference to it.
   * 
   * @param toDelete Path for a node to be deleted
   * @return reference to the node being deleted; null, if operation is unsuccessful.
   * @throws InvalidPathException
   * @throws WorkingDirException
   */
  public IFileSystemNode removeNode(IPath toDelete)
      throws InvalidPathException, WorkingDirException;

  /**
   * Method to move node from old path to new path. If new path is - existing directory: move node to
   * the contents of this directory; if child node with this name already exists, overwrite it -
   * non-existing location: create a location with this name and copy contents of node to it
   * 
   * @param oldPath path to node being moved
   * @param newPath path to new location of node
   * @return reference to the node copied
   * @throws InvalidPathException if oldPath is invalid, or creation of new node is impossible
   * @throws SuccessorException if node is being moved to it's successors
   * @throws UnknownErrorException if unexpected error occurs (should not ever get thrown)
   * @throws DirNotFoundException if directory is being copied to a file
   * @throws WorkingDirException
   */
  public IFileSystemNode moveNode(IPath pathToNode, IPath newParent)
      throws InvalidPathException, SuccessorException, UnknownErrorException,
      NodeExistsException, BadFileNameException, DirNotFoundException,
      WorkingDirException;

  /**
   * Method to copy the node from old location to the children of given directory
   * 
   * @param pathToNode Path to node being copied
   * @param newParent Path to new parent of node being copied
   * @return the reference to node copied
   * @throws InvalidPathException if path to node or to new parent is invalid
   * @throws UnknownErrorException if unexpected error occurs (should not ever get thrown)
   * @throws SuccessorException
   */
  public IFileSystemNode copyNode(IPath pathToNode, IPath newParent)
      throws InvalidPathException, UnknownErrorException, SuccessorException,
      DirNotFoundException;

}
