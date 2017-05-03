
//Match the table by firstCell content.
//rowIndex are zero-based value.
//columnIndex are zero-based value.
//index are zero-based value.
//tag are the tagname of target element
function getValueByType(firstCell, tableIndex, rowIndex, columnIndex, userTag, index) {
	var cell = getTableCell(firstCell, tableIndex, rowIndex, columnIndex);
	if(cell) {
		userTag = userTag.toLowerCase();
		tag = userTag;
		
		if (userTag == "checkbox" || userTag == "radio"){
			tag="input";
		}
		
		if(tag == "td"){
			return cell.innerText;
		}

		var elements = cell.getElementsByTagName(tag);
		
		
		if(elements && index < elements.length) {
			var element = elements[index];
			
			
			if( tag == "img") {
				
				if(element.getAttribute('alt') != ''){
					return element.getAttribute('alt');
				}else if(element.src != ''){
					return element.src;
				}
				
			}else if(tag == "input") {
				if(userTag == "checkbox")
				{
					if(element.type == "checkbox") 
					{
						return element.checked;
					}
				}else if(userTag == "radio") 
				{
					if(element.type == "radio") 
					{
						return element.checked;
					}
				} else {
					if(element.type != "hidden") 
					{
						return element.value;
					}
					
				}
							
			}else if(tag == "select") {
							
				return element.options[element.selectedIndex].text;
				
			} else if(tag == "span") {
				
				if(element.text != undefined && element.text != ''){
					return element.text;
				}else if(element.innerText != undefined  && element.text != ''){
					return element.innerText;
				}else if(element.textContent != undefined  && element.text != ''){
					return element.textContent;
				}
							
			}else if(tag == "div") {
				
				if(element.text != undefined && element.text != ''){
					return element.text;
				}else if(element.innerText != undefined  && element.text != ''){
					return element.innerText;
				}else if(element.textContent != undefined  && element.text != ''){
					return element.textContent;
				}
							
			}else if(tag == "a") {
				return element.innerText;
				
			}else if(tag == "textarea") {
				
					return element.value;
			

				
			}

			
		}
	}
}


//Match the table by firstCell content.
//rowIndex are zero-based value.
//columnIndex are zero-based value.
//compIndex are zero-based value.
//tag are the tagname of target element
function clickElement(firstCell, tableIndex, rowIndex, columnIndex, compType, compValue, compIndex) {
	
	var cell = getTableCell(firstCell, tableIndex, rowIndex, columnIndex);
	
	if(cell) {
		
		var tag = compType.toLowerCase();
		var elements = cell.getElementsByTagName(tag);
		
		if(elements) {
			if(compValue != 'null'){
				
				for(var elementIndex = 0; elementIndex < elements.length; elementIndex++) {
					
					var element = elements[elementIndex];
					var elementExists = false;
										
					if( tag == "img") {
						var actCompValue = element.getAttribute('alt');
						
						if(actCompValue == compValue){
							elementExists=true;
						}
					} else if(tag == "a"){
						var actCompValue = element.innerText;
						//alert('actCompValue :'+actCompValue);
						//alert('compValue :'+compValue);
						if(actCompValue == compValue){
							elementExists=true;
						}
					}
					
					// If element exists then click the element
					if(elementExists){
						element.click();
						break;
					}
				}		
			} else {
				var element = elements[compIndex];
				element.click();
			}
		}
	}
}

