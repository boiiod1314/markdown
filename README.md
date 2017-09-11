基于markdown的文档管理系统

安装步骤

>git clone git@github.com:boiiod1314/markdown.git
>mvn clean
>mvn package -DskipTests -Ppro

打包成功后，在target目录会生产 doc-1.0-assembly.tar.gz 文件

解压 可以看到  bin  conf  lib 目录 

运行项目

>sh bin/start.sh

访问地址： http://ip:9999/

编辑地址： http://ip:9999/editlist.html

