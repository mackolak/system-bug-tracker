package system_bug_tracker.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import system_bug_tracker.model.Issue;
import system_bug_tracker.model.IssueStatus;
import system_bug_tracker.repository.SystemBugTrackerRepository;
import system_bug_tracker.utils.Helper;

@Component
@Slf4j
public class SystemBugTrackerFacade {
  private SystemBugTrackerRepository repository;
  private Helper helper;
  
  public SystemBugTrackerFacade(SystemBugTrackerRepository repository, Helper helper) {
    this.repository = repository;
    this.helper = helper;
  }

  public String createBug(String parentId, String description, String logLink) {
    try {
      Issue issue = repository.save(new Issue(parentId, description, logLink));                        
      return issue.getId();
    } catch (Exception e) {
      log.error("Error during createBug: {}", e.getMessage(),e);
      return "Error happenned during create issue command execution, please try again or consult help";
    }
  }

  public String closeBug(String id) {
    try {
      Long convertedId = helper.convertId(id);
      Optional<Issue> optionalIssue = repository.findById(convertedId);
      if (optionalIssue.isPresent()) {
        Issue issue = optionalIssue.get();
        issue.setStatus(IssueStatus.CLOSED);
        repository.save(issue);
        return "Issue with id: " + issue.getId() + " has been closed.";
      } else {
        return "Issue with id: I-" + convertedId + " was not found.";
      }
    } catch (Exception e) {
      log.error("Error during closeBug: {}", e.getMessage(),e);
      return "Error happenned during close issue command execution, please try again or consult help";
    }
  }

  public void listBugs() {
    try {
      List<Issue> issues = repository.findAll();
      helper.printIssuesTable(issues);
    } catch (Exception e) {
      log.error("Error during listBugs: {}", e.getMessage(),e);
      helper.printMessageToTerminal("Error happenned during close issue command execution, please try again or consult help");
    }
  }
}