//Match the table by firstCell content.
//rowIndex are zero-based value.
//columnIndex are zero-based value.
//compIndex are zero-based value.
//tag are the tagname of target element
function setElement(firstCell, tableIndex, rowIndex, columnIndex, compType, valueToEnter, compIndex) {
	
	var elementExists = false;
	var expComponents = new Array();
	var cell = getTableCell(firstCell, tableIndex, rowIndex, columnIndex);
	
	if(cell) {
		
		userTag = compType.toLowerCase();
		tag = userTag;
		
		if (userTag == "checkbox" || userTag == "radio"){
			tag="input";
		}
		
		var elements = cell.getElementsByTagName(tag);
		//alert('element length :'+elements.length);
		
		/* Get all the componenttype specific components*/
		for(var elementIndex = 0; elementIndex < elements.length; elements++) {
			
			var element = elements[elementIndex];
			
			if(userTag == "checkbox"){
				if (element.type == "checkbox"){
					expComponents.push(element);
				}
			}else if(userTag == "radio"){
				if (element.type == "radio"){
					expComponents.push(element);
				}
			}else if(userTag == "input"){
				if (element.type != "radio" && element.type != "checkbox"){
					expComponents.push(element);
				}
			}else{
				expComponents.push(element);
			}
		}
		
		/* Setting all the required compenent type components like all checkboxes or all radio buttion or all inputs or all selectboxes with in a cell */
		elements = expComponents;
		
		
		if(elements) {
			if(compIndex < elements.length){
			
				var element = elements[compIndex];
			
				//alert("input element count :"+elements.length)
			
				if(tag == "input") {
					if(userTag == "checkbox")
					{
						if(element.type == "checkbox") 
						{
							if(valueToEnter=='true'){
								element.checked = true;
							}else{
								element.checked = false;
							}
							
							element.fireEvent('onchange');
							element.fireEvent('onblur');
							element.fireEvent('onclick');
							elementExists = true;
						}
					}else if(userTag == "radio") 
					{				
						if(element.type == "radio") 
						{
							element.checked = true;
							element.fireEvent('onchange');
							element.fireEvent('onblur');

							elementExists = true;
						}
					} else if(userTag == "input") 
					{
						element.fireEvent('onclick');
						element.value = valueToEnter;
						element.fireEvent('onchange');
						element.fireEvent('onblur');
						elementExists = true;
					}
							
				}else if(tag == "select") {
					//alert("valueToEnter :"+valueToEnter);
					//alert("Select Lenth :"+element.length);
					var items = element.options;
					
					for(var selectIndex = 0; selectIndex < element.length; selectIndex++) 
					{
						if(items[selectIndex].text == valueToEnter)
						{
							//alert("Select Value :"+items[selectIndex].text);
							element.fireEvent('onclick');
							element.value = items[selectIndex].value;
							element.fireEvent('onchange');
							element.fireEvent('onblur');
							elementExists = true;
							break;
						}
					}
				}else if(tag == "textarea") {
					element.fireEvent('onclick');
					element.value = valueToEnter;
					element.fireEvent('onchange');
					element.fireEvent('onblur');
					elementExists = true;
				}
			} 
			
		}
	}
	
	return elementExists;
}


//Match the table by firstCell content.
//rowIndex are zero-based value.
//columnIndex are zero-based value.
function getTableCell(firstCell, tableIndex, rowIndex, columnIndex)
{
	/* Get Table */
	var table = getTable(firstCell, tableIndex);
	
	/* Get Table Rows */
	var rows = table.rows;
	
	//alert('rows.length ' + rows.length)
	if(rowIndex < rows.length) 
	{
		/* Get Specified Row based on rowIndex*/
		var row = rows[rowIndex];
		//alert ('row.cells.length '+row.cells.length);
		
		if(columnIndex < row.cells.length) 
		{
			/* Get Required Cell based on ColumnIndex in the specified row */
			var cell = row.cells[columnIndex];
			return cell;
		}

	}
}

function getTable(firstCell, tableIndex)
{
	var expTables = new Array();
	
	/* Get Tables in the Web Page*/
	var tables = document.getElementsByTagName("table");
	
	//alert(firstCell)
	/* Navigate through Tables and get the required Table based on firstCell */
	for(var i = 0; i < tables.length; i++)
	{
		/* Get First Table */
		var table = tables[i];
		
		/* Get All Rows in the specifed Table*/
	    var rows = table.rows;
		
		if(rows.length > 0) {
			/* Get First Row */
			var row = rows[0];
			var colLength = row.cells.length;
					
			/* 1. Compare the First Column with Expected Value
			   2. If Matched, Get the Required Tables */
			
			for(var colIndex = 0; colIndex < colLength; colIndex++)
			{
				var column = row.cells[colIndex];
				var columnValue = column.innerText;
				
				if(column.innerText == firstCell) {
					//alert('Table '+i+" Matched Column:"+column.innerText);
					expTables.push(table);
					break;
				}
			}	
			
	    }
	}
	
	/* Get the expTable based on index
		index=0 , means first Table
		index=1 , means second Table
	*/
	//alert(expTables.length)
	//if(expTables.length > tableIndex)
	//{
	//	var expTable = expTables[tableIndex];
	//}
	var expTable = expTables[tableIndex];
	
	return expTable;
}




//Match the table by firstCell content.
//columnIndex are zero-based value.
//columnValue is the innerText of target table cell.
function getColumns(firstCell,tableIndex)
{
	var ret = new Array();
	
	/* Get Required Table */
	var table = getTable(firstCell ,tableIndex);
	
	/* Get Table Rows */ 	
	var rows = table.rows;
	//alert(rows.length);
	if(rows.length > 0) {
		var row = rows[0];
		var colLength = row.cells.length;
		
		for(var colIndex = 0; colIndex < colLength; colIndex++)
		{
			var column = row.cells[colIndex];
			var columnValue = column.innerText;
			
			ret.push(columnValue);
		}
	}	
	
	return ret;
}


//Match the table by firstCell content.
//columnIndex are zero-based value.
//columnValue is the innerText of target table cell.
function getRowCount(firstCell,tableIndex)
{
	var ret = new Array();
	
	/* Get Required Table */
	var table = getTable(firstCell ,tableIndex);
	
	/* Get Table Rows */ 	
	var rows = table.rows;
	
	
	return rows.length;
}


