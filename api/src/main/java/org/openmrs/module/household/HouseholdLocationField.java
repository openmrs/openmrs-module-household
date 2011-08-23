package org.openmrs.module.household;

import org.apache.commons.lang.StringUtils;

/**
 * This is a list of all the valid household location fields on HouseholdLocation.
 * 
 * 
 */
public enum HouseholdLocationField {
	
	ADDRESS_1("address1"), ADDRESS_2("address2"), ADDRESS_3("address3"), ADDRESS_4("address4"), 
	ADDRESS_5("address5"), ADDRESS_6("address6"),
	CITY_VILLAGE("cityVillage"), CITY_SUB_LOCATION("citySubLocation"), CITY_LOCATION("cityLocation"), 
	COUNTY_DISTRICT("countyDistrict"), COUNTRY("country"), POSTAL_CODE("postalCode");
	
	String name;
	
	HouseholdLocationField(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static final HouseholdLocationField getByName(String name) {
		
		for (HouseholdLocationField field : HouseholdLocationField.values()) {
			if (StringUtils.equals(name, field.getName())) {
				return field;
			}
		}
		
		return null;
	}
}
