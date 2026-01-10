package com.devCraftLab.studentapp.database;
/**
 * DatabaseService - Simulates database connection
 *
 * এটা একটা shared dependency
 * সব repositories এটা use করবে
 */
public class DatabaseService {

    private String connectionUrl;
    private boolean connected;

    public DatabaseService(String connectionUrl) {
        this.connectionUrl = connectionUrl;
        this.connected = false;
        System.out.println("🔌 DatabaseService created with URL: " + connectionUrl);
    }

    /**
     * Connect to database
     */
    public void connect() {
        if (!connected) {
            System.out.println("📡 Connecting to database...");
            // Simulate connection delay
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.connected = true;
            System.out.println("✅ Connected to database!");
        }
    }

    /**
     * Disconnect from database
     */
    public void disconnect() {
        if (connected) {
            System.out.println("🔌 Disconnecting from database...");
            this.connected = false;
            System.out.println("✅ Disconnected!");
        }
    }

    /**
     * Check if connected
     */
    public boolean isConnected() {
        return connected;
    }

    /**
     * Execute query (simulation)
     */
    public void executeQuery(String query) {
        if (!connected) {
            System.out.println("❌ Not connected to database!");
            return;
        }
        System.out.println("🔍 Executing query: " + query);
    }

    public String getConnectionUrl() {
        return connectionUrl;
    }
}