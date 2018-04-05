package FairGradeAllocator;

import java.io.*;
import java.util.HashMap;

/**
 * Class in which all the essential methods the application is using in the main method
 * are declared and defined.
 */
public class Utilities
{


    /**
     * Method that prints out an explanation about what the application is used for
     */
    public static void displayDetails()
    {

        System.out.println("\n\nInformation about this app:");
        System.out.println("\n\tThis app, which is called the \"Fair Grade Allocator\",\n" +
                "\tallows you to create several projects and enter information\n" +
                "\tabout the team members and will help you to allocate the credit\n" +
                "\tfor the project fairly so that every party is satisfied with\n" +
                "\tthe outcome. Try it out!");

        System.out.print("\n\tPress enter to continue...");

        //waiting for the user to enter any key to continue the program
        try
        {
            finishMethod();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("There was no user input!");
        }

    }


    /**
     * Method that allows the app to wait for any input of the user to return
     * to the Main menu of the application
     * @throws IOException if there is no input given by the user
     */
    public static void finishMethod() throws IOException
    {
        System.in.read();
        //Ignores any input the user might have given before pressing enter
        System.in.skip((long)(System.in.available()));
    }

    /**
     * Method that displayes the allocated grades
     * @param allocatedGrades HashMap of the calculated grades
     */
    public static void showAllocatedGrades(HashMap<String, Double> allocatedGrades)
    {
        System.out.println("\tThe point allocation based on votes is: \n");

        for (String name: allocatedGrades.keySet())
        {
            System.out.println("\t\t" + name + "\t" + allocatedGrades.get(name));
        }

        System.out.print("\n\tPress enter to continue...");
    }


}
