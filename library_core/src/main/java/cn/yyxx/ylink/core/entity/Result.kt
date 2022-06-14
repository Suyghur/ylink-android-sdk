package cn.yyxx.ylink.core.entity

/**
 * @author #Suyghur.
 * Created on 2022/6/14
 */
data class Result<T>(
    val code: Int,
    val msg: String,
    val data: T
)