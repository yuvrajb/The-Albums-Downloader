/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package code;

import design.Skeleton;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JScrollPane;

/**
 *
 * @author Manhattan
 */
public class SearchAlbum extends Thread {
    private Map fetchMap = null;
    private String searchFor = null;
    private JScrollPane albumsTab = null;
    
    /**
     * SearchFriend
     * @param searchFor 
     */
    public SearchAlbum(String searchFor) {
        this.searchFor = searchFor;
        this.fetchMap = LoadAlbumsList.albumNames;
        this.albumsTab = Skeleton.albumsScrollStatic;
    }
    
    @Override
    public void run() {
        if(fetchMap.containsKey(searchFor)) {
            int posY = ((int)fetchMap.get(searchFor));
            this.albumsTab.getVerticalScrollBar().setValue(posY);
            Skeleton.commonTextBarStatic.setText("yay!! album found");
        }
        else {
            Set keySet = fetchMap.keySet();
            Iterator itr = keySet.iterator();
            boolean found = false;
            while(itr.hasNext()) {
                String key = (String)itr.next();
                if(key.contains(searchFor)) {
                    found = true;
                    int posY = ((int)fetchMap.get(key));
                    this.albumsTab.getVerticalScrollBar().setValue(posY);
                    Skeleton.commonTextBarStatic.setText("yay!! album found");
                }
            }
            if(!found) {
                Skeleton.commonTextBarStatic.setText("no album with that name found!");
                Skeleton.searchFieldStatic.setText(""); /* clear text field */
            }
        }
    }
}
