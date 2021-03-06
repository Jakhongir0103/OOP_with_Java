package it.polito.oop.production;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Facade class used to interact with the system.
 *
 */
public class Carmaker {

	/** unique code for diesel engine **/
	public static final int DIESEL = 0;
	/** unique code for gasoline engine **/
	public static final int GASOLINE = 1;
	/** unique code for gpl engine **/
	public static final int GPL = 2;
	/** unique code for electric engine **/
	public static final int ELECTRIC = 3;
	
	// **************** R1 ********************************* //

	/**
	 * Add a new model to the brand factory.
	 * 
	 * Models are uniquely identified by a code
	 * 
	 * @param code 	code
	 * @param name  name
	 * @param year	year of introduction in the market
	 * @param displacement  displacement of the engine in cc
	 * @param enginetype	the engine type (e.g., gasoline, diesel, electric)
	 * @return {@code false} if code is duplicate, 
	*/
	private Map<String, Car> cars = new HashMap<>();
	public boolean addModel(String code, String name,  int year, float displacement, int enginetype) {
		if(!cars.isEmpty() && cars.containsKey(code)) {
			return false;
		}
		cars.put(code, new Car(code, name, year, displacement, enginetype));
		return true;
	}
	
	/**
	 * Count the number of models produced by the brand
	 * 
	 * @return models count
	 */
	public int countModels() {
		return cars.size();
	}
	
	/**
	 * Retrieves information about a model.
	 * Information is formatted as code, name, year, displacement, enginetype
	 * separate by {@code ','} (comma).
	 * 	 
	 * @param code code of the searched model
	 * @return info about the model
	 */
	public String getModel(String code) {
		if(!cars.containsKey(code)) {
			return null;
		}
		return cars.get(code).getCode() + "," + cars.get(code).getName() + "," + cars.get(code).getYear() + "," + cars.get(code).getDisplacement() + "," + cars.get(code).getEnginetype();
	}
	
	
	/**
	 * Retrieves the list of codes of active models.
	 * Active models not older than 10 years with respect to the execution time.
	 * 	 
	 * @return a list of codes of the active models
	 */
	public List<String> getActiveModels() {
		return cars.values().stream().filter(c -> c.getYear() >= java.time.LocalDate.now().getYear() - 10)
						.collect(Collectors.mapping(Car::getCode, Collectors.toList()));
	}
	
	
	/**
	 * Loads a list of models from a file.
	 * @param Filename path of the file
	 * @throws IOException in case of IO problems
	 */
	public void loadFromFile(String Filename) throws IOException  {
		List<String> lines = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(new FileReader(Filename))) {
			 lines = in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			throw e;
		}
		
