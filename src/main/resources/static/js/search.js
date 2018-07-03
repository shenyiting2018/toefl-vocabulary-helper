//var aliasBeans = [];
var wordRootsBeans = [];
var wordRootMaps = [];
var rootWordsBeans = [];
var rootSearchResultTemplate = Handlebars.compile($("#root-search-result-template").html());
var rootSearchEmptyTemplate = Handlebars.compile($("#root-search-empty-template").html());
var analyzeResultTemplate = Handlebars.compile($("#analyze-result-template").html());
var analyzeEmptyTemplate = Handlebars.compile($("#analyze-empty-template").html());
var analyzeResultTableTemplate = Handlebars.compile($("#analyze-result-table-template").html());
var rootWordsTableDataTemplate = Handlebars.compile($("#root-words-table-data-template").html());

var wordRootsMapCache = new Map();
var rootWordsMapCache = new Map();


(function() {
	var rootAliasBeanAliasMatcher = function(rootAliasMaps) {
		return function findMatches(q, cb) {
			var matches, substringRegex;

			// an array that will be populated with substring matches
			matches = [];

			// regex used to determine if a string contains the substring `q`
			substrRegex = new RegExp(q, 'i');

			// iterate through the pool of strings and for any string that
			// contains the substring `q`, add it to the `matches` array
			$.each(rootAliasMaps, function(i, rootAliasMap) {
				if (substrRegex.test(rootAliasMap.alias.aliasString)) {
					matches.push(rootAliasMap);
				}
			});

			cb(matches);
		};
	};

	var wordRootsMatcher = function(wordBeans) {
		return function findMatches(q, cb) {
			var matches, substringRegex;

			// an array that will be populated with substring matches
			matches = [];

			// regex used to determine if a string contains the substring `q`
			substrRegex = new RegExp(q, 'i');

			// iterate through the pool of strings and for any string that
			// contains the substring `q`, add it to the `matches` array
			$.each(wordBeans, function(i, wordBean) {
				if (substrRegex.test(wordBean.word.wordString)) {
					matches.push(wordBean);
				}
			});

			cb(matches);
		};
	};

	var searchRootsTypeahead = function(rootAliasMaps) {
		$('#search-roots').typeahead({
			hint: $('.Typeahead-hint'),
			menu: $('.Typeahead-menu'),
			minLength: 0,
			classNames: {
				open: 'is-open',
				empty: 'is-empty',
				cursor: 'is-active',
				suggestion: 'Typeahead-suggestion',
				selectable: 'Typeahead-selectable'
			}
		}, {
			source:  rootAliasBeanAliasMatcher(rootAliasMaps),
			display: function(item) {
			    return item.alias.aliasString;
			  },
			templates: {
				suggestion: function(data) {
					return rootSearchResultTemplate(data);
				},
				empty: rootSearchEmptyTemplate
			}
		})
		.on('typeahead:asyncrequest', function() {
			$('.Typeahead-spinner').show();
		})
		.on('typeahead:asynccancel typeahead:asyncreceive', function() {
			$('.Typeahead-spinner').hide();
		});
	}

	var analyzeRootsTypeahead = function(wordBeans) {
		$('#analyze-roots').typeahead({
			hint: $('.Typeahead-hint-2'),
			menu: $('.Typeahead-menu-2'),
			minLength: 0,
			classNames: {
				open: 'is-open',
				empty: 'is-empty',
				cursor: 'is-active',
				suggestion: 'Typeahead-suggestion',
				selectable: 'Typeahead-selectable'
			}
		}, {
			source:  wordRootsMatcher(wordBeans),
			display: function(item) {
			    return item.word.wordString;
			  },
			templates: {
				suggestion: function(data) {
					return analyzeResultTemplate(data);
				},
				empty: function(data) {
					return analyzeEmptyTemplate(data);
				}
			}
		})
		.on('typeahead:asyncrequest', function() {
			$('.Typeahead-spinner').show();
		})
		.on('typeahead:asynccancel typeahead:asyncreceive', function() {
			$('.Typeahead-spinner').hide();
		});
	}

	var bulkUpdateActionEventRegister = function() {
		$('input.flat').iCheck({
			checkboxClass: 'icheckbox_flat-green',
			radioClass: 'iradio_flat-green'
		});
		//Table
		$('table input').on('ifChecked', function () {
			checkState = '';
			$(this).parent().parent().parent().addClass('selected');
			countChecked();
		});
		$('table input').on('ifUnchecked', function () {
			checkState = '';
			$(this).parent().parent().parent().removeClass('selected');
			countChecked();
		});

		var checkState = '';

		$('.bulk_action input').on('ifChecked', function () {
			checkState = '';
			$(this).parent().parent().parent().addClass('selected');
			countChecked();
		});
		$('.bulk_action input').on('ifUnchecked', function () {
			checkState = '';
			$(this).parent().parent().parent().removeClass('selected');
			countChecked();
		});
		$('.bulk_action input#check-all').on('ifChecked', function () {
			checkState = 'all';
			countChecked();
		});
		$('.bulk_action input#check-all').on('ifUnchecked', function () {
			checkState = 'none';
			countChecked();
		});
	}

	var registerHandlebars = function() {
		Handlebars.registerHelper('collonAppender', function(items, options) {
			var out = "";
			for(var i=0, l=items.length; i<l; i++) {
				out = out + items[i] + "; "
			}

			return out.substring(0, out.length - 2);
		});

		Handlebars.registerHelper('wordRootsListHelper', function(items, options) {
			var out = "";

			for(var i=0, l=items.length; i<l; i++) {
				out = out + " <div class='ProfileCard-screenName'>@" 
				+ options.fn(items[i].root.rootString) 
				+ ":"
				+ options.fn(items[i].root.meanings)
				+ "--"
				+ options.fn(items[i].description)
				+ "</div>";
			}

			return out;
		});

		Handlebars.registerHelper('rootsListHelper', function(items, options) {
			var out = "";

			for(var i=0, l=items.length; i<l; i++) {
				out = out 
				+ "<div class='ProfileCard-realName'>" + options.fn(items[i]) + "</div>" 
				" <div class='ProfileCard-screenName'>@" + options.fn(items[i]) 
				+ "</div>";
			}

			return out;
		});

		Handlebars.registerHelper('rootWordsTableDataHelper', function(items, options) {
			var out = "";
			for(var i=0, l=items.length; i<l; i++) {
				out = out + "<tr>"
				+ "<td>" + options.fn(items[i].word.wordString) + "</td>" 
				+ "<td>" + options.fn(items[i].word.meanings) + "</td>" 
				+ "<td>" + options.fn(items[i].description) + "</td></tr>";
			}

			return out + "</tr>";
		});	
	}

	var getRootAliasMaps = function() {
		$.get('/toefl/vocabularies/rootAliasMaps', function(data, status) {
			if (status === 'success') {
				if (data.status === 'success') {
					rootAliasMaps = data.data.data;

					searchRootsTypeahead(rootAliasMaps);
				} else {
					console.log('error');
				}
			} else {
				console.log('error');
			}	
		});	
	}

	var analyzeWord = function(wordString) {
		param = {
				wordString: wordString
		}
		clearTable('analyze-table-data');

		$.get('/toefl/vocabularies/analyze', param, function(data, status) {
			if (status === 'success') {
				if (data.status === 'success') {
					var results = data.data.data;
					for (i in results) {
						var result = results[i];
						var recordHTML = analyzeResultTableTemplate(result);
						appendTableRecord('analyze-table-data', recordHTML);
					}

				}
			}
		});
	}
	
	$('#root-search-typeahead').bind('typeahead:select', function(ev, suggestion) {
		ev.preventDefault();

		var root = suggestion.root;
		var handler = function(rootBean) {
			var wordsOfRoot = rootBean.wordRootMaps;
			$('#root-wods-table-root').html(root.rootString + "@" + root.meanings);
			dt = $('#root-words-table').DataTable();
			dt.clear();
			//dt.draw();
			for (wordOfRoot of wordsOfRoot) {
				dt.row.add([wordOfRoot.word.wordString, wordOfRoot.word.meanings, wordOfRoot.description]);
			}
			dt.draw();
		}

		rootBean = getRootBean(root.id, handler);
	});

	$('#root-analyze-typeahead').bind('typeahead:select', function(ev, suggestion) {
		ev.preventDefault();

		var wordString = suggestion.word.wordString;
		var meanings = suggestion.word.meanings;

		$('#word-to-analyze').html(wordString + "@" + meanings);
		analyzeWord(wordString);
	});

	$('#root-analyze-typeahead').bind('typeahead:render', function(ev, suggestion) {
		ev.preventDefault();
		$('#analyze-roots').off('keyup');
		$('#analyze-roots').on('keyup', function(e) {
			if (e.keyCode == 13) {
				var wordString = $('#analyze-roots').val();
				$('#word-to-analyze').html(wordString);
				analyzeWord(wordString);
			}
		});
	});
	
	registerHandlebars();
	getRootAliasMaps();
	getWordBeans(analyzeRootsTypeahead);
})();

