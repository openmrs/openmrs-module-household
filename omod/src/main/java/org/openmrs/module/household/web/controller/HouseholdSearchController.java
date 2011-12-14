package org.openmrs.module.household.web.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HouseholdSearchController {
	private static final Log log = LogFactory.getLog(HouseholdSearchController.class);
	Household grp=null;
	
	@RequestMapping(method=RequestMethod.GET, value="module/household/householdSearch")
	public void preparePage(ModelMap map,@RequestParam(required=false, value="grpids") Integer grpids) {
		try{
			HouseholdService service = Context.getService(HouseholdService.class);
			Household grp=service.getHouseholdGroup(grpids);
			List<HouseholdMembership> householdsMem = service.getAllHouseholdMembershipsByGroup(grp);
			map.addAttribute("hhmembers",householdsMem);
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="module/household/householdSearch")
	public void processForm(ModelMap map, HttpServletRequest request,
		@RequestParam(required=false, value="householdSearchCode") Integer householdSearchCode,
		@RequestParam(required=false, value="householdGroup") String householdGroup,
		//@RequestParam(required=false, value="householdgrpRef") String householdgrpRef,
		@RequestParam(required=false, value="marktext") String marktext,
		@RequestParam(required=false, value="voidReason") String voidReason) //throws NullPointerException 
		{
		HouseholdService service = Context.getService(HouseholdService.class);
		
		map.addAttribute("hhold", householdGroup);
		
		if(request.getParameter("findMembers") !=null && StringUtils.hasText(householdGroup)){
			 //householdGroup1=(Integer.parseInt( householdGroup));
			try{
					//voided=service.getAllHouseholdMemberships();
					grp=service.getHouseholdGroupByIdentifier(householdGroup);
					//householdMembershipMember
					HouseholdMembership hm = service.getHouseholdMembership(grp.getId());
					
					List<HouseholdMembership> householdsMem = service.getAllHouseholdMembershipsByGroup(grp);
		
					Integer householdsMemCount=householdsMem.size();
					
					//Integer x=;
					log.info("\n..............." + householdsMem.size()+ "  "+grp.getId() );
					
					map.addAttribute("hhmembers",householdsMem);
					map.addAttribute("hhmembersgrp", grp);
					map.addAttribute("hhmembersCount", householdsMemCount);
				}
			catch(NullPointerException ex){
				log.error(ex.getMessage());
			}
			 
		}
		// end of search members here
		//start voiding selected members in households
		else if(request.getParameter("voidMembers") !=null && StringUtils.hasText(marktext)){
			voidRecords(marktext,voidReason);
			//grp=service.getHouseholdGroup((householdGroup1));
			List<HouseholdMembership> householdsMem = service.getAllHouseholdMembershipsByGroup(grp);
			map.addAttribute("hhmembers",householdsMem);
		}
		else{
			List<HouseholdMembership> householdsMem = service.getAllHouseholdMembershipsByGroup(grp);
			map.addAttribute("hhmembers",householdsMem);
			log.info("Nothing to save");
		}
		
	}
	
	@Transactional
	private String voidRecords(String voidList,String voidReason){
		HouseholdService service = Context.getService(HouseholdService.class);
		
		String voidListTrimed = voidList.trim();
		//String voidListRefined=voidListTrimed.substring(1);
		
		
		
		String []strMembers = voidListTrimed.split(",");
		Arrays.sort(strMembers);
		
		for(int i=0; i<strMembers.length; i++ ){
			String strMember = strMembers[i];
			
			
			HouseholdMembership membership = service.getHouseholdMembership(Integer.parseInt(strMember));
			membership.setHouseholdMembershipMember(Context.getPatientService().getPatientByUuid(membership.getHouseholdMembershipMember().getUuid()));
			membership.setVoided(true);
			membership.setVoidReason(voidReason);
			membership.setEndDate(new Date());
			service.saveHouseholdMembership(membership);
		}
			
		return "\n" + voidListTrimed + "\nSaved Successfully";
		
	}
	/*@Transactional
	private String voidEntireHousehold(String group,String voidReason){
		Household haGrp=null;
		HouseholdService service = Context.getService(HouseholdService.class);
		Integer aGrp=Integer.parseInt(group);
		haGrp=service.getHouseholdGroup(aGrp);
		List<HouseholdMembership> householdsMem = service.getAllHouseholdMembershipsByGroup(haGrp);
		try{
		for(int i=0;i<=householdsMem.size();i++){
			
			HouseholdMembership membership = service.getHouseholdMembership((i));
			membership.setVoided(true);
			membership.setEndDate(new Date());
			service.saveHouseholdMembership(membership);
			
		}
		}
		catch(NullPointerException nullPointerException){
			nullPointerException.toString();
			
		}
		log.info("voiding caputured for group "+group+" Having members "+householdsMem.size());
		
		return "Household "+group+" voided";
		
	}*/
	
	
}
