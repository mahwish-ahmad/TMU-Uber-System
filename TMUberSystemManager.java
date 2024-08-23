// Name: Mahwish Ahmad 
// Student Number: 501242817

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.TreeMap;

/*
 * 
 * This class contains the main logic of the system.
 * 
 *  It keeps track of all users, drivers and service requests (RIDE or DELIVERY)
 * 
 */
public class TMUberSystemManager
{
  private TreeMap<String, User> users; // Initialize the users TreeMap (because we want to print users in order based on their IDs)
  private ArrayList<Driver> drivers; // Initialize the drivers Array List
  private Queue <TMUberService>[] serviceRequests; // Initilize the Array of Queues which hold TMUberService objects
  private ArrayList<User> userList; // Initialize the users list 

  public double totalRevenue; // Total revenues accumulated via rides and deliveries
  
  // Rates per city block
  private static final double DELIVERYRATE = 1.2;
  private static final double RIDERATE = 1.5;
  // Portion of a ride/delivery cost paid to the driver
  private static final double PAYRATE = 0.1;

  //These variables are used to generate user account and driver ids
  int userAccountId = 900;
  int driverId = 700;

  public TMUberSystemManager()
  {
    users = new TreeMap<>(); // Set users to a new TreeMap
    drivers = new ArrayList<Driver>(); // Set drivers to a new Array List
    serviceRequests = new LinkedList[4]; // Set service requests to a linked list of 4 queues
    userList = new ArrayList<>(users.values()); // Set the user list to a new array list of users using the map
    
    // Populate the service request queues
    for (int i = 0; i < 4; i++)
    {
      serviceRequests[i] = new LinkedList<TMUberService>();
    }

    totalRevenue = 0; 
  }
  
  // Given user account id, find user in users Map
  public User getUser(String accountId)
  {
    return users.get(accountId); // Use the map to find the user
  }


  // Given driver ID, find driver in list of drivers
  public Driver findDriver(String driverID)
  {
    for (Driver driver: drivers)
    {
      if (driver.getId().equals(driverID))
      {
        return driver;
      }
    }
    return null;
  }

  // Given a user Array List, set the users
  public void setUsers(ArrayList<User> userList)
  {
    users.clear(); // Clear the map first
    for (User user: userList) // Iterate through the list to insert values and keys into map
    {
      users.put(user.getAccountId(), user);
    }
  }

  // Given a list of drivers, set the drivers list
  public void setDrivers(ArrayList<Driver> drivers)
  {
    this.drivers = drivers;
  }
  
  // Check for duplicate user
  private boolean userExists(User user)
  {
    // Fill in the code
    for (User other: users.values()) {
      if (other.equals(user)) {
        return true;
      }
    }
    return false;
  }
  
 // Check for duplicate driver
 private boolean driverExists(Driver driver)
 {
   // Fill in the code
   for (Driver d: drivers) {
      if (d.equals(driver)) {
        return true;
      }
   }
   return false;
 }
  
  // Given a user, check if user ride/delivery request already exists in service requests
  private boolean existingRequest(TMUberService req)
  {
    // Use a nested for loop to first iterate through the array and then the actual service objects
    for (Queue<TMUberService> queue: serviceRequests)
    {
      for (TMUberService service: queue)
      {
        if (service.getUser().equals(req.getUser()))
        {
          return true; // Return true if we find the same user with the request
        }
      }
    }
    return false;
  }

  // Calculate the cost of a ride or of a delivery based on distance 
  private double getDeliveryCost(int distance)
  {
    return distance * DELIVERYRATE;
  }

  private double getRideCost(int distance)
  {
    return distance * RIDERATE;
  }

  // Print Information (printInfo()) about all registered users in the system
  public void listAllUsers()
  {
    System.out.println();
    
    int index = 1;
    for (User user: users.values()) // Iterate through the map using users.values() to get ahold of the user objects
    {
      System.out.printf("%-2s. ", index++);
      user.printInfo();
      System.out.println(); 
    }
  }

