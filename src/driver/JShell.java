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
package driver;

import java.util.concurrent.TimeUnit;
import jShell.Shell;

public class JShell {

  public static void main(String[] args) {
    Shell shell = new Shell(); // Initialize the shell

    // While shell is running, collect user's input from shell
    while (shell.isRunning()) {

      try {
        TimeUnit.NANOSECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      if (shell.getInputMode() != null) {
        shell.outputToShell("$ ");
      } else if (shell.getUserData().getCurPath().toString().equals("/")) {

        // Currently at root directory
        shell.outputToShell("User@Laptop:", Shell.TEXTGREEN);
        shell.outputToShell("$ ");
      } else {

        // Currently at non-root directory
        shell.outputToShell("User@Laptop:", Shell.TEXTGREEN);
        shell.outputToShell(shell.getUserData().getCurPath().toString(),
            Shell.TEXTBLUE);
        shell.outputToShell("$ ");
      }

      // Read user's input
      String input = shell.getUserInput();
      shell.onInput(input);
    }
  }

}
