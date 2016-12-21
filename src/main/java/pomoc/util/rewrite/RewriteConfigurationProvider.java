package pomoc.util.rewrite;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

@RewriteConfiguration
public class RewriteConfigurationProvider extends HttpConfigurationProvider {

	static int i=0;
	@Override
	public Configuration getConfiguration(ServletContext arg0) {
//		LocaleTransposition bundle = LocaleTransposition.bundle("pomoc.locale.locale.Paths", "lang");
		return ConfigurationBuilder.begin()
				.addRule(Join.path("/").to("/support/signin2.jsf").withInboundCorrection())
				.addRule(Join.path("/dashboard").to("/support/dashboard2.jsf").withInboundCorrection())
				.addRule(Join.path("/my-tickets").to("/support/myTickets.jsf").withInboundCorrection())
				.addRule(Join.path("/preferences").to("/support/administration/preferences.jsf").withInboundCorrection())
				.addRule(Join.path("/profile").to("/support/myprofile.jsf").withInboundCorrection())
				.addRule(Join.path("/admin/forms").to("/support/administration/forms.jsf").withInboundCorrection())
				.addRule(Join.path("/admin/users").to("/support/administration/users.jsf").withInboundCorrection())
				
				
				.addRule(Join.path("/podsumowanie").to("/dashboard").withInboundCorrection().withChaining())
//			       .addRule()
//			         .when(Direction.isInbound().and(Path.matches("/some/{page}/")))
//			         .perform(Forward.to("/new-{page}/"))
//			   .addRule(Join.path("/{path}").to("/support/{path}.jsf"))
//               .where("path").transposedBy(bundle)
//	               .addRule()
//	               .when(Path.matches("/{lang}/{path}").withRequestBinding())
//	               .perform(Forward.to("/{path}.jsf"))
//	               .where("path").transposedBy(LocaleTransposition.bundle("pomoc.locale.locale.Paths", "lang"))

				;
	}

	@Override
	public int priority() {
		return 10;
	}

}
