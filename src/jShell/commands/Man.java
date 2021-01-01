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

import java.util.Arrays;
import java.util.HashMap;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.CommandNotFoundException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionException;
import jShell.errors.RedirectionOperatorsException;
import jShell.interfaces.IRedirection;
import jShell.interfaces.IShell;

/**
 * The Man class holds all of the functions related to the Man command. It inherits some basic
 * methods from the Command class in order to function properly within the Shell system as a whole.
 * 
 * Man returns a small document in the shell regarding the details of a given command.
 * 
 * Man's output can be redirected into a file if specified by the user.
 * 
 * @author Kevin Meharchand
 *
 */
public class Man extends Command {

  private HashMap<String, String> Manuals = new HashMap<String, String>();

  /**
   * The overridden run method inherited from the Command class. Determines which manual to print out
   * in the shell, throwing an exception if the requested manual does not exist.
   * 
   * @return A formatted string representation of the user's desired manual, otherwise returning
   *         nothing if redirection was called for.
   * @throws InvalidPathException
   * @throws CommandNotFoundException
   * @throws ArgsNumberException
   * @throws RedirectionException
   * @throws BadFileNameException
   * @throws NodeExistsException
   * @throws FileNotFoundException
   * @throws RedirectionOperatorsException
   */
  @Override
  public String run(String[] args)
      throws InvalidPathException, RedirectionException, ArgsNumberException,
      CommandNotFoundException, NodeExistsException, BadFileNameException,
      RedirectionOperatorsException, FileNotFoundException {

    isValid(args);
    fillMap(); // initialize the manuals hashmap

    IRedirection r = IShell.getShell().getRedirection();
    String manual = Manuals.get(args[1]); // isValid ensures input validity

    if (!r.checkRedirection(args[args.length - 2], args[args.length - 1])) {
      return manual + "\n";
    } else {
      r.fileHandling(args[args.length - 2], args[args.length - 1], manual);
    }

    return "";
  }

  /*
   * Method used to check the validity of arguments before code execution.
   */
  private void isValid(String[] args) throws RedirectionException,
      ArgsNumberException, CommandNotFoundException {
    // Checking if correct number of arguments is specified
    if (args.length < 2 || args.length > 4 || args.length == 3) {
      throw new ArgsNumberException();
    }
    // Checking if argument is a valid command
    String[] commands = {"cat", "cd", "echo", "exit", "history", "ls", "mkdir",
        "popd", "pushd", "pwd", "speak", "man", "rm", "cp", "mv", "save",
        "load", "tree", "find", "curl"};
    if (!Arrays.asList(commands).contains(args[1])) {
      // Command is not in the list
      throw new CommandNotFoundException(args[1]);
    }
    // Checking if redirection is valid
    if (args.length == 4) {
      IRedirection r = IShell.getShell().getRedirection();
      if (!r.checkRedirection(args[args.length - 2], args[args.length - 1])) {
        throw new RedirectionException();
      }
    }
  }

  /**
   * A helper function designed to fill the man command's hashmap with each possible manual. If a
   * developer were to add any new manuals, they could simply add a .put line here.
   */
  private void fillMap() {
    Manuals.put("cat", this.printManualCat());
    Manuals.put("cd", this.printManualCd());
    Manuals.put("echo", this.printManualEcho());
    Manuals.put("exit", this.printManualExit());
    Manuals.put("history", this.printManualHistory());
    Manuals.put("ls", this.printManualLs());
    Manuals.put("man", this.printManualMan());
    Manuals.put("mkdir", this.printManualMkdir());
    Manuals.put("popd", this.printManualPopd());
    Manuals.put("pushd", this.printManualPushd());
    Manuals.put("pwd", this.printManualPwd());
    Manuals.put("speak", this.printManualSpeak());
    Manuals.put("rm", this.printManualRm());
    Manuals.put("mv", this.printManualMv());
    Manuals.put("cp", this.printManualCp());
    Manuals.put("curl", this.printManualCurl());
    Manuals.put("save", this.printManualSave());
    Manuals.put("load", this.printManualLoad());
    Manuals.put("find", this.printManualFind());
    Manuals.put("tree", this.printManualTree());
  }

