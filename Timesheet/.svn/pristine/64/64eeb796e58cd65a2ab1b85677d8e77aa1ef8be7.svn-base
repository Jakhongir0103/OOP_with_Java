package timesheet;


public class Worker {
	
	private String workerID;
	private String name;
	private String surname;
	private Profile profile;
	
	public Worker(String uuid, String name, String surname, Profile profile) {
		this.workerID = uuid;
		this.name = name;
		this.surname = surname;
		this.profile = profile;
	}
	
	public String getWorkerID() {
		return workerID;
	}
	
	public Profile getProfile() {
		return profile;
	}

	@Override
	public String toString() {
		return name + " " + surname + " (" + profile.toString() + ")";
	}

}
