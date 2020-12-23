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
 - 期望城市：杭州/上海
 - 期望入职时间：2020年3月1日
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
## 
# 工作经历

## 杭州卓健科技（2017年12月 ~ 2019年12月）


### 药事平台（网上购药）（2018年12月 ~ 2019年12月）Java 前后端开发	### 药事平台（网上购药）（2018年12月 ~ 2019年12月）Java 前后端开发
**项目描述：**


实现了从上游业务获取处方。根据处方在线购药的互联网处方药品购买平台。前后端分离。前端基于Vue开发。后端基于SpringCloud微服务开发包括了注册中心，配置中心，统一网关，负载均衡，熔断器，Turbine、Zipkin、SpringBootAdmin服务监控等。	实现了从上游业务获取处方。根据处方在线购药的互联网处方药品购买平台。前后端分离。前端基于Vue开发。后端基于SpringCloud微服务开发包括了注册中心，配置中心，统一网关，负载均衡，熔断器，Turbine、Zipkin、SpringBootAdmin服务监控等。


**工作内容：**


负责订单、处方、药品、药店、基础配置等多个服务的开发迭代BUG修复。部分手机端PC端页面开发	负责订单、处方、药品、药店、基础配置等多个服务的开发迭代BUG修复。部分手机端PC端页面开发


**技术特点：**


- 利用JWT解决分布式的单点登录和权限认证。	- 利用JWT解决分布式的单点登录和权限认证。
- 对于热点数据采用Redis缓存。用户高平率使用的处方列表、订单列表使用List进行缓存基于Redis分页。购药时高频使用的药品-药店关系采用Set存储在Redis中方便快速查询减少数据库压力。	- 对于热点数据采用Redis缓存。用户高平率使用的处方列表、订单列表使用List进行缓存基于Redis分页。购药时高频使用的药品-药店关系采用Set存储在Redis中方便快速查询减少数据库压力。
- 利用TCC机制解决分布式事务问题。保证数据的最终一致性。	- 利用TCC机制解决分布式事务问题。保证数据的最终一致性。
- 利用MQ实现异步让耗时长的请求快速返回。限流大量写数据库操作的接口实现削峰。基于他广播的特性实现解耦。	- 利用MQ实现异步让耗时长的请求快速返回。限流大量写数据库操作的接口实现削峰。基于他广播的特性实现解耦。
### 一体化预约（2018年4月 ~ 2018年12月）Java 前后端开发	### 一体化预约（2018年4月 ~ 2018年12月）Java 前后端开发
**项目描述：**


为医院提供检验检查预约。床位预约。挂号预约等。在线预约解决方案。方便病人自己预约或者医生在线帮助预约减轻医生护士压力。前端基于Vue开发开发后端基于SpringCloud开发。	为医院提供检验检查预约。床位预约。挂号预约等。在线预约解决方案。方便病人自己预约或者医生在线帮助预约减轻医生护士压力。前端基于Vue开发开发后端基于SpringCloud开发。


**工作内容：**


负责检验检查相关服务开发迭代。基础服务如医院、科室、检查项目、队列、规则等基础数据维护开发。与医院对接患者数据、检验检查开单数据。	负责检验检查相关服务开发迭代。基础服务如医院、科室、检查项目、队列、规则等基础数据维护开发。与医院对接患者数据、检验检查开单数据。


**技术特点：**


- 利用JWT解决分布式的单点登录和权限认证。	- 利用JWT解决分布式的单点登录和权限认证。
- 对基础数据做Redis缓存。减少数据库访问量。	- 对基础数据做Redis缓存。减少数据库访问量。





### 智能随访（2017年12月 ~ 2018年4月）Java、C# 前后端开发	### 智能随访（2017年12月 ~ 2018年4月）Java、C# 前后端开发
**项目描述：**


满足了医院随访、出院患者满意度、出院后患者健康状况收集等需求。此外附加完善的报表统计。前端基于Bootstrap、后台基于.NET和Java	满足了医院随访、出院患者满意度、出院后患者健康状况收集等需求。此外附加完善的报表统计。前端基于Bootstrap、后台基于.NET和Java


**工作内容：**


负责把老项目的.NET代码重构成Java。修改统计模板样式。开发各种统计报表。	负责把老项目的.NET代码重构成Java。修改统计模板样式。开发各种统计报表。
	
**技术特点：**
- 利用JWT解决分布式的单点登录和权限认证。	- 利用JWT解决分布式的单点登录和权限认证。
- 把拨入播出统计和随访模板的统计问题从数据库查询优化到Redis。利用String 统计拨入播出。利用Hash统计模板问题。	- 把拨入播出统计和随访模板的统计问题从数据库查询优化到Redis。利用String 统计拨入播出。利用Hash统计模板问题。
---

## 技术文章

- [通过MyBatis分析ImportBeanDefinitionRegistrar与@Import注解以及相关bean定义的注册过程](https://github.com/ShenFeng312/study/blob/master/spring/Mybatis%E6%89%AB%E5%8C%85Import%E6%B3%A8%E8%A7%A3%E5%AE%9E%E7%8E%B0%E5%8E%9F%E7%90%86.md)
- [BeanDefinitionRegistryPostProcessor的作用以及原理](https://github.com/ShenFeng312/study/blob/master/spring/BeanDefinitionRegistryPostProcessor%E6%8E%A5%E5%8F%A3.md) 


