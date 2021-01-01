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

package jShell.errors;

import java.util.Formatter;

public class BadStringException extends Error {

  private static final String errorTemplate =
      " Please enter a correctly formatted string (Begins and ends with double "
          + "quotes, no double quotes within).";
  private static final String errorTemplate2 =
      " %s is not a correctly formatted string (Begins and ends with double "
          + "quotes, no double quotes within).";

  /**
   * Exceptions for strings that are not correctly formatted.
   * 
   * @param args
   */
  public BadStringException(Object... args) {
    super(new Formatter()
        .format(args.length == 0 ? errorTemplate : errorTemplate2, args)
        .toString());
  }


}
