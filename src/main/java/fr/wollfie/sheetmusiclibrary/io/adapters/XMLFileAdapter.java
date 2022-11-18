package fr.wollfie.sheetmusiclibrary.io.adapters;

import com.google.common.base.Preconditions;
import fr.wollfie.sheetmusiclibrary.io.logging.Logger;
import org.controlsfx.control.PropertySheet;
import org.json.XML;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;

public class XMLFileAdapter {

    private static final DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();

    public static Function<XMLNode, Optional<XMLNode>> thenFirstChildWith(String tag) {
        return node -> node.getFirstChildWith(tag);
    }
    
    public static class XMLNode {
        private final Node node;

        public XMLNode(Node node) {
            this.node = node;
        }
        
        public static XMLNode getRootFrom(File file) throws ParserConfigurationException, IOException, SAXException {
            DocumentBuilder documentBuilder = DBF.newDocumentBuilder();
            Document document = documentBuilder.parse(file);
            document.getDocumentElement().normalize();

            return new XMLNode(document.getDocumentElement());
        }

        public Optional<XMLNode> getParentOfValue(String value) {
            Preconditions.checkNotNull(value);
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node child = nodeList.item(i);
                NodeList secondNodeList = child.getChildNodes();
                for (int j = 0; j < secondNodeList.getLength(); j++) {
                    Node secondChild = secondNodeList.item(j);
                    Node thirdChild = secondChild.getFirstChild();
                    if (thirdChild != null && value.equalsIgnoreCase(thirdChild.getNodeValue())) { 
                        return Optional.of(new XMLNode(child));
                    }
                }
            }
            return Optional.empty();
        }

        public Optional<XMLNode> getFirstChildWith(String tag) {
            Preconditions.checkNotNull(tag);
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node child = nodeList.item(i);
                if (tag.equalsIgnoreCase(child.getNodeName())) { return Optional.of(new XMLNode(child)); } 
            }
            return Optional.empty();
        }

        public List<XMLNode> getAllChildrenWith(String tag) {
            Preconditions.checkNotNull(tag);
            List<XMLNode> validChildren = new ArrayList<>();

            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node child = nodeList.item(i);
                if (tag.equalsIgnoreCase(child.getNodeName())) { validChildren.add(new XMLNode(child)); }
            }
            return validChildren;
        }

        public String getValue() {
            return node.getFirstChild().getTextContent();
        }
        
        public String getName() { return node.getNodeName(); }
    }
}
