/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package code;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Album;
import design.AlbumFrame;
import design.Skeleton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class LoadAlbumsList extends Thread implements ActionListener {
    /* props */
    public static Map albumNames = new HashMap();
    private String userId = null;
    private String userName = null;
    private JComboBox comboReference = null;
    private FacebookClient facebookClient = null;
    private Connection<Album> albumsList = null;
    private List userAlbumsList = null;
    private Iterator itr = null;
    private double x = 6;
    private double y = 10;
    private List buttons = new ArrayList(); /* store user albums */
    private List pageButtons = new ArrayList(); /* store page albums */
    private List labels = new ArrayList(); 
    private List pageLabels = new ArrayList();
    private JScrollPane scrollReference = null;
    private JPanel panelReference = null;
    private int type = 0;
    private JPanel pagePanelReference = null;
    private JScrollPane pageScrollReference = null;
    
    /**
     * LoadAlbumsList
     * @param facebookClient
     * @param userId 
     */
    public LoadAlbumsList(FacebookClient facebookClient, String userId, String userName, int type) {
        this.facebookClient = facebookClient;
        this.userId = userId;        
        this.userName = userName;
        this.panelReference = Skeleton.albumsPanelStatic;
        this.pagePanelReference = Skeleton.pagePanelStatic;
        this.scrollReference = Skeleton.albumsScrollStatic;
        this.pageScrollReference = Skeleton.pageScrollStatic;
        this.type = type;
        
        if(type == 0) {
            /* remove previous items on panel */
            this.panelReference.removeAll();
            this.panelReference.repaint();
            /* clear the map */
            albumNames.clear();
        }
        else {
            /* remove previous items on panel */
            this.pagePanelReference.removeAll();
            this.pagePanelReference.repaint();
        }
    }
    
    @Override 
    public void run() {
        displayAlbums();
    }
    
    /**
     * displayAlbums
     */
    public void displayAlbums() {
        try {
            albumsList = facebookClient.fetchConnection(userId + "/albums", Album.class);
            userAlbumsList = albumsList.getData();
            itr = userAlbumsList.iterator();
            JButton tempB; 
            JLabel tempL;
            if(type == 0) {
                /* tagged photos button */
                tempB = new JButton("tag:tag:tag");
                tempB.setSize(150,150);
                tempB.setFont(new Font("Arial", Font.PLAIN, 0));
                tempB.setLocation((int)Math.floor(x), (int)Math.floor(y));
                tempL = new JLabel("Tagged Photos");
                tempL.setSize(150, 25);
                tempL.setFont(new Font("Segoe UI", Font.BOLD, 12));
                tempL.setLocation((int)Math.floor(x), (int)Math.floor(y) + 150);
                /* add action listener */
                tempB.addActionListener(this);
                /* add to respective lists */
                buttons.add(tempB);
                labels.add(tempL);
                x += (150 + 10);
                /* insert into the map */
                albumNames.put("tagged photos", (int)Math.ceil(y));
                /* add default image */
                ImageIcon iconT = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/photo.png")));
                tempB.setIcon(iconT);
                tempB.setHorizontalAlignment(AbstractButton.CENTER);
                tempB.setVerticalTextPosition(AbstractButton.CENTER);
                /* add button to the skeleton */
                panelReference.add(tempB);
                panelReference.add(tempL);
                panelReference.repaint();
            }
            else {
                Skeleton.commonTextBarStatic.setText("yay! page found!");
            }
            /* display albums */ 
            while(itr.hasNext()) {
                Album temp = (Album)itr.next();
                String albumId = temp.getId();
                String albumName = temp.getName();
                String coverId = temp.getCoverPhoto();
                tempB =  new JButton(albumId + ":" + albumName + ":" + coverId);
                tempB.setSize(150,150);
                tempB.setFont(new Font("Arial", Font.PLAIN, 0));
                tempB.setLocation((int)Math.floor(x), (int)Math.floor(y));
                tempL = new JLabel(albumName);
                tempL.setSize(150, 25);
                tempL.setFont(new Font("Segoe UI", Font.BOLD, 12));
                tempL.setLocation((int)Math.floor(x), (int)Math.floor(y) + 150);
                /* add action listener */
                tempB.addActionListener(this);
                /* add to respective lists */
                if(type == 0) { /* users */
                    buttons.add(tempB);
                    labels.add(tempL);
                } 
                else if(type == 1) { /* pages */
                    pageButtons.add(tempB);
                    pageLabels.add(tempL);
                }
                x += (150 + 10);
                if(x + 160 > 976) {
                    x = 6.33;
                    y += 185;
                }
                /* insert into the map */
                if(type == 0)
                    albumNames.put(albumName.toLowerCase(), (int)Math.ceil(y));
                /* add default image */
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/photo.png")));
                tempB.setIcon(icon);
                tempB.setHorizontalAlignment(AbstractButton.CENTER);
                tempB.setVerticalTextPosition(AbstractButton.CENTER);
                if(type == 0) {
                    /* add button to the skeleton */
                    panelReference.add(tempB);
                    panelReference.add(tempL);
                    panelReference.repaint();
                    panelReference.setPreferredSize(new Dimension(993, (int)y));
                }
                else {
                    pagePanelReference.add(tempB);
                    pagePanelReference.add(tempL);
                    pagePanelReference.repaint();
                    pagePanelReference.setPreferredSize(new Dimension(993, (int)y));
                }
            }
        } catch(Exception e) {
            if(type == 1) {
                Skeleton.searchForPageStatic.setVisible(true);
                Skeleton.searchForPageStatic.setText("no page with id: " + userId + " found! please try again");
                Skeleton.pageLoaderStatic.setVisible(false);
                Skeleton.commonTextBarStatic.setText("search failed!");
            }
        } finally {
            double Y = y;
            if(x != 6.33) {
                Y += 185;
            }
            if(type == 0) {
                Skeleton.loaderStatic.setVisible(false); /* hide loader */
                panelReference.setPreferredSize(new Dimension(993, (int)Y));
                Skeleton.commonTextBarStatic.setText("select an album to display photos!");
                if(Skeleton.loadThumnailsStatic)
                    new CallToDisplayCoverPhotos(facebookClient, buttons).start(); /* thread */
            }
            else {
                Skeleton.pageLoaderStatic.setVisible(false); /* hide loader */
                pagePanelReference.setPreferredSize(new Dimension(993, (int)Y));
                if(Skeleton.loadThumnailsStatic)
                    new CallToDisplayCoverPhotos(facebookClient, pageButtons).start(); /* thread */
            }
        }
    }
    
    /**
     * actionPerformed
     * @param event 
     */
    public void actionPerformed(ActionEvent event) {
        JButton source = (JButton)event.getSource();
        String content = source.getText();
        String split[] = content.split(":");
        String albumId = split[0];
        String albumName = split[1];
        String userName = this.userName;
        if(albumId.equals("tag")) {
            new AlbumFrame(facebookClient, userId, userName);
        }
        else if(type == 0) {
            new AlbumFrame(facebookClient, albumId, userName, albumName); 
        }
        else {
            new AlbumFrame(facebookClient, albumId, userName, albumName, "page");
        }
    }
}
