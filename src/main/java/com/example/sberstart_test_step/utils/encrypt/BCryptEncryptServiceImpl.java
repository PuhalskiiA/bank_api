package com.example.sberstart_test_step.utils.encrypt;

import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Класс реализует сервис шифрования паролей с использованием алгоритма BCrypt.
 */
@Service
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
public class BCryptEncryptServiceImpl implements EncryptService {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * Метод шифрует указанный пароль с использованием алгоритма BCrypt.
     *
     * @param password Пароль, который нужно зашифровать.
     * @return Зашифрованный пароль.
     */
    @Override
    public String encrypt(String password) {
        return passwordEncoder.encode(password);
    }

    /**
     * Метод проверяет, соответствует ли указанный пароль зашифрованному паролю.
     *
     * @param password        Пароль для проверки.
     * @param encodedPassword Зашифрованный пароль.
     * @return Истина, если пароли соответствуют друг другу, иначе ложь.
     */
    @Override
    public Boolean decrypt(String password, String encodedPassword) {
        return passwordEncoder.matches(password, encodedPassword);
    }
}
