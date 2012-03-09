<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/householdSearch.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<h3><spring:message code="household.title"/></h3>
<%@ include file="localHeader.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" />
<link href="${pageContext.request.contextPath}/moduleResources/household/css/tablestyles.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>

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
	
	function inputValidatorVoidReason() {
		var errorDivVoidReason = document.getElementById("errorDivVoidReason");
		var voidReason = document.getElementById("voidReason").value;
		if (voidReason == "") {
			errorDivVoidReason.style.display = '';
			return false;
		}else{
			return true;
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
	
	function listMem(c,n,z) {
		s=document.checked.marktext.value;
		if (c.checked) {
			if (s.indexOf(n)<0)
				s+=','+n;
		} else {
			s=document.checked.marktext.value.replace(','+n,'');
		}
		z=",";
		if (s.substring(2) == z) s=s.substring(2);
		
		document.checked.marktext.value=s;
		if(!document.checked.marktext.value=='')
			document.checked.voidMembers.disabled=false;
		else
			document.checked.voidMembers.disabled=true;
	} 
	function checkAll(field){
		
		for (i = 0; i < field.length; i++){
			field[i].checked = true ;
			listMem(field.length);
			document.checkedForm.voidMembers.disabled=false;
		}
	}
	
	function uncheckAll(field){
		for (i = 0; i < field.length; i++)
			field[i].checked = false ;
		document.checkedForm.marktext.value="";
		document.checkedForm.voidMembers.disabled=true;
	}
</script>
<script type="text/javascript" language="javascript"> 

function display(box) {
// get reference to form object, and to array of same-named checkboxes
var temparr = new Array(), f = box.form, boxgroup = f[box.name];
// loop through it 
alert(boxgroup.length);

for (var i=0; i<boxgroup.length; i++) {
// add the value of any checked box to next available slot in temparr
if (boxgroup[i].checked) temparr[temparr.length] = boxgroup[i].value;
// run the .join() method on the array (separator = ',') and output it to field
f.marktext.value = temparr.join(',');
 if(!document.checkedForm.marktext.value=='')
	document.checkedForm.voidMembers.disabled=false;
else
	document.checkedForm.voidMembers.disabled=true; 
}
}

</script> 
<body <!-- onload="document.forms[0].reset()" -->> 
<div id="dialog" title="Saved Information"></div>

<b class="boxHeader"><spring:message code="household.householdsearch.title"/></b>

<div class="box">
	<a href="../../findPatient.htm">Register Individuals</a>&nbsp;|&nbsp;
	<a href="householdSearch.form">Search for a Household</a>&nbsp;|&nbsp;
	<a href="householdResume.form">Resume Household</a>&nbsp;|&nbsp;
	<a href="householdIndexPerson.form">Change Index/Head</a><br />
  	<form method="POST" name="checkedForm">
		<table border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td><spring:message code="household.householdSearch.identifier" /></td>
		    <td><input type="text" name="householdGroup" id="householdGroup" items="${hhmembers}" /></td>
		    <td><input type="submit" name="findMembers" id="findMembers" onClick="return inputValidator()" value="<spring:message code="household.householdSearch.header"/>"></td>
		    <div class="error" id="errorDiv" style="display: none"><spring:message code="household.householdsearch.errorNumbersOnly"/></div> 
		    
		    <c:choose>
		    		<c:when test="${empty hhmembers}">
		    			<div class="error" id="errorDivEmpty" style="display: none"><spring:message code="household.householdsearch.errorEmpty" />
		    		</div>
		    		</c:when>
		    		<c:otherwise>
		    		</c:otherwise>
		    </c:choose>		
		    			
		   
		   
		  </tr>
		  	<input type="hidden" name="householdgrpRef" value="${hhold}" />
		  	</table>
		
		
		<!-- </div> -->
		<b class="boxHeader"><spring:message code="household.householdSearch.results"/></b>
		<!-- <div class="box"> -->
		<c:choose>
			<c:when test="${not empty hhmembers}">
		<!-- <input type="button" name="CheckAll" value="Check All" onClick="checkAll(document.checkedForm.id)" />
		<input type="button" name="UnCheckAll" value="Uncheck All" onClick="uncheckAll(document.checkedForm.id)" /> -->
			<br /> <b> ____________________________${hhold}___________________ </b><br />			
			<table border="0" id="householdMembers1" cellpadding="0" cellspacing="5">
				<thead>
				  		<tr>
				  			<th class="tbClass">Void?</th>
				  			<th class="tbClass">Names</th>
				  			<th class="tbClass">Gender</th>
				  			<th class="tbClass">Birth Date</th>
				  			<th class="tbClass">Head/Index</th>
				  			<th class="tbClass">Start Date</th>
				  		</tr>
				 </thead>
				 <tbody>	
				  		<c:forEach var="householdMembers" items="${hhmembers}">
				  		
				  		<tr>
				  			<td class="tdClass"><input type="checkbox" name="group" value="${householdMembers.id}" onclick="display(this)"></td>
				  			<td class="tdClass">
				  			<c:choose>
								<c:when test="${not empty householdMembers.householdMembershipMember.givenName }">
								${householdMembers.householdMembershipMember.givenName} &nbsp;
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${not empty householdMembers.householdMembershipMember.middleName }">
								${householdMembers.householdMembershipMember.middleName} &nbsp;
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
							<c:choose>
								<c:when test="${not empty householdMembers.householdMembershipMember.familyName }">
								${householdMembers.householdMembershipMember.familyName} &nbsp;
								</c:when>
								<c:otherwise>
								</c:otherwise>
							</c:choose>
				  			</td>
				   			<td class="tdClass" align="center">${householdMembers.householdMembershipMember.gender}</td>
				  			<td class="tdClass" align="center">${fn:substring(householdMembers.householdMembershipMember.birthdate,0,10)}</td>
				  			<td class="tdClass" align="center">${householdMembers.householdMembershipHeadship}</td>
				  			<td class="tdClass" align="center">${fn:substring(householdMembers.startDate,0,10)}</td>
				  			
				  		</tr>
				  		</c:forEach>
				 </tbody> 		
				</table>
				<hr />
				<table>
					<tr>
						<td>Date voided</td>
						<td><input type="text" name="startDate" onClick="showCalendar(this)" /></td>
					</tr>
			  		<tr>
						<td>
							Void reason
						</td>
						<td>
							<textarea rows="3" cols="20" name="voidReason" id="voidReason"></textarea>
						</td>
						<td>
						<div class="error" id="errorDivVoidReason" style="display: none"><spring:message code="household.householdsearch.voidReason" />
						</td>
						</tr>
					<tr>
						
						
								<td>&nbsp;</td>
								<td><input type="submit" name="voidMembers" id="voidMembers" onClick="return inputValidatorVoidReason();inputValidatorIndexPerson()" value="Void Selected Members" disabled="disabled"/></td>
								<td>&nbsp;</td>		
						
					</tr>
					<tr>
			  			<td>
			  			<c:choose>
							<c:when test="${not empty hhmembersgrp.id}">
			  				
			  			<a href="householdRegistration.form?grpid=${hhold}">Add Members</a>
			  				</c:when>
			  				<c:otherwise>
			  				
			  				</c:otherwise>
			  			</c:choose>	
			  			</td>
			  			<td><input type="text" value="" name="marktext" id="marktext"></td>
			  			
			  		 
			  		</tr>	
				</table>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${not empty hhold}">
						<div class="error" id="errorDiv">
							${hhold} - doesn't have members registered to.
						</div>
					</c:when>
					<c:otherwise>
						Enter something to search.
					</c:otherwise>
				</c:choose>
			</c:otherwise>
			</c:choose>
			  		
	</form>	
			
</div>
</body>
<%@ include file="/WEB-INF/template/footer.jsp"%>