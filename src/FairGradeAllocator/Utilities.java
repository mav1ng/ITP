package FairGradeAllocator;

import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

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
     * Method that saves all the projects the user has created and the allocated votes by
     * writing the information into a file in a certain format. This file will be created
     * when the program is closed by pressing 'q'/'Q'
     * @param projectList HashMap<String, Project> which is a map of all projects that
     *                    have been created
     * @throws IOException General I/O Exception might be thrown
     */
    public static void save(HashMap<String, Project> projectList) throws IOException
    {

        Project currentProject;

        String output;

        FileWriter fw = new FileWriter("projects.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        for (String projectName : projectList.keySet())
        {

            currentProject = projectList.get(projectName);
            output = projectName + "," + currentProject.getTeamSize() + ",";

            for (Member mem : currentProject.getTeamList())
            {
                output += mem.getName() + ",";
            }

            for (Member mem : currentProject.getTeamList())
            {
                output += mem.getName() + ",";

                //checking whether the votes have been allocated before
                if (mem.getVoteMap().containsKey(projectName))
                {
                    for (String other : mem.getVoteMap().get(projectName).keySet())
                    {
                        output += other + "," + mem.getVoteMap().get(projectName).get(other) + ",";
                    }
                }
                //if not, saving a -1 instead of a double value
                else
                {
                    output += -1 + ",";
                }

            }

            output += "#end#";
            pw.println(output);

        }

        pw.close();

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

    public static void start(HashMap<String, Project> projectList) throws IOException
    {
        File saveFile = new File("projects.txt");
        if (saveFile.exists() && !saveFile.isDirectory())
        {
            FileReader fr = new FileReader(saveFile);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null)
            {
                Scanner sc = new Scanner(line);
                try
                {

                    Project currentProject = new Project()
                }

            }
        }

    }

}
