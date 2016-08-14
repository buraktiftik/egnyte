package com.egnyte.utils.auditreporter.csvtools;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

public class CSVWriter {

	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";
	//private static final String header = "_id,name,type,latitude,longitude";

	private static final int variableNumber = 3;

	public void writeToFile(String fileName, List<String[]> data) {

		FileWriter myWriter = null;

		try {
			myWriter = new FileWriter(fileName + ".csv");

			//myWriter.append(header);
			//myWriter.append(NEW_LINE_SEPARATOR);

			// write data here

			Iterator<String[]> myItr = data.iterator();

			while (myItr.hasNext()) {

				String[] result = myItr.next();

				for (int i = 0; i < variableNumber; i++) {
					myWriter.append(result[i]);
					if (i < (variableNumber - 1)) {
						myWriter.append(COMMA_DELIMITER);
					} else {
						myWriter.append(NEW_LINE_SEPARATOR);
					}

				}

			}
			System.out.println("Operation competed successfully");
		} catch (Exception e) {
			System.out.println("Error Detecting Writing to CSV File!");
			e.printStackTrace();
		} finally {
			try {
				myWriter.flush();
				myWriter.close();
			} catch (Exception e) {
				System.out.println("Error detected in closing the File Writer");
				e.printStackTrace();
			}

		}

	}

}
