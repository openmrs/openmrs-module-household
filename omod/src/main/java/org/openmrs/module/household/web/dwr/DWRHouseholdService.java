package org.openmrs.module.household.web.dwr;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
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
import org.openmrs.module.household.model.HouseholdDefinitionParent;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdEncounterType;
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
	
	@SuppressWarnings("unused")
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
                List<Household> hholds = new ArrayList<Household>();
		try {
                    int householdID = Integer.parseInt(strUuid);
                    HouseholdDefinition hd = service.getHouseholdDefinition(householdID);
                    hholds = service.getAllHouseholdsByDefinitionUuid(hd.getUuid());
                } catch (Exception e) {
                    hholds = service.getAllHouseholdsByDefinitionUuid(strUuid);
                }
                
		String toRet = "";
                int voidedHouses = 0;
                int retiredHouses = 0;
                //FDH1-8,1|FDH2-6
                //if(retired/voided)FDH1-8,1[V]|FDH2-6[R]
                for (Household household : hholds) {
                    String ret = "";
                    if(household.getVoided()){//check if Active
                        ret = household.getHouseholdIdentifier() + "[V]," + household.getId();
                        voidedHouses++;
                    }else{
                        if(!StringUtils.isEmpty(household.getRetireReason())){
                            ret = household.getHouseholdIdentifier() + "[R]," + household.getId();
                            retiredHouses++;
                        }else{
                            ret = household.getHouseholdIdentifier() + "," + household.getId();
                        }
                    }
                    if(!StringUtils.isEmpty(toRet)){
                        toRet += "|" + ret; 
                    }else{
                        toRet = ret;
                    }
                            
                }
                
		toRet += "*" + hholds.size() + "*" + voidedHouses + "*" + retiredHouses;
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
			int householdDefinitionParent = Integer.parseInt(intPassed);
			HouseholdDefinitionParent hdp = service.getHouseholdDefinitionParent(householdDefinitionParent);
			List<HouseholdDefinition> householdsDefinitionChildren = service.getHouseholdDefinitionParentChildren(hdp);
			
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
	
	public String createHousehold(String [] arrInput) throws ParseException{
		
		String hDefinition = arrInput[0];
		String personList = arrInput[1];
		String hIdentifierProvided = arrInput[2];
		String startDateProvided = arrInput[3];
		String headOrIndex = arrInput[4];
		String provider = arrInput[5];
		
		HouseholdService service = Context.getService(HouseholdService.class);
		HouseholdDefinition hd = service.getHouseholdDefinition(Integer.parseInt(hDefinition));
		
		Household hh = service.getHouseholdGroupByIdentifier(hIdentifierProvided);
		try {hh.getId();return "false, The household identifier is already assigned.";}catch (Exception e) {}
		
		String [] oldArrMembers =personList.split(",");
		Set<String> arrMembers = new HashSet<String>();
		for (String x : oldArrMembers){
			//Makes sure that this member had not been previously registered to this definition
			Person p = Context.getPersonService().getPerson(Integer.parseInt(x));
			List<HouseholdMembership> hm = Context.getService(HouseholdService.class).getAllHouseholdMembershipsByPerson(p);
			for(HouseholdMembership mem : hm){
				if (mem.getHouseholdMembershipGroups().getHouseholdDef().equals(hd)) {
					return "false,The following members currently exist: " + p.getFamilyName() + " " + p.getGivenName() + " " + p.getMiddleName();
				}
			}
			//populate new members
			arrMembers.add(x);
		}
		
		Household grp = new Household();
		grp.setHouseholdDef(hd);
		grp.setProvider(provider);
                grp.setStartDate(dateFormatHelper(arrInput[3]));
		if(!StringUtils.isEmpty(hIdentifierProvided)) {
                    grp.setHouseholdIdentifier(hIdentifierProvided);
                }
		service.saveHouseholdGroup(grp);
		//provide a default household identifier when not provided
		if(StringUtils.isEmpty(grp.getHouseholdIdentifier())){
			String strIdent = hd.getIdentifierPrefix() + grp.getId();
			int checkDigit = HouseholdCheckDigit.CheckDigit(strIdent);
			String hhPlusCheckDigit = strIdent + "-" + checkDigit;
			grp.setHouseholdIdentifier(hhPlusCheckDigit);
		}
		
		for (String arrmem : arrMembers) {
			HouseholdMembership membership = new HouseholdMembership();
			Person pn = Context.getPersonService().getPerson(Integer.parseInt(arrmem));
			membership.setHouseholdMembershipMember(pn);
			membership.setHouseholdMembershipGroups(grp);
			membership.setProviderId(provider);
			if(Integer.parseInt(arrmem) == Integer.parseInt(headOrIndex))
				membership.setHouseholdMembershipHeadship(true);
			if(StringUtils.isEmpty(arrInput[3])){
                                membership.setStartDate(new Date());
                        }else{
                                membership.setStartDate(dateFormatHelper(arrInput[3]));
                        }
			service.saveHouseholdMembership(membership);
                }
		return "true, Household saved successfully with the Identifier: " + grp.getHouseholdIdentifier() + "," + grp.getId();
	}
	
	public  String getHouseholdMems(String householdGrp){
		
		HouseholdService service = Context.getService(HouseholdService.class);
		Household hh = new Household();
		hh = service.getHouseholdGroupByIdentifier(householdGrp);
		List<HouseholdMembership> householdMembers = service.getAllHouseholdMembershipsByGroup(hh);
		
		/*return householdMembers;*/
		String householdMembersList="";
		for(int i=0; i<householdMembers.size(); i++){
			
			if(StringUtils.isEmpty(householdMembersList)){
				householdMembersList="";
                        }else{
				householdMembersList +="|";
                        }
				
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
                if(householdMembers.size()>0){
                    householdMembersList += "*true*"+householdMembers.get(0).getHouseholdMembershipGroups().getId();
                }else{
                    householdMembersList += "*false";
                }
		return householdMembersList;
	}
	
	public String voidMembers(String voidId,String voidReason) throws ParseException{
		
		HouseholdService service = Context.getService(HouseholdService.class);
		
		String [] hseMembers=voidId.split(",");
		Arrays.sort(hseMembers);
		for(int m=0;m<hseMembers.length;m++){
                    //introduction of variable to handle an individual
                    String mem=hseMembers[m];
                    try {
                            HouseholdMembership membership = service.getHouseholdMembership(Integer.parseInt(mem));
                            membership.setHouseholdMembershipMember(Context.getPatientService().getPatientByUuid(membership.getHouseholdMembershipMember().getUuid()));
                            if(membership.isHouseholdMembershipHeadship()){
                                continue;
                            }else{
                                membership.setVoided(true);
                                membership.setVoidReason(voidReason);
                                membership.setVoidedBy(Context.getUserContext().getAuthenticatedUser());
                                membership.setEndDate(dateFormatHelper(null));
                                service.saveHouseholdMembership(membership);
                             }
                    }catch (Exception e) {
                            return "false, There was a problem while trying to remove member(s).";
                    }	
		}
                return "true, Member(s) were removed successfully";
		
		
		
	}
        
        public String retireMembers(String retireId, String dateRetired, String retireReason) throws ParseException{
		
		HouseholdService service = Context.getService(HouseholdService.class);
		
		String [] hseMembers=retireId.split(",");
		Arrays.sort(hseMembers);
		for(int m=0;m<hseMembers.length;m++){
                    //introduction of variable to handle an individual
                    String mem=hseMembers[m];
                    try {
                            HouseholdMembership membership = service.getHouseholdMembership(Integer.parseInt(mem));
                            membership.setHouseholdMembershipMember(Context.getPatientService().getPatientByUuid(membership.getHouseholdMembershipMember().getUuid()));
                            if(membership.isHouseholdMembershipHeadship()){
                                continue;
                            }else{
                                if(StringUtils.isEmpty(dateRetired)){
                                    membership.setEndDate(dateFormatHelper(""));
                                }else{
                                    membership.setEndDate(dateFormatHelper(dateRetired));
                                }
                                membership.setRetireReason(retireReason);
                                
                                service.saveHouseholdMembership(membership);
                            }
                    }catch (Exception e) {
                        //log.error("" + e.getMessage());    
                        return "false, There was a problem while trying to retire the member(s).";
                    }	
		}
                return "true, Member(s) were retired successfully";
		
		
		
	}
        
	public String saveMembersToHseHold(String []arr) throws ParseException{
		
		String providerID = arr[2];
		HouseholdService service = Context.getService(HouseholdService.class);
			Household group=service.getHouseholdGroup(Integer.parseInt(arr[1]));
			
			String []strMem = arr[0].split(",");
			
			Arrays.sort(strMem);
			int j = 1;
			
			for (int i = 1; i < strMem.length; i++)
			{
				if (! strMem[i].equals(strMem[i -1]))
					strMem[j++] = strMem[i];
			}
			
			String[] unique = new String[j];
			System.arraycopy(strMem, 0, unique, 0, j);
			boolean gotSaved = false;
			for(int i=0; i<unique.length; i++ ){
				
				String strMember = unique[i];
				
				HouseholdMembership membership = new HouseholdMembership();
				Person pn = Context.getPersonService().getPerson(Integer.parseInt(strMember));
				List<HouseholdMembership> member = service.getHouseholdMembershipByGrpByPsn(pn, group);
				boolean proceed = true;
                                if(!member.isEmpty()){ 
                                    for (HouseholdMembership householdMembership : member) {
                                        String here = householdMembership.getEndDate().toString().trim();
                                        //log.error("\nGoing in!!" + here);
                                        if(!StringUtils.isEmpty(here)){
                                            //log.error("\nGot it!!");
                                            proceed =true;
                                            break;
                                        }else{
                                            proceed = false;
                                        }
                                    }
                                }
                                if(proceed){ 
					
					membership.setHouseholdMembershipMember(pn);
					membership.setHouseholdMembershipGroups(group);
			
					membership.setHouseholdMembershipHeadship(false);
					membership.setProviderId(providerID);
					
					if(StringUtils.isEmpty(arr[3])){
						membership.setStartDate(dateFormatHelper(new Date() + ""));
                                        }else{
						membership.setStartDate(dateFormatHelper(arr[3]));
                                        }
					// then save the members to this household grpId
					service.saveHouseholdMembership(membership);
					gotSaved = true;
				}else{
                                    continue;
				}
			}
                        if(gotSaved == false){
                            return "false, There was no new member(s) added.";
                        }
		return "true, New member(s) added successfully.";
	}
	
	public boolean addEditHouseholdDepartment(String [] passedArr){
		HouseholdService service = Context.getService(HouseholdService.class);
                //log.error(">>>>Selection: " + passedArr[1] + "," + passedArr[2] + "," + passedArr[3] + "," + passedArr[4]);
		if(passedArr[0].equals("1")){
			HouseholdDefinitionParent hd = new HouseholdDefinitionParent(passedArr[2], passedArr[3], passedArr[4]);
			hd.setId(0);
                        return service.saveHouseholdDefinitionParent(hd);
		}else if(passedArr[0].equals("2")){
			HouseholdDefinitionParent hd = service.getHouseholdDefinitionParent(Integer.parseInt(passedArr[1]));
			hd.setParentCode(passedArr[2]);
			hd.setParentFullname(passedArr[3]);
			hd.setParentDescription(passedArr[4]);
			return service.saveHouseholdDefinitionParent(hd);
		}else if(passedArr[0].equals("3")){
			HouseholdDefinitionParent hd = service.getHouseholdDefinitionParent(Integer.parseInt(passedArr[1]));
			return service.purgeHouseholdDefinitionParent(hd, null);
		}
		return false;
	}
	
	public boolean addEditHouseholdDefinition(String [] passedArr){
		HouseholdService service = Context.getService(HouseholdService.class);
		if(passedArr[0].equals("1")){
			HouseholdDefinition hd = new HouseholdDefinition(passedArr[2], passedArr[3], passedArr[6]);
			if(!StringUtils.isEmpty(passedArr[4])){
				hd.setParent(service.getHouseholdDefinitionParent(Integer.parseInt(passedArr[4])));
                        }
			if(!StringUtils.isEmpty(passedArr[5])){
				hd.setIdentifierPrefix(passedArr[5]);
                        }
			service.saveHouseholdDefinition(hd);
		}else if(passedArr[0].equals("2")){
			HouseholdDefinition hd = service.getHouseholdDefinition(Integer.parseInt(passedArr[1]));
			hd.setHouseholdDefinitionsCode(passedArr[2]);
			hd.setHouseholdDefinitionsCodeinfull(passedArr[3]);
			hd.setHouseholdDefinitionsDescription(passedArr[6]);
			if(!StringUtils.isEmpty(passedArr[4])){
				hd.setParent(service.getHouseholdDefinitionParent(Integer.parseInt(passedArr[4])));
                        }
			if(!StringUtils.isEmpty(passedArr[5])){
				hd.setIdentifierPrefix(passedArr[5]);
                        }
			service.saveHouseholdDefinition(hd);
		}else if(passedArr[0].equals("3")){
			HouseholdDefinition hd = service.getHouseholdDefinition(Integer.parseInt(passedArr[1]));
			return service.purgeHouseholdDefinition(hd);
		}
		return false;
	}
	
	public boolean addEditHouseholdEncounterType(String [] passedArr){
		HouseholdService service = Context.getService(HouseholdService.class);
		if(passedArr[0].equals("1")){
			HouseholdEncounterType he = new HouseholdEncounterType(passedArr[2], passedArr[3]);
			service.saveHouseholdEncounterType(he);
                        return true;
		}else if(passedArr[0].equals("2")){
			HouseholdEncounterType he =  service.getHouseholdEncounterType(Integer.parseInt(passedArr[1]));
			he.setName(passedArr[2]);
			he.setDescription(passedArr[3]);
			service.saveHouseholdEncounterType(he);
                        return true;
		}else if(passedArr[0].equals("3")){
			HouseholdEncounterType he = service.getHouseholdEncounterType(Integer.parseInt(passedArr[1]));
			return service.retireHouseholdEncounterType(he, passedArr[4]) != null;
		}
		return false;
	}
	
	public String closeEntireHousehold(String householdId,String voidReason,String providerid,String closedate )throws ParseException{
		
		HouseholdService service = Context.getService(HouseholdService.class);
		Household householdToClose = new Household();
		householdToClose = service.getHouseholdGroupByIdentifier(householdId);
		List<HouseholdMembership> householdMembersToClose = service.getAllHouseholdMembershipsByGroup(householdToClose);
		
		//loop through the entire list and void each member
		
		for(HouseholdMembership householdMembership:householdMembersToClose ){
			
			//creation of a string variable that will hold a member value for voiding
			
			Integer memId=householdMembership.getId();
			
			// now we start voiding starting with memId only and only if it is not empty
			HouseholdMembership membership = service.getHouseholdMembership(memId);
			membership.setHouseholdMembershipMember(Context.getPatientService().getPatientByUuid(membership.getHouseholdMembershipMember().getUuid()));
			membership.setVoided(true);
			membership.setVoidReason(voidReason);
			membership.setProviderId(providerid);
			membership.setEndDate(dateFormatHelper(closedate));
			householdToClose.setEndDate(dateFormatHelper(closedate));
			householdToClose.setProvider(providerid);
			
			service.saveHouseholdMembership(membership);
			service.saveHouseholdGroup(householdToClose);
			
		}
				
		
		
		return "Household closed";
		
	}
	
        public String retireHousehold(String householdID, String dateRetired, String retireReason) throws ParseException{
		
		HouseholdService service = Context.getService(HouseholdService.class);
		Household household = service.getHouseholdGroup(Integer.parseInt(householdID));
                household.setEndDate(dateFormatHelper(dateRetired));
                household.setRetireReason(retireReason);
                try {
                    List<HouseholdMembership> mem = service.getAllHouseholdMembershipsByGroup(household);
                    for (HouseholdMembership householdMembership : mem) {
                        householdMembership.setEndDate(dateFormatHelper(dateRetired));
                        householdMembership.setRetireReason(retireReason);

                        service.saveHouseholdMembership(householdMembership);
                    }
                    service.saveHouseholdGroup(household);
                } catch (Exception e) {
                    return "false, There was a problem while trying to retire the Household.";
                }
                return "true, Member(s) were retired successfully";
	}

        public String changeIndex(String [] passedArr) throws ParseException{
            HouseholdService service = Context.getService(HouseholdService.class);
            HouseholdMembership hmNew = service.getHouseholdMembership(Integer.parseInt(passedArr[0]));
            HouseholdMembership hmOld = service.getHouseholdMembership(Integer.parseInt(passedArr[1]));
            String changeReason = "Index Change : " +passedArr[2];
            try {
                //retire New index as member giving reason
                hmNew.setEndDate(dateFormatHelper(null));
                hmNew.setRetireReason(changeReason);
                service.saveHouseholdMembership(hmNew);
                //Add new member marking as index
                HouseholdMembership toAddIndex = new HouseholdMembership(); 
                toAddIndex.setStartDate(dateFormatHelper(null));
                toAddIndex.setResumeReason(changeReason);
                toAddIndex.setHouseholdMembershipGroups(hmNew.getHouseholdMembershipGroups());
                toAddIndex.setHouseholdMembershipMember(hmNew.getHouseholdMembershipMember());
                toAddIndex.setProviderId(passedArr[3]);
                toAddIndex.setHouseholdMembershipHeadship(true);
                service.saveHouseholdMembership(toAddIndex);
                //Retire giving reason  hmOld
                hmOld.setEndDate(dateFormatHelper(null));
                hmOld.setRetireReason(changeReason);
                service.saveHouseholdMembership(hmOld);
                //add old index member as member
                HouseholdMembership toAddOld = new HouseholdMembership(); 
                toAddOld.setStartDate(dateFormatHelper(null));
                toAddOld.setResumeReason("Previously the Index/Head of the household");
                toAddOld.setHouseholdMembershipGroups(hmOld.getHouseholdMembershipGroups());
                toAddOld.setHouseholdMembershipMember(hmOld.getHouseholdMembershipMember());
                toAddOld.setHouseholdMembershipHeadship(false);
                toAddOld.setProviderId(passedArr[3]);
                service.saveHouseholdMembership(toAddOld);
                
                return "true, Successfully changed index";
            } catch (Exception e) {
                return "false, Problem encountered while changing the index.";
            }
            
        }
        
        public String addQuasiMembers(String [] passedArr) throws ParseException{
            HouseholdService service = Context.getService(HouseholdService.class);
            String [] hseMembers=passedArr[0].split(",");
		Arrays.sort(hseMembers);
		for(int m=0;m<hseMembers.length;m++){
                    //introduction of variable to handle an individual
                    String mem=hseMembers[m];
                    try {
                            HouseholdMembership membership = service.getHouseholdMembership(Integer.parseInt(mem));
                            membership.setHouseholdMembershipMember(Context.getPatientService().getPatientByUuid(membership.getHouseholdMembershipMember().getUuid()));
                            membership.setRetireReason("Quasi take effect: " + passedArr[1]);
                            membership.setEndDate(dateFormatHelper(null));
                            membership.setQuasi(true);
                            service.saveHouseholdMembership(membership);
                            
                            /*HouseholdMembership hm = new HouseholdMembership();
                            hm.setStartDate(dateFormatHelper(null));
                            hm.setResumeReason("Previously the Active(non-Quasi) member of the household");
                            hm.setHouseholdMembershipGroups(membership.getHouseholdMembershipGroups());
                            hm.setHouseholdMembershipMember(membership.getHouseholdMembershipMember());
                            hm.setHouseholdMembershipHeadship(false);
                            hm.setProviderId(passedArr[2]);
                            service.saveHouseholdMembership(hm);*/
                            
                    }catch (Exception e) {
                            return "false, There was a problem while trying to quasi member(s).";
                    }	
		}
                return "true, Member(s) were made quasi successfully";
            
        }
        
	static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	private static Date dateFormatHelper(String strvalue) throws ParseException {
		if (strvalue == null || strvalue.length() == 0){
			return new Date();
                }else{
			return dateFormat.parse(strvalue);
                }
	}

}