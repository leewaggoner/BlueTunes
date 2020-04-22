package com.wreckingball.bluetunes.components

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import com.wreckingball.bluetunes.models.Song

class MusicPlayer {
    private var musicPlayer: MediaPlayer? = null
    private var currentSongId = -1L

    fun isPlaying() : Boolean {
        musicPlayer?.let {player ->
            return player.isPlaying
        }
        return false
    }

    fun playSong(context: Context, song: Song) {
        if (isPlaying()) {
            stopSong()
        }
        val player = getMusicPlayer()
        player.setDataSource(context, song.getSongUri())
        player.prepare()
        player.start()
        currentSongId = song.songId
    }

    fun stopSong() {
        musicPlayer?.stop()
        musicPlayer?.reset()
    }

    fun release() {
        if (isPlaying()) {
            stopSong()
        }
        musicPlayer?.release()
        musicPlayer = null
    }

    private fun getMusicPlayer() : MediaPlayer {
        if (musicPlayer == null) {
            musicPlayer = MediaPlayer()
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
            musicPlayer?.setAudioAttributes(attributes)
        }
        return musicPlayer as MediaPlayer
    }
}