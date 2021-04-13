package com.fast2.sample;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fast2.model.manager.Manager;
import com.fast2.model.punnet.Document;
import com.fast2.model.punnet.DocumentId;
import com.fast2.model.punnet.Punnet;
import com.fast2.model.punnet.PunnetId;
import com.fast2.model.task.TaskException;

public class TestDataRenamerIT
{
    private static final Logger LOG = Logger.getLogger(TestDataRenamerIT.class);

    Manager manager;

    DataRenamer task;

    @Before
    public void init() throws TaskException
    {
        manager = Manager.buildDefaultManager();
        task = new DataRenamer();
        task.setManager(manager);
    }

    @After
    public void tearDown() throws TaskException
    {

    }

    private Punnet buildPunnet()
    {
        // Punnet creation
        Punnet punnet = manager.getPunnetFactory().createEmptyPunnet();
        punnet.setPunnetId(new PunnetId());
        // Document creation
        Document document = new com.fast2.pojo.punnet.Document();
        document.setDocumentId(DocumentId.id());

        punnet.addDocument(document);

        return (punnet);
    }

    @Test
    public void testRename() throws TaskException
    {
        Punnet punnet = buildPunnet();
        punnet.getDocuments().get(0).getDataSet().addData("name", "String", "myName");

        task.setSrcData("name");
        task.setTargetData("title");

        task.runTask(punnet);

        assertNotNull(punnet.getDocuments().get(0).getDataSet().getDataValue("title"));
    }

    @Test
    public void testRenameUnexistingData() throws TaskException
    {
        Punnet punnet = buildPunnet();

        task.setSrcData("name");
        task.setTargetData("title");

        task.runTask(punnet);

        assertNull(punnet.getDocuments().get(0).getDataSet().getDataValue("title"));
    }

}
