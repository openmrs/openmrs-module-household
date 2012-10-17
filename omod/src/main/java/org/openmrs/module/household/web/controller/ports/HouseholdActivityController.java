/**
 * 
 */
package org.openmrs.module.household.web.controller.ports;
 
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.Household;
import org.openmrs.module.household.model.HouseholdMembership;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
/**
 * @author jmwogi
 *
 */
@Controller
public class HouseholdActivityController {
 
        private static final Log log = LogFactory.getLog(HouseholdActivityController.class);
        
        @RequestMapping("module/household/ports/householdActivity.form")
   public void preparePage(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                           @RequestParam(required=false, value="householdID") String hid,
                           @RequestParam(required=false, value="program") String pid) {
            HouseholdService service = Context.getService(HouseholdService.class);
            Household hhd = service.getHouseholdGroup(Integer.parseInt(hid));
            List<HouseholdMembership> hhmList = service.getAllHouseholdMembershipsByGroup(hhd);
            List<HouseholdMembership> hhmQuasi = service.getHouseholdQuasiMembersByHousehold(hhd);
            List<HouseholdMembership> hhmRetired = service.getHouseholdRetiredMembersByHousehold(hhd);
            List<HouseholdMembership> hhmVoided = service.getHouseholdMembersVoidedByHousehold(hhd);
            
            List<HouseholdMembership> hhmQuasiFinal = new ArrayList<HouseholdMembership>();
            List<HouseholdMembership> hhmRetiredFinal = new ArrayList<HouseholdMembership>();
            List<HouseholdMembership> hhmVoidedFinal = new ArrayList<HouseholdMembership>();
            
            Set<Integer> num = null; 
            //Quasi
                for (HouseholdMembership householdMembershipQ : hhmQuasi) {
                    boolean gotQuasi = false;
                    for (HouseholdMembership householdMembership : hhmList) {
                        if(householdMembership.getHouseholdMembershipMember().getId() == householdMembershipQ.getHouseholdMembershipMember().getId()){
                            if(!num.contains(householdMembership.getHouseholdMembershipMember().getId())){
                                gotQuasi = true;
                                num.add(householdMembership.getHouseholdMembershipMember().getId());
                                break;
                            }
                        }
                    }
                    if(!gotQuasi){
                        hhmQuasiFinal.add(householdMembershipQ);
                    }
                }
                num = null;
                //Retired
                boolean gotRetired = false;
                for (HouseholdMembership householdMembershipR : hhmRetired) {
                    for (HouseholdMembership householdMembership : hhmList) {
                        if(householdMembership.getHouseholdMembershipMember().getId() == householdMembershipR.getHouseholdMembershipMember().getId()){
                            if(!num.contains(householdMembership.getHouseholdMembershipMember().getId())){
                                gotRetired = true;
                                num.add(householdMembership.getHouseholdMembershipMember().getId());
                                break;
                            }
                        }
                    }
                    if(!gotRetired){
                        hhmRetiredFinal.add(householdMembershipR);
                    }
                }
                num=null;
                //Voided
                for (HouseholdMembership householdMembershipV : hhmVoided) {
                    boolean gotVoided = false;
                    for (HouseholdMembership householdMembership : hhmList) {
                        if(householdMembership.getHouseholdMembershipMember().getId() == householdMembershipV.getHouseholdMembershipMember().getId()){
                            if(!num.contains(householdMembership.getHouseholdMembershipMember().getId())){
                                gotVoided = true;
                                num.add(householdMembership.getHouseholdMembershipMember().getId());
                                break;
                            }
                        }
                    }
                    if(!gotVoided){
                        hhmVoidedFinal.add(householdMembershipV);
                    }
                }
                
            
            boolean isRetired = StringUtils.hasText(hhd.getRetireReason());
            
            if(Integer.parseInt(pid) == hhd.getHouseholdDef().getId() ){
                map.addAttribute("belong",true);
            }else{
                map.addAttribute("belong",false);
            }
            map.addAttribute("household", hhd);
            map.addAttribute("memberList",hhmList);
            map.addAttribute("isRetired", isRetired);
            map.addAttribute("memberQuasi",hhmQuasiFinal);
            map.addAttribute("memberRetired",hhmRetiredFinal);
            map.addAttribute("memberVoided",hhmVoidedFinal);
   }
}