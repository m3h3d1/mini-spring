# <img src="assets/spring-framework.png" width="80" height="80"> mini-spring

## About

**mini-spring** is a simplified version of the Spring framework. It helps you quickly understand Spring's source code and core principles. The project extracts Spring’s essential logic while keeping the code minimal, preserving key features like IoC, AOP, resource loading, event listeners, type conversion, container extension points, bean lifecycle and scope, application context, and more.

If you find this project helpful, please give it a **STAR, thanks!!!**

## Features
### Basics: IoC
* [IoC](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#basics-ioc)
    * [Simple Bean Container](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#simple-bean-container)
    * [BeanDefinition and BeanDefinitionRegistry](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#beandefinition-and-beandefinitionregistry)
    * [Bean Instantiation Strategy](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#bean-instantiation-strategy)
    * [Populating Bean Properties](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#populating-bean-properties)
    * [Injecting a Bean into Another Bean](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#injecting-a-bean-into-another-bean)
    * [Resources and Resource Loaders](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#resources-and-resource-loaders)
    * [Defining Beans in XML](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#defining-beans-in-xml)
    * [Container Extension Mechanisms (BeanFactoryPostProcessor & BeanPostProcessor)](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#bean-factory-post-processor-and-bean-post-processor)
    * [ApplicationContext](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#applicationcontext)
    * [Bean Initialization and Destruction Methods](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#bean-initialization-and-destruction-methods)
    * [Aware Interface](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#aware-interface)
    * [Bean Scope - Adding Prototype Support](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#bean-scope-adding-prototype-support)
    * [FactoryBean](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#FactoryBean)
    * [Container Events and Event Listeners](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#container-events-and-event-listeners)

### Basics: AOP
* [AOP](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#basics-aop)
    * [Pointcut Expressions](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#pointcut-expression)
    * [JDK-Based Dynamic Proxy](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#jdk-based-dynamic-proxy)
    * [CGLIB-Based Dynamic Proxy](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#cglib-based-dynamic-proxy)
    * [AOP Proxy Factory](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#aop-proxy-factory)
    * [Common Advice Types: BeforeAdvice, AfterAdvice, AfterReturningAdvice, ThrowsAdvice](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#common-advice-beforeadvice-afteradvice-afterreturningadvice-throwsadvice)
    * [PointcutAdvisor: Combination of Pointcut and Advice](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#pointcutadvisor-pointcut-and-advice-combination)
    * [Dynamic Proxy in Bean Lifecycle](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#dynamic-proxy-in-bean-lifecycle)

### Extension Features
* [PropertyPlaceholderConfigurer](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#PropertyPlaceholderConfigurer)
* [Package Scan](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#package-scan)
* [@Value Annotation](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#Value-annotation)
* [@Autowired Annotation](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#valAutowired-annotationue注解)
* [Dependency Injection with @Autowired](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#Autowired-annotation)
* [Type Conversion (Part 1)](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#type-conversion-part-1)
* [Type Conversion (Part 2)](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#type-conversion-part-2)

### Advanced Topics
* [Solving Circular Dependency Problem (Part 1): Without Proxy Objects](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#solving-circular-dependency-problem-1-without-proxy-objects)
* [Solving Circular Dependency Problem (Part 2): With Proxy Beans](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#solving-circular-dependency-problem-2-with-proxy-beans)

#### Others
* [Proxy bean properties not set (discovered and fixed by kerwin89)](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#bug-fix-no-property-set-for-proxy-bean-discovered-and-fixed-by-kerwin89)
* [Support for lazy loading and multiple aspects (by zqczgl)](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md#support-for-lazy-init-and-multi-advice-by-zqczgl)

## Usage
Refer to [changelog.md](https://github.com/m3h3d1/mini-spring/blob/main/changelog.md)

## Contributions
Pull Requests are welcome.
