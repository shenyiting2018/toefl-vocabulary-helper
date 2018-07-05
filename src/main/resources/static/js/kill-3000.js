function init_Kill3000DataTables(hiddenButtonHandler) {

	console.log('run_datatables');

	if( typeof ($.fn.DataTable) === 'undefined'){ return; }
	console.log('init_DataTables');

	var handleKill3000DataTableButtons= function() {
		if ($("#kill-3000-table").length) {
			$("#kill-3000-table").DataTable({
				pagingType: "input",
				pageLength: 50,
				dom: "Blfrtip",
				fnDrawCallback: function(e) {
					hiddenButtonHandler();
				},
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

$(document).ready(function() {
	init_Kill3000DataTables(hiddenButtonHandler);

	var drawWordsTablehandler = function(categoryWords) {
		dt = $('#kill-3000-table').DataTable();
		dt.clear();
		var number = 1;
		for (word of categoryWords) {
			button = '<button class="btn btn-info centered-text kill-3000-table-cell-button" value=' + word.id+ '>Details</button>';
			wordMeanings = '<span class="table-word-meanings-column" value=' + word.meanings + '>'
			+ buildDisplayedMeanings(word.meanings)
			+ '</span>'
			node = dt.row.add([number, word.wordString, wordMeanings]);
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
