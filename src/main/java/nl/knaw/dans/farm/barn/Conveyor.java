package nl.knaw.dans.farm.barn;

import java.util.ArrayList;
import java.util.List;

import nl.knaw.dans.farm.Analyzer;

public class Conveyor
{
    
    private List<Analyzer> analyzers = new ArrayList<Analyzer>();
    
    public Conveyor() {
        
    }

    public List<Analyzer> getAnalyzers()
    {
        return analyzers;
    }

    public void setAnalyzers(List<Analyzer> analyzers)
    {
        this.analyzers = analyzers;
    }
    
    

}
