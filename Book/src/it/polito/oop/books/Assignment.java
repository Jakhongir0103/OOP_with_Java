package it.polito.oop.books;

import java.util.List;


public class Assignment {

	private String ID;
	private ExerciseChapter chapter;
	private double totalScore = 0;
	
	public Assignment(String iD, ExerciseChapter chapter) {
		ID = iD;
		this.chapter = chapter;
	}

	public String getID() {
        return ID;
    }

    public ExerciseChapter getChapter() {
        return chapter;
    }

    public double addResponse(Question q, List<String> answers) {
        int FP = 0;
        int FN = 0;
    	for(String a: answers) {
        	if(q.getIncorrectAnswers().contains(a)) {
        		FP ++;
        	}
        }
    	for(String a: q.getCorrectAnswers()) {
        	if(!answers.contains(a)) {
        		FN ++;
        	}
        }
    	long N = q.numAnswers();
    	
    	double score = (double) (N - FP - FN) / N;
    	totalScore += score;
    	return score;
    }
    
    public double totalScore() {
        return totalScore;
    }

}
