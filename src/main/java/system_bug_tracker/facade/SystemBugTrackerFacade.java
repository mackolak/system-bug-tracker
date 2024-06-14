package system_bug_tracker.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import system_bug_tracker.model.Issue;
import system_bug_tracker.model.IssueStatus;
import system_bug_tracker.repository.SystemBugTrackerRepository;
import system_bug_tracker.utils.Helper;

@Component
public class SystemBugTrackerFacade {
  private SystemBugTrackerRepository repository;
  private Helper helper;
  
  public SystemBugTrackerFacade(SystemBugTrackerRepository repository, Helper helper) {
    this.repository = repository;
    this.helper = helper;
  }

  public String createBug(String parentId, String description, String logLink) {
    Issue issue = repository.save(new Issue(parentId, description, logLink));                        
    return issue.getId();
  }

  public String closeBug(String id) {
    Long convertedId = helper.convertId(id);
    Optional<Issue> optionalIssue = repository.findById(convertedId);
    if (optionalIssue.isPresent()) {
      Issue issue = optionalIssue.get();
      issue.setStatus(IssueStatus.CLOSED);
      repository.save(issue);
      return "Issue with id: " + convertedId + " has been closed.";
    } else {
      return "Issue with id: " + convertedId + " was not found.";
    }
  }

  public void listBugs() {
    List<Issue> issues = repository.findAll();
    helper.printIssuesTable(issues);
  }
}
