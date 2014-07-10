/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package code;

import com.restfb.FacebookClient;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class DisplayUserProfilePicture extends Thread{
    private FacebookClient facebookClient;
    private String userId;
    private JButton userButton;
    
    /**
     * 
     * @param userId
     * @param userButton 
     */
    public DisplayUserProfilePicture(FacebookClient facebookClient, String userId, JButton userButton) {
        this.facebookClient = facebookClient;
        this.userId = userId;
        this.userButton = userButton;
    }
    
    @Override
    public void run() {
        try {
            URL url = new URL("http://graph.facebook.com/"+ this.userId +"/picture?width=150&height=150");            
            URLConnection urlC = url.openConnection();
            url = new URL(urlC.getHeaderField("Location"));
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(img);
            this.userButton.setIcon(icon);
            this.userButton.setHorizontalAlignment(AbstractButton.CENTER);
            this.userButton.setVerticalTextPosition(AbstractButton.CENTER);
        } catch(Exception e) { }
    }
}
