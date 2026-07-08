package com.pdm0126.cuidandohuellitas.Components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.pdm0126.cuidandohuellitas.ui.theme.Blanco
import com.pdm0126.cuidandohuellitas.ui.theme.Negro
import com.pdm0126.cuidandohuellitas.ui.theme.NegroFocused
import com.pdm0126.cuidandohuellitas.ui.theme.Verde

@Composable
fun AlertDialogDelete(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(icon, contentDescription = "Icon")
        },
        title = {
            Text(
                text = dialogTitle,
                color = Negro
            )
        },
        text = {
            Text(
                text = dialogText,
                color = NegroFocused
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Verde
                )
            ) {
                Text("Si")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
                colors = ButtonDefaults.textButtonColors(
                    contentColor = Verde
                )
            ) {
                Text("No")
            }
        },
        containerColor = Blanco,
        textContentColor = Negro,
        iconContentColor = Verde,

        )
}