package FairGradeAllocator;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Scanner;

/**
 * Class that handles user input to create a HashMap that the Member class uses
 * as its voteMap
 *
 * @var minPoints maximum number of "voting points" that can be distributed
 * @var maxPoints minimum number of "voting points" that can be distributed
 */
public class Vote {

    final public static int maxPoints = 100;
    final public static int minPoints = 0;

    /**
     * Method that executes the process of allocating the votes
     * @param pList ProjectList object representing the list of existing
     *                    projects as parameter
     * @throws Exception throws Exception if there are no existing projects and then
     * reminds the user to create a project first
     */
    public static void voting(ProjectList pList)
    {
        try
        {
            Project currentProject = pList.chooseProject();
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

        for (Member mem : p.getTeamList())
        {

            HashMap<String, Double> votes = new HashMap<String, Double>();
            sum = 0;

            System.out.println("\tEnter " + mem.getName() + "'s votes, points must add up to " +
                    maxPoints + ":\n");

            while (sum != maxPoints)
            {
                for (Member other : p.getTeamList())
                {
                    if (!(other.equals(mem)))
                    {

                        double currentVote = inputVote(mem, other);

                        votes.put(other.getName(), currentVote);
                        mem.put(p.getName(), votes);
                        sum += currentVote;

                    }
                }

                if (sum != maxPoints)
                {
                    System.out.println("\n\t\tThe points did not add up to " + maxPoints + ". Try again!\n");
                    sum = 0;
                }
                else
                {
                    System.out.println();
                }

            }

        }

    }

    /**
     * Method that asks the user to input the votes one Member allocates to another
     * @param mem member that is voting
     * @param other member that gets voted
     * @return double the input vote-points
     */
    private static double inputVote(Member mem, Member other)
    {
        Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);
        double currentVote = -1;

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

        return currentVote;
    }


}
