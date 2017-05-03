
function listCount(firstCell, tableIndex) {

	/* Get Table */
	var table = getTable(firstCell, tableIndex);
	
	/* Get Table Rows */
	var rows = table.rows;
	
	var preTable = table.previousSibling;
	
	//alert('prev Tag :'+preTable.tagName);
	
	var elements = preTable.getElementsByTagName("select");
	
	var NoOfElements = elements[0].options.length;
	
	return NoOfElements;

}



function listBoxExists(firstCell, tableIndex, xpath) {

	var xPaths = xpath.split(",");	
	
	for(var xPathIndex = 0; xPathIndex < xPaths.length; xPathIndex++) 
	{
		//alert('select :'+xPaths[xPathIndex].toLowerCase()+'index:'+xPathIndex);
		
		if(xPaths[xPathIndex].toLowerCase() == "javascript"){
			//alert('javascript :');
			/* Get Table */
			var table = getTable(firstCell, tableIndex);
			//alert('table  :'+table);			

			/* Get Table Rows */
			var rows = table.rows;
			
			//alert('row count :'+rows.length);
			// Get Previous Table
			var preTable = table.previousSibling;
			
			//alert('prev Tag :'+preTable.tagName);
			
			if(preTable != null && preTable.tagName != undefined){
			
				//alert('prev Tag :'+preTable.tagName);
				if(preTable.tagName.toLowerCase() == "table"){

					var elements = preTable.getElementsByTagName("select");

					//alert('select length :'+elements.length);
					if(elements.length == 0){
						return 'false';
					}

									
					for(var elementIndex = 0; elementIndex < elements.length; elementIndex++) 
					{
						var element = elements[elementIndex];
					
						var titleAttr = element.getAttribute('title');
						//alert('titleAttr :'+titleAttr);
						if(titleAttr == "Select record set"){
							//alert('inside if :'+titleAttr);
							return xPaths[xPathIndex]+':'+'true';
						}else{
							//alert('inside else :'+titleAttr);
						}
					
						
					}
				}
			}	
			
		}else if(xPaths[xPathIndex].toLowerCase() == "m__id" || xPaths[xPathIndex] == "ASFNAVIGATESEL"){
			
			//alert('m__id :');
			
			var expSelectItems = new Array();
	
			/* Get Tables in the Web Page*/
			var expSelectItems = document.getElementsByTagName("select");
			
			//alert('tables.length :'+expSelectItems.length)
			/* Navigate through Tables and get the required Table based on firstCell */
			for(var i = 0; i < expSelectItems.length; i++)
			{
				/* Get Select Item */
				var selectItem = expSelectItems[i];
					
				var idAttribute = selectItem.getAttribute('id');
				//alert('idAttribute :'+idAttribute)
				if(xPaths[xPathIndex].toLowerCase() == "m__id"){
					
					
					if(idAttribute != null  && idAttribute.indexOf(xPaths[xPathIndex]) != -1){
						return xPaths[xPathIndex]+':'+'true';
					}
				}
				
							
				if(xPaths[xPathIndex] == "ASFNAVIGATESEL"){
					//alert('ASFNAVIGATESEL :'+idAttribute);
					if(idAttribute != null && idAttribute.lastIndexOf(xPaths[xPathIndex]) != -1){
						return xPaths[xPathIndex]+':'+'true';
					}
				}
					
				
			}
			
		}
		
		//alert('select1 :'+xPaths[xPathIndex].toLowerCase()+'index:'+xPathIndex);
	}
	//alert('at the end ');
	return 'false';
}



function selectListBoxElement(firstCell, tableIndex, index) {
	
	var selectIndex = 0;
	
	/* Get Table */
	var table = getTable(firstCell, tableIndex);
	
	/* Get Table Rows */
	var rows = table.rows;
	
	var preTable = table.previousSibling;
	
	//alert('prev Tag :'+preTable.tagName);
	
	var elements = preTable.getElementsByTagName("select");
	
	//alert('List Elements :'+elements.length);

	for(var elementIndex = 0; elementIndex < elements.length; elementIndex++) 
	{
		var element = elements[elementIndex];
		
		var titleAttr = element.getAttribute('title');
		
		if(titleAttr == "Select record set"){
			
			selectIndex = elementIndex;
			break
		}
		
		//alert('id Attribute :'+idAttr);
	}

	elements[selectIndex].selectedIndex = index;

	elements[selectIndex].fireEvent('onchange');
	elements[selectIndex].fireEvent('onblur');
}



