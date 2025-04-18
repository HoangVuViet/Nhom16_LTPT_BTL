package ui;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FormThanhToan extends JPanel{
	private JComboBox<String> hinhThucTT;
	private double soTienTT;
	
	public FormThanhToan() {
		JLabel lblHinhThucTT=new JLabel("Chọn hình thức thanh toán:");
		hinhThucTT=new JComboBox<String>();
		hinhThucTT.addItem("Tiền mặt");
		hinhThucTT.addItem("Chuyển khoản");
		hinhThucTT.addItem("Quẹt thẻ");
		
	}
}
