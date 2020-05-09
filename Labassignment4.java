
package labassignment4;
import java.sql.*;
import java.util.*;
import java.text.*;

public class Labassignment4 {
    public static void main(String[] args)
    {
        int input = 10;
        Scanner kb = new Scanner(System.in);
        while(input!=0){
            System.out.println("0. Quit");
            System.out.println("1. Display the scheule of all trips");
            System.out.println("2. Edit the schedule");
            System.out.println("3. Display the stops of a given trip");
            System.out.println("4. Display the weekly schedule of a given driver and date");
            System.out.println("5. Add a drive");
            System.out.println("6. Add a bus");
            System.out.println("7. Delete a bus");
            System.out.println("8. Record the actual data of a given trip offering specified by its key");
            System.out.print("Enter an number option: ");
            input = kb.nextInt();  
            System.out.println("");
            try{
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            }
            catch(ClassNotFoundException cnfe){
                cnfe.printStackTrace(System.err);
            }
            Connection connection = null;
            Statement statement = null;
            try{
                String path = "C:\\Users\\Aaron\\Documents\\Lab4.accdb";
                String url = "jdbc:ucanaccess://" +path;
                connection = DriverManager.getConnection(url);
                statement = connection.createStatement();
                if(input == 1)
                {
                    displaySchedule(statement);
                }
                if(input == 2)
                {
                    editSchedule(statement);
                }
                if(input == 3)
                {
                    displayStops(statement);
                }
                if(input == 4)
                {
                    displayWeeklySchedule(statement);
                }
                if(input == 5)
                {
                   addDriver(statement); 
                }
                if(input == 6)
                {
                    addBus(statement);
                }
                if(input == 7)
                {
                    deleteBus(statement);
                }
                if(input == 8)
                {
                    insertActualTripInfo(statement);
                }
            }
            catch(SQLException e){
                e.printStackTrace(System.err);
            }
            
        }
            
    }
    public static void displaySchedule(Statement statement)throws SQLException
    {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter Start Location Name: ");
        String startLocation = kb.nextLine();
        System.out.print("Enter a Destination Name: ");
        String destination = kb.nextLine();
        System.out.print("Enter a Date: ");
        String date = kb.nextLine();
        
        ResultSet resultset = statement.executeQuery("SELECT A.ScheduledStartTime, A.ScheduledArrivalTime, A.DriverName, A.BusID " +
                    "FROM TripOffering A, Trip B " +
                    "WHERE B.StartLocationName LIKE '" + startLocation + "' AND " +
                    "B.DestinationName LIKE '" + destination + "' AND " +
                    "A.Date = '" + date + "' AND " +
                    "B.TripNumber = A.TripNumber " +
                    "Order by ScheduledStartTime ");
        System.out.println("ScheduledStartTime ScheduledArrivalTime DriverName BusID");
        while(resultset.next()){
            System.out.println(resultset.getString("ScheduledStartTime")+ " " +
                    resultset.getString("ScheduledArrivalTime")+ " "+
                    resultset.getString("DriverName") + " "+
                    resultset.getString("BusID"));
        }    
    }

    public static void editSchedule(Statement statement)throws SQLException{
        Scanner kb = new Scanner(System.in);

        System.out.println("---------------------------------------------------");
        System.out.println("1. Delete a Trip offering");
        System.out.println("2. Add a set of Trip offerings");
        System.out.println("3. Change the driver for a Trip offering");
        System.out.println("4. Change the bus for a given Trip offering");
        System.out.println("Enter decision: ");
        int input = kb.nextInt(); 
        try{
                if(input == 1)
                {
                   deleteTrip(statement);
                }
                if(input == 2)
                {
                    addTrip(statement);
                }
                if(input == 3)
                {
                    changeDriver(statement);
                }
                if(input == 4)
                {
                    changeBus(statement);
                }
            }
            catch(Exception e){
                e.printStackTrace(System.err);
            }
    }

    public static void deleteTrip(Statement statement){
        Scanner kb = new Scanner(System.in);
        
        System.out.print("Enter Trip Number: ");
        String tripNumber = kb.nextLine();
        
        System.out.print("Enter Date: ");
        String date = kb.nextLine();
        
        System.out.print("Enter Scheduled Start Time: ");
        String scheduledStartTime = kb.nextLine();
        
        int delete;
        try {
            delete = statement.executeUpdate("DELETE FROM TripOffering " +
                                                 "WHERE TripNumber = '" + tripNumber + "' AND " +
                                                 "Date = '" + date + "' AND " +
                                                 "ScheduledStartTime = '" + scheduledStartTime + "'");
        
            if(delete == 0){
                System.out.println("There is no Trip Offering with Trip Number: " + tripNumber + " on "+ date + 
                                   " starting at "+ scheduledStartTime);
            }
            else {
                System.out.println("Trip Offering deleted");
            }
        } catch (Exception ex) {
            System.out.println("Unable to delete Trip offer.");
        }
    }

