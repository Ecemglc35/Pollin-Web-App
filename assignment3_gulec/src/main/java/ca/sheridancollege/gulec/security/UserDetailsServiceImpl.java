package ca.sheridancollege.gulec.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ca.sheridancollege.gulec.database.DatabaseAccess;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	@Lazy
	private DatabaseAccess da;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ca.sheridancollege.gulec.beans.User user = da.findUserAccount(username);

		// If the user doesn't exist, throw an exception
		if (user == null) {
			System.out.println("User not found:" + username);
			throw new UsernameNotFoundException("User " + username + " does not exist in the database");
		}

		// Get a list of roles for that user
		List<String> roleNameList = da.getRolesById(user.getUserID());

		// Change the list of the user's roles into a list of GrantedAuthority
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
		if (roleNameList != null) {
			for (String role : roleNameList) {
				grantList.add(new SimpleGrantedAuthority(role));
			}
		}

		// Create a user based on the information above
		UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(user.getEmail(),
				user.getEncryptedPassword(), grantList);
		return userDetails;

	}

}
