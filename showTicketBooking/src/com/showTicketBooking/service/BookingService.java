package com.showTicketBooking.service;

import java.util.List;

import com.showTicketBooking.model.Booking;

public interface BookingService {
	public List<Booking> viewAllBookings();

	public String addBooking();

	public String cancelBooking();

}
