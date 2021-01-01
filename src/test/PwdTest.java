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
import java.util.HashMap;
import org.junit.Test;
import jShell.fileSystem.Path;
import jShell.interfaces.IPath;
import jShell.Shell;
import jShell.commands.Pwd;
import jShell.errors.*;
import jShell.mockClasses.MockFileSystem;
import jShell.mockClasses.MockShell;
import jShell.mockClasses.MockUserData;

public class PwdTest {

  /**
   * Setting up mockShell and Pwd object for testing
   */
  MockShell s = new MockShell();
  Pwd p = new Pwd();

  // testing to String method
  @Test
  public void testtoString() {
    assertEquals("pwd", p.toString());
  }

  // testing ArgsNumberException, additional args are only possible for redirection
  @Test(expected = ArgsNumberException.class)
  public void testwithInvalidArgs()
      throws ArgsNumberException, RedirectionException, InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException {
    String[] params = {"pwd", "a"};
    p.run(params);
  }

  // testing BadFileNameException for Redirection
  @Test(expected = BadFileNameException.class)
  public void testRedirectionWithInvalidFileName() throws InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, ArgsNumberException, RedirectionException {
    String[] params = {"pwd", ">", "!"};
    p.run(params);
  }

  // testing Valid pwd, compare with curPath of mockShell
  @Test
  public void testValidPwd() throws InvalidPathException, NodeExistsException,
      BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, ArgsNumberException, RedirectionException {
    String[] params = {"pwd"};
    assertEquals(p.run(params), s.getUserData().getCurPath().toString());
  }

  // testing ValidRedirection, append and overwrite Cases
  @Test
  public void testValidRedirectionOverWrite() throws InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, ArgsNumberException, RedirectionException {
    String[] params = {"pwd", ">", "childFile"};
    p.run(params);
    String outputMessageOverWrite = "Redirecting output for overwriting:"
        + s.getUserData().getCurPath().toString() + "\n to file with name: "
        + "childFile";
    assertEquals(outputMessageOverWrite, s.getLastOutput());

    // testing Overwrite
    String[] params2 = {"pwd", ">>", "childFile"};
    p.run(params2);
    String outputMessageAppend = "Redirecting output for appending:"
        + s.getUserData().getCurPath().toString() + "\n to file with name: "
        + "childFile";
    assertEquals(outputMessageAppend, s.getLastOutput());
  }

  // testing RedirectionException, this is the wrong order for Redirection
  @Test(expected = RedirectionException.class)
  public void testInvalidRedirectionArgs() throws InvalidPathException,
      NodeExistsException, BadFileNameException, RedirectionOperatorsException,
      FileNotFoundException, ArgsNumberException, RedirectionException {
    String[] params = {"pwd", "a", ">"};
    p.run(params);
  }
}
