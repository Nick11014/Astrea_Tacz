

# **Relatório de Consultoria Técnica: Migração de APIs de Serialização do Forge 1.20.1 para o NeoForge 1.21.1**

## **Seção 1: A Mudança de Paradigma Arquitetural: Do Forge 1.20.1 ao NeoForge 1.21.1**

### **1.1. Introdução: Contextualizando a Migração**

A transição de um mod como o TacZ do ecossistema Forge 1.20.1 para o NeoForge 1.21.1 representa mais do que uma simples atualização de versão. Trata-se de uma migração para um novo mod loader que, embora compartilhe uma herança comum com o Forge, divergiu significativamente em sua filosofia de design e implementação de APIs para versões posteriores à 1.20.1.1 Esta migração deve ser encarada como uma re-plataforma estratégica, alinhando a base de código do TacZ com os paradigmas modernos de desenvolvimento de mods, que priorizam a robustez, a segurança de tipos (type safety) e um alinhamento mais próximo com as APIs do Minecraft vanilla.2

Este relatório serve como um guia técnico exaustivo para essa transição. Ele disseca as duas revoluções de API mais impactantes para um mod complexo como o TacZ: a forma como os dados são armazenados em itens e a maneira como a comunicação de rede é gerenciada. O foco será na substituição do sistema legado de NBT (Named Binary Tag) pelo novo framework de DataComponents e na modernização da pilha de rede, abandonando o SimpleChannel do Forge em favor do sistema unificado de CustomPacketPayload do NeoForge. Ao final deste documento, a equipe de desenvolvimento do TacZ terá um roteiro claro e exemplos práticos para executar uma migração bem-sucedida, resultando em um mod mais estável, manutenível e preparado para o futuro.

### **1.2. Mudanças Fundamentais no Vanilla e NeoForge**

Antes de mergulhar nas APIs de serialização, é crucial abordar mudanças fundamentais na fundação do jogo e do mod loader que afetam todos os mods.

#### **1.2.1. Despluralização de Registros e Tags**

Uma das mudanças mais imediatas e que quebram a compatibilidade foi introduzida pela própria Mojang. Para padronizar a estrutura de dados, os nomes de várias pastas dentro dos data packs e resource packs foram "despluralizados". Essa mudança afeta como o jogo localiza receitas, tabelas de loot, tags e outros recursos.4

A migração requer uma atualização em todos os caminhos de recursos. Uma operação de "localizar e substituir" em todo o projeto é a abordagem mais eficiente para corrigir isso 4:

* tags/items torna-se tags/item  
* tags/blocks torna-se tags/block  
* tags/entity\_types torna-se tags/entity\_type  
* tags/fluids torna-se tags/fluid  
* tags/game\_events torna-se tags/game\_event  
* recipes torna-se recipe  
* advancements torna-se advancement  
* structures torna-se structure  
* loot\_tables torna-se loot\_table

Embora pareça uma alteração menor, ignorá-la resultará em falhas no carregamento de praticamente todos os dados customizados do mod.

#### **1.2.2. O Novo Paradigma de Registro: RegistryHolder**

O NeoForge abandonou o uso de referências de registro preguiçosas, como $Supplier\<Item\>$, em favor de um sistema de RegistryHolder fortemente tipado. Essa é uma mudança arquitetural significativa que visa aumentar a segurança e a clareza do código.2

O sistema antigo, baseado em DeferredRegister, foi substituído por uma abordagem que utiliza detentores (holders) especializados, como RegistryItem\<T\> e RegistryBlock\<T\>. Os principais benefícios dessa nova arquitetura são 2:

1. **Segurança de Tipos (Type Safety):** Elimina a necessidade de chamadas a .get() e garante em tempo de compilação que os tipos corretos estão sendo utilizados.  
2. **Controle Explícito do Ciclo de Vida:** Oferece um controle mais granular sobre quando e como os objetos de registro são inicializados.  
3. **Inicialização Agnóstica ao Loader:** Desacopla o código de registro das especificidades do mod loader, melhorando a portabilidade teórica.

A classe de registro principal do TacZ, que provavelmente utiliza DeferredRegister\<Item\> e DeferredRegister\<Block\>, precisará ser fundamentalmente refatorada para adotar este novo padrão.

#### **1.2.3. Outras Mudanças Notáveis**

* **ResourceLocation:** O construtor público de ResourceLocation foi tornado privado. O código deve agora usar os métodos de fábrica estáticos, como $ResourceLocation.parse(String)$ e $ResourceLocation.fromNameSpaceAndPath(String, String)$.5  
* **Primers de Migração:** A equipe do NeoForge fornece documentos de visão geral ("primers") para cada versão, que servem como um excelente ponto de partida para entender as mudanças de alto nível.6

As mudanças no NeoForge 1.21.1, em conjunto com as atualizações do vanilla, indicam uma forte tendência em direção à formalização e estruturação. Convenções implícitas e "mágicas", como nomes de pastas no plural, estão sendo sistematicamente substituídas por sistemas explícitos, declarativos e seguros em tipo. A Mojang está corrigindo inconsistências históricas 5, enquanto o NeoForge aproveita essa base para construir APIs mais robustas e menos propensas a erros de tempo de execução.2 A migração para o NeoForge 1.21.1, portanto, não é apenas uma atualização técnica; é a adoção de uma filosofia de desenvolvimento mais rigorosa. Para o TacZ, isso representa uma oportunidade de modernizar sua arquitetura de software, alinhando-a com a direção futura do ecossistema de modding e garantindo sua relevância e estabilidade a longo prazo.

