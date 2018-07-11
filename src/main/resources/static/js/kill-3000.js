
function init_Kill3000DataTables() {
	console.log('run_datatables');

	if( typeof ($.fn.DataTable) === 'undefined'){ return; }
	console.log('init_DataTables');

	var handleKill3000DataTableButtons = function() {
		if ($("#kill-3000-table").length) {
			var table = $("#kill-3000-table").DataTable({
				pagingType: "input",
				pageLength: 50,
				dom: "Blfrtip",
				fnDrawCallback: function(e) {
					hiddenButtonHandler();
					detailsControlButtonHandler();
					proficiencyHandler();
				},
				columnDefs: [
					    { "visible": false, "targets": 1 }
					  ],
				buttons: [
					{
						extend: "copy",
						className: "btn-sm"
					},
					{
						extend: "csv",
						className: "btn-sm"
					},
					{
						extend: "excel",
						className: "btn-sm"
					},
					{
						extend: "pdfHtml5",
						className: "btn-sm"
					},
					{
						extend: "print",
						className: "btn-sm"
					},
					],
					responsive: true
			});
		}
	}

	TableManageButtons = function() {
		"use strict";
		return {
			init: function() {
				handleKill3000DataTableButtons();
			}
		};
	}();

	TableManageButtons.init();
};

var hiddenButtonHandler = function() {
	$('.table-word-meaning-toggle').off('click');
	$('.table-word-meaning-toggle').click(event, function(el) {
		var button = $(event.target);
		var text = button.next();

		if (button.hasClass('word-meaning-shown')) {
			//click to hide
			button.removeClass('word-meaning-shown');
			button.removeClass('btn-warning');
			button.addClass('word-meaning-hidden');
			button.addClass('btn-success');
			button.html('Show');

			text.removeClass('hidden');
			text.addClass('hidden');
		} else {
			button.removeClass('word-meaning-hidden');
			button.removeClass('btn-success');
			button.addClass('word-meaning-shown');
			button.addClass('btn-warning');
			button.html('Hide');

			text.removeClass('hidden');
		}
	});
}


var detailsControlButtonHandler = function(table) {
	$('.details-control').off('click');
	$('#kill-3000-table tbody').off('click', 'button.details-control');
	$('#kill-3000-table tbody').on('click', 'button.details-control', function () {
	        var tr = $(this).closest('tr');
	        
	        var table = $('#kill-3000-table').DataTable();
	        var row = table.row( tr );
	 
	        if ( row.child.isShown() ) {
	            // This row is already open - close it
	            row.child.hide();
	            tr.removeClass('shown');
	            $(this).removeClass('btn-info');
	            $(this).addClass('btn-success');
	        }
	        else {
	        	var wordId = row.data()[1];
	        	var that = $(this);
	        	var wordDetailHandlerClojure = function(wordBean) {
	        		wordDetailHandler = function() {
	        			var child = subRowFormatter(wordBean);
	        			row.child(child).show();
	    	            tr.addClass('shown');
	    	            that.addClass('btn-info');
	    	            that.removeClass('btn-success');
	    	            
	    	            $('.word-detail-root-button').off('click');
	    	            $('.word-detail-analyze-button').off('click');
	    	            
	    	            $('.word-detail-root-button').on('click', function(event) {
	    	            	var buttonDOM = $(event.target);
	    	            	var rowDOM = buttonDOM.closest('.word-detail-row').first();
	    	            	var tableDOM = rowDOM.find('.word-detal-right-table-div').first();
	    	            	
	    	            	var rootId = event.target.value;
	    	            	
	    	            	var renderRootWordsHandler = function(rootBean) {
	    	            		var root = rootBean.root;
	    	            		var words = rootBean.wordRootMaps;
	    	            		var rootAliasMaps = rootBean.rootAliasMaps;
	    	            		var aliases = '';
	    	            		for (var i in rootAliasMaps) {
	    	            			aliases += rootAliasMaps[i].alias.aliasString + ',';
	    	            		}
	    	            		
	    	            		var tableRows = '';
	    	            		for (var j in words) {
	    	            			tableRows += '<tr>'+
	    	            							'<td>' + words[j].word.wordString + '</td>'+
	    	            							'<td>' + words[j].word.meanings + '</td>'+
	    	            						'</tr>'
	    	            		}
	    	            		var titleDOM = '<div class="row"><h4>' + '<span class="text-danger">' + root.rootString + ':[' + root.meanings + ']' +  '</span>: ' + aliases + '</h4></div>';
	    	            		var subTableDOM = '<div class="row">'+
	    	            							'<table class="table table-striped table-bordered">'+
	    	            								'<thead>'+
	    	            									'<th>Word</th>'+
	    	            									'<th>Meanings</th>'+
    	            									'</thead>'+
    	            									'<tbody>'+
    	            									tableRows+
    	            									'</tbody>'+
	            									'</table>'+
            									'</div>';
		    	            	tableDOM.empty();
	    	            		tableDOM.append(titleDOM + subTableDOM);
	    	            	}
	    	            	
	    	            	getRootBean(rootId, renderRootWordsHandler, null);
	    	            	
	    	            });
	    	            
	    	            $('.word-detail-analyze-button').on('click', function() {
	    	            	var buttonDOM = $(event.target);
	    	            	var rowDOM = buttonDOM.closest('.word-detail-row').first();
	    	            	var tableDOM = rowDOM.find('.word-detal-right-table-div').first();
	    	            	
	    	            	var wordString = event.target.value;;
	    	            	
	    	            	var renderAnalyzeResultsHandler = function(results) { 	            		
	    	            		var tableRows = '';
	    	            		for (var k in results) {
	    	            			var result = results[k];
	    	            			var verified = !!result.verified? '<i class="fa fa-check fa-2x centered" style="color:#4ef442;"></i>' : '<button class="btn btn-info">Verify!</button>';
	    	            			tableRows += '<tr>'+
	    	            							'<td>' + result.aliasString + '</td>'+
	    	            							'<td>' + result.rootString + '</td>'+	    	            						
	    	            							'<td>' + result.rootMeanings + '</td>'+
	    	            							'<td>' + result.description + '</td>'+
	    	            							'<td>' + verified + '</td>'+
	    	            						'</tr>'
	    	            		}
	    	            		
	    	            		var subTableDOM = '<div class="row">'+
	    	            							'<table class="table table-striped table-bordered">'+
	    	            								'<thead>'+
	    	            									'<th>Root Alias</th>'+
	    	            									'<th>Root</th>'+
	    	            									'<th>Meanings</th>'+
	    	            									'<th>Description</th>'+
	    	            									'<th>Verified</th>'+
    	            									'</thead>'+
    	            									'<tbody>'+
    	            									tableRows+
    	            									'</tbody>'+
	            									'</table>'+
            									'</div>';
		    	            	tableDOM.empty();
	    	            		tableDOM.append(subTableDOM);
	    	            	}
	    	            	
	    	            	analyzeWord(wordString, renderAnalyzeResultsHandler, null);
	    	            });
	        		}
	        		
	        		wordDetailHandler();
	        	}
	        	getWordBean(wordId, wordDetailHandlerClojure);
	        }
	    } );
}


