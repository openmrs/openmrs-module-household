/**
 * 
 */
package org.openmrs.module.household.model;

import java.util.Date;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Person;


/**
 * @author Ampath Developers
 *
 */
public class HouseholdMembership extends BaseOpenmrsData{

	private Integer id;
	private Person householdMembershipMember;
	private Household householdMembershipGroups;
	private boolean householdMembershipHeadship;
	private Date startDate;
	private Date endDate;
	private Date resumeDate;
	private String resumeReason;
		
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
	 * @return the householdMembershipMember
	 */
	public Person getHouseholdMembershipMember() {
		return householdMembershipMember;
	}
	/**
	 * @param householdMembershipMember the householdMembershipMember to set
	 */
	public void setHouseholdMembershipMember(Person householdMembershipMember) {
		this.householdMembershipMember = householdMembershipMember;
	}
	/**
	 * @return the householdMembershipGroups
	 */
	public Household getHouseholdMembershipGroups() {
		return householdMembershipGroups;
	}
	 /**
	 * @param householdMembershipGroups the householdMembershipGroups to set
	 */
	public void setHouseholdMembershipGroups(
			Household householdMembershipGroups) {
		this.householdMembershipGroups = householdMembershipGroups;
	}
	/**
	 * @return the householdMembershipHeadship
	 */
	public boolean isHouseholdMembershipHeadship() {
		return householdMembershipHeadship;
	}
	/**
	 * @param householdMembershipHeadship the householdMembershipHeadship to set
	 */
	public void setHouseholdMembershipHeadship(boolean householdMembershipHeadship) {
		this.householdMembershipHeadship = householdMembershipHeadship;
	}
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}
	
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	/**
	 * 
	 * @return resumeDate
	 */
	public Date getResumeDate() {
		return resumeDate;
	}
	/**
	 * 
	 * @param resumeDate
	 */
	public void setResumeDate(Date resumeDate) {
		this.resumeDate = resumeDate;
	}
	
	/**
	 * 
	 * @return resumeReason
	 */
	public String getResumeReason() {
		return resumeReason;
	}
	
	/**
	 * 
	 * @param resumeReason
	 */
	public void setResumeReason(String resumeReason) {
		this.resumeReason = resumeReason;
	}
	
}
	
