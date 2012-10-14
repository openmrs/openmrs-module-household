/**
 * 
 */
package org.openmrs.module.household.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.openmrs.Concept;
import org.openmrs.Form;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.household.HouseholdLocationField;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdAttribValue;
import org.openmrs.module.household.model.HouseholdAttribute;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdDefinitionParent;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.model.HouseholdLocation;
import org.openmrs.module.household.model.HouseholdLocationEntry;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.model.HouseholdObs;
import org.openmrs.module.household.util.HouseholdConstants;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ampath Developers
 * 
 */
@Transactional
public interface HouseholdService extends OpenmrsService {

	/**
	 * Save one Household Definition object to the database
	 * 
	 * @param HouseholdDefinition
	 *            HouseholdDefinition object to be saved
	 * @return saved HouseholdDefinition object
	 */
	public HouseholdDefinition saveHouseholdDefinition(
			HouseholdDefinition householdDefinition);

	/**
	 * Get one HouseholdDefinition record based on the household id
	 * 
	 * @param id
	 *            the HouseholdDefinition id
	 * @return HouseholdDefinition that match the id
	 */
	@Transactional(readOnly = true)
	public HouseholdDefinition getHouseholdDefinition(Integer id);

	/**
	 * Get one HouseholdDefinition record based on the hd
	 * 
	 * @param id
	 *            the HouseholdDefinition id
	 * @return HouseholdDefinition that match the id
	 */
	@Transactional(readOnly = true)
	public HouseholdDefinition getHouseholdDefinition(HouseholdDefinition hd);

	/**
	 * Get one HouseholdDefinition record based on the household def
	 * 
	 * @param def
	 *            the HouseholdDefinition def
	 * @return HouseholdDefinition that match the def
	 */
	@Transactional(readOnly = true)
	public HouseholdDefinition getHouseholdDefinition(String def);

	/**
	 * Get one HouseholdDefinition record based on the household uuid
	 * 
	 * @param strUuid
	 *            the HouseholdDefinition strUuid
	 * @return HouseholdDefinition that match the strUuid
	 */
	@Transactional(readOnly = true)
	public HouseholdDefinition getHouseholdDefinitionByUuid(String strUuid);

	/**
	 * Get all HouseholdDefinition records
	 * 
	 * @return all HouseholdDefinition record in the system
	 */
	@Transactional(readOnly = true)
	public List<HouseholdDefinition> getAllHouseholdDefinitions();

	/**
	 * Get HouseholdDefinition by code
	 * 
	 * @return HouseholdDefinition record by code
	 */
	public HouseholdDefinition getHouseholdDefinitionByCode(String code);

	/**
	 * Get all HouseholdDefinition records by hd
	 * 
	 * @return all HouseholdDefinition record in the system that are children of
	 *         hd
	 */
	@Transactional(readOnly = true)
	public List<HouseholdDefinition> getHouseholdDefinitionParentChildren(
			HouseholdDefinitionParent hdp);

	public boolean purgeHouseholdDefinition(HouseholdDefinition hd);

	/**
	 * Save one HouseholdGroups object to the database
	 * 
	 * @param householdGroups
	 *            HouseholdGroups object to be saved
	 * @return saved household object
	 */
	public Household saveHouseholdGroup(Household householdGroups);

	/**
	 * Get one HouseholdGroup record based on the HouseholdGroups id
	 * 
	 * @param id
	 *            the HouseholdGroups id
	 * @return HouseholdGroup that match the id
	 */
	@Transactional(readOnly = true)
	public Household getHouseholdGroup(Integer id);

	/**
	 * Get one HouseholdGroup record based on the HouseholdGroups
	 * householdIdentifier
	 * 
	 * @param householdIdentifier
	 *            the HouseholdGroups householdIdentifier
	 * @return HouseholdGroup that match the householdIdentifier
	 */
	public Household getHouseholdGroupByIdentifier(String householdIdentifier);

	/**
	 * Get all HouseholdGroups records
	 * 
	 * @return all HouseholdGroups record in the system
	 */
	@Transactional(readOnly = true)
	public List<Household> getAllHouseholdGroups();

	/**
	 * Get all Household records via definition uuid
	 * 
	 * @return all Households in the system with the matching definitions uuid
	 */
	@Transactional(readOnly = true)
	public List<Household> getAllHouseholdsByDefinitionUuid(
			String definitionUuid);

	/**
	 * Save one HouseholdMembership object to the database
	 * 
	 * @param householdMemberships
	 *            HouseholdMemberships object to be saved
	 * @return saved HouseholdMemberships object
	 */
	public HouseholdMembership saveHouseholdMembership(
			HouseholdMembership householdMemberships);

	/**
	 * Get one Household Membership record based on the HouseholdMembership id
	 * 
	 * @param id
	 *            the HouseholdMembership id
	 * @return HouseholdMembership that match the id
	 */
	@Transactional(readOnly = true)
	public HouseholdMembership getHouseholdMembership(Integer id);

	/**
	 * Get Household Memberships records given household
	 * 
	 * @return Household Memberships with household
	 * 
	 */
	public List<HouseholdMembership> getHouseholdMembershipByHousehold(
			Household household);

	/**
	 * Get all Household Memberships records
	 * 
	 * @return all Household Memberships in the system
	 */
	@Transactional(readOnly = true)
	public List<HouseholdMembership> getAllHouseholdMemberships();

	/**
	 * Get Household Memberships records given Person
	 * 
	 * @return Household Memberships with Person
	 * 
	 */
	@Transactional(readOnly = true)
	public List<HouseholdMembership> getHouseholdIndexByGroup(Household grp);

	/**
	 * get the household memberships by group who exclude headship
	 */
	@Transactional(readOnly = true)
	public List<HouseholdMembership> getAllNonVoidedHouseholdMembershipsByGroupNotIndex(
			Household grp);

