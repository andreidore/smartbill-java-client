package com.github.andreidore.smartbillclient.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import javax.annotation.Nullable;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import net.dongliu.requests.json.JsonProcessor;

/**
 * Provider json ability via jackson
 *
 * 
 */
public class JacksonProcessor implements JsonProcessor {

    private final ObjectMapper objectMapper;

    public JacksonProcessor() {
	this(createDefault());
    }

    private static ObjectMapper createDefault() {

	ObjectMapper mapper = new ObjectMapper();
	mapper.enable(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS);
	mapper.enable(DeserializationFeature.USE_LONG_FOR_INTS);
	mapper.enable(SerializationFeature.WRITE_BIGDECIMAL_AS_PLAIN);

	return mapper.findAndRegisterModules();
    }

    public JacksonProcessor(ObjectMapper objectMapper) {
	this.objectMapper = objectMapper;
    }

    @Override
    public void marshal(Writer writer, @Nullable Object value) throws IOException {
	objectMapper.writeValue(writer, value);
    }

    @Override
    public <T> T unmarshal(InputStream inputStream, Charset charset, Type type) throws IOException {
	try (Reader reader = new InputStreamReader(inputStream, charset)) {
	    JavaType javaType = objectMapper.getTypeFactory().constructType(type);
	    return objectMapper.readValue(reader, javaType);
	}
    }
}
