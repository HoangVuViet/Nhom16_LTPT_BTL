package ui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;


import format.PanelBorder;

public class FormThongKe extends PanelBorder implements ActionListener{
	
    private JCheckBox chkThuoc,chkNhanvien, chkLoiNhuan;
	private JPanel pnlGiua;
	private FormThongKeThuoc frmThongKeThuoc;
	private FormThongKeLoiNhuan frmThongKeThuocLoiNhuan;
	private FormThongKeNhanVien frmThongKeDoanhThu;

	public FormThongKe() {
		frmThongKeThuocLoiNhuan = new FormThongKeLoiNhuan();
		frmThongKeThuoc = new FormThongKeThuoc();
		frmThongKeDoanhThu = new FormThongKeNhanVien();
		
    	setLayout(new BorderLayout());
    	JPanel pnlTren = new JPanel();
    	add(pnlTren, BorderLayout.NORTH);
    	pnlTren.add(new JLabel("Thuốc: "));
    	pnlTren.add(chkThuoc = new JCheckBox());
    	pnlTren.add(new JLabel("Nhân viên: "));
    	pnlTren.add(chkNhanvien = new JCheckBox());
    	pnlTren.add(new JLabel("Lợi nhuận: "));
    	pnlTren.add(chkLoiNhuan = new JCheckBox());
    	
    	ButtonGroup btnGroup = new ButtonGroup();
    	btnGroup.add(chkNhanvien);btnGroup.add(chkLoiNhuan);btnGroup.add(chkThuoc);
    	
    	pnlGiua = new JPanel();
    	add(pnlGiua, BorderLayout.CENTER);
    	//event
    	chkNhanvien.addActionListener(this);
    	chkLoiNhuan.addActionListener(this);
    	chkThuoc.addActionListener(this);
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		if(o.equals(chkThuoc)) {
			System.out.println("thuoc");
			pnlGiua.removeAll(); 
	        pnlGiua.add(frmThongKeThuoc); 
	        pnlGiua.revalidate(); 
	        pnlGiua.repaint();
		}
		else if(o.equals(chkNhanvien)) {
			System.out.println("Nhân viên");
			pnlGiua.removeAll(); 
	        pnlGiua.add(frmThongKeDoanhThu); 
	        pnlGiua.revalidate(); 
	        pnlGiua.repaint();
		}
		else if(o.equals(chkLoiNhuan)) {
			System.out.println("loi nhuan");
			pnlGiua.removeAll(); 
	        pnlGiua.add(frmThongKeThuocLoiNhuan); 
	        pnlGiua.revalidate(); 
	        pnlGiua.repaint();
		}
		
	}
}
