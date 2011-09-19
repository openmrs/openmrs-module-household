<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/householdCHWInitial.form" />
	
<%@ include file="/WEB-INF/template/header.jsp"%>
<h3><spring:message code="household.title"/></h3>

<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<link href="<openmrs:contextPath/>/scripts/jquery-ui/css/<spring:theme code='jqueryui.theme.name' />/jquery-ui.custom.css" type="text/css" rel="stylesheet" />

<openmrs:htmlInclude file="/dwr/interface/DWRPersonService.js" />
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>


<script type="text/javascript">
	// get sub locations
	function getSubLoci(locat){
		DWRHouseholdService.getSubLocations(locat.options[locat.selectedIndex].value, gotSub);
	} 
	
	function gotSub(strSubs){
		var opt = document.chwForm.subLocation;
		if(!(opt.value.length == 0)){
			opt.length = 0;
		}
		var strSubLocat = new Array();
		strSubLocat = strSubs.split(",");
		
		var i;

		for(i = 0; i < strSubLocat.length; i++) {
			opt.options[i] = new Option(strSubLocat[i], strSubLocat[i]);
		}
	}
	//villages
	function getVilla(subLoc,locat){
		DWRHouseholdService.getVillage(subLoc.options[subLoc.selectedIndex].value,locat.options[locat.selectedIndex].value, gotVil);
	}
	
	function gotVil(strVill){
		var opt = document.chwForm.village;
		if(!(opt.value.length == 0)){
			opt.length = 0;
		}
		var strVil = new Array();
		strVil = strVill.split(",");
		
		var i;

		for(i = 0; i < strVil.length; i++) {
			opt.options[i] = new Option(strVil[i], strVil[i]);
		}
	}
	//Household
	function getHouseH(household){
		var valHousehold = household.value;
		DWRHouseholdService.getHousehold(valHousehold,postDetails);
	}
	
	function postDetails(householdMem){
		//alert("PASSED");
		var val2 = document.getElementById("householdDiv");
		val2.style.display = '';
		//alert(householdMem);
		var strHou = new Array();
		strHou = householdMem.split(",");
		
		document.getElementById("headOfHousehold").value = strHou[0];
		document.getElementById("telNoOfHead").value = strHou[1];
	}
	
	function validateInput(){
		var errorDivElement = document.getElementById("errorDiv");
		var revisitItems = document.getElementById("revisitItems").value;
		var returnVisitDate = document.getElementById("returnVisitDate").value;
		//var latrineType = document.getElementById("latrineType").value;
		var waterTreatMethod = document.getElementById("waterTreatMethod").value;
		var waterSources = document.getElementById("waterSources").value;
		var noOfBedNets = document.getElementById("noOfBedNets").value;
		var noOfSleepingSpaces = document.getElementById("noOfSleepingSpaces").value;
		var village = document.getElementById("village").value;
		var chwName = document.getElementById("chwID").value;
		var visitDate = document.getElementById("visitDate").value;
		var household = document.getElementById("household").value;
		var x="";
		
		if(revisitItems==""){
			document.getElementById("here11").style.display = '';
			x="has";
		}else
			document.getElementById("here11").style.display = 'none';
		/* if(returnVisitDate==""){
			document.getElementById("here10").style.display = '';
			x="has";
		}else
			document.getElementById("here10").style.display = 'none';
		if(latrineType==""){
			document.getElementById("here9").style.display = '';
			x="has";
		}else
			document.getElementById("here9").style.display = 'none'; */
		if(waterTreatMethod==""){
			document.getElementById("here8").style.display = '';
			x="has";
		}else
			document.getElementById("here8").style.display = 'none';
		if(waterSources==""){
			document.getElementById("here7").style.display = '';
			x="has";
		}else
			document.getElementById("here7").style.display = 'none';
		if(noOfBedNets==""){
			document.getElementById("here6").style.display = '';
			x="has";
		}else
			document.getElementById("here6").style.display = 'none';
		if(noOfSleepingSpaces==""){
			document.getElementById("here5").style.display = '';
			x="has";
		}else
			document.getElementById("here5").style.display = 'none';
		if(village==""){
			document.getElementById("here4").style.display = '';
			x="has";
		}else
			document.getElementById("here4").style.display = 'none';
		if(chwName==""){
			document.getElementById("here3").style.display = '';
			x="has";
		}else
			document.getElementById("here3").style.display = 'none';
		if(visitDate==""){
			document.getElementById("here2").style.display = '';
			x="has";
		}else
			document.getElementById("here2").style.display = 'none';
		if(household==""){
			document.getElementById("here1").style.display = '';
			x="has";
		}else
			document.getElementById("here1").style.display = 'none';
		if (x == "" ) {
			return true;
		}else{
			errorDivElement.style.display = '';
			return false;
		}
	}
	//select a person
	function selectedChwProvider(userId, provider){
		if(provider !=null){
			var providerId=provider.personId
			var ident = provider.systemId
		}
		$j("#chwID").val(providerId);
		$j("#ident").val(ident);
	}
