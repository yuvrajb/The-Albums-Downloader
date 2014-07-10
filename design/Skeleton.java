/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package design;

import code.FetchPageAlbums;
import code.ScanFriendList;
import code.SearchAlbum;
import code.SearchFriend;
import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.FriendList;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class Skeleton extends javax.swing.JFrame {
    /* props */
    public static JLabel userNameStatic;
    public static JLabel totalFriendsStatic;
    public static JLabel totalAlbumsStatic;
    public static JPanel friendsPanelStatic;
    public static JScrollPane friendsScrollStatic;
    public static JScrollPane friendsTabStatic;
    public static JTextField searchFieldStatic;
    public static JLabel commonTextBarStatic;
    public static JComboBox albumsListStatic;
    public static JTabbedPane tabPane;
    public static JPanel albumsPanelStatic;
    public static JScrollPane albumsScrollStatic;
    public static JScrollPane albumsTabStatic;
    public static int activeTab;
    public static JFrame DownloadFrame = null;
    public static JMenu loaderStatic = null;
    public static JMenu pageLoaderStatic = null;
    public static JLabel noAlbumSelectedStatic = null;
    public static JPanel pagePanelStatic = null;
    public static JScrollPane pageScrollStatic = null;
    public static JLabel searchForPageStatic =  null;
    public static boolean loadThumnailsStatic = true;
    
    private String userId = null;
    private String name = null;
    private FacebookClient facebookClient = null;
    private Connection<FriendList> friendsList = null;
    private boolean aboutFlag = false;
    
    /**
     * shows the second frame after token validation
     * @param facebookClient
     * @param userId
     * @param name
     * @param friendsList 
     */
    public Skeleton(FacebookClient facebookClient, String userId, String name, Connection<FriendList> friendsList) {
        this.facebookClient = facebookClient;
        this.userId = userId;
        this.name = name;
        this.friendsList = friendsList;
        
        initComponents();
        setIcon();
        customChanges();
        assignStaticVariables();
        checkForThumbnails();
        intelligence();
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
     * Make custom changes in the design
     */
    public void customChanges() {
        this.getContentPane().setBackground(new Color(102, 102, 102));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
                
        this.setTitle("Welcome " + this.name + "! - The Albums Downloader");
        this.userName.setText("hi " + this.name + "!!"); /* set the user name */
        this.loader.setVisible(false); /* hide the loader */
        this.page_loader.setVisible(false); 
        
        this.FriendsPanel.setPreferredSize(new Dimension(991, 100));
        this.AlbumsPanel.setPreferredSize(new Dimension(991, 100));
        this.PagesPanel.setPreferredSize(new Dimension(991, 100));
    }
    
    /**
     * assign to static props declared at the start **imp
     */
    public void assignStaticVariables() {
        this.userNameStatic = userName;
        this.totalFriendsStatic = totalFriends;
        this.friendsPanelStatic = FriendsPanel;
        this.friendsScrollStatic = FriendsScroll;
        //this.friendsTabStatic = FriendsTab;
        this.searchFieldStatic = searchField;
        this.commonTextBarStatic = commonTextBar;
        this.tabPane = jTabbedPane1;
        this.albumsPanelStatic = AlbumsPanel;
        this.albumsScrollStatic = AlbumsScroll;
        //this.albumsTabStatic = AlbumsTab;
        this.loaderStatic = loader;
        this.noAlbumSelectedStatic = noAlbumSelected;
        this.pagePanelStatic = PagesPanel;
        this.pageScrollStatic = PagesScroll;
        this.searchForPageStatic = searchForPage;
        this.pageLoaderStatic = page_loader;
    }
    
    /**
     * checkForThumbnails
     */
    public void checkForThumbnails() {
        try {
            String dir = System.getProperty("user.dir");
            dir += "\\ops" ;
            File checkdir = new File(dir);
            if(!checkdir.exists())
                checkdir.mkdir(); /* create dir */
            File newFile = new File(checkdir.getAbsolutePath() + "\\thumb.json");
            if(newFile.exists()) { /* check for existing file */
                FileReader reader = new FileReader(newFile);
                BufferedReader read = new BufferedReader(reader);
                String opt = read.readLine();
                if(opt.equals("0")) {
                    this.loadThumnailsStatic = false;
                    loadThumbnails.setSelected(false);
                }                    
                else {
                    this.loadThumnailsStatic = true;
                    loadThumbnails.setSelected(true);
                }
            }
            else { /* no existing file */
                newFile.createNewFile();
                FileWriter writer = new FileWriter(newFile, true);
                int opt = JOptionPane.showConfirmDialog(null, "You can disable thumbnails if your internet speed is slow. Would you like to disable thumbnails?", "Thumbnails Loading", JOptionPane.OK_CANCEL_OPTION);
                if(opt == 0) { // true;
                    writer.write("0");
                    this.loadThumnailsStatic = false;
                    loadThumbnails.setSelected(false);
                } 
                else {
                    writer.write("1");
                    this.loadThumnailsStatic = true;
                    loadThumbnails.setSelected(true);
                }
                writer.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void updateThumbFile(boolean load) {
        try {
            String dir = System.getProperty("user.dir");
            dir += "\\ops" ;
            File checkdir = new File(dir);
            if(!checkdir.exists())
                checkdir.mkdir(); /* create dir */
            File newFile = new File(checkdir.getAbsolutePath() + "\\thumb.json");
            if(newFile.exists()) { /* check for existing file */
                FileWriter writer = new FileWriter(newFile, false);
                if(load) {
                    writer.write("1");
                }
                else {
                    writer.write("0");
                }
                writer.close();
            }
            else { /* no existing file */
                newFile.createNewFile();
                FileWriter writer = new FileWriter(newFile, false);
                if(load) {
                    writer.write("1");
                }
                else {
                    writer.write("0");
                }
                writer.close();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void intelligence() {
        /* show friends tiles */
        new ScanFriendList(facebookClient, userId, friendsList).start(); // thread
        /* create download frame */
        DownloadFrame = new DownloadsFrame();
        /* get current location of the appliation */
        String path = System.getProperty("user.dir");
        DirectoryChooser.path = path;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        FriendsScroll = new javax.swing.JScrollPane();
        FriendsPanel = new javax.swing.JPanel();
        AlbumsScroll = new javax.swing.JScrollPane();
        AlbumsPanel = new javax.swing.JPanel();
        noAlbumSelected = new javax.swing.JLabel();
        PagesScroll = new javax.swing.JScrollPane();
        PagesPanel = new javax.swing.JPanel();
        searchForPage = new javax.swing.JLabel();
        userName = new javax.swing.JLabel();
        totalFriends = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        searchButton = new javax.swing.JButton();
        searchField = new javax.swing.JTextField();
        commonTextBar = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        loadThumbnails = new javax.swing.JCheckBoxMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        loader = new javax.swing.JMenu();
        page_loader = new javax.swing.JMenu();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        FriendsScroll.setBackground(new java.awt.Color(255, 255, 255));
        FriendsScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        FriendsScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        FriendsPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout FriendsPanelLayout = new javax.swing.GroupLayout(FriendsPanel);
        FriendsPanel.setLayout(FriendsPanelLayout);
        FriendsPanelLayout.setHorizontalGroup(
            FriendsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 987, Short.MAX_VALUE)
        );
        FriendsPanelLayout.setVerticalGroup(
            FriendsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 629, Short.MAX_VALUE)
        );

        FriendsScroll.setViewportView(FriendsPanel);

        jTabbedPane1.addTab("My Friends", FriendsScroll);

        AlbumsScroll.setBackground(new java.awt.Color(255, 255, 255));
        AlbumsScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        AlbumsScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        AlbumsPanel.setBackground(new java.awt.Color(255, 255, 255));

        noAlbumSelected.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        noAlbumSelected.setForeground(new java.awt.Color(102, 102, 102));
        noAlbumSelected.setText("Click on a friend to see his/her albums!");

        javax.swing.GroupLayout AlbumsPanelLayout = new javax.swing.GroupLayout(AlbumsPanel);
        AlbumsPanel.setLayout(AlbumsPanelLayout);
        AlbumsPanelLayout.setHorizontalGroup(
            AlbumsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlbumsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(noAlbumSelected, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        AlbumsPanelLayout.setVerticalGroup(
            AlbumsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AlbumsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(noAlbumSelected)
                .addContainerGap(603, Short.MAX_VALUE))
        );

        AlbumsScroll.setViewportView(AlbumsPanel);

        jTabbedPane1.addTab("Albums", AlbumsScroll);

        PagesScroll.setBackground(new java.awt.Color(255, 255, 255));
        PagesScroll.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        PagesScroll.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        PagesScroll.setPreferredSize(new java.awt.Dimension(1006, 631));

        PagesPanel.setBackground(new java.awt.Color(255, 255, 255));

        searchForPage.setFont(new java.awt.Font("Segoe UI", 1, 11)); // NOI18N
        searchForPage.setForeground(new java.awt.Color(102, 102, 102));
        searchForPage.setText("Enter the id of the page in the search box to fetch albums!");

        javax.swing.GroupLayout PagesPanelLayout = new javax.swing.GroupLayout(PagesPanel);
        PagesPanel.setLayout(PagesPanelLayout);
        PagesPanelLayout.setHorizontalGroup(
            PagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PagesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchForPage, javax.swing.GroupLayout.PREFERRED_SIZE, 944, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        PagesPanelLayout.setVerticalGroup(
            PagesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PagesPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(searchForPage)
                .addContainerGap(603, Short.MAX_VALUE))
        );

        PagesScroll.setViewportView(PagesPanel);

        jTabbedPane1.addTab("Pages", PagesScroll);

        userName.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        userName.setForeground(new java.awt.Color(255, 255, 255));
        userName.setText("...");
        userName.setMinimumSize(new java.awt.Dimension(150, 32));

        totalFriends.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        totalFriends.setForeground(new java.awt.Color(255, 255, 255));
        totalFriends.setText("0 Friends");

        jPanel2.setBackground(new Color(102,102,102));

        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        searchField.setText("Search Friends");
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                searchFieldKeyPressed(evt);
            }
        });

        commonTextBar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        commonTextBar.setForeground(new java.awt.Color(255, 255, 255));
        commonTextBar.setText("hey there!! searching friends is easy now. ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(commonTextBar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 350, Short.MAX_VALUE)
                        .addComponent(searchField, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton)))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(commonTextBar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(searchField)))
        );

        jMenu1.setText("File");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_1, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setText("My Friends");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_2, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem5.setText("Albums");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem5);

        jMenuItem9.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_3, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem9.setText("Pages");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem9);
        jMenu1.add(jSeparator2);

        loadThumbnails.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        loadThumbnails.setSelected(true);
        loadThumbnails.setText("Load Thumbnails");
        loadThumbnails.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadThumbnailsActionPerformed(evt);
            }
        });
        jMenu1.add(loadThumbnails);
        jMenu1.add(jSeparator1);

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setText("Exit");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Downloads");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("My Downloads");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem1);

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_J, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setText("Download Destination");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem8.setText("Help Me");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem8);
        jMenu3.add(jSeparator3);

        jMenuItem7.setText("Check for Updates");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem7);

        jMenuItem6.setText("About");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        loader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/loader.GIF"))); // NOI18N
        loader.setText("loading albums");
        jMenuBar1.add(loader);

        page_loader.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/loader.GIF"))); // NOI18N
        page_loader.setText("loading page");
        jMenuBar1.add(page_loader);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(userName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalFriends, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(userName, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(totalFriends, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO search for name in the map
        searchTriggered();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchFieldKeyPressed
        // TODO search for name in the map
        if(evt.getKeyCode() == 10) { /* enter key */
            searchTriggered();
        }
    }//GEN-LAST:event_searchFieldKeyPressed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        // TODO make relevant changes when tab is changed
        int selectedTab = jTabbedPane1.getSelectedIndex();
        if(selectedTab == 0) {
            commonTextBar.setText("click on a friend to display the albums!");
            searchField.setText("Search Friends");
        }
        else if(selectedTab == 1) {
            commonTextBar.setText("select an album to display photos!");
            searchField.setText("Search Albums");
        }
        else if(selectedTab == 2) {
            commonTextBar.setText("enter the id of the page");
            searchField.setText("Seacrch Page");
        }
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO show downloads frame
        this.DownloadFrame.setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO show file chooser
        new DirectoryChooser();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO exit
        System.exit(0);
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO show friends tab
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        // TODO show albums tab
        jTabbedPane1.setSelectedIndex(1);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO open help page
        String src = "http://tad.eu5.org/#help";
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                URL url = new URL(src);
                desktop.browse(url.toURI());
            } catch(Exception e) { }
        }
        
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        // TODO show pages tab  
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void loadThumbnailsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadThumbnailsActionPerformed
        // TODO edit show thumbnail
        boolean showThumbnail = loadThumbnails.isSelected();
        if(showThumbnail) {
            this.loadThumnailsStatic = true;
            updateThumbFile(true);
        }
        else {
            this.loadThumnailsStatic = false;
            updateThumbFile(false);
        }
    }//GEN-LAST:event_loadThumbnailsActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        // TODO open about frame
        if(aboutFlag) {
            aboutFlag = false;
        }
        else {
            new AboutFrame();
            aboutFlag = true;
        }
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        // TODO call update frame
        new UpdateCheck();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    /**
     * searchTriggered
     */
    private void searchTriggered() {
        // TODO search
        int selectedTab = jTabbedPane1.getSelectedIndex();
        String searchFor = searchField.getText().toLowerCase();
        if(searchFor == null || searchFor.length() <= 0) {
            commonTextBar.setText("please enter some string to search for!");
            return;
        }
        if(selectedTab == 0) { // friends
            new SearchFriend(searchFor).start(); /* thread */
        }
        else if(selectedTab == 1) { // albums
            new SearchAlbum(searchFor).start(); /* thread */
        }
        else if(selectedTab == 2) { // pages
            page_loader.setVisible(true);
            PagesPanel.removeAll();
            PagesPanel.repaint();
            new FetchPageAlbums(facebookClient, searchFor).start(); /* thread */
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AlbumsPanel;
    private javax.swing.JScrollPane AlbumsScroll;
    private javax.swing.JPanel FriendsPanel;
    private javax.swing.JScrollPane FriendsScroll;
    private javax.swing.JPanel PagesPanel;
    private javax.swing.JScrollPane PagesScroll;
    private javax.swing.JLabel commonTextBar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JCheckBoxMenuItem loadThumbnails;
    private javax.swing.JMenu loader;
    private javax.swing.JLabel noAlbumSelected;
    private javax.swing.JMenu page_loader;
    private javax.swing.JButton searchButton;
    private javax.swing.JTextField searchField;
    private javax.swing.JLabel searchForPage;
    private javax.swing.JLabel totalFriends;
    private javax.swing.JLabel userName;
    // End of variables declaration//GEN-END:variables
}
