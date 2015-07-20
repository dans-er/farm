package nl.knaw.dans.farm.main;

import nl.knaw.dans.farm.barn.Conveyor;
import nl.knaw.dans.farm.barn.FedoraConnector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class FarmApp
{
    public static final String APP_CONTEXT_LOCATION = "cfg/application-context.xml";
    
    private static Logger logger = LoggerFactory.getLogger(FarmApp.class);

    public static void main(String[] args) throws Exception
    {
        String appContextLocation;
        if (args.length > 0) {
            appContextLocation = args[0];
        } else {
            appContextLocation = APP_CONTEXT_LOCATION;
        }
        logger.info("Configuration file: {}", appContextLocation);
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(appContextLocation);
        
        try
        {
            FedoraConnector connector = (FedoraConnector) applicationContext.getBean("fedora-connector");
            connector.connect();

            Conveyor conveyor = (Conveyor) applicationContext.getBean("conveyor");
            conveyor.run();
        }
        catch (Exception e)
        {
            logger.error("Last capture caught: ", e);
            throw e;
        }
    }

}
