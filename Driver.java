// Name: Mahwish Ahmad 
// Student Number: 501242817

/*
 * 
 * This class simulates a car driver in a simple uber app 
 * 
 * Everything has been done for you except the equals() method
 */

public class Driver
{
  private String id;
  private String name;
  private String carModel;
  private String licensePlate;
  private double wallet;
  private String type;
  private TMUberService service;
  private String address;
  private int zone;
  
  public static enum Status {AVAILABLE, DRIVING};
  private Status status;

  public Driver(String id, String name, String carModel, String licensePlate, String address, int zone)
  {
    this.id = id;
    this.name = name;
    this.carModel = carModel;
    this.licensePlate = licensePlate;
    this.status = Status.AVAILABLE;
    this.wallet = 0;
    this.type = "";

    // Initiliaze the new variables
    this.address = address; 
    this.zone = zone;
  }
  // Print Information about a driver
  public void printInfo()
  {
    System.out.printf("Id: %-3s Name: %-15s Car Model: %-15s License Plate: %-10s Wallet: %2.2f", 
                      id, name, carModel, licensePlate, wallet);
    System.out.println();
    System.out.printf("Status: %-10s Address: %-30s Zone %-2s", status, address, zone);
    if (status == Status.DRIVING) // Check if the driver is driving
    {
      System.out.println(); // Go to the next line
      // Print info about where driver is going to by printing the from and to addresses
      System.out.printf("From: %-15s To: %-15s", service.getFrom(), service.getTo());
    }
  }
  // Getters and Setters
  public String getType()
  {
    return type;
  }
  public void setType(String type)
  {
    this.type = type;
  }
  public String getId()
  {
    return id;
  }
  public void setId(String id)
  {
    this.id = id;
  }
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public String getCarModel()
  {
    return carModel;
  }
  public void setCarModel(String carModel)
  {
    this.carModel = carModel;
  }
  public String getLicensePlate()
  {
    return licensePlate;
  }
  public void setLicensePlate(String licensePlate)
  {
    this.licensePlate = licensePlate;
  }
  public Status getStatus()
  {
    return status;
  }
  public void setStatus(Status status)
  {
    this.status = status;
  }
  public double getWallet()
  {
    return wallet;
  }
  public void setWallet(double wallet)
  {
    this.wallet = wallet;
  }

  // Create additional methods based on the new instance variables 

  public void setService(TMUberService service)
  {
    this.service = service;
  }
  public TMUberService getService()
  {
    return service;
  }
  public void setAddress(String address)
  {
    this.address = address;
  }
  public String getAddress()
  {
    return address;
  }
  public void setZone(int zone)
  {
    this.zone = zone;
  }
  public int getZone()
  {
    return zone;
  }

  /*
   * Two drivers are equal if they have the same name and license plates.
   * This method is overriding the inherited method in superclass Object
   * 
   * Fill in the code 
   */
  public boolean equals(Object other)
  {
    if (!(other instanceof Driver)) {
      return false; // check if the object is an instance of Driver, return false if it isn't
    }
    Driver newDriver = (Driver)(other); // Cast the other object to a driver object
    if (this.name.equalsIgnoreCase(newDriver.name) && this.licensePlate.equalsIgnoreCase(newDriver.licensePlate)) {
      return true; // return true is the drivers have the same name and license plates
    }
    return false; // else return false
  }
  
  // A driver earns a fee for every ride or delivery
  public void pay(double fee)
  {
    wallet += fee;
  }
}
