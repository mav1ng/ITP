package FairGradeAllocator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * class that stores information about what projects have been created. It provides
 * functions to handle the list of projects
 */
public class ProjectList {

    private HashMap<String, Project> pList;

    /**
     * constructor
     * @param pList HashMap that stores Names of Projects and the Project itself
     */
    public ProjectList(HashMap<String, Project> pList)
    {
        this.pList = pList;
    }



    /**
     * Method that prints out the allocated grades for the members of a specified project that
     * has already been created
     * @throws Exception throws exception if chooseProject() throws Exception
     */
    public void show() throws Exception, NullPointerException
    {
        Project currentProject = chooseProject();
        System.out.println("\tThere are " + currentProject.getTeamSize() + " team members.\n\n");
        HashMap<String, Double> allocatedGrades = Project.gradeCalculator(currentProject);
        Utilities.showAllocatedGrades(allocatedGrades);

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

    }



    /**
     * Method that asks the user to specify the current project.
     * @return returns chosen project
     * @throws Exception throws Exception if there are no projects created already
     */
    public Project chooseProject() throws Exception
    {
        if (isEmpty())
        {
            throw new Exception("ERROR: No projects saved! You have to create a project first!");
        } else
        {
            String currentProjectName = enterProjectName();
            return getpList().get(currentProjectName);
        }
    }


    /**
     * asks the user to enter a project name, handles the user input and checks if input is correct
     * @return String name of the current project
     */
    private String enterProjectName()
    {
        printAvailable();
        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);
        System.out.print("\n\tEnter the project name: ");

        String currentProjectName = sc.nextLine();

        while (!(getpList().keySet().contains(currentProjectName))) {
            System.out.println("\tError: Please enter a valid project name!\n");
            printAvailable();

            System.out.print("\n\tEnter the project name: ");
            currentProjectName = sc.nextLine();
        }

        return currentProjectName;
    }

    /**
     * Prints available projects
     */
    private void printAvailable()
    {
        System.out.println("\n\tAvailable Projects: ");
        for (String name : this.getpList().keySet())
        {
            System.out.println("\t\t" + name);
        }
    }


    /**
     * checks whether current ProjectList is empty
     * @return true if empty, false if not
     */
    private Boolean isEmpty()
    {
        if (getpList().isEmpty())
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * equals and hashCode methods.
     * @param o Object that should be compared
     * @return boolean true if equal boolean false if not
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProjectList that = (ProjectList) o;

        return getpList() != null ? getpList().equals(that.getpList()) : that.getpList() == null;
    }

    @Override
    public int hashCode() {
        return getpList() != null ? getpList().hashCode() : 0;
    }

    /**
     * Method that allows other classes to use the put() method on the instance variable projectList
     * @param p Project that should be put into the projectList
     */
    public void put(Project p)
    {
        pList.put(p.getName(), p);
    }


    /**
     * getter and setter methods
     */

    /**
     * not yet used
     * @param pL HashMap<String, Project>
     */
    public void setPList(HashMap<String, Project> pL)
    {
        pList = pL;
    }

    public HashMap<String, Project> getpList()
    {
        return pList;
    }
}
