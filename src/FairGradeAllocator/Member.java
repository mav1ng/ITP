package FairGradeAllocator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class that saves the information about a member of a team which include name
 * and later on the distribution of his "vote points"
 *
 * @var voteMap HashMap<String, HashMap<String, Double>> will store the distribution of his votes
 * in different project later on in the application
 * @var name String represents the name of the member
 * @var minPoints maximum number of "voting points" a member can distribute to one person
 * @var maxPoints minimum number of "voting points" a member can distribute to one person
 */
public class Member
{

    final public static int maxPoints = 100;
    final public static int minPoints = 0;
    private HashMap<String, HashMap<String, Double>> voteMap = new HashMap<String, HashMap<String, Double>>();
    private String name;

    /**
     * Constructor asks the user to input the name of the member
     * @param n String representing the name of the member
     */
    public Member(String n)
    {
        name = n;
    }


    /**
     * Method that executes the process of allocating the votes
     * @param projectList HashMap<String, Project> representing the list of existing
     *                    projects as parameter
     * @throws Exception throws Exception if there are no existing projects and then
     * reminds the user to create a project first
     */
    public static void voting(HashMap<String, Project> projectList)
    {
        try
        {
            Project currentProject = Utilities.chooseProject(projectList);
            System.out.println("\tThere are " + currentProject.getTeamSize() + " team members.\n\n");

            enterVotes(currentProject);

            System.out.print("\n\tYou successfully allocated the votes!\n" +
                    "\tPress enter to continue...");

            //waiting for the user to enter any key to continue the program

            Utilities.finishMethod();
        } catch (IOException e)
        {
                e.printStackTrace();
                System.out.println("There was no user input!");
        } catch (Exception e)
        {
                e.printStackTrace();
                System.out.println("e");
        }

    }


    /**
     * method that represents the step of asking the user for a points input
     * for different members of the group
     * @param p Project in which the votes should be allocated
     */
    private static void enterVotes(Project p)
    {

        double sum;
        double currentVote;
        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);

        for (Member mem : p.getTeamList())
        {

            HashMap<String, Double> votes = new HashMap<String, Double>();
            currentVote = -1;
            sum = 0;

            System.out.println("\tEnter " + mem.getName() + "'s votes, points must add up to " +
                    maxPoints + ":\n");

            while (sum != maxPoints)
            {
                for (Member other : p.getTeamList())
                {
                    if (!(other.equals(mem)))
                    {

                        System.out.print("\t\tEnter " + mem.getName() + "'s points for " + other.getName() + ": ");

                        if (sc.hasNextDouble())
                        {
                            currentVote = sc.nextDouble();
                        }
                        else
                        {
                            sc.nextLine();
                        }

                        while (currentVote <= minPoints || currentVote >= maxPoints)
                        {
                            currentVote = -1;
                            System.out.println("\n\t\tPlease enter a valid Vote number! (0 < vote number < 100)");
                            System.out.print("\t\tEnter " + mem.getName() + "'s points for " +
                                    other.getName() + ":\t\t");

                            if (sc.hasNextDouble())
                            {
                                currentVote = sc.nextDouble();
                            }
                            else
                            {
                                while ((sc.nextLine()).isEmpty()) {}
                            }
                        }

                        votes.put(other.getName(), currentVote);
                        mem.voteMap.put(p.getName(), votes);
                        sum += currentVote;
                        currentVote = -1;

                    }
                }

                if (sum != maxPoints)
                {
                    System.out.println("\n\t\tThe points did not add up to " + maxPoints + ". Try again!\n");
                    sum = 0;
                    currentVote = -1;
                }
                else
                {
                    System.out.println();
                }

            }

        }

    }


    /**
     * equals and hashCode methods. In this case a member object is equal to another one
     * if they have the same name.
     * @param o Object that should be compared
     * @return boolean true equal boolean false if not
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Member member = (Member) o;

        return name.equals(member.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }


    /**
     * to String method
     * @return String representation of the object
     */
    public String toString()
    {
        return "Name of the member: " + name;
    }


    /**
     * getter and setter methods
     */

    public String getName()
    {
        return name;
    }

    /**
     * not used yet
     */
    public void setName(String name)
    {
        this.name = name;
    }

    public HashMap<String, HashMap<String, Double>> getVoteMap()
    {
        return voteMap;
    }

    /**
     * not used yet
     */
    public void setVoteMap(HashMap<String, HashMap<String, Double>> hm)
    {
        voteMap = hm;
    }
}
