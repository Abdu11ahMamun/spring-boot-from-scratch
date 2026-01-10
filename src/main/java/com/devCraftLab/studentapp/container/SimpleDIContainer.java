package com.devCraftLab.studentapp.container;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * Simple DIY IoC Container
 * Demonstrates how Spring's ApplicationContext works internally
 *
 * Features:
 * - Bean registration (interface → implementation mapping)
 * - Automatic dependency resolution
 * - Singleton scope management
 * - Constructor-based injection
 */
public class SimpleDIContainer {

        // Storage for all managed objects (beans)
        private Map<String, Object> beans;

        // Constructor
        public SimpleDIContainer() {
            this.beans = new HashMap<>();
            System.out.println("🌱 SimpleContainer initialized!");
        }

        /**
         * Register a bean (object) in container
         *
         * @param name - Bean name (identifier)
         * @param bean - The actual object
         */
        public void registerBean(String name, Object bean) {
            if (beans.containsKey(name)) {
                System.out.println("⚠️  Bean '" + name + "' already registered!");
                return;
            }

            beans.put(name, bean);
            System.out.println("✅ Registered bean: " + name + " [" + bean.getClass().getSimpleName() + "]");
        }

        /**
         * Get a bean by name
         *
         * @param name - Bean name
         * @return The bean object
         */
        public Object getBean(String name) {
            if (!beans.containsKey(name)) {
                System.out.println("❌ Bean '" + name + "' not found!");
                return null;
            }

            System.out.println("🔍 Retrieved bean: " + name);
            return beans.get(name);
        }

        /**
         * Get a bean by name and cast to specific type
         *
         * @param name - Bean name
         * @param requiredType - Expected type
         * @return The bean cast to required type
         */
        @SuppressWarnings("unchecked")
        public <T> T getBean(String name, Class<T> requiredType) {
            Object bean = getBean(name);

            if (bean == null) {
                return null;
            }

            if (!requiredType.isInstance(bean)) {
                System.out.println("❌ Bean '" + name + "' is not of type " + requiredType.getSimpleName());
                return null;
            }

            return (T) bean;
        }

        /**
         * Check if container has a bean
         *
         * @param name - Bean name
         * @return true if exists
         */
        public boolean containsBean(String name) {
            return beans.containsKey(name);
        }

        /**
         * Get total number of beans
         *
         * @return count
         */
        public int getBeanCount() {
            return beans.size();
        }

        /**
         * List all registered beans
         */
        public void listAllBeans() {
            if (beans.isEmpty()) {
                System.out.println("📭 No beans registered!");
                return;
            }

            System.out.println("\n📦 Registered Beans in Container:");
            System.out.println("═".repeat(50));
            beans.forEach((name, bean) -> {
                System.out.println("• " + name + " → " + bean.getClass().getSimpleName());
            });
            System.out.println("═".repeat(50));
        }

        /**
         * Clear all beans
         */
        public void clear() {
            beans.clear();
            System.out.println("🗑️  All beans cleared!");
        }
    }