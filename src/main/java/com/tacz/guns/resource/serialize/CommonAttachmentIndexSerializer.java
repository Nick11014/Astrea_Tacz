package com.tacz.guns.resource.serialize;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
// TODO: Re-enable when CommonAttachmentIndex is habilitado
// import com.tacz.guns.resource.index.CommonAttachmentIndex;
import com.tacz.guns.resource.pojo.AttachmentIndexPOJO;

import java.lang.reflect.Type;

// TODO: Re-enable when CommonAttachmentIndex is habilitado
public class CommonAttachmentIndexSerializer implements JsonDeserializer<Object /* CommonAttachmentIndex */> {
    @Override
    public Object /* CommonAttachmentIndex */ deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        try {
            AttachmentIndexPOJO pojo = context.deserialize(json, AttachmentIndexPOJO.class);
            // TODO: Re-enable when CommonAttachmentIndex is habilitado
            return null; // CommonAttachmentIndex.getInstance(pojo);
        } catch (IllegalArgumentException e) {
            throw new JsonParseException(e.getMessage());
        }
    }
}
