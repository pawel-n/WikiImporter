package wikiimporter;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import pl.edu.mimuw.wikiontology.pn347193.analysis.CategoryExtractor;
import pl.edu.mimuw.wikiontology.pn347193.Ontology;
import pl.edu.mimuw.wikiontology.pn347193.OntologyCollectionBuilder;
import pl.edu.mimuw.wikiontology.pn347193.filters.PersonFilter;
import pl.edu.mimuw.wikiontology.pn347193.importers.xml.XMLImporter;

public class WikiImporter {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: WikiImporter <xml file>");
            return;
        }

        OntologyCollectionBuilder builder = new OntologyCollectionBuilder();
        builder.addFilter(PersonFilter.getInstance());
        builder.addAnalysis(CategoryExtractor.getInstance());

        try {
            XMLImporter importer = new XMLImporter(builder);
            FileInputStream input = new FileInputStream(args[0]);
            importer.parse(input);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println("Cannot read the xml file.");
            System.out.println(e.getLocalizedMessage());
        }

        Collection<Ontology> ontologies = builder.build();
        System.out.println(String.format("Imported %d ontologies.", ontologies.
            size()));
        for (Ontology onto : ontologies) {
            System.out.println(onto.getTitle());
        }
    }

}