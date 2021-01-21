 package handler;
 
 import org.eclipse.e4.core.di.annotations.Execute;
 import org.eclipse.jface.dialogs.MessageDialog;
 import org.eclipse.swt.widgets.Shell;
 
 import java.io.BufferedReader;  
 import java.io.FileReader;  
 import java.io.IOException;  
 import java.util.ArrayList; 
 import java.util.List;
 import java.util.Collections;

import org.eclipse.e4.core.di.annotations.Execute;

public class ShowHighestNumber {
	@Execute
	public void execute(Shell shell) {
		//parsing a CSV file into Scanner class constructor  
		try {
			String line = "";
			BufferedReader br = new BufferedReader(new FileReader("/Users/kwoksuncheng/Downloads/numbers.csv"));  
			List<Integer> lList = new ArrayList<Integer>();
			while ((line = br.readLine()) != null) {
				lList.add(Integer.parseInt(line));
			}
			Collections.sort(lList, Collections.reverseOrder());
			
			String out = String.format("Top five numbers: %d, %d, %d, %d, %d.", lList.get(0), lList.get(1), lList.get(2), lList.get(3), lList.get(4));
			MessageDialog.openInformation(shell, "Show Highest Number", out);
		}
		catch(IOException e){
			e.printStackTrace();  
		}
	}
		
}