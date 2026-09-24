![](https://socialify.git.ci/YisRime/Lyric-Getter/image?description=1&descriptionEditable=通过Hook获取音乐软件的歌词，提供给其他模块\软件使用&language=1&name=1&owner=1&theme=Auto)
---
![Release Download](https://img.shields.io/github/downloads/Xposed-Modules-Repo/de.yisrime.lrcget/total?style=flat-square)  
![Release Download](https://img.shields.io/github/downloads/YisRime/Lyric-Getter/total?style=flat-square)  
[![Release Version](https://img.shields.io/github/v/release/YisRime/Lyric-Getter?style=flat-square)](https://github.com/YisRime/Lyric-Getter/releases/latest)  
[![GitHub license](https://img.shields.io/github/license/YisRime/Lyric-Getter?style=flat-square)](https://github.com/YisRime/Lyric-Getter/LICENSE)  
[![GitHub Star](https://img.shields.io/github/stars/YisRime/Lyric-Getter?style=flat-square)](https://github.com/YisRime/Lyric-Getter/stargazers)  
[![GitHub Fork](https://img.shields.io/github/forks/YisRime/Lyric-Getter?style=flat-square)](https://github.com/YisRime/Lyric-Getter/network/members)  
![GitHub Repo size](https://img.shields.io/github/repo-size/YisRime/Lyric-Getter?style=flat-square&color=3cb371)  
[![GitHub Repo Languages](https://img.shields.io/github/languages/top/YisRime/Lyric-Getter?style=flat-square)](https://github.com/YisRime/Lyric-Getter/search?l=koltin)  
[![Build Status](https://img.shields.io/endpoint.svg?url=https%3A%2F%2Factions-badge.atrox.dev%2FYisRime%2FLyric-Getter%2Fbadge%3Fref%3Dmain&style=flat)](https://actions-badge.atrox.dev/YisRime/Lyric-Getter/goto?ref=main)  
![GitHub Star](https://img.shields.io/github/stars/YisRime/Lyric-Getter.svg?style=social)

## 版本与作者

- 版本：1.0.7（versionCode 107）
- 原项目作者：[xiaowine](https://github.com/xiaowine/Lyric-Getter)
- 本分支维护与 libxposed 适配：[Yis_Rime](https://github.com/YisRime)

本版将模块入口由 `assets/xposed_init` 迁移至 `META-INF/xposed/java_init.list`，Hook 层改用 libxposed API（`io.github.libxposed:api:101`、EzXHelper 3.x），配置读写改由 libxposed RemotePreferences 承载，不再依赖全局可读的 XML 偏好文件；模块仅在使用 LyricGetter 时把配置从旧的 `shared_prefs` 一次性导入，随后删除旧文件。因不再走 legacy Xposed API，本模块要求宿主框架实现 libxposed 规范，LSPosed 1.x 与 LSPatch 等仅支持旧 API 的框架无法加载。最低系统版本相应提高至 Android 10。

## 这是什么东西？

#### 这是一个Xposed模块（需框架支持 libxposed 规范，LSPosed 2.x 及以上），通过Hook获取音乐软件的歌词，提供给其他模块\软件使用

## 为什么我的歌词不隐藏？

#### 因为模块通过监听媒体通知事件，来判断是否应该隐藏歌词。而部分音乐软件默认通知样式为自定义的，所以需要将通知样式改为系统样式
---
### 支持软件请在模块内查看，或查看[规则文件](https://github.com/YisRime/Lyric-Getter/blob/gh-pages/app_rules.json)
---

### 你也可以主动使用本模块API，用于收发歌词[Lyric-Getter-Api](https://github.com/xiaowine/Lyric-Getter-Api)

---

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=YisRime/Lyric-Getter&type=Timeline)](https://star-history.com/#YisRime/Lyric-Getter&Timeline)

## Thanks
[<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jb_beam.png" width="200"/>](https://www.jetbrains.com)
