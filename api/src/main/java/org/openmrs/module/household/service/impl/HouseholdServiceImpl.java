/**
 * 
 */
package org.openmrs.module.household.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.Form;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.household.HouseholdLocationField;
import org.openmrs.module.household.dao.HouseholdDAO;
import org.openmrs.module.household.exception.HouseholdModuleException;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdAttribValue;
import org.openmrs.module.household.model.HouseholdAttribute;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.model.HouseholdLocation;
import org.openmrs.module.household.model.HouseholdLocationEntry;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.model.HouseholdObs;
import org.openmrs.module.household.service.HouseholdService;
import org.openmrs.module.household.util.HouseholdLocationUtil;
import org.springframework.transaction.annotation.Transactional;


/**
 *  @author Ampath Developers
 *
 */
public class HouseholdServiceImpl extends BaseOpenmrsService implements HouseholdService {
	
	protected static final Log log = LogFactory.getLog(HouseholdServiceImpl.class);
	
	private HouseholdDAO householdDAO;
	
	private List<String> fullHouseholdLocationCache;
	
	/**
	 * @param householdDAO the householdDAO to set
	 */
	public void setHouseholdDAO(HouseholdDAO householdDAO) {
		this.householdDAO = householdDAO;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#saveHouseholdDefinition(org.openmrs.module.household.model.HouseholdDefinition)
	 */
	
	public HouseholdDefinition saveHouseholdDefinition(
			HouseholdDefinition householdDefinition) {
		return householdDAO.saveHouseholdDefinition(householdDefinition);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdDefinition(java.lang.Integer)
	 */
	
	public HouseholdDefinition getHouseholdDefinition(Integer id) {
		return householdDAO.getHouseholdDefinition(id);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getAllHouseholdDefinitions()
	 */
	
	public List<HouseholdDefinition> getAllHouseholdDefinitions() {
		return householdDAO.getAllHouseholdDefinitions();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#saveHouseholdGroup(org.openmrs.module.household.model.HouseholdGroups)
	 */
	
	public Household saveHouseholdGroup(Household householdGroups) {
		return householdDAO.saveHouseholdGroup(householdGroups);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdGroup(java.lang.Integer)
	 */
	
	public Household getHouseholdGroup(Integer id) {
		return householdDAO.getHouseholdGroup(id);
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdGroupByIdentifier(java.lang.String)
	 */
	public Household getHouseholdGroupByIdentifier(String householdIdentifier){
		return householdDAO.getHouseholdGroupByIdentifier(householdIdentifier);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getAllHouseholdGroups()
	 */
	
	public List<Household> getAllHouseholdGroups() {
		return householdDAO.getAllHouseholdGroups();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#saveHouseholdMembership(org.openmrs.module.household.model.HouseholdMembership)
	 */
	
	public HouseholdMembership saveHouseholdMembership(HouseholdMembership householdMemberships) {
		return householdDAO.saveHouseholdMembership(householdMemberships);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdMembership(java.lang.Integer)
	 */
	
	public HouseholdMembership getHouseholdMembership(Integer id) {
		return householdDAO.getHouseholdMembership(id);
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdMembershipByUuid(java.lang.String)
	 */
	public List<HouseholdMembership> getHouseholdMembershipByUuid(String householdUuid){
		return householdDAO.getHouseholdMembershipByUuid(householdUuid);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getAllHouseholdMemberships()
	 */
	
	public List<HouseholdMembership> getAllHouseholdMemberships() {
		return householdDAO.getAllHouseholdMemberships();
	}
	
	

	/**
	 * @return the householdDAO
	 */
	public HouseholdDAO getHouseholdDAO() {
		return householdDAO;
	}

	
	public List<HouseholdMembership> getAllHouseholdMembershipsByGroup(Household grp) {
		return householdDAO.getAllHouseholdMembershipsByGroup(grp);
	}
	
	/**
	 * @return the HouseholdMemberships
	 */
	
	public List<HouseholdMembership> getAllHouseholdMembershipsByPerson(Person p) {
		return householdDAO.getAllHouseholdMembershipsByPerson(p);
	}
	/**
	 * Get Household Memberships records by person and by group
	 * 
	 * @return Household Memberships with person and group
	 */
	public List<HouseholdMembership> getHouseholdMembershipByGrpByPsn(Person p,Household grp){
		return householdDAO.getHouseholdMembershipByGrpByPsn(p,grp);
	}
	
	public List<HouseholdMembership> getAllVoidedHouseholdMembershipsByGroup(Household grp){
		return householdDAO.getAllVoidedHouseholdMembershipsByGroup(grp);
	}
	// getting the index person of the household
	public List<HouseholdMembership> getIndexPerson(Integer id) {
		return householdDAO.getIndexPerson(id);
	}
	@Override
	public List<HouseholdMembership> getAllNonVoidedHouseholdMembershipsByGroupNotIndex(Household grp){
		return householdDAO.getAllNonVoidedHouseholdMembershipsByGroupNotIndex(grp);
	}
	@Override
	public List<HouseholdMembership> getHouseholdIndexByGroup(Household grp){
		return householdDAO.getHouseholdIndexByGroup(grp);
	}
//======================================================
	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#saveHouseholdAttribute(org.openmrs.module.household.model.HouseholdAttribute)
	 */
	
	public HouseholdAttribute saveHouseholdAttribute(
			HouseholdAttribute householdAttribute) {
		return householdDAO.saveHouseholdAttribute(householdAttribute);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdAttribute(java.lang.Integer)
	 */
	
	public HouseholdAttribute getHouseholdAttribute(Integer id) {
		return householdDAO.getHouseholdAttribute(id);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getAllHouseholdAttribute()
	 */
	
	public List<HouseholdAttribute> getAllHouseholdAttribute() {
		return householdDAO.getAllHouseholdAttribute();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#saveHouseholdAttribValue(org.openmrs.module.household.model.HouseholdAttribValue)
	 */
	
	public HouseholdAttribValue saveHouseholdAttribValue(
			HouseholdAttribValue householdAttribValue) {
		return householdDAO.saveHouseholdAttribValue(householdAttribValue);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdAttribValue(java.lang.Integer)
	 */
	
	public HouseholdAttribValue getHouseholdAttribValue(Integer id) {
		return householdDAO.getHouseholdAttribValue(id);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getAllHouseholdAttribValue()
	 */
	
	public List<HouseholdAttribValue> getAllHouseholdAttribValue() {
		return householdDAO.getAllHouseholdAttribValue();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#saveHouseholdLocation(org.openmrs.module.household.model.HouseholdLocation)
	 */
	@Override
	public HouseholdLocation saveHouseholdLocation(HouseholdLocation location)
			throws APIException {
		return householdDAO.saveHouseholdLocation(location);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdLocation(java.lang.Integer)
	 */
	@Override
	public HouseholdLocation getHouseholdLocation(Integer locationId)
			throws APIException {
		return householdDAO.getHouseholdLocation(locationId);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdLocation(java.lang.String)
	 */
	@Override
	public HouseholdLocation getHouseholdLocation(String name)
			throws APIException {
		return householdDAO.getHouseholdLocation(name);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getDefaultHouseholdLocation()
	 */
	@Override
	public HouseholdLocation getDefaultHouseholdLocation() throws APIException {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdLocationByUuid(java.lang.String)
	 */
	@Override
	public HouseholdLocation getHouseholdLocationByUuid(String uuid)
			throws APIException {
		return householdDAO.getHouseholdLocationByUuid(uuid);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getAllHouseholdLocations()
	 */
	@Override
	public List<HouseholdLocation> getAllHouseholdLocations()
			throws APIException {
		return householdDAO.getAllHouseholdLocations(true);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getAllHouseholdLocations(boolean)
	 */
	@Override
	public List<HouseholdLocation> getAllHouseholdLocations(
			boolean includeRetired) throws APIException {
		return householdDAO.getAllHouseholdLocations(includeRetired);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdLocations(java.lang.String)
	 */
	@Override
	public List<HouseholdLocation> getHouseholdLocations(String nameFragment)
			throws APIException {
		return getHouseholdLocations(nameFragment, false, null, null);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getHouseholdLocations(java.lang.String, boolean, java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public List<HouseholdLocation> getHouseholdLocations(String nameFragment,
			boolean includeRetired, Integer start, Integer length)
			throws APIException {
		return householdDAO.getHouseholdLocations(nameFragment, includeRetired, start, length);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#retireHouseholdLocation(org.openmrs.module.household.model.HouseholdLocation, java.lang.String)
	 */
	@Override
	public HouseholdLocation retireHouseholdLocation(
			HouseholdLocation location, String reason) throws APIException {
		location.setRetired(true);
		location.setRetireReason(reason);
		return saveHouseholdLocation(location);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#unretireHouseholdLocation(org.openmrs.module.household.model.HouseholdLocation)
	 */
	@Override
	public HouseholdLocation unretireHouseholdLocation(
			HouseholdLocation location) throws APIException {
		location.setRetired(false);
		return saveHouseholdLocation(location);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#purgeHouseholdLocation(org.openmrs.module.household.model.HouseholdLocation)
	 */
	@Override
	public void purgeHouseholdLocation(HouseholdLocation location)
			throws APIException {
		householdDAO.deleteHouseholdLocation(location);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdService#getCountOfHouseholdLocations(java.lang.String, java.lang.Boolean)
	 */
	@Override
	public Integer getCountOfHouseholdLocations(String nameFragment,
			Boolean includeRetired) {
		return householdDAO.getCountOfHouseholdLocations(nameFragment, includeRetired);
	}
	
	/*
	 * Distinct Locations returned
	 * 
	 * @param includeRetired
	 * @return List<HouseholdLocation>
	 */
	public List<HouseholdLocation> getAllHouseholdLocationsByLocation(boolean includeRetired){
		return householdDAO.getAllHouseholdLocationsByLocation(includeRetired);
	}
	
	/*
	 * Distinct SubLocations returned
	 * 
	 * @param includeRetired
	 * @return List<HouseholdLocation>
	 */
	public List<HouseholdLocation> getAllHouseholdLocationsBySubLocation(String location, boolean includeRetired){
		return householdDAO.getAllHouseholdLocationsBySubLocation(location, includeRetired);
	}
	
	/*
	 * Distinct village returned
	 * 
	 * @param includeRetired
	 * @return List<HouseholdLocation>
	 */
	public List<HouseholdLocation> getAllHouseholdLocationsByVillage(String subLocation, String location, boolean includeRetired){
		return householdDAO.getAllHouseholdLocationsByVillage(subLocation, location, includeRetired);
	}
	
	/*
	 * 
	 * @param village
	 * @param subLocation
	 * @param location
	 * @param includeRetired
	 * @return HouseholdLocation
	 */
	public HouseholdLocation getAllHouseholdLocationsByLocSubVil(String village, String subLocation, String location, boolean includeRetired){
		return householdDAO.getAllHouseholdLocationsByLocSubVil(village, subLocation, location, includeRetired);
	}

	
	
	@Transactional(readOnly = true)
	public List<String> getPossibleHouseholdLocationValues(HouseholdLocation householdLocation, String fieldName) {	
		
		HouseholdLocationField field = HouseholdLocationField.getByName(fieldName);
	
		if (field == null) {
			throw new HouseholdModuleException(fieldName + " is not the name of a valid householdLocation field");
		}
		
		return getPossibleHouseholdLocationValues(householdLocation, field);
	}
	
	@Transactional(readOnly = true)
	public List<String> getPossibleHouseholdLocationValues(Map<String,String> householdLocationMap, String fieldName) {		
		return getPossibleHouseholdLocationValues(HouseholdLocationUtil.convertHouseholdLocationMapToHouseholdLocation(householdLocationMap),fieldName);
	}
	
	@Transactional(readOnly = true)
	public List<String> getPossibleHouseholdLocationValues(HouseholdLocation householdLocation, HouseholdLocationField field) {	
		
		Map<String,String> possibleHouseholdLocationValues = new HashMap<String,String>();
		HouseholdLocationLevel targetLevel = null;
		
		// iterate through the ordered levels until we reach the level associated with the specified fieldName
		for (HouseholdLocationLevel level : getOrderedHouseholdLocationLevels(false)) {
			if (level.getHouseholdLocationField() != null && level.getHouseholdLocationField().equals(field)) {
				targetLevel = level;
				break;
			}
		}
		
		if (targetLevel == null) {
			log.error("HouseholdLocation field " + field + " is not mapped to household Location level.");
			return null;
		}
		
		// calls getPossibleHouseholdLocationEntries(HouseholdLocation, HouseholdLocationLevel) to perform the actual search
		List<HouseholdLocationEntry> entries = getPossibleHouseholdLocationEntries(householdLocation, targetLevel);
		
		// note that by convention entries should not be null, so we don't test for null here
		
		// take the entries returns and convert them into a list of *unique* names (using case-insensitive comparison)
		// we use a map here to make this process more efficient
		for (HouseholdLocationEntry entry : entries) {
			// see if there is already key for this entry name (converted to lower-case)
			if(!possibleHouseholdLocationValues.containsKey(entry.getName().toLowerCase())) {
				// if not, add the key/value pair for this entry name, where the value equals the entry name,
				// and the key is the entry name converted to lower-case
				possibleHouseholdLocationValues.put(entry.getName().toLowerCase(), entry.getName());
			}
		}
		
		List<String> results = new ArrayList<String>();
		results.addAll(possibleHouseholdLocationValues.values());
		return results;
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationEntry> getPossibleHouseholdLocationEntries(HouseholdLocation householdLocation, HouseholdLocationLevel targetLevel) {
		
		// split the levels into levels before and after the level associated with the field name
		Boolean reachedFieldLevel = false;
		List<HouseholdLocationLevel> higherLevels = new ArrayList<HouseholdLocationLevel>();
		List<HouseholdLocationLevel> lowerLevels = new ArrayList<HouseholdLocationLevel>();
		
		for (HouseholdLocationLevel level : getOrderedHouseholdLocationLevels()) {
			if (reachedFieldLevel) {
				lowerLevels.add(level);
			}
			else {
				higherLevels.add(level);
			}
			if (level.equals(targetLevel)) {
				lowerLevels.add(level);  // we want the target level in both the higher and lower level lists
				reachedFieldLevel = true;
			}
		}
		
		if (!reachedFieldLevel) {
			// if we haven't found an household Location level associated with this field, then we certainly aren't going to be
			// able to find a list of possible values
			// return an empty set here, not null, because null is the default method in core if not overridden
			return new ArrayList<HouseholdLocationEntry>();
		}
		
		List<HouseholdLocationEntry> possibleEntries = new ArrayList<HouseholdLocationEntry>();
		
		// first handle the top level
		HouseholdLocationLevel topLevel= higherLevels.remove(0);
		String topLevelValue = topLevel.getHouseholdLocationField() != null ? HouseholdLocationUtil.getHouseholdLocationFieldValue(householdLocation, topLevel.getHouseholdLocationField()) : null;
		
		// if we have a top level value, find the top-level entry that matches that value
		if (StringUtils.isNotBlank(topLevelValue)) {
			possibleEntries.add(getChildHouseholdLocationEntryByName(null, topLevelValue));
		}
		// otherwise, get all the entries at the top level
		else {
			possibleEntries.addAll(getHouseholdLocationEntriesAtTopLevel());
		}
		
		// now go through all the other levels above the level we are looking for
		for (HouseholdLocationLevel level : higherLevels) {
			List<HouseholdLocationEntry> possibleEntriesAtNextLevel = new ArrayList<HouseholdLocationEntry>();
			
			// find the value of the household Location field at the level we are dealing with
			String levelValue = level.getHouseholdLocationField() != null ? HouseholdLocationUtil.getHouseholdLocationFieldValue(householdLocation, level.getHouseholdLocationField()) : null;
			
			// loop through all the possible entries
			for (HouseholdLocationEntry entry : possibleEntries) {
				// if a value has been specified, we only want child entries that match that value
				if(StringUtils.isNotBlank(levelValue)) {
					HouseholdLocationEntry childEntry = getChildHouseholdLocationEntryByName(entry, levelValue);
					if (childEntry != null) {
						possibleEntriesAtNextLevel.add(childEntry);
					}
				}
				// otherwise, we need to add all children of the possible entry
				else {
					possibleEntriesAtNextLevel.addAll(getChildHouseholdLocationEntries(entry));
				}
			}
			// now continue the loop and move on to the next level
			possibleEntries = possibleEntriesAtNextLevel;
		}
		
		// assign the possible entry to results array
		List<HouseholdLocationEntry> results = possibleEntries;
	
		// now we need to handle the any household Location field values that may have been specified *below* the field
		// we are looking for in the hierarchy
		
		// we need to loop through the results in reverse and find any fields that have values
		Collections.reverse(lowerLevels);
		Iterator<HouseholdLocationLevel> i = lowerLevels.iterator();
		
		possibleEntries = null;
		
		while (i.hasNext()) {
			HouseholdLocationLevel level = i.next();
			
			String levelValue = level.getHouseholdLocationField() != null ? HouseholdLocationUtil.getHouseholdLocationFieldValue(householdLocation, level.getHouseholdLocationField()) : null;
			
			// we are looking for the first level with a value
			if (StringUtils.isNotBlank(levelValue)) {
				possibleEntries = getHouseholdLocationEntriesByLevelAndName(level, levelValue);		
				break;
			}
		}
		
		// if we haven't found any possible lower level entries, then we can just return the possibleEntries we calculated by higher level
		if (possibleEntries == null) {
			return results;
		}
		
		// now that we've go something to start with, we need to work our way back up the tree 
		while (i.hasNext()) {
			
			HouseholdLocationLevel level = i.next();
			
			String levelValue = level.getHouseholdLocationField() != null ? HouseholdLocationUtil.getHouseholdLocationFieldValue(householdLocation, level.getHouseholdLocationField()) : null;
			
			List<HouseholdLocationEntry> possibleEntriesAtNextLevel = new ArrayList<HouseholdLocationEntry>();
			
			for (HouseholdLocationEntry entry : possibleEntries) {
				HouseholdLocationEntry parentEntry = entry.getParent();
				
				if (parentEntry != null) {
					possibleEntriesAtNextLevel.add(parentEntry);
				}	
			}
			
			// if we have a value restriction here, remove any entries that don't fit the restriction
			if (StringUtils.isNotBlank(levelValue)) {
				Iterator<HouseholdLocationEntry> j = possibleEntriesAtNextLevel.iterator();
				while (j.hasNext()) {
					HouseholdLocationEntry entry = j.next();
					if (!entry.getName().equalsIgnoreCase(levelValue)) {
						j.remove();
					}
				}
			}
			
			possibleEntries = possibleEntriesAtNextLevel;
		}
		
		// do a union of the results from the higher and lower level tests
		if (results.retainAll(possibleEntries));
		
		return results;
	}
	
	@Transactional(readOnly = true)
	public List<String> getPossibleFullHouseholdLocations(String searchString) {
		
		// if search string isempty or null, return empty list
		if (StringUtils.isBlank(searchString)) {
			return new ArrayList<String>();
		}
		
		// refresh the cache where all the full household Locations are stored, if needed
		if (this.fullHouseholdLocationCache == null || this.fullHouseholdLocationCache.size() == 0) {
			buildFullHouseholdLocationCache();
		}
		
		List<String> results = new ArrayList<String>();
		
		// first remove all characters that are not alphanumerics or whitespace
		// (more specifically, this pattern matches sets of 1 or more characters that are both non-word (\W) and non-whitespace (\S))
		searchString = Pattern.compile("[\\W&&\\S]+").matcher(searchString).replaceAll("");
				
		// split the search string into words
		String [] words = searchString.split(" ");
		
		// another sanity check; return an empty string if nothing to search on
		if (words.length == 0 || StringUtils.isBlank(words[0])) {
			return new ArrayList<String>();
		}
		
		// find all household Locations in the full household Location cache that contain the first word in the search string
		Pattern p = Pattern.compile(Pattern.quote(words[0]), Pattern.CASE_INSENSITIVE);
		for (String householdLocation : fullHouseholdLocationCache) {
			if (p.matcher(householdLocation).find()) {
				results.add(householdLocation);
			}
		}
		
		// now go through and remove from the results list any household Locations that don't contain the other words in the search string
		if (words.length > 1) {
			for (String word : Arrays.copyOfRange(words, 1, words.length)) {
				Iterator<String> i = results.iterator();
				p = Pattern.compile(Pattern.quote(word), Pattern.CASE_INSENSITIVE);
				while (i.hasNext()) {
					if (!p.matcher(i.next()).find()) {
						i.remove();
					}
				}
			}
		}
 		
		return results;
	}
	
	@Transactional(readOnly = true)
	public Integer getHouseholdLocationEntryCount() {
		return householdDAO.getHouseholdLocationEntryCount();
	}
	
	@Transactional(readOnly = true)
	public Integer getHouseholdLocationEntryCountByLevel(HouseholdLocationLevel level) {
		return householdDAO.getHouseholdLocationEntryCountByLevel(level);
	}
	
	@Transactional(readOnly = true)
	public HouseholdLocationEntry getHouseholdLocationEntry(int householdLocationId) {
		return householdDAO.getHouseholdLocationEntry(householdLocationId);
	}
	
	@Transactional(readOnly = true)
	public HouseholdLocationEntry getHouseholdLocationEntryByUserGenId(String userGeneratedId) {
		return householdDAO.getHouseholdLocationEntryByUserGenId(userGeneratedId);
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationEntry> getHouseholdLocationEntriesByLevel(HouseholdLocationLevel level) {
		return householdDAO.getHouseholdLocationEntriesByLevel(level);
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationEntry> getHouseholdLocationEntriesByLevelAndName(HouseholdLocationLevel level, String name) {
		return householdDAO.getHouseholdLocationEntriesByLevelAndName(level, name);
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationEntry> getHouseholdLocationEntriesAtTopLevel() {
		return getHouseholdLocationEntriesByLevel(getTopHouseholdLocationLevel());
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationEntry> getChildHouseholdLocationEntries(HouseholdLocationEntry entry) {
		if (entry != null) {
			return householdDAO.getChildHouseholdLocationEntries(entry);
		}
		else {
			return getHouseholdLocationEntriesAtTopLevel();
		}
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationEntry> getChildHouseholdLocationEntries(Integer entryId) {
		HouseholdLocationEntry entry = getHouseholdLocationEntry(entryId);
		
		if (entry == null) {
			throw new HouseholdModuleException("Invalid Household Location Entry Id " + entryId);
		}
		
		return getChildHouseholdLocationEntries(entry);
	}
	
	@Transactional(readOnly = true)
	public HouseholdLocationEntry getChildHouseholdLocationEntryByName(HouseholdLocationEntry entry, String childName) {
		if (entry != null) {
			return householdDAO.getChildHouseholdLocationEntryByName(entry, childName);
		}
		else {
			List<HouseholdLocationEntry> entries = householdDAO.getHouseholdLocationEntriesByLevelAndName(getTopHouseholdLocationLevel(), childName);
			if (entries.size() == 0) {
				return null;
			}
			else if (entries.size() == 1) {
				return entries.get(0);
			}
			else {
				throw new HouseholdModuleException("Two or more top-level entries have the same name");
			}
		}
	} 
	
	@Transactional
	public void saveHouseholdLocationEntry(HouseholdLocationEntry entry) {
		householdDAO.saveHouseholdLocationEntry(entry);
		resetFullHouseholdLocationCache();
	}
	
	@Transactional
	public void saveHouseholdLocationEntries(List<HouseholdLocationEntry> entries) {
		for (HouseholdLocationEntry entry : entries) {
			householdDAO.saveHouseholdLocationEntry(entry);
		}
		resetFullHouseholdLocationCache();
	}
	
	@Transactional
	public void deleteAllHouseholdLocationEntries() {
		householdDAO.deleteAllHouseholdLocationEntries();
		resetFullHouseholdLocationCache();
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationLevel> getOrderedHouseholdLocationLevels() {
		return getOrderedHouseholdLocationLevels(true, true);
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationLevel> getOrderedHouseholdLocationLevels(Boolean includeUnmapped) {	
		return getOrderedHouseholdLocationLevels(includeUnmapped, true);
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationLevel> getOrderedHouseholdLocationLevels(Boolean includeUnmapped, Boolean includeEmptyLevels) {
		List<HouseholdLocationLevel> levels = new ArrayList<HouseholdLocationLevel>();
		
		// first, get the top level level
		HouseholdLocationLevel level = getTopHouseholdLocationLevel();
		
		if (level != null) {
			// add the top level to this list
			if ((includeUnmapped == true || level.getHouseholdLocationField() != null) 
					&& (includeEmptyLevels == true ||  getHouseholdLocationEntryCountByLevel(level) > 0)) {
				levels.add(level);
			}
				
			// now fetch the children in order
			while (getChildHouseholdLocationLevel(level) != null) {
				level = getChildHouseholdLocationLevel(level);
				if ((includeUnmapped == true || level.getHouseholdLocationField() != null) 
						&& (includeEmptyLevels == true ||  getHouseholdLocationEntryCountByLevel(level) > 0)) {	
					levels.add(level);
				}
			}
		}
		
		return levels;
	}
	
	@Transactional(readOnly = true)
	public List<HouseholdLocationLevel> getHouseholdLocationLevels() {
		return householdDAO.getHouseholdLocationLevels();
	}
	
	@Transactional(readOnly = true)
	public Integer getHouseholdLocationLevelsCount() {
		List<HouseholdLocationLevel> levels = getHouseholdLocationLevels();
		return levels != null ? levels.size() : 0;
	}
	
	@Transactional(readOnly = true)
	public HouseholdLocationLevel getTopHouseholdLocationLevel() {
		return householdDAO.getTopHouseholdLocationLevel();
	}
	
	@Transactional(readOnly = true)
    public HouseholdLocationLevel getBottomHouseholdLocationLevel() {
		
		// get the ordered list
		List<HouseholdLocationLevel> levels = getOrderedHouseholdLocationLevels();
		
		// return the last member in the list
		if (levels != null && levels.size() > 0) {
			return levels.get(levels.size() - 1);
		}
		else {
			return null;
		}
    }
	
	@Transactional(readOnly = true)
	public HouseholdLocationLevel getHouseholdLocationLevel(Integer levelId) {
		return householdDAO.getHouseholdLocationLevel(levelId);
	}
	
	@Transactional(readOnly = true)
    public HouseholdLocationLevel getChildHouseholdLocationLevel(HouseholdLocationLevel level) {
	    return householdDAO.getHouseholdLocationLevelByParent(level);
    }
	
	@Transactional
	public HouseholdLocationLevel addHouseholdLocationLevel() {
		HouseholdLocationLevel newLevel = new HouseholdLocationLevel();
		newLevel.setParent(getBottomHouseholdLocationLevel());
		// need to call the service method through the context to take care of AOP
		Context.getService(HouseholdService.class).saveHouseholdLocationLevel(newLevel);
		return newLevel;
	}
	
	@Transactional
	public void saveHouseholdLocationLevel(HouseholdLocationLevel level) {
		householdDAO.saveHouseholdLocationLevel(level);
	}
	
	@Transactional
    public void deleteHouseholdLocationLevel(HouseholdLocationLevel level) {
    	householdDAO.deleteHouseholdLocationLevel(level);  
    }
	
	@Transactional
	public void setHouseholdLocationLevelParents() {
		// iterate through the levels
		for (HouseholdLocationLevel level : getHouseholdLocationLevels()) {
			
			if (getHouseholdLocationEntryCountByLevel(level) > 0) {
				// get an entry for this level
				HouseholdLocationEntry entry = getHouseholdLocationEntriesByLevel(level).get(0);
				// if needed, set the parent for this level based on the level of the parent of this entry
				if (entry.getParent() != null && entry.getParent().getLevel() != level.getParent()) {
					level.setParent(entry.getParent().getLevel());
					// need to call the service method through the context to take care of AOP
					Context.getService(HouseholdService.class).saveHouseholdLocationLevel(level);
				}
			}
			// if is level has no entries and no parents, delete it
			else if (level.getParent() == null){
				// need to call the service method through the context to take care of AOP
				Context.getService(HouseholdService.class).deleteHouseholdLocationLevel(level);
			}
		}
	}
	
	/**
	 * Utility methods
	 */
	
	/**
	 * Builds a list of pipe-delimited strings that represents all the possible full household Locations,
	 * and stores this in a local cache for use by the getPossibleFullHouseholdLocations(String) method
	 * A full household Location is represented as a pipe-delimited string of household Location entry names,
	 * ordered from the entry at the highest level to the entry at the lowest level in the tree.
	 * For example, the full household Location for the Beacon Hill neighborhood in the city of Turbo might be:
	 * "Kenya|Rift Valley|Uasin-Gishu|Turbo|Turbo South"
	 *  
	 */
	private void buildFullHouseholdLocationCache() {
		
		// TODO: reset the cache every time the household Location entries are changed?
		
		this.fullHouseholdLocationCache = new ArrayList<String>();
		
		List<HouseholdLocationLevel> levels = getOrderedHouseholdLocationLevels(true,false);
		HouseholdLocationLevel bottomLevel = levels.get(levels.size() - 1);
		
		// go through all the entries at the bottom level of the hierarchy
		for (HouseholdLocationEntry bottomLevelEntry : getHouseholdLocationEntriesByLevel(bottomLevel)) {	
			StringBuilder householdLocation = new StringBuilder();
			householdLocation.append(bottomLevelEntry.getName());
			
			HouseholdLocationEntry entry = bottomLevelEntry;
			
			// follow back up the tree to the top level and concatenate the names to create the full householdLocation string
			while (entry.getParent() != null) {
				entry = entry.getParent();
				householdLocation.insert(0, entry.getName() + "|");
			}
			
			this.fullHouseholdLocationCache.add(householdLocation.toString());
		}
		
	}
	
	private void resetFullHouseholdLocationCache() {
		this.fullHouseholdLocationCache = null;
	}
	
	
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////////////////
	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#saveHouseholdEncounter(org.openmrs.module.household.model.HouseholdEncounter)
	 */
	
	

	public HouseholdEncounter saveHouseholdEncounter(
			HouseholdEncounter householdEncounter) {
		return householdDAO.saveHouseholdEncounter(householdEncounter);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getHouseholdEncounter(java.lang.Integer)
	 */
	
	public HouseholdEncounter getHouseholdEncounter(Integer id) {
		return householdDAO.getHouseholdEncounter(id);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getHouseholdEncounterByUUID(java.lang.Integer)
	 */
	
	public HouseholdEncounter getHouseholdEncounterByUUID(String uuid) {
		return householdDAO.getHouseholdEncounterByUUID(uuid);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getAllHouseholdEncountersByHousehold(org.openmrs.module.household.model.Household)
	 */
	
	public List<HouseholdEncounter> getAllHouseholdEncountersByHousehold(
			Household household) {
		return householdDAO.getAllHouseholdEncountersByHouseholdId(household.getId());
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getAllHouseholdEncountersByHouseholdId(java.lang.Integer)
	 */
	
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdId(
			Integer id) {
		return householdDAO.getAllHouseholdEncountersByHouseholdId(id);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getAllHouseholdEncountersByHouseholdIdentifiers(java.lang.String)
	 */
	
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdIdentifiers(
			String identifier) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getHouseholdEncounters(org.openmrs.module.household.model.Household, org.openmrs.module.household.model.HouseholdLocation, java.util.Date, java.util.Date, java.util.Collection, java.util.Collection, java.util.Collection, boolean)
	 */
	
	public List<HouseholdEncounter> getHouseholdEncounters(Household household,
			HouseholdLocation loc, Date fromDate, Date toDate,
			Collection<Form> enteredViaForms,
			Collection<HouseholdEncounterType> householdEncounterTypes,
			Collection<User> providers, boolean includeVoided) {
		return householdDAO.getHouseholdEncounters(household, loc, fromDate, toDate, enteredViaForms, householdEncounterTypes, providers, includeVoided);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#voidHouseholdEncounter(org.openmrs.module.household.model.HouseholdEncounter, java.lang.String)
	 */
	
	public HouseholdEncounter voidHouseholdEncounter(
			HouseholdEncounter householdEncounter, String reason) {
		if (reason == null)
			throw new IllegalArgumentException("The argument 'reason' is required and so cannot be null");
		
		for (HouseholdObs o : householdEncounter.getHouseholdObsAtTopLevel(false)) {
			if (!o.isVoided()) {
				voidHouseholdObs(o, reason);
			}
		}
		
		householdEncounter.setVoided(true);
		householdEncounter.setVoidedBy(Context.getAuthenticatedUser());
		householdEncounter.setDateVoided(new Date());
		householdEncounter.setVoidReason(reason);
		saveHouseholdEncounter(householdEncounter);
		return householdEncounter;
	}

	/* (non-Javadoc
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#unvoidHouseholdEncounter(org.openmrs.module.household.model.HouseholdEncounter)
	 */
	
	public HouseholdEncounter unvoidHouseholdEncounter(
			HouseholdEncounter householdEncounter) throws APIException {
		String voidReason = householdEncounter.getVoidReason();
		if (voidReason == null)
			voidReason = "";
		
		for (HouseholdObs o : householdEncounter.getHouseholdObsAtTopLevel(true)) {
			if (voidReason.equals(o.getVoidReason()))
				unvoidObs(o);
		}
		
		householdEncounter.setVoided(false);
		householdEncounter.setVoidedBy(null);
		householdEncounter.setDateVoided(null);
		householdEncounter.setVoidReason(null);
		saveHouseholdEncounter(householdEncounter);
		return householdEncounter;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#purgeHouseholdEncounter(org.openmrs.module.household.model.HouseholdEncounter)
	 */
	
	public void purgeHouseholdEncounter(HouseholdEncounter householdEncounter)
			throws APIException {
		householdDAO.deleteHouseholdEncounter(householdEncounter);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#purgeHouseholdEncounter(org.openmrs.module.household.model.HouseholdEncounter, boolean)
	 */
	
	public void purgeHouseholdEncounter(HouseholdEncounter householdEncounter,
			boolean cascade) throws APIException {
		if (cascade) {
			List<HouseholdEncounter> justThisEncounter = new ArrayList<HouseholdEncounter>();
			justThisEncounter.add(householdEncounter);
			List<HouseholdObs> observations = new Vector<HouseholdObs>();
			observations.addAll(getObservations(null, justThisEncounter, null, null, null, null, null,
			    null, null, null, true));
			for (HouseholdObs o : observations) {
				purgeHouseholdObs(o);
			}
		}
		purgeHouseholdEncounter(householdEncounter);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#saveHouseholdEncounterType(org.openmrs.module.household.model.HouseholdEncounterType)
	 */
	
	public HouseholdEncounterType saveHouseholdEncounterType(
			HouseholdEncounterType encounterType) {
		householdDAO.saveHouseholdEncounterType(encounterType);
		return encounterType;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getHouseholdEncounterType(java.lang.Integer)
	 */
	
	public HouseholdEncounterType getHouseholdEncounterType(
			Integer encounterTypeId) throws APIException {
		return householdDAO.getHouseholdEncounterType(encounterTypeId);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getHouseholdEncounterTypeByUuid(java.lang.String)
	 */
	
	public HouseholdEncounterType getHouseholdEncounterTypeByUuid(String uuid)
			throws APIException {
		return householdDAO.getHouseholdEncounterTypeByUuid(uuid);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getAllHouseholdEncounterTypes()
	 */
	
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes()
			throws APIException {
		return householdDAO.getAllHouseholdEncounterTypes(true);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getAllHouseholdEncounterTypes(boolean)
	 */
	
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes(
			boolean includeRetired) throws APIException {
		return householdDAO.getAllHouseholdEncounterTypes(includeRetired);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#findHouseholdEncounterTypes(java.lang.String)
	 */
	
	public List<HouseholdEncounterType> findHouseholdEncounterTypes(String name)
			throws APIException {
		return householdDAO.findHouseholdEncounterTypes(name);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#retireHouseholdEncounterType(org.openmrs.module.household.model.HouseholdEncounterType, java.lang.String)
	 */
	
	public HouseholdEncounterType retireHouseholdEncounterType(
			HouseholdEncounterType encounterType, String reason)
			throws APIException {
		if (reason == null)
			throw new IllegalArgumentException("The 'reason' argument is required");
		
		encounterType.setRetired(true);
		encounterType.setRetireReason(reason);
		return saveHouseholdEncounterType(encounterType);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#unretireEncounterType(org.openmrs.module.household.model.HouseholdEncounterType)
	 */
	
	public HouseholdEncounterType unretireEncounterType(
			HouseholdEncounterType encounterType) throws APIException {
		encounterType.setRetired(false);
		return saveHouseholdEncounterType(encounterType);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#purgeHouseholdEncounterType(org.openmrs.module.household.model.HouseholdEncounterType)
	 */
	
	public void purgeHouseholdEncounterType(HouseholdEncounterType encounterType)
			throws APIException {
		householdDAO.deleteHouseholdEncounterType(encounterType);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getHouseholdEncountersByHousehold(java.lang.String, boolean)
	 */
	
	public List<HouseholdEncounter> getHouseholdEncountersByHousehold(
			String query, boolean includeVoided) throws APIException {
		if (query == null)
			throw new IllegalArgumentException("The 'query' parameter is required and cannot be null");
		
		//return householdDAO.getEncounters(query, null, null, includeVoided);
		//TODO Handle here
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getHouseholdEncounters(java.lang.String, java.lang.Integer, java.lang.Integer, boolean)
	 */
	
	public List<HouseholdEncounter> getHouseholdEncounters(String query,
			Integer start, Integer length, boolean includeVoided)
			throws APIException {
		//return householdDAO.getHouseholdEncounters(query, null, null, includeVoided);
		//TODO Handle this
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getHouseholdObs(java.lang.Integer)
	 */
	
	public HouseholdObs getHouseholdObs(Integer obsId) throws APIException {
		return householdDAO.getHouseholdObs(obsId);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getHouseholdObsByUuid(java.lang.String)
	 */
	
	public HouseholdObs getHouseholdObsByUuid(String uuid) throws APIException {
		return householdDAO.getHouseholdObsByUuid(uuid);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#saveHouseholdObs(org.openmrs.module.household.model.HouseholdObs, java.lang.String)
	 */
	
	public HouseholdObs saveHouseholdObs(HouseholdObs obs, String changeMessage)
			throws APIException {
		
		if (obs.getId() == null) {
			Context.requirePrivilege("Manage Household Obs");
			return householdDAO.saveHouseholdObs(obs);
		} else {
			Context.requirePrivilege("Manage Household Obs");
			
			if (changeMessage == null)
				throw new APIException("ChangeMessage is required when updating an obs in the database");
			
			// get a copy of the passed in obs and save it to the
			// database. This allows us to create a new row and new obs_id
			// this method doesn't copy the obs_id
			HouseholdObs newObs = HouseholdObs.newInstance(obs);
			
			// unset any voided properties on the new obs
			newObs.setVoided(false);
			newObs.setVoidReason(null);
			newObs.setDateVoided(null);
			newObs.setVoidedBy(null);
			// unset the creation stats
			newObs.setCreator(null);
			newObs.setDateCreated(null);
			
			//TODO Re-write: 
			//RequiredDataAdvice.recursivelyHandle(SaveHandler.class, newObs, changeMessage);
			
			// save the new row to the database with the changes that
			// have been made to it
			householdDAO.saveHouseholdObs(newObs);
			
			// void out the original observation to keep it around for
			// historical purposes
			try {
				Context.addProxyPrivilege("Manage Household Obs");
				String reason = changeMessage + " (new obsId: " + newObs.getId() + ")";
				
				// fetch a clean copy of this obs from the database so that
				// we don't write the changes to the database when we save
				// the fact that the obs is now voided
				Context.evictFromSession(obs);
				obs = getHouseholdObs(obs.getId());
				
				// calling this via the service so that AOP hooks are called
				voidHouseholdObs(obs, reason);
				
			}
			finally {
				Context.removeProxyPrivilege("Manage Household Obs");
			}
			
			return newObs;
		}
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#voidHouseholdObs(org.openmrs.module.household.model.HouseholdObs, java.lang.String)
	 */
	
	public HouseholdObs voidHouseholdObs(HouseholdObs obs, String reason)
			throws APIException {
		return householdDAO.saveHouseholdObs(obs);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#unvoidObs(org.openmrs.module.household.model.HouseholdObs)
	 */
	
	public HouseholdObs unvoidObs(HouseholdObs obs) throws APIException {
		return householdDAO.saveHouseholdObs(obs);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#purgeHouseholdObs(org.openmrs.module.household.model.HouseholdObs)
	 */
	
	public void purgeHouseholdObs(HouseholdObs obs) throws APIException {
		purgeHouseholdObs(obs, false);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#purgeHouseholdObs(org.openmrs.module.household.model.HouseholdObs, boolean)
	 */
	
	public void purgeHouseholdObs(HouseholdObs obs, boolean cascade)
			throws APIException {
		if (cascade) {
			throw new APIException("Cascading purge of obs not yet implemented");
		}
		
		householdDAO.deleteHouseholdObs(obs);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getObservationsByHousehold(org.openmrs.module.household.model.Household)
	 */
	
	public List<HouseholdObs> getObservationsByHousehold(Household household) {
		List<Household> hh = new Vector<Household>();
		hh.add(household);
		return getObservations(hh, null, null, null, null, null, null, null, null, null, false);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getObservations(java.util.List, java.util.List, java.util.List, java.util.List, java.util.List, java.util.List, java.lang.Integer, java.lang.Integer, java.util.Date, java.util.Date, boolean)
	 */
	
	public List<HouseholdObs> getObservations(List<Household> household,
			List<HouseholdEncounter> encounters, List<Concept> questions,
			List<Concept> answers, List<HouseholdLocation> locations,
			List<String> sort, Integer mostRecentN, Integer obsGroupId,
			Date fromDate, Date toDate, boolean includeVoidedObs)
			throws APIException {
		if (sort == null)
			sort = new Vector<String>();
		if (sort.isEmpty())
			sort.add("obsDatetime");
		
		return householdDAO.getObservations(household, encounters, questions, answers,  locations, sort, mostRecentN,
		    obsGroupId, fromDate, toDate, includeVoidedObs);
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.service.HouseholdEncounterService#getObservationsByHouseholdAndConcept(org.openmrs.module.household.model.Household, org.openmrs.Concept)
	 */
	
	public List<HouseholdObs> getObservationsByHouseholdAndConcept(
			Household household, Concept question) throws APIException {
		List<Household> hh = new Vector<Household>();
		if (household != null && household.getId() != null)
			hh.add(household);
		List<Concept> questions = new Vector<Concept>();
		questions.add(question);
		
		return getObservations(hh, null, questions, null, null, null, null, null, null, null, false);
	}

	
}

