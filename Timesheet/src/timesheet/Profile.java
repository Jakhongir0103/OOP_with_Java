package timesheet;

public class Profile {

	private int[] workHours;

	public Profile(int[] workHours) {
		this.workHours = workHours;
	}
	
	public int getWorkHours(int weekDay) {
		return workHours[weekDay];
	}

	@Override
	public String toString() {
		return String.format("Sun: %d; Mon: %d; Tue: %d; Wed: %d; Thu: %d; Fri: %d; Sat: %d", workHours[0],
				workHours[1], workHours[2], workHours[3], workHours[4], workHours[5], workHours[6]);
	}

}
