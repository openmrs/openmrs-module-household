/**
 * 
 */
package org.openmrs.module.household.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;


import org.openmrs.Concept;
import org.openmrs.Form;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdAttribValue;
import org.openmrs.module.household.model.HouseholdAttribute;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.model.HouseholdLocation;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.model.HouseholdObs;
import org.openmrs.module.household.model.HouseholdLocationEntry;
import org.openmrs.module.household.model.HouseholdLocationLevel;

/**
 * Contract for database operation on household object
 *  
 * @author Ampath Developers
 */
public interface HouseholdDAO {
	
	/**
	 * Save one Household Definition object to the database
	 * 
	 * @param HouseholdDefinition HouseholdDefinition object to be saved
	 * @return saved HouseholdDefinition object
	 */
	public HouseholdDefinition saveHouseholdDefinition(HouseholdDefinition householdDefinition);
	
	/**
	 * Get one HouseholdDefinition record based on the household id
	 * 
	 * @param id the HouseholdDefinition id
	 * @return HouseholdDefinition that match the id
	 */
	public HouseholdDefinition getHouseholdDefinition(Integer id);
	
	/**
	 * Get all HouseholdDefinition records
	 * 
	 * @return all HouseholdDefinition record in the system
	 */
	public List<HouseholdDefinition> getAllHouseholdDefinitions();

	/**
	 * Save one HouseholdGroups object to the database
	 * 
	 * @param householdGroups HouseholdGroups object to be saved
	 * @return saved household object
	 */
	public Household saveHouseholdGroup(Household householdGroups);
	
	/**
	 * Get one HouseholdGroup record based on the HouseholdGroups id
	 * 
	 * @param id the HouseholdGroups id
	 * @return HouseholdGroup that match the id
	 */
	public Household getHouseholdGroup(Integer id);
	
	/**
	 * Get all HouseholdGroups records
	 * 
	 * @return all HouseholdGroups record in the system
	 */
	public List<Household> getAllHouseholdGroups();
	
	/**
	 * Save one HouseholdMembership object to the database
	 * 
	 * @param householdMemberships HouseholdMemberships object to be saved
	 * @return saved HouseholdMemberships object
	 */
	public HouseholdMembership saveHouseholdMembership(HouseholdMembership householdMemberships);
	
	/**
	 * Get one Household Membership record based on the HouseholdMembership id
	 * 
	 * @param id the HouseholdMembership id
	 * @return HouseholdMembership that match the id
	 */
	public HouseholdMembership getHouseholdMembership(Integer id);
	
	/**
	 * Get all Household Memberships records
	 * 
	 * @return all Household Memberships in the system
	 */
	public List<HouseholdMembership> getAllHouseholdMemberships();
	
	/**
	 * Get Household Memberships records by ID
	 * 
	 * @return Household Memberships with ID
	 */
	public List<HouseholdMembership> getAllHouseholdMembershipsByGroup(Household grp);
	
	/**
	 * Get Household Memberships records by person
	 * 
	 * @return Household Memberships with person
	 */
	public List<HouseholdMembership> getAllHouseholdMembershipsByPerson(Person p);
	
	/**
	 * Get Household Memberships records by person and by group
	 * 
	 * @return Household Memberships with person and group
	 */
	public List<HouseholdMembership> getHouseholdMembershipByGrpByPsn(Person p,Household grp);
	
	/**
	 * Get voided Household Memberships records by ID
	 * 
	 * @return voided Household Memberships with ID
	 */
	public List<HouseholdMembership> getAllVoidedHouseholdMembershipsByGroup(Household grp);
	
	/**
	 * Get index person in a given household
	 * 
	 * @return index person in a given household
	 */
	public List<HouseholdMembership> getIndexPerson(Integer id);
	/**
	 * Get members in a given household excluding the index
	 * 
	 * @return index persons in a given household
	 */
	public List<HouseholdMembership> getAllNonVoidedHouseholdMembershipsByGroupNotIndex(Household grp);
	/**
	 * Get index person in a given household group
	 * 
	 * @return index person in a given household group
	 */
	public List<HouseholdMembership> getHouseholdIndexByGroup(Household grp);
	
