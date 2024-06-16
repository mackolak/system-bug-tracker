package system_bug_tracker.utils;

import java.time.temporal.ChronoUnit;
import java.util.List;

import org.jline.terminal.Terminal;
import org.springframework.stereotype.Component;

import system_bug_tracker.model.Issue;

@Component
public class Helper {
  private Terminal terminal;

  public Helper(Terminal terminal) {
    this.terminal = terminal;
  }

  public void printIssuesTable(List<Issue> issues) {
    String leftAlignFormat = "| %-8s | %-52s | %-8s | %-6s | %-19s | %-11s |%n";
    terminal.writer().println("| ID       | Description                                          |"
    + "ParentId  | Status | CreationTimestamp   | Link        |");
    terminal.writer().println("|----------|------------------------------------------------------|"
    + "----------|--------|---------------------|-------------|");
    issues.forEach((issue) -> {
      terminal.writer().format(leftAlignFormat, issue.getId(), 
                              issue.getDescription(), issue.getParentId(),
                              issue.getStatus(), 
                              issue.getCreationTimestamp().truncatedTo(ChronoUnit.SECONDS).toString(), 
                              issue.getLogLink());
    });
	  terminal.writer().flush();
  }

  public Long convertId(String id) {
    if (id.startsWith("I-")) {
      return Long.valueOf(id.substring(2));
    } else {
      return Long.valueOf(id);
    }
  }

  public void printMessageToTerminal(String message) {
    terminal.writer().println(message);
  }
}
