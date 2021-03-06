/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package design;

import code.PerformUpdateCheck;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class UpdateCheck extends javax.swing.JFrame {
    /* props */
    private String version = "1.0";
    
    /**
     * Creates new form UpdateCheck
     */
    public UpdateCheck() {
        initComponents();
        setIcon();
        customChanges();
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
     * customChanges()
     */
    public void customChanges() {
        this.setResizable(false);
        this.setVisible(true);
        this.setTitle("Updates - The Albums Downloader");
        this.setLocationRelativeTo(null);
        try {
            this.setContentPane(new JLabel(new ImageIcon(AcceptTokens.class.getClassLoader().getResource("resources/back.jpg"))));
            this.add(jLabel2);
            this.add(currVersion);
            this.add(jProgressBar1);
            this.add(updateInfo);
            this.add(downloadButton);
        } catch(Exception e) { }
        currVersion.setText("Current Version " + version);
        updateInfo.setVisible(false);
        downloadButton.setVisible(false);
    }
    
    /**
     * intelligence
     */
    public void intelligence() {
        new PerformUpdateCheck(version, updateInfo, downloadButton, jProgressBar1).start(); /* thread */
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
        jLabel2 = new javax.swing.JLabel();
        currVersion = new javax.swing.JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        updateInfo = new javax.swing.JLabel();
        downloadButton = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/stamp.png"))); // NOI18N

        currVersion.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        currVersion.setForeground(new java.awt.Color(255, 255, 255));
        currVersion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        currVersion.setText("Current Version 1.0");

        jProgressBar1.setToolTipText("Contacting Server");
        jProgressBar1.setValue(75);

        updateInfo.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        updateInfo.setForeground(new java.awt.Color(255, 255, 255));
        updateInfo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        updateInfo.setText("..");

        downloadButton.setText("Download");
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(currVersion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(downloadButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                        .addComponent(updateInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(77, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(currVersion)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addGap(30, 30, 30)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(updateInfo)
                .addGap(18, 18, 18)
                .addComponent(downloadButton)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        // TODO shit to page
        String src = "http://tad.eu5.org/#download";
        Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
        if(desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
            try {
                URL url = new URL(src);
                desktop.browse(url.toURI());
            } catch(Exception e) { }
        }
    }//GEN-LAST:event_downloadButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel currVersion;
    private javax.swing.JButton downloadButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JLabel updateInfo;
    // End of variables declaration//GEN-END:variables
}
