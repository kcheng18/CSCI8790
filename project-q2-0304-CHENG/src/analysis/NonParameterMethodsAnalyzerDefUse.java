package analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.IVariableBinding;

import data.DefUseModel;
import util.UtilAST;
import visitor.NonParameterMethodsDefUseVisitor;

public class NonParameterMethodsAnalyzerDefUse {
	private static final String JAVANATURE = "org.eclipse.jdt.core.javanature";
	   IProject[] projects;
	
	   private List<Map<IVariableBinding, DefUseModel>> dataList = null;
	
	   public NonParameterMethodsAnalyzerDefUse() {
	      dataList = new ArrayList<Map<IVariableBinding, DefUseModel>>();
	   }
	
	   public void analyze() throws CoreException {
	      // =============================================================
	      // 1st step: Project
	      // =============================================================
	      projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
	      for (IProject project : projects) {
	         if (!project.isOpen() || !project.isNatureEnabled(JAVANATURE)) { // Check if we have a Java project.
	            continue;
	         }
	         analyzePackages(JavaCore.create(project).getPackageFragments());
	      }
	   }
	
	   protected void analyzePackages(IPackageFragment[] packages) throws CoreException, JavaModelException {
	      // =============================================================
	      // 2nd step: Packages
	      // =============================================================
	      for (IPackageFragment iPackage : packages) {
	         if (iPackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
	            if (iPackage.getCompilationUnits().length < 1) {
	               continue;
	            }
	            analyzeCompilationUnit(iPackage.getCompilationUnits());
	         }
	      }
	   }
	
	   private void analyzeCompilationUnit(ICompilationUnit[] iCompilationUnits) throws JavaModelException {
	      // =============================================================
	      // 3rd step: ICompilationUnits
	      // =============================================================
	      for (ICompilationUnit iUnit : iCompilationUnits) {
	         CompilationUnit compilationUnit = UtilAST.parse(iUnit);
	         NonParameterMethodsDefUseVisitor javaASTvisitor = new NonParameterMethodsDefUseVisitor(compilationUnit);
	         compilationUnit.accept(javaASTvisitor);
	         dataList.add(javaASTvisitor.getdefUseMap());
	      }
	   }
	
	   public List<Map<IVariableBinding, DefUseModel>> getAnalysisDataList() {
	      return dataList;
	   }
}
