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

import jShell.interfaces.IDirectory;
import jShell.interfaces.IRedirection;
import jShell.interfaces.IShell;
import jShell.interfaces.IUrlDataFetcher;
import jShell.errors.ArgsNumberException;
import jShell.errors.BadFileNameException;
import jShell.errors.FileNotFoundException;
import jShell.errors.InvalidPathException;
import jShell.errors.InvalidURLException;
import jShell.errors.NodeExistsException;
import jShell.errors.RedirectionOperatorsException;
import jShell.errors.UnknownErrorException;
import jShell.fileSystem.File;
import jShell.fileSystem.FileSystemNode;

/**
 * The curl command fetches data from an url and adds the contents to a file into to the current
 * working directory. It doesn't support redirection to a file. The curl commands injects an url
 * data fetcher that it uses to get the contents of the url.
 * 
 * @author Sankalp Sharma
 */

public class Curl extends Command {

  private IUrlDataFetcher udf;

  /**
   * The Url data fetcher is injected via the constructor at run time.
   * 
   * @param udf IUrlDataFetcher object to be used for parsing url contents
   */

  public Curl(IUrlDataFetcher udf) {
    super();
    this.udf = udf;

  }

  /**
   * The run command uses the url data fetcher to get the contents of the url. It then creates a file
   * with the contents of the url and adds that to the current working directory. It overwrites the
   * contents of the file, if a file exists with the same name.
   * 
   * @param String args ,curl command and url for parsing
   * @return empty string that signifies command executed properly
   * @throws ArgsNumberException too many args
   * @throws InvalidURLException url couldn't be parsed properly
   * @throws BadFileNameException invalid filename when extracting it of URL
   * @throws InvalidPathException invalid path for redirection
   * @throws NodeExistsException, RedirectionOperatorsException,
   * @throws RedirectionOperatorsException error when not using redirection operators properly
   * @throws FileNotFoundException file not found for redirection
   */

  @Override
  public String run(String[] args)
      throws ArgsNumberException, InvalidURLException, BadFileNameException,
      InvalidPathException, NodeExistsException, RedirectionOperatorsException,
      FileNotFoundException {
    // catch errors right away
    argChecker(args);
    // special case of ending with a slash or any redirection errors
    String urlName = args[1];
    if (urlName.endsWith("/") || urlName.endsWith(">")
        || urlName.endsWith(">>")) {
      throw new InvalidURLException(urlName);
    }
    // extract . from url to make the fileName and throw an exception if its invalid
    String fileName = urlName.substring(urlName.lastIndexOf("/") + 1,
        urlName.lastIndexOf("."));
    if (!FileSystemNode.nameChecker(fileName)) {
      throw new BadFileNameException(urlName);
    }
    /*
     * Get the contents of url and add it to the file and then add that file to current working
     * directory, overwrite if file with same name already exists
     */
    String contents = udf.getUrlData(urlName);
    File f = new File(fileName);
    f.setContents(contents);
    IDirectory d = IShell.getShell().getUserData().getCurDir();

    if (d.ifChildExists(fileName)) {
      IRedirection r = IShell.getShell().getRedirection();
      r.fileHandling(">", fileName, contents);
    } else {
      d.tryAddChildren(f);
    }
    return "";
  }



  private void argChecker(String args[])
      throws ArgsNumberException, InvalidURLException {
    // can't do parse more than one url at a time
    if (args.length != 2) {
      throw new ArgsNumberException();
    }
  }

  @Override
  public String toString() {

    return "curl";
  }

}
