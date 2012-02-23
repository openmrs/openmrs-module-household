<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/householdDashboard.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<table width="100%">
	<tr>
		<td align="left"><b><spring:message code="household.title"/></b></td>
		<td align="right"><%@ include file="localHeader.jsp" %></td>
	</tr>
</table>





<link href="${pageContext.request.contextPath}/moduleResources/household/css/hor_tab.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/moduleResources/household/scripts/prototype.lite.js"/>
<openmrs:htmlInclude file="/moduleResources/household/scripts/moo.fx.js"/>
<openmrs:htmlInclude file="/moduleResources/household/scripts/moo.fx.pack.js"/>
<openmrs:htmlInclude file="/moduleResources/household/scripts/jquery.ui.core.js"/>
<openmrs:htmlInclude file="/moduleResources/household/scripts/jquery.ui.widget.js"/>
<openmrs:htmlInclude file="/moduleResources/household/scripts/jquery.ui.accordion.js"/>

<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/style-table.js"/>--%>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/css-table.css" type="text/css" rel="stylesheet" /> 

<script type="text/javascript">
function init(){
	var stretchers = document.getElementsByClassName('boxi');
	var toggles = document.getElementsByClassName('tab');
	var myAccordion = new fx.Accordion(
		toggles, stretchers, {opacity: false, height: true, duration: 600}
	);
	//hash functions
	var found = false;
	toggles.each(function(h3, i){
		var div = Element.find(h3, 'nextSibling');
			if (window.location.href.indexOf(h3.title) > 0) {
				myAccordion.showThisHideOpen(div);
				found = true;
			}
		});
		if (!found) myAccordion.showThisHideOpen(stretchers[0]);
}
</script>

<style>
	legend {
		border: 1px solid #b9b7c0;
		padding: 2px 6px;
		font-size: small;
		font-weight: bold;
		color: #black;
		background-color: #DADCFF;
	}
	.corn{
		margin-left: 10px;
		margin-right: 10px;
	}
	fieldset {
		border: 2px solid #c9c9c9;
		-webkit-border-radius: 7px;
		-moz-border-radius: 7px;
		padding: 3px;
		margin-bottom: 5px;
		margin-top: 5px;
		/** margin-left: auto; **/
		margin-right: 20px;
	}
	/*vertical lines*/
	.rpt{
		background-image:url(../images/ui-separator.png) repeat-y;
	}
</style>
<script>
	$j(function() {
		$j( "#accordion" ).accordion();
	});
	$j(document).ready(function() {
		$j("#div1").show();$j("#div2").hide();$j("#div3").hide();$j("#div4").hide();$j("#div5").hide();
		$j("#div6").hide();$j("#div7").hide();$j("#div8").hide();$j("#div9").hide();
	});
	function openClose(val){
		var i=0;
		for(i=0; i<10; i++){
			if(i != val){
				$j('#div' + i).hide();
			}else{
				$j('#div' + val).show();
			}
		}
	}
</script>


