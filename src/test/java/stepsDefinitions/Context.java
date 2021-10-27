package stepsDefinitions;

import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import santtium.implement.SanttiumProvider;
import santtium.interfaces.Santtium;

public class Context {

    public static Santtium santtium;

    @Before
    public void before() throws Exception {
        System.out.println("primero");
        santtium = SanttiumProvider.GetProvider("QOVDC", "1e8a57a3-e885-415c-9711-f7a28147c215", 10);
        santtium.start();
        System.out.println("Hola mundo");
    }

    @AfterStep
    public void afterStep() {
        System.out.println("step");
    }
}
