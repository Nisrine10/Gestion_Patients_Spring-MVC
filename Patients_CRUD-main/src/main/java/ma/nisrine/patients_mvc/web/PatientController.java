package ma.nisrine.patients_mvc.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.nisrine.patients_mvc.entities.Patient;
import ma.nisrine.patients_mvc.repositories.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    @Autowired
    private PatientRepository patientRepository;

    @GetMapping(path = "/index")
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "size", defaultValue = "4") int size,
                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
        Page<Patient> pagePatients = patientRepository.findByPrenomContains(kw, PageRequest.of(page, size));
        model.addAttribute("listPatients", pagePatients.getContent());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", kw);
        return "patients";
    }
    @GetMapping("/delete")
    public String delete(@RequestParam(name = "id")Long id,
                         @RequestParam(name = "keyword",defaultValue = "") String keyword,
                         @RequestParam(name = "page",defaultValue = "0") int page ){
        patientRepository.deleteById(id);
        return "redirect:/index?page="+page+"&keyword="+keyword;

}
    @GetMapping("/")
    public String home(){

        return "redirect:/index";
}
    @GetMapping("/formPatients")
    public String formPatients(Model model){
     model.addAttribute("patient",new Patient());
    return "formPatients";
}
    @PostMapping("/save")
    public String save(Model model,
                       @Valid Patient patient, BindingResult bindingResult ,@RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = " ")String keyword){
        if(bindingResult.hasErrors()) return "formPatients" ;
        patientRepository.save(patient);
        return "redirect:/index?page="+page+"&keyword="+keyword;
}
    @GetMapping("/editPatient")
    public String editPatient(Model model,Long id,String keyword,int page){
        Patient patient=patientRepository.getReferenceById(id);
        if(patient==null) throw new RuntimeException("Patient introuvable");
        model.addAttribute("patient",patient);
        model.addAttribute("page",page);
        model.addAttribute("keyword",keyword);
        return "editPatients";
    }
}

