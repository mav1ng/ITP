package FairGradeAllocator;

import java.io.*;
import java.util.*;

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


    /**
     * Method that loads all the projects the user has created and the allocated votes by
     * reading the information form the textfile projects.txt automatically when the program
     * has been started.
     * @param projectList HashMap<String, Project> which is a map where all the information
     *                    will be loaded into
     * @throws IOException General I/O Exception might be thrown
     */
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
                Scanner sc = new Scanner(line).useDelimiter(",");
                sc.useLocale(Locale.ENGLISH);
                try
                {
                    String currentProjectName = sc.next();
                    int currentTeamSize = sc.nextInt();
                    Project currentProject = new Project(currentProjectName, currentTeamSize);

                    for (int i = 1; i <= currentTeamSize; i++)
                    {
                        Member currentMember = new Member(sc.next());
                        currentProject.addTeamMember(currentMember);
                    }

                    for (int i = 0; i < currentTeamSize; i++)
                    {
                        sc.next();
                        Member currentMember = currentProject.getTeamList().get(i);

                        HashMap<String, HashMap<String, Double>> currentVotingMap
                                = new HashMap<String, HashMap<String, Double>>();

                        HashMap<String, Double> currentVotingMapMember
                                = new HashMap<String, Double>();

                        if (!sc.hasNext("-1"))
                        {
                            for (int j = 0; j < currentTeamSize - 1; j++) {
                                currentVotingMapMember.put(sc.next(), sc.nextDouble());
                            }

                            currentVotingMap.put(currentProjectName, currentVotingMapMember);
                            currentMember.setVoteMap(currentVotingMap);
                        } else
                        {
                            sc.next();
                        }
                    }

                    projectList.put(currentProjectName, currentProject);

                } catch (InputMismatchException e)
                {
                    System.out.println(e);
                    System.out.println("The save file was not formatted correctly. Check save() method!");
                }

                sc.close();

            }

            br.close();

        }

    }

    /**
     * Method that askes the user to specify the current project.
     * @param projectList HashMap<String, Project> HashMap of created projects taking in the name of the
     *                    project as a key and the project itself as a value
     * @return returns current project
     * @throws Exception throws Exception if there are no projects created already
     */
    public static Project chooseProject(HashMap<String, Project> projectList) throws Exception
    {
        if (projectList.isEmpty())
        {
            throw new Exception("ERROR: No projects saved! You have to create a project first!");
        } else
        {
            System.out.println("\n\tAvailable Projects: ");
            for (String name : projectList.keySet()) {
                System.out.println("\t" + name);
            }
            Scanner sc = new Scanner(System.in).useLocale(Locale.ENGLISH);
            System.out.print("\n\tEnter the project name: ");

            String currentProjectName = sc.nextLine();

            while (!(projectList.keySet().contains(currentProjectName))) {
                System.out.println("\tError: Please enter a valid project name!\n");
                System.out.println("\n\tAvailable Projects: ");

                for (String name : projectList.keySet()) {
                    System.out.println("\t\t" + name);
                }

                System.out.print("\n\tEnter the project name: ");
                currentProjectName = sc.nextLine();
            }

            return projectList.get(currentProjectName);
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

}
