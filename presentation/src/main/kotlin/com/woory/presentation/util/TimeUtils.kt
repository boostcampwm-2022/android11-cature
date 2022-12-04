package com.woory.presentation.util

import android.content.Context
import androidx.annotation.StringRes
import com.woory.presentation.R
import org.threeten.bp.Duration
import org.threeten.bp.OffsetDateTime

object TimeUtils {

    fun getDurationStringInMinuteToDay(
        context: Context,
        startDateTime: OffsetDateTime,
        endDateTime: OffsetDateTime
    ): String {
        val duration = Duration.between(startDateTime, endDateTime)

        val days = duration.toDaysPart()
        val hours = duration.toHoursPart()
        val minutes = duration.toMinutesPart()
        val seconds = duration.toSecondsPart()

        val daysWithSuffix = getTimeStringWithSuffix(context, DateTimeType.DAY, days.toInt())
        val hoursWithSuffix = getTimeStringWithSuffix(context, DateTimeType.HOUR, hours)
        val minutesWithSuffix = getTimeStringWithSuffix(context, DateTimeType.MINUTE, minutes)
        val secondsWithSuffix = getTimeStringWithSuffix(context, DateTimeType.SECOND, seconds)

        return if (days > 0L) {
            daysWithSuffix
        } else if (hours > 0) {
            listOf(hoursWithSuffix, minutesWithSuffix).filterNot { it.isEmpty() }.joinToString(" ")
        } else {
            minutesWithSuffix
        }
    }

    private fun getTimeStringWithSuffix(
        context: Context,
        dateTimeType: DateTimeType,
        value: Int
    ): String =
        if (value > 0) {
            dateTimeType.withSuffix(context, value)
        } else {
            ""
        }

    enum class DateTimeType(@StringRes private val withSuffixResId: Int) {
        YEAR(R.string.years_with_suffix),
        MONTH(R.string.months_with_suffix),
        DAY(R.string.days_with_suffix),
        HOUR(R.string.hours_with_suffix),
        MINUTE(R.string.minutes_with_suffix),
        SECOND(R.string.seconds_with_suffix);

        fun withSuffix(context: Context, value: Int): String =
            context.getString(withSuffixResId, value)
    }
}