## **Seção 2: A Revolução na Persistência de Dados: Migrando de NBT para Data Components**

A mudança mais profunda e estrutural na transição para a versão 1.21.1 é o abandono do armazenamento de dados customizados em ItemStacks através de CompoundTags (NBT) em favor do sistema de DataComponents do vanilla.

### **2.1. Análise do Método Legado no TacZ (Forge 1.20.1)**

No Forge 1.20.1, a prática padrão para armazenar dados em um item, como a contagem de munição ou os acessórios de uma arma, envolvia o seguinte padrão 8:

1. Obter o CompoundTag do ItemStack usando $stack.getOrCreateTag()$.  
2. Adicionar ou modificar dados nesse CompoundTag usando chaves de string (e.g., $tag.putInt("ammo\_count", 30)$).

Esta abordagem, embora funcional, apresentava várias desvantagens significativas 9:

* **Falta de Estrutura:** Não havia um esquema definido. Qualquer parte do código podia escrever qualquer dado com qualquer chave, levando a um código frágil e difícil de manter.  
* **Risco de Colisão:** Múltiplos mods poderiam, acidentalmente, usar a mesma chave de NBT, resultando em corrupção de dados.  
* **Ineficiência:** Frequentemente, todo o CompoundTag precisava ser serializado e enviado pela rede, mesmo que apenas um pequeno valor tivesse mudado.

### **2.2. A Arquitetura Moderna: O Ecossistema de DataComponent**

O Minecraft 1.21 introduziu os DataComponents como a solução oficial para esses problemas. Eles fornecem um sistema estruturado, seguro e eficiente para anexar dados a ItemStacks.9 A filosofia central é separar os dados padrão de um item (definidos no objeto

Item) dos dados específicos de uma pilha (armazenados no ItemStack).10

#### **2.2.1. Criando DataComponentTypes Customizados**

Para usar o sistema, o primeiro passo é definir os "tipos" de dados que o mod irá armazenar. Isso é feito criando e registrando um DataComponentType.12

1. **Definição do Objeto de Dados:** É altamente recomendável usar um record Java para definir a estrutura dos seus dados. Records são imutáveis por padrão e fornecem implementações automáticas de equals() e hashCode(), que são requisitos do sistema de componentes.12  
2. **Construção do DataComponentType:** Usa-se o DataComponentType.builder() para configurar o componente. Duas configurações são cruciais 12:  
   * $.persistent(Codec\<T\> codec)$: Fornece um Codec que define como o dado é salvo no disco (persistência).  
   * $.networkSynchronized(StreamCodec\<B, T\> streamCodec)$: Fornece um StreamCodec que define como o dado é enviado pela rede. Se um componente não precisa ser sincronizado, um StreamCodec.unit(defaultValue) deve ser fornecido.  
3. **Registro:** DataComponentTypes são objetos de registro. Eles devem ser registrados no registro BuiltInRegistries.DATA\_COMPONENT\_TYPE usando, por exemplo, um DeferredRegister\<DataComponentType\<?\>\>.12

#### **2.2.2. Manipulando DataComponents em ItemStacks**

Uma vez que os DataComponentTypes estão registrados, a interação com os dados no ItemStack é simplificada e segura 12:

**Tabela 2.1: Tabela Comparativa de APIs de Armazenamento de Dados em ItemStack**

| Operação | Método Legado (Forge 1.20.1) | Método Moderno (NeoForge 1.21.1) |
| :---- | :---- | :---- |
| **Leitura de Dado** | int ammo \= stack.getOrCreateTag().getInt("ammo\_count"); | int ammo \= stack.getOrDefault(TaczComponents.AMMO\_COUNT.get(), 0); |
| **Escrita de Dado** | stack.getOrCreateTag().putInt("ammo\_count", newAmmo); | stack.set(TaczComponents.AMMO\_COUNT.get(), newAmmo); |
| **Verificação de Dado** | boolean hasAmmo \= stack.hasTag() && stack.getTag().contains("ammo\_count"); | boolean hasAmmo \= stack.has(TaczComponents.AMMO\_COUNT.get()); |
| **Remoção de Dado** | stack.getTag().remove("ammo\_count"); | stack.remove(TaczComponents.AMMO\_COUNT.get()); |

### **2.3. Estratégia de Migração para os Dados de Itens do TacZ**

Para o TacZ, a migração envolve identificar todos os dados atualmente armazenados no NBT das armas e mapeá-los para DataComponents granulares. Isso não apenas moderniza o código, mas também o organiza de forma mais lógica.

A tabela a seguir propõe um mapeamento inicial para os dados de uma arma do TacZ. Ela serve como um plano de ação concreto para a refatoração.

**Tabela 2.2: Tabela de Mapeamento de DataComponent para TacZ**

| Chave NBT Legada (Exemplo) | Descrição do Dado | DataComponentType Proposto (com record) | Persistente? (Codec) | Sincronizado? (StreamCodec) |
| :---- | :---- | :---- | :---- | :---- |
| ammo\_count | Munição atual no carregador | TACZ\_COMPONENTS.AMMO\_COUNT (Integer) | Sim | Sim |
| ammo\_type | Tipo de munição carregada | TACZ\_COMPONENTS.AMMO\_TYPE (ResourceLocation) | Sim | Sim |
| gun\_stats\_modifiers | Modificadores de estatísticas (e.g., de desgaste) | TACZ\_COMPONENTS.STAT\_MODIFIERS (StatModifiersRecord) | Sim | Não (recalculado no cliente se necessário) |
| attachments | Lista de acessórios equipados | TACZ\_COMPONENTS.ATTACHMENTS (AttachmentListRecord) | Sim | Sim |
| skin\_id | Identificador da skin aplicada | TACZ\_COMPONENTS.SKIN (ResourceLocation) | Sim | Sim |

