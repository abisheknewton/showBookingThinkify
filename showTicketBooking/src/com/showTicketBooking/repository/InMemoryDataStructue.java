package com.showTicketBooking.repository;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import com.showTicketBooking.model.Booking;
import com.showTicketBooking.model.Show;

public class InMemoryDataStructue {

	private static InMemoryDataStructue DbInstance = null;

	public static Map<String, Show> showsByName = new HashMap<>();
	public static Map<Integer, Booking> bookingsById = new HashMap<>();
	public static Map<String, List<Booking>> userBookings = new HashMap<>();
	public static Map<String, Integer> trendingBookingCounter = new HashMap<>();
	public static Queue<Booking> bookingQueue = new LinkedList<>();
	private Show trendingShow;

	private int bookingIdCounter = 1;

	public static InMemoryDataStructue getDbInstance() {
		if (DbInstance == null) {
			DbInstance = new InMemoryDataStructue();
		}
		return DbInstance;
	}

	public int getNextBookingId() {
		return bookingIdCounter++;
	}

	public Show getTrendingShow() {
		return trendingShow;
	}

	public void updateTrendingShow(Show show) {
		if (trendingShow == null || show.getTotalTicketsBooked() > trendingShow.getTotalTicketsBooked()) {
			trendingShow = show;
		}
	}

}
