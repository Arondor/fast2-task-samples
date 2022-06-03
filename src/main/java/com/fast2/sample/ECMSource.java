package com.example;

import org.apache.log4j.Logger;

import com.arondor.common.management.mbean.annotation.Description;
import com.arondor.common.management.mbean.annotation.Example;
import com.arondor.common.management.mbean.annotation.LongDescription;
import com.arondor.common.management.mbean.annotation.Mandatory;
import com.fast2.model.punnet.DataSet;
import com.fast2.model.punnet.Document;
import com.fast2.model.punnet.DocumentId;
import com.fast2.model.punnet.Punnet;
import com.fast2.model.task.annotation.TaskType;
import com.fast2.task.common.BasicSource;

@Description("Short description of the module")
@LongDescription("Give here more details regarding what is supported, which API version might be used, etc.")
@TaskType(TaskType.Type.Source)
public class ECMSource extends BasicSource
{
    private static final Logger LOG = Logger.getLogger(ECMSource.class);

    @Mandatory
    @Description("Connection provider")
    @LongDescription("Must have read permission")
    private ECMConnectionProvider credentials = null;

    @Mandatory
    @Description("Query")
    @LongDescription("Criteria for extraction, which can be a list of folders, a list of storage repositories")
    List<String> extractionCriteria;

    @Description("Store ECM document UUID")
    private boolean storeDocUUID;


    @Override
    public void produce(Consumer consumer) throws InterruptedException
    {
        if(credentials.isNull())
            throw new IllegalArgumentException("Missing mandatory credentials");
        
        for (Criterion criterion : extractionCriteria){

            ResultSet queryResults = queryECM(credentials, criteria);
            
            // Iterate over query results
            for (ECMDocument documentECM : queryResults) {

                Punnet punnet = getManager().getPunnetFactory().createEmptyPunnet();

                if (storeDocUUID)
                    Document documentF2 = punnet.addDocument(DocumentId.id(documentECM.getUUID())); // provide ID
                else 
                    Document documentF2 = punnet.addDocument(DocumentId.id()); // generate a random ID

                consumer.push(punnet);
            }
        }
        
    }

    // Getters and setters
    ...
}
