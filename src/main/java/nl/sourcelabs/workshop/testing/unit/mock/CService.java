package nl.sourcelabs.workshop.testing.unit.mock;

public class CService {

    private final OtherDepedencyWeDontWantToTest otherDepedencyWeDontWantToTest;

    public CService(final OtherDepedencyWeDontWantToTest otherDepedencyWeDontWantToTest) {
        this.otherDepedencyWeDontWantToTest = otherDepedencyWeDontWantToTest;
    }

    public C findCForA(final A a) {
      return otherDepedencyWeDontWantToTest.doWorkWeDontWantToTest(a);
    }

}
