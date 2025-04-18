package format;

import java.awt.Image;
import javax.swing.ImageIcon;

public class ImageIconCustom extends ImageIcon {

	public ImageIconCustom(String f, int height, int width) {
		java.net.URL imgURL = getClass().getResource(f); // hoặc bỏ dấu `/` đầu tiên nếu dùng getClassLoader
		if (imgURL == null) {
			System.err.println("Không tìm thấy ảnh: " + f);
			return;
		}
		setImage(new ImageIcon(imgURL).getImage().getScaledInstance(height, width, Image.SCALE_AREA_AVERAGING));
	}
}
