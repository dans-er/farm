package nl.knaw.dans.farm.barn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

import nl.knaw.dans.farm.FarmException;
import nl.knaw.dans.farm.FileInformationPackage;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatePointerIterator extends FedoraFileIterator
{
    
    public static final String START_DATE = "2011-01-01";
    public static final String FILE_LOCATION = "datepointer.txt";
    
    private static Logger logger = LoggerFactory.getLogger(DatePointerIterator.class);
    
    private String fileLocation;
    private String startDate;
    private DateTime datePointer;
    private RandomAccessFile raf;
    private DateTimeFormatter format;
    private int counter;
    
    public DatePointerIterator() {
        
    }
    
    @Override
    public FileInformationPackage next()
    {
        counter += 1;
        return super.next();
    }
    
    @Override
    public boolean hasNext()
    {
        if (super.hasNext()) {
            return true;
        } else {
            String currentIdentifier = null;
            while (!super.hasNext() && getDatePointer().isBeforeNow()) {
                writeDate(datePointer);
                datePointer = datePointer.plusDays(1);
                currentIdentifier = findNextIdentifier();
                logger.debug("Looking for {} and starting with {}", getFormat().print(datePointer), currentIdentifier);
            }
            if (currentIdentifier == null) {
                close();
            }
            return currentIdentifier != null;
        }
    }
    
    private void close()
    {
        if (raf != null) {
            try
            {
                raf.close();
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }

    public String getFileLocation()
    {
        if (fileLocation == null) {
            fileLocation = FILE_LOCATION;
        }
        return fileLocation;
    }

    public void setFileLocation(String fileLocation)
    {
        this.fileLocation = fileLocation;
    }
    
    protected DateTime getDatePointer() {
        if (datePointer == null) {
            String start = getStartDate();
            logger.debug("starting with '{}'", start);
            datePointer = new DateTime(start);
        }
        return datePointer;
    }

    public String getStartDate()
    {
        if (startDate == null) {
            try
            {
                startDate = readStartDate();
            }
            catch (FarmException e)
            {
                throw new RuntimeException(e);
            }
            if (startDate == null) {
                startDate = START_DATE;
            }
        }
        return startDate;
    }

    protected String readStartDate() throws FarmException
    {
        logger.debug("Reading startDate from {}.", getFileLocation());
        RandomAccessFile ra = getRaf();
        String lastDate = null;
        String line = null;
        try
        {
            while ((line = ra.readLine()) != null) {
                lastDate = line.split(";")[0];
            }
        }
        catch (IOException e)
        {
            throw new FarmException(e);
        }
        return lastDate;
    }
    
    protected void writeDate(DateTime date) {
        try
        {
            getRaf().writeUTF(getFormat().print(date) + ";" + counter + "\n");
            counter = 0;
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
    
    private RandomAccessFile getRaf() {
        if (raf == null) {
            try
            {
                raf = new RandomAccessFile(getFileLocation(), "rw");
            }
            catch (FileNotFoundException e)
            {
                throw new RuntimeException(e);
            }
        }
        return raf;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }


    @Override
    protected String getQuery()
    {
        // "pid%7Eeasy-file:* mDate%3E%3D2014-10-20 mDate%3C2014-10-28"
        DateTime start = getDatePointer();
        DateTime end = start.plusDays(1);
        return new StringBuilder() //
            .append("pid%7Eeasy-file:*").append(" ")
            .append("mDate%3E%3D").append(getFormat().print(start)).append(" ")
            .append("mDate%3C").append(getFormat().print(end))
            .toString();
    }
    
    private DateTimeFormatter getFormat() {
        if (format == null) {
            format = DateTimeFormat.forPattern("yyyy-MM-dd");
        }
        return format;
    }

}