function checkCompExistance(firstCell, tableIndex, rowIndex, columnIndex, userTag) {

	var cell = getTableCell(firstCell, tableIndex, rowIndex, columnIndex);
	var elementExists = -1;
	
	if(cell) {
	
		userTag = userTag.toLowerCase();
		tag = userTag;
		
		if (userTag == "checkbox" || userTag == "radio"){
			tag="input";
		}
		
		var elements = cell.getElementsByTagName(tag);
	
		for(var elementIndex = 0; elementIndex < elements.length; elementIndex++) 
		{
			var element = elements[elementIndex ];
				
			if( tag == "img") {
				
				if(element.getAttribute('alt') != ''){
					return elementIndex;
				}else if(element.src != ''){
					return elementIndex;
				}
				
			}else if(tag == "input") {
				if(userTag == "checkbox")
				{
					if(element.type == "checkbox") 
					{
						return elementIndex;
					}
				}else if(userTag == "radio") 
				{
					if(element.type == "radio") 
					{
						return elementIndex;
					}
				} else {
					if(element.type != "hidden") 
					{
						return elementIndex;
					}
					
				}
							
			}else if(tag == "select") {
							
				return elementIndex;
				
			} else if(tag == "span") {
				
				if(element.text != undefined && element.text != ''){
					return elementIndex;
				}else if(element.innerText != undefined  && element.text != ''){
					return elementIndex;
				}else if(element.textContent != undefined  && element.text != ''){
					return elementIndex;
				}
							
			}else if(tag == "div") {
				
				if(element.text != undefined && element.text != ''){
					return elementIndex;
				}else if(element.innerText != undefined  && element.text != ''){
					return elementIndex;
				}else if(element.textContent != undefined  && element.text != ''){
					return elementIndex;
				}
							
			}else if(tag == "a") {
				return elementIndex;
			}
		}
	}
	
	return elementExists;
}




