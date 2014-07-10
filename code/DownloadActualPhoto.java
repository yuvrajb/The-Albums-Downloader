/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package code;

import com.restfb.FacebookClient;
import com.restfb.types.Photo;
import design.SingleDownloadPanel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class DownloadActualPhoto extends Thread{
    /* props */
    private FacebookClient facebookClient = null;
    private String photoId = null;
    private String path = null;
    private SingleDownloadPanel panel = null;
    private Photo photo = null;
    private boolean highQuality = false;
    
    /**
     * DownloadActualPhoto
     * @param facebookClient
     * @param photoId
     * @param path
     * @param panel 
     */
    public DownloadActualPhoto(FacebookClient facebookClient, String photoId, String path, SingleDownloadPanel panel, boolean highQuality) {
        this.facebookClient = facebookClient;
        this.photoId = photoId;
        this.path = path;
        this.panel = panel;
        this.highQuality = highQuality;
    }
    
    @Override
    public void run() {
        try {
            photo = facebookClient.fetchObject(photoId, Photo.class);
            List images = photo.getImages();
            String src = null;
            Iterator itr = images.iterator();
            if(highQuality) {
                Photo.Image img = (Photo.Image)itr.next();
                src = img.getSource();
            }
            else {
                itr.next();
                Photo.Image img = (Photo.Image)itr.next();
                src = img.getSource();
            }
            /* open url */
            URL url = new URL(src);
            URLConnection urlC = url.openConnection();
            BufferedImage bufferedImage = ImageIO.read(urlC.getInputStream());
            /* open new file */
            File img = new File(path + "\\" + photoId + ".jpg");
            img.createNewFile();
            ImageIO.write(bufferedImage, "jpg", img);
        } catch(Exception e) {

        } finally {
            panel.updateProgressBar(); /* update progress bar */
        }
        
    }
}
