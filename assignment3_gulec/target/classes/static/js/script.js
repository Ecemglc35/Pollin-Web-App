// This method checks the password on html form 
function verify() {
	var password = document.forms['form']['password'].value;
	var pattern = "!@#$%^&*()-_+=:;><?/|,.";
	var specialCharacter = false;

// Checks whether there is a character suitable for the pattern in the entire password.
	if (password.length >= 5 || password.length <= 10) {
		for (let i = 0; i < password.length; i++) {
			for (let j = 0; j < pattern.length; j++) {
				if (password[i] == pattern[j]) {
					specialCharacter = true;
				}
			}
		}
		
	}
	//If there is no special char. Displays the message.
	if (specialCharacter == false){
		document.getElementById('error').innerHTML = "The password length must be between 5 and 10 characters and contain at least a special character."
		return specialCharacter;
	}
}