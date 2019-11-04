package com.kh.spring.common.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;


@Component
public class RSAEncrypto implements MyEncrypt{
	//key값을 변수로 보관
	//key가 두개가 존재함->security패키지 안에 존재함
	private PublicKey publicKey;
	private PrivateKey privateKey;
	
	public RSAEncrypto() throws Exception{
		String path =this.getClass().getResource("/").getPath();
		path=path.substring(0,path.lastIndexOf("/target"));//잘라내기
		
		File f=new File(path+"/src/main/webapp/WEB-INF/keys.jy");
		
		
		if(f.exists()) {
				try(ObjectInputStream ois=new ObjectInputStream(new FileInputStream(f))){
					Map<String,Object> keys=(Map)ois.readObject();
					publicKey=(PublicKey)keys.get("public");
					privateKey=(PrivateKey)keys.get("private");
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			
		else {
			if(publicKey==null||privateKey==null) {
				getKey();
			}
		}
	}

	private void getKey() throws NoSuchAlgorithmException{
		SecureRandom ser=new SecureRandom();
		//키값이 두개여서 이름이 KeyPairGenerator로 만들기
		KeyPairGenerator keygen;
		
		keygen=KeyPairGenerator.getInstance("RSA");
		keygen.initialize(2048,ser);//두개의 키를 맵핑처리, 두개의 키 한번에 만들기
		KeyPair keypair=keygen.generateKeyPair();
		
		publicKey=keypair.getPublic();//공개키 생성
		privateKey=keypair.getPrivate();//개인키생성=비밀키생성
		
		//////////////////////////////////////////////////// 암호화 키 생성끝!//////////////////
		//파일에 저장을 해야함, 고정값이 유지되게 하기 위해서
		String path =this.getClass().getResource("/").getPath();
		path=path.substring(0,path.lastIndexOf("/target"));
		File f=new File(path+"/src/main/webapp/WEB-INF/keys.jy");
		
		try(ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream(f))) {
			//두개의 오브젝트를 갖고와야 해서 map으로 묶기
			Map<String,Object> keys=new HashMap<String, Object>();
			keys.put("public", publicKey);
			keys.put("private", privateKey);
			oos.writeObject(keys);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	@Override
	public String encrypt(String msg) throws Exception {
		Cipher cipher =Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
		//개인키랑 암호화 맵핑시켜짐
		byte[] encrypt=cipher.doFinal(msg.getBytes());
		return Base64.getEncoder().encodeToString(encrypt);
	}
	@Override
	public String decrypt(String msg) throws Exception {
		Cipher cipher =Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);//복호화 시키기
		byte[] enc=Base64.getDecoder().decode(msg.getBytes());
		byte[] decrypt=cipher.doFinal(enc);//원본으로 바꾸기
		return new String(decrypt,"UTF-8");
		
		
	}
	
	
	

}
