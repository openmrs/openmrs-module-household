/**
 * 
 */
package org.openmrs.module.household.dao.hibernate;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.openmrs.Concept;
import org.openmrs.ConceptName;
import org.openmrs.Form;
import org.openmrs.Person;
import org.openmrs.User;
import org.openmrs.api.APIException;
import org.openmrs.api.db.DAOException;
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


/**
 * @author Ampath Developers
 *
 */
public class HibernateHouseholdDAO implements HouseholdDAO {
	
	private SessionFactory sessionFactory;
	
	
    /**
     * @return the sessionFactory
     */
    public SessionFactory getSessionFactory() {
    	return sessionFactory;
    }

	
    /**
     * @param sessionFactory the sessionFactory to set
     */
    public void setSessionFactory(SessionFactory sessionFactory) {
    	this.sessionFactory = sessionFactory;
    }


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#saveHouseholdDefinition(
	 * org.openmrs.module.household.model.HouseholdDefinition)
	 */
    
	public HouseholdDefinition saveHouseholdDefinition(
			HouseholdDefinition householdDefinition) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdDefinition);
		return householdDefinition;
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdDefinition(java.lang.Integer)
	 */
	
	public HouseholdDefinition getHouseholdDefinition(Integer id) {
		return (HouseholdDefinition) sessionFactory.getCurrentSession().get(
				HouseholdDefinition.class, id);
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdDefinitions()
	 */
	
	@SuppressWarnings("unchecked")
	public List<HouseholdDefinition> getAllHouseholdDefinitions() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdDefinition.class);
		return criteria.list();
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#saveHouseholdGroup(
	 * org.openmrs.module.household.model.HouseholdGroups)
	 */
	
	public Household saveHouseholdGroup(Household householdGroups) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdGroups);
		return householdGroups;
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdGroup(java.lang.Integer)
	 */
	
	public Household getHouseholdGroup(Integer id) {
		return (Household) sessionFactory.getCurrentSession().get(
				Household.class, id);
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdGroups()
	 */
	
	@SuppressWarnings("unchecked")
	public List<Household> getAllHouseholdGroups() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				Household.class);
		return criteria.list();
	}



	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#saveHouseholdMembership(
	 * org.openmrs.module.household.model.HouseholdMembership)
	 */
	
	public HouseholdMembership saveHouseholdMembership(
			HouseholdMembership householdMemberships) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdMemberships);
		return householdMemberships;
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdMembership(java.lang.Integer)
	 */
	
	public HouseholdMembership getHouseholdMembership(Integer id) {
		return (HouseholdMembership) sessionFactory.getCurrentSession().get(
				HouseholdMembership.class, id);
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdMemberships()
	 */
	
	@SuppressWarnings("unchecked")
	public List<HouseholdMembership> getAllHouseholdMemberships() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
		.add(Expression.eq("voided", false));
		
		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdMembershipsByID(int id)
	 */
	@SuppressWarnings("unchecked")
	
	public List<HouseholdMembership> getAllHouseholdMembershipsByGroup(Household grp) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
					
				.add(Expression.eq("householdMembershipGroups",grp))
				.add(Expression.eq("voided", false));
		
				return criteria.list();
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdMembershipsByPerson(Person p)
	 */
	@SuppressWarnings("unchecked")
	
	public List<HouseholdMembership> getAllHouseholdMembershipsByPerson(Person p) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdMembership.class).add(Restrictions.eq("householdMembershipMember",p));
		return criteria.list();
	}
	
@SuppressWarnings("unchecked")
	
	public List<HouseholdMembership> getHouseholdMembershipByGrpByPsn(Person p,Household grp) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdMembership.class)
				.add(Restrictions.eq("householdMembershipMember",p))
				.add(Restrictions.eq("householdMembershipGroups", grp));
		return criteria.list();
	}

