<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/householdResume.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<h3><spring:message code="household.householdResume.title"/></h3>
<%@ include file="localHeader.jsp"%>


<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" />

<script type="text/javascript">
function inputValidator() {
	var errorDivElement = document.getElementById("errorDivEmpty");
	var householdGroup = document.getElementById("householdGroup").value;
	if (householdGroup == "") {
		errorDivElement.style.display = '';
		return false;
	}else{
		return true;
	}
}
</script>
<script type="text/javascript">
function inputValidatorResumeReason() {
	var errorDivResumeReason = document.getElementById("errorDivResumeReason");
	var ResumeReason = document.getElementById("resumeReason").value;
	if (ResumeReason == "") {
		errorDivResumeReason.style.display = '';
		return false;
	}else{
		return true;
	}
}
</script>
<script type="text/javascript">

function isNumberKey(evt)
{
   var error = document.getElementById("errorDiv");
   var groupToSearch = document.getElementById("householdGroup");
   var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode > 31 && (charCode < 48 || charCode > 57)){
	   error.style.display='';
      return false;
   }
      else{
   return true;
      }
}

</script>

<script type="text/javascript">
function list(c,n,z) {
s=document.checked.marktext.value;
if (c.checked) {
if (s.indexOf(n)<0) s+=','+n;
} else {
s=document.checked.marktext.value.replace(','+n,'');
}
z=",";
if (s.substring(2) == z) s=s.substring(2);

document.checked.marktext.value=s;
	if(document.checked.marktext.value=='')
		document.checked.resumeMembers.disabled=true;
	else
		document.checked.resumeMembers.disabled=false;
} 
</script>
<script type="text/javascript" charset="utf-8">
			$j(document).ready(function() {
				$j('#householdMembers').dataTable( {
					"bAutoWidth": false,
				    "bLengthChange":true,
				    "bJQueryUI": false
				    
				    
				} );
			} );
</script>
<script type="text/javascript" charset="utf-8">
		function checkAll(field)
		{
			
		for (i = 0; i < field.length; i++){
			field[i].checked = true ;
			//for(j=0;j<field[i].length;j++){
				//document.checked.marktext.value +=field[i].checked.value;
				list(field.length);
			//}
		
		document.checked.resumeMembers.disabled=false;
		}		
	}
	
	function uncheckAll(field)
	{
	for (i = 0; i < field.length; i++)
		field[i].checked = false ;
	document.checked.marktext.value="";
	document.checked.resumeMembers.disabled=true;
	}


</script>
<body onload="document.checked.resumeMembers.disabled=true">
<b class="boxHeader"><spring:message code="household.householdSearch.header"/></b>

<div class="box">

	<a href="../../findPatient.htm">Register Individuals</a>&nbsp;|&nbsp;
	<a href="householdSearch.form">Search for a Household</a>&nbsp;|&nbsp;
	<a href="householdResume.form">Resume Care</a>&nbsp;|&nbsp;
	<a href="householdIndexPerson.form">Change Household Head</a>
	
  	<form method="POST" name="checked">
		<table border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td><spring:message code="household.householdSearch.identifier" /></td>
		    <td><input type="text" name="householdGroup" id="householdGroup" items="${hhmembers}"  onkeypress="return isNumberKey(event)" /></td>
		    <td><input type="submit" name="findMembers" onClick="return inputValidator()" value="<spring:message code="household.householdSearch.header"/>"></td>
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
		  	<input type="hidden" name="householdgrpRef" value="${hhmembersgrp.id}" />
		</table>
		
		
		<!-- </div> -->
		<b class="boxHeader"><spring:message code="household.householdSearch.results"/></b>
		<!-- <div class="box"> -->
		<c:choose>
			<c:when test="${not empty hhmembers}">
		<input type="button" name="CheckAll" value="Check All" onClick="checkAll(document.checked.id)" />
		<input type="button" name="UnCheckAll" value="Check All" onClick="uncheckAll(document.checked.id)" />
						
			<table border="0" id="householdMembers1" cellpadding="0" cellspacing="5">
				<thead>
				  		<tr>
				  			<th>Resume?</th>
				  			<th>Names</th>
				  			<th>Gender</th>
				  			<th>Birth Date</th>
				  			<th>Head/Index</th>
				  			<th>Start Date</th>
				  			
				  			
				  			
				  		</tr>
				 </thead>
				 <tbody>	
				  		<c:forEach var="householdMembers" items="${hhmembers}">
				  		
				  		<tr>
				  			<td><input type="checkbox" name="id" onclick="list(this,'${householdMembers.id}')"></td>
				  			<td>${householdMembers.householdMembershipMember.names}</td>
				   			<td align="center">${householdMembers.householdMembershipMember.gender}</td>
				  			<td align="center">${fn:substring(householdMembers.householdMembershipMember.birthdate,0,10)}</td>
				  			<td align="center">${householdMembers.householdMembershipHeadship}</td>
				  			<td align="center">${fn:substring(householdMembers.startDate,0,10)}</td>
				  			
				  		</tr>
				  		</c:forEach>
				 </tbody> 		
				</table>
				<hr />
				<table>
					<tr>
						<td>Date Resumed</td>
						<td><input type="text" name="resumeDate" onClick="showCalendar(this)" /></td>
					</tr>
			  		<tr>
						<td>
							Resume reason
						</td>
						<td>
							<textarea rows="3" cols="20" name="resumeReason" id="resumeReason"></textarea>
						</td>
						<td>
						<div class="error" id="errorDivResumeReason" style="display: none"><spring:message code="household.householdsearch.resumeReason" />
						</td>
					</tr>
					<tr>
						
						
								<td>&nbsp;</td>	
								<td><input type="submit" name="resumeMembers" onClick="return inputValidatorResumeReason()" value="Resume Care"/></td>
								<td>&nbsp;</td>
					</tr>
					<tr>
			  			<td>
			  			<c:choose>
							<c:when test="${not empty hhmembersgrp.id}">
			  				
			  			<a href="householdRegistration.form?grpid=${hhmembersgrp.id}">Add Members</a>
			  				</c:when>
			  				<c:otherwise>
			  				
			  				</c:otherwise>
			  			</c:choose>	
			  			</td>
			  			<!-- this inputbox controls the list for voiding -->
			  			<td><input type="hidden" value="" name="marktext"></td>
			  		 
			  		</tr>	
				</table>
			</c:when>
			<c:otherwise>
			</c:otherwise>
			</c:choose>
			  		
	</form>	
			
</div>
</body>
<%@ include file="/WEB-INF/template/footer.jsp"%>