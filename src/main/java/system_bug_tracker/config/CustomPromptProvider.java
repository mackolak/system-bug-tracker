package system_bug_tracker.config;

import org.jline.utils.AttributedString;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

@Component 
public class CustomPromptProvider implements PromptProvider {    
  @Override    
  public AttributedString getPrompt() {    
            return new AttributedString("system-bug-tracker" + " => ");    
	} 
}  