(function() {
	var rootAliasMapBeans = [];
	var table;
	$('#analyze-button').click(function() {
		var wordString = $('#analyze-word').val(); 
		console.log(wordString);
		
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
/*					var dataSet = $.map(results, function(n, i) {
					    return "词根:" + n.aliasString + " (" + n.rootString + "), 含义:" + n.rootMeaning;
					});*/
					
					
					$('#analyze-table').empty();
					$('#analyze-table').DataTable( {
					        data: dataset,
					        columns: [
					            { title: "词根" },
					            { title: "词根原型" },
					            { title: "含义" }
					        ],
					        bDestroy: true
					    } );
				}
			}
		});
	});

	$.get('/toefl/root/mapbeans', function(data, status) {
		if (status === 'success') {
			if (data.status === 'success') {
				rootAliasMapBeans = data.data.data;

				$('#the-basics .typeahead').typeahead({
					hint: true,
					highlight: true,
					minLength: 1
				}, {
					name: 'rootAliasMapBeans',
					display: 'aliasString',
					source: mapBeanAliasMatcher(rootAliasMapBeans),
					templates: {
						empty: [
							'<div class="empty-message">',
							'无法找到词根，是否要添加?',
							'</div>'
							].join('\n'),
							suggestion: function(data) {
								return "<div class='root_in_dropdown' value=" + data.rootId + "><strong>" + data.aliasString + "</strong> (" + data.rootString + ") : " + data.rootMeaning + "</div>";
							}
					}
				});

				$('.typeahead').bind('typeahead:select', function(event, suggestion) {
					var param = {rootIdStr: suggestion.rootId};
					$.get('/toefl/root/rootwords', param, function(data, status){
						if (status === 'success') {
							if (data.status === 'success') {
								debugger
							}
						}
					}); 
				});
			} else {
				console.log('error');
			}
		} else {
			console.logr('error');
		}	
	});	

	var substringMatcher = function(strs) {
		return function findMatches(q, cb) {
			var matches, substringRegex;

			// an array that will be populated with substring matches
			matches = [];

			// regex used to determine if a string contains the substring `q`
			substrRegex = new RegExp(q, 'i');

			// iterate through the pool of strings and for any string that
			// contains the substring `q`, add it to the `matches` array
			$.each(strs, function(i, str) {
				if (substrRegex.test(str)) {
					matches.push(str);
				}
			});

			cb(matches);
		};
	};

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
})();

