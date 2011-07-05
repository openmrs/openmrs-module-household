<ul id="menu">
	<li class="first">
		<a href="${pageContext.request.contextPath}/admin"><spring:message code="admin.title.short"/></a>
	</li>
	<openmrs:hasPrivilege privilege="Manage Household">
		<li <c:if test='<%= request.getRequestURI().contains("householdDefinitions") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdDefinitions.form">
				New Household Definition
			</a>
		</li>
		<li <c:if test='<%= request.getRequestURI().contains("householdRegistration") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdRegistration.form">
				Registration
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="View Household">
		<li <c:if test='<%= request.getRequestURI().contains("householdEncounterType") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdEncounterTypeList.list">
				Encounters
			</a>
		</li>
	</openmrs:hasPrivilege>
</ul>