

# **Guia Técnico Abrangente para a Migração de Mods: De Forge 1.20.1 para NeoForge 1.21.1**

## **Introdução: O Contexto da Migração**

### **Propósito e Escopo**

Este relatório define o seu objetivo como um guia técnico completo e exaustivo para desenvolvedores que estão a migrar um mod da plataforma Minecraft Forge 1.20.1 para a plataforma NeoForge 1.21.1. O seu escopo abrange todas as facetas da migração, desde a configuração inicial do projeto e do sistema de compilação até a adaptação detalhada do código-fonte para as novas arquiteturas de API e as mudanças disruptivas do Minecraft vanilla. O objetivo final é capacitar o desenvolvedor a navegar por este processo complexo, resolver todos os erros de compilação e de tempo de execução, e produzir um mod totalmente funcional e compatível com o ecossistema NeoForge 1.21.1.

### **O "Porquê": Compreendendo a Bifurcação e o Salto de Versão**

Para realizar uma migração bem-sucedida, é fundamental compreender o contexto histórico e técnico que a motiva. O NeoForge não é meramente uma atualização incremental; é uma bifurcação (fork) do projeto MinecraftForge. Esta separação foi iniciada em meados de 2023 pela esmagadora maioria da equipa de desenvolvimento original do Forge, com o objetivo de se afastar de uma estrutura de projeto e de uma dívida técnica que consideravam restritivas.1

A filosofia do NeoForge é criar uma API de modding mais moderna, ágil e orientada para a comunidade, que se alinha mais estreitamente com a direção da modernização do próprio código do Minecraft vanilla.1 Esta abordagem proativa significa que o NeoForge abraça as mudanças do vanilla, em vez de as contornar, resultando numa base de código mais limpa e numa experiência de desenvolvimento potencialmente superior a longo prazo.

### **O Desafio Duplo da Migração**

A conversão de um mod de Forge 1.20.1 para NeoForge 1.21.1 apresenta um desafio duplo, composto por dois conjuntos distintos, embora interligados, de mudanças disruptivas. Uma migração bem-sucedida exige que ambos os conjuntos sejam abordados metodicamente.

1. **Mudanças Arquitetónicas do NeoForge:** Estas são as mudanças resultantes diretamente da bifurcação. Incluem a renomeação fundamental do pacote de $net.minecraftforge$ para $net.neoforged$, bem como as ambiciosas e profundas reformulações de sistemas fundamentais como Registos (Registries), Eventos (Events) e Capacidades (Capabilities). Estas reformulações foram realizadas para pagar dívidas técnicas de longa data e para modernizar a API.6  
2. **Evolução da API do Minecraft Vanilla:** Estas são as mudanças disruptivas significativas introduzidas pela Mojang no jogo base entre as versões 1.20.1 e 1.21.1. Mudanças como a reescrita completa do pipeline de renderização, a grande mudança para componentes de dados (data components) e a refatoração do sistema de receitas afetam todos os mod loaders e são independentes da bifurcação do NeoForge.8

### **Uma Nota Crítica sobre Compatibilidade**

É imperativo declarar explicitamente que este é um processo de migração unidirecional. Um mod que seja portado com sucesso para NeoForge 1.21.1 será fundamental e irrevogavelmente incompatível com todas as versões do Forge, tanto passadas como presentes. A divergência técnica e estrutural entre as duas plataformas é agora demasiado grande para suportar a compatibilidade cruzada sem a implementação de camadas de abstração significativas, que estão fora do escopo da maioria dos projetos de mods.10 Ao iniciar esta migração, um desenvolvedor está a comprometer-se com o ecossistema NeoForge para as versões futuras.

## **Secção 1: Migração Fundamental: Configuração do Projeto e do Ambiente**

Esta secção detalha os passos iniciais, de alto nível e não negociáveis, necessários para adaptar a estrutura do projeto e o ambiente de compilação do mod para o NeoForge. Estas são, na sua maioria, alterações mecânicas que devem ser concluídas antes que qualquer migração significativa a nível de código possa começar.

### **1.1. Modernização do Buildscript (A Revisão do build.gradle)**

O primeiro passo crítico na migração é a atualização dos ficheiros de configuração do Gradle ($build.gradle$, $gradle.properties$, $settings.gradle$) para visar o mod loader NeoForge e as suas ferramentas de compilação associadas.

