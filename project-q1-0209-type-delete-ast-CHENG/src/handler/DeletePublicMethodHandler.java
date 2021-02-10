 
package handler;

import javax.inject.Inject;

import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.ESelectionService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import analysis.DelMethodAnalyzer;
import model.ModelProvider;
import model.ProgramElement;
import view.MyTableViewer;

public class DeletePublicMethodHandler {
	@Inject
	private ESelectionService selectionService;
	@Inject
	private EPartService epartService;
	
	@Execute
	public void execute(Shell shell) {
		System.out.println("DeletePublicMethodHandler!!");

		MPart findPart = epartService.findPart(MyTableViewer.ID);
		Object findPartObj = findPart.getObject();
		if (findPartObj instanceof MyTableViewer) {
			if (selectionService.getSelection() instanceof ProgramElement) {
				ProgramElement p = (ProgramElement) selectionService.getSelection();
				DelMethodAnalyzer analyzer = new DelMethodAnalyzer(p, false, true);
				boolean isPublic = analyzer.isPublic();
				if (isPublic) {
					ModelProvider.INSTANCE.getProgramElements().remove(p);
					// new DelMethodAnalyzer(p);
				}
				else {
					String message = "Can not delete the selected method \"" + p.getMethodName() + "\" because it is not a public method";
					MessageDialog.openWarning(shell, "Warning", message);
				}
				MyTableViewer v = (MyTableViewer) findPartObj;
				v.refresh();
			}
		}
	}
}