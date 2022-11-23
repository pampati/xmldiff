import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TestPOMDiff {

    public static void main(String args[]){

        try {
            List<String> xpaths =getXPathsOfKeys();
            List<String> values;
            List<String> macthingvalues =new ArrayList<>();
            List<String> notmacthingvalues= new ArrayList<>();
            //Add these lines in the code
            List<String> notmacthingXpaths= new ArrayList<>();
            List<String> finalResults= new ArrayList<>();
            ////
            int CountOfnoMatch=0;
            int CountofMatch=0;
            System.out.println(xpaths);
            for(int k=0;k<xpaths.size();k++) {
                values=printValues(xpaths.get(k), xpaths.get(k+1));
                k++;
                //update this below
                if(!values.get(0).split(":")[0].equals(values.get(1).split(":")[0])){
                    //update this above
                    CountOfnoMatch++;
                    notmacthingvalues.add(values.get(0).split(":")[0]);
                    notmacthingvalues.add(values.get(1).split(":")[0]);
                    //update this below
                    notmacthingXpaths.add(values.get(0).split(":")[1]);
                    notmacthingXpaths.add(values.get(1).split(":")[1]);
                    finalResults.add(values.get(0).split(":")[1]+":"+values.get(0).split(":")[0]);
                    finalResults.add(values.get(1).split(":")[1]+":"+values.get(1).split(":")[0]);
                    //update this below
                }else{
                    CountofMatch++;
                    macthingvalues.add(values.get(0).split(":")[0]);
                    macthingvalues.add(values.get(1).split(":")[0]);
                }
            }
            System.out.println("No of not matching values " + CountOfnoMatch);
            System.out.println("No of  matching values " + CountofMatch);

            System.out.println("List of matched values" + macthingvalues);
            System.out.println("List of not matched values " + notmacthingvalues);
            //update this below
            System.out.println("List of not matched xpaths " + notmacthingXpaths);
            System.out.println("List of not matched xpaths " + finalResults);
            ////update this above
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getXpath(String parentKey,String childKey) throws FileNotFoundException {
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader(System.getProperty("user.dir")+"/src/test/data/xpathlocators.json")) {
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObject = (JSONObject) obj;
            JSONObject jsonChildObject = (JSONObject)jsonObject.get(parentKey);
            String path = (String) jsonChildObject.get(childKey);
           // System.out.println(path);
            return path;
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
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
    public static List<String> printValues(String xpathinfile1,String xpathinfile2) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        List<String> values= new ArrayList<>();
        int notmatchcount;
        String firstValue = null;
        String secondValue = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setNamespaceAware(true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(new File(System.getProperty("user.dir")+"/src/test/data/testpom1.xml"));
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        //need to update the first xapath
        XPathExpression expr = xpath.compile(xpathinfile1);
        Object exprValue = expr.evaluate(doc, XPathConstants.STRING);
        if (exprValue != null) {
            firstValue = exprValue.toString();
        }
        doc = db.parse(new File(System.getProperty("user.dir")+"/src/test/data/testpom2.xml"));
        //need to replace the second xpath
        expr = xpath.compile(xpathinfile2);
        exprValue = expr.evaluate(doc, XPathConstants.STRING);
        if (exprValue != null) {
            secondValue = exprValue.toString();
        }
        if (firstValue != null && secondValue != null) {
            System.out.println(firstValue);
            System.out.println(secondValue);

            //Add these lines in the code
            values.add(firstValue+":"+xpathinfile1);
            values.add(secondValue+":"+xpathinfile2);
        }
        return values;
    }
}
