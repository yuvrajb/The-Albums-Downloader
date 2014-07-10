/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package code;

import com.restfb.FacebookClient;
import com.restfb.types.Photo;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.ListIterator;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class DisplaySmallPhoto extends Thread {
    /* props */
    private FacebookClient facebookClient = null;
    private String id = null;
    private JButton photoButton = null;
    private Photo photo = null;
    
    /**
     * DisplaySmallPhoto
     * @param facebookClient
     * @param id
     * @param button 
     */
    public DisplaySmallPhoto(FacebookClient facebookClient, String id, JButton photoButton) {
        this.facebookClient = facebookClient;
        this.id = id;
        this.photoButton = photoButton;
    }
    
    public void run() {
        try{
            photo = facebookClient.fetchObject(id, Photo.class);
            List imgs = photo.getImages();
            ListIterator itr = imgs.listIterator(imgs.size() - 1);
            String src = null;
            while(itr.hasPrevious()) {
                Photo.Image img = (Photo.Image)itr.next();
                if(img.getHeight() >= 150 && img.getWidth() >= 150) {
                    src = img.getSource();
                    break;
                }
            }            
            URL url = new URL(src);
            BufferedImage img = ImageIO.read(url);
            ImageIcon icon = new ImageIcon(img);
            this.photoButton.setIcon(icon);
            this.photoButton.setHorizontalAlignment(AbstractButton.CENTER);
            this.photoButton.setVerticalAlignment(AbstractButton.CENTER);
        } catch(Exception e) { }
    }
}
