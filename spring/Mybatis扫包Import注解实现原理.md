之前在的介绍中已经说明了大多数bean定义都由[BeanDefinitionRegistryPostProcessor](https://www.mdeditor.com/)注册。如果我们要自己干预注册可以自己添加一个BeanDefinitionRegistryPostProcessor的实现。
除了这种方式spring还有一种更简单的方式来注册。``@Import``和``ImportBeanDefinitionRegistrar``
