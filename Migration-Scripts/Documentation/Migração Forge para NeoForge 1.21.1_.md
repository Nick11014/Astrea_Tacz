

# **Guia de Migração Abrangente: De Forge 1.20.1 para NeoForge 1.21.1**

## **Introdução: Além de um Fork \- A Mudança Filosófica no NeoForge**

A transição de um mod de Forge 1.20.1 para NeoForge 1.21.1 transcende uma simples atualização de versão. Representa a adoção de uma nova filosofia de design de API, impulsionada por uma comunidade de desenvolvedores que busca modernizar e estabilizar o ecossistema de modding do Minecraft. O NeoForge, sendo uma bifurcação (fork) do Forge liderada pela vasta maioria de sua equipe de desenvolvimento original, embarcou em um caminho de refatoração significativa, afastando-se de sistemas implícitos e propensos a erros em favor de APIs explícitas, seguras em tipo (type-safe) e orientadas a dados.1

Para o desenvolvedor, isso significa que a migração não é apenas uma questão de atualizar números de versão e corrigir nomes de métodos. É um processo de reaprendizagem de paradigmas fundamentais que foram a base do desenvolvimento em Forge por anos. A solicitação para corrigir um conjunto de importações desatualizadas—especificamente aquelas relacionadas a *Capabilities* e *GUI Overlays*—serve como um ponto de partida ideal para explorar as mudanças arquitetônicas mais profundas.

Este guia detalhará as principais refatorações que um desenvolvedor encontrará, focando em:

1. **A Revisão do Sistema de *Capabilities***: Uma mudança completa do anexo dinâmico baseado em eventos para um modelo de provedor estático e registrado.  
2. **O Sistema de Camadas de GUI (GUI Layer System)**: A substituição do antigo sistema de sobreposição (overlay) por uma API de renderização em camadas mais flexível e poderosa.  
3. **A Modernização do Sistema de Eventos**: Melhorias na forma como os eventos são inscritos e manipulados para aumentar a clareza e a segurança.  
4. **A Ascensão dos Componentes de Dados (Data Components)**: A substituição fundamental do armazenamento de dados NBT em ItemStack por um sistema estruturado e seguro em tipo.

Compreender a lógica por trás dessas mudanças é crucial. O objetivo do NeoForge é fornecer uma base mais robusta e previsível, reduzindo a ambiguidade e melhorando o desempenho e a estabilidade para todos os mods no ecossistema.6 Este guia visa não apenas fornecer as soluções de código necessárias, mas também elucidar o "porquê" por trás de cada mudança, capacitando os desenvolvedores a abraçar totalmente a nova era do modding com NeoForge.

## **Seção 1: Migração Fundacional do Projeto: Estabelecendo a Nova Linha de Base**

Antes de abordar as complexidades do código da API, é imperativo atualizar a estrutura fundamental do projeto. Essas etapas iniciais, que envolvem o ambiente de construção e os arquivos de metadados, são pré-requisitos não negociáveis para que qualquer código seja compilado com sucesso no ecossistema NeoForge 1.21.1.

### **1.1. Atualizações de Buildscript (build.gradle) e Dependências**

O primeiro passo em qualquer migração é reconfigurar o ambiente de construção. NeoForge introduziu mudanças significativas na forma como os projetos são configurados e quais dependências eles utilizam.

**Atualização do Plugin de Construção e Repositório Maven**

O projeto deve ser atualizado para usar uma versão recente do Gradle e um dos plugins de construção modernos do NeoForge. Existem duas opções principais: NeoGradle e ModDevGradle.9 Para a maioria dos projetos, ModDevGradle oferece um

build.gradle mais simples e simplificado, enquanto NeoGradle fornece suporte mais complexo para casos de uso avançados, como projetos multiversão.9 A escolha entre eles reflete uma nova consideração no setup do projeto, um resultado direto da reestruturação da comunidade e da equipe de desenvolvimento que busca inovar em toda a cadeia de ferramentas do desenvolvedor.

Independentemente do plugin escolhido, o build.gradle deve ser atualizado para apontar para o novo repositório Maven do NeoForge e usar o novo artefato de dependência.

1. **Adicionar o Repositório Maven do NeoForge**: O repositório do Forge não é mais suficiente. O seguinte repositório deve ser adicionado ao seu bloco repositories em build.gradle:  
   Groovy  
   maven { url 'https://maven.neoforged.net/releases/' }

   Este passo é crucial, pois todos os artefatos do NeoForge são hospedados aqui.13  
2. **Alterar a Dependência Principal**: A dependência principal do Forge foi substituída. A linha que antes se parecia com minecraft 'net.minecraftforge:forge:...' deve ser alterada para o artefato do NeoForge:  
   Groovy  
   // Antigo  
   // minecraft "net.minecraftforge:forge:1.20.1-47.x.x"

   // Novo  
   neoForge "net.neoforged:neoforge:1.21.1-21.1.x"

   A nomenclatura neoForge substitui minecraft como o nome da configuração da dependência.13

### **1.2. O Manifesto neoforge.mods.toml**

Uma das mudanças mais visíveis e simbolicamente importantes é a renomeação do arquivo de metadados do mod.

**Renomeação Crítica de Arquivo**

O arquivo src/main/resources/META-INF/mods.toml **deve** ser renomeado para src/main/resources/META-INF/neoforge.mods.toml.14 Esta não é uma mudança trivial; é uma declaração de independência do ecossistema Forge. Força ferramentas externas, como launchers e utilitários de publicação (

mc-publish), a reconhecerem explicitamente a distinção entre um mod Forge e um mod NeoForge, eliminando a ambiguidade que existia durante o período inicial de compatibilidade cruzada.2 Ao criar uma barreira clara, a equipe do NeoForge garante que os mods sejam carregados pelo sistema correto, um passo necessário para construir um ecossistema distinto e estável.

**Estrutura e Propriedades Mandatórias**

A estrutura do arquivo permanece em formato TOML, mas os desenvolvedores devem garantir que as propriedades mandatórias estejam presentes e corretas.19

* modLoader \= "javafml": Especifica o carregador de linguagem. Para a maioria dos mods Java, este valor permanece o mesmo.  
* loaderVersion \= "\]: O início de uma tabela de mod. Cada mod dentro do JAR precisa de sua própria seção \[\[mods\]\].  
  * modId \= "seumodid": O identificador único do seu mod, que deve corresponder ao valor na sua anotação @Mod.  
  * version \= "${file.jarVersion}": A versão do seu mod. Usar a substituição do Gradle é a prática padrão.  
  * displayName \= "Nome de Exibição do Seu Mod": O nome legível por humanos.  
  * description \= '''...''': Uma descrição detalhada para a tela de mods.

### **1.3. Navegando pelas Renomeações de Pacotes Principais**

A mudança de API mais fundamental e abrangente é a renomeação do pacote raiz de net.minecraftforge para net.neoforged. Quase todas as importações que antes referenciavam as APIs do Forge precisarão ser atualizadas.23 Uma operação de "encontrar e substituir" em todo o projeto é o primeiro passo prático para a migração de código.