    public static void addTrip(Statement statement) {
        Scanner kb = new Scanner(System.in);
        boolean moreTrips = true;
        
        while(moreTrips){
            System.out.print("Enter Trip Number: ");
            String tripNumber = kb.nextLine();
            
            System.out.print("Enter Date: ");
            String date = kb.nextLine();
            
            System.out.print("Enter Scheduled Start Time: ");
            String scheduledStartTime = kb.nextLine();
            
            System.out.print("Enter Scheduled Arrival Time: ");
            String scheduledArrivalTime = kb.nextLine();
            
            System.out.print("Enter Driver Name : ");
            String driverName = kb.nextLine();
            
            System.out.print("Enter BusID: ");
            String busID = kb.nextLine();
            
            try{
                statement.execute("INSERT INTO TripOffering VALUES ('" + tripNumber + "', '" + date + 
                                  "', '" + scheduledStartTime + "', '" + scheduledArrivalTime + "', '" +
                                  driverName + "', '" + busID + "')");
                System.out.println("New Trip Offering added");
            } catch(Exception e){
                System.out.println("Unable to add trip.");
            }
            System.out.print("Would you like to add another Trip Offering? (y/n): ");
            if(!kb.nextLine().equalsIgnoreCase("y")){
                moreTrips = false;
            }
        }
    }

    public static void changeDriver(Statement statement){
        Scanner kb = new Scanner(System.in);
        
        System.out.print("Enter New Driver Name: ");
        String driverName = kb.nextLine();
        
        System.out.print("Enter Start Trip Number: ");
        String tripNumber = kb.nextLine();
        
        System.out.print("Enter Date: ");
        String date = kb.nextLine();
        
        System.out.print("Enter Scheduled Start Time: ");
        String scheduledStartTime = kb.nextLine();
        
        try{
            int wasChanged = statement.executeUpdate("UPDATE TripOffering " +
                                                     "SET DriverName = '" + driverName + "' " +
                                                     "WHERE TripNumber = '" + tripNumber + "' AND " +
                                                           "Date = '" + date + "' AND " +
                                                           "ScheduledStartTime = '" + scheduledStartTime + "'");
            if(wasChanged == 0){
                System.out.println("Unable to change driver.");
            }
            else{
                System.out.println("Driver changed");
            }
        } catch (Exception e){
            System.out.println("Unable to change driver.");
        }
    }

    public static void changeBus(Statement statement){
        Scanner kb = new Scanner(System.in);
        
        System.out.print("Enter New BusID: ");
        String busID = kb.nextLine();
        
        System.out.print("Enter Existing Trip Number: ");
        String tripNumber = kb.nextLine();
        
        System.out.print("Enter Date: ");
        String date = kb.nextLine();
        
        System.out.print("Enter Scheduled Start Time: ");
        String scheduledStartTime = kb.nextLine();
        
        try{
            int wasChanged = statement.executeUpdate("UPDATE TripOffering " +
                                                     "SET BusID = '" + busID + "' " +
                                                     "WHERE TripNumber = '" + tripNumber + "' AND " +
                                                           "Date = '" + date + "' AND " +
                                                           "ScheduledStartTime = '" + scheduledStartTime + "'");
            if(wasChanged == 0){
                System.out.println("Unable to change bus.");
            }
            else{
                System.out.println("Bus changed.");
            }
        } catch (SQLException e){
            System.out.println("Unable to change bus.");
        }
    }


    public static void displayStops(Statement statement)throws SQLException
    {
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter a trip number:");
        String tripNumber = kb.nextLine();
        ResultSet resultset = statement.executeQuery("SELECT * "+
                "FROM TripStopInfo " +
                "WHERE TripNumber = '" + tripNumber + "' ");
        System.out.println("TripNumber StopNumber SequenceNumber DrivingTime");
        while(resultset.next()){
            System.out.println(resultset.getString("TripNumber")+ " "+
                    resultset.getString("StopNumber")+ " " +
                    resultset.getString("SequenceNumber")+ " " +
                    resultset.getString("DrivingTime"));
        }
    }

