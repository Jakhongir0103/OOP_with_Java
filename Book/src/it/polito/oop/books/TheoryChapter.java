package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class TheoryChapter {

	private String title;
	private int numPages;
	private String text;
	private List<Topic> topic = new ArrayList<>();
	
	public String getText() {
		return text;
	}

    public void setText(String newText) {
    	this.text = newText;
    }


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
    
    public void addTopic(Topic topic) {
    	this.topic.add(topic);
    	this.topic.addAll(topic.getSubTopics());
    }
    
}
