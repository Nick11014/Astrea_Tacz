import os
import json

mod_id = "tacz"
base_path = r"C:/Users/mathe/Desktop/Minecraft Modding/Astrea_Arsenal/src/main/resources/assets"
sounds_json_path = os.path.join(base_path, mod_id, "sounds.json")
mod_sound_events_path = r"C:/Users/mathe/Desktop/Minecraft Modding/Astrea_Arsenal/src/main/java/com/tacz/guns/init/ModSoundEvents.java"

sound_files = []
for root, _, files in os.walk(os.path.join(base_path, mod_id)):
    for file in files:
        if file.endswith(".ogg"):
            relative_path = os.path.relpath(os.path.join(root, file), os.path.join(base_path, mod_id))
            sound_files.append(relative_path.replace("\\", "/"))

# Generate sounds.json
sounds_json_content = {}
for sound_file in sound_files:
    sound_id = sound_file.replace(".ogg", "")
    sounds_json_content[sound_id] = {
        "sounds": [
            {
                "name": f"{mod_id}:{sound_id}",
                "stream": False
            }
        ]
    }

# Generate ModSoundEvents.java content
mod_sound_events_header = f"""package com.tacz.guns.init;

import com.tacz.guns.GunMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class ModSoundEvents {{
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, GunMod.MOD_ID);

"""

mod_sound_events_body = []
for sound_file in sound_files:
    sound_id = sound_file.replace(".ogg", "")
    java_name = sound_id.replace("/", "_").replace(".", "_").upper()
    mod_sound_events_body.append(f"    public static final Supplier<SoundEvent> {java_name} = registerSoundEvent(\"{sound_id}\");")

mod_sound_events_footer = f"""
    private static Supplier<SoundEvent> registerSoundEvent(String name) {{
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(GunMod.MOD_ID, name)));
    }}
}}
"""

with open("sounds_json_output.txt", "w", encoding="utf-8") as f_json:
    f_json.write(json.dumps(sounds_json_content, indent=2))

with open("mod_sound_events_output.txt", "w", encoding="utf-8") as f_java:
    f_java.write(mod_sound_events_header + "\n".join(mod_sound_events_body) + mod_sound_events_footer)