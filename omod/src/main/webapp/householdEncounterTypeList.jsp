<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/householdEncounterTypeList.list" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<h3><spring:message code="household.title"/></h3>

<%@ include file="localHeader.jsp"%>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/tablestyles.css" type="text/css" rel="stylesheet" />

<b class="boxHeader"><spring:message code="household.encounterType.header"/></b>
<div class="box">

	<table>
		<tr>
			<td>
				<form method="POST" name="householdEncType">
				<table>
					<c:choose>
						<c:when test ="${not empty householdEnc}">
							<input type="hidden" name="houseid" value="${householdEnc.householdEncounterTypeId}" />
							<tr>
								<td><spring:message code="household.encounterType.name"/></td>
								<td><input type="text" name="householdEncTypeName" value="${householdEnc.name}" /></td>
							</tr>
							<tr>
								<td><spring:message code="household.encounterType.description"/></td>
								<td>
									<textarea name="householdEncTypeDescription">${householdEnc.description}</textarea>
								</td>
							</tr><tr>
								<td></td><td><input type="submit" value="Save Changes" /></td>
							</tr>
							
						</c:when>
						<c:otherwise>
							<tr>
								<td><spring:message code="household.encounterType.name"/></td>
								<td><input type="text" name="householdEncTypeName" value="${householdEnc.name}" /></td>
							</tr>
							<tr>
								<td><spring:message code="household.encounterType.description"/></td>
								<td>
									<textarea name="householdEncTypeDescription">${householdEnc.description}</textarea>
								</td>
							</tr>
							<tr>
								<td></td><td><input type="submit" value="Add New" /></td>
							</tr>
						</c:otherwise>
					</c:choose>
					
					
				</table>
				</form>
			</td>
			<td valign="top">
				<table cellpadding="5" width="100%">
					<tr>
						<th class="tbClass">No</th>
						<th class="tbClass">Name</th>
						<th class="tbClass">Description</th>
						<th class="tbClass">Action</th>
					</tr>
					<c:forEach var="householdEnc" items="${householdEncTypes}" varStatus="encIndex" >
						<form method="POST" name="${householdEnc.householdEncounterTypeId}">
						<tr>
							<td class="tdClass">${encIndex.index + 1}</td>
							<td class="tdClass">${householdEnc.name}</td>
							<td class="tdClass">${householdEnc.description}</td>
							<td class="tdClass">
								<input type="hidden" name="houseid" id="${householdEnc.householdEncounterTypeId}" value="${householdEnc.householdEncounterTypeId}" />
								<input type="submit" value="Edit" />
							</td>
						</tr>
						</form>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
		

</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>