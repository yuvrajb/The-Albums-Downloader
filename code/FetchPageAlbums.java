/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package code;

import com.restfb.FacebookClient;
import design.Skeleton;

/**
 *
 * @author Yuvraj Singh Babra
 */
public class FetchPageAlbums extends Thread{
    /* props */
    private FacebookClient facebookClient = null;
    private String searchFor = null;
    
    /**
     * FetchPageAlbums
     * @param facebookClient
     * @param searchFor 
     */
    public FetchPageAlbums(FacebookClient facebookClient, String searchFor) {
        this.facebookClient = facebookClient;
        this.searchFor = searchFor;
    }
    
    @Override
    public void run() {
        try {
            Skeleton.searchForPageStatic.setVisible(false);
            Skeleton.commonTextBarStatic.setText("searching albums of " + searchFor);
            new LoadAlbumsList(facebookClient, searchFor, searchFor, 1).start(); /* thread */
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
