package com.androidmodule.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;

/**
 * AMDes
 * 
 * @author LiangMaYong
 * @version 1.0
 */
public class AMDes {

	private String iv = "national";
	private static AMDes des = null;

	private AMDes() {
	}

	private static AMDes getDes() {
		if (des == null) {
			des = new AMDes();
		}
		return des;
	}

	/**
	 * encrypt
	 * 
	 * @param encryptByte
	 *            encryptByte
	 * @param encryptKey
	 *            encryptKey
	 * @return encrypt string
	 */
	public static String encrypt(byte[] encryptByte, String encryptKey) {
		return getDes()._encrypt(encryptByte, getKey(encryptKey));
	}

	@SuppressLint("TrulyRandom")
	private String _encrypt(byte[] encryptByte, String encryptKey) {
		try {
			IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
			byte[] encryptedData = cipher.doFinal(encryptByte);
			return Base64.encode(encryptedData);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * decrypt
	 * 
	 * @param encryptString
	 *            encryptString
	 * @param encryptKey
	 *            encryptKey
	 * @return byte[]
	 */
	public static byte[] decrypt(String encryptString, String encryptKey) {
		return getDes()._decrypt(encryptString, getKey(encryptKey));
	}

	private byte[] _decrypt(String encryptString, String encryptKey) {
		try {
			byte[] encryptByte = Base64.decode(encryptString);
			IvParameterSpec zeroIv = new IvParameterSpec(iv.getBytes());
			SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
			return cipher.doFinal(encryptByte);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * md5 encode
	 * 
	 * @param plain
	 *            plain
	 * @return string
	 */
	private final static String md5(String plain) {
		String re_md5 = new String();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plain.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			re_md5 = buf.toString();
		} catch (NoSuchAlgorithmException e) {
		}
		return re_md5;
	}

	/**
	 * The encryptKey to 8 characters
	 * 
	 * @param encryptKey
	 *            encryptKey
	 * @return string
	 */
	private static String getKey(String encryptKey) {
		return md5(encryptKey).substring(4, 12);
	}

	private static class Base64 {

		private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
				.toCharArray();

		public static String encode(byte[] data) {
			int start = 0;
			int len = data.length;
			StringBuffer buf = new StringBuffer(data.length * 3 / 2);

			int end = len - 3;
			int i = start;

			while (i <= end) {
				int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 0x0ff) << 8)
						| (((int) data[i + 2]) & 0x0ff);

				buf.append(legalChars[(d >> 18) & 63]);
				buf.append(legalChars[(d >> 12) & 63]);
				buf.append(legalChars[(d >> 6) & 63]);
				buf.append(legalChars[d & 63]);

				i += 3;
			}

			if (i == start + len - 2) {
				int d = ((((int) data[i]) & 0x0ff) << 16) | ((((int) data[i + 1]) & 255) << 8);

				buf.append(legalChars[(d >> 18) & 63]);
				buf.append(legalChars[(d >> 12) & 63]);
				buf.append(legalChars[(d >> 6) & 63]);
				buf.append("=");
			} else if (i == start + len - 1) {
				int d = (((int) data[i]) & 0x0ff) << 16;

				buf.append(legalChars[(d >> 18) & 63]);
				buf.append(legalChars[(d >> 12) & 63]);
				buf.append("==");
			}

			return buf.toString();
		}

		public static byte[] decode(String s) {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			try {
				decode(s, bos);
			} catch (IOException e) {
				throw new RuntimeException();
			}
			byte[] decodedBytes = bos.toByteArray();
			try {
				bos.close();
				bos = null;
			} catch (IOException ex) {
				System.err.println("Error while decoding BASE64: " + ex.toString());
			}
			return decodedBytes;
		}

		private static void decode(String s, OutputStream os) throws IOException {
			int i = 0;

			int len = s.length();

			while (true) {
				while (i < len && s.charAt(i) <= ' ')
					i++;

				if (i == len)
					break;

				int tri = (decode(s.charAt(i)) << 18) + (decode(s.charAt(i + 1)) << 12) + (decode(s.charAt(i + 2)) << 6)
						+ (decode(s.charAt(i + 3)));

				os.write((tri >> 16) & 255);
				if (s.charAt(i + 2) == '=')
					break;
				os.write((tri >> 8) & 255);
				if (s.charAt(i + 3) == '=')
					break;
				os.write(tri & 255);

				i += 4;
			}
		}

		private static int decode(char c) {
			if (c >= 'A' && c <= 'Z')
				return ((int) c) - 65;
			else if (c >= 'a' && c <= 'z')
				return ((int) c) - 97 + 26;
			else if (c >= '0' && c <= '9')
				return ((int) c) - 48 + 26 + 26;
			else
				switch (c) {
				case '+':
					return 62;
				case '/':
					return 63;
				case '=':
					return 0;
				default:
					throw new RuntimeException("unexpected code: " + c);
				}
		}
	}
}