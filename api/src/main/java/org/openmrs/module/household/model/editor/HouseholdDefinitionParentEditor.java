/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.household.model.editor;


import java.beans.PropertyEditorSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.household.model.HouseholdDefinitionParent;
import org.openmrs.module.household.service.HouseholdService;
import org.openmrs.api.context.Context;
import org.springframework.util.StringUtils;

/**
 * Allows for serializing/deserializing an object to a string so that Spring knows how to pass
 * an object back and forth through an html form or other medium. <br/>
 * <br/>
 * In version 1.9, added ability for this to also retrieve objects by uuid
 * 
 * @see HouseholdDefinitionParent
 */
public class HouseholdDefinitionParentEditor extends PropertyEditorSupport {
	
	private Log log = LogFactory.getLog(this.getClass());
	
	public HouseholdDefinitionParentEditor() {
	}
	
	/**
	 * @should set using id
	 * @should set using uuid
	 */
    @Override
	public void setAsText(String text) throws IllegalArgumentException {
		HouseholdService hs = Context.getService(HouseholdService.class);
		if (StringUtils.hasText(text)) {
			try {
				setValue(hs.getHouseholdDefinitionParent(Integer.valueOf(text)));
			}
			catch (Exception ex) {
				HouseholdDefinitionParent householdDefinitionParent = hs.getHouseholdDefinitionParent(text);
				setValue(householdDefinitionParent);
				if (householdDefinitionParent == null) {
					log.error("Error setting text: " + text, ex);
					throw new IllegalArgumentException("Department not found: " + ex.getMessage());
				}
			}
		} else {
			setValue(null);
		}
	}
	
    @Override
	public String getAsText() {
		HouseholdDefinitionParent t = (HouseholdDefinitionParent) getValue();
		if (t == null && Context.isAuthenticated()) {
			return null; //return Context.getAuthenticatedUser().getUserProperty(OpenmrsConstants.USER_PROPERTY_DEFAULT_LOCATION);
		} else {
			return t.getId().toString();
		}
	}
	
}
