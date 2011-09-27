<%@ include file="/WEB-INF/template/include.jsp" %>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/patientHousehold.form" />
	
<openmrs:htmlInclude file="/dwr/engine.js" />
<openmrs:htmlInclude file="/dwr/util.js" />
<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/householdSearch.js"></script>
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="t" %>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/tablestyles.css" type="text/css" rel="stylesheet" />

<script type="text/javascript">
var strHH = "";

/* function hideshow(which){
	if (!document.getElementById)
		return
		if (which.style.display=="block")
			which.style.display="none"
		else
			which.style.display="block"
} */

function toggle_visibility() {
    var em = document.getElementById("foo");
    var info = document.getElementById("infoMem");
    if(em.style.display == 'block'){
		em.style.display = 'none';
    	info.style.display = 'none';
    }else{
		em.style.display = 'block';
		info.style.display = 'block';
    }
 }
 b
 function getMem(strHID){
	 DWRHouseholdService.getHouseholdMembersPortlet(strHID,getMemDisp);
	 //toggle_visibility(hItemid)
 }
 function getMemDisp(strVal){
	 strHH = "<table cellpadding='5'><tr><th>Names</th><th>Gender</th><th>Birth Date</th></tr>";
	 var strHou = new Array();
	 strHou = strVal.split(",");
	 var i;

	for(i = 0; i < strHou.length; i++) {
		strHH = strHH + "<tr><td>" + strHou[i] + "</td><td>" + strHou[i+1] + "</td><td>" + strHou[i+2] + "</td></tr>";
		i+=2;
	}
	strHH = strHH + "</table>";
	document.getElementById("foo").innerHTML=strHH;
	toggle_visibility();
 }
 
 function getEncObs(strUuid){
	 DWRHouseholdService.getHouseholdEncountersObs(strUuid,getEncObsDisp);
 }
 function getEncObsDisp(strVal){
	 strHH = "<table cellpadding='5'><tr><th>Obs Name</th><th>Value</th></tr>";
	 var strHou = new Array();
	 strHou = strVal.split(",");
	 var i;

	for(i = 0; i < strHou.length; i++) {
		strHH = strHH + "<tr><td>" + strHou[i] + "</td><td>" + strHou[i+1] + "</td></tr>";
		i+=1;
	}
	strHH = strHH + "</table>";
 }
 
</script>

<b class="boxHeader"><spring:message code="household.definitions.header"/></b>
<div class="box">
	<table id="patientDetail" cellpadding="5" width="80%">
		<thead>
		<tr>
			<th class="tbClass">No</th>
			<th class="tbClass">Household</th>
			<th class="tbClass">Start Date</th>
			<th class="tbClass">End Date</th>
			<th class="tbClass">Encounters</th>
		</tr>
		</thead>
		<!--<t:out value="${model.householdTypessize}"/>
		-->
		<tbody>
			<t:forEach var="householdMem" items="${model.householdMems}" varStatus="state">
				<tr>
					<td class="tdClass"><t:out value="${state.index + 1}"/></td>
					<td class="tdClass"><a href="#" onclick="javascript:getMem(${householdMem.householdMembershipGroups.id});">View Members</a></td>
					<td class="tdClass"> <t:out value="${householdMem.startDate}"/></td>
					<td class="tdClass"> <t:out value="${householdMem.endDate}"/><input type="hidden" name="householdId" id="householdId" value="${householdMem.householdMembershipGroups.id}"></td>
					<td class="tdClass">
						<div class="encountersForHousehold">
							<t:forEach var="enc" items="${model.encounters[householdMem.householdMembershipGroups.id]}" varStatus="states">
								<a href="#">${states.index + 1} . View - ${enc.householdEncounterId}</a> <br /> <%-- onclick="javascript:getEncObs('${enc.uuid}');" --%>
							</t:forEach>
						</div>
					</td>
				</tr>
			</t:forEach>
		</tbody>
	</table>
	
	<div id="infoMem" style="display:none;">

		<h2>Members</h2>
	
		<div id="xsnazzy">
			<b class="xtop"><b class="xb1"></b><b class="xb2"></b><b class="xb3"></b><b class="xb4"></b></b>
			<div class="xboxcontent">
				<div id="foo" style="display:none;" class="tdClass2">
				</div>
			</div>
			<b class="xbottom"><b class="xb4"></b><b class="xb3"></b><b class="xb2"></b><b class="xb1"></b></b>
		</div>
		<br class="clear" /><br/>
	</div> <!-- end of info -->
	<div id="infoObs" style="display:none;">
	</div>
</div>