package com.tacz.guns.api.modifier;

import net.minecraft.network.chat.Component;

public class SimpleJsonProperty<T> extends JsonProperty<T> {

    public SimpleJsonProperty(T value) {
        super(value);
    }

    @Override
    public void initComponents() {
        // Implementação básica, pode ser expandida conforme necessário
        this.components.add(Component.literal(String.valueOf(getValue())));
    }
}