A tabela a seguir fornece uma referência rápida para as mudanças de caminho de importação mais comuns que os desenvolvedores encontrarão.

| Sistema | Importação Antiga (Forge 1.20.1) | Importação Nova (NeoForge 1.21.1) |
| :---- | :---- | :---- |
| **Barramento de Eventos** | net.minecraftforge.eventbus.api.IEventBus | net.neoforged.bus.api.IEventBus |
| **Anotação de Evento** | net.minecraftforge.eventbus.api.SubscribeEvent | net.neoforged.bus.api.SubscribeEvent |
| **Inscrito de Evento** | net.minecraftforge.fml.common.Mod.EventBusSubscriber | net.neoforged.neoforge.event.EventBusSubscriber |
| **Registro de Capability** | net.minecraftforge.event.AttachCapabilitiesEvent | net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent |
| **Registros** | net.minecraftforge.registries.ForgeRegistries | net.minecraft.core.registries.BuiltInRegistries / net.neoforged.neoforge.registries.NeoForgeRegistries |
| **Registro Diferido** | net.minecraftforge.registries.DeferredRegister | net.neoforged.neoforge.registries.DeferredRegister |
| **Camadas de GUI (Overlay)** | net.minecraftforge.client.event.RegisterGuiOverlaysEvent | net.neoforged.neoforge.client.event.RegisterGuiLayersEvent |

Após concluir essas etapas fundamentais, o projeto estará em um estado onde pode ser compilado contra as novas APIs do NeoForge, embora com numerosos erros de compilação que serão abordados nas seções a seguir.

## **Seção 2: A Revisão do Sistema de *Capabilities*: De Anexos Dinâmicos a Provedores Estáticos**

A reformulação do sistema de *Capabilities* é, sem dúvida, a mudança mais impactante e arquitetonicamente significativa na migração de Forge 1.20.1 para NeoForge 1.21.1. Ela aborda diretamente a maioria das importações problemáticas listadas na consulta do usuário e representa uma mudança de paradigma de um sistema dinâmico e baseado em eventos para um modelo estático, registrado e seguro em tipo.

### **2.1. Desconstruindo o Sistema Legado**

Para migrar efetivamente, é essencial entender o que foi removido e por quê. O antigo sistema, centrado em torno de AttachCapabilitiesEvent, foi completamente eliminado. As seguintes classes e interfaces não existem mais no contexto em que eram usadas:

* net.minecraftforge.common.capabilities.Capability  
* net.minecraftforge.common.capabilities.CapabilityManager  
* net.minecraftforge.common.capabilities.CapabilityToken  
* net.minecraftforge.common.capabilities.ICapabilityProvider (como uma interface para anexar dinamicamente)  
* net.minecraftforge.common.capabilities.ICapabilitySerializable  
* net.minecraftforge.common.util.LazyOptional

O sistema legado dependia do AttachCapabilitiesEvent, que era disparado para cada instância de BlockEntity, Entity e ItemStack criada no jogo.24 Os mods então "anexavam" uma implementação de

ICapabilityProvider que, por sua vez, expunha a capacidade real através de um LazyOptional. Este design, embora flexível, apresentava desvantagens significativas:

* **Sobrecarga de Desempenho**: Disparar um evento para cada objeto criado gerava um tráfego massivo no barramento de eventos, especialmente durante a geração de chunks ou manipulação de inventário em grande escala.  
* **Complexidade do Ciclo de Vida**: O uso de LazyOptional era uma tentativa de gerenciar o ciclo de vida da instância da capacidade, mas exigia que os desenvolvedores invalidassem manualmente os LazyOptionals para evitar vazamentos de memória e referências obsoletas, uma fonte comum de bugs sutis.25  
* **Falta de Previsibilidade**: Era difícil determinar com certeza se uma capacidade estaria presente em um objeto sem consultar o evento em tempo de execução, o que tornava o cache e a otimização complexos.7

A equipe do NeoForge identificou esses problemas como um grande obstáculo à estabilidade e ao desempenho e optou por uma reformulação completa, adotando um padrão de injeção de dependência mais explícito.

### **2.2. O Novo Modelo de Provedor: BlockCapability, EntityCapability, ItemCapability**

O novo sistema introduz três classes distintas para definir capacidades, cada uma adaptada ao seu alvo.27 Em vez de um objeto

Capability genérico obtido através de um CapabilityToken, as capacidades são agora objetos estáticos e finais, declarados uma vez por tipo de alvo:

* net.neoforged.neoforge.capabilities.BlockCapability\<T, C\>: Para capacidades em blocos e BlockEntity.  
* net.neoforged.neoforge.capabilities.EntityCapability\<T, C\>: Para capacidades em Entity.  
* net.neoforged.neoforge.capabilities.ItemCapability\<T, C\>: Para capacidades em ItemStack.

Onde $T$ é a interface da capacidade (ex: IItemHandler) e $C$ é o tipo de contexto da consulta (ex: @Nullable Direction).

Exemplo de Declaração de Capacidade:  
A declaração de uma capacidade agora é explícita e segura em tipo.  
*Código Antigo (Forge 1.20.1):*

Java

// Em uma classe de registro de capacidades  
public static final Capability\<IItemHandler\> ITEM\_HANDLER\_CAPABILITY \= CapabilityManager.get(new CapabilityToken\<\>() {});

*Código Novo (NeoForge 1.21.1):*

Java

// Em uma classe dedicada, por exemplo, ModCapabilities.java  
import net.neoforged.neoforge.capabilities.BlockCapability;  
import net.neoforged.neoforge.items.IItemHandler;  
import net.minecraft.core.Direction;  
import net.minecraft.resources.ResourceLocation;  
import org.jetbrains.annotations.Nullable;

public class ModCapabilities {  
    public static final BlockCapability\<IItemHandler, @Nullable Direction\> ITEM\_HANDLER \=  
        BlockCapability.create(ResourceLocation.fromNamespaceAndPath("meumod", "item\_handler"), IItemHandler.class, Direction.class);  
}

### **2.3. Registrando Provedores de Capacidade com RegisterCapabilitiesEvent**

A mudança mais crucial é a substituição do AttachCapabilitiesEvent pelo RegisterCapabilitiesEvent.27 Em vez de anexar capacidades a cada instância de objeto, os desenvolvedores agora registram um "provedor" (uma função lambda) uma única vez durante o carregamento do mod. Este provedor é então invocado pelo sistema quando uma capacidade é solicitada para um objeto correspondente.

Este evento é disparado no barramento de eventos do mod (EventBusSubscriber.Bus.MOD).

**Exemplos de Registro de Provedor:**

**Para um Item:**

Java

