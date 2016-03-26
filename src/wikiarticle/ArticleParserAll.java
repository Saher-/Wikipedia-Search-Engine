package wikiarticle;

import java.io.*;
import javax.xml.stream.*;
import org.apache.lucene.index.IndexWriter;

public class ArticleParserAll {
	ArticleIndexerAll indexer = null;

	/**
	 * Main method.
	 * 
	 * @param args
	 *            argument parameters
	 */
	public static void main(String args[]) {
		// ArticleParser parser = new ArticleParser();
		// parser.run();
	}

	/**
	 * Start the whole process of parsing the XML file to be indexed.
	 * 
	 * @param _indexer
	 *            ArticleIndexer class instance, that have an open Index Writer.
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	public void run(ArticleIndexerAll _indexer) throws XMLStreamException, IOException {
		indexer = _indexer;
		XMLStreamReader read = this.openReader();
		this.parse(read);
		this.close(read);
	}

	/**
	 * Open XML file, and start streaming it.
	 * 
	 * @return Opened XML stream reader.
	 * @throws FileNotFoundException
	 * @throws XMLStreamException
	 */
	private XMLStreamReader openReader() throws FileNotFoundException, XMLStreamException {
		XMLInputFactory factory = XMLInputFactory.newFactory();
		XMLStreamReader reader = factory.createXMLStreamReader(new FileInputStream("wiki-dataset/wiki_data_all.xml"));

		return reader;
	}

	/**
	 * Parse an XML stream reader block (XML tag <..> </..>)
	 * 
	 * @param r
	 *            Stream reader
	 * @throws XMLStreamException
	 * @throws IOException
	 */
	private void parse(XMLStreamReader r) throws XMLStreamException, IOException {
		Article currArt = null;
		String tagContent = null;
		String output = null;

		while (r.hasNext()) {
			int event = r.next();

			// figure out which tag is the stream reading, and act accordingly.
			switch (event) {
			case XMLStreamConstants.START_ELEMENT:
				if ("Article".equals(r.getLocalName()))
					currArt = new Article();
				if ("Wiki".equals(r.getLocalName()))
					continue;
				break;
			case XMLStreamConstants.CHARACTERS:
				tagContent = r.getText().trim();
				break;
			case XMLStreamConstants.END_ELEMENT:
				switch (r.getLocalName()) {
				case "Article":
					indexer.indexArticle(currArt, indexer);
					currArt = currArt.newArticle();
					break;
				case "ID":
					currArt.setId(tagContent);
					break;
				case "Link":
					currArt.setLink(tagContent);
					break;
				case "Title":
					currArt.setTitle(tagContent);
					break;
				case "Description":
					if (tagContent == null)
						currArt.setDesc("N/A");
					currArt.setDesc(tagContent);
					break;
				}
			}
		}
	}

	/**
	 * Close XML stream reader
	 * 
	 * @param reader
	 *            Stream reader to be closed
	 * @throws XMLStreamException
	 */
	private void close(XMLStreamReader reader) throws XMLStreamException {
		reader.close();
	}
}