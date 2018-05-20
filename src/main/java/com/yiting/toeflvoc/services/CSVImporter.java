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

@Service
public class CSVImporter {
    private static final String GRE_CSV_FILE_PATH = "external_files/gre_root.csv";
    private final Logger logger = LoggerFactory.getLogger(CSVImporter.class);
    
    private static final int ROOT_END_INDEX = 0;
    private static final int MEANING_END_INDEX = 1;
    private static final int ALIAS_END_INDEX = 9;

    @Autowired
    private ResourceLoader resourceLoader;
    
    @Autowired
	private RootService rootService;
    
	public void importGRERootCSV() throws IOException {
		final Resource fileResource = this.resourceLoader.getResource(GRE_CSV_FILE_PATH);
		File file = fileResource.getFile();
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);
		
		for (CSVRecord record : records) {
			Iterator<String> columns = record.iterator();
			int count = 0;
			String rootString = null;
			List<String> meaning = new ArrayList<>();
			while (columns.hasNext()) {
				String element = columns.next();
				if (count == ROOT_END_INDEX) {
					rootString = element;
				} else if (count == MEANING_END_INDEX) {
					String[] meanings = element.split("\\|");
					for (String m : meanings) {
						meaning.add(m);
					}
				} else if (count <= ALIAS_END_INDEX) {
					if (StringUtils.isNotBlank(element)) {
						
					}
				}
				count++;
			}
			

			
			this.rootService.addRoot(rootString, meaning);
		}
	}
}