@EventBusSubscriber(modid \= MeuMod.MODID, bus \= EventBusSubscriber.Bus.MOD)  
public class ModEvents {  
    @SubscribeEvent  
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {  
        // Registra um provedor para um item específico.  
        // O provedor é uma função lambda que recebe o ItemStack e o contexto.  
        event.registerItem(ModCapabilities.ITEM\_HANDLER, (itemStack, context) \-\> {  
            return new MeuItemHandler(itemStack); // Retorna a implementação da capacidade  
        }, MEU\_ITEM\_CUSTOM.get()); // O item ao qual este provedor se aplica  
    }  
}

**Para um BlockEntity:**

Java

// Dentro do mesmo evento RegisterCapabilitiesEvent  
event.registerBlockEntity(  
    ModCapabilities.ITEM\_HANDLER, // A capacidade a ser registrada  
    MeuBlockEntities.MEU\_BE.get(), // O tipo do BlockEntity  
    (blockEntity, context) \-\> blockEntity.getInternalItemHandler() // A lambda que obtém a capacidade da instância do BE  
);

**Para uma Entity:**

Java

// Dentro do mesmo evento RegisterCapabilitiesEvent  
event.registerEntity(  
    ModCapabilities.MANA\_HANDLER, // Uma capacidade personalizada, por exemplo  
    MeuEntityTypes.MEU\_MOB.get(), // O tipo da entidade  
    (entity, context) \-\> entity.getManaHandler() // A lambda que obtém a capacidade da instância da entidade  
);

Este novo modelo de registro centralizado é a chave para os ganhos de desempenho e previsibilidade do novo sistema. Ao registrar todos os provedores durante a inicialização, o NeoForge constrói um mapa estático de quais objetos podem fornecer quais capacidades. As consultas subsequentes tornam-se uma busca rápida neste mapa, em vez de um disparo de evento custoso. Isso também torna possível um sistema de cache robusto, como o BlockCapabilityCache, pois o conjunto de provedores possíveis é conhecido antecipadamente.7

### **2.4. Consultando Capacidades e Substituindo LazyOptional**

Com a remoção do LazyOptional, a consulta de capacidades tornou-se mais direta. Os métodos getCapability agora retornam a instância da capacidade diretamente, ou null se não houver nenhum provedor registrado para aquele objeto e contexto.7

*Código Antigo (Forge 1.20.1):*

Java

LazyOptional\<IItemHandler\> lazyHandler \= blockEntity.getCapability(ForgeCapabilities.ITEM\_HANDLER, side);  
lazyHandler.ifPresent(handler \-\> {  
    // Lógica com o handler  
});

*Código Novo (NeoForge 1.21.1):*

Java

// Para BlockEntities, Entities, e ItemStacks  
IItemHandler handler \= blockEntity.getCapability(ModCapabilities.ITEM\_HANDLER, side);  
if (handler\!= null) {  
    // Lógica com o handler  
}

Para blocos no mundo (que podem ou não ter um BlockEntity), a consulta é feita no objeto Level:

Java

IItemHandler handler \= level.getCapability(ModCapabilities.ITEM\_HANDLER, pos, side);  
if (handler\!= null) {  
    // Lógica com o handler  
}

A responsabilidade pela invalidação do ciclo de vida agora recai principalmente sobre o BlockCapabilityCache para consultas de blocos frequentes. Para mudanças de estado que afetam as capacidades de um bloco, o desenvolvedor deve chamar manualmente level.invalidateCapabilities(pos) para notificar o sistema de cache.27

A tabela a seguir resume as principais mudanças no sistema de capacidades.

| Conceito | Abordagem Forge 1.20.1 (net.minecraftforge.\*) | Abordagem NeoForge 1.21.1 (net.neoforged.neoforge.\*) |
| :---- | :---- | :---- |
| **Definição de uma Capacidade** | CapabilityManager.get(new CapabilityToken\<T\>()) | Block/Item/EntityCapability.create(...) |
| **Anexação de uma Capacidade** | Ouvir AttachCapabilitiesEvent, chamar event.addCapability(), fornecer um ICapabilityProvider. | Ouvir RegisterCapabilitiesEvent, chamar event.registerBlock/Item/Entity(), fornecer uma função lambda provedora. |
| **Exposição de uma Capacidade** | Retornar um LazyOptional.of(() \-\> meuHandler) do provedor. | Retornar a instância do handler diretamente da lambda do provedor. |
| **Consulta de uma Capacidade** | objeto.getCapability(CAP).ifPresent(...) | T cap \= objeto.getCapability(CAP); if (cap\!= null) {... } |
| **Gerenciamento do Ciclo de Vida** | Invalidar instâncias de LazyOptional em \#invalidateCaps ou Item\#initCapabilities. | Para blocos, usar BlockCapabilityCache e chamar level.invalidateCapabilities(pos) quando o estado muda. |

## **Seção 3: Modernizando Dados: A Ascensão dos Componentes de Dados**

A introdução dos Componentes de Dados (Data Components) pelo Minecraft na versão 1.20.5 é uma das mudanças mais fundamentais na história recente do jogo, e o NeoForge 1.21.1 a abraça totalmente. Este sistema substitui o antigo e não estruturado CompoundTag (NBT) em ItemStacks, e sua migração está intrinsecamente ligada à refatoração do sistema de capacidades.

### **3.1. O "Porquê": Entendendo a Mudança do NBT do ItemStack**

O antigo sistema de anexar um único CompoundTag a um ItemStack foi preterido. Agora, dados personalizados são armazenados em um mapa estruturado e seguro em tipo, onde as chaves são instâncias de DataComponentType e os valores são objetos imutáveis.33 As razões para esta mudança são multifacetadas:

* **Desempenho**: Acessar um valor em um mapa tipado é significativamente mais rápido do que analisar uma árvore NBT em busca de uma chave de string.  
* **Segurança de Tipo (Type Safety)**: O novo sistema elimina "stringly-typed" keys (chaves baseadas em strings), onde um simples erro de digitação no nome da chave NBT poderia levar a bugs difíceis de rastrear. Com DataComponentType, o compilador pode verificar se a chave existe.  
* **Eficiência de Rede**: Em vez de sincronizar um grande blob NBT, o jogo agora pode sincronizar apenas os componentes que mudaram.  
* **Clareza da API**: As definições de dados agora são explícitas e auto-documentadas através da declaração de DataComponentType.

### **3.2. Definindo e Registrando um DataComponentType**

Assim como itens e blocos, os DataComponentTypes são objetos de registro. Eles devem ser declarados e registrados usando um DeferredRegister especializado.

1. **Crie um DeferredRegister para Componentes**:  
   Java  
   // Em uma classe de registro, por exemplo, ModDataComponents.java  
   public static final DeferredRegister.DataComponents DATA\_COMPONENTS \=  
       DeferredRegister.createDataComponents(MeuMod.MODID);

   39  
