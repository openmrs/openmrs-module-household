/**
 * 
 */
package org.openmrs.module.household.web.controller.ports;
 
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
 
/**
 * @author jmwogi
 *
 */
@Controller
public class HouseholdSettingsDefController {
 
        //private static final Log log = LogFactory.getLog(HouseholdSettingsDefController.class);
        
        @RequestMapping(method = RequestMethod.GET, value = "module/household/ports/householdSettingsDef")
   public void preparePage(ModelMap map, HttpServletRequest request,HttpSession httpSession) {
                HouseholdService service = Context.getService(HouseholdService.class);
                List<HouseholdDefinition> householdsTypes = service.getAllHouseholdDefinitions();
                map.addAttribute("householdsTypes", householdsTypes);
   }
        @RequestMapping(method = RequestMethod.POST, value = "module/household/ports/householdSettingsDef")
   public void postPage(ModelMap map, HttpServletRequest request,HttpSession httpSession) {
                HouseholdService service = Context.getService(HouseholdService.class);
                List<HouseholdDefinition> householdsTypes = service.getAllHouseholdDefinitions();
                map.addAttribute("householdsTypes", householdsTypes);
   }
}