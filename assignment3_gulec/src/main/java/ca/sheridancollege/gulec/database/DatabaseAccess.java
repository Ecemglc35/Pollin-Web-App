package ca.sheridancollege.gulec.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.gulec.beans.Poll;
import ca.sheridancollege.gulec.beans.User;

@Repository
public class DatabaseAccess {

	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	@Autowired
	private PasswordEncoder passwordEncoder;


	/**
	 * Method to get the account associated with a user name(email)
	 * 
	 * @param email
	 * @return user object 
	 */
	public User findUserAccount(String email) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		// provide email parameter to the query
		String query = "SELECT * FROM sec_user where email = :email";
		namedParameters.addValue("email", email);

		try {
			// return all matching users as a list
			return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<User>(User.class));
		} catch (EmptyResultDataAccessException erdae) {
			return null;
		}
	}

	/**
	 * Method to get the roles associated with a userId
	 * 
	 * @param userId
	 * @return String roleName
	 */
	public List<String> getRolesById(Long userId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		// provide the userid as a parameter to find all matching roles
		String query = "SELECT sec_role.roleName " + "FROM user_role, sec_role "
				+ "WHERE user_role.roleId = sec_role.roleId " + "AND userId = :userId";

		namedParameters.addValue("userId", userId);

		return jdbc.queryForList(query, namedParameters, String.class);
	}

	/**
	 * 
	 *  Method to add a user to the DB
	 * @param email
	 * @param fullName
	 * @param password
	 */
	public void addUser(String email, String fullName, String password) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		// Provide email/ password as parameters
		String query = "INSERT INTO sec_user " + "(email, fullName, encryptedPassword, enabled) "
				+ "VALUES (:email, :fullName, :encryptedPassword, 1)";
		namedParameters.addValue("email", email);
		namedParameters.addValue("fullName", fullName);
		namedParameters.addValue("encryptedPassword", passwordEncoder.encode(password));

		// Insert the user info(email/password) to sec_user table of the DB
		jdbc.update(query, namedParameters);
	}
	
	/**
	 * Method to add user with role
	 * 
	 * @param userId
	 * @param roleId
	 */
	public void addRole(Long userId, Long roleId) {
		MapSqlParameterSource namedParameters = new MapSqlParameterSource();

		//Provide parameters
		String query = "INSERT INTO user_role (userId, roleId) " + "VALUES (:userId, :roleId)";
		namedParameters.addValue("userId", userId);
		namedParameters.addValue("roleId", roleId);

		//Insert a row to user_role table to reflect the association 
		jdbc.update(query, namedParameters);
	}

	/**
	 * Method to get all Polls from DB
	 * 
	 * @return List of all poll object
	 */
	public List<Poll> getAllPolls() {
		// Used to specify parameters
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();

		// Specify query
		String query = "SELECT * FROM poll";

		// Return all poll list
		return jdbc.query(query, parameterSource, new BeanPropertyRowMapper<Poll>(Poll.class));
	}
	
	/**
	 * Get poll list according to pollID 
	 * 
	 * @param the unique identifier for the poll
	 * @return List of poll object
	 */
	public List<Poll> getPollByID(Long pollID){
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		String query = "SELECT * FROM poll WHERE pollID = :pollID";
		
		parameterSource.addValue("pollID", pollID);
		
		return jdbc.query(query, parameterSource, new BeanPropertyRowMapper<Poll>(Poll.class));
	}
	
	/**
	 * Update poll according to votes changes 
	 * 
	 * @param poll object 
	 * @return List of poll object
	 */
	public int updatePollVotesAndDate(Poll poll) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();

		
		String query = "UPDATE poll SET votes1 = :votes1, votes2 = :votes2, votes3 = :votes3, date = :date "
				+ "WHERE pollID = :pollID";
		
		parameterSource.addValue("votes1", poll.getVotes1())
			.addValue("votes2", poll.getVotes2())
			.addValue("votes3", poll.getVotes3())
			.addValue("date", poll.getDate())
			.addValue("pollID", poll.getPollID());
		
		return jdbc.update(query, parameterSource);
	}
	
	/**
	 * Method to add Poll to DB
	 * 
	 * @param title
	 * @param question
	 * @param answer1
	 * @param answer2
	 * @param answer3
	 */
	public void addPoll(String title, String question, String answer1, String answer2, String answer3) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		String query = "INSERT INTO poll (title, question, answer1, answer2, answer3)"
				+ " VALUES (:title, :question, :answer1, :answer2, :answer3)";
		parameterSource.addValue("title", title)
		.addValue("question", question)
		.addValue("answer1", answer1)
		.addValue("answer2", answer2)
		.addValue("answer3", answer3);
		
		jdbc.update(query, parameterSource);
	}
	
	/**
	 * Method to delete a specific poll
	 * 
	 * @param pollID
	 */
	public void deletePoll(Long pollID) {
MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		String query = "DELETE FROM poll WHERE pollID = :pollID";
		
		parameterSource.addValue("pollID", pollID);
		
		jdbc.update(query, parameterSource);
	}
	
	/**
	 * Method to find particular poll with poll id
	 * 
	 * @param roleName
	 * @return
	 */
	public Long findRoleId(String roleName) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		
		String query = "SELECT roleId FROM sec_role WHERE roleName = :roleName";
		
		parameterSource.addValue("roleName", roleName);
	
		return jdbc.queryForObject(query, parameterSource, Long.class);
	}
	

}