Essa abordagem granular força uma separação de preocupações muito mais limpa. O DataComponentType define o *esquema* dos dados (incluindo sua serialização), o record define a *estrutura* imutável, e a lógica do jogo apenas *consome* esses dados de forma segura. Isso contrasta com o modelo NBT, onde esquema, estrutura e lógica estavam frequentemente misturados e podiam ser alterados de forma insegura em qualquer lugar do código.8 O resultado é uma arquitetura de dados mais robusta, auto-documentada e modular.

### **2.4. Garantindo a Compatibilidade de Mundos: A Necessidade Crítica dos DataFixers**

Uma migração de código bem-sucedida é inútil se os dados dos jogadores forem perdidos. ItemStacks salvos em mundos de 1.20.1 conterão os dados do TacZ no formato NBT legado. Ao carregar esse mundo na versão 1.21.1, o jogo não terá como converter automaticamente esses dados para o novo sistema de DataComponents.9

A solução para este problema é o framework DataFixerUpper (DFU) do Minecraft. É imperativo que o TacZ implemente um Fix customizado. A estratégia geral para este Fix é:

1. Identificar ItemStacks que pertencem ao TacZ.  
2. Verificar a presença do CompoundTag legado (e.g., stack.tag.tacz\_data).  
3. Ler os valores do NBT antigo.  
4. Escrever esses valores nos novos DataComponents correspondentes.  
5. Remover o CompoundTag legado do ItemStack.

A falha em implementar um DataFixer resultará na perda de todas as armas e progresso dos jogadores ao atualizarem seus mundos, tornando esta etapa a mais crítica para a retenção de usuários. Além disso, a padronização via DataComponents melhora drasticamente a interoperabilidade entre mods. Outros mods agora podem verificar de forma segura e padronizada se um item do TacZ possui um determinado componente (e.g., $stack.has(TaczComponents.AMMO\_COUNT.get())$) sem precisar conhecer os detalhes internos da estrutura NBT do TacZ. Isso abre portas para integrações mais profundas e estáveis, como mods de HUD ou sistemas de armazenamento que podem "entender" e exibir o estado das armas do TacZ.

## **Seção 3: Modernizando a Comunicação de Rede: De SimpleChannel para CustomPacketPayload**

Paralelamente à revolução no armazenamento de dados, o NeoForge 1.21.1 introduz uma refatoração completa do sistema de rede, unificando-o em torno da API de CustomPacketPayload do vanilla.

### **3.1. Análise da Pilha de Rede do TacZ em 1.20.1**

No Forge 1.20.1, o sistema de rede, embora poderoso, era verboso. A implementação típica no TacZ provavelmente segue estes passos 8:

1. **Inicialização do Canal:** Criação de uma instância de SimpleChannel usando $NetworkRegistry.newSimpleChannel(...)$.  
2. **Registro de Mensagens:** Para cada tipo de pacote, uma chamada a $INSTANCE.registerMessage(...) é feita, associando uma classe de pacote a um handler, um encoder e um decoder.  
3. **Implementação de Pacotes:** Cada pacote é uma classe separada com campos para os dados, um construtor que recebe os dados, um construtor que lê de um FriendlyByteBuf, e um método para escrever em um FriendlyByteBuf.  
4. **Implementação de Handlers:** O handler recebe um $Supplier\<NetworkEvent.Context\>$ e deve usar $ctx.get().enqueueWork(...) para garantir que o código que interage com o mundo do jogo seja executado na thread principal.

### **3.2. O Framework de Rede do NeoForge 1.21.1**

O NeoForge adota o sistema de CustomPacketPayload do vanilla, que é mais limpo, mais declarativo e se integra perfeitamente com o StreamCodec.3

**Tabela 3.1: Tabela Comparativa de APIs de Rede**

| Aspecto | Sistema Legado (Forge 1.20.1 SimpleChannel) | Sistema Moderno (NeoForge 1.21.1 CustomPacketPayload) |
| :---- | :---- | :---- |
| **Definição do Pacote** | Classe Java completa com múltiplos construtores e métodos de encode/decode. | record Java simples implementando CustomPacketPayload. |
| **Serialização** | Métodos encode e decode manuais dentro da classe do pacote. | Um StreamCodec reutilizável, definido externamente. |
| **Registro** | $SimpleChannel.registerMessage(...) para cada pacote. | Registro centralizado no evento RegisterPayloadHandlersEvent usando um PayloadRegistrar. |
| **Handler** | Recebe $Supplier\<NetworkEvent.Context\>$. O pacote precisa ser decodificado manualmente. | Recebe o payload já decodificado e um PlayPayloadContext. |
| **Threading** | $ctx.get().enqueueWork(...) | $context.workHandler().submitAsync(...), retornando um CompletableFuture. |

O novo fluxo é o seguinte 16:

