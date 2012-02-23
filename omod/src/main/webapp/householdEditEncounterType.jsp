<%@ include file="/WEB-INF/template/include.jsp"%>

	<form:form id="encTypeForm" modelAttribute="householdET">
		
		<!-- Here :<input type="te xt" name="checking"/> -->
		<table>
			<tr>
				<td>
					<span class="required">*</span>
					Name
				</td>
				<td>
					<form:input path="name"/> <form:errors path="name" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td>
					Description
				</td>
				<td>
					<form:textarea path="description" rows="4" cols="50"/> <form:errors path="description" cssClass="error"/>
				</td>
			</tr>
			<tr>
				<td><spring:message code="general.createdBy"/></td>
				<td>
					<openmrs:format user="${householdET.creator}"/>
					<openmrs:formatDate date="${householdET.dateCreated}"/>
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
