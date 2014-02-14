package com.axaih.pblite;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import com.google.protobuf.Message;

public class MessageAdapter<M extends Message, I, P extends I> {
	MapperFactory mapperFactory;
	final MapperFacade mapperMsgToImpl;
	final MapperFacade mapperImplToMsg;

	protected final Class<M> mClass;

	protected final Class<I> iClass;
	protected final Class<P> pClass;
	protected final M mInstance;

	public MessageAdapter(M.Builder mBuilder, Class<I> iClass, Class<P> dClass) {
		this.iClass = iClass;
		this.pClass = dClass;

		this.mInstance = (M) mBuilder.getDefaultInstanceForType();
		mClass = (Class<M>) this.mInstance.getClass();
		mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false)
				.build();
		mapperFactory.classMap(mInstance.getClass(), iClass).byDefault()
				.mapNulls(false).mapNullsInReverse(false).register();

		mapperMsgToImpl = mapperFactory.getMapperFacade();

		mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false)
				.build();

		mapperFactory.classMap(M.Builder.class, mClass).byDefault()
				.mapNullsInReverse(false).register();

		mapperImplToMsg = mapperFactory.getMapperFacade();

	}

	public I fromMsg(M msg, I i) {

		mapperMsgToImpl.map(msg, i);
		return i;
	}
	

	public String InterfaceToPbLite(I interfaceImpl) {

		M m = toMsg(interfaceImpl);
		return PbLiteSerializer.serialize(m);

	}

	public P pbLiteToPojo(String pbLiteStr, P pojoImpl) {

		M m = (M) PbLiteSerializer.deserialize(mInstance.newBuilderForType(),
				pbLiteStr);

		fromMsg(m, pojoImpl);

		return pojoImpl;

	}

	public I pbLiteToInterface(String pbLiteStr, I interfaceImpl) {

		M m = (M) PbLiteSerializer.deserialize(mInstance.newBuilderForType(),
				pbLiteStr);

		fromMsg(m, interfaceImpl);

		return interfaceImpl;
	}

	@SuppressWarnings("unchecked")
	public M toMsg(I i) {
		M.Builder b = mInstance.newBuilderForType();
		mapperImplToMsg.map(i, b);

		return (M) b.buildPartial();
	}

}
