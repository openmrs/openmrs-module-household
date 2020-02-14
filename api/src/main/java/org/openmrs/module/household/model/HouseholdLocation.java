/**
 * 
 */
package org.openmrs.module.household.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.openmrs.Attributable;
import org.openmrs.BaseOpenmrsMetadata;
import org.openmrs.api.APIException;

/**
 * @author ampath developers
 *
 */
public class HouseholdLocation extends BaseOpenmrsMetadata implements java.io.Serializable, Attributable<HouseholdLocation>  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer householdLocationId;
	
	private String address1;
	
	private String address2;
	
	private String address3;
	
	private String address4;
	
	private String address5;
	
	private String address6;
	
	private String cityVillage;
	
	private String citySubLocation;
	
	private String cityLocation;
	
	private String stateProvince;
	
	private String postalCode;
	
	private String latitude;
	
	private String longitude;
	
	private String countyDistrict;
	
	private HouseholdLocation parentHouseholdLocation;
	
	private Set<HouseholdLocation> childHouseholdLocation;
	
	/** (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#getId()
	 */
	public Integer getId() {
		// TODO Auto-generated method stub
		return getHouseholdLocationId();
	}

	/** (non-Javadoc)
	 * @see org.openmrs.OpenmrsObject#setId(java.lang.Integer)
	 */
	public void setId(Integer id) {
		setHouseholdLocationId(id);
	}

	/**
	 * @return the householdLocationId
	 */
	public Integer getHouseholdLocationId() {
		return householdLocationId;
	}

	/**
	 * @param householdLocationId the householdLocationId to set
	 */
	public void setHouseholdLocationId(Integer householdLocationId) {
		this.householdLocationId = householdLocationId;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @return the address3
	 */
	public String getAddress3() {
		return address3;
	}

	/**
	 * @param address3 the address3 to set
	 */
	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	/**
	 * @return the address4
	 */
	public String getAddress4() {
		return address4;
	}

	/**
	 * @param address4 the address4 to set
	 */
	public void setAddress4(String address4) {
		this.address4 = address4;
	}

	/**
	 * @return the address5
	 */
	public String getAddress5() {
		return address5;
	}

	/**
	 * @param address5 the address5 to set
	 */
	public void setAddress5(String address5) {
		this.address5 = address5;
	}

	/**
	 * @return the address6
	 */
	public String getAddress6() {
		return address6;
	}

	/**
	 * @param address6 the address6 to set
	 */
	public void setAddress6(String address6) {
		this.address6 = address6;
	}

	/**
	 * @return the cityVillage
	 */
	public String getCityVillage() {
		return cityVillage;
	}

	/**
	 * @param cityVillage the cityVillage to set
	 */
	public void setCityVillage(String cityVillage) {
		this.cityVillage = cityVillage;
	}

	/**
	 * @return the citySubLocation
	 */
	public String getCitySubLocation() {
		return citySubLocation;
	}

	/**
	 * @param citySubLocation the citySubLocation to set
	 */
	public void setCitySubLocation(String citySubLocation) {
		this.citySubLocation = citySubLocation;
	}

	/**
	 * @return the cityLocation
	 */
	public String getCityLocation() {
		return cityLocation;
	}

	/**
	 * @param cityLocation the cityLocation to set
	 */
	public void setCityLocation(String cityLocation) {
		this.cityLocation = cityLocation;
	}

	/**
	 * @return the stateProvince
	 */
	public String getStateProvince() {
		return stateProvince;
	}

	/**
	 * @param stateProvince the stateProvince to set
	 */
	public void setStateProvince(String stateProvince) {
		this.stateProvince = stateProvince;
	}

	/**
	 * @return the postalCode
	 */
	public String getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the countyDistrict
	 */
	public String getCountyDistrict() {
		return countyDistrict;
	}

	/**
	 * @param countyDistrict the countyDistrict to set
	 */
	public void setCountyDistrict(String countyDistrict) {
		this.countyDistrict = countyDistrict;
	}

	/**
	 * @return the parentHouseholdLocation
	 */
	public HouseholdLocation getParentHouseholdLocation() {
		return parentHouseholdLocation;
	}

	/**
	 * @param parentHouseholdLocation the parentHouseholdLocation to set
	 */
	public void setParentHouseholdLocation(HouseholdLocation parentHouseholdLocation) {
		this.parentHouseholdLocation = parentHouseholdLocation;
	}

	/**
	 * @return the childHouseholdLocation
	 */
	public Set<HouseholdLocation> getChildHouseholdLocation() {
		return childHouseholdLocation;
	}
	
	/**
	 * Returns all HouseholdChildLocations where child.householdlocationId = this.householdLocationId.
	 * 
	 * @param includeRetired specifies whether or not to include voided HouseholdChildLocations
	 * @return Returns a Set<HouseholdLocation> of all the householdChildLocations.
	 * @since 1.5
	 * @should return a set of householdLocations
	 */
	public Set<HouseholdLocation> getChildHouseholdLocation(boolean includeRetired) {
		Set<HouseholdLocation> ret = new HashSet<HouseholdLocation>();
		if (includeRetired)
			ret = getChildHouseholdLocation();
		else if (getChildHouseholdLocation() != null) {
			for (HouseholdLocation l : getChildHouseholdLocation()) {
				if (!l.isRetired())
					ret.add(l);
			}
		}
		return ret;
	}

	/**
	 * @param childHouseholdLocation the childHouseholdLocation to set
	 */
	public void setChildHouseholdLocation(Set<HouseholdLocation> childHouseholdLocation) {
		this.childHouseholdLocation = childHouseholdLocation;
	}
	
	/**
	 * @param child The child location to add.
	 * @since 1.5
	 * @should return null given null parameter
	 * @should throw APIException given same object as child
	 * @should throw APIException if child already in hierarchy
	 */
	public void addChildLocation(HouseholdLocation child) {
		if (child == null)
			return;
		
		if (getChildHouseholdLocation() == null)
			childHouseholdLocation = new HashSet<HouseholdLocation>();
		
		if (child.equals(this))
			throw new APIException("A location cannot be its own child!");
		
		// Traverse all the way up (down?) to the root, then check whether the child is already
		// anywhere in the tree
		HouseholdLocation root = this;
		while (root.getParentHouseholdLocation() != null)
			root = root.getParentHouseholdLocation();
		
		if (isInHierarchy(child, root))
			throw new APIException("Location hierarchy loop detected! You cannot add: '" + child + "' to the parent: '"
			        + this
			        + "' because it is in the parent hierarchy somewhere already and a location cannot be its own parent.");
		
		child.setParentHouseholdLocation(this);
		childHouseholdLocation.add(child);
	}
	
	/**
	 * Checks whether 'location' is a member of the tree starting at 'root'.
	 * 
	 * @param location The location to be tested.
	 * @param root Location node from which to start the testing (down in the hierarchy).
	 * @since 1.5
	 * @should return false given any null parameter
	 * @should return true given same object in both parameters
	 * @should return true given location that is already somewhere in hierarchy
	 * @should return false given location that is not in hierarchy
	 * @should should find location in hierarchy
	 */
	public static Boolean isInHierarchy(HouseholdLocation location, HouseholdLocation root) {
		if (location == null || root == null)
			return false;
		if (root.equals(location))
			return true;
		if (root.getChildHouseholdLocation() != null) {
			for (HouseholdLocation l : root.getChildHouseholdLocation())
				return isInHierarchy(location, l);
		}
		
		return false;
	}

	@Deprecated
	public List<HouseholdLocation> findPossibleValues(String searchText) {
		return null;
	}

	@Override
	public String toString() {
		return getName();
	}
	
	public String getDisplayString() {
		return getName();
	}

	@Deprecated
	public List<HouseholdLocation> getPossibleValues() {
		// TODO Auto-generated method stub
		return null;
	}

	public HouseholdLocation hydrate(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String serialize() {
		if (getHouseholdLocationId() != null)
			return "" + getHouseholdLocationId();
		else
			return "";
	}

}
