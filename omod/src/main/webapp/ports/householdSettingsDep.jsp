<%@ include file="/WEB-INF/template/include.jsp"%>
<table id="houseDefsDepartment"  class="lineTable">
						<thead>    
					    	<tr>
					            <th scope="col" rowspan="2">&nbsp;</th>
					            <th scope="col" colspan="6">Registered Departments</th>
					        </tr>
					        <tr>
					            <th scope="col">Department Code</th>
					            <th scope="col">Department Fullname</th>
					            <th scope="col">Description</th>
					            <th scope="col"><spring:message code="general.createdBy"/></th>
					            <th scope="col">Action</th>
					        </tr>        
					    </thead>
					    <tbody id="bdytblDepartment">
					    	<c:forEach var="household" items="${householdDepartment}" varStatus="ind">
								<form method="POST" name="formDep${household.id}">
									<tr valign="top">
										<th>${ind.index + 1}</th>
										<td class="highlight">${household.parentCode}</td>
										<td class="highlight">${household.parentFullname}</td>
										<td class="highlight">${household.parentDescription}</td>
										<td class="highlight">
											<openmrs:format user="${household.creator}"/><br />
											<openmrs:formatDate date="${household.dateCreated}"/>
										</td>
										<td class="highlight">
											<input type="hidden" name="houseid" id="${household.id}" value="${household.id}" />
											<a href="#" onclick="javascript:onClickEditDepartment('${household.id}','${household.parentCode}','${household.parentFullname}','${household.parentDescription}')">
												<img src="${pageContext.request.contextPath}/moduleResources/household/images/edit.gif"/></a>
											<a href="#" onclick="javascript:onClickDeleteDepartment('${household.id}','${household.parentCode}')">
												<img src="${pageContext.request.contextPath}/moduleResources/household/images/trash.gif"/></a>
										</td>
									</tr>
								</form>
							</c:forEach>
					    </tbody>
					</table>