	//=============================================================================
	/**
	 * Save one HouseholdAttribute object to the database
	 * 
	 * @param HouseholdAttribute householdAttribute object to be saved
	 * @return saved HouseholdAttribute object
	 */
	public HouseholdAttribute saveHouseholdAttribute(HouseholdAttribute householdAttribute);
	
	/**
	 * Get one HouseholdAttribute record based on the HouseholdAttribute id
	 * 
	 * @param id the HouseholdAttribute id
	 * @return HouseholdAttribute that match the id
	 */
	public HouseholdAttribute getHouseholdAttribute(Integer id);
	
	/**
	 * Get all HouseholdAttribute records
	 * 
	 * @return all HouseholdAttribute record in the system
	 */
	public List<HouseholdAttribute> getAllHouseholdAttribute();
	
	//=============================================================================
	/**
	 * Save one HouseholdAttribValue object to the database
	 * 
	 * @param HouseholdAttribValue householdAttribValue object to be saved
	 * @return saved HouseholdAttribValue object
	 */
	public HouseholdAttribValue saveHouseholdAttribValue(HouseholdAttribValue householdAttribValue);
	
	/**
	 * Get one HouseholdAttribValue record based on the HouseholdAttribValue id
	 * 
	 * @param id the HouseholdAttribValue id
	 * @return HouseholdAttribValue that match the id
	 */
	public HouseholdAttribValue getHouseholdAttribValue(Integer id);
	
	/**
	 * Get all HouseholdAttribValue records
	 * 
	 * @return all HouseholdAttribValue record in the system
	 */
	public List<HouseholdAttribValue> getAllHouseholdAttribValue();
	
	//=============================================================================
		
	
	/////////////////////////**********************************************************//////////////////////////
	
	
	
	
	
	
	
	
	/**
	 * Create or update a location.
	 * 
	 * @param location <code>Location</code> to save
	 * @return the saved <code>Location</code>
	 */
	public HouseholdLocation saveHouseholdLocation(HouseholdLocation location);
	
	/**
	 * Get a location by locationId
	 * 
	 * @param locationId Internal <code>Integer</code> identifier of the <code>HouseholdLocation<code> to get
	 * @return the requested <code>HouseholdLocation</code>
	 */
	public HouseholdLocation getHouseholdLocation(Integer locationId);
	
	/**
	 * Get a location by name
	 * 
	 * @param name String name of the <code>HouseholdLocation</code> to get
	 * @return the requested <code>HouseholdLocation</code>
	 */
	public HouseholdLocation getHouseholdLocation(String name);
	
	/**
	 * Get all locations
	 * 
	 * @param includeRetired boolean - include retired locations as well?
	 * @return <code>List<HouseholdLocation></code> object of all <code>HouseholdLocation</code>s, possibly including
	 *         retired locations
	 */
	public List<HouseholdLocation> getAllHouseholdLocations(boolean includeRetired);
	
	/**
	 * Returns a specified number of locations starting with a given string from the specified index
	 * 
	 * @see getHouseholdLocations(String, boolean, Integer, Integer)
	 */
	public List<HouseholdLocation> getHouseholdLocations(String nameFragment, boolean includeRetired, Integer start, Integer length)
	                                                                                                              throws DAOException;
	
	/**
	 * Completely remove the location from the database.
	 * 
	 * @param location <code>HouseholdLocation</code> object to delete
	 */
	public void deleteHouseholdLocation(HouseholdLocation location);
	
	/**
	 * @param uuid the uuid to look for
	 * @return location matching uuid
	 */
	public HouseholdLocation getHouseholdLocationByUuid(String uuid);
	
	
	/**
	 * @see getCountOfHouseholdLocations(String, Boolean)
	 */
	public Integer getCountOfHouseholdLocations(String nameFragment, Boolean includeRetired);
	
	/**
	 * Get all DISTINCT locations
	 * 
	 * @param includeRetired boolean - include retired locations as well?
	 * @return <code>List<HouseholdLocation></code> object of all <code>HouseholdLocation</code>s, possibly including
	 *         retired locations
	 */
	public List<HouseholdLocation> getAllHouseholdLocationsByLocation(boolean includeRetired);
	
