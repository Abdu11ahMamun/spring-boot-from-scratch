package com.devCraftLab.studentapp.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * BeanFactory - Advanced IoC Container
 *
 * এটা automatically:
 * - Bean definitions পড়ে
 * - Dependencies resolve করে
 * - Objects তৈরি করে
 * - Constructor injection করে
 */
public class BeanFactory {

    // Bean definitions storage
    private Map<String, BeanDefinition> beanDefinitions;

    // Created beans storage (singleton)
    public Map<String, Object> singletonBeans;

    public BeanFactory() {
        this.beanDefinitions = new HashMap<>();
        this.singletonBeans = new HashMap<>();
        System.out.println("🏭 BeanFactory initialized!");
    }

    /**
     * Register a bean definition (metadata)
     */
    public void registerBeanDefinition(BeanDefinition beanDefinition) {
        String beanName = beanDefinition.getBeanName();

        if (beanDefinitions.containsKey(beanName)) {
            System.out.println("⚠️  Bean definition '" + beanName + "' already exists!");
            return;
        }

        beanDefinitions.put(beanName, beanDefinition);
        System.out.println("📝 Registered bean definition: " + beanName +
                " [" + beanDefinition.getBeanClass().getSimpleName() + "]");
    }

    /**
     * Get a bean - creates if not exists (lazy initialization)
     */
    public Object getBean(String beanName) {
        // Check if already created (singleton)
        if (singletonBeans.containsKey(beanName)) {
            System.out.println("♻️  Returning existing singleton: " + beanName);
            return singletonBeans.get(beanName);
        }

        // Get bean definition
        BeanDefinition beanDefinition = beanDefinitions.get(beanName);

        if (beanDefinition == null) {
            System.out.println("❌ No bean definition found for: " + beanName);
            return null;
        }

        // Create the bean
        System.out.println("🔨 Creating bean: " + beanName);
        Object bean = createBean(beanDefinition);

        if (bean != null) {
            // Store as singleton
            singletonBeans.put(beanName, bean);
            System.out.println("✅ Bean created and cached: " + beanName);
        }

        return bean;
    }

    /**
     * Create a bean instance with dependency injection
     */
    private Object createBean(BeanDefinition beanDefinition) {
        try {
            Class<?> beanClass = beanDefinition.getBeanClass();

            // No dependencies - simple creation
            if (!beanDefinition.hasDependencies()) {
                System.out.println("   └─ No dependencies, using default constructor");
                return beanClass.getDeclaredConstructor().newInstance();
            }

            // Has dependencies - resolve them first!
            System.out.println("   └─ Resolving " + beanDefinition.getConstructorParamBeanNames().length + " dependencies...");

            Class<?>[] paramTypes = beanDefinition.getConstructorParamTypes();
            String[] paramBeanNames = beanDefinition.getConstructorParamBeanNames();

            // Resolve dependencies recursively
            Object[] dependencies = new Object[paramBeanNames.length];
            for (int i = 0; i < paramBeanNames.length; i++) {
                System.out.println("      ├─ Resolving dependency: " + paramBeanNames[i]);
                dependencies[i] = getBean(paramBeanNames[i]);  // Recursive call!

                if (dependencies[i] == null) {
                    System.out.println("      └─ ❌ Failed to resolve: " + paramBeanNames[i]);
                    return null;
                }
            }

            // Get constructor and create instance with dependencies
            Constructor<?> constructor = beanClass.getConstructor(paramTypes);
            return constructor.newInstance(dependencies);

        } catch (Exception e) {
            System.out.println("❌ Error creating bean: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get bean with type safety
     */
    @SuppressWarnings("unchecked")
    public <T> T getBean(String beanName, Class<T> requiredType) {
        Object bean = getBean(beanName);

        if (bean == null) {
            return null;
        }

        if (!requiredType.isInstance(bean)) {
            System.out.println("❌ Bean '" + beanName + "' is not of type " + requiredType.getSimpleName());
            return null;
        }

        return (T) bean;
    }

    /**
     * Preinstantiate all singletons (eager initialization)
     */
    public void preInstantiateSingletons() {
        System.out.println("\n🚀 Pre-instantiating all singleton beans...");
        System.out.println("=".repeat(60));

        for (String beanName : beanDefinitions.keySet()) {
            if (!singletonBeans.containsKey(beanName)) {
                getBean(beanName);
            }
        }

        System.out.println("=".repeat(60));
        System.out.println("✅ All beans pre-instantiated!");
    }

    /**
     * List all bean definitions
     */
    public void listBeanDefinitions() {
        if (beanDefinitions.isEmpty()) {
            System.out.println("📭 No bean definitions registered!");
            return;
        }

        System.out.println("\n📋 Registered Bean Definitions:");
        System.out.println("═".repeat(60));
        beanDefinitions.forEach((name, def) -> {
            System.out.println("• " + name + " → " + def.getBeanClass().getSimpleName());
            if (def.hasDependencies()) {
                System.out.println("   Dependencies: " + String.join(", ", def.getConstructorParamBeanNames()));
            }
        });
        System.out.println("═".repeat(60));
    }

    /**
     * List all created singletons
     */
    public void listSingletons() {
        if (singletonBeans.isEmpty()) {
            System.out.println("📭 No singleton beans created yet!");
            return;
        }

        System.out.println("\n🫘 Created Singleton Beans:");
        System.out.println("═".repeat(60));
        singletonBeans.forEach((name, bean) -> {
            System.out.println("• " + name + " → " + bean.getClass().getSimpleName() + " @" +
                    Integer.toHexString(bean.hashCode()));
        });
        System.out.println("═".repeat(60));
    }

    /**
     * Get total bean definitions count
     */
    public int getBeanDefinitionCount() {
        return beanDefinitions.size();
    }

    /**
     * Get created beans count
     */
    public int getSingletonCount() {
        return singletonBeans.size();
    }
}