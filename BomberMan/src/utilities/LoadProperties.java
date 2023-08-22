package utilities;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import model.Element;

public class LoadProperties {
	
	public static void loadStringClassProperties(Map<String, Class<? extends Element>> inputMap, String filePath)
	{
		Properties properties = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            properties.load(input);
            Set<Object> keys = properties.keySet();
            for (Object key : keys) {
            	String className = (String) properties.get(key);
            	Class<? extends Element> c = (Class<? extends Element>) Class.forName(className);
            	inputMap.put((String) key, c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
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
