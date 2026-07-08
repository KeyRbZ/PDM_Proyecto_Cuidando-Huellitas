package com.pdm0126.cuidandohuellitas.utils
import java.util.Locale
import java.text.SimpleDateFormat
import java.util.Calendar

fun calculateAge(date: String): String {
    return try{
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())//definir el formato en el que se guarda la fecha
        val birthDate = dateFormat.parse(date) ?: return "" //si no se puede parsear, devuelve una cadena vacía
        val today = Calendar.getInstance()//obtener la fecha actual
        val birthCalendar = Calendar.getInstance().apply { time = birthDate }//obtener la fecha de nacimiento en base a la fecha parseada

        var years = today.get(Calendar.YEAR) - birthCalendar.get(Calendar.YEAR)//calcular la diferencia de años
        var months = today.get(Calendar.MONTH) - birthCalendar.get(Calendar.MONTH)//calcular la diferencia de meses

        if (months < 0) {
            years--//si los meses son negativos, restar un año
            months += 12//y agregar 12 meses
        }

        when {
            years > 0 && months > 0 -> "$years años y $months meses"
            years > 0 -> "$years años"
            months > 1 -> "$months meses"
            months == 1 -> "1 mes"
            else -> "Menos de 1 mes"
        }

    }catch (e: Exception){
        date //si no se puede calcular, devuelve la fecha
    }
}