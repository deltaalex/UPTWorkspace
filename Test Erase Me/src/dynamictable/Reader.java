package dynamictable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class Reader {

	public Vector<BuildInfo> loadData(String root, String dirFilter, String fileFilter) throws IOException, FileNotFoundException
	{		
		File rootDir = new File(root);
		if(!rootDir.exists())
		{
			throw new IOException("Root folder " + root + " does not exist");
		}
		
		Vector<BuildInfo> builds = new Vector<BuildInfo>();
		
		search(builds, rootDir, dirFilter, fileFilter);
		
		return builds;
	}
	
	private void search(Vector<BuildInfo> builds, File root, String dirFilter, String fileFilter) throws FileNotFoundException
	{
		if(root.isDirectory())
		{
			File[] children = root.listFiles();
			
			for(File child : children)
			{
				if(child.isDirectory() && child.getName().matches(dirFilter))
				{					
					BuildInfo build = addNewBuild(child, fileFilter);
					if(build != null)
						builds.add(build);
				}
			}
		}
	}
	
	private BuildInfo addNewBuild(File root, String fileFilter) throws FileNotFoundException
	{	
		if(root.isDirectory())
		{
			SortedSet<TestInfo> cities = new TreeSet<TestInfo>();
			File[] children = root.listFiles();
			
			for(File child : children)
			{
				if(child.isFile())
				{					
					if(child.getName().matches(fileFilter))
					{
						return addNewTests(cities, child);
					}
				}
			}						
		}
		
		return null;
	}
	
	private BuildInfo addNewTests(SortedSet<TestInfo> cities, File file) throws FileNotFoundException
	{			
		try {
			return new XMLLoader().loadAndVerifyXML(file.getAbsolutePath());
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MissingElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
		return null;
	}
}
