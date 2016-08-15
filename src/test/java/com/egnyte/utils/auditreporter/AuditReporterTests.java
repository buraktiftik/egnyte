package com.egnyte.utils.auditreporter;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuditReporterTests {

	private List<List<String>> users;
	private List<List<String>> files;

	String generateOutputMethodOutput;
	String topNMethodOutput;
	String topNMethodOutputZero;

	// create sample data and the expected output
	@Before
	public void setUp() {
		users = new ArrayList<List<String>>();
		files = new ArrayList<List<String>>();

		generateOutputMethodOutput = "Audit Report\n============\n## User: bob\n* data.txt ==> 5005 bytes\n* words.doc ==> 3003 bytes\n* pic.jpg ==> 6006 bytes\n## User: john\n* module.xml ==> 4004 bytes\n";
		topNMethodOutput = "Top #2 Report\n=============\n* pic.jpg ==>  user bob, 6006 bytes\n* data.txt ==>  user bob, 5005 bytes\n";
		topNMethodOutputZero = "Top #0 Report\n=============\n";

		users.add(Arrays.asList("1", "bob"));
		users.add(Arrays.asList("2", "john"));

		files.add(Arrays.asList("5974448e-9afd-4c9a-ac5a-9b1e84227820", "5005", "data.txt", "1"));
		files.add(Arrays.asList("b4f3esecf-95aa-42a7-bffc-83a5441b7d2e", "4004", "module.xml", "2"));
		files.add(Arrays.asList("fab16fa4-8251-4394-a673-c961a65eb1d2", "3003", "words.doc", "1"));
		files.add(Arrays.asList("73cadd04-c810-4b7d-9516-7b65a22a8cff", "6006", "pic.jpg", "1"));

	}

	@After
	public void tearDown() {
		users = null;
		files = null;

		generateOutputMethodOutput = null;
		topNMethodOutput = null;
		topNMethodOutputZero = null;
	}

	// tests to see if the generate output method is producing the required
	// output after the given input

	@Test
	public void simpleGetterTest() {
		AuditReporter myReporter = new AuditReporter();

		myReporter.setUsers(users);
		myReporter.setFiles(files);

		assertEquals("2", myReporter.getFiles().get(1).get(3));
		assertEquals("john", users.get(1).get(1));

	}

	@Test
	public void generateOutputTest() {

		AuditReporter myReporter = new AuditReporter();

		myReporter.setUsers(users);
		myReporter.setFiles(files);

		assertEquals(generateOutputMethodOutput, myReporter.generateOutput());

	}

	// Top N method test
	@Test
	public void basicTopNSortingTest() {
		AuditReporter myReporter = new AuditReporter();

		myReporter.setUsers(users);
		myReporter.setFiles(files);
		try {
			myReporter.topN(2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(files.get(3), myReporter.getFiles().get(3));
		assertEquals(files.get(2), myReporter.getFiles().get(2));
		assertEquals(files.get(1), myReporter.getFiles().get(1));
		assertEquals(files.get(0), myReporter.getFiles().get(0));

	}

	// Top N method test, also checks if the users and files are not null
	@Test
	public void topNTest() {

		AuditReporter myReporter = new AuditReporter();

		myReporter.setUsers(users);
		myReporter.setFiles(files);

		String actual = null;

		try {
			actual = myReporter.topN(2);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(myReporter.getUsers());
		assertNotNull(myReporter.getFiles());

		assertEquals(topNMethodOutput, actual);

	}

	// Tests if the Top N value input is given the output is as expected
	@Test
	public void topNZeroTest() {

		AuditReporter myReporter = new AuditReporter();

		myReporter.setUsers(users);
		myReporter.setFiles(files);

		String actual = null;

		try {
			actual = myReporter.topN(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(topNMethodOutputZero, actual);
	}

	// Tests the Top N Exception regarding N being larger than number of files
	// thrown
	@Test
	public void topNExceptionTest() {

		AuditReporter myReporter = new AuditReporter();

		myReporter.setUsers(users);
		myReporter.setFiles(files);

		String actual = null;

		try {
			actual = myReporter.topN(50);
		} catch (Exception e) {
			assertEquals("Top N Number Too Big, Larger Than the Number of Files", e.getMessage());
		}

	}

	// Test to see if File Not Found Exception is Thrown
	@Test
	public void loadDataFileNotFoundExceptionTest() {
		AuditReporter myReporter = new AuditReporter();

		try {
			myReporter.loadData("badfile", "badfile2");
		} catch (IOException e) {
			assertEquals("badfile (No such file or directory)", e.getMessage());
		}

	}

}