  // Print Information (printInfo()) about all registered drivers in the system
  public void listAllDrivers()
  {
    System.out.println();

    for (int i = 0; i < drivers.size(); i++) {
      int index = i + 1;
      System.out.printf("%-2s. ", index);
      drivers.get(i).printInfo();
      System.out.println();
    }
  }

  // Print Information (printInfo()) about all current service requests
  public void listAllServiceRequests()
  {
    System.out.println();

    for (int i = 0; i < serviceRequests.length; i++) { // Iterate through the size of the array

      System.out.println("ZONE " + i); // Print the zone along with the zone number
      System.out.println("======");

      Queue <TMUberService> queue = serviceRequests[i]; // Make a Queue which keeps track of the current queue

      int index = 1;
      for (TMUberService service: queue) // Iterate through all the service objects in that queue
      {
        System.out.printf("%-2s. " + "-------------------------------------------------------------------------------", index++);
        service.printInfo();
        System.out.println();
      }
    }
  }

  // Add a new user to the system
  public void registerNewUser(String name, String address, double wallet)
  {
    if (name.equals("") || name == null) { // Check if the name is an empty string or null
      throw new IllegalArgumentException("Invalid User Name"); 
    }
    if (CityMap.validAddress(address) == false) { // Check if the address provided is not valid
      throw new IllegalArgumentException("Invalid User Address");
    }
    if (wallet < 0) { // Check if the user enters a negative value
      throw new IllegalArgumentException("Invalid Money in Wallet");
    }
    
    ArrayList<User> userList = new ArrayList<>(users.values());
    String accountID = TMUberRegistered.generateUserAccountId(userList); // Generate the user ID
    User user = new User(accountID, name, address, wallet); // Make a new user with all its parameters

    if (userExists(user)) { 
      throw new IllegalArgumentException("User Already Exists in System"); // Throw the exception again
    }

    users.put(accountID, user); // Add the user to the map
  }

  // Add a new driver to the system
  public void registerNewDriver(String name, String carModel, String carLicencePlate, String address) 
  {
    if (name.equals("") || name == null) { // Check if the name is an empty string or null
      throw new IllegalArgumentException("Invalid Driver Name"); 
    }
    if (carModel.equals("") || carModel == null) { // Check if the model is an empty string or null
      throw new IllegalArgumentException("Illegal Car Model");
    }
    if (carLicencePlate.equals("") || carLicencePlate == null) { // Check if the license is an empty string or null
      throw new IllegalArgumentException("Invalid Car Licence Plate");
    }
    if (CityMap.validAddress(address) == false)
    {
      throw new InvalidAddressException("Invalid Address"); // Throw a custom exception
    }

    int zone = CityMap.getCityZone(address); // Get the city zone based on the given address

    String driverID = TMUberRegistered.generateDriverId(drivers); // Generate the driver ID

    Driver driver = new Driver(driverID, name, carModel, carLicencePlate, address, zone);
    if (driverExists(driver)) {
      throw new DriverExistsException("Driver Already Exists in System"); // Throw a custom Driver Exists Exception
    }

    drivers.add(driver); // Add the new driver
  }

