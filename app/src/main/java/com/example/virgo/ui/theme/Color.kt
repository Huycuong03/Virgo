package com.example.virgo.ui.theme

import androidx.compose.ui.graphics.Color

// Base Colors
val Blue80 = Color(0xFFD0E4FF)  // Light Blue
val Green80 = Color(0xFFB7E0C6)  // Light Green
val Aqua80 = Color(0xFFB2E1E1)  // Light Aqua
val ColorGradient1 = Color(0xFF006F9B)  // Dark Blue
val ColorGradient2 = Color(0xFF3C9D6A)  // Deep Green
val ColorGradient3 = Color(0xFF47A4C5)  // Aqua Gradient
val ColorBackground = Color(0xFFF0F7FA)  // Light background with slight teal tint
val ColorSurface = Color.White  // Clean white surface
val ColorImageShadow = Color(0xFFE0F0F0)  // Soft shadow for images, light cyan
val ColorIconTitle = Color(0xFF4A4E68)  // Muted grayish blue for icons and titles
val ColorTextPrimary = Color(0xFF2D3A3F)  // Dark grayish blue for main text
val ColorTextPrimaryVariant = ColorTextPrimary.copy(alpha = 0.7f)  // Slightly lighter for secondary text
val ColorTextSecondary = Color(0xFF758E9C)  // Light grayish blue for secondary text
val ColorTextSecondaryVariant = ColorTextSecondary.copy(alpha = 0.7f)  // Lighter version for muted secondary text
val ColorTextAction = ColorGradient2  // Actionable text in a soft green, representing confirmation or action

// State Colors (Error, Warning, Success, Info)
val ColorError = Color(0xFFEB5757)  // Red for errors
val ColorErrorVariant = Color(0xFFFFD6D6)  // Light red for error background or alerts
val ColorWarning = Color(0xFFF2C94C)  // Yellow for warnings (attention-grabbing but not alarming)
val ColorWarningVariant = Color(0xFFFFF4D6)  // Light yellow for warning background
val ColorSuccess = Color(0xFF27AE60)  // Green for success messages or confirmations
val ColorSuccessVariant = Color(0xFFDEF9E4)  // Light green for success background
val ColorInfo = Color(0xFF56CCF2)  // Light blue for informational messages
val ColorInfoVariant = Color(0xFFD9F4FF)  // Light blue for info background

// Additional Accent Colors (for UI elements, buttons, etc.)
val ColorAccent = Color(0xFF00B0A1)  // Teal Green - calm, professional, and associated with health care
val ColorAccentVariant = Color(0xFFB2E8E0)  // Light Teal Green for subtle accents
val ColorDisabled = Color(0xFFB4B6B7)  // Disabled states (buttons, icons, etc.)

// Border & Divider Colors
val ColorDivider = Color(0xFFE0E0E0)  // Light gray for dividers and borders
val ColorBorder = Color(0xFFBDBDBD)  // Slightly darker gray for active borders
val ColorRating = Color(0xFFFFC107)