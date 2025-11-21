package model;

public abstract class Test {
    private String testId;
    private String testName;
    private double cost;

    public Test() {}

    public Test(String testId, String testName, double cost) {
        this.testId = testId;
        this.testName = testName;
        this.cost = cost;
    }

    public String getTestId() { return testId; }
    public void setTestId(String testId) { this.testId = testId; }

    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }

    public double getCost() { return cost; }
    public void setCost(double cost) { this.cost = cost; }

    public abstract String getType();

    @Override
    public String toString() {
        return String.format("Test[id=%s, name=%s, type=%s, cost=%.2f]", testId, testName, getType(), cost);
    }
}