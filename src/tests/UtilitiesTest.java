package tests;

import FairGradeAllocator.Member;
import FairGradeAllocator.Project;
import FairGradeAllocator.Utilities;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

public class UtilitiesTest {


    @Test
    public void gradeCalculator() {
        Project p = new Project("Test Project", 3);
        Member memberA = new Member("A");
        Member memberB = new Member("B");
        Member memberC = new Member("C");

        p.addTeamMember(memberA);
        p.addTeamMember(memberB);
        p.addTeamMember(memberC);

        // Member A votes
        HashMap<String, Double> votes = new HashMap<>();
        votes.put("B", 50.0);
        votes.put("C", 50.0);
        HashMap<String, HashMap<String, Double>> voteMap = new HashMap<>();
        voteMap.put("Test Project", votes);
        memberA.setVoteMap(voteMap);

        // Member B Votes
        votes = new HashMap<>();
        votes.put("A", 35.0);
        votes.put("C", 65.0);
        voteMap = new HashMap<>();
        voteMap.put("Test Project", votes);
        memberB.setVoteMap(voteMap);

        votes = new HashMap<>();
        votes.put("A", 40.0);
        votes.put("B", 60.0);
        voteMap = new HashMap<>();
        voteMap.put("Test Project", votes);
        memberC.setVoteMap(voteMap);

        HashMap<String, Double> results = Utilities.gradeCalculator(p);

        Assert.assertEquals(results.get("A"), 0.23, 0.01);
        Assert.assertEquals(results.get("B"), 0.38, 0.01);
        Assert.assertEquals(results.get("C"), 0.39, 0.01);


    }
}