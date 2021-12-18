package it.polito.oop.production;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
	public boolean addModel(String code, String name,  int year, float displacement, int enginetype) {
		return false;
	}
	
	/**
	 * Count the number of models produced by the brand
	 * 
	 * @return models count
	 */
	public int countModels() {
		return -1;
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
		return null;
	}
	
	
	/**
	 * Retrieves the list of codes of active models.
	 * Active models not older than 10 years with respect to the execution time.
	 * 	 
	 * @return a list of codes of the active models
	 */
	public List<String> getActiveModels() {
		return null;
	}
	
	
	/**
	 * Loads a list of models from a file.
	 * @param Filename path of the file
	 * @throws IOException in case of IO problems
	 */
	public void loadFromFile(String Filename) throws IOException  {
	}
	
	// **************** R2 ********************************* //

	
	
	/**
	 * Creates a new factory given its name. Throws Brand Exception if the name of the factory already exists.
	 * 
	 * @param name the unique name of the factory.
	 * @throws BrandException
	 */
	public void buildFactory(String name) throws BrandException {
	}
	
	
	
	/**
	 * Returns a list of the factory names. The list is empty if no factories are created.
	 * @return A list of factory names.
	 */
	public List<String> getFactories() {
		return null;
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
	}
	
	/**
	 * Returns a map reporting for each engine type the yearly production capacity of a factory.
	 * 
	 * @param fname factory name
	 * @return A map of the yearly productions
	 * @throws BrandException if factory name is not defined or it has no lines
	 */
	public Map<Integer, Integer> estimateYearlyProduction(String fname) throws BrandException {
		return null;
	}

	// **************** R3 ********************************* //

	
	/**
	 * Creates a new storage for the car maker
	 * 
	 * @param name		Name of the storage
	 * @param capacity	Capacity (number of cars) of the storage
	 * @throws BrandException if name already defined or capacity &le; 0
	 */
	public void buildStorage (String name, int capacity) throws BrandException {
	}
	
	/**
	 * Retrieves the names of the available storages. 
	 * The list is empty if no storage is available
	 * 
	 * @return List<String> list of storage names
	 */
	public List<String> getStorageList() {
		return null;
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
	}
	
	/**
	 * Remove a car to the storage if possible.
	 * 
	 * @param sname		Storage name
	 * @param model		Car model
	 * @throws BrandException  if storage or model not defined or storage is empty
	 */
	public void removeCar(String sname, String model) throws BrandException {
	}
	
	/**
	 * Generates a summary of the storage.
	 * 
	 * @param sname		storage name
	 * @return map of models vs. quantities
	 * @throws BrandException if storage is not defined
	 */
	public Map<String,Integer> getStorageSummary(String sname) throws BrandException {
		return null;
	}
	
	// **************** R4 ********************************* //
	

	/**
	 * Sets the thresholds for the sustainability level.
	 * 
	 * @param ismin	lower threshold
	 * @param ismax upper threshold
	 */
	public void setISThresholds (float ismin, float ismax) {
	}
	
	
	
	
	/**
	 * Retrieves the models classified in the given Sustainability class.
	 * 
	 * @param islevel sustainability level, 0:low 1:medium 2:high
	 * @return the list of model names in the class
	 */
	public List<String> getModelsSustainability(int islevel) {
		return null;
	}
	
	
	/**
	 * Computes the Carmaker Sustainability level
	 * 
	 * @return sustainability index
	 */
	public float getCarMakerSustainability() {
		return -1;
	}
	
	// **************** R5 ********************************* //

	/**
	 * Generates an allocation production plan
	 * 
	 * @param request allocation request string
	 * @return {@code true} is planning was successful
	 */
	public boolean plan(String request) {
		return false;
	}
	
	
	
	/**
	 * Returns the capacity of a line
	 * 
	 * @param fname factory name
	 * @param lname line name
	 * @return total capacity of the line
	 */
	public int getLineCapacity(String fname, String lname) {
		return -1;
	}
	
	/**
	 * Returns the allocated capacity of a line
	 * @param fname factory name
	 * @param lname line name
	 * @return already allocated capacity for the line
	 */
	public int getLineAllocatedCapacity(String fname, String lname) {
		return -1;
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
		return (float)-1.0;
	}

	/**
	 * compute the proportion of lines that are unused
	 * (i.e. allocated capacity == 0) as a result
	 * of previous calls to method {@link #plan}
	 * 
	 * @return proportion of unused lines
	 */
	public float unusuedLines() {
		return (float)-1.0;
	}
}
