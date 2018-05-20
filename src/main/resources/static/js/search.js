(function() {
	var rootAliasMapBeans = [];

	$.get('/root/mapbeans', function(data, status) {
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
								return '<div><strong>' + data.aliasString + '</strong> (' + data.rootString + ') : ' + data.rootMeaning + '</div>';
							}
					}
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

