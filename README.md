# Mix
RxJava基本使用&amp;自定义Behavior联动&amp;FlexLayout基础&amp;Dagger2+DataBinding+RxJava+Retrofit+MVP
### 功能简介
* 建立 & 请求 是RxJava的本地实现，一些主要操作符和主要方法的使用，为了不影响其他，被进行了注释
* 下载 是模仿其他人写的MVC和MVP对比简单例子，其中的MVP是错误的写法，具体原因可以参见注释。在这里出现了对权限处理的小问题，compile 'com.lovedise:permissiongen:0.0.6'。
* 联动 花费时间最多的一个模块，主要是因为踩坑。24.2.0以后的库对Behavior进行了修改，至今发现了两个大坑。一是对GONE的子布局不做处理，二是新增了属性peekHeightAuto，需要在布局里做处理。仿知乎的界面，实现了换肤功能，换肤功能来源于remusic,只知道重点API是对ActivityManager.TaskDescription的一些操作，原理还没有细看。此外，BottomSheetDialog是参考国外大神写的，是对BottomSheetDialog和DataBinding的一个基础示例，顺便折腾了共享元素和ConstraintLayout（默默吐槽一句，用得实在不顺手）。
* Flex 模块没花多少心思，后来干脆直接跳转到官方APP上进行尝试了，个人感觉这种流式布局还不是很完善，而且，我只要引入支持RecyclerView的那个库，就会报错
* Dagger2 RRDD（Retrofit+RxJava+DataBinding+Dagger2） + MVP，本来想参考咕咚翻译的，有点小问题，预留给Dagger2完成。
