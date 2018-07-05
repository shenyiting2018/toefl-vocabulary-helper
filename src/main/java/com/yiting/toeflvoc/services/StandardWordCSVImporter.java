package com.yiting.toeflvoc.services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import com.yiting.toeflvoc.models.Category;
import com.yiting.toeflvoc.models.Word;
import com.yiting.toeflvoc.utils.PropertyManager;
import com.yiting.toeflvoc.utils.ResourceDuplicatedException;
import com.yiting.toeflvoc.utils.ResourceNotFoundException;

@Service
public class StandardWordCSVImporter {
    private final Logger logger = LoggerFactory.getLogger(StandardWordCSVImporter.class);

    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
    private VocabularyModelService vocabularyService;
    
    @Autowired
    private PropertyManager propertyManager;
	
    @Transactional
	public int importWordCSV(String fileName, String categoryName) throws IOException, ResourceDuplicatedException, ResourceNotFoundException {
		logger.info(String.format("Start importing %s", fileName));
		String location = this.propertyManager.getCsvFilePath() + fileName;
		
		final Resource fileResource = this.resourceLoader.getResource(location);
		File file = fileResource.getFile();
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.newFormat('|').parse(in);
		int number = 0;
		
		Category category = this.vocabularyService.getCategoryByCategoryName(categoryName);
		
		for (CSVRecord record : records) {
			Iterator<String> columns = record.iterator();
			
			String wordString = null; 
			List<String> meanings = new ArrayList<>();

			int index = 0;
			while (columns.hasNext()) {
				String element = columns.next();
				if (index == 0) {
					wordString = element;
				} else if (index == 1) {
					meanings.add(element);
				} else {
				}
				index++;
			}
		
			Word word = vocabularyService.getWordByWordString(wordString);

			if (word != null) {
				if (word.getMeanings().isEmpty() || (word.getMeanings().size() == 1 && word.getMeanings().get(0).isEmpty())) {
					this.vocabularyService.replaceWordMeanings(word, meanings, number);
				} 
			} else {
				word = vocabularyService.addWord(wordString, meanings, number);
			}
			
			this.vocabularyService.addWordCategoryMap(word, category);
			logger.info(String.format("Imported %s record", number));

			number++;
		}
		
		logger.info(String.format("Importing %s finished, %s records processes", fileName, number));
		return number;
	}
}
