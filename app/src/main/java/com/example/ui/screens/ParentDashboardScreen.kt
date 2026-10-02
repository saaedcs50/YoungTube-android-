package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.BlockedWordEntity
import com.example.data.model.ChannelEntity
import com.example.data.model.SettingsEntity
import com.example.data.model.UsageEntity
import com.example.data.model.VideoEntity
import com.example.ui.DashboardSection
import com.example.ui.theme.YtBrandOrange
import com.example.ui.theme.YtBrandSoft
import com.example.ui.theme.YtRose
import com.example.ui.theme.YtSkyBlue
import com.example.ui.theme.YtSkySoft

@Composable
fun ParentDashboardScreen(
    settings: SettingsEntity?,
    channels: List<ChannelEntity>,
    blockedWords: List<BlockedWordEntity>,
    savedVideos: List<VideoEntity>,
    hiddenVideos: List<VideoEntity>,
    todayUsage: UsageEntity?,
    activeSection: DashboardSection,
    onSelectSection: (DashboardSection) -> Unit,
    onCloseDashboard: () -> Unit,
    onLockDashboard: () -> Unit,
    onOpenAdBlockNotice: () -> Unit,
    onUpdateChildProfile: (String, Int?) -> Unit,
    onUpdateTimer: (Int, String, String) -> Unit,
    onResetTodayUsage: () -> Unit,
    onToggleChannel: (String, Boolean) -> Unit,
    onAddBlockedWord: (String) -> Unit,
    onRemoveBlockedWord: (String) -> Unit,
    onUnhideVideo: (String) -> Unit,
    onUnsaveVideo: (String) -> Unit,
    onToggleHideMusic: (Boolean) -> Unit,
    onAddCustomChannel: (String, String, String) -> Unit,
    onPlayVideo: (VideoEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 2.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // Top Bar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                color = YtBrandSoft,
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.size(36.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Shield,
                                        contentDescription = null,
                                        tint = YtBrandOrange,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "لوحة تحكم الأهل 🔒",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                color = YtSkySoft,
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier
                                    .clickable(onClick = onOpenAdBlockNotice)
                                    .testTag("open_adblock_dialog_button")
                            ) {
                                Text(
                                    text = "حجب الإعلانات 🛡️",
                                    color = YtSkyBlue,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }

                            IconButton(
                                onClick = onLockDashboard,
                                modifier = Modifier.testTag("lock_dashboard_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "قفل",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            IconButton(
                                onClick = onCloseDashboard,
                                modifier = Modifier.testTag("close_dashboard_button")
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = "شاشة الأطفال",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }

                    // Section Tabs Scrollable Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DashboardTabChip(
                            title = "ملف الطفل",
                            icon = Icons.Default.Person,
                            isSelected = activeSection == DashboardSection.CHILD_PROFILE,
                            onClick = { onSelectSection(DashboardSection.CHILD_PROFILE) }
                        )
                        DashboardTabChip(
                            title = "مواعيد التشغيل",
                            icon = Icons.Default.Timer,
                            isSelected = activeSection == DashboardSection.TIMER_SCHEDULE,
                            onClick = { onSelectSection(DashboardSection.TIMER_SCHEDULE) }
                        )
                        DashboardTabChip(
                            title = "تنظيم القنوات (${channels.size})",
                            icon = Icons.Default.Tv,
                            isSelected = activeSection == DashboardSection.CHANNELS_CURATION,
                            onClick = { onSelectSection(DashboardSection.CHANNELS_CURATION) }
                        )
                        DashboardTabChip(
                            title = "الفلترة والحجب",
                            icon = Icons.Default.FilterList,
                            isSelected = activeSection == DashboardSection.CONTENT_FILTERING,
                            onClick = { onSelectSection(DashboardSection.CONTENT_FILTERING) }
                        )
                        DashboardTabChip(
                            title = "المحفوظات (${savedVideos.size})",
                            icon = Icons.Default.Bookmark,
                            isSelected = activeSection == DashboardSection.SAVED_VIDEOS,
                            onClick = { onSelectSection(DashboardSection.SAVED_VIDEOS) }
                        )
                        DashboardTabChip(
                            title = "التوجيه الذكي",
                            icon = Icons.Default.TrendingUp,
                            isSelected = activeSection == DashboardSection.TASTE_SHIFT,
                            onClick = { onSelectSection(DashboardSection.TASTE_SHIFT) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            when (activeSection) {
                DashboardSection.CHILD_PROFILE -> {
                    ChildProfileSection(
                        settings = settings,
                        onSave = onUpdateChildProfile
                    )
                }

                DashboardSection.TIMER_SCHEDULE -> {
                    TimerScheduleSection(
                        settings = settings,
                        todayUsage = todayUsage,
                        onUpdateTimer = onUpdateTimer,
                        onResetTodayUsage = onResetTodayUsage
                    )
                }

                DashboardSection.CHANNELS_CURATION -> {
                    ChannelsCurationSection(
                        channels = channels,
                        onToggleChannel = onToggleChannel,
                        onAddCustomChannel = onAddCustomChannel
                    )
                }

                DashboardSection.CONTENT_FILTERING -> {
                    ContentFilteringSection(
                        settings = settings,
                        blockedWords = blockedWords,
                        hiddenVideos = hiddenVideos,
                        channels = channels,
                        onAddBlockedWord = onAddBlockedWord,
                        onRemoveBlockedWord = onRemoveBlockedWord,
                        onUnhideVideo = onUnhideVideo,
                        onToggleHideMusic = onToggleHideMusic
                    )
                }

                DashboardSection.SAVED_VIDEOS -> {
                    SavedVideosSection(
                        savedVideos = savedVideos,
                        channels = channels,
                        onPlayVideo = onPlayVideo,
                        onUnsaveVideo = onUnsaveVideo
                    )
                }

                DashboardSection.TASTE_SHIFT -> {
                    TasteShiftSection(
                        settings = settings
                    )
                }
            }
        }
    }
}

@Composable
fun DashboardTabChip(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = title,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 13.sp
                )
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = YtBrandOrange,
            selectedLabelColor = Color.White,
            selectedLeadingIconColor = Color.White,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = if (isSelected) YtBrandOrange else Color.Transparent,
            selectedBorderColor = YtBrandOrange
        )
    )
}

