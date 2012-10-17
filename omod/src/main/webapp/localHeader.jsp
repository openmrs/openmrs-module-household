<ul id="menu">
	<li class="first">
		<a href="${pageContext.request.contextPath}/admin"><spring:message code="admin.title.short"/></a>
	</li>
        <openmrs:hasPrivilege privilege="Manage Household">
		<li <c:if test='<%= request.getRequestURI().contains("householdSettingsPanel") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdSettingsPanel.form">
				Configuration
			</a>
		</li>
	</openmrs:hasPrivilege>	
        <openmrs:hasPrivilege privilege="View Household">
		<li <c:if test='<%= request.getRequestURI().contains("householdDashboard") %>'>class="active"</c:if>>
			<a href="${pageContext.request.contextPath}/module/household/householdDashboard.form">
				Dashboard
			</a>
		</li>
	</openmrs:hasPrivilege>
        <c:if test='${gotAmpathLinks}'>
            <openmrs:hasPrivilege privilege="View Household">
                    <li <c:if test='<%= request.getRequestURI().contains("manageHouseholdLocation") %>'>class="active"</c:if>>
                            <a href="${pageContext.request.contextPath}/module/household/manageHouseholdLocation.form">
                             CHW Sites
                            </a>
                    </li>
            </openmrs:hasPrivilege>
            <openmrs:hasPrivilege privilege="View Household">
                    <li <c:if test='<%= request.getRequestURI().contains("householdCHWInitial") %>'>class="active"</c:if>>
                            <a href="${pageContext.request.contextPath}/module/household/householdCHWInitial.form">
                             CHW Encounter
                            </a>
                    </li>
            </openmrs:hasPrivilege>
        </c:if>
</ul>