package com.pdm0126.cuidandohuellitas.Screens.Historial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pdm0126.cuidandohuellitas.Data.Model.ListVaccines
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.Negro
import com.pdm0126.cuidandohuellitas.ui.theme.Verde
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialBottomSheet(
    onSave: (
        nameVac: String,
        startDate: Long,
        interval: Int
    ) -> Unit,
    onDismiss: () -> Unit,
    listVaccines: List<ListVaccines>

) {

    val sheetState = rememberModalBottomSheetState()

    var expanded by remember { mutableStateOf(false) }
    var unitExpanded by remember { mutableStateOf(false) }

    var selectedVaccine by rememberSaveable {
        mutableStateOf("")
    }

    var dateText by rememberSaveable {
        mutableStateOf("")
    }

    var intervalText by rememberSaveable {
        mutableStateOf("")
    }

    var selectedUnit by rememberSaveable {
        mutableStateOf("Días")
    }


    val isValid =
        selectedVaccine.isNotBlank() &&
                dateText.isNotBlank() &&
                intervalText.isNotBlank()


    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = Blanco
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {


            Text(
                text = "Nueva Vacuna",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Verde
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = selectedVaccine,
                    onValueChange = {},
                    readOnly = true,
                    label = {
                        Text("Vacuna")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Verde,
                        unfocusedBorderColor = Verde.copy(alpha = 0.5f),
                        focusedLabelColor = Verde,
                        unfocusedLabelColor = Negro,
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        cursorColor = Verde,
                        focusedContainerColor = Blanco,
                        unfocusedContainerColor = Blanco
                    )
                )


                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    },
                    containerColor = Blanco
                ) {

                    listVaccines.forEach { vaccine ->

                        DropdownMenuItem(
                            text = {
                                Text(vaccine.nameVac,
                                    color = Negro)
                            },
                            onClick = {
                                selectedVaccine = vaccine.nameVac
                                expanded = false
                            }
                        )
                    }
                }
            }


            OutlinedTextField(
                value = dateText,
                onValueChange = {
                    dateText = it
                },
                label = {
                    Text("Fecha (dd/MM/yyyy)")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Verde,
                    unfocusedBorderColor = Verde.copy(alpha = 0.5f),
                    focusedLabelColor = Verde,
                    unfocusedLabelColor = Negro,
                    focusedTextColor = Negro,
                    unfocusedTextColor = Negro,
                    cursorColor = Verde,
                    focusedContainerColor = Blanco,
                    unfocusedContainerColor = Blanco
                )
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                OutlinedTextField(
                    value = intervalText,
                    onValueChange = {
                        intervalText = it.filter { char ->
                            char.isDigit()
                        }
                    },
                    label = {
                        Text("Intervalo de tiempo")
                    },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Verde,
                        unfocusedBorderColor = Verde.copy(alpha = 0.5f),
                        focusedLabelColor = Verde,
                        unfocusedLabelColor = Negro,
                        focusedTextColor = Negro,
                        unfocusedTextColor = Negro,
                        cursorColor = Verde,
                        focusedContainerColor = Blanco,
                        unfocusedContainerColor = Blanco
                    )
                )


                ExposedDropdownMenuBox(
                    expanded = unitExpanded,
                    onExpandedChange = {
                        unitExpanded = !unitExpanded
                    },
                    modifier = Modifier.weight(1f)
                ) {

                    OutlinedTextField(
                        value = selectedUnit,
                        onValueChange = {},
                        readOnly = true,
                        label = {
                            Text("Unidad",
                                color = Negro)
                        },
                        modifier = Modifier.menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                unitExpanded
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Verde,
                            unfocusedBorderColor = Verde.copy(alpha = 0.5f),
                            focusedLabelColor = Verde,
                            unfocusedLabelColor = Negro,
                            focusedTextColor = Negro,
                            unfocusedTextColor = Negro,
                            cursorColor = Verde,
                            focusedContainerColor = Blanco,
                            unfocusedContainerColor = Blanco
                        )
                    )


                    ExposedDropdownMenu(
                        expanded = unitExpanded,
                        onDismissRequest = {
                            unitExpanded = false
                        },
                        containerColor = Blanco
                    ) {

                        listOf(
                            "Días",
                            "Meses",
                            "Años"
                        ).forEach { unit ->

                            DropdownMenuItem(
                                text = {
                                    Text(unit,
                                        color = Negro)
                                },
                                onClick = {
                                    selectedUnit = unit
                                    unitExpanded = false
                                }
                            )
                        }
                    }
                }
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {

                TextButton(
                    onClick = onDismiss
                ) {
                    Text("Cancelar")
                }


                Spacer(
                    modifier = Modifier.width(8.dp)
                )


                Button(
                    enabled = isValid,
                    onClick = {

                        val intervalDays =
                            when(selectedUnit) {
                                "Meses" ->
                                    intervalText.toInt() * 30

                                "Años" ->
                                    intervalText.toInt() * 365

                                else ->
                                    intervalText.toInt()
                            }


                        val dateMillis =
                            SimpleDateFormat(
                                "dd/MM/yyyy",
                                Locale.getDefault()
                            )
                                .parse(dateText)
                                ?.time ?: 0L


                        onSave(
                            selectedVaccine,
                            dateMillis,
                            intervalDays
                        )

                        onDismiss()
                    }
                ) {
                    Text("Guardar")
                }
            }
        }
    }
}