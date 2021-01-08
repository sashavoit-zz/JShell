# JShell

JavaShell is a Unix Shell emulator application written in Java and run through terminal. It provides functionality of main commands of Unix Shell, has it's own mock file system tree (where each node is file or directory), and capable of displaying appropriate error messages if used not properly.

<img src="media/demo.gif" width="800">

# Contents
- [Installation](#installation)
- [Project development](#project-development)
- [Usage](#usage)
- [Credits](#credits)

# Installation

To install and run application open any folder and type 

```console
foo@bar:~$ git clone https://github.com/sashavoit/JShell
foo@bar:~$ cd JShell
foo@bar:~$ mvn -q compile
foo@bar:~$ mvn exec:java
```

# Project development

Project was developed in a team of four following Agile Scrum as using SVN version control. Overall team had 5 sprints. Here you can find [product backlog](productBacklog), [sprint backlogs](sprints), and complete log of [daily SCRUM meetings](dailyScrumMeetings). Here's also a complete set of [CRC cards](crcCards) for each Java class and interface.

# Usage

### output redirection

Any command that has an output supports redirection to a file. Use > to overwrite contents of the file and >> to append.

```console
foo@bar:~$ COMMAND ARGUMENTS > FILE
```
<img src="media/redirection-overwrite.gif" width="800">

```console
foo@bar:~$ COMMAND ARGUMENTS >> FILE
```
<img src="media/redirection-append.gif" width="800">

### man command

```console
foo@bar:~$ man COMMAND
```

Prints out the documentation for a given COMMAND into the shell. Outputs error if COMMAND does not exist.

<img src="media/man.gif" width="600">

### mkdir command

```console
foo@bar:~$ mkdir DIRECTORY...
```

Creates directory(ies) at given full or relative path(s).

### cd command

```console
foo@bar:~$ cd DIRECTORY
```

Change current working directory to given directory.

<img src="media/cd.gif" width="800">

### ls command

```console
foo@bar:~$ ls [-R] [DIRECTORY/FILE]...
```

List contents of DIRECTORY, or a location of FILE. If no directory provided, print contents of current working directory.

Options:
- -R list contents recursively 

<img src="media/ls.gif" width="800">

### echo command

```console
foo@bar:~$ echo "STRING"
```

Outputs STRING. STRING must be surrounded by double quotes.

<img src="media/echo.gif" width="800">

### cat command

```console
foo@bar:~$ cat FILE...
```

Outputs concatenation of contents of the file(s). 

<img src="media/cat.gif" width="800">

### exit command

```console
foo@bar:~$ exit
```

End session of JShell.

<img src="media/exit.gif" width="800">

### mv command

```console
foo@bar:~$ mv SOURCE DEST
```

Move file or directory at SOURCE to location at DEST.

<img src="media/mv.gif" width="800">

### cp command

```console
foo@bar:~$ cp SOURCE DEST
```

Copy file or directory at SOURCE to location at DEST.

<img src="media/cp.gif" width="800">

### rm command 

```console
foo@bar:~$ rm DIRECTORY
```

Deletes directory and all it's contents recursively.

<img src="media/rm.gif" width="800">

### pushd command

```console
foo@bar:~$ pushd DIRECTORY
```

Pushes DIRECTORY onto a directory stack in memory.

<img src="media/pushd.gif" width="800">

### popd command

```console
foo@bar:~$ pushd
```

Changes current working to last directory from the directory stack in memory.

<img src="media/popd.gif" width="800">

### pwd command

```console
foo@bar:~$ pwd
```

Prints full path to current working directory.

<img src="media/pwd.gif" width="800">

### history command

```console
foo@bar:~$ history [N]
```

Prints history of N last commands entered by user (including history command itself). If number N is not provided, print full history.

<img src="media/history.gif" width="800">

### tree command

```console
foo@bar:~$ tree
```

Prints file system tree starting at root directory recursively.

<img src="media/tree.gif" width="800">

### save command

```console
foo@bar:~$ save FILE
```

Saves state of the JShell to FILE on the real hard drive of the computer. Can be loaded in the next session.

<img src="media/save.gif" width="800">

### load command

```console
foo@bar:~$ load FILE
```

Loads the state of the JShell from FILE on the real hard drive of the computer. Must be the first command after the start of the sesion.

<img src="media/load.gif" width="800">

### find command

```console
foo@bar:~$ find PATH... -type TYPE -name "NAME" 
```

Looks for node of type TYPE (d or f) with name NAME at location(s) specified by PATH(s). 

<img src="media/find.gif" width="800">

### curl command

```console
foo@bar:~$ curl URL 
```

Retrieves a file from URL and saves it to current working directory.

<img src="media/curl.gif" width="800">

# Credits

This project was developed as part of CSCB07 Software Design course and University of Toronto. Team of developers:
- Raz Ben Haim 
- Kevin Meharchand
- Sankalp Sharma
- Sasha Voitovych (me)
