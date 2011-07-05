<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/patientHousehold.form" />
	
	<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
	<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="t" %>
<script type="text/javascript">

var patientHouseholdTable;

//Executes when page completes loading.
$j(document).ready(function() {
 patientHouseholdTable = $j("#patientDetail").dataTable({
 	"bAutoWidth": false,
 	"bLengthChange": false,
 	"bJQueryUI": true,
 	"sPaginationType":"full_numbers"
 	}
 );
 
 $j('#householdMembers').dialog({
		autoOpen: false,
		modal: true,
		title: "<spring:message code='definitions.memberList' javaScriptEscape='true' />",
		width: '90%'
	});
 $j('#membersButton').click(function() {
		$j('#householdMembers').dialog('open');
	});
});
</script>

<b class="boxHeader"><spring:message code="household.definitions.header"/></b>
<div class="box">
	<div id=householdMembers >
						<table id="householdMemberDetail" cellpadding="5" width="100%">
					<thead>
					<tr>
						<th>Names</th>
				  		<th>Gender</th>
				  		<th>Birth Date</th>
				  		<th>Head/Index</th>
				  		<th>Start Date</th><!--
				  		${householdMembers.householdMembershipMember.names}
					--></tr>
					</thead><!--
					${householdMembers.householdMembershipMember.names}
					--><tbody>
					<t:forEach var="household" items="${householdMembers}" varStatus="state">
						<tr>
										
							<td>${householdMembers.householdMembershipMember.names}</td>
				   			<td align="center">${householdMembers.householdMembershipMember.gender}</td>
				  			<td align="center">${householdMembers.householdMembershipMember.birthdate}</td>
				  			<td align="center">${householdMembers.householdMembershipHeadship}</td>
				  			<td align="center">${householdMembers.startDate}</td>
						
						</tr>
					</t:forEach>
					</tbody>
				</table>
						
						</div>
	<table>
		<tr>
			<td valign="top">
				<table id="patientDetail" cellpadding="5" width="100%">
					<thead>
					<tr>
						<th>No</th>
						<th>Household</th>
						<th>Start Date</th>
						<th>End Date</th>
					</tr>
					</thead>
					<!--<t:out value="${model.householdTypessize}"/>
					-->
					<tbody>
					<t:forEach var="household" items="${model.household}" varStatus="state">
						<tr>
										
							<td><t:out value="${state.index}"/></td>
							<td><a href="module/household/householdSearch.form?grpids=${household.householdMembershipGroups.id}"> <t:out value="${household.householdMembershipGroups.householdDef.householdDefinitionsCode}"/>:<t:out value="${household.householdMembershipGroups.householdDef.id}"/>-<t:out value="${household.householdMembershipGroups.id}"/></a></td>
							<td> <t:out value="${household.startDate}"/></td>
							<td> <t:out value="${household.endDate}"/><input type="hidden" name="householdId" id="householdId" value="${household.householdMembershipGroups.id}"></td>
						
						</tr>
					</t:forEach>
					</tbody>
				</table>
			</td>
		</tr>
	</table>
		

</div>