<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/householdDefinitions.form" />

<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>

<%@ include file="/WEB-INF/template/header.jsp"%>
<h3><spring:message code="household.title"/></h3>

<%@ include file="localHeader.jsp"%>

﻿<openmrs:htmlInclude file="/scripts/jquery-ui/js/jquery-ui.custom.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/highlight/jquery.highlight-3.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.filteringDelay.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css" /> 
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables.css" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />



<script type="text/javascript">
function toggle_visibility() {
    var em = document.getElementById("foo");
    var info = document.getElementById("infoMem");
    if(em.style.display == 'block'){
		em.style.display = 'none';
    	info.style.display = 'none';
    }else{
		em.style.display = 'block';
		info.style.display = 'block';
    }
 }
 function getHHolds(strUuid){
	 DWRHouseholdService.getHouseholds(strUuid,getHHoldsDisp);
 }
 function getHHoldsDisp(strVal){
	 
	 var strHou = new Array();
	 strHou = strVal.split("|");
	 var i;
	 strHH = "<b><u>" +strHou[0] +" Registered Households<u/><b/><br /><br /><table cellpadding='5'><tr><th></th><th class='tbClass'>a</th>" +
	 	"<th class='tbClass'>b</th><th class='tbClass'>c</th><th class='tbClass'>d</th><th class='tbClass'>e</th></tr>";
	for(i = 1; i < (strHou.length-1); i++) {
		strHH = strHH + "<tr><td  class='tbClass'>" + i + "</td><td>"; 
			if (strHou[i] != null) {
				strHH = strHH + strHou[i] + "</td><td>";
			} 
			if(strHou[i+1] != null){
				strHH = strHH + strHou[i+1] + "</td><td>";
			}
			if(strHou[i+2] != null){
				strHH = strHH +strHou[i+2] + "</td><td>";
			}
			if(strHou[i+3] != null){
				strHH = strHH +strHou[i+3] + "</td><td>";
			}
			if(strHou[i+4]!= null){
				strHH = strHH + strHou[i+4];
			} 
			strHH = strHH + "</td></tr>";
		i+=4;
	}
	strHH = strHH + "</table>";
    document.getElementById("foo").innerHTML=strHH;
	toggle_visibility();
 }
</script>
<script type="text/javascript">
$j(document).ready(function(){
    $j('#houseDefs').dataTable({
    	"bLengthChange":true
    	
    });
});
</script>

<b class="boxHeader"><spring:message code="household.definitions.header"/></b>
<div class="box">

	<table border="0">
		<tr>
			<td valign="top">
				<form method="POST" name="householdDefinitions">
				<table>
					<c:choose>
						<c:when test ="${not empty householdEdit}">
							<input type="hidden" name="houseid" value="${householdEdit.id}" />
							<tr>
								<td><spring:message code="household.definitions.code"/></td>
								<td><input type="text" name="householdDefinitionsCode" value="${householdEdit.householdDefinitionsCode}" /></td>
							</tr>
							<tr>
								<td><spring:message code="household.definitions.codeinfull"/></td>
								<td><input type="text" name="householdDefinitionsCodeinfull" value="${householdEdit.householdDefinitionsCodeinfull}" /></td>
							</tr>
							<tr>
								<td><spring:message code="household.definitions.description"/></td>
								<td>
									<textarea name="householdDefinitionsDescription">${householdEdit.householdDefinitionsDescription}</textarea>
								</td>
							</tr>
							<tr>
								<td></td><td><input type="submit" value="Save Changes" /></td>
							</tr>
							
						</c:when>
						<c:otherwise>
							<tr>
								<td><spring:message code="household.definitions.code"/></td>
								<td><input type="text" name="householdDefinitionsCode" value="${householdDefinitionsCode}" /></td>
							</tr>
							<tr>
								<td><spring:message code="household.definitions.codeinfull"/></td>
								<td><input type="text" name="householdDefinitionsCodeinfull" value="${householdDefinitionsCodeinfull}" /></td>
							</tr>
							<tr>
								<td><spring:message code="household.definitions.description"/></td>
								<td>
									<textarea name="householdDefinitionsDescription">${householdDefinitionsDescription}</textarea>
								</td>
							</tr>
							<tr>
								<td></td><td><input type="submit" value="Add New" /></td>
							</tr>
						</c:otherwise>
					</c:choose>
					
					
				</table>
				</form>
			</td>
			<td width="1" bgcolor="#C0C0C0">&nbsp;</td>
			<td valign="top">
				<table  id="houseDefs">
					<thead>
					<tr>
						<th>No</th>
						<th>Household Code</th>
						<th>Household Code In Full</th>
						<th>Household Description</th>
						<th>View Households</th>
						<th>Action</th>
						<th>&nbsp;</th>
					
					</tr>
					</thead>
					
					<tbody>
					<c:forEach var="household" items="${householdsTypes}" varStatus="ind">
						<form method="POST" name="${household.id}">
						
						<tr>
							<td>${ind.index + 1}</td>
							<td>${household.householdDefinitionsCode}</td>
							<td>${household.householdDefinitionsCodeinfull}</td>
							<td>${household.householdDefinitionsDescription}</td>
							<td><a href="#" onclick="javascript:getHHolds('${household.uuid}');">View</a></td>
							<td>
								<input type="hidden" name="houseid" id="${household.id}" value="${household.id}" />
							    <input type="submit" value="Edit" />
								<!-- <a href="#">Edit</a> -->
							</td>
							<td>
							<a href="#">Delete</a>
							</td>
						</tr>
						
						</form>
					</c:forEach>
						</tbody>
					
				</table>
			</td>
		</tr>
	</table>
</div>
<div id="infoMem" style="display:none;">
	<div id="xsnazzy">
		<b class="xtop"><b class="xb1"></b><b class="xb2"></b><b class="xb3"></b><b class="xb4"></b></b>
		<div class="xboxcontent">
			<div id="foo" style="display:none;" class="tdClass2">
			</div>
		</div>
		<b class="xbottom"><b class="xb4"></b><b class="xb3"></b><b class="xb2"></b><b class="xb1"></b></b>
	</div>
	<br class="clear" /><br/>
</div> <!-- end of info -->
<%@ include file="/WEB-INF/template/footer.jsp"%>