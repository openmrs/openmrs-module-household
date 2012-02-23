/**
 * 
 */
package org.openmrs.module.household.web.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.service.HouseholdService;
import org.openmrs.web.controller.PortletController;

/**
 * @author Ampath Developers
 *
 */
public class PatientHouseholdController extends PortletController{

	//private static final Log log = LogFactory.getLog(PatientHouseholdPortletController.class);
	
	@Override
	protected void populateModel(HttpServletRequest request,
			Map<String, Object> model) {
		//String membersButton=null;
		Person p = Context.getPersonService().getPerson(Integer.parseInt(request.getParameter("patientId")));
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdMembership> householdMem = service.getAllHouseholdMembershipsByPerson(p);
			
		//log.info(""+household.get(0).getHouseholdDefinitionsCode());
		model.put("householdMems", householdMem);
		
		/*HashMap<Integer,List <HouseholdEncounter>> householdEncounters = new HashMap<Integer,List <HouseholdEncounter>>();

		for( int i=0; i<householdMem.size(); i++){
			//Get the encounters for this Group given household with uuid
			Household household = householdMem.get(i).getHouseholdMembershipGroups();
			List <HouseholdEncounter> hEncs = service.getAllHouseholdEncountersByHouseholdUuid(household);
			//model.put("householdEncounter", he);

			if(hEncs.size()>0){
				householdEncounters.put(household.getId(), hEncs);
			}
			
		}
		model.put("encounters", householdEncounters);*/
	}
	
	
}