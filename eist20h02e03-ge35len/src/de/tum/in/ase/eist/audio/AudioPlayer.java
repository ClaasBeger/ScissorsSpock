package de.tum.in.ase.eist.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

/**
 * This class handles the background music played during the game
 *
 */
public class AudioPlayer implements AudioPlayerInterface {

    private MediaPlayer mediaPlayer;
    private boolean playingBackgroundMusic;
    public boolean crashSoundPlayed = false;

	/**
	 * Constructor, gets the media files from resources and sets the boolean
	 * playingBackgroundMusic false, which means that after game startup no
	 * music is being played.
	 */
	public AudioPlayer() {
		this.playingBackgroundMusic = false;
	}

    @Override
    public String getBackgroundMusicFilePath() {
        return BACKGROUND_MUSIC_FILE;
    }

    @Override
    public String getCrashSoundFilePath() {
	    return CRASH_MUSIC_FILE;
    }

    /**
	 * Checks if no music is currently running by checking the value of the
	 * boolean playingBackgroundMusic. Starts playing the background music in a
	 * new thread.
	 */
	public void playBackgroundMusic() {
		if (!this.playingBackgroundMusic) {
			this.playingBackgroundMusic = true;
			this.mediaPlayer = new MediaPlayer(loadAudioFile(getBackgroundMusicFilePath()));
			this.mediaPlayer.setAutoPlay(true);
			// Loop for the main music sound:
			this.mediaPlayer.setOnEndOfMedia(() -> AudioPlayer.this.mediaPlayer.seek(Duration.ZERO));
			this.mediaPlayer.play();
		}
	}
	
	private Media loadAudioFile(String fileName) {
	    URL musicSourceUrl = getClass().getClassLoader().getResource(fileName);
	    if(musicSourceUrl == null) {
            throw new RuntimeException("Please ensure that your resources folder contains the appropriate files for this exercise.");
        }
		String musicSource = musicSourceUrl.toString();
		return new Media(musicSource);
	}

	/**
	 * Checks if music is currently running and stops it if so
	 */
	public void stopBackgroundMusic() {
		if (this.playingBackgroundMusic) {
			this.playingBackgroundMusic = false;
			this.mediaPlayer.stop();
		}
	}

	/**
	 * Checks if the background music is playing by returning the boolean
	 * playingBackgroundMusic. This boolean is initially false after the game
	 * startup and set to true in the playBackgroundMusic() method.
	 * 
	 * @return true if background music is playing
	 * @return false if background music is not playing
	 */
	public boolean isPlayingBackgroundMusic() {
		return playingBackgroundMusic;
	}

    @Override
    public void playCrashSound() {
        // define new MediaPlayer variable for Bang Sound
        MediaPlayer mediaPlayerBang = new MediaPlayer(loadAudioFile(getCrashSoundFilePath()));
        mediaPlayerBang.play();
        // set boolean Variable to true
        this.crashSoundPlayed = true;
    }
    
    @Override
    public void playWinSound() {
        // define new MediaPlayer variable for Bang Sound
        MediaPlayer mediaPlayerBang = new MediaPlayer(loadAudioFile(WIN_MUSIC_FILE));
        mediaPlayerBang.play();
        // set boolean Variable to true
        this.crashSoundPlayed = true;
    }

    @Override
    public boolean getCrashSoundPlayed() {
        return this.crashSoundPlayed;
    }

}
