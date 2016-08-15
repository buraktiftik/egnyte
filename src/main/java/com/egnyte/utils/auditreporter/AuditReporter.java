package com.egnyte.utils.auditreporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.egnyte.utils.auditreporter.csvtools.CSVWriter;

public class AuditReporter {

	private List<List<String>> users;
	private List<List<String>> files;

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	private static final int variableNumber = 3;

	// loads the data, largely untouched except to modify the file location for
	// the resources for maven
	protected void loadData(String userFn, String filesFn) throws IOException {
		String line;

		BufferedReader reader = null;
		try {
			// THIS CODE ALLOWS MAVEN TO READ FROM RESOURCES FOLDER
			// AUTOMATICALLY
			// DISABLE IT FOR JAR CREATION

			// reader = new BufferedReader(new
			// FileReader(this.getClass().getClassLoader().getResource(userFn).getFile()));
			reader = new BufferedReader(new FileReader(userFn));
			users = new ArrayList<List<String>>();

			reader.readLine(); // skip header

			while ((line = reader.readLine()) != null) {
				users.add(Arrays.asList(line.split(",")));
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

		reader = null;
		try {
			// THIS CODE ALLOWS MAVEN TO READ FROM RESOURCES FOLDER
			// AUTOMATICALLY
			// DISABLE IT FOR JAR CREATION

			// reader = new BufferedReader(new
			// FileReader(this.getClass().getClassLoader().getResource(filesFn).getFile()));
			reader = new BufferedReader(new FileReader(filesFn));
			files = new ArrayList<List<String>>();

			reader.readLine(); // skip header

			while ((line = reader.readLine()) != null) {
				files.add(Arrays.asList(line.split(",")));
			}
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}

	// generates the header, and returns it as string
	private String printHeader() {
		StringBuilder result = new StringBuilder();
		result.append("Audit Report");
		result.append(NEW_LINE_SEPARATOR);
		result.append("============");
		result.append(NEW_LINE_SEPARATOR);

		return result.toString();
	}

	private String printUserHeader(String userName) {

		return "## User: " + userName + NEW_LINE_SEPARATOR;
	}

	private String printFile(String fileName, long fileSize) {
		return "* " + fileName + " ==> " + fileSize + " bytes" + NEW_LINE_SEPARATOR;
	}

	// sorts the files list, from largest to smallest
	private void calculateTopN() {

		List<String> tmp;
		for (int i = 0; i < (files.size()); i++) {
			long size = Long.parseLong(files.get(i).get(1));
			for (int y = 0; y < (files.size()); y++) {
				long size2 = Long.parseLong(files.get(y).get(1));
				if (size < size2) {
					tmp = files.get(y);
					files.set(y, files.get(i));
					files.set(i, tmp);

				}
			}

		}
		Collections.reverse(files);

	}

	// method that gets the Top N files
	protected String topN(int topNNumber) throws Exception {

		if (topNNumber > files.size()) {
			throw new Exception("Top N Number Too Big, Larger Than the Number of Files");
		}

		calculateTopN();

		StringBuilder result = new StringBuilder();

		result.append("Top #" + topNNumber + " Report");
		result.append(NEW_LINE_SEPARATOR);
		result.append("=============");
		result.append(NEW_LINE_SEPARATOR);

		for (int i = 0; i < topNNumber; i++) {
			List<String> fileRow = files.get(i);
			String fileName = fileRow.get(2);

			long ownerUserId = Long.parseLong(fileRow.get(3));

			long size = Long.parseLong(fileRow.get(1));
			result.append("* " + fileName + " ==> ");

			for (List<String> userRow : users) {
				String userName = userRow.get(1);
				long userId = Long.parseLong(userRow.get(0));

				if (ownerUserId == userId) {
					result.append(" user " + userName + COMMA_DELIMITER);
				}

			}
			result.append(" " + size + " bytes");
			result.append(NEW_LINE_SEPARATOR);
		}

		return result.toString();

	}

	// Top N CSV output method
	protected void topNCSVOutput(int topNNumber) throws Exception {

		// CSV Related
		CSVWriter myWriter = new CSVWriter();
		List<String[]> result = new ArrayList<String[]>();
		String[] entry;

		if (topNNumber > files.size()) {
			throw new Exception("Top N Number Too Big, Larger Than the Number of Files");
		}

		calculateTopN();

		for (int i = 0; i < topNNumber; i++) {

			List<String> fileRow = files.get(i);
			String fileName = fileRow.get(2);

			long ownerUserId = Long.parseLong(fileRow.get(3));

			long size = Long.parseLong(fileRow.get(1));

			for (List<String> userRow : users) {
				String userName = userRow.get(1);
				long userId = Long.parseLong(userRow.get(0));

				if (ownerUserId == userId) {
					entry = new String[variableNumber];
					entry[0] = fileName;
					entry[1] = userName;
					entry[2] = Long.toString(size);
					result.add(entry);
				}

			}

		}

		myWriter.writeToFile("topNandCOptionResult", result);

	}

	// the regular csv output method
	protected void csvOutput() {
		CSVWriter myWriter = new CSVWriter();

		List<String[]> result = new ArrayList<String[]>();
		String[] entry;

		for (List<String> userRow : users) {

			long userId = Long.parseLong(userRow.get(0));
			String userName = userRow.get(1);

			for (List<String> fileRow : files) {

				long size = Long.parseLong(fileRow.get(1));
				String fileName = fileRow.get(2);
				long ownerUserId = Long.parseLong(fileRow.get(3));
				if (ownerUserId == userId) {
					entry = new String[variableNumber];
					entry[0] = userName;
					entry[1] = fileName;
					entry[2] = Long.toString(size);
					result.add(entry);
				}

			}

		}

		myWriter.writeToFile("cOptionResult", result);

	}

	// prints the given output to the screen
	protected void screenOutput(String output) {
		System.out.println(output);
	}

	// method that generates the string output for the program, the output is
	// then fed to screen output or the csv output methods
	protected String generateOutput() {

		StringBuilder result = new StringBuilder();

		result.append(printHeader());
		try {
			for (List<String> userRow : users) {
				long userId = Long.parseLong(userRow.get(0));
				String userName = userRow.get(1);

				result.append(printUserHeader(userName));

				for (List<String> fileRow : files) {
					String fileId = fileRow.get(0);
					long size = Long.parseLong(fileRow.get(1));
					String fileName = fileRow.get(2);
					long ownerUserId = Long.parseLong(fileRow.get(3));
					if (ownerUserId == userId) {
						result.append(printFile(fileName, size));

					}

				}
			}
		} catch (Exception e) {
			System.err.println(
					"Error reading data from the CSV Files, improperly formatted data or the order of files has been mixed");
			System.exit(0);
		}

		return result.toString();
	}

	// prints the proper usage if the arguments were not correct
	protected void printUsage() {
		System.out.println("Usage Manual: ");
		System.out.println("java -jar egnyte-1.0-jar-with-dependencies.jar users.csv files.csv --top 5 -c");
		System.out.println("first 2 options should be users then files in that order");
		System.out.println("Optional: --top N for sorting");
		System.out.println("-c for csv output (always should be the last variable)");
	}

	// GETTERS AND SETTERS START HERE

	public List<List<String>> getUsers() {
		return users;
	}

	public void setUsers(List<List<String>> users) {
		this.users = users;
	}

	public List<List<String>> getFiles() {
		return files;
	}

	public void setFiles(List<List<String>> files) {
		this.files = files;
	}

}
