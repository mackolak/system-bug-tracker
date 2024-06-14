package system_bug_tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import system_bug_tracker.model.Issue;

@Repository
public interface SystemBugTrackerRepository extends JpaRepository<Issue, Long> {

}