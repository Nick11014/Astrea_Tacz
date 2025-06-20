# Fase 2: Sistemas do Lado do Cliente - Renderização, GUI e Animações

Esta é a parte mais complexa e de maior risco do seu mod. As APIs de renderização costumam mudar entre versões.

## Checklist de Implementação:

* **🔄 Atualizar Renderizadores de Itens (IClientItemExtensions/BER):** ✅ **PADRÃO ESTABELECIDO**
    * ✅ Pesquisa e documentação completa da migração BEWLR → IClientItemExtensions
    * ✅ Guia técnico criado (465 linhas) com padrões específicos para NeoForge 1.21.1
    * ✅ Padrão de implementação definido usando `initializeClient()` 
    * 🚧 Implementação prática bloqueada por problemas de build system
    * Classes: `GunItemRendererWrapper`, `AmmoItemRenderer`, `AttachmentItemRenderer`

* **⏳ Atualizar Renderizadores de Entidades de Bloco (BER):**
    * Revise `GunSmithTableRenderer`, `StatueRenderer` e `TargetRenderer`.
    * **Dependência:** Resolução dos bloqueantes de build

* **⏳ Atualizar Renderizadores de Entidades:**
    * Revise `EntityBulletRenderer`.
    * **Dependência:** Renderizadores de itens funcionais

* **⏳ Atualizar Telas de GUI (`Screen`):**
    * Classes como `GunRefitScreen` e `GunSmithTableScreen`.
    * Verificar construtores, métodos de renderização (`render`, `renderBackground`), e widgets.

* **⏳ Atualizar Overlays (HUD):**
    * Migrar `GunHudOverlay` para nova API `IForgeGui` do NeoForge.
    * Sistema de overlays significativamente alterado.

* **⏳ Revisar Sistema de Animação:**
    * Verificar ponte entre sistema customizado e renderização do jogo.
    * Classes críticas: `FirstPersonRenderGunEvent`, listeners (`ModelRotateListener`).
    * Integração com `PoseStack` após migração para IClientItemExtensions.

* **⏳ Mapeamento de Teclas (`KeyMapping`):**
    * Verificar registro de `KeyMapping` em `ClientSetupEvent`.
    * Classes no pacote `com.tacz.guns.client.input`.

## 📊 Progresso Atual:

### ✅ Fase 2.1 - Renderizadores de Itens (27% completo)
- ✅ **Análise completa:** 196 arquivos client/renderer mapeados
- ✅ **Impedimento resolvido:** Migração BEWLR → IClientItemExtensions documentada
- ✅ **Padrão estabelecido:** Código de exemplo e guia técnico criados
- 🚧 **Implementação:** Bloqueada por estado inconsistente do projeto

### ⏳ Fase 2.2 - HUD e Overlays (0% iniciado)
- **Dependência:** Resolução dos bloqueantes ativos

### ⏳ Fase 2.3 - Eventos e Animação (0% iniciado)  
- **Dependência:** Sistema de renderização base funcional

## 🚫 Bloqueantes Ativos:
1. ✅ **Impedimento #1:** APIs de renderização (RESOLVIDO)
2. 🚧 **Impedimento #2:** Estado inconsistente do projeto (100+ erros de compilação)
3. 🚧 **Impedimento #3:** Build system instável

## 💡 Padrão de Migração (IClientItemExtensions):
```java
// Em cada classe de item (ex: ModernKineticGunItem.java)
@Override
@OnlyIn(Dist.CLIENT)
public void initializeClient(Consumer<IClientItemExtensions> consumer) {
    consumer.accept(new IClientItemExtensions() {
        private final GunItemRendererWrapper renderer = new GunItemRendererWrapper();

        @Override
        public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return renderer;
        }
    });
}
```

## Estratégia de Testes (Fase 2):

* **Teste 1: Renderização de Itens:**
    * Segure cada arma na mão, em primeira e terceira pessoa. Elas devem renderizar corretamente.
    * Verifique os itens no inventário, na hotbar e como itens dropados no chão.
    * Adicione e remova acessórios e veja se o modelo da arma é atualizado corretamente em tempo real.

* **Teste 2: Animações:**
    * Execute todas as animações: inspecionar, recarregar (vazio e tático), mirar, atirar, puxar o ferrolho. Verifique se as animações da arma (view model) e do jogador (third-person) estão funcionando.

* **Teste 3: GUI e HUD:**
    * Abra a mesa de armeiro e a tela de modificação. Todos os botões, slots e diagramas devem ser funcionais e renderizar corretamente.
    * O HUD de munição e outros indicadores devem aparecer e funcionar como esperado durante o jogo.

* **Teste 4: Efeitos Visuais:** Verifique partículas de fumaça do cano, ejeção de cápsulas e buracos de bala.

## Status:
- [ ] Não iniciado
- [x] Em progresso (Impedimento #1 RESOLVIDO, bloqueado por build system)
- [ ] Concluído
- [ ] Testado

### 📈 Progresso Detalhado:
- ✅ **Análise Técnica:** 196 arquivos mapeados ✅ **COMPLETO**
- ✅ **Pesquisa:** Documentação IClientItemExtensions ✅ **COMPLETO**  
- ✅ **Padrão de Migração:** Guia técnico criado ✅ **COMPLETO**
- 🚧 **Implementação Prática:** Bloqueada por build system
- ❌ **Testes:** Aguardando implementação

**Próximo foco:** Resolver bloqueantes de infraestrutura (Impedimentos #2 e #3) para permitir implementação prática da migração IClientItemExtensions.

### 📚 Recursos Técnicos:
- **Guia Completo:** `../Portando Renderização TacZ para NeoForge_.md` (465 linhas)
- **Arquitetura:** BlockEntityWithoutLevelRenderer → IClientItemExtensions
- **Performance:** Caching de renderer instances, evitar instanciação repetida  
- **Debugging:** Padrões de troubleshooting para problemas visuais comuns

### 🚫 **IMPEDIMENTO CRÍTICO:** APIs de Renderização
- **Problema:** BlockEntityWithoutLevelRenderer → IClientItemExtensions (migração desconhecida)
- **Impacto:** Bloqueia 100% da funcionalidade de renderização
- **Escala:** 196 arquivos cliente afetados
- **Prioridade:** 🔴 CRÍTICA

### 📊 Progresso Detalhado:
- ✅ **Análise estrutural:** 196 arquivos cliente mapeados
- 🔴 **Renderizadores:** BLOQUEADOS (impedimento #1)
- 🔴 **HUD/Overlays:** DEPENDENTES (aguardando renderizadores)
- 🔴 **Eventos:** DEPENDENTES (aguardando renderizadores)  
- 🟡 **GUI/Screens:** PODE AVANÇAR (independente)
- ❌ **Testes:** Pendente

**Status Atual:** 🔴 **BLOQUEADO** - Necessária pesquisa de APIs NeoForge 1.21.1
**Próxima ação:** Resolver impedimento #1 (APIs de renderização)
