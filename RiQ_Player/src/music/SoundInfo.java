package music;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URLDecoder;

import javax.swing.JLabel;

import _main.Panel;

public class SoundInfo {

	private JLabel title;
	private JLabel artist;
	private JLabel time;
	private int playTime;
	private String titleName;
	
	public int getPlayTime() {
		return playTime;
	}

	private SoundInfo() {
	}

	private static SoundInfo instance;
	public static SoundInfo getInstance() {
		if(instance == null) instance = new SoundInfo();
		return instance;
	}
	
	/** setting music info */
	@SuppressWarnings("deprecation")
	public void setMusicInfo(Panel panel) {
		String[] url = Sound.getInstance().getURL_Path().split("/");
		String fileName = "알 수 없음 - 알 수 없음";
		try {
			fileName = URLDecoder.decode(url[url.length - 1]);
		} catch (Exception e) {
			System.out.println("파일 이름 디코딩 실패");
		}
		setInfo(panel, fileName);
		setPlayTime();
	}
	
	public int getTitleLength() {
		return title.getText().getBytes().length;
	}
	
	public void setTitleShow() {
		title.setText(title.getText().substring(1));
		if(getTitleLength() < 48)
			title.setText(titleName);
			
	}
	
	private void setPlayTime() {
		playTime = SoundController.getInstance().getClipPlayTime();
	}

	/** setting Title and Artist info */
	private void setInfo(Panel panel, String fileName) {
		String[] fileInfo = getDivideName(fileName);
		setMusicTitle(panel, fileInfo[0]);
		setMusicArtist(panel, fileInfo[1]);
		setMusicLength(panel, (int) panel.getCurTime());
	}

	/** Divide Title and Artist from fileName */
	private String[] getDivideName(String fileName) {
		String[] fileInfo = new String[2];
		if (fileName.contains(" - ")) {
			fileInfo[0] = fileName.split(" - ")[1];
			fileInfo[0] = fileInfo[0].substring(0, fileInfo[0].length() - 4);
			fileInfo[1] = fileName.split(" - ")[0];
		} else {
			fileInfo[0] = fileName.substring(0, fileName.length() - 4);
			fileInfo[1] = "알 수 없음";
		}
		return fileInfo;
	}
	
	/** setting music text */
	private void setMusicTitle(Panel panel, String data) {
		if (title == null) {
			title = new JLabel("재생 중이 아님");
			title.setFont(new Font("맑은 고딕", Font.BOLD, 18));
			title.setForeground(Color.WHITE);
			setLabelStyle(title);
			panel.add(title);
		} else {
			titleName = data;
			title.setText(titleName);
		}
	}

	/** setting music text */
	private void setMusicArtist(Panel panel, String data) {
		if (artist == null) {
			artist = new JLabel("");
			artist.setFont(new Font("맑은 고딕", Font.BOLD, 13));
			artist.setForeground(Color.LIGHT_GRAY);
			setLabelStyle(artist);
			panel.add(artist);
		} else
			artist.setText(data);
	}
	
	/** setting music length text */
	public void setMusicLength(Panel panel, int curTime) {
		String text = String.format("%d:%02d                                                        %d:%02d", curTime/60,curTime%60,playTime/60,playTime%60);
		if (time == null) {
			time = new JLabel("");
			time.setFont(new Font("맑은 고딕",Font.PLAIN, 13));
			time.setForeground(Color.WHITE);
			setLabelStyle(time);
			time.setPreferredSize(new Dimension(350, 12));
			panel.add(time);
		} else
			time.setText(text);
	}

	/** setting JLabel to print text(Title, Artist) */
	private void setLabelStyle(JLabel label) {
		label.setPreferredSize(new Dimension(350, 30));
		label.setHorizontalAlignment(JLabel.CENTER);
	}

}
