package nl.sourcelabs.workshop.testing.unit.captor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.verify;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock
    private Client client;

    //CAPTOR

    @InjectMocks
    private Service sut;

    @Test
    public void given_message_thenExpect_clientCalledWithAppropriateRequest() {

        final String message = "My Message Text";
        final String expectedPrefix = Service.APP_ID + " ";

        sut.sendRequest(message);

        // CAPTURE AND ASSERTIONS

    }

}