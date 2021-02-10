/**
 * @(#) DelMethodVisitor.java
 */
package visitor.rewrite;

import java.lang.reflect.Modifier;

import javax.inject.Inject;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.rewrite.ASTRewrite;
import org.eclipse.jdt.core.dom.rewrite.ListRewrite;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Shell;

import model.ProgramElement;

/**
 * @since J2SE-1.8
 */
public class DelMethodVisitor extends ASTVisitor {
   private ProgramElement progElemToBeRemoved;
   private MethodDeclaration methodToBeRemoved;
   private ASTRewrite rewrite;
   private boolean isPrivate;
   boolean isDeleted = false;

   @Inject
   private Shell shell;

   public DelMethodVisitor(ProgramElement curProgElem) {
      this.progElemToBeRemoved = curProgElem;
      this.isPrivate = false;
      this.isDeleted = false;
   }

   public void setASTRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
   }

   @Override
   public void endVisit(TypeDeclaration typeDecl) {
	   if (isPrivate) {
			ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
					TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
			lrw.remove(methodToBeRemoved, null);
			isDeleted = true;
		}
		else {
			String message = "Can not delete the selected method \"" + progElemToBeRemoved.getMethodName() + "\" because it's not private";
			MessageDialog.openWarning(shell, "Warning", message);
			//UtilMsg.openWarning("Can not delete the selected method \"" + progElemToBeRemoved.getMethodName() + "\" because the method includes some statement(s)");
		}
   }

   public boolean visit(MethodDeclaration node) {
      String name = node.getName().getFullyQualifiedName();
      if (name.equals(progElemToBeRemoved.getMethodName())) {
    	  int methodModifies = node.getModifiers();
    	  isPrivate = (methodModifies & Modifier.PRIVATE) != 0;
    	  System.out.println(node.getName() + ": " + isPrivate);

        // MessageDialog.openInformation(shell, "Title", "" + node.getName());

         this.methodToBeRemoved = node;
         return false;
      }
      return true;
   }
   
   public boolean isDeleted() {
		return isDeleted;
   }

   public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
   }
}
