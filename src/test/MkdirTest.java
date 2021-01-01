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
import org.junit.Test;
import org.junit.Before;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;
import jShell.commands.Mkdir;
import jShell.errors.*;

/**
 * Class to test mkdir command
 * 
 * @author Sasha (Oleksandr)
 *
 */

public class MkdirTest {

  // We will use mock shell for testing
  MockShell shell;
  Mkdir mkdir;

  @Before
  public void init() {
    // Initializing mock shell
    shell = new MockShell();
    mkdir = new Mkdir();
  }

  @Test
  public void testMkdirWithNoArguments() {
    String[] args = {"mkdir"};
    try {
      mkdir.run(args);
      fail();
    } catch (ArgsNumberException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testMkdirDirectoryExist() {
    try {
      String[] arguments1 = {"mkdir", MockFileSystem.validDirFullPath};
      mkdir.run(arguments1);
      fail();
    } catch (NodeExistsException e) {
    } catch (Exception e) {
      fail();
    }
    try {
      String[] arguments2 = {"mkdir", MockFileSystem.validDirRelativePath};
      mkdir.run(arguments2);
      fail();
    } catch (NodeExistsException e) {
    } catch (Exception e) {
      fail();
    }
    try {
      String[] arguments3 = {"mkdir", MockFileSystem.validFileFullPath};
      mkdir.run(arguments3);
      fail();
    } catch (NodeExistsException e) {
    } catch (Exception e) {
      fail();
    }
    try {
      String[] arguments4 = {"mkdir", MockFileSystem.validFileRelativePath};
      mkdir.run(arguments4);
      fail();
    } catch (NodeExistsException e) {
    } catch (Exception e) {
      fail();
    }
    return;
  }

  @Test
  public void testRunInvalidPath() {
    try {
      String[] arguments = {"mkdir", "some/invalid/path"};
      mkdir.run(arguments);
      fail();
    } catch (InvalidPathException e) {
      return;
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testRunValidPath() {
    try {
      String[] arguments =
          {"mkdir", MockFileSystem.validDirFullPath + "/newDir"};
      mkdir.run(arguments);
    } catch (Exception e) {
      fail();
    }
    return;
  }
}