	/**
	 * Get all DISTINCT sub locations
	 * 
	 * @param includeRetired boolean - include retired locations as well?
	 * @return <code>List<HouseholdLocation></code> object of all <code>HouseholdLocation</code>s, possibly including
	 *         retired locations
	 */
	public List<HouseholdLocation> getAllHouseholdLocationsBySubLocation(String location, boolean includeRetired);
	
	/**
	 * Get all DISTINCT villages
	 * 
	 * @param includeRetired boolean - include retired locations as well?
	 * @return <code>List<HouseholdLocation></code> object of all <code>HouseholdLocation</code>s, possibly including
	 *         retired locations
	 */
	public List<HouseholdLocation> getAllHouseholdLocationsByVillage(String subLocation, String location, boolean includeRetired);
	
	/**
	 * Get Village ID
	 * 
	 * @param includeRetired boolean - include retired locations as well?
	 * @return <code>HouseholdLocation</code> object of all <code>HouseholdLocation</code>s, possibly including
	 *         retired locations
	 */
	public HouseholdLocation getAllHouseholdLocationsByLocSubVil(String village, String subLocation, String location, boolean includeRetired);
	
	
	
	
	/**
	 * Returns the number of household location entries
	 */
	public int getHouseholdLocationEntryCount();
	
	/**
	 * Returns the number of household location entries at the given level
	 */
	public int getHouseholdLocationEntryCountByLevel(HouseholdLocationLevel level);
	
	/**
	 * Get an household location entry, reference by id
	 */
	public HouseholdLocationEntry getHouseholdLocationEntry(int householdLocationId);
	
	/**
	 * Get an household location entry, referenced by the userGeneratedId property
	 */
	public HouseholdLocationEntry getHouseholdLocationEntryByUserGenId(String userGeneratedId);
	
	/**
	 * Gets all household location entries associated with a certain level
	 */
	public List<HouseholdLocationEntry> getHouseholdLocationEntriesByLevel(HouseholdLocationLevel level);
	
	/**
	 * Gets all household location entries associated with the a certain level that have the specified name
	 */
	public List<HouseholdLocationEntry> getHouseholdLocationEntriesByLevelAndName(HouseholdLocationLevel level, String name);
	
	/**
	 * Gets all the household location entries that are children of the specified entry
	 */
	public List<HouseholdLocationEntry> getChildHouseholdLocationEntries(HouseholdLocationEntry entry);
	
	/**
	 * Gets the household location entry which the specified name that is a child of the given entry
	 * (Will throw exception if there are multiple matches, as there should never be two entries with the same name AND parent)
	 */
	public HouseholdLocationEntry getChildHouseholdLocationEntryByName(HouseholdLocationEntry entry, String childName);
	
	/**
	 * Saves the specified household location entry
	 */
	public void saveHouseholdLocationEntry(HouseholdLocationEntry ah);
	
	/**
	 * Deletes all the household location entries (use with care!)
	 */
	public void deleteAllHouseholdLocationEntries();
	
	/**
	 * Returns a list of all household location levels
	 */
	public List<HouseholdLocationLevel> getHouseholdLocationLevels();
	
	/**
	 * Returns the top level in the hierarchy (i.e., the highest level, with no parent)
	 * (Will throw an exception if the multiple levels with no parents)
	 */
	public HouseholdLocationLevel getTopHouseholdLocationLevel();
	
	/**
	 * Gets an household location level by Id
	 */
	public HouseholdLocationLevel getHouseholdLocationLevel(int levelId);
	
	/**
	 * Gets the household location level that is the child of the specified level
	 * (Will throw an exception if there are multiple children--a level should only have one child)
	 */
	public HouseholdLocationLevel getHouseholdLocationLevelByParent(HouseholdLocationLevel parent);
	
	/**
	 * Saves the specified household location level
	 */
	public void saveHouseholdLocationLevel(HouseholdLocationLevel level);
	
