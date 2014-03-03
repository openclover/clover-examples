import com_cenqua_clover.JUnitParameterizedTestSniffer;
import org.junit.runner.JUnitCore;

/**
 *
 */
public class RunJUnit4WithClover {
    public static void main(String[] args) {
        JUnitCore core= new JUnitCore();
        core.addListener(new JUnitParameterizedTestSniffer());
        core.run(SquareTest.class);
    }
}
