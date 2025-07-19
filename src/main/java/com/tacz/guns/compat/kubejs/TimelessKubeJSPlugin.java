package com.tacz.guns.compat.kubejs;

import com.tacz.guns.compat.kubejs.events.TimelessClientEvents;
import com.tacz.guns.compat.kubejs.events.TimelessCommonEvents;
import com.tacz.guns.compat.kubejs.events.TimelessServerEvents;
import net.neoforged.fml.ModList;

/**
 * Plugin principal do TacZ para KubeJS
 * MIGRAÇÃO 1.21.1: Versão simplificada e funcional
 * IMPLEMENTAÇÃO: Funciona sem dependências diretas do KubeJS
 */
public class TimelessKubeJSPlugin {
    public static final String KUBEJS_MODID = "kubejs";

    /**
     * Inicialização do plugin quando KubeJS está disponível
     */
    public static void init() {
        if (!isInstalled()) {
            return;
        }
        
        try {
            // Inicializar sistemas de eventos
            TimelessCommonEvents.INSTANCE.init();
            TimelessServerEvents.INSTANCE.init();
            TimelessClientEvents.INSTANCE.init();
            
            System.out.println("TacZ: KubeJS plugin initialized successfully");
            System.out.println("TacZ: Event systems active - Common, Server, Client");
            System.out.println("TacZ: TimelessItem utilities available in scripts");
            
        } catch (Exception e) {
            System.err.println("TacZ: Failed to initialize KubeJS plugin: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Verifica se KubeJS está instalado
     */
    public static boolean isInstalled() {
        return ModList.get().isLoaded(KUBEJS_MODID);
    }
    
    /**
     * Inicialização estática para compatibilidade
     */
    public static void staticInit() {
        if (isInstalled()) {
            System.out.println("TacZ: KubeJS detected - initializing integration");
            init();
        } else {
            System.out.println("TacZ: KubeJS not detected - using basic compatibility");
        }
    }
    
    /**
     * Obtém informações sobre o status da integração
     */
    public static String getIntegrationStatus() {
        if (!isInstalled()) {
            return "KubeJS not installed";
        }
        
        return String.format("KubeJS integration active - Events: %s, %s, %s", 
            TimelessCommonEvents.INSTANCE != null ? "Common" : "None",
            TimelessServerEvents.INSTANCE != null ? "Server" : "None", 
            TimelessClientEvents.INSTANCE != null ? "Client" : "None"
        );
    }
}
