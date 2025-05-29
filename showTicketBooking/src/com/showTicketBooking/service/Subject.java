package com.showTicketBooking.service;

import java.time.LocalTime;

public interface Subject {
	void notifyWaitListBooking(String showName, LocalTime slotTime);
}
