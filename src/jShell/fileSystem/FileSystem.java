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

import java.util.Iterator;
import java.util.LinkedList;
import jShell.errors.BadFileNameException;
import jShell.errors.DirNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RemovingFileException;
import jShell.errors.SuccessorException;
import jShell.errors.UnknownErrorException;
import jShell.errors.WorkingDirException;
import jShell.interfaces.IDirectory;
import jShell.interfaces.IFile;
import jShell.interfaces.IFileSystem;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;
import java.io.Serializable;

// import java.util.*;

/**
 * FileSystem class is used to store file system tree, perform basic traversing operations on it,
 * create new files and directories, remove, move or copy files and directories.
 * 
 * @author Sasha (Oleksandr)
 *
 */
public class FileSystem implements IFileSystem, Serializable {

  // Default id for serializing process
  private static final long serialVersionUID = 1L;

  // Single instance of FileSystem class to be used by all classes
  static FileSystem fs = null;

  // Root directory of the file system
  private IDirectory rootDir;

  /**
   * Constructor for file system object
   * 
   * @param root Root directory for new file system
   */
  private FileSystem(IDirectory rootDir) {
    fs = this;
    this.rootDir = rootDir;
  }

  /**
   * Method for all classes to use to get a reference to the single file system object
   * 
   * @return reference to a single file system object
   */
  public static FileSystem getFileSystem() {
    if (fs == null) {
      // if file system object was not initialized yet, initialize it
      new FileSystem(new Directory(""));
    }
    return fs;
  }

  /**
   * Get reference to root directory
   * 
   * @return Reference to root directory
   */
  public IDirectory getRootDir() {
    return fs.rootDir;
  }

  /**
   * Set root directory to given directory
   */
  public void setRootDir(IDirectory newRoot) {
    this.rootDir = newRoot;
  }

  /**
   * Method to get the reference the node located at full path
   * 
   * @param path Full path
   * @return The reference to the node at full path
   * @throws InvalidPathException if path is invalid full path
   */
  public IFileSystemNode getNodeAtFullPath(IPath path)
      throws InvalidPathException {
    // If path is empty or does not start with a slash, it is not a valid
    // full path
    if (path.toString().isEmpty() || path.toString().charAt(0) != '/') {
      throw new InvalidPathException(path);
    }
    // Sequence of locations to be processed
    String[] sequenceOfLocations = path.getSequence();
    // Iterator variable for nodes being processed
    IFileSystemNode currNode = this.rootDir;
    // Iterating through the sequence of locations
    for (String nodeName : sequenceOfLocations) {
      if (nodeName.equals("..")) {
        // Go up the file system tree
        currNode = this.getParent(currNode);
      } else if (!nodeName.equals(".")) {
        if (currNode.ifChildExists(nodeName)) {
          // If node has a child with given name, it must be a directory
          currNode = ((Directory) currNode).getChild(nodeName);
        } else {
          throw new InvalidPathException(path);
        }
      }
    }
    return currNode;
  }

  /**
   * Method to get the reference the node located at relative path
   * 
   * @param path Relative path
   * @return The reference to the node at relative path
   * @throws InvalidPathException if path is invalid relative path
   */
  public IFileSystemNode getNodeAtRelativePath(IPath path)
      throws InvalidPathException {
    // Empty path is considered to be a valid relative path pointing to the
    // current directory
    if (path.toString().isEmpty()) {
      return IShell.getShell().getUserData().getCurDir();
    }
    // If path starts with a slash, it must be a full path, not a relative path
    if (path.toString().charAt(0) == '/') {
      return null;
    }
    // Get full path from relative path pointing to the same location in the
    // file tree
    IPath fullPath = path.convertRelativeToFull();
    return this.getNodeAtFullPath(fullPath);
  }

  /**
   * Method to get the reference the node located at path (either full or relative)
   * 
   * @param path Full or relative
   * @return The reference to the node at path
   * @throws InvalidPathException if path is invalid path
   */
  public IFileSystemNode getNodeAtPath(IPath path) throws InvalidPathException {
    if (path.isValidFullPath()) {
      return this.getNodeAtFullPath(path);
    }
    if (path.isValidRelativePath()) {
      return this.getNodeAtRelativePath(path);
    }
    throw new InvalidPathException(path);
  }

