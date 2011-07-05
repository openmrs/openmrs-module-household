<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/householdSearch.form" />

<%@ include file="/WEB-INF/template/header.jsp"%>
<h3><spring:message code="household.householdsearch.title"/></h3>
<%@ include file="localHeader.jsp"%>


<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" />

<script type="text/javascript">

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
      return false;

   return true;
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
		document.checked.voidMembers.disabled=true;
	else
		document.checked.voidMembers.disabled=false;
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
	
	document.checked.voidMembers.disabled=false;
	}
	}
	
	function uncheckAll(field)
	{
	for (i = 0; i < field.length; i++)
		field[i].checked = false ;
	document.checked.marktext.value="";
	document.checked.voidMembers.disabled=true;
	}


</script>
<body onload="document.checked.voidMembers.disabled=true">
<b class="boxHeader"><spring:message code="household.householdSearch.header"/></b>

<div class="box">
  	<form method="POST" name="checked">
		<table border="0" cellpadding="0" cellspacing="0">
		  <tr>
		    <td><spring:message code="household.householdSearch.identifier" /></td>
		    <td><input type="text" name="householdGroup" id="householdGroup" items="${hhmembers}"  onkeypress="return isNumberKey(event)" /></td>
		    <td><input type="submit" name="findMembers"  value="<spring:message code="household.householdSearch.header"/>"></td>
		    <%-- <INPUT id="txtChar" value="${hhmembersCount}"> --%> 
		  </tr>
		  	<input type="hidden" name="householdgrpRef" value="${hhmembersgrp.id}" />
		</table>
		
		
		<!-- </div> -->
		<b class="boxHeader"><spring:message code="household.householdSearch.results"/></b>
		<!-- <div class="box"> -->
		<c:choose>
			<c:when test="${not empty hhmembers}">
		<input type="button" name="CheckAll" value="Check All" onClick="checkAll(document.checked.id)" />
		<input type="button" name="UnCheckAll" value="Uncheck All" onClick="uncheckAll(document.checked.id)" />
						
			<table border="0" id="householdMembers" cellpadding="0" cellspacing="0">
				<thead>
				  		<tr>
				  			<th>Void?</th>
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
				  			<td align="center">${householdMembers.householdMembershipMember.birthdate}</td>
				  			<td align="center">${householdMembers.householdMembershipHeadship}</td>
				  			<td align="center">${householdMembers.startDate}</td>
				  			
				  		</tr>
				  		</c:forEach>
				 </tbody> 		
				</table>
				<table>
			  		<tr>
						<td>
							Void reason
						</td>
						<td>
							<textarea rows="3" cols="20" name="voidReason"></textarea>
						</td>
					</tr>
					<tr>
						
						
								<td>&nbsp;</td>	
								<td><input type="button" name="voidMembers" value="Void Selected Members"/></td>
						
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