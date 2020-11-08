package nl.sourcelabs.workshop.testing.unit.mock;

public class OtherDepedencyWeDontWantToTest {

    public C doWorkWeDontWantToTest(final A a) {
        throw new IllegalStateException("this code should never be executed");
    }
}
