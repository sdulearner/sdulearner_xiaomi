# Day2

1. 四种启动模式启动Activity的生命周期MyActivity
2. Service生命周期
3. 动态和静态广播-NetworkBroadcastReceiver、NetworkReceiverActivity
4. AIDL跨进程通信
5. ContentProvider获取通讯录

**Activity启动模式**

standard：标准模式，也是系统默认的模式。每次启动一个Activity都会创建一个新的实例，不管这个实例是否存在。一个任务栈中可以有多个实例，每个实例也可以属于不同的任务栈。

singTop：栈顶复用模式，这个模式下，如果需要启动的新Activity已经处于任务栈的栈顶了，那它就不会重新创建。

singleTask：栈内复用模式，该模式下，如果需要启动的新Activity在任务栈中存在，那么也不会创建新的实例。

singleInstance：单实例模式，这是一种加强型的singleTask模式，每个Activity实例只能独自位于一个任务栈中。



**Service生命周期**

<img src="./images/Day2/20180703002615794.png" alt="Service生命周期"  />



**AIDL跨进程算数**

<img src="./images/Day2/Screenshot_20240602220104.png" alt="AIDL跨进程算数" style="zoom:50%;" />



**ContentProvider获取通讯录**

<img src="./images/Day2/Screenshot_20240602211747.png" alt="ContentProvider获取通讯录" style="zoom:50%;" />

<img src="./images/Day2/Screenshot_20240602213632.png" alt="ContentProvider获取通讯录" style="zoom:50%;" />





