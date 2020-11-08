package nl.sourcelabs.workshop.testing.unit.mock;

public class A2BMapper {

    private final CService cService;

    public A2BMapper(final CService cService) {
        this.cService = cService;
    }

    public B map(final A a) {
        final B b = new B();

        b.setA(a);

        final C c = cService.findCForA(a);
        b.setC(c);

        return b;
    }

}
