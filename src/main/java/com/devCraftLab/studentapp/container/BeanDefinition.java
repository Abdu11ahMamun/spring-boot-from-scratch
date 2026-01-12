package com.devCraftLab.studentapp.container;

/**
 * BeanDefinition - Metadata about a bean
 *
 * এটা একটা bean এর information রাখে:
 * - কোন class এর object?
 * - কি কি dependencies লাগবে?
 */
public class BeanDefinition {

    private String beanName;
    private Class<?> beanClass;
    private Class<?>[] constructorParamTypes;
    private String[] constructorParamBeanNames;

    public BeanDefinition(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    public BeanDefinition(String beanName, Class<?> beanClass,
                          Class<?>[] constructorParamTypes,
                          String[] constructorParamBeanNames) {
        this.beanName = beanName;
        this.beanClass = beanClass;
        this.constructorParamTypes = constructorParamTypes;
        this.constructorParamBeanNames = constructorParamBeanNames;
    }

    // Getters
    public String getBeanName() {
        return beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public Class<?>[] getConstructorParamTypes() {
        return constructorParamTypes;
    }

    public String[] getConstructorParamBeanNames() {
        return constructorParamBeanNames;
    }

    public boolean hasDependencies() {
        return constructorParamTypes != null && constructorParamTypes.length > 0;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanName='" + beanName + '\'' +
                ", beanClass=" + beanClass.getSimpleName() +
                ", dependencies=" + (hasDependencies() ? constructorParamBeanNames.length : 0) +
                '}';
    }
}