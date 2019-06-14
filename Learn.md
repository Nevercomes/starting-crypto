#### 前言
内容概述
1. 安全和密码
2. 常用安全体系介绍
3. 密码分类及Java的安全组成
4. JDK相关包及第三方扩展
5. Base64算法介绍

[Java8 API](https://docs.oracle.com/javase/8/docs/api/index.html)


#### 密码学术语
明文空间，密文空间，密钥空间，密码算法（加密算法、解密算法）

柯克霍夫原则：数据的安全基于密钥而不是算法的保密。即系统的安全取决于密钥，对密钥保密，对算法公开。——现代密码学设计的基本原则

#### 密码分类
##### 按时间分类
- **古典密码** 以字符为基本加密单位
- **现代密码** 以信息块为基本加密单位

##### 保密内容分类
- **受限制算法** 算法保密性
- **基于密钥算法**

##### 密码体制分类
- **对称密码** 加密与解密密钥相同
- **非对称** 不同，分为私钥和公钥

##### 明文处理方法分类
- **分组密码** 指加密时将明文分成固定长度的组，用同一密钥和算法对每一块加密，输出也是固定长度的密文。多用于网络加密。和报文分组相对应
- **流密码** 序列密码 加密时每次加密一位或一个字节明文

#### 散列函数
特点
1. 长度不受限制
2. 哈希值容易计算
3. 散列运算过程不可逆

相关算法
1. 消息摘要算法MD5
2. SHA 安全散列算法
3. MAC 消息认证码算法

#### 数字签名
主要是针对以数字的形式存储的消息进行处理

其会生成一种带有操作者身份信息的数字编码

其包含签名者和签名算法两个概念

#### OSI与TCP/IP安全体系

#### TCP/IP安全体系

#### Java安全组成
四部分构成
1. JCA Java Cryptography Architecture Java加密体系结构
2. JCE Java Cryptography Extension Java加密扩展
3. JSSE Java Secure Socket Extension Java安全套接字扩展
4. JAAS Java Authentication And Authtication Service Java鉴权与鉴权服务

##### JCA
提供基本的Java加密框架，基本的消息摘要、数字签名等等

##### JCE
在JCA的基础上做一些扩展，提供更多的功能DES、AES、RSA等算法都是由JCE提供，其一般位于JDK的一些包中

##### JSSE
提供基于SSL的加密过程，用于在网络传输中

##### JAAS
在Java平台上来进行鉴权

#### 相关的Java包、类及第三方扩展包

##### java.security
消息摘要 不足以进行加密

##### javax.crypto
安全消息摘要，消息认证

##### java.net.ssl
安全套接字

##### Bouncy Castle

##### Commons codec
- Apache
- Base64、二进制、十六进制、字符集编码
- Url编码/解码


#### Base64算法

**Base64其实只是一种简单的替换算法，是一种基于64个字符的编码算法**

Base64的产生其实来源于邮件传输

因为有些网络传送渠道并不支持所有的字节，例如传统的邮件只支持可见字符的传送，像ASCII码的控制字符就 不能通过邮件传送。这样用途就受到了很大的限制，比如图片二进制流的每个字节不可能全部是可见字符，所以就传送不了。最好的方法就是在不改变传统协议的情况下，做一种扩展方案来支持二进制文件的传送。把不可打印的字符也能用可打印字符来表示，问题就解决了。Base64编码应运而生，**Base64就是一种基于64个可打印字符来表示二进制数据的表示方法**。

具体内容可以参考这篇博客: [Base64原理解析](https://www.cnblogs.com/diligenceday/p/6002382.html)

#### 前言

#### 消息摘要
- MD Message Digest 消息摘要
- SHA Secure Hash Algorithm 安全Hash算法 （散列）
- MAC Message Authentication Code 消息认证码

作用：验证数据的完整性

地位：数字签名的核心算法    

#### MD算法概述
MD家族（128位摘要信息）

#### MD算法实现


#### SHA 
- 安全散列算法
- 固定长度摘要信息
- SHA-1，SHA-2（SHA-224、SHA-256、SHA-384、SHA-512）

SHA是从MD4继承而来的

#### 消息摘要算法应用

拿使用腾讯的接口进行登录举例，通常需要以下几步

1. 加入约定的key
2. 增加时间戳
3. 排序

比如我要调用腾讯的登录接口，我需要给腾讯发送这样子的请求

http(s)://**?msg=sj219SJKJB=&timestamp=1937161279(&key={key}&accountId={id})

其中msg为（原始数据+key+时间戳）的消息摘要，timestamp为发送时的时间戳

接口提供方即腾讯会根据我提供的
1. 时间戳
2. 本身约定好的key
3. 我提供的数据检索条件，如这里是根据账号找到密码
4. 校验msg摘要


#### MAC

MAC也称为HMAC（Keyed-Hash Message Authentication Code），含有密钥的散列函数算法

融合MD、SHA
- MD系列：HmacMD2 HmacMD4 HmacMD5
- SHA系列：HmacSHA1 HmacSHA224 HmacSHA256 HmacSHA384 HmacSHA512

应用比如SecureCRT

#### 对称加密算法
- 初等
- DES 长度不够 3DES
- AES 对DES的补充
- PBE
- IDEA

#### DES
DES（Data Encryption Standard） 数据加密标准

出身不错...但是不断被破解，已经不具备安全性

#### 3DES
三重DES

DES的缺陷
1. 算法不公开，违反了柯克霍夫原则
2. 安全性问题

3DES
1. 密码长度更长
2. 迭代次数更多


#### AES
3DES的效率有点低，速度比较慢

AES是目前使用最多的对称加密算法

AES通常用于移动通信系统的加密以及基于SSH协议的软件（SSH Client、 SecureCRT）

### PBE
PBE算法结合了消息摘要算法和对称加密算法的有点

PBE（Password Based Encryption）基于口令加密

口令简单容易记，由用户自己来管理

口令因为简单，所以可以通过穷举来破解

加盐，以扰码来提高安全度

它的密钥就是口令，所以算法本身没有变化是对AES、DES等的封装实现

它是综合性的，常用的比如 PEBWITHMD5AndDES