package timesheet;

public class Activity implements Comparable<Activity> {
	
	private String name;
	private Project project;
	private boolean closed;
	
	public Activity(String name, Project project) {
		this.name = name;
		this.project = project;
		this.closed = false;
	}

	public String getName() {
		return name;
	}

	public Project getProject() {
		return project;
	}

	public void close() {
		closed = true;
	}

	public boolean isClosed() {
		return closed;
	}

	@Override
	public int compareTo(Activity other) {
		return this.getName().compareTo(other.getName());
	}

	@Override
	public String toString() {
		return name;
	}

}
