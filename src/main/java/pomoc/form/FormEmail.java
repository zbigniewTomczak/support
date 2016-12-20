package pomoc.form;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("input-email")
public class FormEmail extends FormInputText {

}
