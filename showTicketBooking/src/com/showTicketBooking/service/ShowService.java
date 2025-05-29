package com.showTicketBooking.service;

import java.util.List;

import com.showTicketBooking.model.Show;
import com.showTicketBooking.model.Slot;

public interface ShowService {
	public String registerShow();
	public List<Show> viewRegisteredShows();
	public List<Show> viewAvailableShowsByGenre();
	public List<Slot> viewAvailableShowSlots();
	public String onboardShowSlots();
	public void viewTrendingShow();
}
