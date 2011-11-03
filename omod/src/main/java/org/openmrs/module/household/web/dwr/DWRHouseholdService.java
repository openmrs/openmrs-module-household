/**
 * 
 */
package org.openmrs.module.household.web.dwr;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdLocationEntry;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.model.HouseholdObs;
import org.openmrs.module.household.service.HouseholdService;
import org.openmrs.module.household.util.HouseholdCheckDigit;

/**
 * @author Ampath developers
 *
 */
public class DWRHouseholdService {
	private static final Log log = LogFactory.getLog(DWRHouseholdService.class);
	
	public String getSubLocations(String strLoc) {
		// hack to make sure other Locations aren't still hanging around
		HouseholdService service = Context.getService(HouseholdService.class);
		Context.clearSession();
		
		//Get all the levels locations,sub-locations,village
		List<HouseholdLocationLevel> levels = service.getOrderedHouseholdLocationLevels(false);
		
		//Get all location entries
		List<HouseholdLocationEntry> hl = service.getHouseholdLocationEntriesByLevel(levels.get(0));
		
		//Get location entry for given name
		List<HouseholdLocationEntry> hln = service.getHouseholdLocationEntriesByLevelAndName(hl.get(0).getHouseholdLocationLevel(), strLoc);
		
		String strVal="";
		for (int i = 0; hln.size()>0; i++) {
			//Get sub-locations for this location given the id
			List<HouseholdLocationEntry> hl1 = service.getChildHouseholdLocationEntries(hln.get(0).getHouseholdLocationEntryId());
			for (int n = 0; n<hl1.size(); n++) {
				if(!(n == hl1.size()-1))
					strVal += hl1.get(n) + ",";
				else
					strVal = strVal + hl1.get(n);
			}
			break;
		}
		
		
		
		//log.info("\n =====" + strVal);
		return strVal;
	}
	
	public String getVillage(String strSubLoc, String strLoc) {
		// hack to make sure other Locations aren't still hanging around
		HouseholdService service = Context.getService(HouseholdService.class);
		Context.clearSession();
		
		//Get all the levels locations,sub-locations,village
		List<HouseholdLocationLevel> levels = service.getOrderedHouseholdLocationLevels(false);
		//log.info("\n =====1=" + levels.size());
		//Get all location entries
		//List<HouseholdLocationEntry> hl = service.getHouseholdLocationEntriesByLevel(levels.get(0));
		//log.info("\n =====2=" + hl.size());
		//Get location entry for given name
		//List<HouseholdLocationEntry> hln = service.getHouseholdLocationEntriesByLevelAndName(hl.get(0).getHouseholdLocationLevel(), strLoc);
		//log.info("\n =====3=" + hln.size());
		//Get all sub-location for this entry
		List<HouseholdLocationEntry> hln2 = service.getHouseholdLocationEntriesByLevel(levels.get(1));
		//log.info("\n =====4=" + hln2.size());
		//Get sub-location entry for given name
		List<HouseholdLocationEntry> hln3 = service.getHouseholdLocationEntriesByLevelAndName(hln2.get(0).getHouseholdLocationLevel(), strSubLoc);
		//log.info("\n =====5=" + hln3.size());
		
		String strVal="";
		for (int i = 0; hln3.size()>0; i++) {
			//Get villages for this sub-location given the id
			List<HouseholdLocationEntry> hl1 = service.getChildHouseholdLocationEntries(hln3.get(0).getHouseholdLocationEntryId());
			for (int n = 0; n<hl1.size(); n++) {
				if(!(n == hl1.size()-1))
					strVal += hl1.get(n) + ",";
				else
					strVal = strVal + hl1.get(n);
			}
			break;
		}
		
		
		
		//log.info("\n =====" + strVal);
		return strVal;
	}
	/**
	 * This method return the household members for an household
	 * @param strHh
	 * @return
	 */
	public String getHousehold(String strHh) {
		HouseholdService service = Context.getService(HouseholdService.class);
		Household hh = new Household();
		hh = service.getHouseholdGroupByIdentifier(strHh);
		List<HouseholdMembership> hm = service.getAllHouseholdMembershipsByGroup(hh);
		
		String strHousehold = null;
		
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = hm.iterator(); iterator.hasNext();) {
			HouseholdMembership householdMembership = (HouseholdMembership) iterator.next();
			
			if(householdMembership.isHouseholdMembershipHeadship()){
				
				
				
				
				strHousehold = householdMembership.getHouseholdMembershipMember().getNames() +
					"," +
					householdMembership.getHouseholdMembershipMember().getAttribute(10);//"Contact Phone Number"
			}
		}
		
