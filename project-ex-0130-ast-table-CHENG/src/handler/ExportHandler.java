 
package handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.ModelProvider;
import model.ProgramElement;
import util.UtilFile;
import view.MyTableViewer;

public class ExportHandler {
	@Inject
	private ESelectionService selectionService;

	@Execute
	public void execute(EPartService epartService, Shell shell) {
		String FilePath = "/Users/kwoksuncheng/Desktop/output.csv";
		MPart findPart = epartService.findPart(MyTableViewer.ID);
		Object findPartObj = findPart.getObject();
		
		if (findPartObj instanceof MyTableViewer) {
			MyTableViewer v = (MyTableViewer) findPartObj;
			//Object selection = selectionService.getSelection();
			List<ProgramElement> listElement = ModelProvider.INSTANCE.getProgramElements();
			//List<Person> listPersons = (List)selection;
			List<String> listContents = new ArrayList<String>();
			listContents.add(
					quote("Package name") + "," + quote("Class name") + "," + quote("Method name") + "," + quote("isReturnVoid")+ "," + quote("Parameter size")+ "," + quote("Starting Position of Method"));

			for (ProgramElement iProgramElement : listElement) {
				String iLine = quote(iProgramElement.getPkgName()) + 
						"," + quote(iProgramElement.getClassName()) + 
						"," + quote(iProgramElement.getMethodName()) + 
						"," + quote(String.valueOf(iProgramElement.isReturnVoid())) + 
						"," + (String.valueOf(iProgramElement.getParameterSize()) +
						"," + (String.valueOf(iProgramElement.getStartPosition())));
				listContents.add(iLine);
			}
			try {
				UtilFile.saveFile(FilePath, listContents);
				MessageDialog.openInformation(shell, "Export", "Saved output.csv");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	String quote(String s) {
		return "\"" + s + "\"";
	}
		
}