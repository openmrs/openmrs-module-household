/**
 * 
 */
package org.openmrs.module.household.web.controller.ports;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/*import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;*/
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * @author jmwogi
 *
 */
@Controller
public class HouseholdSettingsEncTypeController {

	//private static final Log log = LogFactory.getLog(HouseholdSettingsEncTypeController.class);
	
	@RequestMapping(method = RequestMethod.GET, value = "module/household/ports/householdSettingsEncType")
    public void preparePage(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                            @RequestParam(required=false, value="includedRetired") boolean includeRetired) {
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdEncounterType> householdET = service.getAllHouseholdEncounterTypes(includeRetired);
		map.addAttribute("householdEncTypes", householdET);
    }
	@RequestMapping(method = RequestMethod.POST, value = "module/household/ports/householdSettingsEncType")
    public void postPage(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                         @RequestParam(required=false, value="includedRetired") boolean includeRetired) {
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdEncounterType> householdET = service.getAllHouseholdEncounterTypes(includeRetired);
		map.addAttribute("householdEncTypes", householdET);
    }
}