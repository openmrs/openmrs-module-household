 <%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
        redirect="/module/household/householdIndexPerson.form" />
        
        <%-- <link href="${pageContext.request.contextPath}/moduleResources/household/css/tablestyles.css" type="text/css" rel="stylesheet" /> --%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<h3><spring:message code="household.householdIndexPerson.header"/></h3>
<%@ include file="localHeader.jsp"%>
<script type="text/javascript">
        function inputValidator() {
                var errorDivElement = document.getElementById("errorDivEmpty");
                var householdGroup = document.getElementById("householdGroup").value;
                if (householdGroup == "") {
                        document.getElementById("errorDivEmpty").innerHTML = "Empty Household";
                        errorDivElement.style.display = '';
                        return false;
                }else{
                        isValidHouseholdIdentifier();
                }
        }
        function fnRetCheckDigit(val){
                alert("Hey:" + val);
                if(val)
                        return true;
                else{
                        document.getElementById("errorDivEmpty").innerHTML = "Invalid Household Identifier";
                        errorDivElement.style.display = '';
                        return false;
                }
        }
        function isValidHouseholdIdentifier(){
                var hhVal = document.getElementById("householdGroup").value;
                var errorDivElement = document.getElementById("errorDivEmpty");
                if (hhVal == "") {
                        document.getElementById("errorDivEmpty").innerHTML = "Empty Household";
                        errorDivElement.style.display = '';
                        return false;
                }else{
                        DWRHouseholdService.getCheckDigit(hhVal,fnRetCheckDigit);
                }
        }
        function inputValidatorIndexSelect() {
                var errorDivIndexSelect = document.getElementById("errorDivIndexSelect");
                var indexVal = document.getElementById("indexVal").value;
                if (indexVal == "") {
                        errorDivIndexSelect.style.display = '';
                        return false;
                }else{
                        return true;
                }
        }
        function inputValidatorClosehouse() {
                var errorDivClosehsld = document.getElementById("closehousehold");
                var closersn = document.getElementById("closeReason").value;
                if (closersn == "") {
                        errorDivClosehsld.style.display = '';
                        return false;
                }else{
                        return true;
                }
        }
        var index="";
        function getRadios(what){
                // get the index value
                
                var j=what.name.length; 

                        for (i=0; i<j; i++){
                                if(what[i].checked) {
                                
                                        document.getElementById("indexVal").value=what[i].value
                                }
                        }
                }
</script>

<b class="boxHeader"><spring:message code="household.householdSearch.header"/></b>
<div class="box">

        <a href="../../findPatient.htm">Register Individuals</a>&nbsp;|&nbsp;
        <a href="householdSearch.form">Search for a Household</a>&nbsp;|&nbsp;
        <a href="householdResume.form">Resume Household</a>&nbsp;|&nbsp;
        <a href="householdIndexPerson.form">Change Index/Head</a>
        
        <form method="POST" name="checkedIndex">

                <table border="0" cellpadding="0" cellspacing="0">
                  <tr>
                    <td><spring:message code="household.householdSearch.identifier" /></td>
                    <td><input type="text" name="householdGroup" id="householdGroup"/></td>
                    <td><input type="submit" name="findMembers" onClick="return inputValidator()" value="<spring:message code="household.householdSearch.header"/>"></td>
                    <td>
                     <div class="error" id="errorDivEmpty" style="display: none"><spring:message code="household.householdsearch.errorEmpty" /></div>
                     <div class="error" id="errorDiv" style="display: none"><spring:message code="household.householdsearch.errorNumbersOnly"/></div> 
                    </td>
                  </tr>
                </table>  
                <input type="hidden" name="householdGroupCopy" value=${HHgrpId.id}  />
                <b class="boxHeader"><spring:message code="household.householdSearch.results"/></b>
                <c:choose>
                    <c:when test="${not empty index}">
                            <c:forEach var="index" items="${index}">
                                    <table border="0">
                                            <tr>
                                                    <td>
                                <b>The index is </b> ${index.householdMembershipMember.names}
                                                </td>
                                                <td>
                                <input type="hidden" name="memberId" value="${index.id}"/>
                                                </td>
                                        </tr>
                                        
                                </table>
                                        <c:choose>
                                        <c:when test="${length ==0}">
                                                <table border="0">
                                                        <tr>
                                                                <td>Close reason</td>
                                                                        <td><textarea rows="3" cols="20" name="closeReason" id="closeReason"></textarea></td>
                                                                        <td><div class="error" id="closehousehold" style="display: none">Provide close reason</div></td>
                                                        </tr>
                                                        <tr>
                                                        <td>&nbsp;</td>
                                                        <td><input type="submit" name="closeHousehold" id="closeHousehold" onClick="return inputValidatorClosehouse()" value="Close Household" /></td>
                                                        <td>&nbsp;</td>
                                                        </tr>
                                                </table>
                                        </c:when>
                                        <c:otherwise>
                                        </c:otherwise>
                                        </c:choose>
                            </c:forEach>
                    </c:when>
                    <c:otherwise>
                    <!-- The household is either closed or there is no members -->
                    </c:otherwise>
            </c:choose>        
                <c:choose>
                        <c:when test="${not empty hhmembers}"> 
                                    
                        <table border="0" id="householdMembers" cellpadding="0" cellspacing="5">
                                <thead>
                                                  <tr>
                                                          <th class="tbClass">Index?</th>
                                                          <th class="tbClass">Names</th>
                                                          <th class="tbClass">Gender</th>
                                                          <th class="tbClass">Birth Date</th>
                                                          <th class="tbClass">Head/Index</th>
                                                          <th class="tbClass">Start Date</th>
                                                          
                                                          
                                                          
                                                  </tr>
                                 </thead>
                                 <tbody>        
                                                  <c:forEach var="householdMembers" items="${hhmembers}">
                                                  
                                                  
                                                  <c:set var="len" value="${fn:length(householdMembers.householdMembershipMember.names)-1}" />
                                                  
                                                  
                                                  <tr>
                                                          
                                                          <td class="tdClass" onClick='getRadios(document.checkedIndex)'>
                                                          <input type="radio" name="index" value="${householdMembers.id}">
                                                          </td>
                                                          <td class="tdClass">${fn:substring(householdMembers.householdMembershipMember.names,1,25)}</td>
                                                           <td class="tdClass" align="center">${householdMembers.householdMembershipMember.gender}</td>
                                                          <td class="tdClass" align="center">${fn:substring(householdMembers.householdMembershipMember.birthdate,0,10)}</td>
                                                          <td class="tdClass" align="center">${householdMembers.householdMembershipHeadship}</td>
                                                          <td class="tdClass" align="center">${fn:substring(householdMembers.startDate,0,10)}</td>
                                                          
                                                  </tr>
                                                  </c:forEach>
                                 </tbody>                 
                                </table>
                                <table>
                                        
                                        <tr>
                                                <td>&nbsp;</td>
                                                <td><input type="submit" name="changeIndex"  value="Make selected member head of this household" onclick="return inputValidatorIndexSelect()"/></td>
                                        </tr>
                                </table>
                                <input type="hidden" name="indexVal"  id="indexVal" />
                                <div class="error" id="errorDivIndexSelect" style="display: none">Please select a member to replace the index</div>
                        </c:when>
                        <c:otherwise>
                                          <!-- There are no members in this household         -->        
                        </c:otherwise>
                        </c:choose>
        </form>
</div>