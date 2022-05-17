package com.example.pretest_sk_5_0.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pretest_sk_5_0.R
import com.example.pretest_sk_5_0.databinding.ActivityVideoBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.google.android.exoplayer2.util.Util

class VideoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVideoBinding

    private var movPlayer: ExoPlayer? = null
    // get api video
    private val videoURL =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // call function video player
        initVideoPlayer()
    }

    private fun initVideoPlayer() {
        // Create instance
        movPlayer = ExoPlayer.Builder(this).build()
        // Bind Player ke view
        binding.playerView.player = movPlayer
        // Set player when ready
        movPlayer!!.playWhenReady = true
        // Set media source
        movPlayer!!.setMediaSource(buildMediaSource())
        // Prepare player
        movPlayer!!.prepare()
    }
    // on start do...
    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initVideoPlayer()
        }
    }
    // on resume do...
    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || movPlayer == null) {
            initVideoPlayer()
        }
    }
    // on pause do...
    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }
    // on stop do...
    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        if (movPlayer == null) {
            return
        }
        //release player when done
        movPlayer!!.release()
        movPlayer = null
    }

    //creating mediaSource
    private fun buildMediaSource(): MediaSource {
        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

        // Create a progressive media source pointing to a stream uri.
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoURL))

        return mediaSource
    }
}