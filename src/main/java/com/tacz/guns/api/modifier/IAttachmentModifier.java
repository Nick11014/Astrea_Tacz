package com.tacz.guns.api.modifier;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.List;

/**
 * IAttachmentModifier - Implementação Mínima Estratégica
 * 
 * Interface para modificadores de propriedades de acessórios
 * Implementação mínima para desbloquear sistema completo de modificadores
 * 
 * TODO: Expandir funcionalidade quando sistemas dependentes estiverem habilitados:
 * - AttachmentCacheProperty
 * - GunData completo
 * - ItemStack processing
 * - Sistema de eventos
 */
public interface IAttachmentModifier<T, K> {
    /**
     * Configuração básica de modificador, usado como ID no sistema JSON
     * 
     * @return ID do modificador para identificação no JSON
     */
    String getId();

    /**
     * Campo opcional para compatibilidade com versões antigas
     * Implementação mínima - retorna string vazia
     * 
     * @return Nome do campo alternativo para JSON antigo
     */
    default String getOptionalFields() {
        return "";
    }

    /**
     * Lê propriedade do JSON
     * Implementação mínima - usando Object Strategy
     * 
     * @param json String JSON para processing
     * @return JsonProperty processada
     */
    default Object readJson(String json) {
        // TODO: Implementar quando JsonProperty estiver completo
        // return gson.fromJson(json, getPropertyClass());
        return null;
    }

    /**
     * Aplica modificação nas propriedades da arma
     * Implementação mínima - apenas estrutura
     * 
     * @param gunData Dados da arma para modificar
     * @param property Propriedade do modificador
     */
    default void modify(Object gunData, Object property) {
        // TODO: Implementar quando GunData estiver completo
        // GunData data = (GunData) gunData;
        // JsonProperty<T> prop = (JsonProperty<T>) property;
        // applyModification(data, prop.getValue());
    }

    /**
     * Obtém valor da propriedade cacheada
     * Implementação mínima - Object Strategy
     * 
     * @param attachmentItem ItemStack do acessório
     * @return Valor cacheado ou null
     */
    default Object getCache(Object attachmentItem) {
        // TODO: Implementar quando AttachmentCacheProperty estiver disponível
        // ItemStack item = (ItemStack) attachmentItem;
        // return AttachmentCacheProperty.getCache(item, getId());
        return null;
    }

    /**
     * Define valor no cache
     * Implementação mínima - Object Strategy
     * 
     * @param attachmentItem ItemStack do acessório  
     * @param value Valor para cachear
     */
    default void setCache(Object attachmentItem, Object value) {
        // TODO: Implementar quando AttachmentCacheProperty estiver disponível
        // ItemStack item = (ItemStack) attachmentItem;
        // AttachmentCacheProperty.setCache(item, getId(), value);
    }

    /**
     * Obtém descrição do modificador para tooltips
     * Implementação mínima - lista vazia
     * 
     * @return Lista de componentes de texto para tooltip
     */
    default List<Object> getTooltip() {
        // TODO: Implementar quando Component system estiver disponível
        // return List.of(Component.translatable("tooltip.tacz.modifier." + getId()));
        return Collections.emptyList();
    }

    /**
     * Verifica se o modificador está ativo
     * Implementação mínima - sempre true
     * 
     * @param property Propriedade para verificar
     * @return true se ativo
     */
    default boolean isActive(Object property) {
        // TODO: Implementar validação quando tipos estiverem disponíveis
        return property != null;
    }

    /**
     * Obtém estatísticas do modificador
     */
    default String getStats() {
        return String.format("IAttachmentModifier{id=%s}", getId());
    }

    /**
     * Obtém dados de diagrama para interface de refit
     * Implementação mínima - lista vazia
     * 
     * @param gunItem ItemStack da arma
     * @param gunData Dados da arma
     * @param cacheProperty Propriedades de cache do acessório
     * @return Lista de dados de diagrama
     */
    @OnlyIn(Dist.CLIENT)
    default List<DiagramsData> getPropertyDiagramsData(Object gunItem, Object gunData, Object cacheProperty) {
        return Collections.emptyList();
    }

    /**
     * Obtém o tamanho dos dados de diagrama para cálculo de offset de botões
     * 
     * @return Número de diagramas
     */
    @OnlyIn(Dist.CLIENT)
    default int getDiagramsDataSize() {
        return 0;
    }

    /**
     * Dados de diagrama para interface de propriedades
     * 
     * @param defaultPercent   Porcentagem do valor padrão da arma
     * @param modifierPercent  Porcentagem do valor modificado
     * @param modifier         Valor modificado, usado para comparação com valor padrão
     * @param titleKey         Chave do arquivo de idioma para nome da propriedade
     * @param positivelyString Texto exibido quando maior que valor padrão
     * @param negativeString   Texto exibido quando menor que valor padrão
     * @param defaultString    Texto exibido quando igual ao valor padrão
     * @param positivelyBetter true se maior que padrão é melhor (verde), false se pior (vermelho)
     */
    @OnlyIn(Dist.CLIENT)
    record DiagramsData(double defaultPercent, double modifierPercent, Number modifier,
                        String titleKey, String positivelyString,
                        String negativeString, String defaultString,
                        boolean positivelyBetter) {
    }
}