  // Request a ride. User wallet will be reduced when drop off happens
  public void requestRide(String accountId, String from, String to) 
  {
    User user = getUser(accountId); // Use the account id to find the user object in the list of users

    if (user == null) { 
      throw new UserAccountNotFoundException("User Account Not Found"); // Throw a custom exception
    }
    if (CityMap.validAddress(from) == false || CityMap.validAddress(to) == false) { // Check if both addresses are valid 
      throw new InvalidAddressException("Invalid Address"); // Throw a custom exception
    }
    
    int distance = CityMap.getDistance(from, to); // Get the distance between the two addresses
    if (distance <= 1) { 
      throw new InsufficientDistanceException("Insufficient Travel Distance"); // Throw a custom exception
    }

    double rideCost = getRideCost(distance); // get the cost of the ride
    if (user.getWallet() < rideCost) {
      throw new InsufficientFundsException("User Does Not Have Enough Funds"); 
    }

    TMUberService ride = new TMUberRide(from, to, user, distance, rideCost); // make a new uber ride object

    if (existingRequest(ride)) { // Check if there is an existing ride request
      throw new ExistingRideRequest("User Already Has Ride Request");
    }

    int zone = CityMap.getCityZone(from); // Get the zone
    serviceRequests[zone].add(ride); // Add the request to the queue for that zone
    user.addRide(); // Add the ride for the user
  }

  // Request a food delivery. User wallet will be reduced when drop off happens
  public void requestDelivery(String accountId, String from, String to, String restaurant, String foodOrderId)
  {
    User user = getUser(accountId); 
    if (user == null) { 
      throw new UserAccountNotFoundException("User Account Not Found");
    }
    if (CityMap.validAddress(from) == false || CityMap.validAddress(to) == false) { 
      throw new InvalidAddressException("Invalid Address");
    }
    int distance = CityMap.getDistance(from, to); 
    if (distance <= 1) { // Check if the distance is less than or equal to 1
      throw new InsufficientDistanceException("Insufficient Travel Distance");
    }
      
    if (user.getWallet() < getDeliveryCost(distance)) {
      throw new InsufficientFundsException("User Does Not Have Enough Funds");
    }

    if (restaurant.equals("") || restaurant == null) { // If the restaurant is an empty string 
      throw new IllegalArgumentException("Invalid Restaurant Name");
    }

    double deliveryCost = getDeliveryCost(distance); // Get the delivery cost based on the distance of travel 

    // Make a new delivery object with the appropriate parameters
    TMUberService delivery = new TMUberDelivery(from, to, user, distance, deliveryCost, restaurant, foodOrderId); 
    
    if (existingRequest(delivery)) { 
      throw new ExistingDeliveryRequest("User Already Has Delivery Request at Restaurant with this Food Order");
    }

    int zone = CityMap.getCityZone(from); // Get the zone
    serviceRequests[zone].add(delivery); // Add the request to the queue for that zone

    user.addDelivery(); // Add the delivery for the user
  }

  // Cancel an existing service request. 
  // parameter int request is the index in the serviceRequests array list
  public void cancelServiceRequest(int request, int zone)
  {
    if (zone < 0 || zone > 3) // Check if the zone is invalid
    {
      throw new IllegalArgumentException("Invalid Zone #");
    }

    if (request <= 0 || request > serviceRequests[zone].size()) { // Check for invalid request number
      throw new IllegalArgumentException("Invalid Request #");
    }

    Queue<TMUberService> zoneQueue = serviceRequests[zone]; // Get the queue based on the zone number provided

    if (zoneQueue.isEmpty()) // Check if there's no requests in the zone
    {
      throw new ZoneEmptyException("Zone " + zone + " is Empty");
    }

    Iterator<TMUberService> iterator = zoneQueue.iterator(); // Use an iterator on the Queue

    int i = 1; // Initialize the index to 1
    while (iterator.hasNext()) 
    {
      TMUberService service = iterator.next(); // Get the next element from the iterator
      if (i == request) // If we have reached the request number
      {
        iterator.remove(); // Remove the request from the queue
        User user = service.getUser(); // Get the user associated with the request
        String serviceType = service.getServiceType(); // Get the service type 
    
        if (serviceType.equals("RIDE")) { // Check if the type is RIDE
          user.setRides(user.getRides() - 1); // Set the ride for the user
        }
        else { // Check if the type is DELIVERY
          user.setDeliveries(user.getDeliveries() - 1); // Set the delivery for the user
        }
        return; // When we have found the request and removed it, exit
      }
      i++; // Increment the index
    }
  }
  