O processo envolve a adição do repositório Maven do NeoForge para resolver os seus artefactos. A seguinte entrada deve ser adicionada à secção repositories do seu ficheiro $build.gradle$:  
$maven { url “https://maven.neoforged.net/releases/” }$.12  
A dependência principal do mod loader deve ser alterada. A linha que anteriormente especificava o Forge, como $minecraft 'net.minecraftforge:forge:...'$, deve ser substituída pela dependência do NeoForge: neoForge′net.neoforged:neoforge:...′. A versão específica deve corresponder à versão alvo da migração, por exemplo, 1.21.1.12

O ecossistema NeoForge introduziu novos plugins para Gradle, o $NeoGradle$ e a alternativa $ModDevGradle$, que simplificam o processo de desenvolvimento. É fortemente recomendado que os desenvolvedores consultem os MDKs (Mod Development Kits) oficiais do NeoForge como o modelo canónico para um projeto corretamente configurado.6 Para novos projetos ou para obter uma estrutura de projeto limpa, o Gerador de Mods oficial do NeoForge é o ponto de partida ideal.15

A atualização do buildscript é a porta de entrada fundamental para toda a migração. Sem configurar corretamente o sistema de compilação, o IDE do desenvolvedor será incapaz de resolver quaisquer classes do NeoForge ou do Minecraft atualizado, tornando o trabalho subsequente impossível. Este passo redireciona fundamentalmente as dependências e o ciclo de vida da compilação do projeto.

### **1.2. Transição do Ficheiro de Metadados (neoforge.mods.toml)**

Uma mudança obrigatória e direta é a renomeação do ficheiro de metadados do mod. O ficheiro localizado em $src/main/resources/META-INF/mods.toml$ deve ser renomeado para $src/main/resources/META-INF/neoforge.mods.toml$.11

Esta alteração não é arbitrária; é um mecanismo deliberado implementado pela equipa do NeoForge para criar uma distinção clara entre mods Forge e NeoForge ao nível do sistema de ficheiros.11 Isto impede que os mod loaders tentem carregar mods incompatíveis, o que levaria a falhas. Esta simples renomeação de ficheiro é uma parte chave do estabelecimento do ecossistema separado do NeoForge e sublinha a finalidade da divisão da plataforma.

É importante notar que, numa configuração de MDK padrão, muitas propriedades dentro deste ficheiro (como $modId$, $version$, $license$) são tipicamente injetadas a partir do ficheiro $gradle.properties$. Portanto, os desenvolvedores devem modificar o ficheiro $gradle.properties$ para atualizar estes metadados.18

### **1.3. A Grande Renomeação de Pacotes: de net.minecraftforge para net.neoforged**

A maior fonte única de erros de compilação iniciais é a renomeação completa, em todo o projeto, do pacote raiz de $net.minecraftforge$ para $net.neoforged$.6

Esta mudança foi muito mais do que uma alteração cosmética. Foi uma declaração de independência técnica e simbólica. Ao mover-se para um novo espaço de nomes de pacotes, a equipa do NeoForge ficou "libertada" 1 para realizar reformulações profundas e disruptivas de sistemas centrais sem criar conflitos ou ambiguidades com o projeto Forge em andamento. A renomeação do pacote é, portanto, a representação prática e simbólica da bifurcação, permitindo as melhorias arquitetónicas detalhadas mais adiante neste relatório. Esta alteração afeta todos os ficheiros Java no mod que importam qualquer classe dos antigos pacotes Forge ou FML.

### **1.4. Remapeamento Automatizado: O Script classremapper.gradle**

Para lidar com a renomeação massiva de pacotes, a equipa do NeoForge forneceu uma solução automatizada. O script de remapeamento oficial do Gradle é a solução definitiva e primária para este problema.6 A sua utilização é crítica, pois transforma uma tarefa potencialmente avassaladora de corrigir milhares de erros de importação num processo gerenciável de abordar um número menor de alterações genuínas de API. A sua utilização é fortemente aconselhada para evitar dias de trabalho manual tedioso e propenso a erros.

O processo para a sua implementação é o seguinte 19:

1. **Pré-requisito:** Assegure-se de que o projeto está numa base estável do Forge 1.20.1 e compila corretamente antes de começar.  
2. Aplicar Script: Adicione a seguinte linha ao seu ficheiro $build.gradle$, logo abaixo do bloco de plugins:  
   $apply from: 'https://gist.githubusercontent.com/Technici4n/facbcdf18ce1a556b76e6027180c32ce/raw/059ab3d504a590461746fc6e3065159f4932a960/classremapper.gradle'$  
3. **Executar Tarefa:** Execute a tarefa $gradlew updateClassnames$ a partir da linha de comandos.  
4. **Substituir Fontes:** O script irá gerar ficheiros fonte remapeados num novo diretório (por exemplo, $src/main/java\_remapped$). O desenvolvedor deve então fazer uma cópia de segurança e substituir o seu diretório $java$ original pelo conteúdo do diretório $java\_remapped$.

A tabela seguinte ilustra o padrão de alterações que o script realiza, servindo como uma referência rápida.

| Classe Antiga (Forge) | Classe Nova (NeoForge) |
| :---- | :---- |
| $net.minecraftforge.common.MinecraftForge$ | $net.neoforged.neoforge.common.NeoForge$ |
| $net.minecraftforge.eventbus.api.IEventBus$ | $net.neoforged.bus.api.IEventBus$ |
| $net.minecraftforge.fml.common.Mod$ | $net.neoforged.fml.common.Mod$ |
| $net.minecraftforge.registries.ForgeRegistries$ | $net.neoforged.neoforge.registries.NeoForgeRegistries$ (parcialmente, ver Sec. 2.1) |

## **Secção 2: Navegando pelas Reformulações Arquitetónicas do NeoForge**

Esta secção transita da configuração mecânica para as reformulações conceptuais que o NeoForge empreendeu após a bifurcação. Estas mudanças exigem a compreensão de novos padrões de design e a reescrita da lógica do código, não apenas a renomeação de importações.

### **2.1. A Reformulação do Sistema de Registos: Abraçando o Vanilla**

A reformulação do sistema de registos é o principal exemplo da nova filosofia do NeoForge. Ao remover todo o sistema paralelo $IForgeRegistry$, foi eliminada uma grande fonte de dívida técnica e complexidade. Em vez disso, o NeoForge optou por usar e estender diretamente o sistema de $Registry$ do vanilla.5 Isto simplifica a cadeia de ferramentas e promove uma melhor integração com o jogo base, mas constitui uma profunda mudança disruptiva para os desenvolvedores habituados ao Forge.

As principais alterações são as seguintes:

* $IForgeRegistry$ e $ForgeRegistries$ foram depreciados e removidos. Os desenvolvedores devem agora usar a classe $Registry$ do vanilla, acedida através de $net.minecraft.core.registries.BuiltInRegistries$ para conteúdo vanilla.5  
* Os registos adicionados pelo NeoForge (como $FLUID\_TYPES$) estão agora localizados em $net.neoforged.neoforge.registries.NeoForgeRegistries$.5  
* O padrão $RegistryObject\<T\>$ foi substituído por $DeferredHolder\<R, T\>$, que implementa diretamente a interface $Holder$ do vanilla, promovendo uma melhor integração com funcionalidades orientadas a dados.5  
* O padrão de criação de $DeferredRegister$ foi alterado para: $DeferredRegister.create(BuiltInRegistries.BLOCKS, MOD\_ID)$.21  
* A partir do NeoForge 21.2, $BlockBehaviour.Properties$ e $Item.Properties$ devem ter o seu ID definido através do método .setId()$. Isto é tratado automaticamente por auxiliares como $DeferredRegister.Blocks\#registerBlock$\`, mas deve ser feito manualmente noutros casos.22  
* Renomeações de métodos chave, como $RegistryAccess.registry$ para $RegistryAccess.lookup$, também foram implementadas.22

A tabela seguinte fornece uma referência clara para as classes e métodos de registo mais comuns.

| API Antiga do Forge | API Nova do NeoForge/Vanilla | Notas |
| :---- | :---- | :---- |
| $net.minecraftforge.registries.ForgeRegistries$ | $net.minecraft.core.registries.BuiltInRegistries$ ou $net.neoforged.neoforge.registries.NeoForgeRegistries$ | Os registos do vanilla são agora a fonte primária. O NeoForge adiciona os seus próprios numa classe separada. |
| $ForgeRegistries.BLOCKS$ | $BuiltInRegistries.BLOCK$ | Note a convenção de nomenclatura no singular. |
| $RegistryObject\<T\>$ | $DeferredHolder\<R, T\>$ ou $Supplier\<T\>$ | $DeferredHolder$ é o novo padrão, implementando a interface $Holder$ do vanilla para melhor integração com o sistema. |
| $DeferredRegister.create(ForgeRegistries.ITEMS,...)$ | $DeferredRegister.create(BuiltInRegistries.ITEM,...)$ | A chave do registo ($ResourceKey\<Registry\<T\>\>$) é agora passada diretamente. |
| $IForgeRegistry\#getValue(rl)$ | $Registry\#get(rl)$ | Utilização direta da instância $Registry$ do vanilla. |

### **2.2. A Transformação do Barramento de Eventos (Event Bus)**

O sistema de eventos do NeoForge foi significativamente refatorado para melhorar a robustez, a clareza e a experiência do desenvolvedor. Embora estas alterações sejam disruptivas, elas impõem melhores práticas de codificação.

* **Mudanças Estruturais:**  
  * O pacote raiz mudou de $net.minecraftforge.eventbus$ para $net.neoforged.bus$. Esta alteração mecânica é tratada pelo script de remapeamento.19  
  * A anotação @Cancelable foi removida. Para que um evento seja cancelável, ele deve agora implementar a interface $ICancellableEvent$.23 Implementar uma interface é um contrato mais forte e claro do que uma anotação marcadora.  
  * O enum genérico $Event.Result$ ($ALLOW$, $DENY$, $DEFAULT$) está a ser eliminado gradualmente. É substituído por enums $Result$ especializados e seguros em termos de tipo, diretamente dentro das classes de eventos (por exemplo, $MobDespawnEvent.Result.DENY$). Isto melhora a clareza do código e previne erros lógicos, como tentar usar resultados genéricos em eventos que não os suportam.16  
* **Mudanças de Registo e Subscrição:**  
  * O barramento de eventos do jogo, anteriormente acedido através de $MinecraftForge.EVENT\_BUS$, é agora $NeoForge.EVENT\_BUS$.  
  * A anotação para subscrever a este barramento mudou de $Mod.EventBusSubscriber.Bus.FORGE$ para $EventBusSubscriber.Bus.GAME$.11  
  * Numa melhoria significativa da qualidade de vida, o barramento de eventos específico do mod ($IEventBus$) pode agora ser injetado diretamente no construtor da classe principal do mod, simplificando o registo de manipuladores de eventos.18

### **2.3. Outras Refatorações Específicas do NeoForge**

Para além das grandes reformulações de sistemas, o NeoForge introduziu várias outras alterações destinadas a limpar e modernizar a API.

* **$ToolActions$ para $ItemAbilities$:** O sistema $ToolActions$ foi renomeado para $ItemAbilities$. Esta mudança foi feita para refletir melhor o seu propósito mais amplo de definir capacidades de itens para além de apenas ferramentas, incentivando uma adoção mais ampla para compatibilidade entre mods.16  
* **Remoção do $DistExecutor$:** A classe $DistExecutor$ foi completamente removida sem substituição direta. Os dois padrões recomendados para lidar com os lados lógicos (cliente/servidor) são agora:  
  1. Verificações simples if (FMLLoader.getDist() \== Dist.CLIENT) para lógica menor.  
  2. O método preferido: usar classes de ponto de entrada separadas para cliente e comum, anotadas com $@Mod(value="modid", dist=Dist.CLIENT)$.16  
* **Revisão do Pipeline de Dano:** O pipeline de dano foi completamente reformulado para resolver confusões de longa data. O novo fluxo de eventos ($EntityInvulnerabilityCheckEvent$ \-\> $LivingIncomingDamageEvent$ \-\> $LivingShieldBlockEvent$ \-\> $LivingDamageEvent.Pre$ \-\> $LivingDamageEvent.Post$) fornece ganchos muito mais claros e fiáveis para mods que interagem com o cálculo de dano.16

Estas mudanças demonstram uma tendência clara: o NeoForge está a identificar e a remover ou refatorar sistematicamente APIs antigas, confusas ou mal concebidas da era Forge. A equipa está a dar prioridade a uma superfície de API mais limpa, mais intuitiva e melhor documentada, aceitando a dor a curto prazo das alterações disruptivas por ganhos a longo prazo na experiência do desenvolvedor e na qualidade do código.

## **Secção 3: Adaptação à Evolução da API do Minecraft 1.21 Vanilla**

Esta secção foca-se nas alterações disruptivas críticas introduzidas pela Mojang no jogo base entre as atualizações 1.20.1 e 1.21.1. Estas alterações são independentes da divisão Forge/NeoForge, mas são uma parte importante do esforço de migração.

### **3.1. Gestão de Recursos e Carregamento de Dados**

A forma como o Minecraft gere recursos e dados sofreu alterações significativas que têm um impacto de longo alcance nos mods.

* **Fábricas de $ResourceLocation$:** Os construtores públicos para $ResourceLocation$ foram tornados privados. É agora obrigatório o uso dos novos métodos de fábrica estáticos: $ResourceLocation.fromNamespaceAndPath(namespace, path)$, $ResourceLocation.parse("namespace:path")$, e $ResourceLocation.withDefaultNamespace(path)$.8  
* **"Despluralização" de Diretórios:** Os diretórios de pacotes de dados foram "despluralizados". Esta mudança afeta a estrutura física de diretórios em $src/main/resources$, qualquer código que construa manualmente caminhos para esses recursos, a sintaxe de comandos no jogo e até o conteúdo de ficheiros .json que referenciam outros recursos. A tabela abaixo lista as alterações mais comuns.8

| Diretório Antigo (Plural) | Diretório Novo (Singular) |
| :---- | :---- |
| tags/items | tags/item |
| tags/blocks | tags/block |
| tags/entity\_types | tags/entity\_type |
| tags/fluids | tags/fluid |
| recipes | recipe |
| advancements | advancement |
| loot\_tables | loot\_table |
| structures | structure |

### **3.2. Sistemas Orientados a Dados: O Caso dos Encantamentos**

O sistema de encantamentos ($Enchantment$) foi fundamentalmente alterado de um registo estático, definido por código, para um sistema totalmente orientado a dados, carregado a partir de pacotes de dados.8

Esta mudança representa uma grande alteração de paradigma. Os encantamentos já não são um conjunto fixo e conhecido em tempo de compilação. O código deve agora ser escrito de forma mais abstrata e dinâmica. Como consequência direta:

* Qualquer assinatura de método que anteriormente aceitava um objeto $Enchantment$ bruto agora quase certamente requer um $Holder\<Enchantment\>$.  
* Todos os campos estáticos na classe $Enchantments$ são agora do tipo $ResourceKey\<Enchantment\>$.

Isto requer a obtenção de uma instância de $RegistryAccess$ (por exemplo, a partir de um $Level$ ou $HolderLookup.Provider$) para procurar o $Holder$ de um determinado encantamento. Embora isto torne o jogo muito mais flexível e extensível para os criadores de pacotes de dados, impõe um estilo de codificação mais complexo e abstrato aos modders.

### **3.3. Modernização de Receitas: A Interface RecipeInput**

A interface $net.minecraft.world.Container$ (e as suas várias implementações como $CraftingContainer$) foi substituída em todas as assinaturas de métodos relacionados com receitas pela nova interface $net.minecraft.world.item.crafting.RecipeInput$.8

$RecipeInput$ é uma visão mínima e de apenas leitura dos itens num contexto de fabrico. A sua simplicidade é a sua força, possuindo apenas três métodos principais: $getItem(int)$, $size()$, e $isEmpty()$. Esta mudança impõe uma separação de preocupações mais limpa e promove melhores práticas de codificação. As receitas não devem ser capazes de modificar diretamente o inventário ($Container$) que estão a inspecionar. $RecipeInput$ fornece apenas a informação necessária para a correspondência, promovendo a imutabilidade e tornando a lógica da receita mais clara e menos dependente de estado. A migração requer a alteração das assinaturas de métodos em toda a implementação da receita e a adaptação da lógica para usar esta nova visão de apenas leitura.

### **3.4. A Reescrita do Pipeline de Renderização**

Esta é provavelmente a alteração de código manual mais complexa e demorada para muitos mods. Os antigos auxiliares de renderização, mais permissivos, e a gestão implícita de estado desapareceram, substituídos por um sistema mais explícito, verboso e poderoso, que está mais próximo da API gráfica subjacente.

* **O Novo Fluxo de Trabalho do $VertexConsumer$:** A classe central para renderização personalizada é agora $com.mojang.blaze3d.vertex.VertexConsumer$. O novo fluxo de trabalho obrigatório é o seguinte: obter um $VertexConsumer$, chamar $addVertex()$ para iniciar um novo vértice, depois chamar uma série de métodos de definição para cada atributo ($setColor$, $setUv$, $setNormal$, etc.), e repetir para todos os vértices numa primitiva. O método $endVertex()$ foi removido e é agora chamado implicitamente.8

| Método Antigo | Método Novo | Propósito |
| :---- | :---- | :---- |
| vertex(...) | addVertex(...) | Inicia a definição de um novo vértice. |
| color(...) | setColor(...) | Define a cor para o vértice atual. |
| uv(...) | setUv(...) | Define as coordenadas de textura para o vértice atual. |
| overlayCoords(...) | setUv1(...) ou setOverlay(...) | Define as coordenadas de sobreposição (overlay). |
| uv2(...) | setUv2(...) ou setLight(...) | Define as coordenadas de luz (lightmap). |
| normal(...) | setNormal(...) | Define o vetor normal para o vértice atual. |

* **Mudança no $GuiGraphics.blit$:** A assinatura do método $GuiGraphics.blit$ mudou, exigindo agora uma $Function\<ResourceLocation, RenderType\>$ como seu primeiro parâmetro. Na maioria dos casos comuns, isto será $RenderType::guiTextured$.22

## **Secção 4: Referência Exaustiva de Mapeamento de Importações e Classes**

Esta secção é o principal entregável solicitado pelo utilizador: uma referência abrangente e acionável para corrigir importações e outras renomeações. Serve como a "folha de consulta" definitiva para a migração.

### **4.1. Tabela de Correspondência de Importações Mestra (Forge para NeoForge)**

A tabela seguinte é uma representação direta e analisada do ficheiro oficial $neoforge\_renames.tsrg$.19 Esta é a referência definitiva para corrigir as declarações de importação após a execução do script de remapeamento ou para correção manual. Contém todas as renomeações de classes do pacote

$net.minecraftforge$ para o novo pacote $net.neoforged$.

| Caminho de Importação Antigo (Forge) | Caminho de Importação Novo (NeoForge) |
| :---- | :---- |
| $net.minecraftforge.api.distmarker.Dist$ | $net.neoforged.api.distmarker.Dist$ |
| $net.minecraftforge.api.distmarker.OnlyIn$ | $net.neoforged.api.distmarker.OnlyIn$ |
| $net.minecraftforge.common.MinecraftForge$ | $net.neoforged.neoforge.common.NeoForge$ |
| $net.minecraftforge.common.util.LazyOptional$ | $net.neoforged.neoforge.common.util.LazyOptional$ |
| $net.minecraftforge.event.RegisterCommandsEvent$ | $net.neoforged.neoforge.event.RegisterCommandsEvent$ |
| $net.minecraftforge.event.entity.EntityEvent$ | $net.neoforged.neoforge.event.entity.EntityEvent$ |
| $net.minecraftforge.event.entity.player.PlayerEvent$ | $net.neoforged.neoforge.event.entity.player.PlayerEvent$ |
| $net.minecraftforge.eventbus.api.IEventBus$ | $net.neoforged.bus.api.IEventBus$ |
| $net.minecraftforge.eventbus.api.SubscribeEvent$ | $net.neoforged.bus.api.SubscribeEvent$ |
| $net.minecraftforge.fml.common.Mod$ | $net.neoforged.fml.common.Mod$ |
| $net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent$ | $net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent$ |
| $net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext$ | $net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext$ |
| $net.minecraftforge.registries.DeferredRegister$ | $net.neoforged.neoforge.registries.DeferredRegister$ |
| $net.minecraftforge.registries.ForgeRegistries$ | (Depreciado, usar $BuiltInRegistries$ ou $NeoForgeRegistries$) |
| $net.minecraftforge.registries.RegistryObject$ | $net.neoforged.neoforge.registries.DeferredHolder$ |
| $net.minecraftforge.client.event.EntityRenderersEvent.RegisterRenderers$ | $net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers$ |
| $net.minecraftforge.client.event.RegisterKeyMappingsEvent$ | $net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent$ |
| $net.minecraftforge.common.capabilities.Capability$ | $net.neoforged.neoforge.capabilities.Capability$ |
| $net.minecraftforge.common.capabilities.CapabilityManager$ | $net.neoforged.neoforge.capabilities.CapabilityManager$ |
| $net.minecraftforge.common.capabilities.ICapabilityProvider$ | $net.neoforged.neoforge.common.capabilities.ICapabilityProvider$ |
| $net.minecraftforge.network.NetworkDirection$ | $net.neoforged.neoforge.network.PlayNetworkDirection$ |
| $net.minecraftforge.network.simple.SimpleChannel$ | $net.neoforged.neoforge.network.simple.SimpleChannel$ |
| $net.minecraftforge.common.extensions.IForgeBlock$ | $net.neoforged.neoforge.common.extensions.IBlockExtension$ |
| $net.minecraftforge.common.extensions.IForgeItem$ | $net.neoforged.neoforge.common.extensions.IItemExtension$ |
| $net.minecraftforge.event.TickEvent.PlayerTickEvent$ | $net.neoforged.neoforge.event.tick.PlayerTickEvent$ |
| $net.minecraftforge.common.util.FakePlayer$ | $net.neoforged.neoforge.common.util.FakePlayer$ |
| $net.minecraftforge.common.ToolActions$ | $net.neoforged.neoforge.common.ItemAbilities$ |
| $net.minecraftforge.client.gui.overlay.ForgeGui$ | $net.neoforged.neoforge.client.gui.ExtendedGui$ |
| $net.minecraftforge.common.level.Forge biomeModifiers$ | $net.neoforged.neoforge.common.world.BiomeModifier$ |
| ... (e muitas outras renomeações de pacotes e classes)... | ... |

(Nota: A tabela acima é uma amostra representativa. A lista completa, derivada de 19, é extremamente longa e abrange centenas de classes. A utilização do script de remapeamento é a abordagem recomendada para lidar com esta escala.)

### **4.2. Mapeamentos Comuns de Métodos e Campos**

Nem todas as alterações disruptivas ocorrem ao nível da importação. Após a correção das importações, uma segunda vaga de erros de compilação surgirá devido a métodos e campos renomeados dentro de classes corretamente importadas. Esta secção aborda essa camada seguinte de erros, agregando renomeações comuns de vários guias de migração.8

| Classe | Nome Antigo | Nome Novo | Notas |
| :---- | :---- | :---- | :---- |
| VertexConsumer | vertex(...) | addVertex(...) | Inicia a definição de um novo vértice. |
| VertexConsumer | color(...) | setColor(...) | Define a cor para o vértice atual. |
| VertexConsumer | uv(...) | setUv(...) | Define as coordenadas de textura para o vértice atual. |
| VertexConsumer | endVertex() | (removido) | Agora é tratado implicitamente pelo motor de renderização. |
| RegistryAccess | registryOrThrow(...) | lookupOrThrow(...) | Uma renomeação semântica no NeoForge 21.2. |
| Entity | changeDimension(ServerLevel) | changeDimension(DimensionTransition) | Parte da reformulação dos portais e viagens dimensionais. |
| LootContextParams | KILLER\_ENTITY | ATTACKING\_ENTITY | Uma clarificação semântica no sistema de tabelas de saque. |
| BlockEntity | save(CompoundTag) | saveAdditional(CompoundTag, HolderLookup.Provider) | Agora requer um contexto de registo para a serialização. |
| BlockBehaviour.Properties | (nenhum) | .setId(ResourceKey, ResourceLocation) | Obrigatório no NeoForge 21.2 para definir a identidade do bloco. |
| Item.Properties | (nenhum) | .setId(ResourceKey, ResourceLocation) | Obrigatório no NeoForge 21.2 para definir a identidade do item. |

### **4.3. Depreciações, Remoções e Substituições**

Esta secção aborda a categoria mais desafiadora de alterações: APIs que não foram apenas renomeadas, mas completamente removidas. Estas exigem que o desenvolvedor aprenda e implemente um padrão inteiramente novo. A tabela seguinte fornece orientação sobre o que fazer quando uma API simplesmente desaparece.

| API Removida | Substituição / Novo Padrão | Justificação/Fonte |  |
| :---- | :---- | :---- | :---- |
| $net.minecraftforge.fml.DistExecutor$ | if (FMLLoader.getDist() \== Dist.CLIENT) {... } ou, preferencialmente, classes de ponto de entrada separadas usando $@Mod(dist \= Dist.CLIENT)$. | 16 O padrão antigo do | $DistExecutor$ era complexo e frequentemente mal utilizado. Os novos padrões são mais simples, claros e menos propensos a erros. |
| $IItemExtension$ | Uma combinação de $IClientItemExtensions$ para propriedades do lado do cliente e componentes de dados para atributos orientados a dados. | 16 A funcionalidade foi dividida e movida para sistemas mais apropriados e especializados como parte da limpeza geral da API. |  |
| $IForgeRegistry$ | Uso direto de $net.minecraft.core.Registry$. | 5 Uma grande mudança arquitetónica para remover um sistema paralelo e alinhar-se totalmente com a implementação de registos do vanilla. |  |
| new ResourceLocation(...) | Métodos de fábrica estáticos: $ResourceLocation.parse(...)$ ou $ResourceLocation.fromNamespaceAndPath(...)$. | 16 Impõe a validação do espaço de nomes e do caminho no momento da criação e fornece uma intenção mais clara sobre como a localização está a ser construída. |  |
| Event.Result genérico | Enums $Result$ especializados nas classes de eventos específicas (por exemplo, $MobDespawnEvent.Result.DENY$). | 16 Melhora a segurança de tipos, a legibilidade do código e impede que os desenvolvedores tentem usar resultados em eventos que não os suportam. |  |
| BlockEntityType.Builder | Construtor direto: new BlockEntityType\<\>(...). | 22 Simplificação da API, removendo a necessidade de um padrão de construtor (builder). |  |

## **Secção 5: Uma Estratégia Prática de Migração e Melhores Práticas**

Esta secção final sintetiza todos os detalhes técnicos anteriores num fluxo de trabalho holístico e recomendado, e fornece conselhos prospetivos para ajudar os desenvolvedores não só a completar a migração, mas também a manter o seu mod de forma mais eficaz no novo ecossistema.

### **5.1. Fluxo de Trabalho de Migração Recomendado (Uma Lista de Verificação Passo a Passo)**

1. **Cópia de Segurança e Controlo de Versão:** Antes de qualquer alteração, crie uma cópia de segurança completa do projeto 1.20.1 funcional. Se ainda não estiver a usar um sistema de controlo de versão como o Git, este é o momento perfeito para inicializar um repositório para acompanhar as alterações.  
2. **Atualizar o Ambiente de Compilação:** Modifique $build.gradle$, $gradle.properties$, e $settings.gradle$ para visar o NeoForge 1.21.1 e o plugin NeoGradle. Atualize o projeto Gradle no seu IDE. (Referência à Secção 1.1)  
3. **Renomear Ficheiro de Metadados:** Renomeie $mods.toml$ para $neoforge.mods.toml$. (Referência à Secção 1.2)  
4. **Executar Script de Remapeamento Automático:** Execute a tarefa Gradle $updateClassnames$ para tratar automaticamente a grande maioria das renomeações de pacotes e classes. Substitua a pasta de fontes pela saída remapeada. (Referência à Secção 1.4)  
5. **Abordar as Reformulações da API Principal:** Comece a corrigir os erros de compilação restantes, focando-se primeiro nas principais alterações arquitetónicas do NeoForge: Registos, Eventos e Capacidades. (Referência à Secção 2\)  
6. **Adaptar às Mudanças da API Vanilla:** Continue a corrigir os erros de compilação, focando-se agora nas alterações disruptivas do vanilla. O pipeline de renderização será provavelmente o esforço mais significativo aqui. (Referência à Secção 3\)  
7. **Atualizar e Executar Novamente a Geração de Dados:** Todos os fornecedores de geração de dados ($DataGen$) devem ser revistos. Muitas assinaturas de métodos mudaram (por exemplo, agora exigem um $HolderLookup.Provider$), e todos os fornecedores devem ser executados novamente para gerar ficheiros .json atualizados.27  
8. **Testar, Testar e Testar Novamente:** Compile e execute o mod tanto num ambiente de cliente como num servidor dedicado.29 Teste exaustivamente todas as funcionalidades, pois algumas alterações são lógicas e não se apresentarão como erros de compilação.

### **5.2. Armadilhas Comuns e Estratégias de Depuração**

* **Erros Lógicos em Sistemas Reformulados:** Código que compila não é necessariamente correto. Por exemplo, um desenvolvedor pode interpretar mal o novo e mais granular pipeline de eventos de dano, levando a modificações de dano incorretas.16 Outro problema comum é tratar o novo  
  $RecipeInput$ como mutável, o que não é.8 O conselho é reler a lógica de qualquer sistema reformulado, não apenas transliterar o código antigo.  
* **$NoClassDefFoundError$ em Servidores Dedicados:** A mudança para "conjuntos de fontes divididos" (split sourcesets) 30, onde algumas classes que antes eram comuns são agora estritamente do lado do cliente, causará uma falha de  
  $NoClassDefFoundError$ num servidor dedicado. Isto reforça a necessidade absoluta de testar num ambiente de servidor.  
* **Falhas Silenciosas de Bugs Orientados a Dados:** Erros em ficheiros .json, como o uso de um caminho pluralizado antigo numa localização de recurso, podem fazer com que os ativos ou dados não sejam carregados silenciosamente, ou causar falhas com rastreios de pilha que são difíceis de ligar à causa raiz. O conselho é verificar cuidadosamente o ficheiro $latest.log$ do jogo durante o arranque para quaisquer avisos ou erros relacionados com o carregamento de recursos.

### **5.3. Preparação para o Futuro e Considerações Finais**

O NeoForge está comprometido com uma filosofia de modernização contínua e alinhamento próximo com a evolução do vanilla.22 Isto implica que alterações disruptivas menores e mais frequentes são mais prováveis do que as grandes atualizações monolíticas da antiga era do Forge. Aconselha-se que os desenvolvedores adotem práticas modernas de engenharia de software, como uma forte separação de preocupações (por exemplo, isolando o código de renderização, as definições de registo e os manipuladores de eventos nas suas próprias classes dedicadas), para tornar as futuras atualizações menos disruptivas e mais fáceis de gerir.

Em resumo, embora a migração do Forge 1.20.1 para o NeoForge 1.21.1 seja uma tarefa substancial, o resultado é um mod mais robusto, mais performante e mais bem alinhado com a arquitetura moderna do Minecraft e a direção futura da sua comunidade de modding.

#### **Referências citadas**

1. What is happening? \- The NeoForged project, acessado em junho 20, 2025, [https://neoforged.net/news/theproject/](https://neoforged.net/news/theproject/)  
2. So neoforge is just forge but better? : r/feedthebeast \- Reddit, acessado em junho 20, 2025, [https://www.reddit.com/r/feedthebeast/comments/1el2cmk/so\_neoforge\_is\_just\_forge\_but\_better/](https://www.reddit.com/r/feedthebeast/comments/1el2cmk/so_neoforge_is_just_forge_but_better/)  
3. Can somebody explains what NeoForge is? Is it better than forge and fabric? \- Reddit, acessado em junho 20, 2025, [https://www.reddit.com/r/feedthebeast/comments/1e3acvv/can\_somebody\_explains\_what\_neoforge\_is\_is\_it/](https://www.reddit.com/r/feedthebeast/comments/1e3acvv/can_somebody_explains_what_neoforge_is_is_it/)  
4. neoforged/NeoForge: Neo Modding API for Minecraft, based on Forge \- GitHub, acessado em junho 20, 2025, [https://github.com/neoforged/NeoForge](https://github.com/neoforged/NeoForge)  
5. The big Registry system update is here \- NeoForge, acessado em junho 20, 2025, [https://neoforged.net/news/20.2registry-rework/](https://neoforged.net/news/20.2registry-rework/)  
6. NeoForge 20.2 for Minecraft 1.20.2, acessado em junho 20, 2025, [https://neoforged.net/news/20.2release/](https://neoforged.net/news/20.2release/)  
7. \[1.20.2+\] The Registry Overhaul · Issue \#37 · neoforged/NeoForge ... \- GitHub, acessado em junho 20, 2025, [https://github.com/neoforged/NeoForge/issues/37](https://github.com/neoforged/NeoForge/issues/37)  
8. Minecraft 1.20.5/6 \-\> 1.21 Mod Migration Primer · GitHub, acessado em junho 20, 2025, [https://gist.github.com/ChampionAsh5357/d895a7b1a34341e19c80870720f9880f](https://gist.github.com/ChampionAsh5357/d895a7b1a34341e19c80870720f9880f)  
9. What's everyone's feelings/opinions on 1.21.1-5 update as a whole? How do you feel the modding community with utilize it? : r/feedthebeast \- Reddit, acessado em junho 20, 2025, [https://www.reddit.com/r/feedthebeast/comments/1jjzjf3/whats\_everyones\_feelingsopinions\_on\_12115\_update/](https://www.reddit.com/r/feedthebeast/comments/1jjzjf3/whats_everyones_feelingsopinions_on_12115_update/)  
10. The 1.20.2 update for the Neoforge Mod loader will include code changes that will require mods to be built against it, not Forge. What mod loader will you continue to use in 1.20.2? : r/feedthebeast \- Reddit, acessado em junho 20, 2025, [https://www.reddit.com/r/feedthebeast/comments/16uurxz/the\_1202\_update\_for\_the\_neoforge\_mod\_loader\_will/](https://www.reddit.com/r/feedthebeast/comments/16uurxz/the_1202_update_for_the_neoforge_mod_loader_will/)  
11. NeoForge 20.5 for Minecraft 1.20.5, acessado em junho 20, 2025, [https://neoforged.net/news/20.5release/](https://neoforged.net/news/20.5release/)  
12. api:migration:neoforge | Architectury Documentation, acessado em junho 20, 2025, [https://docs.architectury.dev/api/migration/neoforge](https://docs.architectury.dev/api/migration/neoforge)  
13. NeoForge MDKs \- GitHub, acessado em junho 20, 2025, [https://github.com/neoforgemdks](https://github.com/neoforgemdks)  
14. 1.20.2 Release · neoforged NeoForge · Discussion \#199 \- GitHub, acessado em junho 20, 2025, [https://github.com/neoforged/NeoForge/discussions/199](https://github.com/neoforged/NeoForge/discussions/199)  
15. NeoForge Mod Generator, acessado em junho 20, 2025, [https://neoforged.net/mod-generator/](https://neoforged.net/mod-generator/)  
16. NeoForge 21.0 for Minecraft 1.21, acessado em junho 20, 2025, [https://neoforged.net/news/21.0release/](https://neoforged.net/news/21.0release/)  
17. 2024: The first Mod Packs \- The NeoForged project, acessado em junho 20, 2025, [https://neoforged.net/news/2024-retrospection/](https://neoforged.net/news/2024-retrospection/)  
18. Mod Files | NeoForged docs, acessado em junho 20, 2025, [https://docs.neoforged.net/docs/gettingstarted/modfiles](https://docs.neoforged.net/docs/gettingstarted/modfiles)  
19. Renaming script for the class renames introduced in the 20.2 ..., acessado em junho 20, 2025, [https://gist.github.com/Technici4n/facbcdf18ce1a556b76e6027180c32ce](https://gist.github.com/Technici4n/facbcdf18ce1a556b76e6027180c32ce)  
20. Modding Legacy / Template Minecraft Mod \- GitLab, acessado em junho 20, 2025, [https://gitlab.com/modding-legacy/template-minecraft-mod](https://gitlab.com/modding-legacy/template-minecraft-mod)  
21. Registries | NeoForged docs, acessado em junho 20, 2025, [https://docs.neoforged.net/docs/concepts/registries/](https://docs.neoforged.net/docs/concepts/registries/)  
22. NeoForge 21.2 for Minecraft 1.21.2, acessado em junho 20, 2025, [https://neoforged.net/news/21.2release/](https://neoforged.net/news/21.2release/)  
23. Event system changes in NeoForge 20.2, acessado em junho 20, 2025, [https://neoforged.net/news/20.2eventbus-changes/](https://neoforged.net/news/20.2eventbus-changes/)  
24. Events | NeoForged docs, acessado em junho 20, 2025, [https://docs.neoforged.net/docs/concepts/events/](https://docs.neoforged.net/docs/concepts/events/)  
25. Introduction | Mcjty, acessado em junho 20, 2025, [https://mcjty.eu/docs/1.20.4\_neo/porting](https://mcjty.eu/docs/1.20.4_neo/porting)  
26. 1.21 Migration Guide \- CraftTweaker Documentation, acessado em junho 20, 2025, [https://docs.blamejared.com/1.21/en/1\_21\_migration\_guide/](https://docs.blamejared.com/1.21/en/1_21_migration_guide/)  
27. Minecraft 1.20.4 \-\> 1.20.5 Mod Migration Primer \- GitHub, acessado em junho 20, 2025, [https://github.com/neoforged/.github/blob/main/primers/1.20.5/index.md](https://github.com/neoforged/.github/blob/main/primers/1.20.5/index.md)  
28. Resources | NeoForged docs, acessado em junho 20, 2025, [https://docs.neoforged.net/docs/1.20.6/resources/](https://docs.neoforged.net/docs/1.20.6/resources/)  
29. Getting Started with NeoForge | NeoForged docs \- NeoForged Documentation, acessado em junho 20, 2025, [https://docs.neoforged.net/docs/gettingstarted/](https://docs.neoforged.net/docs/gettingstarted/)  
30. NeoForge 21.5 for Minecraft 1.21.5, acessado em junho 20, 2025, [https://neoforged.net/news/21.5release/](https://neoforged.net/news/21.5release/)