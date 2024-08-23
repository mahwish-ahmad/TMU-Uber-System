// Name: Mahwish Ahmad 
// Student Number: 501242817

/*
 * 
 * This class simulates a food delivery service for a simple Uber app
 * 
 * A TMUberDelivery is-a TMUberService with some extra functionality
 */
public class TMUberDelivery extends TMUberService
{
  public static final String TYPENAME = "DELIVERY";
 
  private String restaurant; 
  private String foodOrderId;
   
   // Constructor to initialize all inherited and new instance variables 
   // REMOVE Driver driver
  public TMUberDelivery(String from, String to, User user, int distance, double cost,
                        String restaurant, String order)
  {
    // Fill in the code - make use of the super method
    super(from, to, user, distance, cost, TYPENAME); // Inherit all the previously defined variables from TMUberService
    this.restaurant = restaurant; 
    this.foodOrderId = order; 
  }
 
  
  public String getServiceType()
  {
    return TYPENAME;
  }
  public String getRestaurant()
  {
    return restaurant;
  }
  public void setRestaurant(String restaurant)
  {
    this.restaurant = restaurant;
  }
  public String getFoodOrderId()
  {
    return foodOrderId;
  }
  public void setFoodOrderId(String foodOrderId)
  {
    this.foodOrderId = foodOrderId;
  }
  /*
   * Two Delivery Requests are equal if they are equal in terms of TMUberServiceRequest
   * and the restaurant and food order id are the same  
   */
  public boolean equals(Object other)
  {
    // First check to see if other is a Delivery type
    // Cast other to a TMUService reference and check type
    // If not a delivery, return false
    
    if (!(other instanceof TMUberDelivery)) {
      return false; // if the other object is not a delivery type, return false
    }

    TMUberDelivery delivery = (TMUberDelivery) (other); // cast the other object to a delivery object

    if (!(this.getServiceType().equals("DELIVERY")) || 
    !(this.getRestaurant().equals(delivery.getRestaurant())) || 
    !(this.getFoodOrderId().equals(delivery.getFoodOrderId()))) {
      return false; // if the type, restaurant, food order if are not equal, return false
    }

    return true; 
  }
  /*
   * Print Information about a Delivery Request
   */
  public void printInfo()
  {
    // Fill in the code
    // Use inheritance to first print info about a basic service request

    super.printInfo(); // get the message from the superclass

    // Then print specific subclass info
    System.out.println(); // move to the next line
    System.out.printf("Restaurant: %-15s Food Order #:  %-5s", restaurant, foodOrderId); // format the restaurant the id
  }
}
