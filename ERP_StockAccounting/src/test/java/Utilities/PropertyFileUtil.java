package Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyFileUtil {
	public static String getValueForKey(String Key) throws Throwable
	{
		Properties config=new Properties();
		config.load(new FileInputStream("./PropertyFiles/Environmental.properties"));
		return config.getProperty(Key);
	}

}
