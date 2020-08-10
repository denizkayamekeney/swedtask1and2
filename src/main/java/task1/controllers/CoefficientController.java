package task1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import task1.dto.Coefficient;
import task1.services.CoefficientService;
import task1.services.CoefficientsGroupService;

import javax.validation.Valid;
import java.util.Optional;


@Controller
public class CoefficientController {
    @Autowired
    CoefficientService coefficientService;

    @Autowired
    CoefficientsGroupService coefficientsGroupService;

    @GetMapping("/coefficients/findbygroup")
    public String groupFindAll( @RequestParam("id") Integer groupId, Model model) {
        model.addAttribute("coefficients",coefficientService.findByGroupId(groupId));
        model.addAttribute("groupid",groupId);
        return "coefficients/index";
    }
    @GetMapping("/coefficients/updateForm")
    public String showFormForUpdate(@RequestParam("id") int id,
                                    Model model) {
        model.addAttribute("coefficient", coefficientService.findById(id).orElse(null));
        model.addAttribute("groups", coefficientsGroupService.findAll());
        return "coefficients/coefficient-form";
    }
//
    @GetMapping("/coefficients/add")
    public String showFormForAdd( @RequestParam("groupid") Optional<Integer> groupId, Model model) {
        Coefficient coefficient = new Coefficient();
        Integer evalGroupId = groupId.orElse(null);
        coefficient.setGroupId(evalGroupId);
        model.addAttribute("coefficient", coefficient);
        model.addAttribute("groupid", evalGroupId);
        model.addAttribute("groups", coefficientsGroupService.findAll());
        return "coefficients/coefficient-form";
    }
    @PostMapping("/coefficients/save")
    public String saveVehicle( @Valid @ModelAttribute("coefficient") Coefficient coefficient, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "coefficients/coefficient-form";
        }
        coefficient = coefficientService.save(coefficient);
        return "redirect:/coefficients/findbygroup?id=" + coefficient.getGroupId();
    }


    @GetMapping("/coefficients/delete")
    public String delete(@RequestParam("id") int id) {

        Coefficient coefficient = coefficientService.findById(id).orElse(null);

        if (coefficient == null) {
            return "redirect:/coefficients/group/findall";
        }
        coefficientService.deleteById(id);
        return "redirect:/coefficients/findbygroup?id=" + coefficient.getGroupId();


    }

}
