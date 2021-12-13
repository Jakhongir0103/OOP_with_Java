package mountainhuts;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Class {@code Region} represents the main facade
 * class for the mountains hut system.
 * 
 * It allows defining and retrieving information about
 * municipalities and mountain huts.
 *
 */
public class Region {

	/**
	 * Create a region with the given name.
	 * 
	 * @param name
	 *            the name of the region
	 */
	private String name;
	public Region(String name) {
		this.name = name;
	}

	/**
	 * Return the name of the region.
	 * 
	 * @return the name of the region
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Create the ranges given their textual representation in the format
	 * "[minValue]-[maxValue]".
	 * 
	 * @param ranges
	 *            an array of textual ranges
	 */
	private String[] ranges;
	public void setAltitudeRanges(String... ranges) {
		this.ranges = ranges;
	}

	/**
	 * Return the textual representation in the format "[minValue]-[maxValue]" of
	 * the range including the given altitude or return the default range "0-INF".
	 * 
	 * @param altitude
	 *            the geographical altitude
	 * @return a string representing the range
	 */
	public String getAltitudeRange(Integer altitude) {
		String result = "0-INF";
		if(ranges == null ) {
			return result;
		}
		for(String alt: ranges) {
			if(altitude > Integer.parseInt(alt.split("-")[0]) && altitude <= Integer.parseInt(alt.split("-")[1])) {
				result = alt;
			}
		}
		return result;
	}

	/**
	 * Create a new municipality if it is not already available or find it.
	 * Duplicates must be detected by comparing the municipality names.
	 * 
	 * @param name
	 *            the municipality name
	 * @param province
	 *            the municipality province
	 * @param altitude
	 *            the municipality altitude
	 * @return the municipality
	 */
	private Map<String, Municipality> municipalities = new HashMap<>();
	public Municipality createOrGetMunicipality(String name, String province, Integer altitude) {
		if(!municipalities.isEmpty() && municipalities.containsKey(name)) {
			return municipalities.get(name);
		}
		municipalities.put(name, new Municipality(name, province, altitude));
		return municipalities.get(name);
	}