//========================================================================
	
	public HouseholdAttribute saveHouseholdAttribute(
			HouseholdAttribute householdAttribute) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdAttribute);
		return householdAttribute;
	}


	
	public HouseholdAttribute getHouseholdAttribute(Integer id) {
		return (HouseholdAttribute) sessionFactory.getCurrentSession().get(
				HouseholdAttribute.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdAttribute> getAllHouseholdAttribute() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdAttribute.class);
		return criteria.list();
	}


	
	public HouseholdAttribValue saveHouseholdAttribValue(
			HouseholdAttribValue householdAttribValue) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdAttribValue);
		return householdAttribValue;
	}


	
	public HouseholdAttribValue getHouseholdAttribValue(Integer id) {
		return (HouseholdAttribValue) sessionFactory.getCurrentSession().get(
				HouseholdAttribValue.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdAttribValue> getAllHouseholdAttribValue() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdAttribValue.class);
		return criteria.list();
	}


	
	public HouseholdLocation saveHouseholdLocation(
			HouseholdLocation householdLocation) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdLocation);
		return householdLocation;
	}


	
	public HouseholdLocation getHouseholdLocation(Integer id) {
		return (HouseholdLocation) sessionFactory.getCurrentSession().get(
				HouseholdLocation.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdLocation> getAllHouseholdLocation() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdLocation.class);
		return criteria.list();
	}

	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdLocation(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public HouseholdLocation getHouseholdLocation(String name) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class).add(
			    Expression.eq("name", name));
			
			List<HouseholdLocation> locations = criteria.list();
			if (null == locations || locations.isEmpty()) {
				return null;
			}
			return locations.get(0);
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getAllHouseholdLocations(boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HouseholdLocation> getAllHouseholdLocations(
			boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class);
		if (!includeRetired) {
			criteria.add(Expression.eq("retired", false));
		}
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdLocations(java.lang.String, boolean, java.lang.Integer, java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HouseholdLocation> getHouseholdLocations(String nameFragment,
			boolean includeRetired, Integer start, Integer length)
			throws DAOException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class);
		if (!includeRetired)
			criteria.add(Expression.eq("retired", false));
		
		if (StringUtils.isNotBlank(nameFragment))
			criteria.add(Expression.ilike("name", nameFragment, MatchMode.START));
		
		criteria.addOrder(Order.asc("name"));
		if (start != null)
			criteria.setFirstResult(start);
		if (length != null && length > 0)
			criteria.setMaxResults(length);
		
		return criteria.list();
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#deleteHouseholdLocation(org.openmrs.module.household.model.HouseholdLocation)
	 */
	@Override
	public void deleteHouseholdLocation(HouseholdLocation location) {
		sessionFactory.getCurrentSession().delete(location);
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getHouseholdLocationByUuid(java.lang.String)
	 */
	@Override
	public HouseholdLocation getHouseholdLocationByUuid(String uuid) {
		return (HouseholdLocation) sessionFactory.getCurrentSession().createQuery("from Household_Location l where l.uuid = :uuid").setString(
			    "uuid", uuid).uniqueResult();
	}


	/* (non-Javadoc)
	 * @see org.openmrs.module.household.dao.HouseholdDAO#getCountOfHouseholdLocations(java.lang.String, java.lang.Boolean)
	 */
	@Override
	public Integer getCountOfHouseholdLocations(String nameFragment,
			Boolean includeRetired) {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdLocation.class);
		if (!includeRetired)
			criteria.add(Expression.eq("retired", false));
		
		if (StringUtils.isNotBlank(nameFragment))
			criteria.add(Expression.ilike("name", nameFragment, MatchMode.START));
		
		criteria.setProjection(Projections.rowCount());
		
		return (Integer) criteria.uniqueResult();
	}


	public HouseholdEncounter saveHouseholdEncounter(
			HouseholdEncounter householdEncounter) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdEncounter);
		return householdEncounter;
	}


	
	public HouseholdEncounter getHouseholdEncounter(Integer id) {
		return (HouseholdEncounter) sessionFactory.getCurrentSession().get(
				HouseholdEncounter.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounter> getEncountersByHouseholdId(Integer householdId) throws DAOException {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(HouseholdEncounter.class).createAlias("household", "h").add(
			    Expression.eq("h.Id", householdId)).add(Expression.eq("voided", false)).addOrder(
			    Order.desc("householdEncounterDatetime"));
			
			return crit.list();
	}


	
	public HouseholdEncounter getHouseholdEncounterByUUID(String uuid) {
		return (HouseholdEncounter) sessionFactory.getCurrentSession().createQuery("from Household_Encounter e where e.uuid = :uuid")
        .setString("uuid", uuid).uniqueResult();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdId(
			Integer id) {
		Criteria criteria = (Criteria) sessionFactory.getCurrentSession().get(
				HouseholdEncounter.class, id);
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounter> getAllHouseholdEncountersByHouseholdIdentifiers(
			String identifier) {
		//TODO Correct this
		Criteria criteria = (Criteria) sessionFactory.getCurrentSession().get(
				HouseholdEncounter.class, identifier);
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounter> getHouseholdEncounters(Household household,
			HouseholdLocation loc, Date fromDate, Date toDate,
			Collection<Form> enteredViaForms,
			Collection<HouseholdEncounterType> householdEncounterTypes,
			Collection<User> providers, boolean includeVoided) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(HouseholdEncounter.class);
		if (household != null && household.getId() != null) {
			crit.add(Expression.eq("householdGroupId", household));
		}
		if (loc != null && loc.getHouseholdLocationId() != null) {
			crit.add(Expression.eq("householdLocation", loc));
		}
		if (fromDate != null) {
			crit.add(Expression.ge("householdEncounterDatetime", fromDate));
		}
		if (toDate != null) {
			crit.add(Expression.le("householdEncounterDatetime", toDate));
		}
		if (enteredViaForms != null && enteredViaForms.size() > 0) {
			crit.add(Expression.in("form", enteredViaForms));
		}
		if (householdEncounterTypes != null && householdEncounterTypes.size() > 0) {
			crit.add(Expression.in("householdEncounterTypes", householdEncounterTypes));
		}
		if (providers != null && providers.size() > 0) {
			crit.add(Expression.in("provider", providers));
		}
		if (!includeVoided) {
			crit.add(Expression.eq("voided", false));
		}
		crit.addOrder(Order.asc("householdEncounterDatetime"));
		return crit.list();
	}


	
	public HouseholdEncounterType saveHouseholdEncounterType(
			HouseholdEncounterType encounterType) {
		sessionFactory.getCurrentSession().saveOrUpdate(encounterType);
		return encounterType;
	}


	
	public HouseholdEncounterType getHouseholdEncounterType(
			Integer encounterTypeId) throws APIException {
		return (HouseholdEncounterType) sessionFactory.getCurrentSession().get(
				HouseholdEncounterType.class, encounterTypeId);
	}


	
	public HouseholdEncounterType getHouseholdEncounterTypeByUuid(String uuid)
			throws APIException {
		return (HouseholdEncounterType) sessionFactory.getCurrentSession().createQuery("from Household_Encounter_Type et where et.uuid = :uuid")
        .setString("uuid", uuid).uniqueResult();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes()
			throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdEncounterType.class);
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounterType> getAllHouseholdEncounterTypes(
			boolean includeRetired) throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdEncounterType.class);
		
		criteria.addOrder(Order.asc("name"));
		
		if (includeRetired == false)
			criteria.add(Expression.eq("retired", false));
		
		return criteria.list();
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounterType> findHouseholdEncounterTypes(String name)
			throws APIException {
		return sessionFactory.getCurrentSession().createCriteria(HouseholdEncounterType.class)
		// 'ilike' case insensitive search
		        .add(Expression.ilike("name", name, MatchMode.START)).addOrder(Order.asc("name")).addOrder(
			            Order.asc("retired")).list();
	}

	
	public HouseholdObs getHouseholdObs(Integer obsId) throws APIException {
		return (HouseholdObs) sessionFactory.getCurrentSession().get(HouseholdObs.class, obsId);
	}


	
	public HouseholdObs getHouseholdObsByUuid(String uuid) throws APIException {
		return (HouseholdObs) sessionFactory.getCurrentSession().createQuery("from Household_Obs o where o.uuid = :uuid").setString("uuid",
			    uuid).uniqueResult();
	}


	
	public HouseholdObs saveHouseholdObs(HouseholdObs obs)
			throws APIException {
		if (obs.hasGroupMembers() && obs.getId() != null) {
			// hibernate has a problem updating child collections
			// if the parent object was already saved so we do it
			// explicitly here
			for (HouseholdObs member : obs.getHouseholdGroupMembers())
				if (member.getId() == null)
					saveHouseholdObs(member);
		}
		
		sessionFactory.getCurrentSession().saveOrUpdate(obs);
		
		return obs;
	}
	
	/**
	 * @see org.openmrs.api.HouseholdObsService#purgeHouseholdObs(HouseholdObs)
	 */
	public void deleteHouseholdObs(HouseholdObs obs) throws APIException{
		sessionFactory.getCurrentSession().delete(obs);
	}

	
	@SuppressWarnings("unchecked")
	public List<HouseholdObs> getObservations(List<Household> household,
			List<HouseholdEncounter> encounters, List<Concept> questions,
			List<Concept> answers, List<HouseholdLocation> locations,
			List<String> sortList, Integer mostRecentN, Integer obsGroupId,
			Date fromDate, Date toDate, boolean includeVoidedObs)
			throws APIException {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(HouseholdObs.class, "obs");
		
		if (CollectionUtils.isNotEmpty(household))
			criteria.add(Restrictions.in("householdGroups", household));
		
		if (CollectionUtils.isNotEmpty(encounters))
			criteria.add(Restrictions.in("householdEncounter", encounters));
		
		if (CollectionUtils.isNotEmpty(questions))
			criteria.add(Restrictions.in("concept", questions));
		
		if (CollectionUtils.isNotEmpty(answers))
			criteria.add(Restrictions.in("valueCoded", answers));
		
		if (CollectionUtils.isNotEmpty(locations))
			criteria.add(Restrictions.in("householdLocation", locations));
		
		// TODO add an option for each sort item to be asc/desc
		if (CollectionUtils.isNotEmpty(sortList)) {
			for (String sort : sortList) {
				if (sort != null && !"".equals(sort))
					criteria.addOrder(Order.desc(sort));
			}
		}
		
		if (mostRecentN != null && mostRecentN > 0)
			criteria.setMaxResults(mostRecentN);
		
		if (obsGroupId != null) {
			criteria.createAlias("householdObsGroup", "og");
			criteria.add(Restrictions.eq("og.obsId", obsGroupId));
		}
		
		if (fromDate != null)
			criteria.add(Restrictions.ge("householdObsDatetime", fromDate));
		
		if (toDate != null)
			criteria.add(Restrictions.le("householdObsDatetime", toDate));
		
		List<ConceptName> valueCodedNameAnswers = null;
			criteria.add(Restrictions.in("valueCodedName", valueCodedNameAnswers));
		
		if (includeVoidedObs == false)
			criteria.add(Restrictions.eq("voided", false));
			
			return criteria.list();
	}


	
	public void deleteHouseholdEncounter(HouseholdEncounter encounter)
			throws DAOException {
		sessionFactory.getCurrentSession().delete(encounter);
		
	}
	
	/**
	 * @see org.openmrs.api.db.EncounterDAO#deleteEncounterType(org.openmrs.EncounterType)
	 */
	public void deleteHouseholdEncounterType(HouseholdEncounterType encounterType) throws DAOException {
		sessionFactory.getCurrentSession().delete(encounterType);
	}



	/*
	


	



	
	public HouseholdEncounter getHouseholdEncounter(Integer id) {
		return (HouseholdEncounter) sessionFactory.getCurrentSession().get(
				HouseholdEncounter.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdEncounter> getAllHouseholdEncounter() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdEncounter.class);
		return criteria.list();
	}


	
	public HouseholdObs saveHouseholdObs(HouseholdObs householdObs) {
		sessionFactory.getCurrentSession().saveOrUpdate(householdObs);
		return householdObs;
	}


	
	public HouseholdObs getHouseholdObs(Integer id) {
		return (HouseholdObs) sessionFactory.getCurrentSession().get(
				HouseholdObs.class, id);
	}


	@SuppressWarnings("unchecked")
	
	public List<HouseholdObs> getAllHouseholdObs() {
		Criteria criteria = sessionFactory.getCurrentSession().createCriteria(
				HouseholdObs.class);
		return criteria.list();
	}*/


	
}
