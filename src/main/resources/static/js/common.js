var appendTableRecord = function(tableDataDomId, recordHTML) {
	var tableDataDom = $('#' + tableDataDomId);
	tableDataDom.append(recordHTML);
}

var clearTable = function(tableDataId) {
	var tableDataDom = $('#' + tableDataId);
	tableDataDom.empty();
}

function groupBy(list, keyGetter) {
	const map = new Map();
	list.forEach((item) => {
		const key = keyGetter(item);
		const collection = map.get(key);
		if (!collection) {
			map.set(key, [item]);
		} else {
			collection.push(item);
		}
	});
	return map;
}

var getRootBean = function(rootId, handler, loadingDom) {
	if (!!loadingDom) {
		loadingDom.loading();
	}
	$.get('/toefl/vocabularies/rootBean/' + rootId, function(data, status) {
		if (status === 'success') {
			if (data.status === 'success') {
				rootBean = data.data.data;
				handler(rootBean);
			} else {
				console.log('error');
			}
		} else {
			console.log('error');
		}
		
		if (!!loadingDom) {
			loadingDom.loading('stop');
		}
	});
}

var getWordBean = function(wordId, handler, loadingDom) {
	if (!!loadingDom) {
		loadingDom.loading();
	}
	$.get('/toefl/vocabularies/wordBean/' + wordId, function(data, status) {
		if (status === 'success') {
			if (data.status === 'success') {
				wordBean = data.data.data;
				handler(wordBean);
			} else {
				console.log('error');
			}
		} else {
			console.log('error');
		}	
		
		if (!!loadingDom) {
			loadingDom.loading('stop');
		}
	});
}

var getCategoryWords = function(handler, categoryName, loadingDom) {
	if (!!loadingDom) {
		loadingDom.loading();
	}
	$.get('/toefl/vocabularies/categoryWords/' + categoryName, function(data, status) {
		if (status === 'success') {
			if (data.status === 'success') {
				wordBeans = data.data.data;

				if (!!handler) {
					handler(wordBeans);
				}
			} else {
				console.log('error');
			}
			loadingDom.loading('stop')
		} else {
			console.log('error');
			loadingDom.loading('stop')
		}	
	});	
}

var getWordBeans = function(wordId, handler, loadingDom) {
	loadingDom.loading();
	$.get('/toefl/vocabularies/wordBeans', function(data, status) {
		if (status === 'success') {
			if (data.status === 'success') {
				wordBeans = data.data.data;

				handler(wordBeans);
			} else {
				console.log('error');
			}

			loadingDom.loading('stop')
		} else {
			console.log('error');
			loadingDom.loading('stop')
		}	
	});	
}

var getWordRoots = function(handler, loadingDom) {
	loadingDom.loading();
	$.get('/toefl/vocabularies/wordBeans', function(data, status) {
		if (status === 'success') {
			if (data.status === 'success') {
				wordBeans = data.data.data;

				handler(wordBeans);
			} else {
				console.log('error');
			}

			loadingDom.loading('stop')
		} else {
			console.log('error');
			loadingDom.loading('stop')
		}	
	});	
}

var analyzeWord = function(wordString, handler, loadingDom) {
	if (!!loadingDom) {
		loadingDom.loading();
	}
	
	param = {
		wordString: wordString
	}

	$.get('/toefl/vocabularies/analyze', param, function(data, status) {
		if (status === 'success') {
			if (data.status === 'success') {
				var results = data.data.data;
				
				handler(results);
			} else {
				console.log('error');
			}
		} else {
			console.log('error');
		}
		
		if (!!loadingDom) {
			loadingDom.loading('stop');
		}
	});
}
