package com.example.ui.components

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.AsyncImage
import com.example.data.model.VideoEntity
import com.example.ui.theme.DarkBg
import com.example.ui.theme.DarkSurface
import com.example.ui.theme.YtBrandOrange
import com.example.ui.theme.YtRose
import kotlinx.coroutines.delay

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerView(
    video: VideoEntity,
    channelTitle: String,
    queue: List<VideoEntity>,
    onClose: () -> Unit,
    onSelectNextVideo: (VideoEntity) -> Unit,
    onToggleFavorite: (Boolean) -> Unit,
    onToggleSaved: (Boolean) -> Unit,
    onHideVideo: () -> Unit,
    onBlockChannel: () -> Unit,
    onPlayingStateChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? ComponentActivity

    var isPlaying by remember { mutableStateOf(true) }
    var isLooping by remember { mutableStateOf(false) }
    var isFullscreen by remember { mutableStateOf(false) }
    var currentTimeSec by remember { mutableIntStateOf(0) }
    var durationSec by remember { mutableIntStateOf(if (video.durationSeconds > 0) video.durationSeconds else 600) }
    var isSeeking by remember { mutableStateOf(false) }
    var seekProgress by remember { mutableFloatStateOf(0f) }

    var webViewRef by remember { mutableStateOf<WebView?>(null) }

    LaunchedEffect(isPlaying) {
        onPlayingStateChanged(isPlaying)
    }

    LaunchedEffect(isPlaying, video.videoId) {
        while (isPlaying) {
            delay(1000)
            if (!isSeeking) {
                currentTimeSec += 1
                if (currentTimeSec >= durationSec && durationSec > 0) {
                    if (isLooping) {
                        currentTimeSec = 0
                        webViewRef?.loadUrl("javascript:player.seekTo(0);player.playVideo();")
                    } else {
                        isPlaying = false
                    }
                }
            }
        }
    }

    DisposableEffect(isFullscreen) {
        if (isFullscreen) {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        } else {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
        onDispose {
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            onPlayingStateChanged(false)
        }
    }

    val htmlContent = remember(video.videoId) {
        """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
            <style>
                * { margin: 0; padding: 0; box-sizing: border-box; background: #000000; overflow: hidden; }
                html, body { width: 100%; height: 100%; }
                iframe { width: 100%; height: 100%; border: 0; }
            </style>
        </head>
        <body>
            <div id="player"></div>
            <script>
                var tag = document.createElement('script');
                tag.src = "https://www.youtube.com/iframe_api";
                var firstScriptTag = document.getElementsByTagName('script')[0];
                firstScriptTag.parentNode.insertBefore(tag, firstScriptTag);

                var player;
                function onYouTubeIframeAPIReady() {
                    player = new YT.Player('player', {
                        height: '100%',
                        width: '100%',
                        videoId: '${video.videoId}',
                        host: 'https://www.youtube-nocookie.com',
                        playerVars: {
                            'autoplay': 1,
                            'controls': 0,
                            'rel': 0,
                            'modestbranding': 1,
                            'playsinline': 1,
                            'fs': 0,
                            'disablekb': 1,
                            'iv_load_policy': 3
                        },
                        events: {
                            'onReady': onPlayerReady
                        }
                    });
                }
                function onPlayerReady(event) {
                    event.target.playVideo();
                }
            </script>
        </body>
        </html>
        """.trimIndent()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBg)
    ) {
        if (isFullscreen) {
            Box(modifier = Modifier.fillMaxSize()) {
                AndroidView(
                    factory = { ctx ->
                        WebView(ctx).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                            settings.apply {
                                javaScriptEnabled = true
                                domStorageEnabled = true
                                mediaPlaybackRequiresUserGesture = false
                                cacheMode = WebSettings.LOAD_DEFAULT
                            }
                            webViewClient = WebViewClient()
                            webChromeClient = WebChromeClient()
                            loadDataWithBaseURL("https://www.youtube-nocookie.com", htmlContent, "text/html", "UTF-8", null)
                            webViewRef = this
                        }
                    },
                    modifier = Modifier.fillMaxSize()
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter)
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { isFullscreen = false },
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                            .testTag("exit_fullscreen_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FullscreenExit,
                            contentDescription = "إنهاء ملء الشاشة",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = video.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    )

                    IconButton(
                        onClick = onClose,
                        modifier = Modifier
                            .background(Color.Black.copy(alpha = 0.6f), CircleShape)
                            .testTag("close_player_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "إغلاق",
                            tint = Color.White
                        )
                    }
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                // Top Action Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onClose,
                        modifier = Modifier.testTag("player_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "الرجوع",
                            tint = Color.White
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = channelTitle,
                            style = MaterialTheme.typography.titleSmall,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "مشاهدة آمنة للأطفال 🛡️",
                            style = MaterialTheme.typography.labelSmall,
                            color = YtBrandOrange
                        )
                    }

                    IconButton(
                        onClick = { isFullscreen = true },
                        modifier = Modifier.testTag("enter_fullscreen_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Fullscreen,
                            contentDescription = "تكبير الشاشة",
                            tint = Color.White
                        )
                    }
                }

                // 16:9 Video Player View
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f)
                        .background(Color.Black)
                ) {
                    AndroidView(
                        factory = { ctx ->
                            WebView(ctx).apply {
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT
                                )
                                settings.apply {
                                    javaScriptEnabled = true
                                    domStorageEnabled = true
                                    mediaPlaybackRequiresUserGesture = false
                                }
                                webViewClient = WebViewClient()
                                webChromeClient = WebChromeClient()
                                loadDataWithBaseURL("https://www.youtube-nocookie.com", htmlContent, "text/html", "UTF-8", null)
                                webViewRef = this
                            }
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // Video Details Header
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = video.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            lineHeight = 22.sp,
                            textDirection = TextDirection.Rtl
                        ),
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = channelTitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.LightGray,
                            fontWeight = FontWeight.SemiBold
                        )

                        if (!video.viewCountText.isNullOrBlank()) {
                            Text(
                                text = video.viewCountText,
                                style = MaterialTheme.typography.labelMedium,
                                color = YtBrandOrange
                            )
                        }
                    }
                }

                // Seek Bar with Timestamps
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    val progressValue = if (durationSec > 0) {
                        (currentTimeSec.toFloat() / durationSec.toFloat()).coerceIn(0f, 1f)
                    } else 0f

                    Slider(
                        value = if (isSeeking) seekProgress else progressValue,
                        onValueChange = {
                            isSeeking = true
                            seekProgress = it
                        },
                        onValueChangeFinished = {
                            isSeeking = false
                            val targetSec = (seekProgress * durationSec).toInt()
                            currentTimeSec = targetSec
                            webViewRef?.loadUrl("javascript:player.seekTo($targetSec);")
                        },
                        colors = SliderDefaults.colors(
                            thumbColor = YtBrandOrange,
                            activeTrackColor = YtBrandOrange,
                            inactiveTrackColor = Color.DarkGray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("player_seek_slider")
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val currentMin = currentTimeSec / 60
                        val currentSec = currentTimeSec % 60
                        val totalMin = durationSec / 60
                        val totalSec = durationSec % 60

                        Text(
                            text = String.format("%02d:%02d", currentMin, currentSec),
                            style = MaterialTheme.typography.labelSmall,
                            color = YtBrandOrange,
                            fontWeight = FontWeight.Bold
                        )

                        val remaining = (durationSec - currentTimeSec).coerceAtLeast(0)
                        Text(
                            text = "متبقي: " + String.format("%02d:%02d", remaining / 60, remaining % 60),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.LightGray
                        )

                        Text(
                            text = String.format("%02d:%02d", totalMin, totalSec),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color.Gray
                        )
                    }
                }

                // Primary Playback Controls Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            currentTimeSec = (currentTimeSec - 10).coerceAtLeast(0)
                            webViewRef?.loadUrl("javascript:player.seekTo($currentTimeSec);")
                        },
                        modifier = Modifier.size(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastRewind,
                            contentDescription = "ترجيع 10 ثواني",
                            tint = Color.White
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Surface(
                        color = YtBrandOrange,
                        shape = CircleShape,
                        shadowElevation = 6.dp,
                        modifier = Modifier
                            .size(64.dp)
                            .testTag("player_play_pause_button")
                            .clickable {
                                isPlaying = !isPlaying
                                if (isPlaying) {
                                    webViewRef?.loadUrl("javascript:player.playVideo();")
                                } else {
                                    webViewRef?.loadUrl("javascript:player.pauseVideo();")
                                }
                            }
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                contentDescription = if (isPlaying) "إيقاف مؤقت" else "تشغيل",
                                tint = Color.White,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    IconButton(
                        onClick = {
                            currentTimeSec = (currentTimeSec + 10).coerceAtMost(durationSec)
                            webViewRef?.loadUrl("javascript:player.seekTo($currentTimeSec);")
                        },
                        modifier = Modifier.size(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastForward,
                            contentDescription = "تقديم 10 ثواني",
                            tint = Color.White
                        )
                    }
                }

                // Secondary Action Buttons Row
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onToggleFavorite(!video.isFavorite) },
                        modifier = Modifier.testTag("player_favorite_toggle")
                    ) {
                        Icon(
                            imageVector = if (video.isFavorite) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "المفضلة",
                            tint = if (video.isFavorite) YtRose else Color.White
                        )
                    }

                    IconButton(
                        onClick = { isLooping = !isLooping },
                        modifier = Modifier.testTag("player_loop_toggle")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Repeat,
                            contentDescription = "تكرار",
                            tint = if (isLooping) YtBrandOrange else Color.Gray
                        )
                    }

                    IconButton(
                        onClick = { onToggleSaved(!video.isSavedByParent) },
                        modifier = Modifier.testTag("player_save_parent_toggle")
                    ) {
                        Icon(
                            imageVector = if (video.isSavedByParent) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "حفظ للأهل",
                            tint = if (video.isSavedByParent) YtBrandOrange else Color.Gray
                        )
                    }

                    IconButton(
                        onClick = onHideVideo,
                        modifier = Modifier.testTag("player_hide_video_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = "إخفاء الفيديو",
                            tint = Color.LightGray
                        )
                    }

                    IconButton(
                        onClick = onBlockChannel,
                        modifier = Modifier.testTag("player_block_channel_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Shield,
                            contentDescription = "حظر القناة",
                            tint = YtRose
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Up Next Queue
                if (queue.isNotEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "التالي في قائمة الأمان 🌟",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Text(
                                text = "${queue.size} فيديوهات معتمدة",
                                style = MaterialTheme.typography.labelSmall,
                                color = Color.Gray
                            )
                        }

                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            item { Spacer(modifier = Modifier.width(4.dp)) }
                            items(queue.filter { it.videoId != video.videoId }) { queued ->
                                Card(
                                    modifier = Modifier
                                        .width(180.dp)
                                        .clickable { onSelectNextVideo(queued) }
                                        .testTag("queue_video_${queued.videoId}"),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = DarkSurface)
                                ) {
                                    Column {
                                        val qThumb = queued.thumbnail ?: "https://i.ytimg.com/vi/${queued.videoId}/mqdefault.jpg"
                                        AsyncImage(
                                            model = qThumb,
                                            contentDescription = queued.title,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .aspectRatio(16f / 9f)
                                        )
                                        Text(
                                            text = queued.title,
                                            style = MaterialTheme.typography.bodySmall.copy(
                                                fontSize = 12.sp,
                                                lineHeight = 16.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            color = Color.White,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(8.dp)
                                        )
                                    }
                                }
                            }
                            item { Spacer(modifier = Modifier.width(4.dp)) }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