<div id="wrapper">
	<h3 class="tab" title="first"><div class="tabtxt"><a href="#">Program</a></div><div class="arrow"></div></h3>
	<div class="tab"><h3 class="tabtxt" title="second"><a href="#">Patient</a></h3><div class="arrow"></div></div>
	<div class="boxholder">
		<div class="boxi">
			<div style="padding: 3px;">
			<c:if test="${par == '1'}">
				<form method="post" name="formHD">
					Choose Program: <select name="parent">
									<option id="m" value="" selected="selected"></option>
									<c:forEach var="hh" items="${householdDefinition}" varStatus="ind">
										<option id="${ind.index + 1 }" value="${hh.id}" >${hh.householdDefinitionsCode}</option>
									</c:forEach>
								</select>
							<input type="submit" value="Retrieve">
				</form>
				<br />
			</c:if>
			<c:if test="${par == '2'}">
				<table id="houseDefs"  class="lineTable">
					<thead>    
				    	<tr>
				            <th scope="col" rowspan="2">&nbsp;</th>
				            <th scope="col" colspan="4">Household Definitions for: ${pare.householdDefinitionsCode} </th>
				        </tr>
				        <tr>
				            <th scope="col">Definition</th>
				            <th scope="col">Description</th>
				            <th scope="col">Action</th>
				        </tr>        
				    </thead>
				    <tbody>
				    	<c:forEach var="household" items="${householdDefinition}" varStatus="ind">
							<form method="post" action="householdDashboard.form" name="${household.id}">
								<tr>
									<th>${ind.index + 1}<input type="hidden" name="child" value="${household.id}" /></th>
									<td class="highlight">${household.householdDefinitionsCode} - ${household.householdDefinitionsCodeinfull}</td>
									<td class="highlight">${household.householdDefinitionsDescription}<br />
										<openmrs:format user="${household.creator}"/> : <openmrs:formatDate date="${household.dateCreated}"/>
									</td>
									<td class="highlight"><input type="submit" value="Retrieve"></td>
								</tr>
							</form>
						</c:forEach>
				    </tbody>
				</table>
			</c:if>
			<c:if test="${par == '3'}">
				<table width="100%">
					<tr>
						<td width="25%" valign="top"> 
							<div id="accordion">
								<h3><a href="#">Details</a></h3>
								<div>
									<b>Definition</b>  : ${hdef.householdDefinitionsCode}<br />
									<b>In Full</b>     : ${hdef.householdDefinitionsCodeinfull} <br />
									<b>Description</b> : <i>${hdef.householdDefinitionsDescription}</i>
								</div>
								<h3><a href="#">Section 1</a></h3>
								<div>
									<a href="#" onclick="javascript:openClose('1')">In-patient</a><br />
									<a href="#" onclick="javascript:openClose('2')">Out-patient</a><br />
									<a href="#" onclick="javascript:openClose('3')">Wards</a>
									<a href="#" onclick="javascript:openClose('4')">In-patient</a><br />
									<a href="#" onclick="javascript:openClose('5')">Out-patient</a><br />
								</div>
								<h3><a href="#">Section 2</a></h3>
								<div>
									<a href="#" onclick="javascript:openClose('6')">Wards</a><br />
									<a href="#" onclick="javascript:openClose('7')">Wards</a>
								</div>
							</div>
						</td><td width="2%"><div class="rpt"></div></td>
						<td width="73%" valign="top"> 
							<a href="#" onclick="javascript:openClose('8')">View Households(20)</a> &nbsp;| 
							<a href="#" onclick="javascript:openClose('9')">Add Household</a>&nbsp;
							<img src="${pageContext.request.contextPath}/moduleResources/household/images/home.png" alt="Household"/>
							<div style="width: 100%; height: 1px; background-color: gray;"></div>
								<div id="div1">Selling maize 1</div>
								<div id="div2">Selling maize 2</div>
								<div id="div3">Selling maize 3</div>
								<div id="div4">Selling maize 4</div>
								<div id="div5">Selling maize 5</div>
								<div id="div6">Selling maize 6</div>
								<div id="div7">Selling maize 7</div>
								<div id="div8">Selling maize 8</div>
								<div id="div9">Selling maize 9</div>
							
						</td>
					</tr>
				
				</table>
			</c:if>
			
			</div>
			
			
		</div>
		<div class="boxi">
			<p><strong>The Patient Dashboard</strong><br />Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Ut molestie nunc eu turpis. Donec facilisis enim sed dui. Sed nunc. Cras eu arcu. Praesent vel augue vel dolor ultricies convallis. Nam consectetuer risus eu urna. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Nam suscipit. Duis quis lacus sed tellus auctor blandit. Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin eget massa in ante vehicula pharetra. Ut massa pede, ornare id, ultrices eget, porta et, metus.</p>
		</div>
	</div>
</div>
<script type="text/javascript">
	Element.cleanWhitespace('content');
	init();
</script>
