package com.episen.membership.setting;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;

public class InfraSettings {

	public static KeyPair keypairLoader() {
		
		
		try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("keys/server.p12")) {
			
			KeyStore kstore = KeyStore.getInstance("PKCS12");
			kstore.load(is, "episen".toCharArray());
			
			Key key = kstore.getKey("episen", "episen".toCharArray());
			
			Certificate certificate = kstore.getCertificate("episen");
			
			return new KeyPair(certificate.getPublicKey(), (PrivateKey) key);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