1. **Definir o Payload:** Crie um record que implemente CustomPacketPayload. Este record contém apenas os dados a serem enviados. Ele deve fornecer um Type\<T\> estático e único, que contém seu ResourceLocation.  
2. **Definir o StreamCodec:** Crie um StreamCodec para o seu record. Este codec será responsável por ler e escrever o payload de/para o buffer.  
3. **Registrar o Handler:** No evento RegisterPayloadHandlersEvent, use o PayloadRegistrar para registrar seu payload. Você fornecerá o Type, o StreamCodec e o método handler.  
4. **Implementar o Handler:** O método handler recebe o record do payload (já decodificado) e o contexto. O trabalho na thread principal é agendado com $context.workHandler().submitAsync(...).

### **3.3. Refatorando os Pacotes de Rede do TacZ**

A migração da camada de rede do TacZ envolve a conversão de suas classes de pacotes legadas para o novo padrão de record e StreamCodec.

**Tabela 3.2: Tabela de Mapeamento de Pacotes para TacZ**

| Classe de Pacote Legada (1.20.1) | Propósito | CustomPacketPayload Proposto (1.21.1 record) | Direção |
| :---- | :---- | :---- | :---- |
| ClientboundGunSoundPacket | Tocar som de tiro no cliente | GunSoundPayload(Holder\<SoundEvent\>, Vec3) | Servidor \-\> Cliente |
| ServerboundReloadPacket | Informar ao servidor que o jogador está recarregando | PlayerReloadPayload(EquipmentSlot) | Cliente \-\> Servidor |
| ClientboundSyncAttachmentsPacket | Sincronizar os acessórios de uma arma para outros clientes | SyncAttachmentsPayload(int entityId, AttachmentListRecord attachments) | Servidor \-\> Cliente |

A beleza deste novo sistema reside na **unificação e reutilização**. O StreamCodec criado para serializar um DataComponent (como AttachmentListRecord da Seção 2\) para a rede é exatamente o mesmo StreamCodec que será usado para serializar o CustomPacketPayload (SyncAttachmentsPayload) que transporta esses mesmos dados. Em vez de escrever lógicas de serialização duplicadas e propensas a erros, o desenvolvedor escreve um StreamCodec uma única vez e o reutiliza em múltiplos contextos. Isso reduz drasticamente a complexidade, elimina a duplicação de código e garante que a serialização seja consistente em todo o mod.

Adotar o CustomPacketPayload também alinha o TacZ com a forma como o próprio Minecraft e outros mod loaders, como o Fabric, lidam com a rede.3 Isso torna o mod mais previsível para ferramentas de análise de rede e, crucialmente, melhora a compatibilidade com camadas de interoperabilidade como o Sinytra Connector, aumentando o potencial do TacZ para funcionar em ambientes multi-loader no futuro.

## **Seção 4: O Serializador Unificador: Um Mergulho Profundo no StreamCodec**

O StreamCodec é a espinha dorsal dos novos sistemas de serialização de dados e de rede. Dominar seu uso é fundamental para uma migração bem-sucedida e para o desenvolvimento eficiente no ecossistema NeoForge.

### **4.1. Conceitos Fundamentais e Construção**

Um StreamCodec é uma ferramenta de serialização bidirecional que descreve como um objeto deve ser escrito e lido a partir de um stream de bytes, como um buffer de rede (ByteBuf).18 Ele é conceitualmente análogo ao

Codec, que lida com a serialização para estruturas de dados em memória (como NBT ou JSON).

O método mais comum e poderoso para criar um StreamCodec para um objeto customizado é $StreamCodec.composite(...).18 Essa abordagem é declarativa: em vez de escrever bytes imperativamente, o desenvolvedor declara a estrutura do objeto.

Por exemplo, para um record GunStatsRecord(float damage, int fireRate, float spread):

Java

public static final StreamCodec\<ByteBuf, GunStatsRecord\> STREAM\_CODEC \= StreamCodec.composite(  
    ByteBufCodecs.FLOAT, GunStatsRecord::damage,  
    ByteBufCodecs.VAR\_INT, GunStatsRecord::fireRate,  
    ByteBufCodecs.FLOAT, GunStatsRecord::spread,  
    GunStatsRecord::new  
);

Neste exemplo, estamos declarando que um GunStatsRecord é composto por um float, um int (codificado com tamanho variável para eficiência) e outro float. O StreamCodec cuidará da escrita e leitura na ordem correta, usando os codecs fornecidos (ByteBufCodecs.FLOAT, ByteBufCodecs.VAR\_INT) e, ao final da leitura, chamará o construtor GunStatsRecord::new com os valores decodificados. Essa abordagem é mais segura, mais legível e menos propensa a erros do que a manipulação manual de buffer.

### **4.2. O Contexto do Buffer: ByteBuf vs. FriendlyByteBuf vs. RegistryFriendlyByteBuf**

A escolha do tipo de buffer no genérico do StreamCodec ($StreamCodec\<B, V\>$) é crucial e depende dos dados que estão sendo serializados.18

1. **io.netty.buffer.ByteBuf**: O buffer base do Netty. Deve ser usado para dados genéricos que não dependem de nenhuma funcionalidade do Minecraft (e.g., primitivos, strings, listas de primitivos).  
2. **net.minecraft.network.FriendlyByteBuf**: Estende ByteBuf e adiciona métodos de conveniência para serializar tipos comuns do Minecraft que não dependem de registros, como BlockPos, Vec3, UUID, etc..20  
3. **net.minecraft.network.RegistryFriendlyByteBuf**: Estende FriendlyByteBuf e é essencial para serializar qualquer objeto que precise de um contexto de registro para ser convertido em um ID numérico e vice-versa. Isso inclui Item, Block, SoundEvent, Enchantment e, mais importante, Holder\<T\>.18 Usar um  
   ByteBuf ou FriendlyByteBuf ao tentar serializar um Item resultará em um erro, pois o codec não terá acesso ao registro de itens para mapear o item a seu ID de rede.

