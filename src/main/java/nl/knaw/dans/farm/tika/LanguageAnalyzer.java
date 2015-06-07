package nl.knaw.dans.farm.tika;

import java.io.IOException;

import nl.knaw.dans.farm.Analyzer;
import nl.knaw.dans.farm.FileInformationPackage;
import nl.knaw.dans.farm.ProcessingException;
import nl.knaw.dans.farm.utils.MediaTypeFilter;

import org.apache.commons.io.FileUtils;
import org.apache.tika.language.LanguageIdentifier;
import org.apache.tika.language.LanguageProfile;

public class LanguageAnalyzer implements Analyzer
{
    
    private static MediaTypeFilter MEDIA_TYPE_FILTER = new MediaTypeFilter( //
            MediaTypeFilter.Type.APPLICATION, //
            MediaTypeFilter.Type.MESSAGE , //
            MediaTypeFilter.Type.TEXT);

    @Override
    public void process(FileInformationPackage fip) throws ProcessingException
    {
        String mediaType = fip.getFarmData().getMediaType();
        if (mediaType == null | MEDIA_TYPE_FILTER.isIncluded(mediaType)) {
            try
            {
                LanguageIdentifier lang = new LanguageIdentifier(new LanguageProfile(
                        FileUtils.readFileToString(fip.getFile())));
                fip.getFarmData().setLanguage(lang.getLanguage()).setLanguageCertain(lang.isReasonablyCertain());
            }
            catch (IOException e)
            {
                throw new ProcessingException(e);
            }
        }
    }

}
