# Paging3Demo

本 Demo 分别使用了 Kotlin+协程、RxJava 的方式实现了简单的 Paging3 网络请求 Demo.

demo.mp4
onErrorReturn_Error.png
Paging3.xmind

## 后续迭代功能

- Paging3 自带的 Header\Footer
- 数据来源: 数据库+网络

## 遇到的问题

### 数据不刷新

重新进入页面时, 由于数据源来自 viewModel , 所以不会重新获取.

解决方案: 改属性为函数形式获取 Pager,用来确保每次都是新的 Pager 对象来重新加载数据

### Rxjava 处理 Error 时, onErrorReturn 报错

使用 RxArticlesPagingSource 时,使用 kotlin, 在 loadSingle 中使用 onErrorReturn 函数来处理 Error 时,会报错

解决方案: 使用 java 就 ok 了...

### 数据刷新,界面更新慢

刷新重新获取数据源时, loading 消失, 列表显示出来,之前的数据会先加载出来, 再变成新的数据源

解决方案: 加标志位,数据第一次显示出来时, 列表延迟显示 (没想到更好的方案...)