package nl.sourcelabs.workshop.testing.unit.mock;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class A2BMapperWithoutExtensionTest {

    private CService cService;

    private A2BMapper sut;

    //BEFORE-EACH

    @Test
    @Disabled
    public void given_anA_when_mappedToB_thenExpect_allFieldsMappedCorrectly() {
    }

}