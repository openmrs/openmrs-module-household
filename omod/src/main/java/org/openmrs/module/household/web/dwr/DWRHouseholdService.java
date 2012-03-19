/**
 * 
 */
package org.openmrs.module.household.web.dwr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdLocationEntry;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.model.HouseholdObs;
import org.openmrs.module.household.service.HouseholdService;
import org.openmrs.module.household.util.HouseholdCheckDigit;

/**
 * @author jmwogi
 *
 */
public class DWRHouseholdService {
	private static final Log log = LogFactory.getLog(DWRHouseholdService.class);
	
	public String getSubLocations(String strLoc) {
		HouseholdService service = Context.getService(HouseholdService.class);
		Context.clearSession();
		List<HouseholdLocationLevel> levels = service.getOrderedHouseholdLocationLevels(false);
		List<HouseholdLocationEntry> hl = service.getHouseholdLocationEntriesByLevel(levels.get(0));
		List<HouseholdLocationEntry> hln = service.getHouseholdLocationEntriesByLevelAndName(hl.get(0).getHouseholdLocationLevel(), strLoc);
		
		String strVal="";
		for (int i = 0; hln.size()>0; i++) {
			List<HouseholdLocationEntry> hl1 = service.getChildHouseholdLocationEntries(hln.get(0).getHouseholdLocationEntryId());
			for (int n = 0; n<hl1.size(); n++) {
				if(!(n == hl1.size()-1))
					strVal += hl1.get(n) + ",";
				else
					strVal = strVal + hl1.get(n);
			}
			break;
		}
		return strVal;
	}
	