@Composable
fun ChildProfileSection(
    settings: SettingsEntity?,
    onSave: (String, Int?) -> Unit
) {
    var name by remember(settings) { mutableStateOf(settings?.childName ?: "") }
    var ageStr by remember(settings) { mutableStateOf(settings?.childAge?.toString() ?: "") }
    var isSavedConfirmation by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "ملف الطفل واهتماماته 👦👧",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "خصص اسم الطفل وعمره ليتم إظهار رسائل تشجيعية ومحتوى يناسب مرحلته العمرية.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it; isSavedConfirmation = false },
                label = { Text("اسم الطفل (اختياري)") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("child_name_input")
            )

            OutlinedTextField(
                value = ageStr,
                onValueChange = {
                    if (it.length <= 2 && it.all { c -> c.isDigit() }) {
                        ageStr = it
                        isSavedConfirmation = false
                    }
                },
                label = { Text("عمر الطفل بالسنوات") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("child_age_input")
            )

            Button(
                onClick = {
                    val parsedAge = ageStr.toIntOrNull()
                    onSave(name, parsedAge)
                    isSavedConfirmation = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = YtBrandOrange),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("save_child_profile_button")
            ) {
                Text(if (isSavedConfirmation) "تم الحفظ بنجاح ✅" else "حفظ التعديلات")
            }
        }
    }
}

