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

package jShell;

import jShell.errors.InvalidURLException;
import jShell.interfaces.IUrlDataFetcher;
import java.net.*;
import java.io.*;

/**
 * The UrlDataFetcher class gets the contents of an url in the form of a string.
 * 
 * @author Sankalp Sharma
 * 
 */

public class UrlDataFetcher implements IUrlDataFetcher {

  /**
   * The getUrlData method parses the given url and returns it contents as a string.
   * 
   * @param String the path to the url
   * @return String the contents of the url
   * @throws InvalidURLException if url couldn't be parsed properly
   */

  @Override
  public String getUrlData(String path) throws InvalidURLException {
    // try reading the url using the path link
    try {
      URL url = new URL(path);
      String contents = "";
      BufferedReader reader =
          new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
      for (String line; (line = reader.readLine()) != null;) {
        contents += line + "\n";
      }
      return contents;
    } catch (Exception e) {
      throw new InvalidURLException(path);
    }

  }
}