  // Drop off a ride or a delivery. This completes a service.
  public void dropOff(String driverID) 
  {
    Driver driver = findDriver(driverID);

    if (driver == null) { // if the id is not found, throw the new exception
      throw new DriverNotFoundException("Driver "+ driverID + " is not found in our system");
    } 

    if (driver.getStatus() != Driver.Status.DRIVING)
    {
      throw new InvalidStateException("Driver " + driverID + " is not driving.");
    }

    TMUberService service = driver.getService(); // get the service based on the driver
    User user = service.getUser();
    String serviceType = service.getServiceType(); // get the service type based on the service

    double cost = service.getCost(); // get the cost of the service
    int distance = service.getDistance(); // get the distance of the request

    if (serviceType.equals("RIDE")) // check if the type is RIDE
    { 
      cost += getRideCost(distance); // use the method getRideCost to add the cost of the ride of the cost of the service
    }
    else 
    {
      cost += getDeliveryCost(distance); // add the cost of the delivery to the cost of the service
    }

    totalRevenue += cost; // Total up the revenue by adding cost

    double fee = cost * PAYRATE; // Calculate driver fee

    driver.setWallet(driver.getWallet() + fee); // Pay the driver / Set their wallet

    totalRevenue -= fee; // Take away fee from total revenue

    driver.setStatus(Driver.Status.AVAILABLE); // Set the driver status to AVAILABLE
    driver.setService(null); // Set service to nothing
    driver.setAddress(service.getTo()); // Set To address
    int zoneTo = CityMap.getCityZone(service.getTo()); // Get the zone based on the destination address
    driver.setZone(zoneTo); // Set the zone for the driver

    user.payForService(cost); // user pays for the cost of the service

    int zoneFrom = CityMap.getCityZone(service.getFrom()); // Get the zone based on the from address
    serviceRequests[zoneFrom].remove(service); // Remove it from the queue
  }

  // Driver drives (if available) to a new address - Note that this may change the zone as well
  public void driveTo(String driverID, String address) 
  {
    Driver driver = findDriver(driverID); // Find the driver using the ID

    if (driver == null)
    {
      throw new DriverNotFoundException("Driver "+ driverID + " is not found in our system");
    }

    if (driver.getStatus() != Driver.Status.AVAILABLE)
    {
      throw new InvalidStateException("Driver " + driverID + " is not available.");
    }

    int zone = CityMap.getCityZone(address); // Get the zone based on the address
    driver.setAddress(address); // Set the address and the zone
    driver.setZone(zone);
  }

  // Driver picks up the user and driving to the "from" address
  public void pickUp(String driverID)
  {
    Driver driver = findDriver(driverID); // Find the driver using the ID

    if (driver == null)
    {
      throw new DriverNotFoundException("Driver "+ driverID + " is not found in our system");
    }

    int zone = CityMap.getCityZone(driver.getAddress()); // Get the zone based on the driver's location address
    Queue<TMUberService> zoneQueue = serviceRequests[zone]; // Get the queue for the zone

    if (zoneQueue.isEmpty())
    {
      throw new ZoneEmptyException("No Service Request in Zone " + zone); // Check if zone is empty
    }

    TMUberService service = zoneQueue.poll(); // Removes the very top service

    // Set the service, status and the address to where the driver is picking up
    driver.setService(service); 
    driver.setStatus(Driver.Status.DRIVING);
    driver.setAddress(service.getFrom());
  }

  // Sort users by name
  // Then list all users
  public void sortByUserName()
  {
    userList = new ArrayList<>(users.values()); // Set user list to be an array list of users
    Collections.sort(userList, new NameComparator()); // Sort the users using the list and the name comparator

    // Print out the sorted users by name
    System.out.println();
    
    int index = 1;
    for (User user: userList) // Iterate through the list
    {
      System.out.printf("%-2s. ", index++);
      user.printInfo();
      System.out.println(); 
    }
  }

