/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openmrs.module.household.web.controller.ports;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Mwogi
 */
@Controller
public class HouseholdSettingsEncTypeController {
    private static final Log log = LogFactory.getLog(HouseholdSettingsEncTypeController.class);
	
	@RequestMapping("module/household/ports/householdSettingsEncType")
	public void preparePage(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                           @RequestParam(required=false, value="includedRetired") String includedRetired) {
            HouseholdService service = Context.getService(HouseholdService.class);
            List<HouseholdEncounterType> householdEncTypes = new ArrayList<HouseholdEncounterType>();
            if(includedRetired.equalsIgnoreCase("true")){
                householdEncTypes = service.getAllHouseholdEncounterTypes(true);
            }else{
                householdEncTypes = service.getAllHouseholdEncounterTypes(false);
            }
            map.addAttribute("householdEncTypes", householdEncTypes);
	}
}
