<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/openmrsSearch.js" />
<link href="${pageContext.request.contextPath}/moduleResources/household/css/css-table.css" type="text/css" rel="stylesheet" /> 
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

<style>
	#openmrsSearchTable_wrapper{
	/* Removes the empty space between the widget and the Create New Patient section if the table is short */
	/* Over ride the value set by datatables */
		min-height: 0px; height: auto !important;
	}
	.dataTables_wrapper {
	    clear: both;
	    min-height: 0px;
	    position: relative;
	}
</style>
<openmrs:htmlInclude file="/dwr/util.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRPatientService.js"/>

<script type="text/javascript">
	var lastSearch;
	$j(document).ready(function() {
		new OpenmrsSearch("findPatients", false, doPatientSearch, doSelectHandler, 
			[	{fieldName:"identifier", header:omsgs.identifier},
				{fieldName:"givenName", header:omsgs.givenName},
				{fieldName:"middleName", header:omsgs.middleName},
				{fieldName:"familyName", header:omsgs.familyName},
				{fieldName:"age", header:omsgs.age},
				{fieldName:"gender", header:omsgs.gender},
				{fieldName:"birthdateString", header:omsgs.birthdate},
			],
			{
			  searchLabel: '<spring:message code="Patient.searchBox" javaScriptEscape="true"/>'
			  <c:if test="${not empty param.phrase}">
			      , searchPhrase: '<spring:message text="${ param.phrase }" javaScriptEscape="true"/>'
			  </c:if>
			});
		
		//set the focus to the first input box on the page(in this case the text box for the search widget)
		var inputs = document.getElementsByTagName("input");
	    if(inputs[0])
	    	inputs[0].focus();
	    
	    householdMembersTable = $j('#tblSelectedPerson').dataTable({
			"sDom": 'T<"clear">lfrtip',
			"bPaginate": false,
	        "bLengthChange": false,
	        "bFilter": false,
	        "bSort": false,
	        "bInfo": false,
	        "bAutoWidth": false
		});
	    		
	    
	});

	function doSelectHandler(index, data) {
		var selectedMembers = dwr.util.getValue("hiddenSelectedMembers");
		if (selectedMembers == ""){
			selectedMembers = data.patientId;
			dwr.util.setValue("hiddenSelectedMembers", selectedMembers);
		}else{
			selectedMembers +=  "," + data.patientId;
			dwr.util.setValue("hiddenSelectedMembers", selectedMembers);
		}
		householdMembersTable.fnAddData(["<input type=\"radio\" name=\"indexperson\" onClick=\"javascript:headPerson(" +
		                                 data.patientId + ")\">",data.givenName + " " + data.middleName +" " + data.familyName, data.identifier, data.birthdateString + "(" + data.age + ")", data.gender,"<input type='button' value='x' onclick='javascript:deleteClicked(" + data.patientId + ")' />"]); 
	}

	//searchHandler for the Search widget
	function doPatientSearch(text, resultHandler, getMatchCount, opts) {
		lastSearch = text;
		DWRPatientService.findCountAndPatients(text, opts.start, opts.length, getMatchCount, resultHandler);
	}
	
	function deleteClicked(id){
		//using the personid search through hiddenSelectedMembers
		// obtain the index. This index forms the row index to execute householdMembersTable.fnDeleteRow(intPlace, null, false);
		var strPersonId="";
		var noSeleted = [];
		var intPlace= -1;
        var oldPersonID= document.getElementById("hiddenSelectedMembers").value;
        if (oldPersonID.indexOf(",", 0) < 0)
        	noSeleted = [oldPersonID];
        else
        	noSeleted = oldPersonID.split(",");
		for(var x=0; x<(noSeleted.length); x++){
            if(noSeleted[x]==id){
                intPlace= x;
                householdMembersTable.fnDeleteRow(intPlace);
                if(noSeleted.length == 1)
                	householdMembersTable.fnClearTable();
            }else
                if(strPersonId=="")
                    strPersonId = noSeleted[x];
                else
                    strPersonId += "," + noSeleted[x];
		}
		$j("#hiddenSelectedMembers").val("");
		$j("#hiddenSelectedMembers").val(strPersonId);
		householdMembersTable.fnDraw();
	}
	
	function saveHousehold(){
		var errorDivElement = document.getElementById("errorDiv");
		var personList = document.getElementById("hiddenSelectedMembers").value;
		var hidentifierProvided = document.getElementById("householdIdentifer").value;
		var startDateProvided = document.getElementById("startDate").value;
		var headOrIndex = document.getElementById("hiddenIndex").value;
		var provider = document.getElementById("userHouseholdProvider").value;
		var hDefinition = ${hdef};
		var strArr = [hDefinition, personList, hidentifierProvided, startDateProvided, headOrIndex, provider];
		
		if((personList != "") && (headOrIndex != "") ){
			DWRHouseholdService.createHousehold(strArr, returnedSavedHousehold);
		}else{
			//$j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /> Please select people to form the household and indicate the index/head.');
			errorDivElement.style.display = 'inline';
			setTimeout("highlightError()",5000);
		}
	}
	
	function returnedSavedHousehold(data){
		var arrData = data.split(",");
		var errorDivElement = document.getElementById("errorDiv");
		var highlightDivElement = document.getElementById("highlightDiv");
		if(arrData[0]){
			$j(highlightDivElement).html(arrData[1]);
			highlightDivElement.style.display = 'inline';
			setTimeout("highlightSuccess()",5000);
		}else{
			$j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" />' + arrData[1]);
			errorDivElement.style.display = 'inline';
			setTimeout("highlightError()",5000);
		}
	}
	
	function headPerson(person){
		$j("#hiddenIndex").val(person);
	}
	function selectHouseholdProvider(userid,provider){
		var proHouseholdId = null;
		if(provider != null){
			proHouseholdId=provider.personId;
		}
		$j("#userHouseholdProvider").val(proHouseholdId);
		
	}
