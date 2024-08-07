package com.Stoicodex.Spring.Security.Entities;

import com.Stoicodex.Spring.Security.Model.RoleName;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

public class RoleDeserializer extends JsonDeserializer {

    @Override
    public Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        String roleName = p.getText();
        Role role = new Role();
        role.setName(RoleName.valueOf(roleName));
        return role;
    }
}
