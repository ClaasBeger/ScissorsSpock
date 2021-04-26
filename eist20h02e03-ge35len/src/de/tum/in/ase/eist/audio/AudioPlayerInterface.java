package de.tum.in.ase.eist.audio;

public interface AudioPlayerInterface {

    public static final String BACKGROUND_MUSIC_FILE = "RPSLSsong.wav";
    public static final String CRASH_MUSIC_FILE = "wohoo.wav";
    
    String getCrashSoundFilePath();
    String getBackgroundMusicFilePath();
    void playBackgroundMusic();
    void stopBackgroundMusic();
    boolean isPlayingBackgroundMusic();
    void playCrashSound();
    boolean getCrashSoundPlayed();
	void playWinSound();

}
