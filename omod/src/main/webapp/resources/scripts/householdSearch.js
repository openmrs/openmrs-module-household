/*
 * Date: 12/19/2008
 * This script contains methods that are used to check matching patients
 */

var householdMembersTable;
var hhMem;

// Executes when page completes loading.
$j(document).ready(function() {
    householdMembersTable = $j("#householdMembers").dataTable({
    	"bAutoWidth": false,
    	"bLengthChange": true,
    	"bJQueryUI": true,
    	"sPaginationType": "full_numbers"
    	}
    );
    
    $j("#hhdLoc").dataTable({
    	"bAutoWidth": false,
    	"bLengthChange": true,
    	"bJQueryUI": true,
    	"sPaginationType": "full_numbers"
    	}
    );
    
    $j("#patientDetail").dataTable({
    	"bAutoWidth": false,
    	"bLengthChange": true,
    	"bJQueryUI": true,
    	"sPaginationType": "full_numbers"
    }
    );
    
    /*hhMem = $j("#hhMem").dataTable({
    	"bAutoWidth": false,
    	"bLengthChange": true,
    	//"sPaginationType": "full_numbers",
    	"bJQueryUI": false
    	}
    );*/
});

//Select a patient
function selectedPerson(fieldId, person){
	if (person != null)
		householdMembersTable.fnAddData([person.personId, person.personName, "<input type=\"radio\" name=\"indexperson\" onClick=\"javascript:headPerson(" +
		                                 person.personId + ")\">","<a href=\"javascript:removePerson(" + 
		                                 person.personId + ")\">x</a>"]);
	$j("#new_member_id_selection").focus();
	$j("#new_member_id_selection").val("");
	//TODO: Check duplicates
	var aData = householdMembersTable.fnGetData();
	var strPersonId="";
	for(var i=0; i<aData.length; i++){
		if (aData[i] instanceof Array){
			if(i==0)
				strPersonId = aData[i][0];// + "`" + aData[i][1] + "`" + aData[i][2] + "`" + aData[i][3]; 
			else
				strPersonId = strPersonId + "," + aData[i][0];// + "`" + aData[i][1] + "`" + aData[i][2] + "`" + aData[i][3];
		}
	}
	$j("#hiddenbox").val("");
	$j("#hiddenbox").val(strPersonId);
}

//Funtion to remove the selected row.
function removePerson(person){
	
	var aData = householdMembersTable.fnGetData();
	var strPersonId="";
	var strDelete = "";
	
	for(var i=0; i<aData.length; i++){
		if(aData[i][0]==person){
			if(strDelete == "")
				strDelete = i + ",";
			else
				strDelete = strDelete + i + ",";
		}else{
			if(i==0)
				strPersonId = aData[i][0];// + "`" + aData[i][1] + "`" + aData[i][2] + "`" + aData[i][3]; 
			else
				strPersonId = strPersonId + "," + aData[i][0];// + "`" + aData[i][1] + "`" + aData[i][2] + "`" + aData[i][3];
		}
	}
	var toDel = strDelete.split(",");
	for(var i=0; i<(toDel.length-1); i++){
		householdMembersTable.fnDeleteRow(toDel[i], null, false);
	}
	
	$j("#hiddenbox").val("");
	$j("#hiddenbox").val(strPersonId);
	householdMembersTable.fnDraw();
}

//Funtion to select head of household
function headPerson(person){
	$j("#hiddenIndex").val(person);
}
