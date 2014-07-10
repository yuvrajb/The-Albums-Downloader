/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package design;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JWindow;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class Splash extends JWindow {
    /* props */
    BufferedImage splashImage = null;
    
    /**
     * Splash
     */
    public Splash() {
        /* basic changes */
        this.setSize(900, 550);
        this.setLocationRelativeTo(null);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        /* fetch the image */
        try {
            URL splashURL = getClass().getResource("/resources/splash.jpg");
            splashImage = ImageIO.read(splashURL);
            this.paint(this.getGraphics());
            Thread.sleep(3000);
            this.dispose();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void paint(Graphics g) {
        g.drawImage(splashImage.getScaledInstance(900, 550, BufferedImage.SCALE_SMOOTH), 0, 0, this);
    }
}
