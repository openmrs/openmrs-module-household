/**
 * 
 */
package org.openmrs.module.household.ext;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openmrs.module.household.util.HouseholdConstants;
import org.openmrs.module.web.extension.AdministrationSectionExt;


/**
 * @author Ampath Developers
 *
 */
public class HouseholdAdminExt extends AdministrationSectionExt {
	
	/** Defines the privilege required to the see the Administration section for the module */
	public String getRequiredPrivilege() {
		return "";
	}
	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getLinks()
	 */
	@Override
	public Map<String, String> getLinks() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("module/household/householdDashboard.form", "Dashboard");
		/*map.put("module/household/householdRegistration.form", "Registration");
		map.put("module/household/householdEncounterTypeList.list", "Encounters Types");*/
		map.put("module/household/householdSettingsPanel.form", "Settings");
		/*map.put("module/household/manageHouseholdLocation.form", "Locations/Sites");
		map.put("module/household/householdCHWInitial.form", "CHW Encounter");*/

		return map;
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.AdministrationSectionExt#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Household Module";
	}
	
}
