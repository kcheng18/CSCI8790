package visitor;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

import data.DefUseModel;

public class PublicMethodsDefUseVisitor extends ASTVisitor {
   private Map<IVariableBinding, DefUseModel> defUseMap = new HashMap<IVariableBinding, DefUseModel>();
   private int fAccessesToSystemFields;
   private CompilationUnit compilationUnit;

   public PublicMethodsDefUseVisitor(CompilationUnit compilationUnit) {
      this.compilationUnit = compilationUnit;
   }

   @Override
   public boolean visit(MethodDeclaration node) {
	   int methodModifies = node.getModifiers();
	   if((methodModifies & Modifier.PUBLIC)!= 0) {
		   node.accept(new ASTVisitor() {
			   public boolean visit(VariableDeclarationStatement node) {
			      for (Iterator<?> iter = node.fragments().iterator(); iter.hasNext();) {
			         VariableDeclarationFragment f = (VariableDeclarationFragment) iter.next();

			         IVariableBinding b = f.resolveBinding();
			         DefUseModel data = new DefUseModel(node, f, getCompilationUnit());
			         defUseMap.put(b, data);
			      }
			      return super.visit(node);
			   }

			   public boolean visit(SimpleName node) {
			      if (node.getParent() instanceof VariableDeclarationFragment) {
			         return true;
			      } else if (node.getParent() instanceof SingleVariableDeclaration) {
			         return true;
			      }
			      IBinding binding = node.resolveBinding();
			      // Some SimpleName doesn't have binding information, returns null
			  // But all SimpleName nodes will be binded
			      if (binding == null) {
			         return true;
			      }
			      if (defUseMap.containsKey(binding)) {
			         defUseMap.get(binding).addUsedVars(node);
			      }
			      countNumOfRefToFieldOfJavaLangSystem(node);
			      return super.visit(node);
			   }
		   });
	   }
	   return true;
   }

   void countNumOfRefToFieldOfJavaLangSystem(SimpleName node) {
      IBinding binding = node.resolveBinding();
      if (binding instanceof IVariableBinding) {
         IVariableBinding varBinding = (IVariableBinding) binding;
         ITypeBinding declaringClass = varBinding.getDeclaringClass();
         if (varBinding.isField() && "java.lang.System".equals(declaringClass.getQualifiedName())) {
            fAccessesToSystemFields++;
            System.out.println(fAccessesToSystemFields);
         }
      }
   }
   
   public CompilationUnit getCompilationUnit() {
	   return compilationUnit;
   }
   
   public Map<IVariableBinding, DefUseModel> getdefUseMap() {
      return this.defUseMap;
   }
}
