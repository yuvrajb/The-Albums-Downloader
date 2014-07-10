/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package design;

import code.CallToDisplayAlbumPhotos;
import code.CallToDisplayPageAlbumPhotos;
import code.CallToDisplayTaggedPhotos;
import code.DownloadPhotos;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.Photo;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

/**
 *
 * @author Manhattan
 */
public class AlbumFrame extends javax.swing.JFrame{
    /* props */
    private FacebookClient facebookClient = null;
    private String albumId = null;
    private String userName = null;
    private String albumRealName = null;
    private Connection<Photo> albumPhotos = null;
    private double x = 6;
    private double y = 10;
    private List photos = null;
    private Iterator itr = null;
    private List buttons = new ArrayList();
    private Map selectedPhotos = new HashMap();
    private String userId;
    
    /**
     * AlbumFrame
     * @param facebookClient
     * @param albumId
     * @param userName
     * @param albumName 
     */
    public AlbumFrame(FacebookClient facebookClient, String albumId, String userName, String albumName) {
        this.facebookClient = facebookClient;
        this.albumId = albumId;
        this.userName = userName;
        this.albumRealName = albumName;
        
        initComponents();
        setIcon();
        customChanges();
        intelligence();
    }
    
    /**
     * AlbumFrame
     * @param facebookClient
     * @param userId
     * @param userName 
     */
    public AlbumFrame(FacebookClient facebookClient, String userId, String userName) {
        this.facebookClient = facebookClient;
        this.userId = userId;
        this.userName = userName;
        
        initComponents();
        setIcon();
        customChanges();
        tagIntelligence();
    }
    
    /**
     * AlbumFrame
     * @param facebookClient
     * @param userId
     * @param userName
     * @param albumName
     * @param type 
     */
    public AlbumFrame(FacebookClient facebookClient, String userId, String userName, String albumName, String type) {
        this.facebookClient = facebookClient;
        this.userId = userId;
        this.userName = userName;
        this.albumRealName = albumName;
        
        initComponents();
        setIcon();
        customChanges();
        pageIntelligence();
    }
    
    /**
     * setIcon
     */
    public void setIcon() {
        try{
            URL iconURL = getClass().getResource("/resources/icon_32.png");
            ImageIcon icon = new ImageIcon(iconURL);
            this.setIconImage(icon.getImage());
        } catch(Exception e) { }
    }
    
    /**
     * customChanges
     */
    public void customChanges() {
       this.getContentPane().setBackground(new Color(102, 102, 102));
       this.setLocationRelativeTo(null);
       this.setVisible(true);
       
       this.AlbumPanel.setPreferredSize(new Dimension(991, 100));
    }
    
    /**
     * tagIntelligence
     */
    public void tagIntelligence() {
        /* set the labels */
        this.albumName.setText("Tagged Photos");
        this.albumOwner.setText(this.userName);
        this.setTitle("Tagged photos of " + this.userName + " - The Albums Downloader");
        displayTaggedPhotos();
    }
    
    /**
     * intelligence
     */
    public void intelligence() {
        /* set the labels */
        this.albumName.setText(this.albumRealName);
        this.albumOwner.setText(this.userName);
        this.setTitle("Photos from album \"" + this.albumRealName + "\" by " + this.userName + " - The Albums Downloader");
        displayPhotos();
    }
    
    /**
     * pageIntelligence
     */
    public void pageIntelligence() {
        /* set the labels */
        this.albumName.setText(this.albumRealName);
        this.albumOwner.setText(this.userName);
        this.setTitle("Photos from album \"" + this.albumRealName + "\" by " + this.userName + " - The Albums Downloader");
        displayPagePhotos();
    }
    
    /**
     * displayTaggedPhotos
     */
    public void displayTaggedPhotos() {
        new CallToDisplayTaggedPhotos(facebookClient, userId, AlbumPanel, 6, 10, "-1", selectedPhotos, DownloadButton, loader).start(); /* thread */
    }
    
    /**
     * displayPhotos
     */
    public void displayPhotos() {
        new CallToDisplayAlbumPhotos(facebookClient, albumId, AlbumPanel, 6, 10, "-1", selectedPhotos, DownloadButton, loader).start(); /* thread */
    }
    
