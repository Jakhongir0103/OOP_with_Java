package it.polito.oop.books;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Book {

    /**
	 * Creates a new topic, if it does not exist yet, or returns a reference to the
	 * corresponding topic.
	 * 
	 * @param keyword the unique keyword of the topic
	 * @return the {@link Topic} associated to the keyword
	 * @throws BookException
	 */
	private Map<String, Topic> topics = new HashMap<>();
	public Topic getTopic(String keyword) throws BookException {
		if(keyword.isBlank() || keyword.isEmpty()) {
			throw new BookException();
		}
		
		if(topics.containsKey(keyword)) {
			return topics.get(keyword);
		}

		topics.put(keyword, new Topic(keyword));
		return topics.get(keyword);
	}

	private List<Question> questions = new ArrayList<>();
	public Question createQuestion(String question, Topic mainTopic) {
		Question ques = new Question(question, mainTopic);
		questions.add(ques);
        return ques;
	}

	private List<TheoryChapter> thchap = new ArrayList<>();
	public TheoryChapter createTheoryChapter(String title, int numPages, String text) {
		TheoryChapter ch = new TheoryChapter();
		ch.setTitle(title);
		ch.setNumPages(numPages);
		ch.setText(text);
		thchap.add(ch);
		
        return ch;
	}

	private List<ExerciseChapter> exchap = new ArrayList<>();
	public ExerciseChapter createExerciseChapter(String title, int numPages) {
		ExerciseChapter ch = new ExerciseChapter();
		ch.setTitle(title);
		ch.setNumPages(numPages);
		exchap.add(ch);
		
        return ch;
	}

	public List<Topic> getAllTopics() {
		List<Topic> allTop = new ArrayList<>();;
		thchap.forEach(c -> {
			allTop.addAll(c.getTopics());
		});
		exchap.forEach(c -> {
			allTop.addAll(c.getTopics());
		});
        return allTop.stream().distinct().sorted(Comparator.comparing(Topic::toString)).collect(Collectors.toList());
	}

	public boolean checkTopics() {
		for(ExerciseChapter e: exchap) {
			if(!thchap.stream().filter(c -> c.getTopics().contains(e)).findFirst().isPresent()){
				return false;
			}
		}
        return true;
	}

	private List<Assignment> assignments = new ArrayList<>();;
	public Assignment newAssignment(String ID, ExerciseChapter chapter) {
		Assignment a = new Assignment(ID, chapter);
		assignments.add(a);
        return a;
	}
	
    /**
     * builds a map having as key the number of answers and 
     * as values the list of questions having that number of answers.
     * @return
     */
    public Map<Long,List<Question>> questionOptions(){
        return questions.stream().collect(Collectors.groupingBy(Question::numAnswers));
    }
}
