function showTable(table) {
	
	var tables = ['t1', 't2', 't3','t4','t5','t6'];
	for (var i = 0; i < 6; i++) {
		document.getElementById(tables[i]).style.display = "none";
		}
	document.getElementById(table).style.display = "block";
}
