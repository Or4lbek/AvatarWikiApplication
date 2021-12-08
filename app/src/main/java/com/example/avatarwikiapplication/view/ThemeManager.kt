package com.example.avatarwikiapplication.view


import androidx.annotation.ColorRes
import com.example.avatarwikiapplication.R

object ThemeManager {
    private val listeners = mutableSetOf<ThemeChangedListener>()
    var theme = Theme.LIGHT
        set(value) {
            field = value
            listeners.forEach { listener -> listener.onThemeChanged(value) }
        }

    interface ThemeChangedListener {

        fun onThemeChanged(theme: Theme)
    }

    fun addListener(listener: ThemeChangedListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: ThemeChangedListener) {
        listeners.remove(listener)
    }
}
data class TextViewTheme(
    @ColorRes
    val textColor: Int
)

data class ViewGroupTheme(
    @ColorRes
    val backgroundColor: Int
)

data class ButtonTheme(
    @ColorRes
    val backgroundTint: Int,
    @ColorRes
    val textColor: Int
)

enum class Theme(
    val buttonTheme: ButtonTheme,
    val textViewTheme: TextViewTheme,
    val viewGroupTheme: ViewGroupTheme,
    val toolbarTheme: ViewGroupTheme
) {
    DARK(
        buttonTheme = ButtonTheme(
            backgroundTint = R.color.button_color_dark,
            textColor = android.R.color.white
        ),
        textViewTheme = TextViewTheme(
            textColor = android.R.color.white
        ),
        viewGroupTheme = ViewGroupTheme(
            backgroundColor = android.R.color.background_dark
        ),
        toolbarTheme = ViewGroupTheme(
            backgroundColor = android.R.color.background_dark
        )
    ),
    LIGHT(
        buttonTheme = ButtonTheme(
            backgroundTint = R.color.button_color_light,
            textColor = android.R.color.black
        ),
        textViewTheme = TextViewTheme(
            textColor = android.R.color.black
        ),
        viewGroupTheme = ViewGroupTheme(
            backgroundColor = android.R.color.background_light
        ),
        toolbarTheme = ViewGroupTheme(
            backgroundColor = R.color.purple_500
        )
    )
}