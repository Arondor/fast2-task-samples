package com.fast2.sample;

import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fast2.model.manager.Manager;
import com.fast2.model.punnet.ContentContainer;
import com.fast2.model.punnet.Document;
import com.fast2.model.punnet.DocumentId;
import com.fast2.model.punnet.Punnet;
import com.fast2.model.punnet.PunnetId;
import com.fast2.model.task.TaskException;

public class TestInjector
{

    private static final String TEST_FILE_PATH = "src/test/resources/bill.pdf";

    Manager manager;

    Injector injector;

    @Before
    public void init() throws TaskException
    {
        manager = Manager.buildDefaultManager();
        injector = new Injector();
        injector.setManager(manager);
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
        // Document Content creation
        ContentContainer content = manager.getPunnetContentFactory().createContent(document, TEST_FILE_PATH);
        content.setMimeType("application/pdf");

        document.getContentSet().add(content);
        punnet.addDocument(document);

        return (punnet);
    }

    @Test
    public void testInjection() throws TaskException
    {
        Punnet punnet = buildPunnet();

        punnet.getDocuments().get(0).getFolderReferenceSet().addFolderReference("folder/injection");
        injector.runTask(punnet);

        assertNotNull(punnet.getDocuments().get(0).getDataSet().getDataValue("injectionId"));
    }

}
