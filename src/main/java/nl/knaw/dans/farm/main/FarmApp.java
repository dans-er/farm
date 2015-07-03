package nl.knaw.dans.farm.main;

import nl.knaw.dans.farm.barn.Conveyor;
import nl.knaw.dans.farm.barn.DatePointerIterator;
import nl.knaw.dans.farm.barn.FedoraConnector;
import nl.knaw.dans.farm.fed.ProfileAnalyzer;
import nl.knaw.dans.farm.fits.FitsAnalyzer;
import nl.knaw.dans.fits.FitsWrap;

public class FarmApp
{

    public static void main(String[] args) throws Exception
    {
        FedoraConnector connector = new FedoraConnector();
        connector.connect();
        
        DatePointerIterator iter = new DatePointerIterator();
        Conveyor conveyor = new Conveyor(iter);
        
        ProfileAnalyzer profileAnalyzer = new ProfileAnalyzer();
        conveyor.addAnalyzer(0, profileAnalyzer);
        
        FitsWrap.setFitsHome("/Users/ecco/git/fits-api/fits-0.8.5");
        FitsAnalyzer fitsAnalyzer = new FitsAnalyzer();
        conveyor.addAnalyzer(1, fitsAnalyzer);
        
        conveyor.run();
    }

}
