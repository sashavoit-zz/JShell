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
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;
import jShell.fileSystem.File;
import jShell.fileSystem.Path;
import jShell.interfaces.IFile;
import jShell.interfaces.IFileSystem;
import jShell.interfaces.IFileSystemNode;
import jShell.interfaces.IPath;
import jShell.interfaces.IRedirection;
import jShell.interfaces.IShell;

/**
 * The Cat class holds all of the functions related to the Cat command. It inherits some basic
 * methods from the Command class in order to function properly within the Shell system as a whole.
 * 
 * Cat concatenates the contents of any number of given files together in the shell, separated by
 * three line breaks each. Cat can identify files in local, relative or full paths.
 * 
 * Cat's output can be redirected to a file if specified by the user.
 * 
 * @author Kevin Meharchand
 *
 */
public class Cat extends Command {

  /**
   * The overridden run method inherited from the Command class. Attempts to locate files in the
   * overall shell File System one at a time, returning an appropriate error if the file cannot be
   * found.
   * 
   * @return A string of all of the specified files' contents concatenated into one string, otherwise
   *         nothing if the command's output was redirected.
   * @throws FileNotFoundException
   * @throws ArgsNumberException
   * @throws InvalidPathException
   * @throws BadFileNameException
   * @throws NodeExistsException
   * @throws RedirectionOperatorsException
   */
  @Override
  public String run(String[] args)
      throws FileNotFoundException, ArgsNumberException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException {
    isValid(args);
    IRedirection r = IShell.getShell().getRedirection();
    int argLength;
    boolean goodRedirection =
        r.checkRedirection(args[args.length - 2], args[args.length - 1]);
    if (goodRedirection) {
      argLength = args.length - 2;
    } else {
      argLength = args.length;
    }
    // Create the files array
    IFile[] files = grabFiles(argLength, args);
    // File(s) has been retrieved
    // Determine if we are to output to a file or to the IShell.getShell().
    if (goodRedirection) {
      printContentsToFile(files, IShell.getShell(), args[args.length - 1],
          args[args.length - 2]);
      return "";
    } else {
      return printContents(files, IShell.getShell());
    }
  }


  /*
   * Method used to check the validity of arguments before code execution.
   */
  private void isValid(String[] args) throws ArgsNumberException {

    // No files provided
    if (args.length == 1) {
      throw new ArgsNumberException();
    }
  }

  /**
   * A helper function used to grab and store all of the potential files for concatenation into an
   * array.
   * 
   * @param argLength How many files there are to compile
   * @param args The file names themselves
   * @return An array of Files
   * @throws FileNotFoundException
   */
  private IFile[] grabFiles(int argLength, String[] args)
      throws FileNotFoundException {
    IFile[] files = new IFile[args.length - 1]; // Array to hold all of the files
    IFileSystemNode temp; // Holds the current file to be added to the file array
    IPath p;
    for (int i = 0; i < argLength; i++) {
      if (i > 0) { // Skip the first argument (the command call)
        /*
         * User can pass in either a full path, a relative path, or a file name which assumes they're using
         * their local directory
         */
        p = new Path(args[i]);
        try {
          temp = (IFileSystemNode) IShell.getShell().getFileSystem()
              .getNodeAtPath(p);
        } catch (InvalidPathException ex) {
          temp = null;
        }
        if (!(temp == null)) {
          // If file exists, retrieve it
          try {
            files[i - 1] = (IFile) temp; // Store it in the files array
          } catch (ClassCastException dir) {
            throw new FileNotFoundException(args[i]);
          }
        } else { // Display an appropriate error message and terminate the command
          throw new FileNotFoundException(args[i]);
        }
      }
    }
    return files;
  }

  /**
   * Prints out the contents of each file provided in the files array, with three line breaks between
   * each.
   * 
   * @param files : the array of files to be printed to the shell
   * @param s : The shell that the contents of the file will be printed to.
   * @return The contents of the file(s)
   */
  private String printContents(IFile[] files, IShell s) {
    // Loop through the entire array
    String contents = "";
    for (IFile current : files) {
      if (current != null) {
        // Get the contents of the file and print to the shell
        contents = contents + current.getContents() + "\n\n\n\n";
      }
    }
    return contents;
  }

  /**
   * A method used to print the contents of the file(s) provided by the user into a given file
   * provided by the user.
   * 
   * @param files The files to take the contents from
   * @param s The current instance of the shell
   * @param fileName The filename to print the contents of the other files into
   * @param operation The redirection operator, either appending or overwriting.
   * @throws InvalidPathException
   * @throws NodeExistsException
   * @throws BadFileNameException
   * @throws RedirectionOperatorsException
   * @throws FileNotFoundException
   */
  private void printContentsToFile(IFile[] files, IShell s, String fileName,
      String operation)
      throws InvalidPathException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {
    IRedirection r = IShell.getShell().getRedirection();
    String contents = "";
    /*
     * Before handling the concatenated outputs, if you are overwriting, then only the first call to
     * fileHandling should overwrite. Subsequent file outputs from Cat should be appended.
     * 
     * So the first file should be handled differently if using overwrite.
     */
    // Loop through the entire array
    for (IFile current : files) {
      if (current != null) {
        // Get the contents of the file and print into a file
        contents = contents + current.getContents() + "\n\n\n\n";
      }
    }
    if (!contents.equalsIgnoreCase("")) {
      r.fileHandling(operation, fileName, contents);
    }
  }

  @Override
  /*
   * Returns the name of the command in string form.
   */
  public String toString() {

    return "cat";

  }
}
