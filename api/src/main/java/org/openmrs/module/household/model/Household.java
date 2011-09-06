/**
 * 
 */
package org.openmrs.module.household.model;

import org.openmrs.BaseOpenmrsData;


/**
 * @author Ampath Developers
 *
 */
public class Household extends BaseOpenmrsData implements java.io.Serializable{

	public static final long serialVersionUID = 2L;
	private Integer id;
	private HouseholdDefinition householdDef;
	private String householdIdentifier;
	//private String uuid = null;
	
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
	/**
	 * @return the householdIdentifier
	 */
	public String getHouseholdIdentifier() {
		return householdIdentifier;
	}
	/**
	 * @param householdIdentifier the householdIdentifier to set
	 */
	public void setHouseholdIdentifier(String householdIdentifier) {
		this.householdIdentifier = householdIdentifier;
	}
	/**
	 * @return the uuid
	 *//*
    public String getUuid() {
        return uuid;
    }
    *//**
	 * @param uuid the uuid to set
	 *//*
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }*/
    
}
