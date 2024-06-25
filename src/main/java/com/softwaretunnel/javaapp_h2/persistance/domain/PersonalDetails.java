package com.softwaretunnel.javaapp_h2.persistance.domain;

public class PersonalDetails {
	
	private int PD_ID;
	private String Address;
	private boolean havingPets;
	
	
	public int getPD_ID() {
		return PD_ID;
	}
	public void setPD_ID(int pD_ID) {
		PD_ID = pD_ID;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public boolean isHavingPets() {
		return havingPets;
	}
	public void setHavingPets(boolean havingPets) {
		this.havingPets = havingPets;
	}
	
	

}
