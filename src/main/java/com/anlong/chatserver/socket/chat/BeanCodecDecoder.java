package com.anlong.chatserver.socket.chat;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.anlong.chatserver.constant.StaticValue;
import com.anlong.chatserver.util.ReflectionUtil;



/**
 * @Title: BeanCodecDecoder.java 
 * @Package com.anlong.chatserver.socket.chat
 * @company ShenZhen AnLong Technology CO.,LTD.   
 * @author lixl   
 * @date 2014年2月17日 下午3:55:17 
 * @version V1.0   
 * @Description: TODO
 */
public class BeanCodecDecoder extends CumulativeProtocolDecoder  {
	Logger logger = Logger.getLogger(BeanCodecDecoder.class);
	
	// 缓冲区 IoBuffer 数据
//	private IoBuffer ioBuffer;
	
	@SuppressWarnings({ "static-access", "unchecked" })
	@Override
	protected boolean doDecode(IoSession session, IoBuffer buffer,ProtocolDecoderOutput out) throws Exception {
		Charset charset = Charset.forName(StaticValue.CHARSET_NAME);
		CharsetDecoder cd = charset.newDecoder();  
		
		// logger.info("当前收到数据包长度："+buffer.remaining()+" pos:"+buffer.position()+"    "+buffer);
		
		
		// 有数据时，读取4字节判断消息长度  
		if(buffer.remaining() >= StaticValue.PROTOCOL_SIZE){
			// 记住当前位置			
			buffer.mark();
			
			// 消息字节长度
			int msgSize = buffer.getInt();
			
			// 消息数据不完整  重置buffer
			if(msgSize - StaticValue.PROTOCOL_SIZE > buffer.remaining()){
				buffer.reset();
				return false;
			}else{ 
				buffer.reset();
				int beginPosition = buffer.position();
				int endPosition = beginPosition + msgSize;
				IoBuffer ioBuffer = buffer.getSlice(beginPosition, msgSize);
				buffer.position(endPosition);
				
				
				// 解析消息头长度
				msgSize = ioBuffer.getInt();
				// 解析消息头信息  业务编码
				Short bCode = ioBuffer.getShort();  
				// 密钥
				int key = ioBuffer.getInt();
				// 用户ID
				int uid = ioBuffer.getInt();
				// 消息来源
				byte apid = ioBuffer.get();
				// 请求流水号
				int msgSerial = ioBuffer.getInt();
				// logger.info("接收到数据包信息："+"msgSize:"+msgSize+"  bCode:"+bCode+"   key:"+key+"   uid:"+uid+"  apid:"+apid);
				
				// 获取对象
				String requestPath = StaticValue.REQUEST_PACKEAGE +".Request"+bCode;
				Class requestClass = Class.forName(requestPath);
							
				// 解析消息体
				Object obj = ReadValue(requestClass,ioBuffer);
				// 反射设置消息头
				ReflectionUtil.invokeMethod(obj, "init", msgSize,bCode,key,uid,apid,msgSerial);
				// 写入数据到应用层
				out.write(obj);
				
				//后面还有数据则继续调用再解析
				if(buffer.remaining() > 0){
					return true;  
				}  
			}
		}
		return false;
	}
	