		return strHousehold;
	}
	/**
	 * This method returns all households that belong to a given definition/group.
	 * @param strUuid
	 * @return household identifiers list
	 */
	public String getHouseholds(String strUuid){
		HouseholdService service = Context.getService(HouseholdService.class);
		List<Household> hholds = service.getAllHouseholdsByDefinitionUuid(strUuid);
		String toRet = "";
		toRet = hholds.get(0).getHouseholdDef().getHouseholdDefinitionsCodeinfull();
		for (@SuppressWarnings("rawtypes")
				Iterator iterator = hholds.iterator(); iterator.hasNext();) {
			Household household = (Household) iterator.next();
			toRet +="|" + household.getHouseholdIdentifier();
		}
		return toRet;
	}
	
	public String getHouseholdEncountersObs(String encUuid){
		HouseholdService service = Context.getService(HouseholdService.class);
		Context.clearSession();
		log.info("\n:::::::::>0>" + encUuid);
		HouseholdEncounter he = service.getHouseholdEncounterByUUID(encUuid);
		log.info("\n:::::::::>1>" + he.getUuid());
		
		/*List<HouseholdObs> obsList = (List<HouseholdObs>) service.getObservations(h,e, 
				null, null, null, null, null, null, null, null, false);*/
		Set<HouseholdObs> obsList = he.getAllHouseholdObs();
        log.info("\n:::::::::>2" + obsList.size());
		String toRet = "";
        for (HouseholdObs householdObs : obsList) {

			if(StringUtils.isEmpty(toRet))
				toRet="";
			else
				toRet+="|";
			/*toRet += householdObs.getConcept().getDisplayString() +
			"," + householdObs.getConcept().getAnswers(false);  */
			String ans = "";
			//valueDatetime,valueNumeric,valueText,valueCoded
			if(householdObs.getValueDatetime()!= null)
				ans = householdObs.getValueDatetime()+ "";
			else if(householdObs.getValueNumeric()!= null)
				ans = householdObs.getValueNumeric() + "";
			else if(householdObs.getValueText()!= null)
				ans = householdObs.getValueText();
			else if(householdObs.getValueCoded()!= null)
				ans = householdObs.getValueCoded().getName().toString();
			
			log.info("\n:::::::::>"+ householdObs.getConcept().getName() + "|" + ans);
			
            toRet += householdObs.getConcept().getName() +
			"|" + ans;
        }
		log.info("\n:::::::::>"+ toRet);
		return toRet;
	}
	
	public String getHouseholdMembersPortlet(String grpids){
		HouseholdService service = Context.getService(HouseholdService.class);
		Context.clearSession();
		Household grp=service.getHouseholdGroup(Integer.parseInt(grpids));
		List<HouseholdMembership> householdsMem = service.getAllHouseholdMembershipsByGroup(grp);

		String strHousehold = "";
		for (int i=0; i<householdsMem.size(); i++) {
			if(StringUtils.isEmpty(strHousehold))
				strHousehold="";
			else
				strHousehold+="|";
			strHousehold += householdsMem.get(i).getHouseholdMembershipMember().getPersonName() +
					"|" + householdsMem.get(i).getHouseholdMembershipMember().getGender() +
					"|" + householdsMem.get(i).getHouseholdMembershipMember().getBirthdate();
		}
		return strHousehold;
	}
	
	public boolean getCheckDigit(String hhold){
		try {
			String []strHHold = hhold.split("-");
			int x = HouseholdCheckDigit.CheckDigit(strHHold[0]);
			log.info("\n ======" + strHHold[0] + "-" + strHHold[1]);
			if(x == Integer.parseInt(strHHold[1]))
				return true;
			else
				return false;
		} catch (Exception e) {
			return false;
		}
	}

}
