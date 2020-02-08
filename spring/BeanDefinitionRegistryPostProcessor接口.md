# BeanDefinitionRegistryPostProcessor的作用以及原理
``BeanDefinitionRegistryPostProcessor``接口继承了 ``BeanFactoryPostProcessor``接口
spring的bean注册主要由该接口的postProcessBeanDefinitionRegistry方法实现。比如默认的``ConfigurationClassPostProcessor``

在spring刷新容器的时候。spring容器的refresh会调用``invokeBeanFactoryPostProcessors(beanFactory)``;
其内部就是把spring容器默认和注册的``BeanDefinitionRegistryPostProcessor``的``postProcessBeanFactory``和``postProcessBeanDefinitionRegistry``全部执行一遍由内部来实现bean的注册。
```java
boolean reiterate = true;
while (reiterate) {
    reiterate = false;
    //查出所有实现了BeanDefinitionRegistryPostProcessor接口的bean名称
    postProcessorNames = beanFactory.getBeanNamesForType(BeanDefinitionRegistryPostProcessor.class, true, false);
    for (String ppName : postProcessorNames) {
        //前面的逻辑中，已经对实现了PriorityOrdered和Ordered的bean都处理过了，因此通过processedBeans过滤，processedBeans中没有的才会在此处理
        if (!processedBeans.contains(ppName)) {
            //根据名称和类型获取bean
            BeanDefinitionRegistryPostProcessor pp = beanFactory.getBean(ppName, BeanDefinitionRegistryPostProcessor.class);
            //把已经调用过postProcessBeanDefinitionRegistry方法的bean全部放在registryPostProcessors中
            registryPostProcessors.add(pp);
            //把已经调用过postProcessBeanDefinitionRegistry方法的bean的名称全部放在processedBeans中
            processedBeans.add(ppName);
            //执行此bean的postProcessBeanDefinitionRegistry方法
            pp.postProcessBeanDefinitionRegistry(registry);
            //改变退出while的条件
            reiterate = true;
        }
    }
}
/registryPostProcessors中保存了所有执行过postProcessBeanDefinitionRegistry方法的bean，
//现在再来执行这些bean的postProcessBeanFactory方法
invokeBeanFactoryPostProcessors(registryPostProcessors, beanFactory);
//regularPostProcessors中保存的是所有入参中带来的BeanFactoryPostProcessor实现类，并且这里面已经剔除了BeanDefinitionRegistryPostProcessor的实现类，现在要让这些bean执行postProcessBeanFactory方法
```
  
创建一个的简单的demo
```java
    public static class MyProcessor implements BeanDefinitionRegistryPostProcessor {

        @Override
        public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
            //beanFactory的后置处理
            System.out.println("mytest");
        }

        @Override
        public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
            //通过 registry注册bean
            System.out.println("mytest");
        }
    }
``` 
只要把这个MyProcessor 以@Bean 或者其他方式注入到容器中，那么它就能起作用
