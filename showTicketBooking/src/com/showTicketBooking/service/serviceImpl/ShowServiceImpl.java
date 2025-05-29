package com.showTicketBooking.service.serviceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.showTicketBooking.model.Show;
import com.showTicketBooking.model.Slot;
import com.showTicketBooking.repository.InMemoryDataStructue;
import com.showTicketBooking.service.ShowService;
import static com.showTicketBooking.constants.Constants.*;

public class ShowServiceImpl implements ShowService {

	Scanner sc = new Scanner(System.in);

	@Override
	public String registerShow() {
		System.out.println("Enter show name:");
		String showName = sc.next();

		System.out.println("Enter show genre:");
		String showGenre = sc.next();

		Show newShow = new Show(showName, showGenre);
		System.out.println("Enter show slot start time - between 9 (9 AM) and 20 (8 PM):");
		int showSlot = sc.nextInt();

		if (showSlot < MIN_SHOW_TIME || showSlot > MAX_SHOW_TIME) {
			return "Show timing out of range.";
		}

		System.out.println("Enter show seating capacity:");
		int showCapacity = sc.nextInt();

		Slot newSlot = new Slot(LocalTime.of(showSlot, 0), LocalTime.of(showSlot + 1, 0), showCapacity);
		Map<LocalTime, Slot> slotMap = new HashMap<>();
		slotMap.put(LocalTime.of(showSlot, 0), newSlot);
		newShow.setSlots(slotMap);

		InMemoryDataStructue.getDbInstance().showsByName.put(showName, newShow);

		return "Show registered successfully.";
	}

	@Override
	public List<Show> viewRegisteredShows() {
		List<Show> allShows = new ArrayList<>(InMemoryDataStructue.getDbInstance().showsByName.values());

		for (Show eachShow : allShows) {
			System.out.println(eachShow);
		}
		return allShows;
	}

	@Override
	public List<Slot> viewAvailableShowSlots() {
		System.out.println("Enter show name to view available slots:");
		String showName = sc.next();

		Map<String, Show> showsByName = InMemoryDataStructue.getDbInstance().showsByName;

		List<Slot> availableSlots = new ArrayList<>();

		if (!showsByName.containsKey(showName)) {
			System.out.println("No such show found.");
			return availableSlots;
		}

		for (Slot slot : showsByName.get(showName).getSlots().values()) {
			if (slot.getBookedCount() < slot.getCapacity()) {
				availableSlots.add(slot);
			}
		}

		return availableSlots;
	}

	@Override
	public String onboardShowSlots() {
		System.out.println("Enter show name to add slot:");
		String showName = sc.next();

		Map<String, Show> showsByName = InMemoryDataStructue.getDbInstance().showsByName;

		if (!showsByName.containsKey(showName)) {
			return "Show with the given name not found - needs to be registered.";
		}

		System.out.println("Enter show slot start time - between 9 (9 AM) and 20 (8 PM):");
		int showSlot = sc.nextInt();

		if (showSlot < MIN_SHOW_TIME || showSlot > MAX_SHOW_TIME) {
			return "Show timing out of range.";
		}

		System.out.println("Enter show seating capacity:");
		int showCapacity = sc.nextInt();

		LocalTime slotStartTime = LocalTime.of(showSlot, 0);
		if (showsByName.get(showName).getSlots().containsKey(slotStartTime)) {
			return "Slot already registered for the same show.";
		}

		Slot newSlot = new Slot(slotStartTime, LocalTime.of(showSlot + 1, 0), showCapacity);
		showsByName.get(showName).getSlots().put(slotStartTime, newSlot);

		return "Slot added successfully for the given show.";
	}

	@Override
	public List<Show> viewAvailableShowsByGenre() {
		System.out.println("Enter genre name to filter:");
		String genreName = sc.next();

		Map<String, Show> showsByName = InMemoryDataStructue.getDbInstance().showsByName;
		List<Show> availableShows = new ArrayList<>();

		for (Show show : showsByName.values()) {
			if (show.getGenre().equalsIgnoreCase(genreName)) {
				boolean hasAvailableSlot = show.getSlots().values().stream()
						.anyMatch(slot -> slot.getBookedCount() < slot.getCapacity());

				if (hasAvailableSlot) {
					availableShows.add(show);
				}
			}
		}

		for (Show show : availableShows) {
			System.out.println("Show: " + show.getName());
			for (Map.Entry<LocalTime, Slot> entry : show.getSlots().entrySet()) {
				if (entry.getValue().getBookedCount() < entry.getValue().getCapacity()) {
					System.out.println("\tTime: " + entry.getKey() + ", Slot: " + entry.getValue());
				}
			}
		}
//        System.out.println(availableShows);
		return availableShows;
	}

	@Override
	public void viewTrendingShow() {
		Show trending = InMemoryDataStructue.getDbInstance().getTrendingShow();
		if (trending != null) {
			System.out.println("Trending Live Show: " + trending.getName() + " | Genre: " + trending.getGenre()
					+ " | Total Tickets Booked: " + trending.getTotalTicketsBooked());
		} else {
			System.out.println("No trending show yet.");
		}
	}
}