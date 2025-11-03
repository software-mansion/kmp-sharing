package com.swmansion.kmpsharing.sample

import androidx.compose.runtime.Composable
import kotlin.random.Random
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreGraphics.*
import platform.Foundation.*
import platform.UIKit.*

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun createAndSaveTestBitmap(): String? {
    val width = 512.0
    val height = 512.0
    val size = CGSizeMake(width, height)

    UIGraphicsBeginImageContextWithOptions(size, true, 1.0)
    val ctx = UIGraphicsGetCurrentContext() ?: run {
        UIGraphicsEndImageContext()
        return null
    }

    val bgColor = UIColor.colorWithRed(
        Random.nextDouble(),
        Random.nextDouble(),
        Random.nextDouble(),
        1.0
    )
    CGContextSetFillColorWithColor(ctx, bgColor.CGColor)
    CGContextFillRect(ctx, CGRectMake(0.0, 0.0, width, height))

    repeat(5) {
        val radius = Random.nextInt(20, 100).toDouble()
        val cx = Random.nextDouble(radius, width - radius)
        val cy = Random.nextDouble(radius, height - radius)
        val circleColor = UIColor.colorWithRed(
            Random.nextDouble(),
            Random.nextDouble(),
            Random.nextDouble(),
            1.0
        )
        CGContextSetFillColorWithColor(ctx, circleColor.CGColor)
        val circleRect = CGRectMake(cx - radius, cy - radius, radius * 2, radius * 2)
        CGContextFillEllipseInRect(ctx, circleRect)
    }

    val image = UIGraphicsGetImageFromCurrentImageContext()
    UIGraphicsEndImageContext()
    if (image == null) return null

    val data = UIImageJPEGRepresentation(image, 1.0) ?: return null
    val filename = "test_image_${NSDate().timeIntervalSince1970}.jpg"
    val path = (NSTemporaryDirectory() as NSString).stringByAppendingPathComponent(filename)

    return if (data.writeToFile(path, true)) {
        "file://$path"
    } else {
        null
    }
}
