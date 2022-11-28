package com.woory.presentation.util

import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import java.util.*

object Constants {
    const val REQUIRE_PERMISSION_TEXT = "Permission is required"

    const val PROMISE_CODE_KEY = "PROMISE_CODE_KEY"

    val zoneId: ZoneId = ZoneId.of("Asia/Seoul")
    val zoneOffset: ZoneOffset = ZoneOffset.of("+09:00")

    val locale: Locale = Locale.KOREA
}