A regra geral é sempre usar o tipo de buffer menos específico possível, mas garantir que ele satisfaça as necessidades de todos os seus codecs compostos.

### **4.3. Aplicação Prática no TacZ: Criando um StreamCodec Reutilizável**

Vamos solidificar o conceito com um exemplo prático para o TacZ. Suponha que tenhamos um DataComponent para os acessórios de uma arma e um pacote de rede para sincronizar esses acessórios.

1. **Definir o record e o StreamCodec:**  
   Java  
   // Em uma classe TaczData.java  
   public record AttachmentData(ResourceLocation sight, ResourceLocation barrel, ResourceLocation stock) {  
       public static final StreamCodec\<ByteBuf, AttachmentData\> STREAM\_CODEC \= StreamCodec.composite(  
           ResourceLocation.STREAM\_CODEC, AttachmentData::sight,  
           ResourceLocation.STREAM\_CODEC, AttachmentData::barrel,  
           ResourceLocation.STREAM\_CODEC, AttachmentData::stock,  
           AttachmentData::new  
       );  
       //... Codec para persistência também seria definido aqui  
   }

2. **Usar o StreamCodec no DataComponentType:**  
   Java  
   // Em uma classe TaczComponents.java  
   public static final Supplier\<DataComponentType\<AttachmentData\>\> ATTACHMENTS \= DATA\_COMPONENTS.register("attachments", () \-\>  
       DataComponentType.\<AttachmentData\>builder()  
          .persistent(AttachmentData.CODEC) // Assumindo que CODEC existe  
          .networkSynchronized(AttachmentData.STREAM\_CODEC) // Reutilizando o codec\!  
          .build()  
   );

3. **Usar o mesmo StreamCodec no CustomPacketPayload:**  
   Java  
   // Em uma classe TaczPackets.java  
   public record SyncAttachmentsPayload(int entityId, AttachmentData attachments) implements CustomPacketPayload {  
       public static final Type\<SyncAttachmentsPayload\> TYPE \= new Type\<\>(new ResourceLocation("tacz", "sync\_attachments"));  
       public static final StreamCodec\<ByteBuf, SyncAttachmentsPayload\> STREAM\_CODEC \= StreamCodec.composite(  
           ByteBufCodecs.VAR\_INT, SyncAttachmentsPayload::entityId,  
           AttachmentData.STREAM\_CODEC, SyncAttachmentsPayload::attachments, // Reutilizando o codec aninhado\!  
           SyncAttachmentsPayload::new  
       );  
       //...  
   }

Este exemplo demonstra o poder do novo sistema. A lógica de serialização para AttachmentData é definida uma vez e reutilizada em todos os lugares, garantindo consistência e reduzindo a verbosidade. O conhecimento investido para dominar StreamCodec e Codec é altamente transferível, pois eles formam a base da serialização em todo o Minecraft moderno, incluindo receitas 22, ingredientes customizados 23,

DataComponents 12, pacotes de rede 17 e dados de entidades.24

## **Seção 5: Implementações de Referência e Melhores Práticas**

### **5.1. Nota sobre a Referência Solicitada (SuperbWarfare)**

A consulta original solicitava o uso do mod SuperbWarfare como uma implementação de referência para o NeoForge 1.21.1. Durante a fase de pesquisa, foi determinado que o repositório público para a versão 1.21 deste mod estava inacessível.25 Consequentemente, não foi possível realizar uma análise direta de sua base de código.

Para contornar essa limitação e ainda fornecer um guia de alta qualidade, este relatório adota uma abordagem alternativa: a utilização de exemplos canônicos e padrões de melhores práticas derivados diretamente da documentação oficial do NeoForge e de tutoriais de referência da comunidade, como os produzidos por Kaupenjoe.27 Esses recursos são auditados pela comunidade e representam o "padrão-ouro" para a implementação das novas APIs, garantindo que as recomendações aqui contidas sejam precisas, robustas e alinhadas com as intenções dos desenvolvedores do mod loader.

### **5.2. Padrões de Implementação Canônicos**

A seguir, são apresentados exemplos de código completos e comentados que demonstram a implementação correta dos novos sistemas, servindo como um modelo para a refatoração do TacZ.

#### **5.2.1. Registro de um DataComponent Completo**

Este exemplo mostra uma classe ModDataComponents que registra um componente customizado para armazenar um valor inteiro simples, incluindo seu record, Codec e StreamCodec.

Java

// Em com/tacz/guns/data/ModDataComponents.java  
public class ModDataComponents {  
    public static final DeferredRegister\<DataComponentType\<?\>\> DATA\_COMPONENTS \=  
            DeferredRegister.create(BuiltInRegistries.DATA\_COMPONENT\_TYPE, TacZ.MOD\_ID);

    // Exemplo: Componente para contagem de munição  
    public static final Supplier\<DataComponentType\<Integer\>\> AMMO\_COUNT \= DATA\_COMPONENTS.register("ammo\_count", () \-\>  
            DataComponentType.\<Integer\>builder()  
                   .persistent(Codec.INT) // Para salvar no disco  
                   .networkSynchronized(ByteBufCodecs.VAR\_INT) // Para enviar pela rede  
                   .build()  
    );

