package org.openmrs.module.household.util;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.HouseholdLocationField;
import org.openmrs.module.household.model.HouseholdLocation;
import org.openmrs.module.household.exception.HouseholdModuleException;


public class HouseholdLocationUtil {

	protected static final Log log = LogFactory.getLog(HouseholdLocationUtil.class);
	
	/**
	 * Fetches a global property and converts it to a Boolean value
	 * 
	 * @param globalPropertyName
	 * @return Boolean value of global property
	 */
	public static final Boolean getGlobalPropertyAsBoolean(String globalPropertyName) {
		
		String globalPropertyValue = Context.getAdministrationService().getGlobalProperty(globalPropertyName, "true");
		
		if (globalPropertyValue.equalsIgnoreCase("true")) {
			return true;
		}
		
		if (globalPropertyValue.equalsIgnoreCase("false")) {
			return false;
		}

		throw new HouseholdModuleException("Global property " + globalPropertyName + " must be set to either 'true' or 'false'.");
	}
	
	/**
	 * Given a person address object, this method uses reflection fetch the value of the specified field
	 */
	public static final String getHouseholdLocationFieldValue(HouseholdLocation address, HouseholdLocationField field) {		
		try {
	        Method getter = HouseholdLocation.class.getMethod("get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1));
	        return (String) getter.invoke(address);
	        
        }
        catch (Exception e) {
	        throw new HouseholdModuleException("Unable to get address field " + field.getName() + " off of HouseholdLocation", e);
        }
	}
	
	/**
	 * Given a person address object, this method uses reflection to set the value of the specified field
	 */
	public static final  void setHouseholdLocationFieldValue(HouseholdLocation address, HouseholdLocationField field, String value) {
		try {
			Method setter = HouseholdLocation.class.getMethod("set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1), String.class);
			setter.invoke(address, value);
		}
		catch (Exception e) {
	        throw new HouseholdModuleException("Unable to set address field " + field.getName() + " on HouseholdLocation", e);
        }
	}
	
	
	public static final HouseholdLocation convertHouseholdLocationMapToHouseholdLocation(Map<String,String> addressMap) {
		HouseholdLocation address = new HouseholdLocation();

		for (String addressFieldName : addressMap.keySet()) {
			setHouseholdLocationFieldValue(address, HouseholdLocationField.getByName(addressFieldName), addressMap.get(addressFieldName));
		}
		
		return address;
	}

}


