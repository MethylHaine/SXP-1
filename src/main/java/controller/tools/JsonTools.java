package controller.tools;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import model.entity.ElGamalKey;
import protocol.impl.sigma.Responses;

public class JsonTools<Entity> {
	
	private TypeReference<Entity> type;
	
	
	public JsonTools(final TypeReference<Entity> type) {
		this.type = type;
	}

	public String toJson(Entity entity) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(entity);
		} catch (JsonProcessingException e) {
			LoggerUtilities.logStackTrace(e);
		}
		return "error";
	}
	
	@SuppressWarnings("unchecked")
	public Entity toEntity(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return (Entity) mapper.readValue(json, type);
		} catch (IOException e) {
			LoggerUtilities.logStackTrace(e);
			return null;
		}
	}
	
	
	/**
	 * return a correct json string even if java objects contains Map<Responses,ElGamalKey>
	 * @param entity
	 * 		java entity to transform in json
	 * @param containsMap
	 * 		differentiate from former method
	 * @return
	 */
	public String toJson(Entity entity, boolean containsMap) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule("SimpleModule");
		simpleModule.addSerializer(new MapSerializer<Responses, ElGamalKey>());
		mapper.registerModule(simpleModule);
		try {
			return mapper.writeValueAsString(entity);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "error";
	}

	/**
	 * return a correct java object even if it contains a Map<Responses,ElGamalKey>
	 * @param json
	 * 		json String to put into java
	 * @param containsMap
	 * 		differentiate from former method
	 * @return Entity
	 */
	@SuppressWarnings("unchecked")
	public Entity toEntity(String json, boolean containsMap) {
		ObjectMapper mapper = new ObjectMapper();
		SimpleModule simpleModule = new SimpleModule("SimpleModule");
		simpleModule.addDeserializer(Map.class, new MapResponseKeyDeserializer());
		mapper.registerModule(simpleModule);
		try {
			return (Entity) mapper.readValue(json, type);
		} catch (JsonParseException ex) {
			return null;
		} catch (JsonMappingException ex){
			return null;
		}catch (IOException e){
		e.printStackTrace();
		return null;
		}
	}
}
