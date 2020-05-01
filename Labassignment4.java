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
            
            try
            {
                Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
                String path = "C:\\Users\\Aaron\\Documents\\Lab4.accdb";
                String url = "jdbc:ucanaccess://" +path;
                Connection connection = DriverManager.getConnection(url);
                Statement statement = connection.createStatement();
            }
            catch(Exception cnfe)
            {
                cnfe.printStackTrace(System.err);
            }
                // load the Jdbc-Odbc driver.
        }
            
    }
    public static void display(String st)
    {
        
    }
}
