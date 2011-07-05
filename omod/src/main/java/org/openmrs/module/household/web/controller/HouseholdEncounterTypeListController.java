/**
 * 
 */
package org.openmrs.module.household.web.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *  @author Ampath Developers
 *
 */
@Controller
public class HouseholdEncounterTypeListController {
	
	private static final Log log = LogFactory.getLog(HouseholdEncounterTypeListController.class);
	
	@RequestMapping(method=RequestMethod.GET, value="module/household/householdEncounterTypeList")
	public void preparePage(ModelMap map) {
		
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdEncounterType> householdEncTypes = service.getAllHouseholdEncounterTypes(true);
		map.addAttribute("householdEncTypes", householdEncTypes);
		log.info("\n....SIZE.." + householdEncTypes.size());
	}
	
	@RequestMapping(method=RequestMethod.POST, value="module/household/householdEncounterTypeList")
	public void processForm(ModelMap map, @RequestParam(required=false, value="householdEncTypeName") String householdEncTypeName,
			@RequestParam(required=false, value="houseid") String householdEncTypeID,
			 @RequestParam(required=false, value="householdEncTypeDescription") String householdEncTypeDescription){
		
		HouseholdService service = Context.getService(HouseholdService.class);
		
		
		if(!StringUtils.hasText(householdEncTypeID)){
			//Add new hd
			HouseholdEncounterType householdEnc = new HouseholdEncounterType();
			householdEnc.setName(householdEncTypeName);
			householdEnc.setDescription(householdEncTypeDescription);
			
			service.saveHouseholdEncounterType(householdEnc);
			
		}else{
			HouseholdEncounterType householdEncEdit = new HouseholdEncounterType();
			
			householdEncEdit = service.getHouseholdEncounterType(Integer.parseInt(householdEncTypeID));
			if(StringUtils.hasText(householdEncTypeName)){
				//saving/updating a record
				householdEncEdit.setName(householdEncTypeName);
				householdEncEdit.setDescription(householdEncTypeDescription);
				service.saveHouseholdEncounterType(householdEncEdit);
			}else{
				//to be editted
				map.addAttribute("householdEnc", householdEncEdit);
			}
		}
		
		
		List<HouseholdEncounterType> householdEncTypes = service.getAllHouseholdEncounterTypes();
		map.addAttribute("householdEncTypes", householdEncTypes);
		
	}
}
