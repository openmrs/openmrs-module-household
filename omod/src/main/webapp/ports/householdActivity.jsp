<%@ include file="/WEB-INF/template/include.jsp"%>
<%-- 
    Document   : householdActivity
    Created on : Oct 9, 2012, 10:59:48 AM
    Author     : Mwogi
--%>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/css-table.css" type="text/css" rel="stylesheet" /> 

<div>
    <table style="width: 100%;">
        <tr><td>
                <div style="float: right;">
                    <h2><span style="color: gray;">Household : </span> ${household.householdIdentifier}</h2>
                </div>
                <br />
                <div style="float: right;">
                    <c:if test="${belong}">
                        <c:choose>
                            <c:when test="${isRetired}"></c:when>
                            <c:otherwise>
                                <button class="minimal" onclick="javascript:funEditActivity(1, ${household.id})">Add Member(s)</button> 
                                <button class="minimal" onclick="javascript:funEditActivity(2, ${household.id})">Remove Member(s)</button> 
                                <button class="minimal" onclick="javascript:funEditActivity(3, ${household.id})">Retire Household</button> 
                                <button class="minimal" onclick="javascript:funEditActivity(4, ${household.id})">Retire Member(s)</button> 
                            </c:otherwise>
                        </c:choose>
                    </c:if>
                    <button class="minimal" onclick="javascript:funEditActivity(5, ${household.id})">History</button> 
                </div>
            </td>
        </tr>
        <tr><td>
                <a href="#" id="lnkfun" onclick="javascript:funHideEditActivity()" style="float: right; margin-top: 10px; padding: 3px; display: none;">
                    <img src="${pageContext.request.contextPath}/moduleResources/household/images/destroy.png" alt="[X]" />
                </a>
                <div id="editActivity" class="householdtoggle" style="display: none; margin-top: 5px;margin-bottom: 5px;">
                    
                </div>
            </td>
        </tr>
        <tr><td>
                <c:choose>
                    <c:when test="${isRetired}">
                        <table>
                            <tr>
                                <td>
                                    <div class="horizontalLine"></div>
                                </td>
                            </tr>
                            <tr><td>
                                    <div id="divPerson">
                                        <div class="notice">NOTICE</div>
                                        This household was retired with its members.<br/>
                                        <b>Summary:</b>
                                        <table>
                                            <tr>
                                                <td><b>Date :</b></td><td><i><openmrs:formatDate date="${household.endDate}"/></i></td>
                                            </tr>
                                            <tr>
                                                <td><b>Reason : </b></td><td><i>${household.retireReason}</i></td>
                                            </tr>
                                            <tr>
                                                <td><b>Retired By :</b></td><td><i><openmrs:format user="${household.changedBy}"/></i></td>
                                            </tr>
                                        </table> 
                                    </div>
                                </td>
                            </tr>
                        </table>
                        
                        
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${household.voided}">
                                This household is voided/removed.<br />
                                <b class="notice">Summary:</b>
                                <br/><br/>
                                <table>
                                    <tr>
                                        <td><b>Date :</b></td><td><i><openmrs:formatDate date="${household.dateVoided}"/></i></td>
                                    </tr>
                                    <tr>
                                        <td><b>Reason : </b></td><td><i>${household.voidReason}</i></td>
                                    </tr>
                                    <tr>
                                        <td><b>Voided By :</b></td><td><i><openmrs:format user="${household.voidedBy}"/></i></td>
                                    </tr>
                                </table>
                                
                            </c:when>
                            <c:otherwise>
                                <div class="horizontalLine"></div>
                                <table>
                                    <tr>
                                        <td>
                                           <div class="titlebar">Registered/Existing Members:</div>
                                            <table style="margin-left: 10px;" cellpadding="3">
                                                <tr>
                                                    <td>&nbsp;</td><td>Name</td><td>Gender</td><td>Age</td><td>Joined</td>
                                                </tr>
                                                <c:forEach var="hhm" items="${memberList}" varStatus="ind">
                                                    <tr class="linetablerow">
                                                        <th>
                                                            ${ind.index + 1 }
                                                            <c:choose>
                                                                <c:when test="${hhm.householdMembershipHeadship }">
                                                                    <img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" alt="[HEAD/INDEX]" />
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                        </th>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${hhm.householdMembershipMember.dead }">
                                                                    <span style="color: red;">Dead!</span>
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.givenName }">
                                                                    ${hhm.householdMembershipMember.givenName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.middleName }">
                                                                    ${hhm.householdMembershipMember.middleName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.familyName }">
                                                                    ${hhm.householdMembershipMember.familyName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                         </td>
                                                        <td>[${hhm.householdMembershipMember.gender}]</td>
                                                        <td>${hhm.householdMembershipMember.age} yr(s)</td>
                                                        <td><openmrs:formatDate date="${hhm.startDate}"/> - By : <openmrs:format user="${hhm.creator}"/>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </table>
                                        </td>
                                    </tr>
                                    <c:if test="${not empty memberQuasi}">
                                    <tr>
                                        <td>
                                            <div class="titlebar">Quasi Member(s): (Inactive for the household activities)</div>
                                            <table style="margin-left: 10px;" cellpadding="3">
                                                <tr>
                                                    <td>&nbsp;</td><td>Name:</td><td>By:</td><td>Date:</td>
                                                </tr>
                                                <c:forEach var="hhm" items="${memberQuasi}" varStatus="ind">
                                                    <tr class="linetablerow">
                                                        <td>${ind.index + 1 }</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${hhm.householdMembershipHeadship }">
                                                                    <img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" alt="[HEAD/INDEX]" />
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.givenName }">
                                                                    ${hhm.householdMembershipMember.givenName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.middleName }">
                                                                    ${hhm.householdMembershipMember.middleName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.familyName }">
                                                                    ${hhm.householdMembershipMember.familyName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td><openmrs:format user="${hhm.creator}"/></td>
                                                        <td><openmrs:formatDate date="${hhm.dateCreated}"/></td>
                                                    </tr>
                                                </c:forEach>

                                            </table>
                                        </td>
                                    </tr>
                                    </c:if>
                                    <c:if test="${not empty memberRetired}">
                                    <tr>
                                        <td>
                                            <div class="titlebar">Retired Member(s):</div>
                                            <table style="margin-left: 10px;" cellpadding="3">
                                                <tr>
                                                    <td>&nbsp;</td><td>Name:</td><td>Reason:</td><td>Retired By:</td><td>Date:</td>
                                                </tr>
                                                <c:forEach var="hhm" items="${memberRetired}" varStatus="ind">
                                                    <tr class="linetablerow">
                                                        <td>${ind.index + 1 }</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${hhm.householdMembershipHeadship }">
                                                                    <img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" alt="[HEAD/INDEX]" />
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.givenName }">
                                                                    ${hhm.householdMembershipMember.givenName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.middleName }">
                                                                    ${hhm.householdMembershipMember.middleName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.familyName }">
                                                                    ${hhm.householdMembershipMember.familyName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${hhm.retireReason}</td>
                                                        <td><openmrs:format user="${hhm.changedBy}"/></td>
                                                        <td><openmrs:formatDate date="${hhm.endDate}"/></td>
                                                    </tr>
                                                </c:forEach>

                                            </table>
                                        </td>
                                    </tr>
                                    </c:if>
                                    <c:if test="${not empty memberVoided}">
                                    <tr>
                                        <td>
                                            <div class="titlebar">Voided/Removed Member(s):</div>
                                            <table style="margin-left: 10px;" cellpadding="3">
                                                <c:forEach var="hhm" items="${memberVoided}" varStatus="ind">
                                                    <tr>
                                                        <td>&nbsp;</td><td>Name:</td><td>Reason:</td><td>Voided By:</td><td>Date:</td>
                                                    </tr>
                                                    <tr class="linetablerow">
                                                        <td>${ind.index + 1 }</td>
                                                        <td>
                                                            <c:choose>
                                                                <c:when test="${hhm.householdMembershipHeadship }">
                                                                    <img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" alt="[HEAD/INDEX]" />
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.givenName }">
                                                                    ${hhm.householdMembershipMember.givenName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.middleName }">
                                                                    ${hhm.householdMembershipMember.middleName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                            <c:choose>
                                                                <c:when test="${not empty hhm.householdMembershipMember.familyName }">
                                                                    ${hhm.householdMembershipMember.familyName} &nbsp;
                                                                </c:when>
                                                                <c:otherwise></c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>${hhm.voidReason}</td>
                                                        <td><openmrs:format user="${hhm.voidedBy}"/></td>
                                                        <td><openmrs:formatDate date="${hhm.dateVoided}"/></td>
                                                    </tr>
                                                </c:forEach>

                                            </table>
                                        </td>
                                    </tr>
                                    </c:if>
                                </table>
                            </c:otherwise>
                        </c:choose> 
                    </c:otherwise>
                </c:choose>
             </td>
        </tr>
    </table>
    
   
</div>
