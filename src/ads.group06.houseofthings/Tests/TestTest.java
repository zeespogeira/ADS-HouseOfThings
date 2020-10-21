import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestTest {
    @Mock
    UnitUnderTest databaseMock;
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Test
    public void test1() {
        UnitUnderTest i = new UnitUnderTest();

        assertEquals(1, i.test());
    }
}