  /**
   * Method to add the specified node to the contents of bottom directory
   * 
   * @param node File or Directory object to be added
   * @param path Location of the directory, which contents are being modified
   * @throws InvalidPathException if path is invalid
   * @throws NodeExistsException if child with given name already exists
   */
  private void addToExistingLocationAtPath(IFileSystemNode node, IPath path)
      throws InvalidPathException, NodeExistsException {
    // Getting reference to the directory begin updated with the node
    IDirectory d = (IDirectory) this.getNodeAtPath(path);
    d.tryAddChildren(node);
  }

  /**
   * Creates and returns a file given by the path (either relative or full path).
   * 
   * @param path Path pointing to the file to be created
   * @return Reference to the newly created file
   * @throws InvalidPathException if path for new file is invalid
   * @throws NodeExistsException if node with given name already exists
   * @throws BadFileNameException if name for new file is invalid
   */
  public IFile createNewFile(IPath path)
      throws InvalidPathException, NodeExistsException, BadFileNameException {
    // Location for the file to be inserted
    IPath parentPath = (IPath) path.getParentPath();
    String fileName = path.getBottomName();

    // If name is invalid, don't create new file
    if (!File.nameChecker(fileName)) {
      throw new BadFileNameException(fileName);
    }

    // Creating new file and adding it to file system tree
    IFile newFile = new File(fileName);
    if (parentPath.isValidPath()) {
      this.addToExistingLocationAtPath(newFile, parentPath);
      return newFile;
    }
    throw new InvalidPathException(path);
  }

  /**
   * Creates and returns a directory given by the path (either relative or full path).
   * 
   * @param path Path pointing to the directory to be created
   * @return Reference to the newly created directory
   * @throws InvalidPathException if path for new directory is invalid
   * @throws NodeExistsException if node with given name already exists
   * @throws BadFileNameException if name for new directory is invalid
   */
  public IDirectory createNewDirectory(IPath path)
      throws InvalidPathException, NodeExistsException, BadFileNameException {
    // Location for the Directory to be inserted
    IPath parentPath = path.getParentPath();
    String dirName = path.getBottomName();
    // If name is invalid, don't create new directory
    if (!Directory.nameChecker(dirName)) {
      throw new BadFileNameException();
    }

    // Creating new directory and adding it to file system tree
    IDirectory newDir = new Directory(dirName);
    if (parentPath.isValidPath()) {
      this.addToExistingLocationAtPath(newDir, parentPath);
      return newDir;
    }

    throw new InvalidPathException(path);
  }

  /**
   * This method returns a parent of specified node, assuming that node is part. of current file tree.
   * If node is root directory, return root directory.
   * 
   * @return Parent of dir
   */
  public IDirectory getParent(IFileSystemNode node) {
    if (node == this.rootDir) {
      return (IDirectory) node;
    }
    LinkedList<IDirectory> queue = new LinkedList<IDirectory>();
    queue.add(rootDir);
    Iterator<IDirectory> iterator = queue.iterator();
    while (iterator.hasNext()) {
      IDirectory dir = queue.poll();
      if (dir.getChildren().contains(node)) {
        return dir;
      }
      for (IFileSystemNode n : dir.getChildren()) {
        if (n instanceof IDirectory) {
          queue.add((IDirectory) n);
        }
      }
    }
    return null;
  }

  /**
   * Method that returns contents of the file system tree represented as a string.
   * 
   * @return string representation of file system tree
   */
  @Override
  public String toString() {
    // The string that precedes every new level of the file tree
    String indentation = "\t";
    // The string that precedes first level of the file tree (root directory)
    String initialPrefix = "";
    return this.toStringRecursive(rootDir, indentation, initialPrefix);
  }

  /**
   * Helper method to list all contents of subtree with root at starting node
   * 
   * @param startingNode Root of subtree
   * @param indentation The string that precedes every new level of the file tree
   * @param initialPrefix The string that precedes first level of the subtree
   * @return string representation of subtree
   */
  private String toStringRecursive(IFileSystemNode startingNode,
      String indentation, String initialPrefix) {
    String result = "";
    String prefix = initialPrefix;
    IFileSystemNode node = startingNode;
    if (node == rootDir) {
      // Root is represented as /
      result += prefix + "/" + "\n";
    } else {
      // Add name of node to the string representation of file system tree
      result += prefix + node.getName() + "\n";
    }

    if (node instanceof IDirectory) {
      for (IFileSystemNode child : ((IDirectory) node).getChildren()) {
        // Add the contents of subtrees with root at child
        result +=
            this.toStringRecursive(child, indentation, prefix + indentation);
      }
    }
    return result;

  }