var subRowFormatter = function(wordBean) {
	 // `d` is the original data object for the row
	var wordRootMaps = wordBean.wordRootMaps;
	var wordCategoryMaps = wordBean.wordCategoryMaps;
	
	var wordRootElement = '';
	for (var i in wordRootMaps) {
		var root = wordRootMaps[i].root;
		wordRootElement += ' <button class="btn btn-danger btn-sm word-detail-root-button" value=' + root.id + '>' + root.rootString + ': ' + root.meanings + '</button>';
	}
	wordRootElement += ' <button class="btn btn-primary btn-sm word-detail-analyze-button" value="' + wordBean.word.wordString + '">Analyze</button>';
    return '<div class="row word-detail-row">' +
    			'<div class="col-md-4">' + 
    				'<table table-striped table-bordered cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
				        '<tr>'+
				            '<td>Word ID:</td>'+
				            '<td>'+ wordBean.word.id +'</td>'+
				        '</tr>'+
				        '<tr>'+
				        	'<td>Roots:</td>' + 
				        	'<td>' + wordRootElement + '</td>'+
				        '</tr>'+
				    '</table>'+
		    	'</div>'+
		    	'<div class="col-md-8 word-detal-right-table-div">'+
		    	'</div>'+
		   '</div>';
}


var proficiencyHandler = function() {
	$('.prof-button').off('click');
	$('.prof-button').on('click', function(event) {
		var target = $(event.target);
		var wordId = target.attr('word-id');
		var categoryName = 'kill-3000';
		var newProficiency = target.attr('value');
		var toReplaceButton = target.closest('.proficiency-dropdown').find('button')[0];
		var oldProficiency = toReplaceButton.value;
		
		if (newProficiency !== oldProficiency) {
			param = {
				wordIdStr: wordId,
				categoryName: categoryName,
				proficiency: newProficiency
			};
			
			$.post('/toefl/vocabularies/update-proficiency', param, function(data, status) {
				if (status === 'success') {
					if (data.status === 'success') {
						var newButton = '<button type="button" class="btn btn-' 
							  + getColor(newProficiency) + ' dropdown-toggle column-button selected" data-toggle="dropdown" '
							  + 'value="' + newProficiency + '">'
							  + newProficiency + '     '
						  + '<span class="caret"></span></button>';
						$(toReplaceButton).replaceWith(newButton);
					} else {
						console.log('error');
					}
				} else {
					console.log('error');
				}
			});
		}
	});
}


