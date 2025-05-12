package affine_cipher;

import java.util.Scanner;

public class affine_cipher {
    static final int m = 26; // Alphabet size

    // Function to check if 'a' is coprime with m
    static boolean isCoprime(int a) {
        for (int i = 2; i <= Math.min(a, m); i++) {
            if (a % i == 0 && m % i == 0) return false;
        }
        return true;
    }

    // Function to find modular inverse of a under modulo m
    static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1)
                return x;
        }
        return -1;
    }

    // Encryption function
    static String encrypt(String plaintext, int a, int b) {
        StringBuilder result = new StringBuilder();
        for (char c : plaintext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int x = c - base;
                int enc = (a * x + b) % m;
                result.append((char) (enc + base));
            } else {
                result.append(c); // keep non-alphabet characters
            }
        }
        return result.toString();
    }

    // Decryption function
    static String decrypt(String ciphertext, int a, int b) {
        StringBuilder result = new StringBuilder();
        int a_inv = modInverse(a, m);
        if (a_inv == -1) return "Invalid key: no modular inverse exists.";
        for (char c : ciphertext.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                int y = c - base;
                int dec = (a_inv * (y - b + m)) % m;
                result.append((char) (dec + base));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Affine Cipher - Java Implementation");
        System.out.print("Enter the message: ");
        String message = scanner.nextLine();

        System.out.print("Enter key 'a' (must be coprime with 26): ");
        int a = scanner.nextInt();
        if (!isCoprime(a)) {
            System.out.println("Invalid key 'a'. It must be coprime with 26.");
            return;
        }

        System.out.print("Enter key 'b' (0 <= b < 26): ");
        int b = scanner.nextInt();
        scanner.nextLine(); // consume newline

        System.out.print("Choose operation (encrypt/decrypt): ");
        String operation = scanner.nextLine().trim().toLowerCase();

        String result;
        if (operation.equals("encrypt")) {
            result = encrypt(message, a, b);
            System.out.println("Encrypted message: " + result);
        } else if (operation.equals("decrypt")) {
            result = decrypt(message, a, b);
            System.out.println("Decrypted message: " + result);
        } else {
            System.out.println("Invalid operation. Choose 'encrypt' or 'decrypt'.");
        }

        scanner.close();
    }
}