  /**
   * Remove the node at specified path from the file tree and return the reference to it.
   * 
   * @param toDelete Path for a node to be deleted
   * @return reference to the node being deleted
   * @throws InvalidPathException if path is invalid
   * @throws WorkingDirException
   */
  public IFileSystemNode removeNode(IPath toDelete)
      throws InvalidPathException, WorkingDirException {
    // Getting reference to the node.
    IFileSystemNode node = this.getNodeAtPath(toDelete);
    if (node == null) {
      throw new InvalidPathException();
    }
    String currPath = IShell.getShell().getUserData().getCurPath().toString();
    // get the Abs path of the node to be Delete
    String delStr = node.getPath().toString();

    /*
     * compare the path of node to be deleted and currPath and give an error if user tries to delete the
     * current working directory
     */
    if (currPath.startsWith(delStr)) {
      throw new WorkingDirException();
    }
    // Getting reference to the parent directory
    IDirectory parent = this.getParent(node);
    // Removing node from children of parent directory
    parent.removeChild(node.getName());
    return node;
  }

  /**
   * Method to move the node from old location to the children of given directory
   * 
   * @param pathToNode Path to node to be moved
   * @param newParent Path to new parent of node being moved
   * @return reference to the node, if operation succeeds
   * @throws InvalidPathException if path to node or to new parent is invalid
   * @throws SuccessorException if node is being moved to its successor
   * @throws UnknownErrorException if unexpected error occurs (should not ever get thrown)
   * @throws WorkingDirException
   */
  private IFileSystemNode moveNodeToNewParent(IPath pathToNode, IPath newParent)
      throws InvalidPathException, SuccessorException, UnknownErrorException,
      WorkingDirException {
    // Checking if paths are valid and newParent points to directory
    if (!pathToNode.isValidPath()) {
      throw new InvalidPathException(pathToNode.toString());
    }
    if (!newParent.isValidPath()) {
      throw new InvalidPathException(newParent.toString());
    }
    // If new parent is a successor of the node, return null
    if (pathToNode.isSuccessor(newParent)) {
      throw new SuccessorException(pathToNode.toString(), newParent.toString());
    }
    // Getting references to the node and new parent
    IFileSystemNode node = this.getNodeAtPath(pathToNode);
    IFileSystemNode parent = this.getNodeAtPath(newParent);
    if (!(parent instanceof IDirectory)) {
      // New parent must be a directory
      throw new InvalidPathException(newParent.toString());
    }

    // To move the node, first we remove it from file system tree, and
    // then add it to new location
    node = this.removeNode(pathToNode);

    // If parent already has a child with given name, overwrite the child
    if (((IDirectory) parent).ifChildExists(node.getName())) {
      ((IDirectory) parent).removeChild(node.getName());
    }

    try {
      ((IDirectory) parent).tryAddChildren(node);
    } catch (Exception e) {
      throw new UnknownErrorException();
    }
    return node;
  }

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
  public IFileSystemNode moveNode(IPath oldPath, IPath newPath)
      throws InvalidPathException, SuccessorException, UnknownErrorException,
      DirNotFoundException, WorkingDirException {
    if (!oldPath.isValidPath()) {
      throw new InvalidPathException(oldPath);
    }

    // Case when newPath is valid and points to directory
    if (newPath.isValidPath() && newPath.isPathToDirectory()) {
      return this.moveNodeToNewParent(oldPath, newPath);
    }

    // Case when newPath is valid, but points to file
    if (newPath.isValidPath()) {
      if (oldPath.isPathToDirectory()) {
        // oldPath must be a file!
        throw new DirNotFoundException(newPath.toString());
      }
      File f1 = (File) this.getNodeAtPath(oldPath);
      File f2 = (File) this.getNodeAtPath(newPath);
      f2.setContents(f1.getContents());
      this.removeNode(oldPath);
      return f2;
    }

    // Case when newPath is invalid
    IFileSystemNode nodeToMove = this.removeNode(oldPath);
    nodeToMove.setName(newPath.getBottomName());
    try {
      this.addToExistingLocationAtPath(nodeToMove, newPath.getParentPath());
    } catch (NodeExistsException e) {
      throw new UnknownErrorException();
    }

    return nodeToMove;
  }

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
  private IFileSystemNode copyNodeToNewParent(IPath pathToNode, IPath newParent)
      throws InvalidPathException, UnknownErrorException, SuccessorException {
    // Checking if paths are valid and newParent points to directory
    if (!pathToNode.isValidPath()) {
      throw new InvalidPathException(pathToNode.toString());
    }
    if (!newParent.isValidPath() || !newParent.isPathToDirectory()) {
      throw new InvalidPathException(newParent.toString());
    }
    // If new parent is a successor of the node, return null
    if (pathToNode.isSuccessor(newParent)) {
      throw new SuccessorException(pathToNode.toString(), newParent.toString());
    }
    IDirectory parentDir = (IDirectory) this.getNodeAtPath(newParent);
    IFileSystemNode node = this.getNodeAtPath(pathToNode);
    // If parent already has a child with given name, don't copy the node
    if (parentDir.ifChildExists(node.getName())) {
      parentDir.removeChild(node.getName());
    }
    // Creating a copy of subtree rooted at node
    IFileSystemNode copyOfNode = this.getCopyOfSubtree(node);
    try {
      ((IDirectory) parentDir).tryAddChildren(copyOfNode);
    } catch (NodeExistsException e) {
      // Child was just removed, tryAddChildren should be successful
      throw new UnknownErrorException();
    }
    return copyOfNode;
  }

