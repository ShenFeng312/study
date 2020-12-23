# 联系方式
- 手机：19975257673
- Email：shenfeng197@gmail.com
- 微信号：19975257673
---

# 个人信息

 - 沈锋/男/1997
 - 专科/杭州科技职业技术学院 
 - 工作年限：3年
 - Github：http://github.com/ShenFeng312
 - 期望职位：Java程序员
 - 优势分析：本人有良好的Java基础,阅读过Spring源码、Dubbo源码(部分),有开源项目经验。对JVM、java内存线程模型。有良好的自学习惯。自学能力强。对共工作有热情。

---
# 技能清单

以下均为我熟练使用的技能

- 后台框架：Spring/SpringBoot/SpringCloud/.NETMVC(C#)/Node(阿里巴巴开源的egg)/Dubbo
- 数据库相关：MySQL/Redis
- 消息队列：RocketMQ、NSQ
- 版本管理、文档和自动化部署工具：Svn/Git/Jenkins/Docker
- 云和开放平台：有赞云/微信小程序/微信公众号/
---
# 开源项目经验
## Dubbo
修改记录 modify NacosRegistry#getServiceName #6970
社区反馈Dubbo在注册到nacos时,内部接口会注册失败。重现后发现接口中带有$符号，但是Nacos不支持。修改源码的getServiceName方法。实现注册。并修改了注册NacosRegistry中获取ServiceName方法不统一的bug。
## rokcketmq-spring
rokcketmq-spring 中 RocketMQMessageListener 原本只能用于类上。且必须实现固定接口才能作为一个consumer。修改了starter代码。让它能在方法上被添加。且不需要实现接口。实现原理为在BeanPostProcesser的后置处理中中扫描类的方法上是否有携带注解。如果有注解则注册一个consumer 并以这个bean的携带注解的方法作为消费消息的方法(未提交PR 只是自己试验)
## SpringCloud(一个开源的微服务脚手架，并非官方)
修改记录 fix GatewayAdminApplication start warn #144
去除了自动配置GatewayClassPathWarningAutoConfiguration 
修复authentication-server 启动过程中没有获取到授权信息仍然能正常启动问题

---
# 工作经历
## 法本信息（2020年3月 至今）
### 有赞云大客团队
**工作内容：**
负责有赞云大客团队定制化业务开发。技术兜底。和一些常用组件的开发
- 封装异步TraceId组件
- 封装pagehelper插件(由于开源插件依赖于mybatis的starter的autoconfig和有赞不兼容)
- 负责有赞云连接器第一个版本的技术方案和开发。使用Groovy脚本对三方数据加解密。配置落入数据库。使得不需要发布就可以接入新的三方系统。
- 有赞云商品批量导入工具型应用开发。基于Redis+Lua实现阻塞限流。防止异步任务频繁调用有赞云接口是触发限流报错。封装了有赞云工具型应用token托管相关代码。
- 德克士618万人拼团数据修复。由于万人拼团成团瞬间大量创建电子卡券请求打入ISV导致阻塞。造成生产1w多条订单出错。后帮助修复bug。为了防止该情况再次发生。在有赞云没有MQ上云时。使用Redis+Lua 实现一个简单的MQ。大大提高了tps。
- 解决德克士积分解冻拓展点先触发于积分冻结拓展点问题。由于德克士下游需要的流水号与上游不一致。导致必须要等到冻结拓展点执行完。才能执行积分解冻。所以在解冻时先入MQ等查到冻结信息后再去解冻.(经典TCC的Cancel 先与Try)
- 劲牌、新天地、都市丽人、安踏等项目的开发维护。
## 杭州卓健科技（2017年12月 ~ 2019年12月）


### 药事平台（网上购药）（2018年12月 ~ 2019年12月）Java 前后端开发
**项目描述：**


实现了从上游业务获取处方。根据处方在线购药的互联网处方药品购买平台。前后端分离。前端基于Vue开发。后端基于SpringCloud微服务开发。	实现了从上游业务获取处方。根据处方在线购药的互联网处方药品购买平台。

**工作内容：**


负责订单、处方、药品、药店、基础配置等多个服务的开发迭代BUG修复。部分手机端PC端页面开发

- 对于热点数据采用Redis缓存。用户高平率使用的处方列表、订单列表使用List进行缓存基于Redis分页。购药时高频使用的药品-药店关系采用Set存储在Redis中方便快速查询减少数据库压力。
- 使用分布式锁解决缓存一致性问题防止缓存击穿（雪崩）
- 利用TCC机制解决分布式事务问题。保证数据的最终一致性。
- 利用MQ实现异步让耗时长的请求快速返回。限流大量写数据库操作的接口实现削峰。在药品扣减库存时取消订单，或者退款来解决超卖问题
- 利用ThreadLocal对方法返回结果缓存。防止多次远程调用。在请求结束时清除缓存。提高响应速度。
### 一体化预约（2018年4月 ~ 2018年12月）Java 前后端开发
**项目描述：**
为医院提供检验检查预约。床位预约。挂号预约等。在线预约解决方案。方便病人自己预约或者医生在线帮助预约减轻医生护士压力。

**工作内容：**
负责检验检查相关服务开发迭代。基础服务如医院、科室、检查项目、队列、规则等基础数据维护开发。与医院对接患者数据、检验检查开单数据。


- 基于Redis+Lua实现限流和抢号。


### 智能随访（2017年12月 ~ 2018年4月）Java、C# 前后端开发	
**项目描述：**


满足了医院随访、出院患者满意度、出院后患者健康状况收集等需求。此外附加完善的报表统计。前端基于Bootstrap、后台基于.NET和Java。此外附加完善的报表统计。


**工作内容：**


负责把老项目的.NET代码重构成Java。修改统计模板样式。开发各种统计报表。
	
- 由于数据量逐渐增大。原本对于业务表全表扫描的统计难以达到要求RT时间。后采用把业务表和统计表分开的方式。每生成一次业务记录就更改一次统计表。以实现统计报表的快速查询和导出。
---

## 技术文章

- [通过MyBatis分析ImportBeanDefinitionRegistrar与@Import注解以及相关bean定义的注册过程](https://github.com/ShenFeng312/study/blob/master/spring/Mybatis%E6%89%AB%E5%8C%85Import%E6%B3%A8%E8%A7%A3%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86.md)
- [BeanDefinitionRegistryPostProcessor的作用以及原理](https://github.com/ShenFeng312/study/blob/master/spring/BeanDefinitionRegistryPostProcessor%E6%8E%A5%E5%8F%A3.md) 


