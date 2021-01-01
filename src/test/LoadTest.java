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

package test;

import static org.junit.Assert.*;
import java.io.File;
import java.io.IOException;
import org.junit.After;
import org.junit.Test;
import org.junit.Before;
import jShell.Shell;
import jShell.UserData;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.io.FileOutputStream;
import jShell.commands.Load;
import jShell.errors.ArgsNumberException;
import jShell.errors.FileNotFoundException;
import jShell.errors.LoadNotFirstException;
import jShell.fileSystem.Directory;
import jShell.fileSystem.FileSystem;
import jShell.fileSystem.Path;
import jShell.interfaces.IFileSystemNode;

/**
 * Class to test load command
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class LoadTest {

  // File used to record the state of the shell
  File stateFile;

  // Path to stateFile
  private static final String FILEPATH = "state.txt";

  Shell sh;
  Load load;

  // Components to be recorded to the file and then used to load Shell
  Directory root;
  UserData ud;

  @Before
  public void init() {
    sh = new Shell();
    load = new Load();
    root = new Directory("");
    ud = new UserData(new Path("/"));
    // Creating a file for the state to be recorded
    stateFile = new File(FILEPATH);
    try {
      if (!stateFile.createNewFile()) {
        stateFile.delete();
        stateFile.createNewFile();
      }
    } catch (IOException e) {
      System.out.println("Error during initializing");
    }
  }

  @Test
  public void testLoadingEmptyShell() {
    String[] args = {"load", FILEPATH};
    try {
      saveRootAndUserData();
      ud.getCommandHistory().add(getLoadCall(args));
      load.run(args);
      // Checking that all components of Shell were loaded correctly
      assertEquals(root.getName(), sh.getFileSystem().getRootDir().getName());
      assertTrue(sh.getFileSystem().getRootDir().getChildren().isEmpty());
      assertEquals(ud.getCurPath(), sh.getUserData().getCurPath());
      assertEquals(ud.getCommandHistory(),
          sh.getUserData().getCommandHistory());
      assertEquals(ud.getPathStack(), sh.getUserData().getPathStack());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLoadingShellWithCommandsExecuted() {
    String[] args = {"load", FILEPATH};
    try {
      // Adding commands to command history
      ud.getCommandHistory().add("first command");
      ud.getCommandHistory().add("second command");
      ud.getCommandHistory().add("third command");
      ud.getCommandHistory().add("forth command");

      saveRootAndUserData();
      ud.getCommandHistory().add(getLoadCall(args));
      load.run(args);
      // Checking that all components of Shell were loaded correctly
      assertEquals(sh.getFileSystem().getRootDir().getName(), "");
      assertTrue(sh.getFileSystem().getRootDir().getChildren().isEmpty());
      assertEquals(ud.getCurPath(), sh.getUserData().getCurPath());
      assertEquals(ud.getCommandHistory(),
          sh.getUserData().getCommandHistory());
      assertEquals(ud.getPathStack(), sh.getUserData().getPathStack());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLoadingShellWithNewDirectoriesAdded() {
    String[] args = {"load", FILEPATH};
    try {
      /*
       * Creating a mock file tree with root directory at root:
       * 
       * \ dir childDir file
       */
      Directory dir = new Directory("dir");
      Directory childDir = new Directory("childDir");
      jShell.fileSystem.File file = new jShell.fileSystem.File("file");
      root.tryAddChildren(dir);
      dir.tryAddChildren(childDir);
      dir.tryAddChildren(file);
      saveRootAndUserData();
      // Adding load call to command history
      ud.getCommandHistory().add(getLoadCall(args));
      load.run(args);
      // Checking that all components of File System were loaded correctly
      assertEquals(sh.getFileSystem().getRootDir().getName(), "");
      assertEquals(1, sh.getFileSystem().getRootDir().getChildren().size());
      assertTrue(sh.getFileSystem().getRootDir().ifChildExists("dir"));
      assertTrue(sh.getFileSystem().getRootDir().getChild("dir")
          .ifChildExists("childDir"));
      assertEquals(2,
          ((Directory) sh.getFileSystem().getRootDir().getChild("dir"))
              .getChildren().size());
      assertTrue(sh.getFileSystem().getRootDir().getChild("dir")
          .ifChildExists("file"));
      // Checking that all components of UserData are recorded correctly
      assertEquals(ud.getCurPath(), sh.getUserData().getCurPath());
      assertEquals(ud.getCommandHistory(),
          sh.getUserData().getCommandHistory());
      assertEquals(ud.getPathStack(), sh.getUserData().getPathStack());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLoadingShellWithNonEmptyStackPath() {
    String[] args = {"load", FILEPATH};
    try {
      // Adding commands to command history
      ud.getPathStack().add(new Path("first path"));
      ud.getPathStack().add(new Path("second path"));
      ud.getPathStack().add(new Path("third path"));
      ud.getPathStack().add(new Path("forth path"));
      saveRootAndUserData();
      ud.getCommandHistory().add(getLoadCall(args));
      load.run(args);
      // Checking that all components of File System were loaded correctly
      assertEquals(sh.getFileSystem().getRootDir().getName(), "");
      assertEquals(0, sh.getFileSystem().getRootDir().getChildren().size());
      // Checking that all components of UserData are recorded correctly
      assertEquals(ud.getCurPath(), sh.getUserData().getCurPath());
      assertEquals(ud.getCommandHistory(),
          sh.getUserData().getCommandHistory());
      assertEquals(ud.getPathStack(), sh.getUserData().getPathStack());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLoadingShellWithNonTrivialCurPath() {
    String[] args = {"load", FILEPATH};
    try {
      // Setting current path to /dir
      ud = new UserData(new Path("/dir"));
      saveRootAndUserData();
      load.run(args);
      ud.getCommandHistory().add(getLoadCall(args));
      // Checking that all components of File System were loaded correctly
      assertEquals(sh.getFileSystem().getRootDir().getName(), "");
      assertEquals(0, sh.getFileSystem().getRootDir().getChildren().size());
      // Checking that all components of UserData are recorded correctly
      assertEquals(ud.getCurPath(), sh.getUserData().getCurPath());
      assertEquals(ud.getCommandHistory(),
          sh.getUserData().getCommandHistory());
      assertEquals(ud.getPathStack(), sh.getUserData().getPathStack());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLoadNoArgs() {
    String[] args = {"load"};
    try {
      load.run(args);
      fail();
    } catch (ArgsNumberException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLoadCallWithWrongNumberArgs() {
    String args[] = {"load", FILEPATH, "extra/argument"};
    try {
      load.run(args);
      fail();
    } catch (ArgsNumberException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLoadInvalidPath() {
    String args[] = {"load", "this/is/invalid/path"};
    try {
      load.run(args);
      fail();
    } catch (FileNotFoundException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testLoadNotFirstCommand() {
    sh.getUserData().getCommandHistory()
        .add("this command was executed before load");
    String args[] = {"load", FILEPATH};
    sh.getUserData().getCommandHistory().add(getLoadCall(args));
    try {
      load.run(args);
      fail();
    } catch (LoadNotFirstException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @After
  public void unInit() {
    // Cleaning FileSystem from potential created nodes
    Queue<IFileSystemNode> toDelete = new LinkedList<>();
    for (IFileSystemNode node : FileSystem.getFileSystem().getRootDir()
        .getChildren()) {
      toDelete.add(node);
    }
    for (IFileSystemNode node : toDelete) {
      FileSystem.getFileSystem().getRootDir().removeChild(node);
    }
    // Cleaning global variables
    root = null;
    ud = null;
    stateFile.delete();
  }

  /**
   * Method to save root and user data to stateFile
   */
  private void saveRootAndUserData() {
    try {
      // Opening an input stream
      FileOutputStream stream = new FileOutputStream(stateFile);
      ObjectOutputStream out = new ObjectOutputStream(stream);
      // Writing root directory and user data
      out.writeObject(root);
      out.writeObject(ud);
      // Closing input stream
      out.close();
      stream.close();
    } catch (Exception e) {
      return;
    }
  }

  /**
   * Method to get the String representing a call of load command from the arguments for load command
   * 
   * @param args Arguments for load command
   * @return string representation of call to load
   */
  private String getLoadCall(String[] args) {
    String loadCall = "";

    for (String arg : args) {
      loadCall += arg + " ";
    }

    loadCall = loadCall.substring(0, Math.max(0, loadCall.length() - 1));
    return loadCall;
  }
}
