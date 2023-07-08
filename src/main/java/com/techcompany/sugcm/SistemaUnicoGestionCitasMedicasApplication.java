package com.techcompany.sugcm;

import com.techcompany.sugcm.models.entity.Role;
import com.techcompany.sugcm.repositories.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@SpringBootApplication
public class SistemaUnicoGestionCitasMedicasApplication {
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(SistemaUnicoGestionCitasMedicasApplication.class, args);
    }

    @Bean
    public ModelMapper getModelMapper() {
        registrarUsuario();
        return new ModelMapper();
    }

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("danielprueba104@gmail.com");
        mailSender.setPassword("lala.123456789");

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    void registrarUsuario() {
        var administrator = new Role();
        administrator.setName("ADMINISTRATOR");
        roleRepository.save(administrator);
        var doctor = new Role();
        doctor.setName("DOCTOR");
        roleRepository.save(doctor);
        var patient = new Role();
        patient.setName("PATIENT");
        roleRepository.save(patient);
    }
}
