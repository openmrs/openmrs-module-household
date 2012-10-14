package org.openmrs.module.household.web.controller;
 
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdDefinitionParent;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
 
/**
 * @author jmwogi
 *
 */
@Controller
public class HouseholdDashboardController {
 
        private static final Log log = LogFactory.getLog(HouseholdDashboardController.class);
        
        @RequestMapping(method = RequestMethod.GET, value = "module/household/householdDashboard")
   public void preparePage(ModelMap map) {
       HouseholdService service = Context.getService(HouseholdService.class);
                List<HouseholdDefinitionParent> definitionParents = service.getHouseholdDefinitionParent(false);
                map.addAttribute("definitionParents", definitionParents);
                map.addAttribute("par", 1);
   }
        
        @RequestMapping(method = RequestMethod.POST, value = "module/household/householdDashboard")
   public void postPage(ModelMap map, HttpServletRequest request,HttpSession httpSession,HttpServletResponse response,
                        @RequestParam(required = false, value = "parent") String parent,
                        @RequestParam(required = false, value = "child") String child) throws IOException {
                HouseholdService service = Context.getService(HouseholdService.class);
                /*
                if(!StringUtils.isEmpty(parent)){
                        int householdDefinition = Integer.parseInt(parent);
                        HouseholdDefinition hd = service.getHouseholdDefinition(householdDefinition);
                        List<HouseholdDefinition> householdsTypes = service.getHouseholdDefinitionChildren(hd);
                        
                        map.addAttribute("pare", hd);
                        map.addAttribute("householdDefinition", householdsTypes);
                        map.addAttribute("hdef", hd);
                        map.addAttribute("par", 2);
                }*/
                
                
   }
        /*@RequestMapping(method = RequestMethod.POST, value = "module/household/householdDashboard")
    public ModelMap postPage(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                         @RequestParam(required = false, value = "parent") String parent,
                         @RequestParam(required = false, value = "child") String child) {
                HouseholdService service = Context.getService(HouseholdService.class);
                
                if(!StringUtils.isEmpty(parent)){
                        int householdDefinition = Integer.parseInt(parent);
                        HouseholdDefinition hd = service.getHouseholdDefinition(householdDefinition);
                        List<HouseholdDefinition> householdsTypes = service.getHouseholdDefinitionChildren(hd);
                        
                        map.addAttribute("pare", hd);
                        map.addAttribute("householdDefinition", householdsTypes);
                        map.addAttribute("hdef", hd);
                        map.addAttribute("par", 2);
                }
                
                if(!StringUtils.isEmpty(child)){
                        int householdDefinition = Integer.parseInt(child);
                        HouseholdDefinition hd = service.getHouseholdDefinition(householdDefinition);
                        
                        map.addAttribute("hdef", hd);
                        map.addAttribute("par", 3);
                }
                
                return map;
    }*/
}