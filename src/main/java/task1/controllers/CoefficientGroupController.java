package task1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import task1.dto.CoefficientGroup;
import task1.services.CoefficientService;
import task1.services.CoefficientsGroupService;

import javax.validation.Valid;


@Controller
public class CoefficientGroupController {
    @Autowired
    CoefficientsGroupService coefficientsGroupService;

    @GetMapping("/coefficients/group/findall")
    public String groupFindAll( Model model) {
        model.addAttribute("groups",coefficientsGroupService.findAll());
        return "coefficients/group/index";
    }
    @GetMapping("/coefficients/group/updateForm")
    public String showFormForUpdate(@RequestParam("id") int id,
                                    Model model) {
        CoefficientGroup coefficientGroup = coefficientsGroupService.findById(id).orElse(null);
        model.addAttribute("group", coefficientGroup);
        return "coefficients/group/coefficient-group-form";
    }

    @GetMapping("/coefficients/group/add")
    public String showFormForAdd(Model model) {
        CoefficientGroup coefficientGroup = new CoefficientGroup();
        model.addAttribute("group", coefficientGroup);
        return "coefficients/group/coefficient-group-form";
    }

    @PostMapping("/coefficients/group/save")
    public String saveVehicle( @Valid @ModelAttribute("group") CoefficientGroup coefficientGroup, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "coefficients/group/coefficient-group-form";
        }
        coefficientsGroupService.save(coefficientGroup);
        return "redirect:/coefficients/group/findall";
    }

    @GetMapping("/coefficients/group/delete")
    public String delete(@RequestParam("id") int id) {
        coefficientsGroupService.deleteById(id);
        return "redirect:/coefficients/group/findall";

    }

}
