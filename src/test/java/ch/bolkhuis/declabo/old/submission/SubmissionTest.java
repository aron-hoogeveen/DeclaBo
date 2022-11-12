package ch.bolkhuis.declabo.old.submission;

import ch.bolkhuis.declabo.old.user.FundUser;
import ch.bolkhuis.declabo.old.user.FundUserTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class SubmissionTest {

    @Test
    public void test_equalsUsesFieldsNameDateAndPaidBy() {
        Submission submission1 = SubmissionBuilderTest.getMinimalSubmission();
        Submission submission2 = SubmissionBuilderTest.getMinimalSubmission();

        Assertions.assertEquals(submission1, submission2);

        String name = "name";
        LocalDate date = LocalDate.of(1998, 1, 24);
        FundUser fundUser = new FundUser(0L, "e@e.e", "e", "e", null, FundUserTest.FUND);

        Submission.Builder builder =
                Submission.builder(SubmissionBuilderTest.getMinimalSubmission());

        builder.setName(name);
        builder.setDate(date);
        builder.setPaidBy(fundUser);
        Submission submission3 = builder.build();
        Assertions.assertEquals(submission3, submission3);

        builder = Submission.builder(submission3);
        builder.setName("Different name");
        builder.setDate(date);
        builder.setPaidBy(fundUser);
        Submission submission4 = builder.build();
        Assertions.assertNotEquals(submission3, submission4);

        builder = Submission.builder(submission3);
        builder.setName(name);
        builder.setDate(LocalDate.of(1600, 3, 2));
        builder.setPaidBy(fundUser);
        Submission submission5 = builder.build();
        Assertions.assertNotEquals(submission3, submission5);

        builder = Submission.builder(submission3);
        FundUser differentFundUser =
                new FundUser(0L, "234@kjf.nl", "e", "e", null, FundUserTest.FUND);
        builder.setName(name);
        builder.setDate(date);
        builder.setPaidBy(differentFundUser);
        Submission submission6 = builder.build();
        Assertions.assertNotEquals(submission3, submission6);
    }
}
