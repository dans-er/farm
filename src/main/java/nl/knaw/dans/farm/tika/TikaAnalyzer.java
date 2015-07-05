package nl.knaw.dans.farm.tika;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nl.knaw.dans.farm.Analyzer;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.ProcessingException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class TikaAnalyzer implements Analyzer, ContentHandler
{
    
    private final AutoDetectParser parser;
    private List<FarmHandler> handlers = new ArrayList<FarmHandler>();
    
    public TikaAnalyzer()
    {
        parser = new AutoDetectParser();
    }

    public List<FarmHandler> getHandlers()
    {
        return handlers;
    }

    public void setHandlers(List<FarmHandler> handlers)
    {
        this.handlers = handlers;
    }

    public void addHandler(FarmHandler handler) {
        handlers.add(handler);
    }

    @Override
    public void process(FileInformationPackage fip) throws ProcessingException
    {
        for (FarmHandler handler : handlers) {
            handler.setFileInformationPackage(fip);
        }
        Metadata metadata = new Metadata();
        metadata.set(Metadata.RESOURCE_NAME_KEY, fip.getFileMetadata().getFilename());
        
        ParseContext context = new ParseContext();
        
        //TestHandler handler = new TestHandler();
        ContentHandler handler = this;
        
        try
        {
            parser.parse(fip.getInputStream(), handler, metadata, context);
            
        }
        catch (IOException | SAXException | TikaException e)
        {
            throw new ProcessingException(e);
        }
    }
    
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.characters(ch, start, length);
        }
    }

    @Override
    public void endDocument() throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.endDocument();;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.endElement(uri, localName, qName);
        }
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.endPrefixMapping(prefix);;
        }
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.ignorableWhitespace(ch, start, length);
        }
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.processingInstruction(target, data);
        }
    }

    @Override
    public void setDocumentLocator(Locator locator)
    {
        for (FarmHandler handler : handlers) {
            handler.setDocumentLocator(locator);
        }
    }

    @Override
    public void skippedEntity(String name) throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.skippedEntity(name);
        }
    }

    @Override
    public void startDocument() throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.startDocument();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.startElement(uri, localName, qName, atts);
        }
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException
    {
        for (FarmHandler handler : handlers) {
            handler.startPrefixMapping(prefix, uri);
        }
    }

}
