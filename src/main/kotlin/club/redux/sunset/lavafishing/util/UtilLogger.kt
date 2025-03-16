package club.redux.sunset.lavafishing.util

import com.mojang.logging.LogUtils
import org.slf4j.Logger

object UtilLogger {
    inline val Any.logger: Logger get() = LogUtils.getLogger()
}