	/**
	 * Get the index person of the household
	 * 
	 * @return return the index person of the household
	 */
	@Transactional(readOnly = true)
	public List<HouseholdMembership> getIndexPerson(Integer id);

	/**
	 * Get voided Household Memberships records given ID
	 * 
	 * @return voided Household Memberships with ID
	 */
	@Transactional(readOnly = true)
	public List<HouseholdMembership> getAllVoidedHouseholdMembershipsByGroup(
			Household grp);

	/**
	 * Get Household Memberships records given ID
	 * 
	 * @return Household Memberships with ID
	 */
	@Transactional(readOnly = true)
	public List<HouseholdMembership> getAllHouseholdMembershipsByGroup(
			Household grp);

	/**
	 * Get Household Memberships records given Person
	 * 
	 * @return Household Memberships with Person
	 */
	@Transactional(readOnly = true)
	public List<HouseholdMembership> getAllHouseholdMembershipsByPerson(Person p);

	/**
	 * Get Household Memberships records by person and by group
	 * 
	 * @return Household Memberships with person and group
	 */
	@Transactional(readOnly = true)
	public List<HouseholdMembership> getHouseholdMembershipByGrpByPsn(Person p,
			Household grp);

	/**
	 * Save one Household Attribute object to the database
	 * 
	 * @param HouseholdAttribute
	 *            householdAttribute object to be saved
	 * @return saved HouseholdAttribute object
	 */
	public HouseholdAttribute saveHouseholdAttribute(
			HouseholdAttribute householdAttribute);

	/**
	 * Get one HouseholdAttribute record based on the household id
	 * 
	 * @param id
	 *            the HouseholdAttribute id
	 * @return HouseholdAttribute that match the id
	 */
	@Transactional(readOnly = true)
	public HouseholdAttribute getHouseholdAttribute(Integer id);

	/**
	 * Get all HouseholdAttribute records
	 * 
	 * @return all HouseholdAttribute record in the system
	 */
	@Transactional(readOnly = true)
	public List<HouseholdAttribute> getAllHouseholdAttribute();

	/**
	 * Save one Household AttribValue object to the database
	 * 
	 * @param HouseholdAttribValue
	 *            HouseholdAttribValue object to be saved
	 * @return saved HouseholdAttribValue object
	 */
	public HouseholdAttribValue saveHouseholdAttribValue(
			HouseholdAttribValue householdAttribValue);

	/**
	 * Get one HouseholdAttribValue record based on the household id
	 * 
	 * @param id
	 *            the HouseholdAttribValue id
	 * @return HouseholdAttribValue that match the id
	 */
	@Transactional(readOnly = true)
	public HouseholdAttribValue getHouseholdAttribValue(Integer id);

	/**
	 * Get all HouseholdAttribValue records
	 * 
	 * @return all HouseholdAttribValue record in the system
	 */
	@Transactional(readOnly = true)
	public List<HouseholdAttribValue> getAllHouseholdAttribValue();

	/**
	 * Save location to database (create if new or update if changed)
	 * 
	 * @param location
	 *            is the location to be saved to the database
	 * @should throw APIException if location has no name
	 * @should overwrite transient tag if tag with same name exists
	 * @should throw APIException if transient tag is not found
	 * @should return saved object
	 * @should remove location tag from location
	 * @should add location tag to location
	 * @should remove child location from location
	 * @should cascade save to child location from location
	 * @should update location successfully
	 * @should create location successfully
	 */
	public HouseholdLocation saveHouseholdLocation(HouseholdLocation location)
			throws APIException;

	/**
	 * Returns a location given that locations primary key
	 * <code>locationId</code> A null value is returned if no location exists
	 * with this location.
	 * 
	 * @param locationId
	 *            integer primary key of the location to find
	 * @return HouseholdLocation object that has location.locationId =
	 *         <code>locationId</code> passed in.
	 * @should return null when no location match given location id
	 */
	@Transactional(readOnly = true)
	public HouseholdLocation getHouseholdLocation(Integer locationId)
			throws APIException;

	/**
	 * Returns a location given the location's exact <code>name</code> A null
	 * value is returned if there is no location with this name
	 * 
	 * @param name
	 *            the exact name of the location to match on
	 * @return HouseholdLocation matching the <code>name</code> to
	 *         HouseholdLocation.name
	 * @should return null when no location match given location name
	 */
	@Transactional(readOnly = true)
	public HouseholdLocation getHouseholdLocation(String name)
			throws APIException;

	/**
	 * Returns the default location for this implementation.
	 * 
	 * @return The default location for this implementation.
	 * @should return default location for the implementation
	 * @should return Unknown HouseholdLocation if the global property is
	 *         something else that doesnot exist
	 */
	@Transactional(readOnly = true)
	public HouseholdLocation getDefaultHouseholdLocation() throws APIException;

	/**
	 * Returns a location by uuid
	 * 
	 * @param uuid
	 *            is the uuid of the desired location
	 * @return location with the given uuid
	 * @should find object given valid uuid
	 * @should return null if no object found with given uuid
	 */
	@Transactional(readOnly = true)
	public HouseholdLocation getHouseholdLocationByUuid(String uuid)
			throws APIException;

	/**
	 * Returns all locations, includes retired locations. This method delegates
	 * to the #getAllHouseholdLocations(boolean) method
	 * 
	 * @return locations that are in the database
	 * @should return all locations including retired
	 */
	@Transactional(readOnly = true)
	public List<HouseholdLocation> getAllHouseholdLocations()
			throws APIException;

	/**
	 * Returns all locations.
	 * 
	 * @param includeRetired
	 *            whether or not to include retired locations
	 * @should return all locations when includeRetired is true
	 * @should return only unretired locations when includeRetires is false
	 */
	@Transactional(readOnly = true)
	public List<HouseholdLocation> getAllHouseholdLocations(
			boolean includeRetired) throws APIException;

