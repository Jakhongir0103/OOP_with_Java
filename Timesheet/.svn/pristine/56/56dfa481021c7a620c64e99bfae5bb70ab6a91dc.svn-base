package timesheet;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Timesheet {

	private int firstWeekDay = 1;
	private Set<Integer> holidaySet = new HashSet<>();
	private Map<String, Project> projects = new HashMap<>();
	private Map<String, Profile> profiles = new HashMap<>();
	private Map<String, Worker> workers = new HashMap<>();
	private List<Report> reports = new LinkedList<>();
	private int nextId=0;

// R1
	/**
	 * set the holidays in the calendar. 
	 * Holidays are indicated by an array of integers representing the days in the year. 
	 * That is, 1 is January, 1st; and 365* is December, 31st. 
	 * Leap years are not considered. 
	 * Numbers less than **1** should be ignored. 
	 * The same day may be specified multiple times. 
	 * After holidays have been set, subsequent calls to the method will be ignored.
	 * 
	 * @param holidays holidays days
	 */
	public void setHolidays(int... holidays) {
		if (!holidaySet.isEmpty()) { // can only be defined once!
			return;
		}
		
		for (int holiday : holidays) {
			if (holiday > 0) {
				holidaySet.add(holiday);
			}
		}
	}
	
	/**
	 * Check if a given day is an holiday
	 * 
	 * @param day day to be checked
	 * @return true if day is holiday
	 */
	public boolean isHoliday(int day) {
		return holidaySet.contains(day);
	}
	
	/**
	 * define the day of the week of January, 1st. 
	 * 
	 * The number 0 stands for Sunday, 1 for Monday, etc. 
	 * If the parameter is less than 0 or greater than 6, the method throws the exception
	 * 
	 * @param weekDay day of the week of Jan 1
	 * @throws TimesheetException if the parameter is less than 0 or greater than 6
	 */
	public void setFirstWeekDay(int weekDay) throws TimesheetException {
		if (weekDay < 0 || weekDay > 6) {
			throw new TimesheetException();
		}
		
		firstWeekDay = weekDay;
	}
	
	/**
	 * returns the day of the week (**0-6**) corresponding to the given day of the year (1-365).
	 * 
	 * @param day day in the year
	 * @return day of the week
	 * @throws TimesheetException
	 */
	public int getWeekDay(int day) throws TimesheetException {
		if (day <= 0) {
			throw new TimesheetException();
		}
		
		return (firstWeekDay + day - 1) % 7;
	}
	
	// R2
	/**
	 * Creates a new project with a maximum number of allocated hours. Project names are unique.
	 * 
	 * It is possible to modify the maximum number of hours allocated to a specific project 
	 * by calling the method multiple times.
	 * 
	 * @param projectName name of the project
	 * @param maxHours	  maximum number of hours
	 * @throws TimesheetException if maximum number of hours is negative
	 */
	public void createProject(String projectName, int maxHours) throws TimesheetException {
		if (maxHours < 0) {
			throw new TimesheetException();
		}
		
		if (projects.containsKey(projectName)) {
			Project project = projects.get(projectName);
			project.setMaxHours(maxHours);
		} else {
			projects.put(projectName, new Project(projectName, maxHours));
		}
	}
	
	/**
	 * returns all known projects
	 * 
	 * The format of the description strings in the list is: "{projectName}: {maxHours}".
	 * 
	 * Projects are sorted in descending order by the maximum number of allocated hours,
	 * then alphabetically by project name.
	 * 
	 * @return list of project descriptions
	 */
	public List<String> getProjects() {
		return projects.values().stream()
				.sorted()
				.map(Project::toString)
				.collect(Collectors.toList());
	}
	
	/**
	 * creates a new activity linked to a given project. 
	 * 
	 * Activity names inside the same project can be assumed to be unique.
	 *  
	 * @param projectName	name of the container project
	 * @param activityName	name of the new activity
	 * @throws TimesheetException if the project has not been previously defined
	 */
	public void createActivity(String projectName, String activityName) throws TimesheetException {
		if (!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}

		projects.get(projectName).addActivity(activityName);
	}
	
	/**
	 * marks as completed the given activity inside the given project
	 * 
	 * @param projectName	name of the container project
	 * @param activityName	name of the activity
	 * @throws TimesheetException if the project has not been previously defined, or the activity does not exists in the project
	 */
	public void closeActivity(String projectName, String activityName) throws TimesheetException {
		if (!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}
		
		Project project = projects.get(projectName);
		
		if (!project.hasActivity(activityName)) {
			throw new TimesheetException();
		}
		
		project.getActivity(activityName).close();
	}
	
	/**
	 * returns the activities of the given project that have not yet been 
	 * marked as completed. Activities in the list are sorted alphabetically.
	 * 
	 * @param projectName name of the project
	 * @return list of activity names
	 * @throws TimesheetException if the project has not been previously defined
	 */
	public List<String> getOpenActivities(String projectName) throws TimesheetException {
		if (!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}

		return projects.get(projectName).getActivities().stream()
				.filter(x -> !x.isClosed())
				.sorted()
				.map(Activity::toString)
				.collect(Collectors.toList());
	}

// R3
	/**
	 * Creates a new hourly profile, that is, the maximum number of hours that one may 
	 * work in each day of the week. The array of integers must contain exactly 7 values, 
	 * one for each day of the week, with the first referring to Sunday and the last to Saturday.
	 * 
	 * The method returns a string with the random unique identifier associated to the profile
	 * 
	 * @param workHours profile
	 * @return  profile id
	 * @throws TimesheetException if profile does not contain seven values
	 */
	public String createProfile(int... workHours) throws TimesheetException {
		if (workHours.length != 7) {
			throw new TimesheetException();
		}

//		String uuid = UUID.randomUUID().toString();
		// OR
		String uuid = "Profile"+(nextId++);
		profiles.put(uuid, new Profile(workHours));
		return uuid;
	}
	
	/**
	 * returns a hourly profile given its unique identifier as a string. 
	 * 
	 * The format follows this example: {@code "Sun: 0; Mon: 8; Tue: 8; Wed: 8; Thu: 8; Fri: 8; Sat: 0"}
	 * 
	 * @param profileID id of the profile
	 * @return profile description
	 * @throws TimesheetException if id has not been previously created
	 */
	public String getProfile(String profileID) throws TimesheetException {
		if (!profiles.containsKey(profileID)) {
			throw new TimesheetException();
		}

		return profiles.get(profileID).toString();
	}
	
	/**
	 * creates a worker with a definite hourly profile. 
	 * The method returns a string with the random unique identifier associated to the worker
	 * 
	 * @param name		worker first name
	 * @param surname	worker last name
	 * @param profileID profile id
	 * @return unique id of worker
	 * @throws TimesheetException If the hourly profile `profileID` has not been previously created
	 */
	public String createWorker(String name, String surname, String profileID) throws TimesheetException {
		if (!profiles.containsKey(profileID)) {
			throw new TimesheetException();
		}

		Profile profile = profiles.get(profileID);
//		String uuid = UUID.randomUUID().toString();
		// OR
		String uuid = "Worker"+(nextId++);
		workers.put(uuid, new Worker(uuid, name, surname, profile));
		return uuid;
	}
	
	/**
	 * returns a string describing a worker given the worker's unique identifier. 
	 * 
	 * The string is in the format {@code "[name] [surname] ([profileString])"}, 
	 * where {@code "[profileString]"} is the format used by {@link #getWorkerProfile}
	 * 
	 * @param workerID	worker id
	 * @return worker description
	 * @throws TimesheetException if the worker `workerID` has not been previously created
	 */
	public String getWorker(String workerID) throws TimesheetException {
		if (!workers.containsKey(workerID)) {
			throw new TimesheetException();
		}

		return workers.get(workerID).toString();
	}
	
// R4
	/**
	 * adds a new entry to the timesheet of a given worker
	 * 
	 * Conditions required:
	 * <ul>
	 * <li> the worker identifier should exist;
	 * <li> the day should be greater than **0** and less than **366**, and not a holiday;
	 * <li> the number of hours should not be negative;
	 * <li> the number of hours should be compatible with the hourly profile of the worker;
	 * <li> the project should exist;
	 * <li> the activity should exist inside the project;
	 * <li> the total number of hours allocated to the project should be less than its maximum number of hours;
	 * <li> The activity should not be completed.
	 * </ul>
	 * 
	 * @param workerID		id of the worker
	 * @param projectName	id of the project
	 * @param activityName	id of the activity
	 * @param day			day of the year
	 * @param workedHours	amount of hours
	 * @throws TimesheetException if any condition is not met
	 */
	public void addReport(String workerID, String projectName, String activityName, int day, int workedHours) throws TimesheetException {
		if (day <= 0) {
			throw new TimesheetException("Invalid day number");
		}
		
		if (isHoliday(day)) {
			throw new TimesheetException("Cannot work on holiday");
		}
		
		if (workedHours < 0) {
			throw new TimesheetException("Negative worked hours");
		}

		if (!workers.containsKey(workerID)) {
			throw new TimesheetException("Invalid worker");
		}
		
		Worker worker = workers.get(workerID);
		Profile profile = worker.getProfile();
		
		int weekDay = getWeekDay(day);
		int maxHoursPerDay = profile.getWorkHours(weekDay);
		int oldWorkedHours = getWorkedHoursPerDay(worker, day);
		
		if (oldWorkedHours + workedHours > maxHoursPerDay) {
			throw new TimesheetException("Cannot work more than " + maxHoursPerDay + " hours");
		}
		
		if (!projects.containsKey(projectName)) {
			throw new TimesheetException("Invalid project");
		}
		
		Project project = projects.get(projectName);
		
		int maxHoursPerProject = project.getMaxHours();
		int oldProjectHours = getProjectHours(project);
		
		if (oldProjectHours + workedHours > maxHoursPerProject) {
			throw new TimesheetException("Too many hours on the project");
		}
		
		if (!project.hasActivity(activityName)) {
			throw new TimesheetException("Invalid activity");
		}
		
		Activity activity = project.getActivity(activityName);
		
		if (activity.isClosed()) {
			throw new TimesheetException("Activity is closed");
		}
	
		reports.add(new Report(worker, activity, day, workedHours));
	}
	
	/**
	 * returns the total number of reported hours associated to the given project
	 * 
	 * @param projectName
	 * @return
	 * @throws TimesheetException
	 */
	public int getProjectHours(String projectName) throws TimesheetException {
		if (!projects.containsKey(projectName)) {
			throw new TimesheetException();
		}
		
		Project project = projects.get(projectName);
		
		return getProjectHours(project);
	}
	
	/**
	 * returns the total number of reported hours by the given worker in the given day of the year
	 * 
	 * @param workerID	id of the worker
	 * @param day		day in the year
	 * @return
	 * @throws TimesheetException
	 */
	public int getWorkedHoursPerDay(String workerID, int day) throws TimesheetException {
		if (day <= 0) {
			throw new TimesheetException();
		}
		
		if (!workers.containsKey(workerID)) {
			throw new TimesheetException();
		}
		
		Worker worker = workers.get(workerID);
		
		return getWorkedHoursPerDay(worker, day);
	}

	private int getProjectHours(Project project) {
		return reports.stream()
				.filter(x -> x.getActivity().getProject().equals(project))
				.collect(Collectors.summingInt(Report::getWorkedHours));
	}

	private int getWorkedHoursPerDay(Worker worker, int day) {
		return reports.stream()
				.filter(x -> x.getWorker().equals(worker))
				.filter(x -> x.getDay() == day)
				.collect(Collectors.summingInt(Report::getWorkedHours));
	}
	
// R5
	/**
	 * returns a map that associates each worker to the number of their distinct activities, 
	 * that is, the activities where there is at least **1** hour reported.
	 * 
	 * Note that activities name are unique only inside a project: 
	 * activities in different projects are be considered distinct even if they have the same name.
	 * 
	 * @return worker to number of distinct activities
	 */
	public Map<String, Integer> countActivitiesPerWorker() {
		return reports.stream()
				.collect(Collectors.groupingBy(
						x -> x.getWorker().getWorkerID(),
						Collectors.collectingAndThen(
								Collectors.mapping(Report::getActivity, Collectors.toSet()),
								x -> x.size())));
	}
	
	/**
	 * returns a map that associates each project to the number of hours still available, 
	 * that is, the difference between the maximum number of hours of the project and 
	 * the sum of the reported ones.
	 * 
	 * @return map project to remaining hours
	 */
	public Map<String, Integer> getRemainingHoursPerProject() {
		return reports.stream()
				.collect(Collectors.groupingBy(
						Report::getProject,
						Collectors.summingInt(Report::getWorkedHours)))
				.entrySet().stream()
				.collect(Collectors.groupingBy(
						x -> x.getKey().getName(),
						Collectors.mapping(x -> x.getKey().getMaxHours() - x.getValue(),
								Collectors.summingInt(x -> x))
						));
	}
	
	/**
	 * returns a map that associates each project to another `Map`. 
	 * This second collection is mapping each activity of the project to the total number of hours currently reported.
	 * 
	 * @return maps projects to report of hours per activity
	 */
	public Map<String, Map<String, Integer>> getHoursPerActivityPerProject() {
		return reports.stream()
				.collect(Collectors.groupingBy(
						x -> x.getProject().getName(),
						Collectors.groupingBy(
								x -> x.getActivity().getName(),
								Collectors.summingInt(Report::getWorkedHours))));
	}
}
