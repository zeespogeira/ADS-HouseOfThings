import org.junit.ClassRule;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.BDDMockito.*;


public class TestTest {
    //@BDDMockito
    public UnitUnderTest databaseMock;
    @ClassRule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    //BDDMockito.Then;
    @Test
    public void test1() {
        UnitUnderTest i = new UnitUnderTest();

        assertEquals(1, i.test());
    }
}