	/**
	 * Returns locations that match the beginning of the given string. A null
	 * list will never be returned. An empty list will be returned if there are
	 * no locations. Search is case insensitive. matching this
	 * <code>nameFragment</code>
	 * 
	 * @param nameFragment
	 *            is the string used to search for locations
	 * @should return empty list when no location match the name fragment
	 */
	@Transactional(readOnly = true)
	public List<HouseholdLocation> getHouseholdLocations(String nameFragment)
			throws APIException;

	/**
	 * Returns a specific number locations from the specified starting position
	 * that match the beginning of the given string. A null list will never be
	 * returned. An empty list will be returned if there are no locations.
	 * Search is case insensitive. matching this <code>nameFragment</code>. If
	 * start and length are not specified, then all matches are returned
	 * 
	 * @param nameFragment
	 *            is the string used to search for locations
	 * @param includeRetired
	 *            Specifies if retired locations should be returned
	 * @param start
	 *            the beginning index
	 * @param length
	 *            the number of matching locations to return
	 * @since 1.8
	 */
	@Transactional(readOnly = true)
	public List<HouseholdLocation> getHouseholdLocations(String nameFragment,
			boolean includeRetired, Integer start, Integer length)
			throws APIException;

	/**
	 * Retires the given location. This effectively removes the location from
	 * circulation or use.
	 * 
	 * @param location
	 *            location to be retired
	 * @param reason
	 *            is the reason why the location is being retired
	 * @should retire location successfully
	 * @should throw IllegalArgumentException when no reason is given
	 */
	public HouseholdLocation retireHouseholdLocation(
			HouseholdLocation location, String reason) throws APIException;

	/**
	 * Unretire the given location. This restores a previously retired location
	 * back into circulation and use.
	 * 
	 * @param location
	 * @return the newly unretired location
	 * @throws APIException
	 * @should unretire retired location
	 */
	public HouseholdLocation unretireHouseholdLocation(
			HouseholdLocation location) throws APIException;

	/**
	 * Completely remove a location from the database (not reversible) This
	 * method delegates to #purgeHouseholdLocation(location, boolean) method
	 * 
	 * @param location
	 *            the HouseholdLocation to clean out of the database.
	 * @should delete location successfully
	 */
	public void purgeHouseholdLocation(HouseholdLocation location)
			throws APIException;

	/**
	 * Return the number of all locations that start with the given name
	 * fragment, if the name fragment is null or an empty string, then the
	 * number of all locations will be returned
	 * 
	 * @param nameFragment
	 *            is the string used to search for locations
	 * @param includeRetired
	 *            Specifies if retired locations should be counted or ignored
	 * @return the number of all locations starting with the given nameFragment
	 * @since 1.8
	 */
	@Transactional(readOnly = true)
	public Integer getCountOfHouseholdLocations(String nameFragment,
			Boolean includeRetired);

	/**
	 * Distinct Locations returned
	 * 
	 * @param includeRetired
	 * @return List<HouseholdLocation>
	 */
	@Transactional(readOnly = true)
	public List<HouseholdLocation> getAllHouseholdLocationsByLocation(
			boolean includeRetired);

	/**
	 * Distinct SubLocations returned
	 * 
	 * @param includeRetired
	 * @return List<HouseholdLocation>
	 */
	@Transactional(readOnly = true)
	public List<HouseholdLocation> getAllHouseholdLocationsBySubLocation(
			String location, boolean includeRetired);

	/**
	 * Distinct villages returned
	 * 
	 * @param includeRetired
	 * @return List<HouseholdLocation>
	 */
	@Transactional(readOnly = true)
	public List<HouseholdLocation> getAllHouseholdLocationsByVillage(
			String subLocation, String location, boolean includeRetired);

	/**
	 * 
	 * @param village
	 * @param subLocation
	 * @param location
	 * @param includeRetired
	 * @return HouseholdLocation
	 */
	public HouseholdLocation getAllHouseholdLocationsByLocSubVil(
			String village, String subLocation, String location,
			boolean includeRetired);

	/**
	 * Given a household location, returns the names of all entries that are
	 * hierarchically valid for the specified householdLocationField. (Excluding
	 * duplicate names and ignoring any current value of the specified
	 * householdLocationField)
	 * 
	 * This method can handle restrictions based on household location field
	 * values not only above but also *below* the specified level. (For
	 * instance, if the city is set to "Turbo", and we ask for possible values
	 * for the "state" level, only Uasin-Gishu should be returned)
	 * 
	 * @param location
	 *            the location we are testing against
	 * @param fieldName
	 *            name of the household location field to look up possible
	 *            values for
	 * @return a list of the names of the possible valid location values for the
	 *         specified field; returns an empty list if no matches, should
	 *         return null only if error
	 */
	public List<String> getPossibleHouseholdLocationValues(
			HouseholdLocation location, String fieldName);

	/**
	 * Given a map of household location fields to household location field
	 * values, returns the names of all entries that are hierarchically valid
	 * for the specified householdLocationField. (Excluding duplicate names and
	 * ignoring any current value of the specified householdLocationField)
	 * 
	 * This method can handle restrictions based on household location field
	 * values not only above but also *below* the specified level. (For
	 * instance, if the city is set to "Turbo", and we ask for possible values
	 * for the "state" level, only Uasin-Gishu should be returned)
	 * 
	 * @param locationMap
	 *            a map of household location fields names to household location
	 *            field values
	 * @param fieldName
	 *            name of the household location field to look up possible
	 *            values for
	 * @return a list of the names of the possible valid location values for the
	 *         specified field; returns an empty list if no matches, should
	 *         return null only if error
	 */
	public List<String> getPossibleHouseholdLocationValues(
			Map<String, String> locationMap, String fieldName);

