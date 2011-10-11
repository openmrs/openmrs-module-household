/**
 * 
 */
package org.openmrs.module.household.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
//import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
//import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.Concept;
import org.openmrs.ConceptDescription;
import org.openmrs.ConceptName;
import org.openmrs.api.APIException;
import org.openmrs.util.Format;
import org.openmrs.util.Format.FORMAT_TYPE;

/**
 * @author Ampath Developers
 *
 */
public class HouseholdObs extends BaseOpenmrsData {

	protected Integer obsId;
	
	private static final Log log = LogFactory.getLog(HouseholdObs.class);

	protected Concept concept;
	
	protected Date householdObsDatetime;
	
	/**
	 * The "parent" of this obs. It is the grouping that brings other obs together. note:
	 * householdObsGroup.getConcept().isSet() should be true This will be non-null if this obs is a member of
	 * another groupedObs
	 * 
	 * @see #isObsGrouping() (??)
	 */
	protected HouseholdObs householdObsGroup;
	
	/**
	 * The list of obs grouped under this obs.
	 */
	//protected Set<HouseholdObs> householdGroupMembers;

    protected String householdObsGroupUuid;
	
	protected Concept valueCoded;
	
	protected ConceptName valueCodedName;
	
	protected Integer valueGroupId;
	
	protected Date valueDatetime;
	
	protected Double valueNumeric;
	
	protected String valueModifier;
	
	protected String valueText;
	
	protected String comment;
	
	protected transient Integer householdGroupId;
	
	protected Household householdGroups;
	
	protected HouseholdLocation location;
	
	protected HouseholdEncounter encounter;
	
	protected Date dateStarted;
	
	protected Date dateStopped;
	
	/** default constructor */
	public HouseholdObs() {
	}
	
	/**
	 * Required parameters constructor A value is also required, but that can be one of: valueCoded,
	 * valueDrug, valueNumeric, or valueText
	 * 
	 * @param householdGroups The HouseholdGroups this obs is acting on
	 * @param question The question concept this obs is related to
	 * @param householdObsDatetime The time this obs took place
	 * @param location The location this obs took place
	 */
	public HouseholdObs(Household householdGroups, Concept question, Date householdObsDatetime, HouseholdLocation location) {
		this.householdGroups = householdGroups;
		if (householdGroups != null)
			this.householdGroupId = householdGroups.getId();
		this.concept = question;
		this.householdObsDatetime = householdObsDatetime;
		this.location = location;
	}
	
	/** constructor with id */
	public HouseholdObs(Integer obsId) {
		this.obsId = obsId;
	}
	
	/**
	 * This is an equivalent to a copy constructor. Creates a new copy of the given
	 * <code>obsToCopy</code> with a null obs id
	 * 
	 * @param obsToCopy The HouseholdObs that is going to be copied
	 * @return a new HouseholdObs object with all the same attributes as the given obs
	 */
	public static HouseholdObs newInstance(HouseholdObs obsToCopy) {
		HouseholdObs newHouseholdObs = new HouseholdObs(obsToCopy.getHouseholdGroups(), obsToCopy.getConcept(), obsToCopy.getHouseholdObsDatetime(), obsToCopy
		        .getHouseholdLocation());
		
		newHouseholdObs.setHouseholdObsGroup(obsToCopy.getHouseholdObsGroup());
		newHouseholdObs.setValueCoded(obsToCopy.getValueCoded());
		newHouseholdObs.setValueGroupId(obsToCopy.getValueGroupId());
		newHouseholdObs.setValueDatetime(obsToCopy.getValueDatetime());
		newHouseholdObs.setValueNumeric(obsToCopy.getValueNumeric());
		newHouseholdObs.setValueModifier(obsToCopy.getValueModifier());
		newHouseholdObs.setValueText(obsToCopy.getValueText());
		newHouseholdObs.setComment(obsToCopy.getComment());
		newHouseholdObs.setHouseholdEncounter(obsToCopy.getHouseholdEncounter());
		newHouseholdObs.setDateStarted(obsToCopy.getDateStarted());
		newHouseholdObs.setDateStopped(obsToCopy.getDateStopped());
		newHouseholdObs.setCreator(obsToCopy.getCreator());
		newHouseholdObs.setDateCreated(obsToCopy.getDateCreated());
		newHouseholdObs.setVoided(obsToCopy.getVoided());
		newHouseholdObs.setVoidedBy(obsToCopy.getVoidedBy());
		newHouseholdObs.setDateVoided(obsToCopy.getDateVoided());
		newHouseholdObs.setVoidReason(obsToCopy.getVoidReason());
		
		/*if (obsToCopy.getHouseholdGroupMembers() != null)
			for (HouseholdObs member : obsToCopy.getHouseholdGroupMembers()) {
				// if the obs hasn't been saved yet, no need to duplicate it
				if (member.getHouseholdObsId() == null)
					newHouseholdObs.addGroupMember(member);
				else
					newHouseholdObs.addGroupMember(HouseholdObs.newInstance(member));
			}*/
		
		return newHouseholdObs;
	}
	
