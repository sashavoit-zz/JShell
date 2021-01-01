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
import java.io.ObjectInputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.io.FileInputStream;
import jShell.commands.Save;
import jShell.errors.ArgsNumberException;
import jShell.errors.FileNotFoundException;
import jShell.fileSystem.Directory;
import jShell.fileSystem.FileSystem;
import jShell.fileSystem.Path;
import jShell.interfaces.IFileSystemNode;

/**
 * Class to test save command
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class SaveTest {

  // File used to record the state of shell
  File stateFile;

  // Path to stateFile
  private static final String FILEPATH = "state.txt";

  Shell sh;
  Save save;

  // Components of Shell that will be loaded from the file
  Directory root;
  UserData ud;

  @Before
  public void init() {
    sh = new Shell();
    save = new Save();
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
  public void testSavingEmptyShell() {
    String[] args = {"save", FILEPATH};
    try {
      save.run(args);
      loadRootAndUserData();
      // Checking that all components of Shell are recorded correctly
      assertEquals(root.getName(), "");
      assertTrue(root.getChildren().isEmpty());
      assertEquals(ud.getCurPath(), sh.getUserData().getCurPath());
      assertEquals(ud.getCommandHistory(),
          sh.getUserData().getCommandHistory());
      assertEquals(ud.getPathStack(), sh.getUserData().getPathStack());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testSavingShellWithCommandsExecuted() {
    String[] args = {"save", FILEPATH};
    try {
      // Adding commands to command history
      sh.getUserData().getCommandHistory().add("first command");
      sh.getUserData().getCommandHistory().add("second command");
      sh.getUserData().getCommandHistory().add("third command");
      sh.getUserData().getCommandHistory().add("forth command");
      save.run(args);
      loadRootAndUserData();
      // Checking that all components of Shell are recorded correctly
      assertEquals(root.getName(), "");
      assertTrue(root.getChildren().isEmpty());
      assertEquals(ud.getCurPath(), sh.getUserData().getCurPath());
      assertEquals(ud.getCommandHistory(),
          sh.getUserData().getCommandHistory());
      assertEquals(ud.getPathStack(), sh.getUserData().getPathStack());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testSavingShellWithNewDirectoriesAdded() {
    String[] args = {"save", FILEPATH};
    try {
      sh.getFileSystem().createNewDirectory(new Path("/dir"));
      sh.getFileSystem().createNewDirectory(new Path("/dir/childDir"));
      sh.getFileSystem().createNewFile(new Path("/dir/file"));
      save.run(args);
      loadRootAndUserData();
      // Checking that all components of File System are recorded correctly
      assertEquals(root.getName(), "");
      assertEquals(1, root.getChildren().size());
      assertTrue(root.ifChildExists("dir"));
      assertTrue(root.getChild("dir").ifChildExists("childDir"));
      assertEquals(2, ((Directory) root.getChild("dir")).getChildren().size());
      assertTrue(root.getChild("dir").ifChildExists("file"));
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
  public void testSavingShellWithNonEmptyStackPath() {
    String[] args = {"save", FILEPATH};
    try {
      sh.getUserData().getPathStack().add(new Path("first path"));
      sh.getUserData().getPathStack().add(new Path("second path"));
      sh.getUserData().getPathStack().add(new Path("third path"));
      sh.getUserData().getPathStack().add(new Path("forth path"));
      save.run(args);
      loadRootAndUserData();
      // Checking that all components of File System are recorded correctly
      assertEquals(root.getName(), "");
      assertEquals(0, root.getChildren().size());
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
  public void testSavingShellWithNonTrivialCurPath() {
    String[] args = {"save", FILEPATH};
    try {
      sh.getFileSystem().createNewDirectory(new Path("/dir"));
      sh.getUserData().setCurPath(new Path("/dir"));;
      save.run(args);
      loadRootAndUserData();
      // Checking that all components of File System are recorded correctly
      assertEquals(root.getName(), "");
      assertEquals(1, root.getChildren().size());
      assertTrue(root.ifChildExists("dir"));
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
  public void testSaveNoArgs() {
    String[] args = {"save"};
    try {
      save.run(args);
      fail();
    } catch (ArgsNumberException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testSaveWithWrongNumberArgs() {
    String[] args = {"save", FILEPATH, "this/is/extra/argument"};
    try {
      save.run(args);
      fail();
    } catch (ArgsNumberException e) {
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testSaveInvalidPath() {
    String[] args = {"save", "this/path/is/invalid"};
    try {
      save.run(args);
      fail();
    } catch (FileNotFoundException e) {
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
   * Function used to load root and user data from stateFile
   */
  private void loadRootAndUserData() {
    try {
      // Opening an input stream
      FileInputStream stream = new FileInputStream(stateFile);
      ObjectInputStream in = new ObjectInputStream(stream);
      // Reading root directory and user data
      root = (Directory) in.readObject();
      ud = (UserData) in.readObject();
      // Closing input stream
      in.close();
      stream.close();
    } catch (Exception e) {
      return;
    }
  }
}
