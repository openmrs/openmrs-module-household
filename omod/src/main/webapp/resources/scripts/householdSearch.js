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
});

//Select a patient
function selectedPerson(fieldId, person){
	if (person != null)
		householdMembersTable.fnAddData([person.personName, "<input type=\"radio\" name=\"indexperson\" onClick=\"javascript:headPerson(" +
		                                 person.personId + ")\">","<a href=\"javascript:removePerson(" + 
		                                 person.personId + ")\">x</a>"]);
	$j("#new_member_id_selection").focus();
	$j("#new_member_id_selection").val("");
	var oldPersonID= document.getElementById("hiddenbox").value;
	
	$j("#hiddenbox").val("");
	if(oldPersonID=="")
		$j("#hiddenbox").val(person.personId);
	else
		$j("#hiddenbox").val(oldPersonID + "," +  person.personId);
}

//Funtion to remove the selected row.
function removePerson(person){
	
	var aData = householdMembersTable.fnGetData();
	var strPersonId="";
	var strDelete = "";
	
	var oldPersonID= document.getElementById("hiddenbox").value;
	
	var noSeleted = oldPersonID.split(",");
	var intPlace=-1;
	for(var x=0; x<(noSeleted.length); x++){
		alert(intPlace + "," + noSeleted[x]);
		if(noSeleted[x]==person){
			intPlace= x;
		}else
			if(strPersonId=="")
				strPersonId = noSeleted[x];
			else
				strPersonId = strPersonId + "," + noSeleted[x];
	}
	
	if(!intPlace=="")
		householdMembersTable.fnDeleteRow(intPlace, null, false);
	
	$j("#hiddenbox").val("");
	$j("#hiddenbox").val(strPersonId);
	householdMembersTable.fnDraw();
}

//Funtion to select head of household
function headPerson(person){
	$j("#hiddenIndex").val(person);
}
