(function() {
	var rootAliasMapBeans = [];
	var root
	var table;
	var template = Handlebars.compile($("#result-template").html());
	var empty = Handlebars.compile($("#empty-template").html());

	$('#analyze-button').click(function() {
		var wordString = $('#analyze-word').val(); 
		param = {
				wordString : wordString
		}
		$.get('/toefl/root/analyze', param, function(data, status) {
			if (status === 'success') {
				if (data.status === 'success') {
					var results = data.data.data;
					var dataset = [];
					for (i in results) {
						var result = results[i];
						dataset.push([result.aliasString, result.rootString, result.rootMeaning]);
					}
					$('#analyze-table').empty();
					$('#analyze-table').DataTable();
/*					$('#analyze-table').DataTable( {
						data: dataset,
						columns: [
							{ title: "词根" },
							{ title: "词根原型" },
							{ title: "含义" }
							],
							bDestroy: true
					} );*/
				}
			}
		});
	});

	$.get('/toefl/root/mapbeans', function(data, status) {
		if (status === 'success') {
			if (data.status === 'success') {
				rootAliasMapBeans = data.data.data;

				searchRootsTypeahead(rootAliasMapBeans, empty, template);
				$('.typeahead').bind('typeahead:select', function(event, suggestion) {
					var param = {rootIdStr: suggestion.rootId};
					$.get('/toefl/root/rootwords', param, function(data, status){
						if (status === 'success') {
							if (data.status === 'success') {
							}
						}
					}); 
				});
			} else {
				console.log('error');
			}
		} else {
			console.log('error');
		}	
	});	
})();

var mapBeanAliasMatcher = function(rootAliasMapBeans) {
	return function findMatches(q, cb) {
		var matches, substringRegex;

		// an array that will be populated with substring matches
		matches = [];

		// regex used to determine if a string contains the substring `q`
		substrRegex = new RegExp(q, 'i');

		// iterate through the pool of strings and for any string that
		// contains the substring `q`, add it to the `matches` array
		$.each(rootAliasMapBeans, function(i, rootAliasMapBean) {
			if (substrRegex.test(rootAliasMapBean.aliasString)) {
				matches.push(rootAliasMapBean);
			}
		});

		cb(matches);
	};
};

var searchRootsTypeahead = function(rootAliasMapBeans, empty, template) {
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
		source:  mapBeanAliasMatcher(rootAliasMapBeans),
		displayKey: 'screen_name',
		templates: {
			suggestion: function(data) {
				return template(data);
			},
			empty: empty
		}
	})
	.on('typeahead:asyncrequest', function() {
		$('.Typeahead-spinner').show();
	})
	.on('typeahead:asynccancel typeahead:asyncreceive', function() {
		$('.Typeahead-spinner').hide();
	});
}

var substringMatcher = function(strs) {
	return function findMatches(q, cb) {
		var matches, substringRegex;
		matches = [];
		substrRegex = new RegExp(q, 'i');
		$.each(strs, function(i, str) {
			if (substrRegex.test(str)) {
				matches.push(str);
			}
		});

		cb(matches);
	};
};

var formAnalyzeResultRecord = function(result) {
	
}