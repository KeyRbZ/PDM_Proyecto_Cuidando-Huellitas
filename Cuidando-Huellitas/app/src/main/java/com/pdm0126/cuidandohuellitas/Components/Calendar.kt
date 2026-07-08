package com.pdm0126.cuidandohuellitas.Components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.BlancoFocused
import com.pdm0126.cuidandohuellitas.ui.theme.BordeTextField
import com.pdm0126.cuidandohuellitas.ui.theme.NegroFocused
import com.pdm0126.cuidandohuellitas.ui.theme.Verde
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DatePickerFieldToModal(age: String, onAgeSelected: (String) -> Unit) {
    var petAge by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf<Long?>(null) }
    var showModal by remember { mutableStateOf(false) }
    val textColor = if (petAge.isNotEmpty()) NegroFocused else Color.Transparent
    LaunchedEffect(selectedDate) {
        selectedDate?.let {
            petAge = convertMillisToDate(it)
            onAgeSelected(petAge)
        }
    }

    OutlinedTextField(
        //value = selectedDate?.let { convertMillisToDate(it) } ?: "",
        value = petAge,
        onValueChange = { onAgeSelected(it) },
        placeholder = { Text("M/D/A") },
        trailingIcon = {
            Icon(
                Icons.Default.DateRange,
                contentDescription = "Select date",
                modifier = Modifier.clickable { showModal = true },
                tint = Verde
            )
        },
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = BlancoFocused,
            unfocusedContainerColor = Blanco,
            focusedBorderColor = BordeTextField,
            unfocusedBorderColor = BordeTextField,
            focusedTextColor = NegroFocused,
            unfocusedTextColor = textColor,
            cursorColor = Blanco
        ),

        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp))
            .border(
                1.dp,
                BordeTextField,
                RoundedCornerShape(15.dp)
            )
            .pointerInput(selectedDate) {
                awaitEachGesture {
                    awaitFirstDown(pass = PointerEventPass.Initial)
                    val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                    if (upEvent != null) {
                        showModal = true
                    }
                }
            }
    )

    if (showModal) {
        DatePickerModal(
            onDateSelected = { selectedDate = it },
            onDismiss = { showModal = false }
        )
    }
}

@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        }
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK", color = Verde)
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
            ) {
                Text("Cancel", color = Verde)

            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = Blanco,
            titleContentColor = Verde,
            headlineContentColor = Verde,

            )
    ) {
        DatePicker(
            state = datePickerState,
            colors = DatePickerDefaults.colors(
                containerColor = Blanco,
                titleContentColor = Verde,
                headlineContentColor = Verde,
                weekdayContentColor = Verde,
                yearContentColor = Verde,
                disabledYearContentColor = BlancoFocused,
                currentYearContentColor = Verde,
                selectedYearContentColor = Blanco,
                selectedYearContainerColor = Verde,
                dayContentColor = Verde,
                selectedDayContentColor = Blanco,
                selectedDayContainerColor = Verde,
                todayContentColor = Verde,
                todayDateBorderColor = Verde,
                dividerColor = BlancoFocused,
                navigationContentColor = NegroFocused,
                dateTextFieldColors =
                    TextFieldDefaults.colors(
                        focusedContainerColor = BlancoFocused,
                        unfocusedContainerColor = Blanco,
                        focusedIndicatorColor = BlancoFocused,
                        unfocusedIndicatorColor = BlancoFocused,
                        focusedTextColor = NegroFocused,
                        unfocusedTextColor = BlancoFocused,
                        cursorColor = Blanco,
                        focusedLabelColor = NegroFocused,
                        unfocusedLabelColor = BlancoFocused,

                        )

            )
        )
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}