package pomoc.partner.admin.users;

import javax.enterprise.inject.Model;

import org.ocpsoft.rewrite.annotation.Join;
import org.ocpsoft.rewrite.annotation.Rule;

@Model
@Rule("newuser")
@Join(path = "/admin/newuser", to = "/support/administration/useredit.jsf")
public class NewUserBean {

}
