package uci.fiai.miniakd.extensions

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Vibrator
import android.provider.CalendarContract
import android.provider.Settings
import android.telephony.TelephonyManager
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileFilter
import java.net.InetAddress
import java.net.NetworkInterface
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

// 25 de Agosto de 2019

// region Constants
val PERSON_NAME_REGEX: Regex by lazy {
    Regex("[A-ZÁÉÍÓÚÑ][.|[A-Za-záéíóúñü]*]?[\\s[A-ZÁÉÍÓÚÑ].|[A-Za-záéíóúñü]*]*")
}

val MONDAY: String by lazy { "Lunes" }
val TUESDAY: String by lazy { "Martes" }
val WEDNESDAY: String by lazy { "Miércoles" }
val THURSDAY: String by lazy { "Jueves" }
val FRIDAY: String by lazy { "Viernes" }
val SATURDAY: String by lazy { "Sábado" }
val SUNDAY: String by lazy { "Domingo" }
val UNKNOWN_DAY_OF_WEEK: String by lazy { "UNKNOWN_DAY_OF_WEEK" }
val DAYS_OF_WEEK: List<String>  by lazy {
    listOf(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY)
}

val JANUARY: String by lazy { "Enero" }
val FEBRARY: String by lazy { "Febrero" }
val MARCH: String by lazy { "Marzo" }
val APRIL: String by lazy { "Abril" }
val MAY: String by lazy { "Mayo" }
val JUNE: String by lazy { "Junio" }
val JULY: String by lazy { "Julio" }
val AUGUST: String by lazy { "Agosto" }
val SEPTEMBER: String by lazy { "Septiembre" }
val OCTOBER: String by lazy { "Octubre" }
val NOVEMBER: String by lazy { "Noviembre" }
val DECEMBER: String by lazy { "Diciembre" }
val UNKNOWN_MONTH: String by lazy { "UNKNOWN_MONTH" }
val MONTHS: List<String>  by lazy {
    listOf(
        JANUARY, FEBRARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER
    )
}
// endregion

// region Calendar's Methods
fun Calendar.sameDate(other: Calendar): Boolean {
    return year() == other.year() && month() == other.month() && dayMonth() == other.dayMonth()
}

fun Calendar.year(): Int = get(Calendar.YEAR)

fun Calendar.month(): Int = get(Calendar.MONTH) + 1

fun Calendar.dayMonth(): Int = get(Calendar.DAY_OF_MONTH)

@JvmOverloads
fun Calendar.string(separator: Char = '/'): String {
    return "${dayMonth().to2Digits()}$separator${month().to2Digits()}$separator${year()}"
}

fun Calendar.prettyString(): String = stringSmallFromCalendar(this)

@JvmOverloads
fun Calendar.monthName(small: Boolean = false): String {
    return if (small) monthNameSmall(month()) else monthNameLarge(month())
}

fun hours24to12(hours24: Int): Int {
    return when (hours24) {
        in 13..23 -> hours24 - 12
        0, 24 -> 12
        else -> hours24
    }
}

fun monthNameSmall(month: Int): String {
    return when (month) {
        in 1..12 -> "${MONTHS[month - 1].substring(0..2)}."
        else -> UNKNOWN_MONTH
    }
}

fun monthNameLarge(month: Int): String {
    return when (month) {
        in 1..12 -> MONTHS[month - 1]
        else -> UNKNOWN_MONTH
    }
}

fun stringSmallFromCalendar(calendar: Calendar): String {
    return "${calendar.dayMonth()} de ${monthNameSmall(calendar.month())} de ${calendar.year()}"
}

fun stringLargeFromCalendar(calendar: Calendar): String {
    return "${calendar.dayMonth()} de ${monthNameLarge(calendar.month())} de ${calendar.year()}"
}

fun stringFromCalendar(cal: Calendar): String {
    return "${cal.year()}/${cal.month().to2Digits()}/${cal.dayMonth().to2Digits()}"
}

@Throws(ParseException::class)
@JvmOverloads
fun calendarFromStr(date: String, format: String = "yyyy/MM/dd", time: String = "0000"): Calendar {
    val sdf = SimpleDateFormat(format)
    sdf.parse(date)
    val calendar = sdf.calendar

    calendar.set(Calendar.HOUR_OF_DAY, time.substring(0, 2).toInt())
    calendar.set(Calendar.MINUTE, time.substring(2).toInt())

    return calendar
}

