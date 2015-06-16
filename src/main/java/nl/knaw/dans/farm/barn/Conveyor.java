package nl.knaw.dans.farm.barn;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nl.knaw.dans.farm.Analyzer;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.FileIterator;

public class Conveyor
{
    
    private static Logger logger = LoggerFactory.getLogger(Conveyor.class);
    
    private final FileIterator fileIterator;
    private List<Analyzer> analyzers = new ArrayList<Analyzer>();
    
    
    public Conveyor(FileIterator fileIterator) {
        this.fileIterator = fileIterator;
    }

    public List<Analyzer> getAnalyzers()
    {
        return analyzers;
    }

    public void setAnalyzers(List<Analyzer> analyzers)
    {
        this.analyzers = analyzers;
    }

    public FileIterator getFileIterator()
    {
        return fileIterator;
    }
    
    public void run() {
        while (fileIterator.hasNext()) {
            FileInformationPackage fip = fileIterator.next();
            try
            {
                for (Analyzer analyzer : analyzers) {
                    analyzer.process(fip);
                }
            }
            catch (Exception e)
            {
                logger.error("Caught Exception: ", e);
            } finally {
                fip.close();
            }
        }
    }
    
    

}
