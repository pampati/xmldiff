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

public class TestPOMDiff {

    public static void main(String args[]){

        try {
           String xpathOfversionInFile1 = getXpath("testpom1locations","version");
            String xpathOfversionInFile2 = getXpath("testpom2locations","version");
            printValues(xpathOfversionInFile1,xpathOfversionInFile2);
            String xpathOfArtifactIdInFile1 = getXpath("testpom1locations","artifactId");
            String xpathOfArtifactIdInFile2 = getXpath("testpom2locations","artifactId");
            printValues(xpathOfArtifactIdInFile1,xpathOfArtifactIdInFile2);
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
    public static void printValues(String xpathinfile1,String xpathinfile2) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
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
        }
    }
}
