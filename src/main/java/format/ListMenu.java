package format;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

public class ListMenu<E> extends JList<E>{
	
	private final DefaultListModel defaultLstModel; //lưu trữ và quản lý dữ liệu của danh sách
	
	private int selectedIndex = -1;
	
	private EventedMenuSelected event;
	public void addEventMenuSelected(EventedMenuSelected event) {
		this.event = event;
	}
	
	public ListMenu() {
		defaultLstModel = new DefaultListModel();
		setModel(defaultLstModel);
		setSize(getWidth(), getHeight());
		
		//sự kiện cho JList
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(SwingUtilities.isLeftMouseButton(e)) {
					int index = locationToIndex(e.getPoint()); //lấy vị trí chuột, chuyển thành sô chỉ mục trong list
					Object o = defaultLstModel.getElementAt(index);		//lấy đối tượng tự index đó ra
					if(o instanceof Model_Menu) {
						Model_Menu menu  = (Model_Menu)o;
						if(menu.getType() == Model_Menu.MenuType.MENU) {
							selectedIndex = index;
							if(event!=null) {
								event.selected(index);
							}
						}
					}
					else {
						//selectedIndex = index;
					}
					repaint();
				}
			}
		});
	}
	//ghi đề method getCellRenderer() trả về ListCellRenderer(tạo và cấu hình thành phần hiển thị cho mỗi mục trong một danh sách (JList)) tuỳ chỉnh
	@Override
	public ListCellRenderer<? super E> getCellRenderer() {
		
		return new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
					boolean cellHasFocus) {
				Model_Menu data;
				if(value instanceof Model_Menu) { // nếu value là một đối tượng của lớp model menu thì gán data = value, không thì tạo model menu loại EMPTY
					data = (Model_Menu) value;
				}
				else{
					data = new Model_Menu("", value + "", Model_Menu.MenuType.EMPTY);
				}
				MenuItem item = new MenuItem(data);
				item.setSelected(selectedIndex == index);
				return item;
			}
		};
	}
	public void addItem(Model_Menu data) {
		defaultLstModel.addElement(data);
	}
}
