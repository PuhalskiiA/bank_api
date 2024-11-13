package com.example.sberstart_test_step.utils.encrypt;

/**
 * Интерфейс определяет сервисы шифрования паролей.
 */
public interface EncryptService {
    /**
     * Метод шифрует указанный текст с использованием алгоритма шифрования.
     *
     * @param password пароль, который нужно зашифровать.
     * @return Зашифрованный пароль.
     */
    String encrypt(String password);

    /**
     * Метод проверяет, соответствует ли указанный пароль зашифрованному паролю.
     *
     * @param password        Пароль для проверки.
     * @param encodedPassword Зашифрованный пароль.
     * @return Истина, если пароли соответствуют друг другу, иначе ложь.
     */
    Boolean decrypt(String password, String encodedPassword);
}
