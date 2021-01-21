 package handler;
 
 import org.eclipse.e4.core.di.annotations.Execute;
 import org.eclipse.jface.dialogs.MessageDialog;
 import org.eclipse.swt.widgets.Shell;

import org.eclipse.e4.core.di.annotations.Execute;

public class ShowHelloWorldHandler {
	@Execute
	public void execute(Shell shell) {
		MessageDialog.openInformation(shell, "Show Hello Plug-in", "Hello Plug-in");
	}
		
}