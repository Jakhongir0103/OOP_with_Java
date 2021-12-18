package timesheet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Project implements Comparable<Project> {
	
	private String name;
	private int maxHours;
	private Map<String, Activity> activities;
	
	public Project(String name, int maxHours) {
		this.name = name;
		this.maxHours = maxHours;
		activities = new HashMap<>();
	}
	
	public String getName() {
		return name;
	}
	
	public int getMaxHours() {
		return maxHours;
	}
	
	public void setMaxHours(int maxHours) {
		this.maxHours = maxHours;
	}
	
	public void addActivity(String activityName) {
		activities.put(activityName, new Activity(activityName, this));
	}
	
	public Activity getActivity(String activityName) {
		return activities.get(activityName);
	}
	
	public boolean hasActivity(String activityName) {
		return activities.containsKey(activityName);
	}

	public Collection<Activity> getActivities() {
		return activities.values();
	}

	@Override
	public int compareTo(Project other) {
		if (this.getMaxHours() == other.getMaxHours()) {
			return this.getName().compareTo(other.getName());
		}
		return other.getMaxHours() - this.getMaxHours();
	}

	@Override
	public String toString() {
		return name + ": " + maxHours;
	}

}
