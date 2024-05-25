package ca.sheridancollege.gulec.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * User class to create user beans
 * 
 * @author EcemGulec
 */
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

	private Long userID;
	@NonNull
	private String email;
	@NonNull
	private String fullName;
	@NonNull
	private String encryptedPassword;
	@NonNull
	private Boolean enabled;
}
