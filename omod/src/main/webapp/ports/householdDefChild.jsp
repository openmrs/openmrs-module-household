<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/openmrsSearch.js" />
<link href="${pageContext.request.contextPath}/moduleResources/household/css/css-table.css" type="text/css" rel="stylesheet" /> 

<script type="text/javascript">
	$j(document).ready(function() {
		$j("#div1").show();$j("#div2").hide();$j("#div3").hide();$j("#div4").hide();$j("#div5").hide();
		$j("#div6").hide();$j("#div7").hide();$j("#div8").hide();$j("#div9").hide();
	});

	function highlightError(){
		var errorDivElement = document.getElementById("errorDiv");
		errorDivElement.style.display = 'none';
	}
	function highlightSuccess(){
		var highlightDivElement = document.getElementById("highlightDiv");
		highlightDivElement.style.display = 'none';
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
	fieldset {
		border: 1px solid #c9c9c9;
		-webkit-border-radius: 7px;
		-moz-border-radius: 7px;
		padding: 0px;
		margin-bottom: 1px;
		margin-top: 1px;
		/** margin-left: auto; **/
		margin-right: 0px;
		background-color: #c9c9c9;
	}
	#errorDiv, #highlightDiv{
		height: 30px;
		top: 0px;
		z-index: 100;
		position: fixed ;
		width: 100%;
		font-size: 25px;
		margin-left: -10px;
		text-align: center;
		font-style: italic;
		opacity:0.8;
  		filter:alpha(opacity=80);
	}
	#highlightDiv{
		background-color: #FFFFDD;
	}
	#errorDiv{
		background-color: #FF9988;
	}
	.successfullyDone{
		background-color:#FFFF40;
		color:#000000;
	
	}
</style>

<div id="errorDiv" style="display: none;">
	<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" />Check all marked with *
</div>
<div id="highlightDiv" style="display: none;" align="center">
	Household Saved successfully.
</div>
<table width="100%">
	<tr>
            <td width="25%" valign="top" class="householdbgLight"> 
			<div>
					<div style="width: 100%;">
                                            <table>
                                                <tr>
                                                    <td style="width: fit-content;vertical-align: top; text-align: left;">
                                                        <h2>
                                                            ${hdef.householdDefinitionsCode}: 
                                                        </h2>
                                                        
                                                    </td>
                                                    <td style="width: fit-content;vertical-align: top; text-align: left;">
                                                        <h2>
                                                            ${hdef.householdDefinitionsCodeinfull}
                                                        </h2>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td colspan="2">Description: <i>${hdef.householdDefinitionsDescription}</i></td>
                                                </tr>
                                            </table>
                                            
                                            <div class="horizontalLine"></div>
                                            <b>Summary : </b>
                                            <br />
                                            <table>
                                                    <tr><td>Number of Households</td><td>:<a href="#" onclick="javascript:menuNav(8, ${hdef.id})">${definitionSize}</a></td></tr>
                                                    <!--tr><td>Females in the definition</td><td>:<a href="#">2</a></td></tr>
                                                    <tr><td>Males in the definition</td><td>:<a href="#">2</a></td></tr-->
                                            </table>
					</div>
                                            
					<button class="minified" onclick="javascript:openClose('1')">Home</button>
                                        <button class="minified" onclick="javascript:openClose2('9','${hdef.id}')">New Household</button>
                                        <button class="minified" onclick="javascript:openCloseMain('1')">Find Household</button>
                                        <button class="minified" onclick="javascript:menuNav(8, '${hdef.id}')">View All Households</button>
			</div>
		</td><td width="2%"><div class="rpt"></div></td>
		<td width="73%" valign="top" class="householdbgLight"> 
				
			<div id="divAll">
			
				<div id="div1">
						
						
				</div>
			</div>
		</td>
	</tr>

</table>