  /*
   * The following methods return the manual for the respective command as a string.
   */
  private String printManualCd() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The cd Command:\n");
    toGo += ("\n");
    toGo += ("Usage: cd [DIRECTORY]\n");
    toGo += ("\n");
    toGo += ("This command will change the current "
        + "Directory to 'DIRECTORY'.\n");
    toGo += ("DIRECTORY may be relative to the current"
        + " directory, or it may be a full path.\n");
    toGo += ("\n");
    toGo += ("Similarly to Unix and other shells,"
        + " .. indicates a parent directory\n");
    toGo += ("and . indicates the current directory\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualCat() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The cat Command:\n");
    toGo += ("\n");
    toGo += ("Usage: cat FILE1 [FILE2 FILE3 ...]\n");
    toGo += ("\n");
    toGo += ("This command will print out the contents"
        + " of all files indicated\n");
    toGo += ("(minimum 1) concatenated in the shell.\n");
    toGo += ("\n");
    toGo += ("FILE can be a file name in the current "
        + "working directory, or it\n");
    toGo += ("can be a partial or full path to another"
        + " file in another directory.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualEcho() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The echo Command:\n");
    toGo += ("\n");
    toGo += ("Usage: echo STRING [> FILE]\n");
    toGo += ("Usage: echo STRING [>> FILE]\n");
    toGo += ("\n");
    toGo += ("This command has multiple variants.\n");
    toGo += ("Variant 1: echo STRING\n");
    toGo = toGo
        + ("Simply prints out the given STRING" + " back into the shell.\n");
    toGo += ("\n");
    toGo += ("Variant 2: echo STRING > FILE\n");
    toGo = toGo
        + ("Takes the given STRING and writes it " + "into the given FILE,\n");
    toGo += ("overwriting the contents of FILE"
        + " if it exists, and creating FILE if it doesn't.\n");
    toGo += ("\n");
    toGo += ("Variant 3: echo STRING >> FILE\n");
    toGo += ("Takes the given STRING and appends it "
        + "to the end of the current contents of FILE.\n");
    toGo = toGo
        + ("This variant will also" + " create FILE if it does not exist.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualExit() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The exit Command:\n");
    toGo += ("\n");
    toGo += ("Usage: exit\n");
    toGo += ("\n");
    toGo += ("The exit command closes the shell"
        + " entirely. Adding any arguments after the\n");
    toGo += ("initial command will result"
        + " in an error. Nothing else to see here!\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualHistory() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The history Command:\n");
    toGo += ("\n");
    toGo += ("Usage: history [NUMBER]\n");
    toGo += ("\n");
    toGo += ("The history command prints out into the "
        + "shell a list of the most recently entered commands.\n");
    toGo += ("This list can be shortened by adding a "
        + "number after the command. \n");
    toGo += ("If no number is specified, then all "
        + "commands up to and including\n");
    toGo +=
        ("the current instance of the history " + "command will be listed.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualMan() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The man Command:\n");
    toGo += ("\n");
    toGo += ("Usage: man COMMAND\n");
    toGo += ("\n");
    toGo += ("The man command prints out the"
        + " documentation for a given COMMAND into the shell.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualLs() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The ls Command:\n");
    toGo += ("\n");
    toGo += ("Usage: ls [-R] [PATH ...]\n");
    toGo += ("\n");
    toGo += ("The ls command prints out to the shell"
        + " the contents of the current directory if no PATH is specified.\n");
    toGo += ("\n");
    toGo += ("If a PATH is specified, ls will print "
        + "out the contents of the specified directory at the path.\n");
    toGo += ("\n");
    toGo += ("If the given PATH ends at a file, the"
        + " name of that file will be printed out instead\n");
    toGo += ("\n");
    toGo += ("Multiple PATHs may be included after the "
        + "first. If so, the contents of each will be displayed in the order"
        + " given.\n");
    toGo += ("\n");
    toGo += ("If any PATHs included as arguments are"
        + " invalid/do not exist, an error message will be returned instead"
        + " of any output.\n");
    toGo += ("\n");
    toGo += ("If the -R option is included, ls will"
        + " recursively list the contents\n");
    toGo += ("of the subdirectories of PATH as well.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualMkdir() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The mkdir Command:\n");
    toGo += ("\n");
    toGo += ("Usage: mkdir DIRECTORY [DIRECTORY2...]\n");
    toGo += ("\n");
    toGo += ("The mkdir command creates a user-given"
        + " DIRECTORY at the user-specified path.\n");
    toGo += ("DIRECTORY may be either a full path"
        + " or a path relative to the current directory.\n");
    toGo += ("Mkdir can create multiple DIRECTORIES at a time.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualPopd() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The popd Command:\n");
    toGo += ("\n");
    toGo += ("Usage: popd\n");
    toGo += ("\n");
    toGo += ("The popd command removes the top "
        + "directory stored in the directory stack (see pushd)\n");
    toGo += ("and changes the" + " current working directory to said "
        + "directory.\n");
    toGo += ("\n");
    toGo += ("If there are no directories stored"
        + " in the stack, then popd will not \n");
    toGo += ("move the user's current directory.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualPushd() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The pushd Command:\n");
    toGo += ("\n");
    toGo += ("Usage: pushd DIRECTORY\n");
    toGo += ("\n");
    toGo += ("The pushd command takes the current "
        + " directory and saves it onto the directory stack.\n");
    toGo += ("The current directory will then change"
        + " to the user-given DIRECTORY.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualPwd() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The pwd Command:\n");
    toGo += ("\n");
    toGo += ("Usage: pwd\n");
    toGo += ("\n");
    toGo += ("The pwd command simply prints out the"
        + " current working directory (including its whole path).\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualSpeak() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The speak Command:\n");
    toGo += ("\n");
    toGo += ("Usage: speak [STRING]\n");
    toGo += ("\n");
    toGo += ("The speak command will convert a"
        + " user-given STRING into audible speech output.\n");
    toGo += ("\n");
    toGo += ("The STRING parameter is optional. If"
        + " excluded, the speak command will then enter\n");
    toGo += ("a state in which the user may"
        + " type in any input they wish, followed by the word \n");
    toGo += ("QUIT (case-" + "sensitive) to have the output play.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualRm() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The rm Command:\n");
    toGo += ("\n");
    toGo += ("Usage: rm DIRECTORY\n");
    toGo += ("\n");
    toGo += ("The rm command removes a directory"
        + " (and subsequently its contents)\n");
    toGo += ("from the file system\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualMv() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The mv Command:\n");
    toGo += ("\n");
    toGo += ("Usage: mv OLDPATH NEWPATH\n");
    toGo += ("\n");
    toGo += ("The mv command moves the item at path"
        + " OLDPATH (including its contents if it is a directory)\n");
    toGo = toGo
        + ("to NEWPATH. OLDPATH is then removed" + " from the file system.\n");
    toGo += ("\n");
    toGo += ("If NEWPATH is a directory, OLDPATH"
        + " becomes an item in that directory.\n");
    toGo += ("\n");
    toGo += ("If OLDPATH is a file and NEWPATH does not exist in the");
    toGo += (" current directory, \n then the item at OLDPATH is");
    toGo += (" renamed to NEWPATH.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualCp() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The cp Command:\n");
    toGo += ("\n");
    toGo += ("Usage: cp OLDPATH NEWPATH\n");
    toGo += ("\n");
    toGo += ("The cp command copies the path OLDPATH"
        + " (including the contents of OLDPATH if it is a directory)\n");
    toGo =
        toGo + (" and moves it to the location specified" + " by NEWPATH.\n");
    toGo += ("\n");
    toGo += ("Unlike the mv command, cp does not "
        + "delete OLDPATH once it has moved.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualCurl() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The curl Command:\n");
    toGo += ("\n");
    toGo += ("Usage: curl URL\n");
    toGo += ("\n");
    toGo += ("The curl command retrieves a file from"
        + " the given URL and adds\n");
    toGo += ("it to the current working directory.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualSave() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The save Command:\n");
    toGo += ("\n");
    toGo += ("Usage: save FILENAME\n");
    toGo += ("\n");
    toGo += ("The save command saves the current state"
        + " of the shell to a file on the user's actual computer.\n");
    toGo += ("\n");
    toGo += ("All elements of the shell, including"
        + " files, command states and the mock file system are saved.\n");
    toGo += ("\n");
    toGo += ("If the user chooses to use an existing"
        + " file as FILENAME, the contents of FILENAME\n");
    toGo += ("will be overwritten " + "with the shell's save state.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualLoad() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The load Command:\n");
    toGo += ("\n");
    toGo += ("Usage: load FILENAME\n");
    toGo += ("\n");
    toGo += ("The load command loads a previously"
        + " saved state of the shell (see the \"save\" command manual).\n");
    toGo += ("\n");
    toGo += ("This command cannot be used once any"
        + " other command has been used\n");
    toGo += ("in the current instance of the shell.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualTree() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The tree Command:\n");
    toGo += ("\n");
    toGo += ("Usage: tree\n");
    toGo += ("\n");
    toGo += ("The tree command outputs in a string"
        + " format the entire file system\n");
    toGo += ("of the current instance of the shell.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  private String printManualFind() {
    String toGo = "";
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    toGo += ("The find Command:\n");
    toGo += ("\n");
    toGo += ("Usage: find PATH [PATH2...] " + "-type TYPE -name NAME\n");
    toGo += ("\n");
    toGo += ("The find command searches the "
        + "directory(ies) provided at PATH (and PATH2, PATH3, etc.)\n");
    toGo += ("for objects of type TYPE with the " + "name NAME.\n");
    toGo += ("\n");
    toGo += ("the TYPE argument can be either \"f\""
        + " for files, or \"d\" for directories.\n");
    toGo += ("\n");
    toGo += ("The NAME argument is the desired file or"
        + " directory name to be located, case sensitive.\n");
    toGo += ("~~~~~~~~~~~~~~~~~~~~\n");
    return toGo;
  }

  @Override
  /*
   * Method used to return the name of the command.
   */
  public String toString() {

    return "man";

  }
}
