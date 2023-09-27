package utilities;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import model.Element;

/**
 * utility class to load properties from file
 * @author Matteo
 *
 */
public abstract class LoadProperties {
	
	/**
	 * loads properties from a file
	 * @param inputMap the map with a string (description) as key, and the relevant class as value
	 * @param filePath the file path where properties are stored
	 */
	public static void loadStringClassProperties(Map<String, Class<? extends Element>> inputMap, String filePath)
	{
		Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
            	String className = (String) properties.get(key);
            	@SuppressWarnings("unchecked")
				Class<? extends Element> c = (Class<? extends Element>) Class.forName(className);
            	inputMap.put((String) key, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * loads properties from a file
	 * @param inputMap the map with a string (description) as key, and a string as value (used to load images for explosions for example)
	 * @param filePath the file path where properties are stored
	 */
	public static void loadStringStringProperties(Map<String, String> inputMap, String filePath) {
		Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
            	String value = (String) properties.get(key);
            	inputMap.put((String) key, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * loads properties from a file
	 * @param inputMap the map with a direction as key, and a string as value (used to load images for characters)
	 * @param filePath the file path where properties are stored
	 */
	public static void loadDirectionStringProperties(Map<model.Direction, String> inputMap, String filePath) {
		Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
            	model.Direction d = model.Direction.valueOf((String) key);
            	String value = (String) properties.get(d.toString());
            	inputMap.put((model.Direction) d, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}


}
