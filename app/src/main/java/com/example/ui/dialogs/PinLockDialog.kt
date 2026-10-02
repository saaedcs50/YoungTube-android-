package com.example.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.YtBrandOrange
import com.example.ui.theme.YtBrandSoft
import com.example.ui.theme.YtRose

@Composable
fun PinLockDialog(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    onVerifyPin: (String) -> Boolean,
    securityQuestion: String?,
    onRecoverPin: (String, String) -> Boolean,
    onUnlockedSuccess: () -> Unit
) {
    if (!isOpen) return

    var pinText by remember { mutableStateOf("") }
    var attempts by remember { mutableIntStateOf(0) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isRecovering by remember { mutableStateOf(false) }

    var recoveryAnswer by remember { mutableStateOf("") }
    var newPinText by remember { mutableStateOf("") }
    var recoveryError by remember { mutableStateOf<String?>(null) }

    val isPermanentlyLocked = attempts >= 5

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("pin_lock_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = YtBrandSoft,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (isRecovering) Icons.Default.Security else Icons.Default.Lock,
                                contentDescription = null,
                                tint = YtBrandOrange,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Text(
                        text = if (isRecovering) "استعادة رمز الدخول" else "منطقة الوالدين 🔒",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "إغلاق",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (isRecovering) {
                    Text(
                        text = "أجب على سؤال الأمان السري لتعيين رمز PIN جديد",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (!securityQuestion.isNullOrBlank()) {
                        Surface(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "السؤال: $securityQuestion",
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(12.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = recoveryAnswer,
                        onValueChange = { recoveryAnswer = it },
                        label = { Text("إجابة السؤال السري") },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("recovery_answer_field")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newPinText,
                        onValueChange = { if (it.length <= 6 && it.all { c -> c.isDigit() }) newPinText = it },
                        label = { Text("رمز PIN الجديد (6 أرقام)") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("new_pin_field")
                    )

                    if (recoveryError != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = recoveryError!!,
                            color = YtRose,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = { isRecovering = false }) {
                            Text("رجوع")
                        }

                        Button(
                            onClick = {
                                if (recoveryAnswer.isBlank() || newPinText.length != 6) {
                                    recoveryError = "يرجى ملء جميع الحقول ورمز مكون من 6 أرقام"
                                } else {
                                    val success = onRecoverPin(recoveryAnswer, newPinText)
                                    if (success) {
                                        onUnlockedSuccess()
                                    } else {
                                        recoveryError = "الإجابة غير صحيحة"
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = YtBrandOrange),
                            modifier = Modifier.testTag("submit_recovery_button")
                        ) {
                            Text("تعيين والدخول")
                        }
                    }
                } else if (isPermanentlyLocked) {
                    Icon(
                        imageVector = Icons.Default.Warning,
                        contentDescription = null,
                        tint = YtRose,
                        modifier = Modifier.size(48.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "تم قفل لوحة التحكم مؤقتاً",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = YtRose
                    )

                    Text(
                        text = "تم إدخال الرمز بشكل خاطئ 5 مرات متتالية لحماية إعدادات الطفل.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )

                    if (!securityQuestion.isNullOrBlank()) {
                        Button(
                            onClick = { isRecovering = true },
                            colors = ButtonDefaults.buttonColors(containerColor = YtBrandOrange),
                            modifier = Modifier.testTag("start_recovery_button")
                        ) {
                            Text("استعادة عبر سؤال الأمان")
                        }
                    }
                } else {
                    Text(
                        text = "أدخل رمز PIN المكون من 6 أرقام للوصول إلى لوحة التحكم",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = pinText,
                        onValueChange = {
                            if (it.length <= 6 && it.all { char -> char.isDigit() }) {
                                pinText = it
                                errorMessage = null
                            }
                        },
                        placeholder = { Text("••••••", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()) },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("pin_input_field")
                    )

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = errorMessage!!,
                            color = YtRose,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (!securityQuestion.isNullOrBlank()) {
                            TextButton(onClick = { isRecovering = true }) {
                                Text("نسيت الرمز؟", color = YtBrandOrange, fontSize = 13.sp)
                            }
                        } else {
                            Spacer(modifier = Modifier.width(1.dp))
                        }

                        Button(
                            onClick = {
                                if (pinText.length != 6) {
                                    errorMessage = "يجب إدخال 6 أرقام"
                                } else {
                                    val isCorrect = onVerifyPin(pinText)
                                    if (isCorrect) {
                                        onUnlockedSuccess()
                                    } else {
                                        attempts += 1
                                        val remaining = (5 - attempts).coerceAtLeast(0)
                                        errorMessage = "رمز PIN غير صحيح. المحاولات المتبقية: $remaining"
                                        pinText = ""
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = YtBrandOrange),
                            enabled = pinText.length == 6,
                            modifier = Modifier.testTag("verify_pin_button")
                        ) {
                            Text("تأكيد ودخول")
                        }
                    }
                }
            }
        }
    }
}
