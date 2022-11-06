

import java.io.FileNotFoundException;

import java.io.FileReader;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import org.json.simple.JSONArray;

import org.json.simple.JSONObject;

import org.json.simple.parser.JSONParser;

import org.json.simple.parser.ParseException;



public class TestJsonloop {


    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException {

        // TODO Auto-generated method stub
                System.out.println(getXPathsOfKeys());

    }

    public static List<String> getXPathsOfKeys() throws IOException, ParseException {
        List<String> xpaths1 = new ArrayList<String>();
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "/src/test/data/xpathlocators.json"));
        JSONObject jsonObject = (JSONObject) obj;
        JSONObject obj1 = (JSONObject) jsonObject.get("testpom1locations");
        for (Iterator iterator = obj1.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            JSONObject obj2 = (JSONObject) jsonObject.get("testpom2locations");
            for (Iterator iterator1 = obj2.keySet().iterator(); iterator1.hasNext(); ) {
                String key1 = (String) iterator1.next();
                if (key1.equals(key)) {
                    System.out.println(obj1.get(key));
                    System.out.println(obj2.get(key1));
                    xpaths1.add((String) obj1.get(key));
                    xpaths1.add((String) obj2.get(key1));
                }
            }

        }
        return xpaths1;
    }

}

