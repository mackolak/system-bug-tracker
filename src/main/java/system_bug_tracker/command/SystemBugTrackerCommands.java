package system_bug_tracker.command;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import system_bug_tracker.facade.SystemBugTrackerFacade;

@ShellComponent
public class SystemBugTrackerCommands {
  private SystemBugTrackerFacade facade;

  public SystemBugTrackerCommands(SystemBugTrackerFacade facade) {
    this.facade = facade;
  }

  @ShellMethod(value = "Create new issue.", key = "create")
  public String createBug(@ShellOption(value = "parent-id") String parentId, 
                          @ShellOption(value = "desc") String description,
                          @ShellOption(value = "log-link") String logLink
                          ) {                    
    return facade.createBug(parentId, description, logLink);
  }

  @ShellMethod(value = "Close existing issue.", key = "close")
  public String closeBug(@ShellOption(value = "id") Long id) {
    return facade.closeBug(id);
  }

  @ShellMethod(value = "List all existing issues.", key = "list")
  public void listBugs() {
    facade.listBugs();
  }
}
