package com.showTicketBooking.service;

import java.time.LocalTime;

public interface SlotObserver {
	void update(String showName, LocalTime slotTime);
}
