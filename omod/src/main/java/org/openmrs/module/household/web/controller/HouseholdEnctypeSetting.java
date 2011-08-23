package org.openmrs.module.household.web.controller;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HouseholdEnctypeSetting {
	private static final Log log = LogFactory.getLog(HouseholdEnctypeSetting.class);
	@RequestMapping(method=RequestMethod.GET, value="module/household/householdEnctypeSetting")
	public void preparePage(ModelMap map,@RequestParam(required=false, value="encounters") String encounters) {
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdEncounterType> encounterTypes=service.getAllHouseholdEncounterTypes();
		map.addAttribute("encounterTypes", encounterTypes);
		log.info(encounterTypes.size());

}
}