//Match the table by firstCell content.
//rowIndex are zero-based value.
//columnIndex are zero-based value.
//index are zero-based value.
//tag are the tagname of target element
function getValueByType(firstCell, tableIndex, rowIndex, columnIndex, userTag, index) {
	var expComponents = new Array();
	var cell = getTableCell(firstCell, tableIndex, rowIndex, columnIndex);
	if(cell) {
		userTag = userTag.toLowerCase();
		tag = userTag;
		
		if (userTag == "checkbox" || userTag == "radio"){
			tag="input";
		}
		
		if(tag == "td"){
			return getInnerText(cell);
		}

		var elements = cell.getElementsByTagName(tag);
		
		/* Get all the componenttype specific components*/
		for(var elementIndex = 0; elementIndex < elements.length; elementIndex++) {
			
			var element = elements[elementIndex];
			
			if (element.type != "hidden" ){
					expComponents.push(element);
			}
		}

		elements = expComponents;
		
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
				}else if(getInnerText(element) != undefined  && element.text != ''){
					return getInnerText(element);
				}else if(element.textContent != undefined  && element.text != ''){
					return element.textContent;
				}
							
			}else if(tag == "div") {
				
				if(element.text != undefined && element.text != ''){
					return element.text;
				}else if(getInnerText(element) != undefined  && element.text != ''){
					return getInnerText(element);
				}else if(element.textContent != undefined  && element.text != ''){
					return element.textContent;
				}
							
			}else if(tag == "a") {
				return getInnerText(element);
				
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
		//var elements = cell.getElementsByTagName(tag);

		var elements = new Array();
		
		if(tag == "button"){
			
			var buttonEle = cell.getElementsByTagName("button");
			var intEle = cell.getElementsByTagName("input");
			//alert("size:"+intEle.length);
			var intButtonEle = new Array();
			
			for(var index = 0; index < intEle.length; index++) {
				
				var inpElement = intEle[index];
				//alert("type:"+inpElement.getAttribute('type'));
				
				if(inpElement.getAttribute('type') == "button"){
					intButtonEle.push(inpElement);
				}else if(inpElement.getAttribute('type') == "file"){
					intButtonEle.push(inpElement);
				}
				
			}
			
			//alert("size b:"+buttonEle.length);
			//alert("size: "+intButtonEle.length);

			if(buttonEle.length == 0){
				elements  = intButtonEle;
			}else if(intButtonEle.length == 0){
				elements  = buttonEle;
			}else{
				elements = buttonEle.concat(intButtonEle);
			}

			//alert("size:"+elements.length);
			
		}else{ 
		 
			elements = cell.getElementsByTagName(tag);

		}



		
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
						var actCompValue = getInnerText(element);
						//alert('actCompValue :'+actCompValue);
						//alert('compValue :'+compValue);
						if(actCompValue == compValue){
							elementExists=true;
						}
					}else if(tag == "button"){
						var actCompValue = element.getAttribute('title');;
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

function getCellProperties(firstCell, tableIndex, rowIndex, columnIndex, compType, compIndex){

	var properties = "";
	var propertyTypes = new Array();
	propertyTypes[0] = "id";
	propertyTypes[1] = "name";
	
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
		for(var elementIndex = 0; elementIndex < elements.length; elementIndex++) {
			
			var element = elements[elementIndex];
			
			if(element.type == "hidden"){
				continue;
			}
			
			for(var propIndex = 0; propIndex < propertyTypes.length; propIndex++){
				
				var propertyType = propertyTypes[propIndex];
				var propertyVal = element.getAttribute(propertyType);
				
				if(propertyVal != "" && propertyVal != null){
					
					if(properties == ""){
						properties = propertyType+":::"+propertyVal;
					}else{
						properties = properties + "~~" + propertyType+":::"+propertyVal;
					}
				
				}
				
			}
		}
	}
	
	return properties;
	
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
		for(var elementIndex = 0; elementIndex < elements.length; elementIndex++) {
			
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
				
				if (element.type != "radio" && element.type != "checkbox" && element.type != "hidden" ){
					expComponents.push(element);
				}
			}else{
				expComponents.push(element);
			}
		}
		
		

		/* Setting all the required compenent type components like all checkboxes or all radio buttion or all inputs or all selectboxes with in a cell */
		elements = expComponents;
		
		
		if(elements) {
			//alert("input elements which are not hidden :"+elements.length)

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
							
							//element.fireEvent('onchange');
							//element.fireEvent('onblur');
							element.fireEvent('onclick');

							//var event = document.createEvent("HTMLEvents");
							//event.initEvent("click", true, true);
							//element.dispatchEvent(event);

							var event1 = document.createEvent("HTMLEvents");
							event1.initEvent("change", true, true);
							element.dispatchEvent(event1);


							var event2 = document.createEvent("HTMLEvents");
							event2.initEvent("blur", true, true);
							element.dispatchEvent(event2);



							elementExists = true;
						}
					}else if(userTag == "radio") 
					{				
						if(element.type == "radio") 
						{
							element.checked = true;


							//element.fireEvent('onchange');
							//element.fireEvent('onblur');
							element.fireEvent('onclick');

							var event = document.createEvent("HTMLEvents");
							event.initEvent("change", true, true);
							element.dispatchEvent(event);


							var event = document.createEvent("HTMLEvents");
							event.initEvent("blur", true, true);
							element.dispatchEvent(event);


							elementExists = true;
						}
					} else if(userTag == "input") 
					{
						//element.fireEvent('onclick');
						
						//element.style.backgroundColor = "#FF0000";
						
						element.value = valueToEnter;
						
						//element.fireEvent('onchange');
						//element.fireEvent('onblur');
						//element.fireEvent('blur');
						//element.fireEvent('focusout');

						var event = document.createEvent("HTMLEvents");
						event.initEvent("change", true, true);
						element.dispatchEvent(event);


						var event = document.createEvent("HTMLEvents");
						event.initEvent("blur", true, true);
						element.dispatchEvent(event);
	
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
							//element.fireEvent('onclick');

							element.value = items[selectIndex].value;
							
							//element.fireEvent('onchange');
							//element.fireEvent('onblur');

							var event = document.createEvent("HTMLEvents");
							event.initEvent("change", true, true);
							element.dispatchEvent(event);


							var event = document.createEvent("HTMLEvents");
							event.initEvent("blur", true, true);
							element.dispatchEvent(event);
				
							elementExists = true;
							break;
						}
					}
				}else if(tag == "textarea") {
					//element.fireEvent('onclick');
					element.value = valueToEnter;

					//element.fireEvent('onchange');
					//element.fireEvent('onblur');

						var event = document.createEvent("HTMLEvents");
						event.initEvent("change", true, true);
						element.dispatchEvent(event);


						var event = document.createEvent("HTMLEvents");
						event.initEvent("blur", true, true);
						element.dispatchEvent(event);

					elementExists = true;
				}
			} 
			
		}
	}
	
	return elementExists;
}


function isTableSplit(table)
{
	var tableSplit = false;
	var expTable = table;
	
	tableSplit = findingRowContentTable(table, "tableExists");
	
	//var parentTable = expTable.parentNode;
	
    //This logic is for handling 
	//1. web table in two table tags (one table tag for columns & other table tag for row data)
	//2. web table in single table tag
	//if(parentTable.tagName.toLowerCase() == "div" ){
		//var table = parentTable.nextSibling.firstChild;
		//&& parentTable.nextSibling.firstChild.tagName.toLowerCase() == "table"
		//if(table.tagName.toLowerCase() == "table"){
		 // expTable = table;
		  ////tableSplit = true;
		  //alert('Next Table:'+table.tagName);
		//}
		
	//}
	
	return tableSplit;

}

