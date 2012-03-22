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

	function highlightError(){
		var errorDivElement = document.getElementById("errorDiv");
		errorDivElement.style.display = 'none';
	}
	function highlightSuccess(){
		var highlightDivElement = document.getElementById("highlightDiv");
		highlightDivElement.style.display = 'none';
	}
	
</script>
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
<openmrs:htmlInclude file="/dwr/interface/DWRPersonService.js" />
<openmrs:htmlInclude file="/dwr/interface/DWRUserService.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/openmrsSearch.js" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />

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
		//var val = document.getElementById("searchFieldHI").value;
		var household=dwr.util.getValue("searchFieldHI");
		if ((household!= "") && (household.length > 3)){
			$j('#householdDetails').show();
			DWRHouseholdService.getHouseholdMems(household,fillTable);
			}
		else{
			$j('#householdDetails').hide();
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
	#errorDiv, #highlightDiv{
		height: 30px;
		top: 0px;
		z-index: 100;
		position: fixed ;
		width: 100%;
		font-size: 25px;
		margin-left: -10px;
		text-align: center;
		font-style: italic;
		opacity:0.8;
  		filter:alpha(opacity=80);
	}
	#highlightDiv{
		background-color: #FFFFDD;
	}
	#errorDiv{
		background-color: #FF9988;
	}
	.successfullyDone{
		background-color:#FFFF40;
		color:#000000;
	
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
<div id="errorDiv" style="display: none;">
	<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" />Check all marked with *
</div>
<div id="highlightDiv" style="display: none;" align="center">
	Household Saved successfully.
</div>
<table width="100%">
	<tr>
		<td width="25%" valign="top"> 
			<div class="box" >
				<!-- <fieldset> -->
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
						
					</div>
					
				
				<legend style="width:90%">Main</legend>
				<div>
					<a href="#" onclick="javascript:openClose('1')">Home</a><br />
					<%-- <a href="#" onclick="javascript:openClose('1')"><img src="${pageContext.request.contextPath}/moduleResources/household/images/home.png" alt="Home"/></a><br /> --%>
					<a href="#" onclick="javascript:openClose('8')">View Households(20)</a> &nbsp;<br /> 
					<a href="#" onclick="javascript:openClose2('9','${hdef.id}')">Create Household</a>&nbsp;
				<div>
				<!-- <legend style="width:90%">Section 1</legend>
				<div>
					<a href="#" onclick="javascript:openClose('2')">Out-patient</a><br />
					<a href="#" onclick="javascript:openClose('3')">Wards</a><br />
					<a href="#" onclick="javascript:openClose('4')">In-patient2</a><br />
					<a href="#" onclick="javascript:openClose('5')">Out-patient2</a><br />
				</div>
				<legend style="width:90%">Section 2</legend>
				<div>
					<a href="#" onclick="javascript:openClose('6')">Wards1</a><br />
					<a href="#" onclick="javascript:openClose('7')">Wards2</a>
				</div> -->
				<!-- </fieldset> -->
			</div>
		</td><td width="2%"><div class="rpt"></div></td>
		<td width="73%" valign="top"> 
			<%-- <div style="width: 100%;" align="center">
				<img src="${pageContext.request.contextPath}/moduleResources/household/images/pbreak.jpg" alt="-"/>
			</div> --%>
			<!-- <div style="width: 100%; height: 1px; background-color: gray;"></div> -->
				
			<div id="divAll">
			
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
					
				</script>
			
			
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
						
						<script type="text/javascript">
						$j(document).ready(function() {
							$j('#findMembers').click(function(event){
								
								//var val = document.getElementById("searchFieldHI").value;
								var household=dwr.util.getValue("searchFieldHI");
								if ((household!= "") && (household.length > 3)){
									$j('#householdDetails').show();
									DWRHouseholdService.getHouseholdMems(household,fillTable);
									}
								else{
									$j('#householdDetails').hide();
								}
								
							});
							
							//get some other stuff to load 
							membersToAdd = $j("#membersToAdd").dataTable({
								    	"bAutoWidth": false,
								    	"bLengthChange": true,
								    	"bJQueryUI": false
								    	}
						    );
								
						});
						</script>
						<script type="text/javascript">
						function fillTable(data){
							//remove all the rows 
							//come up with an array of data
							var arrDataSplit=new Array();
							var arrData=data.split("|");
							//alert(data); 
							//alert(arrData);
							
							
							dwr.util.removeAllRows("details");
							//add the required rows 
							//alert("blaaaa"+data.givenName);
							var count=1;
							//alert(arrData); 
							
							 for(var i=0;i<arrData.length;i++){
								 	arrDataSplit=arrData[i].split(",");
								 		
								 			dwr.util.addRows("details",[arrDataSplit],[
																						function() { return (count++);},
																						function() { return (arrDataSplit[0]) ; },
																						function() { return (arrDataSplit[1]) ; },
																						function() { return (arrDataSplit[2]) ; },
																						function() { return (arrDataSplit[3]) ; },
																						function() { return (arrDataSplit[4]) ; },
																						function() { return  '<input type="checkbox" id="void" name="void" onclick="display(this)" value="'+arrDataSplit[5]+'"  />' ; } ,
																						function() { return  '<input type="hidden" id="voidId" name="voidId"  value="'+arrDataSplit[5]+'" />'; } 
								 			                                           ], { escapeHtml:false }
											);
								 		 
								
							} 
								
							
						}
						function clickVoid(){
							 var memberIds=dwr.util.getValue("marktext");
							 var actOnVidedMembersNull=document.getElementById("actOnVidedMembersNull");
							 
							if(memberIds =='' || memberIds == null){
								
								actOnVidedMembersNull.style.display='inline';
							}
							else{
								actOnVidedMembersNull.style.display='none';
									if (confirm("Are you sure you want to void this person ?")) {
										$j(function() {
											$j( "#dialog" ).dialog({ 
												modal: true, 
												show: 'slide',
												height: 'auto',
												hide: 'slide',
												width: 450,
												position: 'top'
												
												});
										});
										
									}
							}	
									
						}
						function voidMembers(memberId,voidReason){
							voidReason=dwr.util.getValue("voidreason");
							memberId=dwr.util.getValue("marktext");
							var errordiv=document.getElementById("errordiv");
							var membersAfterVoidingList=document.getElementById("marktext");
							//alert(memberId);
							if(voidReason ==null || voidReason ==''){
								
								errordiv.style.display='inline';
							return false
							}
							else{
								errordiv.style.display='none';
								// this where the actual voiding take place 
								//alert(memberId + " "+ voidReason);
								DWRHouseholdService.voidMembers(memberId,voidReason,aftervoiding);
								// call the feel table function to refresh the table 
								var householdRefresh=dwr.util.getValue("searchFieldHI");
								
								//code close the dilag 
								setTimeout("closeVoidingDialog()",3000);
								DWRHouseholdService.getHouseholdMems(householdRefresh,fillTable);
								//reset the list 
								membersAfterVoidingList.value="";
								document.formMemebrs.actOnVidedMembers.disabled=true;
								return true;
							}
						}
						function aftervoiding(text){
							var divVoidedwell=document.getElementById("voidewell");
							divVoidedwell.style.display='inline';
						}
						
						function addMembers(){
							
							$j('#addMembers').click(function(event){
								dwr.util.removeAllRows("membersToAddDetails");
								$j("#patientId").val("");
								
								$j(function() {
									$j( "#addMembersToHousehold" ).dialog({ 
										modal: true, 
										show: 'slide',
										height: 'auto',
										hide: 'slide',
										width: 750,
										position: 'top'
										
										});
								});
								
								
							});
						}
						
						function selectedPatient(userid,person){
							var personIdentity=null;
							if(person !=null)
								membersToAdd.fnAddData([person.personName,"<a href=\"javascript:removePerson(" + 
						                                 person.personId + ")\">x</a>"]);
								
								var getOldIds=document.getElementById("patientId").value;
								
								$j("#patientId").val("");
								
								if(getOldIds=="")
									
									$j("#patientId").val(person.personId);
								
								else
									$j("#patientId").val(getOldIds + "," +  person.personId);
								
						}
						
						function selectProvider(userid,provider){
							var proId=null
							if(provider !=null){
								proId=provider.systemId;
							}
							$j("#systemId").val(proId);
						}
						
						function removePerson(person){
							
							var strOfPersonId="";
						        
							var getOldIds=document.getElementById("patientId").value;
							
							var noSeleted = getOldIds.split(",");
							
							for(var i=0; i<(noSeleted.length); i++){
						            if(noSeleted[i]==person){
						                var intPlace= i;
						                membersToAdd.fnDeleteRow(intPlace, null, false);
						            }else
						                if(strOfPersonId=="")
						                    strOfPersonId = noSeleted[i];
						                else
						                	strOfPersonId = strOfPersonId + "," + noSeleted[i];
							}
							
							
							$j("#patientId").val("");
							$j("#patientId").val(strOfPersonId);
							membersToAdd.fnDraw();
						
						}
						//function to allow addition of members to a household group 
						 function saveMembers(){
							var strMembersIds=dwr.util.getValue("patientId"); // handle the lists of members to be added 
							var strProviderId=dwr.util.getValue("systemId");  // captures the provider ids one who added the members 
							var divMembersListEmpty=document.getElementById("membersList"); // error div incase empty members 
							var divproviderList=document.getElementById("providerList");	// error div incase empty providers 
							var groupId=dwr.util.getValue("searchFieldHI");
							var dateAdded=dwr.util.getValue("startDate");
							
								if(strMembersIds == null || strMembersIds == ''){
									
									divMembersListEmpty.style.display ='inline';
									
									return false;
								}
								else if(strProviderId == null || strProviderId == ''){
											
									divproviderList.style.display ='inline';
									
									return false;
								}
								else{
									//alert(strMembersIds+"||"+groupIdss+"||"+strProviderId+"||"+dateAdded); 
									DWRHouseholdService.saveMembersToHseHold(strMembersIds,groupId,strProviderId,dateAdded,membersAddedSuccessfully);
									// call a sleep method that will take sometime after the success message is displayed 
									setTimeout("closeDialog()",3000);
									//DWRHouseholdService.getHouseholdMems(groupId,fillTable);
									pullByIdentifier();
									return true;
									
									
								}
							
						}
						
						function membersAddedSuccessfully(data){
							var divsavedwell=document.getElementById("savedwell");
							divsavedwell.style.display='inline';
							
							
							
						}
						function closeDialog(){
							$j("#addMembersToHousehold").dialog('close');
						}
						function closeVoidingDialog(){
							$j("#dialog").dialog('close');
						}
						
						function display(box) {
							// get reference to form object, and to array of same-named checkboxes 
							var temparr = new Array(), f = box.form, boxgroup = f[box.name];
							// loop through it 
							
							for (var i=0; i<boxgroup.length; i++) {
							// add the value of any checked box to next available slot in temparr
							if (boxgroup[i].checked) temparr[temparr.length] = boxgroup[i].value;
							// run the .join() method on the array (separator = ',') and output it to field 
							f.marktext.value = temparr.join(',');
							 if(!document.formMemebrs.marktext.value=='')
								document.formMemebrs.actOnVidedMembers.disabled=false;
							else
								document.formMemebrs.actOnVidedMembers.disabled=true;  
							}
							}
						function actOnCloseHousehold(){
							var householdToClose=dwr.util.getValue("searchFieldHI");
							if (confirm("Are you sure you want to close this Household ? "+householdToClose)) {
								$j(function() {
									$j( "#dialogCloseHousehold" ).dialog({ 
										modal: true, 
										show: 'slide',
										height: 'auto',
										hide: 'slide',
										width: 450,
										position: 'center'
										
										});
								});
							} 
							
						}
						function closeHousehold(){
							// code to close the entire household to follow here 
							var householdToClose=dwr.util.getValue("searchFieldHI");
							var householdToCloseProvId=dwr.util.getValue("userWhoCloseHousehold");
							var householdToCloseReason=dwr.util.getValue("householdCloseReason");
							var householdToCloseDate=dwr.util.getValue("householdCloseDate");
							
							// call dwrmethod to void the entire household 
							DWRHouseholdService.closeEntireHousehold(householdToClose,householdToCloseReason,householdToCloseProvId,householdToCloseDate,close_closeHousehold);
							
						}
						
						function close_closeHousehold(){
							$j("#dialogCloseHousehold").dialog('close');
						}
						
						function selectProviderWhoCloseHousehold(userid,provider){
							var proWhoCloseHouseholdId=null
							if(provider !=null){
								proWhoCloseHouseholdId=provider.systemId;
							}
							$j("#userWhoCloseHousehold").val(proWhoCloseHouseholdId);
							
							}

						
						</script>
						<div id="dialogCloseHousehold" title="Close Household" style="display:none">
							<table border="0">
								<tr>
									<td>Reason for close:</td>
									<td><input type="text" id="householdCloseReason" /></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>Provider Id:</td>
									<td><openmrs_tag:userField formFieldName="userProviders" roles="Trusted+External+Application,Lab+Technician,Community+Health+Worker+Nutritionist,Clinician,Nurse,Psychosocial+Support+Staff,Pharmacist,HCT+Nurse,Outreach+Worker,Community+Health+Extension+Worker,Clinical+Officer,Provider" callback="selectProviderWhoCloseHousehold" /></td>
									<td><input type="hidden" id="userWhoCloseHousehold" size="10" />
								</tr>
								<tr>
									<td>Close Date</td>
									<td><input type="text" id="householdCloseDate" onClick="showCalendar(this)" readonly="readonly" /></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td>&nbsp;</td>
									<td><input type="button" id="closeHouseholdButton" value="Close Household" onclick="closeHousehold()"/></td>
									<td>&nbsp;</td>
								</tr>
							</table>
						</div>
						
						<div id="addMembersToHousehold" title="Add Members" style="display:none">
							<div id="savedwell" style="display: none" class="successfullyDone">Members added successfully...</div>
								
									<table border="0" cellpadding="0" cellspacing="0">
										<tr> 
											<td>Enter name:</td>
											<td><openmrs_tag:patientField formFieldName="addPeopleTo" callback="selectedPatient"  /></td>
											<td><input type="hidden" name="patientId" id="patientId" size="10" /></td>
											<td><span id="membersList" style="display: none" class="error">Please make sure there are members to be added</span></td>
										</tr>
										<tr>
											<td>Provider:</td>
											<td><openmrs_tag:userField formFieldName="userProvider" roles="Trusted+External+Application,Lab+Technician,Community+Health+Worker+Nutritionist,Clinician,Nurse,Psychosocial+Support+Staff,Pharmacist,HCT+Nurse,Outreach+Worker,Community+Health+Extension+Worker,Clinical+Officer,Provider" callback="selectProvider" /></td>
											<td><input type="hidden" name="systemId" id="systemId" size="10" /></td>
											<td><span id="providerList" style="display: none" class="error">Provider required</span></td>
										</tr>
										<tr>		
											<td>Date:</td>	
											<td><input type="text" name="addDate" id="startDate" onClick="showCalendar(this)" /></td>
											<td>&nbsp;</td>
											<td>&nbsp;</td>
										</tr>
									</table>
									<table id="membersToAdd" border="0">
										<thead>
											<tr>
												<th>Full Names</th>
												<th>Action</th>
											</tr>
										</thead>
										<tbody id="membersToAddDetails">
										</tbody>	
										
									</table>
									<table border="0">	
										<tr>
											<td>&nbsp;</td>
											<td><input type="button" id="add" name="add" value="Save" onclick="saveMembers();" >
										</tr>		
									</table>
									
									
						</div>
						<div id="dialog" title="Void Reason"  style="display:none">
							<div id="voidewell" style="display: none" class="successfullyDone">Member voided successfully...</div>
									<table border="0">
										<thead></thead>
										<tbody>
											<tr>
												<td>
												Provide reason:
												</td>
												<td>
												<input type="text" name="voidreason" id="voidreason" size="50" />
												</td>
											</tr>
											<tr>
												
												<td>&nbsp;</td>
												<td><input type="button" id="saveVoided" value="Void" onclick="voidMembers(this)" /></td>
											</tr>
											<tr>
												<td colspan="2">
												<div class="error" style="display:none" id="errordiv">Please provide void reason</div>
												</td>
											</tr>
										</tbody>	
				
									</table>
											
								</div>
								
						
						<b class="boxHeader">Find household by identifier:</b>
						<div class="box">
							<table border="0">
								<tbody>
									<tr>
									<td>Household Identifier</td>
									<td><input type="text" id="searchFieldHI" onkeypress="pullByIdentifier()"/></td>
									<td><input type="button" id="findMembers" value="Find" /></td>
									</tr>
									<tr>
									<td colspan="3">
									</td>
									
									</tr>
								</tbody>
							</table>
						
						</div>
						<div id="householdDetails" style="display:none" class="box">
							<form method="post" name="formMemebrs">
									<table border="0" cellpadding="0" cellspacing="0">
										<thead>
											<tr>
												<th>No:</th>
												<th>Names</th>
												<th>Gender</th>
												<th>Birth Date</th>
												<th>Head/Index</th>
												<th>Start Date</th>
												<th>Void?</th>
											</tr>
										</thead>
										<tbody id="details">
	
										</tbody>
									</table>
									
									<table id="voidSelectedMembersInto" align="center" border="0">
										<tr>
											<td>&nbsp;</td><td>&nbsp;</td>
											<td>&nbsp;</td><td>&nbsp;</td>
											<td>&nbsp;</td><td>&nbsp;</td>
											<td>
												<input type="hidden" value="" name="marktext" id="marktext">
											</td>
											<td>
												<input type="button" value="Void Members" name="actOnVidedMembers" id="actOnVidedMembers" onclick="clickVoid()"  disabled="disabled" />
											</td>
											<td>
												<input type="button" value="Close Household" name="nameActOnCloseHousehold" id="idActOnCloseHousehold" onclick="actOnCloseHousehold()"  />
											</td>
											
										</tr>
										<tr>
											<td>&nbsp;</td><td><input type="hidden" id="householdToCloseList"/></td>
											<td colspan="2">
												<div id="actOnVidedMembersNull" style="display: none" class="error">At least one member can be voided</div>
											</td>
										</tr>
									</table>
									<a href="#" id="addMembers" onclick="addMembers()">Add Members</a>
								</form>	
						</div>
						
				</div>
				<div id="div2"><img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/></div>
				<div id="div3"><img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/></div>
				<div id="div4"><img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/></div>
				<div id="div5"><img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/></div>
				<div id="div6"><img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/></div>
				<div id="div7"><img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/></div>
				<div id="div8"><img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/></div>
				<div id="div9"><img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/></div>
			</div>
		</td>
	</tr>

</table>