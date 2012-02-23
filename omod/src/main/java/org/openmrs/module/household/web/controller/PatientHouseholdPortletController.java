/**
 * 
 */
package org.openmrs.module.household.web.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.service.HouseholdService;
import org.openmrs.web.controller.PortletController;

/**
 * @author Ampath Developers
 */
public class PatientHouseholdPortletController extends PortletController{

	private static final Log log = LogFactory.getLog(PatientHouseholdPortletController.class);
	
	@Override
	protected void populateModel(HttpServletRequest request,
			Map<String, Object> model) {
		Person p = Context.getPersonService().getPerson(Integer.parseInt(request.getParameter("patientId")));
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdMembership> householdMem = service.getAllHouseholdMembershipsByPerson(p);
			
		log.info("\n >>>>>>>>>>>>>" + householdMem.size());
		model.put("householdM", householdMem);
	}
}