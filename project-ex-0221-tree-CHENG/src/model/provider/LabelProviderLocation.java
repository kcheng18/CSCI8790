package model.provider;

import org.eclipse.jface.viewers.StyledString;

import model.progelement.MethodElement;

public class LabelProviderLocation extends LabelProviderProgElem{
	@Override
	public StyledString getStyledText(Object element) {
		if (element instanceof MethodElement) {
			return new StyledString(((MethodElement) element).getLocationStr());
		}
		return new StyledString(""); // super.getStyledText(element);
	}
}
