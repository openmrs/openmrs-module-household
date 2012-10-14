<%-- 
    Document   : searchHousehold
    Created on : Oct 8, 2012, 6:13:54 PM
    Author     : Mwogi
--%>
<%@ include file="/WEB-INF/template/include.jsp"%>

<openmrs:htmlInclude file="/dwr/util.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRPatientService.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRPersonService.js" />
<openmrs:htmlInclude file="/dwr/interface/DWRUserService.js" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/openmrsSearch.js" />
<style>
	#openmrsSearchTable_wrapper{
	/* Removes the empty space between the widget and the Create New Patient section if the table is short */
	/* Over ride the value set by datatables */
		min-height: 0px; height: auto !important;
	}
	.dataTables_wrapper {
           clear: both;
           min-height: 0px;
           position: relative;
       }
</style>


<script type="text/javascript">
	var hID;
        var lastSearch;
	$j(document).ready(function() {
		$j("#tblSelectedPerson").hide();
		new OpenmrsSearch("findPatients", false, doPatientSearch, doSelectionHandler, 
			[	{fieldName:"identifier", header:omsgs.identifier},
				{fieldName:"givenName", header:omsgs.givenName},
				{fieldName:"middleName", header:omsgs.middleName},
				{fieldName:"familyName", header:omsgs.familyName},
				{fieldName:"age", header:omsgs.age},
				{fieldName:"gender", header:omsgs.gender},
				{fieldName:"birthdateString", header:omsgs.birthdate},
			],
			{
			  searchLabel: '<spring:message code="Patient.searchBox" javaScriptEscape="true"/>'
			  <c:if test="${not empty param.phrase}">
			      , searchPhrase: '<spring:message text="${ param.phrase }" javaScriptEscape="true"/>'
			  </c:if>
			});
		
		//set the focus to the first input box on the page(in this case the text box for the search widget)
		var inputs = document.getElementsByTagName("input");
                if(inputs[0])
                    inputs[0].focus();
	    		
                $j('#findMembers').click(function(event){				
                    var household=dwr.util.getValue("searchFieldHI");
                    if ((household!= "") && (household.length > 3)){
                        DWRHouseholdService.getHouseholdMems(household,fillTheMemberTable);
                    }else{
                            $j('#householdDetails').hide();
                    }
                });
                $j('#btnManageHHD').click(function(event){				
                    event.preventDefault();
                    forwardMem(hID);
                });
                toggleDivPerson();
	});

	function doSelectionHandler(index, data) {
		DWRHouseholdService.getHouseholdsByPersonID(data.patientId, getHouseholdMembership);
	}

	//searchHandler for the Search widget
	function doPatientSearch(text, resultHandler, getMatchCount, opts) {
		lastSearch = text;
		DWRPatientService.findCountAndPatients(text, opts.start, opts.length, getMatchCount, resultHandler);
	}
	
	function getHouseholdMembership(data){
		$j("#tblSelectedPerson").show();
		dwr.util.removeAllRows("bdySelectedPerson");
		var count = 1;
		dwr.util.addRows("bdySelectedPerson", data,[
		function(householdMembership) { return count++; },
		function(householdMembership) { return "<a href='#' onclick='javascript:forwardMem(" + householdMembership.householdMembershipGroups.id +")'>" +
			householdMembership.householdMembershipGroups.householdIdentifier + "</a>";},
		function(householdMembership) { return householdMembership.startDate; },
		function(householdMembership) { return householdMembership.endDate; }
		], { escapeHtml:false }); 
	}
        function toggleDivID(){
            var dp=document.getElementById("divPerson");
            var did=document.getElementById("divID");
            dp.style.display='none';
            did.style.display='inline';
            $j('#householdDetails').hide();
        }
        function toggleDivPerson(){
            var dp=document.getElementById("divPerson");
            var did=document.getElementById("divID");
            dp.style.display='inline';
            did.style.display='none';
        }
        //divID
        function fillTheMemberTable(data){
            //alert(data);
            var sep = data.split("*");
            //remove all the rows and
            //come up with an array of data
            var arrDataSplit=new Array();
            var arrData;
            if(sep.length > 2){
                arrData=sep[0].split("|");
                hID = sep[2];
            }else{
                arrData=[];
                var errorDivElement = document.getElementById("errorDiv");
                var household=dwr.util.getValue("searchFieldHI");
                $j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /> '+ household +'  household does not exist');
                errorDivElement.style.display = 'inline';
                setTimeout("highlightError()",5000);
            }
            dwr.util.removeAllRows("details");
            $j("#memTable").hide();
            $j('#householdDetails').hide();
            $j("#btnManageHHD").css("display:none;");
            //add the required rows 
            //alert("blaaaa"+data.givenName);
            var count=1;
            //alert(arrData); 

             for(var i=0;i<arrData.length;i++){
                arrDataSplit=arrData[i].split(",");

                dwr.util.addRows("details",[arrDataSplit],[
                        function() { return (count++);},
                        function() { return (arrDataSplit[0]) ; },
                        function() { return (arrDataSplit[1]) ; },
                        function() { return (arrDataSplit[2]) ; },
                        function() { return (arrDataSplit[3]) ; },
                        function() { return (arrDataSplit[4]) ; }
                    ], { escapeHtml:false }
                );
            } 
            if(arrData.length != 0){
                $j('#householdDetails').show();
                $j("#btnManageHHD").css("display:inline;");
                $j("#memTable").show();
            }
        }
</script>


    
    <div>
        <table style="width: 100%;">
            <tr>
                <td>
                    <div style="float: right;">
                        <button class="minimal" id="btnByPerson" onclick="javascript:toggleDivPerson();">Find by Person</button> 
                        <button class="minimal" id="btnByID" onclick="javascript:toggleDivID();">Find by Identifier</button>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class="horizontalLine"></div>
                </td>
            </tr>
            <tr><td>
                    <div id="divPerson">
                        <div class="titlebar">By person:</div>
                        <div class="searchWidgetContainer" id="findPatients"></div>
                        <br />
                        <div id="tblSelectedPerson">
                                <table class="lineTable">
                                        <thead>
                                                <tr>
                                                        <th>&nbsp;</th>
                                                        <th>Household Identifier:</th>
                                                        <th>Joined:</th>
                                                        <th>Left:</th>
                                                </tr>
                                        </thead>
                                        <tbody id="bdySelectedPerson">

                                        </tbody>
                                </table>
                        </div>
                    </div>
                    <div id="divID">
                        <div class="titlebar">Find by Identifier</div>
                        <div>
                            Identifier : &nbsp;<input type="text" id="searchFieldHI"/>
                            <button class="minimal" id="findMembers">Search</button>
                            <br/>
                            <div id="householdDetails">
                                <form method="post" name="formMembers" id="formMembers">
                                    <table border="0" cellpadding="3" cellspacing="0" id="memTable">
                                        <thead>
                                                <tr>
                                                        <th>No:</th>
                                                        <th>Names</th>
                                                        <th>Gender</th>
                                                        <th>Birth Date</th>
                                                        <th>Head/Index</th>
                                                        <th>Start Date</th>
                                                </tr>
                                        </thead>
                                        <tbody id="details">

                                        </tbody>
                                    </table>
                                </form>
                                <button class="minimal" id="btnManageHHD">Manage The Household</button>
                            </div>
                        </div>
                    </div>
                </td>
            </tr>
        </table>
    </div>