    // Exemplo: Componente para dados complexos de estatísticas  
    public record GunStats(float damage, float accuracy) {  
        // Codec para persistência (salvar no mundo)  
        public static final Codec\<GunStats\> CODEC \= RecordCodecBuilder.create(instance \-\> instance.group(  
                Codec.FLOAT.fieldOf("damage").forGetter(GunStats::damage),  
                Codec.FLOAT.fieldOf("accuracy").forGetter(GunStats::accuracy)  
        ).apply(instance, GunStats::new));

        // StreamCodec para sincronização de rede  
        public static final StreamCodec\<ByteBuf, GunStats\> STREAM\_CODEC \= StreamCodec.composite(  
                ByteBufCodecs.FLOAT, GunStats::damage,  
                ByteBufCodecs.FLOAT, GunStats::accuracy,  
                GunStats::new  
        );  
    }

    public static final Supplier\<DataComponentType\<GunStats\>\> GUN\_STATS \= DATA\_COMPONENTS.register("gun\_stats", () \-\>  
            DataComponentType.\<GunStats\>builder()  
                   .persistent(GunStats.CODEC)  
                   .networkSynchronized(GunStats.STREAM\_CODEC)  
                   .build()  
    );

    public static void register(IEventBus modEventBus) {  
        DATA\_COMPONENTS.register(modEventBus);  
    }  
}

#### **5.2.2. Registro de um CustomPacketPayload**

Esta classe ModPackets demonstra como registrar payloads de rede usando o RegisterPayloadHandlersEvent.

Java

// Em com/tacz/guns/network/ModPackets.java  
@EventBusSubscriber(modid \= TacZ.MOD\_ID, bus \= EventBusSubscriber.Bus.MOD)  
public class ModPackets {  
    // Payload para o jogador informar o servidor que está recarregando  
    public record ServerboundReloadPayload() implements CustomPacketPayload {  
        public static final Type\<ServerboundReloadPayload\> TYPE \= new Type\<\>(new ResourceLocation(TacZ.MOD\_ID, "reload"));  
        public static final StreamCodec\<ByteBuf, ServerboundReloadPayload\> STREAM\_CODEC \= StreamCodec.unit(new ServerboundReloadPayload());

        @Override  
        public Type\<? extends CustomPacketPayload\> type() {  
            return TYPE;  
        }  
    }

    @SubscribeEvent  
    public static void registerPayloadHandlers(final RegisterPayloadHandlersEvent event) {  
        final PayloadRegistrar registrar \= event.registrar(TacZ.MOD\_ID);

        // Registra um pacote C-\>S (Cliente para Servidor)  
        registrar.play(ServerboundReloadPayload.TYPE, ServerboundReloadPayload.STREAM\_CODEC,  
                (payload, context) \-\> {  
                    // O handler é executado na thread de rede.  
                    // Agende o trabalho para a thread principal do servidor.  
                    context.workHandler().submitAsync(() \-\> {  
                        ServerPlayer player \= (ServerPlayer) context.player().orElse(null);  
                        if (player\!= null) {  
                            // Lógica de recarga da arma aqui  
                        }  
                    });  
                }  
        );  
    }

    public static \<T extends CustomPacketPayload\> void sendToServer(T payload) {  
        PacketDistributor.sendToServer(payload);  
    }

    public static \<T extends CustomPacketPayload\> void sendToPlayer(T payload, ServerPlayer player) {  
        PacketDistributor.sendToPlayer(player, payload);  
    }  
}

#### **5.2.3. Receitas com DataComponents**

A manipulação de DataComponents em receitas é um tópico avançado. O NeoForge introduziu o DataComponentIngredient, que permite que uma receita exija não apenas um item, mas também que esse item tenha um DataComponent específico.23

No entanto, ao criar receitas cujo *resultado* deve ter DataComponents específicos, os construtores de receitas padrão (RecipeBuilder) podem ser insuficientes. A documentação oficial observa que builders como SingleItemRecipeBuilder (para stonecutting) e SmithingTransformRecipeBuilder não suportam a definição de ItemStacks de resultado com DataComponents complexos. Nesses casos, é necessário implementar um RecipeBuilder customizado que possa construir o ItemStack de resultado com os componentes desejados antes de serializar a receita para JSON.28 Isso garante que a lógica de criação de itens customizados seja preservada durante o processo de data generation.

## **Seção 6: Checklist de Migração Abrangente e Recomendações Finais**

### **6.1. Checklist de Ações de Migração para o TacZ**

Esta lista de verificação detalhada serve como um roteiro para o processo de migração do TacZ.

1. **Configuração do Projeto:**  
   * \[ \] Atualizar o build.gradle para usar as dependências e repositórios do NeoForge 1.21.1.  
   * \[ \] Executar as tarefas do Gradle para configurar o ambiente de desenvolvimento (genSources, etc.).  
   * \[ \] Atualizar o mods.toml para refletir as novas especificações do NeoForge.  
2. **Atualizações Fundamentais:**  
   * \[ \] Refatorar todas as classes de registro (ModItems, ModBlocks, etc.) para abandonar DeferredRegister com Supplier e adotar o padrão RegistryHolder (RegistryItem, RegistryBlock).2  
   * \[ \] Executar uma busca e substituição em todo o projeto para "despluralizar" os caminhos de recursos em assets e data (e.g., recipes \-\> recipe).4  
   * \[ \] Substituir todas as instâncias de new ResourceLocation(...) por chamadas aos métodos de fábrica estáticos (.parse(), .fromNameSpaceAndPath()).5  
