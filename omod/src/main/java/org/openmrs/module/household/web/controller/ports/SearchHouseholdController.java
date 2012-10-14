/**
 * 
 */
package org.openmrs.module.household.web.controller.ports;
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
 
 
/**
 * @author jmwogi
 *
 */
@Controller
public class SearchHouseholdController {
 
        //private static final Log log = LogFactory.getLog(SearchHouseholdController.class);
        
        @RequestMapping(method = RequestMethod.GET, value = "module/household/ports/searchHousehold.form")
   public void preparePage(ModelMap map, HttpServletRequest request,HttpSession httpSession) {
                
   }
        @RequestMapping(method = RequestMethod.POST, value = "module/household/ports/searchHousehold.form")
   public void postPage(ModelMap map, HttpServletRequest request,HttpSession httpSession) {
                
   }
}