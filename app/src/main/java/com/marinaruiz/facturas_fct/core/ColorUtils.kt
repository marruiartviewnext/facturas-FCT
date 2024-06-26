package com.marinaruiz.facturas_fct.core

import android.content.Context
import android.util.TypedValue

fun getColorFromThemeAttribute(context: Context, attr: Int): Int {
    val typedValue = TypedValue()
    context.theme.resolveAttribute(attr, typedValue, true)
    return typedValue.data
}