<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/patientHousehold.form" />
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/householdSearch.js"></script>
	<openmrs:htmlInclude file="/openmrs/dwr/interface/DWRHouseholdService.js"/>
	<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
	<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="t" %>
<%-- <link href="${pageContext.request.contextPath}/moduleResources/household/css/tablestyles.css" type="text/css" rel="stylesheet" /> --%>
<script type="text/javascript">
var strHH = "";

function toggle_visibility(hItemid) {
    var em = document.getElementById("foo");
    if(em.style.display == 'block')
       em.style.display = 'none';
    else
       em.style.display = 'block';
 }
 
 function getMem(strHID,hItemid){
	 DWRHouseholdService.getHouseholdMembersPortlet(strHID,getMemDisp);
	 toggle_visibility(hItemid)
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
 }
</script>





<b class="boxHeader"><spring:message code="household.definitions.header"/></b>
	
	<table id="patientDetail" cellpadding="5" width="100%">
		<thead>
		<tr>
			<th>No</th>
			<th>Household</th>
			<th>Start Date</th>
			<th>End Date</th>
			<th>Members</th>
		</tr>
		</thead>
		<!--<t:out value="${model.householdTypessize}"/>
		-->
		<tbody>
			<t:forEach var="household" items="${model.household}" varStatus="state">
				<tr>
								
					<td><t:out value="${state.index + 1}"/></td>
					<td><a href="module/household/householdSearch.form?grpids=${household.householdMembershipGroups.id}"> <t:out value="${household.householdMembershipGroups.householdDef.householdDefinitionsCode}"/>:<t:out value="${household.householdMembershipGroups.householdDef.id}"/>-<t:out value="${household.householdMembershipGroups.id}"/></a></td>
					<td> <t:out value="${household.startDate}"/></td>
					<td> <t:out value="${household.endDate}"/><input type="hidden" name="householdId" id="householdId" value="${household.householdMembershipGroups.id}"></td>
					<td><a href="#" onclick="getMem(${household.householdMembershipGroups.id},foo);">View Members</a>
					</td>
				</tr>
			</t:forEach>
		</tbody>
	</table>


<div id="foo" style="display:none;">
</div>