fun timeFromText(time: String): String {
    var hours: Int = time.substring(0, 2).toInt()
    val minutes = time.substring(2)
    val amPm = if (hours < 12) "a.m." else "p.m."
    hours = hours24to12(hours)

    return "${hours.to2Digits()}:$minutes $amPm"
}
// endregion

// region Toast's Methods
fun toast(context: Context, text: CharSequence) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun toastLong(context: Context, text: CharSequence) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}
// endregion

// region Snackbar's Methods
/**
 * View Snack bar for simple form.
 *
 * @param view the principal view (layout)
 * @param message the message to shown
 */
/*fun snackbarSimple(view: View, message: String) {
    val snackbar = Snackbar.make(
        view, message,
        Snackbar.LENGTH_LONG
    ).setDuration(Snackbar.LENGTH_LONG)
    val snackbarView = snackbar.view
    val tv = snackbarView.findViewById<TextView>(android.support.design.R.id.snackbar_text)

    tv.maxLines = 5
    tv.textSize = 24f
    snackbar.show()
}*/

@JvmOverloads
fun snackbar(view: View, text: String, isLong: Boolean = false) {
    Snackbar.make(
        view, text, if (isLong) Snackbar.LENGTH_LONG else Snackbar.LENGTH_SHORT
    ).show()
}

/*@JvmOverloads
fun snackbarUndo(
    view: View, text: String, action: View.OnClickListener? = null,
    callback: BaseTransientBottomBar.BaseCallback<Snackbar>? = null,
    actionColorText: String = "#f05545"
) {
    Snackbar.make(view, text, Snackbar.LENGTH_LONG).apply {
        if (action != null) {
            setAction("DESHACER", action)
            setActionTextColor(
                ColorStateList.valueOf(
                    Color.parseColor(if (actionColorText.isBlank()) "#f05545" else actionColorText)
                )
            )
        }

        if (callback != null) addCallback(callback)
    }.show()
}*/
// endregion

// region Log's Methods
@JvmOverloads
fun loge(tag: String = "JAGC", msg: String) {
    Log.e(tag, msg)
}

@JvmOverloads
fun logi(tag: String = "JAGC", msg: String) {
    Log.i(tag, msg)
}

@JvmOverloads
fun logd(tag: String = "JAGC", msg: String) {
    Log.d(tag, msg)
}

@JvmOverloads
fun logv(tag: String = "JAGC", msg: String) {
    Log.v(tag, msg)
}

@JvmOverloads
fun logw(tag: String = "JAGC", msg: String) {
    Log.w(tag, msg)
}

@JvmOverloads
fun loga(tag: String = "JAGC", msg: String) {
    Log.wtf(tag, msg)
}
// endregion

// region Math's Methods
@JvmOverloads
fun percent(part: Double, total: Double, rounds: Boolean = false): Double {
    return if (rounds) (part * 100.0 / total).round() else part * 100.0 / total
}

@JvmOverloads
fun percent(part: Float, total: Float, rounds: Boolean = false): Float {
    return if (rounds) (part * 100f / total).round() else part * 100f / total
}

fun percent(part: Int, total: Int) = part * 100 / total

@JvmOverloads
fun partPercent(total: Double, percent: Double, rounds: Boolean = false): Double {
    return if (rounds) (total * percent / 100.0).round() else total * percent / 100.0
}

@JvmOverloads
fun partPercent(total: Float, percent: Float, rounds: Boolean = false): Float {
    return if (rounds) (total * percent / 100f).round() else total * percent / 100f
}

fun partPercent(total: Int, percent: Int) = total * percent / 100

@JvmOverloads
fun totalPercent(part: Double, percent: Double, rounds: Boolean = false): Double {
    return if (rounds) (percent / (part * 100.0)).round() else percent / (part * 100.0)
}

@JvmOverloads
fun totalPercent(part: Float, percent: Float, rounds: Boolean = false): Float {
    return if (rounds) (percent / (part * 100f)).round() else percent / (part * 100f)
}

fun totalPercent(part: Int, percent: Int) = percent / (part * 100)
// endregion

