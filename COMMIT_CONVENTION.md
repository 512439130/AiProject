# 代码提交规范 (Commit Convention)

## 概述

本文档定义了本项目的 Git 提交信息规范，旨在：
- 生成清晰的变更历史
- 便于自动化生成 Changelog
- 使代码审查更高效

## 提交格式

每个提交信息包含以下结构：

```
<type>(<scope>): <subject>

<body>

<footer>
```

- **type** (必需): 提交类型
- **scope** (可选): 影响范围
- **subject** (必需): 简短描述
- **body** (可选): 详细描述
- **footer** (可选): 脚注信息

## 提交类型 (Type)

| 类型 | 说明 | 示例 |
|------|------|------|
| `feat` | 新功能 | `feat(login): 添加手机号验证码登录` |
| `fix` | Bug 修复 | `fix(order): 修复订单列表刷新异常` |
| `docs` | 文档更新 | `docs(readme): 更新构建说明` |
| `style` | 代码格式（不影响代码含义）| `style: 统一缩进为4个空格` |
| `refactor` | 代码重构 | `refactor(user): 重构用户存储逻辑` |
| `perf` | 性能优化 | `perf(list): 优化列表滑动流畅度` |
| `test` | 测试相关 | `test(login): 添加登录单元测试` |
| `chore` | 构建/工具变动 | `chore(gradle): 升级AGP到8.13.0` |
| `revert` | 回滚提交 | `revert: 回滚feat: xxx` |

## 影响范围 (Scope)

用于说明提交影响的模块或组件，可选但建议使用：

| 范围 | 说明 |
|------|------|
| `app` | 整体应用级别 |
| `login` | 登录模块 |
| `order` | 订单模块 |
| `profile` | 个人中心模块 |
| `nav` | 导航相关 |
| `ui` | UI/布局相关 |
| `data` | 数据/存储相关 |
| `build` | 构建配置 |
| `deps` | 依赖更新 |

## 主题描述 (Subject)

- 使用**动词开头**，第一人称现在时（"change" 而非 "changed"/"changes"）
- 首字母**不要**大写
- 末尾**不加**句号或其他标点
- 不超过 50 个字符

**良好示例:**
- `feat(login): 添加国家码选择器`
- `fix(order): 修复订单详情展开崩溃`

**不良示例:**
- `feat: Added new feature` (过去时，英文)
- `fix:修复bug.` (格式不规范，有句号)

## 正文 (Body)

当需要详细说明时使用：

- 说明**为什么**做这次修改（动机）
- 与之前行为的**对比**
- 每行不超过 72 个字符
- 使用空行分隔段落

示例：
```
fix(order): 修复订单列表数据重复问题

订单列表在快速下拉刷新时，由于异步请求未取消，
导致旧数据与新数据同时返回，出现重复项。

现在会在发起新请求前取消未完成的请求。
```

## 脚注 (Footer)

用于引用 Issue 或 Breaking Changes：

```
Closes #123, #456

BREAKING CHANGE: 登录接口返回值结构变更
```

## 完整示例

```
feat(login): 实现手机号验证码登录功能

- 添加手机号格式校验
- 集成短信验证码发送接口
- 添加60秒倒计时功能

Closes #15
```

```
refactor(data): 将 SharedPreferences 封装为 Repository 模式

将 UserStorage 从单例对象改为 UserRepository 接口实现，
便于后续支持多种存储方式（DataStore、Room等）。

BREAKING CHANGE: UserStorage 已废弃，请使用 UserRepository
```

## Android 项目特殊规范

### 资源文件修改
```
style(ui): 调整登录页面按钮样式

添加圆角背景和渐变色，适配深色模式。
```

### 布局修改
```
fix(layout): 修复小屏设备登录页面被遮挡

使用 ConstraintLayout 替换 LinearLayout 嵌套，
支持键盘弹出时自动调整布局。
```

### 依赖更新
```
chore(deps): 升级 Navigation 组件到 2.8.0

- androidx.navigation:navigation-fragment-ktx: 2.7.7 -> 2.8.0
- androidx.navigation:navigation-ui-ktx: 2.7.7 -> 2.8.0
```

## 提交前检查清单

- [ ] 提交信息遵循上述格式
- [ ] 使用正确的 type 和 scope
- [ ] subject 清晰描述了变更内容
- [ ] 代码已本地测试通过
- [ ] 相关单元测试已更新/通过

## 工具推荐

- **Android Studio**: 使用 Git Commit Template 插件
- **命令行**: 使用 `git commit -m "type(scope): subject"`
- **提交钩子**: 可配置 commit-msg hook 自动校验格式

---

*规范参考: [Conventional Commits](https://www.conventionalcommits.org/)*