2. **Defina o DataComponentType**: O registro requer um Codec para persistência (salvar no disco) e um StreamCodec para sincronização de rede. O valor do componente (o objeto que armazena os dados) deve ser imutável; records Java são ideais para isso.  
   Java  
   // Exemplo de um componente para armazenar um contador de usos  
   public record UsesLeft(int value) {  
       public static final Codec\<UsesLeft\> CODEC \= Codec.INT.xmap(UsesLeft::new, UsesLeft::value);  
       public static final StreamCodec\<ByteBuf, UsesLeft\> STREAM\_CODEC \= ByteBufCodecs.VAR\_INT.map(UsesLeft::new, UsesLeft::value);  
   }

   public static final Supplier\<DataComponentType\<UsesLeft\>\> USES\_LEFT \= DATA\_COMPONENTS.registerComponentType("uses\_left", builder \-\> builder  
      .persistent(UsesLeft.CODEC)  
      .networkSynchronized(UsesLeft.STREAM\_CODEC)  
   );

   33  
   A exigência de persistent ou networkSynchronized (ou ambos) é rigorosa; omiti-los resultará em uma exceção durante o carregamento.33

### **3.3. Interagindo com Componentes em ItemStack**

Com o DataComponentType registrado, a interação com os dados no ItemStack é feita através de métodos diretos e seguros em tipo, substituindo getOrCreateTag() e getTag().put....

* **Definindo um Valor Padrão**: Ao criar um Item, você pode definir um valor padrão para um componente.  
  Java  
  // No registro do seu item  
  new Item(new Item.Properties().component(ModDataComponents.USES\_LEFT.get(), new UsesLeft(100)))

  33  
* **Lendo um Valor**:  
  Java  
  // Retorna null se o componente não estiver presente  
  UsesLeft uses \= stack.get(ModDataComponents.USES\_LEFT.get());  
  int value \= (uses\!= null)? uses.value() : 0;

  // Ou com um valor padrão  
  int valueOrDefault \= stack.getOrDefault(ModDataComponents.USES\_LEFT.get(), new UsesLeft(0)).value();

  33  
* **Modificando um Valor**: Como os componentes são imutáveis, você não modifica o valor existente. Em vez disso, você define um novo valor no stack.  
  Java  
  // Define um novo valor, substituindo o antigo  
  stack.set(ModDataComponents.USES\_LEFT.get(), new UsesLeft(50));

  // Atualiza o valor com base no valor antigo  
  stack.update(ModDataComponents.USES\_LEFT.get(), new UsesLeft(0), oldUses \-\> new UsesLeft(oldUses.value() \- 1));

  33

A migração de uma capacidade baseada em NBT agora segue um novo padrão de dois estágios. A capacidade não é mais responsável por sua própria serialização (ICapabilitySerializable foi removido). Em vez disso, a *capacidade* expõe o *comportamento*, enquanto o *componente de dados* armazena o *estado*.

Uma capacidade de item que anteriormente armazenava um contador em NBT agora seria migrada da seguinte forma:

1. Criar um DataComponentType\<Integer\> para armazenar o contador.  
2. A implementação da capacidade (por exemplo, MyCounterCapability) não armazenaria mais o valor diretamente.  
3. Os métodos em MyCounterCapability (por exemplo, decrement()) receberiam o ItemStack e usariam stack.update(MY\_COUNTER\_COMPONENT,...) para modificar o estado persistente.

Esta separação de preocupações—estado (componentes) vs. comportamento (capacidades)—é uma melhoria arquitetônica fundamental que resulta em um código mais limpo, mais robusto e mais fácil de manter.

## **Seção 4: Adaptando-se ao Sistema de Eventos Modernizado**

Juntamente com as grandes refatorações, o sistema de eventos do NeoForge passou por mudanças sutis, mas importantes, destinadas a aumentar a clareza e reduzir erros comuns.

### **4.1. Inscritos de Eventos e Barramentos (Event Subscribers and Buses)**

A anotação para registrar automaticamente classes de manipuladores de eventos foi movida e seus parâmetros foram alterados para serem mais explícitos.

* **Mudança de Pacote e Parâmetros**: A anotação @Mod.EventBusSubscriber foi movida de uma classe aninhada para um pacote de nível superior: net.neoforged.neoforge.event.EventBusSubscriber.41 Além disso, seu uso agora exige mais especificidade:  
  * O parâmetro modid agora é obrigatório. Isso ajuda o NeoForge a rastrear qual mod está registrando qual ouvinte, melhorando drasticamente a utilidade das mensagens de log e dos relatórios de erro.  
  * O parâmetro bus foi introduzido para especificar explicitamente a qual barramento de eventos a classe deve se inscrever: EventBusSubscriber.Bus.MOD para eventos de ciclo de vida (como registro) ou EventBusSubscriber.Bus.GAME para eventos de jogabilidade.42 Isso elimina a ambiguidade de registrar ouvintes no barramento errado.

*Código Antigo (Forge 1.20.1):*

Java

@Mod.EventBusSubscriber(modid \= MeuMod.MODID, bus \= Mod.EventBusSubscriber.Bus.MOD)  
public class ModEvents {  
    //...  
}

*Código Novo (NeoForge 1.21.1):*

Java

import net.neoforged.neoforge.event.EventBusSubscriber;

@EventBusSubscriber(modid \= MeuMod.MODID, bus \= EventBusSubscriber.Bus.MOD)  
public class ModEvents {  
    //...  
}

A tabela a seguir detalha os parâmetros para a nova anotação @EventBusSubscriber.

| Parâmetro | Tipo | Obrigatório? | Descrição |
| :---- | :---- | :---- | :---- |
| modid | String | Sim | O ID do seu mod. Usado para registro e diagnóstico. |
| bus | EventBusSubscriber.Bus | Não (padrão GAME) | O barramento de eventos para se inscrever. Use MOD para eventos de configuração e GAME para eventos de jogo. |
| value | Dist | Não (padrão ambos) | O lado físico para carregar o inscrito (Dist.CLIENT ou Dist.DEDICATED\_SERVER). |

### **4.2. Substituindo Event.Result por TriState**

A enumeração genérica Event.Result (ALLOW, DENY, DEFAULT) foi removida.8 Este foi um ponto comum de confusão e bugs, pois os desenvolvedores podiam definir um resultado em um evento que não o suportava, levando a um comportamento indefinido.

O novo paradigma favorece a segurança de tipo e a intenção explícita:

1. **Eventos com Resultados Específicos**: Muitos eventos agora têm sua própria enumeração Result aninhada (por exemplo, MobDespawnEvent.Result). O método setResult agora só aceitará instâncias dessa enumeração específica, impedindo o uso de resultados incorretos em tempo de compilação.  
2. **TriState para Lógica Ternária**: Para eventos que representam uma lógica de permissão/negação/padrão, a classe net.neoforged.neoforge.common.util.TriState é agora usada. Ela contém três valores: TRUE, FALSE e DEFAULT. Os eventos que usam TriState expõem métodos de setter específicos, como setCanRender(TriState) ou setCanHarvest(TriState), tornando a intenção do código inequívoca.43

*Exemplo de Migração de Manipulador de Evento:*

Java

