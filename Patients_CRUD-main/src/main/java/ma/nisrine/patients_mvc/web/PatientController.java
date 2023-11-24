package ma.nisrine.patients_mvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.nisrine.patients_mvc.entities.Patient;
import ma.nisrine.patients_mvc.repositories.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;
    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name="page",defaultValue = "0") int page,
                           @RequestParam(name="size",defaultValue = "5") int size,
                           @RequestParam(name="keyword",defaultValue = "") String keyword
    ){
        Page<Patient> pagePatients = patientRepository.findByPrenomContains(keyword, PageRequest.of(page,size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage",page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }
    @GetMapping("/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id){
        patientRepository.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }
    @GetMapping(path = "/patients")
    @ResponseBody
    public List<Patient> listPatients(){
        return patientRepository.findAll();
    }
    @GetMapping(path = "/admin/formPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String formPatients(Model model){
        model.addAttribute("patient",new Patient());
        return  "formPatients";
    }
    @PostMapping(path = "/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String savePatient(@Valid Patient patient, BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "formPatients";
        patientRepository.save(patient);

        return "redirect:/";
    }
    @GetMapping(path = "/admin/editPatient")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String editPatient(@RequestParam(name = "id") Long id, Model model){
        Patient patient=patientRepository.findById(id).get();
        model.addAttribute("patient",patient);

        return  "editPatient";
    }


}