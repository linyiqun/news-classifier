(一)windows下的JNI接口使用

1.安装jdk,设置JAVA环境变量
2.确定工程目录下有有效的授权文件user.lic
3.点击桌面开始->运行，键入cmd进入dos界面的命令行
4.进入到工程所在目录
  >cd E:\Project\IctClas_jni_demo
  >e:
6.编译 javac TestMain.java
7.执行 java  TestMain

（二）Linux下的JNI接口使用

1.安装jdk,设置JAVA环境变量
2.确定工程目录下有有效的授权文件user.lic
3.将libICTCLAS50.so 拷贝到/usr/lib下
4.编译 javac -encoding GBK TestMain.java
5.执行 java  TestMain
