package simpleVisitorPattern.visitor;

import simpleVisitorPattern.part.Body;
import simpleVisitorPattern.part.Brake;
import simpleVisitorPattern.part.Engine;
import simpleVisitorPattern.part.Wheel;

public class MyReverseVisitor extends CarPartVisitor {

	public void visit(Wheel part) {
		String newName = reverse(part.getName());
		//System.out.println("[DBG] Reversing the name property: " + newName );
		part.setName(newName);
		part.setModelNumberWheel(reverse(part.getModelNumberWheel()));
		part.setModelYearWheel(reverse(part.getModelYearWheel()));		
	}

	public void visit(Engine part) {
		String newName = reverse(part.getName());
		//System.out.println("[DBG] Reversing the name property: " + newName );
		part.setName(newName);
		part.setModelNumberEngine(reverse(part.getModelNumberEngine()));
		part.setModelYearEngine(reverse(part.getModelYearEngine()));	
	}

	public void visit(Body part) {
		String newName = reverse(part.getName());
		//System.out.println("[DBG] Reversing the name property: " + newName );
		part.setName(newName);
		part.setModelNumberBody(reverse(part.getModelNumberBody()));
		part.setModelYearBody(reverse(part.getModelYearBody()));
	}

	public void visit(Brake part) {
		String newName = reverse(part.getName());
		//System.out.println("[DBG] Reversing the name property: " + newName );
		part.setName(newName);
		part.setModelNumberBrake(reverse(part.getModelNumberBrake()));
		part.setModelYearBrake(reverse(part.getModelYearBrake()));
	}

	String reverse(String name) {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb = sb.reverse();
		String[] text = sb.toString().split(" ");
		String rtext = "";

		for (int i = 1; i < text.length + 1; i++) {
			rtext = rtext + text[text.length - i] + " ";
		}
		
		return rtext;
	}

}
