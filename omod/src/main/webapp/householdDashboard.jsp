<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:require privilege="Manage Household" otherwise="/login.htm"
	redirect="/module/household/householdDashboard.form" />
<%@ include file="/WEB-INF/template/header.jsp"%>

<h3><spring:message code="household.title"/></h3>
<%@ include file="localHeader.jsp"%>



<openmrs:htmlInclude file="/dwr/util.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/hor_tab.css" type="text/css" rel="stylesheet" />
<openmrs:htmlInclude file="/moduleResources/household/scripts/prototype.lite.js"/>
<!-- <openmrs:htmlInclude file="/moduleResources/household/scripts/moo.fx.js"/>
<openmrs:htmlInclude file="/moduleResources/household/scripts/moo.fx.pack.js"/> -->
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
	var definition;
	function menuNav(val, def){
            if(val == 8){
                $j('#divAll').html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/><br/>Loading...');
                DWRHouseholdService.getHouseholds(def,returnHouseholdList);
            }
        }
        function returnHouseholdList(data){
            var dat = data.split("*");
            var houses = dat[1];
            var voided = dat[2];
            var retired = dat[3];
            var holds = dat[0].split("|");
            var strRet = "<span style='float: right;'><table><tr><td>Closed/Voided Households: </td><td>"+voided+
                "</td></tr><tr><td>Retired Households: </td><td>"+retired+
                "</td></tr></table></span><h2>Total Households: "+ houses +
                "</h2><br/><br/><div class='horizontalLine'></div><br/> <table><tr>";
            for(i=0; i<holds.length; i++){
                var hold = holds[i].split(",");
                if((i%5 == 0) && (i != 0)){
                    strRet += "<td><a href='#' onclick='forwardMem("+hold[1]+")'>" + hold[0] + "</a><td></tr><tr>";
                }else{
                    strRet += "<td><a href='#' onclick='forwardMem("+hold[1]+")'>" + hold[0] + "</a><td>";
                }
            }
            strRet += "</tr></table>";
            $j('#divAll').html(strRet);
        }
        function openClose(val){
		var i=0;
		for(i=0; i<10; i++){
			if(i != val){
				$j('#div' + i).hide();
			}else{
				$j('#div' + val).show();
				if(val == 9){
					alert(${hdef});
					$j.get("ports/householdReg.form",
							function(data){
								$j('#divAll').html(data);
							}
						);
				}
			}
		}
	}
	function openClose2(val, def){
		var i=0;
		for(i=0; i<10; i++){
			if(i != val){
				$j('#div' + i).hide();
			}else{
				$j('#div' + val).show();
				if(val == 9){
					$j.get("ports/householdReg.form?hdef=" + def,
							function(data){
								$j('#divAll').html(data);
							}
						);
				}
			}
		}
	}
	function openCloseMain(val){
            if(val == 1){
                $j.get("ports/searchHousehold.form",
                                function(data){
                                        $j('#divAll').html(data);
                                }
                        );
            }
	}
        function forwardMem(stdin){
		$j('#divAll').html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/loading.gif"/><br/>Loading...');
                $j.get("ports/householdActivity.form?householdID="+stdin+"&program="+definition,
                                function(data){
                                        $j('#divAll').html(data);
                                }
                        );
	}
        /**************Maps the buttons for links that involve editting of the household******************/
        function funEditActivity(passed, val){
            $j.get("ports/editHousehold.form?householdID="+ val + "&opt=" + passed,
                    function(data){
                            $j('#editActivity').html(data);
                    }
            );
            $j("#editActivity").show();
            $j("#lnkfun").show();
            
        }
        function funHideEditActivity(){
            $j("#editActivity").hide();
            $j("#lnkfun").hide();
        }
    /*******************************/
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
                            definition = stdin;
				$j("#divchild").html(data);
			}
		);
	}
	/*************************************/
        function unRetirePerson(){
            
        }
</script>


<div id="wrapper" class="householdbg">
    <div style="padding: 3px;">
            <div id="divchild">
                    <!--div class="boxHeader">Choose Program Definition:</div-->
                    <div>
                    <c:if test="${par == '1'}">
                            Department: <select name="parent" id="parent" onchange="javascript:fnChangedParentDefinition()">
                                                                    <option id="m" value="" selected="selected"></option>
                                                                    <c:forEach var="hh" items="${definitionParents}" varStatus="ind">
                                                                            <option id="${ind.index + 1 }" value="${hh.id}" >${hh.parentCode} - ${hh.parentFullname}</option>
                                                                    </c:forEach>
                                                            </select>
                            <br /><br />
                            <table id="houseDefs"  class="lineTable" cellpadding="3">
                                <thead>    
                                   <tr>
                                       <th scope="col" rowspan="2">&nbsp;</th>
                                       <th scope="col" colspan="4">Programs</th>
                                   </tr>
                                   <tr>
                                       <th scope="col">Code</th>
                                       <th scope="col">Program</th>
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