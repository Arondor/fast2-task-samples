package com.fast2.sample;

import com.fast2.model.punnet.ContentContainer;
import com.fast2.model.punnet.Document;
import com.fast2.model.punnet.Punnet;
import com.fast2.model.task.TaskException;
import com.fast2.task.common.BasicTask;

public class PunnetManipulation extends BasicTask
{
    public boolean runTask(Punnet punnet) throws TaskException
    {
        for (Document doc : punnet.getDocuments())
        {
            /*
             * Get existing values from the document
             */
            String docId = doc.getDataSet().getDataValue("DocumentId");
            String sponsor = doc.getDataSet().getDataValue("Sponsor");
            String study = doc.getDataSet().getDataValue("Study");
            String studyCountry = doc.getDataSet().getDataValue("Study_Country");
            String studySite = doc.getDataSet().getDataValue("Study_Site");

            /*
             * Create new properties from provided values
             */
            doc.getDataSet().addData("someaspect:SponsorName", "String", sponsor);
            doc.getDataSet().addData("someaspect:StudySite", "String", studySite);

            /*
             * Create the folder path to file the document to
             */
            doc.getFolderReferenceSet()
                    .addFolderReference("/" + sponsor + "/" + study + "/" + studyCountry + "/" + studySite);

            /*
             * Create a contentContainer with property URL equals to documentId
             */
            ContentContainer container = getManager().getPunnetContentFactory().createContent(doc, docId);
            doc.getContentSet().add(container);

        }
        return true;
    }

}
