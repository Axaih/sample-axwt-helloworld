package com.axaih.pblite;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import com.google.common.net.MediaType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.protobuf.ByteString;
import com.google.protobuf.DescriptorProtos.DescriptorProto;
import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor.JavaType;
import com.google.protobuf.Descriptors.FieldDescriptor.Type;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import com.google.protobuf.Message.Builder;

public class PbLiteSerializer {

	private static final Logger logger = Logger
			.getLogger(PbLiteSerializer.class.getName());

	public final static MediaType PROTOJSON = MediaType.create(
			MediaType.JSON_UTF_8.type(), MediaType.PROTOBUF.type());


	public static Message deserialize(MediaType mediaType, Builder builder,
			InputStream data) throws IOException {

		Message ret = null;

		if (PROTOJSON.equals(mediaType)) {
			ret = deserialize(builder, data);
		} else {

			if (MediaType.PROTOBUF.equals(mediaType)) {

				ret = builder.mergeFrom(data).build();

			}
		}

		return ret;

	}

	/**
	 * @param builder
	 * @param jsonElement
	 * @return Message
	 */
	private static Message deserialize(Builder builder, JsonElement jsonElement) {

		Descriptor descriptor = builder.getDescriptorForType();
		JsonArray jsonArray = jsonElement.getAsJsonArray();
		Message ret = null;
		int s = jsonArray.size();

		descriptor = builder.getDescriptorForType();
		try {
			for (int i = 0; i < s; i++) {
				FieldDescriptor fd = descriptor.findFieldByNumber(i);

				deserialize_(builder, fd, jsonArray.get(i));

			}
			ret = builder.build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * @param builder
	 * @param data
	 * @return
	 */
	public static Message deserialize(Builder builder, Reader data) {

		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(data);
		return deserialize(builder, jsonElement);

	}

	final static Charset UTF_8 = Charset.forName("UTF-8");

	public static Message deserialize(Builder builder, InputStream data) {

		JsonParser jsonParser = new JsonParser();

		Reader reader = new InputStreamReader(data, UTF_8);

		JsonElement jsonElement = jsonParser.parse(reader);
		return deserialize(builder, jsonElement);

	}

	public static Message deserialize(Builder builder, String data) {

		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(data);

		return deserialize(builder, jsonElement);

	}

	private static void deserialize_(Builder builder, FieldDescriptor fd,
			JsonElement jsonElement) throws Exception {
		deserialize_(builder, fd, jsonElement, false);

	}

	private static void deserialize_(Builder builder, FieldDescriptor fd,
			JsonElement jsonElement, boolean isItemFromRepeatedField)
			throws Exception {

		if (fd == null) {
			return;
		}

		if (fd.getJavaType() != JavaType.MESSAGE) {

			if (fd.isRepeated()) {

				if (jsonElement.isJsonArray()) {
					JsonArray jsonArray = jsonElement.getAsJsonArray();
					for (JsonElement el : jsonArray) {

						deserialize_(builder, fd, el);

					}

				} else {

					Object object = null;
					if (jsonElement.isJsonPrimitive()) {

						object = parseJsonPrimitive(fd, jsonElement);
						builder.addRepeatedField(fd, object);

					} else {
						logger.log(
								java.util.logging.Level.FINE,
								"error : field descriptor repeated but jsonElement is neither array nor isJsonPrimitive");
					}

				}
			} else {
				Object object = null;
				if (jsonElement.isJsonPrimitive()) {

					object = parseJsonPrimitive(fd, jsonElement);
					builder.setField(fd, object);

				}

			}

		} else {
			if (fd.isRepeated() && !isItemFromRepeatedField) {

				if (jsonElement.isJsonArray()) {

					DescriptorProto dp = fd.getContainingType().toProto();
					JsonArray jsonArray = jsonElement.getAsJsonArray();
					int s = jsonArray.size();
					List<FieldDescriptor> fdList = dp.getDescriptorForType()
							.getFields();

					for (int i = 0; i < s; i++) {

						deserialize_(builder, fd, jsonArray.get(i), true);
					}

				} else {

					Object object = null;
					if (jsonElement.isJsonPrimitive()) {

						object = parseJsonPrimitive(fd, jsonElement);
						builder.addRepeatedField(fd, object);

					} else {
						logger.log(
								java.util.logging.Level.FINE,
								"error : field descriptor repeated and not isItemFromRepeatedField and has Message Type "
										+ "but jsonElement is neither array nor isJsonPrimitive");
					}

				}

			} else {

				Builder ItemBuilder = builder.getFieldBuilder(fd);

				if (jsonElement.isJsonArray()) {

					JsonArray jsonArray = jsonElement.getAsJsonArray();

					int s = jsonArray.size();

					for (int i = 0; i < s; i++) {

						deserialize_(ItemBuilder, ItemBuilder
								.getDescriptorForType().findFieldByNumber(i),
								jsonArray.get(i));
					}

					builder.setField(fd, ItemBuilder.build());// ?

				} else {

					logger.log(
							java.util.logging.Level.FINE,
							"error : field descriptor has  Message Type and is not repeated, maybe  isItemFromRepeatedField , but jsonElement is not array   ");

				}

			}
		}

	}

	private static Object parseJsonPrimitive(FieldDescriptor fd,
			JsonElement jsonElement) throws Exception {
		Object ret = null;
		if (jsonElement.isJsonPrimitive()) {

			switch (fd.getJavaType()) {
			case LONG: {
				ret = jsonElement.getAsLong();
			}
				break;
			case BOOLEAN: {
				ret = jsonElement.getAsBoolean();
			}
				break;
			case STRING: {
				ret = jsonElement.getAsString();
			}
				break;
			case FLOAT: {
				ret = jsonElement.getAsFloat();
			}
				break;
			case DOUBLE: {
				ret = jsonElement.getAsDouble();
			}

				break;
			case ENUM: {

				ret = fd.getEnumType()
						.findValueByNumber(jsonElement.getAsInt());
			}
				break;
			case INT: {
				ret = jsonElement.getAsInt();
			}
				break;

			default: {
				// logger.log(java.util.logging.Level.FINE,"default case: " +
				// fd.getType().toString());
				throw new Exception("default case: " + fd.getType().toString());
			}

			}

		}

		return ret;
	}

	public static void serialize(MediaType mediaType, Message message,
			OutputStream outputStream) throws IOException {

		if (PROTOJSON.equals(mediaType)) {
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
					outputStream, UTF_8);

			outputStreamWriter.write(serialize(message));

		} else {
			if (MediaType.PROTOBUF.equals(mediaType)) {
				message.writeTo(outputStream);
				
				logger.log(Level.FINE, "wrote to output stream");

				logger.log(Level.FINE,message.toString());

			}else{
				throw new UnsupportedMediaTypeException("");
			}
			
		}

	}

	public static ByteString serialize(MediaType mediaType, Message message)
			    {

		ByteString ret = null;
		if (PROTOJSON.equals(mediaType)) {

			ret = ByteString.copyFromUtf8(serialize(message));

		} else {

			if (MediaType.PROTOBUF.equals(mediaType)) {

				ret = message.toByteString();

			}
		}
		return ret;
	}

	public static String serialize(Message message) {

		StringBuilder sb = new StringBuilder();

		sb = sb.append('[');

		int n = 0;

		for (FieldDescriptor fd : message.getAllFields().keySet()) {

			n = fd.getNumber() - n;

			if (n > 0) {
				sb.append(Strings.repeat(",", n));
			}
			n = fd.getNumber();
			int repeatedFieldCount = 1;

			List<Object> objectList;

			if (fd.isRepeated()) {
				sb.append("[");
				repeatedFieldCount = message.getRepeatedFieldCount(fd);
				objectList = (List<Object>) message.getField(fd);

			} else {
				objectList = ImmutableList.of(message.getField(fd));
			}

			for (int i = 0; i < repeatedFieldCount - 1; i++) {

				serializeField(fd, objectList.get(i), sb);

				sb.append(",");
			}

			if (fd.isRepeated()) {

				if (repeatedFieldCount > 0) {
					serializeField(fd, objectList.get(repeatedFieldCount - 1),
							sb);
				}

				sb.append("]");
			} else {
				serializeField(fd, objectList.get(repeatedFieldCount - 1), sb);
			}

		}
		sb.append("]");
		return sb.toString();

	}

	private static StringBuilder serializeField(FieldDescriptor fd, Object o,
			StringBuilder sb) {

		if (fd.getType().equals(Type.MESSAGE)) {

			sb.append(serialize((Message) o));

		} else {

			sb.append(toValidJsonStringValue(fd, o));
		}

		return sb;
	}

	private static String toValidJsonStringValue(FieldDescriptor fd, Object o) {
		if (o == null)
			return null;

		String ret = null;
		switch (fd.getJavaType()) {

		case STRING: {
			JsonElement jsonElement = new JsonPrimitive((String) o);

			ret = jsonElement.toString();
		}
			break;

		case DOUBLE: {
			ret = Double.toString((double) o);
		}
			break;
		case FLOAT: {
			ret = Float.toString((float) o);
		}

			break;
		case LONG: {
			ret = Long.toString((long) o);
		}
			break;

		case INT: {
			ret = Integer.toString((int) o);
		}
			break;
		case ENUM: {

			ret = Integer.toString(((EnumValueDescriptor) o).getNumber());
		}
			break;
		case BOOLEAN: {
			ret = Boolean.toString((boolean) o);
		}
			break;

		default:
			break;

		}

		return ret;
	}

	public PbLiteSerializer() {

	}
}
