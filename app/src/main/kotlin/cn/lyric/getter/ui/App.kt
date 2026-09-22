package cn.lyric.getter.ui

import android.app.Application
import android.content.Context
import cn.lyric.getter.BuildConfig
import cn.lyric.getter.config.ConfigStore
import cn.lyric.getter.tool.ActivityTools
import cn.lyric.getter.tool.ConfigTools.config
import cn.xiaowine.xkt.AcTool
import cn.xiaowine.xkt.LogTool
import cn.xiaowine.xkt.LogTool.log
import com.google.android.material.color.DynamicColors
import io.github.libxposed.service.XposedService
import io.github.libxposed.service.XposedServiceHelper

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
        AcTool.init(this)
        ActivityTools.application = this
        LogTool.init("Lyrics Getter", { BuildConfig.DEBUG })
        XposedServiceHelper.registerListener(object : XposedServiceHelper.OnServiceListener {
            override fun onServiceBind(service: XposedService) {
                runCatching {
                    ConfigStore.attach(service.getRemotePreferences(ConfigStore.GROUP), true)
                    importLegacyPreferences()
                    config.updateTime = System.currentTimeMillis()
                }.onFailure { "Service bind failed: ${it.message}".log() }
            }

            override fun onServiceDied(service: XposedService) {
                ConfigStore.detach()
            }
        })
    }

    private fun importLegacyPreferences() {
        val name = ConfigStore.GROUP
        val direct = createDeviceProtectedStorageContext()
        val legacy = listOf(
            direct.getSharedPreferences(name, Context.MODE_PRIVATE),
            getSharedPreferences(name, Context.MODE_PRIVATE),
        )
        if (ConfigStore.isEmpty) {
            legacy.firstOrNull { !it.all.isNullOrEmpty() }?.let { ConfigStore.putAll(it.all) }
        }
        direct.deleteSharedPreferences(name)
        deleteSharedPreferences(name)
    }
}
