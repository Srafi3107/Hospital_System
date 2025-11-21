package model;

public class SampleTest extends Test {
    public SampleTest() { super(); }
    public SampleTest(String testId, String testName, double cost) { super(testId, testName, cost); }
    @Override
    public String getType() { return "Sample"; }
}