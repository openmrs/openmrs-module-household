/**
 * 
 */
package org.openmrs.module.household.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Attributable;
import org.openmrs.BaseOpenmrsData;
import org.openmrs.api.context.Context;
import org.openmrs.util.OpenmrsClassLoader;
import org.openmrs.util.OpenmrsUtil;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 *  @author Ampath Developers
 *
 */
@Root(strict = false)
public class HouseholdAttribValue extends BaseOpenmrsData {

private static final Log log = LogFactory.getLog(HouseholdAttribute.class);
	
	// Fields
	
	private Integer householdAttributeId;
	
	private Household household;
	
	private HouseholdAttribute householdAttributeType;
	
	private String value;
	
	/** default constructor */
	public HouseholdAttribValue() {
	}
	
	public HouseholdAttribValue(Integer householdAttributeId) {
		this.householdAttributeId = householdAttributeId;
	}
	
	/**
	 * Constructor for creating a basic attribute
	 * 
	 * @param type HouseholdAttribute
	 * @param value String
	 */
	public HouseholdAttribValue(HouseholdAttribute type, String value) {
		this.householdAttributeType = type;
		this.value = value;
	}
	
	/**
	 * Shallow copy of this HouseholdAttribValue. Does NOT copy householdAttributeId
	 * 
	 * @return a shallows copy of <code>this</code>
	 */
	public HouseholdAttribValue copy() {
		return copyHelper(new HouseholdAttribValue());
	}
	
	/**
	 * The purpose of this method is to allow subclasses of HouseholdAttribValue to delegate a portion of
	 * their copy() method back to the superclass, in case the base class implementation changes.
	 * 
	 * @param target a HouseholdAttribValue that will have the state of <code>this</code> copied into it
	 * @return Returns the HouseholdAttribValue that was passed in, with state copied into it
	 */
	protected HouseholdAttribValue copyHelper(HouseholdAttribValue target) {
		target.setHousehold(getHousehold());
		target.setHouseholdAttributeType(getHouseholdAttributeType());
		target.setValue(getValue());
		target.setCreator(getCreator());
		target.setDateCreated(getDateCreated());
		target.setChangedBy(getChangedBy());
		target.setDateChanged(getDateChanged());
		target.setVoidedBy(getVoidedBy());
		target.setVoided(isVoided());
		target.setDateVoided(getDateVoided());
		target.setVoidReason(getVoidReason());
		return target;
	}
	
	/**
	 * Compares two objects for similarity
	 * 
	 * @param obj
	 * @return boolean true/false whether or not they are the same objects
	 * @should return true if householdAttributeIds match
	 * @should return false if householdAttributeIds do not match
	 * @should match on object equality if a householdAttributeId is null
	 */
	public boolean equals(Object obj) {
		if (obj instanceof HouseholdAttribValue) {
			HouseholdAttribValue attr = (HouseholdAttribValue) obj;
			if (attr.getHouseholdAttributeId() != null && getHouseholdAttributeId() != null)
				return attr.getHouseholdAttributeId().equals(getHouseholdAttributeId());
			
		}
		return this == obj;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		if (this.getHouseholdAttributeId() == null)
			return super.hashCode();
		int hash = 5;
		hash += 29 * hash + this.getHouseholdAttributeId().hashCode();
		return hash;
	}
	
	/**
	 * Compares this HouseholdAttribValue object to the given otherAttribute. This method differs from
	 * {@link #equals(Object)} in that this method compares the inner fields of each attribute for
	 * equality. Note: Null/empty fields on <code>otherAttribute</code> /will not/ cause a false
	 * value to be returned
	 * 
	 * @param otherAttribute HouseholdAttribValue with which to compare
	 * @return boolean true/false whether or not they are the same attributes
	 * @should return true if householdAttributeType value and void status are the same
	 */
	@SuppressWarnings("unchecked")
	public boolean equalsContent(HouseholdAttribValue otherAttribute) {
		boolean returnValue = true;
		
		// these are the methods to compare.
		String[] methods = { "getAttributeType", "getValue", "getVoided" };
		
		@SuppressWarnings("rawtypes")
		Class attributeClass = this.getClass();
		
		// loop over all of the selected methods and compare this and other
		for (String methodAttribute : methods) {
			try {
				Method method = attributeClass.getMethod(methodAttribute, new Class[] {});
				
				Object thisValue = method.invoke(this);
				Object otherValue = method.invoke(otherAttribute);
				
				if (otherValue != null)
					returnValue &= otherValue.equals(thisValue);
				
			}
			catch (NoSuchMethodException e) {
				log.warn("No such method for comparison " + methodAttribute, e);
			}
			catch (IllegalAccessException e) {
				log.error("Error while comparing attributes", e);
			}
			catch (InvocationTargetException e) {
				log.error("Error while comparing attributes", e);
			}
			
		}
		
		return returnValue;
	}
	
