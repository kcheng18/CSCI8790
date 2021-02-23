package model;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

public class TestModelProvider {
	@Test
	public void testGetNumChildNodes() {
		List<ProgramElement> progElements = ProgElementModelProvider.INSTANCE.getProgElements();
		for (ProgramElement iElem : progElements) {
			System.out.println("[DBG] " + iElem.getName());
			System.out.println("[DBG] " + getNumChildNodes(iElem));
		}
	}
	
	int getNumChildNodes(ProgramElement e) {
		return e.getlistChildren().size();
	}
}