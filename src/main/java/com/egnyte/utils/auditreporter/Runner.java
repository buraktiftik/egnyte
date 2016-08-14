package com.egnyte.utils.auditreporter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Runner {

	public static void main(String[] args) throws IOException {
		AuditReporter r = new AuditReporter();

		// argument handling

		// improperly formatted inputs
		if (args.length < 2 || args.length > 5) {
			r.printUsage();
			System.exit(0);
		}

		// only 2 arguments
		else if (args.length == 2) {

			r.loadData(args[0], args[1]);
			r.screenOutput(r.generateOutput());
		}

		// just csv file output
		else if (args.length == 3 && args[(args.length - 1)].equals("-c")) {
			r.loadData(args[0], args[1]);
			r.csvOutput();
		}

		// top N output to screen
		else if (args.length == 4 && args[2].equals("--top")) {
			try {
				r.loadData(args[0], args[1]);
				// r.screenOutput(r.topN(Integer.parseInt(args[3])));
				r.screenOutput(r.topN(Integer.parseInt(args[3])));
				// r.sort();
			} catch (Exception e) {
				System.out.println("The input is improperly formatted");
				r.printUsage();
				e.printStackTrace();
			} finally {
				System.exit(0);
			}
		}

		// top N output to csv file
		else if (args.length == 5 && args[2].equals("--top") && args[(args.length - 1)].equals("-c")) {
			try {
				r.loadData(args[0], args[1]);
				r.topNCSVOutput(Integer.parseInt(args[3]));

			} catch (Exception e) {
				System.out.println("The input is improperly formatted, the problem seems at: " + e.getMessage());
				r.printUsage();
				e.printStackTrace();
			} finally {
				System.exit(0);
			}
			r.csvOutput();
		} else {
			r.printUsage();
			System.exit(0);
		}

	}

}
