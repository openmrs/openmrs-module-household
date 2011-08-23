package org.openmrs.module.household.model;

import org.openmrs.BaseOpenmrsObject;

/**
 * Represents a single entry in the household Location (ie., like "Turbo", or "Kitale")
 * 
 * Must be unique on combined name and parent
 */
public class HouseholdLocationEntry extends BaseOpenmrsObject implements Comparable<HouseholdLocationEntry> {
	
	private Integer householdLocationEntryId;
	
	// the name of the entry ("Turbo")
	private String name;

	// the associated level in the hierarchy 
	private HouseholdLocationLevel level;
	
	// the parent of the entry ("Turbo" would have a parent of "Turbo South")
	private HouseholdLocationEntry parent;
	
	private String userGeneratedId;
	
	private Double latitude;
	
	private Double longitude;
	
	private Double elevation;
	
	/**
	 * To string
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Comparator  -- standard comparison is by name
	 */

    public int compareTo(HouseholdLocationEntry other) {
	    return this.name.compareTo(other.getName());
    }
	
	/**
	 * Equals
	 */
	public boolean equals(Object obj) {
		if (this.getId() == null)
			return false;
		if (obj instanceof HouseholdLocationEntry) {
			HouseholdLocationEntry c = (HouseholdLocationEntry) obj;
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
	
	public Double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	public Double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
	public Double getElevation() {
		return elevation;
	}
	
	public void setElevation(Double elevation) {
		this.elevation = elevation;
	}
	
	public HouseholdLocationLevel getLevel() {
		return level;
	}
	
	public void setLevel(HouseholdLocationLevel level) {
		this.level = level;
	}
	
	public String getUserGeneratedId() {
		return userGeneratedId;
	}
	
	public void setUserGeneratedId(String userGeneratedId) {
		this.userGeneratedId = userGeneratedId;
	}
	
	public Integer getHouseholdLocationEntryId() {
		return householdLocationEntryId;
	}
	
	public void setHouseholdLocationEntryId(Integer householdLocationEntryId) {
		this.householdLocationEntryId = householdLocationEntryId;
	}
	
	public HouseholdLocationEntry getParent() {
		return parent;
	}
	
	public void setParent(HouseholdLocationEntry parent) {
		this.parent = parent;
	}

	/**
	 * Getters and Setters to map fields to alternative names
	 */
	
    public Integer getId() {
    	return this.householdLocationEntryId;
    }
    
    public void setId(Integer id) {
	    this.householdLocationEntryId = id;	    
    }
	
    public String getLocationName() {
		return name;
	}
	
	public void setLocationName(String locationName) {
		this.name = locationName;
	}
    
	public HouseholdLocationLevel getHouseholdLocationLevel() {
		return level;
	}
	
	public void setHouseholdLocationLevel(HouseholdLocationLevel level) {
		this.level = level;
	}
}
