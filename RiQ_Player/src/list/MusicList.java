package list;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import _main.Panel;
import music.Image;
import music.Sound;
import music.SoundController;

@SuppressWarnings("serial")
public class MusicList extends JFrame implements DropTargetListener, MouseListener, KeyListener {

	private JList<Object> list;
	private JScrollPane scrollPane;
	private boolean isPressDel;
	private boolean isPressIns;
	DropTarget target;
	Panel panel;
	
	public MusicList() {
		panel = Panel.getInstance();
		target = new DropTarget(list, DnDConstants.ACTION_COPY_OR_MOVE , this);
	}
	private static MusicList instance;
	public static MusicList getInstance() {
		if(instance == null) instance = new MusicList();
		return instance;
	}
	
	/** transform music list visible */
	public void setVisionPane() {
		if(scrollPane.isVisible())
			scrollPane.setVisible(false);
		else
			scrollPane.setVisible(true);
		Image.getInstance().setVisionImage();
	}

	/** music list setting */
	@SuppressWarnings("static-access")
	public void setList(Object[] data) {
		if(list == null) {
			list = new JList<Object>(data);
			list.setVisibleRowCount(5);
			scrollPane = new JScrollPane(list);
			scrollPane.setPreferredSize(new Dimension(344,295));
			scrollPane.setVisible(false);
			scrollPane.setVerticalScrollBarPolicy(scrollPane.VERTICAL_SCROLLBAR_NEVER);
			scrollPane.setHorizontalScrollBarPolicy(scrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			list.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			list.setForeground(Color.WHITE);
			list.setBackground(Color.GRAY);
			list.setSelectionBackground(Color.LIGHT_GRAY);
			list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			list.addMouseListener(this);
			list.addKeyListener(this);
			list.setDropTarget(target);
			Panel.getInstance().add(scrollPane);
		}else {
			list.setListData(data);
		}
	}
	
	/** now playing music focusing and set Horizontal Center  */
	public void setPosition(int idx) {
		list.setSelectedIndex(idx);
		scrollPane.getVerticalScrollBar().setValue(list.getSelectedIndex() * 21);
	}
	
	/** when list is suffled, scroll reset  */
	public void positionReset() {
		scrollPane.getVerticalScrollBar().setValue(0);
	}
	
	/** select list component delete */
	public void deleteChooseList() {
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				Sound.getInstance().deleteMusic(list.getSelectedIndex());
			}
		});
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getClickCount() == 2) {
			SoundController.getInstance().play(list.getSelectedIndex());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == 155  && !isPressIns && !list.isSelectionEmpty()) {
			isPressIns = true;
			Sound.getInstance().restoreMusic();
			list.clearSelection();
		}
		if(e.getKeyCode() == '\u007F' && !isPressDel && !list.isSelectionEmpty()) {
			isPressDel = true;
			Sound.getInstance().deleteMusic(list.getSelectedIndex());
			list.clearSelection();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == 155) {
			isPressIns = false;
		}
		if(e.getKeyCode() == '\u007F') {
			isPressDel = false;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void drop(DropTargetDropEvent dtde) {
		if(dtde.getDropAction() != 0 && DnDConstants.ACTION_COPY_OR_MOVE != 0) {
			dtde.acceptDrop(dtde.getDropAction());
			try {
				List<File> files = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
				for(File file : files) {
					File addFile = new File(System.getProperty("user.dir") + "\\res\\music\\" + file.getName());
					Sound.getInstance().addMusic(file, addFile);
				}
				dtde.dropComplete(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	}
	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
	}
	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	
}