	/**
	 * Deletes the specified household location level
	 */
	public void deleteHouseholdLocationLevel(HouseholdLocationLevel level);
	
	
/////////////////////////**********************************************************//////////////////////////
	
	
	//=============================================================================
	/**
	 * Save one HouseholdEncounterType object to the database
	 * 
	 * @param HouseholdEncounterType HouseholdEncounterType object to be saved
	 * @return saved HouseholdEncounterType object
	 */
	//public HouseholdEncounterType saveHouseholdEncounterType(HouseholdEncounterType householdEncounterType);
	
	/**
	 * Get one HouseholdEncounterType record based on the HouseholdEncounterType id
	 * 
	 * @param id the HouseholdEncounterType id
	 * @return HouseholdEncounterType that match the id
	 */
	//public HouseholdEncounterType getHouseholdEncounterType(Integer id);
	
	/**
	 * Get all HouseholdEncounterType records
	 * 
	 * @return all HouseholdEncounterType record in the system
	 */
	//public List<HouseholdEncounterType> getAllHouseholdEncounterType();
	
	//=============================================================================
	/**
	 * Save one HouseholdEncounter object to the database
	 * 
	 * @param HouseholdEncounter HouseholdEncounter object to be saved
	 * @return saved HouseholdEncounter object
	 */
	//public HouseholdEncounter saveHouseholdEncounter(HouseholdEncounter householdEncounter);
	
	/**
	 * Get one HouseholdEncounter record based on the household id
	 * 
	 * @param id the HouseholdEncounter id
	 * @return HouseholdEncounter that match the id
	 */
	//public HouseholdEncounter getHouseholdEncounter(Integer id);
	
	/**
	 * Get all HouseholdEncounter records
	 * 
	 * @return all HouseholdEncounter record in the system
	 */
	//public List<HouseholdEncounter> getAllHouseholdEncounter();
	
	//=============================================================================
	/**
	 * Save one HouseholdObs object to the database
	 * 
	 * @param HouseholdObs HouseholdObs object to be saved
	 * @return saved HouseholdObs object
	 */
	//public HouseholdObs saveHouseholdObs(HouseholdObs householdObs);
	
	/**
	 * Get one HouseholdObs record based on the HouseholdObs id
	 * 
	 * @param id the HouseholdObs id
	 * @return HouseholdObs that match the id
	 */
	//public HouseholdObs getHouseholdObs(Integer id);
	
	/**
	 * Get all HouseholdObs records
	 * 
	 * @return all HouseholdObs record in the system
	 */
	//public List<HouseholdObs> getAllHouseholdObs();
	
	
	//////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Save one Household Encounter object to the database
	 * 
	 * @param HouseholdEncounter householdEncounter object to be saved
	 * @return saved HouseholdEncounter object
	 */
	public HouseholdEncounter saveHouseholdEncounter(HouseholdEncounter householdEncounter);
	
	/**
	 * Purge an encounter from database.
	 * 
	 * @param encounter encounter object to be purged
	 */
	public void deleteHouseholdEncounter(HouseholdEncounter encounter) throws DAOException;
	
	/**
	 * Get one HouseholdEncounter record based on the HouseholdEncounter id
	 * 
	 * @param id the HouseholdEncounter id
	 * @return HouseholdEncounter that match the id
	 */
	public HouseholdEncounter getHouseholdEncounter(Integer id);
	
	/**
	 * Get one HouseholdEncounter record based on the HouseholdEncounter uuid
	 * 
	 * @param id the HouseholdEncounter uuid
	 * @return HouseholdEncounter that match the uuid
	 */
	public HouseholdEncounter getHouseholdEncounterByUUID(String uuid);
	
	/**
	 * @param householdId
	 * @return all encounters for the given household identifer
	 * @throws DAOException
	 */
	public List<HouseholdEncounter> getEncountersByHouseholdId(Integer householdId) throws DAOException;
	
	/**
	 * Get all HouseholdEncounter records
	 * 
	 * @return all HouseholdEncounter record in the system based on householdId
	 */
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdId(Integer id);
	
