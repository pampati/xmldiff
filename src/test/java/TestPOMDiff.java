import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;

public class TestPOMDiff {

    public static void main(String args[]){

        try {
            String firstValue = null;
            String secondValue = null;

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder db = dbf.newDocumentBuilder();

            Document doc = db.parse(new File(System.getProperty("user.dir")+"/src/test/data/testpom1.xml"));
            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();

            //need to update the first xapath
            XPathExpression expr = xpath.compile("//project/modelVersion");

            Object exprValue = expr.evaluate(doc, XPathConstants.STRING);

            if (exprValue != null) {
                firstValue = exprValue.toString();
            }

            doc = db.parse(new File(System.getProperty("user.dir")+"/src/test/data/testpom2.xml"));

            //need to replace the second xpath
            expr = xpath.compile("//dependencies/dependency/version");
            exprValue = expr.evaluate(doc, XPathConstants.STRING);

            if (exprValue != null) {
                secondValue = exprValue.toString();
            }

            if (firstValue != null && secondValue != null) {
                System.out.println(firstValue);
                System.out.println(secondValue);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
