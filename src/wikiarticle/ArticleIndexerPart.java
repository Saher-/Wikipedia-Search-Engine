package wikiarticle;

import java.io.*;

import javax.xml.stream.XMLStreamException;

import org.apache.lucene.document.*;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;

import org.apache.lucene.index.*;
import org.apache.lucene.store.*;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;

public class ArticleIndexerPart {
	private IndexWriter indexwr;
	String indexPath = "Wiki-Index";

	/**
	 * Constructor
	 */
	public ArticleIndexerPart() {
	}

	/**
	 * Make directory for indexing, configure the index writer and create it.
	 * 
	 * @return instance of the Index Writer
	 * @throws IOException
	 */
	private IndexWriter getIndexWriter() throws IOException {
		File dir = new File(indexPath);
		if (indexwr == null) {
			// make directory if not exits
			if (!dir.exists()) {
				dir.mkdir();
			}

			// open and configure
			Directory indexDir = FSDirectory.open(dir.toPath());
			IndexWriterConfig config = new IndexWriterConfig(new StandardAnalyzer());
			config.setOpenMode(OpenMode.CREATE);
			indexwr = new IndexWriter(indexDir, config);
		}
		return indexwr;
	}

	/**
	 * Close the Index Writer
	 * 
	 * @throws IOException
	 */
	private void closeIndexWriter() throws IOException {
		if (indexwr != null) {
			indexwr.close();
		}
	}

	/**
	 * Index one article at a time to an open Index with an open Index Writer.
	 * (Usually called from the Article Parser class)
	 * 
	 * @param currArticle
	 *            Article to be indexed
	 * @param index
	 *            Index instance
	 * @throws IOException
	 */
	public void indexArticle(Article currArticle, ArticleIndexerPart index) throws IOException {
		System.out.println("Indexing Article " + currArticle.getId());

		// create a document and fill out its fields
		Document doc = new Document();
		doc.add(new StringField("ID", currArticle.getId(), Field.Store.YES));
		doc.add(new StringField("Title", currArticle.getTitle(), Field.Store.YES));
		doc.add(new StringField("Link", currArticle.getLink(), Field.Store.YES));
		doc.add(new StringField("Description", currArticle.getDesc(), Field.Store.YES));
		String searchableText = currArticle.getTitle() + " " + currArticle.getDesc();
		doc.add(new TextField("Content", searchableText, Field.Store.NO));

		// write document to index
		index.indexwr.addDocument(doc);
	}

	/**
	 * Rebuild Index by reindexing all the current data set.
	 * 
	 * @throws IOException
	 * @throws XMLStreamException
	 */
	public void rebuildIndexes() throws IOException, XMLStreamException {
		// get index writer
		getIndexWriter();

		// run parser
		ArticleParserPart parser = new ArticleParserPart();
		parser.run(this);

		// close index writer
		closeIndexWriter();
	}
}