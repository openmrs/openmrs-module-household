/**
 * 
 */
package org.openmrs.module.household.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import org.openmrs.Concept;
import org.openmrs.Form;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.household.dao.HouseholdDAO;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdAttribValue;
import org.openmrs.module.household.model.HouseholdAttribute;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.model.HouseholdLocation;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.model.HouseholdObs;
import org.openmrs.module.household.service.HouseholdService;


/**
 *  @author Ampath Developers
 *
 */
public class HouseholdServiceImpl extends BaseOpenmrsService implements HouseholdService {
	
	private HouseholdDAO householdDAO;
	
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

