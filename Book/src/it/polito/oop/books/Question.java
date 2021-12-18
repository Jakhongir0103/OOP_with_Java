package it.polito.oop.books;

import java.util.LinkedHashSet;
import java.util.Set;

public class Question {
	
	private String question;
	private Topic mainTopic;
	private Set<String> correctAnswers = new LinkedHashSet<>(); 
	private Set<String> incorrectAnswers = new LinkedHashSet<>(); 
	
	public String getQuestion() {
		return question;
	}
	
	public Topic getMainTopic() {
		return mainTopic;
	}

	public Question(String question, Topic mainTopic) {
		super();
		this.question = question;
		this.mainTopic = mainTopic;
	}

	public void addAnswer(String answer, boolean correct) {
		if(correct) {
			this.correctAnswers.add(answer);
		}else {
			this.incorrectAnswers.add(answer);
		}
	}
	
    @Override
    public String toString() {
        return question + " (" + mainTopic + ")";
    }

	public long numAnswers() {
	    return correctAnswers.size() + incorrectAnswers.size();
	}

	public Set<String> getCorrectAnswers() {
		return correctAnswers;
	}

	public Set<String> getIncorrectAnswers() {
        return incorrectAnswers;
	}
}
