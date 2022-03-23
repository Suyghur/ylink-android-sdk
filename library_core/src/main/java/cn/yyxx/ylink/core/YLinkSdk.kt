package cn.yyxx.ylink.core

/**
 * @author #Suyghur.
 * Created on 2022/03/22
 */
class YLinkSdk private constructor() {

    companion object {

        @JvmStatic
        fun getInstance(): YLinkSdk {
            return YLinkSdkHolder.INSTANCE
        }

        private object YLinkSdkHolder {
            val INSTANCE = YLinkSdk()
        }

        /**
         * 防止单例对象在反序列化时重新生成对象
         */
        private fun readResolve(): Any {
            return YLinkSdkHolder.INSTANCE
        }
    }
}