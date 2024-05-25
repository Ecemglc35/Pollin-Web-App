package ca.sheridancollege.gulec.controllers;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.gulec.beans.Poll;
import ca.sheridancollege.gulec.database.DatabaseAccess;

/**
 * Home Controller Class provides the connection between model and view
 * 
 *  @author EcemGulec
 *  Student Number: 991692324
 *  Date:November 26, 2023
 */
@Controller
public class HomeController {

	@Autowired
	private DatabaseAccess db;

	/**
	 * 
	 * Method to direct the "/" request to secure/pollSelection page
	 * 
	 * @param authentication
	 * @param model
	 * @return /secure/pollSelection.html
	 */
	@GetMapping("/")
	public String secureIndex(Authentication authentication, Model model) {

		model.addAttribute("pollList", db.getAllPolls());
		
		//try-catch to handle NullPointerException
		try {
		model.addAttribute("fullName", db.findUserAccount(authentication.getName()).getFullName());
		}catch(NullPointerException ex) {
			System.out.println("Full name cannot find.");
		}
		
		return "/secure/pollSelection";
	}

	/**
	 * fetches the poll chosen by the user from the database and adds it to the
	 * model and forwards it to the html page.
	 * 
	 * @param the   unique identifier for poll object
	 * @param model for selected poll
	 * @return votingPage.html page
	 */
	@PostMapping("/secure/votingPage")
	public String goVote(@RequestParam Long pollID, Model model) {

		model.addAttribute("poll", db.getPollByID(pollID).get(0));

		return "/secure/votingPage";
	}

	/**
	 * Shows the results and percentage distributions according to the answers for
	 * the poll chosen by the user
	 * 
	 * @param selectedUser's chosen answer
	 * @param the            unique identifier for poll object
	 * @param model          to add percentage of votes and poll chosen
	 * @return /secure/resultPage.html
	 */
	@PostMapping("/secure/resultPage/{pollID}")
	public String goResultPage(@RequestParam String selected, @PathVariable Long pollID, Model model) {

		Poll poll = db.getPollByID(pollID).get(0);

		if (poll.getAnswer1().equalsIgnoreCase(selected)) {
			poll.setVotes1(poll.getVotes1() + 1);
		} else if (poll.getAnswer2().equalsIgnoreCase(selected)) {
			poll.setVotes2(poll.getVotes2() + 1);
		} else if (poll.getAnswer3().equalsIgnoreCase(selected)) {
			poll.setVotes3(poll.getVotes3() + 1);
		} else {
			System.out.println("Error: There is no selected answer.");
		}

		poll.setDate(LocalDate.now());

		db.updatePollVotesAndDate(poll);

		model.addAttribute("selectedPoll", db.getPollByID(pollID).get(0));
		model.addAttribute("percentVotes1", poll.calculatePercentageForVotes1());
		model.addAttribute("percentVotes2", poll.calculatePercentageForVotes2());
		model.addAttribute("percentVotes3", poll.calculatePercentageForVotes3());

		return "/secure/resultPage";
	}

	/**
	 * Method to handle get request for delete poll
	 * 
	 * @param model
	 * @return /secure/deletePoll.html
	 */
	@GetMapping("/secure/deletePoll")
	public String goDeletePoll(Model model) {

		model.addAttribute("pollList", db.getAllPolls());

		return "/secure/deletePoll";
	}

	/**
	 * Method to delete a poll which manager wants
	 * 
	 * @param pollID
	 * @return redirect:/
	 */
	@PostMapping("/secure/deletePoll")
	public String deletePoll(@RequestParam Long pollID) {
		db.deletePoll(pollID);
		return "redirect:/";
	}

	/**
	 * Method to handle http get request to add poll
	 * 
	 * @return /secure/addPoll.html
	 */
	@GetMapping("/secure/addPoll")
	public String goAddPoll() {
		return "/secure/addPoll";
	}

	/**
	 * 
	 * Method to add a poll by manager
	 * 
	 * @param title
	 * @param question
	 * @param answer1
	 * @param answer2
	 * @param answer3
	 * @return redirect:/
	 */
    @PostMapping("/secure/addPoll")
    public void addPoll(@RequestParam String title, @RequestParam String question,
                          @RequestParam String answer1, @RequestParam String answer2,
                          @RequestParam String answer3, Model model) {
    	System.out.println(title);
		db.addPoll(title, question, answer1, answer2, answer3);
		model.addAttribute("message", "Poll Added.");
	
	}

	/**
	 * Method to handle add poll action
	 * 
	 * @param model
	 * @return /secure/allPollResult.html
	 */
	@GetMapping("/secure/allPollResult")
	public String allPollResult(Model model) {
		model.addAttribute("pollList", db.getAllPolls());
		return "/secure/allPollResult";
	}

	/**
	 * Method to register a user
	 * 
	 * @param user name(email)
	 * @param fullName
	 * @param password
	 * @return redirect:/
	 */
	@PostMapping("/register")
	public String postRegister(@RequestParam String username, @RequestParam String fullName,
			@RequestParam String password) {
		db.addUser(username, fullName, password);

		Long userId = db.findUserAccount(username).getUserID();

		db.addRole(userId, db.findRoleId("ROLE_USER"));

		return "redirect:/";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}

	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}



}
