# Imports Desatualizados - Migração Forge 1.20.1 → NeoForge 1.21.1

## Lista de Imports que Precisam ser Atualizados

1. `import net.neoforged.neoforge.network.NetworkHooks;`
2. `import net.neoforged.neoforge.event.tick.TickEvent;`
3. `import net.neoforged.neoforge.client.event.RenderGuiOverlayEvent;`
4. `import net.neoforged.neoforge.client.event.TextureStitchEvent;`
5. `import net.neoforged.neoforge.client.gui.VanillaGuiOverlay;`
6. `import net.neoforged.neoforge.client.gui.IGuiOverlay;`
7. `import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;`
8. `import net.neoforged.neoforge.client.ConfigScreenHandler;`
9. `import net.neoforged.neoforge.common.extensions.IForgeMenuType;`
10. `import net.neoforged.neoforge.client.gui.widget.ForgeSlider;`
11. `import net.neoforged.neoforge.network.NetworkEvent;`
12. `import net.minecraft.commands.arguments.EnumArgument;`
13. `import net.neoforged.neoforge.InterModComms;`
14. `import com.mrcrayfish.controllable.client.binding.IBindingContext;`
15. `import com.mrcrayfish.framework.api.event.TickEvents;`
16. `import net.neoforged.registries.RegistryObject;`
17. `import dev.latvian.mods.kubejs.event.EventJS;`
18. `import dev.latvian.mods.rhino.util.HideFromJS;`
19. `import dev.latvian.mods.kubejs.event.Extra;`
20. `import dev.latvian.mods.kubejs.recipe.RecipeJS;`
21. `import dev.latvian.mods.kubejs.item.OutputItem;`
22. `import dev.latvian.mods.kubejs.registry.RegistryInfo;`
23. `import com.mojang.blaze3d.audio.OggAudioStream;`

## Anotações que Precisam ser Atualizadas

24. `@Mod.EventBusSubscriber` (múltiplas ocorrências)

## Classes/Interfaces que Precisam ser Atualizadas

25. `ParticleOptions.Deserializer` (símbolo não encontrado)
26. `DeferredHolder<? extends AbstractGunItem>` (parâmetros de tipo incorretos)
27. `ForgeGui` (parâmetro em métodos render)
28. `IGuiOverlay` (interface implementada)

## Pacotes de Eventos que Mudaram

29. `TickEvent.ClientTickEvent`
30. `TickEvent.RenderTickEvent`
31. `TickEvent.PlayerTickEvent`
32. `RenderGuiOverlayEvent.Pre`
33. `TextureStitchEvent.Post`
34. `NetworkEvent.Context`

## Total de Imports/APIs para Migrar: 34
