package com.isep.fr.bloom;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {
	private static final String CSV_FILE_PATH = "/home/foudre/eclipse-workspace/bloom/src/main/java/com/isep/fr/bloom/log20170630.csv";
	private static List<String> list = new ArrayList<String>();
	private static List<Integer> counter = new ArrayList<Integer>();
	private static org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(App.class);

	public static void main(String[] args) throws IOException {
		final long startTime = System.currentTimeMillis();
		try (Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
			for (CSVRecord csvRecord : csvParser) {
				// Accessing Values by Column Index
				String ip = csvRecord.get(0);
				// System.out.println("IP : " + ip);
				checkIP(ip);
			}
		} catch (IOException e) {
			log.error("Can't parse the CSV File please check the file and the path");
		}
		int position = getMax(counter);
		System.out.println("max position : " + getMax(counter));
		final long duration = System.currentTimeMillis() - startTime;
		log.info("IP winner : " + list.get(10));
		log.info("Execution time : " + duration + " ms");
	}
	//Check if ip is in the list
	private static void checkIP(String ip) {
		if (!list.contains(ip)) {
			list.add(ip);
			counter.add(1);
		} else {
			int position = list.indexOf(ip);
			counter.set(position, counter.get(position) + 1);
		}
	}

	public static int getMax(List<Integer> list) {
		int max = 0;
		int maxPosition = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) > max) {
				maxPosition = list.indexOf(list.get(i));
			}
		}
		return maxPosition;
	}
}