var buildProficiencyDropdown = function(currentProficiency, wordId) {
	
	return '<div class="dropdown proficiency-dropdown">'
	  + '<button type="button" class="btn btn-' 
		  + getColor(currentProficiency) + ' dropdown-toggle column-button selected" data-toggle="dropdown" '
		  + 'value="' + currentProficiency + '">'
		  + currentProficiency + '     '
	  + '<span class="caret"></span></button>'
	  + '<ul class="dropdown-menu">'
		  + '<li><button class="btn btn-primary column-button prof-button" value="4" word-id=' + wordId + ' href="#">4</button></li>'
		  + '<li class="divider"></li>'
		  + '<li><button class="btn btn-success column-button prof-button" value="3"  word-id=' + wordId + ' href="#">3</button></li>'
		  + '<li><button class="btn btn-info column-button prof-button" value="2" word-id=' + wordId + ' href="#">2</button></li>'
		  + '<li><button class="btn btn-warning column-button prof-button" value="1" word-id=' + wordId + ' href="#">1</button></li>'
		  + '<li class="divider"></li>'
		  + '<li><button class="btn btn-danger column-button prof-button" value="0" word-id=' + wordId + ' href="#">0</button></li>'
	  + '</div>'
  + '</div>';
}

var getColor = function(proficiency) {
	if (proficiency == 4) {
		return 'primary';
	} else if (proficiency == 3) {
		return 'success';
	} else if (proficiency == 2) {
		return 'info';
	} else if (proficiency == 1) {
		return 'warning';
	} else {
		return 'danger';
	}
}

$(document).ready(function() {
	init_Kill3000DataTables(hiddenButtonHandler);
	
	var drawWordsTablehandler = function(categoryWords) {
		dt = $('#kill-3000-table').DataTable();
		dt.clear();
		var number = 1;
		for (word of categoryWords) {
			wordMeanings = '<span class="table-word-meanings-column" value=' + word.meanings + '>'
			+ buildDisplayedMeanings(word.meanings)
			+ '</span>'
			
			numberButton = '<button class="btn btn-success  btn-circle details-control">' + number + '</button>';
			
			wordProficiency = buildProficiencyDropdown(word.proficiency, word.id);
			node = dt.row.add([numberButton, word.id, '<b>' + word.wordString + '</b>', wordMeanings, wordProficiency]);
			number += 1;
		}

		rowNode = dt.columns.adjust().draw();
	}

	$('#switch-meanings-display').click(event, function(el) {
		var dom = $(event.target);
		//$('.table-word-meanings-column').empty();

		if (dom.hasClass('btn-warning')) {					
			//click to hide
			$('.table-word-meaning-toggle').removeClass('word-meaning-shown');
			$('.table-word-meaning-toggle').removeClass('btn-warning');
			$('.table-word-meaning-toggle').addClass('word-meaning-hidden');
			$('.table-word-meaning-toggle').addClass('btn-success');
			$('.table-word-meaning-toggle').html('Show');

			$('.table-word-meanings').removeClass('hidden');
			$('.table-word-meanings').addClass('hidden');

			dom.html('Show');
			dom.removeClass('btn-warning');
			dom.addClass('btn-success');
		} else {		
			//click to show
			$('.table-word-meaning-toggle').removeClass('word-meaning-hidden');
			$('.table-word-meaning-toggle').removeClass('btn-success');
			$('.table-word-meaning-toggle').addClass('word-meaning-shown');
			$('.table-word-meaning-toggle').addClass('btn-warning');
			$('.table-word-meaning-toggle').html('Hide');

			$('.table-word-meanings').removeClass('hidden');

			dom.html('Hide');
			dom.removeClass('btn-success');
			dom.addClass('btn-warning');
		}
	});

	$('#revert-meaning-display').click(event, function(el) {
		$('.table-word-meaning-toggle').each(function(index) {
			var button = $(this);
			var text = button.next();

			if (button.hasClass('word-meaning-shown')) {
				//click to hide
				button.removeClass('word-meaning-shown');
				button.removeClass('btn-warning');
				button.addClass('word-meaning-hidden');
				button.addClass('btn-success');
				button.html('Show');

				text.removeClass('hidden');
				text.addClass('hidden');
			} else {
				button.removeClass('word-meaning-hidden');
				button.removeClass('btn-success');
				button.addClass('word-meaning-shown');
				button.addClass('btn-warning');
				button.html('Hide');

				text.removeClass('hidden');
			}
		});
	})

	var buildDisplayedMeanings = function(meanings) {
		return "<button class='btn btn-warning btn-sm table-word-meaning-toggle word-meaning-shown' value="
		+ meanings
		+ ">Hide</button><span class='table-word-meanings'>"
		+ meanings
		+ "</span>";
	}

	categoryWords = getCategoryWords(drawWordsTablehandler, "kill-3000", $('#kill-3000-table'));

});
