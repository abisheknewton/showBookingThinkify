package com.showTicketBooking;

import java.util.Scanner;

import com.showTicketBooking.service.serviceImpl.BookingServiceImpl;
import com.showTicketBooking.service.serviceImpl.ShowServiceImpl;

public class MainStarterMethod {

	public static void main(String[] args) {

		ShowServiceImpl showService = new ShowServiceImpl();
		BookingServiceImpl bookingService = new BookingServiceImpl();

		while (true) {
			try {
				String response = "";
				showOptions();
				Scanner sc = new Scanner(System.in);
				int choice = sc.nextInt();
				switch (choice) {
				case 1:
					response = showService.registerShow();
					break;
				case 2:
					response = showService.onboardShowSlots();
					break;
				case 3:
					showService.viewAvailableShowsByGenre();
					break;
				case 4:
					response = bookingService.addBooking();
					break;
				case 5:
					response = bookingService.cancelBooking();
					break;
				case 6:
					bookingService.viewAllBookings();
					break;
				case 7:
					showService.viewTrendingShow();
					break;
				case 8:
					showService.viewRegisteredShows();
					break;
				case 0:
					System.out.println("Exiting...");
					return;
				default:
					System.out.println("Enter a valid option ");
				}
				System.out.println(response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	static void showOptions() {
		System.out.println("\n-------------------\tOPTIONS\t  -------------------");
		System.out.println("1. Register show");
		System.out.println("2. Onboard show slots");
		System.out.println("3. Show available by genre");
		System.out.println("4. Book ticket");
		System.out.println("5. Cancel booking");
		System.out.println("6. view booking");
		System.out.println("7. view trending show");
		System.out.println("8. view registered shows");
		System.out.println("0. Exit ");
		System.out.println("Enter an option number");
	}
}
