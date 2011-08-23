package org.openmrs.module.household.util;

import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class HouseholdLocationLevelValidator implements Validator {

	public boolean supports(@SuppressWarnings("rawtypes") Class c) {
		return HouseholdLocationLevel.class.isAssignableFrom(c);
	}

	
    public void validate(Object obj, Errors errors) {
	    HouseholdLocationLevel level = (HouseholdLocationLevel) obj;	    
	  
	    // a level can't have itself as a parent
	    if (level.getParent() == level) {
	    	errors.rejectValue("parent", "addresshierarchy.admin.validation.parent.ownParent");
	    }
	    
	    // confirm that the selected address field and parent aren't associated with another level
	    for (HouseholdLocationLevel compareLevel : Context.getService(HouseholdService.class).getHouseholdLocationLevels()) {
	    	
	    	// we only want to test all OTHER address hierarchy types
	    	
	    	if (level.getId() == null || level.getId() != compareLevel.getId()) {
	    		
	    		if (level.getHouseholdLocationField() != null && compareLevel.getHouseholdLocationField() == level.getHouseholdLocationField()) {
	    			errors.rejectValue("addressField", "addresshierarchy.admin.validation.addressField.alreadyUsed");
	    		}
	    		
	    		if (compareLevel.getParent() == level.getParent()) {
	    			errors.rejectValue("parentType", "addresshierarchy.admin.validation.parentType.alreadyUsed");
	    		}
	    		
	    	}
	    }
    }
}

