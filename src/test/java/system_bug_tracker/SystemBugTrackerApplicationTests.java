package system_bug_tracker;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellTestClient;
import org.springframework.shell.test.ShellTestClient.InteractiveShellSession;
import org.springframework.shell.test.autoconfigure.ShellTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import system_bug_tracker.model.Issue;
import system_bug_tracker.repository.SystemBugTrackerRepository;
import system_bug_tracker.utils.TestHelper;

import static org.awaitility.Awaitility.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@ShellTest(terminalWidth = 200, terminalHeight = 10)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@ComponentScan(basePackageClasses = SystemBugTrackerApplication.class)
class SystemBugTrackerApplicationTests {
	@Autowired
	private ShellTestClient client;
  @Autowired
  private TestHelper helper;
	@MockBean
	private SystemBugTrackerRepository repository;
  private InteractiveShellSession session;

  @AfterEach
  void resetMock() {
    reset(repository);
  }

  @BeforeEach
  void setUp() {
    session = client.interactive().run();
  }

	@Test
	void testCreateBug() throws InterruptedException {
    when(repository.save(Mockito.any(Issue.class)))
      .thenAnswer(invocation -> {
          Issue issue = invocation.getArgument(0);
          issue.setId(1l);
          issue.setCreationTimestamp(LocalDateTime.now());
          return issue;
      });

    helper.isShellReady(session);

    helper.sendShellCommand("create --parent-id I-289 --desc \"issue description\" "
      + "--log-link \"yahoo.com\"", session);

		await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(session.screen())
				.containsText("I-1");
		});
	}

  @Test
  void testCloseBug() {
    Issue issue = new Issue("I-234", "issue description", "gmail.com");
    issue.setId(1l);

    when(repository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(issue));
    when(repository.save(Mockito.any(Issue.class))).thenReturn(issue);
    
    helper.isShellReady(session);
    helper.sendShellCommand("close --id I-1", session);

		await().atMost(4, TimeUnit.SECONDS).untilAsserted(() -> {
			ShellAssertions.assertThat(session.screen())
				.containsText("Issue with id: I-1 has been closed.");
		});

    assertEquals("Closed", issue.getStatus());
  }

  @Test
  void testListBugs() {
    helper.isShellReady(session);

    Issue issue1 = new Issue("I-234", "issue description", "gmail.com");
    issue1.setId(1l);
    issue1.setCreationTimestamp(LocalDateTime.parse("2024-06-16T00:00:00"));
    Issue issue2 = new Issue("I-763", "Another issue description", "yahoo.com");
    issue2.setId(2l);
    issue2.setCreationTimestamp(LocalDateTime.parse("2024-06-16T00:50:00"));

    List<Issue> issues = List.of(issue1, issue2);

    when(repository.findAll()).thenReturn(issues);

    helper.sendShellCommand("list", session);

    await().atMost(4, TimeUnit.SECONDS).untilAsserted(() -> {
      ShellAssertions.assertThat(session.screen())
        .containsText("I-1")
        .containsText("issue description")
        .containsText("I-234")
        .containsText("Open")
        .containsText("2024-06-16T00:00")
        .containsText("gmail.com");

      ShellAssertions.assertThat(session.screen())
        .containsText("I-2")
        .containsText("Another issue description")
        .containsText("I-763")
        .containsText("Open")
        .containsText("2024-06-16T00:50")
        .containsText("yahoo.com");
		});
  }

}
