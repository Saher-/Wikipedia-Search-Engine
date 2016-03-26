Wikipedia Search Engine (with Lucene)
=====================================

#### Description
- **Using Lucene** this program takes query strings via command line, and return ranked search results.
- There are some customizable options to play with also, like how much corpus you want to deal with, and more.
- you can see data pre-processing in action by invoking the right command.
- Easy to use
- search queries, return related Wikipedia articles, links, and description (ranked in order).

#### Design:
- **Data Pre-processing 1:** reading from wiki corpus text files, and constructing one main XML file for all the corpus. 
- **Data Pre-processing 2:** parsing the XML, and creating the index via lucene to make the data searchable.
- **Wikipedia Search Engine** is now ready to recieve search queries, and return ranked wiki articles accordingly.
- **Customization** may include changing the number of top articles returned, redo data pre-processing on a smaller chunk of corpus, and more.

#### Corpus downloads **(Only 3 million articles):
- [wiki_labels_all](http://downloads.dbpedia.org/3.3/en/articles_label_en.nt.bz2)
- [wiki_links_all](http://downloads.dbpedia.org/3.3/en/wikipage_en.csv.bz2)
- [wiki_abstracts_all](http://downloads.dbpedia.org/3.3/en/shortabstract_en.nt.bz2)

#### Documentation
Please Read the documentation provided for more details on the project.

#### NOTE:
- Due to large files, only portion of the corpus were uploaded (current lucene index is the full one though).
- if you like to download the full corpus, download the above lines, and follow the naming conventions as provided.