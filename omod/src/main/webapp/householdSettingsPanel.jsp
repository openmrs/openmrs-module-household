<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Household" otherwise="/login.htm" redirect="/module/household/householdSettingsPanel" />

<%@ include file="/WEB-INF/template/header.jsp" %>

<%@ include file="localHeader.jsp" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/style-table.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/modal.popup.js"></script>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/vert_tab.css" type="text/css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/moduleResources/household/css/css-table.css" type="text/css" rel="stylesheet" />
<script language="JavaScript">
$(document).ready(function() {

    $(".tabs .tab[id^=tab_menu]").hover(function() {
        var curMenu=$(this);
        $(".tabs .tab[id^=tab_menu]").removeClass("selected");
        curMenu.addClass("selected");

        var index=curMenu.attr("id").split("tab_menu_")[1];
        $(".curvedContainer .tabcontent").css("display","none");
        $(".curvedContainer #tab_content_"+index).css("display","block");
    });
});
</script>

<h3><spring:message code="household.title"/></h3>
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
					<form method="post" action="householdAddProgram.form">
						<table>
							<tr>
								<th>Program Code</th>
								<td>
									<input type="text" name="programCode"/>
									<span class="required">*</span>
								</td>
							</tr>
							<tr>
								<th>Code in Full</th>
								<td>
									<input type="text" name="codeInFull"/>
									<span class="required">*</span>
								</td>
							</tr>
							
							<c:if test="${not empty householdsTypes}">
								<tr>
									<th>Parent Program</th>
									<td>
										<select name="parent">
											<option id="m" value="" selected="selected"></option>
											<c:forEach var="hh" items="${householdsTypes}" varStatus="ind">
												<option id="${ind.index + 1 }" value="${hh.id}">${hh.householdDefinitionsCode}</option>
											</c:forEach>
										</select>
										
									</td>
								</tr>
							</c:if>
							
							
							<tr>
								<th>Description</th>
								<td><textarea name="programDescription" rows="3" cols="72"></textarea></td>
							</tr>
							<tr>
								<th></th>
								<td>
									<input type="submit" value="<spring:message code="general.save"/>" />
									<input type="button" value="<spring:message code="general.cancel"/>" class="toggleAddHouseholdProgram" />
								</td>
							</tr>
						</table>
					</form>
				</div>
				<br />
				
				
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
				    <tbody>
				    	<c:forEach var="household" items="${householdsTypes}" varStatus="ind">
							<form method="POST" name="${household.id}">
								<tr>
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
									    <input type="button" id="editProgram${household.id}" onclick="clickEditProgram(${household.id})" value="Edit" />
										<input type="submit" value="Delete" />
									</td>
								</tr>
							</form>
						</c:forEach>
				    </tbody>
				</table>
				<div id="openEdit"></div>
				<script type="text/javascript">
					function clickEditProgram(id){
						var current_id = id;
						$j('#openEdit').load('householdEditProgram.form?id=' + current_id).dialog({position:'top', title:'Edit Household Program/Group', width:600, modal:true});
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
				<a class="toggleAddHouseholdEncounterType" href="#">Add Household Encounter Type</a><br />
				<div id="addHouseholdEncounterType" style="border: 1px black solid; background-color: #e0e0e0; display: none">
					<form method="post" action="householdAddEncounterType.form">
						<table>
							<tr>
								<th>Name</th>
								<td>
									<input type="text" name="name"/>
									<span class="required">*</span>
								</td>
							</tr>
							<tr>
								<th>Description</th>
								<td><textarea name="programDescription" rows="3" cols="72"></textarea></td>
							</tr>
							<tr>
								<th></th>
								<td>
									<input type="submit" value="<spring:message code="general.save"/>" />
									<input type="button" value="<spring:message code="general.cancel"/>" class="toggleAddHouseholdEncounterType" />
								</td>
							</tr>
						</table>
					</form>
				</div>
				<br />
				
				
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
								<tr>
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
										<input type="hidden" name="houseid" id="${householdET.id}" value="${householdET.id}" />
									    <input type="button" id="editET${householdET.id}" onclick="clickEditEncounterType(${householdET.id})" value="Edit" />
										<input type="submit" value="Delete" />
									</td>
								</tr>
							</form>
						</c:forEach>
				    </tbody>
				</table>
				<div id="openEditEncounterType"></div>
				<script type="text/javascript">
					function clickEditEncounterType(id){
						var current_id = id;
						$j('#openEditEncounterType').load('householdEditEncounterType.form?id=' + current_id).dialog({position:'top', title:'Edit Household Encounter Type', width:600, modal:true});
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
