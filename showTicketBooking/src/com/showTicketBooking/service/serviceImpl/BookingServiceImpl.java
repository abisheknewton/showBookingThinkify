package com.showTicketBooking.service.serviceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.showTicketBooking.model.Booking;
import com.showTicketBooking.model.Show;
import com.showTicketBooking.model.Slot;
import com.showTicketBooking.repository.InMemoryDataStructue;
import com.showTicketBooking.service.BookingService;
import com.showTicketBooking.service.SlotObserver;
import com.showTicketBooking.service.Subject;

public class BookingServiceImpl implements BookingService, Subject {

	private final InMemoryDataStructue db = InMemoryDataStructue.getDbInstance();
	private final List<SlotObserver> observers = new ArrayList<>();
	private final Scanner scanner = new Scanner(System.in);

	public BookingServiceImpl() {
		observers.add(new WaitlistManager());
	}

	@Override
	public List<Booking> viewAllBookings() {
		System.out.println("Confirmed Bookings:");
	    db.bookingsById.values().forEach(System.out::println);

	    System.out.println("\nWaitlisted Bookings:");
	    db.bookingQueue.forEach(System.out::println);
	    
		return new ArrayList<>(db.bookingsById.values());
	}
	
	@Override
	public String addBooking() {
	    System.out.print("Enter username: ");
	    String user = scanner.next();
	    System.out.print("Enter show name: ");
	    String showName = scanner.next();
	    System.out.print("Enter show slot hour (9 to 20): ");
	    int hour = scanner.nextInt();
	    LocalTime slotTime = LocalTime.of(hour, 0);

	    Show show = db.showsByName.get(showName);
	    if (show == null) return "Show not found";
	    Slot slot = show.getSlots().get(slotTime);
	    if (slot == null) return "Slot not found";

	    List<Booking> userBookings = db.userBookings.getOrDefault(user, new ArrayList<>());
	    boolean conflictExists = userBookings.stream()
	            .anyMatch(b -> b.getStartTime().equals(slotTime));
	    if (conflictExists) {
	        return "You already have a booking in this time slot. Cannot book two shows at the same time.";
	    }

	    System.out.print("Enter number of persons: ");
	    int noOfPersons = scanner.nextInt();
	    if(noOfPersons > slot.getCapacity()) {
	    	return "Exceeding max capacity of the show";
	    }

	    if (noOfPersons <= (slot.getCapacity() - slot.getBookedCount())) {
	        Booking booking = new Booking();
	        booking.setId(db.getNextBookingId());
	        booking.setUser(user);
	        booking.setShowName(showName);
	        booking.setStartTime(slotTime);
	        booking.setNumPersons(noOfPersons);

	        slot.setBookedCount(slot.getBookedCount() + noOfPersons);
	        slot.getBookings().add(booking);
	        db.bookingsById.put(booking.getId(), booking);
	        db.userBookings.computeIfAbsent(user, k -> new ArrayList<>()).add(booking);
	        db.updateTrendingShow(show);
	        return "Booking successful";
	    } else {
	        Booking waitlistedBooking = new Booking();
	        waitlistedBooking.setId(db.getNextBookingId());
	        waitlistedBooking.setUser(user);
	        waitlistedBooking.setShowName(showName);
	        waitlistedBooking.setStartTime(slotTime);
	        waitlistedBooking.setNumPersons(noOfPersons);
	        slot.getWaitlist().offer(waitlistedBooking);
	        db.bookingQueue.add(waitlistedBooking);
	        return "Show full. Added to waitlist.";
	    }
	}

	@Override
	public String cancelBooking() {
		System.out.print("Enter booking ID to cancel: ");
		int bookingId = scanner.nextInt();

		Booking booking = db.bookingsById.remove(bookingId);
		if (booking == null)
			return "Booking not found.";

		Slot slot = db.showsByName.get(booking.getShowName()).getSlots().get(booking.getStartTime());
		if (slot != null) {
			slot.getBookings().removeIf(b -> b.getId() == bookingId);
			slot.setBookedCount(slot.getBookedCount() - booking.getNumPersons());
			notifyWaitListBooking(booking.getShowName(), booking.getStartTime());
		}
		db.userBookings.getOrDefault(booking.getUser(), new ArrayList<>()).removeIf(b -> b.getId() == bookingId);

		return "Booking cancelled.";
	}

	@Override
	public void notifyWaitListBooking(String showName, LocalTime slotTime) {
		for (SlotObserver observer : observers) {
			observer.update(showName, slotTime);
		}
	}
}
