package org.openmrs.module.household.web.controller;

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
public class HouseholdIndexPersonController {
	private static final Log log = LogFactory.getLog(HouseholdIndexPersonController.class);
	//Integer hshgrp=0;
	//Household grp=null;
	//HouseholdMembership grpId=null;
	
	@RequestMapping(method=RequestMethod.GET, value="module/household/householdIndexPerson")
	public void preparePage(ModelMap map) {
	} 
	
	@RequestMapping(method=RequestMethod.POST, value="module/household/householdIndexPerson")
	public void processForm(ModelMap map,HttpServletRequest request,
			@RequestParam(required=false, value="indexVal") String indexVal,
			@RequestParam(required=false, value="householdGroup") String householdGroup,
			@RequestParam(required=false, value="householdGroupCopy") String householdGroupCopy,
			@RequestParam(required=false, value="memberId") String memberId,
			@RequestParam(required=false, value="closeReason") String closeReason){
		
		HouseholdService service = Context.getService(HouseholdService.class);
		Household grp=null;
			
		if(request.getParameter("findMembers") !=null && StringUtils.hasText(householdGroup)){
			try{
				
				//voided=service.getAllHouseholdMemberships();
				grp=service.getHouseholdGroupByIdentifier(householdGroup);
				//householdMembershipMember
				HouseholdMembership hm = service.getHouseholdMembership(grp.getId());
				
				List<HouseholdMembership> indexPerson=service.getHouseholdIndexByGroup(grp);
				List<HouseholdMembership> householdsMembers = service.getAllNonVoidedHouseholdMembershipsByGroupNotIndex(grp);
				//return the list that will be used on the viewer
				Integer len=householdsMembers.size();
				map.addAttribute("length",len);
				map.addAttribute("hhmembers",householdsMembers);
				map.addAttribute("HHgrpId",hm);
				map.addAttribute("index",indexPerson);
				
			}
			catch(NullPointerException nullPointerException){
				nullPointerException.toString();
			}
			
		}
		 if(request.getParameter("changeIndex") !=null && StringUtils.hasText(indexVal)){
			
			 voidIndex(memberId);
			//set the new index as well
				
			newIndex(indexVal);
			List<HouseholdMembership> indexPerson=service.getHouseholdIndexByGroup(grp);
			List<HouseholdMembership> householdsMembers = service.getAllNonVoidedHouseholdMembershipsByGroupNotIndex(grp);
			map.addAttribute("hhmembers",householdsMembers);
			map.addAttribute("index",indexPerson);
		}
		 if(request.getParameter("closeHousehold") !=null){
			 closeHousehold(memberId,closeReason);
		 }
		
	}
	@Transactional
	private void voidIndex(String memberId){
		
		HouseholdService service = Context.getService(HouseholdService.class);
		//get the membership id
		try{
		Integer memId=Integer.parseInt(memberId);
		//select where membership id is memId
		HouseholdMembership voidIndex=service.getHouseholdMembership(memId);
		//set the index to false
		 voidIndex.setHouseholdMembershipMember(Context.getPatientService().getPatientByUuid(voidIndex.getHouseholdMembershipMember().getUuid()));
		voidIndex.setHouseholdMembershipHeadship(false);
		service.saveHouseholdMembership(voidIndex);
		
		
		log.info("Change index effected   "+memId);
		}
		catch(NumberFormatException numberFormatException){
			numberFormatException.toString();
		}
	}
	
	@Transactional
	private void newIndex(String memberId){
		HouseholdService service = Context.getService(HouseholdService.class);
		try{
		
		
		//get the membership id
		Integer memId=Integer.parseInt(memberId);
		//select where membership id is memId
		HouseholdMembership getNewIndex=service.getHouseholdMembership(memId);
		//set the index to true
		getNewIndex.setHouseholdMembershipMember(Context.getPatientService().getPatientByUuid(getNewIndex.getHouseholdMembershipMember().getUuid()));
		getNewIndex.setHouseholdMembershipHeadship(true);
		service.saveHouseholdMembership(getNewIndex);
		
		}
		catch(NumberFormatException numberFormatException){
			numberFormatException.toString();
			
		}
		log.info("Ingosi  "+memberId);
	}
	@Transactional
	private void closeHousehold(String IndexId,String closeReason){
		HouseholdService service = Context.getService(HouseholdService.class);
		Integer indexId=Integer.parseInt(IndexId);
		HouseholdMembership closeHouse=service.getHouseholdMembership(indexId);
		// set this household closed
		closeHouse.setVoided(true);
		closeHouse.setVoidReason(closeReason);
		//save the closed information
		service.saveHouseholdMembership(closeHouse);
		log.info("close the household");
		
	}
}
