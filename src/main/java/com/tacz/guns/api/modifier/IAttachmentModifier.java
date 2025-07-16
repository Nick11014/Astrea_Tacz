package com.tacz.guns.api.modifier;

import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.List;

public interface IAttachmentModifier<T, K> {

    CacheValue<K> initCache(ItemStack gunItem, GunData gunData, ItemStack attachmentItem);

    void eval(List<T> modifiedValues, CacheValue<K> cache);

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
     * @param json String JSON para processamento
     * @return JsonProperty processada
     */
    default JsonProperty<T> readJson(String json) {
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
    default void modify(GunData gunData, JsonProperty<T> property) {
        // TODO: Implementar
    }

    /**
     * Obtém valor da propriedade cacheada
     * Implementação mínima - Object Strategy
     *
     * @param attachmentItem ItemStack do acessório
     * @return Valor cacheado ou null
     */
    default K getCache(ItemStack attachmentItem) {
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
    default void setCache(ItemStack attachmentItem, K value) {
        // AttachmentCacheProperty.setCache(item, getId(), value);
    }

    /**
     * Obtém descrição do modificador para tooltips
     * Implementação mínima - lista vazia
     *
     * @return Lista de componentes de texto para tooltip
     */
    @OnlyIn(Dist.CLIENT)
    default List<Object> getTooltip() {
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
    default boolean isActive(JsonProperty<T> property) {
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
     * @param gunItem       ItemStack da arma
     * @param gunData       Dados da arma
     * @param cacheProperty Propriedades de cache do acessório
     * @return Lista de dados de diagrama
     */
    @OnlyIn(Dist.CLIENT)
    default List<DiagramsData> getPropertyDiagramsData(ItemStack gunItem, GunData gunData, AttachmentCacheProperty cacheProperty) {
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
     * @param defaultPercent  Porcentagem do valor padrão da arma
     * @param modifierPercent Porcentagem do valor modificado
     * @param modifier        Valor modificado, usado para comparação com valor padrão
     * @param titleKey        Chave do arquivo de idioma para nome da propriedade
     * @param positivelyString Texto exibido quando maior que valor padrão
     * @param negativeString  Texto exibido quando menor que valor padrão
     * @param defaultString   Texto exibido quando igual ao valor padrão
     * @param positivelyBetter true se maior que padrão é melhor (verde), false se pior (vermelho)
     */
    @OnlyIn(Dist.CLIENT)
    record DiagramsData(double defaultPercent, double modifierPercent, Number modifier,
                        String titleKey, String positivelyString,
                        String negativeString, String defaultString,
                        boolean positivelyBetter) {
    }
}