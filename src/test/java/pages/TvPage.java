package pages;

import enumerators.By;
import org.junit.Assert;
import santtium.KeyEvent;
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

    public void validateLabelHome(String label) throws InterruptedException {
        ICtrlWeb labelCtrl = Context.santtium.searchCtrlWeb(By.XPATH, "//*[contains(text(),'viendo')]");
        Assert.assertNotNull("No se encontro el label", labelCtrl);
        labelCtrl.flash();
    }

    public void pausePlayer() throws InterruptedException {
        Context.santtium.keyPress(KeyEvent.Up);
        Context.santtium.keyPress(KeyEvent.Enter);
    }

    public void navegateByPage() throws InterruptedException, IOException {
        Context.santtium.keyPress(KeyEvent.Down);
        Context.santtium.keyPress(KeyEvent.Right);
        Context.santtium.keyPress(KeyEvent.Right);
        Context.santtium.keyPress(KeyEvent.Left);
        Context.santtium.keyPress(KeyEvent.Up);
        Context.santtium.keyPress(KeyEvent.Enter);

        String img = Context.santtium.takeScreenShot(10);
        byte[] data = Base64.getDecoder().decode(img.split(",")[1].getBytes(StandardCharsets.UTF_8));
        OutputStream stream = new FileOutputStream("/Users/santty/Downloads/image.png");
        stream.write(data);

        String imgRaw = Context.santtium.takeScreenShotRaw(10);
        byte[] dataRaw = Base64.getDecoder().decode(imgRaw.split(",")[1].getBytes(StandardCharsets.UTF_8));
        OutputStream streamRaw = new FileOutputStream("/Users/santty/Downloads/imageRaw.png");
        streamRaw.write(dataRaw);
    }
}
