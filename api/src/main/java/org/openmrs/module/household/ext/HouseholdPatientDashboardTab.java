/**
 * 
 */
package org.openmrs.module.household.ext;

import org.openmrs.module.web.extension.PatientDashboardTabExt;

/**
 *  @author Ampath Developers
 *
 */
public class HouseholdPatientDashboardTab extends PatientDashboardTabExt {

	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getPortletUrl()
	 */
	@Override
	public String getPortletUrl() {
		// TODO Auto-generated method stub
		return "patientHousehold";
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getRequiredPrivilege()
	 */
	@Override
	public String getRequiredPrivilege() {
		// TODO Auto-generated method stub
		return "Manage Household";
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getTabId()
	 */
	@Override
	public String getTabId() {
		// TODO Auto-generated method stub
		return "PatientHousehold";
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.web.extension.PatientDashboardTabExt#getTabName()
	 */
	@Override
	public String getTabName() {
		// TODO Auto-generated method stub
		return "Households";
	}

}
