package com.example.programmingquotes.feature.authors.ui.component

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.programmingquotes.R
import com.example.programmingquotes.feature.authors.ui.viewmodel.AuthorViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private var isFirstLaunch = mutableStateOf(true)
private var isBottomSheetVisible = mutableStateOf(true)

@ExperimentalMaterialApi
@Composable
fun BottomSheet(
    content: @Composable (bottomSheetState: ModalBottomSheetState, scope: CoroutineScope) -> Unit
) {

    val authorViewModel: AuthorViewModel = hiltViewModel()
    val context = LocalContext.current
    SideEffect {
        setUpSensorManager(context, authorViewModel)
    }

    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    LaunchedEffect(key1 = bottomSheetState.currentValue) {
        if (bottomSheetState.currentValue == ModalBottomSheetValue.Hidden)
            isFirstLaunch.value = true

        isBottomSheetVisible.value =
            (bottomSheetState.currentValue == ModalBottomSheetValue.Expanded)
    }
    val scope = rememberCoroutineScope()
    val randomQuote = authorViewModel.randomQuote.collectAsState()

    BackHandler(enabled = bottomSheetState.isVisible) {
        scope.launch {
            bottomSheetState.hide()
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        scrimColor = Color.Transparent,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetElevation = 15.dp,
        sheetContent = {
            if (isFirstLaunch.value) {
                SheetContentShake()
            } else {
                SheetContentQuote(
                    title = randomQuote.value.name,
                    content = "Nine women can't make a baby in one month."
                )
            }
        },
        content = { content(bottomSheetState, scope) }
    )

}

@Composable
private fun SheetContentShake() {

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .padding(top = 60.dp),
            painter = painterResource(id = R.drawable.ic_app),
            contentDescription = stringResource(id = R.string.content_desc_app),
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 54.dp,
                    bottom = 32.dp,
                    start = 5.dp,
                    end = 5.dp,
                ),
            text = stringResource(id = R.string.label_shake_your_phone_to_see_magic),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Composable
private fun SheetContentQuote(
    title: String,
    content: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    start = 32.dp
                ),
            text = title,
            style = MaterialTheme.typography.body1
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    start = 24.dp,
                    end = 24.dp
                ),
            text = content,
            style = MaterialTheme.typography.subtitle1
        )
        Text(
            modifier = Modifier
                .padding(
                    top = 32.dp,
                    bottom = 9.dp,
                    start = 32.dp
                ),
            text = stringResource(id = R.string.label_you_can_shake_again),
            style = MaterialTheme.typography.overline
        )
    }
}

private fun setUpSensorManager(context: Context, authorViewModel: AuthorViewModel) {
    var acceleration = 0f
    var currentAcceleration = 0f
    var lastAcceleration = 0f

    val sensorListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            lastAcceleration = currentAcceleration

            currentAcceleration = StrictMath.sqrt((x * x + y * y + z * z).toDouble()).toFloat()
            val delta: Float = currentAcceleration - lastAcceleration
            acceleration = acceleration * 0.9f + delta

            if (acceleration > 12) {
                if (isBottomSheetVisible.value) {
                    isFirstLaunch.value = false
                    authorViewModel.getRandomAuthor()
                }

            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
    }

    val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    sensorManager.registerListener(
        sensorListener,
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
        SensorManager.SENSOR_DELAY_NORMAL
    )
    acceleration = 10f
    currentAcceleration = SensorManager.GRAVITY_EARTH
    lastAcceleration = SensorManager.GRAVITY_EARTH
}