@Composable
fun TimerScheduleSection(
    settings: SettingsEntity?,
    todayUsage: UsageEntity?,
    onUpdateTimer: (Int, String, String) -> Unit,
    onResetTodayUsage: () -> Unit
) {
    var limitMinutesStr by remember(settings) {
        mutableStateOf(settings?.sessionLimitMinutes?.toString() ?: "60")
    }
    var scheduleStart by remember(settings) {
        mutableStateOf(settings?.scheduleStart ?: "00:00")
    }
    var scheduleEnd by remember(settings) {
        mutableStateOf(settings?.scheduleEnd ?: "23:59")
    }
    var isSavedConfirmation by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "مواعيد التشغيل والحد اليومي ⏰",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            val secondsToday = todayUsage?.secondsUsedToday ?: 0
            val minutesToday = secondsToday / 60
            val limitMinutes = limitMinutesStr.toIntOrNull() ?: 60

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "استهلاك اليوم:",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "$minutesToday دقيقة / $limitMinutes دقيقة",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (minutesToday >= limitMinutes) YtRose else YtBrandOrange,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Button(
                        onClick = onResetTodayUsage,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("تصفير عداد اليوم (اختبار)", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp)
                    }
                }
            }

            OutlinedTextField(
                value = limitMinutesStr,
                onValueChange = {
                    if (it.length <= 4 && it.all { c -> c.isDigit() }) {
                        limitMinutesStr = it
                        isSavedConfirmation = false
                    }
                },
                label = { Text("الحد الأقصى اليومي (بالدقائق)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = scheduleStart,
                    onValueChange = { scheduleStart = it; isSavedConfirmation = false },
                    label = { Text("ساعة البداية (00:00)") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = scheduleEnd,
                    onValueChange = { scheduleEnd = it; isSavedConfirmation = false },
                    label = { Text("ساعة النهاية (23:59)") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = {
                    val limit = limitMinutesStr.toIntOrNull() ?: 60
                    onUpdateTimer(limit, scheduleStart, scheduleEnd)
                    isSavedConfirmation = true
                },
                colors = ButtonDefaults.buttonColors(containerColor = YtBrandOrange),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isSavedConfirmation) "تم حفظ المواعيد بنجاح ✅" else "حفظ المواعيد")
            }
        }
    }
}

@Composable
fun ChannelsCurationSection(
    channels: List<ChannelEntity>,
    onToggleChannel: (String, Boolean) -> Unit,
    onAddCustomChannel: (String, String, String) -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "تنظيم وتخصيص القنوات 📺",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "يمكنك تشغيل أو إيقاف أي قناة ومراجعة محتواها",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Button(
                    onClick = { showAddDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = YtBrandOrange),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.testTag("add_custom_channel_button")
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("إضافة قناة")
                }
            }

            channels.forEach { channel ->
                Surface(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = channel.title,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = if (channel.isPreloaded) "قناة مضمنة ومعتمدة" else "قناة مخصصة مضافة",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        Switch(
                            checked = channel.enabled,
                            onCheckedChange = { onToggleChannel(channel.sourceId, it) },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = YtBrandOrange
                            ),
                            modifier = Modifier.testTag("channel_switch_${channel.sourceId}")
                        )
                    }
                }
            }
        }
    }

    if (showAddDialog) {
        AddChannelDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { id, title, cat ->
                onAddCustomChannel(id, title, cat)
                showAddDialog = false
            }
        )
    }
}

