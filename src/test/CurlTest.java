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
import jShell.commands.Curl;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.InvalidURLException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;
import jShell.mockClasses.MockFile;
import jShell.mockClasses.MockShell;
import jShell.mockClasses.MockUrlDataFetcher;

public class CurlTest {

  /**
   * Setting up MockFile and MockUrlDataFetcher and Mockshell for testing
   */

  MockFile f = new MockFile();
  MockUrlDataFetcher mdf = new MockUrlDataFetcher();
  Curl c = new Curl(mdf);
  MockShell s = new MockShell();


  /*
   * Testing the too many arguements exception. There should only be 2 arguments
   */
  @Test(expected = ArgsNumberException.class)
  public void testTooManyArgs() throws ArgsNumberException, InvalidURLException,
      BadFileNameException, InvalidPathException, NodeExistsException,
      RedirectionOperatorsException, FileNotFoundException {
    String[] params = {"curl", "http://www.sampleurl.com", "3"};
    c.run(params);

  }

  // Testing invalid url exception, should only be txt or html files
  @Test(expected = InvalidURLException.class)
  public void testInValidURLDataFetcher() throws InvalidURLException {
    String param =
        "http://www.cs.toronto.edu/~trebla/CSCB09-2019-Summer/lab02/words.htmlxt";
    mdf.getUrlData(param);
  }

  // Testing Valid case of UrlDataFetcher. Valid because of text extension

  @Test
  public void testValidURLDataFetcher() throws InvalidURLException {
    String param = "http://www.sampleurl.com/some.txt";
    assertEquals("these are the contents of the url", mdf.getUrlData(param));
  }

  // testing toString method
  @Test
  public void testToString() {
    assertEquals("curl", c.toString());
  }

  /*
   * Testing adding file contents to current directory. Check is done by comparing the last message of
   * the Shell
   */
  @Test
  public void testAddtoCurrDirectory()
      throws ArgsNumberException, InvalidURLException, BadFileNameException,
      InvalidPathException, NodeExistsException, RedirectionOperatorsException,
      FileNotFoundException {
    String params[] = {"curl", "http://www.sampleurl.com/curlFile.txt"};
    c.run(params);
    assertEquals(s.getLastOutput(), "Adding " + "curlFile" + "\n");
  }

  /*
   * Testing the overwrite method. Curl method uses redirection for overwrite so just need to check if
   * the overwrite option for redirection has been activated
   */
  @Test
  public void testOverwrite() throws ArgsNumberException, InvalidURLException,
      BadFileNameException, InvalidPathException, NodeExistsException,
      RedirectionOperatorsException, FileNotFoundException {
    String params[] = {"curl", "http://www.sampleurl.com/childFile.txt"};
    c.run(params);
    assertEquals(s.getLastOutput(), "Redirecting output for overwriting:"
        + mdf.getUrlData(params[1]) + "\n to file with name: " + "childFile");
  }
}