	/**
	 * Compares two HouseholdObs for similarity. The comparison is done on obsId of both this and the given
	 * <code>obs</code> object. If either has a null obsId, then they are not equal
	 * 
	 * @param obj
	 * @return boolean True if the obsIds match, false otherwise or if either obsId is null.
	 */
	public boolean equals(Object obj) {
		if (obj instanceof HouseholdObs) {
			HouseholdObs o = (HouseholdObs) obj;
			if (this.getHouseholdObsId() != null && o.getHouseholdObsId() != null)
				return (this.getHouseholdObsId().equals(o.getHouseholdObsId()));
			/*
			 * return (this.getConcept().equals(o.getConcept()) &&
			 * this.getPatient().equals(o.getPatient()) &&
			 * this.getHouseholdEncounter().equals(o.getHouseholdEncounter()) &&
			 * this.getHouseholdLocation().equals(o.getHouseholdLocation()));
			 */
		}
		
		// if the obsIds don't match, its possible that they are the same
		// exact object. Check that now on the way out.
		return this == obj;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.getHouseholdObsId() == null)
			return super.hashCode();
		return this.getHouseholdObsId().hashCode();
	}
	
	
	
	// Property accessors
	
	/**
	 * @return Returns the comment.
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * @param comment The comment to set.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * @return Returns the concept.
	 */
	public Concept getConcept() {
		return concept;
	}
	
	/**
	 * @param concept The concept to set.
	 */
	public void setConcept(Concept concept) {
		this.concept = concept;
	}
	
	/**
	 * Get the concept description that is tied to the concept name that was used when making this
	 * observation
	 * 
	 * @return ConceptDescription the description used
	 */
	public ConceptDescription getConceptDescription() {
		// if we don't have a question for this concept,
		// then don't bother looking for a description
		if (getConcept() == null)
			return null;
		
		// ABKTOD: description in which locale?
		return concept.getDescription();
	}
	
	/**
	 * @return Returns the encounter.
	 */
	public HouseholdEncounter getHouseholdEncounter() {
		return encounter;
	}
	
	/**
	 * @param encounter The encounter to set.
	 */
	public void setHouseholdEncounter(HouseholdEncounter encounter) {
		this.encounter = encounter;
	}
	
	/**
	 * @return Returns the location.
	 */
	public HouseholdLocation getHouseholdLocation() {
		return location;
	}
	
	/**
	 * @param location The location to set.
	 */
	public void setHouseholdLocation(HouseholdLocation location) {
		this.location = location;
	}
	
	/**
	 * @return Returns the householdObsDatetime.
	 */
	public Date getHouseholdObsDatetime() {
		return householdObsDatetime;
	}
	
	/**
	 * @param householdObsDatetime The householdObsDatetime to set.
	 */
	public void setHouseholdObsDatetime(Date householdObsDatetime) {
		this.householdObsDatetime = householdObsDatetime;
	}
	
	/**
	 * @return Returns the obsId of the parent obs group
	 * @deprecated The {@link #getHouseholdObsGroup()} method should be used
	 * @see #getHouseholdObsGroup()
	 */
	public Integer getHouseholdObsGroupId() {
		if (getHouseholdObsGroup() == null)
			return null;
		
		return householdObsGroup.getHouseholdObsId();
	}
	
	/**
	 * @param householdObsGroupId The householdObsGroupId to set.
	 * @deprecated This method should not be used. The #setHouseholdObsGroup() method should be used instead
	 * @see #setHouseholdObsGroup(HouseholdObs)
	 */
	public void setHouseholdObsGroupId(Integer householdObsGroupId) {
		throw new APIException("I don't know what to do here because I don't" + "know what the parent is of the group I'm "
		        + "being put into. This method is deprecated " + "and should not be used.");
	}
	
	/**
	 * An obs grouping occurs when the question (#getConcept()) is a set. (@link
	 * org.openmrs.Concept#isSet()) If this is non-null, it means the current HouseholdObs is in the list
	 * returned by <code>householdObsGroup</code>.{@link #getGroupMembers()}
	 * 
	 * @return the HouseholdObs that is the grouping factor
	 */
	public HouseholdObs getHouseholdObsGroup() {
		return householdObsGroup;
	}
	
	/**
	 * This method does NOT add this current obs to the list of obs in householdObsGroup.getGroupMembers().
	 * That must be done (and should be done) manually. (I am not doing it here for fear of screwing
	 * up the normal loading and creation of this object via hibernate/spring)
	 * 
	 * @param householdObsGroup the householdObsGroup to set
	 */
	public void setHouseholdObsGroup(HouseholdObs householdObsGroup) {
		this.householdObsGroup = householdObsGroup;
	}
	
	/**
	 * Convenience method that checks for nullity and length of the (@link #getGroupMembers())
	 * method
	 * <p>
	 * NOTE: This method could also be called "isHouseholdObsGroup" for a little less confusion on names.
	 * However, jstl in a web layer (or any psuedo-getter) access isn't good with both an
	 * "isHouseholdObsGroup" method and a "getHouseholdObsGroup" method. Which one should be returned with a
	 * simplified jstl call like ${obs.householdObsGroup} ? With this setup, ${obs.householdObsGrouping} returns a
	 * boolean of whether this obs is a parent and has members. ${obs.householdObsGroup} returns the parent
	 * object to this obs if this obs is a group member of some other group.
	 * 
	 * @return true if this is the parent group of other obs
	 */
	/*public boolean isHouseholdObsGrouping() {
		return hasGroupMembers();
	}*/
	
	/**
	 * Convenience method that checks for nullity and length of the (@link #getGroupMembers())
	 * method
	 * 
	 * @return true if this is the parent group of other obs
	 */
	/*public boolean hasGroupMembers() {
		return getHouseholdGroupMembers() != null && getHouseholdGroupMembers().size() > 0;
	}*/
	
	/**
	 * Get the members of the obs group, if this obs is a group.
	 * <p>
	 * If it's not a group (i.e. {@link #getConcept()}.{@link org.openmrs.Concept#isSet()} is not
	 * true, then this returns null.
	 * 
	 * @return a Set<HouseholdObs> of the members of this group.
	 * @see #addGroupMember(HouseholdObs)
	 * @see #hasGroupMembers()
	 */
	/*public Set<HouseholdObs> getHouseholdGroupMembers() {
		return householdGroupMembers;
	}*/
	
	/**
	 * Set the members of the obs group, if this obs is a group.
	 * <p>
	 * If it's not a group (i.e. {@link #getConcept()}.{@link org.openmrs.Concept#isSet()} is not
	 * true, then this returns null.
	 * 
	 * @param groupMembers the groupedHouseholdObs to set
	 * @see #addGroupMember(HouseholdObs)
	 * @see #hasGroupMembers()
	 */
	/*public void setHouseholdGroupMembers(Set<HouseholdObs> groupMembers) {
		this.householdGroupMembers = groupMembers;
	}*/
	
	/**
	 * Convenience method to add the given <code>obs</code> to this grouping. Will implicitly make
	 * this obs an HouseholdObsGroup
	 * 
	 * @param member HouseholdObs to add to this group
	 * @see #setGroupMembers(Set)
	 * @see #getGroupMembers()
	 */
	/*public void addGroupMember(HouseholdObs member) {
		if (member == null)
			return;
		
		if (getHouseholdGroupMembers() == null)
			householdGroupMembers = new HashSet<HouseholdObs>();
		
		// a quick sanity check to make sure someone isn't adding
		// itself to the group
		if (member.equals(this))
			throw new APIException("An householdObsGroup cannot have itself as a mentor. householdObsGroup: " + this
			        + " obsMember attempting to add: " + member);
		
		member.setHouseholdObsGroup(this);
		householdGroupMembers.add(member);
	}*/
	
	/**
	 * Convenience method to remove an HouseholdObs from this grouping This also removes the link in the
	 * given <code>obs</code>object to this obs grouper
	 * 
	 * @param member HouseholdObs to remove from this group
	 * @see #setGroupMembers(Set)
	 * @see #getGroupMembers()
	 */
	/*public void removeGroupMember(HouseholdObs member) {
		if (member == null || getHouseholdGroupMembers() == null)
			return;
		
		if (householdGroupMembers.remove(member))
			member.setHouseholdObsGroup(null);
	}*/
	
	/**
	 * Convenience method that returns related HouseholdObs If the HouseholdObs argument is not an HouseholdObsGroup: a
	 * Set<HouseholdObs> will be returned containing all of the children of this HouseholdObs' parent that are not
	 * HouseholdObsGroups themselves. This will include this HouseholdObs by default, unless getHouseholdObsGroup() returns
	 * null, in which case an empty set is returned. If the HouseholdObs argument is an HouseholdObsGroup: a Set<HouseholdObs>
	 * will be returned containing 1. all of this HouseholdObs' group members, and 2. all ancestor HouseholdObs that
	 * are not themselves householdObsGroups.
	 * 
	 * @return Set<HouseholdObs>
	 */
	/*public Set<HouseholdObs> getRelatedHouseholdObservations() {
		Set<HouseholdObs> ret = new HashSet<HouseholdObs>();
		if (this.isHouseholdObsGrouping()) {
			ret.addAll(this.getHouseholdGroupMembers());
			HouseholdObs parentHouseholdObs = this;
			while (parentHouseholdObs.getHouseholdObsGroup() != null) {
				for (HouseholdObs obsSibling : parentHouseholdObs.getHouseholdObsGroup().getHouseholdGroupMembers()) {
					if (!obsSibling.isHouseholdObsGrouping())
						ret.add(obsSibling);
				}
				parentHouseholdObs = parentHouseholdObs.getHouseholdObsGroup();
			}
		} else if (this.getHouseholdObsGroup() != null) {
			for (HouseholdObs obsSibling : this.getHouseholdObsGroup().getHouseholdGroupMembers()) {
				if (!obsSibling.isHouseholdObsGrouping())
					ret.add(obsSibling);
			}
		}
		return ret;
	}*/
	
	/**
	 * @return Returns the obsId.
	 */
	public Integer getHouseholdObsId() {
		return obsId;
	}
	
	/**
	 * @param obsId The obsId to set.
	 */
	public void setHouseholdObsId(Integer obsId) {
		this.obsId = obsId;
	}
	
	
	
	/**
	 * The householdGroups id of the householdGroups on this object. This should be the same as
	 * <code>{@link #getHouseholdGroups1()}.getHouseholdGroupsId()</code>. It is duplicated here for speed and
	 * simplicity reasons
	 * 
	 * @return the integer householdGroups id of the householdGroups this obs is acting on
	 */
	public Integer getHouseholdGroupsId() {
		return householdGroupId;
	}
	
	/**
	 * Set the householdGroups id on this obs object. This method is here for convenience, but really the
	 * {@link #setHouseholdGroups(Household)} method should be used like
	 * <code>setHouseholdGroups(new HouseholdGroups(householdGroupsId))</code>
	 * 
	 * @see #setHouseholdGroups(Household)
	 * @param householdGroupsId
	 */
	protected void setHouseholdGroupsId(Integer householdGroupsId) {
		this.householdGroupId = householdGroupsId;
	}
	
	/**
	 * Get the householdGroups object that this obs is acting on.
	 * 
	 * @see #getHouseholdGroupsId()
	 * @return the householdGroups object
	 */
	public Household getHouseholdGroups() {
		return householdGroups;
	}
	
	/**
	 * Set the householdGroups object to this obs object. This will also set the householdGroupsId on this obs object
	 * 
	 * @see #setHouseholdGroupsId(Integer)
	 * @param householdGroups the Patient/HouseholdGroups object that this obs is acting on
	 */
	public void setHouseholdGroups(Household householdGroups) {
		this.householdGroups = householdGroups;
		if (householdGroups != null)
			this.householdGroupId = householdGroups.getId();
	}

    /**
	 * @return Returns the householdObsGroupUuid
	 */
	public String getHouseholdObsGroupUuid() {
		return householdObsGroupUuid;
	}

	/**
	 * @param householdObsGroupUuid The valueCoded to set.
	 */
	public void setHouseholdObsGroupUuid(String householdObsGroupUuid) {
		this.householdObsGroupUuid = householdObsGroupUuid;
	}

	/**
	 * This converts the value_numeric to a value_boolean, essentially
	 * 
	 * @return Boolean of the obs value
	 */
	public Boolean getValueAsBoolean() {
		return (getValueNumeric() == null ? null : getValueNumeric() != 0);
	}
	
	/**
	 * @return Returns the valueCoded.
	 */
	public Concept getValueCoded() {
		return valueCoded;
	}
	
	/**
	 * @param valueCoded The valueCoded to set.
	 */
	public void setValueCoded(Concept valueCoded) {
		this.valueCoded = valueCoded;
	}
	
	/**
	 * Gets the specific name used for the coded value.
	 * 
	 * @return the name of the coded value
	 */
	public ConceptName getValueCodedName() {
		return valueCodedName;
	}
	
	/**
	 * Sets the specific name used for the coded value.
	 * 
	 * @param valueCodedName the name of the coded value
	 */
	public void setValueCodedName(ConceptName valueCodedName) {
		this.valueCodedName = valueCodedName;
	}
	
	/**
	 * @return Returns the valueDatetime.
	 */
	public Date getValueDatetime() {
		return valueDatetime;
	}
	
	/**
	 * @param valueDatetime The valueDatetime to set.
	 */
	public void setValueDatetime(Date valueDatetime) {
		this.valueDatetime = valueDatetime;
	}
	
	/**
	 * @return Returns the valueGroupId.
	 */
	public Integer getValueGroupId() {
		return valueGroupId;
	}
	
	/**
	 * @param valueGroupId The valueGroupId to set.
	 */
	public void setValueGroupId(Integer valueGroupId) {
		this.valueGroupId = valueGroupId;
	}
	
	/**
	 * @return Returns the valueModifier.
	 */
	public String getValueModifier() {
		return valueModifier;
	}
	
	/**
	 * @param valueModifier The valueModifier to set.
	 */
	public void setValueModifier(String valueModifier) {
		this.valueModifier = valueModifier;
	}
	
	/**
	 * @return Returns the valueNumeric.
	 */
	public Double getValueNumeric() {
		return valueNumeric;
	}
	
	/**
	 * @param valueNumeric The valueNumeric to set.
	 */
	public void setValueNumeric(Double valueNumeric) {
		this.valueNumeric = valueNumeric;
	}
	
	/**
	 * @return Returns the valueText.
	 */
	public String getValueText() {
		return valueText;
	}
	
	/**
	 * @param valueText The valueText to set.
	 */
	public void setValueText(String valueText) {
		this.valueText = valueText;
	}
	
	/**
	 * @return Returns the dateStarted.
	 */
	public Date getDateStarted() {
		return dateStarted;
	}
	
	/**
	 * @param dateStarted The dateStarted to set.
	 */
	public void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}
	
	/**
	 * @return Returns the dateStopped.
	 */
	public Date getDateStopped() {
		return dateStopped;
	}
	
	/**
	 * @param dateStopped The dateStopped to set.
	 */
	public void setDateStopped(Date dateStopped) {
		this.dateStopped = dateStopped;
	}
	
	/***************************************************************************
	 * Convenience methods
	 **************************************************************************/
	
	/**
	 * Convenience method for obtaining the observation's value as a string If the HouseholdObs is complex,
	 * returns the title of the complexData denoted by the section of getValueComplex() before the
	 * first bar '|' character; or returns the entire getValueComplex() if the bar '|' character is
	 * missing.
	 * 
	 * @param locale locale for locale-specific depictions of value
	 * @should return first part of valueComplex for complex obs
	 * @should return first part of valueComplex for non null valueComplexes
	 */
	public String getValueAsString(Locale locale) {
		//branch on hl7 abbreviations
		if (getConcept() != null) {
			String abbrev = getConcept().getDatatype().getHl7Abbreviation();
			if (abbrev.equals("BIT"))
				return getValueAsBoolean() == null ? "" : getValueAsBoolean().toString();
			else if (abbrev.equals("CWE")) {
				if (getValueCoded() == null)
					return "";
				ConceptName valueCodedName = getValueCodedName();
				if (valueCodedName != null) {
					return valueCodedName.getName();
				} else {
					ConceptName fallbackName = getValueCoded().getName();
					if (fallbackName != null) {
						return fallbackName.getName();
					} else {
						return "";
					}
					
				}
			} else if (abbrev.equals("NM") || abbrev.equals("SN"))
				return getValueNumeric() == null ? "" : getValueNumeric().toString();
			else if (abbrev.equals("DT"))
				return (getValueDatetime() == null ? "" : Format.format(getValueDatetime(), locale, FORMAT_TYPE.DATE));
			else if (abbrev.equals("TM"))
				return (getValueDatetime() == null ? "" : Format.format(getValueDatetime(), locale, FORMAT_TYPE.TIME));
			else if (abbrev.equals("TS"))
				return (getValueDatetime() == null ? "" : Format.format(getValueDatetime(), locale, FORMAT_TYPE.TIMESTAMP));
			else if (abbrev.equals("ST"))
				return getValueText();
		}
		
		// if the datatype is 'unknown', default to just returning what is not null
		if (getValueNumeric() != null)
			return getValueNumeric().toString();
		else if (getValueCoded() != null) {
			ConceptName valudeCodedName = getValueCodedName();
			if (valudeCodedName != null) {
				return valudeCodedName.getName();
			} else {
				return "";
			}
		} else if (getValueDatetime() != null)
			return Format.format(getValueDatetime(), locale, FORMAT_TYPE.DATE);
		else if (getValueText() != null)
			return getValueText();
		/*else if (hasGroupMembers()) {
			// all of the values are null and we're an obs group...so loop
			// over the members and just do a getValueAsString on those
			// this could potentially cause an infinite loop if an obs group
			// is a member of its own group at some point in the hierarchy
			StringBuilder sb = new StringBuilder();
			for (HouseholdObs groupMember : getHouseholdGroupMembers()) {
				if (sb.length() > 0)
					sb.append(", ");
				sb.append(groupMember.getValueAsString(locale));
			}
			return sb.toString();
		}*/
		
		return "";
	}
	
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Sets the value for the obs from a string depending on the datatype of the question concept
	 * 
	 * @param s the string to coerce to a boolean
	 * @should set value as boolean if the datatype of the question concept is boolean
	 * @should fail if the value of the string is null
	 * @should fail if the value of the string is empty
	 */
	public void setValueAsString(String s) throws ParseException {
		if (log.isDebugEnabled())
			log.debug("getConcept() == " + getConcept());
		
		if (getConcept() != null && !StringUtils.isBlank(s)) {
			String abbrev = getConcept().getDatatype().getHl7Abbreviation();
			if (abbrev.equals("BIT")) {
				setValueNumeric(Boolean.valueOf(s) ? 1.0 : 0.0);
			} else if (abbrev.equals("CWE")) {
				throw new RuntimeException("Not Yet Implemented");
			} else if (abbrev.equals("NM") || abbrev.equals("SN")) {
				setValueNumeric(Double.valueOf(s));
			} else if (abbrev.equals("DT") || abbrev.equals("TM") || abbrev.equals("TS")) {
				setValueDatetime(df.parse(s));
			} else if (abbrev.equals("ST")) {
				setValueText(s);
			} else {
				throw new RuntimeException("Don't know how to handle " + abbrev);
			}
			
		} else {
			throw new RuntimeException("concept is null for " + this);
		}
	}
	
	/**
	 * This was a convenience method for obtaining a Map of available locale to observation's value
	 * as a string This method is a waste and should be not be used. This was used in the web layer
	 * because jstl can't pass parameters to a method (${obs.valueAsString[locale]} was used instead
	 * of what would be convenient ${obs.valueAsString(locale)}) Now the openmrs:format tag should
	 * be used in the web layer: <openmrs:format obsValue="${obs}"/>
	 * 
	 * @deprecated
	 */
	public Map<Locale, String> getValueAsString() {
		Map<Locale, String> localeMap = new HashMap<Locale, String>();
		Locale[] locales = Locale.getAvailableLocales(); // ABKTODO: get actual available locales
		for (int i = 0; i < locales.length; i++) {
			localeMap.put(locales[i], getValueAsString(locales[i]));
		}
		return localeMap;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (obsId == null)
			return "obs id is null";
		
		return "HouseholdObs #" + obsId.toString();
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		return getHouseholdObsId();
		
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer id) {
		setHouseholdObsId(id);
		
	}

}
