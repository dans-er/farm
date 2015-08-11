package nl.knaw.dans.farm.barn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import nl.knaw.dans.farm.Analyzer;
import nl.knaw.dans.farm.Discriminator;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.FileIterator;
import nl.knaw.dans.farm.ProcessingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Conveyor
{

    private static Logger logger = LoggerFactory.getLogger(Conveyor.class);

    private final FileIterator fileIterator;
    private List<Analyzer> analyzers = new ArrayList<Analyzer>();
    private List<Discriminator> discriminators = new ArrayList<Discriminator>();
    private Reporter reporter;

    public Conveyor(FileIterator fileIterator)
    {
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

    public void addAnalyzer(int index, Analyzer analyzer)
    {
        analyzers.add(index, analyzer);
    }

    public List<Discriminator> getDiscriminators()
    {
        return discriminators;
    }

    public void setDiscriminators(List<Discriminator> discriminators)
    {
        this.discriminators = discriminators;
    }

    public FileIterator getFileIterator()
    {
        return fileIterator;
    }

    public void run() throws Exception
    {
        int errorCount = 0;
        int fileCount = 0;
        while (fileIterator.hasNext())
        {

            FileInformationPackage fip = fileIterator.next();

            logger.info("Processing {}", fip.getIdentifier() + " errorCount=" + errorCount + " fileCount=" + fileCount + " " + fip.getFileMetadata().getFilename());
            try
            {
                if (isAccepted(fip))
                {
                    for (Analyzer analyzer : analyzers)
                    {
                        errorCount += tryProcess(fip, analyzer);
                    }
                }
            }
            catch (Exception e)
            {
                logger.error("Caught Exception: ", e);
                throw e;
            }
            finally
            {
                fip.close();
            }
            fileCount += 1;

        }
    }

    private boolean isAccepted(FileInformationPackage fip)
    {
        boolean accepted = true;
        for (Discriminator discriminator : getDiscriminators())
        {
            if (!discriminator.accept(fip))
            {
                accepted = false;
                break;
            }
        }
        return accepted;
    }

    protected int tryProcess(FileInformationPackage fip, Analyzer analyzer)
    {
        try
        {
            execute(fip, analyzer);
            return 0;
        }
        catch (Exception e)
        {
            logger.error("While processing " + fip.getIdentifier(), e);
            getReporter().reportError(fip.getIdentifier(), fip.getFileMetadata().getFilename(), e);
            return 1;
        }
    }

    protected void execute(final FileInformationPackage fip, final Analyzer analyzer) throws ProcessingException, InterruptedException, ExecutionException,
            TimeoutException
    {
        ExecutorService executor = Executors.newCachedThreadPool();
        Callable<Object> task = new Callable<Object>()
        {

            @Override
            public Object call() throws Exception
            {
                analyzer.process(fip);
                return null;
            }
        };
        Future<Object> future = executor.submit(task);
        future.get(300, TimeUnit.SECONDS);
    }

    public Reporter getReporter()
    {
        if (reporter == null)
        {
            reporter = new Reporter();
        }
        return reporter;
    }

    public void setReporter(Reporter reporter)
    {
        this.reporter = reporter;
    }

}
