/**
 * 
 */
package org.openmrs.module.household.web.controller.ports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jmwogi
 *
 */
@Controller
public class HouseholdDefChildController {

	private static final Log log = LogFactory.getLog(HouseholdDefChildController.class);
	
	@RequestMapping("module/household/ports/householdDefChild")
    public void preparePage(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                            @RequestParam(required = false, value = "child") String child) {
		HouseholdService service = Context.getService(HouseholdService.class);
		if(!StringUtils.isEmpty(child)){
			int householdDefinition = Integer.parseInt(child);
			HouseholdDefinition hd = service.getHouseholdDefinition(householdDefinition);
			
			map.addAttribute("hdef", hd);
		}
    }
}