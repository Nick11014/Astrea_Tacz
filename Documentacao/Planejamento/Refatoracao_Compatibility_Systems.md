# Plano de Refatoração: Sistemas de Compatibilidade

## Objetivo
Refatorar e garantir a integração completa e funcional com mods externos como Cloth Config, Controllable e PlayerAnimator, resolvendo os TODOs existentes e implementando a lógica de compatibilidade necessária.

## TODOs e Localizações Afetadas

Os seguintes arquivos e TODOs foram identificados como relacionados aos sistemas de compatibilidade:

*   `com\tacz\guns\client\gui\compat\ClothConfigScreen.java`:
    *   `// import com.tacz.guns.init.CompatRegistry; // TODO: Re-enable when CompatRegistry is habilitado`
    *   `// TODO: Re-enable when CompatRegistry is habilitado`
*   `com\tacz\guns\compat\cloth\MenuIntegration.java`:
    *   `* TODO: [MIGRAÇÃO] Expandir quando Cloth Config for completamente integrado`
    *   `* TODO: Implementar verificação real quando mod estiver disponível`
    *   `* TODO: Implementar quando Cloth Config estiver disponível` (múltiplas ocorrências)
    *   `// TODO: Implementar registro real de categorias`
*   `com\tacz\guns\compat\controllable\ControllableCompat.java`:
    *   `* TODO: [MIGRAÇÃO] Expandir quando Controllable for completamente integrado`
    *   `* TODO: Implementar verificação real quando mod estiver disponível`
    *   `* TODO: Implementar quando Controllable estiver disponível` (múltiplas ocorrências)
    *   `// TODO: Implementar leitura real da sensibilidade`
*   `com\tacz\guns\compat\jei\GunModSubtype.java`:
    *   `// TODO: Re-enable when IGun interface is habilitada`
*   `com\tacz\guns\compat\playeranimator\PlayerAnimatorCompat.java`:
    *   `* TODO: [MIGRAÇÃO] Expandir quando PlayerAnimator for completamente integrado`
    *   `* TODO: Implementar verificação real quando mod estiver disponível`
    *   `* TODO: Implementar quando PlayerAnimator estiver disponível` (múltiplas ocorrências)
    *   `// TODO: Implementar chamada real para PlayerAnimator` (múltiplas ocorrências)
    *   `* Método utilitário para registro de animações`

## Plano de Ação

### Fase 1: Análise e Verificação de Dependências
1.  **Verificar Disponibilidade dos Mods:** Confirmar se os mods de compatibilidade (Cloth Config, Controllable, PlayerAnimator, JEI) estão disponíveis para a versão do NeoForge 1.21.1 e se suas APIs são estáveis.
2.  **Analisar APIs:** Estudar as APIs de cada mod para entender como a integração deve ser feita.

### Fase 2: Implementação da Compatibilidade
1.  **Cloth Config:**
    *   Habilitar `CompatRegistry` e `ClothConfigScreen.java`.
    *   Implementar a integração completa em `MenuIntegration.java`, incluindo a verificação real da disponibilidade do mod e o registro de categorias.
2.  **Controllable:**
    *   Implementar a integração completa em `ControllableCompat.java`, incluindo a verificação da disponibilidade do mod e a leitura da sensibilidade.
3.  **PlayerAnimator:**
    *   Implementar a integração completa em `PlayerAnimatorCompat.java`, incluindo a verificação da disponibilidade do mod e as chamadas reais para o PlayerAnimator.
    *   Implementar o método utilitário para registro de animações.
4.  **JEI:**
    *   Habilitar a interface `IGun` em `GunModSubtype.java` e garantir a compatibilidade com JEI.

### Fase 3: Testes e Validação
1.  **Testes de Funcionalidade:** Testar cada integração individualmente para garantir que as funcionalidades esperadas (configurações, controles, animações, receitas) funcionem corretamente.
2.  **Testes de Conflito:** Verificar se as integrações não causam conflitos entre si ou com o mod principal.
3.  **Documentação:** Atualizar a documentação sobre as integrações com mods externos.

## Considerações Adicionais
*   **Atualizações de Mods:** Estar ciente de que as APIs dos mods de compatibilidade podem mudar em futuras atualizações, exigindo manutenção contínua.
*   **Modularidade:** Manter a integração com cada mod o mais modular possível para facilitar futuras atualizações ou remoções.