// region Extension Methods
/**
 * Redondea un el valor [Double] al número de lugares decimales dados.
 *
 * @param afterDecimalPoint número de dígits después del punto decimal
 * @return el valor [Double] redondeado a la precisión dada
 */
@JvmOverloads
fun Double.round(afterDecimalPoint: Int = 2): Double {
    val mask = Math.pow(10.0, afterDecimalPoint.toDouble())

    return Math.round(this * mask) / mask
}

/**
 * Redondea un el valor [Float] al número de lugares decimales dados.
 *
 * @param afterDecimalPoint número de dígits después del punto decimal
 * @return el valor [Float] redondeado a la precisión dada
 */
@JvmOverloads
fun Float.round(afterDecimalPoint: Int = 2): Float {
    val mask = Math.pow(10.0, afterDecimalPoint.toDouble()).toFloat()

    return Math.round(this * mask) / mask
}

/**
 * Indica si esta secuencia de caracteres 'nullable' es nula, si está solo compuesta
 * por caracteres en blanco (' ') o si está vacía.
 */
fun CharSequence.isNullOrBlankOrEmpty() = isNullOrBlank() || isNullOrEmpty()

/**
 * Indica si esta secuencia de caracteres está solo compuesta por caracteres
 * en blanco (' ') o si está vacía.
 */
fun CharSequence.isBlankOrEmpty() = isBlank() || isEmpty()

/**
 * Convierte este valor [Int] a [Boolean]. El valor resultante es `true` si este valor [Int]
 * es diferente de 0, si es 0 entonces será `false`.
 *
 * @return si el valor [Int] es 0 devuelve `false` sino `true`
 */
fun Int.toBoolean(): Boolean = this != 0

/**
 * Convierte este valor [Boolean] a [Int]. El valor resultante es 1 si este valor [Boolean]
 * es `true` sino entonces será 0.
 *
 * @return si el valor [Boolean] es `true` devuelve 1 sino 0
 */
fun Boolean.toInt(): Int = if (this) 1 else 0



/**
 * Convierte este [ArrayList] a un [Array] con los valores [String] de sus elementos
 *
 * @return un [Array] con los valores [String] de los elementos
 */
fun <T> ArrayList<T>.toStringArray(): Array<String> = Array(size) { i -> get(i).toString() }

fun <T> List<T>.toArrayList(): ArrayList<T> {
    val newList = ArrayList<T>()

    forEach { newList.add(it) }

    return newList
}

/**
 * Inserta el valor [String] dado al inicio de este [Array] de [String].
 *
 * @param stringValue valor [String] a insertar
 *
 * @return el [Array] inicial con el valor dado insertado al inicio
 */
fun Array<String>.addFirst(stringValue: String): Array<String> = Array(size + 1) { i ->
    if (i == 0) stringValue else get(i - 1)
}

/**
 * Devuelve una copia de la lista actual. Los cambios estructurales en la lista resultante
 * no afectan a esta lista y viceversa.
 */
fun <T> MutableList<T>.copy(): MutableList<T> = MutableList(size) { i -> get(i) }

fun Int.to2Digits(): String = if (this in 0..9) "0$this" else this.toString()

/**
 * Realizar la animación de aparecer sobre la [View].
 *
 * @param duration duración de la animación
 */
@JvmOverloads
/*fun View.fadeInAnim(duration: Long = 150L) {
    ViewCompat.animate(this).cancel()
    this.alpha = 0f
    this.visibility = View.VISIBLE
    ViewCompat.animate(this)
        .alpha(1f)
        .withLayer()
        .setDuration(duration)
        .setInterpolator(FastOutSlowInInterpolator())
        .start()
}*/

/**
 * Realiza animación de desaparecer sobre la [View].
 *
 * @param duration duración de la animación
 */
/*@JvmOverloads
fun View.fadeOutAnim(duration: Long = 200L) {
    ViewCompat.animate(this).cancel()
    this.alpha = 1f
    this.visibility = View.VISIBLE
    ViewCompat.animate(this)
        .alpha(0f)
        .withLayer()
        .setDuration(duration)
        .setInterpolator(FastOutSlowInInterpolator())
        .withEndAction { this.visibility = View.GONE }
        .start()
}*/

