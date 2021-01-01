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

package jShell.mockClasses;

import jShell.errors.InvalidURLException;
import jShell.interfaces.IUrlDataFetcher;

public class MockUrlDataFetcher implements IUrlDataFetcher {

  @Override
  public String getUrlData(String path) throws InvalidURLException {
    // TODO Auto-generated method stub
    if (!path.toLowerCase().endsWith(".txt")) {
      throw new InvalidURLException(path);
    } else {
      return "these are the contents of the url";
    }
  }
}
