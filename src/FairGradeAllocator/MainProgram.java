package FairGradeAllocator;

import java.io.IOException;
import java.util.Scanner;
import java.util.HashMap;

import static java.lang.Character.toLowerCase;

/**
 * Main class that should be run to start the application. It will present the
 * user with a main menu from which he can go on and call several functions
 */
public class MainProgram
{

    public static void main(String[] args)
    {


        //creating the HashMap in which the several projects will be stored
        //information from that HashMap should be stored in the text file later
        HashMap<String, Project> projectList = new HashMap<String, Project>();


        try
        {
            Utilities.start(projectList);
        } catch (IOException e)
        {
            System.out.println(e);
        }


        Scanner scan = new Scanner(System.in);
        char action = 'a';

        //displaying the main menu waiting for the user to put in a key
        //if none of the specified keys is put in the main menu will appear again
        while (action != 'q')
        {
            System.out.println();
            System.out.println("\nWelcome to Split-it\n");
            System.out.println("\tAbout (A)");
            System.out.println("\tCreate Project (C)");
            System.out.println("\tEnter Votes (V)");
            System.out.println("\tShow Project (S)");
            System.out.println("\tQuit (Q)");
            System.out.print("\n\tPlease choose an option: ");
            action = toLowerCase(scan.next().charAt(0));

            switch (action)
            {
                case 'a':
                    //Displays details about the application
                    Utilities.displayDetails();
                    break;

                case 'c':
                    //Creates a new project, adds it to the project list
                    // and asks the user to input additional information
                    Project currentProject = Project.createProject();
                    projectList.put(currentProject.getName(), currentProject);
                    break;

                case 'v':
                    try
                    {
                        //Allocates the Votes for a specified project
                        Member.voting(projectList);
                    }
                    catch (Exception e)
                    {
                        System.out.println(e);
                    }
                    break;

                case 's':
                    try
                    {
                        Project.show(projectList);
                    } catch (Exception e)
                    {
                        System.out.println(e);
                    }
                    break;

                case 'q':
                    //Saving to a file
                    try
                    {
                        Utilities.save(projectList);
                    } catch (IOException e)
                    {
                        System.out.println(e);
                        e.printStackTrace();
                    }
                    break;

                default:
                    break;
            }
        }

        scan.close();
        System.out.println("\nPROGRAM ENDED\n");

    }

}
