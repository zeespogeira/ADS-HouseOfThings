import org.junit.ClassRule;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
//import static org.mockito.BDDMockito.*;


public class TestTest {
    @Test
    public void test1() {
        UnitUnderTest i = new UnitUnderTest();

        assertEquals(1, i.test());
    }
}