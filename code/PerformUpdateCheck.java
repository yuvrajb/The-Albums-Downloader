/*
Copyright (C) 2014 Yuvraj Singh Babrah

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package code;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author Yuvraj Singh Babrah
 */
public class PerformUpdateCheck extends Thread {
    /* props */
    private String version = null;
    private JLabel updateInfo = null;
    private JButton downloadButton = null;
    private JProgressBar jProgressBar1 = null;
    
    /**
     * PerformUpdateCheck
     * @param version
     * @param updateInfo
     * @param downloadButton
     * @param jProgressBar1 
     */
    public PerformUpdateCheck(String version, JLabel updateInfo, JButton downloadButton, JProgressBar jProgressBar1) {
        this.version = version;
        this.updateInfo = updateInfo;
        this.downloadButton = downloadButton;
        this.jProgressBar1 = jProgressBar1;
    }
    
    @Override
    public void run() {
        try {
            URL url = new URL("http://tad.eu5.org/update.php");
            URLConnection urlC = url.openConnection();
            BufferedReader read = new BufferedReader(new InputStreamReader(urlC.getInputStream()));
            String data = read.readLine();
            String split[] = data.split(":");
            String update = split[0];
            String newVersion = split[1];
            jProgressBar1.setValue(100);
            if(update.equals("1")) {
                updateInfo.setVisible(true);
                updateInfo.setText("Update Available: " + newVersion);
                downloadButton.setVisible(true);
            }
            else {
                updateInfo.setVisible(true);
                updateInfo.setText("The Albums Downloader is up to date");
            }
        } catch(Exception e) { }     
    }
}
