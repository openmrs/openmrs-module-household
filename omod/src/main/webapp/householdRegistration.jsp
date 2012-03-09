<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<openmrs:require privilege="View Household" otherwise="/login.htm"
	redirect="/module/household/householdRegistration.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<h3><spring:message code="household.title"/></h3>
<%@ include file="localHeader.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/householdSearch.js"></script>
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" /> 
<link href="${pageContext.request.contextPath}/moduleResources/household/css/tablestyles.css" type="text/css" rel="stylesheet" />


<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<%-- --%>
<openmrs:htmlInclude file="/dwr/interface/DWRPersonService.js"/>

<script type="text/javascript">
function inputValidator() {
	var errorDivElement = document.getElementById("errorDiv");
	var indexPerson = document.getElementById("hiddenIndex").value;
	var listToSave = document.getElementById("hiddenbox").value;
	if ((indexPerson == "" )||(listToSave == "" )) {
		errorDivElement.style.display = '';
		return false;
	}else{
		return true;
	}
}
</script>
<div class="error" id="errorDiv" style="display: none"><img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /><spring:message code="household.registration.index"/></div>

<b class="boxHeader"><spring:message code="household.registration.header"/></b>
<div class="box">
	<div>
		<c:choose>
			<c:when test="${ErrorHH == true}">
				<span class="error"><spring:message code="household.registration.errorhh"/></span>
			</c:when>
			<c:otherwise>
			</c:otherwise>
		</c:choose>
	</div>
	<div>
		<c:choose>
			<c:when test="${NumberOfHD == 0}">
				<span class="error"><spring:message code="household.registration.errorhd"/></span>
				<a href="householdDefinitions.form">
					<spring:message code="household.registration.redirect"/>
				</a>
			</c:when>
			<c:otherwise>
				<a href="../../findPatient.htm">Register Individuals</a>&nbsp;|&nbsp;
				<a href="householdSearch.form">Search for a Household</a>&nbsp;|&nbsp;
				<a href="householdResume.form">Resume Household</a>&nbsp;|&nbsp;
				<a href="householdIndexPerson.form">Change Index/Head</a>
				<form method="post" name="householdRegistration" onsubmit="return inputValidator()">
					
					<table border="0">
						<tr>
							<td>
								<table border="0">
									<tbody>
									<tr>
										<td colspan="2">
											<div id="memberLookup">
												<b>Find Household Member:</b>
												<openmrs_tag:patientField formFieldName="new_member" callback="selectedPerson" />
											</div>
									        <input type="text" id="hiddenbox" name="hiddenbox"/>
									        <c:choose>
												<c:when test ="${not empty HouseholdID}">
													<input type="text" id="hiddenIndex" name="hiddenIndex" value="${HouseholdID}"/>
												</c:when>
												<c:otherwise>
													<input type="text" id="hiddenIndex" name="hiddenIndex"/>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>	
									<c:choose>
										<c:when test="${empty HouseholdID}">
											<tr>
												<td>Definition:</td>
												<td>
													<select name="defin">
														<c:forEach var="household" items="${householdsTypes}">
															<option id="${household.householdDefinitionsCode}" value="${household.id}">${household.householdDefinitionsCode}</option>
														</c:forEach>
													</select>
													
												</td>
											</tr>
											<tr>
												<td>Start Date</td>	
												<td><input type="text" name="startDate" id="startDate" onClick="showCalendar(this)" /></td>
											</tr>
										</c:when>
										<c:otherwise>
										</c:otherwise>
									</c:choose>
									
									<tr>
										<td colspan="2">
											<table id="householdMembers">
												<thead>
													<tr> 
														<th>Full Name</th>
														<th>Is Head/Index?</th>
														<th>Action</th>
													</tr>
												</thead>
												<tbody></tbody>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="2"> 
											<input id="saveButton" type="submit" value="<spring:message code='household.registration.saveButton'/>" />
										</td>
									</tr>
									</tbody>
								</table>
								
							</td>
							<c:choose>
								<c:when test="${not empty householdSaved}">
									<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td valign="top">
										<br />
										<h4><spring:message code="household.registration.registered"/></h4> &nbsp;&nbsp;<h3>${HouseholdID}</h3><br>
										<table id="mytable">
											<tr> 
												<th class="tbClass">No</th>
												<th class="tbClass">Full Names</th>
												<th class="tbClass">Gender</th>
											</tr>
											<c:forEach var="householdMems" items="${householdSaved}" varStatus="ind">
													<tr>
														<td class="tdClass">${ind.index + 1}</td>
														<td class="tdClass">${householdMems.householdMembershipMember.names}</td>
														<td class="tdClass">${householdMems.householdMembershipMember.gender}</td>
													</tr>
											</c:forEach>
										</table>
									</td>
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
						</tr>
					</table>
				</form>
			</c:otherwise>
		</c:choose>
	</div>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>