package pomoc.util.rewrite;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

@RewriteConfiguration
public class RewriteConfigurationProvider extends HttpConfigurationProvider {

	@Override
	public Configuration getConfiguration(ServletContext arg0) {
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/").to("/support/signin2.jsf"))
				.addRule(Join.path("/dashboard").to("/support/dashboard2.jsf"))
//			       .addRule()
//			         .when(Direction.isInbound().and(Path.matches("/some/{page}/")))
//			         .perform(Forward.to("/new-{page}/"))
				;
	}

	@Override
	public int priority() {
		return 10;
	}

}
