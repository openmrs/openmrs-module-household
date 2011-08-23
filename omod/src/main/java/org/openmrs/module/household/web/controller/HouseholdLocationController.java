package org.openmrs.module.household.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.HouseholdLocationField;
import org.openmrs.module.household.model.HouseholdLocationEntry;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.exception.HouseholdModuleException;
import org.openmrs.module.household.util.HouseholdLocationFieldEditor;
import org.openmrs.module.household.util.HouseholdLocationLevelEditor;
import org.openmrs.module.household.service.HouseholdService;
import org.openmrs.module.household.util.HouseholdLocationImportUtil;
import org.openmrs.module.household.util.HouseholdLocationLevelValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class HouseholdLocationController {

	protected static final Log log = LogFactory.getLog(HouseholdLocationController.class);
	
	/** Validator for this controller */
	private HouseholdLocationLevelValidator validator;
	
	@Autowired
	public HouseholdLocationController(HouseholdLocationLevelValidator validator) {
		this.validator = validator;
	}
	
	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		// register custom binders
		binder.registerCustomEditor(HouseholdLocationLevel.class, new HouseholdLocationLevelEditor()); 
		binder.registerCustomEditor(HouseholdLocationField.class, new HouseholdLocationFieldEditor()); 
	}
	
	@ModelAttribute("householdLocationFields")
	public HouseholdLocationField [] getHouseholdLocationFields() {
		return HouseholdLocationField.values();
	}
	
	/*@ModelAttribute("nameMappings")
	public Map<String,String> getAddressNameMappings() {
		//TODO return (AddressSupport.getInstance() != null && AddressSupport.getInstance().getDefaultLayoutTemplate() != null) ? AddressSupport.getInstance().getDefaultLayoutTemplate().getNameMappings() : null;
		return null;
	}*/
	
	@ModelAttribute("levels")
	public List<HouseholdLocationLevel> getOrderedHouseholdLocationLevels() {
		// before getting the levels, we first make sure the parents are set properly (mainly to handle any migration from the 1.2 model)
		Context.getService(HouseholdService.class).setHouseholdLocationLevelParents();
		return Context.getService(HouseholdService.class).getHouseholdLocationLevels();
	}
	
	@ModelAttribute("messages")
	public List<String> showMessage(@RequestParam(value = "message", required = false) String message) {
		List<String> messages = new ArrayList<String>();
		if(StringUtils.isNotBlank(message)) {
			messages.add(message);
		}
		return messages;
	}
	
	@ModelAttribute("sampleEntries")
	public List<List<String>> getSampleEntries(@ModelAttribute("levels") List<HouseholdLocationLevel> levels) {
		
		List<List<String>> sampleEntries = new ArrayList<List<String>>();
		
		for (HouseholdLocationLevel level : levels) {
			List<String> sampleEntry = new ArrayList<String>();
			List<HouseholdLocationEntry> entries = Context.getService(HouseholdService.class).getHouseholdLocationEntriesByLevel(level);
			if(entries != null && entries.size() > 0) {
				sampleEntry.add(entries.get(0).getName());
				sampleEntry.add(String.valueOf(entries.size()));
			}
			else {
				sampleEntry.add("");
				sampleEntry.add("0");
			}
			
			sampleEntries.add(sampleEntry);
		}
		
		return sampleEntries;
	}
	
	@ModelAttribute("level")
	public HouseholdLocationLevel getHouseholdLocationLevel(@RequestParam(value = "levelId", required = false) Integer levelId) {
				
		HouseholdLocationLevel level;
    	
    	// fetch the household location level, or if none specified, create a new one
    	if (levelId != null) {
    		level = Context.getService(HouseholdService.class).getHouseholdLocationLevel(levelId);
    		
    		if (level == null) {
    			throw new HouseholdModuleException("Invalid household location level id " + levelId);
    		}
    	}
    	else {
    		level = new HouseholdLocationLevel();
    		// set the new type to be the child of the bottom-most type in the hierarchy
    		level.setParent(Context.getService(HouseholdService.class).getBottomHouseholdLocationLevel());
    	}
    	
    	return level;
	}
	
    @RequestMapping("/module/household/manageHouseholdLocation.form")
	public ModelAndView manageHouseholdLocation() {
		return new ModelAndView("/module/household/manageHouseholdLocation");
	}
    
    @RequestMapping("/module/household/editHouseholdLocationLevel.form")
    public ModelAndView viewHouseholdLocationLevel() {
    	return new ModelAndView("/module/household/editHouseholdLocationLevel");
    }
    
    @SuppressWarnings("unchecked")
    @RequestMapping("/module/household/updateHouseholdLocationLevel.form")
    public ModelAndView updateHouseholdLocationLevel(@ModelAttribute("level") HouseholdLocationLevel level, BindingResult result, SessionStatus status,  ModelMap map) {
    
    	// validate form entries
		validator.validate(level, result);
		
		if (result.hasErrors()) {
			map.put("errors", result);
			return new ModelAndView("/module/household/editHouseholdLocationLevel", map);
		}
    	
		// add/update the household location type
		Context.getService(HouseholdService.class).saveHouseholdLocationLevel(level);
		
		// clears the command object from the session
		status.setComplete();
		
		return new ModelAndView("redirect:/module/household/manageHouseholdLocation.form");
    	
    }
    
    @RequestMapping("/module/household/deleteHouseholdLocationLevel.form")
    public ModelAndView deleteHouseholdLocationLevel(@ModelAttribute("level") HouseholdLocationLevel level) {
    	
    	// we are only allowing the deletion of the bottom-most type
    	if (level != Context.getService(HouseholdService.class).getBottomHouseholdLocationLevel()) {
    		throw new HouseholdModuleException("Cannot delete Household Location Level; not bottom type in the hierarchy");
    	}
    	
    	if (Context.getService(HouseholdService.class).getHouseholdLocationEntryCountByLevel(level) > 0) {
    		throw new HouseholdModuleException("Cannot delete Household Location; it has associated entries");
    	}
    	
    	// deletes the household location type
    	Context.getService(HouseholdService.class).deleteHouseholdLocationLevel(level);
    	
    	return new ModelAndView("redirect:/module/household/manageHouseholdLocation.form");
    }
 
    @SuppressWarnings("unchecked")
    @RequestMapping("/module/household/uploadHouseholdLocation.form")
	public ModelAndView processHouseholdLocationUploadForm(@RequestParam("file") MultipartFile file,
	                                                      @RequestParam("delimiter") String delimiter, 
	                                                      @RequestParam(value = "overwrite", required = false) Boolean overwrite,
	                                                      ModelMap map) {	
				
		// handle validation
		if (delimiter == null || delimiter.isEmpty()) {
			((List<String>) map.get("messages")).add("householdLocation.validation.noDelimiter");
		}
		if (file == null || file.isEmpty()) {
			((List<String>) map.get("messages")).add("householdLocation.validation.noFile");
		}
		if (((List<String>) map.get("messages")).size() > 0) {
			map.addAttribute("delimiter", delimiter);
			map.addAttribute("overwrite", overwrite);
			return new ModelAndView("/module/household/manageHouseholdLocation", map);
		}
		
		// do the actual update
		try {
			// delete old records if overwrite has been selected
			if (overwrite != null && overwrite == true) {
				Context.getService(HouseholdService.class).deleteAllHouseholdLocationEntries();
			}
			
			// do the actual import
	        HouseholdLocationImportUtil.importHouseholdLocationFile(file.getInputStream(), delimiter);
        }
        catch (Exception e) {
	        log.error("Unable to import household location file", e);
	        ((List<String>) map.get("messages")).add("householdLocation.uploadFailure");
			map.addAttribute("delimiter", delimiter);
			map.addAttribute("overwrite", overwrite);
			return new ModelAndView("/module/household/manageHouseholdLocation", map);
        }
        
		return new ModelAndView("redirect:/module/household/manageHouseholdLocation.form?message=" +
								"householdLocation.uploadSuccess", map);
	}
}

