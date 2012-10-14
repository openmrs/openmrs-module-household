/**
 * 
 */
package org.openmrs.module.household.web.controller.ports;
 
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
/**
 * @author jmwogi
 *
 */
@Controller
public class editHouseholdController {
 
        private static final Log log = LogFactory.getLog(editHouseholdController.class);
        
        @RequestMapping("module/household/ports/editHousehold.form")
   public void preparePage(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                           @RequestParam(required=false, value="householdID") String hid,
                           @RequestParam(required=false, value="opt") String opt) {
            HouseholdService service = Context.getService(HouseholdService.class);
            Household hhd = service.getHouseholdGroup(Integer.parseInt(hid));
            
            if(Integer.parseInt(opt) == 1){
                map.addAttribute("act","Add Member(s)");
            }else if(Integer.parseInt(opt) == 2){
                List<HouseholdMembership> hhmList = service.getAllHouseholdMembershipsByGroup(hhd);
                map.addAttribute("memberList",hhmList);
                map.addAttribute("act","Remove Member(s)");
            }else if(Integer.parseInt(opt) == 3){
                map.addAttribute("act","Retire Household");
            }else if(Integer.parseInt(opt) == 4){
                List<HouseholdMembership> hhmList = service.getAllHouseholdMembershipsByGroup(hhd);
                map.addAttribute("memberList",hhmList);
                map.addAttribute("act","Retire member(s)");
            }else if(Integer.parseInt(opt) == 5){
                List<HouseholdMembership> hhmHist = service.getHouseholdMembershipByHousehold(hhd);
                map.addAttribute("memberHist",hhmHist);
                map.addAttribute("act","History");
            }
            
            map.addAttribute("opts",opt);
            map.addAttribute("household", hhd);
   }
}