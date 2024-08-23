// Name: Mahwish Ahmad 
// Student Number: 501242817

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple Command-line based Uber App 

// This system supports "ride sharing" service and a delivery service

public class TMUberUI
{
  public static void main(String[] args)
  {
    // Create the System Manager - the main system code is in here 

    TMUberSystemManager tmuber = new TMUberSystemManager();
    
    Scanner scanner = new Scanner(System.in);
    System.out.print(">");

    // Process keyboard actions
    while (scanner.hasNextLine())
    {
      try
      {
        String action = scanner.nextLine();

        if (action == null || action.equals("")) 
        {
          System.out.print("\n>");
          continue;
        }
        // Quit the App
        else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
          return;

        // Print all the registered drivers
        else if (action.equalsIgnoreCase("DRIVERS"))  // List all drivers
        {
          tmuber.listAllDrivers(); 
        }
        // Print all the registered users
        else if (action.equalsIgnoreCase("USERS"))  // List all users
        {
          tmuber.listAllUsers(); 
        }
        // Print all current ride requests or delivery requests
        else if (action.equalsIgnoreCase("REQUESTS"))  // List all requests
        {
          tmuber.listAllServiceRequests(); 
        }
        // Register a new driver
        else if (action.equalsIgnoreCase("REGDRIVER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String carModel = "";
          System.out.print("Car Model: ");
          if (scanner.hasNextLine())
          {
            carModel = scanner.nextLine();
          }
          String license = "";
          System.out.print("Car License: ");
          if (scanner.hasNextLine())
          {
            license = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }

          tmuber.registerNewDriver(name, carModel, license, address);
          System.out.printf("Driver: %-15s Car Model: %-15s License Plate: %-10s", name, carModel, license);
          
        }
        // Register a new user
        else if (action.equalsIgnoreCase("REGUSER")) 
        {
          String name = "";
          System.out.print("Name: ");
          if (scanner.hasNextLine())
          {
            name = scanner.nextLine();
          }
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          double wallet = 0.0;
          System.out.print("Wallet: ");
          if (scanner.hasNextDouble())
          {
            wallet = scanner.nextDouble();
            scanner.nextLine(); // consume nl!! Only needed when mixing strings and int/double
          }

          tmuber.registerNewUser(name, address, wallet);
          System.out.printf("User: %-15s Address: %-15s Wallet: %2.2f", name, address, wallet);
        }
        // Request a ride
        else if (action.equalsIgnoreCase("REQRIDE")) 
        {
          // Get the following information from the user (on separate lines)
          // Then use the TMUberSystemManager requestRide() method properly to make a ride request
          // "User Account Id: "      (string)
          // "From Address: "         (string)
          // "To Address: "           (string)
  
          String id = "";
          System.out.println("User Account Id: ");
          if (scanner.hasNextLine()) {
            id = scanner.nextLine();
          }
  
          String fromAdd = "";
          System.out.println("From Address: ");
          if (scanner.hasNextLine()) {
            fromAdd = scanner.nextLine();
          }
  
          String toAdd = "";
          System.out.println("To Address: ");
          if (scanner.hasNextLine()) {
            toAdd = scanner.nextLine();
          }

          tmuber.requestRide(id, fromAdd, toAdd); // Call the request ride method
          String name = tmuber.getUser(id).getName(); // Get the name of the user using their ID

          System.out.printf("RIDE for: %-15s From Address: %-15s To Address: %-15s", name, fromAdd, toAdd);
        }
  
        // Request a food delivery
        else if (action.equalsIgnoreCase("REQDLVY")) 
        {
          // Get the following information from the user (on separate lines)
          // Then use the TMUberSystemManager requestDelivery() method properly to make a ride request
          // "User Account Id: "      (string)
          // "From Address: "         (string)
          // "To Address: "           (string)
          // "Restaurant: "           (string)
          // "Food Order #: "         (string)
         
          String id = "";
          System.out.println("User Account Id: ");
          if (scanner.hasNextLine()) {
            id = scanner.nextLine();
          }
  
          String fromAdd = "";
          System.out.println("From Address: ");
          if (scanner.hasNextLine()) {
            fromAdd = scanner.nextLine();
          }
  
          String toAdd = "";
          System.out.println("To Address: ");
          if (scanner.hasNextLine()) {
            toAdd = scanner.nextLine();
          }
  
          String restaurant = "";
          System.out.println("Restaurant: ");
          if (scanner.hasNextLine()) {
            restaurant = scanner.nextLine();
          }
  
          String foodID = "";
          System.out.println("Food Order #: ");
          if (scanner.hasNextLine()) {
            foodID = scanner.nextLine(); 
          }
          
          tmuber.requestDelivery(id, fromAdd, toAdd, restaurant, foodID); // Call the request delivery method
          String name = tmuber.getUser(id).getName(); // Get the name of the user by using their ID

          System.out.printf("DELIVERY for: %-15s From Address: %-15s To Address: %-15s", name, fromAdd, toAdd);
          
        }
        // Sort users by name
        else if (action.equalsIgnoreCase("SORTBYNAME")) 
        {
          tmuber.sortByUserName();
        }
        // Sort users by number of ride they have had
        else if (action.equalsIgnoreCase("SORTBYWALLET")) 
        {
          tmuber.sortByWallet();
        }
        // Sort current service requests (ride or delivery) by distance
        else if (action.equalsIgnoreCase("SORTBYDIST")) 
        {
          tmuber.sortByDistance();
        }
  
        // Cancel a current service (ride or delivery) request
  
        else if (action.equalsIgnoreCase("CANCELREQ")) 
        {
          int request = -1;
          System.out.print("Request #: ");
          if (scanner.hasNextInt())
          {
            request = scanner.nextInt();
            scanner.nextLine(); // consume nl character
          }
          int zone = -1; // Set zone = -1
          System.out.print("Zone #: ");
          if (scanner.hasNextInt())
          {
            zone = scanner.nextInt(); // Get the zone
            scanner.nextLine();
          }

          tmuber.cancelServiceRequest(request, zone); // Call the cancel request method
  
          System.out.println("Service request #" + request + " in Zone " + zone + " Canceled");
        }
  
        else if (action.equalsIgnoreCase("DROPOFF")) 
        {
          String driverID = ""; // Set the ID to an empty string
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine())
          {
            driverID = scanner.nextLine(); // Get the ID
          }

          tmuber.dropOff(driverID); // Call the dropoff method
  
          System.out.println("Driver " + driverID + " Dropping Off");
        }
        // Get the Current Total Revenues
        else if (action.equalsIgnoreCase("REVENUES")) 
        {
          System.out.println("Total Revenue: " + tmuber.totalRevenue);
        }
        // Unit Test of Valid City Address 
        else if (action.equalsIgnoreCase("ADDR")) 
        {
          String address = "";
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine();
          }
          System.out.print(address);
          if (CityMap.validAddress(address))
            System.out.println("\nValid Address"); 
          else
            System.out.println("\nBad Address"); 
        }
        // Unit Test of CityMap Distance Method
        else if (action.equalsIgnoreCase("DIST")) 
        {
          String from = "";
          System.out.print("From: "); 
          if (scanner.hasNextLine())
          {
            from = scanner.nextLine(); // Get the from address
          }
          String to = "";
          System.out.print("To: ");
          if (scanner.hasNextLine())
          {
            to = scanner.nextLine(); // Get the to address
          }
          System.out.print("\nFrom: " + from + " To: " + to);
          System.out.println("\nDistance: " + CityMap.getDistance(from, to) + " City Blocks");
        }
        
        // PICKUP: takes a string driverId and calls a pickup() method in TMUberSystemManager. 
  
        else if (action.equalsIgnoreCase("PICKUP"))
        {
          String driverID = ""; // Set ID to an empty string
          System.out.print("Driver ID: ");
          if (scanner.hasNextLine())
          {
            driverID = scanner.nextLine(); // Get the driver's ID
          }
  
          tmuber.pickUp(driverID); // Call the pickup method in TMUberSystemManager
          int zone = tmuber.findDriver(driverID).getZone(); // Get the zone that the driver is picking up in
  
          System.out.println("Driver " + driverID + " Picking Up in Zone " + zone);
        }
  
        // LOADUSERS: takes a string filename and calls the static method loadPreregisteredUsers(). 
  
        else if (action.equalsIgnoreCase("LOADUSERS"))
        {
          String userFile = "";  // Set the file name to an empty string
          System.out.print("Users File: ");
  
          try
          {
            if (scanner.hasNextLine())
            {
              userFile = scanner.nextLine(); // Get the file
            }
    
            ArrayList<User> userList = TMUberRegistered.loadPreregisteredUsers(userFile); // Get the users array list 
            tmuber.setUsers(userList); // Set the users
            System.out.println("Users Loaded");
          }
          catch (FileNotFoundException e)
          {
            System.out.println(userFile + " Not Found"); // Catch FileNotFoundException
          }
        }
  
        // LOADDRIVERS: takes a string filename and calls the static method loadPreregisteredDrivers().
  
        else if (action.equalsIgnoreCase("LOADDRIVERS"))
        {
          String driverFile = ""; // Set the file name to an empty string
          System.out.print("Drivers File: ");
  
          try
          {
            if (scanner.hasNextLine())
            {
              driverFile = scanner.nextLine(); // Get the file
            }
  
            ArrayList<Driver> driverList = TMUberRegistered.loadPreregisteredDrivers(driverFile); // Get the drivers array list
            tmuber.setDrivers(driverList); // Set the drivers
            System.out.println("Drivers Loaded");
          }
          catch (FileNotFoundException e)
          {
            System.out.println(driverFile + " Not Found"); // Catch FileNotFoundException
          }
        }
  
        // DRIVETO: takes a driver id (string) and an address (string) and calls the TMUberSystemManager method void 
        // driveTo() – see description below.
  
        else if (action.equalsIgnoreCase("DRIVETO"))
        {
          String driverID = ""; // Set ID to an empty string
          System.out.print("Driver Account ID: ");
  
          if (scanner.hasNextLine())
          {
            driverID = scanner.nextLine(); // Get the ID
          }
  
          String address = ""; // Set the address to an empty string
          System.out.print("Address: ");
          if (scanner.hasNextLine())
          {
            address = scanner.nextLine(); // Get the address
          }

          tmuber.driveTo(driverID, address); // Call the driveTo method

          int zone = CityMap.getCityZone(address);
          System.out.println("Driver " + driverID + " Now in Zone " + zone);
  
        }
        // Test for correct zone number
        else if (action.equalsIgnoreCase("ZONE"))
        {
          String address = ""; // Set the address to an empty string
          System.out.print("Address: ");

          if (scanner.hasNextLine())
          {
            address = scanner.nextLine(); // Get the address
          }

          System.out.println("Zone: "+ CityMap.getCityZone(address)); // Print the zone number
        }
      }
      // Catch all of the exceptions with the custom exceptions 
      catch (IOException e)
      {
        System.out.println(e.getMessage());
      }
      catch (IllegalArgumentException e)
      {
        System.out.println(e.getMessage());
      }
      catch (InvalidStateException e)
      {
        System.out.println(e.getMessage());
      }
      catch (InvalidAddressException e)
      {
        System.out.println(e.getMessage());
      }
      catch (UserAccountNotFoundException e)
      {
        System.out.println(e.getMessage());
      }
      catch (DriverNotFoundException e)
      {
        System.out.println(e.getMessage());
      }
      catch (DriverExistsException e)
      {
        System.out.println(e.getMessage());
      }
      catch (ExistingRideRequest e)
      {
        System.out.println(e.getMessage());
      }
      catch (ExistingDeliveryRequest e)
      {
        System.out.println(e.getMessage());
      }
      catch (InsufficientDistanceException e)
      {
        System.out.println(e.getMessage());
      }
      catch (InsufficientFundsException e)
      {
        System.out.println(e.getMessage());
      }
      catch (NoAvailableDriverException e)
      {
        System.out.println(e.getMessage());
      }
      catch (ZoneEmptyException e)
      {
        System.out.println(e.getMessage());
      }
      System.out.print("\n>");
    }
  }
} 

