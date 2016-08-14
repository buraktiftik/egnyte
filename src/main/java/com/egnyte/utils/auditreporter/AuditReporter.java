package com.egnyte.utils.auditreporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AuditReporter {

	private List<List<String>> users;
	private List<List<String>> files;

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	// loads the data, largely untouched except to modify the file location for
	// the resources for maven
	protected void loadData(String userFn, String filesFn) throws IOException {
		String line;

		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(this.getClass().getClassLoader().getResource(userFn).getFile()));
			// reader = new BufferedReader(new FileReader(userFn));
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
			reader = new BufferedReader(
					new FileReader(this.getClass().getClassLoader().getResource(filesFn).getFile()));
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

		return "## User: " + userName;
	}

	private String printFile(String fileName, long fileSize) {
		return "* " + fileName + " ==> " + fileSize + " bytes";
	}

	// sorts the files list
	private void calculateTopN() {

		List<String> tmp;
		for (int i = 0; i < (files.size() ); i++) {
			long size = Long.parseLong(files.get(i).get(1));
			for (int y = 0; y < (files.size() ); y++) {
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

	// DELETE THIS
	public void sort() {

		int tmp;
		int[] myArray = { 5, 3, 2, 11, 1, 6,21,4 };

		for (int i = 0; i < (myArray.length ); i++) {
			int size = myArray[i];
			for (int y = 0; y < (myArray.length ); y++) {
				int size2 = myArray[y];
				if (size < size2) {
					tmp = myArray[y];
					myArray[y] = myArray[i];
					myArray[i] = tmp;

				}
			}

		}
		for (int i : myArray) {
			System.out.println(i);
		}
	}

	protected String topN(int topNNumber) throws Exception {

		if (topNNumber > files.size()) {
			throw new Exception("Top N Number Too Big, Larger Than the Number of Files");
		}

		calculateTopN();

		StringBuilder result = new StringBuilder();
		// System.out.println("Top N method was called with variable number: " +
		// topNNumber);

		result.append("Top #3 Report");
		result.append(NEW_LINE_SEPARATOR);
		result.append("=============");
		result.append(NEW_LINE_SEPARATOR);

		
/*
		for (int i = 0; i < topNNumber; i++) {
			List<String> fileRow = files.get(i);
			String fileName = fileRow.get(2);

			long ownerUserId = Long.parseLong(fileRow.get(3));
			
			long size = Long.parseLong(fileRow.get(1));
			result.append(printFile(fileName, size));

			for (List<String> userRow : users) {
				String userName = userRow.get(1);
				long userId = Long.parseLong(userRow.get(0));
				
				if (ownerUserId == userId) {
					result.append(printUserHeader(userName));
				}
				result.append(NEW_LINE_SEPARATOR);
			}
		}*/

		
		for(int i=0;i<topNNumber;i++){
			result.append(files.get(i).get(1));
			result.append(COMMA_DELIMITER);
			result.append(files.get(i).get(2));
			result.append(NEW_LINE_SEPARATOR);
		}
		
		
		return result.toString();

	}

	protected void csvOutput() {
		System.out.println("CSV Output Method was called");
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
		for (List<String> userRow : users) {
			long userId = Long.parseLong(userRow.get(0));
			String userName = userRow.get(1);

			result.append(printUserHeader(userName));
			result.append(NEW_LINE_SEPARATOR);
			for (List<String> fileRow : files) {
				String fileId = fileRow.get(0);
				long size = Long.parseLong(fileRow.get(1));
				String fileName = fileRow.get(2);
				long ownerUserId = Long.parseLong(fileRow.get(3));
				if (ownerUserId == userId) {
					result.append(printFile(fileName, size));
					result.append(NEW_LINE_SEPARATOR);
				}
				result.append(NEW_LINE_SEPARATOR);
			}
		}

		return result.toString();
	}

	protected void printUsage() {
		System.out.println("Usage is xx");
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
