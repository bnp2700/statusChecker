package com.bnpinnovation.configure;

public enum OSType {
	MacOS("mac", "darwin"), Windows("win"), Linux("nux"), Other("generic");

	private static OSType detectedOS;

	private final String[] keys;

	private OSType(String... keys) {
		this.keys = keys;
	}

	public static OSType getOS_Type() {
		if (detectedOS == null)
			detectedOS = getOperatingSystemType(System.getProperty("os.name",
					Other.keys[0]).toLowerCase());
		return detectedOS;
	}

	private boolean match(String osKey) {
		for (int i = 0; i < keys.length; i++) {
			if (osKey.indexOf(keys[i]) >= 0)
				return true;
		}
		return false;
	}


	private static OSType getOperatingSystemType(String osKey) {
		for (OSType osType : values()) {
			if (osType.match(osKey))
				return osType;
		}
		return Other;
	}
}