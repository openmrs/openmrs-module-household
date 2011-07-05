/**
 * 
 */
package org.openmrs.module.household.service;

import junit.framework.Assert;

import org.junit.Test;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.service.HouseholdService;
import org.openmrs.test.BaseModuleContextSensitiveTest;

/**
 *  @author Ampath Developers
 */
public class HouseholdServiceTest extends BaseModuleContextSensitiveTest {
	
	@Test
	public void householdDefinitionCreationHierarchyTest() {
		HouseholdService service = Context.getService(HouseholdService.class);
		
		HouseholdDefinition household = new HouseholdDefinition();
		household.setHouseholdDefinitionsCode("HCT");
		household.setHouseholdDefinitionsCodeinfull("Homebased Counselling and Testing");
		household.setHouseholdDefinitionsDescription("The index person here is the Father or Mother of the Household");
		
		Assert.assertNull(household.getId());
		service.saveHouseholdDefinition(household);
		Assert.assertNotNull(household.getId());
	}
}