</script>
<table width="100%">
	<tr>
		<td valign="top"> 
				<b class="boxHeader">Select Household members:</b>
				<div class="box">
					<div class="searchWidgetContainer" id="findPatients"></div>
				</div>
				<br />
				<b class="boxHeader">Create a Household:</b>
				<div class="box">
					<div id="divSelectedPerson">
					<input type="hidden" id="hiddenSelectedMembers" name="hiddenSelectedMembers"/>
					<input type="hidden" id="hiddenIndex" name="hiddenIndex"/>
						<table class="lineTable" id="tblSelectedPerson" width="100%">
							<thead>
								<tr>
									<th>Index:</th>
									<th>Name:</th>
									<th>Identifier:</th>
									<th>Age:</th>
									<th>Gender:</th>
									<th>&nbsp;</th>
								</tr>
							</thead>
							<tbody id="bdySelectedPerson">
								
							</tbody>
						</table>
						<div class="css_clear"></div>
						<table>
							<tr>
								<td>Registration Date</td><td><input type="text" name="startDate" id="startDate" onClick="showCalendar(this)" /></td>
							</tr>
							<tr>
								<td>
								Pre-Generated Identifier</td><td><input type="text" name="householdIdentifer" id="householdIdentifer"/><br />
								<i>Leave this field empty if you want the system to generate for you the Household Identifier.</i></td>
							</tr>
							<tr>
								<td>Provider Id:</td>
								<td><openmrs_tag:userField formFieldName="userProviders" roles="Trusted+External+Application,Lab+Technician,Community+Health+Worker+Nutritionist,Clinician,Nurse,Psychosocial+Support+Staff,Pharmacist,HCT+Nurse,Outreach+Worker,Community+Health+Extension+Worker,Clinical+Officer,Provider" callback="selectHouseholdProvider" />
									<input type="hidden" id="userHouseholdProvider" size="10" />
								</td>
							</tr>
						</table>
						<br />
						<input type="button" onclick="saveHousehold()" value="Create household"/>
					</div>
				</div>
		</td>
	</tr>
	
	<!-- Check if this is a saved record. If then show the newly created household -->
	<tr>
		<td>
			
		</td>
	</tr>

</table>