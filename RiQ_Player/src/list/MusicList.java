package list;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import _main.Panel;
import music.SoundController;

@SuppressWarnings("serial")
public class MusicList extends JList<Object> {

	private JList<Object> list;
	private JScrollPane scrollPane;
	
	public MusicList() {}
	private static MusicList instance;
	public static MusicList getInstance() {
		if(instance == null) instance = new MusicList();
		return instance;
	}
	
	/** music list setting */
	public void setList(Object[] data) {
		if(list == null) {
			list = new JList<Object>(data);
			list.setVisibleRowCount(3);
			scrollPane = new JScrollPane(list);
			scrollPane.setPreferredSize(new Dimension(450,70));
			list.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			list.setForeground(Color.WHITE);
			list.setBackground(Color.black);
			Panel.getInstance().add(scrollPane);
			action();
		}else {
			list.setListData(data);
		}
	}
	
	/** select list component action */
	public void action() {
		list.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				SoundController.getInstance().play(list.getSelectedIndex());
			}
		});
	}
	
}
