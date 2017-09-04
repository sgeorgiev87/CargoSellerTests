package com.cargoseller.tests.enums;

public enum Username {
	SHIPPER("ShipperTest"),
	CARRIER("CarrierTest");
	
private String username;
	
	private Username(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
	
}
