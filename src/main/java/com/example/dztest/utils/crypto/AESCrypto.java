package com.example.dztest.utils.crypto;

public class AESCrypto {
    public static enum Algorithm {
        // DES
        DES("DES", 56),

        // 3DES
        DESede112("DESede", 112),
        DESede168("DESede", 168),

        // AES
        AES128("AES", 128),
        AES192("AES", 192),
        AES256("AES", 256);

        private String name;
        private int keySize;

        Algorithm(String name, int keySize) {
            this.name = name;
            this.keySize = keySize;
        }

        public String getName() {
            return name;
        }

        public int getKeySize() {
            return keySize;
        }

        @Override
        public String toString() {
            return this.name + "-" + this.keySize;
        }

        /**
         *
         * @param name
         * @return
         */
        public static Algorithm get(String name, int length) {
            for (Algorithm algorithm : Algorithm.values()) {
                if (algorithm.getName().equals(name) && algorithm.getKeySize() == length) {
                    return algorithm;
                }
            }

            throw new UnsupportedOperationException("Can't find Algorithm by name: "
                    + name + ", keySize: " + length);
        }
    }

    public static enum Mode {
        ECB("ECB"),
        CBC("CBC"),
        CFB("CFB"),
        PCBC("PCBC"),
        CTR("CTR"),
        CTS("CTS"),
        OFB("OFB");

        private String name;

        private Mode(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        /**
         *
         * @param name
         * @return
         */
        public static Mode get(String name) {
            return Mode.valueOf(name);
        }
    }

    public static enum Padding {
        NoPadding("NoPadding"),
        PKCS5Padding("PKCS5Padding"),
        ISO10126Padding("ISO10126Padding");

        private String name;

        private Padding(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static Padding get(String name) {
            return Padding.valueOf(name);
        }
    }
}
