/**
 * 
 */
package org.openmrs.module.household.web.controller;

//import java.util.List;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Person;
import org.openmrs.api.AdministrationService;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdEncounter;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.model.HouseholdLocation;
import org.openmrs.module.household.model.HouseholdLocationEntry;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.model.HouseholdObs;
import org.openmrs.module.household.service.HouseholdService;
import org.openmrs.web.WebConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Ampath Developers
 *
 */
@Controller
public class HouseholdCHWInitialController {
	private static final Log log = LogFactory.getLog(HouseholdCHWInitialController.class);
    private static String obsGrpUuid = "";
	
	@RequestMapping(method=RequestMethod.GET, value="module/household/householdCHWInitial")
	public void preparePage(ModelMap map) {
		HouseholdService service = Context.getService(HouseholdService.class);
		
		// get the ordered address hierarchy levels and add them to the map
		List<HouseholdLocationLevel> levels = service.getOrderedHouseholdLocationLevels(false);
		
		if (CollectionUtils.isNotEmpty(levels)){
			map.addAttribute("hierarchyLevels", levels);
			
			log.info("\n ....SIZE...:" + levels.size());
			
			//Get HouseholdLocations top Hierarchy
			try {
				List<HouseholdLocationEntry> entries = Context.getService(HouseholdService.class).getHouseholdLocationEntriesByLevel(levels.get(0));
				String strLoc = "";
				for(int i=0; i < entries.size(); i++){
					strLoc += entries.get(i).getName().toString() + ",";
				}
				map.addAttribute("loci", entries);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="module/household/householdCHWInitial")
	public void processForm(ModelMap map, HttpServletRequest request,HttpSession httpSession) throws ParseException{
		HouseholdService service = Context. getService(HouseholdService.class);
		//try{
		//get control values from jsp
		String household = request.getParameter("household");
		String visitDate = request.getParameter("visitDate");
		String location = request.getParameter("location");
		String subLocation = request.getParameter("subLocation");
		String village = request.getParameter("village");
		String chwIdentity=request.getParameter("chwID");
		//String enctype=request.getParameter("enctype");
		
		AdministrationService as = Context.getAdministrationService();
		int x=Integer.parseInt(as.getGlobalProperty("household.enctype")) ;
		
		log.info(x);
		//Save Encounter -> EncounterType , Form, Location, Household, Provider
		
		HouseholdEncounter encounter = new HouseholdEncounter();
		HouseholdEncounterType encounterType = new HouseholdEncounterType();
		HouseholdLocation householdLocation = new HouseholdLocation();
		Household householdGroups = new Household();
		
		householdLocation.setCityLocation(location);
		householdLocation.setCitySubLocation(subLocation);
		householdLocation.setCityVillage(village);
		householdLocation.setName(village);
		
		service.saveHouseholdLocation(householdLocation);
		
		encounterType = service.getHouseholdEncounterType(x);
		householdGroups = service.getHouseholdGroupByIdentifier(household);
		
		encounter.setHouseholdEncounterType(encounterType);
		encounter.setHouseholdEncounterDatetime(dateHelper(visitDate) );
		encounter.setHouseholdLocation(householdLocation);
		//encounter.setHouseholdGroups(householdGroups);
		//convert the provider id into an integer from string
		Integer providerId=Integer.parseInt(chwIdentity);
		//use providerId in the person object
		Person provider = new Person(providerId);
		encounter.setProvider(provider);
		encounter.setHouseholdGroupId(householdGroups);
		encounter.setForm(null);
		
		service.saveHouseholdEncounter(encounter);
		
		//Save Obs
		HouseholdObsHandler(request, encounter, householdGroups,httpSession);
		
	}
	
	static DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
	private static Date dateHelper(String value) throws ParseException {
		if (value == null || value.length() == 0)
			return null;
		else
			return df.parse(value);
	}
	

	private void HouseholdObsHandler(HttpServletRequest request, HouseholdEncounter encounter, Household householdGroups,HttpSession httpSession){
		
		HouseholdService service = Context.getService(HouseholdService.class);
		
		String strNoOfSleepingSpaces = request.getParameter("noOfSleepingSpaces");
		String strNoOfBedNets = request.getParameter("noOfBedNets");
		int noOfSleepingSpaces = Integer.parseInt(strNoOfSleepingSpaces);//6865N
		int noOfBedNets = Integer.parseInt(strNoOfBedNets);//6866N
		String bednetsObserved = request.getParameter("bednetsObserved");//6869C
		String bednetsCondition = request.getParameter("bednetsCondition");//6870C
		String bednetsEducation = request.getParameter("bednetsEducation");//6871C
		String stableFoodAvailable = request.getParameter("stableFoodAvailable");//6872C
		String []waterSources = request.getParameterValues("waterSources");//6386C[]
		String treatWater = request.getParameter("treatWater");//6860C
		String []waterTreatMethod = request.getParameterValues("waterTreatMethod");//6864C[]
		String hasLatrine = request.getParameter("hasLatrine");//6874C
		String []latrineType = request.getParameterValues("latrineType");//6390C[]
		String sharedLatrine = request.getParameter("sharedLatrine");//6875C
		String handWashFacility = request.getParameter("handWashFacility");//6876C
		String waterEducation = request.getParameter("waterEducation");//6877C
		String returnVisitDate = request.getParameter("returnVisitDate");//5096C
		String []revisitItems = request.getParameterValues("revisitItems");//6889C[]
		
		String memInSchool = request.getParameter("memInSchool");//7233C
		String disabledMems = request.getParameter("disabledMems");//7131C
		String memsWithKnownHIVStatus = request.getParameter("memsWithKnownHIVStatus");//7232C
		String memLivingAway = request.getParameter("memLivingAway");//7236C
		String memWithChronic = request.getParameter("memWithChronic");//7235C
		String visitReason = request.getParameter("visitReason");//1839C
		
		/*String []arrayItems = {bednetsObserved, bednetsCondition,
				bednetsEducation, stableFoodAvailable, treatWater, hasLatrine, sharedLatrine,
				handWashFacility, waterEducation, returnVisitDate};*/
		int grpObs = 1;
		
		List<HouseholdObs> obs = new ArrayList<HouseholdObs>();


		//handle the string arrayItems
		if(!(StringUtils.isEmpty(bednetsObserved))){
			obs.add(putObsToList(6869, householdGroups, Integer.parseInt(bednetsObserved), false, 0));
		}
		if(!(StringUtils.isEmpty(bednetsCondition))){
			obs.add(putObsToList(6870, householdGroups, Integer.parseInt(bednetsCondition), false, 0));
		}
		if(!(StringUtils.isEmpty(bednetsEducation))){
			obs.add(putObsToList(6871, householdGroups, Integer.parseInt(bednetsEducation), false, 0));
		}
		if(!(StringUtils.isEmpty(stableFoodAvailable))){
			obs.add(putObsToList(6872, householdGroups, Integer.parseInt(stableFoodAvailable), false, 0));
		}
		if(!(StringUtils.isEmpty(treatWater))){
			obs.add(putObsToList(6860, householdGroups, Integer.parseInt(treatWater), false, 0));
		}
		if(!(StringUtils.isEmpty(hasLatrine))){
			obs.add(putObsToList(6874, householdGroups, Integer.parseInt(hasLatrine), false, 0));
		}
		if(!(StringUtils.isEmpty(sharedLatrine))){
			obs.add(putObsToList(6875, householdGroups, Integer.parseInt(sharedLatrine), false, 0));
		}
		if(!(StringUtils.isEmpty(handWashFacility))){
			obs.add(putObsToList(6876, householdGroups, Integer.parseInt(handWashFacility), false, 0));
		}
		if(!(StringUtils.isEmpty(waterEducation))){
			obs.add(putObsToList(6877, householdGroups, Integer.parseInt(waterEducation), false, 0));
		}
		if(!(StringUtils.isEmpty(returnVisitDate))){
			obs.add(putObsToListDate(5096, householdGroups, returnVisitDate, false, 2));
		}
		if(!(StringUtils.isEmpty(visitReason))){
			obs.add(putObsToList(1839, householdGroups, Integer.parseInt(visitReason), false, 0));
		}
		try{
			if(waterSources.length != 0){
	            obsGrpUuid = UUID.randomUUID() + "";
				for(int x=0;x<waterSources.length;x++)
					obs.add(putObsToList(6386, householdGroups, Integer.parseInt(waterSources[x]), true, grpObs));
				grpObs++;
			}
		}
		catch (Exception e) {}
		try{
			if(waterTreatMethod.length != 0){
				obsGrpUuid = UUID.randomUUID() + "";
	            for(int x=0;x<waterTreatMethod.length;x++)
					obs.add(putObsToList(6864, householdGroups, Integer.parseInt(waterTreatMethod[x]), true, grpObs));
				grpObs++;
			}
		}
		catch (Exception e) {}
		try{
			if(latrineType.length != 0){
	            obsGrpUuid = UUID.randomUUID() + "";
				for(int x=0;x<latrineType.length;x++)
					obs.add(putObsToList(6390, householdGroups, Integer.parseInt(latrineType[x]), true, grpObs));
				grpObs++;
			}
		}catch (Exception e) {}
		try{
			if(revisitItems.length != 0){
				obsGrpUuid = UUID.randomUUID() + "";
				for(int x=0;x<revisitItems.length;x++)
					obs.add(putObsToList(6390, householdGroups, Integer.parseInt(revisitItems[x]), true, grpObs));
	            grpObs++;
			}
		}catch (Exception e) {}
		if(!StringUtils.isEmpty(strNoOfSleepingSpaces)){
			obs.add(putObsToList(6865, householdGroups, noOfSleepingSpaces, false, 1));
		}
		if(!StringUtils.isEmpty(strNoOfBedNets)){
			obs.add(putObsToList(6866, householdGroups, noOfBedNets, false, 1));
		}
		if(!StringUtils.isEmpty(memInSchool)){
			obs.add(putObsToList(7233, householdGroups, Integer.parseInt(memInSchool), false, 1));
		}
		if(!StringUtils.isEmpty(disabledMems)){
			obs.add(putObsToList(7131, householdGroups, Integer.parseInt(disabledMems), false, 1));
		}
		if(!StringUtils.isEmpty(memsWithKnownHIVStatus)){
			obs.add(putObsToList(7232, householdGroups, Integer.parseInt(memsWithKnownHIVStatus), false, 1));
		}
		if(!StringUtils.isEmpty(memLivingAway)){
			obs.add(putObsToList(7236, householdGroups, Integer.parseInt(memLivingAway), false, 1));
		}
		if(!StringUtils.isEmpty(memWithChronic)){
			obs.add(putObsToList(7235, householdGroups, Integer.parseInt(memWithChronic), false, 1));
		}
		
		
		for (HouseholdObs ob : obs){
			ob.setHouseholdEncounter(encounter);
			service.saveHouseholdObs(ob, "msg");
			log.info("\n .......:>>" + ob.getConcept().getConceptId());
			httpSession.setAttribute(WebConstants.OPENMRS_MSG_ATTR, "Observations Saved Successfully");
		}
	}
	
	
	private HouseholdObs putObsToList(int quiz, Household householdGroups, int ans, boolean hasGrp, int obsGrp){
		HouseholdObs o = new HouseholdObs();
		o.setConcept(Context.getConceptService().getConcept(quiz));
		o.setHouseholdGroups(householdGroups);
		if((obsGrp==1) && !hasGrp)
			o.setValueNumeric(Double.parseDouble(ans + ""));
		else{
			o.setValueCoded(Context.getConceptService().getConcept(ans));
            if (hasGrp)
                o.setHouseholdObsGroupUuid(obsGrpUuid);
        }
		//o.setValueCoded(Context.getConceptService().getConcept(ans));
		o.setHouseholdObsDatetime(new Date());
		if (hasGrp){
			o.setValueGroupId(obsGrp);
		}
		return o;
	}

	private HouseholdObs putObsToListDate(int quiz, Household householdGroups, String ans, boolean hasGrp, int obsGrp){
		HouseholdObs o = new HouseholdObs();
		o.setConcept(Context.getConceptService().getConcept(quiz));
		o.setHouseholdGroups(householdGroups);
		if((obsGrp==1) && !hasGrp)
			o.setValueNumeric(Double.parseDouble(ans + ""));
		else if((obsGrp==2) && !hasGrp)
			try {
				o.setValueDatetime(dateHelper(ans + ""));
			} catch (ParseException e) {}
		else
			o.setValueCoded(Context.getConceptService().getConcept(ans));
		//o.setValueCoded(Context.getConceptService().getConcept(ans));
		o.setHouseholdObsDatetime(new Date());
		if (hasGrp){
			o.setValueGroupId(obsGrp);
		}
		return o;
	}
}
