package system_bug_tracker.utils;

import java.util.concurrent.TimeUnit;

import org.springframework.shell.test.ShellAssertions;
import org.springframework.shell.test.ShellTestClient.InteractiveShellSession;
import org.springframework.stereotype.Component;

import static org.awaitility.Awaitility.*;

@Component
public class TestHelper {
  public void isShellReady(InteractiveShellSession session) {
      await().atMost(2, TimeUnit.SECONDS).untilAsserted(() -> {
        ShellAssertions.assertThat(session.screen()).containsText("system-bug-tracker");
      });
  }

  public void sendShellCommand(String command, InteractiveShellSession session) {
    session.write(
      session.writeSequence()
      .text(command)
      .carriageReturn()
      .build()
    ).run();
  }
}
