package system.operations;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;


public class DataPersistence {
	
	private XStream xstream = new XStream(new DomDriver("UTF-8"));
	
	public void saveSystem(DataSystem sys) {
		File f = new File("data.xml");
		
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
		xml += xstream.toXML(sys);
		
		pw.print(xml);
		pw.close();
		System.out.println("System saved");
	}
	
	public DataSystem loadSystem() {
		File file = new File("data.xml");
		
		if(!file.exists()) {
			return new DataSystem();
		} else {
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return (DataSystem) xstream.fromXML(fis);
		}
	}
}

