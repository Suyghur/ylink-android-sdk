package cn.yyxx.ylink.core.utils

import android.app.Application
import android.util.Log
import com.dolin.zap.Zap
import com.dolin.zap.entity.Config
import com.dolin.zap.entity.Level
import java.lang.reflect.Array

/**
 * @author #Suyghur.
 * Created on 2022/03/22
 */
object Logger {
    private const val TAG: String = "ylink_sdk"

    private var hasZapInit = false

    internal fun initZap(application: Application) {
        //初始化Zap日志框架
        val config = Config.Builder()
            //logcat输出最低等级
            .setLogcatLevel(Level.DEBUG)
            //是否开启缓存日志
            .setRecordEnable(true)
            //缓存日志最低等级
            .setRecordLevel(Level.DEBUG)
            //是否开启压缩缓存日志内容
            .setCompressEnable(true)
            //缓存文件的过期时间
            .setOverdueDay(3)
            //缓存文件大小限制，超过则会自动扩容新文件
            .setFileSizeLimitDay(10)
            .create()
        Zap.initialize(application, config)
        hasZapInit = true
    }

    @JvmStatic
    fun d(any: Any?) {
        d(TAG, any)
    }

    @JvmStatic
    fun d(tag: String, any: Any?) {
        if (hasZapInit) {
            Zap.d(tag, any)
        } else {
            any.print(Level.DEBUG, tag)
        }
    }

    @JvmStatic
    fun i(any: Any?) {
        i(TAG, any)
    }

    @JvmStatic
    fun i(tag: String, any: Any?) {
        if (hasZapInit) {
            Zap.i(tag, any)
        } else {
            any.print(Level.INFO, tag)
        }
    }

    @JvmStatic
    fun e(any: Any?) {
        d(TAG, any)
    }

    @JvmStatic
    fun e(tag: String, any: Any?) {
        if (hasZapInit) {
            Zap.e(tag, any)
        } else {
            any.print(Level.ERROR, tag)
        }
    }

    private fun Any?.print(level: Level, tag: String) {
        val msg = if (this == null) {
            "null"
        } else {
            val clz: Class<*> = javaClass
            if (clz.isArray) {
                val sb = StringBuilder(clz.simpleName)
                sb.append(" [ ")
                val len = Array.getLength(this)
                for (i in 0 until len) {
                    if (i != 0 && i != len - 1) {
                        sb.append(", ")
                    }
                    val tmp = Array.get(this, i)
                    sb.append(tmp)
                }
                sb.append(" ] ")
                sb.toString()
            } else {
                "$this"
            }
        }

        when (level) {
            Level.DEBUG -> Log.d(tag, msg)
            Level.INFO -> Log.i(tag, msg)
            Level.ERROR -> Log.e(tag, msg)
        }
    }
}