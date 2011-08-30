/**
 * 
 */
package org.openmrs.module.household.web.controller;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

///to format date
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Patient;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
//import org.openmrs.module.household.model.
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ampath Developers
 *
 */
@Controller
public class HouseholdRegistrationController {

	private static final Log log = LogFactory.getLog(HouseholdRegistrationController.class);
	private Household hgrp = null;
	private int listCounts=0;
	
	@RequestMapping(method=RequestMethod.GET, value="module/household/householdRegistration")
	public void preparePage(ModelMap map,@RequestParam(required=false, value="grpid") String grpid) {
		HouseholdService service = Context.getService(HouseholdService.class);
		List<HouseholdDefinition> householdsTypes = service.getAllHouseholdDefinitions();
		map.addAttribute("householdsTypes", householdsTypes);
		
		listCounts=householdsTypes.size();
		map.addAttribute("NumberOfHD", listCounts);
		
		if(StringUtils.hasText(grpid)){
			Household grp=service.getHouseholdGroup(Integer.parseInt(grpid));
			
			List<HouseholdMembership> householdsMem = service.getAllHouseholdMembershipsByGroup(grp);
			map.addAttribute("householdSaved", householdsMem);
			map.addAttribute("householdGrpid", grpid);
		
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="module/household/householdRegistration")
	public void processForm(ModelMap map, @RequestParam(required=false, value="hiddenbox") String householdMem,
			@RequestParam(required=false, value="defin") String householdDef,
			@RequestParam(required=false, value="hiddenIndex") String householdIndex,
			 @RequestParam(required=false, value="startDate") String  startDate,
            @RequestParam(required=false, value="grpid") String grpid) throws ParseException{
		
		log.info("Processing post request ..." + householdMem);
		
			HouseholdService service = Context.getService(HouseholdService.class);
			List<HouseholdDefinition> householdsTypes = service.getAllHouseholdDefinitions();
			map.addAttribute("householdsTypes", householdsTypes);
			if(StringUtils.hasText(householdMem)){
				map.addAttribute("HouseholdID", savedRecord(householdDef,householdMem, householdIndex,grpid,startDate));
				List<HouseholdMembership> householdsMem = service.getAllHouseholdMembershipsByGroup(hgrp);
				map.addAttribute("householdSaved", householdsMem);
				map.addAttribute("ErrorHH", false);
			}else
				map.addAttribute("ErrorHH", true);
		
	}
	
	@Transactional
	private String savedRecord(String householdDef, String householdMem, String householdIndex,String grpid,String startDate ) throws ParseException{
		
		HouseholdService service = Context.getService(HouseholdService.class);
		///adding new members to the given grpid
		//String startDate = request.getParameter("startDate");
		if(StringUtils.hasText(grpid)){			
			
			Household grp=service.getHouseholdGroup(Integer.parseInt(grpid));
			String []strMem = householdMem.split(",");
			
			Arrays.sort(strMem);
			
			int k = 1;
			
			for (int i = 1; i < strMem.length; i++)
			{
				if (! strMem[i].equals(strMem[i -1]))
					strMem[k++] = strMem[i];
			}
			
			String[] unique = new String[k];
			System.arraycopy(strMem, 0, unique, 0, k);
			
		
			for(int i=0; i<unique.length; i++ ){
				String strMember = unique[i];
				if (StringUtils.hasText(strMember)){
					HouseholdMembership membership = new HouseholdMembership();
					Person pn = Context.getPersonService().getPerson(Integer.parseInt(strMember));
					
					//used to check if a member exists with the same person id from a given group
					
					List<HouseholdMembership> member = service.getHouseholdMembershipByGrpByPsn(pn, grp);
					//if the list is empty you are allowed to add new members to this group
					if(member.isEmpty()){ 
					
					membership.setHouseholdMembershipMember(pn);
					membership.setHouseholdMembershipGroups(grp);
			
						membership.setHouseholdMembershipHeadship(false);
					
					membership.setStartDate(dateFormatHelper(startDate));
					service.saveHouseholdMembership(membership);
				}
					//else write to a log file and skip these additions.
					else{
						log.info("\n This PersonId " +pn.getPersonId()+ " \n Already Exists in this group");
					}
				}
			}
			int groupId=Integer.parseInt(grpid);
			
			
			
			return "" + groupId;
			
			
		}
		else{
		//get household def
		int intDef = Integer.parseInt(householdDef);
		Integer intIndex = Integer.parseInt(householdIndex);
		HouseholdDefinition hd = service.getHouseholdDefinition(intDef);
		//save household group
		hgrp = new Household();
		hgrp.setHouseholdDef(hd);
		service.saveHouseholdGroup(hgrp);
		
		String []strMem = householdMem.split(",");
		
		Arrays.sort(strMem);
		
		int k = 1;
		
		for (int i = 1; i < strMem.length; i++)
		{
			if (! strMem[i].equals(strMem[i -1]))
				strMem[k++] = strMem[i];
		}
		
		String[] unique = new String[k];
		System.arraycopy(strMem, 0, unique, 0, k);
		
		//strMen == Unique
		
		for(int i=0; i<unique.length; i++ ){
			String strMember = unique[i];
			if (StringUtils.hasText(strMember)){
				HouseholdMembership membership = new HouseholdMembership();
				Person pn = Context.getPersonService().getPerson(Integer.parseInt(strMember));
				membership.setHouseholdMembershipMember(pn);
				membership.setHouseholdMembershipGroups(hgrp);
				
				String strVal1 = (intIndex+"").trim();
				String strVal2 = (pn.getPersonId() + "").trim();
				if (strVal1.equals(strVal2)){
					membership.setHouseholdMembershipHeadship(true);
				}else
					membership.setHouseholdMembershipHeadship(false);
				
				membership.setStartDate(dateFormatHelper(startDate));
				service.saveHouseholdMembership(membership);
			}
		}
		
		
		//save individually
		return hd.getHouseholdDefinitionsCode() + ":" + hd.getId() + "-" + hgrp.getId();
	}
	
}
	static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	private static Date dateFormatHelper(String strvalue) throws ParseException {
		if (strvalue == null || strvalue.length() == 0)
			return new Date();
		else
			return dateFormat.parse(strvalue);
	}
}