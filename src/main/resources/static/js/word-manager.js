(function() {
	var format = function(data){
		// `d` is the original data object for the row
		var wordIdStr = $(data[0]).prop('value');

		var handler = function(wordBean) {
			return 
			'<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
			'<tr>'+
			'<td>Full name:</td>'+
			'<td>'+wordBean.word.wordString+'</td>'+
			'</tr>'+
			'<tr>'+
			'<td>Extension number:</td>'+
			'<td>'+wordBean.word.wordId+'</td>'+
			'</tr>'+
			'<tr>'+
			'<td>Extra info:</td>'+ 
			'<td>And any further details here (images etc)...</td>'+
			'</tr>'+
			'</table>';		
		} 

		var text = getWordBean(wordIdStr, handler);

		return text;
	}


	var drawWordsTablehandler = function(wordBeans) {
		dt = $('#word-manager-table').DataTable();
		dt.clear();
		for (wordBean of wordBeans) {
			dt.row.add(['<button class="btn btn-info centered-text word-manager-table-cell-button" value=' + wordBean.word.id+ '>Details</button>', wordBean.word.wordString, wordBean.word.meanings]);
		}

		$('.word-manager-table-cell-button').off('click');
		dt.columns.adjust().draw();

		$('.word-manager-table-cell-button').on('click', function () {
			var tr = $(this).closest('tr');
			var table = $('#word-manager-table').DataTable();
			var row = table.row( tr );

			if ( row.child.isShown() ) {
				// This row is already open - close it
				row.child.hide();
				tr.removeClass('shown');
			}
			else {
				// Open this row
				row.child( format(row.data()) ).show();
				tr.addClass('shown');
			}
		} );
	}

	wordBeans = getWordBeans(drawWordsTablehandler);
})();
