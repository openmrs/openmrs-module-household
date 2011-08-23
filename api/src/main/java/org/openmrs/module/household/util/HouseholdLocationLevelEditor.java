package org.openmrs.module.household.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.service.HouseholdService;
import org.springframework.util.StringUtils;


public class HouseholdLocationLevelEditor extends PropertyEditorSupport {

	private Log log = LogFactory.getLog(this.getClass());
	
	public HouseholdLocationLevelEditor() {
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			try {
				setValue(Context.getService(HouseholdService.class).getHouseholdLocationLevel(Integer.valueOf(text)));
			}
			catch (Exception ex) {
				log.error("Error setting text" + text, ex);
				throw new IllegalArgumentException("Concept not found: " + ex.getMessage());
			}
		} else {
			setValue(null);
		}
	}
	
	public String getAsText() {
		HouseholdLocationLevel level = (HouseholdLocationLevel) getValue();
		if (level == null) {
			return "";
		} else {
			return level.getId().toString();
		}
	}
	
}
