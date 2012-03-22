<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Household" otherwise="/login.htm" redirect="/module/household/householdSettingsPanel" />

<%@ include file="/WEB-INF/template/header.jsp" %>

<openmrs:htmlInclude file="/dwr/util.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/style-table.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/modal.popup.js"></script>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/vert_tab.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/moduleResources/household/css/css-table.css" type="text/css" rel="stylesheet" />
<script language="JavaScript">
$(document).ready(function() {

    $(".tabs .tab[id^=tab_menu]").click(function() {
        var curMenu=$(this);
        $(".tabs .tab[id^=tab_menu]").removeClass("selected");
        curMenu.addClass("selected");

        var index=curMenu.attr("id").split("tab_menu_")[1];
        $(".curvedContainer .tabcontent").css("display","none");
        $(".curvedContainer #tab_content_"+index).css("display","block");
    });
});
function checkPrefix(){
	var par = document.getElementById("parent").value;
	if(par == ""){
		$("#identifierPrefix").val("");
		$("#pref").css("display","none");
	}else
		$("#pref").css("display","inline");
		
}
function passHDObject(){
	var par = document.getElementById("hdAddEdit").value;
	var par1 = document.getElementById("hdid").value;
	var par2 = document.getElementById("programCode").value;
	var par3 = document.getElementById("codeInFull").value;
	var par4 = document.getElementById("parent").value;
	var par5 = document.getElementById("identifierPrefix").value;
	var par6 = document.getElementById("programDescription").value;
	
	if((par == "Add") || (par == ""))
		var parArrHD = ["1", par1, par2, par3, par4, par5, par6];
	else if(par == "Edit")
		var parArrHD = ["2", par1, par2, par3, par4, par5, par6];
	else
		var parArrHD = ["3", par1, par2, par3, par4, par5, par6];
	DWRHouseholdService.addEditHouseholdDefinition( parArrHD, returnHD);
}
function returnHD(data){
	//alert("Successfully saved \n" + data);
	var checkDelete = document.getElementById("hdAddEdit").value;
	if (checkDelete != "Delete"){
		$j('#addHouseholdProgram').slideToggle('fast');
		event.preventDefault();
	}
		$j.get("ports/householdSettingsDef.form",
				function(dat){
					$j('#idDefinition').html(dat);
				}
			);
	document.getElementById("hdAddEdit").value = "Add";
	document.getElementById("hdid").value = "";
	document.getElementById("programCode").value = "";
	document.getElementById("codeInFull").value = "";
	document.getElementById("parent").value = "";
	document.getElementById("identifierPrefix").value = "";
	document.getElementById("programDescription").value = "";
}

function passEncTypeObject(){
	var par = document.getElementById("hdAddEditEnc").value;
	var par1 = document.getElementById("hdidEnc").value;
	var par2 = document.getElementById("idname").value;
	var par3 = document.getElementById("idencTypeDescription").value;
	var par4 = document.getElementById("hdidRetire").value;
	
	if((par == "Add") || (par == ""))
		var parArrHD = ["1", par1, par2, par3];
	else if(par == "Edit")
		var parArrHD = ["2", par1, par2, par3];
	else
		var parArrHD = ["3", par1, par2, par3, par4];
	DWRHouseholdService.addEditHouseholdEncounterType( parArrHD, returnEncType);
}
function returnEncType(data){
	//alert("Successfully saved \n" + data);
	var checkDelete = document.getElementById("hdAddEditEnc").value;
	if (checkDelete != "Retire"){
		$j('#addHouseholdEncounterType').slideToggle('fast');
		event.preventDefault();
	}
		$j.get("ports/householdSettingsEncType.form?includedRetired=" + false,
				function(dat){
					$j('#idEncType').html(dat);
				}
			);
	document.getElementById("hdAddEditEnc").value = "Add";
	document.getElementById("hdidEnc").value = "";
	document.getElementById("idname").value = "";
	document.getElementById("idencTypeDescription").value = "";
	document.getElementById("hdidRetire").value = "";
}
</script>

