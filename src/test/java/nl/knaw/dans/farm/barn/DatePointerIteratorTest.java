package nl.knaw.dans.farm.barn;

import org.joda.time.DateTime;
import org.junit.Test;

import com.yourmediashelf.fedora.client.request.FindObjects;
import com.yourmediashelf.fedora.client.response.FindObjectsResponse;

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
        DatePointerIterator dpi = new DatePointerIterator();
        dpi.setStartDate("2014-10-20");
        while (dpi.hasNext()) {
            System.err.println(dpi.next().getFileProfile().getLastModified());
        }
    }
    
}
