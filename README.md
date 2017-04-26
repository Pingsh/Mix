# Mix
RxJava基本使用&amp;自定义Behavior联动&amp;FlexLayout基础&amp;Dagger2+DataBinding+RxJava+Retrofit+MVP
### 功能简介
#### 建立 & 请求 
* 是RxJava的本地实现，一些主要操作符和主要方法的使用，为了不影响其他，进行了注释, 想要了解的, 可以看这里 [RxJava 2.0](http://www.jianshu.com/u/c50b715ccaeb). 此外, 后来找到的一些项目中, 大多使用了 RxJava1.0 ,关于两者的区别, 可以看看这里 [RxJava1.X升级到RxJava2.X](http://www.jianshu.com/p/2badfbb3a33b)
#### 下载
* 是模仿其他人写的MVC和MVP对比简单例子，其中的MVP是错误的写法，具体原因可以参见注释。在这里出现了对权限处理的小问题，引入了他人封装的库,  [权限封装](https://github.com/lovedise/PermissionGen)。 
#### 联动 
* 花费时间最多的一个模块，主要是因为踩坑。24.2.0以后的库对Behavior进行了修改，至今发现了两个大坑。一是对GONE的子布局不做处理，二是新增了属性peekHeightAuto，需要在布局里做处理。仿知乎的界面，实现了换肤功能，换肤功能来源于[remusic](https://github.com/aa112901/remusic)。   ,只知道重点API是对ActivityManager.TaskDescription的一些操作，原理还没有细看。此外，BottomSheetDialog是参考国外大神写的，是对BottomSheetDialog和DataBinding的一个基础示例，顺便折腾了共享元素和ConstraintLayout（默默吐槽一句，用得实在不顺手）。
#### Flex 
* 没花多少心思，后来干脆直接跳转到官方APP上进行尝试了，个人感觉这种流式布局还不是很完善，而且，我只要引入支持RecyclerView的那个库，就会报错，理解用法之后，无心深究，下次单独弄个仓库折腾
#### Dagger
* RRDD（Retrofit+RxJava+DataBinding+Dagger2） + MVP，本来想参考咕咚翻译的，有点小问题，预留给Dagger2完成, 个人觉得Dagger不是很好用, 降低了代码的可读性, 封装的粒度很难掌握,而且, 一旦出错就不好定位, 对于我这种不很擅长找BUG的人来说,简直了.关于Dagger的知识点,是在这里GET的.[dagger2让你爱不释手](http://www.jianshu.com/p/65737ac39c44)
#### Dagger2
* 同样是RRDD，和Dagger的区别在于V和P的处理，参考了谷歌 MVP+Dagger 的 官方Demo。
#### MVVM
* 初步上手了一下, 不是很会用, 等单独的module完成之后再上传.总结了一些东西, 不知道是否正确, 不太敢传上去, 怕误人子弟了...[总结在这里](http://note.youdao.com/noteshare?id=95e1cbb161f2c60d02287c603245795b), 有兴趣的可以看看.
#### AutoActivity
* 简单引入了[张鸿洋的自动布局](http://blog.csdn.net/lmj623565791/article/details/49990941), 只是一个基本尝试
* 其中的TextView是重点, 从[TextView图文混排](http://www.jianshu.com/p/72d31b7da85b) get 到新知识, 解决了setMovementMethod(LinkMovementMethod.getInstance())之后, maxLines失效 , 文本可滑动的问题.比较实用的知识点,在即时通讯、评论等地方用得上， 推荐阅读和深挖.
