/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package code;

import com.restfb.FacebookClient;
import design.DownloadsFrame;
import design.SingleDownloadPanel;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class DownloadPhotos extends Thread {
    /* props */
    private FacebookClient facebookClient = null;
    private List photoIds = null;
    private String albumName = null;
    private String albumOwner = null;
    private String path = null;
    private boolean highQuality = false;
    
    /**
     * DownloadPhotos
     * @param facebookClient
     * @param photoIds
     * @param albumName
     * @param albumOwner
     * @param path 
     */
    public DownloadPhotos(FacebookClient facebookClient, List photoIds, String albumName, String albumOwner, String path, boolean highQuality) {
        this.facebookClient = facebookClient;
        this.photoIds = photoIds;
        this.albumName = albumName;
        this.albumOwner = albumOwner;
        this.path = path;
        this.highQuality = highQuality;
    }
    
    @Override
    public void run() {
        /* create directory */
        String dirName = albumName + " by " + albumOwner;
        try {
            File file = new File(path + "\\" + dirName);
            boolean created = file.mkdir();
            if(!created){
                String timestamp = new Date().toString();
                file = new File(path + "\\" + timestamp);
                file.mkdir();
            }
            Iterator itr = photoIds.iterator();
            String finalPath = path + "\\" + dirName;
            /* insert new panel in downloads window */
            SingleDownloadPanel panel = new SingleDownloadPanel();
            panel.setSize(961, 81);
            panel.setLimit(photoIds.size());
            panel.updateTopLabel(albumName + " - " + albumOwner + " (" + finalPath + ")");
            panel.updateBottomLabel("0/" + photoIds.size());
            DownloadsFrame.addPanel(panel);
            /* send reqs to download photos */
            while(itr.hasNext()) {
                new DownloadActualPhoto(facebookClient, (String)itr.next(), finalPath, panel, highQuality).start(); /* thread */
            }
        } catch(Exception e) {
            
        }
    }
}