	// property accessors
	
	/**
	 * @return Returns the household.
	 */
	@Element(required = true)
	public Household getHousehold() {
		return household;
	}
	
	/**
	 * @param household The household to set.
	 */
	@Element(required = true)
	public void setHousehold(Household household) {
		this.household = household;
	}
	
	/**
	 * @return the householdAttributeType
	 */
	@Element(required = true)
	public HouseholdAttribute getHouseholdAttributeType() {
		return householdAttributeType;
	}
	
	/**
	 * @param householdAttributeType the householdAttributeType to set
	 */
	@Element(required = true)
	public void setHouseholdAttributeType(HouseholdAttribute householdAttributeType) {
		this.householdAttributeType = householdAttributeType;
	}
	
	/**
	 * @return the value
	 */
	@Element(data = true, required = false)
	public String getValue() {
		return value;
	}
	
	/**
	 * @param value the value to set
	 */
	@Element(data = true, required = false)
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 * @should return toString of hydrated value
	 */
	@SuppressWarnings({ "rawtypes" })
	public String toString() {
		Object o = getHydratedObject();
		if (o instanceof Attributable)
			return ((Attributable) o).getDisplayString();
		else if (o != null)
			return o.toString();
		
		return this.value;
	}
	
	/**
	 * @return the householdAttributeId
	 */
	@Attribute(required = true)
	public Integer getHouseholdAttributeId() {
		return householdAttributeId;
	}
	
	/**
	 * @param householdAttributeId the householdAttributeId to set
	 */
	@Attribute(required = true)
	public void setHouseholdAttributeId(Integer householdAttributeId) {
		this.householdAttributeId = householdAttributeId;
	}
	
	/**
	 * Will try to create an object of class 'HouseholdAttribute.format'. If that implements
	 * <code>Attributable</code>, hydrate(value) is called. Defaults to just returning getValue()
	 * 
	 * @return hydrated object or getValue()
	 * @should load class in format property
	 * @should still load class in format property if not Attributable
	 */
	@SuppressWarnings("unchecked")
	public Object getHydratedObject() {
		try {
			@SuppressWarnings("rawtypes")
			Class c = OpenmrsClassLoader.getInstance().loadClass(getHouseholdAttributeType().getFormat());
			try {
				Object o = c.newInstance();
				if (o instanceof Attributable) {
					@SuppressWarnings("rawtypes")
					Attributable attr = (Attributable) o;
					return attr.hydrate(getValue());
				}
			}
			catch (InstantiationException e) {
				// try to hydrate the object with the String constructor
				log.trace("Unable to call no-arg constructor for class: " + c.getName());
				Object o = c.getConstructor(String.class).newInstance(getValue());
				return o;
			}
		}
		catch (Throwable t) {
			log.warn("Unable to hydrate value: " + getValue() + " for type: " + getHouseholdAttributeType(), t);
		}
		
		log.debug("Returning value: '" + getValue() + "'");
		return getValue();
	}
	
	/**
	 * Convenience method for voiding this attribute
	 * 
	 * @param reason
	 * @should set voided bit to true
	 */
	public void voidAttribute(String reason) {
		setVoided(true);
		setVoidedBy(Context.getAuthenticatedUser());
		setVoidReason(reason);
		setDateVoided(new Date());
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * @should return negative if other attribute is voided
	 * @should return negative if other attribute has earlier date created
	 * @should return negative if this attribute has lower attribute type than argument
	 * @should return negative if other attribute has lower value
	 * @should return negative if this attribute has lower attribute id than argument
	 */
	public int compareTo(HouseholdAttribValue other) {
		int retValue = 0;
		retValue = isVoided().compareTo(other.isVoided());
		if (retValue == 0)
			retValue = OpenmrsUtil.compareWithNullAsLatest(getDateCreated(), other.getDateCreated());
		if (retValue == 0)
			retValue = getHouseholdAttributeType().getHouseholdAttributeTypeId().compareTo(
			    other.getHouseholdAttributeType().getHouseholdAttributeTypeId());
		if (retValue == 0)
			retValue = OpenmrsUtil.compareWithNullAsGreatest(getValue(), other.getValue());
		if (retValue == 0)
			retValue = OpenmrsUtil.compareWithNullAsGreatest(getHouseholdAttributeId(), other.getHouseholdAttributeId());
		
		return retValue;
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		
		return getHouseholdAttributeId();
	}
	
	/**
	 * @since 1.5
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer id) {
		setHouseholdAttributeId(id);
		
	}

}
