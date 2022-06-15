package com.example.composeexplore.ui.component.widgets

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.glance.*
import androidx.glance.action.ActionParameters
import androidx.glance.action.clickable
import androidx.glance.appwidget.*
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.layout.*
import androidx.glance.state.PreferencesGlanceStateDefinition
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.example.composeexplore.R
import com.example.composeexplore.ui.component.widgets.AppWidget.Companion.APP_WIDGET_PREFS_KEY
import com.example.composeexplore.ui.component.widgets.AppWidget.Companion.MAX_COUNTER
import com.example.composeexplore.ui.component.widgets.AppWidget.Companion.MIN_COUNTER

class AppWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget
        get() = AppWidget()
}


class AppWidget : GlanceAppWidget() {

    companion object {
        const val APP_WIDGET_PREFS_KEY = "APP_WIDGET_PREFS_KEY"
        const val MAX_COUNTER = 999
        const val MIN_COUNTER = 0
    }

    @Composable
    override fun Content() {
        AppWidgetContent(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(
                    day = Color.DarkGray,
                    night = Color.Black
                )
                .appWidgetBackground()
                .cornerRadius(16.dp)
                .padding(8.dp),
        )
    }
}

@Composable
fun AppWidgetContent(
    modifier: GlanceModifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val prefs = currentState<Preferences>()
        val counter = prefs[intPreferencesKey(APP_WIDGET_PREFS_KEY)] ?: 0
        AppWidgetCounter(
            context = context,
            counter = counter,
            modifier = GlanceModifier
                .fillMaxWidth()
                .defaultWeight()
        )
        WaterWidgetButtonLayout(
            modifier = GlanceModifier
                .fillMaxSize()
                .defaultWeight()
        )
    }
}

@Composable
fun AppWidgetCounter(
    context: Context,
    counter: Int,
    modifier: GlanceModifier
) {
    Text(
        text = context.getString(
            R.string.prefix_counter,
            counter.toString()
        ),
        modifier = modifier,
        style = TextStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = ColorProvider(
                color = Color.White
            )
        ),
    )
}

@Composable
fun WaterWidgetButtonLayout(
    modifier: GlanceModifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            provider = ImageProvider(
                resId = R.drawable.ic_remove
            ),
            contentDescription = null,
            modifier = GlanceModifier
                .clickable(
                    onClick = actionRunCallback<DownCounterClickAction>()
                )
                .defaultWeight()
        )
        Image(
            provider = ImageProvider(
                resId = R.drawable.ic_add
            ),
            contentDescription = null,
            modifier = GlanceModifier
                .clickable(
                    onClick = actionRunCallback<AddCounterClickAction>()
                )
                .defaultWeight()
        )
    }
}


class AddCounterClickAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) {
            it.toMutablePreferences()
                .apply {
                    val counter = this[intPreferencesKey(APP_WIDGET_PREFS_KEY)] ?: 0
                    if (counter < MAX_COUNTER) {
                        this[intPreferencesKey(APP_WIDGET_PREFS_KEY)] = counter + 1
                    }
                }
        }
        AppWidget().update(context, glanceId)
    }
}

class DownCounterClickAction : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        updateAppWidgetState(context, PreferencesGlanceStateDefinition, glanceId) {
            it.toMutablePreferences()
                .apply {
                    val counter = this[intPreferencesKey(APP_WIDGET_PREFS_KEY)] ?: 0
                    if (counter > MIN_COUNTER) {
                        this[intPreferencesKey(APP_WIDGET_PREFS_KEY)] = counter - 1
                    }
                }
        }
        AppWidget().update(context, glanceId)
    }
}