	/**
	 * Given a household location, returns the names of all entries that are
	 * hierarchically valid for the specified householdLocationField. (Excluding
	 * duplicate names and ignoring any current value of the specified
	 * householdLocationField)
	 * 
	 * This method can handle restrictions based on household location field
	 * values not only above but also *below* the specified level. (For
	 * instance, if the city is set to "Turbo", and we ask for possible values
	 * for the "state" level, only Uasin-Gishu should be returned)
	 * 
	 * @param location
	 * @param field
	 * @return a list of the names of the possible valid household location
	 *         entries; returns an empty list if no matches, should return null
	 *         only if error
	 */
	public List<String> getPossibleHouseholdLocationValues(
			HouseholdLocation location, HouseholdLocationField field);

	/**
	 * Given a household location, returns all the household location entries
	 * that are hierarchically valid for the specified level. (Ignoring any
	 * current value of the householdLocationField associated with the specified
	 * level).
	 * 
	 * This method can handle restrictions based on household location field
	 * values not only above but also *below* the specified level. (For
	 * instance, if the city is set to "Turbo", and we ask for possible values
	 * for the "state" level, only Uasin-Gishu should be returned)
	 * 
	 * @param location
	 * @param level
	 * @return a list of possible valid household location entries; returns an
	 *         empty list if no matches, should return null only if error
	 */
	public List<HouseholdLocationEntry> getPossibleHouseholdLocationEntries(
			HouseholdLocation location, HouseholdLocationLevel level);

	/**
	 * Given a search string, returns all the "full locations" that match that
	 * search string Returns a list of full locations, represented as a
	 * pipe-delimited string of household location entry names, ordered from the
	 * entry at the highest level to the entry at the lowest level in the tree.
	 * For example, the full location for the Beacon Hill neighborhood in the
	 * city of Turbo might be:
	 * "United States|Uasin-Gishu|Suffolk County|Turbo|Beacon Hill"
	 * 
	 * @param searchString
	 *            the search string to search for
	 * @return a list of full locations; returns an empty list if no matches
	 */
	public List<String> getPossibleFullHouseholdLocations(String searchString);

	/**
	 * Returns a count of the total number of household location entries
	 * 
	 * @return the number of household location entries
	 */
	public Integer getHouseholdLocationEntryCount();

	/**
	 * Returns a count of the total number of household location entries
	 * associated with the given level
	 * 
	 * @param level
	 * @return the number of household location entries associated with the
	 *         given level
	 */
	public Integer getHouseholdLocationEntryCountByLevel(
			HouseholdLocationLevel level);

	/**
	 * Returns the household location entry with the given id
	 * 
	 * @param locationHierarchyEntryId
	 * @return the household location entry with the given id
	 */
	public HouseholdLocationEntry getHouseholdLocationEntry(
			int locationHierarchyEntryId);

	/**
	 * Returns the household location entry with the given user generated id
	 * 
	 * @param userGeneratedId
	 * @return the household location entry with the given user generated id
	 */
	public HouseholdLocationEntry getHouseholdLocationEntryByUserGenId(
			String userGeneratedId);

	/**
	 * Returns all household location entries at with the given level
	 * 
	 * @param level
	 * @return a list of all household location entries at the given level
	 */
	public List<HouseholdLocationEntry> getHouseholdLocationEntriesByLevel(
			HouseholdLocationLevel level);

	/**
	 * Returns all household location entries at the given level that have the
	 * specified name (name match is case-insensitive)
	 * 
	 * @param level
	 * @param name
	 * @return a list of all household location entries at the given level that
	 *         have the specified name
	 */
	public List<HouseholdLocationEntry> getHouseholdLocationEntriesByLevelAndName(
			HouseholdLocationLevel level, String name);

	/**
	 * Returns all household location entries at the top level in the hierarchy
	 * 
	 * @return a list of all the household location entries at the top level of
	 *         the hierarchy
	 */
	public List<HouseholdLocationEntry> getHouseholdLocationEntriesAtTopLevel();

	/**
	 * Returns all household location entries that are children of the specified
	 * entry (If no entry specified, returns all the entries at the top level)
	 * 
	 * @param entry
	 * @return a list of all the household location entries that are children of
	 *         the specified entry
	 */
	public List<HouseholdLocationEntry> getChildHouseholdLocationEntries(
			HouseholdLocationEntry entry);

	/**
	 * Returns all household location entries that are child of the entry with
	 * the given id
	 * 
	 * @param entryId
	 * @return a list of all
	 */
	public List<HouseholdLocationEntry> getChildHouseholdLocationEntries(
			Integer entryId);

	/**
	 * Returns the household location entry that is the child entry of the
	 * specified entry and have the specified name (case-insensitive) (If no
	 * entry specified, tests against all entries at the top level) (Throws an
	 * exception if there is only one match, because there should be no two
	 * entries with the same parent and name)
	 * 
	 * @param entry
	 * @param name
	 * @return the entry with the specified parent and name
	 */
	public HouseholdLocationEntry getChildHouseholdLocationEntryByName(
			HouseholdLocationEntry entry, String childName);

	/**
	 * Saves the specified household location entry
	 * 
	 * @param entry
	 */
	public void saveHouseholdLocationEntry(HouseholdLocationEntry entry);

	/**
	 * Saves a block of household location entries within a single transaction
	 * (This should be used with care since the save interceptors may not be
	 * used properly. This method is mainly used to speed up the performance of
	 * importing a hierarchy from a file)
	 * 
	 * @param entries
	 */
	public void saveHouseholdLocationEntries(
			List<HouseholdLocationEntry> entries);

	/**
	 * Removes all household location entries--use with care
	 */
	public void deleteAllHouseholdLocationEntries();

