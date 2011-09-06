<ul id="menu">
	<li class="first">
		<a href="${pageContext.request.contextPath}/admin"><spring:message code="admin.title.short"/></a>
	</li>
	<openmrs:hasPrivilege privilege="Manage Household">
		<li <c:if test='<%= request.getRequestURI().contains("householdDefinitions") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdDefinitions.form">
				Definitions/Groups
			</a>
		</li>
		<li <c:if test='<%= request.getRequestURI().contains("householdEncounterType") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdEncounterTypeList.list">
				Encounters
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="View Household">
		<li <c:if test='<%= request.getRequestURI().contains("householdRegistration") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdRegistration.form">
				Registration
			</a>
		</li>
		
		<%-- <li <c:if test='<%= request.getRequestURI().contains("householdLocationUpload") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdLocationUpload.form">
			 Upload Sites
			</a>
		</li> --%>
		
		<li <c:if test='<%= request.getRequestURI().contains("householdCHWInitial") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdCHWInitial.form">
			 CHW Initial Encounter
			</a>
		</li>
	</openmrs:hasPrivilege>
	<openmrs:hasPrivilege privilege="Manage Household">
			<li <c:if test='<%= request.getRequestURI().contains("enctypeSetting") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdEnctypeSetting.htm">
			 Household Settings
			</a>
		</li>
	</openmrs:hasPrivilege>	
	<openmrs:hasPrivilege privilege="Manage Household">
			<li <c:if test='<%= request.getRequestURI().contains("manageHouseholdLocation") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/manageHouseholdLocation.form">
			 Sites/Locations
			</a>
		</li>
	</openmrs:hasPrivilege>	
</ul>