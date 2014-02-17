package com.anlong.chatserver.socket.chat;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.anlong.chatserver.constant.StaticValue;
import com.anlong.chatserver.entity.response.BaseResponse;
import com.anlong.chatserver.util.ErrorCodeParse;
import com.anlong.chatserver.util.ReflectionUtil;


/**
 * @Title: BeanCodecEncoder.java 
 * @Package com.anlong.chatserver.socket.chat
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午3:56:03 
 * @version V1.0   
 * @Description: TODO
 */
public class BeanCodecEncoder extends ProtocolEncoderAdapter {
	Logger logger = Logger.getLogger(BeanCodecEncoder.class);
	// 缓冲区 IoBuffer 数据
	private IoBuffer ioBuffer;
	
	/**
	 * 编码过滤器
	 * */
	public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
		Charset charset = Charset.forName(StaticValue.CHARSET_NAME);
		CharsetDecoder cd = charset.newDecoder();  
		
		if(message instanceof BaseResponse){
			BaseResponse baseMessage=(BaseResponse)message;
			// IoBuffer 是一个抽象类，所以它不能直接被实例化。
			ioBuffer = IoBuffer.allocate(16).setAutoExpand(true);
			ioBuffer.limit(0);

			// 写入消息头
			WriteHead(baseMessage);
			// 封装协议体
			if(!message.getClass().equals(BaseResponse.class)){
				WriteValue(message);
			}
			
			// 重新写入协议字节大小
			int position = ioBuffer.position();
			ioBuffer.position(0);
			ioBuffer.putInt(position);
			ioBuffer.position(position);

			baseMessage.setMsgSize(position);
			logger.info("写入数据长度："+position +"返回数据包："+baseMessage.toString() );
			// 为下一次读取数据做准备
			ioBuffer.flip();
			out.write(ioBuffer);
		}
	}
	
	private void WriteHead(BaseResponse baseMessage) throws UnsupportedEncodingException{
		// 封装协议头
		ioBuffer.putInt(0);  
		if(baseMessage.getbCode() != null){
			ioBuffer.putShort(baseMessage.getbCode());
		}else{
			ioBuffer.putShort((short)0);
		}
		
		if(baseMessage.getKey() != null){
			ioBuffer.putInt(baseMessage.getKey());
		}else {
			ioBuffer.putInt(0);
		}
		
		if(baseMessage.getUid() != null){
			ioBuffer.putInt(baseMessage.getUid());
		}else {
			ioBuffer.putInt(0);
		}
		
		if (baseMessage.getRtCode() != null) {
			ioBuffer.putShort(baseMessage.getRtCode());
			// 设置错误消息
			baseMessage.setRtMsg(ErrorCodeParse.getErrorMessage(baseMessage.getRtCode()));
		}else {
			ioBuffer.putShort((short)0);
		}
		
		if(baseMessage.getRtMsg() != null){
			byte[] rtMsgArr = baseMessage.getRtMsg().getBytes(StaticValue.CHARSET_NAME);
			ioBuffer.putShort((short) rtMsgArr.length);
			ioBuffer.put(rtMsgArr);
		}else {
			byte[] rtMsgArr = ErrorCodeParse.getErrorMessage(baseMessage.getbCode()).getBytes(StaticValue.CHARSET_NAME);
			ioBuffer.putShort((short) rtMsgArr.length);
			ioBuffer.put(rtMsgArr);
			// ioBuffer.putShort((short)0);
		}
		
		if(baseMessage.getMsgSerial() != null){
			ioBuffer.putInt(baseMessage.getMsgSerial());
		}else {
			ioBuffer.putInt(0);
		}
	}

	/**
	 * 写入IoBuffer流
	 * */
	@SuppressWarnings("unchecked")
	private void WriteValue(Object obj){
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			// 属性类型
			String fieldType = null;
			// 字段名称
			String fieldName = null;
			// 首字母大写名称
			String fieldNameUpper = null;
			// 反射返回值
			Object result = null;
			
			for (Field field : fields) {
				fieldType = field.getType().getSimpleName();
				fieldName = field.getName();
				fieldNameUpper = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
				result = ReflectionUtil.invokeMethod(obj, "get"+fieldNameUpper);
				
				if(fieldType.equals("Byte")){   
					if(result != null){
						ioBuffer.put((Byte)result);
					}else {
						ioBuffer.put((byte) 0);
					}
				}else if(fieldType.equals("Short")){
					if(result != null){
						ioBuffer.putShort((Short)result);
					}else {
						ioBuffer.putShort((short) 0);
					}
				}else if(fieldType.equals("Integer")){
					if(result != null){
						ioBuffer.putInt((Integer) result);
					}else {
						ioBuffer.putInt(0);
					}
				}else if(fieldType.equals("String")){
					if( result != null){
						String strResult = (String)result;
						byte[] byteArr = strResult.getBytes(StaticValue.CHARSET_NAME);
		
						ioBuffer.putShort((short) byteArr.length);
						ioBuffer.put(byteArr);
					}else {
						ioBuffer.putShort((short) 0);
					}
				}else if(fieldType.equals("List")){
					Type fc = field.getGenericType(); 
					// 这里判断是泛型集合
					if(fc instanceof ParameterizedType){
						ParameterizedType pt = (ParameterizedType) fc;
						 // 得到泛型里的class类型对象。
						Class genericClazz = (Class)pt.getActualTypeArguments()[0]; 
						
						List list = (List) result;
						if(list != null){
							// System.out.println("listsize:"+list.size());
							// 写入数组大小
							ioBuffer.putShort((short) list.size());
							for(int i=0;i<list.size();i++){
								if(genericClazz.getSimpleName().equals("Byte")){    // 基本数据类型 
									ioBuffer.put((Byte) list.get(i));
								}else if(genericClazz.getSimpleName().equals("Short")){
									ioBuffer.putShort((Short) list.get(i));
								}else if(genericClazz.getSimpleName().equals("Integer")){
									ioBuffer.putInt((Integer) list.get(i));
								}else if(genericClazz.getSimpleName().equals("String")){
									String str = (String) list.get(i);
									byte[] strByteArr = str.getBytes(StaticValue.CHARSET_NAME);
									
									ioBuffer.putShort((short) strByteArr.length);
									ioBuffer.put(strByteArr);
								}else{
									// List 自定义对象解析  在写入对象之前先写入整个对象的长度
									int beginPos = ioBuffer.position();
									ioBuffer.putShort((short) 0);
									// 写入自定义对象
									WriteValue(list.get(i));
									// 写入对象长度
									int endPos = ioBuffer.position();
									ioBuffer.position(beginPos);
									ioBuffer.putShort((short) (endPos-beginPos-2));
									ioBuffer.position(endPos);
								}
							}
						}else {
							ioBuffer.putShort((short) 0);
						}
					}
				}else{  //自定义数据类型
					if( result != null){
						WriteValue(result);
					}
				}
				
			}			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}

}
