package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import connect.ConnectDB;
import dao.NhanVienDao;
import dao.ThuocDao;
import entity.NhanVien;
import entity.TaiKhoan;
import entity.Thuoc;
import format.EventedMenuSelected;
import format.ListMenu;
import format.Model_Menu;
import format.PanelBorder;
import format.PanelHeader;
import format.PanelMenuNV;
import format.PanelMenuQLy;

public class MainFrame extends JFrame {
	
	private PanelBorder pnlTong; //panel tổng
	private PanelMenuQLy pnlMenuQLy;	// menu bên trái(nếu là qly)	
	private PanelMenuNV pnlMenuNV; //neu la nv
	private PanelHeader pnlHeader;//panelHeader ở trên
	private PanelBorder pnlPhai;
	
	private JPanel pnlContent;
	
	private FormHoaDon frmHoaDon;
	private FormNhanVien frmNhanVien;
	private FormNhaCungCap frmNhaCungCap;
	private FormKhachHang frmKhachHang;
	private FormThuoc frmThuoc;
	private FormThongKe frmThongKe;
	private FormThongTin frmThongTin;
	private NhanVienDao nvDao;
	private ThuocDao thDao;
	
    public MainFrame(TaiKhoan tk) throws SQLException {
    	
    	try {
			ConnectDB.getInstance().connectDataBase();
			System.out.println("Succes connect to Form Main");
		}catch (SQLException e1) {
			e1.printStackTrace();
		}
		nvDao = new NhanVienDao();
    
    	setUndecorated(true);
    	setSize(1300, 700);
    	setLocationRelativeTo(null);
    	
    	// Khởi tạo các component
		pnlTong = new PanelBorder(); //Panel tổng
		pnlTong.setLayout(new BorderLayout()); // Set layout cho panelBorder
		NhanVien nv=nvDao.layNhanVienTheoMa(tk.getMaNV());
        pnlHeader = new PanelHeader(this, nv,tk);
        pnlHeader.setBackground(Color.decode("#FEFBF6"));
		
		frmThongTin = new FormThongTin();
		frmHoaDon = new FormHoaDon(tk);
		frmNhanVien = new FormNhanVien();
		frmNhaCungCap = new FormNhaCungCap();
		frmKhachHang = new FormKhachHang();
		frmThuoc = new FormThuoc(nvDao.layNhanVienTheoMa(tk.getMaNV()));
		frmThongKe = new FormThongKe();
		
		add(pnlTong);
		
		// Panel bên trái: MENU	
        
        if(nv.isQuanLy()==true) {
        	pnlMenuQLy=new PanelMenuQLy();
        	pnlTong.add(pnlMenuQLy, BorderLayout.WEST);
        	pnlMenuQLy.movingFrame(this);   
            pnlMenuQLy.setPreferredSize(new Dimension(200, getHeight()));
            System.out.println("Quản Lý Đăng Nhập Thành Công!");
            pnlMenuQLy.addEventMenuSelected(new EventedMenuSelected() {
    			
    			@Override
    			public void selected(int index) {
    				System.out.println("Selected index: " + index);
    				if(index == 0) {
    					showForm(frmThongTin);
    				}
    				if(index == 2) {
    					showForm(frmThuoc);
    				}
    				else if(index==4){
    					showForm(frmHoaDon);
    				}
    				else if(index == 6) {
    					showForm(frmNhanVien);
    				}
    				else if(index == 8) {
    					showForm(frmKhachHang);
    				}
    				else if(index == 10) {
    					showForm(frmNhaCungCap);
    				}
    				else if(index == 12) {
    					showForm(frmThongKe);
    				}
    				
    			}
    		});
        }else {
        	pnlMenuNV=new PanelMenuNV();
        	pnlTong.add(pnlMenuNV, BorderLayout.WEST);
        	pnlMenuNV.movingFrame(this);   
            pnlMenuNV.setPreferredSize(new Dimension(200, getHeight()));
            System.out.println("Nhân Viên "+ tk.getMaNV()+" Đăng Nhập Thành Công!");
            pnlMenuNV.addEventMenuSelected(new EventedMenuSelected() {
    			
    			@Override
    			public void selected(int index) {
    				System.out.println("Selected index: " + index);
    				if(index == 0) {
    					showForm(frmThongTin);
    				}
    				if(index == 2) {
    					showForm(frmThuoc);
    				}
    				else if(index==4){
    					showForm(frmHoaDon);
    				}
    				else if(index == 6) {
    					showForm(frmKhachHang);
    				}		
    				
    			}
    		});
        }
        
        // Panel bên phải: Header + Content
        pnlPhai = new PanelBorder();
        pnlPhai.setOpaque(false);
        pnlPhai.setLayout(new GridBagLayout());
        pnlTong.add(pnlPhai, BorderLayout.CENTER);

        // Header (phần trên bên phải)
        GridBagConstraints headerConstraints = new GridBagConstraints();
        headerConstraints.gridx = 0;
        headerConstraints.gridy = 0;
        headerConstraints.fill = GridBagConstraints.BOTH;
        headerConstraints.weightx = 1.0;
        headerConstraints.weighty = 0.04; // Chiếm 10% chiều cao của phần bên phải
        pnlPhai.add(pnlHeader, headerConstraints);
        
        // Phần còn lại (phần dưới bên phải)
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.decode("#8CB9BD"));
        GridBagConstraints contentConstraints = new GridBagConstraints();
        contentConstraints.gridx = 0;
        contentConstraints.gridy = 1;
        contentConstraints.fill = GridBagConstraints.BOTH;
        contentConstraints.weightx = 1.0;
        contentConstraints.weighty = 1.0 - 0.04; // Chiếm 90% chiều cao của phần bên phải
        pnlPhai.add(contentPanel, contentConstraints);
        
        pnlContent = new JPanel(); 
        pnlContent.setLayout(new BorderLayout());
        pnlContent.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(pnlContent, BorderLayout.CENTER);
        
        // Di chuyển app
        

    }
    
    public void showForm(Component f) {
		pnlContent.removeAll();
		pnlContent.add(f, BorderLayout.CENTER);
		pnlContent.repaint();
		pnlContent.revalidate();
	}

    public static void main(String[] args) throws SQLException {
        new MainFrame(null).setVisible(true);
    }
}
