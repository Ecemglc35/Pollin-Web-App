package ca.sheridancollege.gulec.beans;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Poll class to keep poll info
 * 
 *@author EcemGulec
 */

@Data
@Component
@NoArgsConstructor
@AllArgsConstructor
public class Poll {
	private Long pollID;
	private String title;
	private String question;
	private String answer1;
	private String answer2;
	private String answer3;
	private int votes1;
	private int votes2;
	private int votes3;
	private LocalDate date;
	
	
	/**
	 * Calculate vote percentage
	 * 
	 * @return integer percentage for votes1
	 */
	public int calculatePercentageForVotes1() {
		
		double total = (votes1+votes2+votes3);
		
		return (int)(votes1*100/total);
	}
	
	/**
	 * Calculate vote percentage
	 * 
	 * @return integer percentage for votes2
	 */
	public int calculatePercentageForVotes2() {
		
		double total = (votes1+votes2+votes3);
		
		return (int)(votes2*100/total);
	}
	
	/**
	 * Calculate vote percentage
	 * 
	 * @return integer percentage for votes3
	 */
	public int calculatePercentageForVotes3() {
		
		double total = (votes1+votes2+votes3);
		
		return (int)(votes3*100/total);
	}
}