/**
 * Closing animation.
 *
 * @param removeView true to remove the view when the animation is over, false otherwise.
 */
/*@JvmOverloads
fun View.shrinkAnim(removeView: Boolean = false) {
    ViewCompat.animate(this).cancel()
    ViewCompat.animate(this)
        .alpha(0f)
        .withLayer()
        .setDuration(200L)
        .setInterpolator(FastOutSlowInInterpolator())
        .withEndAction {
            if (removeView) {
                val parent = this.parent as ViewGroup
                parent.removeView(this)
            } else {
                this.visibility = View.GONE
            }
        }
        .start()
}*/

/**
 * Rotate a view of the specified degrees.
 *
 * @param animate true to animate the rotation, false to be instant.
 * @see [rotateBackward]
 */
/*@JvmOverloads
fun View.rotateForward(angle: Float, animate: Boolean = true) {
    ViewCompat.animate(this)
        .rotation(angle)
        .withLayer()
        .setDuration(if (animate) 200L else 0L)
        .setInterpolator(FastOutSlowInInterpolator())
        .start()
}*/

/**
 * Rotate a view back to its default angle (0°).
 *
 * @param animate true to animate the rotation, false to be instant.
 * @see [rotateForward]
 */
/*@JvmOverloads
fun View.rotateBackward(animate: Boolean = true) {
    ViewCompat.animate(this)
        .rotation(0.0f)
        .withLayer()
        .setDuration(if (animate) 200L else 0L)
        .setInterpolator(FastOutSlowInInterpolator())
        .start()
}*/

fun String.upperCaseFirstChar(): String {
    return when {
        this.isBlankOrEmpty() -> this
        this.length == 1 -> this.toUpperCase()
        else -> this[0].toUpperCase() + this.substring(1)
    }
}
// endregion

// region Other Methods
/**
 * Causa la vibración del dispositivo.
 *
 * @param milliseconds duración en milisegundos de la vibración
 * del dispositivo (250ms de forma predeterminada)
 */
/*@JvmOverloads
fun vibrate(context: Context, milliseconds: Long = 250L) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    if (vibrator.hasVibrator()) vibrator.vibrate(milliseconds)
}*/

@JvmOverloads
fun inDevelopment(context: Context, time: Int = Toast.LENGTH_LONG) {
    Toast.makeText(context, "Función en desarrollo", time).show()
}

fun drawableIdByName(context: Context, name: String, packageName: String): Int {
    return context.resources.getIdentifier(name, "drawable", packageName)
}

fun dipToPx(context: Context, dip: Float): Float {
    val metrics = context.resources.displayMetrics

    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics)
}

fun dipToPxRounded(context: Context, dip: Float): Int {
    val metrics = context.resources.displayMetrics

    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, metrics))
}

fun pxToDp(px: Float): Int = Math.round(px / Resources.getSystem().displayMetrics.density)

fun getDisplaySize(activity: Activity): Point {
    val display = activity.windowManager.defaultDisplay
    val point = Point(0, 0)

    display.getSize(point)

    return point
}

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
fun getRealDisplaySize(activity: Activity): Point {
    val display = activity.windowManager.defaultDisplay
    val point = Point(0, 0)

    display.getRealSize(point)

    return point
}

@JvmOverloads
fun versionApp(packageManager: PackageManager, packageName: String, prefix: Boolean = true): String? {
    var versionName: String? = null

    try {
        versionName = if (prefix) "v" else ""
        versionName += packageManager.getPackageInfo(packageName, PackageManager.GET_CONFIGURATIONS)
            .versionName
    } catch (_: PackageManager.NameNotFoundException) {
    }

    return versionName
}

fun getPrimaryColor(context: Context): Int {
    val colorAttr: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        android.R.attr.colorPrimary
    } else {
        context.resources.getIdentifier("colorPrimary", "attr", context.packageName)
    }
    val outValue = TypedValue()
    context.theme.resolveAttribute(colorAttr, outValue, true)
    return outValue.data
}

fun getAccentColor(context: Context): Int {
    val colorAttr: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        android.R.attr.colorAccent
    } else {
        //Get colorAccent defined for AppCompat
        context.resources.getIdentifier("colorAccent", "attr", context.packageName)
    }
    val outValue = TypedValue()

    context.theme.resolveAttribute(colorAttr, outValue, true)

    return outValue.data
}

