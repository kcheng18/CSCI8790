 
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;

import model.Person;
import model.PersonModelProvider;
import view.SimpleTableView0209Cheng;

public class TableDeleteHandler {
	@Inject
	private ESelectionService selectionService;
	
	@Execute
	public void execute(EPartService epartService) {
		MPart findPart = epartService.findPart(SimpleTableView0209Cheng.ID);
	      Object findPartObj = findPart.getObject();

	      if (findPartObj instanceof SimpleTableView0209Cheng) {
	    	  SimpleTableView0209Cheng v = (SimpleTableView0209Cheng) findPartObj;
	    	  Object selection = selectionService.getSelection();
	    	  remove(selection);
	    	  v.refresh();
	      }
	   }

	   private void remove(Object selection) {
	      if (selection instanceof Person) {
	         Person p = (Person) selection;
	         PersonModelProvider.INSTANCE.getPersons().remove(p);
	         return;
	      }
	      if (selection instanceof Object[]) {
	         Object[] objs = (Object[]) selection;
	         for (int i = 0; i < objs.length; i++) {
	            remove(objs[i]);
	         }
	      }	
	   }
}