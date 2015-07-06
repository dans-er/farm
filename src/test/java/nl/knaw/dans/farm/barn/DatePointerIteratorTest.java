package nl.knaw.dans.farm.barn;

import nl.knaw.dans.farm.FileInformationPackage;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class DatePointerIteratorTest extends AbstractFedoraTest
{
    
//    @Test
//    public void looseTest() throws Exception {
//        FindObjectsResponse response = new FindObjects() //
//            .query("pid%7Eeasy-file:* mDate%3E%3D2014-10-20 mDate%3C2014-10-28") //
//            .pid() //
//            .execute();
//        for (String pid : response.getPids()) {
//            System.err.println(pid);
//        }
//        String token = response.getToken();
//        while (token != null) {
//            response = new FindObjects() //
//            .sessionToken(token) //
//            .pid() //
//            .execute();
//            System.err.println();
//            for (String pid : response.getPids()) {
//                System.err.println(pid);
//            }
//            token = response.getToken();
//        }
//    }
    
    @Test
    public void datePointerIteratorTest() throws Exception {
        int maxCount = 1000000;
        int count = 0;
        DatePointerIterator dpi = new DatePointerIterator();
        //dpi.setStartDate("2015-04-17");
        while (dpi.hasNext() && count < maxCount) {
            FileInformationPackage fip = dpi.next();
            System.err.println(fip.getFileProfile().getLastModified());
            count += 1;
            fip.close();
        }
    }
    
    
}
