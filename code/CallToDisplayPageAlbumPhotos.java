/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package code;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Photo;
import design.Skeleton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JPanel;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class CallToDisplayPageAlbumPhotos extends Thread implements ActionListener {
    /* props */
    private FacebookClient facebookClient = null;
    private String albumId = null;
    private JPanel AlbumPanel = null;
    private Connection<Photo> albumPhotos = null;
    private List photos = null;
    private Iterator itr = null;
    private List buttons = new ArrayList();
    private double x = 6;
    private double y = 10;
    private String after = "-1";
    private Map selectedPhotos = null;
    private JButton downloadButton = null;
    private JMenu loader = null;
    
    /**
     * CallToDisplayAlbumPhotos
     * @param facebookClient
     * @param albumId
     * @param AlbumPanel 
     */
    public CallToDisplayPageAlbumPhotos(FacebookClient facebookClient, String albumId, JPanel AlbumPanel, double x, double y, String after, Map selectedPhotos, JButton DownloadButton, JMenu loader) {
        this.facebookClient = facebookClient;
        this.albumId = albumId;
        this.AlbumPanel = AlbumPanel;
        this.x = x;
        this.y = y;
        this.after = after;
        this.selectedPhotos = selectedPhotos;
        this.downloadButton = DownloadButton;
        this.loader = loader;
    }           
    
    @Override
    public void run() {
        String nextURL = null;
        try {
            JButton tempB;
            albumPhotos = facebookClient.fetchConnection(albumId + "/photos" , Photo.class, Parameter.with("after", after));
            nextURL = albumPhotos.getNextPageUrl();
            photos = albumPhotos.getData();
            itr = photos.iterator();
            /* display photos */
            while(itr.hasNext()) {
                Photo temp = (Photo)itr.next();
                String photoId = temp.getId();  
                tempB =  new JButton("bogus:bogus:" + photoId);
                tempB.setSize(150,150);
                tempB.setFont(new Font("Arial", Font.PLAIN, 0));
                tempB.setLocation((int)Math.floor(x), (int)Math.floor(y));
                /* add action listener */
                tempB.addActionListener(this);
                /* add to respective lists */
                buttons.add(tempB);
                x += (150 + 10);
                if(x + 160 > 976) {
                    x = 6.33;
                    y += 160;
                }
                /* add default image */
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/photo.png")));
                tempB.setIcon(icon);
                tempB.setHorizontalAlignment(AbstractButton.CENTER);
                tempB.setVerticalTextPosition(AbstractButton.CENTER);
                /* add button to the skeleton */
                AlbumPanel.add(tempB);
                AlbumPanel.repaint();
                AlbumPanel.setPreferredSize(new Dimension(993, (int)y));
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            double Y = y;
            if(x != 6.33) {
                Y += 160;
            }
            AlbumPanel.setPreferredSize(new Dimension(993, (int)Y));
            if(Skeleton.loadThumnailsStatic)
                new CallToDisplayCoverPhotos(facebookClient, buttons).start(); /* thread */
            try {
                if(nextURL != null || nextURL.length() <= 0) {
                    String after = nextURL.substring(nextURL.indexOf("after=") + 6);
                    after.replace("%3D", "=");
                    new CallToDisplayPageAlbumPhotos(facebookClient, albumId, AlbumPanel, x, y, after, selectedPhotos, downloadButton, loader).start(); /* thread */
                }
                else {
                    loader.setVisible(false);
                }
            }catch(Exception ex){
                loader.setVisible(false);
            }
            
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedButton = (JButton)e.getSource();
        String content = selectedButton.getText();
        String split[] = content.split(":");
        String photoId = split[2]; 
        /* check for photo in the map */
        if(selectedPhotos.containsKey(photoId)) {
           /* deselect the button */
           JButton temp = (JButton)selectedPhotos.get(photoId);
           int x = temp.getLocation().x;
           int y = temp.getLocation().y;
           x -= 5;
           y -= 5;
           temp.setSize(150, 150);
           temp.setLocation(x, y);
           selectedPhotos.remove(photoId);
           /* check for empty map */
           if(selectedPhotos.size() == 0) {
               downloadButton.setText("Download Album");
           }
        }
        else {
            /* add the button in the map */
            int x = selectedButton.getLocation().x;
            int y = selectedButton.getLocation().y;
            x += 5;
            y += 5;
            selectedButton.setSize(140, 140);
            selectedButton.setLocation(x, y);
            selectedPhotos.put(photoId, selectedButton);
            downloadButton.setText("Download Selected Photos");
        }
    }
}