3. **Migração de Dados de Itens (DataComponents):**  
   * \[ \] Criar a Tabela de Mapeamento de DataComponent para TacZ (conforme Seção 2.3).  
   * \[ \] Implementar uma classe TaczComponents para definir todos os DataComponentTypes, seus records, Codecs e StreamCodecs.  
   * \[ \] Registrar os DataComponentTypes no barramento de eventos do mod.  
   * \[ \] Refatorar sistematicamente todo o código que usa ItemStack.getOrCreateTag(), .getTag(), .setTag() para usar os novos métodos: stack.get(), stack.set(), stack.has(), stack.remove().  
   * \[ \] **(Crítico)** Projetar e implementar um DataFixer para migrar os dados NBT de ItemStacks de mundos antigos para o novo formato de DataComponent.  
4. **Migração da Rede (CustomPacketPayload):**  
   * \[ \] Criar a Tabela de Mapeamento de Pacotes para TacZ (conforme Seção 3.3).  
   * \[ \] Converter todas as classes de pacotes legadas em records Java que implementam CustomPacketPayload.  
   * \[ \] Implementar os StreamCodecs necessários para cada payload, reutilizando os codecs dos DataComponents sempre que possível.  
   * \[ \] Criar uma classe TaczPackets para registrar todos os handlers de payload no evento RegisterPayloadHandlersEvent.  
   * \[ \] Refatorar todos os handlers de pacotes para a nova assinatura, usando context.workHandler().submitAsync() para o trabalho na thread principal.  
   * \[ \] Atualizar todos os locais que enviam pacotes para usar PacketDistributor.sendToServer(...) ou PacketDistributor.sendToPlayer(...).  
5. **Testes:**  
   * \[ \] Testar a criação e o uso de todas as armas e itens do TacZ em um mundo novo.  
   * \[ \] Testar a carga de um mundo antigo (1.20.1) e verificar se o DataFixer migrou corretamente os dados dos itens existentes.  
   * \[ \] Testar exaustivamente todas as interações de rede (tiro, recarga, sincronização de estado) em um ambiente de servidor dedicado.

### **6.2. Análise de Armadilhas e Desafios Comuns**

* **Imutabilidade:** A mudança para DataComponents e ItemStacks efetivamente imutáveis é uma mudança de mentalidade. Modificar um componente obtido de um ItemStack e não o "salvar" de volta com stack.set(...) é um erro comum que fará com que as alterações sejam descartadas silenciosamente.12  
* **Contexto de Registro:** Tentar serializar um Item ou Holder\<T\> com um StreamCodec que usa ByteBuf ou FriendlyByteBuf em vez de RegistryFriendlyByteBuf levará a falhas. É crucial entender o contexto do buffer.  
* **Complexidade dos DataFixers:** Escrever DataFixers é uma tarefa notoriamente complexa e pouco documentada. É recomendável começar com exemplos simples e testar cada etapa do processo de migração de dados de forma isolada.  
* **Burnout do Desenvolvedor:** A escala dessas mudanças é substancial e pode ser esmagadora. A migração de um mod complexo como o TacZ é um esforço significativo.9 É aconselhável adotar uma abordagem incremental, refatorando um sistema de cada vez (e.g., primeiro os  
  DataComponents, depois a rede) e testando continuamente.

### **6.3. Recomendações Finais**

A migração do TacZ para o NeoForge 1.21.1 é uma tarefa desafiadora, mas essencial para a saúde e longevidade do projeto. As mudanças de API, embora radicais, representam um avanço significativo em direção a um desenvolvimento de mods mais seguro, estruturado e eficiente.

Ao abraçar os DataComponents, o TacZ ganhará uma arquitetura de dados robusta e interoperável. Ao adotar CustomPacketPayload e StreamCodec, sua camada de rede se tornará mais limpa, menos verbosa e mais alinhada com os padrões modernos. Os benefícios a longo prazo em termos de manutenibilidade, desempenho e estabilidade superarão em muito o custo inicial da migração.

Recomenda-se que a equipe de desenvolvimento utilize este relatório como um guia passo a passo, siga o checklist de migração rigorosamente e não hesite em se engajar com a comunidade NeoForge, por exemplo, através de seu servidor no Discord 6, para obter suporte em desafios específicos. A transição é uma jornada, mas o resultado será um mod mais forte, preparado para prosperar no futuro do modding de Minecraft.

#### **Referências citadas**

