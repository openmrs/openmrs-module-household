/**
 * 
 */
package org.openmrs.module.household.model;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Program;


/**
 * @author Ampath Developers
 *
 */
public class HouseholdDefinitionParent extends BaseOpenmrsData{

	private Integer id;
	private String parentCode;
	private String parentFullname;
	private String parentDescription;
	
	public HouseholdDefinitionParent(String defCode, String defCodeInFull, String description){
		setParentCode(defCode);
		setParentFullname(defCodeInFull);
		setParentDescription(description);
	}
	public HouseholdDefinitionParent(){};
	/** constructor with id */
	public HouseholdDefinitionParent(Integer id) {
		this.id = id;
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
	 * @return the householdDefinitionsCode
	 */
	public String getParentCode() {
		return parentCode;
	}
	/**
	 * @param parentCode the parentCode to set
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	/**
	 * @return the parentFullname
	 */
	public String getParentFullname() {
		return parentFullname;
	}
	/**
	 * @param parentFullname the parentFullname to set
	 */
	public void setParentFullname(
			String parentFullname) {
		this.parentFullname = parentFullname;
	}
	/**
	 * @return the householdDefinitionsDescription
	 */
	public String getParentDescription() {
		return parentDescription;
	}
	/**
	 * @param parentDescription the parentDescription to set
	 */
	public void setParentDescription(
			String parentDescription) {
		this.parentDescription = parentDescription;
	}
	
}
