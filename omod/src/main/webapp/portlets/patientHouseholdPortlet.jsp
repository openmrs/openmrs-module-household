<%@ include file="/WEB-INF/template/include.jsp" %>

<table id="patientDet" border="1" cellpadding="5" width="80%">
	<thead>
	<tr>
		<th class="tbClass">No</th>
		<th class="tbClass">Household Name</th>
		<th class="tbClass">Start Date</th>
		<th class="tbClass">End Date</th>
	</tr>
	</thead>
	<tbody>
		<c:forEach var="householdMem" items="${model.householdM}" varStatus="state">
			<tr>
				<td class="tdClass" valign="top"><c:out value="${state.index + 1}"/></td>
				<td class="tdClass" valign="top"><a href="#" onclick="javascript:getMem(${householdMem.householdMembershipGroups.id});">${householdMem.householdMembershipGroups.householdIdentifier}</a></td>
				<td class="tdClass" valign="top"> <c:out value="${householdMem.startDate}"/></td>
				<td class="tdClass" valign="top"> <c:out value="${householdMem.endDate}"/><input type="hidden" name="householdId" id="householdId" value="${householdMem.householdMembershipGroups.id}"></td>
			</tr>
		</c:forEach>
	</tbody>
</table>