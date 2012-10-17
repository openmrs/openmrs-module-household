package org.openmrs.module.household.web.controller;
 
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdDefinition;
import org.openmrs.module.household.model.HouseholdDefinitionParent;
import org.openmrs.module.household.model.HouseholdEncounterType;
import org.openmrs.module.household.model.editor.HouseholdDefinitionEditor;
import org.openmrs.module.household.model.editor.HouseholdDefinitionParentEditor;
import org.openmrs.module.household.model.editor.HouseholdEncounterTypeEditor;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
public class HouseholdSettingsPanel {
   private static final Log log = LogFactory.getLog(HouseholdSettingsPanel.class);
   
   /**
         * Set up automatic primitive-to-class mappings
         * 
         * @param wdb
         */
        @InitBinder
        public void initBinder(WebDataBinder wdb) {
                wdb.registerCustomEditor(HouseholdDefinition.class, new HouseholdDefinitionEditor());
		wdb.registerCustomEditor(HouseholdDefinitionParent.class, new HouseholdDefinitionParentEditor());
                wdb.registerCustomEditor(HouseholdEncounterType.class, new HouseholdEncounterTypeEditor());
        }
   
   @RequestMapping("module/household/householdSettingsPanel.form")
   public void preparePage(ModelMap map) {
       HouseholdService service = Context.getService(HouseholdService.class);
       List<HouseholdDefinitionParent> householdParent = service.getHouseholdDefinitionParent(false);
                map.addAttribute("householdDepartment", householdParent);
                
       List<HouseholdDefinition> householdsTypes = service.getAllHouseholdDefinitions();
                map.addAttribute("householdsTypes", householdsTypes);
                
                List<HouseholdEncounterType> householdET = service.getAllHouseholdEncounterTypes(false);
                map.addAttribute("householdEncTypes", householdET);
                String val = Context.getAdministrationService().getGlobalProperty("household.enableAmpathLinks");
                if(val.equalsIgnoreCase("true")){
                    map.addAttribute("gotAmpathLinks", true);
                }else{
                    map.addAttribute("gotAmpathLinks", false);
                }
   }
   
   /*@RequestMapping("module/household/householdAddProgram")
   public String programAddPage(ModelMap map, @RequestParam("programCode") String programCode,
                                        @RequestParam("codeInFull") String codeInFull,
                                        @RequestParam("programDescription") String programDescription,
                                        @RequestParam(required=false, value="parent") HouseholdDefinitionParent parent,
                                        @RequestParam(required=false, value="identifierPrefix") String identifierPrefix,
                                        WebRequest request) {
       
       if (!StringUtils.hasText(programCode)) {
                        request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Context.getMessageSourceService().getMessage(
                            "Program Code Required"), WebRequest.SCOPE_SESSION);
                } else if (!StringUtils.hasText(codeInFull)) {
                        request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Context.getMessageSourceService().getMessage(
                    "Program Code in full Required"), WebRequest.SCOPE_SESSION);
                } else if (Context.getService(HouseholdService.class).getHouseholdDefinition(programCode) != null) {
                        request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Context.getMessageSourceService().getMessage(
                            "Program is a duplicate"), WebRequest.SCOPE_SESSION);
                } else {
                        
                        
                        HouseholdDefinition hd = new HouseholdDefinition(programCode, codeInFull, programDescription);
                        hd.setParent(parent);
                        if((hd.getParent() != null) && (StringUtils.hasText(identifierPrefix))){
                                hd.setIdentifierPrefix(identifierPrefix);
                        }
                        Context.getService(HouseholdService.class).saveHouseholdDefinition(hd);
                        request.setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage(
                            "Program saved"), WebRequest.SCOPE_SESSION);
                }
       
       
       return "redirect:householdSettingsPanel.form";
   }*/
   
