package nl.knaw.dans.farm.barn;

import java.io.File;
import java.io.IOException;
import java.util.List;

import nl.knaw.dans.farm.FileInformationPackage;

import org.apache.commons.io.FileUtils;
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
    
    private DateTimeFormatter format;
    private int counter;
    
    public DatePointerIterator() {
        
    }
    
    @Override
    public FileInformationPackage next()
    {
        counter += 1;
        logger.info("Fip " + counter + " for " + getFormat().print(datePointer));
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
                writeDate(datePointer, counter);
                datePointer = datePointer.plusDays(1);
                counter = 0;
                currentIdentifier = findNextIdentifier();
                logger.debug("Looking for {} and starting with {}", getFormat().print(datePointer), currentIdentifier);
            }
            return currentIdentifier != null;
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
            datePointer = getStartDate();
            logger.debug("starting with '{}'", getFormat().print(datePointer));
        }
        return datePointer;
    }

    public DateTime getStartDate()
    {
        if (startDate != null) {
            return new DateTime(startDate);
        } else {
            String lastDate = readStartDate();
            if (lastDate != null) {
                return new DateTime(lastDate).plusDays(1);
            } else {
                return new DateTime(START_DATE);
            }
        }
    }

    protected String readStartDate()
    {
        logger.debug("Reading startDate from {}.", getFileLocation());
        File file = new File(getFileLocation());
        String lastDate = null;
        if (file.exists()) {
            try
            {
                List<String> lines = FileUtils.readLines(file, "UTF-8");
                if (lines.size() > 0) {
                    lastDate = lines.get(lines.size() - 1).split(";")[0];
                }
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
        return lastDate;
    }
    
    protected void writeDate(DateTime date, int count) {
        File file = new File(getFileLocation());
        String line = getFormat().print(date) + ";" + count + "\n";
        try
        {
            FileUtils.write(file, line, "UTF-8", true);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
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
