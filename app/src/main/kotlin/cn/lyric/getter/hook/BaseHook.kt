package cn.lyric.getter.hook

abstract class BaseHook {
    var isInit: Boolean = false
    open fun init() {}
}
