package nl.knaw.dans.farm.barn;

import static org.junit.Assert.assertEquals;

import org.apache.commons.io.IOUtils;
import org.junit.Ignore;
import org.junit.Test;

import com.yourmediashelf.fedora.client.request.GetDatastream;
import com.yourmediashelf.fedora.client.request.GetDatastreamDissemination;
import com.yourmediashelf.fedora.client.response.FedoraResponse;
import com.yourmediashelf.fedora.client.response.GetDatastreamResponse;

@Ignore
public class FedoraTester extends AbstractFedoraTest
{
    
    @Test
    public void getDatastream() throws Exception {
        String pid = "fedora-system:ContentModel-3.0";
        String dsId = "RELS-EXT";
        GetDatastreamResponse response = new GetDatastream(pid, dsId).execute();
        //System.out.println(IOUtils.toString(response.getEntityInputStream()));
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void getDatastreamDissemination() throws Exception {
        String pid = "fedora-system:ContentModel-3.0";
        String dsId = "RELS-EXT";
        FedoraResponse response = new GetDatastreamDissemination(pid, dsId).execute();
        //System.out.println(IOUtils.toString(response.getEntityInputStream()));
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void getFileMetadata() throws Exception {
        String pid = "easy-file:7382";
        String dsId = "EASY_FILE_METADATA";
        FedoraResponse response = new GetDatastreamDissemination(pid, dsId).execute();
        System.out.println(IOUtils.toString(response.getEntityInputStream()));
    }
    

}
