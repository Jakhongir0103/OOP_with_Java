package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class ExerciseChapter {

	private String title;
	private int numPages;
	private List<Topic> topic = new ArrayList<>();
	private List<Question> questions = new ArrayList<>();

	public List<Topic> getTopics() {
        return topic.stream().distinct().sorted(Comparator.comparing(Topic::toString)).collect(Collectors.toList());
	}
	
    public String getTitle() {
        return title;
    }

    public void setTitle(String newTitle) {
		this.title = newTitle;
    }

    public int getNumPages() {
        return numPages;
    }
    
    public void setNumPages(int newPages) {
		this.numPages = newPages;
    }
    
	public void addQuestion(Question question) {
		questions.add(question);
		topic.add(question.getMainTopic());
	}	
}
