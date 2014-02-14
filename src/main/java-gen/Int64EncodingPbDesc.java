// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: js/int64_encoding.proto

public final class Int64EncodingPbDesc {
  private Int64EncodingPbDesc() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registry.add(Int64EncodingPbDesc.jstype);
  }
  /**
   * Protobuf enum {@code Int64Encoding}
   */
  public enum Int64Encoding
      implements com.google.protobuf.ProtocolMessageEnum {
    /**
     * <code>JS_DEFAULT = 0;</code>
     */
    JS_DEFAULT(0, 0),
    /**
     * <code>JS_NUMBER = 1;</code>
     */
    JS_NUMBER(1, 1),
    ;

    /**
     * <code>JS_DEFAULT = 0;</code>
     */
    public static final int JS_DEFAULT_VALUE = 0;
    /**
     * <code>JS_NUMBER = 1;</code>
     */
    public static final int JS_NUMBER_VALUE = 1;


    public final int getNumber() { return value; }

    public static Int64Encoding valueOf(int value) {
      switch (value) {
        case 0: return JS_DEFAULT;
        case 1: return JS_NUMBER;
        default: return null;
      }
    }

    public static com.google.protobuf.Internal.EnumLiteMap<Int64Encoding>
        internalGetValueMap() {
      return internalValueMap;
    }
    private static com.google.protobuf.Internal.EnumLiteMap<Int64Encoding>
        internalValueMap =
          new com.google.protobuf.Internal.EnumLiteMap<Int64Encoding>() {
            public Int64Encoding findValueByNumber(int number) {
              return Int64Encoding.valueOf(number);
            }
          };

    public final com.google.protobuf.Descriptors.EnumValueDescriptor
        getValueDescriptor() {
      return getDescriptor().getValues().get(index);
    }
    public final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptorForType() {
      return getDescriptor();
    }
    public static final com.google.protobuf.Descriptors.EnumDescriptor
        getDescriptor() {
      return Int64EncodingPbDesc.getDescriptor().getEnumTypes().get(0);
    }

    private static final Int64Encoding[] VALUES = values();

    public static Int64Encoding valueOf(
        com.google.protobuf.Descriptors.EnumValueDescriptor desc) {
      if (desc.getType() != getDescriptor()) {
        throw new java.lang.IllegalArgumentException(
          "EnumValueDescriptor is not for this type.");
      }
      return VALUES[desc.getIndex()];
    }

    private final int index;
    private final int value;

    private Int64Encoding(int index, int value) {
      this.index = index;
      this.value = value;
    }

    // @@protoc_insertion_point(enum_scope:Int64Encoding)
  }

  public static final int JSTYPE_FIELD_NUMBER = 50001;
  /**
   * <code>extend .google.protobuf.FieldOptions { ... }</code>
   */
  public static final
    com.google.protobuf.GeneratedMessage.GeneratedExtension<
      com.google.protobuf.DescriptorProtos.FieldOptions,
      Int64EncodingPbDesc.Int64Encoding> jstype = com.google.protobuf.GeneratedMessage
          .newFileScopedGeneratedExtension(
        Int64EncodingPbDesc.Int64Encoding.class,
        null);

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\027js/int64_encoding.proto\032 google/protob" +
      "uf/descriptor.proto*.\n\rInt64Encoding\022\016\n\n" +
      "JS_DEFAULT\020\000\022\r\n\tJS_NUMBER\020\001:?\n\006jstype\022\035." +
      "google.protobuf.FieldOptions\030\321\206\003 \001(\0162\016.I" +
      "nt64EncodingB\025B\023Int64EncodingPbDesc"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          jstype.internalInit(descriptor.getExtensions().get(0));
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.DescriptorProtos.getDescriptor(),
        }, assigner);
  }

  // @@protoc_insertion_point(outer_class_scope)
}