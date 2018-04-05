package FairGradeAllocator;

import java.io.*;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class FileHandling {

    private static final String fileName = "projects.txt";

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
        File saveFile = new File(fileName);
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
     * Method that saves all the projects the user has created and the allocated votes by
     * writing the information into a file in a certain format. This file will be created
     * when the program is closed by pressing 'q'/'Q'
     * @param pList ProjectList object which stores information about all projects that
     *                    have been created in its instance variable
     * @throws IOException General I/O Exception might be thrown
     */
    public static void save(ProjectList pList) throws IOException
    {

        Project currentProject;

        String output;

        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter pw = new PrintWriter(bw);

        for (String projectName : pList.getpList().keySet())
        {

            currentProject = pList.getpList().get(projectName);
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

}
