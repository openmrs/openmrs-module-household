/**
 * 
 */
package org.openmrs.module.household.ext;

import java.util.HashMap;
import java.util.Map;

import org.openmrs.module.web.extension.AdministrationSectionExt;


/**
 * @author Ampath Developers
 *
 */
public class HouseholdAdminExt extends AdministrationSectionExt {
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getLinks()
	 */
	@Override
	public Map<String, String> getLinks() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("module/household/householdDefinitions.form", "Household Definitions");
		map.put("module/household/householdRegistration.form", "Registration Form");
		map.put("module/household/householdEncounterTypeList.list", "Encounters");

		return map;
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Households";
	}
	
}