		for(String line:lines) {
			String[] l = line.split("\t");
			if(!this.addModel(l[0], l[1], Integer.parseInt(l[2]), Float.valueOf(l[3]), Integer.parseInt(l[4]))) {
				break;
			}
		}
	}
	
	// **************** R2 ********************************* //

	
	
	/**
	 * Creates a new factory given its name. Throws Brand Exception if the name of the factory already exists.
	 * 
	 * @param name the unique name of the factory.
	 * @throws BrandException
	 */
	private Map<String, Factory> factories = new HashMap<>();
	public void buildFactory(String name) throws BrandException {
		if(!factories.isEmpty() && factories.containsKey(name)) {
			throw new BrandException();
		}
		factories.put(name, new Factory(name));
	}
	
	
	
	/**
	 * Returns a list of the factory names. The list is empty if no factories are created.
	 * @return A list of factory names.
	 */
	public List<String> getFactories() {
		return factories.values().stream().collect(Collectors.mapping(Factory::getName, Collectors.toList()));
	}
	
	
	/**
	 * Create a set of production lines for a factory.
	 * Each production line is identified by name,capacity and type of engines it can handle.
	 * 
	 * @param fname The factory name
	 * @parm  line	An array of strings. Each string identifies a production line.
	 * 
	 * @throws BrandException if factory name is not defined or line specification is malformed
	 */
	public void setProductionLines (String fname, String... line) throws BrandException {
		if(this.factories.isEmpty()) {
			throw new BrandException();
		}
		if(!factories.containsKey(fname)) {
			throw new BrandException();
		}
		for(String li:line) {
			String[] l = li.replaceAll(" ", "").split(":");
			if(l.length != 3) {
				throw new BrandException();
			}
			try {
				Integer.parseInt(l[1]);
				if(Integer.parseInt(l[2]) > 4 || Integer.parseInt(l[2]) < 0) {
					throw new BrandException();
				}
				if(Integer.parseInt(l[1]) < 0) {
					throw new BrandException();
				}
			}catch(NumberFormatException e){
				throw new BrandException();
			}
		}
		for(String li:line) {
			String[] l = li.replaceAll(" ", "").split(":");
			factories.get(fname).addLine(l[0], Integer.parseInt(l[1]), Integer.parseInt(l[2]));
		}
	}
	
	/**
	 * Returns a map reporting for each engine type the yearly production capacity of a factory.
	 * 
	 * @param fname factory name
	 * @return A map of the yearly productions
	 * @throws BrandException if factory name is not defined or it has no lines
	 */
	public Map<Integer, Integer> estimateYearlyProduction(String fname) throws BrandException {
		if(this.factories.isEmpty()) {
			throw new BrandException();
		}
		if(!factories.containsKey(fname)) {
			throw new BrandException();
		}
		if(factories.get(fname).getLines().isEmpty()) {
			throw new BrandException();
		}
		return factories.get(fname).getLines().values().stream().collect(Collectors.groupingBy(v-> v.getMot(), Collectors.summingInt(v -> v.getnCars())));
	}

	// **************** R3 ********************************* //

	
	/**
	 * Creates a new storage for the car maker
	 * 
	 * @param name		Name of the storage
	 * @param capacity	Capacity (number of cars) of the storage
	 * @throws BrandException if name already defined or capacity &le; 0
	 */
	private Map<String, Warehouse> warehouses = new HashMap<>();
	public void buildStorage (String name, int capacity) throws BrandException {
		if(!warehouses.isEmpty() && warehouses.containsKey(name)) {
			throw new BrandException();
		}
		if(capacity <= 0) {
			throw new BrandException();
		}
		warehouses.put(name, new Warehouse(name, capacity));
	}
	
	/**
	 * Retrieves the names of the available storages. 
	 * The list is empty if no storage is available
	 * 
	 * @return List<String> list of storage names
	 */
	public List<String> getStorageList() {
		return warehouses.values().stream().collect(Collectors.mapping(v -> v.getName(), Collectors.toList()));
	}
	
	/**
	 * Add a car to the storage if possible
	 * 
	 * @param sname		storage name
	 * @param model		car model
	 * 
	 * @throws BrandException if storage or model not defined or storage is full
	 */
	public void storeCar(String sname, String model) throws BrandException {
		if(!this.warehouses.containsKey(sname)) {
			throw new BrandException();
		}
		if(!cars.containsKey(model)) {
			throw new BrandException();
		}
		if(this.warehouses.get(sname).getCarsParked().size() >= this.warehouses.get(sname).getCapacity()) {
			throw new BrandException();
		}
		this.warehouses.get(sname).parkCar(model);
	}
	
	/**
	 * Remove a car to the storage if possible.
	 * 
	 * @param sname		Storage name
	 * @param model		Car model
	 * @throws BrandException  if storage or model not defined or storage is empty
	 */
	public void removeCar(String sname, String model) throws BrandException {
		if(!this.warehouses.containsKey(sname)) {
			throw new BrandException();
		}
		if(!cars.containsKey(model)) {
			throw new BrandException();
		}
		if(this.warehouses.get(sname).getCarsParked().isEmpty()) {
			throw new BrandException();
		}
		this.warehouses.get(sname).removeCar(model);
	}
	
	/**
	 * Generates a summary of the storage.
	 * 
	 * @param sname		storage name
	 * @return map of models vs. quantities
	 * @throws BrandException if storage is not defined
	 */
	public Map<String,Integer> getStorageSummary(String sname) throws BrandException {
		if(!this.warehouses.containsKey(sname)) {
			throw new BrandException();
		}
		Map<String,Integer> res = new HashMap<>();	
		Map<Object, Long> map =  this.warehouses.get(sname).getCarsParked().stream().collect(Collectors.groupingBy(c -> c, Collectors.counting()));
		for(Object k: map.keySet()) {
			res.put(k.toString(), map.get(k).intValue());
		}
		return res;
	}
	
	// **************** R4 ********************************* //
	

	/**
	 * Sets the thresholds for the sustainability level.
	 * 
	 * @param ismin	lower threshold
	 * @param ismax upper threshold
	 */
	private float ismin;
	private float ismax;
	public void setISThresholds (float ismin, float ismax) {
		this.ismin = ismin;
		this.ismax = ismax;
	}
	
	/**
	 * Retrieves the models classified in the given Sustainability class.
	 * 
	 * @param islevel sustainability level, 0:low 1:medium 2:high
	 * @return the list of model names in the class
	 */
	public List<String> getModelsSustainability(int islevel) {
		if(islevel == 0) {
			return this.cars.values().stream().filter(c -> c.getEnginetype() * 100 / (float) (java.time.LocalDate.now().getYear() - c.getYear()) < this.ismin)
									.collect(Collectors.mapping(c -> c.getCode(), Collectors.toList()));
		}
		if(islevel == 1) {
			return this.cars.values().stream().filter(c -> (float)(c.getEnginetype() * 100) / (float) (java.time.LocalDate.now().getYear() - c.getYear()) >= this.ismin
											       && c.getEnginetype() * 100 / (float) (java.time.LocalDate.now().getYear() - c.getYear()) <= this.ismax)
									.collect(Collectors.mapping(c -> c.getCode(), Collectors.toList()));
		}
		if(islevel == 2) {
			return this.cars.values().stream().filter(c -> c.getEnginetype() * 100 / (float) (java.time.LocalDate.now().getYear() - c.getYear()) > this.ismax)
									.collect(Collectors.mapping(c -> c.getCode(), Collectors.toList()));
		}
		return new ArrayList<>();
	}
	
	
	/**
	 * Computes the Carmaker Sustainability level
	 * 
	 * @return sustainability index
	 */
	public float getCarMakerSustainability() {
		float avg = 0;
		for(String c:this.warehouses.values().stream().flatMap(l -> l.getCarsParked().stream()).collect(Collectors.toList())){
			avg += this.cars.get(c).getEnginetype() * 100 / (float) (java.time.LocalDate.now().getYear() - this.cars.get(c).getYear());
		}
		return avg / this.warehouses.values().stream().flatMap(l -> l.getCarsParked().stream()).count();
	}
	
	// **************** R5 ********************************* //

	/**
	 * Generates an allocation production plan
	 * 
	 * @param request allocation request string
	 * @return {@code true} is planning was successful
	 */
	public boolean plan(String request) {
		for(String req:request.split(",")) {
			for(int i = 0; i < Integer.parseInt(req.split(":")[1]); i ++) {
				try {
					factories.values().stream().flatMap(l -> l.getLines().values().stream())
					 		 .filter(l -> l.getAllocatedCars().size() < l.getnCars() && this.cars.get(req.split(":")[0]).getEnginetype() == l.getMot())
					 		 .findFirst().get().allocateCar(req.split(":")[0]);
				}catch(NoSuchElementException e){
					return false;
				}
			}
		}
		return true;
	}
	
	
	
	/**
	 * Returns the capacity of a line
	 * 
	 * @param fname factory name
	 * @param lname line name
	 * @return total capacity of the line
	 */
	public int getLineCapacity(String fname, String lname) {
		return this.factories.get(fname).getLines().get(lname).getnCars();
	}
	
	/**
	 * Returns the allocated capacity of a line
	 * @param fname factory name
	 * @param lname line name
	 * @return already allocated capacity for the line
	 */
	public int getLineAllocatedCapacity(String fname, String lname) {
		return this.factories.get(fname).getLines().get(lname).getAllocatedCars().size();
	}
	
	
	
	// **************** R6 ********************************* //
	
	/**
	 * compute the proportion of lines that are fully allocated
	 * (i.e. allocated capacity == total capacity) as a result
	 * of previous calls to method {@link #plan}
	 * 
	 * @return proportion of lines fully allocated
	 */
	public float linesfullyAllocated() {
		return factories.values().stream().flatMap(l -> l.getLines().values().stream())
					.filter(l -> l.getAllocatedCars().size() == l.getnCars()).count() / 
					(float) factories.values().stream().flatMap(l -> l.getLines().values().stream()).count();
	}

	/**
	 * compute the proportion of lines that are unused
	 * (i.e. allocated capacity == 0) as a result
	 * of previous calls to method {@link #plan}
	 * 
	 * @return proportion of unused lines
	 */
	public float unusuedLines() {
		return factories.values().stream().flatMap(l -> l.getLines().values().stream())
				.filter(l -> l.getAllocatedCars().isEmpty() == true).count() / 
				(float) factories.values().stream().flatMap(l -> l.getLines().values().stream()).count();
	}
}
