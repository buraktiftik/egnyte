package com.egnyte.utils.auditreporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AuditReporter {

	private List<List<String>> users;
	private List<List<String>> files;

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

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

	protected void topN(int topNNumber) {
		System.out.println("Top N method was called with variable number: " + topNNumber);
	}

	protected void csvOutput() {
		System.out.println("CSV Output Method was called");
	}

	protected void screenOutput(String output) {
		System.out.println(output);
	}

	protected String generateOutput() {

		StringBuilder result = new StringBuilder();

		result.append(printHeader());
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

		return result.toString();
	}

	protected void printUsage() {
		System.out.println("Usage is xx");
	}

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
