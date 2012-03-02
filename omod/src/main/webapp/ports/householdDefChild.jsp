<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/openmrsSearch.js" />
<link href="${pageContext.request.contextPath}/moduleResources/household/css/css-table.css" type="text/css" rel="stylesheet" /> 

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#div1").show();$j("#div2").hide();$j("#div3").hide();$j("#div4").hide();$j("#div5").hide();
		$j("#div6").hide();$j("#div7").hide();$j("#div8").hide();$j("#div9").hide();
	});
	
	

</script>
<style>
	#openmrsSearchTable_wrapper{
	/* Removes the empty space between the widget and the Create New Patient section if the table is short */
	/* Over ride the value set by datatables */
		min-height: 0px; height: auto !important;
	}
</style>
<openmrs:htmlInclude file="/dwr/util.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRPatientService.js"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/openmrsSearch.js" />

<script type="text/javascript">
	var lastSearch;
	$j(document).ready(function() {
		$j("tblSelectedPerson").hide();
		new OpenmrsSearch("findPatients", false, doPatientSearch, doSelectionHandler, 
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
	    		
	    
	});

	function doSelectionHandler(index, data) {
		DWRHouseholdService.getHouseholdsByPersonID(data.patientId, getHouseholdMembership);
	}

	//searchHandler for the Search widget
	function doPatientSearch(text, resultHandler, getMatchCount, opts) {
		lastSearch = text;
		DWRPatientService.findCountAndPatients(text, opts.start, opts.length, getMatchCount, resultHandler);
	}
	
	/*function getHouseholdsByPersonID(val){
		DWRHouseholdService.getHouseholdsByPersonID(val, getHouseholdMembership);
	}*/
	
	function getHouseholdMembership(data){
		$j("tblSelectedPerson").show();
		dwr.util.removeAllRows("bdySelectedPerson");
		var count = 1;
		dwr.util.addRows("bdySelectedPerson", data,[
		function(householdMembership) { return count++; },
		function(householdMembership) { return "<a href='#' onclick='javascript:forwardMem(" + householdMembership.householdMembershipGroups.id +")'>" +
			householdMembership.householdMembershipGroups.householdIdentifier + "</a>";},
		function(householdMembership) { return householdMembership.startDate; },
		function(householdMembership) { return householdMembership.endDate; }
		], { escapeHtml:false }); 
	}
	
	function forwardMem(stdin){
		/* PULL MEMBERS HERE
		$j.get("ports/householdDefChild.form?child=" + stdin,
			function(data){
				$j("#divchild").html(data);
			}
		);*/
	}
	
	function pullByIdentifier(){
		var val = document.getElementById("searchFieldHI").value;
		if ((val!= "") && (val.length > 3)){
			alert("Meets criteria1");
			
		}
	}
	
</script>
<style>
	legend {
		border: 1px solid #b9b7c0;
		padding: 2px 6px;
		font-size: small;
		font-weight: bold;
		color: #black;
		background-color: #DADCFF;
	}
	fieldset {
		border: 1px solid #c9c9c9;
		-webkit-border-radius: 7px;
		-moz-border-radius: 7px;
		padding: 0px;
		margin-bottom: 1px;
		margin-top: 1px;
		/** margin-left: auto; **/
		margin-right: 0px;
		background-color: #c9c9c9;
	}
</style>

<div class="boxHeader">
	<tr>
		<td><b>Definition</b></td><td>:&nbsp;${hdef.householdDefinitionsCode}</td>
	</tr>
	<br />
	<tr><td><b>In Full</b></td><td>:&nbsp;${hdef.householdDefinitionsCodeinfull}</td></tr>
	<tr><td><b>Description</b></td><td>:&nbsp;<i>${hdef.householdDefinitionsDescription}</i></td></tr>
	 
</div>

<table width="100%">
	<tr>
		<td width="25%" valign="top"> 
			<div>
				<fieldset>
					<div style="width: 100%; background-color: #c9c9c9;">
						<b>Summary : </b>
						<br />
						<table>
							<tr><td>Number of Households</td><td>:<a href="#">2</a></td></tr>
							<tr><td>Females in the definition</td><td>:<a href="#">2</a></td></tr>
							<tr><td>Males in the definition</td><td>:<a href="#">2</a></td></tr>
						</table>
					<!-- </div>
					<div style="width: 100%; background-color: #ccc; height: 1px;"></div>
					<div> -->
						<a href="#" onclick="javascript:openClose('8')">View Households(20)</a> &nbsp;<br /> 
						<a href="#" onclick="javascript:openClose('9')">Create Household</a>&nbsp;
					</div>
					
				</fieldset>
				<legend style="width:100%">Section 1</legend>
				<div>
					<a href="#" onclick="javascript:openClose('1')"><img src="${pageContext.request.contextPath}/moduleResources/household/images/home.png" alt="Home"/></a><br />
					<a href="#" onclick="javascript:openClose('2')">Out-patient</a><br />
					<a href="#" onclick="javascript:openClose('3')">Wards</a><br />
					<a href="#" onclick="javascript:openClose('4')">In-patient2</a><br />
					<a href="#" onclick="javascript:openClose('5')">Out-patient2</a><br />
				</div>
				<legend style="width:100%">Section 2</legend>
				<div>
					<a href="#" onclick="javascript:openClose('6')">Wards1</a><br />
					<a href="#" onclick="javascript:openClose('7')">Wards2</a>
				</div>
				
			</div>
		</td><td width="2%"><div class="rpt"></div></td>
		<td width="73%" valign="top"> 
			<div style="width: 100%;" align="center">
				<img src="${pageContext.request.contextPath}/moduleResources/household/images/pbreak.jpg" alt="-"/>
			</div>
			<!-- <div style="width: 100%; height: 1px; background-color: gray;"></div> -->
				<div id="div1">
						<b class="boxHeader">Find household by patient:</b>
						<div class="box">
							
							<div class="searchWidgetContainer" id="findPatients"></div>
							<br />
							<div id="tblSelectedPerson">
								<table class="lineTable">
									<thead>
										<tr>
											<th>&nbsp;</th>
											<th>Household Identifier:</th>
											<th>Start Date:</th>
											<th>End Date:</th>
										</tr>
									</thead>
									<tbody id="bdySelectedPerson">
										
									</tbody>
								</table>
							</div>
						</div>
						<br><br>
						<b class="boxHeader">Find household by identifier:</b>
						<div class="box">
							<table>
								<tbody>
									<tr><td>Household Identifier</td><td><input type="text" id="searchFieldHI" onkeypress="pullByIdentifier()"/></td></tr>
									<tr><td colspan="2">
										DATA
									</td></tr>
								</tbody>
							</table>
							: 
						</div>
						
				</div>
				<div id="div2">Selling maize 2</div>
				<div id="div3">Selling maize 3</div>
				<div id="div4">Selling maize 4</div>
				<div id="div5">Selling maize 5</div>
				<div id="div6">Selling maize 6</div>
				<div id="div7">Selling maize 7</div>
				<div id="div8">Selling maize 8</div>
				<div id="div9">Selling maize 9</div>
			
		</td>
	</tr>

</table>