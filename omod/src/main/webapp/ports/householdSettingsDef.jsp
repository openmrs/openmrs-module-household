<%@ include file="/WEB-INF/template/include.jsp"%>
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
    <tbody id="bdytbl">
    	<c:forEach var="hd" items="${householdsTypes}" varStatus="ind">
			<form method="POST" name="${hd.id}">
				<tr valign="top">
					<th>${ind.index + 1}</th>
					<td class="highlight">
						<c:choose>
							<c:when test="${not empty hd.parent}">
								${hd.householdDefinitionsCode} [Parent: ${hd.parent.parentCode}]
							</c:when>
							<c:otherwise>
								${hd.householdDefinitionsCode}
							</c:otherwise>
						</c:choose>
					
					</td>
					<td class="highlight">${hd.householdDefinitionsCodeinfull}</td>
					<td class="highlight">${hd.householdDefinitionsDescription}</td>
					<td class="highlight">
						<openmrs:format user="${hd.creator}"/><br />
						<openmrs:formatDate date="${hd.dateCreated}"/>
					</td>
					<td class="highlight">
						<input type="hidden" name="houseid" id="${hd.id}" value="${hd.id}" />
					    <a href="#" onclick="javascript:onClickEditProgram('${hd.id}','${hd.householdDefinitionsCode}','${hd.householdDefinitionsCodeinfull}','${hd.householdDefinitionsDescription}','${hd.parent.id}','${hd.identifierPrefix}')">
							<img src="${pageContext.request.contextPath}/moduleResources/household/images/edit.gif" alt="Edit"/>
						</a>
						<a href="#" onclick="javascript:onClickDeleteProgram('${hd.id}','${hd.householdDefinitionsCode}')">
							<img src="${pageContext.request.contextPath}/moduleResources/household/images/trash.gif" alt="Delete"/>
						</a>
					</td>
				</tr>
			</form>
		</c:forEach>
    </tbody>
</table>