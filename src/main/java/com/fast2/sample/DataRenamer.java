package com.fast2.sample;

import com.arondor.common.management.mbean.annotation.Description;
import com.arondor.common.management.mbean.annotation.LongDescription;
import com.fast2.model.punnet.Document;
import com.fast2.model.punnet.Punnet;
import com.fast2.model.task.TaskException;
import com.fast2.model.task.annotation.TaskType;
import com.fast2.task.common.BasicTask;

@Description("Short Task Description")
@LongDescription("Longer description of what the task done")
@TaskType(TaskType.Type.Transformer)
public class DataRenamer extends BasicTask
{
    @Description("Key of data to rename")
    @LongDescription("Data name needing to be renamed")
    private String srcData;

    @Description("New key name of data")
    @LongDescription("New name of the remaned data")
    private String targetData;

    public boolean runTask(Punnet punnet) throws TaskException
    {
        if (punnet.getDocuments() == null)
        {
            throw new TaskException("No document found in the punnet");
        }

        for (Document document : punnet.getDocuments())
        {
            String value = document.getDataSet().getDataValue(srcData);

            if (value != null && document.getDataSet().removeData(srcData))
            {
                document.getDataSet().addData(targetData, "String", value);
            }
        }

        return false;
    }

    public String getSrcData()
    {
        return srcData;
    }

    public void setSrcData(String srcData)
    {
        this.srcData = srcData;
    }

    public String getTargetData()
    {
        return targetData;
    }

    public void setTargetData(String targetdata)
    {
        this.targetData = targetdata;
    }
}
