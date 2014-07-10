/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package code;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.Album;
import com.restfb.types.FriendList;
import com.restfb.types.User;
import design.AcceptTokens;
import design.Skeleton;
import javax.swing.JFrame;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class ValidateTokens extends Thread{
    /* props */
    private String tokenValue = null;
    private JFrame acceptTokensFrame = null;
    private FacebookClient facebookClient = null;
    private Connection<FriendList> friendsList = null;
    private Connection<Album> albumsList = null;
    
    /**
     * ValidateTokens
     * @param acceptTokensFrame
     * @param tokenValue 
     */
    public ValidateTokens(JFrame acceptTokensFrame, String tokenValue) {
        this.acceptTokensFrame = acceptTokensFrame;
        this.tokenValue = tokenValue;
    }
    
    @Override
    public void run() {
        try {
            facebookClient = new DefaultFacebookClient(tokenValue);
            User user = facebookClient.fetchObject("/me", User.class);
            String firstName = user.getFirstName();
            String userId = user.getId();
            /* fetch friends list */
            friendsList = facebookClient.fetchConnection("me/friends", FriendList.class, Parameter.with("offset", "0"), Parameter.with("limit", "5000"));
            AcceptTokens.errorLabel.setText("all set!! shifting ..."); // display success msg
            acceptTokensFrame.setVisible(false); // hide the first frame
            new Skeleton(facebookClient, userId, firstName, friendsList); // call the other frame **imp            
        } catch(Exception e) {
            AcceptTokens.errorLabel.setText("authorization failed!! either the token expired or the value is incorrect."); // display err msg
            AcceptTokens.validateButtonStatic.setEnabled(true); // enable button
        }
    }
}
