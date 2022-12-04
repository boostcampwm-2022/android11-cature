package com.woory.presentation.binding

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.woory.presentation.R
import com.woory.presentation.model.Promise
import com.woory.presentation.ui.history.PromiseHistoryViewType
import com.woory.presentation.util.TimeUtils.getDurationStringInMinuteToDay
import org.threeten.bp.OffsetDateTime

@BindingAdapter(value = ["itemStateType", "itemStatePromise"], requireAll = true)
fun AppCompatTextView.bindItemState(type: PromiseHistoryViewType, promise: Promise) {
    val currentDateTime = OffsetDateTime.now()
    val beforeStartTime =
        getDurationStringInMinuteToDay(context, currentDateTime, promise.data.gameDateTime)
    val beforeEndTime =
        getDurationStringInMinuteToDay(context, currentDateTime, promise.data.promiseDateTime)

    text = when (type) {
        PromiseHistoryViewType.BEFORE -> context.getString(
            R.string.history_item_state_before,
            beforeStartTime
        )
        PromiseHistoryViewType.ONGOING -> context.getString(
            R.string.history_item_state_ongoing,
            beforeEndTime
        )
        PromiseHistoryViewType.END -> context.getString(R.string.promises_end)
    }
}

@BindingAdapter("date_time")
fun AppCompatTextView.bindDateTime(dateTime: OffsetDateTime) = with(dateTime) {
    text = context.getString(
        R.string.date_time_template,
        year,
        monthValue,
        dayOfMonth,
        hour,
        minute
    )
}