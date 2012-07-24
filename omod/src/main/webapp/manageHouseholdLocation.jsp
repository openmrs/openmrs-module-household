<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Manage Household Locations" otherwise="/login.htm"
	redirect="/module/household/manageHouseholdLocation.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>

<h2><spring:message code="household.title" /></h2>

<%@ include file="localHeader.jsp"%>
<!-- SPECIALIZED STYLES FOR THIS PAGE -->
<style type="text/css">
	td.tableCell {padding-left:4px; padding-right:4px; padding-top:2px; padding-bottom:2px; vertical-align:top}
</style>

<br/>

<!--  DISPLAY ANY ERROR MESSAGES -->
<c:if test="${fn:length(messages) > 0}">
	<c:forEach var="message" items="${messages}">
		<span class="error"><spring:message code="${message}"/></span><br/><br/>
	</c:forEach>
	<br/>
</c:if>

<!--  display the Household Location types -->
<div>
<b class="boxHeader"><spring:message code="household.householdLocation.level" /></b>
<table cellpadding="2" cellspacing="0" class="box">
	<tr>
		<th><spring:message code="household.householdLocation.level" /></th>
		<th><spring:message code="general.name" /></th>
		<th><spring:message code="household.householdLocation.exampleEntry" /></th>
		<th><spring:message code="household.householdLocation.mappedAddressField" /></th>
		<th><spring:message code="household.householdLocation.required" /></th>
		<th>&nbsp;</th>
		<th width="20%">&nbsp;</th>
	</tr>
	<c:forEach items="${levels}" var="level" varStatus="i">
		<tr>
			<td class="tableCell">${i.count}</td>
			<td class="tableCell">${level.name}</td>
			<td class="tableCell">${sampleEntries[i.count-1][0]} (${sampleEntries[i.count-1][1]} <spring:message code="household.householdLocation.totalEntries"/>)</td>
			<td class="tableCell">
				<c:choose>
					<c:when test="${! empty level.householdLocationField}">
						<spring:message code="${level.householdLocationField.name}"/>
					</c:when>
					<c:otherwise>
						(<spring:message code="general.none"/>)
					</c:otherwise>
				</c:choose>
			</td>
			<td class="tableCell">
				<c:if test="${level.required == true}">
					<spring:message code="general.yes"/>
				</c:if>
				<c:if test="${level.required == false}">
					<spring:message code="general.no"/>
				</c:if>
			</td>
			<td class="tableCell">
				<a href="${pageContext.request.contextPath}/module/household/editHouseholdLocationLevel.form?levelId=${level.id}">
				   <spring:message code="general.edit" />
				</a>
				<!-- only show delete option for last element, and only if it doesn't have any sample entries -->
				<c:if test="${i.count == fn:length(levels) && sampleEntries[i.count-1][1] == 0}">
					&nbsp;|&nbsp;
					<a href="${pageContext.request.contextPath}/module/household/deleteHouseholdLocationLevel.form?levelId=${level.id}"
					   onclick="return confirm('<spring:message code="household.householdLocation.confirmDeleteLevel"/>');">
					   <spring:message code="general.delete" />
					</a>
				</c:if>
			</td>
			<td class="tableCell">&nbsp;</td>
		</tr>
	</c:forEach>
	<tr>
		<td class="tableCell" colspan="4">
			<a href="${pageContext.request.contextPath}/module/household/editHouseholdLocationLevel.form">
				<spring:message code="household.householdLocation.addLevel" />
			</a>
		</td>
	</tr>
</table>
</div>

<br/>
<br/>

<div><b class="boxHeader"><spring:message code="household.householdLocation.uploadHouseholdLocations" /></b>

<form id="uploadHouseholdLocation" action="uploadHouseholdLocation.form" method="post" enctype="multipart/form-data">

<table cellspacing="0" cellpadding="0" class="box">

<tr>
	<td class="tableCell" style="font-weight:bold"><nobr><spring:message code="household.householdLocation.fileName" />:</nobr></td>
	<td class="tableCell"><input type="file" name="file" size="50" /></td>
	<td class="tableCell" width="60%">&nbsp;</td>
</tr>

<tr>
	<td class="tableCell" style="font-weight:bold"><nobr><spring:message code="household.householdLocation.delimiter" />:</nobr></td>
	<td class="tableCell"><input type="text" name="delimiter" size="1" value="${delimiter}" /></td>
	<td class="tableCell">&nbsp;</td>
</tr>

<tr>
	<td class="tableCell" style="font-weight:bold"><nobr><spring:message code="household.householdLocation.overwrite" />:</nobr></td>
	<td class="tableCell"><input type="checkbox" name="overwrite" <c:if test="${overwrite}">checked</c:if>/></td>
	<td class="tableCell">&nbsp;</td>
</tr>

<tr>
	<td class="tableCell">
		<button type="submit">
			<spring:message code="general.upload" text="Upload"/>
		</button>
		<button type="reset">
			<spring:message code="general.cancel" text="Cancel"/>
		</button>	
	</td>
	<td class="tableCell">&nbsp;</td>
	<td class="tableCell">&nbsp;</td>
</tr>


</table>


</form>

</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>