package ae.nlp.biocreative;


import com.pengyifan.bioc.io.BioCCollectionReader;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.net.URL;


class Parser {

    BioCCollectionReader getBioCCollection(File file) throws IOException, SAXException, XMLStreamException, ParserConfigurationException {
        //Remove DTD def, else the xml parser attempts to find the DTD ..
        removeDtd(file.getAbsolutePath());
        return new BioCCollectionReader(new FileInputStream(file.getAbsolutePath()));
    }


    public void removeDtd(String filePath) throws IOException, XMLStreamException {
        XMLEventWriter output = null;
        XMLEventReader input = null;
        StringWriter stringWriter = new StringWriter();

        try {
            XMLInputFactory inFactory = XMLInputFactory.newFactory();
            XMLOutputFactory outFactory = XMLOutputFactory.newFactory();
            inFactory.setProperty(XMLInputFactory.SUPPORT_DTD, false);
            input = inFactory.createXMLEventReader(
                    new FileInputStream(filePath));
            XMLEventReader filtered = inFactory.createFilteredReader(
                    input, new DTDFilter());
            output = outFactory.createXMLEventWriter(stringWriter
            );

            output.add(filtered);


            try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {


                bw.write(stringWriter.toString());

                // no need to close it.
                //bw.close();


            }
        }
        finally {
            if (output != null)
                output.close();

            if (input != null) {
                input.close();
            }
        }

    }

    static class DTDFilter implements EventFilter {
        @Override
        public boolean accept(XMLEvent event) {
            return event.getEventType() != XMLStreamConstants.DTD;
        }

    }
}