function getRowContentTable(table)
{
	
	var expTable = table;
	
	expTable = findingRowContentTable(table, "getTable");
	
	//alert('Table :'+table.tagName);
	//alert('Table Parent :'+parentTable.tagName);
	
	//This logic is for handling splitted table only in a page
	//if(parentTable.tagName.toLowerCase() == "div" ){
		//expTable = parentTable.nextSibling.firstChild;
	//}
	
	
    //This logic is for handling 
	//1. web table in two table tags (one table tag for columns & other table tag for row data)
	//2. web table in single table tag
	
	//if(parentTable.tagName.toLowerCase() == "div" ){
		//var table = parentTable.nextSibling.firstChild;
		
		//if(table.tagName.toLowerCase() == "table"){
		 // expTable = table;
		 
		//}
		
	//}
	
	return expTable;
}


function findingRowContentTable(table, returnCondition)
{
	var tableSplit = false;
	var returnValue = table;
	
	var expTable = table;
	
	// Get Parent Node for the table 
	var parentTable = expTable.parentNode;

	//Check, if the parent table is "div" tag or not
	if(parentTable.tagName.toLowerCase() == "div" ){

		//Get the next Sibling of the Parent Node
		var comp = parentTable.nextSibling
		
		//If Next Sibling is null, navigate till the sibling is not null
		while(comp == null){
			
			parentTable = parentTable.parentNode;
			//alert('Parent Table Tag :'+parentTable.tagName);
			
			comp = parentTable.nextSibling
		}
		
		//Now, Next Sibling is not null\
		//We need to navigate till first children is "div"
		while(comp.tagName.toLowerCase() != "div"){
			
			comp = comp.firstChild;		
		}
		
		
		//Get the table tag of content table
		var table = comp.firstChild;
		
		if(table.tagName.toLowerCase() == "table"){
		  
			var elements = comp.getElementsByTagName("table");
			
			//alert('Table Length:'+elements.length);

			for(var elementIndex = 0; elementIndex < elements.length; elementIndex++) {

				var element = elements[elementIndex];

				if(element != null){
					
					var table_id = elements[elementIndex].getAttribute('id');
					//alert('Table id:'+elements[elementIndex].getAttribute('id'));

					if(table_id != null){
						var contentPos = elements[elementIndex].getAttribute('id').search("Content");
						//alert('contentPos '+contentPos );
						if(contentPos > 0){
							table = elements[elementIndex];
							break;
						}

						var contentPos = elements[elementIndex].getAttribute('id').search("content");
						//alert('contentPos '+contentPos );
						if(contentPos > 0){
							table = elements[elementIndex];
							break;
						}
					}

					
					
				}
			
			}

			expTable = table;
		  	tableSplit = true;
		  	//alert('Next Table:'+table.tagName);
		}
		
		//alert('Parent Table Tag Final :'+comp.tagName);
	}	
	
	if(returnCondition.toLowerCase() == "gettable"){
	
		returnValue = expTable;
		//alert('Final Tag :'+returnValue.tagName);
	}
	
	if(returnCondition.toLowerCase() == "tableexists"){
		returnValue = tableSplit;
		//alert('Final Tag :'+returnValue);
	}

	return returnValue;
}



