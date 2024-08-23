// Name: Mahwish Ahmad 
// Student Number: 501242817

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class TMUberRegistered
{
    // These variables are used to generate user account and driver ids
    private static int firstUserAccountID = 900;
    private static int firstDriverId = 700;

    // Generate a new user account id
    public static String generateUserAccountId(ArrayList<User> current)
    {
        return "" + firstUserAccountID + current.size();
    }

    // Generate a new driver id
    public static String generateDriverId(ArrayList<Driver> current)
    {
        return "" + firstDriverId + current.size();
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    // The test scripts and test outputs included with the skeleton code use these
    // users and drivers below. You may want to work with these to test your code (i.e. check your output with the
    // sample output provided). 

    public static ArrayList<User> loadPreregisteredUsers(String filename) throws IOException
    {
        ArrayList<User> users = new ArrayList<>(); // Initialize the users array list
        Scanner in = new Scanner(new File(filename)); // Make a scanner and make it read the file

        while (in.hasNextLine())
        {
            String name = in.nextLine(); 
            String address = in.nextLine();
            double wallet = Double.parseDouble(in.nextLine()); // Parse the line to a double
            User user = new User(generateUserAccountId(users), name, address, wallet); // Generate the new user
            users.add(user); // Add them to the array list
        }

        in.close(); // Close the scanner
        return users; // Return the list
    }

    // Database of Preregistered users
    // In Assignment 2 these will be loaded from a file
    public static ArrayList<Driver> loadPreregisteredDrivers(String filename) throws IOException
    {
        ArrayList<Driver> drivers = new ArrayList<>();
        Scanner in = new Scanner(new File(filename));

        while (in.hasNextLine())
        {
            String name = in.nextLine();
            String car = in.nextLine();
            String liscence = in.nextLine();
            String address = in.nextLine();
            int zone = CityMap.getCityZone(address); // Get the zone based on the address
            Driver driver = new Driver(generateDriverId(drivers), name, car, liscence, address, zone); // Make the driver object
            drivers.add(driver); // Add it to the list of drivers
        }

        in.close(); // Close the scanner
        return drivers; // Return the list
    }
}

