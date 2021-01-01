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

import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.DirNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.MoveDirException;
import jShell.errors.NodeExistsException;
import jShell.errors.SuccessorException;
import jShell.errors.UnknownErrorException;
import jShell.errors.WorkingDirException;
import jShell.fileSystem.Path;
import jShell.interfaces.IDirectory;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;

/**
 * The mv command moves the item at path OLDPATH (including its contents if it is a directory) to
 * NEWPATH. OLDPATH is then removed from the file system.
 * 
 * @author Raz Ben Haim
 *
 */

public class Mv extends Command {


  public Mv() {
    super();
  }

  @Override
  public String run(String[] args)
      throws InvalidPathException, ArgsNumberException, NodeExistsException,
      MoveDirException, UnknownErrorException, SuccessorException,
      BadFileNameException, DirNotFoundException, WorkingDirException {

    // Checking if arguments are valid
    if (args.length != 3) {
      throw new ArgsNumberException();
    }

    IPath p = new Path(args[1]);
    p = p.convertRelativeToFull();

    // Assuming arguments are valid, move the node
    IShell.getShell().getFileSystem().moveNode(p, new Path(args[2]));

    // Mv has no output
    return "";
  }

  @Override
  public String toString() {
    return "mv";
  }
}
