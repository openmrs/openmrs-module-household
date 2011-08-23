package org.openmrs.module.household.util;

import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.household.HouseholdLocationField;
import org.springframework.util.StringUtils;


public class HouseholdLocationFieldEditor extends PropertyEditorSupport  {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public HouseholdLocationFieldEditor() {
	}
	
	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			try {
				setValue(HouseholdLocationField.getByName(text));
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
		HouseholdLocationField field = (HouseholdLocationField) getValue();
		if (field == null) {
			return "";
		} else {
			return field.getName();
		}
	}
}
