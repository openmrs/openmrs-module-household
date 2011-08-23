package org.openmrs.module.household.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.io.UnicodeInputStream;
import org.openmrs.api.context.Context;
import org.openmrs.module.household.model.HouseholdLocationEntry;
import org.openmrs.module.household.model.HouseholdLocationLevel;
import org.openmrs.module.household.exception.HouseholdModuleException;
import org.openmrs.module.household.service.HouseholdService;


public class HouseholdLocationImportUtil {
	
	  protected static final Log log = LogFactory.getLog(HouseholdLocationImportUtil.class);
	
	/**
	 * Takes a file of delimited addresses and creates and address hierarchy out of it
	 * Starting level determines what level of the hierarchy to start at when doing the input
	 */
	public static final void importAddressHierarchyFile(InputStream stream, String delimiter, HouseholdLocationLevel startingLevel) {
		
		HouseholdService ahService = Context.getService(HouseholdService.class);
		
		String line;
		
		// to let us know if we even need to query the database (to speed up performance)
		Boolean hasExistingEntries = ahService.getHouseholdLocationEntryCount() > 0 ? true : false;
		
		 // a cache we use to speed up performance
		Map<HouseholdLocationEntry,Map<String,HouseholdLocationEntry>> entryCache = new HashMap<HouseholdLocationEntry,Map<String,HouseholdLocationEntry>>(); 
		
		// the list of all address hierarchy entries
		List<HouseholdLocationEntry> entries = new LinkedList<HouseholdLocationEntry>();
		
		// get an ordered list of the address hierarchy levels
		List<HouseholdLocationLevel> levels = ahService.getOrderedHouseholdLocationLevels();
		
		// if we aren't starting at the top level of the hierarchy, remove all the levels before the one we wish to start at
		if (startingLevel != null) {
			Iterator<HouseholdLocationLevel> i = levels.iterator();
			while (i.next() != startingLevel) {
				i.remove();
			}
		}
		
		// process the file
		try {
			// Note that we are using UnicodeInputStream to work around this Java bug: http://bugs.sun.com/view_bug.do?bug_id=4508058 
			BufferedReader reader = new BufferedReader(new InputStreamReader(new UnicodeInputStream(stream), Charset.forName("UTF-8")));
			
			// step through the file line by line
	        while ((line = reader.readLine()) != null) {
	        	
	        	// now split the line up by the delimiter
	        	String [] locations = line.split(delimiter);
	        	
	        	if (locations != null) {
	        	
	        		Stack<HouseholdLocationEntry> entryStack = new Stack<HouseholdLocationEntry>();
	        		
		        	// now cycle through all the locations on this line
		        	for (int i = 0; i < locations.length; i++) {
		        	
		        		// create a new level if we need it
	        			if (levels.size() == i) {
	        				levels.add(ahService.addHouseholdLocationLevel());
	        			}
		        		
	        			String strippedLocation = StringUtils.strip(StringUtils.trim(locations[i]));
	        			
	        			HouseholdLocationEntry entry = null;
	        			HouseholdLocationEntry parent = entryStack.isEmpty() ? null : entryStack.peek();
	        			
	        			// first see if this entry already exists in the cache
	        			if (entryCache.containsKey(parent) && entryCache.get(parent).containsKey(strippedLocation.toLowerCase())) {
	        				entry = entryCache.get(parent).get(strippedLocation.toLowerCase());
	        			}
	        			// if it is not in the cache, see if it is in the database if there are existing entries
	        			else if (hasExistingEntries) {
	            			entry = ahService.getChildHouseholdLocationEntryByName(parent, strippedLocation);
	            			// if we have found an entry, add it to the cache
	            			if (entry != null) {
	            				addToCache(entryCache, parent, entry);
	            			}
	        			}
	        			    		
		        		// if we still haven't found the entry, we need to create it
		        		if (entry == null) {
		        			
		        			// create the new entry and set its name, location and parent
		        			entry = new HouseholdLocationEntry();
		        			entry.setName(strippedLocation);
		        			entry.setLevel(levels.get(i));
		        			entry.setParent(parent);
		        			
		        			// add the entry to the list to add, and add it to the cache
		        			entries.add(entry);
		        			addToCache(entryCache, parent, entry);
		        			
		        		}
		        		// push this entry onto the stack
	        			entryStack.push(entry);
		        	}
	        	}
	        }  
        }
        catch (IOException e) { 
	        throw new HouseholdModuleException("Error accessing address hierarchy import stream ", e);
        }
        
        // now do the actual save
        ahService.saveHouseholdLocationEntries(entries);
	}
	
	public static final void importHouseholdLocationFile(InputStream stream, String delimiter) {
		importAddressHierarchyFile(stream, delimiter, null);
	}
	
	/**
	 * Takes a CSV file of addresses and creates and address hierarchy out of it
	 */
	public static final void importCsvFile(InputStream stream, HouseholdLocationLevel startingLevel) {
		importAddressHierarchyFile(stream, ",", startingLevel);
	}
	
	public static final void importCsvFile(InputStream stream) {
		importCsvFile(stream, null);
	}
	
	
	/**
	 * Utility methods
	 */
	public static final void addToCache(Map<HouseholdLocationEntry,Map<String,HouseholdLocationEntry>> entryCache, 
	                                    HouseholdLocationEntry parent, HouseholdLocationEntry entry) {
		if (!entryCache.containsKey(parent)) {
			entryCache.put(parent, new HashMap<String,HouseholdLocationEntry>());
		}
		entryCache.get(parent).put(entry.getName().toLowerCase(), entry);
	}
}