	/**
	 * Gets all household location levels, ordered from the top of hierarchy to
	 * the bottom
	 * 
	 * @return the ordered list of household location levels
	 */
	public List<HouseholdLocationLevel> getOrderedHouseholdLocationLevels();

	/**
	 * Gets the household location levels, ordered from the top the hierarchy to
	 * the bottom
	 * 
	 * @param includeUnmapped
	 *            specifies whether or not to include hierarchy levels that
	 *            aren't mapped to an underlying household location field
	 * @return the ordered list of household location levels
	 */
	public List<HouseholdLocationLevel> getOrderedHouseholdLocationLevels(
			Boolean includeUnmapped);

	/**
	 * Gets the household location levels, ordered from the top of the hierarchy
	 * to the bottom
	 * 
	 * @param includeUnmapped
	 *            specifies whether or not to include hierarchy levels that
	 *            aren't mapped to an underlying household location field
	 * @param includeEmptyLevels
	 *            specified whether or not include hierarchy levels that don't
	 *            have any household location entries entries
	 * @return the ordered list of household location levels
	 */
	public List<HouseholdLocationLevel> getOrderedHouseholdLocationLevels(
			Boolean includeUnmapped, Boolean includeEmptyLevels);

	/**
	 * Gets all household location levels
	 * 
	 * @return a list of all household location levels
	 */
	public List<HouseholdLocationLevel> getHouseholdLocationLevels();

	/**
	 * Gets a count of the number of household location levels
	 * 
	 * @return the number of household location levels
	 */
	public Integer getHouseholdLocationLevelsCount();

	/**
	 * Gets the household location level that represents the top level of the
	 * hierarchy
	 * 
	 * @return the household location level at the top level of the hierarchy
	 */
	public HouseholdLocationLevel getTopHouseholdLocationLevel();

	/**
	 * Gets the household location level that represents the lowest level of the
	 * hierarchy
	 * 
	 * @return the household location level at the lowest level of the hierarchy
	 */
	public HouseholdLocationLevel getBottomHouseholdLocationLevel();

	/**
	 * Gets an HouseholdLocationLevel by id
	 * 
	 * @param levelId
	 * @return the household location level with the given id
	 */
	public HouseholdLocationLevel getHouseholdLocationLevel(Integer levelId);

	/**
	 * Finds the child HouseholdLocationLevel of the given
	 * HouseholdLocationLevel
	 * 
	 * @param level
	 * @return the household location level that is the child of the given level
	 */
	public HouseholdLocationLevel getChildHouseholdLocationLevel(
			HouseholdLocationLevel level);

	/**
	 * Adds (and saves) a new HouseholdLocationLevel at the bottom of the
	 * hierarchy
	 * 
	 * @return the new household location level
	 */
	public HouseholdLocationLevel addHouseholdLocationLevel();

	/**
	 * Saves an HouseholdLocationLevel
	 * 
	 * @param the
	 *            level to save
	 */
	public void saveHouseholdLocationLevel(HouseholdLocationLevel level);

	/**
	 * Deletes an HouseholdLocationLevel
	 * 
	 * @param the
	 *            level to delete
	 */
	public void deleteHouseholdLocationLevel(HouseholdLocationLevel level);

	/**
	 * Attempt to determine the hierarchy of household location levels based on
	 * the hierarchy of entries and assign the parent levels accordingly
	 */
	public void setHouseholdLocationLevelParents();

	// ////////////////////////////////////////////
	/**
	 * Save one Household Encounter object to the database
	 * 
	 * @param HouseholdEncounter
	 *            householdEncounter object to be saved
	 * @return saved HouseholdEncounter object
	 */
	public HouseholdEncounter saveHouseholdEncounter(
			HouseholdEncounter householdEncounter);

	/**
	 * Get one HouseholdEncounter record based on the HouseholdEncounter id
	 * 
	 * @param id
	 *            the HouseholdEncounter id
	 * @return HouseholdEncounter that match the id
	 */
	@Transactional(readOnly = true)
	public HouseholdEncounter getHouseholdEncounter(Integer id);

	/**
	 * Get one HouseholdEncounter record based on the HouseholdEncounter uuid
	 * 
	 * @param id
	 *            the HouseholdEncounter uuid
	 * @return HouseholdEncounter that match the uuid
	 */
	@Transactional(readOnly = true)
	public HouseholdEncounter getHouseholdEncounterByUUID(String uuid);

	/**
	 * Get all HouseholdEncounter records
	 * 
	 * @return all HouseholdEncounter record in the system
	 */
	@Transactional(readOnly = true)
	public List<HouseholdEncounter> getAllHouseholdEncountersByHousehold(
			Household household);

	/**
	 * Get all HouseholdEncounter records
	 * 
	 * @return all HouseholdEncounter record in the system based on householdId
	 */
	@Transactional(readOnly = true)
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdUuid(
			Household household);

	/**
	 * Get all HouseholdEncounter records
	 * 
	 * @return all HouseholdEncounter record in the system based on household
	 *         identifier
	 */
	@Transactional(readOnly = true)
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdIdentifiers(
			String identifier);

	/**
	 * Get all encounters that match a variety of (nullable) criteria. Each
	 * extra value for a parameter that is provided acts as an "and" and will
	 * reduce the number of results returned
	 * 
	 * @param household
	 *            the Household the encounter is for
	 * @param loc
	 *            the HouseholdLocation this encounter took place
	 * @param fromDate
	 *            the minimum date (inclusive) this encounter took place
	 * @param toDate
	 *            the maximum date (exclusive) this encounter took place
	 * @param enteredViaForms
	 *            the form that entered this encounter must be in this list
	 * @param encounterTypes
	 *            the type of HouseholdEncounter must be in this list
	 * @param providers
	 *            the provider of this encounter must be in this list
	 * @param includeVoided
	 *            true/false to include the voided encounters or not
	 * @return a list of encounters ordered by increasing encounterDatetime
	 * @should get encounters by HouseholdLocation
	 * @should get encounters on or after date
	 * @should get encounters on or up to a date
	 * @should get encounters by form
	 * @should get encounters by HouseholdType
	 * @should get encounters by provider
	 * @should exclude voided encounters
	 * @should include voided encounters
	 */
	@Transactional(readOnly = true)
	public List<HouseholdEncounter> getHouseholdEncounters(Household household,
			HouseholdLocation loc, Date fromDate, Date toDate,
			Collection<Form> enteredViaForms,
			Collection<HouseholdEncounterType> householdEncounterTypes,
			Collection<User> providers, boolean includeVoided);

