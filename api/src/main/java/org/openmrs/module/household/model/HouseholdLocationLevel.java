package org.openmrs.module.household.model;

import org.openmrs.BaseOpenmrsObject;
import org.openmrs.module.household.HouseholdLocationField;

/**
 * Represents an Household Location level (ie., like "Country", or "State", or "City")
 */
public class HouseholdLocationLevel extends BaseOpenmrsObject {
	
	private Integer levelId;
	
	private String name;
	
	// the parent of this level
	private HouseholdLocationLevel parent;
	
	// the associated address field (see HouseholdLocationField enum) this level maps to (may be null)
	private HouseholdLocationField householdLocationField;
	
	// whether or not the associated address field should be allowed to be empty in an Address
	private Boolean required = false;
	
	/**
	 * To string
	 */
	public String toString() {
		return getName();
	}
	
	public boolean equals(Object obj) {
		if (this.getId() == null)
			return false;
		if (obj instanceof HouseholdLocationLevel) {
			HouseholdLocationLevel c = (HouseholdLocationLevel) obj;
			return (this.getId().equals(c.getId()));
		}
		return false;
	}

	/**
	 * Getters and Setters
	 */
	
	public void setName(String name) {
	    this.name = name;
    }

	public String getName() {
	    return name;
    }
	
	public HouseholdLocationLevel getParent() {
		return parent;
	}
	
	public void setParent(HouseholdLocationLevel parent) {
		this.parent = parent;
	}

	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	
	public Integer getLevelId() {
		return this.levelId;
	}

	public void setHouseholdLocationField(HouseholdLocationField householdLocationField) {
	    this.householdLocationField = householdLocationField;
    }

	public HouseholdLocationField getHouseholdLocationField() {
	    return householdLocationField;
    }

    public Integer getId() {
	    return this.levelId;
    }

    public void setId(Integer id) {
	   this.levelId = id;
    }

	public void setRequired(Boolean required) {
	    this.required = required;
    }

	public Boolean getRequired() {
	    return required;
    }

}