//Match the table by firstCell content.
//rowIndex are zero-based value.
//columnIndex are zero-based value.
function getTableCell(firstCell, tableIndex, rowIndex, columnIndex)
{
	/* Get Table */
	var table = getTable(firstCell, tableIndex);
	
	var tableSplit = isTableSplit(table);
	
	table = getRowContentTable(table);
	
	/* Get Table Rows */
	var rows = table.rows;
	
	//alert('rows.length ' + rows.length)
	if(rowIndex < (rows.length+1)) 
	{
		//alert('RowIndex :'+rowIndex);
		var rowStartIndex = findRowIndex(rows, firstCell,tableSplit);

		//alert('rowStartIndex  :'+rowStartIndex );
		var actualRowIndex = parseInt(rowStartIndex)+parseInt(rowIndex)-parseInt(1);

		//alert('actualRowIndex   :'+actualRowIndex);

		/* Get Specified Row based on rowIndex*/
		var row = rows[actualRowIndex];

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
	
	//alert('tables.length :'+tables.length)
	/* Navigate through Tables and get the required Table based on firstCell */
	for(var i = 0; i < tables.length; i++)
	{
		/* Get First Table */
		var table = tables[i];
		
		/* Get All Rows in the specifed Table*/
	    var rows = table.rows;
		
		if(rows.length > 0) {
			
			/* Get Column Row Number */
			var columnRow = findColumnsROW(rows);
			//alert('columnRow: '+columnRow )

			if(columnRow >= 0){
				
				var row = rows[columnRow];
				//var row = rows[0];
				//alert('rowLength1: '+rows.length )	
				var colLength = row.cells.length;
				//alert('colLength1: '+colLength )	
				/* 1. Compare the First Column with Expected Value
				   2. If Matched, Get the Required Tables */
			
				for(var colIndex = 0; colIndex < colLength; colIndex++)
				{
					var column = row.cells[colIndex];
					//var columnValue = column.innerText;
					var columnValue = getInnerText(column);
					//alert('Table '+i+" Matched Column:"+column.innerText+'hi');
					if(trim(columnValue) == trim(firstCell)) {
						//alert('Table '+i+" Matched Column:"+column.innerText);
						expTables.push(table);
						break;
					}	
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
	
	/**
		This is to navigate through the tables for identifying the column with 'td' tag.
	*/
	if(expTables.length == 0){
		
		for(var i = 0; i < tables.length; i++)
		{
			/* Get First Table */
			var table = tables[i];
		
			/* Get All Rows in the specifed Table*/
	    		var rows = table.rows;

			//alert('tables index :'+i)
		
			if(rows.length > 0) {
			
				/* Get Column Row Number */
				var columnRow = findColumnsRowsWithoutTH(rows, firstCell);
				
				if(columnRow >= 0){
				
					expTables.push(table);	
				}	
		
			}
		}
	}
	
	
	var expTable = expTables[tableIndex];
	
	var parentTable = expTable.parentNode;
	
	
	
	return expTable;
}



function findRowIndex(rows, columnname, tableSplit)
{
	var rowStartIndex = -1;
	var rowLength = 0;
	var colIndex = -1;
	var columnHeaderExists = false;
	var noOfColumnHeders = 0;
	
	if(tableSplit){
		return 0;
	}
	
	if(rows.length > 4){
		rowLength = 4;
	}else{
		rowLength = rows.length;
	}

	//alert("row Length :"+rowLength);
	// This method works only for the tables having "th"
	for(var rowIndex = 0; rowIndex < rowLength; rowIndex++){
	
		var row = rows[rowIndex];

		var colLength = row.cells.length;
		
		if(colLength > 1){
			colIndex = 1;
		}else{
			colIndex = 0;
		}
		
		//alert("row.cells[colIndex].tagName.toLowerCase() :"+row.cells[colIndex].tagName.toLowerCase());

		if (row.cells[colIndex].tagName.toLowerCase() == "th")
        {       
				noOfColumnHeders = parseInt(noOfColumnHeders) + parseInt(1);       	
				columnHeaderExists = true;
				
        }else{
			columnHeaderExists = false;
		}
		
		if(noOfColumnHeders > 0 && columnHeaderExists == false){
			rowStartIndex = rowIndex;
			break;
		}	
	}

	//alert("rowStartIndex"+rowStartIndex);
	
	if(rowStartIndex == -1)
	{
		var columStartIndex = findColumnsRowsWithoutTH(rows, columnname);

		//alert("columStartIndex :"+columStartIndex );

		rowStartIndex  = parseInt(columStartIndex) + parseInt(1)
	}
	
	//alert("rowStartIndex1:"+rowStartIndex);

	return rowStartIndex;
}





function findColumnsRowsWithoutTH(rows, columnName)
{
	var columnRow = -1;
	var rowLength = 0;
	var colLength = 0;

	if(rows.length > 4){
		rowLength = 4;
	}else{
		rowLength = rows.length;
	}

	for(var rowIndex = 0; rowIndex < rowLength; rowIndex++){
	
		var row = rows[rowIndex];

		var colLength = row.cells.length;
	
		if(row.cells.length > 4){
			colLength = 4;
		}else{
			colLength = row.cells.length;
		}

		for(var colIndex = 0; colIndex < colLength ; colIndex++){
		
			var column = row.cells[colIndex];

			//var columnValue = column.innerText;
			var columnValue = getInnerText(column);
			
			//alert('Matched Column:'+columnValue+'~~');

			if(trim(columnValue) == columnName) {
				
				columnRow = rowIndex;
				break;
			}	
			
		}
		
		if(columnRow >= 0){
			break;
		}
		
	}

	return columnRow;

}

function findColumnsROW(rows)
{
	var columnRow = -1;
	var rowLength = 0;
	var colLength = 0;

	if(rows.length > 4){
		rowLength = 4;
	}else{
		rowLength = rows.length;
	}

	//alert("row Length :"+rowLength);

	for(var rowIndex = 0; rowIndex < rowLength; rowIndex++){
	
		var row = rows[rowIndex];

		var colLength = row.cells.length;
		//alert("colLength :"+colLength);
		if(row.cells.length > 4){
			colLength = 4;
		}else{
			colLength = row.cells.length;
		}

		for(var colIndex = 0; colIndex < colLength ; colIndex++){
			

			if (row.cells[colIndex].tagName.toLowerCase() == "th")
               {
                		columnRow = rowIndex;
						//alert("columnRow1 :"+columnRow );
					break;
               }
			
		}
		
		//alert("columnRow2 :"+columnRow );
		
		if(columnRow >= 0){
			break;
		}
		
	}
	
	//alert("columnRow 3:"+columnRow );

	return columnRow;

}


//Match the table by firstCell content.
//columnIndex are zero-based value.
//columnValue is the innerText of target table cell.
function getColumns(firstCell,tableIndex)
{
	
	var ret = new Array();
        var colCaptureInfo = new Array();
	
	/* Get Required Table */
	var table = getTable(firstCell ,tableIndex);
	
	/* Get Table Rows */ 	
	var rows = table.rows;
	//alert(rows.length);

	if(rows.length > 0) {
	
		//Get the Column Row Number
		var columnRow = findColumnsROW(rows);
		//alert('columnRow  :'+columnRow );

		if(columnRow < 0){
			columnRow = findColumnsRowsWithoutTH(rows, firstCell);
		}
		
		if(columnRow >= 0){
			
			//Get Columns
			var row = rows[columnRow];
		
			var colLength = row.cells.length;


			var maxRowSpan = getMaxRowSpan(rows, columnRow);
	
			//alert("Max Row Span :"+maxRowSpan);
			
			var columnRowsIndex = maxRowSpan+columnRow+4;

			//alert("columnRowsIndex:"+columnRowsIndex);

			for(var index=0; index < columnRowsIndex; index++){
				
				
				colCaptureInfo[index] = 0;
			}
			
		
			var columns = getColumnsWithSpecificColumnSpan1(rows, columnRow, 0, colLength, colCaptureInfo );
			//var columns = getColumnsWithSpecificColumnSpan(rows, columnRow, 0, colLength);
			
			//alert("Columns :"+columns);
			ret = columns.split("~~");	
			
			// for(var colIndex = 0; colIndex < colLength; colIndex++)
			// {
				// var column = row.cells[colIndex];
				// var columnValue = column.innerText;
			
				// if(column.innerText == undefined || column.innerText == ''){
					// columnValue = 'empty';	
				// }
				//alert('Column value :'+columnValue);
	
				// ret.push(columnValue);
			// }
		}
		
	}	
	
	return ret;
}


function getMaxRowSpan(rows, columnRow){

	//Get Columns
	var row = rows[columnRow];
	
	var maxRowSpan = 1;
	
	for(var colIndex = 0; colIndex < row.cells.length; colIndex++)
	{
		var column = row.cells[colIndex];
		
		var rowSpan = column.rowSpan;//.getAttribute('rowspan');
		
		if(maxRowSpan <= rowSpan){
			maxRowSpan = rowSpan;
		}
		
	}

	return maxRowSpan;
}

function getMaxColumnSpan(rows, columnRow){

	//Get Columns
	var row = rows[columnRow];
	
	var maxColSpan = 1;
	
	for(var colIndex = 0; colIndex < row.cells.length; colIndex++)
	{
		var column = row.cells[colIndex];
		
		var colSpan = column.colSpan;//.getAttribute('colspan');
		
		if(maxColSpan <= colSpan){
			maxColSpan = colSpan;
		}
		
	}

	//alert("columnRow :"+columnRow+"      Max Column Span :"+maxColSpan);
	return maxColSpan;
}


function getColumnsWithSpecificColumnSpan1(rows, columnRow, startColIndex, endColIndex, colCaptureInfo){
	
	//Get Columns
	var row = rows[columnRow];
		
	var nextRowColIndex = 0;
	
	var columns = "";
	
	var maxRowSpan = getMaxRowSpan(rows, columnRow);
	var maxColSpan = getMaxColumnSpan(rows, columnRow);
	
	//alert("Max Row Span :"+maxRowSpan);
	//alert("columnRow :"+columnRow+"   Max Row Span :"+maxRowSpan+"  Max Column Span :"+maxColSpan);
	for(var colIndex = startColIndex; colIndex < endColIndex; colIndex++)
	{
		var column = row.cells[colIndex];
		
		//alert('Column'+column);

		if(column == undefined || column == null){
			//alert('Column:'+column);
			continue;
		}

		var rowSpan = column.rowSpan;//.getAttribute('rowspan');
		var colSpan = column.colSpan;//.getAttribute('colspan');
		
		//alert("columnRow"+columnRow+':rowspan:'+rowSpan+':colSpan:'+colSpan+':maxrowspan:'+maxRowSpan+':maxcolSpan:'+maxColSpan);
		
		if(rowSpan == maxRowSpan){ //(rowSpan == 1 && colSpan == 1) 
				
			//alert("Column Span :"+colspan);
				
			//var columnValue = column.innerText;
			//alert('Column value :'+columnValue);

			columnValue = getInnerText(column);
			

			
			if(columnValue == undefined || columnValue == '' || columnValue == ' '){
				columnValue = 'empty';	
			}
			
			//alert('Column value :'+columnValue);
			
			//if(columns == ""){
				//columns = columnValue;
			//}else{
				//columns = columns + "~~" + columnValue;
			//}
			
			if(rowSpan == 1 && maxColSpan > 1){
				
				// columnRow == Current Column row
				var rowNumber = columnRow + rowSpan;
			
				var nextRowColStartIndex = colCaptureInfo[rowNumber] ;
				var nextRowColEndIndex   = colCaptureInfo[rowNumber] + colSpan ;
				colCaptureInfo[rowNumber] = nextRowColEndIndex ; //colCaptureInfo[rowNumber] = 6 , means six elements are captured i.e when colSpan = 6 i.e 0 to 5 elements
			
				//alert('nextRowColStartIndex:'+nextRowColStartIndex+'nextRowColEndIndex:'+nextRowColEndIndex);
				
				if(columns == ""){
					//alert("before empty "+colIndex +" Columns in fun :"+columns);
					columns =  getColumnsWithSpecificColumnSpan1(rows, rowNumber,nextRowColStartIndex,nextRowColEndIndex,colCaptureInfo);
					//alert("after empty "+colIndex +" Columns in fun :"+columns);
				}else{
					//alert("before colIndex "+colIndex +" Columns in fun :"+columns);
					columns =  columns + "~~" + getColumnsWithSpecificColumnSpan1(rows, rowNumber,nextRowColStartIndex,nextRowColEndIndex,colCaptureInfo);
					//alert("after colIndex "+colIndex +" Columns in fun :"+columns);
				}
				
				
				
			}else{
			
				if(columns == ""){
					columns = columnValue;
				}else{
					columns = columns + "~~" + columnValue;
				}
			
			}
			
			//alert("columns :"+columns);
			
		}else if(rowSpan < maxRowSpan){
		
			//var a = Number(columnRow) + Number(rowSpan);
			//var rowNumber = a;
			//alert(a);
			var rowNumber = Number(columnRow) + Number(rowSpan);
			//	var rowNumber = columnRow + rowSpan;
			//alert(":rowNumber:"+rowNumber);
			var nextRowColStartIndex = Number(colCaptureInfo[rowNumber]) ;
			var nextRowColEndIndex   = Number(colCaptureInfo[rowNumber]) + Number(colSpan) ;
			colCaptureInfo[rowNumber] = nextRowColEndIndex ; //colCaptureInfo[rowNumber] = 6 , means six elements are captured i.e when colSpan = 6 i.e 0 to 5 elements
			
			//alert(":rowNumber:"+rowNumber+':nextRowColStartIndex:'+nextRowColStartIndex+':nextRowColEndIndex:'+nextRowColEndIndex+':colCaptureInfo:'+colCaptureInfo);
			
			if(columns == ""){
				//alert("before empty "+colIndex +" Columns in fun :"+columns);
				columns =  getColumnsWithSpecificColumnSpan1(rows, rowNumber,nextRowColStartIndex,nextRowColEndIndex,colCaptureInfo);
				//alert("after empty "+colIndex +" Columns in fun :"+columns);
			}else{
				//alert("before colIndex "+colIndex +" Columns in fun :"+columns);
				columns =  columns + "~~" + getColumnsWithSpecificColumnSpan1(rows, rowNumber,nextRowColStartIndex,nextRowColEndIndex,colCaptureInfo);
				//alert("after colIndex "+colIndex +" Columns in fun :"+columns);
			}
			
		
		
		}

		//alert("rowspan = maxrowspan colIndex "+colIndex +" Columns in fun :"+columns);
	}
	
	//alert("Columns in function :"+columns);
	
	return columns;

}




function getColumnsWithSpecificColumnSpan(rows, columnRow, startColIndex, endColIndex){
	
	//Get Columns
	var row = rows[columnRow];
		
	var nextRowColIndex = 0;
	
	var columns = "";
			
	for(var colIndex = startColIndex; colIndex < endColIndex; colIndex++)
	{
		var column = row.cells[colIndex];
		
		var colSpan = column.getAttribute('colspan');
		
		if(colSpan == 1){
				
			//alert("Column Span :"+colspan);
				
			var columnValue = column.innerText;
			
			if(column.innerText == undefined || column.innerText == ''){
				columnValue = 'empty';	
			}
			
			//alert('Column value :'+columnValue);
			
			if(columns == ""){
				columns = columnValue;
			}else{
				columns = columns + "~~" + columnValue;
			}
			
			
		}else {
		
			var nextRowColStartIndex = nextRowColIndex;
			var nextRowColEndIndex = nextRowColIndex + colSpan ;
			
			if(columns == "" && colIndex == 0){ // first column is splitted to some columns
				columns = getColumnsWithSpecificColumnSpan(rows, columnRow+1,nextRowColStartIndex,nextRowColEndIndex);
			}else{
				columns = columns + "~~" + getColumnsWithSpecificColumnSpan(rows, columnRow+1,nextRowColStartIndex,nextRowColEndIndex);
			}

			
			// Provious code
			//columns = columns + "~~" + getColumnsWithSpecificColumnSpan(rows, columnRow+1,nextRowColStartIndex,nextRowColEndIndex);
			
			nextRowColIndex = nextRowColEndIndex;
		}
	}
	
	//alert("Columns in fun :"+columns);
	
	return columns;

}

//Match the table by firstCell content.
//columnIndex are zero-based value.
//columnValue is the innerText of target table cell.
function getRowCount(firstCell,tableIndex)
{
	

	var ret = new Array();
	
	//alert("firstCell :"+firstCell);

	/* Get Required Table */
	var table = getTable(firstCell ,tableIndex);
	
	table = getRowContentTable(table);
	
	/* Get Table Rows */ 	
	var rows = table.rows;
	
	//alert("rows :"+rows.length);
	
	return rows.length;
}

//Match the table by firstCell content.
//columnIndex are zero-based value.
//columnValue is the innerText of target table cell.
function getRowStartIndex(firstCell,tableIndex)
{
	var ret = new Array();
	
	/* Get Required Table */
	var table = getTable(firstCell ,tableIndex);
	
	var tableSplit = isTableSplit(table);
	
	table = getRowContentTable(table);
	
	/* Get Table Rows */ 	
	var rows = table.rows;
	
	var rowStartIndex = findRowIndex(rows, firstCell,tableSplit);
	
	return rowStartIndex;
}


function trim(str) {
    return str.replace(/^\s+|\s+$/g,'');
} 


function getInnerText(cell){


	var columnValue = cell.textContent;
	

	if(columnValue == undefined){
		columnValue = cell.innerText;
	}

	if(columnValue.indexOf('Effort(') != -1 || columnValue.indexOf('Cost(') != -1){// This issue was occured in Projects Advacedpack flow where data is availalbe as "1.0 Effort(1.0 Effort)" in the flow "PJT_Workplan_Plan_To_Execution_FSVE"
		//alert("textContent inside if "+columnValue);
		//Not replacing any data
	}else{
		columnValue = columnValue.replace(/t\(.*?\)/g,''); //Used for Replacting t('5','6') or t(8,0) or t('5')
	}
	//columnValue = columnValue.replace(/t\(.*?\)/g,''); //Used for Replacting t('5','6') or t(8,0) or t('5')

	
	if(cell.getElementsByTagName('script').length > 0){
				
		var scriptTagLength = cell.getElementsByTagName('script').length;
		
		for(var scriptIndex = 0; scriptIndex < scriptTagLength; scriptIndex++) 
		{
			var scriptTagVal = cell.getElementsByTagName('script')[scriptIndex].textContent;
			
			scriptTagVal = scriptTagVal.replace(/t\(.*?\)/g,'');

			if(scriptTagVal == null || scriptTagVal == ''){
				return columnValue;
			}
			
			columnValue = columnValue.replace(scriptTagVal,'');				
		
		}
		
	}

	return columnValue;

}

function getInnerText1(cell){


	var columnValue = cell.textContent;
	

	if(columnValue == undefined){
		columnValue = cell.innerText;
	}

	if(columnValue.indexOf('Effort(') != -1 || columnValue.indexOf('Cost(') != -1){// This issue was occured in Projects Advacedpack flow where data is availalbe as "1.0 Effort(1.0 Effort)" in the flow "PJT_Workplan_Plan_To_Execution_FSVE"
		//alert("textContent inside if "+columnValue);
		//Not replacing any data
	}else{
		columnValue = columnValue.replace(/t\(.*?\)/g,''); //Used for Replacting t('5','6') or t(8,0) or t('5')
	}
	//columnValue = columnValue.replace(/t\(.*?\)/g,''); //Used for Replacting t('5','6') or t(8,0) or t('5')

	
	if(cell.getElementsByTagName('script').length > 0){
				
		
		var scriptTagVal = cell.getElementsByTagName('script')[0].textContent;

		scriptTagVal = scriptTagVal.replace(/t\(.*?\)/g,'');

		if(scriptTagVal == null || scriptTagVal == ''){
			return columnValue;
		}
				
		var scriptTagPostion = columnValue.search(scriptTagVal.slice(1,5));
				
		columnValue = columnValue.substr(0,scriptTagPostion-1);
				
				
	}

	return columnValue;

}


function getInnerText_old(cell){

	var columnValue = column.textContent;
			
			
	if(column.getElementsByTagName('script').length > 0){
	
		var scriptTagVal = column.getElementsByTagName('script')[0].textContent;

		//alert("scriptTagVal  :"+scriptTagVal);

		columnValue = columnValue.replace(scriptTagVal,'');
	}
	
	return columnValue;

}


