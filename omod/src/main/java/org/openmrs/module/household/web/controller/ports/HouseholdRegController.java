/**
 * 
 */
package org.openmrs.module.household.web.controller.ports;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
 
/**
 * @author jmwogi
 *
 */
@Controller
public class HouseholdRegController {
 
        private static final Log log = LogFactory.getLog(HouseholdRegController.class);
        
        @RequestMapping("module/household/ports/householdReg")
   public void preparePage(ModelMap map, HttpServletRequest request,HttpSession httpSession,
                           @RequestParam(required=false, value="hdef") String defid) {
                map.addAttribute("hdef",defid );
   }
}