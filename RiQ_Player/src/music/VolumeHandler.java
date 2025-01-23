package music;

import javax.sound.sampled.FloatControl;

public class VolumeHandler{

	
	private FloatControl gain;
	private FloatControl setting;
	
	private static VolumeHandler instance;
	public static VolumeHandler getInstance() {
		if(instance == null) instance = new VolumeHandler();
		return instance;
	}

	VolumeHandler() {
		setGain((float) -15.0);
	}

	/** set volume gain */
	private void setGain(float dB) {
		gain = (FloatControl) SoundController.getInstance().getClip().getControl(FloatControl.Type.MASTER_GAIN);
		this.gain.setValue(dB);
		setting = gain;
	}

	/** maintain setting volume */
	public void volumeSetting() {
		setGain((float) setting.getValue());
	}

	/** volume to 0 % */
	public void volumeMin() {
		setGain((float) -80.0);
	}

	/** volume to 100 % */
	public void volumeMax() {
		setGain((float) 0.0);
	}

	/** volume down */
	public void volumeDown() {
		float dB = (float) (setting.getValue() - 1.0);
		if(dB < (float) -80.0) dB = (float) -80.0;
		setGain(dB);
	}

	/** volume up */
	public void volumeUp() {
		float dB = (float) (setting.getValue() + 1.0);
		if(dB > (float) 0.0) dB = (float) 0.0;
		setGain(dB);
	}
}
