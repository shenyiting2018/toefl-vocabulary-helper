package com.yiting.toeflvoc.services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.yiting.toeflvoc.models.Alias;
import com.yiting.toeflvoc.models.Root;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.utils.PropertyManager;

@Service
public class CSVImporter {
    private final Logger logger = LoggerFactory.getLogger(CSVImporter.class);
    
    private static final int ROOT_END_INDEX = 0;
    private static final int MEANING_END_INDEX = 1;
    private static final int ALIAS_END_INDEX = 9;

    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
    private VocabularyBeanService vocabularyService;
    
    @Autowired
    private PropertyManager propertyManager;
    
	public int importGRERootCSV() throws IOException {
		logger.info("Start importing csv file");
		String location = this.propertyManager.getCsvFilePath();
		final Resource fileResource = this.resourceLoader.getResource(location);
		File file = fileResource.getFile();
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
		
		int number = 0;
		for (CSVRecord record : records) {
			Iterator<String> columns = record.iterator();
			int count = 0;
			
			String rootString = null; 
			List<String> meaning = new ArrayList<>();
			List<String> aliasStrings = new ArrayList<>();
			List<String> wordStrings = new ArrayList<>();
			
			while (columns.hasNext()) {
				String element = columns.next();
				if (count == ROOT_END_INDEX) {
					rootString = element;
					aliasStrings.add(element);
				} else if (count == MEANING_END_INDEX) {
					String[] meanings = element.split("\\;");
					for (String m : meanings) {
						meaning.add(m);
					}
				} else if (count <= ALIAS_END_INDEX) {
					if (StringUtils.isNotBlank(element)) {
						aliasStrings.add(element);
					}
				} else {
					if (StringUtils.isNotBlank(element)) {
						wordStrings.add(element);
					}
				}
				count++;
			}
			
			Root root = this.vocabularyService.addRoot(rootString, meaning);
			logger.debug(String.format("Imported root %s with meaning %s", root.getRootString(), root.getMeaning().toString()));
			for (String aliasString : aliasStrings) {
				Alias alias = vocabularyService.addAlias(aliasString);
				this.vocabularyService.addRootAliasMap(root, alias, "");
			}
			
			for (String wordString : wordStrings) {
				Word word = vocabularyService.addWord(wordString, new ArrayList<>());
				this.vocabularyService.addWordRootMap(word, root, "");
			}
			
			number++;
		}
		logger.info(String.format("Importing CSV finished, %s records processes", number));
		return number;
	}
}
