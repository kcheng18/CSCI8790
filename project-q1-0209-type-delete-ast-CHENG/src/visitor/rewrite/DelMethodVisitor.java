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
   private boolean delPrivate = false;
   private boolean delPublic = false;
   private boolean isPrivate;
   private boolean isPublic;

   @Inject
   private Shell shell;

   public DelMethodVisitor(ProgramElement curProgElem, boolean delPrivate, boolean delPublic) {
      this.progElemToBeRemoved = curProgElem;
   }

   public void setASTRewrite(ASTRewrite rewrite) {
      this.rewrite = rewrite;
      this.isPrivate = false;
      this.isPublic = false;
   }

   @Override
   public void endVisit(TypeDeclaration typeDecl) {
	   if ((isPrivate && delPrivate) || (isPublic && delPublic) || (!delPrivate && !delPublic)) {
		   ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
		            TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
	      lrw.remove(methodToBeRemoved, null);
	   }
      // ListRewrite lrw = rewrite.getListRewrite(typeDecl, //
         //   TypeDeclaration.BODY_DECLARATIONS_PROPERTY);
      // lrw.remove(methodToBeRemoved, null);
   }

   public boolean visit(MethodDeclaration node) {
      String name = node.getName().getFullyQualifiedName();
      if (name.equals(progElemToBeRemoved.getMethodName())) {
    	  int methodModifies = node.getModifiers();
    	  isPrivate = (methodModifies & Modifier.PRIVATE) != 0;
    	  System.out.println(node.getName() + ": " + isPrivate);
    	  
    	  isPublic = (methodModifies & Modifier.PUBLIC) != 0;
    	  System.out.println(node.getName() + ": " + isPrivate);
         // MessageDialog.openInformation(shell, "Method Deleted", "Method" + node.getName() + "has been deleted!!!");

         this.methodToBeRemoved = node;
         return false;
      }
      return true;
   }
   
   public boolean isPrivate() {
		return isPrivate;
   }
   
   public boolean isPublic() {
		return isPublic;
   }
   
}
