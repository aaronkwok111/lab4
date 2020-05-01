
package labassignment4;
import java.sql.*;
import java.util.Scanner;

public class Labassignment4 {
    public static void main(String[] args)
    {
        int input = 1000;
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
                    
                }
                if(input == 3)
                {
                    displayStops(statement);
                }
                if(input == 4)
                {
                    
                }
                if(input == 5)
                {
                   addDriver(statement); 
                }
                if(input == 6)
                {
                    
                }
                if(input == 7)
                {
                    deleteBus(statement);
                }
                if(input == 8)
                {
                    
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
    public static void addDriver(Statement statement){
        Scanner kb = new Scanner(System.in);
        System.out.print("Enter new driver's name: ");
        String name = kb.nextLine();
        System.out.print("Enter new driver's phone number: ");
        String phoneNumber = kb.nextLine();
        try{
            statement.execute("INSERT INTO Driver VALUES ('"+ phoneNumber+"','"+ name + "')" );
        }
        catch(SQLException e){
            System.out.println("Unable to enter new driver");
        }
    }
    public static void deleteBus(Statement statement){
        Scanner kb = new Scanner(System.in);
        System.out.println("What bus ID would you like to delete: ");
        String busID = kb.nextLine();
        try{
            statement.executeUpdate("DELETE Bus " +
                    "WHERE BusID = '" + busID +"'");
        }
        catch(SQLException e)
        {
            System.out.println("Unable to delete bus ");
        }
    }
}
