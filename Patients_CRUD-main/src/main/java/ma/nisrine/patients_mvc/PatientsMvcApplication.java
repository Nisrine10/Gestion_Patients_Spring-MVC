package ma.nisrine.patients_mvc;

import ma.nisrine.patients_mvc.entities.Patient;
import ma.nisrine.patients_mvc.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class PatientsMvcApplication {

    public static void main(String[] args) {

        SpringApplication.run(PatientsMvcApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(
                    new Patient(null,"Aomari","Nisrine",new Date(),false,132));
            patientRepository.save(
                    new Patient(null,"Aomari","Nour",new Date(),false,321));
            patientRepository.save(
                    new Patient(null,"Aomari","Ayman",new Date(),false,127));
            patientRepository.save(
                    new Patient(null,"Bourich","Amal",new Date(),false,100));
            patientRepository.findAll().forEach(p->{
                System.out.println(p.getNom());

            });
        };


    }

}