@Composable
fun ContentFilteringSection(
    settings: SettingsEntity?,
    blockedWords: List<BlockedWordEntity>,
    hiddenVideos: List<VideoEntity>,
    channels: List<ChannelEntity>,
    onAddBlockedWord: (String) -> Unit,
    onRemoveBlockedWord: (String) -> Unit,
    onUnhideVideo: (String) -> Unit,
    onToggleHideMusic: (Boolean) -> Unit
) {
    var newWord by remember { mutableStateOf("") }
    val hideMusic = settings?.hideMusicVideos == true

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "الفلترة والحماية الذكية 🛡️",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "إخفاء الفيديوهات ذات الموسيقى (بدون موسيقى)",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "يحجب الأغاني والمقاطع التي تحتوي على معازف وموسيقى",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = hideMusic,
                        onCheckedChange = onToggleHideMusic,
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = YtBrandOrange
                        )
                    )
                }
            }

            Text(
                text = "الكلمات المحظورة (يحجب أي فيديو يحتوي عنوانه عليها)",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = newWord,
                    onValueChange = { newWord = it },
                    placeholder = { Text("أدخل كلمة لحظره (مثال: رعب)") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )

                Button(
                    onClick = {
                        if (newWord.isNotBlank()) {
                            onAddBlockedWord(newWord)
                            newWord = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = YtBrandOrange),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("إضافة")
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                blockedWords.forEach { item ->
                    Surface(
                        color = YtRose.copy(alpha = 0.12f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(text = item.word, color = YtRose, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "حذف",
                                tint = YtRose,
                                modifier = Modifier
                                    .size(16.dp)
                                    .clickable { onRemoveBlockedWord(item.word) }
                            )
                        }
                    }
                }
            }

            if (hiddenVideos.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "الفيديوهات المخفية يدوياً (${hiddenVideos.size})",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.bodyMedium
                )

                hiddenVideos.forEach { v ->
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = v.title,
                                style = MaterialTheme.typography.bodySmall,
                                maxLines = 1,
                                modifier = Modifier.weight(1f)
                            )
                            IconButton(onClick = { onUnhideVideo(v.videoId) }) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = "استعادة",
                                    tint = YtBrandOrange
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SavedVideosSection(
    savedVideos: List<VideoEntity>,
    channels: List<ChannelEntity>,
    onPlayVideo: (VideoEntity) -> Unit,
    onUnsaveVideo: (String) -> Unit
) {
    val channelMap = remember(channels) { channels.associateBy { it.sourceId } }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "الفيديوهات المحفوظة للأهل 🔖",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            if (savedVideos.isEmpty()) {
                Text(
                    text = "لا توجد فيديوهات محفوظة حالياً. يمكنك الضغط على رمز الحفظ في المشغل لحفظ الفيديوهات لمراجعتها لاحقاً.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                savedVideos.forEach { video ->
                    val ch = channelMap[video.channelId]
                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = video.title,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.bodyMedium,
                                    maxLines = 1
                                )
                                Text(
                                    text = ch?.title ?: "قناة أطفال",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }

                            Row {
                                IconButton(onClick = { onPlayVideo(video) }) {
                                    Icon(
                                        imageVector = Icons.Default.PlayArrow,
                                        contentDescription = "تشغيل",
                                        tint = YtBrandOrange
                                    )
                                }
                                IconButton(onClick = { onUnsaveVideo(video.videoId) }) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "إزالة",
                                        tint = YtRose
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TasteShiftSection(
    settings: SettingsEntity?
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "التوجيه الذكي وتطوير الاهتمامات (Taste Shift) ✨",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "يقوم النظام بإدراج فيديوهات تعليمية واستكشافية تدريجياً بنسبة ذكية لتعويد الطفل على محتوى نافع ومفيد دون إشعاره بالملل.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Surface(
                color = YtBrandSoft,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "الحالة: مفعل تلقائياً بنسبة توازن 20% لضمان التنوع المفيد للطفل في كل الجلسات.",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.SemiBold,
                    color = YtBrandOrange,
                    modifier = Modifier.padding(14.dp)
                )
            }
        }
    }
}

@Composable
fun AddChannelDialog(
    onDismiss: () -> Unit,
    onConfirm: (sourceId: String, title: String, categories: String) -> Unit
) {
    var sourceId by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("education") }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "إضافة قناة جديدة 📺",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = sourceId,
                    onValueChange = { sourceId = it },
                    label = { Text("معرف القناة (Channel ID) أو الرابط") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("اسم القناة") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = category,
                    onValueChange = { category = it },
                    label = { Text("التصنيف (education, stories, science...)") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("إلغاء")
                    }
                    Button(
                        onClick = {
                            if (sourceId.isNotBlank() && title.isNotBlank()) {
                                onConfirm(sourceId, title, category)
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = YtBrandOrange)
                    ) {
                        Text("إضافة القناة")
                    }
                }
            }
        }
    }
}
