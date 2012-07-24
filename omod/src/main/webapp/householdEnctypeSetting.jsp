<%@ include file="/WEB-INF/template/include.jsp" %>
<openmrs:require privilege="Manage Household" otherwise="/login.htm" redirect="/module/household/setEncounterType.htm" />
<%@ include file="/WEB-INF/template/header.jsp" %>
<%@ include file="localHeader.jsp" %>

<!-- import jquery for openmrs 1.6.x -->
<openmrs:htmlInclude file="/scripts/jquery/jquery-1.3.2.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui-1.7.2.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/css/redmond/jquery-ui-1.7.2.custom.css" />
 
<script type="text/javascript">
        var $j = jQuery.noConflict();
</script>
<!-- end 1.6.x required prep -->
<!-- <c:set var="encounterType" value="household.enctype"/> -->
<openmrs:globalProperty key="household.enctype" var="encounterType" />
<br />
<b class="boxHeader">Household Settings</b>

<div class="box">
	<b>Select Encounter Type</b>
	<select name="encounters">
		<c:forEach var="encounterTypes" items="${encounterTypes}">
			<option id="${encounterTypes.householdEncounterTypeId}" value="${encounterTypes.householdEncounterTypeId}">${encounterTypes.householdEncounterTypeId} ${encounterTypes.name}</option>
		</c:forEach>
	</select>
	
	<openmrs:portlet url="globalProperties" 
		parameters="propertyPrefix=household|excludePrefix=household.started;household.mandatory;household.database_version"/>
</div>

<%@ include file="/WEB-INF/template/footer.jsp" %>