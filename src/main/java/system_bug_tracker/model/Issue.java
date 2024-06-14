package system_bug_tracker.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "issues")
@Data
@NoArgsConstructor
public class Issue {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;
  @Column(name = "description")
  private String description;
  @Column(name = "parent_id")
  private String parentId;
  @Column(name = "status")
  private IssueStatus status;
  @CreationTimestamp
  @Column(name = "creation_timestamp")
  private LocalDateTime creationTimestamp;
  @Column(name = "log_link")
  private String logLink;

  public Issue(String parentId, String description, String logLink) {
    this.parentId = parentId;
    this.description = description;
    this.logLink = logLink;
    this.status = IssueStatus.OPEN;
  }

  public String getId() {
    return "I-" + Long.toString(id);
  }

  public String getStatus() {
    return status.getStatus();
  }
}
