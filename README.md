# AiProject (FCBox Locker AI)

Ai Test Project - Android 智能柜/物流订单管理应用

---

## 项目概述

- **应用ID**: `com.fcbox.locker.ai.project`
- **类型**: Android 原生应用
- **业务领域**: 智能柜/物流订单管理

## 技术栈

| 层级 | 技术选型 |
|------|----------|
| 语言 | Kotlin 2.0.21 |
| 构建工具 | Gradle Kotlin DSL 8.13.0 |
| 最低 SDK | API 21 (Android 5.0) |
| 目标 SDK | API 36 (Android 16) |
| Java 版本 | 11 |

## 架构模式

### 整体架构: Single-Activity + Fragment Navigation

```
┌─────────────────────────────────────────┐
│           MainActivity                  │
│    (单Activity容器，沉浸式状态栏配置)      │
└─────────────────┬───────────────────────┘
                  │
    ┌─────────────┼─────────────┐
    ▼             ▼             ▼
┌────────┐   ┌────────┐   ┌──────────┐
│ Splash │   │ Login  │   │  Main    │
│Fragment│   │Fragment│   │ Fragment │
└────────┘   └────────┘   └────┬─────┘
                               │
              ┌────────────────┼────────────────┐
              ▼                ▼                ▼
        ┌──────────┐    ┌──────────┐     ┌──────────┐
        │  Orders  │    │ Profile  │     │ WebView  │
        │ Fragment │    │ Fragment │     │ Fragment │
        └──────────┘    └──────────┘     └──────────┘
```

### 导航结构

**主导航图** (`nav_graph.xml`):
- SplashFragment → (检查登录状态) → LoginFragment / MainFragment
- LoginFragment → (登录成功) → MainFragment
- MainFragment → (登出) → LoginFragment
- WebViewFragment (通用网页展示)

**嵌套导航图** (`nav_main_graph.xml`):
- OrdersFragment (默认页面)
- ProfileFragment

### 状态管理

- **UserStorage**: SharedPreferences 封装单例
  - `is_logged_in`: 登录状态
  - `user_phone`: 用户手机号
  - `user_name`: 用户名

## 依赖体系

### 核心依赖 (gradle/libs.versions.toml)

| 依赖 | 版本 |
|------|------|
| Android Gradle Plugin | 8.13.0 |
| Kotlin | 2.0.21 |
| Core KTX | 1.10.1 |
| AppCompat | 1.6.1 |
| Material Design | 1.11.0 |
| Navigation | 2.7.7 |

### 功能库
- `androidx.core:core-ktx` - Kotlin 扩展
- `androidx.appcompat:appcompat` - AppCompat 支持
- `com.google.android.material:material` - Material Design
- `androidx.navigation:navigation-fragment-ktx` - 导航组件
- `androidx.navigation:navigation-ui-ktx` - 导航 UI

## 代码规范

### 包结构
```
com.fcbox.locker.ai.project/
├── MainActivity.kt          # 主Activity
├── SplashFragment.kt        # 启动页
├── LoginFragment.kt         # 登录页
├── MainFragment.kt          # 主容器
├── OrdersFragment.kt        # 订单列表
├── ProfileFragment.kt       # 个人中心
├── WebViewFragment.kt       # 网页容器
├── CountryCodeBottomSheet.kt # 国家码选择器
├── OrderAdapter.kt          # 订单列表适配器
├── OrderModels.kt           # 数据模型
└── UserStorage.kt           # 用户状态存储
```

### 命名规范
- **类名**: PascalCase (`SplashFragment`, `OrderAdapter`)
- **包名**: 全小写，域名反写 (`com.fcbox.locker.ai.project`)
- **布局文件**: snake_case (`fragment_splash.xml`, `item_order.xml`)
- **资源ID**: snake_case (`@+id/splashFragment`)

### View Binding 使用规范
- 所有 Fragment/Activity 启用 View Binding
- 绑定类命名: `FragmentSplashBinding`, `ActivityMainBinding`
- 内存管理: 在 `onDestroyView()` 中清理 binding 引用

### 状态栏处理
- 使用 `WindowCompat.setDecorFitsSystemWindows(window, false)` 实现沉浸式
- 主题使用 `Theme.AiProject.NoActionBar`

## 资源组织

```
res/
├── layout/           # 布局文件
│   ├── activity_*.xml
│   ├── fragment_*.xml
│   ├── item_*.xml
│   └── dialog_*.xml
├── navigation/       # 导航图
│   ├── nav_graph.xml
│   └── nav_main_graph.xml
├── drawable/         # 矢量图和形状
├── color/            # 颜色选择器
├── menu/             # 菜单定义
└── values/           # 字符串、颜色、样式
```

## 构建命令

```bash
# 构建
./gradlew build
./gradlew assembleDebug

# 测试
./gradlew test
./gradlew connectedAndroidTest

# 代码质量
./gradlew lint
```

## 关键实现模式

### 1. 启动页逻辑
```kotlin
// SplashFragment: 2秒延迟后检查登录状态
Handler(Looper.getMainLooper()).postDelayed({
    if (UserStorage.isLoggedIn(requireContext())) {
        findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
    } else {
        findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
    }
}, 2000)
```

### 2. 导航栈管理
- 登录成功: `popUpTo="@id/nav_graph" popUpToInclusive="true"` 清除栈
- 启动页跳转: `popUpToInclusive="true"` 移除 Splash

### 3. 嵌套导航
- MainFragment 内部使用 `R.id.nav_main_container` 作为子 NavHost
- BottomNavigationView 配合 `setupWithNavController()` 使用

## 注意事项

- 所有网络请求需要 `INTERNET` 权限
- 使用 SharedPreferences 进行轻量级状态存储
- View Binding 自动清理避免内存泄漏
- 支持多语言 (values-en/)

---

*此文档由 Claude Code 自动生成，用于记录项目技术架构和代码规范。*
