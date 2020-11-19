package nl.sourcelabs.workshop.testing.unit.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class A2BMapperWithoutExtensionTest {

    private CService cService;

    private A2BMapper sut;

    @BeforeEach
    public void beforeEach() {
        cService = Mockito.mock(CService.class);
        sut = new A2BMapper(cService);
    }

    @Test
    public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        final A a = new A();
        final C c = new C();

        System.out.println("cService before Mocking: " + cService.findCForA(a));
        when(cService.findCForA(a)).thenReturn(c);
        System.out.println("cService after Mocking: " + cService.findCForA(a));

        final B output = sut.map(a);

        assertThat(output.getA()).isEqualTo(a);
        assertThat(output.getC()).isEqualTo(c);

        verify(cService, times(3)).findCForA(a);

    }

}