package nl.knaw.dans.farm.tika;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class TestHandler implements ContentHandler
{
    
    boolean ignoreWhitespace = true;
    private StringBuilder sb = new StringBuilder();
    

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        //System.err.println("characters: " + start + " " + length + "\n" + String.copyValueOf(ch, start, length));
        sb.append(ch, start, length);
    }

    @Override
    public void endDocument() throws SAXException
    {
        System.err.println("endDocument");

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        System.err.println("endElement: " + uri + " " + localName + " " + qName + "\n" + sb.toString());
        sb = new StringBuilder();
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException
    {
        System.err.println("endPrefixMapping: " + prefix );

    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
    {
        if (!ignoreWhitespace)
            System.err.println("ignorableWhitespace: " + start + " " + length + "\n" + String.copyValueOf(ch, start, length));

    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException
    {
        System.err.println("processingInstruction: " + target + " " + data);

    }

    @Override
    public void setDocumentLocator(Locator locator)
    {
        System.err.println("setDocumentLocator: " + locator);

    }

    @Override
    public void skippedEntity(String name) throws SAXException
    {
        System.err.println("skippedEntity: " + name);

    }

    @Override
    public void startDocument() throws SAXException
    {
        System.err.println("startDocument");

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
    {
        System.err.println("startElement: " + uri + " " + localName + " " + qName);
        for (int i = 0; i < atts.getLength(); i++)
        {
            System.err.println("\t" + atts.getLocalName(i) + "=" + atts.getValue(i));
        }

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException
    {
        System.err.println("startPrefixMapping: " + prefix + " " + uri);

    }

}
