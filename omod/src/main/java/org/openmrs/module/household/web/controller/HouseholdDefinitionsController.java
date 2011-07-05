/**
 * 
 */
package org.openmrs.module.household.web.controller;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Ampath Developers
 *
 */
@Controller
public class HouseholdDefinitionsController {

	private static final Log log = LogFactory.getLog(HouseholdDefinitionsController.class);
	
	@RequestMapping(method=RequestMethod.GET, value="module/household/householdDefinitions")
	public void preparePage(ModelMap map) {
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdDefinition> householdsTypes = service.getAllHouseholdDefinitions();
		map.addAttribute("householdsTypes", householdsTypes);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="module/household/householdDefinitions")
	public void processForm(ModelMap map, @RequestParam(required=false, value="householdDefinitionsCode") String householdDefinitionsCode,
			 @RequestParam(required=false, value="householdDefinitionsCodeinfull") String householdDefinitionsCodeinfull, 
			 @RequestParam(required=false, value="householdDefinitionsDescription") String householdDefinitionsDescription,
			 @RequestParam(required=false, value="houseid") String householdDefinitionsID){
		
		HouseholdService service = Context.getService(HouseholdService.class);
		
		if(!StringUtils.hasText(householdDefinitionsID)){
			log.info("Processing post request ..." + householdDefinitionsCode + ", " + householdDefinitionsCodeinfull + ", " + householdDefinitionsDescription);
			//Add new hd
			HouseholdDefinition householdDefinition = new HouseholdDefinition();
			householdDefinition.setHouseholdDefinitionsCode(householdDefinitionsCode);
			householdDefinition.setHouseholdDefinitionsCodeinfull(householdDefinitionsCodeinfull);
			householdDefinition.setHouseholdDefinitionsDescription(householdDefinitionsDescription);
			
			HouseholdDefinition hd = service.saveHouseholdDefinition(householdDefinition);
			log.info(hd.getId());
			
		}else{
			HouseholdDefinition householdDefEdit = new HouseholdDefinition();
			
			householdDefEdit = service.getHouseholdDefinition(Integer.parseInt(householdDefinitionsID));
			if(StringUtils.hasText(householdDefinitionsCode)){
				//saving/updating a record
				householdDefEdit.setHouseholdDefinitionsCode(householdDefinitionsCode);
				householdDefEdit.setHouseholdDefinitionsCodeinfull(householdDefinitionsCodeinfull);
				householdDefEdit.setHouseholdDefinitionsDescription(householdDefinitionsDescription);
				
				service.saveHouseholdDefinition(householdDefEdit);
			}else{
				//to be editted
				map.addAttribute("householdEdit", householdDefEdit);
			}
		}
		
		
		List<HouseholdDefinition> householdsTypes = service.getAllHouseholdDefinitions();
		map.addAttribute("householdsTypes", householdsTypes);
	}
}