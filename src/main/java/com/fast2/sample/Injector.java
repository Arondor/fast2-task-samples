package com.fast2.sample;

import java.util.List;

import org.apache.log4j.Logger;

import com.fast2.model.punnet.Document;
import com.fast2.model.punnet.Folder;
import com.fast2.model.punnet.Punnet;
import com.fast2.model.task.TaskException;
import com.fast2.model.task.annotation.TaskType;
import com.fast2.task.common.BasicTask;

@TaskType(TaskType.Type.Loader)
public class Injector extends BasicTask
{
    private static final Logger LOG = Logger.getLogger(Injector.class);

    @Override
    public boolean runTask(Punnet punnet) throws TaskException
    {

        List<Document> documentsList = punnet.getDocuments();
        if (documentsList != null)
        {
            for (Document doc : punnet.getDocuments())
            {
                try
                {
                    inject(punnet, doc);
                }
                catch (TaskException e)
                {
                    throw new TaskException(
                            "Could not inject document : " + doc.getDocumentId().toString() + " because " + e);
                }
            }
        }
        else
        {
            LOG.debug("No document to inject");
        }
        return true;

    }

    private void inject(Punnet punnet, Document doc) throws TaskException
    {
        LOG.info("Start of injection for document : " + doc.getDataSet().getDataValue("name"));

        List<Folder> foldersList = doc.getFolderReferenceSet().getFinalFolderReferences();
        if (!foldersList.isEmpty())
        {
            for (Folder folder : doc.getFolderReferenceSet().getFinalFolderReferences())
            {
                // Injection
                doc.getDataSet().addData("injectionId", "String", "docId");

            }
        }
        else
        {
            LOG.debug("Could not inject document : " + doc.getDataSet().getDataValue("name")
                    + " because no given folder");

        }

    }

}