<h3><spring:message code="household.title"/></h3>
<%@ include file="localHeader.jsp" %>
<br />
<b class="boxHeader">Household Settings</b>
<div class="box">
	<div class="tabscontainer">
	     <div class="tabs">
	         <div class="tab selected first" id="tab_menu_1">
	             <div class="link">Program/Groups</div>
	             <div class="arrow"></div>
	         </div>
	         <div class="tab" id="tab_menu_2">
	             <div class="link">Encounter Types</div>
	             <div class="arrow"></div>
	         </div>
	          <div class="tab" id="tab_menu_3">
	             <div class="link">General Properties</div>
	             <div class="arrow"></div>
	         </div>
	         <div class="tab last" id="tab_menu_4">
	             <div class="link">Custom Privileges</div>
	             <div class="arrow"></div>
	         </div>
	    </div>
		<div class="curvedContainer">
			<div class="tabcontent" id="tab_content_1" style="display:block">
				<script type="text/javascript">
					$j(document).ready(function() {
						$j('.toggleAddHouseholdProgram').click(function(event) {
							$j('#addHouseholdProgram').slideToggle('fast');
							event.preventDefault();
						});
					});
				</script>
				<a class="toggleAddHouseholdProgram" href="#">Add Household Program/Definition</a><br />
				<div id="addHouseholdProgram" style="border: 1px black solid; background-color: #e0e0e0; display: none">
					<form method="post"> <!-- action="householdAddProgram.form" -->
						<table>
							<tr>
								<th>Program Code</th>
								<td>
									<input type="hidden" name="hdid" id="hdid"/>
									<input type="hidden" name="hdAddEdit" id="hdAddEdit"/>
									<input type="text" name="programCode" id="programCode"/>
									<span class="required">*</span>
								</td>
							</tr>
							<tr>
								<th>Code in Full</th>
								<td>
									<input type="text" name="codeInFull" id="codeInFull"/>
									<span class="required">*</span>
								</td>
							</tr>
							
							<c:if test="${not empty householdsTypes}">
								<tr>
									<th>Parent Program</th>
									<td>
										<select name="parent" id="parent" onchange="javascript:checkPrefix()">
											<option id="m" value="" selected="selected"></option>
											<c:forEach var="hh" items="${definitionParents}" varStatus="ind">
												<option id="${ind.index + 1 }" value="${hh.id}">${hh.householdDefinitionsCode}</option>
											</c:forEach>
										</select>
										
									</td>
								</tr>
							</c:if>
							<div id="pref" style="display: none;">
								<tr>
									<th>Identifier Prefix</th>
									<td><input type="text" id="identifierPrefix" name="identifierPrefix"/></td>
								</tr>
							</div>
							<tr>
								<th>Description</th>
								<td><textarea name="programDescription" id="programDescription" rows="3" cols="72"></textarea></td>
							</tr>
							<tr>
								<th></th>
								<td>
									<input type="button" onclick="javascript:passHDObject()" value="<spring:message code="general.save"/>" />
									<input type="button" value="<spring:message code="general.cancel"/>" class="toggleAddHouseholdProgram" />
								</td>
							</tr>
						</table>
					</form>
				</div>
				<br />
				
				<div id="idDefinition">
					<table id="houseDefs"  class="lineTable">
						<thead>    
					    	<tr>
					            <th scope="col" rowspan="2">&nbsp;</th>
					            <th scope="col" colspan="6">Registered Household Programs</th>
					        </tr>
					        <tr>
					            <th scope="col">Program Code</th>
					            <th scope="col">Program Code In Full</th>
					            <th scope="col">Description</th>
					            <th scope="col"><spring:message code="general.createdBy"/></th>
					            <th scope="col">Action</th>
					        </tr>        
					    </thead>
					    <tbody id="bdytbl">
					    	<c:forEach var="household" items="${householdsTypes}" varStatus="ind">
								<form method="POST" name="${household.id}">
									<tr valign="top">
										<th>${ind.index + 1}</th>
										<td class="highlight">
											<c:choose>
												<c:when test="${not empty household.parent}">
													${household.householdDefinitionsCode} [Parent: ${household.parent.householdDefinitionsCode}]
												</c:when>
												<c:otherwise>
													${household.householdDefinitionsCode}
												</c:otherwise>
											</c:choose>
										
										</td>
										<td class="highlight">${household.householdDefinitionsCodeinfull}</td>
										<td class="highlight">${household.householdDefinitionsDescription}</td>
										<td class="highlight">
											<openmrs:format user="${household.creator}"/><br />
											<openmrs:formatDate date="${household.dateCreated}"/>
										</td>
										<td class="highlight">
											<input type="hidden" name="houseid" id="${household.id}" value="${household.id}" />
											<a href="#" onclick="javascript:onClickEditProgram('${household.id}','${household.householdDefinitionsCode}','${household.householdDefinitionsCodeinfull}','${household.householdDefinitionsDescription}','${household.parent.id}','${household.identifierPrefix}')">
												<img src="${pageContext.request.contextPath}/moduleResources/household/images/edit.gif"/></a>
											<a href="#" onclick="javascript:onClickDeleteProgram('${household.id}','${household.householdDefinitionsCode}')">
												<img src="${pageContext.request.contextPath}/moduleResources/household/images/trash.gif"/></a>
										   <%--  <input type="button" id="editProgram${household.id}" onclick="clickEditProgram(${household.id})" value="Edit" /> --%>
											<!-- <input type="submit" value="Delete" /> -->
										</td>
									</tr>
								</form>
							</c:forEach>
					    </tbody>
					</table>
				</div>
				<div id="openEdit"></div>
				<script type="text/javascript">
				
					function onClickEditProgram(id,householdDefinitionsCode,householdDefinitionsCodeinfull,householdDefinitionsDescription,parentId,pref ){
						document.getElementById("hdAddEdit").value = "Edit";
						document.getElementById("hdid").value = id;
						document.getElementById("programCode").value = householdDefinitionsCode;
						document.getElementById("codeInFull").value = householdDefinitionsCodeinfull;
						document.getElementById("parent").value = parentId;
						document.getElementById("identifierPrefix").value = pref;
						document.getElementById("programDescription").value = householdDefinitionsDescription;
						$j('#addHouseholdProgram').slideToggle('fast');
						event.preventDefault();
					}
					function onClickDeleteProgram(id,def){
						var r=confirm("Do you really want to delete the " + def + " definition?");
						if (r==true){
							document.getElementById("hdAddEdit").value = "Delete";
							document.getElementById("hdid").value = id;
							passHDObject();
						}else{
							document.getElementById("hdAddEdit").value = "";
						}
					}
				</script>
				
			</div>
			<div class="tabcontent" id="tab_content_2">
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				<script type="text/javascript">
					$j(document).ready(function() {
						$j('.toggleAddHouseholdEncounterType').click(function(event) {
							$j('#addHouseholdEncounterType').slideToggle('fast');
							event.preventDefault();
						});
					});
				</script>
				<span>
					<a class="toggleAddHouseholdEncounterType" href="#">Add Household Encounter Type</a><br />
					<span style="float: right"> 
						<label for="includeRetired">
							<input type="checkbox" id="includeRetired" value="0" onchange="javascript:pullRetired()"/>
							Included Retired
						</label>
					</span>
				</span>
				<div id="addHouseholdEncounterType" style="border: 1px black solid; background-color: #e0e0e0; display: none">
					<form method="post">  <!-- action="householdAddEncounterType.form" -->
						<table>
							<tr>
								<th>Name</th>
								<td>
									<input type="hidden" name="hdidEnc" id="hdidEnc"/>
									<input type="hidden" name="hdAddEditEnc" id="hdAddEditEnc"/>
									<input type="hidden" name="hdidRetire" id="hdidRetire"/>
									<input type="text" name="name" id="idname"/>
									<span class="required">*</span>
								</td>
							</tr>
							<tr>
								<th>Description</th>
								<td><textarea name="encTypeDescription" rows="3" cols="72" id="idencTypeDescription"></textarea></td>
							</tr>
							<tr>
								<th></th>
								<td>
									<input type="button" onclick="javascript:passEncTypeObject()" value="<spring:message code="general.save"/>" />
									<input type="button" value="<spring:message code="general.cancel"/>" class="toggleAddHouseholdEncounterType" />
								</td>
							</tr>
						</table>
					</form>
				</div>
				<br />
				
				<div id="idEncType">
					<table id="houseEncType"  class="lineTable">
						<thead>    
					    	<tr>
					            <th scope="col" rowspan="2">&nbsp;</th>
					            <th scope="col" colspan="6">Household Encounter Types</th>
					        </tr>
					        <tr>
					            <th scope="col">Name</th>
					            <th scope="col">Description</th>
					            <th scope="col"><spring:message code="general.createdBy"/></th>
					            <th scope="col">Action</th>
					        </tr>        
					    </thead>
					    <tbody>
					    	<c:forEach var="householdET" items="${householdEncTypes}" varStatus="ind">
								<form method="POST" name="${householdET.id}">
									<tr valign="top">
										<th>${ind.index + 1}</th>
										<td class="highlight">
											${householdET.name} 
										</td>
										<td class="highlight">${householdET.description}</td>
										<td class="highlight">
											<openmrs:format user="${householdET.creator}"/><br />
											<openmrs:formatDate date="${householdET.dateCreated}"/>
										</td>
										<td class="highlight">
											<a href="#" onclick="javascript:onClickEditEncounterType('${householdET.id}','${householdET.name}','${householdET.description}')">
												<img src="${pageContext.request.contextPath}/moduleResources/household/images/edit.gif"/></a>
											<a href="#" onclick="javascript:onClickDeleteEncounterType('${householdET.id}','${householdET.name}')">
												<img src="${pageContext.request.contextPath}/moduleResources/household/images/trash.gif"/></a>
										</td>
									</tr>
								</form>
							</c:forEach>
					    </tbody>
					</table>
				</div>
				<div id="openEditEncounterType"></div>
				<script type="text/javascript">
					function onClickEditEncounterType(id,nam,description){
						document.getElementById("hdAddEditEnc").value = "Edit";
						document.getElementById("hdidEnc").value = id;
						document.getElementById("idname").value = nam;
						document.getElementById("idencTypeDescription").value = description;
						$j('#addHouseholdEncounterType').slideToggle('fast');
						event.preventDefault();
					}
					function onClickDeleteEncounterType(id,nam){
						var reason=prompt("Please enter the reson for retiring this Encounter Type.","");
						if (reason!=null && reason!=""){
							var r=confirm("Do you really want to Retire the " + nam + " Encounter Type?");
							if (r==true){
								document.getElementById("hdAddEditEnc").value = "Retire";
								document.getElementById("hdidEnc").value = id;
								document.getElementById("hdidRetire").value = reason;
								passEncTypeObject();
							}else{
								document.getElementById("hdAddEditEnc").value = "";
							}
						}else{
							alert("Sorry, you never entered the reason for retiring this record. Not retired.")
						}
					}
					function pullRetired(){
						var val = document.getElementById("includeRetired");
						if(val.checked){
							$j.get("ports/householdSettingsEncType.form?includedRetired=" + true,
									function(dat){
										$j('#idEncType').html(dat);
									}
								);
						}else{
							$j.get("ports/householdSettingsEncType.form?includedRetired=" + false,
									function(dat){
										$j('#idEncType').html(dat);
									}
								);
						}
					}
				</script>
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
				
			</div>
			<div class="tabcontent" id="tab_content_3">
				<strong>General Properties</strong>
				<br><br>
				<openmrs:portlet url="globalProperties"
				parameters="propertyPrefix=household|excludePrefix=household.started;household.mandatory;household.database_version"/>
			</div>
			<div class="tabcontent" id="tab_content_4">
				<strong>jQuery</strong> is a cross-browser JavaScript library designed to simplify the client-side scripting of HTML.[1] It was released in January 2006 at BarCamp NYC by John Resig. Used by over 43% of the 10,000 most visited websites, jQuery is the most popular JavaScript library in use today.[2][3]
				<br><br>
				jQuery is free, open source software, dual-licensed under the MIT License and the GNU General Public License, Version 2.[4] jQuery's syntax is designed to make it easier to navigate a document, select DOM elements, create animations, handle events, and develop Ajax applications. jQuery also provides capabilities for developers to create plugins on top of the JavaScript library. Using these facilities, developers are able to create abstractions for low-level interaction and animation, advanced effects and high-level, theme-able widgets. This contributes to the creation of powerful and dynamic web pages.
				<br><br>
				Microsoft and Nokia have announced plans to bundle jQuery on their platforms,[5] Microsoft adopting it initially within Visual Studio[6] for use within Microsoft's ASP.NET AJAX framework and ASP.NET MVC Framework while Nokia has integrated it into their Web Run-Time widget development platform.[7] jQuery has also been used in MediaWiki since version 1.16.[8]
			</div>
		</div>
	</div>
</div>
