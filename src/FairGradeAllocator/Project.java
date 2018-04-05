package FairGradeAllocator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

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
    final private static int minTeamSize = 3;


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
    public static Project createProject(ProjectList pList)
    {

        String currentName = enterProjectName(pList);
        int currentNumber = enterProjectSize();

        System.out.println();

        Project currentProject = new Project(currentName, currentNumber);
        currentProject.enterTeamNames();

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
     * Method that checks whether a String for a Project name is valid to be added to a specified ProjectList
     * @param currentProject String that is checked
     * @param pList ProjectList to which the Project with the specified name should be added
     * @return Boolean true if not valid, false if valid
     */
    public static Boolean isValidProjectName(String currentProject, ProjectList pList)
    {
        if (currentProject.trim().isEmpty() || pList.getpList().keySet().contains(currentProject))
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * asks the user to enter a project name, handles the user input and checks if input is correct
     * @param pList ProjectList needs to be checked to know which projects are already created
     * @return the input project name
     */
    private static String enterProjectName(ProjectList pList)
    {
        Scanner sc = new Scanner(System.in);

        System.out.print("\n\tEnter the project name: ");
        String currentName = sc.nextLine();

        while (isValidProjectName(currentName, pList))
        {
            System.out.println("\tPlease enter a valid name. Don't use the same name twice!");
            System.out.print("\n\tEnter the project name: ");
            currentName = sc.nextLine();
        }

        return currentName;
    }

    /**
     * asks the user to enter a project size, handles the user input and checks if input is correct
     * @return int input project size
     */
    private static int enterProjectSize()
    {

        Scanner sc = new Scanner(System.in);

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

        return currentNumber;
    }

    /**
     * asks the user to enter the names of the teammembers of the current project
     * handles the user input and checks if input is correct
     */
    private void enterTeamNames()
    {
        Scanner sc = new Scanner(System.in);

        //Looping over the size of the team asking the user to put in
        //the needed information about the members of the team
        for (int i = 1; i <= getTeamSize(); i++)
        {

            String currentMemberName;

            System.out.print("\t\tEnter the name of team member " + i + ": ");
            currentMemberName = sc.nextLine();

            while (Member.isValidMemberName(currentMemberName, this))
            {
                System.out.println("\t\tEnter a valid name! Don't use any digits and avoid existing names!");
                System.out.print("\t\tEnter the name of team member " + i + ": ");
                currentMemberName = sc.nextLine();
            }

            //creating a new Member objects and adding it to the teamList
            //of the current project.
            Member currentMember = new Member(currentMemberName);
            addTeamMember(currentMember);
            setNameList();
        }

    }


    /**
     * Method that calculates the allocated shares of a project with exactly three members.
     * @param p Project for which the grades should be calculated
     * @return HashMap<String, Double> that contains the calculated shares of the members
     */
    public static HashMap<String, Double> gradeCalculator(Project p)
    {
        Double[][] voteMatrix = new Double[3][3];
        Double[][] voteRatioMatrix = new Double[3][3];

        ArrayList<Member> teamList = p.getTeamList();

        for (int i = 0; i < teamList.size(); i++) {
            for (int j = 0; j < teamList.size(); j ++) {
                if (i != j) {
                    voteMatrix[i][j] = (teamList.get(i).getVoteMap().get(p.getName()).get(teamList.get(j).getName())/100);
                }
            }
        }

        voteRatioMatrix[0][1] = (voteMatrix[0][1]/voteMatrix[0][2]);
        voteRatioMatrix[0][2] = (voteMatrix[0][2]/voteMatrix[0][1]);
        voteRatioMatrix[1][0] = (voteMatrix[1][0]/voteMatrix[1][2]);
        voteRatioMatrix[1][2] = (voteMatrix[1][2]/voteMatrix[1][0]);
        voteRatioMatrix[2][0] = (voteMatrix[2][0]/voteMatrix[2][1]);
        voteRatioMatrix[2][1] = (voteMatrix[2][1]/voteMatrix[2][0]);


        HashMap<String, Double> shares = new HashMap<>();
        shares.put(teamList.get(0).getName(), 1/(1+voteRatioMatrix[1][2]+voteRatioMatrix[2][1]));
        shares.put(teamList.get(1).getName(), 1/(1+voteRatioMatrix[0][2]+voteRatioMatrix[2][0]));
        shares.put(teamList.get(2).getName(), 1/(1+voteRatioMatrix[0][1]+voteRatioMatrix[1][0]));

        return shares;


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

    /**
     * equals and hashCode methods.
     * @param o Object that should be compared
     * @return boolean true equal boolean false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (getTeamSize() != project.getTeamSize()) return false;
        if (getTeamList() != null ? !getTeamList().equals(project.getTeamList()) : project.getTeamList() != null)
            return false;
        if (getName() != null ? !getName().equals(project.getName()) : project.getName() != null) return false;
        return nameList != null ? nameList.equals(project.nameList) : project.nameList == null;
    }

    @Override
    public int hashCode() {
        int result = getTeamList() != null ? getTeamList().hashCode() : 0;
        result = 31 * result + getTeamSize();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (nameList != null ? nameList.hashCode() : 0);
        return result;
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
