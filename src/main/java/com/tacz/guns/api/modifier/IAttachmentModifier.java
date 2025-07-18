package com.tacz.guns.api.modifier;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tacz.guns.api.item.IAttachment;
import com.tacz.guns.resource.modifier.AttachmentCacheProperty;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.util.Collections;
import java.util.List;

public interface IAttachmentModifier<T, K> {

    Gson GSON = new GsonBuilder().create();

    CacheValue<K> initCache(ItemStack gunItem, GunData gunData, ItemStack attachmentItem);

    void eval(List<T> modifiedValues, CacheValue<K> cache);

    /**
     * ConfiguraÃ§Ã£o bÃ¡sica de modificador, usado como ID no sistema JSON
     *
     * @return ID do modificador para identificaÃ§Ã£o no JSON
     */
    String getId();

    /**
     * Campo opcional para compatibilidade com versÃµes antigas
     * ImplementaÃ§Ã£o mÃ­nima - retorna string vazia
     *
     * @return Nome do campo alternativo para JSON antigo
     */
    default String getOptionalFields() {
        return "";
    }

    /**
     * LÃª propriedade do JSON
     * ImplementaÃ§Ã£o mÃ­nima - retorna null
     *
     * @param json String JSON para processamento
     * @return JsonProperty processada
     */
    JsonProperty<T> readJson(String json);

    Class<T> getPropertyClass();

    /**
     * Aplica modificaÃ§Ã£o nas propriedades da arma
     * ImplementaÃ§Ã£o mÃ­nima - apenas estrutura
     *
     * @param gunData Dados da arma para modificar
     * @param property Propriedade do modificador
     */
    default void modify(GunData gunData, JsonProperty<T> property) {
        // TODO: Implementar
    }

    /**
     * ObtÃ©m valor da propriedade cacheada
     * ImplementaÃ§Ã£o mÃ­nima - Object Strategy
     *
     * @param attachmentItem ItemStack do acessÃ³rio
     * @return Valor cacheado ou null
     */
    default K getCache(ItemStack attachmentItem) {
        // return AttachmentCacheProperty.getCache(item, getId());
        return null;
    }

    /**
     * Define valor no cache
     * ImplementaÃ§Ã£o mÃ­nima - Object Strategy
     *
     * @param attachmentItem ItemStack do acessÃ³rio
     * @param value Valor para cachear
     */
    default void setCache(ItemStack attachmentItem, K value) {
        // AttachmentCacheProperty.setCache(item, getId(), value);
    }

    /**
     * ObtÃ©m descriÃ§Ã£o do modificador para tooltips
     * ImplementaÃ§Ã£o mÃ­nima - lista vazia
     *
     * @return Lista de componentes de texto para tooltip
     */
    @OnlyIn(Dist.CLIENT)
    default List<Object> getTooltip() {
        // return List.of(Component.translatable("tooltip.tacz.modifier." + getId()));
        return Collections.emptyList();
    }

    /**
     * Verifica se o modificador estÃ¡ ativo
     * ImplementaÃ§Ã£o mÃ­nima - sempre true
     *
     * @param property Propriedade para verificar
     * @return true se ativo
     */
    default boolean isActive(JsonProperty<T> property) {
        return property != null;
    }

    /**
     * ObtÃ©m estatÃ­sticas do modificador
     */
    default String getStats() {
        return String.format("IAttachmentModifier{id=%s}", getId());
    }

    /**
     * ObtÃ©m dados de diagrama para interface de refit
     * ImplementaÃ§Ã£o mÃ­nima - lista vazia
     *
     * @param gunItem       ItemStack da arma
     * @param gunData       Dados da arma
     * @param cacheProperty Propriedades de cache do acessÃ³rio
     * @return Lista de dados de diagrama
     */
    @OnlyIn(Dist.CLIENT)
    default List<DiagramsData> getPropertyDiagramsData(ItemStack gunItem, GunData gunData, AttachmentCacheProperty cacheProperty) {
        return Collections.emptyList();
    }

    /**
     * ObtÃ©m o tamanho dos dados de diagrama para cÃ¡lculo de offset de botÃµes
     *
     * @return NÃºmero de diagramas
     */
    @OnlyIn(Dist.CLIENT)
    default int getDiagramsDataSize() {
        return 0;
    }

    /**
     * Dados de diagrama para interface de propriedades
     *
     * @param defaultPercent  Porcentagem do valor padrÃ£o da arma
     * @param modifierPercent Porcentagem do valor modificado
     * @param modifier        Valor modificado, usado para comparaÃ§Ã£o com valor padrÃ£o
     * @param titleKey        Chave do arquivo de idioma para nome da propriedade
     * @param positivelyString Texto exibido quando maior que valor padrÃ£o
     * @param negativeString  Texto exibido quando menor que valor padrÃ£o
     * @param defaultString   Texto exibido quando igual ao valor padrÃ£o
     * @param positivelyBetter true se maior que padrÃ£o Ã© melhor (verde), false se pior (vermelho)
     */
    @OnlyIn(Dist.CLIENT)
    record DiagramsData(double defaultPercent, double modifierPercent, Number modifier,
                        String titleKey, String positivelyString,
                        String negativeString, String defaultString,
                        boolean positivelyBetter) {
    }
}