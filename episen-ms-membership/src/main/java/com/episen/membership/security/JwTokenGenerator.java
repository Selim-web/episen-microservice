package com.episen.membership.security;

import java.security.interfaces.RSAPublicKey;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.springframework.stereotype.Service;

import com.episen.membership.model.User;
import com.episen.membership.setting.InfraSettings;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwTokenGenerator {
	
	private JWSSigner signer;
	
	@PostConstruct
	public void init() {
		
		signer = new RSASSASigner(InfraSettings.keypairLoader().getPrivate());
		//JWSVerifier verifier = new RSASSAVerifier((RSAPublicKey)InfraSettings.keypairLoader().getPublic());
		System.out.println(generateToken("admin", Arrays.asList("ADMIN")));
	}
	
	public String generateToken(String subject, List<String> roles) {
		
		ZonedDateTime currentDate = ZonedDateTime.now();
		
		JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
										.keyID(UUID.randomUUID().toString()) // TODO build private key id
										.type(JOSEObjectType.JWT)
										.build();
		
		JWTClaimsSet claimSet = new JWTClaimsSet.Builder()
												.subject(subject)
												.audience("web")
												.issuer("episen-membership")
												.issueTime(Date.from(currentDate.toInstant()))
												.expirationTime(Date.from(currentDate.plusHours(12).toInstant()))
												.jwtID(UUID.randomUUID().toString())
												.claim("ROLES", roles)
												.build();
		
		SignedJWT signedJWT = new SignedJWT(header, claimSet);
		
		try {
			signedJWT.sign(signer);
		} catch (JOSEException e) {
			throw new RuntimeException(e);
		}
		
		return signedJWT.serialize();
										
	}
	
}
