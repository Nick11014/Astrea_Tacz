# Fase 4: Módulos de Compatibilidade

Depois que o mod principal estiver estável, porte as integrações.

## Checklist de Implementação:

* **[ ] Portar JEI:**
    * Atualize a dependência do JEI.
    * Revise a classe `GunModPlugin` e as categorias customizadas. A API do JEI provavelmente mudou.

* **[ ] Portar KubeJS:**
    * Atualize a dependência.
    * A classe `TimelessKubeJSPlugin` e os `events` customizados precisarão de validação e possíveis ajustes para a nova API do KubeJS para NeoForge 1.21.

* **[ ] Portar PlayerAnimator:**
    * Atualize a dependência.
    * Verifique as classes em `com.tacz.guns.compat.playeranimator.animation`. A forma como as animações são registradas e aplicadas ao modelo do jogador pode ter mudado.

* **[ ] Outras Compatibilidades (Cloth Config, Oculus, etc.):**
    * Siga o mesmo processo: atualizar dependência e revisar o código de integração.

## Estratégia de Testes (Fase 4):

* Ative um mod de compatibilidade por vez.
* **JEI:** Verifique se as receitas da mesa de armeiro aparecem corretamente.
* **KubeJS:** Crie scripts de teste para verificar se os eventos e builders customizados do TacZ estão funcionando.
* **PlayerAnimator:** Em terceira pessoa, verifique se as animações customizadas de segurar a arma, mirar e recarregar estão sendo aplicadas ao modelo do jogador.

## Status:
- [ ] Não iniciado
- [ ] Em progresso  
- [ ] Concluído
- [ ] Testado