	/**
	 * Return all the municipalities available.
	 * 
	 * @return a collection of municipalities
	 */
	public Collection<Municipality> getMunicipalities() {
		return this.municipalities.values();
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 *
	 * @param name
	 *            the mountain hut name
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return the mountain hut
	 */
	private Map<String, MountainHut> MHuts = new HashMap<>();
	public MountainHut createOrGetMountainHut(String name, String category, Integer bedsNumber,
			Municipality municipality) {
		if(MHuts.containsKey(name)) {
			return MHuts.get(name);
		}
		MHuts.put(name, new MountainHut(name, category, bedsNumber, municipality));
		return MHuts.get(name);
	}

	/**
	 * Create a new mountain hut if it is not already available or find it.
	 * Duplicates must be detected by comparing the mountain hut names.
	 * 
	 * @param name
	 *            the mountain hut name
	 * @param altitude
	 *            the mountain hut altitude
	 * @param category
	 *            the mountain hut category
	 * @param bedsNumber
	 *            the number of beds in the mountain hut
	 * @param municipality
	 *            the municipality in which the mountain hut is located
	 * @return a mountain hut
	 */
	public MountainHut createOrGetMountainHut(String name, Integer altitude, String category, Integer bedsNumber,
			Municipality municipality) {
		if(MHuts.containsKey(name)) {
			return MHuts.get(name);
		}
		MHuts.put(name, new MountainHut(name, category, bedsNumber, municipality));
		MHuts.get(name).setAltitude(altitude);
		return MHuts.get(name);
	}

	/**
	 * Return all the mountain huts available.
	 * 
	 * @return a collection of mountain huts
	 */
	public Collection<MountainHut> getMountainHuts() {
		return this.MHuts.values();
	}

	/**
	 * Factory methods that creates a new region by loadomg its data from a file.
	 * 
	 * The file must be a CSV file and it must contain the following fields:
	 * <ul>
	 * <li>{@code "Province"},
	 * <li>{@code "Municipality"},
	 * <li>{@code "MunicipalityAltitude"},
	 * <li>{@code "Name"},
	 * <li>{@code "Altitude"},
	 * <li>{@code "Category"},
	 * <li>{@code "BedsNumber"}
	 * </ul>
	 * 
	 * The fields are separated by a semicolon (';'). The field {@code "Altitude"}
	 * may be empty.
	 * 
	 * @param name
	 *            the name of the region
	 * @param file
	 *            the path of the file
	 */
	public static Region fromFile(String name, String file) {
		List<String> lines = readData(file);
		Region r = new Region(name);

		for(String line: lines) {
			if(line.equals("Province;Municipality;MunicipalityAltitude;Name;Altitude;Category;BedsNumber")) {
				continue;
			}
			String[] lineArr = line.split(";");
			
			r.createOrGetMunicipality(lineArr[1], lineArr[0], Integer.parseInt(lineArr[2]));
			if(lineArr[4].equals("")) {
				r.createOrGetMountainHut(lineArr[3], lineArr[5], Integer.parseInt(lineArr[6]), r.getMunicipalities().stream().filter(m -> m.getName().equals(lineArr[1])).findFirst().get());
			}else {
				r.createOrGetMountainHut(lineArr[3], Integer.parseInt(lineArr[4]), lineArr[5], Integer.parseInt(lineArr[6]), r.getMunicipalities().stream().filter(m -> m.getName().equals(lineArr[1])).findFirst().get());
			}
		}
		return r;
	}

	/**
	 * Internal class that can be used to read the lines of
	 * a text file into a list of strings.
	 * 
	 * When reading a CSV file remember that the first line
	 * contains the headers, while the real data is contained
	 * in the following lines.
	 * 
	 * @param file the file name
	 * @return a list containing the lines of the file
	 */
	@SuppressWarnings("unused")
	private static List<String> readData(String file) {
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			return in.lines().collect(toList());
		} catch (IOException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	/**
	 * Count the number of municipalities with at least a mountain hut per each
	 * province.
	 * 
	 * @return a map with the province as key and the number of municipalities as
	 *         value
	 */
	public Map<String, Long> countMunicipalitiesPerProvince() {
		return this.municipalities.values().stream()
				.collect(Collectors.groupingBy(Municipality::getProvince, Collectors.counting()));
	}

	/**
	 * Count the number of mountain huts per each municipality within each province.
	 * 
	 * @return a map with the province as key and, as value, a map with the
	 *         municipality as key and the number of mountain huts as value
	 */
	public Map<String, Map<String, Long>> countMountainHutsPerMunicipalityPerProvince() {
		return this.MHuts.values().stream()
				.collect(Collectors.groupingBy(m -> m.getMunicipality().getProvince(), 
						 Collectors.groupingBy(m -> m.getMunicipality().getName(),
						 Collectors.counting())));
	}

	/**
	 * Count the number of mountain huts per altitude range. If the altitude of the
	 * mountain hut is not available, use the altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the number of mountain huts
	 *         as value
	 */
	public Map<String, Long> countMountainHutsPerAltitudeRange() {
		return this.MHuts.values().stream()
				.collect(Collectors.groupingBy(h -> getAltitudeRange(h.getAltitude().orElse(h.getMunicipality().getAltitude())),
						 Collectors.counting()));
	}

	/**
	 * Compute the total number of beds available in the mountain huts per each
	 * province.
	 * 
	 * @return a map with the province as key and the total number of beds as value
	 */
	public Map<String, Integer> totalBedsNumberPerProvince() {
		return this.MHuts.values().stream()
				.collect(Collectors.groupingBy(MountainHut::getProvince,
						 (Collectors.summingInt(MountainHut::getBedsNumber))));
	}

	/**
	 * Compute the maximum number of beds available in a single mountain hut per
	 * altitude range. If the altitude of the mountain hut is not available, use the
	 * altitude of its municipality.
	 * 
	 * @return a map with the altitude range as key and the maximum number of beds
	 *         as value
	 */
	public Map<String, Optional<Integer>> maximumBedsNumberPerAltitudeRange() {
		Map<String, Optional<MountainHut>> map = this.MHuts.values().stream()
				.collect(Collectors.groupingBy(h -> getAltitudeRange(h.getAltitude().orElse(h.getMunicipality().getAltitude())),
						 Collectors.maxBy(Comparator.comparing(MountainHut::getBedsNumber))));
		
		Map<String, Optional<Integer>> result = new HashMap<>();
		for(String k: map.keySet()) {
			result.put(k, Optional.ofNullable(map.get(k).get().getBedsNumber()));
		}
		return result;
	}

	/**
	 * Compute the municipality names per number of mountain huts in a municipality.
	 * The lists of municipality names must be in alphabetical order.
	 * 
	 * @return a map with the number of mountain huts in a municipality as key and a
	 *         list of municipality names as value
	 */
	public Map<Long, List<String>> municipalityNamesPerCountOfMountainHuts() {
		Map<String, Long> map = this.MHuts.values().stream()
				.collect(Collectors.groupingBy(h -> h.getMunicipality().getName(),
						 Collectors.counting()));
		
		List<LastQuery> list = new ArrayList<>();
		for(String k: map.keySet()) {
			list.add(new LastQuery(k, map.get(k)));
		}
		
		return list.stream().sorted(Comparator.comparing(LastQuery::getName))
						.collect(Collectors.groupingBy(l -> l.getNumbers(), 
								 Collectors.mapping(l -> l.getName(), Collectors.toList())));
	}

	class LastQuery{
		private String name;
		private Long numbers;
		
		public LastQuery(String name, Long numbers) {
			super();
			this.name = name;
			this.numbers = numbers;
		}
		
		public String getName() {
			return name;
		}
		public Long getNumbers() {
			return numbers;
		}
	}
}