    /**
     * displayPagePhotos
     */
    public void displayPagePhotos() {
        new CallToDisplayPageAlbumPhotos(facebookClient, userId, AlbumPanel, 6, 10, "-1", selectedPhotos, DownloadButton, loader).start(); /* thread */
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        albumName = new javax.swing.JLabel();
        albumOwner = new javax.swing.JLabel();
        AlbumScroll = new javax.swing.JScrollPane();
        AlbumPanel = new javax.swing.JPanel();
        DownloadButton = new javax.swing.JButton();
        HighestQuality = new javax.swing.JCheckBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem5 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem6 = new javax.swing.JMenuItem();
        loader = new javax.swing.JMenu();

        jLabel2.setText("jLabel2");

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        albumName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        albumName.setForeground(new java.awt.Color(255, 255, 255));
        albumName.setText("New York City!!");
        albumName.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        albumOwner.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        albumOwner.setForeground(new java.awt.Color(255, 255, 255));
        albumOwner.setText("Yuvraj Singh Babrah");

        AlbumScroll.setBackground(new java.awt.Color(255, 255, 255));
        AlbumScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        AlbumScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        AlbumPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout AlbumPanelLayout = new javax.swing.GroupLayout(AlbumPanel);
        AlbumPanel.setLayout(AlbumPanelLayout);
        AlbumPanelLayout.setHorizontalGroup(
            AlbumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 978, Short.MAX_VALUE)
        );
        AlbumPanelLayout.setVerticalGroup(
            AlbumPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 633, Short.MAX_VALUE)
        );

        AlbumScroll.setViewportView(AlbumPanel);

        DownloadButton.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        DownloadButton.setText("Download Album");
        DownloadButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DownloadButtonMouseClicked(evt);
            }
        });

        HighestQuality.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        HighestQuality.setForeground(new java.awt.Color(255, 255, 255));
        HighestQuality.setSelected(true);
        HighestQuality.setText("Highest Quality");
        HighestQuality.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jMenu1.setText("File");

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Downloads");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);
        jMenu1.add(jSeparator2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Exit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuItem1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jMenuItem1KeyTyped(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Photos");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Deselect Photos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);
        jMenu2.add(jSeparator1);

        jMenuItem3.setText("Download Highest Quality");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("Download Destination");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);
        jMenu2.add(jSeparator3);

        jMenuItem6.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem6.setText("Download Entire Album");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);

        jMenuBar1.add(jMenu2);

        loader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/loader.GIF"))); // NOI18N
        loader.setText("loading photos");
        jMenuBar1.add(loader);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AlbumScroll, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(albumName, javax.swing.GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
                            .addComponent(albumOwner, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(DownloadButton))
                            .addComponent(HighestQuality, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(albumName)
                    .addComponent(DownloadButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(albumOwner)
                    .addComponent(HighestQuality))
                .addGap(20, 20, 20)
                .addComponent(AlbumScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void DownloadButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DownloadButtonMouseClicked
        // TODO fetch all button components
        JButton button = (JButton)evt.getSource();
        String content = button.getText();
        List photoIds = new LinkedList();
        /* fetch download directory */
        String path = DirectoryChooser.path;
        if(content == "Download Album") {
            /* download entire album */
            int totalPhotos = this.AlbumPanel.getComponentCount();
            if(totalPhotos == 0) {
                JOptionPane.showMessageDialog(this, "Please Wait!! Photos from the album are being loaded!", "No Photos Found!", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Component photos[] = this.AlbumPanel.getComponents();
            for(int i = 0; i < totalPhotos; i ++) {
                if(photos[i] instanceof JButton) {
                    JButton temp = (JButton)photos[i];
                    String buttonContent = temp.getText();
                    String splits[] = buttonContent.split(":");
                    String imageId = splits[2];
                    photoIds.add(imageId);
                }
            }
            /* fetch loading state */
            boolean visibility = loader.isVisible();
            String msg = "";
            String prompt = "";
            if(visibility) {
                prompt = "<br><p><b>Some photos are still being loaded!</b></p>";
            }
            msg = "<html><body><p>Download " + albumName.getText() + " (" + photoIds.size() + " photos) to " + path + "?</p>" + prompt;
            int opt = JOptionPane.showConfirmDialog(this, msg, "Confirm Download!", JOptionPane.OK_CANCEL_OPTION);
            if(opt == 0) {
                new DownloadPhotos(facebookClient, photoIds, albumName.getText(), albumOwner.getText(), path, jCheckBox1.isSelected()).start(); /* thread */
                this.dispose(); /* dispose frame */
                Skeleton.DownloadFrame.setVisible(true); /* show the downlod frame */
            }
            else {  }
        }
        else {
            /* download selected photos */
            Set photos = selectedPhotos.keySet();
            Iterator itr = photos.iterator();
            while(itr.hasNext()) {
                photoIds.add((String)itr.next());
            }
            int opt = JOptionPane.showConfirmDialog(this, "Download " + albumName.getText() + " (" + photoIds.size() + " photos) to " + path + "?", "Confirm Download", JOptionPane.OK_CANCEL_OPTION);
            if(opt == 0) {
                new DownloadPhotos(facebookClient, photoIds, albumName.getText(), albumOwner.getText(), path, jCheckBox1.isSelected()).start(); /* thread */
                //this.dispose(); /* dispose frame */ do not dispose when selected photos are being downloaded
                Skeleton.DownloadFrame.setVisible(true); /* show the downlod frame */
            }
            else {  }
        }
    }//GEN-LAST:event_DownloadButtonMouseClicked

    private void jMenuItem1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jMenuItem1KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem1KeyTyped

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO dispose the frame
        this.dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO deselect all the photos in the selectedPhotos
        Collection selectedButtons = selectedPhotos.values();
        Iterator itr = selectedButtons.iterator();
        while(itr.hasNext()) {
            JButton temp = (JButton)itr.next();
            int x = temp.getLocation().x;
            int y = temp.getLocation().y;
            x -= 5;
            y -= 5;
            temp.setSize(150, 150);
            temp.setLocation(x, y);
        }
        DownloadButton.setText("Download Album");
        selectedPhotos.clear();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO check the HighestQuality
        HighestQuality.setSelected(true);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO show file chooser
        new DirectoryChooser();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO download entire album
        List photoIds = new LinkedList();
        /* fetch download directory */
        String path = DirectoryChooser.path;
        int totalPhotos = this.AlbumPanel.getComponentCount();
        if(totalPhotos == 0) {
            JOptionPane.showMessageDialog(this, "Please Wait!! Photos from the album are being loaded!", "No Photos Found!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Component photos[] = this.AlbumPanel.getComponents();
        for(int i = 0; i < totalPhotos; i ++) {
            if(photos[i] instanceof JButton) {
                JButton temp = (JButton)photos[i];
                String buttonContent = temp.getText();
                String splits[] = buttonContent.split(":");
                String imageId = splits[2];
                photoIds.add(imageId);
            }
        }
        /* fetch loading state */
        boolean visibility = loader.isVisible();
        String msg = "";
        String prompt = "";
        if(visibility) {
            prompt = "<br><p><b>Some photos are still being loaded!</b></p>";
        }
        msg = "<html><body><p>Download " + albumName.getText() + " (" + photoIds.size() + " photos) to " + path + "?</p>" + prompt;
        int opt = JOptionPane.showConfirmDialog(this, msg, "Confirm Download!", JOptionPane.OK_CANCEL_OPTION);
        if(opt == 0) {
            new DownloadPhotos(facebookClient, photoIds, albumName.getText(), albumOwner.getText(), path, jCheckBox1.isSelected()).start(); /* thread */
            this.dispose(); /* dispose frame */
            Skeleton.DownloadFrame.setVisible(true); /* show the downlod frame */
        }
        else {  }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO show downloads frame
        Skeleton.DownloadFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AlbumPanel;
    private javax.swing.JScrollPane AlbumScroll;
    private javax.swing.JButton DownloadButton;
    private javax.swing.JCheckBox HighestQuality;
    private javax.swing.JLabel albumName;
    private javax.swing.JLabel albumOwner;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JMenu loader;
    // End of variables declaration//GEN-END:variables
}
