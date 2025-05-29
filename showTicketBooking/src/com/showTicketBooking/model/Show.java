package com.showTicketBooking.model;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Show {
	private String name;
	private String genre;
	private Map<LocalTime, Slot> slots;
	private int totalTicketsBooked;

	public int getTotalTicketsBooked() {
		return totalTicketsBooked;
	}

	public void setTotalTicketsBooked(int totalTicketsBooked) {
		this.totalTicketsBooked = totalTicketsBooked;
	}

	public Show(String name, String genre) {
		this.name = name;
		this.genre = genre;
		this.slots = new HashMap<>();
	}

	public String getName() {
		return name;
	}

	public String getGenre() {
		return genre;
	}

	public Map<LocalTime, Slot> getSlots() {
		return slots;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setSlots(Map<LocalTime, Slot> slots) {
		this.slots = slots;
	}

	public void addSlot(LocalTime time, Slot slot) {
		this.slots.put(time, slot);
	}

//    @Override
//    public String toString() {
//        return "Show{" +
//                "name='" + name + '\'' +
//                ", genre='" + genre + '\'' +
//                ", slots=" + slots +
//                '}';
//    }

//	@Override
//	public String toString() {
//	    StringBuilder sb = new StringBuilder();
//	    sb.append(String.format("Show Name: %s | Genre: %s%n", name, genre));
//	    sb.append("Slots:\n");
//	    for (Map.Entry<LocalTime, Slot> entry : slots.entrySet()) {
//	        sb.append("  ").append(entry.getValue().toString()).append("\n");
//	    }
//	    return sb.toString();
//	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("Show Name: %s | Genre: %s%n", name, genre));
		sb.append("Slots:\n");

		slots.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.forEach(entry -> sb.append("  ").append(entry.getValue()).append("\n"));

		return sb.toString();
	}

}