package com.gnorsilva.android.reschecker;

import java.util.regex.Pattern;

public class Resource {
	
	private final Pattern xml;
	private final Pattern java;
	private final String resourceType;
	private final String resourceName;
	
	public Pattern getJavaPattern(){
		return java;
	}
	
	public Pattern getXMLPattern(){
		return xml;
	}

	public Resource(String resourceType, String resourceName) throws NullPointerException{
		if ( resourceType == null || resourceName == null || resourceType.length() == 0 || resourceName.length() == 0){
			throw new NullPointerException("Resource Type and Resource Name cannot be null");
		}
		xml = Pattern.compile("((\\W@" + resourceType + "\\/|parent=\")" + resourceName + "\\W)|\\W" + resourceName + "\\.");
		java = Pattern.compile("\\WR." + resourceType + "." + resourceName + "\\W");
		this.resourceName = resourceName;
		this.resourceType = resourceType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resourceName == null) ? 0 : resourceName.hashCode());
		result = prime * result + ((resourceType == null) ? 0 : resourceType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Resource))
			return false;
		Resource other = (Resource) obj;
		if (resourceName == null) {
			if (other.resourceName != null)
				return false;
		} else if (!resourceName.equals(other.resourceName))
			return false;
		if (resourceType == null) {
			if (other.resourceType != null)
				return false;
		} else if (!resourceType.equals(other.resourceType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return resourceType + "." + resourceName;
	}
}