	public String getVillage(String strSubLoc, String strLoc) {
		HouseholdService service = Context.getService(HouseholdService.class);
		Context.clearSession();
		List<HouseholdLocationLevel> levels = service.getOrderedHouseholdLocationLevels(false);
		List<HouseholdLocationEntry> hln = service.getHouseholdLocationEntriesByLevelAndName(levels.get(0), strLoc);
		List<HouseholdLocationEntry> hlnSub = service.getChildHouseholdLocationEntries(hln.get(0).getId());
		HouseholdLocationEntry sublocation = null;
		for (int n = 0; n<hlnSub.size(); n++) {
			if (hlnSub.get(n).getName().equals(strSubLoc)){
				sublocation = hlnSub.get(n);
				break;
			}
		}
		List<HouseholdLocationEntry> hlnVill = service.getChildHouseholdLocationEntries(sublocation.getId());
		
		String strVal="";
		for (int n = 0; n<hlnVill.size(); n++) {
			if(!(n == hlnVill.size()-1))
				strVal += hlnVill.get(n) + ",";
			else
				strVal = strVal + hlnVill.get(n);
		}
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
		HouseholdEncounter he = service.getHouseholdEncounterByUUID(encUuid);
		
		Set<HouseholdObs> obsList = he.getAllHouseholdObs();
        String toRet = "";
        for (HouseholdObs householdObs : obsList) {

			if(StringUtils.isEmpty(toRet))
				toRet="";
			else
				toRet+="|";
			String ans = "";
			if(householdObs.getValueDatetime()!= null)
				ans = householdObs.getValueDatetime()+ "";
			else if(householdObs.getValueNumeric()!= null)
				ans = householdObs.getValueNumeric() + "";
			else if(householdObs.getValueText()!= null)
				ans = householdObs.getValueText();
			else if(householdObs.getValueCoded()!= null)
				ans = householdObs.getValueCoded().getName().toString();
			
			
            toRet += householdObs.getConcept().getName() +
			"|" + ans;
        }
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
	
	public List<HouseholdDefinition> getParentHouseholdDefinitions(String intPassed){
		HouseholdService service = Context.getService(HouseholdService.class);
		if(!StringUtils.isEmpty(intPassed)){
			int householdDefinition = Integer.parseInt(intPassed);
			HouseholdDefinition hd = service.getHouseholdDefinition(householdDefinition);
			List<HouseholdDefinition> householdsDefinitionChildren = service.getHouseholdDefinitionChildren(hd);
			
			return householdsDefinitionChildren;
		}else
			return null;
		/*else if(!isParentPassed && !StringUtils.isEmpty(intPassed)){
			int householdDefinition = Integer.parseInt(intPassed);
			HouseholdDefinition hd = service.getHouseholdDefinition(householdDefinition);
			
			map.addAttribute("hdef", hd);
			map.addAttribute("par", 3);
		}*/
	}
	/**
	 * Get Households by personID
	 * @Param personID
	 */
	public List<HouseholdMembership> getHouseholdsByPersonID(String personID){
		Person p = Context.getPersonService().getPerson(Integer.parseInt(personID));
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdMembership> householdMem = service.getAllHouseholdMembershipsByPerson(p);
		return householdMem;
	}
	
	public  String getHouseholdMems(String householdGrp){
		
		HouseholdService service = Context.getService(HouseholdService.class);
		Household hh = new Household();
		hh = service.getHouseholdGroupByIdentifier(householdGrp);
		List<HouseholdMembership> householdMembers = service.getAllHouseholdMembershipsByGroup(hh);
		
		/*return householdMembers;*/
		String householdMembersList="";
		for(int i=0; i<householdMembers.size(); i++){
			
			if(StringUtils.isEmpty(householdMembersList))
				householdMembersList="";
			
				
			else
				householdMembersList +="|";
				
			householdMembersList += householdMembers.get(i).getHouseholdMembershipMember().getPersonName()
			+","+
			householdMembers.get(i).getHouseholdMembershipMember().getGender()
			+","+
			householdMembers.get(i).getHouseholdMembershipMember().getBirthdate().toString().substring(0, 11)+","+
			householdMembers.get(i).isHouseholdMembershipHeadship()
			+","+
			householdMembers.get(i).getStartDate().toString().substring(0, 11)
			+","+
			householdMembers.get(i).getId();
			
			
			
		}
		return householdMembersList;
	}
	
	public void voidMembers(String voidId,String voidReason){
		
		HouseholdService service = Context.getService(HouseholdService.class);
		
		String [] hseMembers=voidId.split(",");
		Arrays.sort(hseMembers);
		for(int m=0;m<hseMembers.length;m++){
				//introduction of variable to handle an individual
				String mem=hseMembers[m];
				try {
					HouseholdMembership membership = service.getHouseholdMembership(Integer.parseInt(mem));
					membership.setHouseholdMembershipMember(Context.getPatientService().getPatientByUuid(membership.getHouseholdMembershipMember().getUuid()));
					membership.setVoided(true);
					membership.setVoidReason(voidReason);
					membership.setEndDate(new Date());
					service.saveHouseholdMembership(membership);
					//return true;
				} 
				catch (Exception e) {
					log.info("Not able to void the selection"+e.toString());
				}
				
		}
		
		
		
	}
	public String saveMembersToHseHold(String householdMemToAdd,String grpId,String provider,String startDate) throws ParseException{
		
		HouseholdService service = Context.getService(HouseholdService.class);
			
			
				
				Household group=service.getHouseholdGroupByIdentifier(grpId);
				
				String []strMem = householdMemToAdd.split(",");
				
				Arrays.sort(strMem);
				
				int j = 1;
				
				for (int i = 1; i < strMem.length; i++)
				{
					if (! strMem[i].equals(strMem[i -1]))
						strMem[j++] = strMem[i];
				}
				
				String[] unique = new String[j];
				System.arraycopy(strMem, 0, unique, 0, j);
				
				for(int i=0; i<unique.length; i++ ){
					
					String strMember = unique[i];
					
					HouseholdMembership membership = new HouseholdMembership();
					Person pn = Context.getPersonService().getPerson(Integer.parseInt(strMember));
					List<HouseholdMembership> member = service.getHouseholdMembershipByGrpByPsn(pn, group);
					if(member.isEmpty()){ 
						
						membership.setHouseholdMembershipMember(pn);
						membership.setHouseholdMembershipGroups(group);
				
						membership.setHouseholdMembershipHeadship(false);
						membership.setProviderId(provider);
						
						if(StringUtils.isEmpty(startDate))
							membership.setStartDate(new Date());
						else
							membership.setStartDate(dateFormatHelper(startDate));
						
						// then save the members to this household grpId
						service.saveHouseholdMembership(membership);
						
					}
					else{
						continue;
					}
						
					
				}
			
		
		
		return "Added successfully  ";
	}
	
	static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	private static Date dateFormatHelper(String strvalue) throws ParseException {
		if (strvalue == null || strvalue.length() == 0)
			return new Date();
		else
			return dateFormat.parse(strvalue);
	}

}