// Código Antigo (Forge 1.20.1)  
@SubscribeEvent  
public static void onRenderNameTag(RenderNameTagEvent event) {  
    if (someCondition) {  
        event.setResult(Event.Result.DENY);  
    }  
}

// Código Novo (NeoForge 1.21.1)  
// Nota: O próprio evento pode ter sido renomeado ou reestruturado.  
// Este é um exemplo conceitual.  
@SubscribeEvent  
public static void onRenderNameTag(RenderNameTagEvent event) {  
    if (someCondition) {  
        // O método agora é específico para a ação do evento  
        // e usa TriState para clareza.  
        event.setCanRender(TriState.FALSE);  
    }  
}

Essa mudança, embora exija a atualização de muitos manipuladores de eventos, é uma melhoria significativa na robustez da API. Ela transfere a detecção de erros do tempo de execução para o tempo de compilação, que é um dos princípios centrais da filosofia de design do NeoForge.

## **Seção 5: Migrando Renderização e Lógica do Lado do Cliente**

A migração para o NeoForge 1.21.1 também envolve mudanças significativas na forma como a lógica e a renderização do lado do cliente são tratadas, abordando diretamente a importação IGuiOverlay do usuário e aposentando ferramentas de longa data como DistExecutor.

### **5.1. O Novo Sistema de Camadas de GUI (GUI Layer System)**

O sistema de sobreposição de GUI (GUI Overlay) do Forge, que usava a interface IGuiOverlay e o evento RegisterGuiOverlaysEvent, foi completamente removido.16 Essa remoção reflete uma mudança para um sistema mais genérico e poderoso, agora chamado de "Camadas de GUI" (

GUI Layers). Este novo sistema não se limita a simples sobreposições de HUD, mas permite a injeção de lógica de renderização personalizada em qualquer ponto da pilha de renderização da GUI do jogo.16

### **5.2. Implementando e Registrando Camadas de GUI**

A migração de uma IGuiOverlay antiga para o novo sistema envolve duas etapas principais:

1. **Usar o Novo Evento de Registro**: O evento para registrar renderizadores de GUI personalizados agora é net.neoforged.neoforge.client.event.RegisterGuiLayersEvent. Este evento, como seu predecessor, é disparado no barramento de eventos do mod no lado do cliente (Dist.CLIENT).16  
2. **Implementar a Nova Interface Funcional**: A interface IGuiOverlay foi substituída pela interface funcional net.neoforged.neoforge.client.gui.LayeredDraw.Layer. Esta interface tem um único método, render(GuiGraphics, float), que recebe os mesmos parâmetros que o método de renderização antigo, tornando a migração da lógica de renderização em si bastante direta.48

**Exemplo Completo de Migração de HUD:**

O código a seguir demonstra como migrar um HUD simples que exibe texto na tela.

*Código Antigo (Forge 1.20.1):*

Java

// Em uma classe de eventos do cliente  
@Mod.EventBusSubscriber(modid \= MeuMod.MODID, bus \= Mod.EventBusSubscriber.Bus.MOD, value \= Dist.CLIENT)  
public class ClientEvents {  
    @SubscribeEvent  
    public static void registerOverlays(RegisterGuiOverlaysEvent event) {  
        event.registerAboveAll("my\_hud", (forgeGui, guiGraphics, partialTick, screenWidth, screenHeight) \-\> {  
            // Lógica de renderização aqui  
            guiGraphics.drawString(Minecraft.getInstance().font, "Hello Forge\!", 10, 10, 0xFFFFFF);  
        });  
    }  
}

*Código Novo (NeoForge 1.21.1):*

Java

import net.neoforged.api.distmarker.Dist;  
import net.neoforged.bus.api.SubscribeEvent;  
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;  
import net.neoforged.neoforge.event.EventBusSubscriber;  
import net.minecraft.client.Minecraft;  
import net.minecraft.resources.ResourceLocation;

@EventBusSubscriber(modid \= MeuMod.MODID, bus \= EventBusSubscriber.Bus.MOD, value \= Dist.CLIENT)  
public class ClientModEvents {  
    @SubscribeEvent  
    public static void registerGuiLayers(RegisterGuiLayersEvent event) {  
        // O registro agora usa um ResourceLocation para o ID  
        event.registerAboveAll(  
            ResourceLocation.fromNamespaceAndPath(MeuMod.MODID, "my\_hud"),  
            // A nova interface funcional tem uma assinatura mais limpa  
            (guiGraphics, partialTicks) \-\> {  
                // A lógica de renderização permanece a mesma  
                guiGraphics.drawString(Minecraft.getInstance().font, "Hello NeoForge\!", 10, 10, 0xFFFFFF);  
            }  
        );  
    }  
}

Os métodos de registro como registerAboveAll, registerBelow, etc., ainda existem, mas agora aceitam um ResourceLocation como identificador, o que é mais consistente com o resto do sistema de registro do Minecraft.47

### **5.3. O Fim do DistExecutor**

DistExecutor foi uma ferramenta usada por muito tempo para executar código específico do lado do cliente ou do servidor de uma forma que evitasse falhas de carregamento de classe em um ambiente de servidor dedicado (que não possui classes de cliente). Esta classe foi removida no NeoForge.8 A remoção foi motivada pelo fato de que suas implementações baseadas em reflexão eram complexas e muitas vezes ofuscavam abordagens mais simples e modernas.

Existem duas alternativas principais e mais limpas para lidar com código específico de lado:

1. **Pontos de Entrada Separados (Abordagem Recomendada)**: A anotação @Mod agora inclui um parâmetro dist. Ao usar este parâmetro, você pode criar duas classes de mod principais separadas: uma para a lógica comum (que roda em ambos os lados) e outra para a lógica exclusiva do cliente. O NeoForge garantirá que a classe do cliente seja carregada apenas no cliente físico.  
   *Classe Comum:*  
   Java  
   @Mod(MeuMod.MODID)  
   public class MeuMod {  
       public MeuMod(IEventBus modEventBus) {  
           // Lógica comum, registro de itens, blocos, etc.  
       }  
   }

   *Classe do Cliente:*  
   Java  
   @Mod(value \= MeuMod.MODID, dist \= Dist.CLIENT)  
   public class MeuModClient {  
       public MeuModClient(IEventBus modEventBus) {  
           // Lógica exclusiva do cliente, como registro de manipuladores de eventos de renderização,  
           // keybindings, e camadas de GUI.  
           modEventBus.addListener(ClientModEvents::registerGuiLayers);  
       }  
   }

   Esta abordagem é a mais limpa e robusta, pois isola completamente o código do cliente, evitando qualquer possibilidade de NoClassDefFoundError no servidor.20  