1. I'm out of the loop, what is the difference between Neoforge and Fabric? (1.20.1) \- Reddit, acessado em julho 7, 2025, [https://www.reddit.com/r/feedthebeast/comments/1gtt3q6/im\_out\_of\_the\_loop\_what\_is\_the\_difference\_between/](https://www.reddit.com/r/feedthebeast/comments/1gtt3q6/im_out_of_the_loop_what_is_the_difference_between/)  
2. \[NeoForge 1.21\] v2.1 \- XunLib \- Modrinth, acessado em julho 7, 2025, [https://modrinth.com/mod/xunlib/version/i3kvC5ji](https://modrinth.com/mod/xunlib/version/i3kvC5ji)  
3. The Networking Refactor \- NeoForge, acessado em julho 7, 2025, [https://neoforged.net/news/20.4networking-rework/](https://neoforged.net/news/20.4networking-rework/)  
4. 1.21 Migration Guide \- CraftTweaker Documentation, acessado em julho 7, 2025, [https://docs.blamejared.com/1.21/en/1\_21\_migration\_guide/](https://docs.blamejared.com/1.21/en/1_21_migration_guide/)  
5. Minecraft 1.20.5/6 \-\> 1.21 Mod Migration Primer · GitHub, acessado em julho 7, 2025, [https://gist.github.com/ChampionAsh5357/d895a7b1a34341e19c80870720f9880f](https://gist.github.com/ChampionAsh5357/d895a7b1a34341e19c80870720f9880f)  
6. Minecraft 1.21 \-\> 1.21.1 Mod Migration Primer \- GitHub, acessado em julho 7, 2025, [https://github.com/neoforged/.github/blob/main/primers/1.21.1/index.md](https://github.com/neoforged/.github/blob/main/primers/1.21.1/index.md)  
7. NeoForge Modding Tutorial \- Minecraft 1.21.1: Update to 1.21.3 | \#61 \- YouTube, acessado em julho 7, 2025, [https://www.youtube.com/watch?v=tz0WOnXZ2pw](https://www.youtube.com/watch?v=tz0WOnXZ2pw)  
8. acessado em dezembro 31, 1969, [https://github.com/MCModderAnchor/TACZ/tree/1.20.1/src/main/java/com/tacz/guns](https://github.com/MCModderAnchor/TACZ/tree/1.20.1/src/main/java/com/tacz/guns)  
9. How will the new tag system effect mods going forward? : r/feedthebeast \- Reddit, acessado em julho 7, 2025, [https://www.reddit.com/r/feedthebeast/comments/1b2iws0/how\_will\_the\_new\_tag\_system\_effect\_mods\_going/](https://www.reddit.com/r/feedthebeast/comments/1b2iws0/how_will_the_new_tag_system_effect_mods_going/)  
10. Items | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/items/](https://docs.neoforged.net/docs/items/)  
11. Items | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/1.21.1/items/](https://docs.neoforged.net/docs/1.21.1/items/)  
12. Data Components | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/1.21.1/items/datacomponents](https://docs.neoforged.net/docs/1.21.1/items/datacomponents)  
13. Enchantments | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/1.21.1/resources/server/enchantments/](https://docs.neoforged.net/docs/1.21.1/resources/server/enchantments/)  
14. NBT data not transferring : r/feedthebeast \- Reddit, acessado em julho 7, 2025, [https://www.reddit.com/r/feedthebeast/comments/1ihsrrd/nbt\_data\_not\_transferring/](https://www.reddit.com/r/feedthebeast/comments/1ihsrrd/nbt_data_not_transferring/)  
15. Issues when migrating from Forge 1.20.1 to NeoForge 1.21.1 : r/admincraft \- Reddit, acessado em julho 7, 2025, [https://www.reddit.com/r/admincraft/comments/1j6mmpc/issues\_when\_migrating\_from\_forge\_1201\_to\_neoforge/](https://www.reddit.com/r/admincraft/comments/1j6mmpc/issues_when_migrating_from_forge_1201_to_neoforge/)  
16. Registering Payloads | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/1.20.4/networking/payload/](https://docs.neoforged.net/docs/1.20.4/networking/payload/)  
17. Registering Payloads | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/networking/payload/](https://docs.neoforged.net/docs/networking/payload/)  
18. Stream Codecs | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/networking/streamcodecs/](https://docs.neoforged.net/docs/networking/streamcodecs/)  
19. StreamCodec (neoforge 1.20.6-20.6.119) \- nekoyue.github.io, acessado em julho 7, 2025, [https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.20.6-neoforge/net/minecraft/network/codec/StreamCodec.html](https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.20.6-neoforge/net/minecraft/network/codec/StreamCodec.html)  
20. FriendlyByteBuf (neoforge 1.21.0-21.0.30-beta) \- nekoyue.github.io, acessado em julho 7, 2025, [https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.21.x-neoforge/net/minecraft/network/FriendlyByteBuf.html](https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.21.x-neoforge/net/minecraft/network/FriendlyByteBuf.html)  
21. NeoForge 20.5 for Minecraft 1.20.5, acessado em julho 7, 2025, [https://neoforged.net/news/20.5release/](https://neoforged.net/news/20.5release/)  
22. Recipes | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/1.21.1/resources/server/recipes/\#data-generation-for-custom-recipes](https://docs.neoforged.net/docs/1.21.1/resources/server/recipes/#data-generation-for-custom-recipes)  
23. Ingredients | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/resources/server/recipes/ingredients/](https://docs.neoforged.net/docs/resources/server/recipes/ingredients/)  
24. Data and Networking | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/entities/data/](https://docs.neoforged.net/docs/entities/data/)  
25. acessado em dezembro 31, 1969, [https://github.com/Mercurows/SuperbWarfare/tree/1.21](https://github.com/Mercurows/SuperbWarfare/tree/1.21)  
26. acessado em dezembro 31, 1969, [https://github.com/Mercurows/SuperbWarfare/tree/1.21/src/main/java/com/mercurows/superbwarfare](https://github.com/Mercurows/SuperbWarfare/tree/1.21/src/main/java/com/mercurows/superbwarfare)  
27. NeoForge Modding Tutorial \- Minecraft 1.21: Data Components Explained | \#14 \- YouTube, acessado em julho 7, 2025, [https://www.youtube.com/watch?v=du6O6zEQwc4](https://www.youtube.com/watch?v=du6O6zEQwc4)  
28. Built-In Recipe Types | NeoForged docs, acessado em julho 7, 2025, [https://docs.neoforged.net/docs/1.21.1/resources/server/recipes/builtin/](https://docs.neoforged.net/docs/1.21.1/resources/server/recipes/builtin/)