	/**
	 * Voiding a encounter essentially removes it from circulation
	 * 
	 * @param encounter
	 *            HouseholdEncounter object to void
	 * @param reason
	 *            String reason that it's being voided
	 * @should void encounter and set attributes
	 * @should cascade to obs
	 * @should cascade to orders
	 * @should throw error with null reason parameter
	 */
	public HouseholdEncounter voidHouseholdEncounter(
			HouseholdEncounter householdEncounter, String reason);

	/**
	 * Unvoid encounter record
	 * 
	 * @param encounter
	 *            HouseholdEncounter to be revived
	 * @should cascade unvoid to obs
	 * @should cascade unvoid to orders
	 * @should unvoid and unmark all attributes
	 */
	public HouseholdEncounter unvoidHouseholdEncounter(
			HouseholdEncounter householdEncounter) throws APIException;

	/**
	 * Completely remove an encounter from database. For super users only. If
	 * dereferencing encounters, use
	 * <code>voidEncounter(org.openmrs.module.household.model.HouseholdEncounter)</code>
	 * 
	 * @param encounter
	 *            encounter object to be purged
	 * @should purgeEncounter
	 */
	public void purgeHouseholdEncounter(HouseholdEncounter householdEncounter)
			throws APIException;

	/**
	 * Completely remove an encounter from database. For super users only. If
	 * dereferencing encounters, use
	 * <code>voidHouseholdEncounter(org.openmrs.module.household.model.HouseholdEncounter)</code>
	 * 
	 * @param encounter
	 *            HouseholdEncounter object to be purged
	 * @param cascade
	 *            Purge any related observations as well?
	 * @should cascade purge to obs and orders
	 */
	public void purgeHouseholdEncounter(HouseholdEncounter householdEncounter,
			boolean cascade) throws APIException;

	// //////////////////////////////////////

	/**
	 * Save a new HouseholdEncounter Type or update an existing Household
	 * Encounter Type.
	 * 
	 * @param encounterType
	 * @should save encounter type
	 * @should not overwrite creator
	 * @should not overwrite creator or date created
	 * @should not overwrite date created
	 * @should update an existing encounter type name
	 */
	public HouseholdEncounterType saveHouseholdEncounterType(
			HouseholdEncounterType encounterType);

	/**
	 * Get householdEncounterType by internal identifier
	 * 
	 * @param encounterTypeId
	 *            Integer
	 * @return encounterType with given internal identifier
	 * @throws APIException
	 * @should throw error if given null parameter
	 */
	@Transactional(readOnly = true)
	public HouseholdEncounterType getHouseholdEncounterType(
			Integer encounterTypeId) throws APIException;

	/**
	 * Get HouseholdEncounterType by its UUID
	 * 
	 * @param uuid
	 * @return
	 * @should find object given valid uuid
	 * @should return null if no object found with given uuid
	 */
	@Transactional(readOnly = true)
	public HouseholdEncounterType getHouseholdEncounterTypeByUuid(String uuid)
			throws APIException;

	/**
	 * Get HouseholdEncounterType by its name
	 * 
	 * @param name
	 * @return
	 * @should find object given valid name
	 * @should return null if no object found with given name
	 */
	@Transactional(readOnly = true)
	public HouseholdEncounterType getHouseholdEncounterTypeByName(String name)
			throws APIException;

	/**
	 * Get all Household encounter types (including retired)
	 * 
	 * @return encounter types list
	 * @throws APIException
	 * @should not return retired types
	 */
	@Transactional(readOnly = true)
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes()
			throws APIException;

	/**
	 * Get all Household encounter types. If includeRetired is true, also get
	 * retired encounter types.
	 * 
	 * @param includeRetired
	 * @return encounter types list
	 * @throws APIException
	 * @should not return retired types
	 * @should include retired types with true includeRetired parameter
	 */
	@Transactional(readOnly = true)
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes(
			boolean includeRetired) throws APIException;

	/**
	 * Find Household Encounter Types with name matching the beginning of the
	 * search string. Search strings are case insensitive so that
	 * "NaMe".equals("name") is true. Includes retired EncounterTypes.
	 * 
	 * @param name
	 *            of the encounter type to find
	 * @return List<HouseholdEncounterType> matching encounters
	 * @throws APIException
	 * @should return types by partial name match
	 * @should return types by partial case insensitive match
	 * @should include retired types in the results
	 * @should not partial match name on internal substrings
	 * @should return types ordered on name and nonretired first
	 */
	@Transactional(readOnly = true)
	public List<HouseholdEncounterType> findHouseholdEncounterTypes(String name)
			throws APIException;

	/**
	 * Retire an HouseholdEncounterType. This essentially marks the given
	 * encounter type as a non-current type that shouldn't be used anymore.
	 * 
	 * @param encounterType
	 *            the Household encounter type to retire
	 * @param reason
	 *            required non-null purpose for retiring this encounter type
	 * @throws APIException
	 * @should retire type and set attributes
	 * @should throw error if given null reason parameter
	 */
	public HouseholdEncounterType retireHouseholdEncounterType(
			HouseholdEncounterType encounterType, String reason)
			throws APIException;

