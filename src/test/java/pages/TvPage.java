package pages;

import enumerators.By;
import org.junit.Assert;
import santtium.KeyEvent;
import santtium.implement.KeyToPress;
import santtium.interfaces.ICtrlWeb;
import stepsDefinitions.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class TvPage {
    public TvPage() {
    }

    public void validateLabelHome(String label) throws Exception {
        ICtrlWeb labelCtrl = Context.santtium.searchCtrlWeb(By.XPATH, "//*[contains(text(),'viendo')]");
        Assert.assertNotNull("No se encontro el label", labelCtrl);
        labelCtrl.flash();
    }

    public void pausePlayer() throws Exception {
        Context.santtium.keyPress(new KeyToPress(KeyEvent.Up, "keyup"));
        Context.santtium.keyPress(new KeyToPress(KeyEvent.Enter, "keyup"));
    }

    public void navegateByPage() throws Exception {
        Context.santtium.keyPress(new KeyToPress(KeyEvent.Down, "keyup"));
        Context.santtium.keyPress(new KeyToPress(KeyEvent.Right, "keyup"));
        Context.santtium.keyPress(new KeyToPress(KeyEvent.Right, "keyup"));
        Context.santtium.keyPress(new KeyToPress(KeyEvent.Left, "keyup"));
        Context.santtium.keyPress(new KeyToPress(KeyEvent.Up, "keyup"));
        Context.santtium.keyPress(new KeyToPress(KeyEvent.Enter, "keyup"));

        String img = Context.santtium.takeScreenShot(10);
        /*
        Example Screenshot
        byte[] data = Base64.getDecoder().decode(img.split(",")[1].getBytes(StandardCharsets.UTF_8));
        OutputStream stream = new FileOutputStream("/YOUR PATH/image.png");
        stream.write(data);

        String imgRaw = Context.santtium.takeScreenShotRaw(10);
        byte[] dataRaw = Base64.getDecoder().decode(imgRaw.split(",")[1].getBytes(StandardCharsets.UTF_8));
        OutputStream streamRaw = new FileOutputStream("/YOUR PATH/imageRaw.png");
        streamRaw.write(dataRaw);
        */
    }
}
