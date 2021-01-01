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

package jShell.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import jShell.Shell;
import jShell.errors.BadFileNameException;
import jShell.errors.BadStringException;
import jShell.errors.DirNotFoundException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;
import jShell.fileSystem.Path;
import jShell.interfaces.IDirectory;
import jShell.interfaces.IFile;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;

/**
 * The find command searches the directory(ies) provided at PATH (and PATH2, PATH3, etc.) for
 * objects of type TYPE with the name NAME.
 * 
 * @author Raz Ben Haim
 *
 */

public class Find extends Command {

  public Find() {
    super();
  }

  @Override
  public String run(String[] args) throws InvalidPathException,
      DirNotFoundException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException, BadStringException {

    // Variable to hold found nodes
    ArrayList<IFileSystemNode> foundNodes = new ArrayList<IFileSystemNode>();

    Queue<IDirectory> searchIn = new LinkedList<IDirectory>();
    ArrayList<IDirectory> searchInList = new ArrayList<IDirectory>();

    // Path to the search location
    IPath path = new Path(args[1]);

    // Parsing arguments
    if (args[1].equals("-type")) {
      // Type is specified
      searchInList.add((IDirectory) IShell.getShell().getFileSystem()
          .getNodeAtPath(IShell.getShell().getUserData().getCurPath()));

    } else if (path.isValidPath()) {
      // Path is valid; search the location
      for (int i = 1; path.isValidPath(); i++) {
        path = new Path(args[i]);
        if (!path.isValidPath()) {
          break;
        }
        try {
          searchInList.add((IDirectory) IShell.getShell().getFileSystem()
              .getNodeAtPath(path));
        } catch (ClassCastException e) {
          throw new DirNotFoundException(path);
        }
      }
    } else {
      throw new InvalidPathException(path);
    }

    // Add directories to the queue
    for (IDirectory t : searchInList) {
      searchIn = this.addAllDirsToQueue(searchIn, t);
    }
    ArrayList<String> list = new ArrayList<String>(Arrays.asList(args));

    // Go to the name option
    int startAt = list.indexOf("-name") + 1;
    String name = "";
    try {
      for (int i = startAt; true; i++) {
        if (i == startAt) {
          name += args[i];
        } else {
          name += " " + args[i];
        }
        if (args[i].endsWith("\"")) {
          break;
        }
      }
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new BadStringException();
    }

    // Remove double quotes from the name
    name = name.replaceAll("\"", "");

    // Iterate over directories
    Iterator<IDirectory> iterator = searchIn.iterator();
    while (iterator.hasNext()) {
      IFileSystemNode node = iterator.next().getChild(name);
      if (node == null) {
        continue;
      }
      try {
        switch (list.get(list.indexOf("-type") + 1)) {
          case ("d"):
            if (!(node instanceof IDirectory)) {
              continue;
            }
            foundNodes.add(node);
            break;
          case ("f"):
            if (!(node instanceof IFile)) {
              continue;
            }
            foundNodes.add(node);
            break;
          default:
            return "";
        }
      } catch (IndexOutOfBoundsException e) {
        return "";
      }
    }

    if (foundNodes.isEmpty()) {
      // No nodes are found
      return "";
    }

    // Add found nodes to the output
    StringBuilder message = new StringBuilder();
    int i = 1;
    for (IFileSystemNode node : foundNodes) {
      message.append(
          node.getPath().toString() + ((foundNodes.size() != i) ? "\n" : ""));
      i++;
    }

    // Check if redirection is happening
    if (list.contains(">")) {
      IShell.getShell().getRedirection().fileHandling(">",
          args[list.indexOf(">") + 1], message.toString());

      // Output is redirected
      return "";
    } else if (list.contains(">>")) {
      Shell.getShell().getRedirection().fileHandling(">>",
          args[list.indexOf(">>") + 1], message.toString());

      // Output is redirected
      return "";
    }

    // Output is printed to the shell
    return message.toString();
  }

  private Queue<IDirectory> addAllDirsToQueue(Queue<IDirectory> queue,
      IDirectory dir) {
    queue.add(dir);
    for (IFileSystemNode node : dir.getChildren()) {
      if (node instanceof IDirectory) {
        addAllDirsToQueue(queue, (IDirectory) node);
      }
    }
    return queue;
  }

  @Override
  public String toString() {
    return "find";
  }
}
