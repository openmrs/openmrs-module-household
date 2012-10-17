<%@ include file="/WEB-INF/template/include.jsp"%>
<%-- 
    Document   : editHousehold
    Created on : Oct 9, 2012, 10:59:48 AM
    Author     : Mwogi
--%>
<link href="${pageContext.request.contextPath}/moduleResources/household/css/css-table.css" type="text/css" rel="stylesheet" /> 
<openmrs:htmlInclude file="/scripts/jquery/dataTables/css/dataTables_jui.css"/>
<openmrs:htmlInclude file="/scripts/jquery/dataTables/js/jquery.dataTables.min.js"/>
<openmrs:htmlInclude file="/scripts/jquery-ui/js/openmrsSearch.js" />
<openmrs:htmlInclude file="/scripts/calendar/calendar.js" />
<openmrs:htmlInclude file="/dwr/util.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRHouseholdService.js"/>
<openmrs:htmlInclude file="/dwr/interface/DWRPatientService.js"/>

<style type="text/css">
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
    function returnedSavedHousehold(data){
        var arr = data.split(",");
        if(arr[0]){
            var highlightDivElement = document.getElementById("highlightDiv");
            $j(highlightDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" /> ' + arr[1]);
            highlightDivElement.style.display = 'inline';
            setTimeout("highlightSuccess()",5000);
        }else{
            var errorDivElement = document.getElementById("errorDiv");
            $j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /> ' + arr[1]);
            errorDivElement.style.display = 'inline';
            setTimeout("highlightError()",5000);
        }
        forwardMem(${household.id});
    }
</script>

<div style="border: 1px white dotted; border-radius: 4px;background-color: white; padding-left: 5px; padding-right: 5px; padding-bottom: 5px;">
    <div style="float: left;">
        <i>${act}</i>
    </div>
    <br />
    <br />
        <c:if test="${opts == 1}"><!--New member -->
            
            <script type="text/javascript">
                //var $j = jQuery.noConflict();
                var lastSearch;
                $j(document).ready(function() {
                        new OpenmrsSearch("findPatients", false, doPatientSearch, doSelectHandler, 
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
                    if(inputs[0]){
                        inputs[0].focus();
                    }
                    householdMembersTable = $j('#tblSelectedPerson').dataTable({
                        "sDom": 'T<"clear">lfrtip',
                        "bPaginate": false,
                        "bLengthChange": false,
                        "bFilter": false,
                        "bSort": false,
                        "bInfo": false,
                        "bAutoWidth": false
                        });


                });

                function doSelectHandler(index, data) {
                        var selectedMembers = dwr.util.getValue("hiddenSelectedMembers");
                        if (selectedMembers == ""){
                                selectedMembers = data.patientId;
                                dwr.util.setValue("hiddenSelectedMembers", selectedMembers);
                        }else{
                                selectedMembers +=  "," + data.patientId;
                                dwr.util.setValue("hiddenSelectedMembers", selectedMembers);
                        }
                        householdMembersTable.fnAddData([data.givenName + " " + data.middleName +" " + data.familyName, data.identifier, data.birthdateString + "(" + data.age + ")", data.gender,"<input type='button' value='x' onclick='javascript:deleteClicked(" + data.patientId + ")' />"]); 
                }

                //searchHandler for the Search widget
                function doPatientSearch(text, resultHandler, getMatchCount, opts) {
                        lastSearch = text;
                        DWRPatientService.findCountAndPatients(text, opts.start, opts.length, getMatchCount, resultHandler);
                }
                function selectHouseholdProvider(userid,provider){
                        var proHouseholdId = null;
                        if(provider != null){
                                proHouseholdId=provider.personId;
                        }
                        $j("#userHouseholdProvider").val(proHouseholdId);
                }
                
                function deleteClicked(id){
                    //using the personid search through hiddenSelectedMembers
                    // obtain the index. This index forms the row index to execute householdMembersTable.fnDeleteRow(intPlace, null, false);
                    var strPersonId="";
                    var noSeleted = [];
                    var intPlace= -1;
                    var oldPersonID= document.getElementById("hiddenSelectedMembers").value;
                    if (oldPersonID.indexOf(",", 0) < 0)
                            noSeleted = [oldPersonID];
                    else{
                            noSeleted = oldPersonID.split(",");
                    }
                    for(var x=0; x<(noSeleted.length); x++){
                        if(noSeleted[x]==id){
                            intPlace= x;
                            householdMembersTable.fnDeleteRow(intPlace);
                            if(noSeleted.length == 1)
                                    householdMembersTable.fnClearTable();
                        }else{
                            if(strPersonId=="")
                                strPersonId = noSeleted[x];
                            else
                                strPersonId += "," + noSeleted[x];
                        }
                    }
                    $j("#hiddenSelectedMembers").val("");
                    $j("#hiddenSelectedMembers").val(strPersonId);
                    householdMembersTable.fnDraw();
                }
                
                function addMembers(){
                    var errorDivElement = document.getElementById("errorDiv");
                    var personList = document.getElementById("hiddenSelectedMembers").value;
                    var startDateProvided = document.getElementById("startDate").value;
                    var provider = document.getElementById("userHouseholdProvider").value;
                    var houseid = ${household.id};
                    var strArr = [personList, houseid, provider, startDateProvided];

                    if((personList != "")&&(provider != "")&&(startDateProvided != "")){
                            DWRHouseholdService.saveMembersToHseHold(strArr, returnedSavedHousehold);
                    }else{
                            $j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /> Please select registration date, provider and the members to add to the household.');
                            errorDivElement.style.display = 'inline';
                            setTimeout("highlightError()",5000);
                    }
                }
            </script>
            <div>
                <div class="titlebar">Choose member(s) to add to the <span style="color: gray;">Household : </span> ${household.householdIdentifier} </div>
                <div>
                        <div class="searchWidgetContainer" id="findPatients"></div>
                </div>
                <br />
                <div class="titlebar">New Member(s) to Add:</div>
                <div>
                        <div id="divSelectedPerson">
                        <input type="hidden" id="hiddenSelectedMembers" name="hiddenSelectedMembers"/>
                        <input type="hidden" id="hiddenIndex" name="hiddenIndex"/>
                                <table class="lineTable" id="tblSelectedPerson" width="100%">
                                        <thead>
                                                <tr>
                                                        <th>Name:</th>
                                                        <th>Identifier:</th>
                                                        <th>Age:</th>
                                                        <th>Gender:</th>
                                                        <th>&nbsp;</th>
                                                </tr>
                                        </thead>
                                        <tbody id="bdySelectedPerson">

                                        </tbody>
                                </table>
                                <div class="css_clear"></div>
                                <table>
                                        <tr>
                                                <td>Registration Date</td><td><input type="text" name="startDate" id="startDate" onClick="showCalendar(this)" /></td>
                                        </tr>
                                        <tr>
                                                <td>Provider Id:</td>
                                                <td><openmrs_tag:userField formFieldName="userProviders" roles="Trusted+External+Application,Lab+Technician,Community+Health+Worker+Nutritionist,Clinician,Nurse,Psychosocial+Support+Staff,Pharmacist,HCT+Nurse,Outreach+Worker,Community+Health+Extension+Worker,Clinical+Officer,Provider" callback="selectHouseholdProvider" />
                                                        <input type="hidden" id="userHouseholdProvider" size="10" />
                                                </td>
                                        </tr>
                                </table>
                                <br />
                                <button onclick="javascript:addMembers()" class="minimal">Save</button>
                        </div>
                </div>
            </div>
                             
        </c:if><!--/New member ->
        <c:if test="${opts == 2}"><!--Remove Member -->
            <div>
                <script type="text/javascript">
                    function selectThoseToVoid(id) {
                        var selectedMembers = dwr.util.getValue("hdnMembersToVoid");
                        if (selectedMembers == ""){
                                selectedMembers = id;
                                dwr.util.setValue("hdnMembersToVoid", selectedMembers);
                        }else{
                                var found = [];

                                if (selectedMembers.indexOf(",", 0) < 0){
                                        found = [selectedMembers];
                                }else{
                                        found = selectedMembers.split(",");
                                }
                                var newMem = "";
                                var got = false;
                                for(var x=0; x<(found.length); x++){
                                    if(found[x] != id){
                                        if (newMem == ""){
                                            newMem = found[x];
                                        }else{
                                            newMem +=  "," + found[x];
                                        }
                                    }else{
                                        got =true;
                                    }
                                }
                                if(got){
                                    dwr.util.setValue("hdnMembersToVoid", newMem);
                                }else{
                                    dwr.util.setValue("hdnMembersToVoid", newMem +=  "," + id);
                                }
                        }
                    }
                    function voidMembers() {
                        var errorDivElement = document.getElementById("errorDiv");
                        var voidableMembers = document.getElementById("hdnMembersToVoid").value;
                        var voidReason = document.getElementById("voidReason").value;
                        if ((voidReason == "")||(voidableMembers == "")) {
                                $j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /> Please provide a reason for removing the member(s).');
                                errorDivElement.style.display = 'inline';
                                setTimeout("highlightError()",5000);
                        }else{
                                DWRHouseholdService.voidMembers(voidableMembers,voidReason, returnedSavedHousehold );
                        }
                    }
                </script>
                <div>
                    <div class="titlebar">Registered/Existing Members:</div>
                    <table style="margin-left: 10px;" cellpadding="3">
                        <tr>
                            <td>&nbsp;</td><td>Name</td><td>Gender</td><td>Age</td><td>Joined</td><td>Remove</td>
                        </tr>
                        <c:forEach var="hhm" items="${memberList}" varStatus="ind">
                            <tr class="linetablerow">
                                <th>
                                    ${ind.index + 1 }
                                    <c:choose>
                                        <c:when test="${hhm.householdMembershipHeadship }">
                                            <img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" alt="[HEAD/INDEX]" />
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </th>
                                <td>
                                    <c:choose>
                                        <c:when test="${hhm.householdMembershipMember.dead }">
                                            <span style="color: red;">Dead!</span>
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty hhm.householdMembershipMember.givenName }">
                                            ${hhm.householdMembershipMember.givenName} &nbsp;
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty hhm.householdMembershipMember.middleName }">
                                            ${hhm.householdMembershipMember.middleName} &nbsp;
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty hhm.householdMembershipMember.familyName }">
                                            ${hhm.householdMembershipMember.familyName} &nbsp;
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                 </td>
                                <td>[${hhm.householdMembershipMember.gender}]</td>
                                <td>${hhm.householdMembershipMember.age} yr(s)</td>
                                <td><openmrs:formatDate date="${hhm.startDate}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${hhm.householdMembershipHeadship }"></c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="groupVoid" onclick="javascript:selectThoseToVoid(${hhm.id})">
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Removal reason: </td>
                                <td colspan="4">
                                        <textarea rows="3" cols="60" name="voidReason" id="voidReason"></textarea>
                                </td>
                            </tr>
                    </table>
                    <input type="hidden" value="" name="hdnMembersToVoid" id="hdnMembersToVoid">
                    <br />
                    <button class="minimal" onclick="javascript:voidMembers()">Remove Member(s)</button>

                </div>

            </div>
        </c:if><!--/Remove Member ->
        <c:if test="${opts == 3}"><!--Retire Household -->
            <div>
                <script type="text/javascript">
                    function retireHouse() {
                        var errorDivElement = document.getElementById("errorDiv");
                        var retireReason = document.getElementById("retireReasonHousehold").value;
                        var dateRetired = document.getElementById("retireDateHousehold").value;
                        var house = ${household.id};
                        if (retireReason == "") {
                                $j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /> Please provide a reason for retiring the household.');
                                errorDivElement.style.display = 'inline';
                                setTimeout("highlightError()",5000);
                        }else{
                                DWRHouseholdService.retireHousehold(house,dateRetired, retireReason, returnedSavedHousehold );
                        }
                    }
                </script>

                <table id="tableretirehousehold" name="tableretirehousehold"
                    <tr>
                        <td>Retire reason: </td>
                        <td>
                                <textarea rows="3" cols="60" name="retireReasonHousehold" id="retireReasonHousehold"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td>Retire date: </td>
                        <td>
                              <input type="text" name="retireDateHousehold" id="retireDateHousehold" onClick="showCalendar(this)" />  
                        </td>
                    </tr>
                </table>
                <br />
                <button class="minimal" onclick="javascript:retireHouse()">Retire Household</button>
            </div>
        </c:if><!--/Retire Household ->
        <c:if test="${opts == 4}"><!--Retire Members -->
            <div>
                <script type="text/javascript">
                    function selectThoseToRetire(id) {
                        var selectedMembers = dwr.util.getValue("hdnMembersToRetire");
                        if (selectedMembers == ""){
                                selectedMembers = id;
                                dwr.util.setValue("hdnMembersToRetire", selectedMembers);
                        }else{
                                var found = [];

                                if (selectedMembers.indexOf(",", 0) < 0){
                                        found = [selectedMembers];
                                }else{
                                        found = selectedMembers.split(",");
                                }
                                var newMem = "";
                                var got = false;
                                for(var x=0; x<(found.length); x++){
                                    if(found[x] != id){
                                        if (newMem == ""){
                                            newMem = found[x];
                                        }else{
                                            newMem +=  "," + found[x];
                                        }
                                    }else{
                                        got =true;
                                    }
                                }
                                if(got){
                                    dwr.util.setValue("hdnMembersToRetire", newMem);
                                }else{
                                    dwr.util.setValue("hdnMembersToRetire", newMem +=  "," + id);
                                }
                        }
                    }
                    function retireMembers() {
                        var errorDivElement = document.getElementById("errorDiv");
                        var retirableMembers = document.getElementById("hdnMembersToRetire").value;
                        var retireReason = document.getElementById("retireReason").value;
                        var dateRetired = document.getElementById("retireDate").value;
                        if ((retireReason == "")||(retirableMembers == "")) {
                                $j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /> Please select member(s) and provide a reason for retiring the member(s).');
                                errorDivElement.style.display = 'inline';
                                setTimeout("highlightError()",5000);
                        }else{
                                DWRHouseholdService.retireMembers(retirableMembers,dateRetired, retireReason, returnedSavedHousehold );
                        }
                    }
                </script>
            
                <div class="titlebar">Registered/Existing Members:</div>
                <table style="margin-left: 10px;" cellpadding="3">
                    <tr>
                        <td>&nbsp;</td><td>Name</td><td>Gender</td><td>Age</td><td>Joined</td><td>Retire</td>
                    </tr>
                    <c:forEach var="hhm" items="${memberList}" varStatus="ind">
                        <tr class="linetablerow">
                            <th>
                                ${ind.index + 1 }
                                <c:choose>
                                    <c:when test="${hhm.householdMembershipHeadship }">
                                        <img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" alt="[HEAD/INDEX]" />
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </th>
                            <td>
                                <c:choose>
                                    <c:when test="${hhm.householdMembershipMember.dead }">
                                        <span style="color: red;">Dead!</span>
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty hhm.householdMembershipMember.givenName }">
                                        ${hhm.householdMembershipMember.givenName} &nbsp;
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty hhm.householdMembershipMember.middleName }">
                                        ${hhm.householdMembershipMember.middleName} &nbsp;
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty hhm.householdMembershipMember.familyName }">
                                        ${hhm.householdMembershipMember.familyName} &nbsp;
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                             </td>
                            <td>[${hhm.householdMembershipMember.gender}]</td>
                            <td>${hhm.householdMembershipMember.age} yr(s)</td>
                            <td><openmrs:formatDate date="${hhm.startDate}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${hhm.householdMembershipHeadship }"></c:when>
                                    <c:otherwise>
                                        <input type="checkbox" name="groupRetire" onclick="javascript:selectThoseToRetire(${hhm.id})">
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                        <tr>
                            <td>&nbsp;</td>
                            <td>Retire reason: </td>
                            <td colspan="4">
                                    <textarea rows="3" cols="60" name="retireReason" id="retireReason"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                            <td>Retire date: </td>
                            <td colspan="4">
                                  <input type="text" name="retireDate" id="retireDate" onClick="showCalendar(this)" />  
                            </td>
                        </tr>
                </table>
                <input type="hidden" value="" name="hdnMembersToRetire" id="hdnMembersToRetire">
                <br />
                <button class="minimal" onclick="javascript:retireMembers()">Retire Member(s)</button>

            </div>
        </c:if><!--/Retire Members ->
        <c:if test="${opts == 5}"><!--History -->
            <div class="titlebar">Timeline:</div>
                <table style="margin-left: 10px;" cellpadding="3">
                    <tr>
                        <td>&nbsp;</td><td>Name</td><td>Head</td><td>Joined</td><td>Left</td><td>Reason</td><!--td>Provider</td--><td>By</td>
                    </tr>
                    <c:forEach var="hhm" items="${memberHist}" varStatus="ind">
                        <tr  class="linetablerow">
                            <td>
                                ${ind.index + 1 }
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${hhm.householdMembershipMember.dead }">
                                        <span style="color: red;">Dead!</span>
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <a href="${pageContext.request.contextPath}/patientDashboard.form?patientId=${hhm.householdMembershipMember.id}" target="_blank">
                                    <c:choose>
                                        <c:when test="${not empty hhm.householdMembershipMember.givenName }">
                                            ${hhm.householdMembershipMember.givenName} &nbsp;
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty hhm.householdMembershipMember.middleName }">
                                            ${hhm.householdMembershipMember.middleName} &nbsp;
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty hhm.householdMembershipMember.familyName }">
                                            ${hhm.householdMembershipMember.familyName} &nbsp;
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                 </a>
                            </td>
                            <td>
                                <c:choose>
                                    <c:when test="${hhm.householdMembershipHeadship }">
                                        <img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" alt="[HEAD/INDEX]" />
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </td>
                            <td><openmrs:formatDate date="${hhm.startDate}"/></td>
                            <td><openmrs:formatDate date="${hhm.endDate}"/></td>
                            <td>
                                <c:if test="${not empty hhm.retireReason}">
                                    ${hhm.retireReason}
                                </c:if>
                                <c:if test="${not empty hhm.voidReason}">
                                    ${hhm.voidReason}
                                </c:if>
                            </td>
                            <!--td>{hhm.providerId}</td-->
                            <td>
                                <c:if test="${not empty hhm.retireReason}">
                                    <openmrs:format user="${hhm.changedBy}"/>
                                </c:if>
                                <c:if test="${not empty hhm.voidReason}">
                                    <openmrs:format user="${hhm.voidedBy}"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
        </c:if><!--/History -->
        <c:if test="${opts == 6}"><!--Change Index -->
            <div>
            <script type="text/javascript">
                function selectOneToMakeIndex(id) {
                    $j("#hdnNewindex").val(id);
                }
                function changeIndexMember() {
                    var errorDivElement = document.getElementById("errorDiv");
                    var newIndexMember = document.getElementById("hdnNewindex").value;
                    var changeReason = document.getElementById("changeReason").value;
                    var provider = document.getElementById("userProvider").value;
                    var holdHead = ${holdHead}
                    var arr = [newIndexMember,holdHead,changeReason,provider];
                    if ((changeReason == "")||(newIndexMember == "")) {
                            $j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /> Please select new index and provide a reason for changing the index member.');
                            errorDivElement.style.display = 'inline';
                            setTimeout("highlightError()",5000);
                    }else{
                            DWRHouseholdService.changeIndex(arr, returnedSavedHousehold );
                    }
                }
                function selectProvider(userid,provider){
                        var proHouseholdId = null;
                        if(provider != null){
                                proHouseholdId=provider.personId;
                        }
                        $j("#userProvider").val(proHouseholdId);
                }
            </script>
            <div>
                <div class="titlebar">Registered/Existing Members:</div>
                <table style="margin-left: 10px;" cellpadding="3">
                    <tr>
                        <td>&nbsp;</td><td>Name</td><td>Gender</td><td>Age</td><td>Joined</td><td>New Index</td>
                    </tr>
                    <c:forEach var="hhm" items="${memberList}" varStatus="ind">
                        <tr class="linetablerow">
                            <th>
                                ${ind.index + 1 }
                                <c:choose>
                                    <c:when test="${hhm.householdMembershipHeadship }">
                                        <img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" alt="[HEAD/INDEX]" />
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                            </th>
                            <td>
                                <c:choose>
                                    <c:when test="${hhm.householdMembershipMember.dead }">
                                        <span style="color: red;">Dead!</span>
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty hhm.householdMembershipMember.givenName }">
                                        ${hhm.householdMembershipMember.givenName} &nbsp;
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty hhm.householdMembershipMember.middleName }">
                                        ${hhm.householdMembershipMember.middleName} &nbsp;
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${not empty hhm.householdMembershipMember.familyName }">
                                        ${hhm.householdMembershipMember.familyName} &nbsp;
                                    </c:when>
                                    <c:otherwise></c:otherwise>
                                </c:choose>
                             </td>
                            <td>[${hhm.householdMembershipMember.gender}]</td>
                            <td>${hhm.householdMembershipMember.age} yr(s)</td>
                            <td><openmrs:formatDate date="${hhm.startDate}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${hhm.householdMembershipHeadship }"></c:when>
                                    <c:otherwise>
                                        <input type="radio" name="groupIndex" onclick="javascript:selectOneToMakeIndex(${hhm.id})" />
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                        <tr>
                            <td>&nbsp;</td>
                            <td>Reason for change: </td>
                            <td colspan="4">
                                <input type="hidden" id="hdnNewindex" />
                                    <textarea rows="3" cols="60" name="changeReason" id="changeReason"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td> 
                            <td>Provider :</td>
                            <td colspan="4"><openmrs_tag:userField formFieldName="userProviders" roles="Trusted+External+Application,Lab+Technician,Community+Health+Worker+Nutritionist,Clinician,Nurse,Psychosocial+Support+Staff,Pharmacist,HCT+Nurse,Outreach+Worker,Community+Health+Extension+Worker,Clinical+Officer,Provider" callback="selectProvider" />
                                    <input type="hidden" id="userProvider" size="10" />
                            </td>
                        </tr>
                </table>
                <br />
                <button class="minimal" onclick="javascript:changeIndexMember()">Change Index Member</button>

            </div>
            
            </div>
        </c:if><!--/Change Index -->
        <c:if test="${opts == 7}"><!--Add quasi Member(s) -->
            <div>
                <script type="text/javascript">
                    function selectThoseToQuasi(id) {
                        var selectedMembers = dwr.util.getValue("hdnMembersToQuasi");
                        if (selectedMembers == ""){
                                selectedMembers = id;
                                dwr.util.setValue("hdnMembersToQuasi", selectedMembers);
                        }else{
                                var found = [];

                                if (selectedMembers.indexOf(",", 0) < 0){
                                        found = [selectedMembers];
                                }else{
                                        found = selectedMembers.split(",");
                                }
                                var newMem = "";
                                var got = false;
                                for(var x=0; x<(found.length); x++){
                                    if(found[x] != id){
                                        if (newMem == ""){
                                            newMem = found[x];
                                        }else{
                                            newMem +=  "," + found[x];
                                        }
                                    }else{
                                        got =true;
                                    }
                                }
                                if(got){
                                    dwr.util.setValue("hdnMembersToQuasi", newMem);
                                }else{
                                    dwr.util.setValue("hdnMembersToQuasi", newMem +=  "," + id);
                                }
                        }
                    }
                    function quasiMembers() {
                        var errorDivElement = document.getElementById("errorDiv");
                        var quasiMembers = document.getElementById("hdnMembersToQuasi").value;
                        var quasiReason = document.getElementById("quasiReason").value;
                        var provider = document.getElementById("userQuasiProvider").value;
                        var q = [quasiMembers,quasiReason,provider];
                        if ((quasiReason == "")||(quasiMembers == "")) {
                                $j(errorDivElement).html('<img src="${pageContext.request.contextPath}/moduleResources/household/images/problem.gif" /> Please provide a reason for removing the member(s).');
                                errorDivElement.style.display = 'inline';
                                setTimeout("highlightError()",5000);
                        }else{
                                DWRHouseholdService.addQuasiMembers(q, returnedSavedHousehold );
                        }
                    }
                    function selectQuasiProvider(userid,provider){
                        var proHouseholdId = null;
                        if(provider != null){
                                proHouseholdId=provider.personId;
                        }
                        $j("#userQuasiProvider").val(proHouseholdId);
                    }
                </script>
                <div>
                    <div class="titlebar">Registered/Existing Members:</div>
                    <table style="margin-left: 10px;" cellpadding="3">
                        <tr>
                            <td>&nbsp;</td><td>Name</td><td>Gender</td><td>Age</td><td>Joined</td><td>Quasi</td>
                        </tr>
                        <c:forEach var="hhm" items="${memberList}" varStatus="ind">
                            <tr class="linetablerow">
                                <th>
                                    ${ind.index + 1 }
                                    <c:choose>
                                        <c:when test="${hhm.householdMembershipHeadship }">
                                            <img src="${pageContext.request.contextPath}/moduleResources/household/images/tick.png" alt="[HEAD/INDEX]" />
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </th>
                                <td>
                                    <c:choose>
                                        <c:when test="${hhm.householdMembershipMember.dead }">
                                            <span style="color: red;">Dead!</span>
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty hhm.householdMembershipMember.givenName }">
                                            ${hhm.householdMembershipMember.givenName} &nbsp;
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty hhm.householdMembershipMember.middleName }">
                                            ${hhm.householdMembershipMember.middleName} &nbsp;
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${not empty hhm.householdMembershipMember.familyName }">
                                            ${hhm.householdMembershipMember.familyName} &nbsp;
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                 </td>
                                <td>[${hhm.householdMembershipMember.gender}]</td>
                                <td>${hhm.householdMembershipMember.age} yr(s)</td>
                                <td><openmrs:formatDate date="${hhm.startDate}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${hhm.householdMembershipHeadship }"></c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${hhm.quasi }">Q</c:when>
                                                <c:otherwise>
                                                    <input type="checkbox" name="groupVoid" onclick="javascript:selectThoseToQuasi(${hhm.id})">
                                                    </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                            <tr>
                                <td>&nbsp;</td>
                                <td>Reason: </td>
                                <td colspan="4">
                                        <textarea rows="3" cols="60" name="quasiReason" id="quasiReason"></textarea>
                                </td>
                            </tr>
                            <tr>
                            <td>&nbsp;</td> 
                            <td>Provider :</td>
                            <td colspan="4"><openmrs_tag:userField formFieldName="userProviders" roles="Trusted+External+Application,Lab+Technician,Community+Health+Worker+Nutritionist,Clinician,Nurse,Psychosocial+Support+Staff,Pharmacist,HCT+Nurse,Outreach+Worker,Community+Health+Extension+Worker,Clinical+Officer,Provider" callback="selectQuasiProvider" />
                                    <input type="hidden" id="userQuasiProvider" size="10" />
                            </td>
                        </tr>
                    </table>
                    <input type="hidden" value="" name="hdnMembersToQuasi" id="hdnMembersToQuasi">
                    <br />
                    <button class="minimal" onclick="javascript:quasiMembers()">Quasi Member(s)</button>

                </div>

            </div>
        </c:if><!--/Add quasi Member(s) ->
</div>