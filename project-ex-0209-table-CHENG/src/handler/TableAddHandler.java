 
package handler;

import java.util.List;
import javax.inject.Inject;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;

import model.Person;
import model.PersonModelProvider;
import view.SimpleTableView0209Cheng;
import util.UtilFile;

public class TableAddHandler {
	@Inject
	private EPartService epartService;
	
	@Execute
	public void execute() {
		String inputdata = getFilePath();
		PersonModelProvider personInstance = PersonModelProvider.INSTANCE;
		List<String> contents = UtilFile.readFile(inputdata);
		List<List<String>> tableContents = UtilFile.convertTableContents(contents);
		
		if (tableContents != null) {
			for (List<String> iList : tableContents) {                        
				personInstance.getPersons().add(new Person(iList.get(0), iList.get(1), iList.get(2), iList.get(3)));
			}
		}
		MPart findPart = epartService.findPart(SimpleTableView0209Cheng.ID);
		   Object findPartObj = findPart.getObject();

		   if (findPartObj instanceof SimpleTableView0209Cheng) {
			   SimpleTableView0209Cheng v = (SimpleTableView0209Cheng) findPartObj;
		      v.refresh();
		   }		
	}
	private static String getFilePath() {
		return "/Users/kwoksuncheng/workspaceCSCI8790/workspaceCSCI8790-CHENG/project-ex-0209-table-CHENG/input_add.txt";
	}
}
