package simpleVisitorPattern.visitor;

import java.io.*;
import java.util.*;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Brake;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;
import util.UtilFile;

public class MyFileSaveVisitor extends CarPartVisitor {
	private String workDir = System.getProperty("user.dir");
	String filePath = workDir + File.separator + "outputdata.csv";
	List<String> listContents = new ArrayList<String>();

	public void visit(Wheel part) {
		listContents.add(part.getName() + ", " + part.getModelNumberWheel() + ", " + part.getModelYearWheel());
		try {
			UtilFile.saveFile(filePath, listContents);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void visit(Engine part) {
		listContents.add(part.getName() + ", " + part.getModelNumberEngine() + ", " + part.getModelYearEngine());
		try {
			UtilFile.saveFile(filePath, listContents);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void visit(Body part) {
		listContents.add(part.getName() + ", " + part.getModelNumberBody() + ", " + part.getModelYearBody());
		try {
			UtilFile.saveFile(filePath, listContents);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void visit(Brake part) {
		listContents.add(part.getName() + ", " + part.getModelNumberBrake() + ", " + part.getModelYearBrake());
		try {
			UtilFile.saveFile(filePath, listContents);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
