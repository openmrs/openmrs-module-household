<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/householdDashboard.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<%-- <table width="100%">
	<tr>
		<td align="left"><b><spring:message code="household.title"/></b></td>
		<td align="right"><%@ include file="localHeader.jsp" %></td>
	</tr>
</table> --%>
<h3><spring:message code="household.title"/></h3>
<%@ include file="localHeader.jsp"%>



<openmrs:htmlInclude file="/dwr/util.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/hor_tab.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/moduleResources/household/scripts/prototype.lite.js"/>
<openmrs:htmlInclude file="/moduleResources/household/scripts/moo.fx.js"/>
<openmrs:htmlInclude file="/moduleResources/household/scripts/moo.fx.pack.js"/>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/css-table.css" type="text/css" rel="stylesheet" /> 

<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/moduleResources/household/scripts/style-table.js"/>--%>

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
	function fnChangedParentDefinition(){
		var val = document.getElementById("parent").value;
		DWRHouseholdService.getParentHouseholdDefinitions(val, fnRetChildDefinition);
	}
	function fnRetChildDefinition(data){
		dwr.util.removeAllRows("tblPush");
		var count = 1;
		dwr.util.addRows("tblPush", data,[
		function(householdDefinition) { return count++; },
		function(householdDefinition) { return "<a href='#' onclick='javascript:forwardPass(" + householdDefinition.id +")'>" +householdDefinition.householdDefinitionsCode + "</a>";},
		function(householdDefinition) { return householdDefinition.householdDefinitionsCodeinfull; },
		function(householdDefinition) { return householdDefinition.householdDefinitionsDescription; }
		], { escapeHtml:false }); 
	}
	
	function forwardPass(stdin){
		$j.get("ports/householdDefChild.form?child=" + stdin,
			function(data){
				$j("#divchild").html(data);
			}
		);
	}
	
</script>


<div id="wrapper">
	<h3 class="tab" title="first"><div class="tabtxt"><a href="#">Program</a></div><div class="arrow"></div></h3>
	<div class="tab"><h3 class="tabtxt" title="second"><a href="#">Patient</a></h3><div class="arrow"></div></div>
	<div class="boxholder">
		<div class="boxi">
		<div>
			<div style="padding: 3px;">
				<div id="divchild" class="box" >
					<c:if test="${par == '1'}">
						Choose Program: <select name="parent" id="parent" onchange="javascript:fnChangedParentDefinition()">
											<option id="m" value="" selected="selected"></option>
											<c:forEach var="hh" items="${householdDefinition}" varStatus="ind">
												<option id="${ind.index + 1 }" value="${hh.id}" >${hh.householdDefinitionsCode}</option>
											</c:forEach>
										</select>
						<table id="houseDefs"  class="lineTable">
							<thead>    
							   	<tr>
						           <th scope="col" rowspan="2">&nbsp;</th>
						           <th scope="col" colspan="4">Definitions</th>
						       </tr>
						       <tr>
						           <th scope="col">Code</th>
						           <th scope="col">Definition</th>
						           <th scope="col">Description</th>
						       </tr>        
						   </thead>
						   <tbody id="tblPush">
						   
						    </tbody>
						</table>
						<br />
					</c:if>
				</div>
			</div>
			
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
