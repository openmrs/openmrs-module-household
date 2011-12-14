package org.openmrs.module.household.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class HouseholdResumeController {
        private static final Log log = LogFactory.getLog(HouseholdResumeController.class);
        
        @RequestMapping(method=RequestMethod.GET, value="module/household/householdResume")
        public void preparePage(ModelMap map) {
                
        }
        
        @RequestMapping(method=RequestMethod.POST, value="module/household/householdResume")
        public void processForm(ModelMap map, HttpServletRequest request,
                    @RequestParam(required=false, value="householdSearchCode") Integer householdSearchCode,
                        @RequestParam(required=false, value="householdGroup")  String householdGroup,
                        @RequestParam(required=false, value="resumeDate") String resumeDate,
                        @RequestParam(required=false, value="marktext") String marktext,
                        @RequestParam(required=false, value="resumeReason") String resumeReason) throws ParseException
                        {
                Household grp=null;
                HouseholdService service = Context.getService(HouseholdService.class);
                
                map.addAttribute("householdGrp",householdGroup);
                        
                log.info("Processing post request ..." + householdGroup);
                
                
                
                if(request.getParameter("findMembers") !=null && StringUtils.hasText(householdGroup)){
                        try{
                                        //voided=service.getAllHouseholdMemberships();
                                grp=service.getHouseholdGroupByIdentifier(householdGroup);
                                        //householdMembershipMember
                                HouseholdMembership dfn=service.getHouseholdMembership(grp.getId());
                                
                                List<HouseholdMembership> householdsMem = service.getAllVoidedHouseholdMembershipsByGroup(grp);
        
                                Integer householdsMemCount=householdsMem.size();
                                
                                //Integer x=;
                                log.info("\n..............." + householdsMem.size()+ "  "+grp.getId() );
                                
                                map.addAttribute("hhmembers",householdsMem);
                                map.addAttribute("hhmembersgrp", dfn);
                                map.addAttribute("hhmembersCount", householdsMemCount);
                                
                        }catch(NullPointerException ex){
                                log.error(ex.getMessage());
                        }
                }
                // end of search members here
                //start voiding selected members in households
                else if(request.getParameter("resumeMembers") !=null && StringUtils.hasText(marktext)){
                        resumeRecords(marktext,resumeReason,resumeDate);
                        //grp=service.getHouseholdGroup((householdGroup1));
                        List<HouseholdMembership> householdsMem = service.getAllVoidedHouseholdMembershipsByGroup(grp);
                        map.addAttribute("hhmembers",householdsMem);
                }
                
                else{
                        List<HouseholdMembership> householdsMem = service.getAllVoidedHouseholdMembershipsByGroup(grp);
                        map.addAttribute("hhmembers",householdsMem);
                        log.info("Nothing to save");
                }
        }
        @Transactional
        private String resumeRecords(String resumeList,String resumeReason,String resumeDate) throws ParseException{
                HouseholdService service = Context.getService(HouseholdService.class);
                String resumedListTrimed = resumeList.trim();
                //String resumeListRefined=resumedListTrimed.substring(1);
                
                String []strMembers = resumedListTrimed.split(",");
                Arrays.sort(strMembers);
                
                for(int i=0; i<strMembers.length; i++ ){
                        String strMember = strMembers[i];
                        
                        HouseholdMembership membership = service.getHouseholdMembership(Integer.parseInt(strMember));
                        membership.setHouseholdMembershipMember(Context.getPatientService().getPatientByUuid(membership.getHouseholdMembershipMember().getUuid()));
                        membership.setVoided(false);
                        membership.setResumeReason(resumeReason);
                        membership.setResumeDate(dateFormatHelper(resumeDate));
                        service.saveHouseholdMembership(membership);
                }
                        
                return "\n" + resumedListTrimed + "\nSaved Successfully";
                
        }
        static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
        private static Date dateFormatHelper(String strvalue) throws ParseException {
                if (strvalue == null || strvalue.length() == 0)
                        return new Date();
                else
                        return dateFormat.parse(strvalue);
        }
        
        
}