   /**
         * Display the edit page for HouseholdDefinitions
         */
   /*@RequestMapping(method = RequestMethod.GET, value = "module/household/householdEditProgram")
        public void showEdit(@RequestParam("id") HouseholdDefinition hd, ModelMap model) {
                HouseholdDefinition householdDefinition = hd;
                
                model.addAttribute("householdDef", householdDefinition); // this will go in the session
                model.addAttribute("householdParent", householdDefinition.getParent());
                
                HouseholdService service = Context.getService(HouseholdService.class);
                List<HouseholdDefinitionParent> householdsTypes = service.getHouseholdDefinitionParent(false);
                model.addAttribute("householdsTypes", householdsTypes);
        }*/
        
        /**
         * Handle submission for editing a Program (for editing its name/parent/description)
         */
        /*@RequestMapping(method = RequestMethod.POST, value = "module/household/householdEditProgram")
        public String handleEditSubmission(WebRequest request, @ModelAttribute("householdDef") HouseholdDefinition householdDefinition,
                BindingResult result, SessionStatus status) {
                
                if (result.hasErrors()) {
                        return "householdEditProgram";
                } else {
                        Context.getService(HouseholdService.class).saveHouseholdDefinition(householdDefinition);
                        request.setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage(
                            "Household Definition  Saved"), WebRequest.SCOPE_SESSION);
                        status.setComplete();
                        return "redirect:householdSettingsPanel.form";
                }
        }
        
        @RequestMapping("module/household/householdAddEncounterType")
   public String programAddET(ModelMap map, @RequestParam("name") String name,
                                        @RequestParam("programDescription") String description,
                                        WebRequest request) {
       
       if (!StringUtils.hasText(name)) {
                        request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Context.getMessageSourceService().getMessage(
                            "Encounter type name Required"), WebRequest.SCOPE_SESSION);
                } else if (Context.getService(HouseholdService.class).getHouseholdEncounterTypeByName(name) != null) {
                        request.setAttribute(WebConstants.OPENMRS_ERROR_ATTR, Context.getMessageSourceService().getMessage(
                            "Encounter type is a duplicate"), WebRequest.SCOPE_SESSION);
                } else {
                        
                        HouseholdEncounterType et = new HouseholdEncounterType(name, description);
                        Context.getService(HouseholdService.class).saveHouseholdEncounterType(et);
                        request.setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage(
                            "Encounter type saved"), WebRequest.SCOPE_SESSION);
                }
       
       
       return "redirect:householdSettingsPanel.form";
   }*/
   
   /**
         * Display the edit page for HouseholdEncounterType
         */
   /*@RequestMapping(method = RequestMethod.GET, value = "module/household/householdEditEncounterType")
        public void showEditET(@RequestParam("id") HouseholdEncounterType hET, ModelMap model) {
                HouseholdEncounterType householdET = hET;
                model.addAttribute("householdET", householdET);
        }*/
        
        /**
         * Handle submission for editing a EncounterType (for editing its name/description)
         */
        /*@RequestMapping(method = RequestMethod.POST, value = "module/household/householdEditEncounterType")
        public String handleEditETSubmission(WebRequest request, @ModelAttribute("householdET") HouseholdEncounterType het,
                BindingResult result, SessionStatus status) {
                
                if (result.hasErrors()) {
                        return "householdEditEncounterType";
                } else {
                        Context.getService(HouseholdService.class).saveHouseholdEncounterType(het);
                        request.setAttribute(WebConstants.OPENMRS_MSG_ATTR, Context.getMessageSourceService().getMessage(
                            "Household Encounter Type  Saved"), WebRequest.SCOPE_SESSION);
                        status.setComplete();
                        return "redirect:householdSettingsPanel.form";
                }
        }
        
        @RequestMapping("module/household/householdSettingsEncType")
        public void repostEncounterSettings(ModelMap map, boolean includedRetired){
        	HouseholdService service = Context.getService(HouseholdService.class);
        	List<HouseholdEncounterType> encType = service.getAllHouseholdEncounterTypes(includedRetired);
        	map.addAttribute("householdEncTypes",encType);
        }*/
        
}