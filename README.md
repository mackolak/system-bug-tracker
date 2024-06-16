# system-bug-tracker
Simple CLI application that serves as system bug tracker.
Available commands, the same information as below can be displayed by typing help in the application (including command with help will give more info for it):
- **create** - Create new issue.
- **close** - Close existing issue.
- **list** - List all existing issues.

### Create
Mandatory parameters are, prefix is --:
- **parent-id** - can be between 0 - 10 characters
- **desc** - can be between 0 - 100 characters
- **log-link** - can be between 0 - 10 characters

### Close
Mandatory parameters are, prefix is --:
- **id** - can start with I- or number of the issue only, - can be between 0 - 10 characters

### List
No parameters needed

## Database
Application uses H2 database and stores the data in file in current directory from which the program was executed.

## Tests
Currently covered by integration tests without db (due to problem with @ShellTest not loading JPARepository into Application context)

## Environment variables
No additional env variables needed, apart from the settings already in application.properties file

## Running on local
Either run in editor of choice, or docker needs to be installed for second option(currently not able to run jib build):
Build image:
`docker build . -t system-bug-tracker`

and then run it with:
`docker run -it system-bug-tracker`

# TBD
- better error handling - include exit codes and handle wrong user input
- add test for error scenarios