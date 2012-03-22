<%@ include file="/WEB-INF/template/include.jsp"%>
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
					<td class="highlight">${householdET.description}<br />
						<c:choose>
							<c:when test="${not empty householdET.dateRetired}">
								<span style="color: orange; font-style: italic;">RETIRED : Reason - ${householdET.retireReason} </span>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
					
					</td>
					<td class="highlight">
						<openmrs:format user="${householdET.creator}"/><br />
						<openmrs:formatDate date="${householdET.dateCreated}"/><br />
						<c:choose>
							<c:when test="${not empty householdET.dateRetired}">
								<span style="color: orange;">
									Retired by: <openmrs:format user="${householdET.retiredBy}"/><br />
									Retired on: <openmrs:formatDate date="${householdET.dateRetired}"/>
								</span>
							</c:when>
							<c:otherwise>
							</c:otherwise>
						</c:choose>
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