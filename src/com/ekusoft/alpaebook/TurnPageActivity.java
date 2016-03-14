package com.ekusoft.alpaebook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.ekusoft.alpaebook.TurnPageView.OnTurnPageIndexListener;
import com.networkbench.agent.impl.NBSAppAgent;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore.Images.Thumbnails;
import android.util.Log;
import android.widget.TextView;

public class TurnPageActivity extends Activity implements OnTurnPageIndexListener{
	
	private final String TAG = this.getClass().getName();
	private static final String IMAGE_PATH = Environment.getRootDirectory() + "/pic";
	private TextView tvPage;
	private TurnPageView pageView;
	List<String> list = null;
	private int[] mBitmapIds = {
			R.drawable.p0, R.drawable.p1, R.drawable.p2, R.drawable.p3, R.drawable.p4, 
			R.drawable.p5, R.drawable.p6, R.drawable.p7, R.drawable.p8, R.drawable.p9
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
		
		// 初始化听云App探针的调用
		NBSAppAgent.setLicenseKey("3d061f6abd2a4302a4c95743dad777ee").withLocationServiceEnabled(true).start(this);
		// 若不需要采集地理位置信息（报表中将没有区域维度的数据）请使用以下代码
		// NBSAppAgent.setLicenseKey("3d061f6abd2a4302a4c95743dad777ee").start(this);
      		
		tvPage = (TextView)findViewById(R.id.tvPageIndex);
		pageView = (TurnPageView)findViewById(R.id.pageview);
		pageView.addTurnPageIndexChanged(this);
		
		list = getPictures(IMAGE_PATH); 
		Log.i(TAG, "path:" + IMAGE_PATH);
		
		// 获得所有要显示的bitmap
//		for(int i = 0; i < mBitmapIds.length ;i++) {
//			  资源文件下获得图片
//			 Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mBitmapIds[i]);
//			 pageView.setBitmaps(bitmap);
//			
//		}
		
		//获取某文件夹下的图片
		if (list != null) {
			for (int i = 0; i < list.size(); i++) {
				Bitmap bm = BitmapFactory.decodeFile(list.get(i)); 
				pageView.setBitmaps(bm);
			}
		}
		
    }
	@Override
	public void setCurPageIndex(int index) {
		// TODO Auto-generated method stub
		tvPage.setText("第 " + index + " 页");
	}
	
	
	/**
	 * 
	 * @param strPath 有获取的图片路径
	 * @return  strPath下的所有图片路径集合
	 */
	public List<String> getPictures(final String strPath) { 
	    List<String> list = new ArrayList<String>(); 
	    File file = new File(strPath); 
	    File[] allfiles = file.listFiles(); 
	    if (allfiles == null) { 
	      return null; 
	    } 
	    List<String> listTemp = new ArrayList<String>();
	    // 取得所有的图片
	    for(int i = 0; i < allfiles.length; i++) { 
	      final File fi = allfiles[i]; 
	      if(fi.isFile()) { 
	              int idx = fi.getPath().lastIndexOf("."); 
	              if (idx <= 0) { 
	                  continue; 
	              } 
	              String suffix = fi.getPath().substring(idx); 
	              if (suffix.toLowerCase().equals(".jpg") || suffix.toLowerCase().equals(".png")) { 
	            	  
	            	  listTemp.add(fi.getPath()); 
	              } 
	      } 
	   } 
	   // 按一定顺序存放图片
	   for(int i = 0; i<listTemp.size(); i++) {
		   for(int j = 0; j<listTemp.size(); j++) {
			   if (getFileName(listTemp.get(j)).equals("p" + i)) {
				list.add(listTemp.get(j));
				//listTemp.remove(j);
			}
		   }
	   }
	   
	   return list; 
	 }
	
	/**
	 * 
	 * @param pathname 路径名
	 * @return  返回文件名
	 */
	public String getFileName(String pathname) {  
        
        int start=pathname.lastIndexOf("/");  
        int end=pathname.lastIndexOf(".");  
        if(start!=-1 && end!=-1){  
            return pathname.substring(start+1,end);    
        }else{  
            return null;  
        }  
          
    }
}