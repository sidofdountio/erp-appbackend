import com.sidof.app.AuthServerApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <blockquote><pre>
 * Author   : @Dountio
 * LinkedIn : @SidofDountio
 * GitHub   : @SidofDountio
 * Version  : V1.0
 * Email    : sidofdountio406@gmail.com
 * Since    : 3/27/26
 * </blockquote></pre>
 */

@SpringBootTest(classes = AuthServerApplication.class)
public class AuthServerApplicationTest {

    @Test
    void helloTest() {
        System.out.println("Hello Java test for auth server");
    }
}
