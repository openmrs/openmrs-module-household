/**
 * 
 */
package org.openmrs.module.household.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.BaseOpenmrsData;
import org.openmrs.Form;
import org.openmrs.Person;
import org.openmrs.User;

/**
 *  @author Ampath Developers
 *
 */
public class HouseholdEncounter extends BaseOpenmrsData {

private Integer encounterId;
	
	private Date encounterDatetime;
	
	private Household householdGroups;
	
	private Integer householdGroupId;
	
	private HouseholdLocation householdLocation;
	
	private Form form;
	
	private HouseholdEncounterType encounterType;
	
	private Person provider;
	
	private Set<HouseholdObs> householdObs;
	
	// Constructors
	
	/** default constructor */
	public HouseholdEncounter() {
	}
	
	/**
	 * @param encounterId
	 * @should set encounter id
	 */
	public HouseholdEncounter(Integer encounterId) {
		this.encounterId = encounterId;
	}
	
	/**
	 * Compares two HouseholdEncounter objects for similarity
	 * 
	 * @param obj HouseholdEncounter object to compare to
	 * @return boolean true/false whether or not they are the same objects
	 * @see java.lang.Object#equals(java.lang.Object)
	 * @should equal encounter with same encounter id
	 * @should not equal encounter with different encounter id
	 * @should not equal on null
	 * @should have equal encounter objects with no encounter ids
	 * @should not have equal encounter objects when one has null encounter id
	 */
	public boolean equals(Object obj) {
		if (obj instanceof HouseholdEncounter) {
			HouseholdEncounter enc = (HouseholdEncounter) obj;
			if (this.getHouseholdEncounterId() != null && enc.getHouseholdEncounterId() != null)
				return (this.getHouseholdEncounterId().equals(enc.getHouseholdEncounterId()));
			/*return (this.getHouseholdEncounterType().equals(enc.getHouseholdEncounterType()) &&
					this.gethouseholdGroups().equals(enc.gethouseholdGroups()) &&
					this.getProvider().equals(enc.getProvider()) &&
					this.getHouseholdLocation().equals(enc.getHouseholdLocation()) &&
					this.getHouseholdEncounterDatetime().equals(enc.getHouseholdEncounterDatetime())); */
		}
		return this == obj;
		
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 * @should have same hashcode when equal
	 * @should have different hash code when not equal
	 * @should get hash code with null attributes
	 */
	public int hashCode() {
		if (this.getHouseholdEncounterId() == null)
			return super.hashCode();
		return this.getHouseholdEncounterId().hashCode();
	}
	
	// Property accessors
	
	/**
	 * @return Returns the encounterDatetime.
	 */
	public Date getHouseholdEncounterDatetime() {
		return encounterDatetime;
	}
	
	/**
	 * @param encounterDatetime The encounterDatetime to set.
	 */
	public void setHouseholdEncounterDatetime(Date encounterDatetime) {
		this.encounterDatetime = encounterDatetime;
	}
	
	/**
	 * @return Returns the encounterId.
	 */
	public Integer getHouseholdEncounterId() {
		return encounterId;
	}
	
	/**
	 * @param encounterId The encounterId to set.
	 */
	public void setHouseholdEncounterId(Integer encounterId) {
		this.encounterId = encounterId;
	}
	
	/**
	 * @return Returns the encounterType.
	 */
	public HouseholdEncounterType getHouseholdEncounterType() {
		return encounterType;
	}
	
	/**
	 * @param encounterType The encounterType to set.
	 */
	public void setHouseholdEncounterType(HouseholdEncounterType encounterType) {
		this.encounterType = encounterType;
	}
	
	/**
	 * @return Returns the householdLocation.
	 */
	public HouseholdLocation getHouseholdLocation() {
		return householdLocation;
	}
	
	/**
	 * @param householdLocation The householdLocation to set.
	 */
	public void setHouseholdLocation(HouseholdLocation householdLocation) {
		this.householdLocation = householdLocation;
	}
	
	/**
	 * @return Returns a Set<HouseholdObs> of all non-voided, non-obsGroup children HouseholdObs of this HouseholdEncounter
	 * @should not return null with null obs set
	 * @should get obs
	 * @should not get voided obs
	 * @should only get child obs
	 * @should not get child obs if child also on encounter
	 * @should get both child and parent obs after removing child from parent grouping
	 * @should get obs with two levels of hierarchy
	 * @should get obs with three levels of hierarchy
	 * @should not get voided obs with three layers of hierarchy
	 */
	public Set<HouseholdObs> getHouseholdObs() {
		Set<HouseholdObs> ret = new HashSet<HouseholdObs>();
		
		if (this.householdObs != null) {
			for (HouseholdObs o : this.householdObs)
				ret.addAll(getHouseholdObsLeaves(o));
			// this should be all thats needed unless the encounter has been built by hand
			//if (o.isVoided() == false && o.isHouseholdObsGrouping() == false)
			//	ret.add(o);
		}
		
		return ret;
	}
	
	/**
	 * Convenience method to recursively get all leaf obs of this encounter. This method goes down
	 * into each obs and adds all non-grouping obs to the return list
	 * 
	 * @param obsParent current obs to loop over
	 * @return list of leaf obs
	 */
	private List<HouseholdObs> getHouseholdObsLeaves(HouseholdObs obsParent) {
		List<HouseholdObs> leaves = new ArrayList<HouseholdObs>();
		
		if (obsParent.hasGroupMembers()) {
			for (HouseholdObs child : obsParent.getHouseholdGroupMembers()) {
				if (child.isVoided() == false) {
					if (child.isHouseholdObsGrouping() == false)
						leaves.add(child);
					else
						// recurse if this is a grouping obs
						leaves.addAll(getHouseholdObsLeaves(child));
				}
			}
		} else if (obsParent.isVoided() == false) {
			leaves.add(obsParent);
		}
		
		return leaves;
	}
	
	/**
	 * Returns all HouseholdObs where HouseholdObs.encounterId = HouseholdEncounter.encounterId In practice, this method should
	 * not be used very often...
	 * 
	 * @param includeVoided specifies whether or not to include voided HouseholdObs
	 * @return Returns the all HouseholdObs.
	 * @should not return null with null obs set
	 * @should get obs
	 * @should get both parent and child obs
	 * @should get both parent and child with child directly on encounter
	 * @should get both child and parent obs after removing child from parent grouping
	 */
	public Set<HouseholdObs> getAllHouseholdObs(boolean includeVoided) {
		if (includeVoided && householdObs != null)
			return householdObs;
		
		Set<HouseholdObs> ret = new HashSet<HouseholdObs>();
		
		if (this.householdObs != null) {
			for (HouseholdObs o : this.householdObs) {
				if (includeVoided)
					ret.add(o);
				else if (!o.isVoided())
					ret.add(o);
			}
		}
		return ret;
	}
	
	/**
	 * Convenience method to call {@link #getAllHouseholdObs(boolean)} with a false parameter
	 * 
	 * @return all non-voided obs
	 * @should not get voided obs
	 */
	public Set<HouseholdObs> getAllHouseholdObs() {
		return getAllHouseholdObs(false);
	}
	
	/**
	 * Returns a Set<HouseholdObs> of all root-level HouseholdObs of an HouseholdEncounter, including obsGroups
	 * 
	 * @param includeVoided specifies whether or not to include voided HouseholdObs
	 * @return Returns all obs at top level -- will not be null
	 * @should not return null with null obs set
	 * @should get obs
	 * @should not get voided obs
	 * @should only get parents obs
	 * @should only return the grouped top level obs
	 * @should get both child and parent obs after removing child from parent grouping
	 */
	public Set<HouseholdObs> getHouseholdObsAtTopLevel(boolean includeVoided) {
		Set<HouseholdObs> ret = new HashSet<HouseholdObs>();
		for (HouseholdObs o : getAllHouseholdObs(includeVoided)) {
			if (o.getHouseholdObsGroup() == null)
				ret.add(o);
		}
		return ret;
	}
	
	/**
	 * @param householdObs The householdObs to set.
	 */
	public void setHouseholdObs(Set<HouseholdObs> householdObs) {
		this.householdObs = householdObs;
	}
	
	/**
	 * Add the given HouseholdObs to the list of householdObs for this HouseholdEncounter.
	 * 
	 * @param householdObservation the HouseholdObs to add to this encounter
	 * @should add householdObs with null values
	 * @should not fail with null householdObs
	 * @should set encounter attribute on householdObs
	 * @should add householdObs to non null initial householdObs set
	 * @should add encounter attrs to householdObs if attributes are null
	 */
	public void addHouseholdObs(HouseholdObs observation) {
		if (householdObs == null)
			householdObs = new HashSet<HouseholdObs>();
		if (observation != null) {
			observation.setHouseholdEncounter(this);
			
			if (observation.getHouseholdObsDatetime() == null)
				observation.setHouseholdObsDatetime(getHouseholdEncounterDatetime());
			if (observation.getHouseholdGroups() == null)
				observation.setHouseholdGroups(getHouseholdGroups());
			if (observation.getHouseholdLocation() == null)
				observation.setHouseholdLocation(getHouseholdLocation());
			householdObs.add(observation);
		}
	}
	
	/**
	 * Remove the given observation from the list of obs for this HouseholdEncounter
	 * 
	 * @param observation
	 * @should remove obs successfully
	 * @should not throw error when removing null obs from empty set
	 * @should not throw error when removing null obs from non empty set
	 */
	public void removeHouseholdObs(HouseholdObs observation) {
		if (householdObs != null)
			householdObs.remove(observation);
	}
	
	/**
	 * @return Returns the householdGroups.
	 */
	public Household getHouseholdGroups() {
		return householdGroups;
	}
	
	/**
	 * @param householdGroups The householdGroups to set.
	 */
	public void setHouseholdGroups(Household householdGroups) {
		this.householdGroups = householdGroups;
	}
	
	/**
	 * @return the householdGroupId
	 */
	public Integer getHouseholdGroupId() {
		return householdGroupId;
	}
	
	/**
	 * @param householdGroupId the householdGroupId to set
	 */
	public void setHouseholdGroupId(Integer householdGroupId) {
		this.householdGroupId = householdGroupId;
	}
	
	/**
	 * @return Returns the provider.
	 * @since 1.6 (used to return User)
	 */
	public Person getProvider() {
		return provider;
	}
	
	/**
	 * @param provider The provider to set.
	 * @deprecated use {@link #setProvider(Person)} 
	 */
	public void setProvider(User provider) {
		setProvider(provider.getPerson());
	}
	
	/**
	 * @param provider The provider to set.
	 */
	public void setProvider(Person provider) {
		this.provider = provider;
	}
	
	/**
	 * @return Returns the form.
	 */
	public Form getForm() {
		return form;
	}
	
	/**
	 * @param form The form to set.
	 */
	public void setForm(Form form) {
		this.form = form;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * @should not fail with empty object
	 */
	@Override
	public String toString() {
		String ret = "";
		ret += encounterId == null ? "(no ID) " : encounterId.toString() + " ";
		ret += this.getHouseholdEncounterDatetime() == null ? "(no Date) " : this.getHouseholdEncounterDatetime().toString() + " ";
		ret += this.getHouseholdEncounterType() == null ? "(no Type) " : this.getHouseholdEncounterType().getName() + " ";
		ret += this.getHouseholdLocation() == null ? "(no HouseholdLocation) " : this.getHouseholdLocation().getName() + " ";
		ret += this.getHouseholdGroups() == null ? "(no Household Groups) " : this.getHouseholdGroups().getId().toString() + " ";
		ret += this.getForm() == null ? "(no Form) " : this.getForm().getName() + " ";
		ret += this.getHouseholdObsAtTopLevel(false) == null ? "(no HouseholdObss) " : "num HouseholdObs: " + this.getHouseholdObsAtTopLevel(false) + " ";
		return "Household Encounter: [" + ret + "]";
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		
		return getHouseholdEncounterId();
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer id) {
		setHouseholdEncounterId(id);
		
	}

}
