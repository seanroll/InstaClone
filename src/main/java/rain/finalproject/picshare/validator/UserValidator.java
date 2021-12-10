package rain.finalproject.picshare.validator;

import rain.finalproject.picshare.model.User;
import rain.finalproject.picshare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.*;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> OtherClass) {
		return User.class.equals(OtherClass);
	}

	@Override
	public void validate(Object Obj, Errors Err) {
		User UserToValidate = (User)Obj;

		ValidationUtils.rejectIfEmptyOrWhitespace(Err, "username", "NotEmpty");
		if (UserToValidate.getUsername().length() < 6 || UserToValidate.getUsername().length() > 32) {
			Err.rejectValue("username", "Size.userForm.username");
		}
		if (userService.findByUsername(UserToValidate.getUsername()) != null) {
			Err.rejectValue("username", "Duplicate.userForm.username");
		}

		ValidationUtils.rejectIfEmptyOrWhitespace(Err, "password", "NotEmpty");
		if (UserToValidate.getPassword().length() < 8 || UserToValidate.getPassword().length() > 32) {
			Err.rejectValue("password", "Size.userForm.password");
		}

		if (!UserToValidate.getPasswordConfirm().equals(UserToValidate.getPassword())) {
			Err.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
		}
	}
}
