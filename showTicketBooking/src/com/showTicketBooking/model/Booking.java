package com.showTicketBooking.model;

import java.time.LocalTime;

public class Booking {
	int id;
    String user;
    String showName;
    LocalTime startTime;
    int numPersons;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getShowName() {
		return showName;
	}
	public void setShowName(String showName) {
		this.showName = showName;
	}
	public LocalTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}
	public int getNumPersons() {
		return numPersons;
	}
	public void setNumPersons(int numPersons) {
		this.numPersons = numPersons;
	}
//	@Override
//	public String toString() {
//		return "Booking [id=" + id + ", user=" + user + ", showName=" + showName + ", startTime=" + startTime
//				+ ", numPersons=" + numPersons + "]";
//	}
	
	@Override
	public String toString() {
	    return String.format(
	        "Booking ID: %-3d | User: %-10s | Show: %-15s | Time: %-5s | Persons: %d",
	        id, user, showName, startTime, numPersons
	    );
	}

}