	/**
	 * Unretire an HouseholdEncounterType. This brings back the given Household
	 * encounter type and says that it can be used again
	 * 
	 * @param encounterType
	 *            the Household encounter type to unretire
	 * @throws APIException
	 * @should unretire type and unmark attributes
	 */
	public HouseholdEncounterType unretireEncounterType(
			HouseholdEncounterType encounterType) throws APIException;

	/**
	 * Completely remove an Household encounter type from database.
	 * 
	 * @param encounterType
	 * @throws APIException
	 * @should purge type
	 */
	public void purgeHouseholdEncounterType(HouseholdEncounterType encounterType)
			throws APIException;

	/**
	 * Search for Household encounters by Household name or Household
	 * identifier.
	 * 
	 * @param query
	 *            Household name or identifier
	 * @param includeVoided
	 *            Specifies whether voided encounters should be included
	 * @return list of encounters for the given Household
	 * @throws APIException
	 * @should get all unvoided encounters for the given Household name
	 * @should get all unvoided encounters for the given Household identifier
	 * @should throw error if given null parameter
	 * @should include voided Household encounters in the returned list if
	 *         includedVoided is true
	 * @since 1.7
	 */
	public List<HouseholdEncounter> getHouseholdEncountersByHousehold(
			String query, boolean includeVoided) throws APIException;

	/**
	 * Search for encounters by patient name or patient identifier and returns a
	 * specific number of them from the specified starting position. If start
	 * and length are not specified, then all matches are returned
	 * 
	 * @param query
	 *            patient name or identifier
	 * @param start
	 *            beginning index for the batch
	 * @param length
	 *            number of encounters to return in the batch
	 * @param includeVoided
	 *            Specifies whether voided encounters should be included
	 * @return list of encounters for the given patient based on batch settings
	 * @throws APIException
	 * @since 1.8
	 */
	@Transactional(readOnly = true)
	public List<HouseholdEncounter> getHouseholdEncounters(String query,
			Integer start, Integer length, boolean includeVoided)
			throws APIException;

	// /////////////////////OBS///////////////////////////////////

	/**
	 * Get an Household observation
	 * 
	 * @param obsId
	 *            integer obsId of observation desired
	 * @return matching Household Obs
	 * @throws APIException
	 * @should get obs matching given obsId
	 */
	@Transactional(readOnly = true)
	public HouseholdObs getHouseholdObs(Integer obsId) throws APIException;

	/**
	 * Get HouseholdObs by its UUID
	 * 
	 * @param uuid
	 * @return
	 * @should find object given valid uuid
	 * @should return null if no object found with given uuid
	 */
	@Transactional(readOnly = true)
	public HouseholdObs getHouseholdObsByUuid(String uuid) throws APIException;

	/**
	 * Save the given obs to the database. This will move the contents of the
	 * given <code>householdObs</code> to the database. This acts as both the
	 * initial save and an update kind of save. The returned obs will be the
	 * same as the obs passed in. It is included for chaining. If this is an
	 * initial save, the obsId on the given <code>householdObs</code> object
	 * will be updated to reflect the auto numbering from the database. The
	 * obsId on the returned householdObs will also have this number. If there
	 * is already an obsId on the given <code>householdObs</code> object, the
	 * given obs will be voided and a new row in the database will be created
	 * that has a new obs id.
	 * 
	 * @param obs
	 *            the Household Obs to save to the database
	 * @param changeMessage
	 *            String explaining why <code>householdObs</code> is being
	 *            changed. If <code>householdObs</code> is a new obs,
	 *            changeMessage is nullable, or if it is being updated, it would
	 *            be required
	 * @return Obs that was saved to the database
	 * @throws APIException
	 * @should create new file from complex data for new obs
	 * @should not overwrite file when updating a complex obs
	 * @should void the given obs in the database
	 * @should create very basic obs and add new obsId
	 * @should allow changing of every property on obs
	 * @should return a different object when updating an obs
	 * @should set creator and dateCreated on new obs
	 * @should cascade save to child obs groups
	 * @should cascade update to new child obs groups
	 */
	public HouseholdObs saveHouseholdObs(HouseholdObs obs, String changeMessage)
			throws APIException;

	/**
	 * Equivalent to deleting an observation
	 * 
	 * @param obs
	 *            HouseholdObs to void
	 * @param reason
	 *            String reason it's being voided
	 * @throws APIException
	 * @should set voided bit on given obs
	 * @should fail if reason parameter is empty
	 */
	public HouseholdObs voidHouseholdObs(HouseholdObs obs, String reason)
			throws APIException;

	/**
	 * Revive an observation (pull a Lazarus)
	 * 
	 * @param obs
	 *            HouseholdObs to unvoid
	 * @throws APIException
	 * @should unset voided bit on given obs
	 * @should cascade unvoid to child grouped Householdobs
	 */
	public HouseholdObs unvoidObs(HouseholdObs obs) throws APIException;

	/**
	 * Completely remove an observation from the database. This should typically
	 * not be called because we don't want to ever lose data. The data really
	 * <i>should</i> be voided and then it is not seen in interface any longer
	 * (see #voidHouseholdObs(HouseholdObs) for that one) If other things link
	 * to this obs, an error will be thrown.
	 * 
	 * @param obs
	 * @throws APIException
	 * @see #purgeHouseholdObs(HouseholdObs, boolean)
	 * @should delete the given obs from the database
	 */
	public void purgeHouseholdObs(HouseholdObs obs) throws APIException;