fun getRotateDrawable(drawable: Drawable, angle: Float): Drawable {
    if (angle == 0f) return drawable

    val drawables = arrayOf(drawable)

    return object : LayerDrawable(drawables) {
        override fun draw(canvas: Canvas) {
            canvas.save()
            canvas.rotate(
                angle,
                (drawable.bounds.width() / 2).toFloat(),
                (drawable.bounds.height() / 2).toFloat()
            )
            super.draw(canvas)
            canvas.restore()
        }
    }
}

/**
 * Creates a [Bitmap] from a [Drawable].
 */
fun getBitmapFromDrawable(drawable: Drawable?): Bitmap? {
    if (drawable == null) {
        return null
    } else {
        val bitmap: Bitmap

        if (drawable is BitmapDrawable) {
            val bitmapDrawable = drawable as BitmapDrawable?

            if (bitmapDrawable!!.bitmap != null) return bitmapDrawable.bitmap
        }

        bitmap = if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
            // Single color bitmap will be created of 1x1 pixel
            Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888)
        } else {
            Bitmap.createBitmap(
                drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
        }

        val canvas = Canvas(bitmap)

        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        return bitmap
    }
}

/**
 * Creates a [Drawable] from a [Bitmap].
 */
fun getDrawableFromBitmap(bitmap: Bitmap?): Drawable? {
    return if (bitmap == null) null else BitmapDrawable(bitmap)
}

/**
 * Perform a tap of [ViewConfiguration.getTapTimeout] milliseconds on the view.
 *
 * @param view the view you want to tap
 */
fun performTap(view: View) {
    view.isPressed = true
    view.postDelayed({
        view.isPressed = false
        view.performClick()
    }, ViewConfiguration.getTapTimeout().toLong())
}

@JvmOverloads
fun emailIntent(context: Context, emails: Array<String>, subject: String = "", text: String = "") {
    val emailIntent = Intent(Intent.ACTION_SEND)
    emailIntent.type = "text/html"
    emailIntent.putExtra(Intent.EXTRA_EMAIL, emails)
    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
    emailIntent.putExtra(Intent.EXTRA_TEXT, text)

    try {
        context.startActivity(Intent.createChooser(emailIntent, "Enviar correo con..."))
    } catch (_: ActivityNotFoundException) {
        toastLong(context, "No hay ningún cliente de correo instalado.")
    }
}

fun webIntent(context: Context, url: String) {
    val browseIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(browseIntent)
}

fun phoneIntent(context: Context, phone: String) {
    val phoneIntent = Intent(Intent.ACTION_DIAL)
    phoneIntent.data = Uri.parse("tel:$phone")

    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)
        == PackageManager.PERMISSION_GRANTED
    ) context.startActivity(phoneIntent)
    else
        toastLong(context, "No hay permisos para llamadas")
}

fun calendarIntent(context: Context, begin: Calendar, end: Calendar, title: String, location: String) {
    val calendarIntent = Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI)
    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin.timeInMillis)
    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.timeInMillis)
    calendarIntent.putExtra(CalendarContract.Events.TITLE, title)
    calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, location)

    if (isIntentSafe(context, calendarIntent))
        context.startActivity(calendarIntent)
}

fun isIntentSafe(context: Context, intent: Intent): Boolean {
    val packageManager = context.packageManager
    return packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size > 0
}
// endregion

// region Android Information's Methods
/**
 * Helper method to determine if the device has an extra-large screen. For
 * example, 10" tablets are extra-large.
 */
fun isXLargeTablet(context: Context): Boolean {
    return context.resources.configuration.screenLayout and
            Configuration.SCREENLAYOUT_SIZE_MASK >= Configuration.SCREENLAYOUT_SIZE_XLARGE
}

/**
 * Verifica si el almacenamiento externo está disponible para leer y escribir.
 *
 * @return true si el almacenamiento externo está disponible para leer y escribir
 */
fun isExternalStorageWritable(): Boolean = Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()

/**
 * Checks if external storage is available to at least read.
 *
 * @return if external storage is available to at least read
 */
fun isExternalStorageReadable(): Boolean {
    val state = Environment.getExternalStorageState()

    return Environment.MEDIA_MOUNTED == state || Environment.MEDIA_MOUNTED_READ_ONLY == state
}

