package nl.sourcelabs.workshop.testing.unit.spy;

public class Service {

    private final MathUtil mathUtil;

    public Service(final MathUtil mathUtil) {
        this.mathUtil = mathUtil;
    }

    public int add(final int a, final int b) {
        // Imagine this MathUtil method call is actually difficult behaviour to mock
        final int result = mathUtil.doVeryVeryComplicatedCalculation(a, b);

        validateResult(result);

        return result;
    }

    private void validateResult(final int result) {
        // Complicated validation logic
    }

}
