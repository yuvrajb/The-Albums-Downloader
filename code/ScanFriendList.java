/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package code;

import com.restfb.Connection;
import com.restfb.FacebookClient;
import com.restfb.types.FriendList;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Manhattan
 */
public class ScanFriendList extends Thread implements ActionListener{
    /* props */
    public static Map MyFriends = new HashMap();
    private static JPanel panelReference = Skeleton.friendsPanelStatic;
    private static JScrollPane scrollReference = Skeleton.friendsScrollStatic;
    private static double x = 6;
    private static double y = 10;
    private FacebookClient facebookClient = null;
    private String userId = null;
    private Connection<FriendList> friendList = null;
    private List friendsList = null;
    private Iterator itr = null;
    private List buttons = new ArrayList(); 
    private List labels = new ArrayList();
    
    /**
     * ScanFriendList
     * @param facebookClient
     * @param userId
     * @param friendList 
     */
    public ScanFriendList(FacebookClient facebookClient, String userId, Connection<FriendList> friendList) {
        this.facebookClient = facebookClient;
        this.userId = userId;
        this.friendList = friendList;
    }
    
    @Override
    public void run() {
        if(x == 6 && y == 10) {
            /* display logged in users button */
            JButton me = new JButton(userId + ":" + "me");
            me.setFont(new Font("Arial", Font.PLAIN, 0));
            me.setSize(150, 150);
            me.setLocation((int)x, (int)y);
            JLabel meL = new JLabel("Me");
            meL.setFont(new Font("Segoe UI", Font.BOLD, 12));
            meL.setSize(150, 25);
            meL.setLocation((int)x, 150 + (int)y);
            panelReference.add(me);
            panelReference.add(meL);
            me.addActionListener(this); /* add action listener */
            buttons.add(me);
            labels.add(meL);
            x = 150 + 15;
            /* insert into map */
            MyFriends.put("me", 10);
        }
        
        /* call the function to do rest of the task */
        displayMyFriends();
    }
    
    public void displayMyFriends() {
        /* fetch info about more pages */
        boolean more = this.friendList.hasNext();
        String nextURL = this.friendList.getNextPageUrl();
        int count = 0;
        try {
            /* fetch data from object */
            friendsList = this.friendList.getData();
            count = friendsList.size();
            itr = friendsList.iterator(); 
            /* update total friends in skeleton */
            updateTotalFriends(count, more);
            JButton tempB; 
            JLabel tempL;
            /* display friends */
            boolean firstClick = false;
            while(itr.hasNext()) {
                FriendList temp = (FriendList)itr.next();
                String name = temp.getName();
                String id = temp.getId();
                tempB =  new JButton(id + ":" + name);
                tempB.setSize(150,150);
                tempB.setFont(new Font("Arial", Font.PLAIN, 0));
                tempB.setLocation((int)Math.floor(x), (int)Math.floor(y));
                tempL = new JLabel(name);
                tempL.setSize(150, 25);
                tempL.setFont(new Font("Segoe UI", Font.BOLD, 12));
                tempL.setLocation((int)Math.floor(x), (int)Math.floor(y) + 150);
                /* insert into map */
                MyFriends.put(name.toLowerCase(), (int)Math.ceil(y));
                /* add action listener */
                tempB.addActionListener(this);
                /* add to respective lists */
                buttons.add(tempB);
                labels.add(tempL);
                x += (150 + 10);
                if(x + 160 > 976) {
                    x = 6.33;
                    y += 185;
                    panelReference.setPreferredSize(new Dimension(993, (int)y));
                    if(!firstClick) {
                        firstClick = true;
                        //scrollReference.getVerticalScrollBar().setValue(0);
                    }
                }
                /* add default image */
                ImageIcon icon = new ImageIcon(Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/resources/photo.png")));
                tempB.setIcon(icon);
                tempB.setHorizontalAlignment(AbstractButton.CENTER);
                tempB.setVerticalTextPosition(AbstractButton.CENTER);
                /* add button to the skeleton */
                panelReference.add(tempB);
                panelReference.add(tempL);
                panelReference.repaint();
                panelReference.setPreferredSize(new Dimension(993, (int)y));
            }
        } catch(Exception e) {
            //
        } finally {
            if(x != 6.33) {
                y += 185;
            }
            panelReference.setPreferredSize(new Dimension(993, (int)y));
            if(Skeleton.loadThumnailsStatic)
                new CallToDisplayUserPicture(facebookClient, buttons).start(); /* thread */
        }
    }
    
    /**
     * updateTotalFriends
     * @param count
     * @param more 
     */
    public void updateTotalFriends(int count, boolean more) {
        JLabel countTillNow = Skeleton.totalFriendsStatic;
        String temp = countTillNow.getText();
        String[] tempArr = temp.split(" ");
        int tempCount = Integer.parseInt(tempArr[0]);
        String text = null;
        if(more) {
            text = "+ Friends";
        }
        else {
            text = " Friends";
        }
        count += tempCount;
        countTillNow.setText(count + text);
        /* update the context labels */
//        temp = Skeleton.contextLabel[0];
//        tempArr = temp.split(",");
//        tempArr[1] = count + text;
//        Skeleton.contextLabel[0] = tempArr[0] + "," + tempArr[1];
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        JButton source = (JButton)event.getSource();
        String content = source.getText();
        String temp[] = content.split(":");
        String userId = temp[0];
        String name = temp[1];
        new LoadAlbumsList(facebookClient, userId, name, 0).start(); /* thread */
        Skeleton.tabPane.setSelectedIndex(1); /* shift to albums tab */
        Skeleton.loaderStatic.setVisible(true);
        Skeleton.commonTextBarStatic.setText("loading albums list of " + name);
        /* update the context labels */
//        content = Skeleton.contextLabel[1];
//        temp = content.split(",");
//        temp[0] = name;
//        Skeleton.contextLabel[1] = temp[0] + "," + temp[1];
    }
}
