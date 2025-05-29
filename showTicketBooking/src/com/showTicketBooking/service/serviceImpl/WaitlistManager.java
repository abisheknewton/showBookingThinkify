package com.showTicketBooking.service.serviceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

import com.showTicketBooking.model.Booking;
import com.showTicketBooking.model.Show;
import com.showTicketBooking.model.Slot;
import com.showTicketBooking.repository.InMemoryDataStructue;
import com.showTicketBooking.service.SlotObserver;

public class WaitlistManager implements SlotObserver {

	@Override
	public void update(String showName, LocalTime slotTime) {

		// TODO Auto-generated method stub

		InMemoryDataStructue db = InMemoryDataStructue.getDbInstance();
		Show show = db.showsByName.get(showName);
		if (show == null)
			return;

		Slot slot = show.getSlots().get(slotTime);
		if (slot == null || slot.getWaitlist().isEmpty())
			return;

		Booking waitlistedBooking = slot.getWaitlist().peek();
		int availableSeats = slot.getCapacity() - slot.getBookedCount();
		if (waitlistedBooking.getNumPersons() <= availableSeats) {
			slot.setBookedCount(slot.getBookedCount() + waitlistedBooking.getNumPersons());
			slot.getBookings().add(waitlistedBooking);
			db.bookingsById.put(waitlistedBooking.getId(), waitlistedBooking);
			db.userBookings.computeIfAbsent(waitlistedBooking.getUser(), k -> new ArrayList<>()).add(waitlistedBooking);
			slot.getWaitlist().poll();
			db.bookingQueue.remove(waitlistedBooking);
			System.out.println("Waitlisted user " + waitlistedBooking.getUser() + " has been auto-booked for "
					+ showName + " at " + slotTime);
		}
	}
}