</script>



<%@ include file="localHeader.jsp"%>
<openmrs:globalProperty key="household.enctype" var="encounterType" />
<div id="dialog" title="Saved Information"/>
<div class="boxHeader"><spring:message code="household.householdCHWInitial.header"/></div>
<div class="box">
	<div class="error" id="errorDiv" style="display: none">
		<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" />Check all marked with *
	</div>
	
	<form method="post" name="chwForm" onsubmit="return validateInput()">
	<%-- <input type="hidden" name="enctype" id="enctype" value="${encounterType}" /> --%>
	<table border="0">
	<tbody>
		<tr>
			<td valign="top">
				<table border="0">
					<tr>
						<td><b>Household ID : </b></td>
						<td><input type="text" name="household" id="household" /></td>
						<div id="here1" class="error" style="display: none" >*</div>
					
						
						<td><input type="button" name="getHouse" value="Load" onclick="getHouseH(household)"/></td>
					</tr>	
				</table>	
				<div id="householdDiv" style="display: none" >
				   
					Head of Household  :
					 <input type="text" readonly="readonly" id="headOfHousehold" />
					
					<br />
					Contacts [Tel. No.]:
					 <input type="text" readonly="readonly" id="telNoOfHead" />
				
				</div>
					
				
			</td>
			<td bgcolor="#cecece" valign="top">
				<table border="0">
					<tr>
				     <td><b>Visit Date:</b></td>
				     <td> <input type="text" name="visitDate" id="visitDate" onClick="showCalendar(this)"/></td><br />
					</tr>
					 <tr>
					 	<td colspan="2">
					 <div id="here2" class="error" style="display: none" >*</div>
					 	</td>
					 </tr>
					 <tr>  
						<td>
									
							<b>Name of CHW:</b>
						</td>
						<td>
							<openmrs_tag:userField formFieldName="providerId"   roles="Trusted+External+Application,Lab+Technician,Community+Health+Worker+Nutritionist,Clinician,Nurse,Psychosocial+Support+Staff,Pharmacist,HCT+Nurse,Outreach+Worker,Community+Health+Extension+Worker,Clinical+Officer,Provider" callback="selectedChwProvider" />
						</td>
						
					</tr>
					<tr> 
					  <td><b>Provider ID for CHW:</b></td>
					  <td><input type="text" name="ident" id="ident" readonly="readonly" />
					  	<input type="hidden" name="chwID" id="chwID" /></td>
					</tr>
					
					<tr>
						<td colspan="2">
						<div id="here3" class="error" style="display: none" >*</div>
						</td>
					</tr>	
				</table>
			</td>
		</tr>
		<tr>
			<td  bgcolor="#cecece">
				<b>Location :</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="location" onchange="getSubLoci(location)" onclick="getSubLoci(location)">
						<c:forEach var="loca" items="${loci}" varStatus="ind">
							<option id="${ind.index + 1 }" value="${loca}">${loca}</option>
						</c:forEach>
					</select>
					<input type="hidden" on/>
					<!--input type="submit" name="getSubLoci" value="Get Sub-locations" /-->
				<br />
				<b>Sub-Location :</b>
				<select name="subLocation" onchange="getVilla(subLocation,location)" onclick="getVilla(subLocation,location)"></select><br />
				<b>Village :</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<select name="village" id="village"></select><div id="here4" class="error" style="display: none" >*</div>
			</td>
			<td rowspan="2">
				<b>Number of sleeping spaces:</b><input type="text" name="noOfSleepingSpaces" id="noOfSleepingSpaces" /> <div id="here5" class="error" style="display: none" >*</div><br />
				<b>Number of bed nets:</b><input type="text" name="noOfBedNets" id="noOfBedNets" /> <div id="here6" class="error" style="display: none" >*</div><br />
				<b>Bed nets Observed? :</b> 
					<input type="radio" name="bednetsObserved" title="Yes" value="1065"/>Yes
					<input type="radio" name="bednetsObserved" title="No" value="1066"/>No<br />
				<b>General Condition of bed nets:</b><br />
					<input type="radio" name="bednetsCondition" title="Good" value="6343"/>Good
					<input type="radio" name="bednetsCondition" title="Few Holes" value="6868"/>Few Holes
					<input type="radio" name="bednetsCondition" title="Poor" value="6656"/>Poor<br />
				<b>Bed nets Education? :</b> 
					<input type="radio" name="bednetsEducation" title="Yes" value="1065"/>Yes
					<input type="radio" name="bednetsEducation" title="No" value="1066"/>No
			</td>
		</tr>
		<tr>
			<td>
				<b>Stable food available?</b><br />
					<input type="radio" name="stableFoodAvailable" title="Yes" value="1065"/>Yes
					<input type="radio" name="stableFoodAvailable" title="No" value="1066"/>No
			</td>
		</tr>
		<tr>
			<td>
				<b>Water Sources :</b><br />
					<select name="waterSources" id="waterSources" multiple="multiple" size="6">
						<option value="6859">Piped</option>
						<option value="6858">River</option>
						<option value="6856">Bore hole</option>
						<option value="6384">Rain Water</option>
						<option value="6857">Well</option>
						<option value="5622">Other</option>
					</select><div id="here7" class="error" style="display: none" >*</div><br />
				<b>Do you treat water?</b><br />
					<input type="radio" name="treatWater" title="Yes" value="1065"/>Yes
					<input type="radio" name="treatWater" title="No" value="1066"/>No<br />
				<b>Water treatment method :</b><br />
					<select name="waterTreatMethod" id="waterTreatMethod" multiple="multiple" size="5">
						<option value="1107">None</option>
						<option value="6861">Boil</option>
						<option value="6862">Chlorinate</option>
						<option value="6863">Filter</option>
						<option value="5622">other</option>
					</select><div id="here8" class="error" style="display: none" >*</div>
			</td>
			<td bgcolor="#cecece">
				<b>Do you have latrine?</b><br />
					<input type="radio" name="hasLatrine" title="Yes" value="1065"/>Yes
					<input type="radio" name="hasLatrine" title="No" value="1066"/>No<br />
				<b>Type of latrine :</b><br />
					<select name="latrineType" id="latrineType" multiple="multiple" size="4">
						<option value="6387">Flush</option>
						<option value="6388">Pit</option>
						<option value="6873">Pit with slab</option>
						<option value="6389">VIP</option>
					</select><div id="here9" class="error" style="display: none" >*</div><br />
				<b>Shared?</b><br />
					<input type="radio" name="sharedLatrine" title="Yes" value="1065"/>Yes
					<input type="radio" name="sharedLatrine" title="No" value="1066"/>No<br />
				<b>Hand washing facilities?</b><br />
					<input type="radio" name="handWashFacility" title="Yes" value="1065"/>Yes
					<input type="radio" name="handWashFacility" title="No" value="1066"/>No<br />
				<b>Safe water education?</b><br />
					<input type="radio" name="waterEducation" title="Yes" value="1065"/>Yes
					<input type="radio" name="waterEducation" title="No" value="1066"/>No<br />
			</td>
		</tr>
		<tr>
			<td bgcolor="#cecece">
				<b>Return Visit Date :</b><input type="text" name="returnVisitDate" id="returnVisitDate" onClick="showCalendar(this)" /><div id="here10" class="error" style="display: none" >*</div><br />
				<b>Return Visit Items :</b><br />
				<select name="revisitItems" id="revisitItems" multiple="multiple" size="7">
					<option value="6882">Deliver AMRS ID cards</option>
					<option value="6883">Follow up pregnant woman</option>
					<option value="6884">Follow up post-partum</option>
					<option value="6885">Follow up infant</option>
					<option value="6886">Follow up immunization defaulter</option>
					<option value="6887">Follow up malnourished child</option>
					<option value="6888">Return for health education</option>	
					<option value="5622">Return for Other</option>
				</select><div id="here11" class="error" style="display: none" >*</div>
			</td>
			<td align="center">
				<br />
				<input type="submit" value="Submit" name="filled" id="filled1" />
			</td>
		</tr>
		</tbody>
	</table>
	</form>
</div>
<%@ include file="/WEB-INF/template/footer.jsp"%>