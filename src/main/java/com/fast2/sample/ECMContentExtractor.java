package com.example;

import java.util.List;
import java.util.logging.Logger;

import com.arondor.common.management.mbean.annotation.Description;
import com.arondor.common.management.mbean.annotation.LongDescription;
import com.arondor.common.management.mbean.annotation.Mandatory;
import com.fast2.model.punnet.Punnet;
import com.fast2.model.task.annotation.TaskType;
import com.fast2.task.common.ContentSource;
import com.fast2.model.context.PatternResolver;
import com.fast2.model.punnet.ContentContainer;
import com.fast2.model.punnet.Data;
import com.fast2.model.punnet.Document;
import com.fast2.model.punnet.Punnet;
import com.fast2.model.task.TaskException;
import com.fast2.model.task.annotation.TaskType;
import com.fast2.task.common.BasicTask;

@Description("Short description of the module")
@LongDescription("Give here more details regarding what is supported, which API version might be used, etc.")
@TaskType(TaskType.Type.ContentSource)
public class ECMContentExtractor extends BasicTask
{
    private static final Logger LOG = Logger.getLogger(ECMSource.class);

    @Mandatory
    @Description("Connection provider")
    @LongDescription("Must have read permission")
    private ECMConnectionProvider credentials = null;

    @Description("Extract folders")
    @LongDescription("Save parent folders references as punnet data")
    private boolean extractFolders;

    @Description("Extract annotations")
    @LongDescription("Save annotations references as punnet data")
    private boolean extractAnnotations;

    @Description("Extract metadata")
    @LongDescription("Save metadata into document dataset")
    private boolean extractMetadata;

    @Override
    public boolean runTask(Punnet punnet) throws TaskException
    {
        if (credentials.isNull())
            throw new IllegalArgumentException("Missing mandatory credentials");

        for (Document documentF2 : punnet.getDocuments())
        {
            try
            {
                DocumentECM documentECM = fetchDocument(credentials, documentF2.getDocumentId());

                if (extractMetadata)
                    mapMetadata(documentECM, documentF2);

                if (extractAnnotations)
                    attachAnnotations(documentECM, documentF2);

                if (extractFolders)
                    attachFolders(documentECM, documentF2);
            }
            catch (Exception e)
            {
                throw new TaskException("Could not retrieve document with UUID: " + documentF2.getDocumentId(), e);
            }
        }
        return true;
    }

    // Getters and setters
    // ...
}