    public static void displayWeeklySchedule(Statement statement) throws SQLException{
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter a driver name:");
        String driverName = kb.nextLine();
        System.out.print("Enter a date in the format MM/DD/YY:");
        String date = kb.nextLine();
        
        for(int i = 0; i < 7; i++){
            try{
                ResultSet rs = statement.executeQuery("SELECT TripNumber, Date, ScheduledStartTime, ScheduledArrivalTime, BusID " +
                                                    "FROM TripOffering " +
                                                    "WHERE DriverName LIKE '" + driverName + "' " +
                                                                "AND Date = '" + date + "' " +
                                                    "Order By ScheduledStartTime ");

                ResultSetMetaData rsMetaData = rs.getMetaData();
                int columnCount = rsMetaData.getColumnCount();
                if(i == 0){
                    System.out.println("Day 1:");

                    for(int j = 1; j <= columnCount; j++){
                            System.out.printf("%-20s ", rsMetaData.getColumnName(j));
                    }
                    System.out.println();
                }
                while(rs.next()){
                    for(int j = 1; j <= columnCount; j++)
                        System.out.printf("%-20s ", rs.getString(j));

                    System.out.println();

                }
                rs.close();
            }catch(Exception e){

                System.out.println("Unable to display schedule for " + date);

            }
            // int day = Integer.parseInt(date.substring(4,5)) + 1;
            // String newDate = date.substring(0,4) + Integer.toString(day) + date.substring(5);
            // date = newDate;
            String [] dateArr = date.split(" ");
            int day = Integer.parseInt(dateArr[1]) + 1;
            date = dateArr[0] + " " + Integer.toString(day);
            if(i < 6)
                System.out.println("Day " + (i+2) + ":");
        }
    }

    public static void addDriver(Statement statement){
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter new driver's name: ");
        String name = kb.nextLine();
        System.out.print("Enter new driver's phone number: ");
        String phoneNumber = kb.nextLine();
        try{
            statement.execute("INSERT INTO Driver VALUES ('"+ phoneNumber+"','"+ name + "')" );
            System.out.println("Driver has been added.");
        }
        catch(SQLException e){
            System.out.println("Unable to enter new driver");
        }
    }

    public static void addBus(Statement statement){
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter new bus ID: ");
        String id = kb.nextLine();
        System.out.print("Enter new bus's model: ");
        String model = kb.nextLine();
        System.out.print("Enter new bus's year: ");
        String year = kb.nextLine();
        try{
            statement.execute("INSERT INTO Bus VALUES ('"+ id +"','"+ model + "','" + year + "')" );
            System.out.println("Bus has been added.");
        }
        catch(SQLException e){
            System.out.println("Unable to enter new bus");
        }
    }

    public static void deleteBus(Statement statement){
        Scanner kb = new Scanner(System.in);
        System.out.println("What bus ID would you like to delete: ");
        String busID = kb.nextLine();
        try{
            statement.executeUpdate("DELETE Bus " +
                    "WHERE BusID = '" + busID +"'");
            System.out.println("Bus has been deleted.");
        }
        catch(SQLException e)
        {
            System.out.println("Unable to delete bus ");
        }
    }

    public static void insertActualTripInfo(Statement statement) throws SQLException {
        Scanner kb = new Scanner(System.in);
        
        System.out.print("Enter Trip Number: ");
        String tripNumber = kb.nextLine();
        
        System.out.print("Enter Date: ");
        String date = kb.nextLine();
        
        System.out.print("Enter Scheduled Start Time: ");
        String scheduledStartTime = kb.nextLine();
        
        System.out.print("Enter Stop Number: ");
        String stopNumber = kb.nextLine();
        
        
        try{
            ResultSet rs1 = statement.executeQuery("SELECT ActualStartTime, ActualArrivalTime " +
                                                  "FROM ActualTripStopInfo " +
                                                  "WHERE TripNumber = '" + tripNumber + "' AND " +
                                                        "Date = '" + date + "' AND " +
                                                        "ScheduledStartTime = '" + scheduledStartTime + "' AND " + 
                                                        "StopNumber = '" + stopNumber + "'");
            
            rs1.next();
            int wasChanged1 = statement.executeUpdate("UPDATE TripOffering " +
                                          "SET ScheduledArrivalTime = '" + rs1.getString(2) + "' " +
                                          "WHERE TripNumber = '" + tripNumber + "' AND " +
                                              "Date = '" + date + "' AND " +
                                              "ScheduledStartTime = '" + scheduledStartTime + "'");
            
            ResultSet rs2 = statement.executeQuery("SELECT ActualStartTime, ActualArrivalTime " +
                                                  "FROM ActualTripStopInfo " +
                                                  "WHERE TripNumber = '" + tripNumber + "' AND " +
                                                        "Date = '" + date + "' AND " +
                                                        "ScheduledStartTime = '" + scheduledStartTime + "' AND " + 
                                                        "StopNumber = '" + stopNumber + "'");
            rs2.next();
            
            int wasChanged2 = statement.executeUpdate("UPDATE TripOffering " +
                                                      "SET ScheduledStartTime = '" + rs2.getString(1) + "' " +
                                                      "WHERE TripNumber = '" + tripNumber + "' AND " +
                                                            "Date = '" + date + "' AND " +
                                                            "ScheduledStartTime = '" + scheduledStartTime + "'");
        } catch(Exception e){
            System.out.println("Error! Information was not updated");
        }
        
    }
}
