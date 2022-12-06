package io.github.sunshinewzy.skydream.util

import io.github.sunshinewzy.sunstcore.objects.SOrderKt

infix fun Int.orderWith(y: Int) = SOrderKt.orderWith(this, y)