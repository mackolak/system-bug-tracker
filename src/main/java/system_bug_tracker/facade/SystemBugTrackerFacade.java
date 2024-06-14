package system_bug_tracker.facade;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import system_bug_tracker.model.Issue;
import system_bug_tracker.model.IssueStatus;
import system_bug_tracker.repository.SystemBugTrackerRepository;

@Component
public class SystemBugTrackerFacade {
  private SystemBugTrackerRepository repository;
  
  public SystemBugTrackerFacade(SystemBugTrackerRepository repository) {
    this.repository = repository;
  }

  public String createBug(String parentId, String description, String logLink) {
    Issue issue = repository.save(new Issue(parentId, description, logLink));                        
    return issue.getId();
  }

  public String closeBug(Long id) {
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

  public String listBugs() {
    List<Issue> issues = repository.findAll();
    if (!issues.isEmpty()) {
      return issues.toString();
    } else {
      return "No issues found";
    }
  }
}