	/**
	 * Get all HouseholdEncounter records
	 * 
	 * @return all HouseholdEncounter record in the system based on household identifier
	 */
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdIdentifiers(String identifier);
	
	/**
	 * Get all encounters that match a variety of (nullable) criteria. Each extra value for a
	 * parameter that is provided acts as an "and" and will reduce the number of results returned
	 * 
	 * @param household the Household the encounter is for
	 * @param loc the HouseholdLocation this encounter took place
	 * @param fromDate the minimum date (inclusive) this encounter took place
	 * @param toDate the maximum date (exclusive) this encounter took place
	 * @param enteredViaForms the form that entered this encounter must be in this list
	 * @param encounterTypes the type of HouseholdEncounter must be in this list
	 * @param providers the provider of this encounter must be in this list
	 * @param includeVoided true/false to include the voided encounters or not
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
	public List<HouseholdEncounter> getHouseholdEncounters(Household household, HouseholdLocation loc, Date fromDate, Date toDate,
	                                     Collection<Form> enteredViaForms, Collection<HouseholdEncounterType> householdEncounterTypes,
	                                     Collection<User> providers, boolean includeVoided);
	
	
	////////////////////////////////////////
	
	/**
	 * Save a new HouseholdEncounter Type or update an existing Household Encounter Type.
	 * 
	 * @param encounterType
	 * @should save encounter type
	 * @should not overwrite creator
	 * @should not overwrite creator or date created
	 * @should not overwrite date created
	 * @should update an existing encounter type name
	 */
	public HouseholdEncounterType saveHouseholdEncounterType(HouseholdEncounterType encounterType);
	
	/**
	 * Get householdEncounterType by internal identifier
	 * 
	 * @param encounterTypeId Integer
	 * @return encounterType with given internal identifier
	 * @throws APIException
	 * @should throw error if given null parameter
	 */
	public HouseholdEncounterType getHouseholdEncounterType(Integer encounterTypeId) throws APIException;
	
	/**
	 * Get HouseholdEncounterType by its UUID
	 * 
	 * @param uuid
	 * @return
	 * @should find object given valid uuid
	 * @should return null if no object found with given uuid
	 */
	public HouseholdEncounterType getHouseholdEncounterTypeByUuid(String uuid) throws APIException;
	
	/**
	 * Get all Household encounter types (including retired)
	 * 
	 * @return encounter types list
	 * @throws APIException
	 * @should not return retired types
	 */
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes() throws APIException;
	
	/**
	 * Get all Household encounter types. If includeRetired is true, also get retired encounter types.
	 * 
	 * @param includeRetired
	 * @return encounter types list
	 * @throws APIException
	 * @should not return retired types
	 * @should include retired types with true includeRetired parameter
	 */
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes(boolean includeRetired) throws APIException;
	
	/**
	 * Find Household Encounter Types with name matching the beginning of the search string. Search strings
	 * are case insensitive so that "NaMe".equals("name") is true. Includes retired EncounterTypes.
	 * 
	 * @param name of the encounter type to find
	 * @return List<HouseholdEncounterType> matching encounters
	 * @throws APIException
	 * @should return types by partial name match
	 * @should return types by partial case insensitive match
	 * @should include retired types in the results
	 * @should not partial match name on internal substrings
	 * @should return types ordered on name and nonretired first
	 */
	public List<HouseholdEncounterType> findHouseholdEncounterTypes(String name) throws APIException;
	
	/**
	 * @see org.openmrs.api.db.EncounterDAO#deleteEncounterType(org.openmrs.EncounterType)
	 */
	public void deleteHouseholdEncounterType(HouseholdEncounterType encounterType) throws APIException;
	/**
	 * Search for encounters by patient name or patient identifier and returns a specific number of
	 * them from the specified starting position. If start and length are not specified, then all
	 * matches are returned
	 * 
	 * @param query patient name or identifier
	 * @param start beginning index for the batch
	 * @param length number of encounters to return in the batch
	 * @param includeVoided Specifies whether voided encounters should be included
	 * @return list of encounters for the given patient based on batch settings
	 * @throws APIException
	 */
	/*public List<HouseholdEncounter> getHouseholdEncounters(String query, Integer start, Integer length, boolean includeVoided)
	                                                                                                        throws APIException;*/
	