	/**
	 * 利用反射解析IoBuffer数据，返回数据对象
	 * */
	@SuppressWarnings("unchecked")
	private Object ReadValue(Class clz,IoBuffer ioBuffer){
		try {
			// 创建对象实例
			Object obj = clz.newInstance();
			// 获取对象属性
			Field[] fields = clz.getDeclaredFields();
			// 属性类型
			String fieldType = null;
			// 字段名称
			String fieldName = null;
			// 首字母大写名称
			String fieldNameUpper = null;
			
			for (Field field : fields) {
				fieldType = field.getType().getSimpleName();
				fieldName = field.getName();
				fieldNameUpper = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
				
				if(ioBuffer.remaining() <= 0){
					break;
				}
				
				if(fieldType.equals("Byte")){   
					Byte by = ioBuffer.get();
					//System.out.println("Type : Byte  contnets:"+by);
					ReflectionUtil.invokeMethod(obj, "set"+fieldNameUpper, by);
					
//					ReflectionUtil.invokeMethod(obj, "set"+fieldNameUpper, ioBuffer.get());
				}else if(fieldType.equals("Short")){
					Short st = ioBuffer.getShort();
					//System.out.println("Type : Short  contnets:"+st);
					ReflectionUtil.invokeMethod(obj, "set"+fieldNameUpper, st);
					
//					ReflectionUtil.invokeMethod(obj, "set"+fieldNameUpper, ioBuffer.getShort());
				}else if(fieldType.equals("Integer")){
					Integer st = ioBuffer.getInt();
					//System.out.println("Type : Integer  contnets:"+st);
					ReflectionUtil.invokeMethod(obj, "set"+fieldNameUpper, st);
					
//					ReflectionUtil.invokeMethod(obj, "set"+fieldNameUpper, ioBuffer.getInt());
				}else if(fieldType.equals("String")){
					// 先获取String的长度
					short strSize = ioBuffer.getShort();
					byte[] strByteArr = new byte[strSize]; 
						
					ioBuffer.get(strByteArr,0,strSize);
					String str = new String(strByteArr,StaticValue.CHARSET_NAME);
					ReflectionUtil.invokeMethod(obj, "set"+fieldNameUpper,str);
					
					//System.out.println("Type: String   Length："+strSize +"   contents:"+str);
				}else if(fieldType.equals("List")){  // 集合数据类型
					// 数组长度
					short arrSize = ioBuffer.getShort();
					Type fc = field.getGenericType(); 
					List list= new ArrayList();
					
					// 这里判断是泛型集合
					if(fc instanceof ParameterizedType){
						 ParameterizedType pt = (ParameterizedType) fc;
						 // 得到泛型里的class类型对象
						 Class genericClazz = (Class)pt.getActualTypeArguments()[0]; 
						 
						 // 向集合添加数据
						 for (int i = 0; i < arrSize; i++) {
							 if(genericClazz.getSimpleName().equals("Byte")){    // 基本数据类型 
								 list.add(ioBuffer.get());
							 }else if(genericClazz.getSimpleName().equals("Short")){
								 list.add(ioBuffer.getShort());
							 }else if(genericClazz.getSimpleName().equals("Integer")){
								 list.add(ioBuffer.getInt());
							 }else if(genericClazz.getSimpleName().equals("String")){
								 short strSize = ioBuffer.getShort();
								 byte[] strByteArr = new byte[strSize]; 
									
								 ioBuffer.get(strByteArr,0,strSize);
								 String str = new String(strByteArr,StaticValue.CHARSET_NAME);
								 list.add(str);
							 }else{  // 自定义数据类型
								 // 获取对象的长度
								 short ptSize = ioBuffer.getShort();
								 // 截取对象长度
								 int pos = ioBuffer.position();
								 IoBuffer ptBuffer = ioBuffer.getSlice(pos, ptSize);
								 ioBuffer.position(pos+ptSize);
								 
								 // 递归获取对象信息
								 list.add(ReadValue(genericClazz,ptBuffer));
							 }
						 }
						 
						 // 反射设置list集合
						 Method method = clz.getMethod("set"+fieldNameUpper, List.class);
						 method.invoke(obj,list);
//						 reflection.invokeMethod(obj, "set"+fieldNameUpper,list);  //List 和 ArrayList不兼容
					}
				}else{  // 自定义数据类型
					String requestPath = StaticValue.REQUEST_COMMON+"."+fieldType;
					Class requestClass = Class.forName(requestPath);
					ReflectionUtil.invokeMethod(obj, "set"+fieldNameUpper, ReadValue(requestClass,ioBuffer));
				}
			}
			
			return obj;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
}
