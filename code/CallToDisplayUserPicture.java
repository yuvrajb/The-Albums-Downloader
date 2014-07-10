/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package code;

import com.restfb.FacebookClient;
import java.util.Iterator;
import java.util.List;
import javax.swing.JButton;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class CallToDisplayUserPicture extends Thread {
    /* props */
    private FacebookClient facebookClient = null;
    private List buttons = null;
    
    /**
     * CallToDisplayUserPicture
     * @param facebookClient
     * @param buttons 
     */
    public CallToDisplayUserPicture(FacebookClient facebookClient, List buttons) {
        this.facebookClient = facebookClient;
        this.buttons = buttons;
    }
    
    @Override
    public void run() {
        Iterator itr = buttons.iterator();
        while(itr.hasNext()) {
            JButton tempButton = (JButton)itr.next();
            String content = tempButton.getText();
            String split[] = content.split(":");
            String id = split[0];
            new DisplayUserProfilePicture(facebookClient, id, tempButton).start(); /* thread */
        }
    }
    
}