/**
 * Devuelve la dirección MAC del dispositivo o null en caso de no existir.
 *
 * @return dirección MAC del dispositivo o null en caso de no existir
 */
fun getMAC(): String? {
    val networkInterface: NetworkInterface
    val sb = StringBuilder()

    try {
        networkInterface = NetworkInterface.getByInetAddress(InetAddress.getLocalHost())
        val mac = networkInterface.hardwareAddress

        for (i in mac.indices)
            sb.append(String.format("%02X%s", mac[i], if (i < mac.size - 1) "-" else ""))
    } catch (e: Exception) {
        e.printStackTrace()
    }

    val macString = sb.toString()

    return if (macString.isEmpty()) null else macString
}

/**
 * Devuelve el número telefónico de la linea 1 del dispositivo o null en caso de no
 * existir o no tener el permiso READ_PHONE_STATE.
 *
 * @param context Entorno de la aplicación
 *
 * @return número telefónico de la linea 1 o null en caso de no existir o no
 * tener el permiso READ_PHONE_STATE
 */
/*fun getPhoneNumber(context: Context): String? {
    val telMgr: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
            == PackageManager.PERMISSION_GRANTED
        ) telMgr.line1Number
        else null
    } else telMgr.line1Number
}*/

/**
 * Gets the number of cores available in this device, across all processrs.
 *
 * @return The number of cores, or 1 if failed to get result
 */
fun getNumberOfCores(): Int {
    return Runtime.getRuntime().availableProcessors()
}



/**
 * Devuelve el identificador Android del dispositivo
 *
 * @param resolver
 *
 * @return identificador Android
 */
fun getAndroidID(resolver: ContentResolver): String {
    return Settings.Secure.getString(resolver, Settings.Secure.ANDROID_ID)
}

/**
 * Devuelve el número de serie del dispositivo
 *
 * @param resolver
 *
 * @return número de serie del dispositivo
 */
fun getSerialNumber(resolver: ContentResolver): String {
    return if (Build.SERIAL != Build.UNKNOWN) Build.SERIAL
    else Settings.Secure.getString(resolver, Settings.Secure.ANDROID_ID)
}
/*
fun isNetworkAvailable(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo: NetworkInfo? = cm.activeNetworkInfo

    return activeNetworkInfo != null && activeNetworkInfo.isConnected
}*/
// endregion

// region Generate Methods
/**
 * Genera un nombre de archivo teniendo en cuenta hora y fecha de creación.
 *
 * @return Nombre de archivo
 */
@JvmOverloads
fun generateFileName(prefix: String = "JAGC"): String {
    val calendar = Calendar.getInstance()

    val year = calendar.year()
    val month = calendar.month()
    val dayOfMonth = calendar.dayMonth()
    var hours = calendar.get(Calendar.HOUR)
    val minutes = calendar.get(Calendar.MINUTE)
    val t = if (calendar.get(Calendar.AM_PM) == Calendar.PM) "PM" else "AM"

    if (hours == 0) {
        hours = 12
    }

    return "${prefix}_${year}_${month.to2Digits()}_${dayOfMonth.to2Digits()}_${hours.to2Digits()}${minutes.to2Digits()}$t"
}

/**
 * Codifica un valor [String] dado generando un serial en la forma XXXX-XXXX-XXXX-XXXX.
 *
 * @param key valor [String] a cifrar
 * @return valor [String] codificado en la forma XXXX-XXXX-XXXX-XXXX
 */
fun generateSerial(key: String): String {
    val md: MessageDigest
    val sb = StringBuilder()
    var ser: String

    try {
        md = MessageDigest.getInstance("MD5")
        md.reset()
        md.update(key.toByteArray())
        val bytes = md.digest()

        for (i in bytes.indices) {
            val hex = Integer.toHexString(0xff and bytes[i].toInt())

            if (hex.length == 1) sb.append('0')

            sb.append(hex)
        }
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    ser = sb.toString().toUpperCase()
    ser = ser.substring(0, 16)
    ser = ser.substring(0, 4) + "-" + ser.substring(4, 8) + "-" + ser.substring(8, 12) + "-" +
            ser.substring(12)

    return ser
}
// endregion