  /**
   * Method to copy node from old path to new path. If new path is - existing directory: copy node to
   * the contents of this directory; if child node with this name already exists, overwrite it -
   * non-existing location: create a location with this name and copy contents of node to it
   * 
   * @param oldPath path to node being copied
   * @param newPath path to new location of node
   * @return reference to the node copied
   * @throws InvalidPathException if oldPath is invalid, or creation of new node is impossible
   * @throws SuccessorException if node is being moved to it's successors
   * @throws UnknownErrorException if unexpected error occurs (should not ever get thrown)
   * @throws DirNotFoundException if directory is being copied to a file
   */
  public IFileSystemNode copyNode(IPath oldPath, IPath newPath)
      throws InvalidPathException, SuccessorException, UnknownErrorException,
      DirNotFoundException {
    if (!oldPath.isValidPath()) {
      throw new InvalidPathException(oldPath);
    }

    // Case when newPath is valid and points to directory
    if (newPath.isValidPath() && newPath.isPathToDirectory()) {
      return this.copyNodeToNewParent(oldPath, newPath);
    }

    // Case when newPath is valid, but points to file
    if (newPath.isValidPath()) {
      if (oldPath.isPathToDirectory()) {
        // oldPath must be a file!
        throw new DirNotFoundException(newPath.toString());
      }
      File f1 = (File) this.getNodeAtPath(oldPath);
      File f2 = (File) this.getNodeAtPath(newPath);
      f2.setContents(f1.getContents());
      return f2;
    }

    // Case when newPath is invalid
    IFileSystemNode nodeToCopy =
        this.getCopyOfSubtree(this.getNodeAtPath(oldPath));
    nodeToCopy.setName(newPath.getBottomName());
    try {
      this.addToExistingLocationAtPath(nodeToCopy, newPath.getParentPath());
    } catch (NodeExistsException e) {
      throw new UnknownErrorException();
    }

    return nodeToCopy;
  }

  /**
   * Method to create a copy of subtree with root at given node
   * 
   * @param oldRoot Reference to the root of the subtree being copied
   * @return reference to the root of copied tree
   * @throws UnknownErrorException if unexpected error occurs (should not ever get thrown)
   */
  private IFileSystemNode getCopyOfSubtree(IFileSystemNode oldRoot)
      throws UnknownErrorException {
    if (!(oldRoot instanceof IDirectory)) {
      // oldRoot is a file, then just return it's copy
      IFile copyRoot = new File(oldRoot.getName());
      copyRoot.setContents(((File) oldRoot).getContents());
      return copyRoot;
    }

    // oldRoot is a directory
    IDirectory copyRoot = new Directory(oldRoot.getName());
    for (IFileSystemNode child : ((Directory) oldRoot).getChildren()) {

      // Recursively adding all successors
      IFileSystemNode copyChild = this.getCopyOfSubtree(child);
      try {
        copyRoot.tryAddChildren(copyChild);
      } catch (NodeExistsException e) {
        // Child existed in original subtree, tryAddChildren should be successful
        throw new UnknownErrorException();
      }

    }
    return copyRoot;
  }
}
