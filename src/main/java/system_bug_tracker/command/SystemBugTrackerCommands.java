package system_bug_tracker.command;

import java.util.List;
import java.util.Optional;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import system_bug_tracker.model.Issue;
import system_bug_tracker.model.IssueStatus;
import system_bug_tracker.repository.SystemBugTrackerRepository;

@ShellComponent
public class SystemBugTrackerCommands {
  private SystemBugTrackerRepository repository;
  
  public SystemBugTrackerCommands(SystemBugTrackerRepository repository) {
    this.repository = repository;
  }

  @ShellMethod(value = "Create new issue.", key = "create")
  public String createBug(@ShellOption(value = "parent-id") String parentId, 
                          @ShellOption(value = "desc") String description,
                          @ShellOption(value = "log-link") String logLink
                          ) {
    Issue issue = repository.save(new Issue(parentId, description, logLink));                        
    return issue.getId();
  }

  @ShellMethod(value = "Close existing issue.", key = "close")
  public String closeBug(@ShellOption(value = "id") Long id) {
    Optional<Issue> optionalIssue = repository.findById(id);
    if (optionalIssue.isPresent()) {
      Issue issue = optionalIssue.get();
      issue.setStatus(IssueStatus.CLOSED);
      repository.save(issue);
      return "Issue with id: " + id + " has been closed.";
    } else {
      return "Issue with id: " + id + " was not found.";
    }
  }

  @ShellMethod(value = "List all existing issues.", key = "list")
  public String listBugs() {
    List<Issue> issues = repository.findAll();
    if (!issues.isEmpty()) {
      return issues.toString();
    } else {
      return "No issues found";
    }
    
  }

}
