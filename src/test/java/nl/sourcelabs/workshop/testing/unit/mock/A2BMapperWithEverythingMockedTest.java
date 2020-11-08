package nl.sourcelabs.workshop.testing.unit.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class A2BMapperWithEverythingMockedTest {

    @Mock
    private A a;

    @Mock
    private C c;

    @Mock
    private CService cService;

    @InjectMocks
    private A2BMapper sut;

    @Test
    public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
        when(cService.findCForA(a)).thenReturn(c);

        final B output = sut.map(a);

        assertThat(output.getA()).isEqualTo(a);
        assertThat(output.getC()).isEqualTo(c);

        verify(cService).findCForA(a);
    }

}