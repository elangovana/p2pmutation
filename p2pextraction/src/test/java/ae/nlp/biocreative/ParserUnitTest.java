package ae.nlp.biocreative;

import ae.nlp.biocreative.helpers.ConfigHelper;
import com.pengyifan.bioc.io.BioCCollectionReader;
import org.apache.commons.configuration.ConfigurationException;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;
import org.testng.Assert;
import org.testng.annotations.*;



public class ParserUnitTest {
    private Parser sut;
    private static File relationshipDatafile;
    private static final Logger logger =
            Logger.getLogger(ParserUnitTest.class.getName());



    @BeforeClass
    static void  classSetup() throws IOException, ConfigurationException {

        relationshipDatafile = Paths.get(ConfigHelper.getTestDataDirectory(), "relationtrainingdata.xml").toAbsolutePath().toFile();

    }


    @BeforeTest
    void setUp() throws IOException {
        sut = new Parser();
    }

    @AfterTest
    void tearDown() {

    }

    @Test
    void process() throws IOException, XMLStreamException, ParserConfigurationException, SAXException {
        //Act
        BioCCollectionReader actual = sut.getBioCCollection(relationshipDatafile);
        //Assert
        Assert.assertEquals(actual.readCollection().getDocmentCount(),7);
    }


}