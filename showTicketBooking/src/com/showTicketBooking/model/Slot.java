package com.showTicketBooking.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Slot {
	private LocalTime startTime;
	private LocalTime endTime;
	private int capacity;
	private int bookedCount;
	private List<Booking> bookings;
	private Queue<Booking> waitlist;

	public Slot(LocalTime startTime, LocalTime endTime, int capacity) {
		this.startTime = startTime;
		this.endTime = endTime;
		this.capacity = capacity;
		this.bookedCount = 0;
		this.bookings = new ArrayList<>();
		this.waitlist = new LinkedList<>();
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getBookedCount() {
		return bookedCount;
	}

	public List<Booking> getBookings() {
		return bookings;
	}

	public Queue<Booking> getWaitlist() {
		return waitlist;
	}

//    public boolean isAvailable(int persons) {
//        return (capacity - bookedCount) >= persons;
//    }

	public void addBooking(Booking booking) {
		bookings.add(booking);
		bookedCount += booking.getNumPersons();
	}

	public void cancelBooking(Booking booking) {
		bookings.remove(booking);
		bookedCount -= booking.getNumPersons();
	}

	public void addToWaitlist(Booking booking) {
		waitlist.offer(booking);
	}

//    @Override
//    public String toString() {
//        return "Slot{" +
//                "start=" + startTime +
//                ", end=" + endTime +
//                ", capacity=" + capacity +
//                ", booked=" + bookedCount +
//                ", waitlist=" + waitlist +
//                '}';
//    }

	@Override
	public String toString() {
		return String.format("Slot: %s - %s | Capacity: %d | Booked: %d | Waitlist: %d", startTime, endTime, capacity,
				bookedCount, waitlist != null ? waitlist.size() : 0);
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setBookedCount(int bookedCount) {
		this.bookedCount = bookedCount;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}

	public void setWaitlist(Queue<Booking> waitlist) {
		this.waitlist = waitlist;
	}
}