	///////////////////////OBS///////////////////////////////////
	
	/**
	 * Get an Household observation
	 * 
	 * @param obsId integer obsId of observation desired
	 * @return matching Household Obs
	 * @throws APIException
	 * @should get obs matching given obsId
	 */
	public HouseholdObs getHouseholdObs(Integer obsId) throws APIException;
	
	/**
	 * Get HouseholdObs by its UUID
	 * 
	 * @param uuid
	 * @return
	 * @should find object given valid uuid
	 * @should return null if no object found with given uuid
	 */
	public HouseholdObs getHouseholdObsByUuid(String uuid) throws APIException;
	
	/**
	 * Save the given obs to the database. This will move the contents of the given <code>householdObs</code>
	 * to the database. This acts as both the initial save and an update kind of save. The returned
	 * obs will be the same as the obs passed in. It is included for chaining. If this is an initial
	 * save, the obsId on the given <code>householdObs</code> object will be updated to reflect the auto
	 * numbering from the database. The obsId on the returned householdObs will also have this number. If
	 * there is already an obsId on the given <code>householdObs</code> object, the given obs will be voided
	 * and a new row in the database will be created that has a new obs id.
	 * 
	 * @param obs the Household Obs to save to the database
	 * @param changeMessage String explaining why <code>householdObs</code> is being changed. If
	 *            <code>householdObs</code> is a new obs, changeMessage is nullable, or if it is being
	 *            updated, it would be required
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
	public HouseholdObs saveHouseholdObs(HouseholdObs obs) throws APIException;
	
	/**
	 * @see org.openmrs.api.HouseholdObsService#purgeHouseholdObs(HouseholdObs)
	 */
	public void deleteHouseholdObs(HouseholdObs obs) throws DAOException;
	
	/**
	 * This method fetches observations according to the criteria in the given arguments. All
	 * arguments are optional and nullable. If more than one argument is non-null, the result is
	 * equivalent to an "and"ing of the arguments. (e.g. if both a <code>HouseholdLocation</code> and a
	 * <code>fromDate</code> are given, only Obs that are <u>both</u> at that Location and after the
	 * fromDate are returned). <br/>
	 * <br/>
	 * Note: If <code>whom</code> has elements, <code>Household Definition</code> is ignored <br/>
	 * <br/>
	 * Note: to get all observations on a certain date, use:<br/>
	 * Date fromDate = "2009-08-15";<br/>
	 * Date toDate = OpenmrsUtil.lastSecondOfDate(fromDate); List<Obs> obs = getObservations(....,
	 * fromDate, toDate, ...);
	 * 
	 * @param whom List<Household> to restrict obs to (optional)
	 * @param encounters List<HouseholdEncounter> to restrict obs to (optional)
	 * @param questions List<Concept> to restrict the obs to (optional)
	 * @param answers List<Concept> to restrict the valueCoded to (optional)
	 * @param locations The org.openmrs.module.household.model.HouseholdLocation objects to restrict to (optional)
	 * @param sort list of column names to sort on (obsId, obsDatetime, etc) if null, defaults to
	 *            obsDatetime (optional)
	 * @param mostRecentN restrict the number of obs returned to this size (optional)
	 * @param obsGroupId the Obs.getObsGroupId() to this integer (optional)
	 * @param fromDate the earliest HouseholdObs date to get (optional)
	 * @param toDate the latest HouseholdObs date to get (optional)
	 * @param includeVoidedObs true/false whether to also include the voided obs (required)
	 * @return list of Observations that match all of the criteria given in the arguments
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
	public List<HouseholdObs> getObservations(List<Household> whom, List<HouseholdEncounter> encounters, List<Concept> questions,
	                                 List<Concept> answers, List<HouseholdLocation> locations,
	                                 List<String> sort, Integer mostRecentN, Integer obsGroupId, Date fromDate, Date toDate,
	                                 boolean includeVoidedObs) throws APIException;
}