2. **Verificações em Tempo de Execução**: Para pequenas e isoladas porções de código, uma simples verificação em tempo de execução ainda é uma opção viável.  
   Java  
   import net.neoforged.fml.loading.FMLLoader;  
   import net.neoforged.api.distmarker.Dist;

   public void algumMetodo() {  
       if (FMLLoader.getDist() \== Dist.CLIENT) {  
           // Executa código que só deve rodar no cliente  
           Minecraft.getInstance()....  
       }  
   }

   Esta abordagem deve ser usada com moderação, pois pode levar a código desordenado se usada excessivamente. É mais adequada para casos onde a separação em classes inteiras seria um exagero.

## **Seção 6: Solução de Problemas Comuns na Migração**

A migração de uma base de código entre versões de API tão distintas inevitavelmente introduzirá erros. Compreender os erros mais comuns e como diagnosticá-los é fundamental para um processo de migração tranquilo.

### **6.1. Resolvendo java.lang.NoSuchMethodError**

Este é, de longe, o erro mais comum que os desenvolvedores encontrarão durante a migração. Um NoSuchMethodError é um erro de tempo de execução que ocorre quando o código foi compilado com uma versão de uma biblioteca (neste caso, a API do Forge 1.20.1), mas está sendo executado com uma versão diferente (a API do NeoForge 1.21.1) onde o método esperado foi renomeado, removido ou teve sua assinatura (parâmetros) alterada.50

Para diagnosticar e resolver este erro, siga esta lista de verificação:

1. **Verifique Todas as Importações**: A causa mais provável é uma importação remanescente de net.minecraftforge. Faça uma busca em todo o projeto por import net.minecraftforge e substitua cada ocorrência pela sua contraparte em net.neoforged.  
2. **Limpe o Cache de Construção**: Dependências antigas podem permanecer no cache do Gradle. Execute os seguintes comandos no terminal do seu projeto para forçar uma atualização completa:

./gradlew \--refresh-dependencies  
./gradlew clean  
\`\`\`  
Depois, atualize seu projeto no seu IDE.50

3\. Verifique as Dependências de Terceiros: Se o seu mod depende de outras bibliotecas, certifique-se de que você está usando uma versão da biblioteca que é explicitamente compatível com o NeoForge 1.21.1. Uma dependência antiga pode estar tentando chamar métodos do Forge que não existem mais.  
4\. Analise o Rastreamento de Pilha (Stack Trace): O rastreamento de pilha lhe dirá exatamente qual classe e método está causando o erro. Use essa informação em conjunto com as tabelas de migração e a documentação oficial do NeoForge para encontrar o substituto correto. Por exemplo, um erro em ForgeGui.renderSelectedItemName 51 aponta diretamente para a refatoração do sistema de GUI.

### **6.2. Estratégias de Depuração**

Além de corrigir erros de compilação diretos, a migração pode introduzir problemas de lógica ou desempenho.

* **Migração Incremental**: Não tente migrar todo o seu mod de uma só vez. Siga uma abordagem passo a passo. Primeiro, faça o projeto compilar, concentrando-se nas mudanças de build.gradle e neoforge.mods.toml. Em seguida, migre um sistema de cada vez: comece com os registros (DeferredRegister), depois passe para os eventos, depois para as capacidades e, finalmente, para a renderização. Teste o jogo após cada etapa bem-sucedida. Esta abordagem isola os problemas, tornando muito mais fácil identificar a causa raiz de um bug.55  
* **Use o Perfilador de Depuração (Debug Profiler)**: Se o seu mod apresentar problemas de desempenho após a migração, use o perfilador embutido do Minecraft (F3 \+ L). Ele pode ajudar a identificar se uma nova implementação de API (como um provedor de capacidade mal otimizado) está causando lag.56  
* **Consulte a Comunidade**: O ecossistema do NeoForge é altamente colaborativo. O servidor oficial do Discord é um recurso inestimável, com canais dedicados ao suporte a modders. Muitos dos desenvolvedores principais do NeoForge estão ativos lá e podem fornecer orientação sobre as melhores práticas e soluções para problemas complexos.57

## **Conclusão**

A migração de um mod de Forge 1.20.1 para NeoForge 1.21.1 é uma tarefa substancial que vai muito além de uma simples atualização de dependências. Ela representa uma adoção deliberada de uma nova filosofia de design de API que prioriza a explicitação, a segurança de tipo e o desempenho em detrimento da flexibilidade irrestrita dos sistemas legados.

As refatorações centrais—o sistema de capacidades baseado em provedores, a substituição do NBT por componentes de dados e a modernização do sistema de eventos—são todas facetas dessa mesma filosofia. Elas exigem que os desenvolvedores sejam mais explícitos em suas intenções, registrando provedores centralmente em vez de anexá-los dinamicamente e definindo estruturas de dados com codecs em vez de usar mapas de string não tipados. Embora isso possa exigir uma reestruturação significativa do código, o resultado é um mod que é inerentemente mais robusto, mais fácil de depurar e mais performático.

Para os desenvolvedores, o caminho a seguir é claro: abrace os novos padrões. Entenda que a remoção de LazyOptional e AttachCapabilitiesEvent não é uma perda de funcionalidade, mas um ganho em previsibilidade e desempenho. Veja os Componentes de Dados não como um substituto complicado para NBT, mas como uma ferramenta poderosa para definir dados de itens de forma estruturada e segura. Abrace a clareza exigida por eventos como RegisterCapabilitiesEvent e RegisterGuiLayersEvent.

Ao internalizar a lógica por trás dessas mudanças, os desenvolvedores não estarão apenas "corrigindo importações", mas sim alinhando seu código com as melhores práticas de um ecossistema de modding que está evoluindo para ser mais estável e sustentável a longo prazo. A transição para o NeoForge 1.21.1 é um investimento no futuro, garantindo que os mods construídos hoje permaneçam compatíveis e performáticos no cenário em constante mudança do Minecraft modificado.

#### **Referências citadas**

1. Minecraft Forge vs NeoForge // What is this New Mod Loader? \- YouTube, acessado em junho 24, 2025, [https://www.youtube.com/watch?v=d6a8XYIfnG0](https://www.youtube.com/watch?v=d6a8XYIfnG0)  
2. Forge or NeoForge for 1.20.1? : r/feedthebeast \- Reddit, acessado em junho 24, 2025, [https://www.reddit.com/r/feedthebeast/comments/1iaduyh/forge\_or\_neoforge\_for\_1201/](https://www.reddit.com/r/feedthebeast/comments/1iaduyh/forge_or_neoforge_for_1201/)  
3. I'm out of the loop, what is the difference between Neoforge and Fabric? (1.20.1) \- Reddit, acessado em junho 24, 2025, [https://www.reddit.com/r/feedthebeast/comments/1gtt3q6/im\_out\_of\_the\_loop\_what\_is\_the\_difference\_between/](https://www.reddit.com/r/feedthebeast/comments/1gtt3q6/im_out_of_the_loop_what_is_the_difference_between/)  
4. Why NeoForge is popular in 1.21 : r/feedthebeast \- Reddit, acessado em junho 24, 2025, [https://www.reddit.com/r/feedthebeast/comments/1hm191l/why\_neoforge\_is\_popular\_in\_121/](https://www.reddit.com/r/feedthebeast/comments/1hm191l/why_neoforge_is_popular_in_121/)  
5. Can somebody explains what NeoForge is? Is it better than forge and fabric? \- Reddit, acessado em junho 24, 2025, [https://www.reddit.com/r/feedthebeast/comments/1e3acvv/can\_somebody\_explains\_what\_neoforge\_is\_is\_it/](https://www.reddit.com/r/feedthebeast/comments/1e3acvv/can_somebody_explains_what_neoforge_is_is_it/)  
6. 2024: The first Mod Packs \- The NeoForged project, acessado em junho 24, 2025, [https://neoforged.net/news/2024-retrospection/](https://neoforged.net/news/2024-retrospection/)  
7. The Capability rework \- The NeoForged project, acessado em junho 24, 2025, [https://neoforged.net/news/20.3capability-rework/](https://neoforged.net/news/20.3capability-rework/)  
8. NeoForge 21.0 for Minecraft 1.21, acessado em junho 24, 2025, [https://neoforged.net/news/21.0release/](https://neoforged.net/news/21.0release/)  
9. NeoForge MDKs \- GitHub, acessado em junho 24, 2025, [https://github.com/neoforgemdks](https://github.com/neoforgemdks)  
10. github.com, acessado em junho 24, 2025, [https://github.com/neoforgemdks\#:\~:text=Some%20MDKs%20may%20have%202,versions%20in%20the%20same%20project.](https://github.com/neoforgemdks#:~:text=Some%20MDKs%20may%20have%202,versions%20in%20the%20same%20project.)  
11. 1-20-5 | Topic | Ecosyste.ms \- Repos, acessado em junho 24, 2025, [https://repos.ecosyste.ms/topics/1-20-5](https://repos.ecosyste.ms/topics/1-20-5)  
12. ModDevGradle 2 Stable Release \- The NeoForged project, acessado em junho 24, 2025, [https://neoforged.net/news/moddevgradle2/](https://neoforged.net/news/moddevgradle2/)  
13. api:migration:neoforge | Architectury Documentation, acessado em junho 24, 2025, [https://docs.architectury.dev/api/migration/neoforge](https://docs.architectury.dev/api/migration/neoforge)  
14. Mod Files | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/1.20.4/gettingstarted/modfiles/](https://docs.neoforged.net/docs/1.20.4/gettingstarted/modfiles/)  
15. Configuration | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/1.20.6/misc/config](https://docs.neoforged.net/docs/1.20.6/misc/config)  
16. NeoForge 20.5 for Minecraft 1.20.5, acessado em junho 24, 2025, [https://neoforged.net/news/20.5release/](https://neoforged.net/news/20.5release/)  
17. Renaming mods.toml · Issue \#45 · neoforged/FancyModLoader \- GitHub, acessado em junho 24, 2025, [https://github.com/neoforged/FancyModLoader/issues/45](https://github.com/neoforged/FancyModLoader/issues/45)  
18. \[Bug\] Doesn't recognize \`neoforge.mods.toml\` if \`neoforge\` dependency is omitted · Issue \#130 · Kira-NT/mc-publish \- GitHub, acessado em junho 24, 2025, [https://github.com/Kira-NT/mc-publish/issues/130](https://github.com/Kira-NT/mc-publish/issues/130)  
19. Mod Files | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/gettingstarted/modfiles](https://docs.neoforged.net/docs/gettingstarted/modfiles)  
20. Mod Files | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/1.21.1/gettingstarted/modfiles/](https://docs.neoforged.net/docs/1.21.1/gettingstarted/modfiles/)  
21. Configuration | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/1.20.4/misc/config](https://docs.neoforged.net/docs/1.20.4/misc/config)  
22. Mod Files | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/1.21.4/gettingstarted/modfiles](https://docs.neoforged.net/docs/1.21.4/gettingstarted/modfiles)  
23. Event system changes in NeoForge 20.2, acessado em junho 24, 2025, [https://neoforged.net/news/20.2eventbus-changes/](https://neoforged.net/news/20.2eventbus-changes/)  
24. Capabilities \- Minecraft Forge Documentation, acessado em junho 24, 2025, [https://docs.minecraftforge.net/en/latest/datastorage/capabilities/](https://docs.minecraftforge.net/en/latest/datastorage/capabilities/)  
25. Curios/CHANGELOG.md at 1.21.1 · TheIllusiveC4/Curios \- GitHub, acessado em junho 24, 2025, [https://github.com/TheIllusiveC4/Curios/blob/1.21.1/CHANGELOG.md](https://github.com/TheIllusiveC4/Curios/blob/1.21.1/CHANGELOG.md)  
26. Curios API \- Changelog \- Modrinth, acessado em junho 24, 2025, [https://modrinth.com/mod/curios/changelog?page=5](https://modrinth.com/mod/curios/changelog?page=5)  
27. Capabilities | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/datastorage/capabilities/](https://docs.neoforged.net/docs/datastorage/capabilities/)  
28. Capabilities | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/datastorage/capabilities](https://docs.neoforged.net/docs/datastorage/capabilities)  
29. 2.9.8 for NeoForge 1.21.1 \- Common Capabilities \- Modrinth, acessado em junho 24, 2025, [https://modrinth.com/mod/common-capabilities/version/1.21.1-2.9.8](https://modrinth.com/mod/common-capabilities/version/1.21.1-2.9.8)  
30. Uses of Package net.neoforged.neoforge.capabilities \- nekoyue.github.io, acessado em junho 24, 2025, [https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.21.x-neoforge/net/neoforged/neoforge/capabilities/package-use.html](https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.21.x-neoforge/net/neoforged/neoforge/capabilities/package-use.html)  
31. RegisterCapabilitiesEvent (neoforge 1.21.0-21.0.30-beta), acessado em junho 24, 2025, [https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.21.x-neoforge/net/neoforged/neoforge/capabilities/RegisterCapabilitiesEvent.html](https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.21.x-neoforge/net/neoforged/neoforge/capabilities/RegisterCapabilitiesEvent.html)  
32. 683455f39771cd4edda841ff\_29, acessado em junho 24, 2025, [https://assets.website-files.com/675362315499a5e4fc789370/683455f39771cd4edda841ff\_29472108869.pdf](https://assets.website-files.com/675362315499a5e4fc789370/683455f39771cd4edda841ff_29472108869.pdf)  
33. Data Components | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/1.21.1/items/datacomponents](https://docs.neoforged.net/docs/1.21.1/items/datacomponents)  
34. Custom Data Components | Fabric Documentation, acessado em junho 24, 2025, [https://docs.fabricmc.net/1.21/develop/items/custom-data-components](https://docs.fabricmc.net/1.21/develop/items/custom-data-components)  
35. Items | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/1.21.1/items/](https://docs.neoforged.net/docs/1.21.1/items/)  
36. Let's talk about Feature Item Stack Components\! \- Minecraft Feedback, acessado em junho 24, 2025, [https://feedback.minecraft.net/hc/en-us/community/posts/24488228743565-Let-s-talk-about-Feature-Item-Stack-Components](https://feedback.minecraft.net/hc/en-us/community/posts/24488228743565-Let-s-talk-about-Feature-Item-Stack-Components)  
37. 1.21 \- Getting an ItemStack from NBTTagCompound \- SpigotMC, acessado em junho 24, 2025, [https://www.spigotmc.org/threads/getting-an-itemstack-from-nbttagcompound.653173/](https://www.spigotmc.org/threads/getting-an-itemstack-from-nbttagcompound.653173/)  
38. Old NBT format V/S New item compenents : r/MinecraftCommands \- Reddit, acessado em junho 24, 2025, [https://www.reddit.com/r/MinecraftCommands/comments/1dd6ir6/old\_nbt\_format\_vs\_new\_item\_compenents/](https://www.reddit.com/r/MinecraftCommands/comments/1dd6ir6/old_nbt_format_vs_new_item_compenents/)  
39. KubeJS TFMG \- Minecraft Mod \- Modrinth, acessado em junho 24, 2025, [https://modrinth.com/mod/kubejs-tfmg](https://modrinth.com/mod/kubejs-tfmg)  
40. Enchantments | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/resources/server/enchantments/](https://docs.neoforged.net/docs/resources/server/enchantments/)  
41. Events | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/concepts/events/](https://docs.neoforged.net/docs/concepts/events/)  
42. How to add a loot table provider to NeoForge 1.21.4. · Issue \#1812 \- GitHub, acessado em junho 24, 2025, [https://github.com/neoforged/NeoForge/issues/1812](https://github.com/neoforged/NeoForge/issues/1812)  
43. Events | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/1.21.1/concepts/events](https://docs.neoforged.net/docs/1.21.1/concepts/events)  
44. Cobblemon Firework Capsules \- Minecraft Mod \- Modrinth, acessado em junho 24, 2025, [https://modrinth.com/mod/cobblemon-firework-capsules](https://modrinth.com/mod/cobblemon-firework-capsules)  
45. Bibliocraft Legacy is a port of the original BiblioCraft Minecraft mod by Nuchaz. It adds various pieces of decorative and functional furniture into the game. \- GitHub, acessado em junho 24, 2025, [https://github.com/MinecraftschurliMods/Bibliocraft-Legacy](https://github.com/MinecraftschurliMods/Bibliocraft-Legacy)  
46. Baked Models | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/1.21.1/resources/client/models/bakedmodel/](https://docs.neoforged.net/docs/1.21.1/resources/client/models/bakedmodel/)  
47. RegisterGuiOverlaysEvent (forge 1.19.3-44.1.8) \- nekoyue.github.io, acessado em junho 24, 2025, [https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.19.3/net/minecraftforge/client/event/RegisterGuiOverlaysEvent.html](https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.19.3/net/minecraftforge/client/event/RegisterGuiOverlaysEvent.html)  
48. RegisterGuiLayersEvent (neoforge 1.20.6-20.6.119) \- nekoyue.github.io, acessado em junho 24, 2025, [https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.20.6-neoforge/net/neoforged/neoforge/client/event/RegisterGuiLayersEvent.html](https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.20.6-neoforge/net/neoforged/neoforge/client/event/RegisterGuiLayersEvent.html)  
49. 10.4.0.14 \- Mekanism \- Modrinth, acessado em junho 24, 2025, [https://modrinth.com/mod/mekanism/version/10.4.0.14](https://modrinth.com/mod/mekanism/version/10.4.0.14)  
50. How do I fix a NoSuchMethodError? \- java \- Stack Overflow, acessado em junho 24, 2025, [https://stackoverflow.com/questions/35186/how-do-i-fix-a-nosuchmethoderror](https://stackoverflow.com/questions/35186/how-do-i-fix-a-nosuchmethoderror)  
51. Trying to launch forge but game crashed with: Error: java.lang.NoSuchMethodError \- Reddit, acessado em junho 24, 2025, [https://www.reddit.com/r/MinecraftForge/comments/188uoqg/trying\_to\_launch\_forge\_but\_game\_crashed\_with/](https://www.reddit.com/r/MinecraftForge/comments/188uoqg/trying_to_launch_forge_but_game_crashed_with/)  
52. The game crashed whilst unexpected error (Forge 1.20.1-47.1.44) : r/MinecraftForge, acessado em junho 24, 2025, [https://www.reddit.com/r/MinecraftForge/comments/15otlpv/the\_game\_crashed\_whilst\_unexpected\_error\_forge/](https://www.reddit.com/r/MinecraftForge/comments/15otlpv/the_game_crashed_whilst_unexpected_error_forge/)  
53. java.lang.NoSuchMethodError: on initialization \- Modder Support \- Minecraft Forge Forums, acessado em junho 24, 2025, [https://forums.minecraftforge.net/topic/79049-javalangnosuchmethoderror-on-initialization/](https://forums.minecraftforge.net/topic/79049-javalangnosuchmethoderror-on-initialization/)  
54. intellij idea \- Why do i get a java.lang.NoSuchMethodError: even though the build is green?, acessado em junho 24, 2025, [https://stackoverflow.com/questions/13120694/why-do-i-get-a-java-lang-nosuchmethoderror-even-though-the-build-is-green](https://stackoverflow.com/questions/13120694/why-do-i-get-a-java-lang-nosuchmethoderror-even-though-the-build-is-green)  
55. How to ACTUALLY start developing my own mod? : r/feedthebeast \- Reddit, acessado em junho 24, 2025, [https://www.reddit.com/r/feedthebeast/comments/1jys24o/how\_to\_actually\_start\_developing\_my\_own\_mod/](https://www.reddit.com/r/feedthebeast/comments/1jys24o/how_to_actually_start_developing_my_own_mod/)  
56. Debug Profiler | NeoForged docs, acessado em junho 24, 2025, [https://docs.neoforged.net/docs/misc/debugprofiler/](https://docs.neoforged.net/docs/misc/debugprofiler/)  
57. The NeoForge Project \- Discord, acessado em junho 24, 2025, [https://discord.com/invite/neoforged](https://discord.com/invite/neoforged)  
58. Neo Forged : r/feedthebeast \- Reddit, acessado em junho 24, 2025, [https://www.reddit.com/r/feedthebeast/comments/14xybvk/neo\_forged/](https://www.reddit.com/r/feedthebeast/comments/14xybvk/neo_forged/)  
59. Some discord server for minecraft modding with forge? : r/feedthebeast \- Reddit, acessado em junho 24, 2025, [https://www.reddit.com/r/feedthebeast/comments/18538rc/some\_discord\_server\_for\_minecraft\_modding\_with/](https://www.reddit.com/r/feedthebeast/comments/18538rc/some_discord_server_for_minecraft_modding_with/)  
60. The NeoForged project, acessado em junho 24, 2025, [https://neoforged.net/](https://neoforged.net/)