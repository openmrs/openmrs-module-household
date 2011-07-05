/**
 * 
 */
package org.openmrs.module.household.model;


/**
 * @author Ampath Developers
 *
 */
public class Household{

	private Integer id;
	private HouseholdDefinition householdDef;
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @return the householdDef
	 */
	public HouseholdDefinition getHouseholdDef() {
		return householdDef;
	}
	/**
	 * @param householdDef the householdDef to set
	 */
	public void setHouseholdDef(HouseholdDefinition householdDef) {
		this.householdDef = householdDef;
	}
	
}
