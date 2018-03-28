package FairGradeAllocator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that saves information about a Project and stores the information
 * in appropriate data structures
 *
 * @var teamList ArrayList<Member> stores the different Member objects of the team
 * @var teamSize int represents the number of members working on the project
 * @var name String represents the name of the project
 * @var nameList ArrayList<String> stores the names of all the different members of the team
 * @var maxTeamSize int maximum team size
 * @var minTeamSize int minimum team size
 */
public class Project
{

    private ArrayList<Member> teamList = new ArrayList<Member>();
    private int teamSize;
    private String name;
    private ArrayList<String> nameList = new ArrayList<String>();

    final private static int maxTeamSize = 10000;
    final private static int minTeamSize = 2;


    /**
     * Constructor to create a Project object name and teamSize have to be
     * specified
     * @param name name of the Project
     * @param teamSize number of members working on the project
     */
    public Project(String name, int teamSize)
    {
        this.name = name;
        this.teamSize = teamSize;
    }




    /**
     * Method that creates a new project and asks the user to put in additional
     * information about the size of the team and the names of the team members
     * @return Project returns a Project object which includes an ArrayList of the
     * members the user has entered.
     */
    public static Project createProject()
    {

        Scanner sc = new Scanner(System.in);

        System.out.print("\n\tEnter the project name: ");
        String currentName = sc.nextLine();

        while (currentName.trim().isEmpty())
        {
            System.out.println("\tPlease enter a valid name");
            System.out.print("\n\tEnter the project name: ");
            currentName = sc.nextLine();
        }

        int currentNumber = 0;

        System.out.print("\tEnter the number of team members: ");
        if (sc.hasNextInt())
        {
            currentNumber = sc.nextInt();
        }
        else
        {
            sc.nextLine();
        }

        while (((currentNumber < minTeamSize) || currentNumber > maxTeamSize))
        {
            System.out.println("\tPlease enter a valid teamsize!");
            System.out.print("\tEnter the number of team members: ");

            if (sc.hasNextInt())
            {
                currentNumber = sc.nextInt();
            }
            else
            {
                sc.nextLine();
            }
        }

        //Creating a new Project object with the specified name and team size
        Project currentProject = new Project(currentName, currentNumber);
        sc.nextLine();
        System.out.println();

        //Looping over the size of the team asking the user to put in
        //the needed information about the members of the team
        for (int i = 1; i <= currentProject.getTeamSize(); i++)
        {

            String currentMemberName;

            System.out.print("\t\tEnter the name of team member " + i + ": ");
            currentMemberName = sc.nextLine();

            while (currentMemberName.trim().isEmpty() || currentProject.nameList.contains(currentMemberName))
            {
                System.out.println("\t\tEnter a valid name! Don't use the same name twice!");
                System.out.print("\t\tEnter the name of team member " + i + ": ");
                currentMemberName = sc.nextLine();
            }

            //creating a new Member objects and adding it to the teamList
            //of the current project.
            Member currentMember = new Member(currentMemberName);
            currentProject.addTeamMember(currentMember);
            currentProject.setNameList();
        }

        System.out.print("\n\tYou successfully created a new project!\n" +
                "\tPress enter to continue...");

        //waiting for the user to enter any key to continue the program
        try
        {
            Utilities.finishMethod();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("There was no user input!");
        }

        return currentProject;

    }


    /**
     * method that adds a Member object to the teamList of a certain project
     * @param m Member object that is to be added to the project
     */
    public void addTeamMember(Member m)
    {
        teamList.add(m);
    }

    /**
     * to String method
     * @return String representation of the object
     */
    public String toString()
    {
        String output = "Name of the Project: " + name + "\nSize of the Project: " + teamSize;
        output += "\n\nList of team members: \n" + getTeamNames();
        return output;
    }


    //getter and setter methods


    /**
     * Method that creates a list of the names of all members that have been added to this
     * project
     */
    public void setNameList()
    {
        for (Member mem : teamList)
        {
            nameList.add(mem.getName());
        }
    }

    /**
     * not used yet
     */
    public void setTeamList(ArrayList<Member> ar)
    {
        teamList = ar;
    }

    public ArrayList<Member> getTeamList()
    {
        return teamList;
    }

    /**
     * not used yet
     */
    public void setTeamSize(int number)
    {
        teamSize = number;
    }

    public int getTeamSize()
    {
        return teamSize;
    }

    /**
     * not used yet
     */
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    /**
     * prints The names of all team members
     */
    public String getTeamNames()
    {
        String output = "";
        for (Member mem : teamList)
        {
            output += mem.getName() + "\n";
        }

        return output;
    }
}
