package com.gnorsilva.android.reschecker;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import static com.gnorsilva.android.reschecker.Utils.log;

import nu.xom.Builder;
import nu.xom.Document;
import nu.xom.ParsingException;
import nu.xom.ValidityException;

public class ResourceExtractor {

	public static List<Resource> getResources(String projectPath) {
		List<Resource> resources = null;
		try {
			String packageName = getPackageName(projectPath);
			Class<?> rFile = loadRClass(projectPath, packageName);
			resources = extractResources(rFile);
		}  catch (ClassNotFoundException e) {
			log("Unable to load R.java as a class for " + projectPath);
			e.printStackTrace();
		} catch (MalformedURLException e) {
			log("Unable to read the \"gen\" folder for " + projectPath);
			e.printStackTrace();
		} catch (ValidityException e) {
			log("Unable to validate the AndroidManifest.xml for " + projectPath);
			e.printStackTrace();
		} catch (ParsingException e) {
			log("Unable to parse the AndroidManifest.xml for " + projectPath);
			e.printStackTrace();
		} catch (IOException e) {
			log("Unable to read the AndroidManifest.xml for " + projectPath);
			e.printStackTrace();
		} catch (PackageNotFoundException e) {
			log("Unable to find package attribute in AndroidManifest.xml for " + projectPath);
			e.printStackTrace();
		}
		return resources;
	}
	
	public static String getPackageName(String projectPath) throws ValidityException, ParsingException, IOException, PackageNotFoundException {
		Builder parser = new Builder();
		Document doc = parser.build(projectPath + "AndroidManifest.xml");
		String packageName = doc.getRootElement().getAttributeValue("package");
		if ( packageName == null || packageName.length() == 0){
			throw new PackageNotFoundException();
		}
		return packageName; 
	}
	
	protected static Class<?> loadRClass(String projectPath, String packageName) throws ClassNotFoundException, MalformedURLException {
		String genDirPath = projectPath + "gen";
		String rPath = genDirPath + File.separator + packageName.replace('.', File.separatorChar) + File.separator + "R.java";
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, rPath);
		URL url = new File(genDirPath).toURI().toURL();
		URLClassLoader loader = new URLClassLoader(new URL [] {url});
		return loader.loadClass(packageName + ".R");
	}
	
	private static List<Resource> extractResources(Class<?> rFile) {
		List<Resource> resources = new ArrayList<Resource>();
		for (Class<?> resTypeClass : rFile.getClasses()) {
			String resType = resTypeClass.getSimpleName();
			for ( Field resource : resTypeClass.getFields()){
				try{
				resources.add(new Resource(resType, resource.getName()));
				}catch(NullPointerException e){
					log("Unable to add resource for type " + resType + " and name " + resource.getName());
				}
			}
		}
		return resources;
	}

	public static class PackageNotFoundException extends Exception{
		private static final long serialVersionUID = -1378381061138252764L;
		
	}
}
