package cn.yyxx.ylink.ui.ext

import android.content.Context
import android.widget.Toast

/**
 * @author #Suyghur.
 * Created on 2022/6/6
 */


fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}