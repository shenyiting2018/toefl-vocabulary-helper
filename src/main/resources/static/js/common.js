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