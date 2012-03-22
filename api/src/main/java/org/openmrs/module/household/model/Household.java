/**
 * 
 */
package org.openmrs.module.household.model;

import java.util.Date;

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
	private Date startDate;
	private Date endDate;
	private String provider;
	
	public Date getStartDate() {
		return startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public String getProvider() {
		return provider;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public void setProvider(String provider) {
		this.provider = provider;
	}
	
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
