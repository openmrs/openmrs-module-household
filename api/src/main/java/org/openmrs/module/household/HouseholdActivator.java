package org.openmrs.module.household;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.Activator;


/**
 * 
 */

/**
 * @author Ampath Developers
 *
 */
public class HouseholdActivator implements Activator {
	private static Log log = LogFactory.getLog(HouseholdActivator.class);
	/* (non-Javadoc)
	 * @see org.openmrs.module.Activator#startup()
	 */
	public void startup() {
		log.info("Starting Household module");
	}
	
	/* (non-Javadoc)
	 * @see org.openmrs.module.Activator#shutdown()
	 */
	public void shutdown() {
		log.info("Stopping Household module");
	}
	
}
