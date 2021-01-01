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
import jShell.errors.DirNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.SuccessorException;
import jShell.errors.UnknownErrorException;
import jShell.fileSystem.Path;
import jShell.interfaces.IDirectory;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IPath;
import jShell.interfaces.IShell;

/**
 * Ls class handles all operations related to ls command, namely checks if arguments are valid
 * arguments for ls command and outputs the contents of specified location or multiple locations in
 * the file system tree.
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class Cp extends Command {
  @Override
  public String run(String[] args)
      throws InvalidPathException, ArgsNumberException, NodeExistsException,
      UnknownErrorException, SuccessorException, DirNotFoundException {

    if (args.length != 3) {
      // Invalid number of arguments
      throw new ArgsNumberException();
    }

    // Assuming arguments are valid, copy the node
    IShell.getShell().getFileSystem().copyNode(new Path(args[1]),
        new Path(args[2]));

    // Produces no output if successful
    return "";
  }

  @Override
  public String toString() {

    return "cp";
  }
}
