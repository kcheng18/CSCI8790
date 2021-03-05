 
package handler;

import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jdt.core.dom.IVariableBinding;


import analysis.PublicMethodsAnalyzerDefUse;
import data.DefUseModel;
import view.SimpleViewer;

public class DefUseAnalysisForPublicMethodsHandler extends DefUseHandler {
	@Execute
	public void execute(EPartService service) {
		MPart part = service.findPart(viewId);
		
		if (part != null && part.getObject() instanceof SimpleViewer) {
			PublicMethodsAnalyzerDefUse analyzer = new PublicMethodsAnalyzerDefUse();
	         try {
	            analyzer.analyze();
	         } catch (CoreException e) {
	            e.printStackTrace();
	         }
	         List<Map<IVariableBinding, DefUseModel>> analysisDataList = analyzer.getAnalysisDataList();

	         SimpleViewer viewer = (SimpleViewer) part.getObject();
	         displayDefUsedView(viewer, analysisDataList);
		}
	}
}