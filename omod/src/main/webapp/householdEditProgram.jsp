<%@ include file="/WEB-INF/template/include.jsp"%>

	<form:form id="programForm" modelAttribute="householdDef">
		
		<!-- Here :<input type="te xt" name="checking"/> -->
		<table>
			<tr>
				<td>
					<span class="required">*</span>
					Program Code
				</td>
				<td>
					<form:input path="householdDefinitionsCode"/> <form:errors path="householdDefinitionsCode" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					<span class="required">*</span>
					Program Code in Full
				</td>
				<td>
					<form:input path="householdDefinitionsCodeinfull"/> <form:errors path="householdDefinitionsCodeinfull" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					Parent:
				</td>
				<td>
					<select name="parent">
						<c:choose>
							<c:when test="<form:input path='parent'/>">
								<option id="m" value="" selected="selected"></option>
								<c:forEach var="hh" items="${householdsTypes}" varStatus="ind">
									<option id="${ind.index + 1 }" value="${hh.id}">${hh.householdDefinitionsCode}</option>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<option id="m" value=""></option>
								<c:forEach var="hh" items="${householdsTypes}" varStatus="ind">
								
									<c:choose>
										<c:when test="${householdParent == hh}">
											<option id="${ind.index + 1 }" selected="selected" value="${hh.id}">${hh.householdDefinitionsCode}</option>
										</c:when>
										<c:otherwise>
											<option id="${ind.index + 1 }" value="${hh.id}">${hh.householdDefinitionsCode}</option>
										</c:otherwise>
									</c:choose>
								
									
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</select>
				
				</td>
			</tr>
			<tr>
				<td>
					Description
				</td>
				<td>
					<form:textarea path="householdDefinitionsDescription" rows="4" cols="50"/> <form:errors path="householdDefinitionsDescription" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td><spring:message code="general.createdBy"/></td>
				<td>
					<openmrs:format user="${householdDefinition.creator}"/>
					<openmrs:formatDate date="${householdDefinition.dateCreated}"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<input type="submit" value="<spring:message code="general.save"/>" />
					<input type="button" value="<spring:message code="general.cancel"/>" onClick="window.location = 'householdSettingsPanel.form'"/>
				</td>
			</tr>
		</table>
	</form:form>
<!-- </div> -->
