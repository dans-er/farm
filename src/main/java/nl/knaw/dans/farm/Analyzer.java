package nl.knaw.dans.farm;


public interface Analyzer
{
    
    void process(FileInformationPackage fip) throws ProcessingException;

}