	/**
	 * Completely remove an observation from the database. This should typically
	 * not be called because we don't want to ever lose data. The data really
	 * <i>should</i> be voided and then it is not seen in interface any longer
	 * (see #voidHouseholdObs(HouseholdObs) for that one)
	 * 
	 * @param obs
	 *            the observation to remove from the database
	 * @param cascade
	 *            true/false whether or not to cascade down to other things that
	 *            link to this observation (like Orders and ObsGroups)
	 * @throws APIException
	 * @see #purgeHouseholdObs(HouseholdObs)
	 * @should throw APIException if given true cascade
	 */
	public void purgeHouseholdObs(HouseholdObs obs, boolean cascade)
			throws APIException;

	/**
	 * Get all Observations for the given Household, sorted by obsDatetime
	 * ascending. Does not return voided observations.
	 * 
	 * @param who
	 *            the user to match on
	 * @return a List<HouseholdObs> object containing all non-voided
	 *         observations for the specified person
	 * @see #getObservations(List, List, List, List, List, List, List, Integer,
	 *      Integer, Date, Date, boolean)
	 * @should get all observations assigned to given person
	 */
	@Transactional(readOnly = true)
	public List<HouseholdObs> getObservationsByHousehold(Household household);

	/**
	 * This method fetches observations according to the criteria in the given
	 * arguments. All arguments are optional and nullable. If more than one
	 * argument is non-null, the result is equivalent to an "and"ing of the
	 * arguments. (e.g. if both a <code>HouseholdLocation</code> and a
	 * <code>fromDate</code> are given, only Obs that are <u>both</u> at that
	 * Location and after the fromDate are returned). <br/>
	 * <br/>
	 * Note: If <code>whom</code> has elements,
	 * <code>Household Definition</code> is ignored <br/>
	 * <br/>
	 * Note: to get all observations on a certain date, use:<br/>
	 * Date fromDate = "2009-08-15";<br/>
	 * Date toDate = OpenmrsUtil.lastSecondOfDate(fromDate); List<Obs> obs =
	 * getObservations(...., fromDate, toDate, ...);
	 * 
	 * @param whom
	 *            List<Household> to restrict obs to (optional)
	 * @param encounters
	 *            List<HouseholdEncounter> to restrict obs to (optional)
	 * @param questions
	 *            List<Concept> to restrict the obs to (optional)
	 * @param answers
	 *            List<Concept> to restrict the valueCoded to (optional)
	 * @param locations
	 *            The org.openmrs.module.household.model.HouseholdLocation
	 *            objects to restrict to (optional)
	 * @param sort
	 *            list of column names to sort on (obsId, obsDatetime, etc) if
	 *            null, defaults to obsDatetime (optional)
	 * @param mostRecentN
	 *            restrict the number of obs returned to this size (optional)
	 * @param obsGroupId
	 *            the Obs.getObsGroupId() to this integer (optional)
	 * @param fromDate
	 *            the earliest HouseholdObs date to get (optional)
	 * @param toDate
	 *            the latest HouseholdObs date to get (optional)
	 * @param includeVoidedObs
	 *            true/false whether to also include the voided obs (required)
	 * @return list of Observations that match all of the criteria given in the
	 *         arguments
	 * @throws APIException
	 * @should compare dates using lte and gte
	 * @should get all obs assigned to given encounters
	 * @should get all obs with question concept in given questions parameter
	 * @should get all obs with answer concept in given answers parameter
	 * @should return all obs whose Household is a Household only
	 * @should return obs with HouseholdLocation in given locations parameter
	 * @should sort returned obs by obsDatetime if sort is empty
	 * @should sort returned obs by conceptId if sort is concept
	 * @should limit number of obs returned to mostReturnN parameter
	 * @should return obs whose groupId is given obsGroupId
	 * @should not include voided obs
	 * @should include voided obs if includeVoidedObs is true
	 */
	@Transactional(readOnly = true)
	public List<HouseholdObs> getObservations(List<Household> whom,
			List<HouseholdEncounter> encounters, List<Concept> questions,
			List<Concept> answers, List<HouseholdLocation> locations,
			List<String> sort, Integer mostRecentN, Integer obsGroupId,
			Date fromDate, Date toDate, boolean includeVoidedObs)
			throws APIException;

	/**
	 * Get all nonvoided observations for the given patient with the given
	 * concept as the question concept (conceptId)
	 * 
	 * @param who
	 *            person to match on
	 * @param question
	 *            conceptId to match on
	 * @return list of all nonvoided observations matching these criteria
	 * @throws APIException
	 * @see #getObservations(List, List, List, List, List, List, Integer,
	 *      Integer, Date, Date, boolean)
	 * @should get observations matching Household and question
	 * @should not fail with null Household parameter
	 */
	@Transactional(readOnly = true)
	public List<HouseholdObs> getObservationsByHouseholdAndConcept(
			Household household, Concept question) throws APIException;

	public boolean saveHouseholdDefinitionParent(
			HouseholdDefinitionParent householdDefinitionParent);

	@Transactional(readOnly = true)
	public HouseholdDefinitionParent getHouseholdDefinitionParent(int id);

	@Transactional(readOnly = true)
	public HouseholdDefinitionParent getHouseholdDefinitionParent(String uuid);

	@Transactional(readOnly = true)
	public HouseholdDefinitionParent getHouseholdDefinitionParent(
			HouseholdDefinitionParent householdDefinitionParent);

	public boolean purgeHouseholdDefinitionParent(
			HouseholdDefinitionParent householdDefinitionParent,
			String voidReason);

	@Transactional(readOnly = true)
	public List<HouseholdDefinitionParent> getHouseholdDefinitionParent(
			boolean voidedIncluded);

	// Household Membership properties
	public List<HouseholdMembership> getHouseholdQuasiMembersByHousehold(
			Household household);

	public List<HouseholdMembership> getHouseholdRetiredMembersByHousehold(
			Household household);

	public List<HouseholdMembership> getHouseholdMembersVoidedByHousehold(
			Household household);

	public List<Household> getHouseholdsByDefinition(
			HouseholdDefinition householdDefinition);
}
