package Bean;

import android.graphics.Bitmap;

/**
 * 作品类
 * @author Administrator
 *
 */
public class Work{

	private String srcPath;
	private String imgName;
	public Work(String srcPath, String imgName) {
		super();
		this.srcPath = srcPath;
		this.imgName = imgName;
	}
	/**
	 * @return the imgBitmap
	 */
	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath){
		this.srcPath = srcPath;
	}
	/**
	 * @return the imgName
	 */
	public String getImgName() {
		return imgName;
	}
	/**
	 * @param imgName the imgName to set
	 */
	public void setImgName(String imgName) {
		this.imgName = imgName;
	}
	
	
}
