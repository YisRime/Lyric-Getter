package cn.lyric.getter.hook.app

import android.widget.LinearLayout
import cn.lyric.getter.hook.BaseHook
import cn.lyric.getter.tool.HookTools
import cn.xiaowine.xkt.Tool
import io.github.kyuubiran.ezxhelper.core.util.ClassUtil.loadClass
import io.github.kyuubiran.ezxhelper.xposed.dsl.HookFactory.`-Static`.createHook
import io.github.kyuubiran.ezxhelper.core.finder.MethodFinder.`-Static`.methodFinder

object NeteaseLite : BaseHook() {
    private var nowLyric: String = ""

    private var nextLyric: String by Tool.observableChange("") { _, _, newValue ->
        if (nowLyric.isNotEmpty()) {
            HookTools.eventTools.sendLyric(nowLyric)
        }
        nowLyric = newValue
    }

    override fun init() {
        super.init()
        loadClass("com.netease.cloudmusic.meta.LyricLine").methodFinder().filterByName("getContent").first().createHook {
            after {
                nextLyric = it.result.toString()
            }
        }
        loadClass("android.view.WindowManagerImpl").methodFinder().first { name == "addView" }.createHook {
            after { view ->
                if (view.args[0]!!::class.java.name.contains("floatlyric")) {
                    (view.args[0] as LinearLayout).removeAllViews()
                }
            }
        }
    }
}
