/**
 * 
 */
package org.openmrs.module.household.model;

import org.openmrs.BaseOpenmrsMetadata;

/**
 *  @author Ampath Developers
 *
 */
public class HouseholdEncounterType extends BaseOpenmrsMetadata {

	private Integer householdEncounterTypeId;
	
	// Constructors
	
	/** default constructor */
	public HouseholdEncounterType() {
	}
	
	/**
	 * Constructor with id
	 * 
	 * @should set encounter type id with given parameter
	 */
	public HouseholdEncounterType(Integer householdEncounterTypeId) {
		this.householdEncounterTypeId = householdEncounterTypeId;
	}
	
	/**
	 * Required values constructor. This is the minimum number of values that must be non-null in
	 * order to have a successful save to the database
	 * 
	 * @param name the name of this encounter type
	 * @param description a short description of why this encounter type exists
	 */
	public HouseholdEncounterType(String name, String description) {
		setName(name);
		setDescription(description);
	}
	
	/**
	 * Compares two EncounterType objects for similarity
	 * 
	 * @param obj
	 * @return boolean true/false whether or not they are the same objects
	 * @should have equal encounter type objects by encounter type id
	 * @should not have equal encounter type objects by encounterTypeId
	 * @should have equal encounter type objects with no encounterTypeId
	 * @should not have equal encounter type objects when one has null encounterTypeId
	 */
	public boolean equals(Object obj) {
		if (obj == null || !(obj instanceof HouseholdEncounterType))
			return false;
		
		HouseholdEncounterType encounterType = (HouseholdEncounterType) obj;
		if (this.householdEncounterTypeId != null && encounterType.getHouseholdEncounterTypeId() != null)
			return (this.householdEncounterTypeId.equals(encounterType.getHouseholdEncounterTypeId()));
		else
			return this == encounterType;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 * @should get hashCode even with null attributes
	 */
	public int hashCode() {
		if (this.getHouseholdEncounterTypeId() == null)
			return super.hashCode();
		return this.getHouseholdEncounterTypeId().hashCode();
	}
	
	// Property accessors
	
	/**
	 * @return Returns the encounterTypeId.
	 */
	public Integer getHouseholdEncounterTypeId() {
		return householdEncounterTypeId;
	}
	
	/**
	 * @param encounterTypeId The encounterTypeId to set.
	 */
	public void setHouseholdEncounterTypeId(Integer encounterTypeId) {
		this.householdEncounterTypeId = encounterTypeId;
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return getHouseholdEncounterTypeId();
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer id) {
		setHouseholdEncounterTypeId(id);
		
	}

}
