package FairGradeAllocator;

import java.util.HashMap;
import java.util.regex.Pattern;

/**
 * Class that saves the information about a member of a team which include name
 * and later on the distribution of his "vote points"
 *
 * @var voteMap HashMap<String, HashMap<String, Double>> will store the distribution of his votes
 * in different project later on in the application
 * @var name String represents the name of the member
 */
public class Member
{

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

    public Member(String n, HashMap<String, HashMap<String, Double>> map)
    {
        name = n;
        voteMap = map;
    }


    /**
     * Method that checks whether a String for a member is valid to be added to a specified project
     * @param currentMember String that is checked
     * @param currentProject Project to which the Member with the String should be added
     * @return Boolean true if not valid, false if valid
     */
    public static Boolean isValidMemberName(String currentMember, Project currentProject)
    {
        if (currentMember.trim().isEmpty() || currentProject.getTeamNames().contains(currentMember) ||
                currentMember.equals("-1") || Pattern.compile("[0-9]").matcher(currentMember).find())
        {
            return true;
        } else
        {
            return false;
        }
    }


    /**
     * Method that allows other classes to use the put() method on the instance variable votemap
     * @param name Name of the Member about to be added to the votemap as a key
     * @param hm Map of votes about to be added to the votemap as a value
     */
    public void put(String name, HashMap<String, Double> hm)
    {
        voteMap.put(name, hm);
    }


    /**
     * equals and hashCode methods. In this case a member object is equal to another one
     * if they have the same name.
     * @param o Object that should be compared
     * @return boolean true if equal boolean false if not
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

    public void setVoteMap(HashMap<String, HashMap<String, Double>> hm)
    {
        voteMap = hm;
    }
}