  // Helper class for method sortByUserName
  private class NameComparator implements Comparator<User>  
  {
    public int compare(User user1, User user2) {
        return user1.getName().compareTo(user2.getName());
    }
  }

  // Sort users by number amount in wallet
  // Then list all users
  public void sortByWallet()
  {
    userList = new ArrayList<>(users.values()); // Set user list to be an array list of users
    Collections.sort(userList, new UserWalletComparator()); // Sort the users using the list and the wallet comparator

    // Print the sorted users by wallet
    System.out.println();
    
    int index = 1;
    for (User user: userList) // Iterate through the list
    {
      System.out.printf("%-2s. ", index++);
      user.printInfo();
      System.out.println(); 
    }
  }

  // Helper class for use by sortByWallet
  private class UserWalletComparator implements Comparator<User>
  {
    public int compare(User user1, User user2) {
      return Double.compare(user1.getWallet(), user2.getWallet());
    }
  }

  // Sort trips (rides or deliveries) by distance
  // Then list all current service requests
  public void sortByDistance() 
  {
    for (Queue<TMUberService> queue: serviceRequests) // Iterate through the service requests
    {
      ArrayList<TMUberService> serviceList = new ArrayList<>(queue); // Make an array list for the current zone
      Collections.sort(serviceList, new DistanceComparator()); // Sort the list using the distance comparator
      queue.clear(); // Clear the queue but then add all the sorted elements to the queue
      queue.addAll(serviceList);
    }
    listAllServiceRequests(); // List the requests
  }

  // Helper class for method sortByDistance
  private class DistanceComparator implements Comparator<TMUberService>
  {
    public int compare(TMUberService service1, TMUberService service2) {
      int distance1 = CityMap.getDistance(service1.getFrom(), service1.getTo());
      int distance2 = CityMap.getDistance(service2.getFrom(), service2.getTo());
      return Integer.compare(distance1, distance2);
    }
  }
}

// All the necessary custom exceptions 
class InvalidStateException extends RuntimeException
{
  public InvalidStateException() {}

  public InvalidStateException(String message)
  {
    super(message);
  }
}
class InvalidAddressException extends RuntimeException
{
  public InvalidAddressException() {}

  public InvalidAddressException(String message)
  {
    super(message);
  }
}
class UserAccountNotFoundException extends RuntimeException
{
  public UserAccountNotFoundException() {}

  public UserAccountNotFoundException(String message)
  {
    super(message);
  }
}
class DriverNotFoundException extends RuntimeException
{
  public DriverNotFoundException() {}

  public DriverNotFoundException(String message)
  {
    super(message);
  }
}
class DriverExistsException extends RuntimeException
{
  public DriverExistsException() {}

  public DriverExistsException(String message)
  {
    super(message);
  }
}
class ExistingRideRequest extends RuntimeException
{
  public ExistingRideRequest() {}

  public ExistingRideRequest(String message)
  {
    super(message);
  }
}
class ExistingDeliveryRequest extends RuntimeException
{
  public ExistingDeliveryRequest() {}

  public ExistingDeliveryRequest(String message)
  {
    super(message);
  }
}
class InsufficientDistanceException extends RuntimeException
{
  public InsufficientDistanceException() {}

  public InsufficientDistanceException(String message)
  {
    super(message);
  }
}
class InsufficientFundsException extends RuntimeException
{
  public InsufficientFundsException() {}

  public InsufficientFundsException(String message)
  {
    super(message);
  }
}
class NoAvailableDriverException extends RuntimeException
{
  public NoAvailableDriverException() {}

  public NoAvailableDriverException(String message)
  {
    super(message);
  }
}
class ZoneEmptyException extends RuntimeException
{
  public ZoneEmptyException() {}
  
  public ZoneEmptyException(String message)
  {
    super(message);
  }
}