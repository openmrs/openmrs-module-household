/**
 * 
 */
package org.openmrs.module.household.model;

import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.Privilege;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * @author Ampath Developers
 * Household Attributes
 */
@Root(strict = false)
public class HouseholdAttribute extends BaseOpenmrsMetadata {

	private Integer householdAttributeTypeId;
	
	private String format;
	
	private Double sortWeight;
	
	private Boolean searchable = false;
	
	private Privilege editPrivilege;
	
	private String householdDescription;
	
	/** default constructor */
	public HouseholdAttribute() {
	}
	
	/**
	 * @return the householdDescription
	 */
	public String getHouseholdDescription() {
		return householdDescription;
	}

	/**
	 * @param householdDescription the householdDescription to set
	 */
	public void setHouseholdDescription(String householdDescription) {
		this.householdDescription = householdDescription;
	}

	/** constructor with id */
	public HouseholdAttribute(Integer myHouseholdAttributeId) {
		this.householdAttributeTypeId = myHouseholdAttributeId;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.getHouseholdAttributeTypeId() == null)
			return super.hashCode();
		return 7 * this.getHouseholdAttributeTypeId().hashCode();
	}
	
	/**
	 * Compares two objects for similarity
	 * 
	 * @param obj
	 * @return boolean true/false whether or not they are the same objects
	 */
	public boolean equals(Object obj) {
		if (obj instanceof HouseholdAttribute) {
			HouseholdAttribute p = (HouseholdAttribute) obj;
			if (p != null)
				return (householdAttributeTypeId.equals(p.getHouseholdAttributeTypeId()));
		}
		return false;
	}
	
	// Property accessors
	
	/**
	 * @return Returns the format.
	 */
	@Element(data = true, required = false)
	public String getFormat() {
		return format;
	}
	
	/**
	 * @param format The format to set.
	 */
	@Element(data = true, required = false)
	public void setFormat(String format) {
		this.format = format;
	}
	
	/**
	 * @return the sortWeight
	 */
	public Double getSortWeight() {
		return sortWeight;
	}
	
	/**
	 * @param sortWeight the formOrder to set
	 */
	public void setSortWeight(Double sortWeight) {
		this.sortWeight = sortWeight;
	}
	
	/**
	 * @return Returns the HouseholdAttributeId.
	 */
	@Attribute(required = false)
	public Integer getHouseholdAttributeTypeId() {
		return householdAttributeTypeId;
	}
	
	/**
	 * @param newHouseholdAttributeId The HouseholdAttributeId to set.
	 */
	@Attribute(required = false)
	public void setHouseholdAttributeTypeId(Integer newHouseholdAttributeId) {
		this.householdAttributeTypeId = newHouseholdAttributeId;
	}
	
	/**
	 * @return the searchable status
	 */
	public Boolean isSearchable() {
		return getSearchable();
	}
	
	/**
	 * @return the searchable status
	 */
	@Attribute(required = false)
	public Boolean getSearchable() {
		return searchable;
	}
	
	/**
	 * @param searchable the searchable to set
	 */
	@Attribute(required = false)
	public void setSearchable(Boolean searchable) {
		this.searchable = searchable;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return getName();
	}
	
	/**
	 * The privilege required in order to edit this attribute
	 * 
	 * @return Returns the required privilege
	 * @since 1.5
	 */
	public Privilege getEditPrivilege() {
		return editPrivilege;
	}
	
	/**
	 * The privilege required in order to edit this attribute If <code>editPrivilege</code> is null,
	 * no extra permissions are required to edit this type
	 * 
	 * @param editPrivilege
	 * @since 1.5
	 */
	public void setEditPrivilege(Privilege editPrivilege) {
		this.editPrivilege = editPrivilege;
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return getHouseholdAttributeTypeId();
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer id) {
		setHouseholdAttributeTypeId(id);
		
	}

}
